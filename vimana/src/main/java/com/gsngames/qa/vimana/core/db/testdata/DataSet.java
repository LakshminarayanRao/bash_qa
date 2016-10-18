package com.gsngames.qa.vimana.core.db.testdata;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "dataset")
public class DataSet {

    @Attribute
    private String name;

    @ElementList(name = "set", inline = true, required = true)
    private List<Set> sets;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }

    /**
     * Gets the Set based on the name
     * @param name
     * @return
     */
    public Set getSet(String name) {
        Set resultSet = null;
        for (Set set : this.sets) {
            if (set.getName().trim().equalsIgnoreCase(name)) {
                resultSet = set;
                break;
            }
        }
        return resultSet;
    }

    @Override
    public String toString() {
        String tab="  ";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tab+"DataSet Name:"+name+"\n");
        for(Set set: getSets()){
            stringBuilder.append(set.toString());
        }
        return stringBuilder.toString();
    }
    
    

}
