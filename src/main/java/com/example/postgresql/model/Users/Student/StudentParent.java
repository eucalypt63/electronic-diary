package com.example.postgresql.model.Users.Student;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ELD_SCHOOL_STUDENTS_AND_PARENTS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class StudentParent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "ssap_sst_id", nullable = false, foreignKey = @ForeignKey(name = "ssap_sst_id"))
    private SchoolStudent schoolStudent;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "ssap_p_id", nullable = false, foreignKey = @ForeignKey(name = "ssap_p_id"))
    private Parent parent;
}
