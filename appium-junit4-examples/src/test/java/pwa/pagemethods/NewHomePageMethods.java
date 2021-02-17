package pwa.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import pwa.pageobjects.NewHomePageObjects;
import web.pagemethods.WebBaseMethods;

public class NewHomePageMethods {
	private WebDriver driver;
	private NewHomePageObjects newHomePageObjects;

	public NewHomePageMethods(WebDriver driver) {
		this.driver = driver;
		newHomePageObjects = PageFactory.initElements(driver, NewHomePageObjects.class);
		
	}

	public List<String> getAllTopNewsHref() {
		List<String> topNewsHrefList = new LinkedList<>();
		try {
			List<WebElement> hrefs = newHomePageObjects.getTopStoriesListETHomepage();
			for(WebElement href :hrefs)
			{
				topNewsHrefList.add(href.getAttribute("href"));
			}
		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return topNewsHrefList;

	}

	public List<String> getEtPrimeWidgetStoriesHref() {
		List<String> etPrimeWidgetStoriesHrefList = new LinkedList<>();
		try {
			List<WebElement> hrefs = newHomePageObjects.getEtPrimeWidgetStoriesList();
			for(WebElement href :hrefs)
			{
				etPrimeWidgetStoriesHrefList.add(href.getAttribute("href"));
			}

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return etPrimeWidgetStoriesHrefList;

	}

	public boolean clickFirstETFreeArticle() {
		boolean flag = false;
		List<WebElement> topStoriesFreeArticleLink = new LinkedList<>();
		try {
			topStoriesFreeArticleLink = newHomePageObjects.getTopStoriesFreeArticleListETHomepage();
			if (topStoriesFreeArticleLink.size() > 0) {
				flag = WebBaseMethods.clickElementUsingJSE(topStoriesFreeArticleLink.get(0));
				
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}

		return flag;
	}

	public boolean clickETPrimeNavBarLink() {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(newHomePageObjects.getEtPrimeNavBarLink());
			Thread.sleep(3000);
			if (driver.getCurrentUrl().contains("prime"))
				flag = true;
		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;

		}
		return flag;
	}
	
	/**
	 * Method to click Menu Icon
	 */
	public void clickMenuIcon() {
		WaitUtil.sleep(2000);
		WaitUtil.explicitWaitByElementToBeClickable(driver, 20, newHomePageObjects.getMenuIcon());
		WebBaseMethods.clickElementUsingJSE(newHomePageObjects.getMenuIcon());
	}

}
