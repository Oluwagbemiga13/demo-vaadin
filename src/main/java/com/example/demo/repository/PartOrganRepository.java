package com.example.demo.repository;

import com.example.demo.entity.PartOrgan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartOrganRepository extends JpaRepository<PartOrgan, Long> {

    List<PartOrgan> findAllByPartId(Long partId);

    List<PartOrgan> findAllByOrganId(Long partId);

    Optional<PartOrgan> findByPartIdAndOrganId(Long partId, Long organId);

}
