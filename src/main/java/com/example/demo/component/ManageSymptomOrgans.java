package com.example.demo.component;

import com.example.demo.dto.OrganDTO;
import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.OrganSymptom;
import com.example.demo.service.OrganService;
import com.example.demo.service.OrganSymptomService;
import com.example.demo.service.SymptomService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
@Route("manage-symptom-and-organs")
@Slf4j
public class ManageSymptomOrgans extends VerticalLayout{
    @Autowired
    private OrganSymptomService organSymptomService;

    @Autowired
    private OrganService organService;

    @Autowired
    private SymptomService symptomService;

    private int menuItemWidth = 250;

    private ComboBox<SymptomDTO> symptomComboBox;
    private Grid<OrganDTO> attachedOrgans;
    private Grid<OrganDTO> freeOrgans;


    @PostConstruct
    private void init() {
        setMargin(false);
        setPadding(false);

        Button manageOrganRelationsButton = new Button("Manage Organ Relations", e -> {
            getUI().ifPresent(ui -> ui.navigate(ManageOrganSymptoms.class));
        });

        Button backButton = new Button("Back",
                e -> getUI().ifPresent(ui -> ui.navigate(AddSymptomAndOrganComponent.class)));

        manageOrganRelationsButton.setWidth(menuItemWidth + "px");

        backButton.setWidth(menuItemWidth + "px");

        HorizontalLayout menuLayout = new HorizontalLayout(
                manageOrganRelationsButton,
                backButton
        );

        menuLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        menuLayout.setSpacing(false);
        menuLayout.setMargin(false);
        menuLayout.getElement().getStyle().set("margin-left", "auto");
        menuLayout.getElement().getStyle().set("margin-right", "auto");

        add(menuLayout);

        //Label organSelectionLabel = new Label("Select an organ from the list:");
        //organSelectionLabel.getStyle().set("font-size", "30px");


        symptomComboBox = new ComboBox<>("Select an organ");
        symptomComboBox.setWidth("500px");
        symptomComboBox.setItems(symptomService.findAll());
        symptomComboBox.setItemLabelGenerator(SymptomDTO::getName);


        symptomComboBox.addValueChangeListener(event -> {
            SymptomDTO selectedSymptom = event.getValue();
            if (selectedSymptom != null) {
                attachedOrgans.setItems(organSymptomService.findOrgansBySymptomId(selectedSymptom.getId()));
                freeOrgans.setItems(organSymptomService.findOrgansNotMappedToSymptom(selectedSymptom));
            } else {
                attachedOrgans.setItems(Collections.emptyList());
                freeOrgans.setItems(Collections.emptyList());
            }
        });

//        VerticalLayout comboLayout = new VerticalLayout(organSelectionLabel, organComboBox);

        VerticalLayout comboLayout = new VerticalLayout(symptomComboBox);

        add(comboLayout);

        attachedOrgans = new Grid<>(OrganDTO.class);
        freeOrgans = new Grid<>(OrganDTO.class);

        attachedOrgans.setColumns("id","name");
        freeOrgans.setColumns("id","name");


        Label organGridLabel = new Label("Attached:");
        Label symptomGridLabel = new Label("Free:");

        Button removeButton = new Button("Remove");
        removeButton.addClickListener(event -> {
            SymptomDTO selectedSymptom = symptomComboBox.getValue();
            OrganDTO selectedOrgan = attachedOrgans.asSingleSelect().getValue();

            if (selectedSymptom != null && selectedOrgan != null) {
                organSymptomService.deleteRelation(selectedOrgan,selectedSymptom);
                log.info("deleted {}  from {}", selectedOrgan.getName(), selectedSymptom.getName());

                // Refresh the grid to show the updated data
                refreshGrids();
            }
        });

        Button addButton = new Button("Add");
        addButton.addClickListener(event -> {
            SymptomDTO selectedSymptom = symptomComboBox.getValue();
            OrganDTO selectedOrgan = freeOrgans.asSingleSelect().getValue();

            if (selectedSymptom != null && selectedOrgan != null) {
                OrganSymptom organSymptom = organSymptomService.createRelation(selectedOrgan.getId(), selectedSymptom.getId());

                log.info(organSymptom.getOrgan().getName());
                // Refresh the grid to show the updated data
                refreshGrids();
            }
        });

        VerticalLayout leftGrid = new VerticalLayout(organGridLabel, attachedOrgans, removeButton);
        VerticalLayout rightGrid = new VerticalLayout(symptomGridLabel, freeOrgans, addButton);
        leftGrid.setAlignItems(FlexComponent.Alignment.CENTER);
        rightGrid.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout grids = new HorizontalLayout(leftGrid,rightGrid);
        grids.setWidth("100%");
        grids.setSpacing(true);
        grids.setMargin(false);
        add(grids);


//        HorizontalLayout removeLayout = new HorizontalLayout(removeButton);
//        removeLayout.setAlignItems(Alignment.CENTER);
//        removeLayout.setWidth("500px");
//        removeLayout.setAlignSelf(Alignment.CENTER, removeButton);


//        HorizontalLayout addLayout = new HorizontalLayout(addButton);
//        addLayout.setAlignItems(Alignment.CENTER);
//        addLayout.setWidth("500px");
//        addLayout.setAlignSelf(Alignment.CENTER, addButton);

//        HorizontalLayout manipulateLayout = new HorizontalLayout(removeLayout,addLayout);
//        manipulateLayout.setAlignItems(Alignment.CENTER);
//
//        add(manipulateLayout);


        // Set the alignment of the menuLayout to the top of the component
        setAlignSelf(FlexComponent.Alignment.CENTER, menuLayout);

    }

    private void refreshGrids() {
        SymptomDTO selectedSymptom = symptomComboBox.getValue();
        attachedOrgans.setItems(organSymptomService.findOrgansBySymptomId(selectedSymptom.getId()));
        freeOrgans.setItems(organSymptomService.findOrgansNotMappedToSymptom(selectedSymptom));
    }
}
