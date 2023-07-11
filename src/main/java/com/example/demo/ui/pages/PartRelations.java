package com.example.demo.ui.pages;

import com.example.demo.dto.simple.DTO;
import com.example.demo.service.join.PartOrganService;
import com.example.demo.service.join.SymptomPartService;
import com.example.demo.service.simple.OrganService;
import com.example.demo.service.simple.PartService;
import com.example.demo.service.simple.SymptomService;
import com.example.demo.ui.tool.ButtonInitializer;
import com.example.demo.ui.tool.ComboBoxManager;
import com.example.demo.ui.tool.ComponentBuilder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Route("part-relations")
@Slf4j
@RequiredArgsConstructor
public class PartRelations extends VerticalLayout {
    private final PartOrganService partOrganService;

    private final ButtonInitializer buttonInitializer;

    private final ComboBoxManager comboBoxManager;

    private final PartService partService;

    private final ComponentBuilder componentBuilder;

    private final SymptomPartService symptomPartService;

    private final SymptomService symptomService;

    private final OrganService organService;


    private String menuItemWidth = "250px";

    @PostConstruct
    private void init() {
        Button backButton = buttonInitializer.createNavButton("Back", this, ManageOrgans.class, menuItemWidth);

        ComboBox<DTO> combo = comboBoxManager.getComboBox(partService);
        Grid<DTO> freeEntitiesGrid = new Grid<>(DTO.class);
        Grid<DTO> attachedEntities = new Grid<>(DTO.class);
        Grid<DTO> freeEntitiesGrid_2 = new Grid<>(DTO.class);
        Grid<DTO> attachedEntities_2 = new Grid<>(DTO.class);
        VerticalLayout layout = new VerticalLayout(backButton, combo,
                componentBuilder.create_managing_relation_layout(partOrganService, 0, organService, combo,
                        freeEntitiesGrid_2, attachedEntities_2, "100%"),
                componentBuilder.create_managing_relation_layout(symptomPartService, 1, symptomService, combo,
                        freeEntitiesGrid, attachedEntities, "100%"));
        layout.setAlignItems(Alignment.CENTER);
        layout.setAlignSelf(Alignment.CENTER);
        layout.setSpacing(true);
        layout.setMargin(true);
        add(layout);

    }


}
