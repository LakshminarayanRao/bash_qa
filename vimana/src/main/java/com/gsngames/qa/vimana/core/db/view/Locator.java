package com.gsngames.qa.vimana.core.db.view;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;

public class Locator {
	private By by;
	private String value;

	public By getBy() {
		this.by = getElementBy(this.value);
		return by;
	}

	public By getBy(String regx, String value) {
		this.value = this.value.replace(regx, value);
		this.by = getElementBy(this.value);
		return this.by;
	}

	/**
	 * This method can be used to replace multiple variables in locators. Make
	 * sure the regx match with the corresponding values.
	 * 
	 * @usage getElement("Compose", "ToLozenges").getBy(new
	 *        String[]{"--EMAILID--", "--CONTACTNAME--"}, new String[]{
	 *        homeEmail, contactFullName}); in the above example for the locator
	 *        ToLozenge, "--EMAILID--" will be replaced with homeEmail and
	 *        "--CONTACTNAME--" will be replaced with contactFullName and
	 * 
	 * @param regx
	 *            the array of variables
	 * @param value
	 *            the array of values
	 * @return By new by object with the replacements
	 * @throws Exception
	 * @author rajivj
	 */
	public By getBy(String[] regx, String[] value) throws Exception {
		if (regx.length != value.length) {
			throw new Exception(
					"The replacement strings lengths do not match. Make sure the sizes of the regx & value matches.");
		} else {
			for (int i = 0; i < regx.length; i++) {
				this.value = this.value.replace(regx[i], value[i]);
			}
		}
		this.by = getElementBy(this.value);
		return this.by;
	}

	public void setBy(By by) {
		this.by = by;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the By object based on the locator name
	 * 
	 * @param locatorName
	 * @return
	 * @throws VimanaException
	 */
	public static By getElementBy(String locatorName) {
		By by = null;

		int start = locatorName.indexOf("=");
		int end = locatorName.length();

		if (start != -1) {

			String key = locatorName.substring(0, start);
			String value = locatorName.substring(start + 1, end);
			if (key.equalsIgnoreCase("id")) {
				by = By.id(value);
			} else if (key.equalsIgnoreCase("name")) {
				by = By.name(value);
			} else if (key.equalsIgnoreCase("css")) {
				by = By.cssSelector(value);
			} else if (key.equalsIgnoreCase("xpath")) {
				by = By.xpath(value);
			} else if (key.equalsIgnoreCase("class")) {
				by = By.className(value);
			} else if (key.equalsIgnoreCase("link")) {
				by = By.linkText(value);
			} else if (key.equalsIgnoreCase("tag")) {
				by = By.tagName(value);
			} else if (key.equalsIgnoreCase("plink")) {
				by = By.partialLinkText(value);
			} else {
				assertTrue(false,
						"Locator doesn't start with one of these values id,name,css,xpath,link,tag,plink. Example id=something, name=something etc");
			}

			// fail the test if by is null
			assertNotNull(by, "By object can not be null. Please verify the value of locator " + locatorName);
		}

		return by;

	}
}
