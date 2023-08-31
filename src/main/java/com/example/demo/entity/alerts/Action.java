package com.example.demo.entity.alerts;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "actions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Action {

    public enum ActionType {
        GO_TO_QUESTION,
        LOG_AND_SHOW,
        // ... Add other types of actions as needed
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionType type;

    @Column
    private String description;

    // For GO_TO_QUESTION type
    @OneToOne
    private Question targetQuestion;

    // For LOG_AND_REDIRECT type or similar actions
    @Column
    private String logMessage;

    @Column
    private String redirectUrl; // URL or identifier to which UI should navigate

    @OneToOne(mappedBy = "action")
    private Answer answer;
}
