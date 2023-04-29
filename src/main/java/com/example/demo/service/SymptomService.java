package com.example.demo.service;

import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.OrganSymptom;
import com.example.demo.entity.Symptom;
import com.example.demo.mapper.SymptomMapper;
import com.example.demo.repository.OrganSymptomRepository;
import com.example.demo.repository.SymptomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
//@Transactional
public class SymptomService implements EntityService<Symptom, SymptomDTO> {

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private OrganSymptomRepository organSymptomRepository;

    @Autowired
    private SymptomMapper symptomMapper;


    @Override
    public Class<SymptomDTO> getDTOClass() {
        return SymptomDTO.class;
    }

    @Override
    public Class<Symptom> getEntityClass() {
        return Symptom.class;
    }

    @Override
    public void save(SymptomDTO symptomDTO) {
        Symptom symptom = new Symptom();
        symptom.setName(symptomDTO.getName());
        log.info("Symptom {} was saved.", symptomDTO.getName());
        symptomRepository.save(symptom);
    }

    @Override
    public List<SymptomDTO> findAll() {
        return symptomMapper.toDto(symptomRepository.findAll());
    }


    @Transactional
    public void delete(Long id) {
        List<OrganSymptom> organSymptoms = organSymptomRepository.findAllBySymptomId(id);
        if (organSymptoms.isEmpty()) {
            symptomRepository.deleteById(id);
        } else {
            //deleteAllIntermediate(organSymptoms);
            //organSymptoms.forEach(organSymptom -> organSymptomRepository.delete(organSymptom));
            symptomRepository.deleteById(id);
        }
    }


    public void deleteAll(List<SymptomDTO> symptomsDTOs) {
        List<Symptom> symptoms = symptomsDTOs.stream()
                .map(d -> symptomMapper.toEntity(d))
                .toList();

        symptomRepository.deleteAll(symptoms);

    }


}