package com.example.postgresql.model.Education.Gradebook;

import com.example.postgresql.model.Education.Group.Group;
import com.example.postgresql.model.TeacherAssignment;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ELD_GRADEBOOK_DAYS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class GradebookDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gd_ta_id", nullable = false, foreignKey = @ForeignKey(name = "gd_ta_id"))
    private TeacherAssignment teacherAssignment;

    @ManyToOne
    @JoinColumn(name = "gd_sl_id", nullable = false, foreignKey = @ForeignKey(name = "gd_sl_id"))
    private ScheduleLesson scheduleLesson;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime dateTime;

    private String topic;
    private String homework;

    @Column(nullable = false)
    @NonNull
    private Long quarterNumber;
}