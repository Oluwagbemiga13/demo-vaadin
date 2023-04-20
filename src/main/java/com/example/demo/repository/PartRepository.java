package com.example.demo.repository;

import com.example.demo.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {

//    @Query("SELECT b FROM Part b WHERE b.id NOT IN (SELECT o.bodyPart.id FROM Organ o WHERE o.symptoms.id = :symptomId)")
//    List<Part> findBodyPartsNotMappedToSymptom(@Param("symptomId") Long symptomId);

    @Query("SELECT p FROM Part p WHERE p.id NOT IN (SELECT ps.part.id FROM SymptomPart ps WHERE ps.symptom.id = :symptomId)")
    List<Part> findPartsNotMappedToSymptom(@Param("symptomId") Long symptomId);


    @Query("SELECT ps.part FROM SymptomPart ps WHERE ps.symptom.id = :symptomId")
    List<Part> findPartsMappedToSymptom(@Param("symptomId") Long symptomId);

}


