package com.example.demo.service;

import com.example.demo.dto.OrganDTO;
import com.example.demo.dto.PartDTO;
import com.example.demo.entity.*;
import com.example.demo.mapper.GenericMapper;
import com.example.demo.mapper.OrganMapper;
import com.example.demo.mapper.PartMapper;
import com.example.demo.repository.*;
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
public class PartOrganService implements JoinService<PartOrgan, PartDTO, OrganDTO> {

    @Autowired
    private PartOrganRepository partOrganRepository;

    @Autowired
    private OrganRepository organRepository;

    @Autowired
    private PartRepository partRepository;

    private final PartService partService;

    private final OrganService organService;

    @Autowired
    private PartMapper partMapper;

    @Autowired
    private OrganMapper organMapper;


    @Override
    @Transactional
    public PartOrgan createRelation(Long partId, Long organId) {
        Organ organ = organService.findById(organId);
        Part part = partService.findById(partId);

        PartOrgan partOrgan = new PartOrgan();
        partOrgan.setOrgan(organ);
        partOrgan.setPart(part);

        partOrganRepository.save(partOrgan);

        log.info(partOrgan + " was saved.");
        return partOrgan;
    }

    @Override
    public List<OrganDTO> findSecondsByFirstId(Long partId) {
        return organMapper.toDto(partOrganRepository.findAllByPartId(partId).stream()
                .map(PartOrgan::getOrgan)
                .toList());
    }

    @Override
    public List<PartDTO> findFirstsBySecondId(Long organId) {
        return partMapper.toDto(partOrganRepository.findAllByOrganId(organId).stream()
                .map(PartOrgan::getPart)
                .toList());
    }

    @Override
    public List<OrganDTO> findSecondNotMappedToFirst(PartDTO partDTO) {
        return organService.findOrgansNotMappedToPart(partDTO);
    }

    @Override
    public List<PartDTO> findFirstNotMappedToSecond(OrganDTO organDTO) {
        return partService.findPartsNotMappedToOrgan(organDTO);
    }

    @Override
    public void deleteRelation(PartDTO partDTO, OrganDTO organDTO) {
        Optional<PartOrgan> partOrganOptional = partOrganRepository.findByPartIdAndOrganId(partDTO.getId(),organDTO.getId());
        //partOrganRepository.delete(partOrgan);

        Optional<PartOrgan> organSymptomOptional = partOrganRepository.findByPartIdAndOrganId(partDTO.getId(), organDTO.getId());

        if (partOrganOptional.isPresent()) {
            PartOrgan partOrgan = organSymptomOptional.get();

            // Remove the relationship from the parent entities
            Organ organ = partOrgan.getOrgan();
            Part part = partOrgan.getPart();
            organ.getParts().remove(partOrgan);
            part.getOrgans().remove(partOrgan);

            // Update the parent entities
            organRepository.save(organ);
            partRepository.save(part);

            // Delete the OrganSymptom entity
            partOrganRepository.delete(partOrgan);
        }
    }

    @Override
    public GenericMapper getFirstMapper() {
        return partMapper;
    }

    @Override
    public GenericMapper getSecondMapper() {
        return organMapper;
    }

    @Override
    public EntityService getFirstService() {
        return partService;
    }

    @Override
    public EntityService getSecondService() {
        return organService;
    }
}
