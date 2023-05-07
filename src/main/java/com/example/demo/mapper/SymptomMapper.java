package com.example.demo.mapper;

import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.Symptom;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SymptomMapper extends GenericMapper<Symptom, SymptomDTO> {

}
