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

@Entity
@Table(name = "ELD_YEAR_SCORES")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class YearScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ys_ss_id", foreignKey = @ForeignKey(name = "ys_ss_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SchoolSubject schoolSubject;

    @ManyToOne
    @JoinColumn(name = "ys_sst_id", foreignKey = @ForeignKey(name = "ys_sst_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SchoolStudent schoolStudent;

    @Column(nullable = false)
    @NonNull
    private Long score;
}
