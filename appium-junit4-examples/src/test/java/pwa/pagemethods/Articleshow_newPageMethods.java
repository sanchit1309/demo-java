package pwa.pagemethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pwa.pageobjects.Articleshow_newPageObjects;

public class Articleshow_newPageMethods {

	private WebDriver driver;
	private Articleshow_newPageObjects articleshow_newPageObjects;
	private LoginPageMethods loginPageMethods;

	public Articleshow_newPageMethods(WebDriver driver) {
		this.driver = driver;
		loginPageMethods = new LoginPageMethods(driver);
		articleshow_newPageObjects = PageFactory.initElements(driver, Articleshow_newPageObjects.class);
	}

}
