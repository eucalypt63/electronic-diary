package com.example.postgresql.model;

import javax.persistence.*;
import javax.security.auth.Subject;

import com.example.postgresql.model.Users.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ELD_TEACHER_ASSIGNMENTS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TeacherAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "ta_t_id", nullable = false, foreignKey = @ForeignKey(name = "ta_t_id"))
    private Teacher teacher;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "ta_ss_id", nullable = false, foreignKey = @ForeignKey(name = "ta_ss_id"))
    private SchoolSubject schoolSubject;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "ta_c_id", nullable = false, foreignKey = @ForeignKey(name = "ta_c_id"))
    private Classes classRoom;
}