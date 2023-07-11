package com.example.demo.repository.simple;

import com.example.demo.entity.simple.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findByName(String name);
}
