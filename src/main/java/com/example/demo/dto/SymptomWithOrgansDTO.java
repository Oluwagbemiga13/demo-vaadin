package com.example.demo.dto;

import java.util.Objects;
import java.util.Set;

public record SymptomWithOrgansDTO(Long id, String name, Set<OrganWithSymptomsDTO> organs) {

    public SymptomWithOrgansDTO withName(String name) {
        return new SymptomWithOrgansDTO(this.id, name, this.organs);
    }

}
