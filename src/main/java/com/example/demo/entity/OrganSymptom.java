package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organ_symptom")
public class OrganSymptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organ_id")
    private Organ organ;

    @ManyToOne
    @JoinColumn(name = "symptom_id")
    private Symptom symptom;

    // Additional fields, if any (e.g., relationship strength)

    // Constructors, getters, and setters
}

