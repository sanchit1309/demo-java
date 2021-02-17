package pwa.pagemethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import pwa.pageobjects.PwaListingPageObjects;
import web.pagemethods.WebBaseMethods;

public class PwaListingPageMethods {
	
	private PwaListingPageObjects pwaListingPageObjects;
	private WebDriver driver;
	private StoryPageMethods storyPageMethods;
	
	
	public PwaListingPageMethods(WebDriver driver) {
		this.driver = driver;
		pwaListingPageObjects = PageFactory.initElements(driver, PwaListingPageObjects.class);
		storyPageMethods = new StoryPageMethods(driver);
	}
	
	public List<WebElement> getHomeHeadlines() {
		List<WebElement> homeHeadlines = new ArrayList<>();
		try {
			homeHeadlines= pwaListingPageObjects.getNewsHeadings();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return homeHeadlines;
	}
	
	public List<WebElement> getMoreHeadlines() {
		List<WebElement> moreHeadlines = new ArrayList<>();
		moreHeadlines.addAll(getListOfHeadings());
		WaitUtil.sleep(2000);
		moreHeadlines.addAll(pwaListingPageObjects.getMoreNewsList());
		return moreHeadlines;
	} 
	
	public List<WebElement> getListOfHeadings() {
		List<WebElement> topHeadlines = new ArrayList<>();
		WebBaseMethods.slowScroll(15);
		//topHeadlines.addAll(pwaListingPageObjects.getHomeTopStories());
		topHeadlines.addAll(pwaListingPageObjects.getTopPageStories());
		System.out.println("Total headlines fetched from listing page "+topHeadlines.size());
		return topHeadlines;
	}
	
	public Map<String, Boolean> checkRecency(int days) {
		Map<String, Boolean> recencyMap = new HashMap<String, Boolean>();
		String urlMain;
		urlMain = driver.getCurrentUrl();
		recencyMap = checkHomeTabRecency(days);
		WebBaseMethods.navigateTimeOutHandle(urlMain);
		return recencyMap;
	}
	public Map<String, Boolean> checkHomeTabRecency(int days) {
		Map<String, Boolean> tabRecencyMap = new HashMap<String, Boolean>();
		boolean status = false;
		try {
			Map<String, String> headlineLinks = getHeadlinesLink(getMoreHeadlines());
			for (Map.Entry<String, String> entry : headlineLinks.entrySet()) {
				if (entry.getValue().contains("morning-brief") || !entry.getValue().contains(".cms"))
					continue;
				status = checkRecencyArticle(entry.getValue(), days);
				tabRecencyMap.put(entry.getKey(), status);
				if (status == false)
					Reporter.log("Old Article :: " + entry.getKey());
			}
			return tabRecencyMap;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Check date of the article
	 * 
	 * @param URL
	 * @param days
	 * @return
	 */
	public boolean checkRecencyArticle(String URL, int days) {
		try {
			driver.get(URL);
			String date = storyPageMethods.getArticleDate();
			return VerificationUtil.isLatest(date, days);
		} catch (Exception e) {
			return false;
		}

	}
	
	public Map<String, String> getHeadlinesLink(List<WebElement> headlineList) {
		Map<String, String> headlineLinks = new HashMap<>();
		for (WebElement ele : headlineList) {
			String eleText = "";
			eleText = WebBaseMethods.getTextUsingJSE(ele).length() > 0 ? ele.getText() : "Top Headline";

			if (!headlineLinks.containsKey(eleText)) {
				headlineLinks.put(eleText, WebBaseMethods.getHrefUsingJSE(ele));
			}
		}
		return headlineLinks;
	}
}
