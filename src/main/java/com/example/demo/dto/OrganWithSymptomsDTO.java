package com.example.demo.dto;

import com.example.demo.entity.Organ;
import com.example.demo.entity.Symptom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrganWithSymptomsDTO {
    private Organ organ;
    private List<Symptom> symptoms;

}
