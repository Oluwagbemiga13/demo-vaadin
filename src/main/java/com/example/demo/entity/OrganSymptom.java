package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


//@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "organ_symptom")
public class OrganSymptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organ_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Organ organ;

    @ManyToOne
    @JoinColumn(name = "symptom_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Symptom symptom;

    // Additional fields, if any (e.g., relationship strength)

    // Constructors, getters, and setters
}

