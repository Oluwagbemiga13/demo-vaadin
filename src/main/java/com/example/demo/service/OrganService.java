package com.example.demo.service;

import com.example.demo.dto.OrganDTO;
import com.example.demo.entity.Organ;
import com.example.demo.repository.OrganRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class OrganService {

    @Autowired
    private OrganRepository organRepository;

    public void saveOrgan(OrganDTO organDTO) {
        log.info("{} was accepted", organDTO);
        Organ organ = new Organ();
        organ.setName(organDTO.getName());
        organRepository.save(organ);
       Optional<Organ> organ1 = organRepository.findById(organ.getId());
       if(organ1.isEmpty()){
           log.error("Organ ID: {} was not found", organ.getId());
       }
       else {
           log.info("{} was retrieved", organ1);
       }
    }

    public List<Organ> findAll() {
        return organRepository.findAll();
    }

    public void delete(Long id) {
        organRepository.deleteById(id);
    }
}
