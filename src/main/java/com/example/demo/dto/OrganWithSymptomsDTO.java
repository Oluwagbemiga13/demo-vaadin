package com.example.demo.dto;

import com.example.demo.entity.Organ;
import com.example.demo.entity.Symptom;

import java.util.List;

public record OrganWithSymptomsDTO(Organ organ, List<Symptom> symptoms) {
}
