package com.example.demo.ui.tool;

import com.example.demo.dto.simple.DTO;
import com.example.demo.dto.simple.OrganDTO;
import com.example.demo.service.join.OrganSymptomService;
import com.example.demo.service.join.PartOrganService;
import com.example.demo.service.simple.OrganService;
import com.example.demo.service.simple.PartService;
import com.example.demo.service.simple.SymptomService;
import com.example.demo.ui.dialogs.EntityCreationDialog;
import com.example.demo.ui.pages.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;


@Route("/api/test")
@RequiredArgsConstructor
@Slf4j
public class TestComponent extends VerticalLayout {

    private final MenuInitializer menuInitializer;

    private final GridManager gridManager;

    private final ButtonInitializer buttonInitializer;

    private final SymptomService symptomService;

    private final OrganService organService;

    private final ComponentBuilder componentBuilder;

    private final OrganSymptomService organSymptomService;

    private final PartOrganService partOrganService;

    private final ComboBoxManager comboBoxManager;

    private final PartService partService;

    final String MENU_BUTTON_WIDTH = "200px";


    @PostConstruct
    public void init() {

        //add(initMenu());

        //add(initGrid());

        //add(initButton());

//        add(initGrid_2());
//
//        add(initGrid_3());

        //add(initGrid_4());

        //add(initGrid_7());

        add(initGrid_4());

        setAlignSelf(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);


    }

    public VerticalLayout initMenu() {
        Button manageSymptoms = buttonInitializer.createNavButton("Manage Symptoms", this, ManageSymptoms.class, MENU_BUTTON_WIDTH);
        Button manageOrgans = buttonInitializer.createNavButton("Manage Organs", this, ManageOrgans.class, MENU_BUTTON_WIDTH);
        Button manageBodyParts = buttonInitializer.createNavButton("Manage Body", this, ManageParts.class, MENU_BUTTON_WIDTH);
        Button manageLogs = buttonInitializer.createNavButton("Manage Logs", this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button questionnaires = buttonInitializer.createNavButton("Questionnaires", this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button notes = buttonInitializer.createNavButton("Notes", this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button settings = buttonInitializer.createNavButton("Settings", this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button helpAndSupport = buttonInitializer.createNavButton("Help & Support", this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button about = buttonInitializer.createNavButton("About", this, TestComponent.class, MENU_BUTTON_WIDTH);

        List<Component> buttons = Arrays.asList(manageSymptoms, manageOrgans, manageBodyParts, manageLogs, questionnaires, notes, settings, helpAndSupport, about);

        return menuInitializer.createVerticalMenu(buttons);
    }

    public Grid<OrganDTO> initGrid() {
        return gridManager.createLonelyGrid(organService, new String[]{"name"});
    }


    public Button initButton() {

        Runnable action = () -> {
            EntityCreationDialog entityCreationDialog = new EntityCreationDialog(symptomService);
            entityCreationDialog.open();
            entityCreationDialog.addOpenedChangeListener(event -> {
                if (!event.isOpened()) {
                }
            });
        };
        return buttonInitializer.createActButton("123", action, "120px");
    }

    public VerticalLayout initGrid_2() {

        return gridManager.createGrid_CreateButton(new String[]{"name"}, organService);
    }

    public VerticalLayout initGrid_3() {

        return gridManager.createGrid_CreateButton(new String[]{"name"}, symptomService);
    }

    public HorizontalLayout initGrid_4() {
        return componentBuilder.simple_entity_grid_options(symptomService, this, WelcomeComponent.class, SymptomRelations.class);
    }

    public HorizontalLayout initGrid_5() {
        ComboBox<DTO> combo = comboBoxManager.getComboBox(organService);
        Grid<DTO> freeEntitiesGrid = new Grid<>(DTO.class);
        Grid<DTO> attachedEntities = new Grid<>(DTO.class);
        return new HorizontalLayout(combo, componentBuilder.create_attached_entities_option(organSymptomService, 1, organService, combo, attachedEntities, freeEntitiesGrid, "200%"),
                componentBuilder.create_free_entities_option(organSymptomService, 1, organService, combo, freeEntitiesGrid, attachedEntities, "200%"));

    }

    public VerticalLayout initGrid_6() {
        ComboBox<DTO> combo = comboBoxManager.getComboBox(organService);
        Grid<DTO> freeEntitiesGrid = new Grid<>(DTO.class);
        Grid<DTO> attachedEntities = new Grid<>(DTO.class);
        VerticalLayout layout = new VerticalLayout(combo, componentBuilder.create_managing_relation_layout(organSymptomService, 1, organService, combo,
                freeEntitiesGrid, attachedEntities, "150%"));
        layout.setAlignItems(Alignment.CENTER);
        layout.setAlignSelf(Alignment.CENTER);
        return layout;
    }

    public VerticalLayout initGrid_7() {
        ComboBox<DTO> combo = comboBoxManager.getComboBox(organService);
        Grid<DTO> freeEntitiesGrid = new Grid<>(DTO.class);
        Grid<DTO> attachedEntities = new Grid<>(DTO.class);
        VerticalLayout layout = new VerticalLayout(combo, componentBuilder.create_managing_relation_layout(organSymptomService, 1, organService, combo,
                freeEntitiesGrid, attachedEntities, "100%"));
        layout.setAlignItems(Alignment.CENTER);
        layout.setAlignSelf(Alignment.CENTER);
        layout.setSpacing(true);
        layout.setMargin(true);
        return layout;
    }

    public VerticalLayout initGrid_8() {
        ComboBox<DTO> combo = comboBoxManager.getComboBox(symptomService);
        Grid<DTO> freeEntitiesGrid = new Grid<>(DTO.class);
        Grid<DTO> attachedEntities = new Grid<>(DTO.class);
        VerticalLayout layout = new VerticalLayout(combo, componentBuilder.create_managing_relation_layout(organSymptomService, 1, symptomService, combo,
                freeEntitiesGrid, attachedEntities, "100%"));
        layout.setAlignItems(Alignment.CENTER);
        layout.setAlignSelf(Alignment.CENTER);
        layout.setSpacing(true);
        layout.setMargin(true);
        return layout;
    }

    public VerticalLayout initGrid_9() {
        ComboBox<DTO> combo = comboBoxManager.getComboBox(organService);
        Grid<DTO> freeEntitiesGrid = new Grid<>(DTO.class);
        Grid<DTO> attachedEntities = new Grid<>(DTO.class);
        VerticalLayout layout = new VerticalLayout(combo, componentBuilder.create_managing_relation_layout(organSymptomService, 1, organService, combo,
                freeEntitiesGrid, attachedEntities, "100%"));
        layout.setAlignItems(Alignment.CENTER);
        layout.setAlignSelf(Alignment.CENTER);
        layout.setSpacing(true);
        layout.setMargin(true);
        return layout;
    }

    public VerticalLayout initGrid_10() {
        ComboBox<DTO> combo = comboBoxManager.getComboBox(organService);
        Grid<DTO> freeEntitiesGrid = new Grid<>(DTO.class);
        Grid<DTO> attachedEntities = new Grid<>(DTO.class);
        Grid<DTO> freeEntitiesGrid_2 = new Grid<>(DTO.class);
        Grid<DTO> attachedEntities_2 = new Grid<>(DTO.class);
        VerticalLayout layout = new VerticalLayout(combo,
                componentBuilder.create_managing_relation_layout(partOrganService, 1, partService, combo,
                        freeEntitiesGrid_2, attachedEntities_2, "100%"),
                componentBuilder.create_managing_relation_layout(organSymptomService, 0, symptomService, combo,
                        freeEntitiesGrid, attachedEntities, "100%"));

        layout.setAlignItems(Alignment.CENTER);
        layout.setAlignSelf(Alignment.CENTER);
        layout.setSpacing(true);
        layout.setMargin(true);
        return layout;
    }


}
