package com.example.demo.dto.simple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public interface DTO<D> {

    D createInstance(String name);

    String getName();

    void setName(String name);


    D withName(String name);

    Long getId();
}
