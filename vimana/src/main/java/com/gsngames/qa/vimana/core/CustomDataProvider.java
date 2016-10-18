package com.gsngames.qa.vimana.core;

import java.io.IOException;
import java.lang.reflect.Method;

import org.json.JSONException;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.gsngames.qa.vimana.core.db.testdata.Set;


/**
 * Custom data provider used for data driven tests
 * 
 * All the test cases that implements should use
 * 
 * @Test - dataDriverName=CustomDataProvider   and dataDriverClass= {@link CustomDataProvider}
 * @TestMetadata - dataSetName= <dataSetName> from testdata.xml
 * 
 * all the setMethod should define {@link Set} input parameter 
 * 
 * @author lnr
 *
 */
public class CustomDataProvider extends Vimana{	
	
    @DataProvider(name = "CustomDataProvider")
    public static Object[][] getData(Method method,ITestContext context) throws VimanaException, JSONException, IOException {
        String dataSetName = null;
        
        TestMetadata testMetadata = method.getAnnotation(TestMetadata.class);
        if (testMetadata != null) {
            dataSetName = testMetadata.dataSetName();
        } else {
            throw new VimanaException("DataSetName is missing. Please provide dataSetName in @TestMetadata");
        }
        

        //if there is a project that does not have the keyword dataDriven at all then the tests would be executedin the datadriven mode as it is currently
        if(getConfigProperty("dataDriven")==null || getConfigProperty("dataDriven").equalsIgnoreCase("true")||getConfigProperty("dataDriven").equalsIgnoreCase("y"))
        	return getFlangeFactory().getTestDataHandler().getDataSets(dataSetName);
        else
        	return getFlangeFactory().getTestDataHandler().getFirstSet(dataSetName);
        	
    }
}