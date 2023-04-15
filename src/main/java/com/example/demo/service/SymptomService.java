package com.example.demo.service;

import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.Symptom;
import com.example.demo.repository.SymptomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SymptomService {

    private final SymptomRepository symptomRepository;

    public SymptomService(SymptomRepository symptomRepository) {
        this.symptomRepository = symptomRepository;
    }

    public SymptomDTO saveSymptom(SymptomDTO symptomDTO) {
        Symptom symptom = new Symptom();
        symptom.setName(symptomDTO.name());
        Symptom savedSymptom = symptomRepository.save(symptom);
        return SymptomDTO.withId(symptomDTO, savedSymptom.getId());
    }

    public List<Symptom> findAll(){
        return symptomRepository.findAll();
    }


    public void delete(Long id) {
        symptomRepository.deleteById(id);
    }
}
