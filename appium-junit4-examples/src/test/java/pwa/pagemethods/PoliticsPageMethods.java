package pwa.pagemethods;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.VerificationUtil;
import pwa.pageobjects.PoliticsPageObjects;

public class PoliticsPageMethods {
	
	private PoliticsPageObjects politicsPageObjects;
	private PwaListingPageMethods pwaListingPageMethods;
	
	public PoliticsPageMethods(WebDriver driver) {
		pwaListingPageMethods = new PwaListingPageMethods(driver);
		politicsPageObjects = PageFactory.initElements(driver, PoliticsPageObjects.class);
	}

	public List<WebElement> getSectionNewsHeadlines() {
		List<WebElement> sectionHeadlines = new ArrayList<>();
		sectionHeadlines= politicsPageObjects.getSectionNews();
		return sectionHeadlines;
	}

	public List<String> getSectionNewsHref(String sectionDiv) {
		List<String> news = new ArrayList<>();
		news= VerificationUtil.getLinkHrefList(politicsPageObjects.getSectionNews());
		return news;
	}

	public List<WebElement> getTopSectionNews() {
		return pwaListingPageMethods.getListOfHeadings();
	}

	
}
