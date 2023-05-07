package com.example.demo.mapper;

import com.example.demo.dto.OrganDTO;
import com.example.demo.entity.Organ;
import org.mapstruct.Mapper;

import java.util.List;


public interface GenericMapper <E,D> {

    D toDto(E entity);

    E toEntity(D dto);

    List<D> toDto(List<E> entities);

    List<E> toEntity(List<D> dtos);

}
