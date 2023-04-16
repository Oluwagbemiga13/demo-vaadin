package com.example.demo.component;

import com.example.demo.entity.Organ;
import com.example.demo.entity.OrganSymptom;
import com.example.demo.entity.Symptom;
import com.example.demo.service.OrganService;
import com.example.demo.service.OrganSymptomService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

@Route("manage-organs-and-symptoms")
@Slf4j
public class ManageOrgansAndSymptomsComponent extends VerticalLayout {

    private final OrganSymptomService organSymptomService;

    private final OrganService organService;

    private int menuItemWidth;

    private ComboBox<Organ> organComboBox;
    private Grid<Symptom> attachedSymptoms;
    private Grid<Symptom> freeSymptoms;

    @Autowired
    public ManageOrgansAndSymptomsComponent(OrganService organService, OrganSymptomService organSymptomService) {
        this.organSymptomService = organSymptomService;
        this.organService = organService;
        menuItemWidth = 250;

        init();
    }

    private void init() {
        setMargin(false);
        setPadding(false);

        Button manageOrganRelationsButton = new Button("Manage Organ Relations", e -> {
            // Add logic to manage organ relations
        });

        Button manageSymptomRelationsButton = new Button("Manage Symptom Relations", e -> {
            // Add logic to manage symptom relations
        });

        Button backButton = new Button("Back", e -> getUI().ifPresent(ui -> ui.navigate(ManageSymptomsComponent.class)));

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

        organComboBox = new ComboBox<>("Select an organ");
        organComboBox.setItems(organService.findAll());
        organComboBox.setItemLabelGenerator(Organ::getName);



        organComboBox.addValueChangeListener(event -> {
            Organ selectedOrgan = event.getValue();
            if (selectedOrgan != null) {
               attachedSymptoms.setItems(organSymptomService.findSymptomsByOrganId(selectedOrgan.getId()));
                freeSymptoms.setItems(organSymptomService.findSymptomsNotMappedToOrgan(selectedOrgan));
            } else {
                attachedSymptoms.setItems(Collections.emptyList());
                freeSymptoms.setItems(Collections.emptyList());
            }
        });

        attachedSymptoms = new Grid<>(Symptom.class);
        freeSymptoms = new Grid<>(Symptom.class);

        attachedSymptoms.setColumns("id", "name");
        freeSymptoms.setColumns("id", "name");


        Label organSelectionLabel = new Label("Select an organ from the list:");
        Label organGridLabel = new Label("Attached:");
        Label symptomGridLabel = new Label("Free:");


        VerticalLayout gridLayout = new VerticalLayout(organSelectionLabel, organComboBox, organGridLabel, attachedSymptoms, symptomGridLabel, freeSymptoms);
        gridLayout.setWidth("100%");
        gridLayout.setAlignItems(Alignment.CENTER);
        gridLayout.setSpacing(true);
        gridLayout.setMargin(false);

        add(gridLayout);

        // Set the alignment of the menuLayout to the top of the component
        setAlignSelf(Alignment.START, menuLayout);

    }
}
