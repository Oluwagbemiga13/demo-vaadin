package com.example.demo.component;
import com.example.demo.service.OrganService;
import com.example.demo.service.SymptomService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("manage-symptoms")
public class ManageSymptomsComponent extends VerticalLayout {

    @Autowired
    private OrganService organService;

    @Autowired
    private SymptomService symptomService;

    private int menuItemWidth;

    public ManageSymptomsComponent() {
        menuItemWidth = 200;

//        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setMargin(false);
        setPadding(false);

        // Create the menu items
        Button createOrganButton = new Button("Create new Organ", e -> {
            CreateOrganComponent createOrganComponent = new CreateOrganComponent(organService);
            createOrganComponent.open();
        });
        Button createSymptomButton = new Button("Create Symptom", e -> {
            CreateSymptomComponent createOrganComponent = new CreateSymptomComponent(symptomService);
            createOrganComponent.open();
        });
        Button manageRelationsButton = new Button("Manage Relations");
        Button backButton = new Button("Back", e -> getUI().ifPresent(ui -> ui.navigate(WelcomeComponent.class)));

        // Set the width of all menu items to the fixed width
        createOrganButton.setWidth(menuItemWidth + "px");
        createSymptomButton.setWidth(menuItemWidth + "px");
        manageRelationsButton.setWidth(menuItemWidth + "px");
        backButton.setWidth(menuItemWidth + "px");

        // Create a horizontal layout for the menu items
        HorizontalLayout menuLayout = new HorizontalLayout(
                createOrganButton,
                createSymptomButton,
                manageRelationsButton,
                backButton
        );

        menuLayout.setAlignItems(Alignment.CENTER);
        menuLayout.setSpacing(false);
        menuLayout.setMargin(false);
        menuLayout.getElement().getStyle().set("margin-left", "auto");
        menuLayout.getElement().getStyle().set("margin-right", "auto");

        // Set the alignment of the menuLayout to the top of the component
        setAlignSelf(Alignment.START, menuLayout);

        // Add the menu layout to the top of the component
        add(menuLayout);

        // Add the rest of the component content below the menu
        add(new H1("Manage Symptoms Component"));
    }

}