package com.example.demo.service;

import com.example.demo.dto.PartDTO;
import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.OrganSymptom;
import com.example.demo.entity.Symptom;
import com.example.demo.mapper.SymptomMapper;
import com.example.demo.repository.OrganSymptomRepository;
import com.example.demo.repository.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional
public class SymptomService {

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private OrganSymptomRepository organSymptomRepository;

    @Autowired
    private SymptomMapper symptomMapper;

//    public SymptomService(SymptomRepository symptomRepository) {
//        this.symptomRepository = symptomRepository;
//    }

    public SymptomDTO saveSymptom(SymptomDTO symptomDTO) {
        Symptom symptom = new Symptom();
        symptom.setName(symptomDTO.getName());
        Symptom savedSymptom = symptomRepository.save(symptom);
        return new SymptomDTO(symptomDTO, savedSymptom.getId());
    }

    public List<SymptomDTO> findAll() {
        return symptomMapper.toDto(symptomRepository.findAll());
    }


    @Transactional
    public void delete(Long id) {
        List<OrganSymptom> organSymptoms = organSymptomRepository.findAllBySymptomId(id);
        if (organSymptoms.isEmpty()) {
            symptomRepository.deleteById(id);
        } else {
            deleteAll(organSymptoms);
            //organSymptoms.forEach(organSymptom -> organSymptomRepository.delete(organSymptom));
            symptomRepository.deleteById(id);
        }
    }

    public List<SymptomDTO> findSymptomsNotMappedToPart(PartDTO part) {
        return symptomMapper.toDto(symptomRepository.findSymptomsNotMappedToPart(part.getId()));
    }

    @Transactional
    public void deleteAll(List<OrganSymptom> organSymptoms) {
        organSymptomRepository.deleteAll(organSymptoms);
    }
}
