package pwa.pagemethods;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import pwa.pageobjects.IndustryPageObjects;
import web.pagemethods.WebBaseMethods;

public class IndustryPageMethods {
	
	private IndustryPageObjects industryPageObjects;
	private PwaListingPageMethods pwaListingPageMethods;
	
	public IndustryPageMethods(WebDriver driver) {
		industryPageObjects = PageFactory.initElements(driver, IndustryPageObjects.class);
		pwaListingPageMethods = new PwaListingPageMethods(driver);
	}
	
//	public List<String> getTopSectionHeaders() {
//		List<String> topSectionHeaders = new ArrayList<String>();
//		List<WebElement> subMenu = industryPageObjects.getIndustrySubMenu();
//		for (int i = 0; i < subMenu.size(); i++) {
//			subMenu = industryPageObjects.getIndustrySubMenu();
//			topSectionHeaders.add(subMenu.get(i).getText().replaceAll(".*\\n", ""));
//		}
//		return topSectionHeaders;
//	}

	public String getSectionNewsLink(String sectionDiv) {
		WebBaseMethods.moveToElement(industryPageObjects.getSectionHeading(sectionDiv));
		return industryPageObjects.getSectionHeading(sectionDiv).getAttribute("href");
	}

	public List<WebElement> getSectionNewsHeadlines(String sectionDiv) {
		List<WebElement> sectionHeadlines = new ArrayList<>();
		//sectionHeadlines.addAll(industryPageObjects.getImageLink(sectionDiv));
		sectionHeadlines.addAll(industryPageObjects.getSectionNews(sectionDiv));
		return sectionHeadlines;
	}

	public List<String> getSectionNewsHref(String sectionDiv) {
		List<String> news = new ArrayList<>();
		int counter = 0;
		boolean retryFlag = false;
		do {
			try {
				news = WebBaseMethods.getListHrefUsingJSE(industryPageObjects.getSectionNews(sectionDiv));
			} catch (WebDriverException e) {
				retryFlag = true;
				counter++;
			}
		} while (counter < 5 && retryFlag);
		return news;
	}

	public List<WebElement> getTopSectionNews() {
		return pwaListingPageMethods.getListOfHeadings();
	}

	public List<String> getTopSectionNewsHref() {
		List<String> topNews = new ArrayList<String>();
		int counter=0;
		boolean retryFlag = false;
		do {
			try {
				topNews = WebBaseMethods.getListHrefUsingJSE(pwaListingPageMethods.getMoreHeadlines());
			} catch (WebDriverException e) {
				retryFlag = true;
				counter++;
			}
		} while (counter < 5 && retryFlag);
		return topNews;
	}

}
