package com.example.postgresql.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ED_ScAdmin")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ScAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String login;

    @NonNull
    private String password;

    @NonNull
    private String role;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonManagedReference
    private School school;
}
