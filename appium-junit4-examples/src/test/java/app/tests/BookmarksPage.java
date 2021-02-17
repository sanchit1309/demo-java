package app.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.AlertsPromptsMethods;
import app.pagemethods.AppListingPageMethods;
import app.pagemethods.BookmarksPageMethods;
import app.pagemethods.HeaderPageMethods;
import app.pagemethods.LoginPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.PrimeSectionMethods;
import app.pagemethods.StoryPageMethods;
import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class BookmarksPage extends BaseTest {

	MenuPageMethods menuPageMethods;
	HeaderPageMethods headerPageMethods;
	AppListingPageMethods appListingPageMethods;
	BookmarksPageMethods bookmarksPageMethods;
	StoryPageMethods storyPageMethods;
	private LoginPageMethods loginPageMethods;
	AppiumDriver<?> appDriver;
	private String menuName = "NRI";
	private SoftAssert softAssert;
	AlertsPromptsMethods alertsPromptsMethods;
	LoginPage loginPage;
	PrimeSectionMethods primeSectionMethods;
	public static String headingOnArticleshow;
	public static String headline;

	@BeforeClass(alwaysRun = true)
	public void launchApp() throws IOException {
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		menuPageMethods = new MenuPageMethods(appDriver);
		headerPageMethods = new HeaderPageMethods(appDriver);
		appListingPageMethods = new AppListingPageMethods(appDriver);
		bookmarksPageMethods = new BookmarksPageMethods(appDriver);
		storyPageMethods = new StoryPageMethods(appDriver);
		loginPageMethods = new LoginPageMethods(appDriver);
		primeSectionMethods = new PrimeSectionMethods(appDriver);
		alertsPromptsMethods = new AlertsPromptsMethods(appDriver);
		Assert.assertTrue(navigateToJobsTab());
	}

	@Test(description = "Verifies Articles in bookmarks section and the section heading", priority = 0, enabled = true)
	public void verifyBookmarksArticleAndHeader() {
		softAssert = new SoftAssert();

		WaitUtil.sleep(12000);
		List<MobileElement> headLinesList = appListingPageMethods.getHeadlineList();
		softAssert.assertTrue(headLinesList.size() > 0, "No news found on " + menuName);
		if (BaseTest.platform.contains("android")) {
			softAssert.assertTrue(bookmarksPageMethods.clickFirstStory(headLinesList),
					"Clicking on first story was not successful");
			headline = headLinesList.get(0).getText();
			bookmarksPageMethods.bookmarkFirstArticle();
			softAssert.assertTrue(navigateToMyLibrary(), "Unable to navigate to My Library page");
			String Header = headerPageMethods.getHeaderText();
			softAssert.assertTrue(Header.equals("My Library"), "<br>-My Library header is not displayed");
			softAssert.assertTrue(bookmarksPageMethods.verifyMyLibraryIcons(),
					"Unable to verify tabs on My library page");
			softAssert.assertTrue(bookmarksPageMethods.getbookmarkedArticle(headline),
					"Article bookmarked is not found in My Library");
		} else {
			int count = storyPageMethods.bookmarkArticle(headLinesList);
			System.out.println(count);
			softAssert.assertTrue(navigateToMyLibrary(), "Unable to navigate to My Library page");
			String Header = headerPageMethods.getHeaderText();
			softAssert.assertTrue(Header.equals("My Library"), "<br>-My Library header is not displayed");
			softAssert.assertTrue(bookmarksPageMethods.verifyMyLibraryIcons(),
					"Unable to verify tabs on My library page");
			int bookMarkArticleCount = bookmarksPageMethods.getBookmarkedCount();
			softAssert.assertEquals(bookMarkArticleCount, count,
					"<br> Mismatch in article bookmark count, actual: " + bookMarkArticleCount + " expected: " + count);
			softAssert.assertEquals(true, true);
		}
		softAssert.assertAll();
	}

	@Test(description = "Verfies deletion of article", priority = 1, dependsOnMethods = {
			"verifyBookmarksArticleAndHeader" }, enabled = false)
	public void verifyArticleDeletionBookmarks() {
		softAssert = new SoftAssert();
		if(BaseTest.platform.contains("android")) {
			softAssert.assertTrue(bookmarksPageMethods.clickBookmarkIconOnArticle(),
					"Unable to unbookmark the article from saved story");
			iAppCommonMethods.navigateBack(appDriver);
			Assert.assertFalse(bookmarksPageMethods.getbookmarkedArticle(headingOnArticleshow),
					"Unbookmarked article is still present in the bookmark list ");
		}
		else {
		softAssert.assertTrue(bookmarksPageMethods.verifyDeleteArticle(), "<br>Unable to delete bookmarked article.");
		}
		softAssert.assertAll();
	}

	@Test(description = "Verfies login through history tab", priority = 2, enabled = true)
	public void verifyLoginviaHistoryTab() {
		boolean status = false;
		softAssert = new SoftAssert();
		if(!BaseTest.platform.contains("android")) {
	    Assert.assertTrue(navigateToMyLibrary(), "Unable to navigate to My Library page");
		}
		softAssert.assertTrue(bookmarksPageMethods.clickHistoryTab(), "<br>Unable to click History tab");
		softAssert.assertTrue(bookmarksPageMethods.clickLoginIcon(), "<br>Unable to click Login icon");
		Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifynosubscriptionUserSignIn", 1);
		WaitUtil.sleep(2000);
		status = loginPageMethods.enterCredentials(TestData.get("Email"), TestData.get("Password"));
		softAssert.assertTrue(status, "Unable to enter login credentials");
		driver.navigate().back();
		if (alertsPromptsMethods.isRatingCloseIconDisplayed()) {
			iAppCommonMethods.tapScreen(appDriver, 0.3, 0.3);
		}
		WaitUtil.sleep(5000);
		if (status) {
			headerPageMethods.clickMenuIcon();
			menuPageMethods.scrollDownToSettingIcon();
			softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
			softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		}
		softAssert.assertAll();
	}

	@Test(description = " Verify bookmark of prime article ", priority = 3, enabled = true)
	public void verifyBookmarkArticleFromPrimeHomepage() {
		Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyActiveUserSignIn", 1);
		boolean status = false;
		softAssert = new SoftAssert();
		headerPageMethods.clickMenuIcon();
		// menuPageMethods.isLoginDisplayed(), "Sign in icon not shown in left
		// menu");
		menuPageMethods.clickSignInIcon();
		status = loginPageMethods.enterCredentials(TestData.get("Email"), TestData.get("Password"));
		WaitUtil.sleep(2000);
		Assert.assertTrue(status, "Unable to enter login credentials");
		WaitUtil.sleep(2000);
		if (alertsPromptsMethods.isRatingCloseIconDisplayed()) {
			iAppCommonMethods.tapScreen(appDriver, 0.3, 0.3);
		}
		Assert.assertTrue(navigateToHome(), "Unable to navigate to Home page");
		WaitUtil.sleep(2000);
		primeSectionMethods.clickPrimeIcon();
		headingOnArticleshow = bookmarksPageMethods.getPrimeArticleHeading();
		System.out.println(" heading ;;;" + headingOnArticleshow);
		Assert.assertTrue(primeSectionMethods.clickOnPrimeListingStory(),
				"Unable to click first story from prime section");
		Assert.assertTrue(bookmarksPageMethods.clickBookmarkIconOnArticle(), " Bookmark prime article failed");
		iAppCommonMethods.navigateBack(appDriver);
		Assert.assertTrue(navigateToMyLibrary(), "Unable to navigate to My Library page");
		softAssert.assertTrue(bookmarksPageMethods.getbookmarkedArticle(headingOnArticleshow),
				"Article bookmarked is not found in My Library");
		softAssert.assertAll();

	}

	@Test(description = "verify unbookmark of article from my Library section ", dependsOnMethods = {
			"verifyBookmarkArticleFromPrimeHomepage" }, priority = 4, enabled = true)
	public void verifyUnbookmarkArticle() {
		softAssert = new SoftAssert();
		if (BaseTest.platform.contains("ios")) {
			softAssert.assertTrue(bookmarksPageMethods.clickRemoveBookmarkIconOnArticle(),
					"<br>Unable to delete bookmarked article.");
		} else {
			softAssert.assertTrue(bookmarksPageMethods.clickBookmarkIconOnArticle(),
					"Unable to unbookmark the article from saved story");
			iAppCommonMethods.navigateBack(appDriver);
		}
		System.out.println(headingOnArticleshow);
		Assert.assertFalse(bookmarksPageMethods.getbookmarkedArticle(headingOnArticleshow),
				"Unbookmarked article is still present in the bookmark list ");
		softAssert.assertAll();

	}

	@Test(description = " Verify history of prime article ", priority = 5, dependsOnMethods = {
			"verifyBookmarkArticleFromPrimeHomepage" }, enabled = true)
	public void verifyHistoryOfPrimeArticle() {
		softAssert = new SoftAssert();
		softAssert.assertTrue(bookmarksPageMethods.clickHistoryTab(), "<br>Unable to click History tab");
		Assert.assertTrue(bookmarksPageMethods.getbookmarkedArticle(headingOnArticleshow),
				" Article is not present in the history list of the user ");
		iAppCommonMethods.navigateBack(appDriver);
		if (alertsPromptsMethods.isRatingCloseIconDisplayed()) {
			iAppCommonMethods.tapScreen(appDriver, 0.3, 0.3);
		}
		iAppCommonMethods.navigateBack(appDriver);
		headerPageMethods.clickMenuIcon();
		menuPageMethods.scrollDownToSettingIcon();
		softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
		softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		softAssert.assertAll();

	}

	@AfterClass(alwaysRun = true)
	public void quit() throws IOException {
		appDriver.quit();
	}

	private boolean navigateToListPage(String menuLabel) {
		headerPageMethods.clickMenuIcon();
		return new MenuPageMethods(appDriver).clickL1L2MenuByLabel(menuLabel, "Top News");
	}

	private boolean navigateToMyLibrary() {
		headerPageMethods.clickMenuIconSwipeUpFirst();
		return menuPageMethods.clickMenuByLabel("My Library");
	}

	private boolean navigateToHome() {
		new HeaderPageMethods(appDriver).clickMenuIconSwipeUpFirst();
		return new MenuPageMethods(appDriver).clickMenuByLabel("Home");
	}

	private boolean navigateToJobsTab() {
		headerPageMethods.clickMenuIcon();
		return new MenuPageMethods(appDriver).clickMenuByLabel("Jobs");
	}
}
