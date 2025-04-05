package com.example.postgresql.model.Education.Gradebook;

import com.example.postgresql.model.Users.Student.SchoolStudent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "ELD_GRADEBOOK_ATTENDANCES")
@Data
@NoArgsConstructor
public class GradebookAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ga_gd_id", foreignKey = @ForeignKey(name = "ga_gd_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GradebookDay gradebookDay;

    @ManyToOne
    @JoinColumn(name = "ga_sst_id", foreignKey = @ForeignKey(name = "ga_sst_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SchoolStudent schoolStudent;
}