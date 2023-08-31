package com.example.demo.mapper;

import com.example.demo.dto.alert.ActionDTO;
import com.example.demo.entity.alerts.Action;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActionMapper extends GenericMapper<Action, ActionDTO> {
}
