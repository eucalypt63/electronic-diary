package com.example.postgresql.model.Education.Gradebook;

import com.example.postgresql.model.SchoolSubject;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ELD_QUARTER_INFO")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class QuarterInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private Long quarterNumber;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime dateStartTime;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime dateEndTime;
}
