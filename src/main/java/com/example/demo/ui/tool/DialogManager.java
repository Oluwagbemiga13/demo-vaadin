package com.example.demo.ui.tool;

import com.example.demo.service.simple.EntityService;
import com.example.demo.ui.dialogs.ConfirmationDialog;
import com.example.demo.ui.dialogs.EntityCreationDialog;
import org.springframework.stereotype.Component;

@Component
public class DialogManager {

    public ConfirmationDialog createConfirmation(String message, Runnable onConfirm) {
        return new ConfirmationDialog(message, onConfirm);
    }

    public EntityCreationDialog createEntityDialog(EntityService managingService) {
        return new EntityCreationDialog(managingService);
    }


}
