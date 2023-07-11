package com.example.demo.mapper;

import com.example.demo.dto.simple.PartDTO;
import com.example.demo.entity.simple.Part;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PartMapper extends GenericMapper<Part,PartDTO> {

    PartMapper INSTANCE = Mappers.getMapper(PartMapper.class);

//    PartDTO toDto(Part entity);
//
//    Part toEntity(PartDTO dto);
//
//    List<PartDTO> toDto(List<Part> entities);
//
//    List<Part> toEntity(List<PartDTO> dtos);
}

