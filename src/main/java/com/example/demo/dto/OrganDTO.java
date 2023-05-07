package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrganDTO extends DTO {
    Long id;
    String name;

    public OrganDTO withName(String name) {
        return new OrganDTO(this.id, name);
    }



}
