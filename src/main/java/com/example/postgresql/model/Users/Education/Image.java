package com.example.postgresql.model.Users.Education;

import com.example.postgresql.model.Users.Education.EducationalInstitution;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ELD_IMAGES")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String pathImages;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "i_ei_id", nullable = false, foreignKey = @ForeignKey(name = "i_ei_id"))
    private EducationalInstitution educationalInstitution;
}