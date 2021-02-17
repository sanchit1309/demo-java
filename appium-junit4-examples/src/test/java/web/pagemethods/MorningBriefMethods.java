package web.pagemethods;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import web.pageobjects.MorningBriefObjects;

public class MorningBriefMethods {

	private MorningBriefObjects morningBriefObjects;
	private WebDriver driver;

	public MorningBriefMethods(WebDriver driver) {
		morningBriefObjects = PageFactory.initElements(driver, MorningBriefObjects.class);
		this.driver = driver;
	}

	public List<String> getMorningBriefUrls() {
		return WebBaseMethods.getListHrefUsingJSE(morningBriefObjects.getSlidesAnchorTag());

	}
}