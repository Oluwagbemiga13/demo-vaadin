package com.example.demo.ui.tool;

import com.example.demo.dto.DTO;
import com.example.demo.service.EntityService;
import com.example.demo.service.JoinService;
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

    public HorizontalLayout simple_entity_grid_options (EntityService entityService, com.vaadin.flow.component.Component currentUI, Class backDestination){

        Html symptomLabel = labelManager.createLabel("bold", "25px", entityService.getEntityName()+"s");

        Grid<DTO> grid = gridManager.createLonelyGrid(entityService,new String[]{"name"});

        VerticalLayout gridLayout = new VerticalLayout(symptomLabel,grid);
        gridLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        gridLayout.setWidth("80%");

        String entityName = entityService.getEntityName();

        Button createButton = buttonInitializer.createActButton("Create "+ entityName, () -> {
            EntityCreationDialog entityCreationDialog = dialogManager.createEntityDialog(entityService);
            entityCreationDialog.open();
            entityCreationDialog.addOpenedChangeListener(event -> {
                if (!event.isOpened()) {
                    gridManager.refreshGrid(grid,entityService);
                }
            });
        }, MENU_BUTTON_WIGHT);


        Button deleteButton = buttonInitializer.createActButton("Delete "+entityName, () -> {
            Optional<DTO> dtoOptional = grid.asSingleSelect().getOptionalValue();
            if(dtoOptional.isPresent())
            {
                ConfirmationDialog confirmationDialog = dialogManager.createConfirmation("Do you want to delete "+ dtoOptional.get().getName(),
                        () ->{
                            entityService.delete(dtoOptional.get().getId());
                            gridManager.refreshGrid(grid,entityService);
                            log.info("Deleted ", dtoOptional.get().getName());
                        });
                confirmationDialog.open();
            }
        }, "300px");

        Button backButton = buttonInitializer.createNavButton("Back", currentUI, backDestination, MENU_BUTTON_WIGHT);

//        gridLayout.add(deleteButton);

        VerticalLayout menuLayout = menuInitializer.createVerticalMenu(List.of(createButton, deleteButton, backButton));
        menuLayout.setAlignItems(FlexComponent.Alignment.START);

        HorizontalLayout layout = new HorizontalLayout(gridLayout,menuLayout);
        //layout.setAlignItems(FlexComponent.Alignment.CENTER);
        //layout.setAlignSelf(FlexComponent.Alignment.CENTER,gridLayout,menuLayout);

        //THIS NEEDS TO BE ALWAYS SET (MISSING LAYOUT ISSUE)
        layout.setWidth("100%");

        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        //layout.setSpacing(true);

        return layout;
    }




    public VerticalLayout create_attached_entities_option(JoinService joinService, EntityService entityService, ComboBox comboBox,
                                                            Grid<DTO> attachedEntities, Grid<DTO> freeEntities, String gridSize){

        attachedEntities.setWidth(gridSize);
        AtomicReference<DTO> selectedDTO = new AtomicReference<>();

        comboBox.addValueChangeListener(event -> {
            DTO selectedEntity = (DTO) event.getValue();
            selectedDTO.set(selectedEntity);

            if (selectedEntity != null) {
                attachedEntities.setItems(joinService.findSecondsByFirstId(selectedEntity.getId()));
                freeEntities.setItems(joinService.findSecondNotMappedToFirst(selectedEntity.getId()));

            } else {
                attachedEntities.setItems(Collections.emptyList());
            }
        });

        Button removeButton = buttonInitializer.createActButton("Remove", () -> {
            if(selectedDTO.get() != null && attachedEntities.asSingleSelect() != null){
                joinService.deleteRelation(selectedDTO.get(), attachedEntities.asSingleSelect().getValue());
                log.info(selectedDTO.get().getName() + " was removed from" + attachedEntities.asSingleSelect().getValue().getName());
                gridManager.refreshGrid(attachedEntities, joinService.findSecondsByFirstId(selectedDTO.get().getId()));
                gridManager.refreshGrid(freeEntities, joinService.findSecondNotMappedToFirst(selectedDTO.get().getId()));
            }
            else throw new IllegalArgumentException("Something was not selected.");
        }, MENU_BUTTON_WIGHT);

        VerticalLayout attachedLayout = new VerticalLayout(attachedEntities,removeButton);
        attachedLayout.setSpacing(true);
        attachedLayout.setMargin(true);

        attachedLayout.setAlignItems(FlexComponent.Alignment.CENTER);


        VerticalLayout layout = new VerticalLayout(attachedLayout);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        return layout;


    }

    public VerticalLayout create_free_entities_option(JoinService joinService, EntityService entityService, ComboBox comboBox, Grid<DTO> freeEntities,
                                                        Grid<DTO> attachedEntities, String gridSize){

        freeEntities.setWidth(gridSize);
        AtomicReference<DTO> selectedDTO = new AtomicReference<>();

        comboBox.addValueChangeListener(event -> {
            DTO selectedEntity = (DTO) event.getValue();
            selectedDTO.set(selectedEntity);

            if (selectedEntity != null) {
                freeEntities.setItems(joinService.findSecondNotMappedToFirst(selectedEntity.getId()));
                attachedEntities.setItems(joinService.findSecondsByFirstId(selectedEntity.getId()));

            } else {
                freeEntities.setItems(Collections.emptyList());
            }
        });

        Button addButton = buttonInitializer.createActButton("Add", () -> {
            if(selectedDTO.get() != null && freeEntities.asSingleSelect() != null){
                joinService.createRelation(selectedDTO.get().getId(), freeEntities.asSingleSelect().getValue().getId());
                log.info(selectedDTO.get().getName() + " was connected to" + freeEntities.asSingleSelect().getValue().getName());
                gridManager.refreshGrid(attachedEntities, joinService.findSecondsByFirstId(selectedDTO.get().getId()));
                gridManager.refreshGrid(freeEntities, joinService.findSecondNotMappedToFirst(selectedDTO.get().getId()));
            }
            else throw new IllegalArgumentException("Something was not selected.");
        }, MENU_BUTTON_WIGHT);

        VerticalLayout freeLayout = new VerticalLayout(freeEntities,addButton);
        freeLayout.setSpacing(true);
        freeLayout.setMargin(true);

        freeLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        VerticalLayout layout = new VerticalLayout(freeLayout);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        layout.setSpacing(true);
        layout.setMargin(true);

        return layout;
    }

    public HorizontalLayout create_managing_relation_layout(JoinService joinService, EntityService entityService, ComboBox comboBox,
                                                            Grid<DTO> freeEntities, Grid<DTO> attachedEntities, String gridSize){
        HorizontalLayout layout = new HorizontalLayout(create_attached_entities_option(joinService,entityService,comboBox,attachedEntities,freeEntities, gridSize),
                create_free_entities_option(joinService,entityService,comboBox,freeEntities,attachedEntities, gridSize));

        layout.setAlignSelf(FlexComponent.Alignment.CENTER);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setWidth(gridSize);


        layout.setSpacing(true);
        layout.setMargin(true);
        return layout;

    }
}
