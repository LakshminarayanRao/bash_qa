package com.gsngames.qa.vimana.core.db.testdata;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="testdata")
public class TestData {

    @ElementList(name = "data", inline = true, required = false)
    private List<Data> datas;

    @ElementList(name = "dataset", inline = true, required = false)
    private List<DataSet> dataSets;

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public List<DataSet> getDataSets() {
        return dataSets;
    }

    public void setDataSets(List<DataSet> dataSets) {
        this.dataSets = dataSets;
    }

    public DataSet getDataSet(String name) {
        DataSet resultDataSet = null;
        for (DataSet dataSet : this.dataSets) {
            if (dataSet.getName().trim().equalsIgnoreCase(name)) {
                resultDataSet = dataSet;
                break;
            }
        }
        return resultDataSet;
    }

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
        StringBuilder stringBuilder = new StringBuilder();
        for(DataSet dataSet: getDataSets()){
            stringBuilder.append(dataSet.toString());
        }
        return stringBuilder.toString();
    }
    

}
