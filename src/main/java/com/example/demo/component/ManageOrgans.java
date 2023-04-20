package com.example.demo.component;

import com.example.demo.dto.OrganDTO;
import com.example.demo.dto.SymptomDTO;
import com.example.demo.service.OrganService;
import com.example.demo.service.SymptomService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Route("manage-organs")
@Slf4j
@RequiredArgsConstructor
public class ManageOrgans extends VerticalLayout{

    private final OrganService organService;
    private final SymptomService symptomService;

    private String menuItemWidth = "300px";

    private String editButtonsWidth = "300px";

    private String menuButtonsHeight = "75px";

    private Grid<OrganDTO> organGrid;

    private Button createOrganButton;
    private Button createSymptomButton;
    private Button manageRelationsButton;
    private Button backButton;
    private Button deleteSymptomButton;
    private Button deleteOrganButton;


    @PostConstruct
    private void init() {
        organGrid = new Grid<>(OrganDTO.class);

        organGrid.setVisible(true);

        setMargin(false);
        setPadding(false);

        // Create the menu items

        Html organLabel = new Html("<div style='font-weight: bold; font-size: 45px;'>Organs</div>");

        organGrid.setColumns("name"); // Adjust the column names according to your Organ entity


        organGrid.setWidth("100%");


        createOrganButton = new Button("Create new Organ", e -> {
            CreateOrganDialog createOrganDialog = new CreateOrganDialog(organService);
            createOrganDialog.open();
            createOrganDialog.addOpenedChangeListener(event -> {
                if (!event.isOpened()) {
                    refreshGrids();
                }
            });
        });
        createOrganButton.setWidth(editButtonsWidth);

        deleteOrganButton = new Button("Delete selected Organ", e -> {
            OrganDTO selectedOrgan = organGrid.asSingleSelect().getValue();
            if (selectedOrgan != null) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog("Are you sure you want to delete this Organ?", () -> {
                    organService.delete(selectedOrgan.getId());
                    Notification.show("Organ deleted successfully.");
                    refreshGrids();
                    organGrid.asSingleSelect().clear();
                });
                confirmationDialog.open();
            }
        });

        deleteOrganButton.setWidth(editButtonsWidth);

        manageRelationsButton = new Button("Manage Relations", e -> getUI().ifPresent(ui -> ui.navigate(ManageOrganSymptoms.class)));
        manageRelationsButton.setHeight(menuButtonsHeight);
        manageRelationsButton.setWidth(menuItemWidth);

        backButton = new Button("Back", e -> getUI().ifPresent(ui -> ui.navigate(WelcomeComponent.class)));
        backButton.setHeight(menuButtonsHeight);
        backButton.setWidth(menuItemWidth);

        // Create a horizontal layout for the menu items
        VerticalLayout menuLayout = new VerticalLayout(
                createOrganButton, deleteOrganButton,
                manageRelationsButton,
                backButton
        );

        menuLayout.setAlignItems(FlexComponent.Alignment.START);


        VerticalLayout leftGrid = new VerticalLayout(organLabel, organGrid);
        leftGrid.setAlignItems(FlexComponent.Alignment.CENTER);

        VerticalLayout rightOptions = new VerticalLayout(menuLayout);
        rightOptions.setAlignItems(FlexComponent.Alignment.START);


        HorizontalLayout gridsLayout = new HorizontalLayout(/*organLayout,*/ leftGrid, rightOptions);
        gridsLayout.setWidth("80%");
        gridsLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        gridsLayout.setSpacing(true);
        gridsLayout.setMargin(false);

        // Add the grids layout to the component
        add(gridsLayout);

        // Set the alignment of the deleteButtonsLayout to the top of the component
        setAlignSelf(FlexComponent.Alignment.CENTER, menuLayout, gridsLayout);

        refreshGrids();

    }


    private void refreshGrids() {
        List<OrganDTO> organs = organService.findAll();


        log.info("Fetched Organs: {}", organs);


        organGrid.setItems(organs);



        log.info("OrganGrid Items: {}", organGrid.getDataProvider().size(new Query<>()));

    }

}
