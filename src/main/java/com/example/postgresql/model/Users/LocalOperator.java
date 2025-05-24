package com.example.postgresql.model.Users;

import com.example.postgresql.model.Education.EducationInfo.EducationalInstitution;
import com.example.postgresql.model.Users.User.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "ELD_LOCAL_OPERATOR")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class LocalOperator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "lo_u_id", foreignKey = @ForeignKey(name = "t_u_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "lo_ei_id", foreignKey = @ForeignKey(name = "t_ei_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EducationalInstitution educationalInstitution;

    private String email;
}
