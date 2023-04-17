package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrganDTO {
Long id;
String name;

public OrganDTO withName(String name) {
        return new OrganDTO(this.id, name);
    }

}
