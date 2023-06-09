package com.example.demo.ui.tool;

import com.example.demo.dto.DTO;
import com.example.demo.service.JoinService;
import com.example.demo.ui.dialogs.ConfirmationDialog;
import com.example.demo.ui.dialogs.EntityCreationDialog;
import com.example.demo.service.EntityService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
@org.springframework.stereotype.Component
@RequiredArgsConstructor
public class GridManager {

    String MENU_BUTTON_WIDTH = "300px";

    private final ButtonInitializer buttonInitializer;

    public <T> Grid<T> createLonelyGrid(EntityService entityService, String[] displayedAttributes) {

        Grid<T> grid = new Grid<>(entityService.getDTOClass());
        grid.setItems(entityService.findAll());
        grid.setColumns(displayedAttributes);
        return grid;
    }

    public <T> VerticalLayout createGrid_CreateButton(String[] displayedAttributes,
                                                      EntityService entityService){

        VerticalLayout layout = new VerticalLayout();

        Grid grid = createLonelyGrid(entityService,displayedAttributes);
        layout.add(grid);

        Button button = new Button("Create " + entityService.getEntityName(), e -> {
            EntityCreationDialog entityCreationDialog = new EntityCreationDialog(entityService);
            entityCreationDialog.open();
            entityCreationDialog.addOpenedChangeListener(event -> {
                if (!event.isOpened()) {
                    refreshGrid(grid, entityService);
                   log.info("PRINT SOMETHING!");
                }
            });
        });


        layout.add(button);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setAlignSelf(FlexComponent.Alignment.CENTER);

        return layout;
    }

//    public Grid<DTO> freeEntitiesGrid(JoinService joinService, EntityService entityService){
//
//    }



    public void refreshGrid(Grid grid, EntityService service) {

        grid.setItems(service.findAll());
        log.debug("Fetched Symptoms: {}", service.findAll());
    }

    public void refreshGrid(Grid grid, List<DTO> dtos) {
        grid.setItems(dtos);

    }

//    private Grid exposeGrid()
}
