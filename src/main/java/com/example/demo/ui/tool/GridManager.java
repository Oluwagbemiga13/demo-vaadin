package com.example.demo.ui.tool;

import com.example.demo.ui.pages.CreateSymptomDialog;
import com.example.demo.service.EntityService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@org.springframework.stereotype.Component
public class GridManager {

    public <T> Grid<T> createLonelyGrid(Class<T> dtoClass, List<T> dtoList, String[] displayedAttributes) {
        Grid<T> grid = new Grid<>(dtoClass);
        grid.setItems(dtoList);
        grid.setColumns(displayedAttributes);
        return grid;
    }

    public <T> VerticalLayout createGrid_CreateButton(Class<T> dtoClass, List<T> dtoList,
                                                      String[] displayedAttributes,
                                                      EntityService entityService){

        VerticalLayout layout = new VerticalLayout();
        Grid grid = createLonelyGrid(dtoClass,dtoList,displayedAttributes);
        layout.add(grid);

        Button button = new Button("Create Symptom", e -> {
            CreateSymptomDialog createSymptomDialog = new CreateSymptomDialog(entityService);
            createSymptomDialog.open();
            createSymptomDialog.addOpenedChangeListener(event -> {
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
    private void refreshGrid(Grid grid, EntityService service) {

        grid.setItems(service.findAll());
        log.debug("Fetched Symptoms: {}", service.findAll());
    }

//    private Grid exposeGrid()
}
