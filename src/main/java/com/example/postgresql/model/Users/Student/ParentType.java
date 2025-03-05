package com.example.postgresql.model.Users.Student;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ELD_PARENTS_TYPES")
@Data
@NoArgsConstructor
public class ParentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}