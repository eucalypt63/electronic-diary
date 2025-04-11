package com.example.postgresql.model.Education.Gradebook;

import com.example.postgresql.model.Education.Group.Group;
import com.example.postgresql.model.TeacherAssignment;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "ELD_SCHEDULE_LESSONS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ScheduleLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sl_g_id", foreignKey = @ForeignKey(name = "sl_g_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "sl_ta_id", foreignKey = @ForeignKey(name = "sl_ta_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TeacherAssignment teacherAssignment;

    @OneToOne
    @JoinColumn(name = "sl_qi_id", foreignKey = @ForeignKey(name = "sl_qi_id"))
    private QuarterInfo quarterInfo;

    @Column(nullable = false)
    @NonNull
    private Long dayNumber;

    @Column(nullable = false)
    @NonNull
    private Long lessonNumber;
}