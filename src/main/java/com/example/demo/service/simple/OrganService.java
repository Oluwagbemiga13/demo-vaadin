package com.example.demo.service.simple;

import com.example.demo.dto.simple.OrganDTO;
import com.example.demo.dto.simple.PartDTO;
import com.example.demo.dto.simple.SymptomDTO;
import com.example.demo.entity.simple.Organ;
import com.example.demo.mapper.OrganMapper;
import com.example.demo.repository.simple.OrganRepository;
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
    public OrganDTO save(OrganDTO organDTO) {
        log.info("{} was accepted", organDTO);
        Organ organ = new Organ();
        organ.setName(organDTO.getName());

        return organMapper.toDto(organRepository.save(organ));
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
    public Organ findById(Long id) {
        Optional<Organ> optional = organRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else throw new IllegalArgumentException("Organ with " + id + "was not found.");
    }

    public List<OrganDTO> findOrgansNotMappedToSymptom(SymptomDTO symptomDTO) {
        return organMapper.toDto(organRepository.findOrgansNotMappedToSymptom(symptomDTO.getId()));
    }

    public List<OrganDTO> findOrgansNotMappedToPart(PartDTO partDTO) {
        return organMapper.toDto(organRepository.findOrgansNotMappedToPart(partDTO.getId()));
    }
}
