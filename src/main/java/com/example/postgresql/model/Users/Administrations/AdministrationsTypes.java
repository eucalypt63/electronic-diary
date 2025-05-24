package com.example.postgresql.model.Users.Administrations;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ELD_ADMINISTRATIONS_TYPES")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class AdministrationsTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;
}
