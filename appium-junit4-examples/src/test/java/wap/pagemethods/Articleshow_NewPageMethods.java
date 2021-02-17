package wap.pagemethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import wap.pageobjects.Articleshow_NewPageObjects;
import web.pagemethods.WebBaseMethods;

public class Articleshow_NewPageMethods {
	private WebDriver driver;
	private Articleshow_NewPageObjects articleshow_NewPageObjects;
	private LoginPageMethods loginPageMethods;

	public Articleshow_NewPageMethods(WebDriver driver) {
		this.driver = driver;
		loginPageMethods = new LoginPageMethods(driver);
		articleshow_NewPageObjects = PageFactory.initElements(driver, Articleshow_NewPageObjects.class);
	}

	public String getArticleHeading() {
		try {
			return articleshow_NewPageObjects.getArticleHeading().getText();
		} catch (Exception ee) {
			ee.printStackTrace();
			return "Article heading not found";
		}
	}

	public boolean clickHeaderSharingButton() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(articleshow_NewPageObjects.getFacebookSharingIcon());
			

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean verifyFbLogin(String email, String password) {
		boolean flag = false;
		try {
			loginPageMethods.facebookActivity(email, password);
			WaitUtil.sleep(3000);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, articleshow_NewPageObjects.getFbPostButton());
			if (articleshow_NewPageObjects.getFbPostButton().isDisplayed())
				flag = true;
		} catch (Exception e) {
			System.out.println(driver.getCurrentUrl());
		}
		return flag;
	}

	public String getFbSharedHeading() {
		try {
			return articleshow_NewPageObjects.getFacebookSharingIcon().getText();
		} catch (Exception ee) {
			ee.printStackTrace();
			return "Shared article heading is not found on the Facebook";
		}

	}

	public boolean verifySharingMessage(String articleHeading, String fbArticleHeading) {
		boolean flag = false;
		boolean compare1 = false;
		boolean compare2 = false;
		try {
			compare1 = articleHeading.contains(fbArticleHeading);
			compare2 = fbArticleHeading.contains(articleHeading);
			if (compare1 || compare2) {
				flag = true;

			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari")) {
				WebBaseMethods.switchToParentClosingChilds();
			} else {
				driver.close();
				WaitUtil.sleep(5000);
				WAPHelper.genericSwitchToWebView((AppiumDriver<?>) driver);
			}
		}
		return flag;
	}

	public List<WebElement> getSectionNewsHeadlines(String sectionDiv) {
		List<WebElement> sectionHeadlines = new ArrayList<>();
		// sectionHeadlines.addAll(risePageObjects.getImageLink(sectionDiv));
		try {
			sectionHeadlines.addAll(articleshow_NewPageObjects.getSectionNews(sectionDiv));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sectionHeadlines;
	}

	public List<String> getSectionNewsHref(String section) {
		List<WebElement> news = new LinkedList<>();
		List<String> hrefList = new LinkedList<>();
		news.addAll(articleshow_NewPageObjects.getSectionNewsHref(section));
		news.forEach(action -> {
			hrefList.add(action.getAttribute("href"));
		});
		System.out.println(hrefList);
		return hrefList;
	}

	public boolean checkTheFontSize(String size) {
		boolean fontSizeFlag = false;
		String fontSize = "";
		try {
			switch (size) {
			case "small":
				WebBaseMethods.clickElementUsingJSE(articleshow_NewPageObjects.getFontSizeSmall());
				WaitUtil.sleep(2000);
				fontSize = articleshow_NewPageObjects.getArticleSummary().getCssValue("font-size");
				System.out.println(fontSize);
				if (fontSize.equalsIgnoreCase("12.6px"))
					fontSizeFlag = true;
				break;

			case "large":
				WebBaseMethods.clickElementUsingJSE(articleshow_NewPageObjects.getFontSizeLarge());
				WaitUtil.sleep(2000);
				fontSize = articleshow_NewPageObjects.getArticleSummary().getCssValue("font-size");
				System.out.println(fontSize);
				if (fontSize.equalsIgnoreCase("15.4px"))
					fontSizeFlag = true;
				break;

			default:
				WebBaseMethods.clickElementUsingJSE(articleshow_NewPageObjects.getFontSizeNormal());
				WaitUtil.sleep(2000);
				fontSize = articleshow_NewPageObjects.getArticleSummary().getCssValue("font-size");
				System.out.println(fontSize);
				if (fontSize.equalsIgnoreCase("14px"))
					fontSizeFlag = true;
				break;

			}

		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return fontSizeFlag;

	}

	public void openTwitterShare() {
		WebBaseMethods.clickElementUsingJSE(articleshow_NewPageObjects.getTwitterSharingIcon());
		if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari")) {
			WebBaseMethods.switchToChildWindow(5);
		}
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageMethods.getTwitterEmailField());
	}

	public String getArticleSummary() {
		try {
			return WebBaseMethods.getTextUsingJSE(articleshow_NewPageObjects.getArticleHeading());
		} catch (Exception ee) {
			ee.printStackTrace();
			return "Article summary not found";
		}
	}

	public boolean clickArticleCommentBtn() {
		boolean flag = false;
		try {
			flag =  WebBaseMethods.clickElementUsingJSE(articleshow_NewPageObjects.getCommentIcon());
		

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String getArticleSynopsis() {
		String text = "";
		try {

			text = articleshow_NewPageObjects.getArticleSummary().getText();
		} catch (Exception ee) {
			text = "Synopsis of the article is not found";
			ee.printStackTrace();
		}
		return text;

	}

	public String getArticleDateTime() {
		String text = "";
		try {

			text = articleshow_NewPageObjects.getArticleDateTimeText().getText();
		} catch (Exception ee) {
			text = "Date time of the article is not found";
			ee.printStackTrace();
		}
		return text;

	}

	public String getArticleBodyText() {
		String text = "";
		try {

			text = articleshow_NewPageObjects.getArticleTextBody().getText();
		} catch (Exception ee) {
			text = "Article body text of the article is not found";
			ee.printStackTrace();
		}
		return text;

	}

	public String getArticleImageSynopsis() {
		String text = "";
		try {

			text = articleshow_NewPageObjects.getArticleFigCaption().getText();
		} catch (Exception ee) {
			text = "Figure caption of the article is not found";
			ee.printStackTrace();
		}
		return text;

	}

	public Map<String, String> getArticleAllElements() {
		Map<String, String> articleDetails = new HashMap<>();

		articleDetails.put("articleTitle", getArticleHeading());
		articleDetails.put("articleSynopsis", getArticleSynopsis());
		articleDetails.put("articleFigureSynopsis", getArticleImageSynopsis());
		// articleDetails.put("articleByLineText", getArticleByLineText());
		articleDetails.put("articleTimeLineText", getArticleDateTime());
		articleDetails.put("articleBodyText", getArticleBodyText());

		return articleDetails;
	}

}
