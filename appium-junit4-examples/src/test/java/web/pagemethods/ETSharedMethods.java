package web.pagemethods;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.ETSharedObjects;

import static common.launchsetup.BaseTest.driver;

public class ETSharedMethods {
	private static ETSharedObjects etSharedObjects;

	public static void init(WebDriver driver) {
		etSharedObjects = PageFactory.initElements(driver, ETSharedObjects.class);
	}

	public static void declineNotifications() {
		try {
			WaitUtil.waitForLoad(driver);
			// WaitUtil.explicitWaitByElementToBeClickable(driver, 20,
			// etSharedObjects.getNotificationNotNow());
			WaitUtil.turnOffImplicitWaits(driver);
			WebBaseMethods.clickElementUsingJSE(etSharedObjects.getNotificationNotNow());
			WaitUtil.turnOnImplicitWaits(driver);
		} catch (WebDriverException exception) {
			// do nothing
		}
	}

	public static void clickETLinkInterstitialPage() {
		try {
			WaitUtil.turnOffImplicitWaits(driver);
			etSharedObjects.getEtLinkInterstitialAdvt().click();
			WaitUtil.turnOnImplicitWaits(driver);
		} catch (NoSuchElementException e) {
			// do nothing
		}
	}

	public static boolean isGDPRShown() {
		WaitUtil.turnOffImplicitWaits(driver);
		boolean flag=etSharedObjects.getGDPR().size() > 0;
		System.out.println("isGDPR:" +flag);
		WaitUtil.turnOnImplicitWaits(driver);
		return flag;
	}

	public static List<String> getDisplayedElementsList(List<WebElement> li) {
		List<String> headLinesDisplayed = new LinkedList<>();
		li.forEach(action -> {
			if (action.isDisplayed())
				headLinesDisplayed.add(action.getText());
		});
		return headLinesDisplayed;
	}

	public static List<WebElement> getArticleListNotToBeMissedUrls() {
		return etSharedObjects.getNotTobeMissedHeadlinesurls();
	}

	public static List<WebElement> getArticleListNotToBeMissedHeadlines() {
		WebBaseMethods.scrollToBottom();
		List<WebElement> el = etSharedObjects.getNotTobeMissedHeadlinesList();
		WebBaseMethods.scrollElementIntoViewUsingJSE(el.get(el.size() - 1));
		WaitUtil.sleep(2000);
		WebBaseMethods.scrollUpDownUsingJSE(0, 150);
		WaitUtil.sleep(2000);
		return etSharedObjects.getNotTobeMissedHeadlinesList();
	}

	public static List<String> getBrokenTags() {
		List<String> brokenLinkText = new LinkedList<>();
		List<WebElement> foundList = etSharedObjects.getBrokenTags();
		if (foundList.size() > 0) {
			brokenLinkText.addAll(WebBaseMethods.getListTextUsingJSE(foundList));
		}
		return brokenLinkText;
	}

	public static List<WebElement> getH1HeadlineList() {
		return etSharedObjects.geth1HeadlineList();
	}

	public List<WebElement> getPollQuestion() {
		return etSharedObjects.getPollQuestion();
	}

	public static List<String> getTopNav() {
		String tabsNotToBeConsidered = "Mobile Apps, Home";
		List<String> toIgnore = Arrays.asList(tabsNotToBeConsidered.split("\\s*,\\s*"));
		List<WebElement> li = etSharedObjects.getTopNav();
		List<String> liString = new LinkedList<>();
		for (WebElement el : li) {
			if (el.getText().equals("More")) {
				WebBaseMethods.JSHoverOver(el);
				List<WebElement> sections = el.findElements(By.xpath(".//div/a[contains(@class,'subsec1')]"));
				for (WebElement e : sections) {
					liString.add(e.getAttribute("href"));
				}
			} else if (toIgnore.stream().anyMatch(p -> p.equalsIgnoreCase(el.getText()))) {

			} else {
				liString.add(el.findElement(By.xpath("./a")).getAttribute("href"));
			}
		}
		return liString;
	}

	public static List<String> getSecondLevelNav() {
		List<WebElement> li = etSharedObjects.getTopNav();
		List<String> liString = new LinkedList<>();
		if (!(li.size() > 0))
			return liString;
		List<WebElement> sections = li.get(0).findElements(By.xpath("./../following-sibling::nav[@id='subSecNav']//a"));
		for (WebElement e : sections) {
			liString.add(e.getAttribute("href"));
		}

		return liString;
	}
}
