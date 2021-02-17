package web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.HomePageObjects;
import web.pageobjects.WealthPageObjects;

public class WealthPageMethods {
	private WebDriver driver;
	private HomePageObjects homePageObjects;
	private WealthPageObjects wealthPageObjects;

	public WealthPageMethods(WebDriver driver) {
		this.driver = driver;
		homePageObjects = PageFactory.initElements(driver, HomePageObjects.class);
		wealthPageObjects = PageFactory.initElements(driver, WealthPageObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
	}

	public WealthPageObjects getWealthPageObjects() {
		return wealthPageObjects;
	}

	public String getTaxLink() {
		return wealthPageObjects.getTaxLink().getAttribute("href");
	}

	public List<WebElement> getTaxHeadlines() {
		return wealthPageObjects.getTaxHeadlines();
	}

	public String getSaveLink() {
		return wealthPageObjects.getSaveLink().getAttribute("href");
	}

	public List<WebElement> getSaveHeadlines() {
		return wealthPageObjects.getSaveHeadlines();
	}

	public String getInsureLink() {
		return wealthPageObjects.getInsureLink().getAttribute("href");
	}

	public List<WebElement> getInsureHeadlines() {
		return wealthPageObjects.getInsureHeadlines();
	}

	public String getInvestLink() {
		return wealthPageObjects.getInvestLink().getAttribute("href");
	}

	public List<WebElement> getInvestHeadlines() {
		return wealthPageObjects.getInvestHeadlines();
	}

	public String getSpendLink() {
		return wealthPageObjects.getSpendLink().getAttribute("href");
	}

	public List<WebElement> getSpendHeadlines() {
		return wealthPageObjects.getSpendHeadlines();
	}

	public String getBorrowLink() {
		return wealthPageObjects.getBorrowLink().getAttribute("href");
	}

	public List<WebElement> getBorrowHeadlines() {
		return wealthPageObjects.getBorrowHeadlines();
	}

	public String getEarnLink() {
		return wealthPageObjects.getEarnLink().getAttribute("href");
	}

	public List<WebElement> getEarnHeadlines() {
		return wealthPageObjects.getEarnHeadlines();
	}

	public String getPlanLink() {
		return wealthPageObjects.getPlanLink().getAttribute("href");
	}

	public List<WebElement> getPlanHeadlines() {
		return wealthPageObjects.getPlanHeadlines();
	}

	public String getRealEstateLink() {
		return wealthPageObjects.getRealEstateLink().getAttribute("href");
	}

	public List<WebElement> getRealEstateHeadlines() {
		return wealthPageObjects.getRealEstateHeadlines();
	}

	public String getP2PLink() {
		return wealthPageObjects.getP2pLink().getAttribute("href");
	}

	public List<WebElement> getP2PHeadlines() {
		return wealthPageObjects.getP2pHeadlines();
	}
	
	public List<String> getWealthKeywordsLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(wealthPageObjects.getKeywordsUnderTopStories());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}
	
	public List<String> getTopStoriesOnWealthPage() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(wealthPageObjects.getTopStoriesOnWealthPage());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}
	
	public List<String> getWhatsHotSectionLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(wealthPageObjects.getWhatsHotSectionLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}
	
	public List<String> getThisWeeksEditionListLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(wealthPageObjects.getThisWeeksEditionListLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}
	
	public List<String> getEtWealthClassroomWidgetLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(wealthPageObjects.getEtWealthClassroomWidgetLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}
	
	public List<String> getEtWealthMagazineArchiveWidgetLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(wealthPageObjects.getEtWealthMagazineArchiveWidgetLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}
	
	public List<String> getNewsAndUpdatesWidgetLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(wealthPageObjects.getNewAndUpdatesWidgetLinksRHS());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}
	
	public List<String> getKnowAllAboutLinksOfSection(String sectionName) {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(wealthPageObjects.getKnowAllAboutLinksOfSection(sectionName));

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}
	
	

}
