package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "symptoms")
public class Symptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "symptom", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH}, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrganSymptom> organs = new HashSet<>();

    @OneToMany(mappedBy = "symptom", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH}, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<PartSymptom> parts = new HashSet<>();

    // Other fields if any
}
