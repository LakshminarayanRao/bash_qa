package com.gsngames.qa.vimana.core;

import org.json.JSONObject;

import com.gsngames.qa.vimana.core.handler.WebDriverHandler;
import com.gsngames.qa.vimana.core.handler.impl.WebDriverHandlerImpl;

/**
 * 
 * @author lnr
 * 
 */
public class VimanaRemoteDriver {

	private WebDriverHandler webDriver;
	JSONObject capObject;

	public VimanaRemoteDriver() throws Exception {
		this.capObject = Vimana.getCapabilityJson();
		String hubIp =VimanaConfig.getConfigProperty("cloudMaster");
		if (this.capObject.has(Constants.CAPABILITY_KEY_WD)) {
			this.webDriver = new WebDriverHandlerImpl(hubIp, this.capObject);
		}

	}

	public WebDriverHandler getWebDriver() {
		return webDriver;
	}

	public void setWebDriver(WebDriverHandler webDriver) {
		this.webDriver = webDriver;
	}

	public void releaseDrivers() throws VimanaException {

		if (this.webDriver != null) {
			this.webDriver.quitDriver();
			this.webDriver = null;
		}
	}
}
