package com.example.demo.mapper;

import com.example.demo.dto.alert.AlertDTO;
import com.example.demo.entity.alerts.Alert;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlertMapper extends GenericMapper<Alert, AlertDTO> {
}
