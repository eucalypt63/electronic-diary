package com.example.postgresql.model.Users.Education;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ELD_EDUCATIONAL_INSTITUTIONS_TYPES")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class EducationalInstitutionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;
}