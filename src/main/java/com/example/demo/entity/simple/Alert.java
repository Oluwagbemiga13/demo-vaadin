package com.example.demo.entity.simple;

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

    @Column(nullable = false)
    private String severity;



    // you can add other attributes here
}

