package com.example.demo.repository.simple;

import com.example.demo.entity.simple.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
