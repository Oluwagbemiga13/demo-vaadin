package com.example.demo.mapper;

import com.example.demo.dto.simple.OrganDTO;
import com.example.demo.entity.simple.Organ;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganMapper extends GenericMapper<Organ,OrganDTO> {

    //OrganMapper INSTANCE = Mappers.getMapper(OrganMapper.class);

//    OrganDTO toDto(Organ entity);
//
//    Organ toEntity(OrganDTO dto);
//
//    List<OrganDTO> toDto(List<Organ> entities);
//
//    List<Organ> toEntity(List<OrganDTO> dtos);
}
