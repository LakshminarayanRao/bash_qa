package com.gsngames.qa.vimana.core.handler.impl;

import java.util.HashMap;
import java.util.Map;

import com.gsngames.qa.vimana.core.VimanaException;
import com.gsngames.qa.vimana.core.db.testdata.Data;
import com.gsngames.qa.vimana.core.db.testdata.DataSet;
import com.gsngames.qa.vimana.core.db.testdata.Set;
import com.gsngames.qa.vimana.core.handler.TestDataHandler;


public class JavaTestDataHandlerImpl implements TestDataHandler {
   Map<String,Data> datas = new HashMap<String, Data>();

   public JavaTestDataHandlerImpl(){
       Data d1 =new Data();
       d1.setName("d1");
       d1.setValue("D1 VALUE");
       
       Data d2 =new Data();
       d2.setName("d2");
       d2.setValue("D2 VALUE");
       
       datas.put("d1",d1);
       datas.put("d2",d1);
   }
    @Override
    public Data getData(String dataName) throws VimanaException {
        return datas.get(dataName);
    }

    @Override
    public DataSet getDataSet(String dataSetName) throws VimanaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set getSet(String dataSetName, String setName) throws VimanaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Data getSetData(String dataSetName, String setName, String dataName) throws VimanaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object[][] getDataSets(String dataSetName) throws VimanaException {
        // TODO Auto-generated method stub
        return null;
    }
    
	@Override
	public Object[][] getFirstSet(String dataSetName) throws VimanaException {
		DataSet dataSet = getDataSet(dataSetName);
		Object[][] datas = new Object[1][1];
		datas[0] =  new Object[] {dataSet.getSets().get(0)};
		return datas;
	}

}
