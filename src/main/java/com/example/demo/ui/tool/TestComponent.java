package com.example.demo.ui.tool;

import com.example.demo.ui.dialogs.EntityCreationDialog;
import com.example.demo.ui.pages.ManageOrgans;
import com.example.demo.ui.pages.ManageParts;
import com.example.demo.ui.pages.ManageSymptoms;
import com.example.demo.dto.OrganDTO;
import com.example.demo.service.OrganService;
import com.example.demo.service.SymptomService;
import com.example.demo.ui.pages.WelcomeComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


@Route("/api/test")
@RequiredArgsConstructor
@Slf4j
public class TestComponent extends VerticalLayout {

    private final MenuInitializer menuInitializer;

    private final GridManager gridManager;

    private final ButtonInitializer buttonInitializer;

    private final SymptomService symptomService;

    private final OrganService organService;

    private final ComponentBuilder componentBuilder;

    final String MENU_BUTTON_WIDTH = "200px";


    @PostConstruct
    public void init(){

        //add(initMenu());

        //add(initGrid());

        //add(initButton());

//        add(initGrid_2());
//
//        add(initGrid_3());

        add(initGrid_4());



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
        return gridManager.createLonelyGrid(organService, new String[]{"name"});
    }


    public Button initButton(){

       Runnable action = () -> {
            EntityCreationDialog entityCreationDialog = new EntityCreationDialog(symptomService);
            entityCreationDialog.open();
            entityCreationDialog.addOpenedChangeListener(event -> {
                if (!event.isOpened()) {
                }
            });
        };
        return buttonInitializer.createActButton("123",action, "120px");
    }

    public VerticalLayout initGrid_2(){

        return gridManager.createGrid_CreateButton(new String[]{"name"}, organService);
    }

    public VerticalLayout initGrid_3(){

        return gridManager.createGrid_CreateButton(new String[]{"name"}, symptomService);
    }

    public HorizontalLayout initGrid_4(){
//        return gridManager.createGrid_sideMenu(new String[]{"name"}, symptomService);
        return componentBuilder.grid_menu2(symptomService, this, WelcomeComponent.class);
    }


}
