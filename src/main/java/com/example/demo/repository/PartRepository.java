package com.example.demo.repository;

import com.example.demo.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {

//    @Query("SELECT b FROM Part b WHERE b.id NOT IN (SELECT o.bodyPart.id FROM Organ o WHERE o.symptoms.id = :symptomId)")
//    List<Part> findBodyPartsNotMappedToSymptom(@Param("symptomId") Long symptomId);

}

