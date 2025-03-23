package com.example.postgresql.model.Education.Gradebook;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ELD_SCHEDULE")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private Long quarterNumber;

    @Column(nullable = false)
    @NonNull
    private Long dayNumber;
}