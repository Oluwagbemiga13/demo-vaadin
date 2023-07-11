package com.example.demo.ui.tool;

import com.vaadin.flow.component.Html;
import org.springframework.stereotype.Component;

@Component
public class LabelManager {

    Html createLabel(String fontWeight, String fontSize, String displayedText) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<div style='font-weight: ");
        stringBuilder.append(fontWeight);
        stringBuilder.append("; font-size:");
        stringBuilder.append(fontSize);
        stringBuilder.append(";'>");
        stringBuilder.append(displayedText);
        stringBuilder.append("</div>");
        return new Html(stringBuilder.toString());
    }

}
