package com.example.demo.repository;

import com.example.demo.entity.OrganSymptom;
import com.example.demo.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrganSymptomRepository extends JpaRepository<OrganSymptom, Long> {
    List<OrganSymptom> findAllByOrganId(Long organId);
    List<OrganSymptom> findAllBySymptomId(Long symptomId);
    Optional<OrganSymptom> findByOrganIdAndSymptomId(Long organId, Long symptomId);

    @Query("SELECT s FROM Symptom s WHERE s.id NOT IN (SELECT os.symptom.id FROM OrganSymptom os)")
    List<Symptom> findSymptomsNotMappedToAnyOrgan();
}
