package web.pagemethods;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.Budget2021PageObjects;

public class Budget2021PageMethods {

	private WebDriver driver;
	private Budget2021PageObjects budget2021PageObjects;

	public Budget2021PageMethods(WebDriver driver) {
		this.driver = driver;
		budget2021PageObjects = PageFactory.initElements(driver, Budget2021PageObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
	}

	public List<String> getSectionNewsHref(String sectionDiv) {
		List<String> news = new ArrayList<>();
		try {
			news = WebBaseMethods.getListHrefUsingJSE(budget2021PageObjects.getSectionNews(sectionDiv));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return news;
	}

	public List<String> getAllUrls() {
		List<String> allUrls = new LinkedList<>();
		try {
			allUrls = WebBaseMethods.getListHrefUsingJSE(budget2021PageObjects.getAllUrls());
		} catch (Exception ee) {
			ee.printStackTrace();
			allUrls = new LinkedList<>();
		}
		return allUrls;

	}

	public List<String> getMoreFromSectionLinks() {
		List<String> moreFromUrls = new LinkedList<>();
		try {
			moreFromUrls = WebBaseMethods.getListHrefUsingJSE(budget2021PageObjects.getMoreFromSectionLinks());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return moreFromUrls;

	}

	public List<String> getSectorInFocusSectionLinks(String sectionDiv) {
		List<String> urlsList = new LinkedList<>();
		try {
			urlsList = WebBaseMethods
					.getListHrefUsingJSE(budget2021PageObjects.getSectorInFocusSectionNews(sectionDiv));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return urlsList;

	}

	public List<String> getBudgetTrendingLinks() {
		List<String> urlsList = new LinkedList<>();
		try {
			urlsList = WebBaseMethods.getListHrefUsingJSE(budget2021PageObjects.getBudgetTrendingWidgetLinks());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return urlsList;

	}

	public List<String> getBudgetArchiveLinks() {
		List<String> urlsList = new LinkedList<>();
		try {
			urlsList = WebBaseMethods.getListHrefUsingJSE(budget2021PageObjects.getBudgetArchiveWidgetLinks());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return urlsList;

	}

}
