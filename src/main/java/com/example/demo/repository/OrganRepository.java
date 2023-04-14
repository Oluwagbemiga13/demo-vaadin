package com.example.demo.repository;

import com.example.demo.entity.Organ;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrganRepository extends JpaRepository<Organ, Long> {

}
