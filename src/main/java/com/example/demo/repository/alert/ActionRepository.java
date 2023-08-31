package com.example.demo.repository.alert;

import com.example.demo.entity.alerts.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action,Long> {
}
