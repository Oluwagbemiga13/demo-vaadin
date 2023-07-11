package com.example.demo.entity.simple;

import com.example.demo.entity.join.OrganSymptom;
import com.example.demo.entity.join.PartOrgan;
import com.example.demo.entity.join.SymptomPart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;

    @Column(nullable = false)
    private String message;

    @Column
    private String severity;

    @ManyToOne
    private OrganSymptom organSymptom;

    @ManyToOne
    private PartOrgan partOrgan;

    @ManyToOne
    private SymptomPart symptomPart;


    // you can add other attributes here
}

