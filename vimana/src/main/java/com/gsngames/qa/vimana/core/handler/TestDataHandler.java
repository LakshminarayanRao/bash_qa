package com.gsngames.qa.vimana.core.handler;


import com.gsngames.qa.vimana.core.VimanaException;
import com.gsngames.qa.vimana.core.db.testdata.Data;
import com.gsngames.qa.vimana.core.db.testdata.DataSet;
import com.gsngames.qa.vimana.core.db.testdata.Set;

/**
 * 
 * @author lnr
 *
 */
public interface TestDataHandler {

    public Data getData(String dataName) throws VimanaException;
    
    public DataSet getDataSet(String dataSetName) throws VimanaException;
    
    public Set getSet(String dataSetName,String setName) throws VimanaException;

    public Data getSetData(String dataSetName,String setName,String dataName) throws VimanaException;

    public Object[][] getDataSets(String dataSetName) throws VimanaException;
    public Object[][] getFirstSet(String dataSetName) throws VimanaException;

}
