package com.example.demo.ui.tool;

import com.example.demo.ui.dialogs.ConfirmationDialog;
import org.springframework.stereotype.Component;

@Component
public class DialogManager {

    public ConfirmationDialog createConfirmation(String message, Runnable onConfirm){
        return new ConfirmationDialog(message, onConfirm);
    }


}
