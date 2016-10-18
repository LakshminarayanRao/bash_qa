package com.gsngames.qa.vimana.core.handler.impl;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.gsngames.qa.vimana.core.Vimana;
import com.gsngames.qa.vimana.core.VimanaException;
import com.gsngames.qa.vimana.core.VimanaWait;
import com.gsngames.qa.vimana.core.client.SikuliClient;
import com.gsngames.qa.vimana.core.client.VimanaRemoteClient;
import com.gsngames.qa.vimana.core.db.view.Locator;
import com.gsngames.qa.vimana.core.handler.WebDriverHandler;

/**
 * This class implements {@link WebDriverHandler} interface. Use this class to
 * get the thread specific handle
 * 
 * @author lnr
 * 
 */

public class WebDriverHandlerImpl implements WebDriverHandler {
	private RemoteWebDriver driver;
	private VimanaRemoteClient flangeRemoteClient;
	// private String hubHost;
	// private String hubUrl;
	JSONObject capObject;
	private String rcIp;

	public WebDriverHandlerImpl(String ip, JSONObject capabilities) throws Exception {
		this.rcIp = ip;
		this.capObject = capabilities;
		createDriver();
	}

	/**
	 * Gets the remote web drive session based on the driver config settings
	 */

	public RemoteWebDriver createDriver() throws VimanaException {
		try {
			this.driver = getDriverByCapability();
			this.driver.manage().timeouts().setScriptTimeout(2, TimeUnit.SECONDS);
			// this.flangeRemoteClient = new
			// FlangeRemoteClient(getIP(this.hubHost,this.driver.getSessionId()),
			// "");
		} catch (Exception e) {
			throw new VimanaException("Error while creating session for thread:" + Thread.currentThread(), e);
		}
		return driver;
	}


	public SikuliClient sikuli() {
		return this.flangeRemoteClient.getSikuliClient();
	}

	public RemoteWebDriver web() {
		return this.driver;
	}

	/**
	 * Quits the Remote webdriver session
	 */
	public void quitDriver() throws VimanaException {
		if (driver != null) {
			driver.quit();
		} else {
			throw new VimanaException("Unable to quit the driver session this " + Thread.currentThread().getName() + " thread");
		}
	}

	/**
	 * Get the Remote web driver instance based on driver capability mentioned
	 * as per driver config file
	 * 
	 * @return {@link WebDriver}
	 */

	private RemoteWebDriver getDriverByCapability() throws Exception {

		// Getting config capability info
		String version = this.capObject.getJSONObject("wd").getString("version");
		String browser = this.capObject.getJSONObject("wd").getString("browser");
		String tempPlat = this.capObject.getJSONObject("wd").getString("platform");
		String exactPlatform = Platform.valueOf(tempPlat).toString();

		System.out.println("Creating Driver with required Capability Version: " + version + " Browser: " + browser + " Platform: " + exactPlatform);

		RemoteWebDriver webDriver = null;
		// If running locally then create a local driver and then return.
		// if (hubUrl.contains("localhost")) {
		// return getLocalDriver();
		// }

		DesiredCapabilities capabilities = new DesiredCapabilities(browser, version, Platform.valueOf(exactPlatform));
		capabilities.setJavascriptEnabled(true);
		capabilities.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, true);
		capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

		if (browser.equalsIgnoreCase("chrome")) {
			capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
		}
		/*
		 * if (browser.equalsIgnoreCase("android")) { webDriver = new
		 * AndroidDriver(new URL(hubUrl)); } else { webDriver = new
		 * RemoteWebDriver(new URL(hubUrl), capabilities); }
		 * 
		 * localhost:9515
		 */
		webDriver = new RemoteWebDriver(new URL("http://" + this.rcIp + ":9515"), capabilities);
		return webDriver;
	}

	/*
	 * private String getIP(String hostName, SessionId session) { int port =
	 * 4444; String rcIP = new String(); String errorMsg =
	 * "Failed to acquire remote webdriver node and port info. Root cause: ";
	 * 
	 * try { HttpHost host = new HttpHost(hostName, port); DefaultHttpClient
	 * client = new DefaultHttpClient(); URL sessionURL = new URL("http://" +
	 * hostName + ":" + port + "/grid/api/testsession?session=" + session);
	 * BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest(
	 * "POST", sessionURL.toExternalForm()); HttpResponse response =
	 * client.execute(host, r); JSONObject object = extractObject(response); //
	 * System.out.println("This is the RESPONSE:    "+response); //
	 * System.out.println("This is the JSON:    "+object);
	 * 
	 * URL myURL = new URL(object.getString("proxyId")); if ((myURL.getHost() !=
	 * null) && (myURL.getPort() != -1)) { rcIP = myURL.getHost(); } } catch
	 * (Exception e) { e.printStackTrace(); throw new RuntimeException(errorMsg,
	 * e); } return rcIP; }
	 */

	private JSONObject extractObject(HttpResponse response) throws IOException, JSONException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuilder s = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null)
			s.append(line);

		rd.close();
		return new JSONObject(s.toString());
	}

	public int getIframeIndex(String iframeContent) throws VimanaException {
		for (int i = 0; i < 15; i++) {
			web().switchTo().frame(i); // WebElement
			if (web().getPageSource().contains(iframeContent)) {
				web().switchTo().defaultContent();
				return i;
			} else {
				web().switchTo().defaultContent();
			}
		}
		return -1;
	}

	public Object executeJavascript(WebDriver driver, String script) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(script);
	}

	public boolean isElementPresent(By by) throws VimanaException {
		WebElement webElement = web().findElement(by);
		if (webElement != null)
			return true;
		else
			return false;
	}

	public boolean isElementDisplayed(By by) throws VimanaException {
		WebElement webElement = web().findElement(by);
		if (webElement.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean waitForElementPresent(By by, int maxWaitTimeInSec) throws VimanaException {
		maxWaitTimeInSec = Vimana.getModifiedWaitTime(maxWaitTimeInSec);
		int waittime = maxWaitTimeInSec * 1000;
		long starttime = System.currentTimeMillis();
		while (System.currentTimeMillis() < starttime + waittime) {
			if (isElementPresent(by)) {
				return true;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new VimanaException(e);
			}
		}
		return false;
	}

	public boolean waitForElementNotPresent(By by, int maxWaitTimeInSec) throws VimanaException {
		int waittime = maxWaitTimeInSec * 1000;
		long starttime = System.currentTimeMillis();
		while (System.currentTimeMillis() < starttime + waittime) {
			if (!isElementPresent(by)) {
				return true;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new VimanaException(e);
			}
		}
		return false;
	}

	public boolean waitForElementDisplayed(By by, int maxWaitTimeInSec) throws VimanaException {
		// required to control additional slow repsonse
		maxWaitTimeInSec = Vimana.getModifiedWaitTime(maxWaitTimeInSec);
		int waittime = maxWaitTimeInSec * 1000;
		long starttime = System.currentTimeMillis();
		while (System.currentTimeMillis() < starttime + waittime) {
			if (isElementDisplayed(by)) {
				return true;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				new VimanaException(e);
			}
		}
		return false;
	}

	public boolean waitForElementNotDisplayed(By by, int maxWaitTimeInSec) throws VimanaException {

		int waittime = maxWaitTimeInSec * 1000;
		long starttime = System.currentTimeMillis();
		while (System.currentTimeMillis() < starttime + waittime) {
			if (!isElementDisplayed(by)) {
				return true;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				new VimanaException(e);
			}
		}
		return false;
	}

	/**
	 * Implementation of double click. Accepts the webElement at parameter and
	 * performs doubleClick action on that particular Element.
	 * 
	 * @throws VimanaException
	 */
	public void doubleClick(By by) throws VimanaException {
		Actions builder = new Actions(web());
		Action doubleClick = builder.doubleClick(web().findElement(by)).build();
		doubleClick.perform();
	}

	/**
	 * Implementation of right/context click. Accepts the webElement at
	 * parameter and performs doubleClick action on that particular Element.
	 * Implemented using native java script mouseEvent. TODO once WebDriver
	 * comes up with fix for Firefox We should change the implementation to
	 * Actions builder.
	 * 
	 * @throws VimanaException
	 * 
	 */
	public void rightClick(By by) throws VimanaException {
		// Once Web driver fixes the Fire fox right click issue, we shuould use
		// the actions implementation by simply un-commenting below block.
		/*
		 * Actions builder = new
		 * Actions(getFlangeFactory().getWebDriverHandler().getInstance());
		 * Action rightClick =
		 * builder.contextClick(getFlangeFactory().getWebDriverHandler
		 * ().getInstance ().findElement(by)).build(); rightClick.perform();
		 */
		if (Vimana.getConfigProperty("browserName").toLowerCase().contains("internet")) {
			runJavaScript("arguments[0].fireEvent('oncontextmenu');", web().findElement(by));
		} else {
			runJavaScript("var evt = document.createEvent('MouseEvents');" + "evt.initMouseEvent('contextmenu',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null); " + "arguments[0].dispatchEvent(evt);", web().findElement(by));
		}
	}

	/**
	 * Implementation of JavascriptExecutor. Accepts JavaScript in the form of
	 * String and accepts WebElement on which script should be executed.
	 * 
	 * @throws VimanaException
	 */
	public void runJavaScript(String script, WebElement webElement) throws VimanaException {
		JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) web();
		javaScriptExecutor.executeScript(script, webElement);
	}

	/**
	 * Implementation of JavascriptExecutor. Accepts JavaScript in the form of
	 * String.
	 * 
	 * @throws VimanaException
	 */
	public void runJavaScript(String script) throws VimanaException {
		JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) web();
		javaScriptExecutor.executeScript(script);
	}

	/**
	 * This method can be used to select a desired options for any select
	 * dropdown Usage: select(getElement(), getElement().getValue());
	 * select(Locator, text);
	 * 
	 * @param selectLocator
	 *            the locator of the select dropdown
	 * @param optionText
	 *            The select option as a string
	 * @throws VimanaException
	 */
	public void select(Locator selectLocator, String optionText) throws Exception {
		Select select = new Select(web().findElement(selectLocator.getBy()));
		select.selectByVisibleText(optionText);
	}

	/**
	 * This method can be used to clear the textbox content for which
	 * driver.clear() is not working. clearTextareaContent(getElement
	 * ("signatureoptions", "SignatureEditorPlainTextMode"))
	 * 
	 * @param textBoxLocator
	 *            the locator obj of the textbox for which the text has to be
	 *            cleared
	 * @throws Exception
	 */
	public void clearTextareaContent(Locator textBoxLocator) throws Exception {
		// checking for the locator
		assertTrue(waitForElementDisplayed(textBoxLocator.getBy(), Vimana.getWait(VimanaWait.MEDIUM)), " the text box locator was not found: " + textBoxLocator.getValue());
		web().findElement(textBoxLocator.getBy()).click();
		web().findElement(textBoxLocator.getBy()).click();
		Thread.sleep(Vimana.getWait(VimanaWait.SHORT) / 2 * 1000);
		// Performing Ctrl + a, Delete
		Actions action = new Actions(web());
		action.sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
		action.sendKeys(Keys.DELETE).build().perform();
	}

	/**
	 * Implementation of closing a tab. Static method that injects JS that makes
	 * the "x" on the tab visible without hovering of mouse.
	 * 
	 * @throws VimanaException
	 */

	/*
	 * public void closeActiveTabJS() throws VimanaException { final By
	 * genericCloseTab = getElement("tabs", "genericCloseTabLocator") .getBy();
	 * ((JavascriptExecutor) web()) .executeScript(
	 * "var myNode; YUI().use('node', function(Y) { myNode = Y.Node.one('span.close-tab')._node; }); myNode.style.visibility='visible';"
	 * ); waitForElementDisplayed(genericCloseTab, getWait(FlangeWait.MEDIUM));
	 * web().findElement(genericCloseTab).click(); }
	 */

	/**
	 * Implementation of mouse over. Accepts the webElement at parameter and
	 * performs mouseOver action on that particular Element.
	 * 
	 * @throws VimanaException
	 */
	public void mouseOver(By by) throws VimanaException {
		Actions builder = new Actions(web());
		Action mouseOver = builder.moveToElement(web().findElement(by)).build();
		mouseOver.perform();
	}

	/**
	 * This method can be used to simulate character typing. It simulates a
	 * single key press at a time. For Characters & Numbers: any chars from a-z
	 * or A-Z can be passed as parameter. kepPress('a) keyPress('A') For
	 * Numbers: any chars from 0-9 can be passed as parameter. kepPress('0)
	 * kepPress('8)
	 * 
	 * @param c
	 *            the desired Character a-z, A-Z or 0-9
	 * @throws Exception
	 */
	public void keyPress(char c) throws Exception {
		Actions action = new Actions(web());
		action.sendKeys(Keys.chord(String.valueOf(c))).build().perform();
	}

	/**
	 * This method can be used to simulate special character (escape, shift,
	 * control, alt etc) typing. It simulates a single key press at a time. Use
	 * the keys Enum for the desired special Keys. keyPress(Keys.ENTER)
	 * keyPress(Keys.ESCAPE) kepPress(Keys.CONTROL)
	 * 
	 * @param keys
	 *            the desired Keys Character Keys.ENTER, Keys.CONTROL etc
	 * @throws Exception
	 */
	public void keyPress(Keys keys) throws Exception {
		Actions action = new Actions(web());
		action.sendKeys(keys).build().perform();
	}

	/**
	 * This method can be used to simulate a sequence of a special character
	 * (escape, shift, control, alt etc) followed a character(a-z, A-Z, 0-9)
	 * typing.
	 * 
	 * keyPress(Keys.CONTROL, 'a') - to simulate a select all
	 * keyPress(Keys.CONTROL, 'z') - to simulate a undo keyPress(Keys.CONTROL,
	 * '\\') - to simulate a close tab
	 * 
	 * @param keys
	 *            the desired Keys Character Keys.ENTER, Keys.CONTROL etc
	 * @param c
	 *            the desired Character a-z, A-Z or 0-9
	 * @throws Exception
	 */
	public void keyPress(Keys keys, char c) throws Exception {
		Actions action = new Actions(web());
		// action.sendKeys(keys).sendKeys(Keys.chord(""+c)).build().perform();
		action.sendKeys(Keys.chord(String.valueOf(keys), String.valueOf(c))).build().perform();
	}

	/**
	 * This method can be used to simulate a sequence of a special character
	 * (escape, shift, control, alt etc) followed another special character
	 * typing.
	 * 
	 * keyPress(Keys.CONTROL, Keys.ENTER) - to simulate a send mail for Minty
	 * application which is Ctrl + Enter
	 * 
	 * @param keys
	 *            the desired Keys Character Keys.ENTER, Keys.CONTROL etc
	 * @param keys
	 *            the desired Keys Character Keys.ENTER, Keys.CONTROL etc
	 * @throws Exception
	 */
	public void keyPress(Keys firstKeys, Keys secondKeys) throws Exception {
		Actions action = new Actions(web());
		// action.sendKeys(Keys.CONTROL).sendKeys(Keys.ENTER).build().perform();
		action.sendKeys(Keys.chord(String.valueOf(firstKeys), String.valueOf(secondKeys))).build().perform();
	}

	/**
	 * This method can be used to to switch windows withinSelenium control. Once
	 * switched, we need to explicitly call this method again to goto the
	 * default window.
	 * 
	 * @param Some
	 *            page source string of the window where we need to switch to.
	 * @throws Exception
	 */
	public void switchWindowBasedOnPageSource(String somePageSourceContent) throws VimanaException {
		HashSet<String> availableWindows = (HashSet<String>) web().getWindowHandles();
		for (String window : availableWindows) {
			web().switchTo().window(window);
			if (web().getPageSource().contains(somePageSourceContent)) {
				break;
			}
		}
	}

	/**
	 * This method can be used to drag and drop stuff. From Minty point of view,
	 * this can be used to drag and drop mails between folders.
	 * 
	 * Usage: To drag and drop a mail to Spam Folder
	 * dragAndDrop(getElement("messagelist",
	 * "messageBasedOnSubject").getBy("--VARIABLE--",
	 * "ComposeAndSendTest1328739840798"), getElement("leftpane",
	 * "spam").getBy()); Usage: To drag and drop a mail to Trash Folder
	 * dragAndDrop(getElement("messagelist",
	 * "messageBasedOnSubject").getBy("--VARIABLE--",
	 * "ComposeAndSendTest1328739840798"), getElement("leftpane",
	 * "trash").getBy()); Usage: To drag and drop a mail to a personal folder
	 * called PersonalFolder123 dragAndDrop(getElement("messagelist",
	 * "messageBasedOnSubject").getBy("--VARIABLE--",
	 * "ComposeAndSendTest1328739840798"), getElement("leftpane",
	 * "personalFoldersList").getBy("--FOLDERNAME--", "PersonalFolder123"));
	 * 
	 * @param sourceElement
	 *            By object of the source element
	 * @param destinationElement
	 *            By object of the destination element
	 * @throws Exception
	 */
	public void dragAndDrop(By sourceElement, By destinationElement) throws Exception {

		Actions action = new Actions(web());
		// action.dragAndDrop(sourceElement, targetElement).perform(); Does not
		// work for chrome but works for IE & FF
		action.moveToElement(web().findElement(sourceElement)).perform();
		action.clickAndHold(web().findElement(sourceElement)).perform();
		action.moveToElement(web().findElement(destinationElement)).perform();
		// need to delay the release action,otherwise the drag and drop may fail
		Thread.sleep(Vimana.getWait(VimanaWait.SHORT) * 1000);
		action.release(web().findElement(destinationElement)).perform();
	}
}
