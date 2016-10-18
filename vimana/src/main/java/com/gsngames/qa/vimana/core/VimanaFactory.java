package com.gsngames.qa.vimana.core;

import java.util.Properties;

import com.gsngames.qa.vimana.core.handler.AccountHandler;
import com.gsngames.qa.vimana.core.handler.CacheHandler;
import com.gsngames.qa.vimana.core.handler.TestDataHandler;
import com.gsngames.qa.vimana.core.handler.ViewHandler;
import com.gsngames.qa.vimana.core.handler.VimanaDriverHandler;
import com.gsngames.qa.vimana.core.handler.impl.AccountHandlerImpl;
import com.gsngames.qa.vimana.core.handler.impl.CacheHandlerImpl;
import com.gsngames.qa.vimana.core.handler.impl.TestDataHandlerImpl;
import com.gsngames.qa.vimana.core.handler.impl.ViewHandlerImpl;
import com.gsngames.qa.vimana.core.handler.impl.VimanaDriverHandlerImpl;

/**
 * 
 * The base framework class that exposes all the services
 * 
 * @author lnr
 * 
 */

public class VimanaFactory {

	// Property is a globally visible variable using

	private static Properties properties;

	private TestDataHandler testDataHandler;
	private ViewHandler viewHandler;
	private AccountHandler accountHandler;
	private CacheHandler cacheHandler;
	private VimanaDriverHandler driverHandler;

	public VimanaFactory() {
		try {
			this.testDataHandler = new TestDataHandlerImpl();
			this.viewHandler = new ViewHandlerImpl();
			this.accountHandler = new AccountHandlerImpl();
			this.cacheHandler = new CacheHandlerImpl();
			this.driverHandler = new VimanaDriverHandlerImpl();
			System.out.println(this.driverHandler + " -> ....");
		} catch (VimanaException e) {
			e.printStackTrace();
		}
	}

	public VimanaFactory(Properties properties) {
		this();
		VimanaFactory.properties = properties;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		VimanaFactory.properties = properties;
	}

	public TestDataHandler getTestDataHandler() {
		return testDataHandler;
	}

	public void setTestDataHandler(TestDataHandler testDataHandler) {
		this.testDataHandler = testDataHandler;
	}

	public ViewHandler getViewHandler() {
		return viewHandler;
	}

	public void setViewHandler(ViewHandler viewHandler) {
		this.viewHandler = viewHandler;
	}

	public AccountHandler getAccountHandler() {
		return accountHandler;
	}

	public void setAccountHandler(AccountHandler accountHandler) {
		this.accountHandler = accountHandler;
	}

	public CacheHandler getCacheHandler() {
		return cacheHandler;
	}

	public void setCacheHandler(CacheHandler cacheHandler) {
		this.cacheHandler = cacheHandler;
	}

	public VimanaDriverHandler getDriverHandler() {
		return driverHandler;
	}

	public void setDriverHandler(VimanaDriverHandler driverHandler) {
		this.driverHandler = driverHandler;
	}

	public static String getConfigProperty(String key) {
		return properties.getProperty(key);
	}

	public static Properties getConfig() {
		return properties;
	}

}
