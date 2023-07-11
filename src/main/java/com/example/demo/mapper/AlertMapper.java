package com.example.demo.mapper;

import com.example.demo.dto.simple.AlertDTO;
import com.example.demo.entity.simple.Alert;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlertMapper extends GenericMapper<Alert, AlertDTO> {
}
