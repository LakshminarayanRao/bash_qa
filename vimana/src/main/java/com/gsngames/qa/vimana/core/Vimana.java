package com.gsngames.qa.vimana.core;

import java.lang.reflect.Method;

import com.gsngames.qa.vimana.core.handler.VimanaDriverHandler;
import com.gsngames.qa.vimana.core.handler.WebDriverHandler;

/**
 * @author lnr
 */

public class Vimana extends ContainerManager {

	// private static final Logger log = LoggerFactory.getLogger(Flange.class);

	public void createDrivers(Method method) {
		System.out.println("Executing Test Method:" + method.getName());
		try {
			VimanaDriverHandler driverHandler = getFlangeFactory().getDriverHandler();
			System.out.println("Driver Handler:" + driverHandler);
			// Initialize all Drivers
			driverHandler.newInstance();

			// create cache
			getFlangeFactory().getCacheHandler().createCache();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Quits the driver at the end of every test execution
	 * 
	 * @throws VimanaException
	 */
	public void releaseDrivers() throws VimanaException {
		if (getFlangeFactory() != null) {
			VimanaDriverHandler driverHandler = getFlangeFactory().getDriverHandler();
			driverHandler.releaseInstace();
		}
	}

	/**
	 * All the handles exposed to View and Test Classes
	 * 
	 * @throws VimanaException
	 */
	public static WebDriverHandler webdriver() throws VimanaException {
		return getFlangeFactory().getDriverHandler().getInstance().getWebDriver();
	}

}
