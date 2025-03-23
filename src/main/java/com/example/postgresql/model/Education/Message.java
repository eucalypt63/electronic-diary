package com.example.postgresql.model.Education;

import com.example.postgresql.model.Users.User.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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

    @Column(nullable = false)
    @NonNull
    private LocalDateTime dateTime;

    @Column(nullable = false)
    @NonNull
    private String message;

    @ManyToOne
    @JoinColumn(name = "m_u_sender_id", nullable = false, foreignKey = @ForeignKey(name = "m_u_sender_id"))
    private User senderUser;

    @ManyToOne
    @JoinColumn(name = "m_u_getter_id", nullable = false, foreignKey = @ForeignKey(name = "m_u_getter_id"))
    private User getterUser;
}