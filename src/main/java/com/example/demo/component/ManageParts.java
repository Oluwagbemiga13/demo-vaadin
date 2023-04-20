package com.example.demo.component;

import com.example.demo.dto.PartDTO;
import com.example.demo.dto.SymptomDTO;
import com.example.demo.service.OrganService;
import com.example.demo.service.PartService;
import com.example.demo.service.SymptomService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Route("manage-parts")
@Slf4j
@RequiredArgsConstructor
public class ManageParts extends VerticalLayout {

    private final OrganService organService;
    private final SymptomService symptomService;

    private final PartService partService;

    private String menuItemWidth = "250px";

    private String editButtonsWidth = "300px";

    private String menuButtonsHeight = "75px";

    private Grid<PartDTO> partGrid;
    private Grid<SymptomDTO> symptomGrid;
    private Button createOrganButton;
    private Button createSymptomButton;
    private Button manageRelationsButton;
    private Button backButton;
    private Button deleteSymptomButton;
    private Button deleteOrganButton;


    @PostConstruct
    private void init() {
        partGrid = new Grid<>(PartDTO.class);
        symptomGrid = new Grid<>(SymptomDTO.class);

        partGrid.setVisible(true);

        setMargin(false);
        setPadding(false);

        // Create the menu items

        manageRelationsButton = new Button("Manage Relations", e -> getUI().ifPresent(ui -> ui.navigate(ManageOrganSymptoms.class)));
        manageRelationsButton.setHeight(menuButtonsHeight);
        manageRelationsButton.setWidth(menuItemWidth);

        backButton = new Button("Back", e -> getUI().ifPresent(ui -> ui.navigate(WelcomeComponent.class)));
        backButton.setHeight(menuButtonsHeight);
        backButton.setWidth(menuItemWidth);

        // Create a horizontal layout for the menu items
        HorizontalLayout menuLayout = new HorizontalLayout(
                manageRelationsButton,
                backButton
        );

        menuLayout.setAlignItems(Alignment.CENTER);
        menuLayout.setSpacing(true);
        menuLayout.setMargin(false);
        menuLayout.getElement().getStyle().set("margin-left", "auto");
        menuLayout.getElement().getStyle().set("margin-right", "auto");

        // Set the alignment of the menuLayout to the top of the component
        setAlignSelf(Alignment.START, menuLayout);

        // Add the menu layout to the top of the component
        add(menuLayout);

        Html organLabel = new Html("<div style='font-weight: bold; font-size: 25px;'>Organs</div>");
        Html symptomLabel = new Html("<div style='font-weight: bold; font-size: 25px;'>Symptoms</div>");


        partGrid.setColumns("name"); // Adjust the column names according to your Organ entity
        symptomGrid.setColumns("name"); // Adjust the column names according to your Symptom entity

        partGrid.setWidth("100%");
        symptomGrid.setWidth("100%");

        createOrganButton = new Button("Create new Organ", e -> {
            CreateOrganDialog createOrganDialog = new CreateOrganDialog(organService);
            createOrganDialog.open();
            createOrganDialog.addOpenedChangeListener(event -> {
                if (!event.isOpened()) {
                    refreshGrids();
                }
            });
        });
        createOrganButton.setWidth(editButtonsWidth);

        deleteOrganButton = new Button("Delete selected Organ", e -> {
            PartDTO selectedOrgan = partGrid.asSingleSelect().getValue();
            if (selectedOrgan != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog("Are you sure you want to delete this Organ?", () -> {
                    organService.delete(selectedOrgan.getId());
                    Notification.show("Organ deleted successfully.");
                    refreshGrids();
                    partGrid.asSingleSelect().clear();
                });
                confirmationDialog.open();
            }
        });

        deleteOrganButton.setWidth(editButtonsWidth);

        createSymptomButton = new Button("Create Symptom", e -> {
            CreateSymptomDialog createSymptomDialog = new CreateSymptomDialog(symptomService);
            createSymptomDialog.open();
            createSymptomDialog.addOpenedChangeListener(event -> {
                if (!event.isOpened()) {
                    refreshGrids();
                }
            });
        });
        createSymptomButton.setWidth(editButtonsWidth);


        deleteSymptomButton = new Button("Delete selected Symptom", e -> {
            SymptomDTO selectedSymptom = symptomGrid.asSingleSelect().getValue();
            if (selectedSymptom != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog("Are you sure you want to delete this Symptom?", () -> {
                    symptomService.delete(selectedSymptom.getId());
                    Notification.show("Symptom deleted successfully.");
                    refreshGrids();
                    symptomGrid.asSingleSelect().clear();
                });
                confirmationDialog.open();
            }
        });

        deleteSymptomButton.setWidth(editButtonsWidth);


        VerticalLayout organLayout = new VerticalLayout(organLabel, partGrid, createOrganButton, deleteOrganButton);
        organLayout.setAlignItems(Alignment.CENTER);

        VerticalLayout symptomLayout = new VerticalLayout(symptomLabel, symptomGrid, createSymptomButton, deleteSymptomButton);
        symptomLayout.setAlignItems(Alignment.CENTER);


        HorizontalLayout gridsLayout = new HorizontalLayout(organLayout, symptomLayout);
        gridsLayout.setWidth("80%");
        gridsLayout.setAlignItems(Alignment.CENTER);
        gridsLayout.setSpacing(true);
        gridsLayout.setMargin(false);

        // Add the grids layout to the component
        add(gridsLayout);

        // Set the alignment of the deleteButtonsLayout to the top of the component
        setAlignSelf(Alignment.CENTER, menuLayout, gridsLayout);

        // Add the delete buttons layout to the top of the component
        //add(deleteButtonsLayout);

        refreshGrids();

    }


    private void refreshGrids() {
        List<PartDTO> organs = partService.findAll();
        List<SymptomDTO> symptoms = symptomService.findAll();

        log.info("Fetched Organs: {}", organs);
        log.info("Fetched Symptoms: {}", symptoms);

        partGrid.setItems(organs);
        symptomGrid.setItems(symptoms);


        log.info("OrganGrid Items: {}", partGrid.getDataProvider().size(new Query<>()));
        log.info("SymptomGrid Items: {}", symptomGrid.getDataProvider().size(new Query<>()));
    }

}
