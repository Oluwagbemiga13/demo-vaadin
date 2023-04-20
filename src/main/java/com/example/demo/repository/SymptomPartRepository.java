package com.example.demo.repository;

import com.example.demo.entity.SymptomPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SymptomPartRepository extends JpaRepository<SymptomPart, Long> {

    List<SymptomPart> findAllByPartId(Long partId);

    List<SymptomPart> findAllBySymptomId(Long symptomId);

    Optional<SymptomPart> findBySymptomIdAndPartId(Long symptomId, Long partId);

}

