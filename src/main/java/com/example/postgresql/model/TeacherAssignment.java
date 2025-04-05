package com.example.postgresql.model;

import javax.persistence.*;

import com.example.postgresql.model.Education.Group.Group;
import com.example.postgresql.model.Users.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "ELD_TEACHER_ASSIGNMENTS")
@Data
@NoArgsConstructor
public class TeacherAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ta_t_id", foreignKey = @ForeignKey(name = "ta_t_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "ta_ss_id", foreignKey = @ForeignKey(name = "ta_ss_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SchoolSubject schoolSubject;

    @ManyToOne
    @JoinColumn(name = "ta_g_id", foreignKey = @ForeignKey(name = "ta_g_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Group group;
}