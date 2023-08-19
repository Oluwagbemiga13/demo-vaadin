package com.example.demo.ui.pages.create_alert;

import com.example.demo.dto.join.JoinItemDTO;
import com.example.demo.dto.simple.DTO;
import com.example.demo.dto.simple.OrganDTO;
import com.example.demo.dto.simple.PartDTO;
import com.example.demo.dto.simple.SymptomDTO;
import com.example.demo.mapper.SymptomPartMapper;
import com.example.demo.service.join.PartOrganService;
import com.example.demo.service.join.SymptomPartService;
import com.example.demo.service.simple.OrganService;
import com.example.demo.service.simple.PartService;
import com.example.demo.service.simple.SymptomService;
import com.example.demo.ui.tool.GridManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Route("create-alert")
@Slf4j
public class CreateAlert extends VerticalLayout {

    private RadioButtonGroup<String> searchOptions = new RadioButtonGroup<>();
    private ComboBox<DTO> searchBox = new ComboBox("Pick Name");
    private Button searchButton = new Button("Search");

    private Button addAlertButton = new Button("Attach Alert");
    private Grid<JoinItemDTO> resultsGrid;

    @Autowired
    private OrganService organService;

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private PartService bodyPartService;

    @Autowired
    private SymptomPartMapper symptomPartMapper;

    @Autowired
    private SymptomPartService symptomPartService;

    @Autowired
    private PartOrganService partOrganService;

    @Autowired
    private GridManager gridManager;

    public CreateAlert() {
        setupLayout();
    }

    private void setupLayout() {
        searchOptions.setItems("Organ Name", "Symptom Name", "Body Part Name");
        searchOptions.setLabel("Search by:");
        searchOptions.addValueChangeListener(event -> {
            String selectedValue = event.getValue();
            if ("Organ Name".equals(selectedValue)) {
                searchBox.setItems(getDTOsFromOrgans(organService.findAll()));
            } else if ("Symptom Name".equals(selectedValue)) {
                searchBox.setItems(getDTOsFromSymptoms(symptomService.findAll()));
            } else if ("Body Part Name".equals(selectedValue)) {
                searchBox.setItems(getDTOsFromParts(bodyPartService.findAll()));
            }
        });

        searchBox.setItemLabelGenerator(DTO::getName);

        searchButton.addClickListener(event -> {

            List<JoinItemDTO> items = filterList(concatLists(symptomPartService.getAll(), partOrganService.getAll()),
                    searchBox.getValue());

            if (resultsGrid == null) {
                resultsGrid = gridManager.createLonelyGrid(items, new String[]{"name"});
                add(resultsGrid);
                resultsGrid.addItemClickListener(selectedEvent -> add(addAlertButton));

                addAlertButton.addClickListener(buttonClickEvent -> {
                    JoinItemDTO selectedDto = resultsGrid.asSingleSelect().getValue();
                    log.info("DTO : {}", selectedDto.toString());
                });
            }
            else {
                gridManager.refreshGrid(resultsGrid,items);
            }
        });



        add(searchOptions, searchBox, searchButton);
    }

    private List<DTO> getDTOsFromOrgans(List<OrganDTO> organs) {
        return organs.stream().map(DTO.class::cast).toList();
    }

    private List<DTO> getDTOsFromParts(List<PartDTO> parts) {
        return parts.stream().map(DTO.class::cast).toList();
    }

    private List<DTO> getDTOsFromSymptoms(List<SymptomDTO> symptoms) {
        return symptoms.stream().map(DTO.class::cast).toList();
    }

    private List<JoinItemDTO> concatLists(List<? extends JoinItemDTO> first, List<? extends JoinItemDTO> second) {
        List<JoinItemDTO> concatenatedList = new ArrayList<>(first);
        concatenatedList.addAll(second);
        return concatenatedList;
    }

    private List<JoinItemDTO> filterList(List<JoinItemDTO> list, DTO selectedItem) {
        return list.stream()
                .filter(l -> l.getFullName().contains(selectedItem.getName()))
                .filter(distinctByKey(JoinItemDTO::getFullName))
                .toList();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
