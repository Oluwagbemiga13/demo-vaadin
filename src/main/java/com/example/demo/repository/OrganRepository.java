package com.example.demo.repository;

import com.example.demo.entity.Organ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OrganRepository extends JpaRepository<Organ, Long> {

    @Query("SELECT o FROM Organ o WHERE o.id NOT IN (SELECT os.organ.id FROM OrganSymptom os WHERE os.symptom.id = :symptomId)")
    List<Organ> findOrgansNotMappedToSymptom(@Param("symptomId") Long symptomId);


}
