package com.example.demo.repository;

import com.example.demo.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {

    @Query("SELECT s FROM Symptom s WHERE s.id NOT IN (SELECT os.symptom.id FROM OrganSymptom os)")
    List<Symptom> findSymptomsNotMappedToAnyOrgan();

    @Query("SELECT s FROM Symptom s WHERE s.id NOT IN (SELECT os.symptom.id FROM OrganSymptom os WHERE os.organ.id = :organId)")
    List<Symptom> findSymptomsNotMappedToOrgan(@Param("organId") Long organId);
}
