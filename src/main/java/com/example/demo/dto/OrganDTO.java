package com.example.demo.dto;

import java.util.Objects;

public record OrganDTO(Long id, String name) {


    public OrganDTO withName(String name) {
        return new OrganDTO(this.id, name);
    }

}
