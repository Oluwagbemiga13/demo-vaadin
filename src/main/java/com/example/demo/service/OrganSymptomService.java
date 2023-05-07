package com.example.demo.service;

import com.example.demo.dto.OrganDTO;
import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.Organ;
import com.example.demo.entity.OrganSymptom;
import com.example.demo.entity.Symptom;
import com.example.demo.mapper.GenericMapper;
import com.example.demo.mapper.OrganMapper;
import com.example.demo.mapper.SymptomMapper;
import com.example.demo.repository.OrganRepository;
import com.example.demo.repository.OrganSymptomRepository;
import com.example.demo.repository.SymptomRepository;
import jakarta.el.PropertyNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganSymptomService implements JoinService<OrganSymptom, OrganDTO, SymptomDTO> {

    @Autowired
    private OrganSymptomRepository organSymptomRepository;

    @Autowired
    private OrganRepository organRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    private final SymptomService symptomService;

    private final OrganService organService;

    @Autowired
    private OrganMapper organMapper;

    @Override
    public EntityService getFirstService() {
        return organService;
    }

    @Override
    public EntityService getSecondService() {
        return symptomService;
    }

    @Autowired
    private SymptomMapper symptomMapper;

    @Transactional
    @Override
    public OrganSymptom createRelation(Long organId, Long symptomId) {
        Organ organ = organService.findById(organId);
        Symptom symptom = symptomRepository.findById(symptomId).orElseThrow(() -> new EntityNotFoundException("Symptom not found with ID: " + symptomId));

        OrganSymptom organSymptom = new OrganSymptom();
        organSymptom.setOrgan(organ);
        organSymptom.setSymptom(symptom);

        log.info(organMapper.toDto(organ).getName());

        return organSymptomRepository.save(organSymptom);
    }

    @Transactional
    @Override
    public void removeRelation(Long organId, Long symptomId) throws PropertyNotFoundException {
        OrganSymptom organSymptom = organSymptomRepository.findByOrganIdAndSymptomId(organId, symptomId)
                .orElseThrow(() -> new PropertyNotFoundException("OrganSymptom relationship not found for Organ ID: " + organId + " and Symptom ID: " + symptomId));
        organSymptomRepository.delete(organSymptom);
    }

    @Override
    public List<SymptomDTO> findSecondsByFirstId(Long organId) {
        return symptomMapper.toDto(organSymptomRepository.findAllByOrganId(organId).stream()
                .map(OrganSymptom::getSymptom)
                .toList());
    }

    @Override
    public List<OrganDTO> findFirstsBySecondId(Long secondId) {
        return organMapper.toDto(organSymptomRepository.findAllBySymptomId(secondId).stream()
                .map(OrganSymptom::getOrgan)
                .toList());
    }

    @Override
    public List<SymptomDTO> findSecondNotMappedToFirst(Long longId) {
        return symptomMapper.toDto(symptomRepository.findSymptomsNotMappedToOrgan(longId));
    }

    @Override
    public List<OrganDTO> findOFirstNotMappedToSecond(Long id) {
        return organMapper.toDto(organRepository.findOrgansNotMappedToSymptom(id));
    }


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

    @Override
    public GenericMapper getSecondMapper() {
        return symptomMapper;
    }

    @Override
    public GenericMapper getFirstMapper() {
        return organMapper;
    }
}
