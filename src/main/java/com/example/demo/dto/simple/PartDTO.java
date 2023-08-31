package com.example.demo.dto.simple;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PartDTO implements DTO<PartDTO> {
    private Long id;
    private String name;

    @Override
    public PartDTO createInstance(String name) {
        PartDTO partDTO = new PartDTO();
        partDTO.setName(name);
        return partDTO;
    }

    @Override
    public PartDTO withName(String name) {
        return new PartDTO(this.id, name);
    }
}

