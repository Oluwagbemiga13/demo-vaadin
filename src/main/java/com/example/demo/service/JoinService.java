package com.example.demo.service;

import com.example.demo.mapper.GenericMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Here I fully dedicated to rewrite this whole project, using SOLID.
 * @param <E> Entity representation of Intermediate Entity
 * @param <F> First DTO from name of service. e.g. OrganSymptomService -> OrganDTO
 * @param <S> Second DTO from name of service.
 */
public interface JoinService <E, F, S>{

    @Transactional
    public E createRelation(Long firstId, Long secondId);

    public List<S> findSecondsByFirstId(Long firstId);

    public List<F> findFirstsBySecondId(Long secondId);

    List<S> findSecondNotMappedToFirst(F firstDTO);

    List<F> findFirstNotMappedToSecond(S secondDTO);

    @Transactional
    void deleteRelation(F firstDTO, S secondDTO);

    GenericMapper getFirstMapper();

    GenericMapper getSecondMapper();

    EntityService getFirstService();

    EntityService getSecondService();
}
