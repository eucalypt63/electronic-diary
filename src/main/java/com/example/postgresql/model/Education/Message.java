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
@Table(name = "ELD_MESSAGES")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "m_u_getter_id", foreignKey = @ForeignKey(name = "m_u_getter_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User getterUser;

    @ManyToOne
    @JoinColumn(name = "m_u_sender_id", foreignKey = @ForeignKey(name = "m_u_sender_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User senderUser;

    @Column(nullable = false, length = 1000)
    @NonNull
    private String message;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime dateTime;
}