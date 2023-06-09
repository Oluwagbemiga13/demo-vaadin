package com.example.demo.ui.pages;

import com.example.demo.ui.dialogs.ConfirmationDialog;
import com.example.demo.ui.dialogs.EntityCreationDialog;
import com.example.demo.ui.tool.ComponentBuilder;
import com.example.demo.ui.tool.GridManager;
import com.example.demo.dto.OrganDTO;
import com.example.demo.dto.SymptomDTO;
import com.example.demo.service.OrganService;
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

@Route("manage-symptoms")
@Slf4j
@RequiredArgsConstructor
public class ManageSymptoms extends VerticalLayout {

    private final ComponentBuilder componentBuilder;

    private final OrganService organService;
    private final SymptomService symptomService;

    private final GridManager gridManager;

    private String menuItemWidth = "300px";

    private String editButtonsWidth = "300px";

    private String menuButtonsHeight = "75px";

    private Grid<OrganDTO> organGrid;
    private Grid<SymptomDTO> symptomGrid;
    private Button createOrganButton;
    private Button createSymptomButton;
    private Button manageRelationsButton;
    private Button backButton;
    private Button deleteSymptomButton;
    private Button deleteOrganButton;


    @PostConstruct
    private void init() {
        add(componentBuilder.simple_entity_grid_options(symptomService, this, WelcomeComponent.class, SymptomRelations.class));


////        organGrid = new Grid<>(OrganDTO.class);
//        symptomGrid = gridManager.createLonelyGrid(symptomService,new String[]{"name"});
//
////        organGrid.setVisible(true);
//
//        setMargin(false);
//        setPadding(false);
//
//
////        Html organLabel = new Html("<div style='font-weight: bold; font-size: 25px;'>Organs</div>");
//        Html symptomLabel = new Html("<div style='font-weight: bold; font-size: 45px;'>Symptoms</div>");
//
//
////        organGrid.setColumns("name"); // Adjust the column names according to your Organ entity
//
//
////        organGrid.setWidth("100%");
//
//
////        createOrganButton = new Button("Create new Organ", e -> {
////            CreateEntityDialog createOrganDialog = new CreateEntityDialog(organService);
////            createOrganDialog.open();
////            createOrganDialog.addOpenedChangeListener(event -> {
////                if (!event.isOpened()) {
////                    refreshGrids();
////                }
////            });
////        });
////        createOrganButton.setWidth(editButtonsWidth);
////
////        deleteOrganButton = new Button("Delete selected Organ", e -> {
////            OrganDTO selectedOrgan = organGrid.asSingleSelect().getValue();
////            if (selectedOrgan != null) {
////                ConfirmationDialog confirmationDialog = new ConfirmationDialog("Are you sure you want to delete this Organ?", () -> {
////                    organService.delete(selectedOrgan.getId());
////                    Notification.show("Organ deleted successfully.");
////                    refreshGrids();
////                    organGrid.asSingleSelect().clear();
////                });
////                confirmationDialog.open();
////            }
////        });
//
////        deleteOrganButton.setWidth(editButtonsWidth);
//
//        createSymptomButton = new Button("Create Symptom", e -> {
//            EntityCreationDialog entityCreationDialog = new EntityCreationDialog(symptomService);
//            entityCreationDialog.open();
//            entityCreationDialog.addOpenedChangeListener(event -> {
//                if (!event.isOpened()) {
//                    refreshGrids();
//                }
//            });
//        });
//        createSymptomButton.setWidth(editButtonsWidth);
//
//
//        deleteSymptomButton = new Button("Delete selected Symptom", e -> {
//            SymptomDTO selectedSymptom = symptomGrid.asSingleSelect().getValue();
//            if (selectedSymptom != null) {
//                ConfirmationDialog confirmationDialog = new ConfirmationDialog("Are you sure you want to delete this Symptom?", () -> {
//                    symptomService.delete(selectedSymptom.getId());
//                    Notification.show("Symptom deleted successfully.");
//                    refreshGrids();
//                    symptomGrid.asSingleSelect().clear();
//                });
//                confirmationDialog.open();
//            }
//        });
//
//        deleteSymptomButton.setWidth(editButtonsWidth);
//
//        manageRelationsButton = new Button("Manage Relations", e -> getUI().ifPresent(ui -> ui.navigate(SymptomRelations.class)));
//        manageRelationsButton.setHeight(menuButtonsHeight);
//        manageRelationsButton.setWidth(menuItemWidth);
//
//        backButton = new Button("Back", e -> getUI().ifPresent(ui -> ui.navigate(WelcomeComponent.class)));
//        backButton.setHeight(menuButtonsHeight);
//        backButton.setWidth(menuItemWidth);
//
//        // Create a horizontal layout for the menu items
//        VerticalLayout menuLayout = new VerticalLayout(
//                createSymptomButton, deleteSymptomButton,
//                manageRelationsButton,
//                backButton
//        );
//
//        menuLayout.setAlignItems(Alignment.START);
////        menuLayout.setSpacing(true);
////        menuLayout.setMargin(false);
////        menuLayout.getElement().getStyle().set("margin-left", "auto");
////        menuLayout.getElement().getStyle().set("margin-right", "auto");
//
//        // Set the alignment of the menuLayout to the top of the component
//        //setAlignSelf(Alignment.START, menuLayout);
//
//        // Add the menu layout to the top of the component
//
//
////        VerticalLayout organLayout = new VerticalLayout(organLabel, organGrid, createOrganButton, deleteOrganButton);
////        organLayout.setAlignItems(Alignment.CENTER);
//
//        VerticalLayout leftGrid = new VerticalLayout(symptomLabel, symptomGrid);
//        leftGrid.setAlignItems(Alignment.CENTER);
//
//        VerticalLayout rightOptions = new VerticalLayout(menuLayout);
//        rightOptions.setAlignItems(Alignment.START);
//
//
//        HorizontalLayout gridsLayout = new HorizontalLayout(/*organLayout,*/ leftGrid, rightOptions);
//        gridsLayout.setWidth("80%");
//        gridsLayout.setAlignItems(Alignment.CENTER);
//        gridsLayout.setSpacing(true);
//        gridsLayout.setMargin(false);
//
//        // Add the grids layout to the component
//        add(gridsLayout);
//
//        // Set the alignment of the deleteButtonsLayout to the top of the component
//        setAlignSelf(Alignment.CENTER, menuLayout, gridsLayout);
//
//        refreshGrids();

    }


    private void refreshGrids() {
//        List<OrganDTO> organs = organService.findAll();
        symptomGrid.setItems(symptomService.findAll());

//        log.info("Fetched Organs: {}", organs);
        log.info("Fetched Symptoms: {}", symptomService.findAll());

//        organGrid.setItems(organs);
       // symptomGrid.setItems(symptoms);


//        log.info("OrganGrid Items: {}", organGrid.getDataProvider().size(new Query<>()));
        log.info("SymptomGrid Items: {}", symptomGrid.getDataProvider().size(new Query<>()));
    }

}
