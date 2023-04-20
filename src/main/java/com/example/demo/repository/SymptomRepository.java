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

    @Query("SELECT s FROM Symptom s WHERE s.id NOT IN (SELECT ps.symptom.id FROM SymptomPart ps WHERE ps.part.id = :partId)")
    List<Symptom> findSymptomsNotMappedToPart(@Param("partId") Long partId);



}
