package com.example.postgresql.model.Users.Student;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "ELD_SCHOOL_STUDENTS_AND_PARENTS")
@Data
@NoArgsConstructor
public class StudentParent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sstap_sst_id", foreignKey = @ForeignKey(name = "sstap_sst_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SchoolStudent schoolStudent;

    @ManyToOne
    @JoinColumn(name = "sstap_p_id", foreignKey = @ForeignKey(name = "sstap_p_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "sstap_pt_id", foreignKey = @ForeignKey(name = "sstap_pt_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ParentType parentType;
}
