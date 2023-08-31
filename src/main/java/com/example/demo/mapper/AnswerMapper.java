package com.example.demo.mapper;

import com.example.demo.dto.alert.AnswerDTO;
import com.example.demo.entity.alerts.Answer;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    AnswerDTO toDto(Answer entity, @Context CycleAvoidingMappingContext context);

    Answer toEntity(AnswerDTO dto, @Context CycleAvoidingMappingContext context);

    // Include similar methods for List mappings if needed

    List<AnswerDTO> toDto(List<Answer> entities, @Context CycleAvoidingMappingContext context);

    List<Answer> toEntity(List<AnswerDTO> dtos, @Context CycleAvoidingMappingContext context);
}

