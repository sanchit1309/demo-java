package wap.tests;

import java.io.IOException;
import java.util.Arrays;
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
import wap.pagemethods.MutualFundsPageMethods;
import wap.pagemethods.WapListingPageMethods;

public class MutualFundsPage extends BaseTest {
	private String wapUrl;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	private WapListingPageMethods wapListingMethods;
	MutualFundsPageMethods mutualFundsPageMethods;
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
		mutualFundsPageMethods = new MutualFundsPageMethods(driver);

		Assert.assertTrue(openMutualFundsSection(), "Unable to click Mutual Funds tab");
	}

	public boolean openMutualFundsSection() {
		homePageMethods.clickHmaberMenuIcon();
		WaitUtil.sleep(2000);
		flag = menuPageMethods.clickMenuOptionReact("MF");
		WaitUtil.sleep(2000);
		wapListingMethods.removeGoogleVignetteAd();
		WaitUtil.sleep(3000);
		if (driver.getCurrentUrl().contains("/mutual-funds"))
			flag = true;
		else {
			flag = false;
		}
		return flag;
	}

	@Test(description = "This test verifies the top news section on Mutual funds page")
	public void verifyTopStoriesSection() {
		softAssert = new SoftAssert();
		int articleCount = 15;

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

	@Test(description = "Verify mutual funds page sub section", dataProvider = "subSections", priority = 2)
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
			List<String> newsStories = mutualFundsPageMethods.getSubSectionStoriesText(sectionName);
			int count = newsStories.size();
			softAssert.assertTrue(count >= articleCount, "<br>- Headlines under " + subSection
					+ " sections should be more than " + articleCount + " in number, instead found " + count);
			List<String> NewsDup = VerificationUtil.isListUnique(newsStories);
			softAssert.assertTrue(NewsDup.isEmpty(),
					"<br>- " + subSection + " Section has duplicate stories, repeating story(s)->" + NewsDup);
			mutualFundsPageMethods.getSubSectionStoriesHref(sectionName).forEach(keyword -> {
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
		String[] sections = { "Mutual Funds News", "Mutual Funds Learn" };

		return sections;

	}

	@Test(description = "Verify top mutual funds widget sub section on MF page ", dataProvider = "topMutualFundsSubSections", priority = 3)
	public void verifyTopMutualFundsSubSections(String subSection) {
		String sectionName = subSection;
		softAssert = new SoftAssert();
		try {
			int articleCount = 1;

			List<String> newsStories = mutualFundsPageMethods.getTopMutualFundsSchemeText(sectionName);
			int count = newsStories.size();
			softAssert.assertTrue(count >= articleCount, "<br>- Schemes under " + subSection
					+ " sections should be more than " + articleCount + " in number, instead found " + count);
			List<String> NewsDup = VerificationUtil.isListUnique(newsStories);
			softAssert.assertTrue(NewsDup.isEmpty(),
					"<br>- " + subSection + " Section has duplicate schemes, repeating scheme(s)->" + NewsDup);
			mutualFundsPageMethods.getTopMutualFundsSchemeHref(sectionName).forEach(keyword -> {
				int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
				softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top " + subSection + " scheme <a href="
						+ keyword + ">link</a> is throwing " + topLinksResponseCode);
			});

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured while checking for section " + sectionName);
		}
		softAssert.assertAll();
	}

	@DataProvider
	public String[] topMutualFundsSubSections() {
		String[] sections = { "equity", "debt", "hybrid", "commodities", "all" };

		return sections;

	}

	@Test(description = "Verify top ETF widget sub section on MF page ", dataProvider = "topETFSubSections", priority = 4)
	public void verifyToETFSubSections(String subSection) {
		String sectionName = subSection;
		softAssert = new SoftAssert();
		try {
			int articleCount = 1;
			List<String> indexTabs = Arrays.asList("BSE", "NSE");
			indexTabs.forEach(tab -> {
				boolean flag = mutualFundsPageMethods.clickETFSubSectionNseBseTab(sectionName, tab);
				softAssert.assertTrue(flag,
						"The " + tab + " tab is not clickable under the sub section " + sectionName);
				if (flag) {
					List<String> newsStories = mutualFundsPageMethods.getTopETFSchemeText(sectionName);
					int count = newsStories.size();
					softAssert.assertTrue(count >= articleCount, "<br>- Schemes under tab " + tab + " of " + subSection
							+ " sections should be more than " + articleCount + " in number, instead found " + count);
					List<String> NewsDup = VerificationUtil.isListUnique(newsStories);
					softAssert.assertTrue(NewsDup.isEmpty(), "<br>- tab " + tab + " of " + subSection
							+ " Section has duplicate schemes, repeating scheme(s)->" + NewsDup);
					mutualFundsPageMethods.getTopETFSchemeHref(sectionName).forEach(keyword -> {
						int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
						softAssert.assertEquals(topLinksResponseCode, 200, "<br>- The tab " + tab + " of " + subSection
								+ " scheme <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
					});
				}
			});

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured while checking for section " + sectionName);
		}
		softAssert.assertAll();
	}

	@DataProvider
	public String[] topETFSubSections() {
		String[] sections = { "all", "gold", "banking", "index", "liquid", "international" };

		return sections;

	}

}
