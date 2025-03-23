package com.example.postgresql.model.Education.News;

import com.example.postgresql.model.Users.User.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ELD_NEWS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "n_u_owner_id", nullable = false, foreignKey = @ForeignKey(name = "n_u_owner_id"))
    private User ownerUser;

    @Column(nullable = false)
    @NonNull
    private String title;

    @Column(nullable = false)
    @NonNull
    private String content;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime dateTime;
}