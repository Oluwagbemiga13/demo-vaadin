package com.example.demo.repository;

import com.example.demo.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomRepository extends JpaRepository<Symptom, String> {
}