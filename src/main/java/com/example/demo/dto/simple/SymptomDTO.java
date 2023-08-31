package com.example.demo.dto.simple;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SymptomDTO implements DTO<SymptomDTO> {

    private Long id;
    private String name;

    public SymptomDTO(SymptomDTO symptomDTO, Long id) {
        this.id = id;
        this.name = symptomDTO.name;
    }

    @Override
    public SymptomDTO createInstance(String name) {

        return null;
    }

    @Override
    public SymptomDTO withName(String name) {

        return new SymptomDTO();
    }

//    public static SymptomDTO withName(SymptomDTO symptomDTO, String name) {
//        return new SymptomDTO(symptomDTO.getId(), name);
//    }
}
