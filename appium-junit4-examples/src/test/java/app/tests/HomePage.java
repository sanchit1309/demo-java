package app.tests;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.AdTechMethods;
import app.pagemethods.AlertsPromptsMethods;
import app.pagemethods.AppListingPageMethods;
import app.pagemethods.HeaderPageMethods;
import app.pagemethods.HomePageMethods;
import app.pagemethods.LoginPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.PrimeSectionMethods;
import app.pagemethods.SlideshowsPageMethods;
import app.pagemethods.StoryPageMethods;
import busineslogic.BusinessLogicMethods;
import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.urlRepo.AppFeeds;
import common.urlRepo.FeedRepo;
import common.utilities.ApiHelper;
import common.utilities.ExcelUtil;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import common.utilities.reporting.ScreenShots;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HomePage extends BaseTest {

	private HomePageMethods homePageMethods;
	private AppListingPageMethods appListingPageMethods;
	private StoryPageMethods storyPageMethods;
	private AdTechMethods adTechMethods;
	private PrimeSectionMethods primeSectionMethods;
	private HeaderPageMethods headerPageMethods;
	private AppiumDriver<?> appDriver;
	private List<String> allSections = new LinkedList<String>();
	private String sectionToExlude = "More from Partners,Multimedia,Podcast,ET Audio,ET LiveTv,Game-A-Thon";
	private List<String> headlines = new LinkedList<String>();
	private List<String> primeNewsList = new ArrayList<String>();
	private Map<String, ArrayList<ArrayList<String>>> HeadlinesData = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();
	private List<ArrayList<String>> newsList = new ArrayList<ArrayList<String>>();
	private SoftAssert softAssert;
	private int storyCounter;
	private int counter;
	private String feed;
	private RestAssuredConfig config;
	private String baseUrl = new Config().fetchConfig(new File("./src/main/resources/properties/Web.properties"),
			"HomeUrl");
	private LoginPageMethods loginPageMethods;
	private MenuPageMethods menuPageMethods;
	private AlertsPromptsMethods alertsPromptsMethods;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "toVerifyStoriesInEachSection" })
	public void setUp(@Optional("1") int toVerifyStoriesInEachSection) throws IOException {

		config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
				.setParam("CONNECTION_MANAGER_TIMEOUT", 1000).setParam("SO_TIMEOUT", 1000));

		config.getConnectionConfig().closeIdleConnectionsAfterEachResponse();
		launchBrowser();
		storyCounter = toVerifyStoriesInEachSection;
		appDriver = iAppCommonMethods.getDriver();
		storyPageMethods = new StoryPageMethods(appDriver);
		homePageMethods = new HomePageMethods(appDriver);
		adTechMethods = new AdTechMethods(appDriver);
		appListingPageMethods = new AppListingPageMethods(appDriver);
		primeSectionMethods = new PrimeSectionMethods(appDriver);
		headerPageMethods = new HeaderPageMethods(appDriver);
		allSections = ApiHelper.getDataFromAllPages(AppFeeds.homeFeed, "sn", "Item");
		System.out.println("allsection : " + allSections);
		HeadlinesData = ApiHelper.getDataFromAPI(AppFeeds.homeFeed, "Item", "NewsItem", "sn", "id", "hl", "isPrime",
				"primePlus");
		System.out.println("headline data : " + HeadlinesData);
		feed = String.format(AppFeeds.homeFeed, Config.fetchConfigProperty("ETAppVersion"),
				Config.fetchConfigProperty("APIPlatformName"));
		loginPageMethods = new LoginPageMethods(appDriver);
		menuPageMethods = new MenuPageMethods(appDriver);
		alertsPromptsMethods = new AlertsPromptsMethods(appDriver);

	}

	@Test(description = "Verifies breaking news", priority = 0, enabled = true)
	public void breakingNews() {
		List<String> apiBreakingNewsLi = ApiHelper.getDataForBreakingNews(AppFeeds.breakingNewsFeed);
		List<String> uiBreakingNewsLi = new LinkedList<String>();
		if (apiBreakingNewsLi.size() > 0) {
			uiBreakingNewsLi = appListingPageMethods.getBreakingNews();
			System.out.println(uiBreakingNewsLi);
			new AlertsPromptsMethods(appDriver).clickBreakNewsCloseIcon();
			;
			Assert.assertTrue(!uiBreakingNewsLi.isEmpty(), "List of breaking news found from API:" + apiBreakingNewsLi
					+ ",not matching to value on UI:" + uiBreakingNewsLi);
		}
	}

	@Test(description = "Verifies Brief Section on Home Page", priority = 1, enabled = true)
	public void verifyETBriefSection() {
		softAssert = new SoftAssert();
		String pageFeed = feed;
		Response response = given().log().all().header("Accept", "application/json").header("Accept-Charset", "UTF-8")
				.header("Content-Type", "application/json;charset=utf-8").when().config(config).get(pageFeed);
		int status = response.statusCode();
		softAssert.assertEquals(status, 200, "Feed:" + pageFeed + " is not giving 200, instead is " + status);
		long time1 = response.time();
		softAssert.assertTrue(time1 < 5000L,
				"Feed:" + pageFeed + " is not giving response in 5 secs, instead is taking " + time1);

		String output = response.getBody().asString();
		JsonPath js = new JsonPath(output);
		String res = js.getString("Item[0].NewsItem[3].hl");
		if (res.contains("Tap for ET Morning Brief") || res.contains("Tap for ET Evening Brief")) {
			Boolean flag = homePageMethods.verifyETBriefSection();
			Assert.assertTrue(flag, "The page is not scrolled upto Brief Section");
			Boolean clickflag = homePageMethods.clickBriefSectionLink();
			softAssert.assertTrue(clickflag, "<br>-Brief Section Link not clicked");
			String headline = headerPageMethods.getHeaderText();
			softAssert.assertTrue(headline.contains("Brief"), "<br>-Link not rediecting to Brief Section page ");
			iAppCommonMethods.navigateBack(appDriver);
		}
		softAssert.assertAll();
	}

	@Test(description = "Verifies Quick Read Section and Headlines on Home Page with api", priority = 2, enabled = false)
	public void verifyQuickReadSection() {
		softAssert = new SoftAssert();
		String pageFeed = feed + "&curpg=" + 1;
		Response response = given().header("Accept", "application/json").header("Accept-Charset", "UTF-8")
				.header("Content-Type", "application/json;charset=utf-8").when().config(config).get(pageFeed);
		int status1 = response.statusCode();
		softAssert.assertEquals(status1, 200, "Feed:" + pageFeed + " is not giving 200, instead is " + status1);
		long time1 = response.time();
		softAssert.assertTrue(time1 < 5000L,
				"Feed:" + pageFeed + " is not giving response in 5 secs, instead is taking " + time1);
		String output = response.getBody().asString();
		List<String> apiHeadlines = JsonPath.given(output).getList("Item.NewsItem.qritems.hl");
		softAssert.assertTrue(apiHeadlines.isEmpty(), "Headlines list is empty in api for Quick Reads");
		apiHeadlines = appListingPageMethods.getItemsOutAsList(apiHeadlines);
		Assert.assertTrue(homePageMethods.scrollSectionToTop("QUICK READS"),
				"The page is not scrolled upto QUICK READS Section");
		for (String headline : apiHeadlines) {
			boolean flag = homePageMethods.verifyStringDisplayed(headline);
			if (!flag)
				homePageMethods.scrollQuickReadsTabsLeft();
			softAssert.assertTrue(homePageMethods.verifyStringDisplayed(headline),
					"<br>- headline " + headline + " is not displayed on UI");
		}
		softAssert.assertAll();
	}

	@Test(description = "Verifies Stock Screener Section on Home Page and Link size with api", priority = 3, enabled = false)
	public void verifyStockScreenerSection() {
		globalFlag = false;
		int expectedCount = 3;
		softAssert = new SoftAssert();
		navigateToHome();
		Assert.assertTrue(homePageMethods.scrollSectionToTop("Stock Screener"),
				"The page is not scrolled upto Stock Screener Section");
		List<MobileElement> stockscreener = homePageMethods.getScreenerSectionLinkCount();
		int actualCount = stockscreener.size();
		softAssert.assertEquals(actualCount, expectedCount,
				"<br>-Link size is not equal to expectedCount " + expectedCount);
		softAssert.assertAll();
	}

	@Test(description = "Verifies More From Partner Section and Headlines on homepage with api", priority = 4, enabled = true)
	public void verifyMoreFromPartnerSection() {
		globalFlag = false;
		softAssert = new SoftAssert();
		ArrayList<ArrayList<String>> headlineList = new ArrayList<ArrayList<String>>();
		for (String sectionName : HeadlinesData.keySet()) {
			if (sectionName.equals("More from Partners")) {
				if (BaseTest.platform.contains("android")) {
					iAppCommonMethods.scrollUpToElement("More from Partners");
					Assert.assertTrue(homePageMethods.scrollToMoreFromPartnerStories(),
							"The page is not scrolled upto More from Partners Section");

				} else {
					Assert.assertTrue(homePageMethods.scrollSectionToTop("More from Partners"),
							"The page is not scrolled upto More from Partners Section");
				}
				headlineList = HeadlinesData.get(sectionName);
				softAssert.assertTrue(headlineList != null, "Headlines list is empty in api for More from Partners");
				for (ArrayList<String> newsItem : headlineList) {
					String headline = newsItem.get(1);
					int count = 0;
					System.out.println("headline is---" + headline);
					if (!homePageMethods.verifyStringDisplayed(formatText(headline))) {
						System.out.println("headlinelist ize :  " + headlineList.size());
						System.out.println("count now : " + count);
						if ((BaseTest.platform.contains("android")) && (headlineList.size() > count)) {
							homePageMethods.scrollSectionTabToLeft();
							count++;
							System.out.println("count value : " + count);
						} else {
							homePageMethods.scrollSectionTabToLeft();
						}
					}
					softAssert.assertTrue(homePageMethods.verifyStringDisplayed(headline),
							"<br>- headline " + headline + " is not shown.");

				}
			}
		}
		softAssert.assertAll();
	}

	@Test(description = "Verifies latest slideshows article in Multimedia section and headlines with api", priority = 5, enabled = true)
	public void verifyMultimendiaSectionForSlideShows() {
		globalFlag = false;
		SlideshowsPageMethods slideshowsPageMethods = new SlideshowsPageMethods(appDriver);
		ArrayList<ArrayList<String>> headlineList = new ArrayList<ArrayList<String>>();
		Set<String> sizeCheck = new HashSet<String>();
		List<String> headingOnUI = null;
		softAssert = new SoftAssert();
		if (BaseTest.platform.contains("android")) {
			iAppCommonMethods.scrollUpToElement("Multimedia");
			Assert.assertTrue(homePageMethods.scrollToMultimediaCard(),
					"The page is not scrolled upto Multimedia Section");
			iAppCommonMethods.swipeByScreenPercentage(0.50, 0.70);

		} else {
			Assert.assertTrue(homePageMethods.scrollSectionToTop("Multimedia"),
					"The page is not scrolled upto MultiMedia Section");
			iAppCommonMethods.swipeByScreenPercentage(0.50, 0.75);
		}
		List<MobileElement> slideshows = homePageMethods.getHomepageSlideshows();
		Assert.assertTrue(slideshows != null, "No slide show found on home page multimedia section");
		String headline = slideshows.get(0).getText();
		iAppCommonMethods.clickElement(slideshows.get(0));
		softAssert.assertTrue(slideshowsPageMethods.verifyTotalSlideShowCount(),
				"Slideshow count mismatch for article : " + headline);
		WaitUtil.sleep(2000);
		iAppCommonMethods.navigateBack(appDriver);
		WaitUtil.sleep(2000);
		for (String sectionName : HeadlinesData.keySet()) {
			if (sectionName.equals("Multimedia")) {
				headlineList = HeadlinesData.get(sectionName);
				int apiListSize = headlineList.size();
				System.out.println("api list size : " + apiListSize);
				softAssert.assertTrue(headlineList != null, "Headlines list is empty in api for Multimedia");
				if (BaseTest.platform.contains("android")) {
					int count = 0;
					while (apiListSize > count) {
						headingOnUI = homePageMethods.getMultimediaHeadlines();
						if (BaseTest.platform.contains("android")) {
							homePageMethods.scrollLeft("Multimedia");
						} else
							homePageMethods.scrollSectionTabToLeft();
						count++;
						System.out.println("count vlue : " + count);
					}
					for (String s : headingOnUI) {
						softAssert.assertTrue(s != null, "<br>- headline " + s + " is not shown.");
						sizeCheck.add(s);
					}
					System.out.println("size of headlines on ui --- " + sizeCheck.size());
					Assert.assertEquals(sizeCheck.size(), apiListSize,
							" size of Multimedia items present on ui and api do not match");
				} else {
					for (ArrayList<String> newsItem : headlineList) {
						String multimediaheadline = newsItem.get(1);
						boolean flag = homePageMethods.verifyStringDisplayed(multimediaheadline);
						if (!flag)
							if (BaseTest.platform.contains("android")) {
								homePageMethods.scrollLeft("Multimedia");
							} else
								homePageMethods.scrollSectionTabToLeft();
						softAssert.assertTrue(homePageMethods.verifyStringDisplayed(multimediaheadline),
								"<br>- headline " + multimediaheadline + " is not shown.");
					}
				}
			}
		}
		softAssert.assertAll();
	}

	@Test(description = "Verifies  Podcast section and headlines on homepage with api", priority = 6, enabled = true)
	public void verifyPodcastSection() {
		softAssert = new SoftAssert();
		globalFlag = false;
		ArrayList<ArrayList<String>> headlineList = new ArrayList<ArrayList<String>>();
		for (String sectionName : HeadlinesData.keySet()) {

			if (sectionName.equals("Podcast")) {
				if (BaseTest.platform.contains("android")) {
					iAppCommonMethods.scrollUpToElement("Podcast");
					Assert.assertTrue(homePageMethods.scrollToPodcastCardSection(),
							"The page is not scrolled upto Podcast Section");
				} else {
					Assert.assertTrue(homePageMethods.scrollSectionToTop("Podcast"),
							"The page is not scrolled upto Podcasts");
				}
				headlineList = HeadlinesData.get("Podcast");
				System.out.println("headline data of podcast :: " + headlineList);
				softAssert.assertTrue(headlineList != null, "Headlines list is empty in api for Podcasts");
				for (ArrayList<String> newsItem : headlineList) {
					String apiheadline = newsItem.get(1);
					boolean flag = homePageMethods.verifyStringDisplayed(apiheadline);
					if (!flag)
						if (BaseTest.platform.contains("android")) {
							homePageMethods.scrollLeft("Podcast");
						} else
							homePageMethods.scrollSectionTabToLeft();
					softAssert.assertTrue(homePageMethods.verifyStringDisplayed(apiheadline),
							"<br>- headline " + apiheadline + " is not shown. on UI");
				}
			}
		}
		softAssert.assertAll();
	}

	@Test(description = "Verifies markets home page widget", priority = 7, enabled = true)
	public void verifiesHomePageETMarketsWidget() {
		softAssert = new SoftAssert();
		if (BaseTest.platform.contains("android")) {
			Assert.assertTrue(homePageMethods.scrollToETMarketsSection(),
					"The page is not scrolled upto ET Markets Section");
		} else
			Assert.assertTrue(homePageMethods.scrollSectionToTop("ET Markets"),
					"The page is not scrolled upto ET Markets Section");
		int sizeToValidate = BaseTest.platform.contains("android") ? 4 : 6;
		Map<String, String> data = homePageMethods.getMarketsData();
		Assert.assertTrue(data != null, "No data found in the markets benchmark section");
		int allbenchmarks = data.size();
		if (allbenchmarks < sizeToValidate) {
			iAppCommonMethods.swipeByScreenPercentage(0.75, 0.70);
			data.putAll(homePageMethods.getMarketsData());
		}
		data.forEach((benchmarkName, ltpUi) -> {
			if (benchmarkName.equalsIgnoreCase("S&P BSE Sensex")) {
				benchmarkName = "SENSEX";
			}
			double ltp = homePageMethods.getValueFromApi(benchmarkName);
			int allowedDiff = BusinessLogicMethods.getBenchmarksAllowedValueDiff(benchmarkName);
			softAssert.assertTrue(
					VerificationUtil.valueIsInRange(ltp, VerificationUtil.parseDouble(ltpUi), allowedDiff),
					"<br>The value for " + benchmarkName + " is outside the allowed diff of " + allowedDiff + ",actual:"
							+ ltpUi + ",expected:" + ltp);
		});
		int sizeFound = data.size();
		softAssert.assertEquals(sizeFound, sizeToValidate,
				"A benchmark value is not found, actual:" + sizeFound + ",expected:" + sizeToValidate);

		softAssert.assertAll();
	}

	@Test(description = "Verifies Industry sub sections and headlines on homepage with api", priority = 8, enabled = true)
	public void verifyIndustrySection() {
		softAssert = new SoftAssert();
		globalFlag = false;
		String output;
		String pageFeed = feed + "&curpg=" + 5;
		System.out.println("feed is---" + pageFeed);
		softAssert.assertTrue(homePageMethods.scrolltoSection("Industry"),
				"The page is not scrolled upto Industry Section");
		List<String> sections = ApiHelper.getValueFromAPI(pageFeed, "nm", "sn", "Industry", "Item", "ss");
		System.out.println("sections " + sections);
//		List<String> sections=new ArrayList<String>(Arrays.asList( "Healthcare", "Jobs", "Retail", "Services", "Media", "Telecom", "Transportation"));
//		System.out.println("sections after " + sections);
		softAssert.assertTrue(!sections.isEmpty(), "sections list is empty ");
		for (String section : sections) {
			output = ApiHelper.getAPIResponse(pageFeed);
			String sectionURL = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "sn", "Industry", "dn",
					section, "du");
			String url = baseUrl + sectionURL;
			output = ApiHelper.getAPIResponse(url);
			System.out.println("Section is---" + section);
			List<String> apiHeadlines = JsonPath.given(output).getList("NewsItem.hl");
			System.out.println("before null remove :: " + apiHeadlines);
			while (apiHeadlines.remove(null)) {
			}
			System.out.println("api headlines " + apiHeadlines);
			if (!homePageMethods.verifyStringDisplayed(section))
				homePageMethods.scrollIndustryMenuTabsLeft();
			softAssert.assertTrue(homePageMethods.verifyStringDisplayed(section),
					"<br>- Section  " + section + "of Industry is not shown.");
			Boolean clickFlag = homePageMethods.verifyIndustrySectionTab(section, true);
			if (clickFlag != null)
				softAssert.assertTrue(clickFlag, "<br>-Unable to click Section:" + section);
			WaitUtil.sleep(1000);
			for (String headline : apiHeadlines) {
				if (headline == null)
					continue;
				System.out.println("formated headline is---" + formatText(headline));
				System.out.println("without format headline : ---- " + headline);
				homePageMethods.display();
				boolean headlineflag = appListingPageMethods.verifyHeadlineIsDisplayed(headline);
				softAssert.assertTrue(headlineflag,
						"<br>- headline " + headline + " is not displayed for section " + section);
			}
			if ((BaseTest.platform.contains("android") && (section.equalsIgnoreCase("featured")))) {
				section = "Industry";
			}
			boolean linkflag = homePageMethods.verifyViewSectionLink(section, true);
			softAssert.assertTrue(linkflag, "The view all link -  for section " + section + "is not clicked");
			if (linkflag) {
				String SectionHeader = headerPageMethods.getHeaderText();
				softAssert.assertTrue(SectionHeader.equalsIgnoreCase(section),
						"<br>-Link not rediecting to section " + section);
				iAppCommonMethods.navigateBack(appDriver);
			}
			boolean scrollflag = homePageMethods.scrollDowntoSection(section);
			softAssert.assertTrue(scrollflag, "Not scrolled down to section " + section);

		}
		softAssert.assertAll();
	}

	@Test(description = "Verifies Top Mutual Funds widget on home page", priority = 9, enabled = true)
	public void verifiesHomePageMutualFundsWidget() {
		softAssert = new SoftAssert();
		globalFlag = false;
		Response response = given().header("Accept", "application/json").header("Accept-Charset", "UTF-8")
				.header("Content-Type", "application/json;charset=utf-8").when().config(config)
				.get(FeedRepo.TopMutualCategoryList);
		int status1 = response.statusCode();
		softAssert.assertEquals(status1, 200, "Feed:" + feed + " is not giving 200, instead is " + status1);
		long time1 = response.time();
		softAssert.assertTrue(time1 < 5000L,
				"Feed:" + feed + " is not giving response in 5 secs, instead is taking " + time1);
		String output = response.getBody().asString();
		System.out.println("response body : " + output);
		List<String> tabNames = JsonPath.given(output).getList("primaryObj.value");
		System.out.println("----Tabs----" + tabNames);
		if (BaseTest.platform.contains("android")) {
			Assert.assertTrue(homePageMethods.scrollToMFWidget(), "The page is not scrolled upto Top Mutual Funds");
		} else {
			Assert.assertTrue(homePageMethods.scrollSectionToTop("Top Mutual Funds"),
					"The page is not scrolled upto Top Mutual Funds");
			iAppCommonMethods.swipeByScreenPercentage(0.30, 0.80);
		}
		WaitUtil.sleep(1000);
		tabNames.forEach(text -> {
			if (!homePageMethods.verifyStringDisplayed(text))
				homePageMethods.scrollMutualFundCategoryTabsLeft();
			System.out.println("text :: " + text);
			softAssert.assertTrue(homePageMethods.verifyStringDisplayed(text),
					"<br>- Tab " + text + "of Top Mutual Fund is missing");
			boolean clickflag = appListingPageMethods.clickString(text);
			softAssert.assertTrue(clickflag, "The Category  " + text + "is not clicked");
			WaitUtil.sleep(1000);
			String url = String.format(FeedRepo.TopMutuafundCategoryfeed, text);
			List<String> subMenuList = ApiHelper.getValueFromAPI(url, "secondaryObjectiveManual", "", "", "data");
			System.out.println("submenu list :: " + subMenuList);
			softAssert.assertTrue(subMenuList.size() >= 0, "<br>-Sub Menu options is empty" + subMenuList);
			subMenuList.forEach(submenu -> {
				System.out.println(submenu);
				if (BaseTest.platform.contains("android")) {
					while (!homePageMethods.verifyMFSubSection(submenu.toUpperCase()))
						homePageMethods.scrollMutualFundSubCategoryTabsLeft();

				} else {
					if (!homePageMethods.verifyStringDisplayed(submenu.toUpperCase()))
						homePageMethods.scrollMutualFundSubCategoryTabsLeft();
				}
				softAssert.assertTrue(homePageMethods.verifyStringDisplayed(submenu.toUpperCase()),
						"<br>- Category " + text + "for Top Mutual Fund " + submenu + " submenu is not shown.");
			});
		});
		softAssert.assertAll();
	}

	@Test(description = "Verifies all section on home page", priority = 10, enabled = true)
	public void verifiesAllSectionNews() {
		boolean sectionNameflag = false;
		globalFlag = false;
		softAssert = new SoftAssert();
		counter = storyCounter;
		navigateToHome();
		for (String sectionName : HeadlinesData.keySet()) {
			if (sectionToExlude.contains(sectionName) && !sectionName.isEmpty())
				continue;
			System.out.println("--section name is--" + sectionName);
			newsList = HeadlinesData.get(sectionName);
			System.out.println("newslist ::: " + sectionName);
			softAssert.assertTrue(newsList != null, "Headlines list is empty for section-" + sectionName);
			if (sectionName.isEmpty()) {
				System.out.println("--section name is Top News--");
				sectionNameflag = true;
			} else {
				sectionNameflag = homePageMethods.scrolltoSection(sectionName);
				if (!sectionName.equalsIgnoreCase("Panache"))
					softAssert.assertTrue(adTechMethods.isAdDisplayed("Colombia"),
							"Colombia Ad missing from " + sectionName + " section on Home page");
				softAssert.assertTrue(sectionNameflag, "The page is not scrolled upto section " + sectionName);
			}
			if (sectionNameflag) {
				verifySectionNewsList(sectionName, storyCounter);
				if (!sectionName.isEmpty()) {
					boolean linkflag = homePageMethods.verifyViewSectionLink(sectionName, true);
					softAssert.assertTrue(linkflag,
							"The view all link -  for section " + sectionName + " is not clicked");
					if (linkflag) {
						String SectionHeader = headerPageMethods.getHeaderText();
						softAssert.assertTrue(SectionHeader.equalsIgnoreCase(sectionName),
								"<br>-Link not rediecting to section page" + sectionName);
						verifySectionNewsList(sectionName, 0);
						iAppCommonMethods.navigateBack(appDriver);
					}
				}
			}

		}
		softAssert.assertAll();
	}

	@Test(description = "Verifies if section returned from API are shown and also header text after clicking a section", priority = 11, enabled = true)
	public void verifyHomePageSectionsLandingPages() {
		softAssert = new SoftAssert();
		Map<String, String> sectionName = homePageMethods.getSectionLandingPage();
		Map<String, Boolean> adFlags = homePageMethods.getSectionColombiaAdFlags();
		System.out.println();
		sectionName.forEach((apiName, foundValue) -> {
			softAssert.assertTrue(apiName.equals(foundValue),
					"<br>- On clicking section label:" + apiName + " landed onto:" + foundValue);
		});
		adFlags.forEach((section, flag) -> {
			softAssert.assertTrue(flag, "Colombia Ad is missing from section " + section + " on Home Page");
		});
		softAssert.assertAll();
	}

	@Test(description = "Verifies top navigation horizontal tabs on homepage", priority = 12, enabled = true)
	public void verifyTopNavigation() {
		softAssert = new SoftAssert();
		List<String> apiMenuOptions = ApiHelper.getValueFromAPI(AppFeeds.tabsFeed, "dn", "", "", "Item");
		System.out.println("api menu options : " + apiMenuOptions);
		softAssert.assertTrue(apiMenuOptions.size() >= 5, "<br>-Menu options are less than 5" + apiMenuOptions);
		iAppCommonMethods.swipeUp();
		apiMenuOptions.forEach(text -> {
			if (!text.equalsIgnoreCase("myET")) {
				if (!headerPageMethods.isTabDisplayed(text))
					headerPageMethods.scrollTopTabsLeft();
				softAssert.assertTrue(headerPageMethods.isTabDisplayed(text), "<br>- Tab " + text + " is not shown.");
			}
		});

		softAssert.assertAll();
	}

	@Test(description = "Verifies Header,Footer and colombia ads on home page", priority = 13, enabled = true)
	public void verifyAds() {
		softAssert = new SoftAssert();
		softAssert.assertTrue(adTechMethods.isAdDisplayed("Header"), "Header ad missing from Home Page");
		WaitUtil.sleep(2000);
		softAssert.assertTrue(adTechMethods.isAdDisplayed("Colombia"), "ColombiaAd missing from Top News on Home Page");
		softAssert.assertTrue(adTechMethods.isAdDisplayed("Footer"), "Footer ad missing from Home Page");
		softAssert.assertAll();
	}

	@Test(description = "Verify for Prime/Premium articles  on home page app blocker validation on articleshow for non logged in user from homepage feed api", priority = 14, enabled = true)
	public void verifyPremiumArticles() {
		softAssert = new SoftAssert();
		int counter = 2;
		boolean flag = navigateToHome();
		softAssert.assertTrue(flag, "The page is navigated to home - ");
		headlines = primeNewsList;
		System.out.println("ET premium article list size" + headlines.size());
		globalFlag = false;
		for (int i = 0; i < counter; i++) {
			String text = headlines.get(i);
			System.out.println("headlines-" + text);
			softAssert.assertTrue(iAppCommonMethods.scrollUpToElement(formatText(text)),
					"The page is not scrolled up to premium article - " + formatText(text));
			WaitUtil.sleep(2000);
			iAppCommonMethods.getElementByText(text).click();
			softAssert.assertTrue(primeSectionMethods.verifyPrimeSubscribeWidget(),
					"Subscribe ET prime widget is missing for  article" + formatText(text));
			iAppCommonMethods.navigateBack(appDriver);
			iAppCommonMethods.scrollDown();
			headerPageMethods.clickMenuIcon();
			softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
			softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
			softAssert.assertAll();
			WaitUtil.sleep(1000);

		}

		softAssert.assertAll();

	}

	@Test(description = " Verify Start Free Trial widget on Homepage for non subscribed user", enabled = true, priority = 15)
	public void verifyStartFreeTrialWidgetforNonSubscribedUser() {
		softAssert = new SoftAssert();
		softAssert.assertTrue(loginPageMethods.clickOnTopHeadersignInButton(),
				"SingIn Button On top header is not clickable");
		Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifynosubscriptionUserSignIn", 1);
		Boolean status = loginPageMethods.enterCredentials(TestData.get("Email"), TestData.get("Password"));
		alertsPromptsMethods.clickRatingCloseIcon();
		softAssert.assertTrue(status, "Unable to enter login credentials");
		if (BaseTest.platform.contains("android")) {
			iAppCommonMethods.scrollUpToElement("START FREE TRIAL");
		} else {
			Assert.assertTrue(homePageMethods.verifyPrimeBanner(),
					" ET Prime Banner Widget is not present on Homepage for non prime user");
		}
		Assert.assertTrue(homePageMethods.verifyStartFreetrialWidget(),
				" ET Prime Start Free TrialWidget is not present on Homepage for non prime user");
		iAppCommonMethods.scrollDown();
		headerPageMethods.clickMenuIcon();
		softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
		softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		softAssert.assertAll();

	}

	@Test(description = " Verify Start Membership widget on Homepage for expired user", enabled = true, priority = 16)
	public void verifyStartMembershipWidgetforExpiredUser() {
		softAssert = new SoftAssert();
		softAssert.assertTrue(loginPageMethods.clickOnTopHeadersignInButton(),
				"SingIn Button On top header is not clickable");
		Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyExpiredUserSignIn", 1);
		Boolean status = loginPageMethods.enterCredentials(TestData.get("Email"), TestData.get("Password"));
		alertsPromptsMethods.clickRatingCloseIcon();
		softAssert.assertTrue(status, "Unable to enter login credentials");
		if (BaseTest.platform.contains("android")) {
			iAppCommonMethods.scrollUpToElement("START YOUR MEMBERSHIP");
		} else {
			Assert.assertTrue(homePageMethods.verifyPrimeBanner(),
					" ET Prime Banner Widget is not present on Homepage for non prime user");
		}
		Assert.assertTrue(homePageMethods.verifyStartMembershipWidget(),
				" ET Prime Start Membership Widget is not present on Homepage for expired user");
		iAppCommonMethods.scrollDown();
		headerPageMethods.clickMenuIcon();
		softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
		softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		softAssert.assertAll();

	}

	@DataProvider(name = "sectionName")
	private Object[] sectionName() {
		allSections.removeAll(Arrays.asList(sectionToExlude.split("\\s*,\\s*")));
		return allSections.toArray(new Object[allSections.size()]);
	}

	@DataProvider(name = "IndustrySubSections")
	public Object[] getIndustrySection() {
		String api = AppFeeds.homeMarketFeed;
		List<String> sections = ApiHelper.getValueFromAPI(api, "nm", "sn", "Industry", "Item", "ss");
		return sections.toArray(new Object[sections.size()]);
	}

	@AfterClass(alwaysRun = true)
	public void quit() throws Exception {
		appDriver.quit();
	}

	private void verifyHeadlineNDateUIvalidation(String headline) {
		Boolean clickFlag = appListingPageMethods.clickHeadline(headline);
		if (clickFlag != null)
			softAssert.assertTrue(clickFlag, "<br>-Unable to click story:" + headline);
		Boolean headlineFlag = storyPageMethods.checkHeadlineLength();
		softAssert.assertTrue(headlineFlag != null, "<br>-Headline not found on clicking:" + headline);
		if (headlineFlag != null)
			softAssert.assertTrue(headlineFlag,
					"<br>Headline not shown on story page, list page headline is:" + headline);
		if (headlineFlag == null || !headlineFlag)
			new ScreenShots().seleniumNativeScreenshot(appDriver, "headlineMissingHome");
		WaitUtil.sleep(1000);
		iAppCommonMethods.swipeUp();
		Boolean dateFlag = storyPageMethods.checkIfDateinRange(2);
		softAssert.assertTrue(dateFlag != null, "<br>-Date not found for story:" + headline);
		if (dateFlag != null)
			softAssert.assertTrue(dateFlag, "<br>Story->" + headline + " is older than 2 days");
		iAppCommonMethods.swipeDown();

	}

	private boolean navigateToHome() {
		new HeaderPageMethods(appDriver).clickMenuIconSwipeUpFirst();
		return new MenuPageMethods(appDriver).clickMenuByLabel("Home");
	}

	private boolean scrollToSectionFromTop(String sectionName) {
		boolean scrolledFromCurrentPostn = homePageMethods.clickNavigateBackWhileScrolling(sectionName);
		globalFlag = false;
		if (!scrolledFromCurrentPostn) {
			System.out.println("sectionName:" + sectionName + " navigating home");
			if (!navigateToHome())
				return false;
			return homePageMethods.clickNavigateBackWhileScrolling(sectionName);
		}
		return scrolledFromCurrentPostn;
	}

	public void verifySectionNewsList(String sectionName, int newsCount) {
		for (ArrayList<String> newsItem : newsList) {
			String headline = newsItem.get(1);
			System.out.println("--headline name is------" + headline);
			if (headline.equalsIgnoreCase("NA"))
				continue;
			boolean headlineflag = appListingPageMethods.verifyHeadlineIsDisplayed(headline);
			softAssert.assertTrue(headlineflag,
					"The page is not scrolled upto- " + headline + " for section " + sectionName);
			if (newsItem.get(2).equals("true") || newsItem.get(3).equals("true")) {
				primeNewsList.add(headline);
				continue;
			}
			if (headlineflag) {
				if (newsCount > 0 && sectionName != "") {
					String ID = newsItem.get(0);
					verifyArticleShowPageDetails(headline, ID);
					newsCount--;
				}

			} else {
				continue;
			}

		}
	}

	public void verifyAPIdataWithUIdata(String headline, String ID) {
		HashMap<String, String> apiResponseList = getStoryFeedFromAPI(ID);
		System.out.println("apiResponseList-  " + apiResponseList);
		HashMap<String, String> uiDataList = storyPageMethods.getUIArticleDetails();
		System.out.println("uiDataList-  " + uiDataList);
		for (String apiKey : apiResponseList.keySet()) {
			System.out.println("KEY-  " + apiKey);
			String apiValue = apiResponseList.get(apiKey);
			System.out.println("**apiValue**- " + apiValue);
			String uiValue = uiDataList.get(apiKey);
			System.out.println("**uiValue**- " + uiValue);
			if (apiValue == null || apiValue.isEmpty()) {
				softAssert.assertNull(uiValue, "API value is null or empty for UI value" + uiValue);
			} else {
				softAssert.assertNotNull(uiValue, "UI value is null for Key " + apiKey + " API value " + apiValue);
				System.out.println("Result------------ " + apiValue.equals(uiValue));
				softAssert.assertTrue(apiValue.equals(uiValue), "For headline--" + headline + " - Key " + apiKey
						+ " API value " + apiValue + "---------does not match with UI Value------" + uiValue);
			}

		}
	}

	public void verifyArticleShowPageDetails(String headline, String id) {
		verifyHeadlineNDateUIvalidation(headline);
		verifyAPIdataWithUIdata(headline, id);
		Boolean widgetflag = appListingPageMethods.verifyETprimeWidget();
		softAssert.assertTrue(widgetflag, "ET prime support widget not displayed ");
		Boolean trialButton = appListingPageMethods.verifyStartFreeTrialLink();
		softAssert.assertTrue(trialButton, "ET prime support widget Start Free Trial Link  not displayed ");
		iAppCommonMethods.navigateBack(appDriver);
		WaitUtil.sleep(1000);
	}

	public HashMap<String, String> getStoryFeedFromAPI(String ID) {
		String URL = String.format(AppFeeds.dataHomeFeed, Config.fetchConfigProperty("APIPlatformName"),
				Config.fetchConfigProperty("ETAppVersion"), ID);
		Response response = given().header("Accept", "application/json").header("Accept-Charset", "UTF-8")
				.header("Content-Type", "application/json;charset=utf-8").when().config(config).get(URL);
		int status = response.statusCode();
		softAssert.assertEquals(status, 200, "URL:" + URL + " is not giving 200, instead is " + status);
		long time = response.time();
		softAssert.assertTrue(time < 5000L,
				"Feed:" + URL + " is not giving response in 5 secs, instead is taking " + time);
		String output = response.getBody().asString();
		return appListingPageMethods.parseStoryFeedAPIData(output);

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
