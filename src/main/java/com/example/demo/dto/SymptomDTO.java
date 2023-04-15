package com.example.demo.dto;

public record SymptomDTO(Long id, String name) {

    public static SymptomDTO withId(SymptomDTO symptomDTO, Long id) {
        return new SymptomDTO(id, symptomDTO.name());
    }

    public static SymptomDTO withName(SymptomDTO symptomDTO, String name) {
        return new SymptomDTO(symptomDTO.id(), name);
    }
}
