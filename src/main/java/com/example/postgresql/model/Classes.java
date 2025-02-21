package com.example.postgresql.model;

import javax.persistence.*;

import com.example.postgresql.model.Users.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ELD_CLASSES")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "c_t_id", nullable = false, foreignKey = @ForeignKey(name = "c_t_id"))
    private Teacher teacher;
}
