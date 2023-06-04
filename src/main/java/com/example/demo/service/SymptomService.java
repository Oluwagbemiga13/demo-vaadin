package com.example.demo.service;

import com.example.demo.dto.OrganDTO;
import com.example.demo.dto.PartDTO;
import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.OrganSymptom;
import com.example.demo.entity.Part;
import com.example.demo.entity.Symptom;
import com.example.demo.mapper.SymptomMapper;
import com.example.demo.repository.OrganSymptomRepository;
import com.example.demo.repository.SymptomRepository;
import com.vaadin.flow.data.binder.Binder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
//@Transactional
public class SymptomService implements EntityService<Symptom, SymptomDTO> {

    Binder<SymptomDTO> binder = new Binder<>(SymptomDTO.class);

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
    public Binder<SymptomDTO> getBinder() {
        return this.binder;
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

    @Override
    public String getEntityName() {
        return "Symptom";
    }

    @Override
    public Symptom findById(Long id) {
        Optional<Symptom> optional = symptomRepository.findById(id);
        if(optional.isEmpty()){
            throw  new IllegalArgumentException("ID :" + id + " was not found.");
        }
        return optional.get();
    }

    List<SymptomDTO> findSymptomsNotMappedToOrgan(OrganDTO organDTO){
        return  symptomMapper.toDto(symptomRepository.findSymptomsNotMappedToOrgan(organDTO.getId()));
    }

    List<SymptomDTO> findSymptomsNotMappedToPart(PartDTO partDTO){
        return  symptomMapper.toDto(symptomRepository.findSymptomsNotMappedToPart(partDTO.getId()));
    }


}