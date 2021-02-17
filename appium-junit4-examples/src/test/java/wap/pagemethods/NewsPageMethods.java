package wap.pagemethods;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.VerificationUtil;
import wap.pageobjects.NewsPageObjects;
import web.pagemethods.WebBaseMethods;

public class NewsPageMethods {
	private WapListingPageMethods wapListingPageMethods;
	private NewsPageObjects newsPageObjects;

	public NewsPageMethods(WebDriver driver) {
		wapListingPageMethods = new WapListingPageMethods(driver);
		newsPageObjects = PageFactory.initElements(driver, NewsPageObjects.class);
	}

	public List<String> getTopSectionHeaders() {
		List<String> topSectionHeaders = new ArrayList<String>();
		try {
			for (WebElement ele : newsPageObjects.getNewsSubMenu()) {
				topSectionHeaders.add(ele.getText().replaceAll(".*\\n", ""));
			}
		} catch (NoSuchElementException e) {

		}
		return topSectionHeaders;
	}

	public String getSectionNewsLink(String sectionDiv) {
		try {
			WebBaseMethods.moveToElement(newsPageObjects.getSectionHeading(sectionDiv));
			return newsPageObjects.getSectionHeading(sectionDiv).getAttribute("href");
		} catch (WebDriverException e) {
			return "";
		}
	}

	public List<WebElement> getSectionNewsHeadlines(String sectionDiv) {
		List<WebElement> sectionHeadlines = new ArrayList<>();
		sectionHeadlines.addAll(newsPageObjects.getImageLink(sectionDiv));
		sectionHeadlines.addAll(newsPageObjects.getSectionNews(sectionDiv));
		return sectionHeadlines;
	}

	public List<String> getSectionNewsHref(String sectionDiv) {
		List<String> news = new ArrayList<>();
		news = VerificationUtil.getLinkHrefList(newsPageObjects.getSectionNews(sectionDiv));
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