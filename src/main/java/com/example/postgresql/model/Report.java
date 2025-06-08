package com.example.postgresql.model;

import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ELD_REPORT")
@Data
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "r_ei_id", foreignKey = @ForeignKey(name = "r_ei_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EducationalInstitution educationalInstitution;

    @Column(nullable = false)
    @NonNull
    private String pathToFile;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime dateTime;

}