package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PartDTO {
    private Long id;
    private String name;

    public PartDTO withName(String name) {
        return new PartDTO(this.id, name);
    }
}

