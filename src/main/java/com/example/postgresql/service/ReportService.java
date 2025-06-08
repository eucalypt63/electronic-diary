package com.example.postgresql.service;

import com.example.postgresql.model.Class;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Education.Gradebook.*;
import com.example.postgresql.model.Education.Group.Group;
import com.example.postgresql.model.Report;
import com.example.postgresql.model.SchoolSubject;
import com.example.postgresql.model.TeacherAssignment;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import com.example.postgresql.repository.ReportRepository;
import com.example.postgresql.repository.SchoolSubjectRepository;
import com.example.postgresql.service.Education.*;
import com.example.postgresql.service.Users.SchoolStudentService;
import com.example.postgresql.service.Users.TeacherService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private GradebookService gradebookService;
    @Autowired
    private ClassService classService;
    @Autowired
    private SchoolStudentService schoolStudentService;
    @Autowired
    private SchoolSubjectRepository schoolSubjectRepository;
    @Autowired
    private EducationalInstitutionService educationalInstitutionService;
    @Autowired
    private QuarterScoreService quarterScoreService;
    @Autowired
    private YearScoreService yearScoreService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private TeacherService teacherService;


    public List<Report> findReportByEducationalInstitutionId(Long id){return reportRepository.findReportByEducationalInstitutionId(id);}
    public void deleteReportById(Long id){reportRepository.deleteById(id);}

    public Report findReportById(Long id){return reportRepository.findById(id).orElse(null);}

    private final String uploadUrl = "http://77.222.37.9/files/";


    public Report createReport(Long educationalInstitutionId) {
        // 1. Получаем учебное заведение
        EducationalInstitution institution = educationalInstitutionService
                .findEducationalInstitutionById(educationalInstitutionId);

        // 2. Генерируем Excel-файл
        byte[] reportContent = generateExcelReport(institution);

        // 3. Создаём уникальное имя файла
        String filename = "report_" + institution.getId() + "_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";

        // 4. Загружаем файл на сервер
        String fileUrl = uploadFile(reportContent, filename);

        // 5. Сохраняем отчёт в БД
        Report report = new Report();
        report.setEducationalInstitution(institution);
        report.setDateTime(LocalDateTime.now());
        report.setPathToFile(fileUrl);

        return reportRepository.save(report);
    }

    private byte[] generateExcelReport(EducationalInstitution institution) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             XSSFWorkbook workbook = new XSSFWorkbook()) {

            // 1. Инициализация кэша для стилей текста
            Map<String, XSSFCellStyle> textStyles = new HashMap<>();

            // 2. Создание стилей
            XSSFCellStyle defaultStyle = workbook.createCellStyle();
            XSSFFont defaultFont = workbook.createFont();
            defaultFont.setColor(new XSSFColor(new byte[] {0, 0, 0}, null));
            defaultStyle.setFont(defaultFont);
            defaultStyle.setAlignment(HorizontalAlignment.CENTER);

            XSSFCellStyle headerStyle = workbook.createCellStyle();
            XSSFFont headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            // 2. Получение данных
            List<Class> classes = classService.findAllByTeacherEducationalInstitutionId(institution.getId());
            List<SchoolSubject> schoolSubjects = schoolSubjectRepository.findAll();

            for (Class cl : classes) {
                Sheet sheet = workbook.createSheet(cl.getName());
                int rowNum = 0;

                List<SchoolStudent> schoolStudents = schoolStudentService.findAllSchoolStudentByClassId(cl.getId());
                List<QuarterScore> allQuarterScores = Collections.synchronizedList(new ArrayList<>());
                List<YearScore> allYearScores = Collections.synchronizedList(new ArrayList<>());

                // Параллельный сбор всех оценок
                schoolSubjects.parallelStream().forEach(subject -> {
                    schoolStudents.parallelStream().forEach(student -> {
                        // Получение четвертных оценок
                        List<QuarterScore> quarterScores = quarterScoreService
                                .findQuarterScoreBySchoolStudentIdAndSchoolSubjectId(
                                        student.getId(),
                                        subject.getId()
                                );
                        allQuarterScores.addAll(quarterScores);

                        // Получение годовой оценки
                        YearScore yearScore = yearScoreService.findYearScoreBySchoolStudentIdAndSchoolSubjectId(
                                student.getId(),
                                subject.getId()
                        );
                        if (yearScore != null) {
                            allYearScores.add(yearScore);
                        }
                    });
                });

                // 3. Расчет средних баллов
                Map<SchoolSubject, Map<QuarterInfo, Double>> subjectQuarterAverages = calculateSubjectQuarterAverages(schoolSubjects, allQuarterScores);
                Map<SchoolStudent, Map<QuarterInfo, Double>> studentQuarterAverages = calculateStudentQuarterAverages(schoolStudents, allQuarterScores);
                Map<SchoolSubject, Double> subjectYearAverages = calculateSubjectYearAverages(schoolSubjects, allYearScores);
                Map<SchoolStudent, Double> studentYearAverages = calculateStudentYearAverages(schoolStudents, allYearScores);

                // 4. Таблица средних по предметам
                Row headerRow = sheet.createRow(rowNum++);
                headerRow.createCell(0).setCellValue("Предмет");
                for (int i = 1; i <= 4; i++) {
                    headerRow.createCell(i).setCellValue("Четверть " + i);
                }
                headerRow.createCell(5).setCellValue("Годовая");

                for (int i = 0; i <= 5; i++) {
                    headerRow.getCell(i).setCellStyle(headerStyle);
                }

                // Фильтруем предметы с оценками
                List<SchoolSubject> subjectsWithScores = schoolSubjects.stream()
                        .filter(subject -> !subjectQuarterAverages.get(subject).isEmpty())
                        .toList();

                // 2. Заполнение таблицы с правильным форматированием чисел
                for (SchoolSubject subject : subjectsWithScores) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(subject.getName());

                    Map<QuarterInfo, Double> quarterAverages = subjectQuarterAverages.get(subject);
                    for (int i = 1; i <= 4; i++) {
                        final int quarterNum = i;
                        Double avg = quarterAverages.entrySet().stream()
                                .filter(e -> e.getKey().getQuarterNumber() == quarterNum)
                                .map(Map.Entry::getValue)
                                .findFirst()
                                .orElse(null);

                        if (avg != null) {
                            Cell cell = row.createCell(i);
                            // Округление до двух знаков без проблем с локализацией
                            double roundedValue = Math.round(avg * 100) / 100.0;
                            cell.setCellValue(roundedValue);

                            // Расчет цвета текста
                            byte red = (byte) Math.min(255, (10 - avg) * 28.33);
                            byte green = (byte) Math.min(255, avg * 25.5);

                            // Создание или получение стиля из кэша
                            String styleKey = red + "_" + green + "_0";
                            XSSFCellStyle textStyle = textStyles.computeIfAbsent(styleKey, k -> {
                                XSSFCellStyle style = workbook.createCellStyle();
                                XSSFFont font = workbook.createFont();
                                font.setColor(new XSSFColor(new byte[] {red, green, (byte)0}, null));
                                style.setFont(font);
                                style.setAlignment(HorizontalAlignment.CENTER);
                                return style;
                            });

                            cell.setCellStyle(textStyle);
                        }
                    }

                    // 1. Для годовых оценок по предметам
                    Double yearAvg = subjectYearAverages.get(subject);
                    if (yearAvg != null) {
                        Cell cell = row.createCell(5);
                        double roundedValue = Math.round(yearAvg * 100) / 100.0;
                        cell.setCellValue(roundedValue);

                        byte red = (byte) Math.min(255, (10 - yearAvg) * 28.33);
                        byte green = (byte) Math.min(255, yearAvg * 25.5);
                        String styleKey = red + "_" + green + "_0";
                        XSSFCellStyle textStyle = textStyles.computeIfAbsent(styleKey, k -> {
                            XSSFCellStyle style = workbook.createCellStyle();
                            XSSFFont font = workbook.createFont();
                            font.setColor(new XSSFColor(new byte[]{red, green, (byte)0}, null));
                            style.setFont(font);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            return style;
                        });
                        cell.setCellStyle(textStyle);
                    }
                }

                // 5. Таблица учеников с пропусками
                rowNum += 2;

                List<Group> groups = groupService.findGroupByClassId(cl.getId());
                Map<SchoolStudent, Map<Integer, Double>> studentAbsencePercentages = new ConcurrentHashMap<>();

                schoolStudents.parallelStream().forEach(student -> {
                    Map<Integer, Double> absenceMap = new ConcurrentHashMap<>();
                    studentAbsencePercentages.put(student, absenceMap);

                    // Получаем группы студента
                    Set<Long> studentGroupIds = groupService.findGroupMemberBySchoolStudentId(student.getId())
                            .parallelStream()
                            .map(groupMember -> groupMember.getGroup().getId())
                            .collect(Collectors.toSet());

                    List<Group> filteredGroups = groups.parallelStream()
                            .filter(group -> studentGroupIds.contains(group.getId()))
                            .toList();

                    // Инициализация структур для хранения данных по четвертям
                    Map<Integer, List<GradebookDay>> quarterGradebookDays = new ConcurrentHashMap<>();
                    Map<Integer, List<GradebookAttendance>> gradebookAttendances = new ConcurrentHashMap<>();

                    // Параллельная обработка четвертей
                    IntStream.rangeClosed(1, 4).parallel().forEach(quarter -> {
                        quarterGradebookDays.put(quarter, new CopyOnWriteArrayList<>());
                        gradebookAttendances.put(quarter, gradebookService
                                .findAttendancesBySchoolStudentIdAndGradebookDayScheduleLessonQuarterInfoId(
                                        student.getId(),
                                        (long) quarter
                                ));
                    });

                    // Параллельная обработка предметов и групп
                    schoolSubjects.parallelStream().forEach(subject -> {
                        filteredGroups.parallelStream().forEach(group -> {
                            List<TeacherAssignment> teacherAssignments = teacherService
                                    .findTeacherAssignmentByGroupIdAndSchoolSubjectId(
                                            group.getId(),
                                            subject.getId()
                                    );

                            if (!teacherAssignments.isEmpty()) {
                                teacherAssignments.parallelStream().forEach(teacherAssignment -> {
                                    IntStream.rangeClosed(1, 4).parallel().forEach(quarter -> {
                                        List<GradebookDay> days = gradebookService
                                                .findGradebookDayByScheduleLessonTeacherAssignmentId(
                                                        teacherAssignment.getId(),
                                                        (long) quarter
                                                );
                                        if (days != null) {
                                            quarterGradebookDays.get(quarter).addAll(days);
                                        }
                                    });
                                });
                            }
                        });
                    });

                    // Расчет процента пропусков по четвертям
                    IntStream.rangeClosed(1, 4).parallel().forEach(quarter -> {
                        List<GradebookAttendance> attendances = gradebookAttendances.getOrDefault(quarter, Collections.emptyList());
                        long absentLessons = attendances.size();
                        long totalLessons = quarterGradebookDays.getOrDefault(quarter, Collections.emptyList()).size();
                        double absencePercentage = totalLessons > 0 ?
                                ((double) absentLessons / totalLessons) * 100 : 0.0;
                        absenceMap.put(quarter, absencePercentage);
                    });
                });

                // Создаем заголовки таблицы учеников
                Row studentHeaderRow = sheet.createRow(rowNum++);
                studentHeaderRow.createCell(0).setCellValue("Ученик");

                Row subHeaderRow = sheet.createRow(rowNum++);
                for (int quarter = 1; quarter <= 4; quarter++) {
                    int col = (quarter-1)*2 + 1;

                    sheet.addMergedRegion(new CellRangeAddress(rowNum-2, rowNum-2, col, col+1));

                    Cell quarterHeader = studentHeaderRow.createCell(col);
                    quarterHeader.setCellValue("Четверть " + quarter);
                    quarterHeader.setCellStyle(headerStyle);

                    subHeaderRow.createCell(col).setCellValue("Средний балл");
                    subHeaderRow.createCell(col+1).setCellValue("Пропуски %");

                    subHeaderRow.getCell(col).setCellStyle(headerStyle);
                    subHeaderRow.getCell(col+1).setCellStyle(headerStyle);
                }

                studentHeaderRow.createCell(9).setCellValue("Годовая");
                studentHeaderRow.getCell(9).setCellStyle(headerStyle);

                // Заполняем данные учеников
                for (SchoolStudent student : schoolStudents) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(
                            student.getLastName() + " " + student.getFirstName() +
                                    (student.getPatronymic() != null ? " " + student.getPatronymic() : "")
                    );

                    Map<QuarterInfo, Double> quarterAverages = studentQuarterAverages.get(student);
                    Map<Integer, Double> absencePercentages = studentAbsencePercentages.get(student);

                    for (int quarter = 1; quarter <= 4; quarter++) {
                        int col = (quarter-1)*2 + 1;

                        // Средний балл
                        int finalQuarter = quarter;
                        Double avg = quarterAverages.entrySet().stream()
                                .filter(e -> e.getKey().getQuarterNumber() == finalQuarter)
                                .map(Map.Entry::getValue)
                                .findFirst()
                                .orElse(null);

                        if (avg != null) {
                            Cell scoreCell = row.createCell(col);
                            double roundedValue = Math.round(avg * 100) / 100.0;
                            scoreCell.setCellValue(roundedValue);

                            byte red = (byte) Math.min(255, (10 - avg) * 28.33);
                            byte green = (byte) Math.min(255, avg * 25.5);
                            String styleKey = red + "_" + green + "_0";
                            XSSFCellStyle textStyle = textStyles.computeIfAbsent(styleKey, k -> {
                                XSSFCellStyle style = workbook.createCellStyle();
                                XSSFFont font = workbook.createFont();
                                font.setColor(new XSSFColor(new byte[]{red, green, (byte)0}, null));
                                style.setFont(font);
                                style.setAlignment(HorizontalAlignment.CENTER);
                                return style;
                            });
                            scoreCell.setCellStyle(textStyle);
                        }

                        // Процент пропусков
                        Double absencePct = absencePercentages.get(quarter);
                        Cell absenceCell = row.createCell(col+1);
                        double roundedPct = Math.round(absencePct * 100) / 100.0;
                        absenceCell.setCellValue(roundedPct);

                        byte red = (byte) Math.min(255,
                                (10 - Math.min(10, avg)) * 25 +
                                        Math.min(30, absencePct) * 8.5);
                        byte green = (byte) Math.max(0,
                                128 - (10 - Math.min(10, avg)) * 12 -
                                        Math.min(30, absencePct) * 4.2);

                        absenceCell.setCellStyle(createTextStyle(workbook, red, green, (byte)0, textStyles));
                    }

                    Double yearAvg = studentYearAverages.get(student);
                    if (yearAvg != null) {
                        Cell cell = row.createCell(9);
                        double roundedValue = Math.round(yearAvg * 100) / 100.0;
                        cell.setCellValue(roundedValue);

                        byte red = (byte) Math.min(255, (10 - yearAvg) * 28.33);
                        byte green = (byte) Math.min(255, yearAvg * 25.5);
                        String styleKey = red + "_" + green + "_0";
                        XSSFCellStyle textStyle = textStyles.computeIfAbsent(styleKey, k -> {
                            XSSFCellStyle style = workbook.createCellStyle();
                            XSSFFont font = workbook.createFont();
                            font.setColor(new XSSFColor(new byte[]{red, green, (byte)0}, null));
                            style.setFont(font);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            return style;
                        });
                        cell.setCellStyle(textStyle);
                    }
                }

                // Автоподбор ширины столбцов
                for (int i = 0; i < 10; i++) {
                    sheet.autoSizeColumn(i);
                }
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка генерации отчёта", e);
        }
    }

    private XSSFCellStyle createTextStyle(XSSFWorkbook workbook, byte red, byte green, byte blue,
                                          Map<String, XSSFCellStyle> styleCache) {
        String key = red + "_" + green + "_" + blue;
        if (!styleCache.containsKey(key)) {
            XSSFCellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setColor(new XSSFColor(new byte[] {red, green, blue}, null));
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            styleCache.put(key, style);
        }
        return styleCache.get(key);
    }

    private XSSFCellStyle getOrCreateTextStyle(XSSFWorkbook workbook, byte red, byte green, byte blue,
                                               Map<String, XSSFCellStyle> styleCache) {
        String key = red + "_" + green + "_" + blue;
        if (!styleCache.containsKey(key)) {
            XSSFCellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setColor(new XSSFColor(new byte[] {red, green, blue}, null));
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            styleCache.put(key, style);
        }
        return styleCache.get(key);
    }

    // Вспомогательные методы для вычисления средних
    private Map<SchoolSubject, Map<QuarterInfo, Double>> calculateSubjectQuarterAverages(
            List<SchoolSubject> subjects, List<QuarterScore> scores) {
        return subjects.stream()
                .collect(Collectors.toMap(
                        subject -> subject,
                        subject -> scores.stream()
                                .filter(score -> score.getSchoolSubject().equals(subject))
                                .collect(Collectors.groupingBy(
                                                QuarterScore::getQuarterInfo,
                                                Collectors.averagingDouble(QuarterScore::getScore)
                                        ))
                                ));
    }

    private Map<SchoolStudent, Map<QuarterInfo, Double>> calculateStudentQuarterAverages(
            List<SchoolStudent> students, List<QuarterScore> scores) {
        return students.stream()
                .collect(Collectors.toMap(
                        student -> student,
                        student -> scores.stream()
                                .filter(score -> score.getSchoolStudent().equals(student))
                                .collect(Collectors.groupingBy(
                                        QuarterScore::getQuarterInfo,
                                        Collectors.averagingDouble(QuarterScore::getScore)
                                ))
                ));
    }

    private Map<SchoolSubject, Double> calculateSubjectYearAverages(
            List<SchoolSubject> subjects, List<YearScore> scores) {
        return subjects.stream()
                .collect(Collectors.toMap(
                        subject -> subject,
                        subject -> scores.stream()
                                .filter(score -> score.getSchoolSubject().equals(subject))
                                .mapToDouble(YearScore::getScore)
                                .average()
                                .orElse(0.0)
                ));
    }

    private Map<SchoolStudent, Double> calculateStudentYearAverages(
            List<SchoolStudent> students, List<YearScore> scores) {
        return students.stream()
                .collect(Collectors.toMap(
                        student -> student,
                        student -> scores.stream()
                                .filter(score -> score.getSchoolStudent().equals(student))
                                .mapToDouble(YearScore::getScore)
                                .average()
                                .orElse(0.0)
                ));
    }

    private String uploadFile(byte[] content, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(content, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                uploadUrl + filename,
                HttpMethod.PUT,
                requestEntity,
                String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Ошибка загрузки файла: " + response.getBody());
        }

        return uploadUrl + filename;
    }
}
