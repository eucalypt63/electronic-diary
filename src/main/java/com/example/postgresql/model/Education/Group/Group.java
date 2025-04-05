package com.example.postgresql.model.Education.Group;

import javax.persistence.*;

import com.example.postgresql.model.Class;
import com.example.postgresql.model.TeacherAssignment;
import com.example.postgresql.model.Users.Student.SchoolStudent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "ELD_GROUPS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "g_c_id", foreignKey = @ForeignKey(name = "g_c_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Class classRoom;

    @Column(nullable = false)
    @NonNull
    private String groupName;
}