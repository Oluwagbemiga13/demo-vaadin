package com.example.demo.service;

import com.example.demo.dto.PartDTO;
import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.OrganSymptom;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EntityService<E, D, I> {
    void saveEntity(D dto);

    public List<D> findAll();

    @Transactional
    public void delete(Long id);

    @Transactional
    public void deleteAllIntermediate(List<I> list);

    public void deleteAll(List<D> dtos);
}
