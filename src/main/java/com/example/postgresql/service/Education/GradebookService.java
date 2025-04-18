package com.example.postgresql.service.Education;

import com.example.postgresql.model.Education.Gradebook.GradebookDay;
import com.example.postgresql.repository.Education.Gradebook.GradebookAttendanceRepository;
import com.example.postgresql.repository.Education.Gradebook.GradebookDayRepository;
import com.example.postgresql.repository.Education.Gradebook.GradebookScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradebookService {

    @Autowired
    private GradebookDayRepository gradebookDayRepository;
    @Autowired
    private GradebookScoreRepository gradebookScoreRepository;
    @Autowired
    private GradebookAttendanceRepository gradebookAttendanceRepository;

    public void saveGradebookDay(GradebookDay gradebookDay){gradebookDayRepository.save(gradebookDay);}
    public List<GradebookDay>findAllGD(){return gradebookDayRepository.findAll();}
    //Нахождения дня по Group->TA->Id
    //Нахождение по id дня для посещаемости и оценок

}
