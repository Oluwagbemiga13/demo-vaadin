package com.example.demo.service;

import com.example.demo.dto.OrganDTO;
import com.example.demo.entity.Organ;
import com.example.demo.repository.OrganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrganService {

    @Autowired
    private OrganRepository organRepository;

    public void saveOrgan(OrganDTO organDTO) {
        Organ organ = new Organ();
        organ.setName(organDTO.name());
        organRepository.save(organ);
    }
}
