package com.example.demo.repository.simple;

import com.example.demo.entity.simple.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {

}
