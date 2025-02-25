package com.example.postgresql.model.Users;

import javax.persistence.*;

import com.example.postgresql.model.Users.User.User;
import lombok.*;

@Entity
@Table(name = "ELD_ADMINISTRATORS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "a_u_id", nullable = false, foreignKey = @ForeignKey(name = "a_u_id"))
    private User user;

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
