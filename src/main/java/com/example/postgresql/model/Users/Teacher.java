package com.example.postgresql.model.Users;

import javax.persistence.*;

import com.example.postgresql.model.Users.Education.EducationalInstitution;
import com.example.postgresql.model.Users.User.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ELD_TEACHERS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "t_u_id", nullable = false, foreignKey = @ForeignKey(name = "t_u_id"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "t_ei_id", foreignKey = @ForeignKey(name = "t_ei_id"))
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
