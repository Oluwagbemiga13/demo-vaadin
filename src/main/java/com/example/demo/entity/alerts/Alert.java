package com.example.demo.entity.alerts;

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
@Table(name = "alerts", uniqueConstraints={
        @UniqueConstraint(columnNames = {"organ_symptom_id"}),
        @UniqueConstraint(columnNames = {"part_organ_id"}),
        @UniqueConstraint(columnNames = {"symptom_part_id"})
})
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;

    @Column
    private String message;

    @Column
    private String severity;

    @OneToOne
    @JoinColumn(name = "organ_symptom_id")
    private OrganSymptom organSymptom;

    @OneToOne
    @JoinColumn(name = "part_organ_id")
    private PartOrgan partOrgan;

    @OneToOne
    @JoinColumn(name = "symptom_part_id")
    private SymptomPart symptomPart;
}

