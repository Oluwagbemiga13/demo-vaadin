package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DTO {
    Long id;
    String name;

    public DTO(String name){
        this.name = name;
    }

    public DTO withName(String name) {
        return new OrganDTO(this.id, name);
    }
}
