package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SymptomDTO extends DTO {

    private Long id;
    private String name;

    public SymptomDTO(SymptomDTO symptomDTO, Long id) {
        this.id = id;
        this.name = symptomDTO.name;
    }

//    public static SymptomDTO withName(SymptomDTO symptomDTO, String name) {
//        return new SymptomDTO(symptomDTO.getId(), name);
//    }
}
