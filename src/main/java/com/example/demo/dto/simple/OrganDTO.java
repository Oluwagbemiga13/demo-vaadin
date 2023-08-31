package com.example.demo.dto.simple;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrganDTO implements DTO<OrganDTO>{
    Long id;
    String name;

    @Override
    public OrganDTO createInstance(String name) {
        return null;
    }

    public OrganDTO withName(String name) {
        return new OrganDTO(this.id, name);
    }


}
