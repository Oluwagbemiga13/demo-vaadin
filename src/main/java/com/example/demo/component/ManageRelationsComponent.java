//package com.example.demo.component;
//
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.router.Route;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Route("manage-relations")
//@Slf4j
//public class ManageRelationsComponent extends VerticalLayout {
//
//    private final OrganWithSymptomsService organWithSymptomsService;
//    private final SymptomWithOrgansService symptomWithOrgansService;
//
//
//    private int menuItemWidth;
//
//    private Grid<OrganWithSymptoms> organWithSymptomsGrid;
//    private Grid<SymptomWithOrgans> symptomWithOrgansGrid;
//    private Grid<String> relatedGrid;
//
//    @Autowired
//    public ManageRelationsComponent(OrganWithSymptomsService organWithSymptomsService,
//                                    SymptomWithOrgansService symptomWithOrgansService) {
//        this.organWithSymptomsService = organWithSymptomsService;
//        this.symptomWithOrgansService = symptomWithOrgansService;
//        menuItemWidth = 250;
//
//        init();
//    }
//
//    private void init() {
//        setMargin(false);
//        setPadding(false);
//        organWithSymptomsGrid = new Grid<>(OrganWithSymptoms.class);
//        symptomWithOrgansGrid = new Grid<>(SymptomWithOrgans.class);
//        relatedGrid = new Grid<>();
//
//        organWithSymptomsGrid.setColumns("id", "name");
//        symptomWithOrgansGrid.setColumns("id", "name");
//        relatedGrid.addColumn(item -> item).setHeader("Related Items");
//
//        organWithSymptomsGrid.addItemClickListener(event -> {
//            OrganWithSymptoms organWithSymptoms = event.getItem();
//            relatedGrid.setItems(organWithSymptomsService.getSymptomNames(organWithSymptoms));
//        });
//
//        symptomWithOrgansGrid.addItemClickListener(event -> {
//            SymptomWithOrgans symptomWithOrgans = event.getItem();
//            relatedGrid.setItems(symptomWithOrgansService.getOrganNames(symptomWithOrgans));
//        });
//
//        organWithSymptomsGrid.setVisible(false);
//        symptomWithOrgansGrid.setVisible(false);
//        relatedGrid.setVisible(true);
//
//        VerticalLayout gridsLayout = new VerticalLayout(organWithSymptomsGrid, relatedGrid);
//        gridsLayout.setWidth("100%");
//        gridsLayout.setAlignItems(Alignment.CENTER);
//        gridsLayout.setSpacing(true);
//        gridsLayout.setMargin(false);
//
//        add(gridsLayout);
//
//
//        // Create the menu items
//        Button manageOrganRelationsButton = new Button("Manage Organ Relations", e -> {
//            // Add logic to manage organ relations
//        });
//
//        Button manageSymptomRelationsButton = new Button("Manage Symptom Relations", e -> {
//            // Add logic to manage symptom relations
//        });
//
//        Button backButton = new Button("Back", e -> getUI().ifPresent(ui -> ui.navigate(ManageSymptomsComponent.class)));
//
//
//        manageOrganRelationsButton.addClickListener(e -> {
//            organWithSymptomsGrid.setItems(organWithSymptomsService.findAll());
//            organWithSymptomsGrid.setVisible(true);
//            relatedGrid.setVisible(false);
//            symptomWithOrgansGrid.setVisible(false);
//        });
//
//        manageSymptomRelationsButton.addClickListener(e -> {
//            symptomWithOrgansGrid.setItems(symptomWithOrgansService.findAll());
//            organWithSymptomsGrid.setVisible(false);
//            relatedGrid.setVisible(false);
//            symptomWithOrgansGrid.setVisible(true);
//        });
//
//        // Set the width of all menu items to the fixed width
//        manageOrganRelationsButton.setWidth(menuItemWidth + "px");
//        manageSymptomRelationsButton.setWidth(menuItemWidth + "px");
//        backButton.setWidth(menuItemWidth + "px");
//
//        // Create a horizontal layout for the menu items
//        HorizontalLayout menuLayout = new HorizontalLayout(
//                manageOrganRelationsButton,
//                manageSymptomRelationsButton,
//                backButton
//        );
//
//        menuLayout.setAlignItems(Alignment.CENTER);
//        menuLayout.setSpacing(false);
//        menuLayout.setMargin(false);
//        menuLayout.getElement().getStyle().set("margin-left", "auto");
//        menuLayout.getElement().getStyle().set("margin-right", "auto");
//
//        // Set the alignment of the menuLayout to the top of the component
//        setAlignSelf(Alignment.START, menuLayout);
//
//        // Add the menu layout to the top of the component
//        add(menuLayout);
//
//
//        // Add the layout for creating new objects "SymptomWithOrgans" and "OrganWithSymptoms"
//        // Create dialogs, grids, and buttons similar to ManageSymptomsComponent for these new objects
//    }
//
//}
