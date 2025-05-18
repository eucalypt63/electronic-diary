package com.example.postgresql.model.Education.News;

import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
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
@Table(name = "ELD_NEWS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "n_ei_id", foreignKey = @ForeignKey(name = "n_ei_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EducationalInstitution educationalInstitution;

    @ManyToOne
    @JoinColumn(name = "n_u_owner_id", foreignKey = @ForeignKey(name = "n_u_owner_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User ownerUser;

    @Column(nullable = false)
    @NonNull
    private String title;

    @Column(nullable = false, length = 4000)
    @NonNull
    private String content;

    @Column(nullable = false)
    @NonNull
    private LocalDateTime dateTime;
}