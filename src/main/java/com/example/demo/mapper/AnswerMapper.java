package com.example.demo.mapper;

import com.example.demo.dto.simple.AnswerDTO;
import com.example.demo.entity.simple.Answer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper extends GenericMapper <Answer, AnswerDTO>{
}
