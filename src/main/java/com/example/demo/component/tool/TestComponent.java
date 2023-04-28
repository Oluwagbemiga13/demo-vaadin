package com.example.demo.component.tool;

import com.example.demo.component.CreateSymptomDialog;
import com.example.demo.component.ManageOrgans;
import com.example.demo.component.ManageParts;
import com.example.demo.component.ManageSymptoms;
import com.example.demo.dto.OrganDTO;
import com.example.demo.service.OrganService;
import com.example.demo.service.SymptomService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.text.BreakIterator;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


@Route("/api/test")
@RequiredArgsConstructor
public class TestComponent extends VerticalLayout {

    private final MenuInitializer menuInitializer;

    private final GridInitializer gridInitializer;

    private final ButtonInitializer buttonInitializer;

    private final SymptomService symptomService;

    private final OrganService organService;
    final String MENU_BUTTON_WIDTH = "200px";


    @PostConstruct
    public void init(){

        //add(initMenu());

        //add(initGrid());

        add(initButton());


    }

    public VerticalLayout initMenu(){
        Button manageSymptoms = buttonInitializer.createNavButton("Manage Symptoms", this, ManageSymptoms.class, MENU_BUTTON_WIDTH);
        Button manageOrgans = buttonInitializer.createNavButton("Manage Organs",this, ManageOrgans.class, MENU_BUTTON_WIDTH);
        Button manageBodyParts = buttonInitializer.createNavButton("Manage Body",this, ManageParts.class, MENU_BUTTON_WIDTH);
        Button manageLogs = buttonInitializer.createNavButton("Manage Logs",this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button questionnaires = buttonInitializer.createNavButton("Questionnaires",this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button notes = buttonInitializer.createNavButton("Notes",this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button settings = buttonInitializer.createNavButton("Settings",this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button helpAndSupport = buttonInitializer.createNavButton("Help & Support",this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button about = buttonInitializer.createNavButton("About",this, TestComponent.class, MENU_BUTTON_WIDTH);

        List<Component> buttons = Arrays.asList(manageSymptoms, manageOrgans, manageBodyParts, manageLogs, questionnaires, notes, settings, helpAndSupport, about);

        return menuInitializer.createVerticalMenu(buttons);
    }

    public Grid<OrganDTO> initGrid(){
        return gridInitializer.createSingleGrid(OrganDTO.class,organService.findAll(), new String[]{"name"});
    }


    public Button initButton(){

        Consumer<Button> action = button -> {
            CreateSymptomDialog createSymptomDialog = new CreateSymptomDialog(symptomService);
            createSymptomDialog.open();
            createSymptomDialog.addOpenedChangeListener(event -> {
                if (!event.isOpened()) {
                }
            });
        };
        return buttonInitializer.createActButton("123",action, "120px");
    }


}
