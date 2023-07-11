package com.example.demo.mapper;

import com.example.demo.dto.simple.SymptomDTO;
import com.example.demo.entity.simple.Symptom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SymptomMapper extends GenericMapper<Symptom, SymptomDTO> {

}
