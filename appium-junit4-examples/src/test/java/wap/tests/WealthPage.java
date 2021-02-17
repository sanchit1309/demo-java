package wap.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.MenuPageMethods;
import wap.pagemethods.WapListingPageMethods;
import wap.pagemethods.WealthPageMethods;

public class WealthPage extends BaseTest {
	private String wapUrl;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	private WapListingPageMethods wapListingMethods;
	private WealthPageMethods wealthPageMethods;
	String excelPath = "MenuSubMenuData";
	Map<String, String> testData;
	SoftAssert softAssert;
	boolean flag;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		homePageMethods = new HomePageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
		wapListingMethods = new WapListingPageMethods(driver);
		wealthPageMethods = new WealthPageMethods(driver);

		Assert.assertTrue(openWealthSection(), "Unable to click Wealth tab");
	}

	public boolean openWealthSection() {
		homePageMethods.clickHmaberMenuIcon();
		WaitUtil.sleep(2000);
		flag = menuPageMethods.clickMenuOptionReact("Wealth");
		WaitUtil.sleep(2000);
		wapListingMethods.removeGoogleVignetteAd();
		WaitUtil.sleep(3000);
		if (driver.getCurrentUrl().contains("/personal-finance"))
			flag = true;
		else {
			flag = false;
		}
		return flag;
	}

	@Test(description = "This test verifies the top news section on Wealth page")
	public void verifyTopStoriesSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;

		List<String> topNewsStories = wapListingMethods.getOldViewListingPageTopStoriesText();
		int count = topNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under top section should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> topNewsDup = VerificationUtil.isListUnique(topNewsStories);
		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>- Top News Section has duplicate stories, repeating story(s)->" + topNewsDup);
		wapListingMethods.getOldViewListingPageTopStoriesHref().forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top News link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify wealth page sub section", dataProvider = "subSections", priority = 2)
	public void verifySubSections(String subSection) {
		String sectionName = subSection;
		softAssert = new SoftAssert();
		try {
			int articleCount = 5;

			String sectionHeading = wapListingMethods.getSubSectionHeadingLink(sectionName);
			if (sectionHeading.length() > 0) {
				int responseCode = HTTPResponse.checkResponseCode(sectionHeading);
				softAssert.assertEquals(responseCode, 200, "<br>- The " + subSection + "  heading link <a href="
						+ sectionHeading + ">link</a> is throwing " + responseCode);
			} else {
				softAssert.assertTrue(false, "Section heading link is not shown for the section: " + sectionName);
			}
			List<String> newsStories = wealthPageMethods.getSubSectionStoriesText(sectionName);
			int count = newsStories.size();
			softAssert.assertTrue(count >= articleCount, "<br>- Headlines under " + subSection
					+ " sections should be more than " + articleCount + " in number, instead found " + count);
			List<String> NewsDup = VerificationUtil.isListUnique(newsStories);
			softAssert.assertTrue(NewsDup.isEmpty(),
					"<br>- " + subSection + " Section has duplicate stories, repeating story(s)->" + NewsDup);
			wealthPageMethods.getSubSectionStoriesHref(sectionName).forEach(keyword -> {
				int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
				softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top " + subSection + " link <a href="
						+ keyword + ">link</a> is throwing " + topLinksResponseCode);
			});

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured while checking for section " + sectionName);
		}
		softAssert.assertAll();
	}

	@DataProvider
	public String[] subSections() {
		String[] sections = { "Insure", "Spend", "Borrow", "Earn", "Plan", "Real Estate", "Personal Finance News",
				"P2P" };

		return sections;

	}

	@Test(description = "Verify wealth page slider sub section such as WhatsHot, calculator, Et wealth classroom ", dataProvider = "sliderSubSections", priority = 3)
	public void verifySliderSubSections(String subSection) {
		String sectionName = subSection;
		softAssert = new SoftAssert();
		try {
			int articleCount = 5;

			List<String> newsStories = wealthPageMethods.getSliderSubSectionStoriesHref(sectionName);
			int count = newsStories.size();
			softAssert.assertTrue(count >= articleCount, "<br>- Links under " + subSection
					+ " sections should be more than " + articleCount + " in number, instead found " + count);
			List<String> NewsDup = VerificationUtil.isListUnique(newsStories);
			softAssert.assertTrue(NewsDup.isEmpty(),
					"<br>- " + subSection + " Section has duplicate stories, repeating story(s)->" + NewsDup);
			newsStories.forEach(keyword -> {
				int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
				softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top " + subSection + " link <a href="
						+ keyword + ">link</a> is throwing " + topLinksResponseCode);
			});

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured while checking for section " + sectionName);
		}
		softAssert.assertAll();
	}

	@DataProvider
	public String[] sliderSubSections() {
		String[] sections = { "whatsHot", "calculator", "etNow" };

		return sections;

	}

	@Test(description = "Verify wealth page Edition sub section such as This weeks edition, wealth magazine archive ", dataProvider = "editionSubSections", priority = 4)
	public void verifyEditonSubSections(String subSection) {
		String sectionName = subSection;
		softAssert = new SoftAssert();
		try {
			int articleCount = 4;

			List<String> newsStories = wealthPageMethods.getEditionSubSectionStoriesText(sectionName);
			int count = newsStories.size();
			softAssert.assertTrue(count >= articleCount, "<br>- Links under " + subSection
					+ " sections should be more than " + articleCount + " in number, instead found " + count);
			List<String> NewsDup = VerificationUtil.isListUnique(newsStories);
			softAssert.assertTrue(NewsDup.isEmpty(),
					"<br>- " + subSection + " Section has duplicate stories, repeating story(s)->" + NewsDup);
			wealthPageMethods.getEditionSubSectionStoriesHref(sectionName).forEach(keyword -> {
				int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
				softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top " + subSection + " link <a href="
						+ keyword + ">link</a> is throwing " + topLinksResponseCode);
			});

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured while checking for section " + sectionName);
		}
		softAssert.assertAll();
	}

	@DataProvider
	public String[] editionSubSections() {
		String[] sections = { "weekEdition", "wealthArchive" };

		return sections;

	}
}
