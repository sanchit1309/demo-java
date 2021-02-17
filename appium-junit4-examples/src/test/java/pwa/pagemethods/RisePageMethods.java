package pwa.pagemethods;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.VerificationUtil;
import pwa.pageobjects.RisePageObjects;
import web.pagemethods.WebBaseMethods;

public class RisePageMethods {
	
	private RisePageObjects risePageObjects;
	private PwaListingPageMethods pwaListingPageMethods;
	
	public RisePageMethods(WebDriver driver) {
		risePageObjects = PageFactory.initElements(driver, RisePageObjects.class);
		pwaListingPageMethods = new PwaListingPageMethods(driver);
	}
	
	public List<String> getTopSectionHeaders(){
		List<String> topSectionHeaders = new ArrayList<String>();
		for(WebElement ele: risePageObjects.getRiseSubMenu()){
			topSectionHeaders.add(ele.getText().replaceAll(".*\\n",""));
		}
		return topSectionHeaders;
	}
	
	public List<WebElement> getTopSectionNews() {
		return pwaListingPageMethods.getListOfHeadings();
	}

	public List<String> getTopSectionNewsHref() {
		List<String> topNews = new ArrayList<String>();
		topNews = VerificationUtil.getLinkHrefList(pwaListingPageMethods.getMoreHeadlines());
		return topNews;
	}

	public String getSectionNewsLink(String sectionDiv) {
		WebBaseMethods.moveToElement(risePageObjects.getSectionHeading(sectionDiv));
		return risePageObjects.getSectionHeading(sectionDiv).getAttribute("href");
	}

	public List<WebElement> getSectionNewsHeadlines(String sectionDiv) {
		List<WebElement> sectionHeadlines = new ArrayList<>();
		sectionHeadlines= risePageObjects.getSectionNews(sectionDiv);
		return sectionHeadlines;
	}

	public List<String> getSectionNewsHref(String sectionDiv) {
		List<String> news = new ArrayList<>();
		news= VerificationUtil.getLinkHrefList(risePageObjects.getSectionNews(sectionDiv));
		return news;
	}

}
