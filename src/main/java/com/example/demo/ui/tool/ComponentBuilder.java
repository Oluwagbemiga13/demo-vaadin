package com.example.demo.ui.tool;

import com.example.demo.dto.DTO;
import com.example.demo.service.EntityService;
import com.example.demo.service.SymptomService;
import com.example.demo.ui.dialogs.ConfirmationDialog;
import com.example.demo.ui.dialogs.EntityCreationDialog;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ComponentBuilder {

    private final SymptomService symptomService;

    private final GridManager gridManager;

    private final MenuInitializer menuInitializer;

    private final ButtonInitializer buttonInitializer;

    private final DialogManager dialogManager;

    private final LabelManager labelManager;

    private String MENU_BUTTON_WIGHT = "300px";

    public HorizontalLayout grid_menu2(EntityService entityService, com.vaadin.flow.component.Component currentUI, Class backDestination){

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
}
