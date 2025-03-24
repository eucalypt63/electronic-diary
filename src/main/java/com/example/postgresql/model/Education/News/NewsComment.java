package com.example.postgresql.model.Education.News;

import com.example.postgresql.model.Users.User.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ELD_NEW_COMMENTS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class NewsComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nc_u_getter_id", nullable = false, foreignKey = @ForeignKey(name = "nc_u_getter_id"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "nc_n_getter_id", nullable = false, foreignKey = @ForeignKey(name = "nc_n_getter_id"))
    private News news;

    @Column(nullable = false)
    @NonNull
    private String content;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime dateTime;
}
