package com.example.demo.dto.join;

import com.example.demo.dto.simple.DTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinItemDTO {

    private String name;

    private Long id;
    private DTO firstDTO;
    private DTO secondDTO;

    public String getFullName(){
        return firstDTO.getName() + " + " + secondDTO.getName();
    }



}
