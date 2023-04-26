package com.example.demo.component.tool;

import com.vaadin.flow.component.grid.Grid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GridInitializer {

    public <T> Grid<T> createSingleGrid(Class<T> dtoClass, List<T> dtoList, String[] displayedAttributes) {
        Grid<T> grid = new Grid<>(dtoClass);
        grid.setItems(dtoList);
        grid.setColumns(displayedAttributes);
        return grid;
    }
}
