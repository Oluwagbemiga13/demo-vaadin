package com.example.demo.dto.simple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PartDTO extends DTO {
    private Long id;
    private String name;

    public PartDTO withName(String name) {
        return new PartDTO(this.id, name);
    }
}

