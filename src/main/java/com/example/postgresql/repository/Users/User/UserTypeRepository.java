package com.example.postgresql.repository.Users.User;

import com.example.postgresql.model.Users.User.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
}