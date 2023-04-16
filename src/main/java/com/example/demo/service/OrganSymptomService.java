package com.example.demo.service;

import com.example.demo.entity.Organ;
import com.example.demo.entity.OrganSymptom;
import com.example.demo.entity.Symptom;
import com.example.demo.repository.OrganRepository;
import com.example.demo.repository.OrganSymptomRepository;
import com.example.demo.repository.SymptomRepository;
import jakarta.el.PropertyNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganSymptomService {

    @Autowired
    private OrganSymptomRepository organSymptomRepository;

    @Autowired
    private OrganRepository organRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    @Transactional
    public OrganSymptom createRelation(Long organId, Long symptomId) {
        Organ organ = organRepository.findById(organId).orElseThrow(() -> new EntityNotFoundException("Organ not found with ID: " + organId));
        Symptom symptom = symptomRepository.findById(symptomId).orElseThrow(() -> new EntityNotFoundException("Symptom not found with ID: " + symptomId));

        OrganSymptom organSymptom = new OrganSymptom();
        organSymptom.setOrgan(organ);
        organSymptom.setSymptom(symptom);

        return organSymptomRepository.save(organSymptom);
    }

    @Transactional
    public void removeRelation(Long organId, Long symptomId) throws PropertyNotFoundException {
        OrganSymptom organSymptom = organSymptomRepository.findByOrganIdAndSymptomId(organId, symptomId)
                .orElseThrow(() -> new PropertyNotFoundException("OrganSymptom relationship not found for Organ ID: " + organId + " and Symptom ID: " + symptomId));
        organSymptomRepository.delete(organSymptom);
    }

    public List<Symptom> findSymptomsByOrganId(Long organId) {
        return organSymptomRepository.findAllByOrganId(organId).stream()
                .map(OrganSymptom::getSymptom)
                .collect(Collectors.toList());
    }

    public List<Organ> findOrgansBySymptomId(Long symptomId) {
        return organSymptomRepository.findAllBySymptomId(symptomId).stream()
                .map(OrganSymptom::getOrgan)
                .collect(Collectors.toList());
    }

    public List<Symptom> findSymptomsNotMappedToAnyOrgan() {
        return symptomRepository.findSymptomsNotMappedToAnyOrgan();
    }

    public List<Symptom> findSymptomsNotMappedToOrgan(Organ organ){
        return symptomRepository.findSymptomsNotMappedToOrgan(organ.getId());
    }

}
