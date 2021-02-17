package web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.ElectionsPageObjects;

public class ElectionsPageMethods {
	private WebDriver driver;
	private ElectionsPageObjects electionsPageObjects;

	public ElectionsPageMethods(WebDriver driver) {
		this.driver = driver;
		electionsPageObjects = PageFactory.initElements(driver, ElectionsPageObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
	}

	public List<WebElement> getSpecificHeadlineLinks(String sectionName) {
		List<WebElement> urls = new LinkedList<WebElement>();

		try {

			urls = electionsPageObjects.getSpecificHeadlineLinks(sectionName);

		} catch (Exception ee) {
			urls = new LinkedList<WebElement>();
			ee.printStackTrace();

		}
		return urls;

	}

	public List<String> getAllUrls() {
		List<String> allUrls = new LinkedList<>();
		try {
			allUrls = WebBaseMethods.getListHrefUsingJSE(electionsPageObjects.getAllUrls());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return allUrls;

	}

	public List<String> getTopHeadlinesLinks() {
		List<String> topHeadlines = new LinkedList<>();
		try {
			topHeadlines = WebBaseMethods.getListHrefUsingJSE(electionsPageObjects.getTopHeadlines());
		} catch (Exception ee) {
			topHeadlines = new LinkedList<>();
			ee.printStackTrace();

		}

		return topHeadlines;
	}
	
	public List<String> getTopHeadlinesText() {
		List<String> topHeadlines = new LinkedList<>();
		try {
			topHeadlines = WebBaseMethods.getListTextUsingJSE(electionsPageObjects.getTopHeadlines());
		} catch (Exception ee) {
			topHeadlines = new LinkedList<>();
			ee.printStackTrace();

		}

		return topHeadlines;
	}
	
}
