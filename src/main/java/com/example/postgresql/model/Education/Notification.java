package com.example.postgresql.model.Education;

import com.example.postgresql.model.Users.User.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ELD_NOTIFICATION")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "no_u_id", foreignKey = @ForeignKey(name = "no_u_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(nullable = false)
    @NonNull
    private String title;

    @Column(nullable = false)
    @NonNull
    private String content;

    @Column(nullable = false)
    @NonNull
    private String link;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime dateTime;
}