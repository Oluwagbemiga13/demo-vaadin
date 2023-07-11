package com.example.demo.entity.join;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class JoinEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
