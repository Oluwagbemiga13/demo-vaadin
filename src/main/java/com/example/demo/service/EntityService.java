package com.example.demo.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EntityService<E, D> {
    void save(D dto);

    List<D> findAll();

    @Transactional
    void delete(Long id);


    void deleteAll(List<D> dtos);

}
