package com.gsngames.qa.vimana.core.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.gsngames.qa.vimana.core.VimanaConfig;
import com.gsngames.qa.vimana.core.VimanaException;
import com.gsngames.qa.vimana.core.db.PreDataReader;
import com.gsngames.qa.vimana.core.db.testdata.Data;
import com.gsngames.qa.vimana.core.db.testdata.DataSet;
import com.gsngames.qa.vimana.core.db.testdata.Set;
import com.gsngames.qa.vimana.core.db.testdata.TestDataRoot;
import com.gsngames.qa.vimana.core.handler.TestDataHandler;

/**
 * 
 * @author lnr
 * 
 */
public class TestDataHandlerImpl implements TestDataHandler {
    private TestDataRoot testDataRoot;
    private PreDataReader preDataReader;

    public TestDataHandlerImpl() throws VimanaException {
        this.preDataReader = new PreDataReader("testdata.xml", VimanaConfig.getConfigProperty("product"), VimanaConfig.getConfigProperty("intl"));
        Serializer serializer = new Persister();
        String xml = null;
        try {
            xml = preDataReader.getPreProcessedXML();
            xml= xml.replaceAll("<br/>", "\n");
            this.testDataRoot = serializer.read(TestDataRoot.class, xml);
        } catch (Exception e) {
            throw new VimanaException(e.fillInStackTrace());
        }
    }

    @Override
    public Data getData(String dataName) throws VimanaException {
        Data data = this.testDataRoot.getData(dataName);
        if (data != null) {
            return data;
        } else {
            throw new VimanaException("Data under TestData with name'" + dataName + "' not found");
        }
    }

    /**
     * Gets the DataSet which includes enabled sets
     */

    @Override
    public DataSet getDataSet(String dataSetName) throws VimanaException {
        DataSet dataSet = this.testDataRoot.getDataSet(dataSetName);
        List<Set> sets = new ArrayList<Set>();
        if (dataSet != null) {
            for (Set set : dataSet.getSets()) {
                if (!set.isEnable()) {
                    sets.add(set);
                }
            }
            // remove the disabled sets
            for (Set set : sets) {
                dataSet.getSets().remove(set);
            }
            return dataSet;
        } else {
            throw new VimanaException("DataSet under TestData with name'" + dataSetName + "' not found");
        }
    }

    /**
     * Gets the enabled Set
     */

    @Override
    public Set getSet(String dataSetName, String setName) throws VimanaException {
        DataSet dataSet = this.testDataRoot.getDataSet(dataSetName);
        if (dataSet != null) {
            Set set = dataSet.getSet(setName);
            if (set != null) {
                if (set.isEnable()) {
                    return set;
                } else {
                    throw new VimanaException("Set:" + setName + "-->DataSet:" + dataSetName + " is not enabled");
                }

            } else {
                throw new VimanaException("Set:" + setName + "--> DataSet:" + dataSetName + " not found");
            }

        } else {
            throw new VimanaException("Set:" + setName + " --> DataSet:" + dataSetName + " not found");
        }

    }

    @Override
    public Data getSetData(String dataSetName, String setName, String dataName) throws VimanaException {
        DataSet dataSet = this.testDataRoot.getDataSet(dataSetName);

        if (dataSet != null) {
            Set set = dataSet.getSet(setName);
            if (set != null) {

                if (set.isEnable()) {
                    Data data = set.getData(dataName);
                    if (data != null) {
                        return data;
                    } else {
                        throw new VimanaException("Data:" + dataName + "->Set:" + setName + "->DataSet:" + dataSetName + " not found");
                    }
                } else {
                    throw new VimanaException("Set:" + setName + "->DataSet:" + dataSetName + " is not enabled");
                }

            } else {
                throw new VimanaException("Set:" + setName + "->DataSet:" + dataSetName + " not found");
            }

        } else {
            throw new VimanaException("Set:" + setName + "->DataSet:" + dataSetName + " not found");
        }

    }

    @Override
    public Object[][] getDataSets(String dataSetName) throws VimanaException {
        DataSet dataSet = getDataSet(dataSetName);
        Object[][] datas = new Object[dataSet.getSets().size()][1];
        int count = 0;
        for (Set set : dataSet.getSets()) {
            datas[count] = new Object[] { set };
            ++count;
        }
        return datas;
    }
    
	@Override
	public Object[][] getFirstSet(String dataSetName) throws VimanaException {
		DataSet dataSet = getDataSet(dataSetName);
		Object[][] datas = new Object[1][1];
		datas[0] =  new Object[] {dataSet.getSets().get(0)};
		return datas;
	}
}
