package com.example.postgresql.model.Education.Group;

import com.example.postgresql.model.Users.Student.SchoolStudent;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "gm_g_id", nullable = false, foreignKey = @ForeignKey(name = "gm_g_id"))
    private Group group;

    @ManyToOne
    @JoinColumn(name = "gm_sst_id", nullable = false, foreignKey = @ForeignKey(name = "gm_sst_id"))
    private SchoolStudent schoolStudent;
}