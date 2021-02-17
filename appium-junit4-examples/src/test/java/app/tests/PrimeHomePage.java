package app.tests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.AlertsPromptsMethods;
import app.pagemethods.AppListingPageMethods;
import app.pagemethods.HeaderPageMethods;
import app.pagemethods.LoginPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.PrimeSectionMethods;
import app.pagemethods.PrimeSectionMethods.ArticleType;
import app.pagemethods.StoryPageMethods;
import app.pageobjects.AppListingPageObjects;
import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.urlRepo.AppFeeds;
import common.utilities.ApiHelper;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PrimeHomePage extends BaseTest {
	SoftAssert softAssert;
	RestAssuredConfig config;
	private AppiumDriver<?> appDriver;
	AppListingPageObjects appListingPageObjects;
	private AppListingPageMethods appListingPageMethods;
	private PrimeSectionMethods primeSectionMethods;
	private HeaderPageMethods headerPageMethods;
	private MenuPageMethods menuPageMethods;
	private LoginPageMethods loginPageMethods;
	private StoryPageMethods storyPageMethods;
	AlertsPromptsMethods alertsPromptsMethods;
	private String sectionToExlude = "Popular Categories, Writers on Panel,Top News, Top News - Layout2, Top News - Layout3,Recent Stories,Our Esteemed Readers,Our team";
	String krytonURL = "https://krypton.economictimes.indiatimes.com/api/view/153/etshow_authorised_xsl?hostid=%s&msid=%s";
	private ArrayList<String> gridNames = new ArrayList<>(
			Arrays.asList("Top News", "Top News - Layout2", "Top News - Layout3", "Recent Stories"));
	ArrayList<ArrayList<String>> newsList = new ArrayList<ArrayList<String>>();
	HashMap<String, ArrayList<ArrayList<String>>> primeHeadlinesData = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();

	@BeforeClass(alwaysRun = true)
	public void launchApp() throws IOException {
		config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
				.setParam("CONNECTION_MANAGER_TIMEOUT", 1000).setParam("SO_TIMEOUT", 1000));

		config.getConnectionConfig().closeIdleConnectionsAfterEachResponse();
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		appListingPageObjects = new AppListingPageObjects();
		appListingPageMethods = new AppListingPageMethods(appDriver);
		primeSectionMethods = new PrimeSectionMethods(appDriver);
		headerPageMethods = new HeaderPageMethods(appDriver);
		menuPageMethods = new MenuPageMethods(appDriver);
		loginPageMethods = new LoginPageMethods(appDriver);
		storyPageMethods = new StoryPageMethods(appDriver);
		alertsPromptsMethods = new AlertsPromptsMethods(appDriver);
	}

	@Test(description = "Verify prime home page api response ", priority = 8, enabled = true)
	public void verifyPrimeHomeFeeds() {
		softAssert = new SoftAssert();
		String feed = String.format(AppFeeds.primeHomeFeed, Config.fetchConfigProperty("APIPlatformName"),
				Config.fetchConfigProperty("ETAppVersion"));
		System.out.println("Prime Home API:" + feed);
		Response response = given().header("Accept", "application/json").header("Accept-Charset", "UTF-8")
				.header("Content-Type", "application/json;charset=utf-8").when().config(config).get(feed);
		int status1 = response.statusCode();
		softAssert.assertEquals(status1, 200, "Feed:" + feed + " is not giving 200, instead is " + status1);
		long time1 = response.time();
		softAssert.assertTrue(time1 < 5000L,
				"Feed:" + feed + " is not giving response in 5 secs, instead is taking " + time1);
		String output = response.getBody().asString();
		softAssert.assertTrue((JsonPath.given(output).getMap("$.").size()) > 0,
				"Feed:" + feed + " is giving blank response");
		System.out.println("status" + status1);
		primeHeadlinesData = ApiHelper.getDataFromAPI(AppFeeds.primeHomeFeed, "Item", "NewsItem", "sn", "id", "hl",
				"primePlus", "isPrime");
		Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyActiveUserSignIn", 1);
		boolean status = new LoginPage().login(softAssert, headerPageMethods, menuPageMethods, loginPageMethods,
				TestData.get("Email"), TestData.get("Password"));
		softAssert.assertTrue(status, "User not able to login prime homepage");

	}

	@Test(description = "Verifies Top News section and Recent Stories articles list wit api list", priority = 9, enabled = true)
	public void verifyTopNewsAndRecentStoriesSectionHeadlines() {
		softAssert = new SoftAssert();
		Assert.assertTrue(primeSectionMethods.clickPrimeHomeTab(), "Prime Tab not clickable");
		for (String sectionName : primeHeadlinesData.keySet()) {
			boolean flag = false;
			System.out.println("Section Name - " + sectionName);
			newsList = primeHeadlinesData.get(sectionName);
			softAssert.assertTrue(newsList.size() > 0, "The section" + sectionName + " has no headlines");
			System.out.println("gridnameee === " + gridNames);
			if (gridNames.contains(sectionName)) {
				String firstHeadline = (newsList.get(0).get(1));
				System.out.println("headline --- " + firstHeadline);
				flag = primeSectionMethods.verifyTopGridsHeadline(firstHeadline);
				if (!flag) {
					softAssert.assertTrue(flag,
							"The heading " + newsList.get(0).get(1) + " is not shown on the prime homepage");
					continue;
				}
				System.out.println("newsletisy before for loop " + newsList);
				for (ArrayList<String> newsItem : newsList) {
					String headline = newsItem.get(1);
					String formatHeadline = (headline);
					System.out.println("--headline name is--" + formatHeadline);
					boolean headlineflag = appListingPageMethods.verifyHeadlineIsDisplayed(formatHeadline);
					softAssert.assertTrue(headlineflag,
							"The page is not scrolled upto headline- " + headline + " for section " + sectionName);
				}
			}
		}
		softAssert.assertAll();
	}

	@Test(description = "Verifies all L2 category articles list and content validation with krypton api", priority = 10, enabled = true)
	public void verifyArticleshowData() {
		int storyCounter = 1;
		globalFlag = false;
		softAssert = new SoftAssert();
		Assert.assertTrue(primeSectionMethods.clickPrimeHomeTab(), "Prime Tab not clickable");
		Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyActiveUserSignIn", 1);
		// Fetch User's Ticket ID and Access Token
		primeSectionMethods.generateAuthenticationTokens(TestData.get("Email"), TestData.get("Password"));
		for (String sectionName : primeHeadlinesData.keySet()) {
			if (sectionToExlude.contains(sectionName))
				continue;
			newsList = primeHeadlinesData.get(sectionName);
			System.out.println("**Section Name**- " + sectionName);
			softAssert.assertTrue(newsList != null, "Headlines list is empty for section-" + sectionName);
			boolean sectionNameflag = primeSectionMethods.scrollToCategory(sectionName);
			softAssert.assertTrue(sectionNameflag, "The page is not scrolled upto section- " + sectionName);
			if (sectionNameflag) {
				verifySectionNewsList(sectionName, storyCounter);
				if (!sectionName.isEmpty()) {
					boolean linkflag = primeSectionMethods.verifyViewSectionLink(sectionName, true);
					softAssert.assertTrue(linkflag,
							"The view all link -  for section " + sectionName + " is not clicked");
					if (linkflag) {
						softAssert.assertTrue(primeSectionMethods.isL2categoryShown(sectionName),
								"<br>-Link not rediecting to section page " + sectionName);
						WaitUtil.sleep(1000);
						verifySectionNewsList(sectionName, 0);
						iAppCommonMethods.navigateBack(appDriver);
					}

				}
			}
		}
		softAssert.assertAll();
	}

	@Test(description = "Verifies top navigation horizontal tabs on homepage with api", priority = 11, enabled = true)
	public void verifyTopNavigation() {
		softAssert = new SoftAssert();
		String feed = AppFeeds.primetabsFeed;
		Response response = given().header("Accept", "application/json").header("Accept-Charset", "UTF-8")
				.header("Content-Type", "application/json;charset=utf-8").when().config(config).get(feed);
		int status1 = response.statusCode();
		softAssert.assertEquals(status1, 200, "Feed:" + feed + " is not giving 200, instead is " + status1);
		long time1 = response.time();
		softAssert.assertTrue(time1 < 5000L,
				"Feed:" + feed + " is not giving response in 5 secs, instead is taking " + time1);
		String output = response.getBody().asString();
		List<String> apiTab = JsonPath.given(output).getList("Item.nm");
		softAssert.assertTrue(apiTab.size() > 0, "Headlines list is empty in api for tabs");
		apiTab = appListingPageMethods.getItemsOutAsList(apiTab);
		WaitUtil.sleep(1000);
		navigateToPrimeHome("ET Prime", "ET Prime", true);
		List<String> menuOptions = ApiHelper.getValueFromAPI(AppFeeds.lhsFeed, "nm", "", "", "Item");
		List<String> subMenu = ApiHelper.getValuesFromJSONResponse(ApiHelper.getOutput(), "nm", "nm", "ET Prime",
				"Item", "ss");
		for (String tabName : subMenu) {
			if (!tabName.contains("ET Prime")) {
				if (!headerPageMethods.isTabDisplayed(tabName))
					headerPageMethods.scrollTopTabsLeft();
				softAssert.assertTrue(headerPageMethods.isTabDisplayed(tabName),
						"<br>- Tab " + tabName + " is not shown.");
			}
		}
		headerPageMethods.clickMenuIcon();
		menuPageMethods.scrollDownToSettingIcon();
		softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
		softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		softAssert.assertAll();
	}

	@Test(description = " This test verifies login from non prime user and blocker layer shown to user on primearticleshow page", priority = 1, enabled = true)
	public void verifysignInFromNonPrimeUser() {
		Map<String, String> testData = ExcelUtil.getTestDataRow("TestDataSheet", "verifynosubscriptionUserSignIn", 1);
		softAssert = new SoftAssert();
		navigateToHome();
		WaitUtil.sleep(1000);
		primeSectionMethods.clickPrimeIcon();
		softAssert.assertTrue(primeSectionMethods.clickOnPrimeListingStory(),
				"Unable to click first story from prime section");
		boolean status = primeSectionMethods.scrollToNewPlanPaywall();
		if (status) {
			softAssert.assertTrue(primeSectionMethods.scrollToSubscribeButton(),
					" Unable to scroll to subscribe button");
			WaitUtil.sleep(1000);
			iAppCommonMethods.navigateBack(appDriver);
			alertsPromptsMethods.clickRatingCloseIcon();
//			primeSectionMethods.clickOnSubscribeButton();
//			softAssert.assertTrue(primeSectionMethods.clickOnSignInButton(), "Unable to click prime sign in link");
//			softAssert.assertTrue(loginPageMethods.enterCredentials(testData.get("Email"), testData.get("Password")),
//					"Unable to sign in user");
//			
		} else {
			primeSectionMethods.scrollToTopPage();
			softAssert.assertTrue(primeSectionMethods.clickOnSignInButton(), "Unable to click prime sign in link");
			softAssert.assertTrue(loginPageMethods.enterCredentials(testData.get("Email"), testData.get("Password")),
					"Unable to sign in user");
			softAssert.assertTrue(primeSectionMethods.verifyStartFreeTrialWidgetPresent(),
					"Start your free Trial Widget not coming for no Prime user");
			// WaitUtil.sleep(1000);

			iAppCommonMethods.navigateBack(appDriver);
			alertsPromptsMethods.clickRatingCloseIcon();
			iAppCommonMethods.scrollDown();
			headerPageMethods.clickMenuIcon();
			softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
			softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		}
		softAssert.assertAll();

	}

	@Test(description = " This test verifies login from expired user and blocker layer with Start Your membership  shown to user on primearticleshow page", priority = 2, enabled = true)
	public void verifysignInFromExpiredUser() {
		Map<String, String> testData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyExpiredUserSignIn", 1);
		softAssert = new SoftAssert();
		navigateToHome();
		WaitUtil.sleep(2000);
		primeSectionMethods.clickPrimeIcon();
		softAssert.assertTrue(primeSectionMethods.clickOnPrimeListingStory(),
				"Unable to click first story from prime section");
		boolean status = primeSectionMethods.scrollToNewPlanPaywall();
		if (status) {
			softAssert.assertTrue(primeSectionMethods.scrollToSubscribeButton(),
					" Unable to scroll to subscribe button");
			WaitUtil.sleep(1000);
			iAppCommonMethods.navigateBack(appDriver);
			alertsPromptsMethods.clickRatingCloseIcon();
		} else {
			primeSectionMethods.scrollToTopPage();
			softAssert.assertTrue(primeSectionMethods.clickOnSignInButton(), "Unable to click prime sign in link");
			softAssert.assertTrue(loginPageMethods.enterCredentials(testData.get("Email"), testData.get("Password")),
					"Unable to sign in user");
			alertsPromptsMethods.clickRatingCloseIcon();
			softAssert.assertTrue(primeSectionMethods.verifyStartMembershipWidgetPresent(),
					"Start your membership Widget not coming for no Prime user");
			WaitUtil.sleep(1000);
			iAppCommonMethods.navigateBack(appDriver);
			alertsPromptsMethods.clickRatingCloseIcon();
			iAppCommonMethods.scrollDown();
			headerPageMethods.clickMenuIcon();
			softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
			softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		}
		softAssert.assertAll();
	}

	@Test(description = "This test case verifies subscription page redirection from prime articleshow", priority = 3, enabled = true)
	public void verifysubscriptionPageRedirection() {
		softAssert = new SoftAssert();
		navigateToHome();
		WaitUtil.sleep(1000);
		primeSectionMethods.clickPrimeIcon();
		softAssert.assertTrue(primeSectionMethods.clickOnPrimeListingStory(),
				"Unable to click first story from prime section");
		softAssert.assertTrue(primeSectionMethods.validatePlanPageRedirectionFromFooter(),
				"Plan page redirection from footer of prime articleshow failed");
		iAppCommonMethods.navigateBack(appDriver);
		iAppCommonMethods.navigateBack(appDriver);
		softAssert.assertAll();
	}

	public void verifySectionNewsList(String sectionName, int newsCount) {
		for (ArrayList<String> newsItem : newsList) {
			String headline = newsItem.get(1);
			String formatHeadline = formatText(headline);
			System.out.println("--headline name is---" + formatHeadline);
			boolean headlineflag = appListingPageMethods.verifyHeadlineIsDisplayed(formatHeadline);
			softAssert.assertTrue(headlineflag,
					"The page is not scrolled upto headline- " + headline + " for section " + sectionName);
			if (headlineflag && newsCount > 0) {
				String ID = newsItem.get(0);
				System.out.println("newsitem... " + newsItem);
				ArticleType articleType = primeSectionMethods.getArticleType(newsItem);
				System.out.println("articletype...." + articleType);
				Boolean clickFlag = appListingPageMethods.clickHeadline(formatHeadline);
				if (clickFlag != null)
					softAssert.assertTrue(clickFlag,
							"<br>-Unable to click story: " + headline + " for section " + sectionName);
				if (articleType == null) {
					softAssert.assertNotNull(articleType,
							"Headine " + headline + " doesn't have the articleType  for section " + sectionName);
				} else {
					verifyAPIdataWithUIdata(headline, ID, articleType);
				}
				iAppCommonMethods.navigateBack(appDriver);
				alertsPromptsMethods.clickRatingCloseIcon();
				WaitUtil.sleep(1000);
				newsCount--;
			}
		}
	}

	public void verifyAPIdataWithUIdata(String headline, String ID, ArticleType type) {
		HashMap<String, String> apiResponseList = primeSectionMethods.getKryptonAPIData(ID, type);
		HashMap<String, String> uiDataList = storyPageMethods.getUIArticleDetails();
		System.out.println("api response...." + apiResponseList);
		System.out.println("client side response list...." + uiDataList);
		for (String apiKey : apiResponseList.keySet()) {
			System.out.println("KEY-  " + apiKey);
			String apiValue = apiResponseList.get(apiKey);
			System.out.println("**apiValue**- " + apiValue);
			String uiValue = uiDataList.get(apiKey);
			System.out.println("**uiValue**- " + uiValue);
			softAssert.assertNotNull(apiValue);
			if (apiValue.isEmpty()) {
				softAssert.assertNull(uiValue);
			} else {
				softAssert.assertNotNull(uiValue, "UI value is null for Key " + apiKey + " API value " + apiValue);
				System.out.println("Result-- " + apiValue.equals(uiValue));
				softAssert.assertTrue(apiValue.equals(uiValue), "For headline--" + headline + " - Key " + apiKey
						+ " API value " + apiValue + "--does not match with UI Value--" + uiValue);
			}
		}
	}

	@AfterClass(alwaysRun = true)
	public void quit() throws Exception {
		appDriver.quit();
	}

	private void navigateToPrimeHome(String l1Menu, String l2Menu, boolean isClickNeeded) {
		softAssert.assertTrue(navigateToMoreApps(), "Page not redirected to More Apps");
		headerPageMethods.clickMenuIconSwipeUpFirst();
		menuPageMethods.scrollDownToSettingIcon();
		WaitUtil.sleep(1000);
		Assert.assertTrue(menuPageMethods.scrollToMenuOptionClick(l1Menu, isClickNeeded),
				"<br>Unable to find menu option: " + l1Menu);
		WaitUtil.sleep(1000);
		softAssert.assertTrue(menuPageMethods.clickL2MenuByName(l2Menu), "Sub Menu" + l2Menu + "is not clicked");
	}

	private boolean navigateToMoreApps() {
		headerPageMethods.clickMenuIconSwipeUpFirst();
		return menuPageMethods.clickMenuByLabel("More Apps");
	}

	private boolean navigateToHome() {
		new HeaderPageMethods(appDriver).clickMenuIconSwipeUpFirst();
		return new MenuPageMethods(appDriver).clickMenuByLabel("Home");
	}

	public static String formatText(String text) {
		if (text == null)
			return text;
		try {
			byte[] d = text.getBytes("cp1252");
			text = new String(d, Charset.forName("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return text;
	}
}
