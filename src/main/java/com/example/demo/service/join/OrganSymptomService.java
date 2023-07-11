package com.example.demo.service.join;

import com.example.demo.dto.simple.OrganDTO;
import com.example.demo.dto.simple.SymptomDTO;
import com.example.demo.entity.simple.Organ;
import com.example.demo.entity.join.OrganSymptom;
import com.example.demo.entity.simple.Symptom;
import com.example.demo.mapper.GenericMapper;
import com.example.demo.mapper.OrganMapper;
import com.example.demo.mapper.SymptomMapper;
import com.example.demo.repository.simple.OrganRepository;
import com.example.demo.repository.join.OrganSymptomRepository;
import com.example.demo.repository.simple.SymptomRepository;
import com.example.demo.service.simple.EntityService;
import com.example.demo.service.simple.OrganService;
import com.example.demo.service.simple.SymptomService;
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


    @Override
    @Transactional
    public OrganSymptom createRelation(Long organId, Long symptomId) {
        Organ organ = organService.findById(organId);
        Symptom symptom = symptomService.findById(symptomId);

        OrganSymptom organSymptom = new OrganSymptom();
        organSymptom.setOrgan(organ);
        organSymptom.setSymptom(symptom);

        log.info(organMapper.toDto(organ).getName());

        return organSymptomRepository.save(organSymptom);
    }


    @Override
    public List<SymptomDTO> findSecondsByFirstId(Long organId) {
        return symptomMapper.toDto(organSymptomRepository.findAllByOrganId(organId).stream()
                .map(OrganSymptom::getSymptom)
                .toList());
    }

    @Override
    public List<OrganDTO> findFirstsBySecondId(Long symptomId) {
        return organMapper.toDto(organSymptomRepository.findAllBySymptomId(symptomId).stream()
                .map(OrganSymptom::getOrgan)
                .toList());
    }

    @Override
    public List<SymptomDTO> findSecondNotMappedToFirst(OrganDTO firstDTO) {
        return symptomService.findSymptomsNotMappedToOrgan(firstDTO);
    }

    @Override
    public List<OrganDTO> findFirstNotMappedToSecond(SymptomDTO secondDTO) {
        return organService.findOrgansNotMappedToSymptom(secondDTO);
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
