package com.gsngames.qa.vimana.core.db.testdata;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="testdataRoot")
public class TestDataRoot {
	
	@ElementList(name = "testdata", inline = true, required = false)
	private List<TestData> testDatas;

	public List<TestData> getTestDatas() {
		return testDatas;
	}

	public void setTestDatas(List<TestData> testDatas) {
		this.testDatas = testDatas;
	}

	 
	 public DataSet getDataSet(String name) {
	        DataSet resultDataSet = null;
	        for(TestData testData: this.testDatas){
	        	for (DataSet dataSet : testData.getDataSets()) {
		            if (dataSet.getName().trim().equalsIgnoreCase(name)) {
		                resultDataSet = dataSet;
		                break;
		            }
		        }	
	        }
	        return resultDataSet;
	    }

	    public Data getData(String name) {
	        Data resultData = null;

	        try{
		        for(TestData testData: this.testDatas){
		        	for (Data data : testData.getDatas()) {
			            if (data.getName().trim().equalsIgnoreCase(name)) {
			                resultData = data;
			                return resultData;
			            }
			        }	
		        }
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        
	        return resultData;
	    }
}
