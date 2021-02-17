package wap.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import wap.pageobjects.WealthPageObjects;
import web.pagemethods.WebBaseMethods;

public class WealthPageMethods {
	private WapListingPageMethods wapListingPageMethods;
	private WealthPageObjects wealthPageObjects;

	public WealthPageMethods(WebDriver driver) {
		wapListingPageMethods = new WapListingPageMethods(driver);
		wealthPageObjects = PageFactory.initElements(driver, WealthPageObjects.class);
	}

	public List<String> getSubSectionStoriesHref(String sectionName) {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods.getListHrefUsingJSE(wealthPageObjects.getSubSectionLinks(sectionName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public List<String> getSubSectionStoriesText(String sectionName) {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods.getListTextUsingJSE(wealthPageObjects.getSubSectionLinks(sectionName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}
	
	public List<String> getSliderSubSectionStoriesHref(String sectionName) {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods.getListHrefUsingJSE(wealthPageObjects.getSliderSubSectionLinks(sectionName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public List<String> getSliderSubSectionStoriesText(String sectionName) {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods.getListTextUsingJSE(wealthPageObjects.getSliderSubSectionLinks(sectionName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}
	
	public List<String> getEditionSubSectionStoriesHref(String sectionName) {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods.getListHrefUsingJSE(wealthPageObjects.getEditionSubSectionLinks(sectionName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public List<String> getEditionSubSectionStoriesText(String sectionName) {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods.getListTextUsingJSE(wealthPageObjects.getEditionSubSectionLinks(sectionName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}
}
