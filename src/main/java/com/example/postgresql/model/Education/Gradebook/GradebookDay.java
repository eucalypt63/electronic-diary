package com.example.postgresql.model.Education.Gradebook;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @JoinColumn(name = "gd_sl_id",  foreignKey = @ForeignKey(name = "gd_sl_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ScheduleLesson scheduleLesson;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime dateTime;

    private String topic;
    private String homework;
}