package com.example.postgresql.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ELD_EDUCATIONAL_INSTITUTIONS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class EducationalInstitution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false)
    @NonNull
    private String address;

    private String pathImage;
    private String email;

    @Column(nullable = false)
    @NonNull
    private String phoneNumber;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "ei_eit_id", nullable = false, foreignKey = @ForeignKey(name = "ei_eit_id"))
    private EducationalInstitutionType educationalInstitutionType;
}