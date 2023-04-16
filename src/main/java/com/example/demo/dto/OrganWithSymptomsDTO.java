package com.example.demo.dto;

import java.util.Objects;
import java.util.Set;

public record OrganWithSymptomsDTO(Long id, String name, Set<SymptomWithOrgansDTO> symptoms) {

    public OrganWithSymptomsDTO withName(String name) {
        return new OrganWithSymptomsDTO(this.id, name, this.symptoms);
    }

}
