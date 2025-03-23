package com.example.postgresql.model.Education.Gradebook;

import com.example.postgresql.model.Education.Group.Group;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ELD_GRADEBOOK_DAYS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class GradebookDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gd_g_id", nullable = false, foreignKey = @ForeignKey(name = "gd_g_id"))
    private Group group;

    private LocalDateTime day;
    private String topic;

    @Column(nullable = false)
    @NonNull
    private String homework;

    @Column(nullable = false)
    @NonNull
    private Long quarterNumber;
}