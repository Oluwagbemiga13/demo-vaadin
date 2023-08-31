package com.example.demo.mapper;

import com.example.demo.dto.alert.QuestionDTO;
import com.example.demo.entity.alerts.Question;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionDTO toDto(Question entity, @Context CycleAvoidingMappingContext context);

    Question toEntity(QuestionDTO dto, @Context CycleAvoidingMappingContext context);

    // Include similar methods for List mappings if needed

    List<QuestionDTO> toDto(List<Question> entities, @Context CycleAvoidingMappingContext context);

    List<Question> toEntity(List<QuestionDTO> dtos, @Context CycleAvoidingMappingContext context);
}

