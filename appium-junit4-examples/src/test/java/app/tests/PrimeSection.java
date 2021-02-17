package app.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.HeaderPageMethods;
import app.pagemethods.LoginPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.PrimeSectionMethods;
import app.pagemethods.SearchPageMethods;
import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class PrimeSection extends BaseTest {

	private SoftAssert softAssert;
	private AppiumDriver<?> appDriver;
	private PrimeSectionMethods primeSectionMethods;
	private LoginPageMethods loginPageMethods;
	SearchPageMethods searchPageMethods;
	HeaderPageMethods headerPageMethods;
	MenuPageMethods menuPageMethods;

	@BeforeClass(alwaysRun = true)
	public void launchApp() throws IOException {
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		primeSectionMethods = new PrimeSectionMethods(appDriver);
		loginPageMethods = new LoginPageMethods(appDriver);
		headerPageMethods = new HeaderPageMethods(appDriver);
		menuPageMethods = new MenuPageMethods(appDriver);
		searchPageMethods = new SearchPageMethods(appDriver);
		Assert.assertTrue(primeSectionMethods.clickPrimeIcon(), "Prime icon not clickable");
		WaitUtil.sleep(5000);
	}

	public void doRelaunch() {
		appDriver.resetApp();
		iAppCommonMethods.handleWelcomeScreenAlerts();
		Assert.assertTrue(primeSectionMethods.clickPrimeIcon(), "Prime icon not clickable");
		WaitUtil.sleep(5000);

	}

	@Test(description = "This test case verified signing in user on Prime", priority = 0)
	public void getLogin() {
		Map<String, String> testData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyPrimeLogin", 1);
		softAssert = new SoftAssert();
		primeSectionMethods.clickPrimeIcon();
		softAssert.assertTrue(primeSectionMethods.clickPrimeHomepageFirstStory(),
				"Unable to click first story from prime section");
		softAssert.assertTrue(primeSectionMethods.primeSignInLinkClick("Already a member? Sign in Now"),
				"Unable to click prime sign in link");
		softAssert.assertTrue(loginPageMethods.enterCredentials("sandy.crtr4886@gmail.com", "Sandy@12345"),
				"Unable to sign in user");
		softAssert.assertFalse(primeSectionMethods.verifyPrimeSubscribeWidget(),
				"Subscribe ET prime widget displayed after login");
		WaitUtil.sleep(1000);
		iAppCommonMethods.navigateBack(appDriver);
		softAssert.assertAll();
	}

	@Test(description = "This testcase verifies the browse section", priority = 1,enabled=true)
	public void verifyBrowseSection() {
		softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		primeSectionMethods.clickPrimeIcon();
		softAssert.assertTrue(primeSectionMethods.clickBrowseTab(), "Browse tab is not clicked");

		String sectionsName = "Consumer, Corporate Governance, Economy + policy, Environment, Fintech + BFSI,Infrastructure,Media + communications,Money + markets,Transportation";
		String sections = primeSectionMethods.browseSectionList(sectionsName);

		List<String> sectionName = Arrays.asList(sections.split("\\s*,\\s*"));
		sectionName.forEach(section -> {
			primeSectionMethods.clickPrimeIcon();
			primeSectionMethods.clickBrowseTab();
			iAppCommonMethods.scrollUpToElement(section);
			softAssert.assertTrue(primeSectionMethods.clickBrowseSectionLink(section),
					"Browse section " + section + " is not clicked");
			String headerText = new HeaderPageMethods(appDriver).getHeaderText();
			boolean isTextMatching = headerText.equalsIgnoreCase(section);
			System.out.println(headerText + " -- " + isTextMatching);
			softAssert.assertTrue(isTextMatching,
					"Not landing on expected page, actual:" + headerText + " ,expected:" + section);
			softAssert.assertTrue(primeSectionMethods.getPrimeSectionStoriesNumber() > 0,
					"Stories are not shown under the section " + section);
			iAppCommonMethods.swipeDown();
			iAppCommonMethods.navigateBack(appDriver);
			WaitUtil.sleep(1000);

		});

		softAssert.assertAll();

	}

	@Test(description = "This testcase verifies the prime homepage sections", priority = 2,enabled=true)
	public void verifyPrimeHomepageSections() {
		softAssert = new SoftAssert();
		Assert.assertTrue(primeSectionMethods.goToHomeTabFromTop(), "Unable to click home tab");
		String sectionNames = "CURRENT EDITION, PREVIOUS EDITION, CONSUMER, FEATURED COMMENTS, CORPORATE GOVERNANCE, ECONOMY + POLICY, ENERGY, ENVIRONMENT, FINTECH + BFSI";
		List<String> sectionName = Arrays.asList(sectionNames.split("\\s*,\\s*"));
		sectionName.forEach(section -> {
			softAssert.assertTrue(primeSectionMethods.scrollToSection(section),
					"The section " + section + " is not shown on the prime homepage");

		});

		softAssert.assertAll();

	}

	@Test(description = "This testcase verifies the prime articleshow", priority = 3,enabled=true)
	public void verifyPrimeArticleshow() {
		softAssert = new SoftAssert();
		primeSectionMethods.goToHomeTabFromTop();
		WaitUtil.sleep(1000);
		softAssert.assertTrue(primeSectionMethods.goToHomeTabFromTop(), "Prime tab is not clickable ");
		//iAppCommonMethods.scrollDown();
		int allowedStalness = getStalness();
		List<MobileElement> primeArticles = primeSectionMethods.getPrimeHomepageStories();
		Assert.assertTrue(primeSectionMethods.getPrimeHomepageStories().size() > 0,
				"Prime stories are not shown on Prime homepage");
		primeArticles.forEach(article -> {
			String articleheading = article.getText();
			article.click();
			WaitUtil.sleep(2000);
			softAssert.assertTrue(primeSectionMethods.primeArticleHeadline().length() > 0,
					"The article heading is not shown on for the Prime article " + articleheading);
			softAssert.assertTrue(primeSectionMethods.primeArticleshowSummary().length() > 0,
					"The article summary is not shown on for the Prime article " + articleheading);
			softAssert.assertTrue(primeSectionMethods.primeArticleDate().length() > 0,
					"The article date is not shown on for the Prime article " + articleheading);
			softAssert.assertTrue(primeSectionMethods.primeArticleshowAuthor().length() > 0,
					"The article author name is not shown on for the Prime article " + articleheading);

			String publishDate = primeSectionMethods.getPrimeArticleshowDateText();

			System.out.println(publishDate);
			if (publishDate != null) {
				softAssert.assertTrue(VerificationUtil.isLatest(publishDate, allowedStalness),
						"The article date is not of the current date for the prime article " + articleheading);
			}

			iAppCommonMethods.navigateBack(appDriver);

		});

		softAssert.assertAll();

	}

	@Test(dataProvider = "getAuthorAndDetails", description = "This testcase verifies the author section", priority = 4,enabled=true)
	public void verifyPrimeAuthorSection(String authorName, Boolean isAuthor, Boolean isContributor) {
		softAssert = new SoftAssert();
		primeSectionMethods.goToHomeTabFromTop();
		primeSectionMethods.clickAuthorsTab();
		String predicate = primeSectionMethods.authorProfileLink(authorName);
		iAppCommonMethods.scrollUpToElement(predicate);
		WaitUtil.sleep(1000);
		iAppCommonMethods.swipeUp();
		primeSectionMethods.clickFirstAuthorViewFullProfileLink();

		if (isAuthor) {
			primeSectionMethods.clickPrimeHomepageFirstStory();
			String storyPageAuthor = primeSectionMethods.primeArticleshowAuthor();
			softAssert.assertTrue(storyPageAuthor.contains(authorName.toUpperCase()),
					"Author name shown on the first story of author:" + authorName + " is not same, instead is:"
							+ storyPageAuthor);
			iAppCommonMethods.navigateBack(appDriver);
		} else {
			iAppCommonMethods.swipeUp();
			softAssert.assertTrue(primeSectionMethods.isPrimeContentComingSoonShown(),
					"Content coming soon message is not shown for the author " + authorName);

		}
		primeSectionMethods.clickPrimeAuthorsContributionsTab();
		if (isContributor) {
			softAssert.assertTrue(primeSectionMethods.getAuthorCommentPostedOnHeading(),
					"Author " + authorName + " is a contributor but its contributions are not shown ");
		} else {
			iAppCommonMethods.swipeUp();
			softAssert.assertTrue(primeSectionMethods.isPrimeContentComingSoonShown(),
					"Content coming soon message is not shown for the author " + authorName);

		}
		iAppCommonMethods.swipeDown();
		iAppCommonMethods.navigateBack(appDriver);
		WaitUtil.sleep(1000);
		iAppCommonMethods.swipeDown();
		softAssert.assertAll();

	}
	@Test(description = "This test verifies Prime search functionality", priority = 5, dataProvider="Search Data")
	public void verifyPrimeSearch(String keyword) {
		softAssert = new SoftAssert();
		headerPageMethods.clickMenuIcon();
		menuPageMethods.scrollToMenuOptionClick("Search", true);
		// List<String> feedSearchResult = new LinkedList<>();
		List<String> appSearchResult = new LinkedList<>();
		// Assert.assertTrue(primeSectionMethods.clickSearchIcon(),"Unable to
		// click search icon");
		if (searchPageMethods.enterValueInSearchBox(keyword) == true) {
			softAssert.assertTrue(searchPageMethods.clickEtPrimeTab(), "Unable to click on News Tab");
			appSearchResult = primeSectionMethods.getSearchResults();
			softAssert.assertTrue(appSearchResult.size() > 0, "No result shown  in app");
		} else {
			softAssert.assertTrue(false, "<br>Unable to enter value " + keyword);
		}
		iAppCommonMethods.navigateBack(appDriver);
		softAssert.assertAll();
	}

	@DataProvider
	public static Object[][] getAuthorAndDetails() {
		Object[][] data = new Object[1][3];

		data[0][0] = "Shishir Prasad";
		data[0][1] = true;
		data[0][2] = false;
		/*
		 * data[1][0] = "Tanmoy Goswami"; data[1][1] = true; data[1][2] = false;
		 * 
		 * data[2][0] = "Arti Singh"; data[2][1] = true; data[2][2] = true;
		 * 
		 * data[3][0] = "Debleena Majumdar"; data[3][1] = true; data[3][2] =
		 * true;
		 */
		return data;

	}
	@DataProvider(name="Search Data")
	private Object[] getData(){
		Map<String, String> testData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyPrimeSearch", 1);
		List<String> searchedVal = Arrays.asList(testData.get("Keyword").split("\\s*,\\s*"));
		return searchedVal.toArray(new Object[searchedVal.size()]);
	
	}
	private int getStalness() {
		int dayOfWeek = new DateTime().getDayOfWeek();
		int allowedStaleness;
		switch (dayOfWeek) {
		case 1:
			allowedStaleness = 2;
			break;
		case 7:
			allowedStaleness = 1;
			break;
		default:
			allowedStaleness = 0;

		}
		return allowedStaleness;
	}

	@AfterClass(alwaysRun = true)
	public void quit() throws Exception {
		appDriver.quit();
	}

}
