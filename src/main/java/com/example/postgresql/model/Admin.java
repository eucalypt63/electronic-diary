package com.example.postgresql.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ED_admin") // Измените имя таблицы, если необходимо
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String login;

    @NonNull
    private String password;

    @NonNull
    private String role;
}