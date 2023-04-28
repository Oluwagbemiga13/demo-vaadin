package com.example.demo.service;

import com.example.demo.dto.OrganDTO;
import com.example.demo.dto.SymptomDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Here I fully dedicated to rewrite this whole project, using SOLID.
 * @param <D> DTO representation of Intermediate Entity
 * @param <F> First Entitiy from name of service. e.g. OrganSymptomService -> Organ
 * @param <S> Second Entitiy from name of service.
 */
public interface JoinService <D, F, S>{

    @Transactional
    public D createRelation(Long firstId, Long secondId);

    @Transactional
    public void removeRelation(Long firstId, Long secondId);

    public List<S> findSecondsByFirstId(Long firstId);

    public List<F> findFirstsBySecondId(Long secondId);

    List<S> findSecondNotMappedToFirst(OrganDTO organ);

    List<OrganDTO> findOFirstNotMappedToSecond(SymptomDTO symptom);

    @Transactional
    void deleteRelation(F firstDTO, S secondDTO);
}
