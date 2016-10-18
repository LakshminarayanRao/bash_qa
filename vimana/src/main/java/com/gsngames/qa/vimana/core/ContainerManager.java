package com.gsngames.qa.vimana.core;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import com.gsngames.qa.vimana.core.db.account.Account;
import com.gsngames.qa.vimana.core.db.testdata.Data;
import com.gsngames.qa.vimana.core.db.testdata.DataSet;
import com.gsngames.qa.vimana.core.db.testdata.Set;
import com.gsngames.qa.vimana.core.db.view.Locator;
import com.gsngames.qa.vimana.core.handler.AccountHandler;
import com.gsngames.qa.vimana.core.handler.CacheHandler;

public class ContainerManager {

	public static String configFile;
	private static VimanaFactory vimanaFactory;

	/**
	 * Gets the handles to the factory and bootstraps the {@link VimanaFactory}
	 * 
	 * @param configFile
	 * @throws Exception
	 */
	public void initializeFactory(String configFile) throws Exception {
		System.out.println("configFile --> " + configFile);
		Vimana.configFile = configFile;
		VimanaConfig properties = new VimanaConfig();
		properties.setProperties(getProperties(configFile));
		vimanaFactory = new VimanaFactory(properties.getProperties());
		System.out.println("Vimana Factory..:" + vimanaFactory);
	}

	private Properties getProperties(String configFile) throws VimanaException {
		Properties configProperties = new Properties();
		try {
			configProperties.load(new FileReader(new File(configFile)));
		} catch (IOException e) {
			throw new VimanaException("Error while loading the driver property file:" + configFile, e.fillInStackTrace());
		}
		return configProperties;

	}

	public static String getConfigProperty(String key) {

		return VimanaConfig.getConfigProperty(key);
	}

	public static JSONObject getCapabilityJson() {
		JSONObject capabilityObj=null;
		try {
			capabilityObj = new JSONObject(VimanaConfig.getConfigProperty("capabilities"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return  capabilityObj;
	}

	public static VimanaFactory getFlangeFactory() {
		return vimanaFactory;
	}

	

	public int getTotalAccounts() {
		return vimanaFactory.getAccountHandler().getTotalAccounts();
	}

	public Account getAccount() {
		Account account = null;
		try {
			account = vimanaFactory.getAccountHandler().getUnusedAccount();
			vimanaFactory.getCacheHandler().put("currentAccount", account);
		} catch (VimanaException e) {
			assertTrue(false, e.getMessage());
		}
		assertNotNull(account, "getAccount() returned null");
		return account;
	}

	public Account getAccount(String testId) {
		Account account = null;
		try {
			account = vimanaFactory.getAccountHandler().getAccount(testId);
		} catch (VimanaException e) {
			assertTrue(false, e.getMessage());
		}
		assertNotNull(account, "getAccount() returned null");
		return account;
	}

	public static AccountHandler getAccountHandler() {
		return getFlangeFactory().getAccountHandler();
	}

	public static String getElementLocator(String viewName, String elementName) {
		String result = null;
		try {
			result = vimanaFactory.getViewHandler().getElement(viewName, elementName).getValue();
		} catch (VimanaException e) {
			e.printStackTrace();
			assertTrue(false, e.getMessage());
		}
		assertNotNull(result, "Locator value is NULL with View Name:" + viewName + " elementName:" + elementName);
		return result;
	}

	public static Locator getElement(String viewName, String elementName) {
		Locator locator = null;
		try {
			locator = vimanaFactory.getViewHandler().getElement(viewName, elementName);
		} catch (VimanaException e) {
			assertTrue(false, "CHECK:" + e.getMessage());
		}
		return locator;
	}

	/**
	 * Gets the int representation for Wait based on Short, Medium and Long wait
	 * 
	 * @param flangeWait
	 * @return
	 */
	public static int getWait(VimanaWait flangeWait) {
		if (flangeWait.equals(VimanaWait.SHORT)) {
			return Integer.parseInt(getConfigProperty("shortWait"));
		} else if (flangeWait.equals(VimanaWait.MEDIUM)) {
			return Integer.parseInt(getConfigProperty("mediumWait"));
		} else if (flangeWait.equals(VimanaWait.LONG)) {
			return Integer.parseInt(getConfigProperty("longWait"));
		} else {
			return 2;
		}
	}

	public final static int getModifiedWaitTime(int seconds) {
		// local variables to hold global config values
		int shortWait = 0;
		int mediumWait = 0;
		int longWait = 0;
		int bufferWait = 0;

		// Handling in case parameters are not defined
		try {
			shortWait = Integer.parseInt(getConfigProperty("shortWait"));
			mediumWait = Integer.parseInt(getConfigProperty("mediumWait"));
			longWait = Integer.parseInt(getConfigProperty("longWait"));
			bufferWait = Integer.parseInt(getConfigProperty("bufferWait"));
		} catch (NumberFormatException e) {
		}

		// Changing waitForElement present as per global settings.
		if (seconds < shortWait) {
			seconds = seconds + bufferWait;
		}
		if (seconds == shortWait) {
			seconds = seconds + bufferWait;
		} else if (seconds > shortWait && seconds < mediumWait) {
			seconds = seconds + mediumWait + bufferWait;
		} else if (seconds == mediumWait) {
			seconds = seconds + bufferWait;
		} else if (seconds > mediumWait && seconds < longWait) {
			seconds = seconds + longWait + bufferWait;
		} else if (seconds == longWait) {
			seconds = seconds + bufferWait;
		} else if (seconds > longWait) {
			seconds = seconds + bufferWait;
		}
		return seconds;
	}

	public static Data getData(String dataName) {
		Data data = null;
		try {
			data = getFlangeFactory().getTestDataHandler().getData(dataName);
		} catch (VimanaException e) {
			assertTrue(false, "getData(String dataName) throwed exception:" + e.getMessage());
		}
		assertNotNull(data, "getData(String dataName) returned null Data object for data name: " + dataName);
		return data;
	}

	public static DataSet getDataSet(String dataSetName) {
		DataSet dataSet = null;
		try {
			dataSet = getFlangeFactory().getTestDataHandler().getDataSet(dataSetName);
		} catch (VimanaException e) {
			e.printStackTrace();
			assertTrue(false, "getDataSet(String dataSetName) throwed exception:" + e.getMessage());
		}
		assertNotNull(dataSet, "getDataSet(String dataSetName) returned null DataSet object for dataSet name: " + dataSetName);
		return dataSet;
	}

	public static Set getSet(String dataSetName, String setName) {
		Set set = null;
		try {
			set = getFlangeFactory().getTestDataHandler().getSet(dataSetName, setName);
		} catch (VimanaException e) {
			assertTrue(false, "getSet(String dataSetName, String setName) throwed exception:" + e.getMessage());
		}
		assertNotNull(set, "getSet(String dataSetName, String setName) returned null Set object for dataSet name: " + dataSetName + " and setName :" + setName);
		return set;
	}

	public static Data getSetData(String dataSetName, String setName, String dataName) {
		Data data = null;
		try {
			data = getFlangeFactory().getTestDataHandler().getSetData(dataSetName, setName, dataName);
		} catch (VimanaException e) {
			assertTrue(false, "getSetData(String dataSetName, String setName, String dataName) throwed exception:" + e.getMessage());
		}
		assertNotNull(data, "getSetData(String dataSetName, String setName, String dataName) returned null Data object for dataSetName: " + dataSetName + " setName:" + setName + " dataName:" + dataName);
		return data;
	}

	public static CacheHandler getCache() {
		return getFlangeFactory().getCacheHandler();
	}

	
}
