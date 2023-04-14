package com.example.demo;

import com.example.demo.controller.HelloWorld;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("hello") // Route to access this view at http://localhost:8080/hello
public class HelloController extends VerticalLayout implements HelloWorld {

    public HelloController() {
        setSizeFull(); // Set the layout size to 100% width and height
        setJustifyContentMode(JustifyContentMode.CENTER); // Center content vertically
        setAlignItems(Alignment.CENTER); // Center content horizontally

        Button button = new Button("Click me",
                e -> Notification.show(sayHello())); // Show notification on button click
        add(button); // Add button to the layout
    }

    @Override
    public String sayHello() {
        return "Hello, World!";
    }
}
