package com.example.demo.service;

import com.example.demo.dto.OrganDTO;
import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.Organ;
import com.example.demo.entity.OrganSymptom;
import com.example.demo.entity.Symptom;
import com.example.demo.mapper.OrganMapper;
import com.example.demo.mapper.SymptomMapper;
import com.example.demo.repository.OrganRepository;
import com.example.demo.repository.OrganSymptomRepository;
import com.example.demo.repository.SymptomRepository;
import jakarta.el.PropertyNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrganSymptomService {

    @Autowired
    private OrganSymptomRepository organSymptomRepository;

    @Autowired
    private OrganRepository organRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private OrganMapper organMapper;

    @Autowired
    private SymptomMapper symptomMapper;

    @Transactional
    public OrganSymptom createRelation(Long organId, Long symptomId) {
        Organ organ = organRepository.findById(organId).orElseThrow(() -> new EntityNotFoundException("Organ not found with ID: " + organId));
        Symptom symptom = symptomRepository.findById(symptomId).orElseThrow(() -> new EntityNotFoundException("Symptom not found with ID: " + symptomId));

        OrganSymptom organSymptom = new OrganSymptom();
        organSymptom.setOrgan(organ);
        organSymptom.setSymptom(symptom);

        log.info(organMapper.toDto(organ).getName());

        return organSymptomRepository.save(organSymptom);
    }

    @Transactional
    public void removeRelation(Long organId, Long symptomId) throws PropertyNotFoundException {
        OrganSymptom organSymptom = organSymptomRepository.findByOrganIdAndSymptomId(organId, symptomId)
                .orElseThrow(() -> new PropertyNotFoundException("OrganSymptom relationship not found for Organ ID: " + organId + " and Symptom ID: " + symptomId));
        organSymptomRepository.delete(organSymptom);
    }

    public List<SymptomDTO> findSymptomsByOrganId(Long organId) {
        return symptomMapper.toDto(organSymptomRepository.findAllByOrganId(organId).stream()
                .map(OrganSymptom::getSymptom)
                .toList());
    }

    public List<OrganDTO> findOrgansBySymptomId(Long symptomId) {
        return organMapper.toDto(organSymptomRepository.findAllBySymptomId(symptomId).stream()
                .map(OrganSymptom::getOrgan)
                .toList());
    }

    public List<Symptom> findSymptomsNotMappedToAnyOrgan() {
        return symptomRepository.findSymptomsNotMappedToAnyOrgan();
    }

    public List<SymptomDTO> findSymptomsNotMappedToOrgan(OrganDTO organ) {
        return symptomMapper.toDto(symptomRepository.findSymptomsNotMappedToOrgan(organ.getId()));
    }

    //    public void deleteRelation(OrganDTO organ, SymptomDTO symptom) {
//        Optional<OrganSymptom> organSymptom = organSymptomRepository.findByOrganIdAndSymptomId(organ.getId(), symptom.getId());
//        organSymptom.ifPresent(value -> organSymptomRepository.delete(value));
//
////        Long idOrganSymptom = organSymptom.get().getId();
////        organSymptomRepository.deleteById(idOrganSymptom);
//    }
//

    @Transactional
    public void deleteRelation(OrganDTO organDTO, SymptomDTO symptomDTO) {
        Optional<OrganSymptom> organSymptomOptional = organSymptomRepository.findByOrganIdAndSymptomId(organDTO.getId(), symptomDTO.getId());

        if (organSymptomOptional.isPresent()) {
            OrganSymptom organSymptom = organSymptomOptional.get();

            // Remove the relationship from the parent entities
            Organ organ = organSymptom.getOrgan();
            Symptom symptom = organSymptom.getSymptom();
            organ.getSymptoms().remove(organSymptom);
            symptom.getOrgans().remove(organSymptom);

            // Update the parent entities
            organRepository.save(organ);
            symptomRepository.save(symptom);

            // Delete the OrganSymptom entity
            organSymptomRepository.delete(organSymptom);
        }
    }
}
