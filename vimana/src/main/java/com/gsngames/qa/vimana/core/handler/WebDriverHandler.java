package com.gsngames.qa.vimana.core.handler;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.gsngames.qa.vimana.core.VimanaException;
import com.gsngames.qa.vimana.core.client.SikuliClient;
import com.gsngames.qa.vimana.core.db.view.Locator;

/**
 * @author lnr
 * 
 */
public interface WebDriverHandler {

	/* creates the thread specific driver instance */
	public WebDriver createDriver() throws VimanaException;

	/* Gets the thread specific driver instance */
	public RemoteWebDriver web() throws VimanaException;

	public SikuliClient sikuli();

	/* Quits the driver instances */
	public void quitDriver() throws VimanaException;
	
	public void select(Locator selectLocator, String optionText) throws Exception ;

	//public void quitAllDriver() throws VimanaException;

	//public FlangeRemoteClient getFlangeRemoteClient() throws VimanaException;

	//public String getHubHost();

	//public String getHubUrl();

	//public String getRemoteIp() throws VimanaException;
	
	

}
