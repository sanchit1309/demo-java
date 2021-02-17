package prime.wap.pagemethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import prime.wap.pageobjects.PrimeHomePageObjects;
import web.pagemethods.WebBaseMethods;

public class PrimeHomePageMethods {

	private WebDriver driver;
	PrimeHomePageObjects primeHomePageObjects;

	public PrimeHomePageMethods(WebDriver driver) {
		primeHomePageObjects = PageFactory.initElements(driver, PrimeHomePageObjects.class);
		this.driver = driver;
	}

	public List<String> getSectionStoriesHrefList(String sectionName) {
		List<String> hrefList = new LinkedList<>();
		try {
			hrefList = VerificationUtil.getLinkHrefList(primeHomePageObjects.getSectionStoriesHref(sectionName));
			System.out.println(hrefList);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public Map<String, String> getViewAllFromSectionNameAndURL(String sectionName) {
		Map<String, String> mp = new HashMap<String,String>();
		try {
			mp.put(primeHomePageObjects.getViewAllFromSectionLink(sectionName).getText(), primeHomePageObjects.getViewAllFromSectionLink(sectionName).getAttribute("href"));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return mp;
	}

	public List<String> getAllErrorLinks(List<String> hrefList) {

		List<String> errorLinks = new LinkedList<String>();
		System.out.println(hrefList);
		System.out.println(hrefList.size());
		hrefList.forEach(href -> {
			try {
				int response = HTTPResponse.checkResponseCode(href);
				System.out.println(href + "-------" + response);
				if (!(response == 200)) {
					errorLinks.add(href + " is throwing " + response);
				}
			} catch (Exception ee) {
				ee.printStackTrace();
				errorLinks.add("Exception occured while checking for " + href);
			}
		});
		return errorLinks;
	}

	public List<String> checkIfListIsUnique(List<String> hrefList) {
		List<String> dupLinks = new LinkedList<String>();
		try {
			dupLinks = VerificationUtil.isListUnique(hrefList);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return dupLinks;
	}

	public List<String> getOurTeamAuthorsLink() {
		List<String> hrefList = new LinkedList<>();
		try {
			hrefList = VerificationUtil.getLinkHrefList(primeHomePageObjects.getOurTeamAuthorLinks());
			System.out.println(hrefList);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public List<String> getOurEsteemedReadersList() {
		List<String> hrefList = new LinkedList<>();
		try {
			hrefList = VerificationUtil.getLinkTextList(primeHomePageObjects.getOurEsteemedReadersSectionList());
			System.out.println(hrefList);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public List<String> getPopularCategoriesSectionLinks() {
		List<String> hrefList = new LinkedList<>();
		try {
			hrefList = VerificationUtil.getLinkHrefList(primeHomePageObjects.getPopularCategoriesSectionLink());
			System.out.println(hrefList);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public List<String> getPopularCategoriesSectionLinksText() {
		List<String> hrefList = new LinkedList<>();
		try {
			hrefList = WebBaseMethods.getListTextUsingJSE(primeHomePageObjects.getPopularCategoriesSectionLinkText());
			System.out.println(hrefList);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public boolean isSubscribeButtonDisplaying() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.isElementVisible(primeHomePageObjects.getSubscribeBtn(), 10);

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isSignInButtonDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isElementVisible(primeHomePageObjects.getSignInBtn(), 4);

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isStartYourMembershipButtonDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isElementVisible(primeHomePageObjects.getStartYourMembershipBtn(), 4);

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public Map<String, String> getL1CategoryNameAndStoryURLList(String sectionName) {
		List<WebElement> hrefList = new LinkedList<>();
		Map<String,String> mp = new HashMap<String,String>();
		try {
			hrefList = primeHomePageObjects.getSectionStoriesHref(sectionName);
			for(WebElement wb : hrefList )
			{
				mp.put(wb.getAttribute("href"),primeHomePageObjects.getL1CategoryName(wb).getText());
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return mp;
	}

	public Map<String, String> getMinuteReadAndStoryURLList(String sectionName) {
		List<WebElement> hrefList = new LinkedList<>();
		Map<String,String> mp = new HashMap<String,String>();
		try {
			hrefList = primeHomePageObjects.getSectionStoriesHref(sectionName);
			for(WebElement wb : hrefList )
			{
				mp.put(wb.getAttribute("href"),primeHomePageObjects.getMinuteReadForWebElement(wb).getText());
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return mp;
	}

	public String getViewAllTitleNameForCategory(String category) {
		String name = "";
		try {
			name = primeHomePageObjects.getViewAllCategoryName(category).getText();
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return name;
	}

	public Map<String, String> getTopStoriesHref() {
		Map<String,String> nameAndHref = new HashMap<String,String>();
		try {

			List<WebElement> hrefs = primeHomePageObjects.getSectionStoriesHref("Top News");
			for(WebElement href : hrefs)
			{
				nameAndHref.put(primeHomePageObjects.getStoryTitleOfWebElement(href).getText(), href.getAttribute("href"));
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return nameAndHref;
	}

	public boolean clickingOnArticleNumberOfTopStories(int number) {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(primeHomePageObjects.getSectionStoriesHref("Top News").get(number));
			flag = true;
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public List<String> getListOfTopStoriesHrefs() {
		List<String> hrefs = new ArrayList<String>();
		try {

			List<WebElement> webElements = primeHomePageObjects.getSectionStoriesHref("Top News");
			for(WebElement href : webElements)
			{
				hrefs.add(href.getAttribute("href"));
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return hrefs;
	}

	public List<String> getListOfPremiumArticlesOfTopStoriesHrefs() {
		List<String> hrefs = new ArrayList<String>();
		try {

			List<WebElement> webElements = primeHomePageObjects.getAllTopStoriesPremiumArticles();
			for(WebElement href : webElements)
			{
				hrefs.add(href.getAttribute("href"));
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return hrefs;
	}

	public List<String> getListOfPrimeArticlesOfTopStoriesHrefs() {
		List<String> hrefs = new ArrayList<String>();
		try {

			List<WebElement> webElements = primeHomePageObjects.getAllTopStoriesPrimeArticles();
			for(WebElement href : webElements)
			{
				hrefs.add(href.getAttribute("href"));
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return hrefs;
	}

	public boolean clickCategoryOnHeader(String category) {
		boolean flag = false;
		try {
			//WaitUtil.explicitWaitByPresenceOfElement(driver, 25, headerPageObjects.getCategoryByNameFromHeader(category));
			WebBaseMethods.isDisplayed(primeHomePageObjects.getCategoryByNameFromHeader(category),25);
			WebBaseMethods.clickElementUsingJSE(primeHomePageObjects.getCategoryByNameFromHeader(category));
			flag = WebBaseMethods.isDisplayed(primeHomePageObjects.getSelectedCategoryFromHeader(category),25);

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

}
