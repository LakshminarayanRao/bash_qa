package com.gsngames.qa.vimana.core.db.view;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "uielement")
public class UIElement {

    @Attribute(required = true)
    private String name;

    @Attribute(required = false)
    private String action;
    

    @ElementList(inline = true, name = "findElement", required = false)
    List<FindElement> findElements;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<FindElement> getFindElements() {
        return findElements;
    }

    public void setFindElements(List<FindElement> findElements) {
        this.findElements = findElements;
    }
    
    

}
