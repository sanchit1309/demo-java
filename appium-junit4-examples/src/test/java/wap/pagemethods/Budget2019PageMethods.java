package wap.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import wap.pageobjects.Budget2019PageObjects;
import web.pagemethods.WebBaseMethods;

public class Budget2019PageMethods {

	
	private Budget2019PageObjects budget2019PageObjects;
	private WapListingPageMethods wapListingPageMethods;

	public Budget2019PageMethods(WebDriver driver) {
		wapListingPageMethods = new WapListingPageMethods(driver);
		budget2019PageObjects = PageFactory.initElements(driver, Budget2019PageObjects.class);

	}
	
	public List<String> getAllUrls() {
		List<String> allUrls = new LinkedList<>();
		try {
			allUrls = WebBaseMethods.getListHrefUsingJSE(budget2019PageObjects.getAllUrls());
		} catch (Exception ee) {
			ee.printStackTrace();
			allUrls = new LinkedList<>();
		}
		return allUrls;

	}
	
}
