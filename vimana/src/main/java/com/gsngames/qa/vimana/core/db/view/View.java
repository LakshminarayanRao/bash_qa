package com.gsngames.qa.vimana.core.db.view;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class View {
    @Attribute(required = true)
    private String name;

    @ElementList(name = "uielement", inline = true, required = true)
    private List<UIElement> uiElements;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UIElement> getUiElements() {
        return uiElements;
    }

    public void setUiElements(List<UIElement> uiElements) {
        this.uiElements = uiElements;
    }
    
    public UIElement getUIElement(String name){
        for(UIElement uiElement: uiElements){
            if(uiElement.getName().equalsIgnoreCase(name)){
                return uiElement;
            }
        }
        return null;
    }

}
