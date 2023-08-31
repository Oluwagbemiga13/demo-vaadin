package com.example.demo.repository.alert;

import com.example.demo.entity.alerts.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByName(String name);

    @Query("SELECT q FROM Question q JOIN FETCH q.possibleAnswers")
    List<Question> findAllWithEagerRelationships();
}
