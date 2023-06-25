package com.example.demo.repository.simple;

import com.example.demo.entity.simple.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
