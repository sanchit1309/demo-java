package wap.pagemethods;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.VerificationUtil;
import wap.pageobjects.RisePageObjects;
import web.pagemethods.WebBaseMethods;

public class RisePageMethods {
	
	private RisePageObjects risePageObjects;
	private WapListingPageMethods wapListingPageMethods;
	
	public RisePageMethods(WebDriver driver){
		risePageObjects = PageFactory.initElements(driver,RisePageObjects.class);
		wapListingPageMethods = new WapListingPageMethods(driver);
	}

	public List<String> getTopSectionHeaders(){
		List<String> topSectionHeaders = new ArrayList<String>();
		try {
			for(WebElement ele: risePageObjects.getRiseSubMenu()){
				topSectionHeaders.add(ele.getText().replaceAll(".*\\n",""));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return topSectionHeaders;
	}
	
	public List<WebElement> getTopSectionNews() {
		List<WebElement> headings = new ArrayList<WebElement>();
		try {
			headings= wapListingPageMethods.getListOfHeadings();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return headings;
	}

	public List<String> getTopSectionNewsHref() {
		List<String> topNews = new ArrayList<String>();
		try {
			topNews = VerificationUtil.getLinkHrefList(wapListingPageMethods.getMoreHeadlines());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return topNews;
	}

	public String getSectionNewsLink(String sectionDiv) {
		String href = "";
		try {
			WebBaseMethods.moveToElement(risePageObjects.getSectionHeading(sectionDiv));
			href = risePageObjects.getSectionHeading(sectionDiv).getAttribute("href");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return href;
	}

	public List<WebElement> getSectionNewsHeadlines(String sectionDiv) {
		List<WebElement> sectionHeadlines = new ArrayList<>();
		//sectionHeadlines.addAll(risePageObjects.getImageLink(sectionDiv));
		try {
			sectionHeadlines.addAll(risePageObjects.getSectionNews(sectionDiv));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sectionHeadlines;
	}

	public List<String> getSectionNewsHref(String sectionDiv) {
		List<String> news = new ArrayList<>();
		try {
			news= VerificationUtil.getLinkHrefList(risePageObjects.getSectionNews(sectionDiv));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return news;
	}


}
