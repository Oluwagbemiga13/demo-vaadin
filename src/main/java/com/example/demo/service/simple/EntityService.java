package com.example.demo.service.simple;

import com.vaadin.flow.data.binder.Binder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EntityService<E, D> {

    Class<D> getDTOClass();

    Binder<D> getBinder();

    Class<E> getEntityClass();

    D save(D dto);

    List<D> findAll();

    @Transactional
    void delete(Long id);

    void deleteAll(List<D> dtos);

    String getEntityName();

    E findById(Long id);


}
