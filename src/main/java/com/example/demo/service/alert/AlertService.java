package com.example.demo.service.alert;

import com.example.demo.dto.alert.AlertDTO;
import com.example.demo.dto.join.JoinItemDTO;
import com.example.demo.dto.simple.*;
import com.example.demo.entity.alerts.Alert;
import com.example.demo.mapper.AlertMapper;
import com.example.demo.repository.alert.AlertRepository;
import com.example.demo.service.join.OrganSymptomService;
import com.example.demo.service.join.PartOrganService;
import com.example.demo.service.join.SymptomPartService;
import com.example.demo.service.simple.EntityService;
import com.vaadin.flow.data.binder.Binder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertService implements EntityService<Alert, AlertDTO> {

    private final AlertRepository alertRepository;

    private final OrganSymptomService organSymptomService;

    private final SymptomPartService symptomPartService;

    private final PartOrganService partOrganService;

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
        if (optional.isPresent()) return optional.get();
        else throw new IllegalArgumentException("ID :" + id + " does not exist");
    }

    public AlertDTO saveAlertFromJoinDTO(JoinItemDTO joinItemDTO) throws DataIntegrityViolationException {
        Alert alert = new Alert();

        // Check the types of the DTOs to decide which relation to set
        DTO firstDTO = (DTO) joinItemDTO.getFirstDTO();
        DTO secondDTO = (DTO) joinItemDTO.getSecondDTO();
        Long joinId = joinItemDTO.getId();

        if (firstDTO instanceof OrganDTO && secondDTO instanceof SymptomDTO) {
            alert.setOrganSymptom(organSymptomService.findById(joinId).get());
        } else if (firstDTO instanceof PartDTO && secondDTO instanceof OrganDTO) {
            alert.setPartOrgan(partOrganService.findById(joinId).get());
        } else if (firstDTO instanceof SymptomDTO && secondDTO instanceof PartDTO) {
            alert.setSymptomPart(symptomPartService.findById(joinId).get());
        }

        return alertMapper.toDto(alertRepository.save(alert));
    }

}
