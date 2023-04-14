package com.example.demo.component;

import com.example.demo.entity.Organ;
import com.example.demo.entity.Symptom;
import com.example.demo.service.OrganService;
import com.example.demo.service.SymptomService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("manage-symptoms")
@Slf4j
public class ManageSymptomsComponent extends VerticalLayout {

    private final OrganService organService;
    private final SymptomService symptomService;

    private int menuItemWidth;

    private Grid<Organ> organGrid;
    private Grid<Symptom> symptomGrid;

    @Autowired
    public ManageSymptomsComponent(OrganService organService, SymptomService symptomService) {
        this.organService = organService;
        this.symptomService = symptomService;
        menuItemWidth = 200;

    }

    @PostConstruct
    private void init() {
        organGrid = new Grid<>(Organ.class);
        symptomGrid = new Grid<>(Symptom.class);

        organGrid.setVisible(true);

//        setAlignItems(Alignment.CENTER);
//        setJustifyContentMode(JustifyContentMode.CENTER);
        setMargin(false);
        setPadding(false);

        // Create the menu items
        Button createOrganButton = new Button("Create new Organ", e -> {
            CreateOrganDialog createOrganDialog = new CreateOrganDialog(organService);
            createOrganDialog.open();
            createOrganDialog.addOpenedChangeListener(event -> {
                if (!event.isOpened()) {
                    refreshGrids();
                }
            });
        });

        Button createSymptomButton = new Button("Create Symptom", e -> {
            CreateSymptomDialog createSymptomDialog = new CreateSymptomDialog(symptomService);
            createSymptomDialog.open();
            createSymptomDialog.addOpenedChangeListener(event -> {
                if (!event.isOpened()) {
                    refreshGrids();
                }
            });

        });
        Button manageRelationsButton = new Button("Manage Relations");
        Button backButton = new Button("Back", e -> getUI().ifPresent(ui -> ui.navigate(WelcomeComponent.class)));

        // Set the width of all menu items to the fixed width
        createOrganButton.setWidth(menuItemWidth + "px");
        createSymptomButton.setWidth(menuItemWidth + "px");
        manageRelationsButton.setWidth(menuItemWidth + "px");
        backButton.setWidth(menuItemWidth + "px");

        // Create a horizontal layout for the menu items
        HorizontalLayout menuLayout = new HorizontalLayout(
                createOrganButton,
                createSymptomButton,
                manageRelationsButton,
                backButton
        );

        menuLayout.setAlignItems(Alignment.CENTER);
        menuLayout.setSpacing(false);
        menuLayout.setMargin(false);
        menuLayout.getElement().getStyle().set("margin-left", "auto");
        menuLayout.getElement().getStyle().set("margin-right", "auto");

        // Set the alignment of the menuLayout to the top of the component
        setAlignSelf(Alignment.START, menuLayout);

        // Add the menu layout to the top of the component
        add(menuLayout);

        organGrid.setColumns("id", "name"); // Adjust the column names according to your Organ entity
        symptomGrid.setColumns("id", "name"); // Adjust the column names according to your Symptom entity

        organGrid.setWidth("100%");
        symptomGrid.setWidth("100%");


        HorizontalLayout gridsLayout = new HorizontalLayout(organGrid, symptomGrid);
        gridsLayout.setWidth("100%");
        gridsLayout.setAlignItems(Alignment.CENTER);
        gridsLayout.setSpacing(true);
        gridsLayout.setMargin(false);

        // Add the grids layout to the component
        add(gridsLayout);
        
        refreshGrids();
    }


    private void refreshGrids() {
        List<Organ> organs = organService.findAll();
        List<Symptom> symptoms = symptomService.findAll();

        log.info("Fetched Organs: {}", organs);
        log.info("Fetched Symptoms: {}", symptoms);

        organGrid.setItems(organs);
        symptomGrid.setItems(symptoms);


        log.info("OrganGrid Items: {}", organGrid.getDataProvider().size(new Query<>()));
        log.info("SymptomGrid Items: {}", symptomGrid.getDataProvider().size(new Query<>()));
    }

}
