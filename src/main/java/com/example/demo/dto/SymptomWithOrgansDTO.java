package com.example.demo.dto;

import com.example.demo.entity.simple.Organ;
import com.example.demo.entity.simple.Symptom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SymptomWithOrgansDTO {
    private Symptom symptom;
    private List<Organ> organs;
}
