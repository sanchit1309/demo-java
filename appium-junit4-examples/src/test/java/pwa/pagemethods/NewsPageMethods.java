package pwa.pagemethods;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.VerificationUtil;
import pwa.pageobjects.NewsPageObjects;
import web.pagemethods.WebBaseMethods;

public class NewsPageMethods {

	private NewsPageObjects newsPageObjects;
	private PwaListingPageMethods pwaListingPageMethods;
	
	public NewsPageMethods(WebDriver driver) {
		newsPageObjects = PageFactory.initElements(driver, NewsPageObjects.class);
		pwaListingPageMethods = new PwaListingPageMethods(driver);
	}
	
/*	public List<String> getTopSectionHeaders(){
		List<String> topSectionHeaders = new ArrayList<String>();
		for(WebElement ele: newsPageObjects.getNewsSubMenu()){
			topSectionHeaders.add(ele.getText().replaceAll(".*\\n",""));
		}
		return topSectionHeaders;
	}*/

	public String getSectionNewsLink(String sectionDiv) {
		WebBaseMethods.moveToElement(newsPageObjects.getSectionHeading(sectionDiv));
		return newsPageObjects.getSectionHeading(sectionDiv).getAttribute("href");
	}

	public List<WebElement> getSectionNewsHeadlines(String sectionDiv) {
		List<WebElement> sectionHeadlines = new ArrayList<>();
		//sectionHeadlines.addAll(newsPageObjects.getImageLink(sectionDiv));
		sectionHeadlines.addAll(newsPageObjects.getSectionNews(sectionDiv));
		return sectionHeadlines;
	}

	public List<String> getSectionNewsHref(String sectionDiv) {
		List<String> news = new ArrayList<>();
		news= VerificationUtil.getLinkHrefList(newsPageObjects.getSectionNews(sectionDiv));
		return news;
	}

	public List<WebElement> getTopSectionNews() {
		return pwaListingPageMethods.getListOfHeadings();
	}

	public List<String> getTopSectionNewsHref() {
		List<String> topNews = new ArrayList<String>();
		topNews = VerificationUtil.getLinkHrefList(pwaListingPageMethods.getMoreHeadlines());
		return topNews;
	}
}
