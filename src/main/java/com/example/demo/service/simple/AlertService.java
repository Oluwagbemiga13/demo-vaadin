package com.example.demo.service.simple;

import com.example.demo.dto.simple.AlertDTO;
import com.example.demo.entity.simple.Alert;
import com.example.demo.entity.simple.Question;
import com.example.demo.mapper.AlertMapper;
import com.example.demo.repository.simple.AlertRepository;
import com.vaadin.flow.data.binder.Binder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertService implements EntityService<Alert, AlertDTO> {

    private final AlertRepository alertRepository;

    private final AlertMapper alertMapper;

    private final QuestionService questionService;

    @Override
    public Class<AlertDTO> getDTOClass() {
        return AlertDTO.class;
    }

    @Override
    public Binder<AlertDTO> getBinder() {
        return new Binder<>(AlertDTO.class);
    }

    @Override
    public Class<Alert> getEntityClass() {
        return Alert.class;
    }

    @Override
    public AlertDTO save(AlertDTO dto) {
        Alert alert = new Alert();
        alert.setMessage(dto.getMessage());
        alert.setSeverity(dto.getSeverity());

        return alertMapper.toDto(alertRepository.save(alert));

    }

    @Override
    public List<AlertDTO> findAll() {
        return alertMapper.toDto(alertRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        alertRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<AlertDTO> dtos) {
        alertRepository.deleteAll(alertMapper.toEntity(dtos));
    }

    @Override
    public String getEntityName() {
        return "Alert";
    }

    @Override
    public Alert findById(Long id) {
        Optional<Alert> optional = alertRepository.findById(id);
        if (optional.isPresent())return optional.get();
        else throw new IllegalArgumentException("ID :" + id + " does not exist");
    }

    // Implement other methods...
}
