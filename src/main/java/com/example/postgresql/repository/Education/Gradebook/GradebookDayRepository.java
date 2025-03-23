package com.example.postgresql.repository.Education.Gradebook;

import com.example.postgresql.model.Education.Gradebook.GradebookDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradebookDayRepository extends JpaRepository<GradebookDay, Long> {
}