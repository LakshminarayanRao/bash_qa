package com.gsngames.qa.bashfb.test;

import java.lang.reflect.Method;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.gsngames.qa.vimana.core.Vimana;
import com.gsngames.qa.vimana.core.VimanaException;

public class AbstractWebDriverNG extends Vimana {

	@BeforeSuite(groups = { "prerequisite" })
	@Parameters({ "configFile" })
	public void beforeSuite(ITestContext context, String configFile) throws Exception {
		initializeFactory(configFile);
	}

	public static RemoteWebDriver wd() throws Exception {
		return webdriver().web();
	}

	@BeforeMethod(groups = { "prerequisite" })
	public void startTestMethod(Method method) throws Exception {
		System.out.println("Starting testing method....");
		super.createDrivers(method);
	}

	// @AfterMethod(groups = { "prerequisite" }, dependsOnMethods = {//
	// "captureScreenShot" })
	@AfterMethod(groups = { "prerequisite" })
	public void endMethod(Method method, ITestResult result) throws VimanaException, InterruptedException {
		super.releaseDrivers();
	}

	@AfterSuite(groups = { "prerequisite" })
	public void afterSuite(ITestContext context) throws Exception {

	}

	public void scroll() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 1) {
				break;
			}
			Thread.sleep(3000);
			((JavascriptExecutor) wd()).executeScript("window.scrollBy(0,-1000)", "");
			Thread.sleep(3000);
		}
	}

}
