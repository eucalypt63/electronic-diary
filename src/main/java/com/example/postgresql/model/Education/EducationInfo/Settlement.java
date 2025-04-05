package com.example.postgresql.model.Education.EducationInfo;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne
    @JoinColumn(name = "s_r_id", foreignKey = @ForeignKey(name = "s_r_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Region region;
}