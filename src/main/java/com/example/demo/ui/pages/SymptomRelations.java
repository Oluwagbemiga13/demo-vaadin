package com.example.demo.ui.pages;

import com.example.demo.dto.DTO;
import com.example.demo.dto.OrganDTO;
import com.example.demo.dto.PartDTO;
import com.example.demo.dto.SymptomDTO;
import com.example.demo.entity.OrganSymptom;
import com.example.demo.entity.SymptomPart;
import com.example.demo.service.*;
import com.example.demo.ui.tool.ButtonInitializer;
import com.example.demo.ui.tool.ComboBoxManager;
import com.example.demo.ui.tool.ComponentBuilder;
import com.example.demo.ui.tool.MenuInitializer;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Route("symptom-relations")
@Slf4j
@RequiredArgsConstructor
public class SymptomRelations extends VerticalLayout {

    private final OrganSymptomService organSymptomService;

    private final SymptomPartService symptomPartService;
    private final OrganService organService;
    private final SymptomService symptomService;
    private final PartService partService;

    private final PartOrganService partOrganService;
    private final ComboBoxManager comboBoxManager;

    private final MenuInitializer menuInitializer;

    private final ButtonInitializer buttonInitializer;

    private final ComponentBuilder componentBuilder;

    Html attachedLabel_1 = new Html("<div style='font-weight: bold; font-size: 25px; color: gray;'>Attached</div>");
    Html freeLabel_1 = new Html("<div style='font-weight: bold; font-size: 25px; color: gray;'>Free</div>");

    Html attachedLabel_2 = new Html("<div style='font-weight: bold; font-size: 25px; color: gray;'>Attached</div>");
    Html freeLabel_2 = new Html("<div style='font-weight: bold; font-size: 25px; color: gray;'>Free</div>");


    private ComboBox<SymptomDTO> symptomComboBox;

    private Grid<OrganDTO> attachedOrgans;
    private Grid<OrganDTO> freeOrgans;
    private Grid<PartDTO> attachedParts;
    private Grid<PartDTO> freeParts;

    private String editButtonsWidth = "250px";
    private String menuButtonsHeight = "75px";
    private String menuItemWidth = "250px";

    public VerticalLayout fillWithContent() {
        Button backButton = buttonInitializer.createNavButton("Back", this, ManageSymptoms.class,menuItemWidth);

        ComboBox<DTO> combo = comboBoxManager.getComboBox(symptomService);
        Grid<DTO> freeEntitiesGrid = new Grid<>(DTO.class);
        Grid<DTO> attachedEntities = new Grid<>(DTO.class);
        Grid<DTO> freeEntitiesGrid_2 = new Grid<>(DTO.class);
        Grid<DTO> attachedEntities_2 = new Grid<>(DTO.class);
        VerticalLayout layout = new VerticalLayout(backButton, combo,
                componentBuilder.create_managing_relation_layout(organSymptomService,1, organService, combo,
                        freeEntitiesGrid, attachedEntities, "100%"),
                componentBuilder.create_managing_relation_layout(symptomPartService,0, partService, combo,
                        freeEntitiesGrid_2, attachedEntities_2, "100%"));

        layout.setAlignItems(Alignment.CENTER);
        layout.setAlignSelf(Alignment.CENTER);
        layout.setSpacing(true);
        layout.setMargin(true);
        return layout;
    }


    @PostConstruct
    private void init() {
//        setMargin(false);
//        setPadding(false);
//
//        Button manageOrganRelationsButton = new Button("Manage Organ Relations", e -> {
//            getUI().ifPresent(ui -> ui.navigate(OrganRelations.class));
//        });
//
//        manageOrganRelationsButton.setWidth(menuItemWidth);
//        manageOrganRelationsButton.setHeight(menuButtonsHeight);
//
//        Button backButton = new Button("Back",
//                e -> getUI().ifPresent(ui -> ui.navigate(ManageSymptoms.class)));
//
//        backButton.setHeight(menuButtonsHeight);
//        backButton.setWidth(menuItemWidth);
//
//        symptomComboBox = new ComboBox<>("Select an symptom");
//        symptomComboBox.setWidth("510px");
//        symptomComboBox.setItems(symptomService.findAll());
//        symptomComboBox.setItemLabelGenerator(SymptomDTO::getName);
//
//        symptomComboBox.addValueChangeListener(event -> {
//            SymptomDTO selectedSymptom = event.getValue();
//            if (selectedSymptom != null) {
//                attachedOrgans.setItems(organSymptomService.findFirstsBySecondId(selectedSymptom.getId()));
//                freeOrgans.setItems(organSymptomService.findFirstNotMappedToSecond(selectedSymptom));
//                attachedParts.setItems(partService.findAllPartsBySymptomId(selectedSymptom.getId()));
//                freeParts.setItems(partService.findPartsNotMappedToSymptom(selectedSymptom));
//            } else {
//                attachedOrgans.setItems(Collections.emptyList());
//                freeOrgans.setItems(Collections.emptyList());
//                attachedParts.setItems(Collections.emptyList());
//                freeParts.setItems(Collections.emptyList());
//            }
//        });
//
//        HorizontalLayout menuLayout = new HorizontalLayout(
//                manageOrganRelationsButton,
//                backButton
//        );
//
//        menuLayout.setAlignItems(FlexComponent.Alignment.CENTER);
//        menuLayout.setSpacing(true);
//        menuLayout.setMargin(false);
//        menuLayout.getElement().getStyle().set("margin-left", "auto");
//        menuLayout.getElement().getStyle().set("margin-right", "auto");
//
//        VerticalLayout wholeMenuLayout = new VerticalLayout(menuLayout, symptomComboBox);
//        wholeMenuLayout.setSpacing(true);
//        wholeMenuLayout.setAlignItems(Alignment.CENTER);
//
//        add(wholeMenuLayout);
//
//        attachedOrgans = new Grid<>(OrganDTO.class);
//        freeOrgans = new Grid<>(OrganDTO.class);
//
//        attachedOrgans.setColumns("name");
//        freeOrgans.setColumns("name");
//
//        Button removeOrganButton = new Button("Remove");
//        removeOrganButton.addClickListener(event -> {
//            SymptomDTO selectedSymptom = symptomComboBox.getValue();
//            OrganDTO selectedOrgan = attachedOrgans.asSingleSelect().getValue();
//
//            if (selectedSymptom != null && selectedOrgan != null) {
//                organSymptomService.deleteRelation(selectedOrgan, selectedSymptom);
//                log.info("deleted {}  from {}", selectedOrgan.getName(), selectedSymptom.getName());
//
//                // Refresh the grid to show the updated data
//                refreshGrids();
//            }
//        });
//
//        removeOrganButton.setWidth(editButtonsWidth);
//
//        Button addOrganButton = new Button("Add");
//        addOrganButton.addClickListener(event -> {
//            SymptomDTO selectedSymptom = symptomComboBox.getValue();
//            OrganDTO selectedOrgan = freeOrgans.asSingleSelect().getValue();
//
//            if (selectedSymptom != null && selectedOrgan != null) {
//                OrganSymptom organSymptom = organSymptomService.createRelation(selectedOrgan.getId(), selectedSymptom.getId());
//
//                log.info(organSymptom.getOrgan().getName());
//                // Refresh the grid to show the updated data
//                refreshGrids();
//            }
//        });
//
//        addOrganButton.setWidth(editButtonsWidth);
//
//        VerticalLayout attachedOrganGrid = new VerticalLayout(attachedLabel_1, attachedOrgans, removeOrganButton);
//        VerticalLayout freeOrganGrid = new VerticalLayout(freeLabel_1, freeOrgans, addOrganButton);
//        attachedOrganGrid.setAlignItems(FlexComponent.Alignment.CENTER);
//        freeOrganGrid.setAlignItems(FlexComponent.Alignment.CENTER);
//
//        HorizontalLayout grids = new HorizontalLayout(attachedOrganGrid, freeOrganGrid);
//        grids.setWidth("80%");
//        grids.setSpacing(true);
//        grids.setMargin(false);
//        grids.setAlignSelf(Alignment.CENTER);
//
//        Html organLabel = new Html("<div style='font-weight: bold; font-size: 35px; color: black;'>Organ</div>");
//
//        VerticalLayout organContainer = new VerticalLayout(organLabel, grids);
//        organContainer.setAlignItems(Alignment.CENTER);
//        organContainer.setAlignSelf(Alignment.CENTER);
//
//        add(organContainer);
//
//        //TODO: ROZDĚLIT !!!
//
//        attachedParts = new Grid<>(PartDTO.class);
//        freeParts = new Grid<>(PartDTO.class);
//
//        attachedParts.setColumns("name");
//        freeParts.setColumns("name");
//
//        Button removePartButton = new Button("Remove");
//        removePartButton.addClickListener(event -> {
//            SymptomDTO selectedSymptom = symptomComboBox.getValue();
//            PartDTO selectedPart = attachedParts.asSingleSelect().getValue();
//
//            if (selectedSymptom != null && selectedPart != null) {
//                partService.deleteRelation(selectedPart, selectedSymptom);
//                log.info("deleted {} from {}", selectedPart.getName(), selectedSymptom.getName());
//
//                // Refresh the grid to show the updated data
//                refreshGrids();
//            }
//        });
//
//        removePartButton.setWidth(editButtonsWidth);
//
//        Button addPartButton = new Button("Add");
//        addPartButton.addClickListener(event -> {
//            SymptomDTO selectedSymptom = symptomComboBox.getValue();
//            PartDTO selectedPart = freeParts.asSingleSelect().getValue();
//
//            if (selectedSymptom != null && selectedPart != null) {
//                SymptomPart symptomPart = partService.createRelation(selectedPart.getId(), selectedSymptom.getId());
//
//                log.info("Created relation between {} and  {}", selectedPart.getName(), selectedSymptom.getName());
//                // Refresh the grid to show the updated data
//                refreshGrids();
//            }
//        });
//
//        addPartButton.setWidth(editButtonsWidth);
//
//        VerticalLayout leftPartGrid = new VerticalLayout(attachedLabel_2, attachedParts, removePartButton);
//        VerticalLayout rightPartGrid = new VerticalLayout(freeLabel_2, freeParts, addPartButton);
//        leftPartGrid.setAlignItems(FlexComponent.Alignment.CENTER);
//        rightPartGrid.setAlignItems(FlexComponent.Alignment.CENTER);
//
//        HorizontalLayout partGrids = new HorizontalLayout(leftPartGrid, rightPartGrid);
//        partGrids.setWidth("80%");
//        partGrids.setSpacing(true);
//        partGrids.setMargin(false);
//        partGrids.setAlignSelf(Alignment.CENTER);
//
//
//        // Set the alignment of the menuLayout to the top of the component
//        setAlignSelf(FlexComponent.Alignment.CENTER, menuLayout, organContainer);
//
//        Html partLabel = new Html("<div style='font-weight: bold; font-size: 35px; color: black;'>Part</div>");
//
//        VerticalLayout partContainer = new VerticalLayout(partLabel, partGrids);
//        partContainer.setAlignItems(Alignment.CENTER);
//        partContainer.setAlignSelf(Alignment.CENTER);
//
//        add(partContainer);
//
//        refreshGrids();

        add(fillWithContent());
    }

    private void refreshGrids() {
        SymptomDTO selectedSymptom = symptomComboBox.getValue();
        if (selectedSymptom != null) {

            attachedOrgans.setItems(organSymptomService.findFirstsBySecondId(selectedSymptom.getId()));
            freeOrgans.setItems(organSymptomService.findFirstNotMappedToSecond(selectedSymptom));

            attachedParts.setItems(partService.findAllPartsBySymptomId(selectedSymptom.getId()));
            freeParts.setItems(partService.findPartsNotMappedToSymptom(selectedSymptom));
        }


    }
}
