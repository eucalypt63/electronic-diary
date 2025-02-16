package com.example.postgresql.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ELD_USERS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String login;

    @Column(nullable = false)
    @NonNull
    private byte[] hash;

    @Column(nullable = false)
    @NonNull
    private byte[] salt;

    @ManyToOne
    @JoinColumn(name = "u_ei_id", foreignKey = @ForeignKey(name = "u_ei_id"))
    private EducationalInstitution educationalInstitution;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "u_ut_id", nullable = false, foreignKey = @ForeignKey(name = "u_ut_id"))
    private UserType userType;

}