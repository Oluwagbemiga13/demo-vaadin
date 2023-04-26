package com.example.demo.component.tool;

import com.example.demo.component.ManageOrgans;
import com.example.demo.component.ManageParts;
import com.example.demo.component.ManageSymptoms;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;


@Route("/api/test")
@RequiredArgsConstructor
public class TestComponent extends VerticalLayout {

    private final MenuInitializer menuInitializer;
    final String MENU_BUTTON_WIDTH = "200px";


    @PostConstruct
    public void init(){

        add(initMenu());
    }

    public VerticalLayout initMenu(){
        Button manageSymptoms = menuInitializer.createNavButton("Manage Symptoms", this, ManageSymptoms.class, MENU_BUTTON_WIDTH);
        Button manageOrgans = menuInitializer.createNavButton("Manage Organs",this, ManageOrgans.class, MENU_BUTTON_WIDTH);
        Button manageBodyParts = menuInitializer.createNavButton("Manage Body",this, ManageParts.class, MENU_BUTTON_WIDTH);
        Button manageLogs = menuInitializer.createNavButton("Manage Logs",this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button questionnaires = menuInitializer.createNavButton("Questionnaires",this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button notes = menuInitializer.createNavButton("Notes",this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button settings = menuInitializer.createNavButton("Settings",this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button helpAndSupport = menuInitializer.createNavButton("Help & Support",this, TestComponent.class, MENU_BUTTON_WIDTH);
        Button about = menuInitializer.createNavButton("About",this, TestComponent.class, MENU_BUTTON_WIDTH);

        List<Button> buttons = Arrays.asList(manageSymptoms, manageOrgans, manageBodyParts, manageLogs, questionnaires, notes, settings, helpAndSupport, about);

        return menuInitializer.createVerticalMenu(buttons);
    }


}
