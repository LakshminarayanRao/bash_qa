package com.gsngames.qa.vimana.core.db.view;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class ViewRoot {

    @ElementList(name = "views", inline = true, required = true)
    private List<Views> views;

    public List<Views> getViews() {
        return views;
    }

    public void setViews(List<Views> views) {
        this.views = views;
    }

    public UIElement getUIElement(String viewName, String elementName) {
        for (Views views : getViews()) {
            for (View view : views.getViews()) {
                if (view.getName().trim().equalsIgnoreCase(viewName.trim())) {
                    for (UIElement uiElement : view.getUiElements()) {
                        if (uiElement.getName().trim().equalsIgnoreCase(elementName.trim())) {
                            return uiElement;
                        }
                    }
                }
            }
        }

        return null;
    }

}
