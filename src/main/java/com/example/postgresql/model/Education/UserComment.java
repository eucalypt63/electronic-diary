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
@Table(name = "ELD_USER_COMMENTS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UserComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime dateTime;

    @Column(nullable = false)
    @NonNull
    private String content;

    @ManyToOne
    @JoinColumn(name = "uc_u_getter_id", foreignKey = @ForeignKey(name = "uc_u_getter_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User getterUser;

    @ManyToOne
    @JoinColumn(name = "uc_u_sender_id", foreignKey = @ForeignKey(name = "uc_u_sender_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User senderUser;
}