package com.example.demo.mapper;

import com.example.demo.dto.join.OrganSymptomDTO;
import com.example.demo.entity.join.OrganSymptom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganSymptomMapper extends GenericMapper<OrganSymptom, OrganSymptomDTO> {
}
