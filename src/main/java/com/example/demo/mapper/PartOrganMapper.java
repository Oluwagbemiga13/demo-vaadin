package com.example.demo.mapper;

import com.example.demo.dto.join.PartOrganDTO;
import com.example.demo.entity.join.PartOrgan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartOrganMapper extends GenericMapper<PartOrgan, PartOrganDTO> {
}
