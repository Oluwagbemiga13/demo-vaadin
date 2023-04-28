package com.example.demo.component.tool;

import com.example.demo.component.CreateSymptomDialog;
import com.example.demo.service.EntityService;
import com.example.demo.service.JoinService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class GridInitializer {

    public <T> Grid<T> createLonelyGrid(Class<T> dtoClass, List<T> dtoList, String[] displayedAttributes) {
        Grid<T> grid = new Grid<>(dtoClass);
        grid.setItems(dtoList);
        grid.setColumns(displayedAttributes);
        return grid;
    }

    public <T> VerticalLayout createGrid_DeleteButton(Class<T> dtoClass, List<T> dtoList,
                                                      String[] displayedAttributes,
                                                      EntityService entityService){

        VerticalLayout layout = new VerticalLayout();
        layout.add(createLonelyGrid(dtoClass,dtoList,displayedAttributes));

        Button button = new Button("Create Symptom", e -> {
            CreateSymptomDialog createSymptomDialog = new CreateSymptomDialog(entityService);
            createSymptomDialog.open();
            createSymptomDialog.addOpenedChangeListener(event -> {
                if (!event.isOpened()) {
                   log.info("PRINT SOMETHING!");
                }
            });
        });

        layout.add(button);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setAlignSelf(FlexComponent.Alignment.CENTER);

        return layout;

    }
}
