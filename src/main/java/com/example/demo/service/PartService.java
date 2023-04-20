package com.example.demo.service;

import com.example.demo.dto.PartDTO;
import com.example.demo.entity.Part;
import com.example.demo.mapper.PartMapper;
import com.example.demo.repository.PartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PartService {

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private PartMapper partMapper;

    public void savePart(PartDTO partDTO) {
        log.info("{} was accepted", partDTO);
        Part part = new Part();
        part.setName(partDTO.getName());
        partRepository.save(part);
        Optional<Part> part1 = partRepository.findById(part.getId());
        if(part1.isEmpty()){
            log.error("Part ID: {} was not found", part.getId());
        }
        else {
            log.info("{} was retrieved", part1);
        }
    }

    public List<PartDTO> findAll() {
        return partMapper.toDto(partRepository.findAll());
    }

    public void delete(Long id) {
        partRepository.deleteById(id);
    }
}

