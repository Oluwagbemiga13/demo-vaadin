package com.example.demo.repository.alert;

import com.example.demo.entity.alerts.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {

}