package com.example.demo.ui.tool;

import com.example.demo.dto.simple.DTO;
import com.example.demo.service.join.JoinService;
import com.example.demo.service.simple.EntityService;
import com.example.demo.ui.dialogs.ConfirmationDialog;
import com.example.demo.ui.dialogs.EntityCreationDialog;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
@Slf4j
public class ComponentBuilder {

    private final GridManager gridManager;

    private final MenuInitializer menuInitializer;

    private final ButtonInitializer buttonInitializer;

    private final DialogManager dialogManager;

    private final LabelManager labelManager;

    private final ComboBoxManager comboBoxManager;

    //private final GenericMapper genericMapper;

    private String MENU_BUTTON_WIGHT = "300px";

    public HorizontalLayout simple_entity_grid_options(EntityService entityService, com.vaadin.flow.component.Component currentUI, Class backDestination, Class manageDestination) {

        Html symptomLabel = labelManager.createLabel("bold", "25px", entityService.getEntityName() + "s");

        Grid<DTO> grid = gridManager.createLonelyGrid(entityService, new String[]{"name"});

        VerticalLayout gridLayout = new VerticalLayout(symptomLabel, grid);
        gridLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        gridLayout.setWidth("80%");

        String entityName = entityService.getEntityName();

        Button createButton = buttonInitializer.createActButton("Create " + entityName, () -> {
            EntityCreationDialog entityCreationDialog = dialogManager.createEntityDialog(entityService);
            entityCreationDialog.open();
            entityCreationDialog.addOpenedChangeListener(event -> {
                if (!event.isOpened()) {
                    gridManager.refreshGrid(grid, entityService);
                }
            });
        }, MENU_BUTTON_WIGHT);


        Button deleteButton = buttonInitializer.createActButton("Delete " + entityName, () -> {
            Optional<DTO> dtoOptional = grid.asSingleSelect().getOptionalValue();
            if (dtoOptional.isPresent()) {
                ConfirmationDialog confirmationDialog = dialogManager.createConfirmation("Do you want to delete " + dtoOptional.get().getName(),
                        () -> {
                            entityService.delete(dtoOptional.get().getId());
                            gridManager.refreshGrid(grid, entityService);
                            log.info("Deleted ", dtoOptional.get().getName());
                        });
                confirmationDialog.open();
            }
        }, "300px");

        Button backButton = buttonInitializer.createNavButton("Back", currentUI, backDestination, MENU_BUTTON_WIGHT);

        Button manageButton = buttonInitializer.createNavButton("Manage relations", currentUI, manageDestination, MENU_BUTTON_WIGHT);

//        gridLayout.add(deleteButton);

        VerticalLayout menuLayout = menuInitializer.createVerticalMenu(List.of(createButton, deleteButton, backButton, manageButton));
        menuLayout.setAlignItems(FlexComponent.Alignment.START);

        HorizontalLayout layout = new HorizontalLayout(gridLayout, menuLayout);
        //layout.setAlignItems(FlexComponent.Alignment.CENTER);
        //layout.setAlignSelf(FlexComponent.Alignment.CENTER,gridLayout,menuLayout);

        //THIS NEEDS TO BE ALWAYS SET (MISSING LAYOUT ISSUE)
        layout.setWidth("100%");

        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        //layout.setSpacing(true);

        return layout;
    }


    public VerticalLayout create_attached_entities_option(JoinService joinService, int dtoIndex, EntityService entityService, ComboBox comboBox,
                                                          Grid<DTO> attachedEntities, Grid<DTO> freeEntities, String gridSize) {

        attachedEntities.setWidth(gridSize);
        AtomicReference<DTO> selectedDTO = new AtomicReference<>();

        comboBox.addValueChangeListener(event -> {
            DTO selectedEntity = (DTO) event.getValue();
            selectedDTO.set(selectedEntity);

            if (selectedEntity != null) {
                if (dtoIndex > 1) {
                    throw new IllegalArgumentException("Dto index bigger than 1");
                } else {
                    if (dtoIndex == 0) {
                        attachedEntities.setItems(joinService.findSecondsByFirstId(selectedEntity.getId()));
                        freeEntities.setItems(joinService.findSecondNotMappedToFirst(selectedEntity));
                    }
                    if (dtoIndex == 1) {
                        attachedEntities.setItems(joinService.findFirstsBySecondId(selectedEntity.getId()));
                        freeEntities.setItems(joinService.findFirstNotMappedToSecond(selectedEntity));
                    }
                }

            } else {
                attachedEntities.setItems(Collections.emptyList());
            }
        });

        Button removeButton = buttonInitializer.createActButton("Remove", () -> {
            if (selectedDTO.get() != null && attachedEntities.asSingleSelect() != null) {

                log.info(selectedDTO.get().getName() + " was removed from" + attachedEntities.asSingleSelect().getValue().getName());
                if (dtoIndex > 1) {
                    throw new IllegalArgumentException("Dto index bigger than 1");
                } else {
                    if (dtoIndex == 0) {
                        joinService.deleteRelation(selectedDTO.get(), attachedEntities.asSingleSelect().getValue());
                        attachedEntities.setItems(joinService.findSecondsByFirstId(selectedDTO.get().getId()));
                        freeEntities.setItems(joinService.findSecondNotMappedToFirst(selectedDTO.get()));
                    }
                    if (dtoIndex == 1) {
                        joinService.deleteRelation(attachedEntities.asSingleSelect().getValue(), selectedDTO.get());
                        attachedEntities.setItems(joinService.findFirstsBySecondId(selectedDTO.get().getId()));
                        freeEntities.setItems(joinService.findFirstNotMappedToSecond(selectedDTO.get()));
                    }
                }
                // gridManager.refreshGrid(attachedEntities, joinService.findSecondsByFirstId(selectedDTO.get().getId()));
                // gridManager.refreshGrid(freeEntities, joinService.findSecondNotMappedToFirst(selectedDTO.get()));
            } else throw new IllegalArgumentException("Something was not selected.");
        }, MENU_BUTTON_WIGHT);

        Html attachedGridLabel = new Html("<div style='font-weight: bold; font-size: 25px; color: gray;'>Attached</div>");

        VerticalLayout attachedLayout = new VerticalLayout(attachedGridLabel, attachedEntities, removeButton);
        attachedLayout.setSpacing(true);
        attachedLayout.setMargin(true);

        attachedLayout.setAlignItems(FlexComponent.Alignment.CENTER);


        VerticalLayout layout = new VerticalLayout(attachedLayout);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        return layout;


    }

    public VerticalLayout create_free_entities_option(JoinService joinService, int dtoIndex, EntityService entityService, ComboBox comboBox, Grid<DTO> freeEntities,
                                                      Grid<DTO> attachedEntities, String gridSize) {

        freeEntities.setWidth(gridSize);
        AtomicReference<DTO> selectedDTO = new AtomicReference<>();

        comboBox.addValueChangeListener(event -> {
            DTO selectedEntity = (DTO) event.getValue();
            selectedDTO.set(selectedEntity);

            if (selectedEntity != null) {
                if (dtoIndex > 1) {
                    throw new IllegalArgumentException("Dto position bigger than 1");
                } else {
                    if (dtoIndex == 0) {
                        attachedEntities.setItems(joinService.findSecondsByFirstId(selectedEntity.getId()));
                        freeEntities.setItems(joinService.findSecondNotMappedToFirst(selectedEntity));
                    }
                    if (dtoIndex == 1) {
                        attachedEntities.setItems(joinService.findFirstsBySecondId(selectedEntity.getId()));
                        freeEntities.setItems(joinService.findFirstNotMappedToSecond(selectedEntity));
                    }
                }

            } else {
                freeEntities.setItems(Collections.emptyList());
            }
        });

        Button addButton = buttonInitializer.createActButton("Add", () -> {
            if (selectedDTO.get() != null && freeEntities.asSingleSelect() != null) {

                log.info(selectedDTO.get().getName() + " was connected to" + freeEntities.asSingleSelect().getValue().getName());
                if (dtoIndex > 1) {
                    throw new IllegalArgumentException("Dto index bigger than 1");
                } else {
                    if (dtoIndex == 0) {
                        joinService.createRelation(selectedDTO.get().getId(), freeEntities.asSingleSelect().getValue().getId());
                        attachedEntities.setItems(joinService.findSecondsByFirstId(selectedDTO.get().getId()));
                        freeEntities.setItems(joinService.findSecondNotMappedToFirst(selectedDTO.get()));
                    }
                    if (dtoIndex == 1) {
                        joinService.createRelation(freeEntities.asSingleSelect().getValue().getId(), selectedDTO.get().getId());
                        attachedEntities.setItems(joinService.findFirstsBySecondId(selectedDTO.get().getId()));
                        freeEntities.setItems(joinService.findFirstNotMappedToSecond(selectedDTO.get()));
                    }
                }

//                gridManager.refreshGrid(attachedEntities, joinService.findSecondsByFirstId(selectedDTO.get().getId()));
//                gridManager.refreshGrid(freeEntities, joinService.findSecondNotMappedToFirst(selectedDTO.get()));
            } else throw new IllegalArgumentException("Something was not selected.");
        }, MENU_BUTTON_WIGHT);

        Html freeGridLabel = new Html("<div style='font-weight: bold; font-size: 25px; color: gray;'>Free</div>");

        VerticalLayout freeLayout = new VerticalLayout(freeGridLabel, freeEntities, addButton);
        freeLayout.setSpacing(true);
        freeLayout.setMargin(true);

        freeLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        VerticalLayout layout = new VerticalLayout(freeLayout);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        layout.setSpacing(true);
        layout.setMargin(true);

        return layout;
    }

    public VerticalLayout create_managing_relation_layout(JoinService joinService, int dtoIndex, EntityService entityService, ComboBox comboBox,
                                                          Grid<DTO> freeEntities, Grid<DTO> attachedEntities, String gridSize) {

        String html = "<div style='font-weight: bold; font-size: 25px;'>" + entityService.getEntityName() + "s</div>";
        Html label = new Html(html);

        HorizontalLayout gridLayout = new HorizontalLayout(
                create_attached_entities_option(joinService, dtoIndex, entityService, comboBox, attachedEntities, freeEntities, gridSize),
                create_free_entities_option(joinService, dtoIndex, entityService, comboBox, freeEntities, attachedEntities, gridSize)
        );

        gridLayout.setAlignSelf(FlexComponent.Alignment.CENTER);
        gridLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        gridLayout.setWidth(gridSize);

        VerticalLayout layout = new VerticalLayout(label, gridLayout);
        layout.setAlignSelf(FlexComponent.Alignment.CENTER);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

//        HorizontalLayout finalLayout = new HorizontalLayout(layout);


        gridLayout.setSpacing(false);
        gridLayout.setMargin(false);
        return layout;

    }
}
