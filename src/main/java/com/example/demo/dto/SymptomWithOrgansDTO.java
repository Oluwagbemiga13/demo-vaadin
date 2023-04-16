package com.example.demo.dto;

import com.example.demo.entity.Organ;
import com.example.demo.entity.Symptom;

import java.util.List;

public record SymptomWithOrgansDTO(Symptom symptom, List<Organ> organs) {
}
