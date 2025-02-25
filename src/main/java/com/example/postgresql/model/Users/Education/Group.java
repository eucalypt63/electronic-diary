package com.example.postgresql.model.Users.Education;

import javax.persistence.*;

import com.example.postgresql.model.TeacherAssignment;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ELD_GROUPS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "g_ta_id", nullable = false, foreignKey = @ForeignKey(name = "g_ta_id"))
    private TeacherAssignment teacherAssignment;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "g_ss_id", nullable = false, foreignKey = @ForeignKey(name = "g_ss_id"))
    private SchoolStudent schoolStudent;
}