package com.example.demo.mapper;

import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.Symptom;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SymptomMapper {
//    SymptomMapper INSTANCE = Mappers.getMapper(SymptomMapper.class);

    SymptomDTO toDto(Symptom entity);

    Symptom toEntity(SymptomDTO dto);

    List<SymptomDTO> toDto(List<Symptom> entities);

    List<Symptom> toEntity(List<SymptomDTO> dtos);
}
