package wap.pagemethods;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.VerificationUtil;
import wap.pageobjects.PoliticsPageObjects;

public class PoliticsPageMethods {

	private WapListingPageMethods wapListingPageMethods;
	private PoliticsPageObjects politicsPageObjects;

	public PoliticsPageMethods(WebDriver driver){
		wapListingPageMethods = new WapListingPageMethods(driver);
		politicsPageObjects = PageFactory.initElements(driver,PoliticsPageObjects.class);	
	}

	public List<WebElement> getSectionNewsHeadlines() {
		List<WebElement> sectionHeadlines = new ArrayList<>();
		//sectionHeadlines.addAll(politicsPageObjects.getImageLink(sectionDiv));
		sectionHeadlines.addAll(politicsPageObjects.getSectionNews());
		return sectionHeadlines;
	}

	public List<String> getSectionNewsHref() {
		List<String> news = new ArrayList<>();
		news= VerificationUtil.getLinkHrefList(politicsPageObjects.getSectionNews());
		return news;
	}

	public List<WebElement> getTopSectionNews() {
		return wapListingPageMethods.getListOfHeadings();
	}

	public List<String> getTopSectionNewsHref() {
		List<String> topNews = new ArrayList<String>();
		topNews = VerificationUtil.getLinkHrefList(wapListingPageMethods.getListOfHeadings());
		return topNews;
	}
}
