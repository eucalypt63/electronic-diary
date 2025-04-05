package com.example.postgresql.model;

import javax.persistence.*;

import com.example.postgresql.model.Users.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "ELD_CLASSES")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @OneToOne
    @JoinColumn(name = "c_t_id", foreignKey = @ForeignKey(name = "c_t_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Teacher teacher;
}