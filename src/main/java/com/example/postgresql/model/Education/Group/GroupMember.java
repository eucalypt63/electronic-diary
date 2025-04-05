package com.example.postgresql.model.Education.Group;

import com.example.postgresql.model.Users.Student.SchoolStudent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "ELD_GROUP_MEMBERS")
@Data
@NoArgsConstructor
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gm_g_id", foreignKey = @ForeignKey(name = "gm_g_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Group group;

    @OneToOne
    @JoinColumn(name = "gm_sst_id",  foreignKey = @ForeignKey(name = "gm_sst_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SchoolStudent schoolStudent;
}