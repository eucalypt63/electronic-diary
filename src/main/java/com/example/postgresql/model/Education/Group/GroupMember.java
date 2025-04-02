package com.example.postgresql.model.Education.Group;

import com.example.postgresql.model.Users.Student.SchoolStudent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ELD_GROUP_MEMBERS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "gm_g_id", nullable = false, foreignKey = @ForeignKey(name = "gm_g_id"))
    private Group group;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "gm_sst_id", nullable = false, foreignKey = @ForeignKey(name = "gm_sst_id"))
    private SchoolStudent schoolStudent;
}