package com.example.postgresql.model.Users.Education;

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
    private String phoneNumber;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "ei_eit_id", nullable = false, foreignKey = @ForeignKey(name = "ei_eit_id"))
    private EducationalInstitutionType educationalInstitutionType;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "ei_s_id", nullable = false, foreignKey = @ForeignKey(name = "ei_s_id"))
    private Settlement settlement;
}