package com.example.demo.component;

import com.example.demo.dto.OrganDTO;
import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.Organ;
import com.example.demo.entity.OrganSymptom;
import com.example.demo.entity.Symptom;
import com.example.demo.service.OrganService;
import com.example.demo.service.OrganSymptomService;
import com.example.demo.service.SymptomService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@Route("manage-organs-and-symptoms")
@Slf4j
public class ManageOrgansAndSymptomsComponent extends VerticalLayout {

    @Autowired
    private OrganSymptomService organSymptomService;

    @Autowired
    private OrganService organService;

    @Autowired
    private SymptomService symptomService;

    private int menuItemWidth = 250;

    private ComboBox<OrganDTO> organComboBox;
    private Grid<SymptomDTO> attachedSymptoms;
    private Grid<SymptomDTO> freeSymptoms;

//    @Autowired
//    public ManageOrgansAndSymptomsComponent(OrganService organService, OrganSymptomService organSymptomService) {
//        this.organSymptomService = organSymptomService;
//        this.organService = organService;
//        menuItemWidth = 250;
//
//        init();
//    }

    @PostConstruct
    private void init() {
        setMargin(false);
        setPadding(false);

        Button manageOrganRelationsButton = new Button("Manage Organ Relations", e -> {
            // Add logic to manage organ relations
        });

        Button manageSymptomRelationsButton = new Button("Manage Symptom Relations", e -> {
            // Add logic to manage symptom relations
        });

        Button backButton = new Button("Back",
                e -> getUI().ifPresent(ui -> ui.navigate(ManageSymptomsComponent.class)));

        manageOrganRelationsButton.setWidth(menuItemWidth + "px");
        manageSymptomRelationsButton.setWidth(menuItemWidth + "px");
        backButton.setWidth(menuItemWidth + "px");

        HorizontalLayout menuLayout = new HorizontalLayout(
                manageOrganRelationsButton,
                manageSymptomRelationsButton,
                backButton
        );

        menuLayout.setAlignItems(Alignment.CENTER);
        menuLayout.setSpacing(false);
        menuLayout.setMargin(false);
        menuLayout.getElement().getStyle().set("margin-left", "auto");
        menuLayout.getElement().getStyle().set("margin-right", "auto");

        add(menuLayout);

        //Label organSelectionLabel = new Label("Select an organ from the list:");
        //organSelectionLabel.getStyle().set("font-size", "30px");


        organComboBox = new ComboBox<>("Select an organ");
        organComboBox.setWidth("500px");
        organComboBox.setItems(organService.findAll());
        organComboBox.setItemLabelGenerator(OrganDTO::getName);


        organComboBox.addValueChangeListener(event -> {
            OrganDTO selectedOrgan = event.getValue();
            if (selectedOrgan != null) {
                attachedSymptoms.setItems(organSymptomService.findSymptomsByOrganId(selectedOrgan.getId()));
                freeSymptoms.setItems(organSymptomService.findSymptomsNotMappedToOrgan(selectedOrgan));
            } else {
                attachedSymptoms.setItems(Collections.emptyList());
                freeSymptoms.setItems(Collections.emptyList());
            }
        });

//        VerticalLayout comboLayout = new VerticalLayout(organSelectionLabel, organComboBox);

        VerticalLayout comboLayout = new VerticalLayout(organComboBox);

        add(comboLayout);

        attachedSymptoms = new Grid<>(SymptomDTO.class);
        freeSymptoms = new Grid<>(SymptomDTO.class);

        attachedSymptoms.setColumns("id","name");
        freeSymptoms.setColumns("id","name");


        Label organGridLabel = new Label("Attached:");
        Label symptomGridLabel = new Label("Free:");

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

        VerticalLayout leftGrid = new VerticalLayout(organGridLabel, attachedSymptoms, removeButton);
        VerticalLayout rightGrid = new VerticalLayout(symptomGridLabel, freeSymptoms, addButton);
        leftGrid.setAlignItems(Alignment.CENTER);
        rightGrid.setAlignItems(Alignment.CENTER);

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
        setAlignSelf(Alignment.CENTER, menuLayout);

    }

    private void refreshGrids() {
        OrganDTO selectedOrgan = organComboBox.getValue();
        attachedSymptoms.setItems(organSymptomService.findSymptomsByOrganId(selectedOrgan.getId()));
        freeSymptoms.setItems(organSymptomService.findSymptomsNotMappedToOrgan(selectedOrgan));
    }
}
