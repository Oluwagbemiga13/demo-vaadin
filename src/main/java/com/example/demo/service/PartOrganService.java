package com.example.demo.service;

import com.example.demo.dto.OrganDTO;
import com.example.demo.dto.PartDTO;
import com.example.demo.entity.PartOrgan;
import com.example.demo.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartOrganService implements JoinService<PartOrgan, PartDTO, OrganDTO> {


    @Override
    public PartOrgan createRelation(Long firstId, Long secondId) {
        return null;
    }

    @Override
    public List<OrganDTO> findSecondsByFirstId(Long firstId) {
        return null;
    }

    @Override
    public List<PartDTO> findFirstsBySecondId(Long secondId) {
        return null;
    }

    @Override
    public List<OrganDTO> findSecondNotMappedToFirst(PartDTO firstDTO) {
        return null;
    }

    @Override
    public List<PartDTO> findFirstNotMappedToSecond(OrganDTO secondDTO) {
        return null;
    }

    @Override
    public void deleteRelation(PartDTO firstDTO, OrganDTO secondDTO) {

    }

    @Override
    public GenericMapper getFirstMapper() {
        return null;
    }

    @Override
    public GenericMapper getSecondMapper() {
        return null;
    }

    @Override
    public EntityService getFirstService() {
        return null;
    }

    @Override
    public EntityService getSecondService() {
        return null;
    }
}
