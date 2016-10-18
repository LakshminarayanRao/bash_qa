package com.gsngames.qa.vimana.core.db.view;

import java.util.List;

import org.simpleframework.xml.ElementList;

public class Views {

    @ElementList(name = "view", inline = true, required = true)
    private List<View> views;

    public List<View> getViews() {
        return views;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }
    

}
