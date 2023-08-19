package com.example.demo.dto.join;

import com.example.demo.dto.simple.DTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class JoinItemDTO {

    private String name;

    private Long id;
    private DTO firstDTO;
    private DTO secondDTO;

    public String getFullName(){
        return firstDTO.getName() + " + " + secondDTO.getName();
    }

}
