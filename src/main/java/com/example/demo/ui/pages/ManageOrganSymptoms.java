package com.example.demo.ui.pages;

import com.example.demo.dto.OrganDTO;
import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.OrganSymptom;
import com.example.demo.service.OrganService;
import com.example.demo.service.OrganSymptomService;
import com.example.demo.service.SymptomService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

@Route("manage-organs-and-symptoms")
@Slf4j
public class ManageOrganSymptoms extends VerticalLayout {

    @Autowired
    private OrganSymptomService organSymptomService;

    @Autowired
    private OrganService organService;

    @Autowired
    private SymptomService symptomService;


    private ComboBox<OrganDTO> organComboBox;
    private Grid<SymptomDTO> attachedSymptoms;
    private Grid<SymptomDTO> freeSymptoms;

    private String editButtonsWidth = "250px";
    private String menuItemWidth = "250px";
    private String menuButtonsHeight = "75px";

    @PostConstruct
    private void init() {
        setMargin(false);
        setPadding(false);

        Button manageSymptomRelationsButton = new Button("Manage Symptom Relations", e -> {
            getUI().ifPresent(ui -> ui.navigate(SymptomRelations.class));
        });

        manageSymptomRelationsButton.setHeight(menuButtonsHeight);
        manageSymptomRelationsButton.setWidth(menuItemWidth);

        Button backButton = new Button("Back",
                e -> getUI().ifPresent(ui -> ui.navigate(ManageSymptoms.class)));


        backButton.setHeight(menuButtonsHeight);
        backButton.setWidth(menuItemWidth);

        HorizontalLayout menuLayout = new HorizontalLayout(
                manageSymptomRelationsButton,
                backButton
        );

        menuLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        menuLayout.setSpacing(true);
        menuLayout.setMargin(false);
        menuLayout.getElement().getStyle().set("margin-left", "auto");
        menuLayout.getElement().getStyle().set("margin-right", "auto");

        organComboBox = new ComboBox<>("Select an organ");
        organComboBox.setWidth("510px");
        organComboBox.setItems(organService.findAll());
        organComboBox.setItemLabelGenerator(OrganDTO::getName);


        organComboBox.addValueChangeListener(event -> {
            OrganDTO selectedOrgan = event.getValue();
            if (selectedOrgan != null) {
                attachedSymptoms.setItems(organSymptomService.findSecondsByFirstId(selectedOrgan.getId()));
                freeSymptoms.setItems(organSymptomService.findSecondNotMappedToFirst(selectedOrgan));
            } else {
                attachedSymptoms.setItems(Collections.emptyList());
                freeSymptoms.setItems(Collections.emptyList());
            }
        });

        VerticalLayout wholeMenuLayout = new VerticalLayout(menuLayout, organComboBox);
        wholeMenuLayout.setSpacing(true);
        wholeMenuLayout.setAlignItems(Alignment.CENTER);

        add(wholeMenuLayout);

        //Label organSelectionLabel = new Label("Select an organ from the list:");
        //organSelectionLabel.getStyle().set("font-size", "30px");


        attachedSymptoms = new Grid<>(SymptomDTO.class);
        freeSymptoms = new Grid<>(SymptomDTO.class);

        attachedSymptoms.setColumns("name");
        freeSymptoms.setColumns("name");

        Html attachedGridLabel = new Html("<div style='font-weight: bold; font-size: 25px; color: gray;'>Attached</div>");
        Html freeGridLabel = new Html("<div style='font-weight: bold; font-size: 25px; color: gray;'>Free</div>");


        Button removeButton = new Button("Remove");
        removeButton.addClickListener(event -> {
            OrganDTO selectedOrgan = organComboBox.getValue();
            SymptomDTO selectedSymptom = attachedSymptoms.asSingleSelect().getValue();

            if (selectedOrgan != null && selectedSymptom != null) {
                organSymptomService.deleteRelation(selectedOrgan, selectedSymptom);
                log.info("deleted {}  from {}", selectedSymptom.getName(), selectedOrgan.getName());

                // Refresh the grid to show the updated data
                refreshGrids();
            }
        });
        removeButton.setWidth(editButtonsWidth);

        Button addButton = new Button("Add");
        addButton.addClickListener(event -> {
            OrganDTO selectedOrgan = organComboBox.getValue();
            SymptomDTO selectedSymptom = freeSymptoms.asSingleSelect().getValue();

            if (selectedOrgan != null && selectedSymptom != null) {
                OrganSymptom organSymptom = organSymptomService.createRelation(selectedOrgan.getId(), selectedSymptom.getId());

                // Refresh the grid to show the updated data
                refreshGrids();
            }
        });
        addButton.setWidth(editButtonsWidth);

        VerticalLayout leftGrid = new VerticalLayout(attachedGridLabel, attachedSymptoms, removeButton);
        VerticalLayout rightGrid = new VerticalLayout(freeGridLabel, freeSymptoms, addButton);
        leftGrid.setAlignItems(Alignment.CENTER);
        rightGrid.setAlignItems(Alignment.CENTER);

        HorizontalLayout grids = new HorizontalLayout(leftGrid, rightGrid);
        grids.setWidth("80%");
        grids.setSpacing(true);
        grids.setMargin(false);
        grids.setAlignSelf(Alignment.CENTER);
        add(grids);

        // Set the alignment of the menuLayout to the top of the component
        setAlignSelf(Alignment.CENTER, menuLayout, grids);

    }

    private void refreshGrids() {
        OrganDTO selectedOrgan = organComboBox.getValue();
        attachedSymptoms.setItems(organSymptomService.findSecondsByFirstId(selectedOrgan.getId()));
        freeSymptoms.setItems(organSymptomService.findSecondNotMappedToFirst(selectedOrgan));
    }
}
