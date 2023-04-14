package com.example.demo.dto;

public record SymptomDTO(String id, String name) {

    public static SymptomDTO withId(SymptomDTO symptomDTO, String id) {
        return new SymptomDTO(id, symptomDTO.name());
    }

    public static SymptomDTO withName(SymptomDTO symptomDTO, String name) {
        return new SymptomDTO(symptomDTO.id(), name);
    }
}
