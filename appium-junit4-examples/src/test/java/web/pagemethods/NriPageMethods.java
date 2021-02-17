package web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.HomePageObjects;
import web.pageobjects.NriPageObjects;

public class NriPageMethods {

	private WebDriver driver;
	private HomePageObjects homePageObjects;
	private NriPageObjects nriPageObjects;

	public NriPageMethods(WebDriver driver) {
		this.driver = driver;
		homePageObjects = PageFactory.initElements(driver, HomePageObjects.class);
		nriPageObjects = PageFactory.initElements(driver, NriPageObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
	}

	public NriPageObjects getNriPageObjects() {
		return nriPageObjects;
	}

	public String getNrisInNewsLink() {
		return nriPageObjects.getNrisInNewsLink().getAttribute("href");
	}

	public List<WebElement> getNrisInNewsHeadlines() {
		return nriPageObjects.getNrisInNewsHeadlines();
	}

	public String getNriRealEstateLink() {
		return nriPageObjects.getNriRealEstateLink().getAttribute("href");
	}

	public List<WebElement> getNriRealEstateHeadlines() {
		return nriPageObjects.getNriRealEstateHeadlines();
	}

	public String getNriInvestmentsLink() {
		return nriPageObjects.getNriInvestmentsLink().getAttribute("href");
	}

	public List<WebElement> getNriInvestmentsHeadlines() {
		return nriPageObjects.getNriInvestmentsHeadlines();
	}

	public String getForexAndRemittanceLink() {
		return nriPageObjects.getForexAndRemittanceLink().getAttribute("href");
	}

	public List<WebElement> getForexAndRemittanceHeadlines() {
		return nriPageObjects.getForexAndRemittanceHeadlines();
	}

	public String getVisaAndImmigrationLink() {
		return nriPageObjects.getVisaAndImmigrationLink().getAttribute("href");
	}

	public List<WebElement> getVisaAndImmigrationHeadlines() {
		return nriPageObjects.getVisaAndImmigrationHeadlines();
	}

	public String getWorkingAbroadLink() {
		return nriPageObjects.getWorkingAbroadLink().getAttribute("href");
	}

	public List<WebElement> getWorkingAbroadHeadlines() {
		return nriPageObjects.getWorkingAbroadHeadlines();
	}

	public String getReturningToIndiaLink() {
		return nriPageObjects.getReturningToIndiaLink().getAttribute("href");
	}

	public List<WebElement> getReturningToIndiaHeadlines() {
		return nriPageObjects.getReturningToIndiaHeadlines();
	}

	public String getNriTaxLink() {
		return nriPageObjects.getNriTaxLink().getAttribute("href");
	}

	public List<WebElement> getNriTaxHeadlines() {
		return nriPageObjects.getNriTaxHeadlines();
	}

	public String getNriServicesLink() {
		return nriPageObjects.getNriServicesLink().size() > 0
				? nriPageObjects.getNriServicesLink().get(0).getAttribute("href") : "";
	}

	public List<WebElement> getNriServicesHeadlines() {
		return nriPageObjects.getNriServicesHeadlines();
	}

	public List<String> getNRILatestNewsSectionLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(nriPageObjects.getNriLatestNewsWidget());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}

	public List<String> getTopNewsFromETLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(nriPageObjects.getTopNewsFromETWidget());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}

	public List<String> getTopTrendingLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(nriPageObjects.getTrendingTerms());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}

	public List<String> getTopStoriesLinksNRIPage() {
		List<String> topStories = new LinkedList<>();
		try {
			topStories = WebBaseMethods.getListHrefUsingJSE(nriPageObjects.getTopStoriesOnNriPage());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return topStories;
	}
}
