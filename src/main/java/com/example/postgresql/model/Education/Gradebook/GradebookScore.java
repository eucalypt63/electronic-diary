package com.example.postgresql.model.Education.Gradebook;

import com.example.postgresql.model.Users.Student.SchoolStudent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ELD_GRADEBOOK_SCORES")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class GradebookScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gs_gd_id", nullable = false, foreignKey = @ForeignKey(name = "gs_gd_id"))
    private GradebookDay gradebookDay;

    @ManyToOne
    @JoinColumn(name = "gs_sst_id", nullable = false, foreignKey = @ForeignKey(name = "gs_sst_id"))
    private SchoolStudent schoolStudent;

    @Column(nullable = false)
    @NonNull
    private Long score;
}