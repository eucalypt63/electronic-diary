package com.example.postgresql.model.Users.Student;

import javax.persistence.*;

import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Users.User.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "ELD_PARENTS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "p_u_id", foreignKey = @ForeignKey(name = "p_u_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "p_ei_id", foreignKey = @ForeignKey(name = "p_ei_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
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
