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

public class PanachePage extends BaseTest {
	private String wapUrl;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	private WapListingPageMethods wapListingMethods;
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

		Assert.assertTrue(openPanacheSection(), "Unable to click Panache tab");
	}

	public boolean openPanacheSection() {
		homePageMethods.clickHmaberMenuIcon();
		WaitUtil.sleep(2000);
		flag = menuPageMethods.clickMenuOptionReact("Panache");
		WaitUtil.sleep(2000);
		wapListingMethods.removeGoogleVignetteAd();
		WaitUtil.sleep(3000);
		if (driver.getCurrentUrl().contains("/panache"))
			flag = true;
		else {
			flag = false;
		}
		return flag;
	}

	@Test(description = "This test verifies the top news section on panache page")
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

	@Test(description = "Verify panache page sub section", dataProvider = "subSections", priority = 2)
	public void verifyPanacheSubSection(String subSection) {
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
			List<String> newsStories = wapListingMethods.getPanaceListingSubSectionPageStoriesText(sectionName);
			int count = newsStories.size();
			softAssert.assertTrue(count >= articleCount, "<br>- Headlines under " + subSection
					+ " sections should be more than " + articleCount + " in number, instead found " + count);
			List<String> NewsDup = VerificationUtil.isListUnique(newsStories);
			softAssert.assertTrue(NewsDup.isEmpty(),
					"<br>- " + subSection + " Section has duplicate stories, repeating story(s)->" + NewsDup);
			wapListingMethods.getPanaceListingSubSectionPageStoriesHref(sectionName).forEach(keyword -> {
				int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
				softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top " + subSection + " link <a href="
						+ keyword + ">link</a> is throwing " + topLinksResponseCode);
			});
			String sectionMoreLink = wapListingMethods.getSubSectionMoreLink(sectionName);
			if (sectionMoreLink.length() > 0) {
				int responseCode = HTTPResponse.checkResponseCode(sectionMoreLink);
				softAssert.assertEquals(responseCode, 200, "<br>- The " + subSection + " more link <a href="
						+ sectionMoreLink + ">link</a> is throwing " + responseCode);
			} else {
				softAssert.assertTrue(false, "Section more link is not show for the section: " + sectionName);
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured while checking for section " + sectionName);
		}
		softAssert.assertAll();
	}

	@DataProvider
	public String[] subSections() {
		String[] sections = { "Tech and Gadgets", "Worklife", "Cars & Bikes", "Lifestyle", "Health", "People",
				"Entertainment", "Books", "Food & Drinks", "Humour", "City Life" };

		return sections;

	}

}
