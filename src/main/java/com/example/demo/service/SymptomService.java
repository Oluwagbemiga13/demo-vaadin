package com.example.demo.service;

import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.Symptom;
import com.example.demo.mapper.SymptomMapper;
import com.example.demo.repository.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Transactional
public class SymptomService {

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private SymptomMapper symptomMapper;

//    public SymptomService(SymptomRepository symptomRepository) {
//        this.symptomRepository = symptomRepository;
//    }

    public SymptomDTO saveSymptom(SymptomDTO symptomDTO) {
        Symptom symptom = new Symptom();
        symptom.setName(symptomDTO.getName());
        Symptom savedSymptom = symptomRepository.save(symptom);
        return new SymptomDTO( symptomDTO, savedSymptom.getId());
    }

    public List<SymptomDTO> findAll(){
        return symptomMapper.toDto(symptomRepository.findAll());
    }


    public void delete(Long id) {
        symptomRepository.deleteById(id);
    }
}
