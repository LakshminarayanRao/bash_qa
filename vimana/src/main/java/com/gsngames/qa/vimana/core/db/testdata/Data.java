package com.gsngames.qa.vimana.core.db.testdata;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

public class Data {
    @Attribute(required=true)
    private String name;

    @Text(required = false)
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        String tab="      ";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tab+"Data Name:"+name+" Value:"+value+"\n");
        return stringBuilder.toString();
    }

    
    


}
