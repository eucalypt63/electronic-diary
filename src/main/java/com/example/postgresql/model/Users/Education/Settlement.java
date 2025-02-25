package com.example.postgresql.model.Users.Education;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ELD_SETTLEMENTS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "s_r_id", nullable = false, foreignKey = @ForeignKey(name = "s_r_id"))
    private Region region;
}