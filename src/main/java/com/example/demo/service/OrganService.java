package com.example.demo.service;

import com.example.demo.dto.OrganDTO;
import com.example.demo.dto.PartDTO;
import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.Organ;
import com.example.demo.mapper.OrganMapper;
import com.example.demo.repository.OrganRepository;
import com.vaadin.flow.data.binder.Binder;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class OrganService implements EntityService<Organ, OrganDTO> {

    Binder<OrganDTO> binder = new Binder<>(OrganDTO.class);

    @Autowired
    private OrganRepository organRepository;

    @Autowired
    private OrganMapper organMapper;

    @Override
    public Class<OrganDTO> getDTOClass() {
        return OrganDTO.class;
    }

    @Override
    public Binder<OrganDTO> getBinder() {
        return this.binder;
    }

    @Override
    public Class<Organ> getEntityClass() {
        return Organ.class;
    }

    @Override
    public void save(OrganDTO organDTO) {
        log.info("{} was accepted", organDTO);
        Organ organ = new Organ();
        organ.setName(organDTO.getName());
        organRepository.save(organ);
        Optional<Organ> organ1 = organRepository.findById(organ.getId());
        if (organ1.isEmpty()) {
            log.error("Organ ID: {} was not found", organ.getId());
        } else {
            log.info("{} was retrieved", organ1);
        }
    }


    public List<OrganDTO> findAll() {
        return organMapper.toDto(organRepository.findAll());
    }

    public void delete(Long id) {
        organRepository.deleteById(id);
    }

    @Override
    public String getEntityName() {
        return "Organ";
    }

    @Override
    public void deleteAll(List dtos) {
        throw new NotImplementedException("NOT IMPLEMENTED");
    }

    @Override
    public Organ findById(Long id){
        Optional<Organ> optional = organRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        else throw new IllegalArgumentException("Organ with " + id + "was not found.");
    }

    List<OrganDTO> findOrgansNotMappedToSymptom(SymptomDTO symptomDTO){
        return organMapper.toDto(organRepository.findOrgansNotMappedToSymptom(symptomDTO.getId()));
    }

    List<OrganDTO> findOrgansNotMappedToPart(PartDTO partDTO){
        return organMapper.toDto(organRepository.findOrgansNotMappedToPart(partDTO.getId()));
    }
}
