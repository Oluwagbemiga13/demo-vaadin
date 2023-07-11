package com.example.demo.service.join;

import com.example.demo.dto.simple.PartDTO;
import com.example.demo.dto.simple.SymptomDTO;
import com.example.demo.entity.join.SymptomPart;
import com.example.demo.entity.simple.Part;
import com.example.demo.entity.simple.Symptom;
import com.example.demo.mapper.GenericMapper;
import com.example.demo.mapper.PartMapper;
import com.example.demo.mapper.SymptomMapper;
import com.example.demo.repository.join.SymptomPartRepository;
import com.example.demo.repository.simple.PartRepository;
import com.example.demo.repository.simple.SymptomRepository;
import com.example.demo.service.simple.EntityService;
import com.example.demo.service.simple.PartService;
import com.example.demo.service.simple.SymptomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SymptomPartService implements JoinService<SymptomPart, SymptomDTO, PartDTO> {

    private final SymptomPartRepository symptomPartRepository;

    private final SymptomService symptomService;

    private final PartService partService;

    private final SymptomMapper symptomMapper;

    private final PartMapper partMapper;

    private final SymptomRepository symptomRepository;

    private final PartRepository partRepository;

    @Override
    public SymptomPart createRelation(Long firstId, Long secondId) {
        Part part = partService.findById(secondId);
        Symptom symptom = symptomService.findById(firstId);

        SymptomPart symptomPart = new SymptomPart();
        symptomPart.setPart(part);
        symptomPart.setSymptom(symptom);

        log.info("Relation between {} and {} was created.", part.getName(), symptom.getName());

        return symptomPartRepository.save(symptomPart);
    }


    @Override
    public List<PartDTO> findSecondsByFirstId(Long firstId) {

        return partMapper.toDto(symptomPartRepository.findAllBySymptomId(firstId).stream()
                .map(SymptomPart::getPart)
                .toList());
    }

    @Override
    public List<SymptomDTO> findFirstsBySecondId(Long secondId) {

        return symptomMapper.toDto(symptomPartRepository.findAllByPartId(secondId).stream()
                .map(SymptomPart::getSymptom)
                .toList());
    }

    @Override
    public List<PartDTO> findSecondNotMappedToFirst(SymptomDTO longId) {
        return partService.findPartsNotMappedToSymptom(longId);
    }

    @Override
    public List<SymptomDTO> findFirstNotMappedToSecond(PartDTO secondDTO) {
        return symptomService.findSymptomsNotMappedToPart(secondDTO);
    }


    @Override
    public void deleteRelation(SymptomDTO firstDTO, PartDTO secondDTO) {
        Optional<SymptomPart> symptomPartOptional = symptomPartRepository.findBySymptomIdAndPartId(firstDTO.getId(), secondDTO.getId());

        if (symptomPartOptional.isPresent()) {
            SymptomPart symptomPart = symptomPartOptional.get();

            // Remove the relationship from the parent entities
            Part part = symptomPart.getPart();
            Symptom symptom = symptomPart.getSymptom();
            part.getSymptoms().remove(symptomPart);
            symptom.getParts().remove(symptomPart);

            // Update the parent entities
            // TODO: Create method update in EntityService. This method will hande updating stuff.
            //  That improves SOLID principles
            partRepository.save(part);
            symptomRepository.save(symptom);

            // Delete the SymptomPart entity
            symptomPartRepository.delete(symptomPart);
            log.info("Relation between {} and {} was removed.", symptom.getName(), part.getName());
        }

    }

    @Override
    public GenericMapper getFirstMapper() {

        return symptomMapper;
    }

    @Override
    public GenericMapper getSecondMapper() {

        return partMapper;
    }

    @Override
    public EntityService getFirstService() {
        return symptomService;
    }

    @Override
    public EntityService getSecondService() {
        return partService;
    }
}
