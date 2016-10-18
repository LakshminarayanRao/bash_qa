package com.gsngames.qa.bashfb.test.helper;

import org.openqa.selenium.By;

import com.gsngames.qa.bashfb.test.AbstractWebDriverNG;
import com.gsngames.qa.vimana.core.db.account.Account;

public class BashView extends AbstractWebDriverNG {
	public static void login(Account account) throws Exception {
		wd().get("https://www.facebook.com/login/");

		wd().findElement(By.id("email")).sendKeys("rabbit0057@gmail.com");
		wd().findElement(By.id("pass")).sendKeys("Incorrect@57");
		wd().findElement(By.id("loginbutton")).click();
		Thread.sleep(3000);
	}

	public static void loadGame() throws Exception {
		wd().findElement(By.xpath(".//*[@id='navItem_8547499526']/a/div")).click();
		Thread.sleep(10000);
		wd().navigate().to(
				"https://bingo.bitrhymes.com/apps/bingo/facebook/app/?fb_source=bookmark&ref=bookmarks&count=0&fb_bmpos=_0");
	}
}
