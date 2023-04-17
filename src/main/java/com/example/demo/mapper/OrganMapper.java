package com.example.demo.mapper;

import com.example.demo.dto.OrganDTO;
import com.example.demo.entity.Organ;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganMapper {

    //OrganMapper INSTANCE = Mappers.getMapper(OrganMapper.class);

    OrganDTO toDto(Organ entity);

    Organ toEntity(OrganDTO dto);

    List<OrganDTO> toDtoList(List<Organ> entities);

    List<Organ> toEntityList(List<OrganDTO> dtos);
}
