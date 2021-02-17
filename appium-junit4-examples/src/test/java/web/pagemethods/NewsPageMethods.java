package web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import busineslogic.BusinessLogicMethods;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pageobjects.HomePageObjects;
import web.pageobjects.NewsPageObjects;

public class NewsPageMethods {
	private WebDriver driver;
	private HomePageObjects homePageObjects;
	private NewsPageObjects newsPageObjects;

	public NewsPageMethods(WebDriver driver) {
		this.driver = driver;
		homePageObjects = PageFactory.initElements(driver, HomePageObjects.class);
		newsPageObjects = PageFactory.initElements(driver, NewsPageObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
	}

	public NewsPageObjects getNewsPageObjects() {
		return newsPageObjects;
	}

	public String getCompanyLink() {
		return newsPageObjects.getCompanyLink().getAttribute("href");
	}

	public List<WebElement> getCompanyHeadlines() {
		return newsPageObjects.getCompanyHeadlines();
	}

	public String getEconomyLink() {
		return newsPageObjects.getEconomyLink().getAttribute("href");
	}

	public List<WebElement> getEconomyHeadlines() {
		return newsPageObjects.getEconomyHeadlines();
	}

	public String getPoliticsAndNationLink() {
		return newsPageObjects.getPoliticsAndNationLink().getAttribute("href");
	}

	public List<WebElement> getPoliticsAndNationHeadlines() {
		return newsPageObjects.getPoliticsAndNationHeadlines();
	}

	public String getBrandwireLink() {
		return newsPageObjects.getBrandwireLink().getAttribute("href");
	}

	public List<WebElement> getBrandwireHeadlines() {
		return newsPageObjects.getBrandwireHeadlines();
	}

	public String getInternationalLink() {
		return newsPageObjects.getInternationalLink().getAttribute("href");
	}

	public List<WebElement> getInternationalHeadlines() {
		return newsPageObjects.getInternationalHeadlines();
	}

	public String getDefenseLink() {
		return newsPageObjects.getDefenceLink().getAttribute("href");
	}

	public List<WebElement> getDefenseHeadlines() {
		return newsPageObjects.getDefenceHeadlines();
	}

	public String getSportsLink() {
		return newsPageObjects.getSportsLink().getAttribute("href");
	}

	public List<WebElement> getSportsHeadlines() {
		return newsPageObjects.getSportsHeadlines();
	}

	public String getIndiaUnlimitedLink() {
		return newsPageObjects.getIndiaUnlimitedLink().getAttribute("href");
	}

	public List<WebElement> getIndiaUnlimitedHeadlines() {
		return newsPageObjects.getIndiaUnlimitedHeadlines();
	}

	public String getEnviornmentLink() {
		return newsPageObjects.getEnviornmentLink().getAttribute("href");
	}

	public List<WebElement> getEnviornmentHeadlines() {
		return newsPageObjects.getEnvironmentHeadlines();
	}

	public String getScienceLink() {
		return newsPageObjects.getScienceLink().getAttribute("href");
	}

	public List<WebElement> getScienceHeadlines() {
		return newsPageObjects.getScienceHeadlines();
	}

	public List<Double> getBenchmarkTextValuesUI() {
		List<Double> actualBenchmarkValueLi = new LinkedList<>();
		WaitUtil.sleep(4000);
		newsPageObjects.getBenchmarkValues().forEach(webEl->{
			try{
			actualBenchmarkValueLi.add(VerificationUtil.parseDouble(webEl.getText()));
			}catch (Exception e) {
				// TODO: handle exception
			}
		});
		return actualBenchmarkValueLi;
	}

	public List<Double> getBenchmarkValuesFromAPI() {
		List<Double> expectBenchmarkValueLi = new LinkedList<>();
		expectBenchmarkValueLi.add(BusinessLogicMethods.getFeedSensexCurrentPrice());
		expectBenchmarkValueLi.add(BusinessLogicMethods.getFeedNiftyCurrentPrice());
		expectBenchmarkValueLi.add(BusinessLogicMethods.getFeedCommodityPrice("GOLD"));
		return expectBenchmarkValueLi;
	}
	public List<WebElement> getMarketLatestNews() {
		return newsPageObjects.getMarketLatestNews();
	}


	public List<String> getTopStoriesNewsPageLinksHref() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods
					.getListHrefUsingJSE(newsPageObjects.getTopStoriesOnNewsPaage());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}
}
