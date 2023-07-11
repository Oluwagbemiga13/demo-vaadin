package com.example.demo.mapper;

import com.example.demo.dto.simple.QuestionDTO;
import com.example.demo.entity.simple.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper extends GenericMapper<Question, QuestionDTO>{
}
