package com.example.demo.service;

import com.example.demo.dto.OrganDTO;
import com.example.demo.dto.PartDTO;
import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.Part;
import com.example.demo.entity.Symptom;
import com.example.demo.entity.SymptomPart;
import com.example.demo.mapper.PartMapper;
import com.example.demo.mapper.SymptomMapper;
import com.example.demo.repository.PartRepository;
import com.example.demo.repository.SymptomPartRepository;
import com.example.demo.repository.SymptomRepository;
import jakarta.el.PropertyNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartService {

    private final PartRepository partRepository;

    private final SymptomPartRepository symptomPartRepository;

    private final SymptomRepository symptomRepository;

    private final PartMapper partMapper;

    private final SymptomMapper symptomMapper;

    public void savePart(PartDTO partDTO) {
        log.info("{} was accepted", partDTO);
        Part part = new Part();
        part.setName(partDTO.getName());
        partRepository.save(part);
        log.info("{} was saved", part.getName());

    }

    @Transactional
    public SymptomPart createRelation(Long partId, Long symptomId) {
        Part part = partRepository.findById(partId).orElseThrow(() -> new EntityNotFoundException("Part not found with ID: " + partId));
        Symptom symptom = symptomRepository.findById(symptomId).orElseThrow(() -> new EntityNotFoundException("Symptom not found with ID: " + symptomId));

        SymptomPart symptomPart = new SymptomPart();
        symptomPart.setPart(part);
        symptomPart.setSymptom(symptom);

        log.info("Relation between {} and {} was created.", part.getName(), symptom.getName());

        return symptomPartRepository.save(symptomPart);
    }


    public List<PartDTO> findPartsNotMappedToSymptom(SymptomDTO symptom) {
        return partMapper.toDto(partRepository.findPartsNotMappedToSymptom(symptom.getId()));
    }

    public List<PartDTO> findAllPartsBySymptomId(Long symptomId) {
        return partMapper.toDto(partRepository.findPartsMappedToSymptom(symptomId));
    }


    public List<PartDTO> findAll() {
        return partMapper.toDto(partRepository.findAll());
    }

    public void delete(Long id) {
        partRepository.deleteById(id);
    }

    @Transactional
    public void deleteRelation(PartDTO partDTO, SymptomDTO symptomDTO) {
        Optional<SymptomPart> symptomPartOptional = symptomPartRepository.findBySymptomIdAndPartId(symptomDTO.getId(), partDTO.getId());

        if (symptomPartOptional.isPresent()) {
            SymptomPart symptomPart = symptomPartOptional.get();

            // Remove the relationship from the parent entities
            Part part = symptomPart.getPart();
            Symptom symptom = symptomPart.getSymptom();
            part.getSymptoms().remove(symptomPart);
            symptom.getParts().remove(symptomPart);

            // Update the parent entities
            partRepository.save(part);
            symptomRepository.save(symptom);

            // Delete the SymptomPart entity
            symptomPartRepository.delete(symptomPart);
            log.info("Relation between {} and {} was removed.", symptom.getName(), part.getName());
        }
    }

    @Transactional
    public void deleteRelation(PartDTO partDTO, OrganDTO organDTO) {
        Optional<SymptomPart> symptomPartOptional = symptomPartRepository.findBySymptomIdAndPartId(organDTO.getId(), partDTO.getId());

        if (symptomPartOptional.isPresent()) {
            SymptomPart symptomPart = symptomPartOptional.get();

            // Remove the relationship from the parent entities
            Part part = symptomPart.getPart();
            Symptom symptom = symptomPart.getSymptom();
            part.getSymptoms().remove(symptomPart);
            symptom.getParts().remove(symptomPart);

            // Update the parent entities
            partRepository.save(part);
            symptomRepository.save(symptom);

            // Delete the SymptomPart entity
            symptomPartRepository.delete(symptomPart);
            log.info("Relation between {} and {} was removed.", symptom.getName(), part.getName());
        }
    }
}

