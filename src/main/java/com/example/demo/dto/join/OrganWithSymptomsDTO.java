package com.example.demo.dto.join;

import com.example.demo.entity.simple.Organ;
import com.example.demo.entity.simple.Symptom;
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
