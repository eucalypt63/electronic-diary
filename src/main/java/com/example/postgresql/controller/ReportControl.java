package com.example.postgresql.controller;

import com.example.postgresql.DTO.ResponseDTO.ReportResponseDTO;
import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Report;
import com.example.postgresql.service.Education.EducationalInstitutionService;
import com.example.postgresql.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReportControl {

    @Autowired
    private ReportService reportService;
    @Autowired
    private EducationalInstitutionService educationalInstitutionService;

    @GetMapping("/findReportsByEducationalId")
    @RequiredRoles({"Main admin", "Administration"})
    @ResponseBody
    public ResponseEntity<List<ReportResponseDTO>> findReportsByEducationalId(@RequestParam Long educationalId) {
        List<Report> reports = reportService.findReportByEducationalInstitutionId(educationalId);

        List<ReportResponseDTO> reportResponseDTOS = reports.stream()
                .map(report -> new ReportResponseDTO(
                        report.getId(),
                        report.getDateTime(),
                        "/downloadReport?reportId=" + report.getId()))
                .collect(Collectors.toList());


        return ResponseEntity.ok(reportResponseDTOS);
    }

    @GetMapping("/downloadReport")
    @RequiredRoles({"Main admin", "Administration"})
    public ResponseEntity<Resource> downloadReport(@RequestParam Long reportId) {
        Report report = reportService.findReportById(reportId);

        try {
            Resource resource = new UrlResource(new URL(report.getPathToFile()));

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Could not read file: " + report.getPathToFile());
            }

            String filename = report.getPathToFile().substring(report.getPathToFile().lastIndexOf('/') + 1);
            String contentType = "application/octet-stream";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid URL for file: " + report.getPathToFile(), e);
        }
    }

    @PostMapping("/createReport")
    @RequiredRoles({"Main admin", "Administration"})
    public ResponseEntity<ReportResponseDTO> createReport(@RequestParam Long educationalInstitutionId) {
        Report createdReport = reportService.createReport(educationalInstitutionId);
        ReportResponseDTO responseDTO = new ReportResponseDTO(
                createdReport.getId(),
                createdReport.getDateTime(),
                "/downloadReport?reportId=" + createdReport.getId()
        );

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/deleteReportById")
    @RequiredRoles({"Main admin", "Administration"})
    @ResponseBody
    public ResponseEntity<String> deleteReportById(@RequestParam Long id){
        reportService.deleteReportById(id);
        return ResponseEntity.ok("{\"message\": \"Отчёт успешно удалён \"}");
    }
}
