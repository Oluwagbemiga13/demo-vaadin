package com.example.demo.ui.tool;


import com.example.demo.dto.DTO;
import com.example.demo.service.EntityService;
import com.vaadin.flow.component.combobox.ComboBox;
import org.springframework.stereotype.Service;

@Service
public class ComboBoxManager {

    public ComboBox<DTO> getComboBox(EntityService entityService){
        ComboBox <DTO> comboBox = new ComboBox<>("Select an "+ entityService.getEntityName());
        comboBox.setWidth("510px");
        comboBox.setItems(entityService.findAll());
        comboBox.setItemLabelGenerator(com.example.demo.dto.DTO::getName);
        comboBox.setItems(entityService.findAll());
        return comboBox;
    }
}
