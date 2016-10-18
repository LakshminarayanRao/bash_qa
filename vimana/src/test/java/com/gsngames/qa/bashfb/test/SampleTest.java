package com.gsngames.qa.bashfb.test;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.Test;

import com.gsngames.qa.bashfb.test.helper.BashView;
import com.gsngames.qa.vimana.core.TestMetadata;
import com.gsngames.qa.vimana.core.db.account.Account;
import com.gsngames.qa.vimana.core.db.testdata.Set;

public class SampleTest extends AbstractWebDriverNG {
	static WebDriver driver;
	Screen screen = new Screen();
	Pattern start = new Pattern(
			"/Users/lakshminarayanrao/Documents/dev/workspace/vimana/src/test/resources/img/fb_start.png");
	Pattern done = new Pattern(
			"/Users/lakshminarayanrao/Documents/dev/workspace/vimana/src/test/resources/img/fb_done.png");

	@TestMetadata(userStory = "WebDriverCompose1", scenario = "Compose Using WebDriver", steps = "Given When Then")
	@Test(groups = { "sample1" }, description = "Compose something1")
	public void testcase1() throws Exception {
		wd().get("http://mail.yahoo.com");
		Thread.sleep(5000);
		wd().findElement(By.id("username")).sendKeys("calbugbash32");
		wd().findElement(By.id("passwd")).sendKeys("testing");
		wd().findElement(By.id(".save")).click();
		Thread.sleep(8000);
		// wd().findElement(By.cssSelector("li[class='yuhead-me'] >
		// a")).click();
		wd().close();
	}


	@TestMetadata(userStory = "WebDriverCompose1", scenario = "Compose Using WebDriver", steps = "Given When Then")
	@Test(groups = { "sample1" }, description = "Compose something1")
	public void TestFacebook1() throws Exception {
		Set set = getDataSet("BashTestScanario1").getSet("BashTestScanarioTestCase1");
		System.out.println(set.getData("testData1"));
		System.out.println(getElement("BashHomeView", "Tab1").getValue());
	}

	@TestMetadata(userStory = "WebDriverCompose1", scenario = "Compose Using WebDriver", steps = "Given When Then")
	@Test(groups = { "sample" }, description = "Compose something1")
	public void TestFacebook() throws Exception {

		String extentReportFile = System.getProperty("user.dir") + "\\extentReportFile.html";
		String extentReportImage = System.getProperty("user.dir") + "\\Screenshots\\BingoBash.png";

		// driver = new FirefoxDriver();
		// driver.manage().window().maximize();

		/*
		 * DesiredCapabilities caps = DesiredCapabilities.firefox();
		 * caps.setCapability("ignoreZoomSetting", true);
		 */
		// driver = new FirefoxDriver();

		wd().manage().window().maximize();
		Account account = new Account();
		account.setUserName("rabbit0057@gmail.com");
		account.setPassword("Incorrect@57");
		
		BashView.login(account);

		BashView.loadGame();
		
		//driver.switchTo().alert().dismiss();

		
		wd().findElement(By.id("send_gift")).click();
		Thread.sleep(3000);
		
		scroll();

		wd().findElement(By.id("play")).click();
		Thread.sleep(3000);

		wd().findElement(By.id("profile")).click();
		Thread.sleep(3000);
		scroll();


		wd().findElement(By.id("store")).click();
		Thread.sleep(3000);
		scroll();


		wd().findElement(By.id("credits")).click();
		Thread.sleep(3000);

		wd().findElement(By.id("coins")).click();
		Thread.sleep(3000);

		wd().findElement(By.id("earn")).click();
		Thread.sleep(6000);

		// wd().findElement(By.xpath("//*[@id='tp-guide']/div/div[3]/form/div/div[2]/label/a")).click();
		System.out.println("START............");
		screen.wait(start, 30);
		screen.doubleClick(start);
		Thread.sleep(3000);
		for (int second = 0;; second++) {
			if (second >= 1) {
				break;
			}
			((JavascriptExecutor) wd()).executeScript("window.scrollBy(0,250)", "");
			Thread.sleep(5000);
			// wd().findElement(By.id("fb_form_button")).click();
			screen.wait(done, 30);
			screen.doubleClick(done);
			((JavascriptExecutor) wd()).executeScript("window.scrollBy(0,-250)", "");
			Thread.sleep(3000);

			System.out.println("END.................");

			wd().findElement(By.id("request")).click();
			Thread.sleep(3000);

			wd().findElement(By.id("team")).click();
			Thread.sleep(3000);
			scroll();


			wd().findElement(By.id("invite")).click();
			Thread.sleep(3000);
			scroll();

			Thread.sleep(3000);
			File scrFile = ((TakesScreenshot) wd()).getScreenshotAs(OutputType.FILE);
			// Now you can do whatever you need to do with it, for example copy
			// somewhere
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\Screenshots\\BingoBash.png"));
			// Utills.captureScreenshot("extentReportImage");

			wd().quit();

		}

	}

}
