package com.example.demo.mapper;

import com.example.demo.dto.PartDTO;
import com.example.demo.entity.Part;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

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

