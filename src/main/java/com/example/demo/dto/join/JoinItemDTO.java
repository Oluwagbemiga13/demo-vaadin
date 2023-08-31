package com.example.demo.dto.join;

import com.example.demo.dto.simple.DTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public interface JoinItemDTO<F,S> {

    String getName();

    Long getId();
    String getFullName();

    F getFirstDTO();

    S getSecondDTO();
}
