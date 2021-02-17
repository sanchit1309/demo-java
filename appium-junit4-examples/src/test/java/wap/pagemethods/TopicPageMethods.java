package wap.pagemethods;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import wap.pageobjects.TopicPageObjects;
import web.pagemethods.WebBaseMethods;

public class TopicPageMethods {

	private TopicPageObjects topicPageObjects;
	// private WapListingPageMethods wapListingPageMethods;

	public TopicPageMethods(WebDriver driver) {
		topicPageObjects = PageFactory.initElements(driver, TopicPageObjects.class);
		// wapListingPageMethods = new WapListingPageMethods(driver);
	}

	public String getTopicPagHeading() {
		String heading = "";
		try {
			heading = topicPageObjects.getTopicHeading().getText();
		} catch (NoSuchElementException e) {

		}
		return heading;
	}

	// public List<String> getListItems(String menuName){
	// topicPageObjects.get
	// }

	public List<String> getTopicMenuHeader() {
		List<String> menuTabs = new ArrayList<>();
		try {
			WebBaseMethods.scrollElementIntoViewUsingJSE(topicPageObjects.getTopicMenu().get(0));
			menuTabs = VerificationUtil.getLinkTextList(topicPageObjects.getTopicMenu());
		} catch (IndexOutOfBoundsException e) {

		}
		return menuTabs;
	}

	public void openTopicSubMenu(String menuName) {
		for (WebElement menu : topicPageObjects.getTopicMenu()) {
			// WebBaseMethods.moveToElement(tab);
			if (menu.getText().equalsIgnoreCase(menuName)) {
				WebBaseMethods.clickElementUsingJSE(menu);
				break;
			}
		}
	}

	public List<String> getSubMenuListItems(String menuName) {
		List<String> subMenuEntriesHref = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			try {
				WaitUtil.sleep(2000);
				subMenuEntriesHref = WebBaseMethods.getListHrefUsingJSE(topicPageObjects.getMenuItemsList(menuName));
				break;
			} catch (WebDriverException e) {
				e.toString();
				Reporter.log("Trying to recover from a stale element :" + e.getMessage());
			}
		}
		return subMenuEntriesHref;
	}

	public String getNoResultText(String menuName) {
		WaitUtil.sleep(1000);
		String errorMsg = topicPageObjects.getErrorMessage(menuName).getText();
		return errorMsg;
	}

	public boolean hasTopStories() {
		boolean flag = false;
		try {
			topicPageObjects.getTopStory();
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public List<String> getActiveSectionHref() {
		List<String> hrefList = new LinkedList<>();
		try {
			hrefList = WebBaseMethods.getListHrefUsingJSE(topicPageObjects.getActiveListUrl());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return hrefList;

	}

	public boolean clickSectionTab(String sectionName) {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(topicPageObjects.getSectionTab(sectionName));
			
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;
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

	public List<String> getAllErrorLinks(List<String> hrefList) {
		List<String> errorLinks = new LinkedList<String>();
		System.out.println(hrefList);
		System.out.println(hrefList.size());
		hrefList.forEach(href -> {
			try {
				int response = HTTPResponse.checkResponseCode(href);
				System.out.println(href + "-------" + response);
				if (!(response == 200)) {
					errorLinks.add(href);
				}
			} catch (Exception ee) {
				ee.printStackTrace();
				errorLinks.add(href);
			}
		});
		return errorLinks;
	}

	public List<String> getTopSectionHref() {
		List<String> hrefList = new LinkedList<>();
		try {
			hrefList = WebBaseMethods.getListHrefUsingJSE(topicPageObjects.getMainStoryUrl());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return hrefList;

	}

	public boolean checkStatusOfLinkIs200orNot(String href) {
		boolean flag = false;
		try {
			int response = HTTPResponse.checkResponseCode(href);
			if (response == 200) {
				flag = true;
			}
		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return flag;

	}

	public String getCompanyWidgetLink() {

		String url = "";
		try {
			url = WebBaseMethods.getHrefUsingJSE(topicPageObjects.getCompanyLink());
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		System.out.println(url);
		return url;

	}

	public String getCompanyWidgetCurrentPrice() {
		String price = "";
		try {

			price = WebBaseMethods.getTextUsingJSE(topicPageObjects.getCurrentCompanyPrice());
			System.out.println("The price is " + price);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return price;
	}
}
