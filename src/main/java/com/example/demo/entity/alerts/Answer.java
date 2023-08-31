package com.example.demo.entity.alerts;

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
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String answer;

    @ManyToOne
    private Question question;

    @OneToOne
    private Question nextQuestion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "action_id")
    private Action action;

}
