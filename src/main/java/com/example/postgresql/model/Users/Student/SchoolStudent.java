package com.example.postgresql.model.Users.Student;

import javax.persistence.*;

import com.example.postgresql.model.Class;
import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.User.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ELD_SCHOOL_STUDENTS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SchoolStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "sst_u_id", nullable = false, foreignKey = @ForeignKey(name = "sst_u_id"))
    private User user;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "sst_c_id", nullable = false, foreignKey = @ForeignKey(name = "sst_c_id"))
    private Class classRoom;

    @ManyToOne
    @JoinColumn(name = "sst_ei_id", nullable = false, foreignKey = @ForeignKey(name = "sst_ei_id"))
    private EducationalInstitution educationalInstitution;

    @Column(nullable = false)
    @NonNull
    private String firstName;

    @Column(nullable = false)
    @NonNull
    private String lastName;

    private String patronymic;
    private String pathImage;
    private String email;
    private String phoneNumber;
}
