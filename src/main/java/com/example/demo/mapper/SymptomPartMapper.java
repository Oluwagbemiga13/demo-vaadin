package com.example.demo.mapper;

import com.example.demo.dto.join.SymptomPartDTO;
import com.example.demo.entity.join.SymptomPart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SymptomPartMapper extends GenericMapper<SymptomPart, SymptomPartDTO> {
}
