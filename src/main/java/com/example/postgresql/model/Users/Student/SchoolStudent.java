package com.example.postgresql.model.Users.Student;

import javax.persistence.*;

import com.example.postgresql.model.Class;
import com.example.postgresql.model.Users.User.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ELD_SCHOOL_STUDENTS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SchoolStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @OneToOne
    @JoinColumn(name = "sst_u_id", nullable = false, foreignKey = @ForeignKey(name = "sst_u_id"))
    private User user;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "sst_c_id", nullable = false, foreignKey = @ForeignKey(name = "sst_c_id"))
    private Class classRoom;

    @ManyToOne
    @JoinColumn(name = "sst_p_father_id", foreignKey = @ForeignKey(name = "sst_p_father_id"))
    private Parent father;

    @ManyToOne
    @JoinColumn(name = "sst_p_mother_id", foreignKey = @ForeignKey(name = "sst_p_mother_id"))
    private Parent mother;

    @Column(nullable = false)
    @NonNull
    private String firstName;

    @Column(nullable = false)
    @NonNull
    private String lastName;

    private String patronymic;
    private String pathImage;
    private String email;
    private String phoneNumber;
}
