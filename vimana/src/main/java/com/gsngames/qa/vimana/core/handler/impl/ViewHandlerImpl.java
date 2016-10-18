package com.gsngames.qa.vimana.core.handler.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.gsngames.qa.vimana.core.Resource;
import com.gsngames.qa.vimana.core.VimanaConfig;
import com.gsngames.qa.vimana.core.VimanaException;
import com.gsngames.qa.vimana.core.db.PreDataReader;
import com.gsngames.qa.vimana.core.db.view.FindElement;
import com.gsngames.qa.vimana.core.db.view.Locator;
import com.gsngames.qa.vimana.core.db.view.UIElement;
import com.gsngames.qa.vimana.core.db.view.ViewRoot;
import com.gsngames.qa.vimana.core.handler.ViewHandler;


/**
 * This class implements {@link ViewHandler} Gets the element (locator) based on
 * view name, element name and browser type as per driverconfig.properties
 * 
 * @author lnr
 * 
 */

public class ViewHandlerImpl implements ViewHandler {

    private ViewRoot viewRoot;
    private PreDataReader preDataReader;
    private Map<String, String> viewCache;

    public ViewHandlerImpl() throws VimanaException {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        viewCache = Collections.synchronizedMap(hashMap);

        this.preDataReader = new PreDataReader("view.xml", VimanaConfig.getConfigProperty("product"), VimanaConfig.getConfigProperty("intl"));
        Serializer serializer = new Persister();
        String xml = null;
        try {
            xml = preDataReader.getPreProcessedXML();
            FileUtils.writeStringToFile(new File(Resource.dataPath + File.separator + "test.xml"), xml);
            this.viewRoot = serializer.read(ViewRoot.class, xml);
        } catch (Exception e) {
            throw new VimanaException(e.fillInStackTrace());
        }

    }

	/**
	 * Implementation for getElement
	 */
	public Locator getElement(String viewName, String elementName) throws VimanaException {
		String key = "View_" + viewName + "_" + elementName;
		String retVal = viewCache.get(key);
		if (retVal == null) {
			UIElement uiElement = viewRoot.getUIElement(viewName, elementName);

			if (uiElement != null) {
				retVal = getElementByBrowserAndIntl(uiElement);
				// Make sure that the return value is not null
				if (retVal != null) {
					viewCache.put(key, retVal);
				} else {
					throw new VimanaException("getElement(String viewName, String elementName) returned null value with viewName:" + viewName + " elementName:" + elementName);
				}
			} else {
				throw new VimanaException("UI Element with View:" + viewName + " Element Name:" + elementName + " is null or not present.");
			}
		}

		Locator locator = new Locator();
		locator.setValue(retVal);
		return locator;
	}

	/**
	 * Gets the find Element based on the browser name & intl
	 * 
	 * @param uiElement
	 * @value true if the value of the element is required, false if not required
	 * @action true if the action of the element is required, false if not required
	 * 
	 * @return value or action of the element based on the current browser & intl, if not found returning null
	 */
	private String getElementByBrowserAndIntl(UIElement uiElement) {
		//NEED TO REVISE FOLLOWING LOGIC
		String currentBrowser = VimanaConfig.getConfigProperty("browserName").trim();
		String currentIntl = VimanaConfig.getConfigProperty("intl").trim();

		//Getting all the findElements into a data structure
		HashMap<String, ArrayList<ArrayList<String>>> elements = new HashMap<String, ArrayList<ArrayList<String>>>();
		List<FindElement> findElements = uiElement.getFindElements();
		for(FindElement findElement: findElements) {
			String elementBrowser = findElement.getBrowser().trim();
			String elementIntl = findElement.getIntl();
			elementIntl = (elementIntl!=null) ? elementIntl.trim() : null;
			String elementValue = findElement.getValue();
			elementValue = (elementValue!=null) ? elementValue.trim() : null;
			if(elementIntl!=null && elementIntl.contains(",")) {
				String[] intls = elementIntl.split(",");
				for(String intl: intls) {
					intl = intl.trim();
					ArrayList<String> listData = new ArrayList<String>();
					listData.add(intl); listData.add(elementValue);

					if(elements.containsKey(elementBrowser)) {
						elements.get(elementBrowser).add(listData);
					}
					else {
						ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
						temp.add(listData);
						elements.put(elementBrowser, temp);
					}
				}
			} else {
				ArrayList<String> listData = new ArrayList<String>();
				listData.add(elementIntl); listData.add(elementValue);

				if(elements.containsKey(elementBrowser)) {
					elements.get(elementBrowser).add(listData);
				}
				else {
					ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
					temp.add(listData);
					elements.put(elementBrowser, temp);
				}
			}
		}

		//Getting all the findElements based on the current browser
		ArrayList<ArrayList<String>> browserElementMap = new ArrayList<ArrayList<String>>();
		if (elements.containsKey(currentBrowser)) {
			browserElementMap = elements.get(currentBrowser);
		}else {
			browserElementMap = elements.get("default");
		}

		//Getting the intl specific findElements from the above list
		ArrayList<String> element = new ArrayList<String>();
		boolean foundIntlSpecificLocator = false;
		for (ArrayList<String> list: browserElementMap) {
			if(list.get(0) != null && list.get(0).equalsIgnoreCase(currentIntl)) {
				element = list;
				foundIntlSpecificLocator = true;
			} else if(list.get(0) == null && foundIntlSpecificLocator==false) {
				element = list;
			}
		}

		if(element.size()!=0)
			return element.get(1);
		else 
			return null;
	}


	@Override
	public String getAction(String viewName, String elementName) throws VimanaException, Exception {
		UIElement uiElement = viewRoot.getUIElement(viewName, elementName);
		String action = uiElement.getAction();
		if (action == null) {
			throw new Exception("UI Element with View: " + viewName + " , ElementName: " + elementName + " , does not contain an action attribute.");
		} else {
			return action; 
		}
	}
}
