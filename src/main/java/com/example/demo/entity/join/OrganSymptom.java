package com.example.demo.entity.join;

import com.example.demo.entity.simple.Organ;
import com.example.demo.entity.simple.Symptom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

}

