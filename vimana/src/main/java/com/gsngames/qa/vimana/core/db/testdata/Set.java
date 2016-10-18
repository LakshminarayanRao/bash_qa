package com.gsngames.qa.vimana.core.db.testdata;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class Set {
    @Attribute(required = true)
    private String name;

    @Attribute(required = true)
    private boolean enable;

    @ElementList(name = "data", inline = true, required = true)
    private List<Data> datas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * Get the data value based on the data name
     * @param name
     * @return
     */
    public Data getData(String name) {
        Data resultData = null;
        for (Data data : this.datas) {
            if (data.getName().trim().equalsIgnoreCase(name)) {
                resultData = data;
                break;
            }
        }
        return resultData;
    }
    
    @Override
    public String toString() {
        String tab="    ";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tab+"Set Name:"+name+"\n");
        for(Data data: getDatas()){
            stringBuilder.append(data.toString());
        }
        return stringBuilder.toString();
    }
}
