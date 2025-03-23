package com.example.postgresql.model.Education.Gradebook;

import com.example.postgresql.model.Education.Group.Group;
import com.example.postgresql.model.TeacherAssignment;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ELD_SHEDULE_LESSONS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ScheduleLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sl_g_id", nullable = false, foreignKey = @ForeignKey(name = "sl_g_id"))
    private Group group;

    @ManyToOne
    @JoinColumn(name = "sl_sh_d", nullable = false, foreignKey = @ForeignKey(name = "sl_sh_d"))
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "sl_ta_id", nullable = false, foreignKey = @ForeignKey(name = "sl_ta_id"))
    private TeacherAssignment teacherAssignment;

    @Column(nullable = false)
    @NonNull
    private Long lessonNumber;

}