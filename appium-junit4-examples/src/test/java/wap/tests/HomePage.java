package wap.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import busineslogic.BusinessLogicMethods;
import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.urlRepo.WapFeedRepo;
import common.utilities.CommonMethods;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import wap.pagemethods.AdTechMethods;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.WapListingPageMethods;
import web.pagemethods.WebBaseMethods;

import static io.restassured.RestAssured.given;

public class HomePage extends BaseTest {
	private String wapUrl;
	private HomePageMethods homePageMethods;
	private WapListingPageMethods wapListingPageMethods;
	private AdTechMethods adTechMethods;
	private String excelPath = "MenuSubMenuData";
	private Map<String, String> testData;
	private Map<String, String> industryHomeTopScroll;
	private SoftAssert softAssert;
	private String primeUrl;
	RestAssuredConfig config;
	Config configObject = new Config();
	File file = new File("./src/main/resources/properties/subSectionMsids.properties");
	private CommonMethods commonMethods;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		WebBaseMethods.scrollFixedHeightMultipleTimes(10, "Bottom");
		homePageMethods = new HomePageMethods(driver);
		wapListingPageMethods = new WapListingPageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		// testData = ExcelUtil.getTestDataRow(excelPath, "verifyHomePage", 1);
		// industryHomeTopScroll = ExcelUtil.getTestDataRow(excelPath,
		// "verifyIndustryHomeNavigation", 1);
		primeUrl = "https://prime.economictimes.indiatimes.com";

		config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
				.setParam("CONNECTION_MANAGER_TIMEOUT", 1000).setParam("SO_TIMEOUT", 1000));

		config.getConnectionConfig().closeIdleConnectionsAfterEachResponse();
		commonMethods = new CommonMethods(driver);

	}

	@Test(description = "verify the top stories on ET homepage")
	public void verifyTopSectionStories() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		homePageMethods.jqueryInjForReactPages();
		WaitUtil.sleep(3000);
		int articleCount = 15;

		softAssert = new SoftAssert();

		List<String> topNewsStories = homePageMethods.getAllTopSectionStories();
		System.out.println(topNewsStories);
		int count = topNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Top headlines on the ET homepage should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> topStoriesNewsDup = VerificationUtil.isListUnique(topNewsStories);
		softAssert.assertTrue(topStoriesNewsDup.isEmpty(),
				"<br>- Top stories is having duplicate stories, repeating story(s)->" + topStoriesNewsDup);
		topNewsStories.forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Stories link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();

	}

	@Test(description = "Verify Home page Mutual Funds section", groups = { "Home Page" }, priority = 6)
	public void verifyHomeMFSection() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		int articleCount = 5;
		String section = "Mutual Funds";
		softAssert = new SoftAssert();

		List<String> mFNewsStories = VerificationUtil.getLinkTextList(homePageMethods.getSectionNewsHeadlines(section));
		int count = mFNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under MF sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> mFNewsDup = VerificationUtil.isListUnique(mFNewsStories);
		softAssert.assertTrue(mFNewsDup.isEmpty(),
				"<br>- MF is having duplicate stories, repeating story(s)->" + mFNewsDup);
		homePageMethods.getSectionHref(section).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top 10 MF link <a href=" + keyword + ">" + keyword
					+ "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertTrue(homePageMethods.getMoreSectionNewsHref(section).contains("mutual-funds"),
				"More link is not taking to MF page,instead it takes to "
						+ homePageMethods.getMoreSectionNewsHref(section));
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page Markets section", groups = { "Home Page" }, priority = 8)
	public void verifyHomeMarketsSection() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		int articleCount = 5;
		String section = "ET Markets";
		softAssert = new SoftAssert();
		List<String> marketNewsStories = VerificationUtil
				.getLinkTextList(homePageMethods.getSectionNewsHeadlines(section));
		int count = marketNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under market sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> marketNewsDup = VerificationUtil.isListUnique(marketNewsStories);
		softAssert.assertTrue(marketNewsDup.isEmpty(),
				"<br>- Markets is having duplicate stories, repeating story(s)->" + marketNewsDup);
		homePageMethods.getSectionHref(section).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top 10 market link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});

		softAssert.assertTrue(homePageMethods.getMoreMarketsHref().contains("markets"),
				"More link is not taking to markets page,instead it takes to " + homePageMethods.getMoreMarketsHref());
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page Politics section", groups = { "Home Page" }, priority = 9)
	public void verifyHomePoliticsSection() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		int articleCount = 5;
		String section = "Politics";
		softAssert = new SoftAssert();

		List<String> politicsNewsStories = VerificationUtil
				.getLinkTextList(homePageMethods.getSectionNewsHeadlines(section));
		int count = politicsNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under politics sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> politicsNewsDup = VerificationUtil.isListUnique(politicsNewsStories);
		softAssert.assertTrue(politicsNewsDup.isEmpty(),
				"<br>- Politics is having duplicate stories, repeating story(s)->" + politicsNewsDup);
		homePageMethods.getSectionHref(section).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top 10 politics link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		System.out.println(homePageMethods.getMoreSectionNewsHref(section));
		softAssert.assertTrue(homePageMethods.getMoreSectionNewsHref(section).contains("politics"),
				"More link is not taking to politics page,instead it takes to "
						+ homePageMethods.getMoreSectionNewsHref(section));
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page Small Biz section", groups = { "Home Page" }, priority = 13)
	public void verifyHomeSmallBizSection() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		int articleCount = 5;
		String section = "Rise";
		softAssert = new SoftAssert();

		List<String> smallBizNewsStories = VerificationUtil
				.getLinkTextList(homePageMethods.getSectionNewsHeadlines(section));
		int count = smallBizNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Small Biz sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> smallBizNewsDup = VerificationUtil.isListUnique(smallBizNewsStories);
		softAssert.assertTrue(smallBizNewsDup.isEmpty(),
				"<br>- Small Biz is having duplicate stories, repeating story(s)->" + smallBizNewsDup);
		homePageMethods.getSectionHref(section).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top 10 Small Biz link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertTrue(homePageMethods.getMoreSectionNewsHref(section).contains("small-biz"),
				"More link is not taking to Small Biz page,instead it takes to "
						+ homePageMethods.getMoreSectionNewsHref(section));
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page Tech section", groups = { "Home Page" }, priority = 14)
	public void verifyHomeTechSection() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		int articleCount = 5;
		String section = "Tech";
		softAssert = new SoftAssert();
		List<String> techNewsStories = VerificationUtil
				.getLinkTextList(homePageMethods.getSectionNewsHeadlines(section));
		int count = techNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Tech sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> techNewsDup = VerificationUtil.isListUnique(techNewsStories);
		softAssert.assertTrue(techNewsDup.isEmpty(),
				"<br>- Tech is having duplicate stories, repeating story(s)->" + techNewsDup);
		homePageMethods.getSectionHref(section).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top 10 Tech link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertTrue(homePageMethods.getMoreSectionNewsHref(section).contains("tech"),
				"More link is not taking to Tech page,instead it takes to "
						+ homePageMethods.getMoreSectionNewsHref(section));
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page Economy section", groups = { "Home Page" }, priority = 16)
	public void verifyHomeEconomySection() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		int articleCount = 5;
		String section = "Economy";
		softAssert = new SoftAssert();

		List<String> economyNewsStories = VerificationUtil
				.getLinkTextList(homePageMethods.getSectionNewsHeadlines(section));
		int count = economyNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Economy sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> economyNewsDup = VerificationUtil.isListUnique(economyNewsStories);
		softAssert.assertTrue(economyNewsDup.isEmpty(),
				"<br>- Economy is having duplicate stories, repeating story(s)->" + economyNewsDup);
		homePageMethods.getSectionHref(section).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top 10 Economy link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertTrue(homePageMethods.getMoreSectionNewsHref(section).contains("economy"),
				"More link is not taking to Economy page,instead it takes to "
						+ homePageMethods.getMoreSectionNewsHref(section));
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the multimedia section on the homepage")
	public void verifyMultimediaSection() {

		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		homePageMethods.jqueryInjForReactPages();
		WaitUtil.sleep(2000);
		int articleCount = 10;
		softAssert = new SoftAssert();

		List<String> multiMediaLinks = homePageMethods.getMultimediaLinks();
		System.out.println(multiMediaLinks);
		int count = multiMediaLinks.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Stories under multimedia sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> multimediaNewsDup = VerificationUtil.isListUnique(multiMediaLinks);
		softAssert.assertTrue(multimediaNewsDup.isEmpty(),
				"<br>- Multimedia section is having duplicate stories, repeating story(s)url->" + multimediaNewsDup);
		multiMediaLinks.forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			System.out.println(keyword + ": " + topLinksResponseCode);

			softAssert.assertEquals(topLinksResponseCode, 200, "<br>-Multimedia stories link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		List<String> webLinks = commonMethods.getLinksRedirectingToWeb(multiMediaLinks);
		softAssert.assertTrue(webLinks.size() == 0,
				"<br>-Few links " + webLinks + " on the multimedia section is redirecting to web links");

		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Podcast section on the homepage")
	public void verifyPodcastSection() {

		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		homePageMethods.jqueryInjForReactPages();
		WaitUtil.sleep(2000);
		int articleCount = 5;
		softAssert = new SoftAssert();

		List<String> podcastLinks = homePageMethods.getPodcastStoryLinks();
		System.out.println(podcastLinks);
		int count = podcastLinks.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Stories under podcast section should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> podcastNewsDup = VerificationUtil.isListUnique(podcastLinks);
		softAssert.assertTrue(podcastNewsDup.isEmpty(),
				"<br>- Podcast section is having duplicate stories, repeating story(s)url->" + podcastNewsDup);
		podcastLinks.forEach(keyword -> {
			int podcastLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			System.out.println(keyword + ": " + podcastLinksResponseCode);

			softAssert.assertEquals(podcastLinksResponseCode, 200, "<br>-Podcast link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + podcastLinksResponseCode);
		});

		softAssert.assertAll();

	}

	@Test(description = "This test verifies the prime section on the homepage")
	public void verifyPrimeWidgetSection() {

		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		homePageMethods.jqueryInjForReactPages();
		WaitUtil.sleep(2000);
		int articleCount = 3;
		softAssert = new SoftAssert();

		List<String> primeWidgetStoryLinks = homePageMethods.getPrimeLinks();
		System.out.println(primeWidgetStoryLinks);
		int count = primeWidgetStoryLinks.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Stories under prime widget should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> primeWidgetNewsDup = VerificationUtil.isListUnique(primeWidgetStoryLinks);
		softAssert.assertTrue(primeWidgetNewsDup.isEmpty(),
				"<br>- Prime section is having duplicate stories, repeating story(s)url->" + primeWidgetNewsDup);
		primeWidgetStoryLinks.forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			System.out.println(keyword + ": " + topLinksResponseCode);

			softAssert.assertEquals(topLinksResponseCode, 200, "<br>-Prime stories on homepage link <a href=" + keyword
					+ ">" + keyword + "</a> is throwing " + topLinksResponseCode);
		});
		String startYourTrialLink = homePageMethods.getStartYourFreeTrialPrimeWidgetLink();
		if (startYourTrialLink.length() > 0) {
			int startTrialLinkResponseCode = HTTPResponse.checkResponseCode(startYourTrialLink);
			System.out.println(startYourTrialLink + ": " + startTrialLinkResponseCode);

			softAssert.assertEquals(startTrialLinkResponseCode, 200,
					"<br>-Prime widget start your trial link on homepage <a href=" + startYourTrialLink + ">"
							+ startYourTrialLink + "</a> is throwing " + startTrialLinkResponseCode);
		} else {
			softAssert.assertTrue(false, "Start your free trial link in the prime widget is not shown on the homepage");
		}
		softAssert.assertAll();

	}

	@Test(description = "this test verifies the Industry section on the homepage")
	public void verifyIndustrySection() {
		String mainSection = "Industry";
		List<String> subSections = Arrays.asList("Featured_Industry", "Auto", "Banking/F", "Cons. Pro", "Energy",
				"Ind'l Goo", "Healthcar", "Services", "Media/Ent", "Transport");
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		homePageMethods.jqueryInjForReactPages();
		WaitUtil.sleep(5000);
		int articleCount = 5;
		softAssert = new SoftAssert();
		subSections.forEach(section -> {
			boolean flag = false;
			flag = homePageMethods.clickSliderWidgetSubSec(mainSection, section.split("_")[0]);
			if (flag) {

				String sectionMsid = configObject.fetchConfig(file, section.replace(" ", "_"));

				Response response = given().when().config(config).param("type", "widget_subsecnews1")
						.param("msid", sectionMsid).get(WapFeedRepo.HomepageWidgetsSubSection_Feed);

				int statusCode = response.statusCode();
				System.out.println("statuscode: " + statusCode);
				softAssert.assertTrue(statusCode == 200, "The api: " + WapFeedRepo.HomepageWidgetsSubSection_Feed
						+ " is giving response code " + statusCode + " for msid:" + sectionMsid);
				List<String> subLinksFromApi = (List<String>) response.jsonPath().getList("searchResult.data*.url")
						.get(0);
				int index = subLinksFromApi.indexOf(null);
				System.out.println("Index ::" + index);
				if (index != -1) {
					subLinksFromApi.remove(index);
				}
				System.out.println("values from api for section: " + section + " list: " + subLinksFromApi);
				subLinksFromApi = homePageMethods.convertAllLinksIntoAbsoluteLinks(subLinksFromApi);
				System.out.println("converted list: " + subLinksFromApi);
				softAssert.assertTrue(subLinksFromApi.size() == articleCount,
						"<br>-the stories under the section " + section + " from api for " + mainSection
								+ " widget should be equal to " + articleCount + " instead found: "
								+ subLinksFromApi.size());
				List<String> subLinksFromUi = homePageMethods.getSliderWidgetSubSecStoryLinks(mainSection);
				softAssert.assertTrue(subLinksFromUi.size() == articleCount,
						"<br>-the stories under the section " + section + " on UI for " + mainSection
								+ " widget should be equal to " + articleCount + " instead found: "
								+ subLinksFromUi.size());

				System.out.println("List of urls from ui section:" + section + " list: " + subLinksFromUi);
				List<String> missingLinks = VerificationUtil.differenceTwoLists(subLinksFromUi, subLinksFromApi);
				System.out.println("list of missing links from api in ui is: " + missingLinks);
				softAssert.assertTrue(
						VerificationUtil.validateContentOfList(subLinksFromApi, subLinksFromUi)
								&& missingLinks.size() == 0,
						"<br>-The links from api are different from the links shown on ui for section " + section
								+ ", under " + mainSection + " widget, the links list:" + missingLinks);
				subLinksFromUi.forEach(keyword -> {
					int linksResponseCode = HTTPResponse.checkResponseCode(keyword);
					System.out.println(keyword + ": " + linksResponseCode);

					softAssert.assertEquals(linksResponseCode, 200,
							"<br>-Stories under the section " + section + " on " + mainSection + " widget <a href="
									+ keyword + ">" + keyword + "</a> is throwing " + linksResponseCode);

				});
				String moreLink = homePageMethods.getSliderWidgetSubSectionMoreLink(mainSection);
				int moreLinkResponseCode = HTTPResponse.checkResponseCode(moreLink);
				System.out.println(moreLink + ": " + moreLinkResponseCode);
				softAssert.assertTrue(moreLink.contains("industry"), "<br>-the more link: " + moreLink
						+ " under section-" + section + " on " + mainSection + " widget is not proper");
				softAssert.assertTrue(moreLinkResponseCode == 200,
						"<br>-More link under the section " + section + " on " + mainSection + " widget <a href="
								+ moreLink + ">more link</a> is throwing " + moreLinkResponseCode);

			} else {
				softAssert.assertTrue(false,
						"<br>-The section " + section + " is not clickable under the " + mainSection + " widget");
			}

		});
		softAssert.assertAll();

	}

	@Test(description = "this test verifies the ET Wealth section on the homepage")
	public void verifyEtWealthSection() {
		String mainSection = "ET Wealth";
		List<String> subSections = Arrays.asList("Featured_Wealth", "Tax", "Save", "Invest", "Insure", "Spend",
				"Borrow", "Earn", "Plan", "Real Esta");
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		homePageMethods.jqueryInjForReactPages();
		WaitUtil.sleep(5000);
		int articleCount = 5;
		softAssert = new SoftAssert();
		subSections.forEach(section -> {
			boolean flag = false;
			flag = homePageMethods.clickSliderWidgetSubSec(mainSection, section.split("_")[0]);
			if (flag) {

				String sectionMsid = configObject.fetchConfig(file, section.replace(" ", "_"));

				Response response = given().when().config(config).param("type", "widget_subsecnews1")
						.param("msid", sectionMsid).get(WapFeedRepo.HomepageWidgetsSubSection_Feed);
				int statusCode = response.statusCode();
				System.out.println("statuscode: " + statusCode);
				softAssert.assertTrue(statusCode == 200, "The api: " + WapFeedRepo.HomepageWidgetsSubSection_Feed
						+ " is giving response code " + statusCode + " for msid:" + sectionMsid);

				List<String> subLinksFromApi = (List<String>) response.jsonPath().getList("searchResult.data*.url")
						.get(0);
				int index = subLinksFromApi.indexOf(null);
				System.out.println("Index ::" + index);
				if (index != -1) {
					subLinksFromApi.remove(index);
				}

				System.out.println("values from api for section: " + section + " list: " + subLinksFromApi);
				subLinksFromApi = homePageMethods.convertAllLinksIntoAbsoluteLinks(subLinksFromApi);
				System.out.println("converted list: " + subLinksFromApi);

				softAssert.assertTrue(subLinksFromApi.size() == articleCount,
						"<br>-the stories under the section " + section + " from api for " + mainSection
								+ " widget should be equal to " + articleCount + " instead found: "
								+ subLinksFromApi.size());
				List<String> subLinksFromUi = homePageMethods.getSliderWidgetSubSecStoryLinks(mainSection);
				softAssert.assertTrue(subLinksFromUi.size() == articleCount,
						"<br>-the stories under the section " + section + " on UI for " + mainSection
								+ " widget should be equal to " + articleCount + " instead found: "
								+ subLinksFromUi.size());

				System.out.println("List of urls from ui section:" + section + " list: " + subLinksFromUi);
				List<String> missingLinks = VerificationUtil.differenceTwoLists(subLinksFromUi, subLinksFromApi);
				System.out.println("list of missing links from api in ui is: " + missingLinks);
				softAssert.assertTrue(
						VerificationUtil.validateContentOfList(subLinksFromApi, subLinksFromUi)
								&& missingLinks.size() == 0,
						"<br>-The links from api are different from the links shown on ui for section " + section
								+ ", under " + mainSection + " widget, the links list:" + missingLinks);
				subLinksFromUi.forEach(keyword -> {
					int linksResponseCode = HTTPResponse.checkResponseCode(keyword);
					System.out.println(keyword + ": " + linksResponseCode);

					softAssert.assertEquals(linksResponseCode, 200,
							"<br>-Stories under the section " + section + " on " + mainSection + " widget <a href="
									+ keyword + ">" + keyword + "</a> is throwing " + linksResponseCode);

				});
				String moreLink = homePageMethods.getSliderWidgetSubSectionMoreLink(mainSection);
				int moreLinkResponseCode = HTTPResponse.checkResponseCode(moreLink);
				System.out.println(moreLink + ": " + moreLinkResponseCode);
				softAssert.assertTrue(moreLink.contains("wealth") || moreLink.contains("personal-finance"),
						"<br>-the more link: " + moreLink + " under section-" + section + " on " + mainSection
								+ " widget is not proper");
				softAssert.assertTrue(moreLinkResponseCode == 200,
						"<br>-More link under the section " + section + " on " + mainSection + " widget <a href="
								+ moreLink + ">more link</a> is throwing " + moreLinkResponseCode);

			} else {
				softAssert.assertTrue(false,
						"<br>-The section " + section + " is not clickable under the " + mainSection + " widget");
			}

		});
		softAssert.assertAll();

	}

	@Test(description = "this test verifies the ET Panache section on the homepage")
	public void verifyEtPanacheSection() {
		String mainSection = "ET Panache";
		List<String> subSections = Arrays.asList("Featured_Panache", "Worklife", "Cars & Bi", "Lifestyle",
				"Food & Dr", "Health", "People");
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		homePageMethods.jqueryInjForReactPages();
		WaitUtil.sleep(5000);
		int articleCount = 5;
		softAssert = new SoftAssert();
		subSections.forEach(section -> {
			boolean flag = false;
			flag = homePageMethods.clickSliderWidgetSubSec(mainSection, section.split("_")[0]);
			if (flag) {

				String sectionMsid = configObject.fetchConfig(file, section.replace(" ", "_"));

				Response response = given().when().config(config).param("type", "widget_subsecnews1")
						.param("msid", sectionMsid).get(WapFeedRepo.HomepageWidgetsSubSection_Feed);
				int statusCode = response.statusCode();
				System.out.println("statuscode: " + statusCode);
				softAssert.assertTrue(statusCode == 200, "The api: " + WapFeedRepo.HomepageWidgetsSubSection_Feed
						+ " is giving response code " + statusCode + " for msid:" + sectionMsid);

				List<String> subLinksFromApi = (List<String>) response.jsonPath().getList("searchResult.data*.url")
						.get(0);
				int index = subLinksFromApi.indexOf(null);
				System.out.println("Index ::" + index);
				if (index != -1) {
					subLinksFromApi.remove(index);
				}
				System.out.println("values from api for section: " + section + " list: " + subLinksFromApi);
				subLinksFromApi = homePageMethods.convertAllLinksIntoAbsoluteLinks(subLinksFromApi);
				System.out.println("converted list: " + subLinksFromApi);

				softAssert.assertTrue(subLinksFromApi.size() == articleCount,
						"<br>-the stories under the section " + section + " from api for " + mainSection
								+ " widget should be equal to " + articleCount + " instead found: "
								+ subLinksFromApi.size());
				List<String> subLinksFromUi = homePageMethods.getSliderWidgetSubSecStoryLinks(mainSection);
				softAssert.assertTrue(subLinksFromUi.size() == articleCount,
						"<br>-the stories under the section " + section + " on UI for " + mainSection
								+ " widget should be equal to " + articleCount + " instead found: "
								+ subLinksFromUi.size());

				System.out.println("List of urls from ui section:" + section + " list: " + subLinksFromUi);
				List<String> missingLinks = VerificationUtil.differenceTwoLists(subLinksFromUi, subLinksFromApi);
				System.out.println("list of missing links from api in ui is: " + missingLinks);
				softAssert.assertTrue(
						VerificationUtil.validateContentOfList(subLinksFromApi, subLinksFromUi)
								&& missingLinks.size() == 0,
						"<br>-The links from api are different from the links shown on ui for section " + section
								+ ", under " + mainSection + " widget, the links list:" + missingLinks);
				subLinksFromUi.forEach(keyword -> {
					int linksResponseCode = HTTPResponse.checkResponseCode(keyword);
					System.out.println(keyword + ": " + linksResponseCode);

					softAssert.assertEquals(linksResponseCode, 200,
							"<br>-Stories under the section " + section + " on " + mainSection + " widget <a href="
									+ keyword + ">" + keyword + "</a> is throwing " + linksResponseCode);

				});
				String moreLink = homePageMethods.getSliderWidgetSubSectionMoreLink(mainSection);
				int moreLinkResponseCode = HTTPResponse.checkResponseCode(moreLink);
				System.out.println(moreLink + ": " + moreLinkResponseCode);
				softAssert.assertTrue(moreLink.contains("panache"), "<br>-the more link: " + moreLink
						+ " under section-" + section + " on " + mainSection + " widget is not proper");
				softAssert.assertTrue(moreLinkResponseCode == 200,
						"<br>-More link under the section " + section + " on " + mainSection + " widget <a href="
								+ moreLink + ">more link</a> is throwing " + moreLinkResponseCode);

			} else {
				softAssert.assertTrue(false,
						"<br>-The section " + section + " is not clickable under the " + mainSection + " widget");
			}

		});
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the spotlight section")
	public void verifySpotlightSection() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		homePageMethods.jqueryInjForReactPages();
		WaitUtil.sleep(5000);
		int articleCount = 3;
		List<String> spotLightSections = homePageMethods.getSpotlightSectionsText();
		System.out.println("spotLightSections -----" + spotLightSections);

		softAssert = new SoftAssert();
		spotLightSections.forEach(section -> {
			List<String> subSectionLinks = homePageMethods.getSpotlightSubSectionsStoryLinks(section);
			System.out.println(subSectionLinks);
			int count = subSectionLinks.size();
			softAssert.assertTrue(count >= articleCount, "<br>- Stories under Spotlight section " + section
					+ " should be more than " + articleCount + " in number, instead found " + count);
			List<String> subNewsDup = VerificationUtil.isListUnique(subSectionLinks);
			softAssert.assertTrue(subNewsDup.isEmpty(), "<br>- Stories under Spotlight section " + section
					+ " is having duplicate stories, repeating story(s)url->" + subNewsDup);
			subSectionLinks.forEach(keyword -> {
				int subLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
				System.out.println(keyword + ": " + subLinksResponseCode);

				softAssert.assertEquals(subLinksResponseCode, 200, "<br>-Stories under Spotlight section " + section
						+ " link <a href=" + keyword + ">" + keyword + "</a> is throwing " + subLinksResponseCode);
			});

		});
		softAssert.assertAll();
		
	}

	@Test(description = "This test verifies the markets widget on homepage")
	public void verifyMarketsWidget() {
		String section = "ET Markets";
		List<String> subSections = Arrays.asList("Benchmark", "Gainers", "Losers", "Movers");
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		homePageMethods.jqueryInjForReactPages();
		WaitUtil.sleep(5000);
		int linksCount = 4;
		softAssert = new SoftAssert();
		subSections.forEach(marketSection -> {
			boolean isSubsectionClicked = homePageMethods.clickMarketSubSectionTab(marketSection);
			if (isSubsectionClicked) {
				List<String> widgetSectionLinks = homePageMethods.getMarketWidgetLinks();
				softAssert.assertTrue(widgetSectionLinks.size() == linksCount,
						"<br>-The links count " + widgetSectionLinks.size() + " shown under the markets widget section "
								+ marketSection + " is not equal to expected count " + linksCount);

				if (marketSection.equalsIgnoreCase("Benchmark")) {
					verifyMarketsBechmarkSection(softAssert, marketSection);
				} else {

					verifyMarketSubSection(softAssert, marketSection);
				}
			} else {
				softAssert.assertTrue(false,
						"The subsection: " + marketSection + " is not clickable on markets widget on ET homepage");
			}

		});
		softAssert.assertAll();

	}

	public SoftAssert verifyMarketsBechmarkSection(SoftAssert softAssert, String sectionName) {
		try {
			String feed = WapFeedRepo.getMarketWidgetFeedRepo(sectionName);
			Response response = given().when().config(config).get(feed);
			int statusCode = response.statusCode();
			System.out.println("statuscode: " + statusCode);
			softAssert.assertTrue(statusCode == 200, "The api: " + feed + " is giving response code" + statusCode);

			Map<String, Map<String, String>> valueMap = response.jsonPath().getMap("$.");
			valueMap.remove("market_status");
			int size = valueMap.keySet().size();
			softAssert.assertTrue(size == 4,
					"All links are not there in api for benchmark expected count: 4 and actual found: " + size);
			valueMap.keySet().forEach(key -> {
				Map<String, String> values = valueMap.get(key);
				if (key.equalsIgnoreCase("sensex") || key.equalsIgnoreCase("nifty")) {
					String currentIndexValue = values.get("currentIndexValue");
					String netChange = values.get("netChange");
					String percentChange = values.get("percentChange");
					String declines = values.get("declines");
					String advances = values.get("advances");

					softAssert.assertTrue(currentIndexValue != null || currentIndexValue.length() > 0,
							"the currentIndexValue for " + key
									+ " is not shown under benchmark on markets widget on ET homepage");
					softAssert.assertTrue(netChange != null || netChange.length() > 0, "the netChange for " + key
							+ " is not shown under benchmark on markets widget on ET homepage");
					softAssert.assertTrue(declines != null || declines.length() > 0, "the declines for " + key
							+ " is not shown under benchmark on markets widget on ET homepage");
					softAssert.assertTrue(percentChange != null || percentChange.length() > 0, "the percentChange for "
							+ key + " is not shown under benchmark on markets widget on ET homepage");
					softAssert.assertTrue(advances != null || advances.length() > 0, "the advances for " + key
							+ " is not shown under benchmark on markets widget on ET homepage");

				} else if (key.equalsIgnoreCase("gold")) {
					String lastTradedPrice = values.get("lastTradedPrice");
					String netChange = values.get("netChange");
					String percentChange = values.get("percentChange");

					softAssert.assertTrue(netChange != null || netChange.length() > 0, "the netChange for " + key
							+ " is not shown under benchmark on markets widget on ET homepage");
					softAssert.assertTrue(lastTradedPrice != null || lastTradedPrice.length() > 0,
							"the lastTradedPrice for " + key
									+ " is not shown under benchmark on markets widget on ET homepage");
					softAssert.assertTrue(percentChange != null || percentChange.length() > 0, "the percentChange for "
							+ key + " is not shown under benchmark on markets widget on ET homepage");

				} else {
					String spotRate = values.get("spotRate");
					String change = values.get("change");
					String percentChange = values.get("percentChange");

					softAssert.assertTrue(change != null || change.length() > 0,
							"the change for " + key + " is not shown under benchmark on markets widget on ET homepage");
					softAssert.assertTrue(spotRate != null || spotRate.length() > 0, "the spotRate for " + key
							+ " is not shown under benchmark on markets widget on ET homepage");
					softAssert.assertTrue(percentChange != null || percentChange.length() > 0, "the percentChange for "
							+ key + " is not shown under benchmark on markets widget on ET homepage");

				}

			});
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "Unable to fetch values");
		}
		return softAssert;
	}

	public SoftAssert verifyMarketSubSection(SoftAssert softAssert, String sectionName) {
		try {
			String feed = WapFeedRepo.getMarketWidgetFeedRepo(sectionName);

			Response response = given().when().config(config).get(WapFeedRepo.getMarketWidgetFeedRepo(sectionName));
			int statusCode = response.statusCode();
			System.out.println("statuscode: " + statusCode);
			softAssert.assertTrue(statusCode == 200, "The api: " + feed + " is giving response code" + statusCode);

			int linksCount = 4;
			List<String> companyList = response.jsonPath().getList("stocks*.companyName2");
			System.out.println("companyList: " + companyList);
			softAssert.assertTrue(companyList.size() == linksCount, "The links in api for " + sectionName
					+ " is not equal to the expected count " + companyList.size());
			companyList.forEach(company -> {
				Map<String, String> companyDataFromApi = response
						.path("stocks.find { it.companyName2 == '" + company + "' }", "");
				String lastTradedPrice = companyDataFromApi.get("lastTradedPrice");
				String netChange = companyDataFromApi.get("netChange");
				String percentChange = companyDataFromApi.get("percentChange");
				System.out.println("companyData from api is " + companyDataFromApi);
				System.out.println("lastTradedPrice: " + lastTradedPrice + " netChange: " + netChange
						+ " percentChange: " + percentChange);
				softAssert.assertTrue(lastTradedPrice != null || lastTradedPrice.length() > 0,
						"<br>Last traded price is not shown for company " + company + " under section " + sectionName
								+ " on markets widget on ET homepage");
				softAssert.assertTrue(netChange != null || netChange.length() > 0,
						"<br>Last traded price is not shown for company " + company + " under section " + sectionName
								+ " on markets widget on ET homepage");
				softAssert.assertTrue(percentChange != null || percentChange.length() > 0,
						"<br>Last traded price is not shown for company " + company + " under section " + sectionName
								+ " on markets widget on ET homepage");

			});
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "Unable to fetch values");
		}
		return softAssert;
	}

	@Test(description = "This test verifies the markets section in the top stories on the homepage")
	public void verifyMarketsWidgetTopStories() {

		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		homePageMethods.jqueryInjForReactPages();
		WaitUtil.sleep(2000);
		int articleCount = 3;
		softAssert = new SoftAssert();

		List<String> marketsWidgetTopStoriesLinks = homePageMethods.getMarketWidgetTopStoriesLinks();
		System.out.println(marketsWidgetTopStoriesLinks);
		int count = marketsWidgetTopStoriesLinks.size();
		softAssert.assertTrue(count >= articleCount,
				"<br>- Stories under markets widget in top stories should be more than " + articleCount
						+ " in number, instead found " + count);
		List<String> marketsWidgetNewsDup = VerificationUtil.isListUnique(marketsWidgetTopStoriesLinks);
		softAssert.assertTrue(marketsWidgetNewsDup.isEmpty(),
				"<br>- Markets widget in top stories section is having duplicate stories, repeating story(s)url->"
						+ marketsWidgetNewsDup);
		marketsWidgetTopStoriesLinks.forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			System.out.println(keyword + ": " + topLinksResponseCode);

			softAssert.assertEquals(topLinksResponseCode, 200, "<br>-Markets stories on homepage link <a href="
					+ keyword + ">" + keyword + "</a> is throwing " + topLinksResponseCode);
		});

		softAssert.assertAll();

	}

	@Test(description = "This test verifies the presence of data on the market widget under top stories ")
	public void verifyTopStoriesMarketWidgetData() {
		softAssert = new SoftAssert();
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		homePageMethods.jqueryInjForReactPages();
		WaitUtil.sleep(5000);
		Map<String, String> dataMap = homePageMethods.getTopStoriesMarketStripWidgetData();
		try {
			dataMap.forEach((indexLink, indexValue) -> {
				softAssert.assertTrue(indexValue.length() > 0, "Data for the indexlink: " + indexLink
						+ " is not found on the markets widget in the top stories");

			});

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");

		}

		softAssert.assertAll();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	////////////////////////////////////// Deprecated//////////////////////////////////////////////////////////
	// Below functions are deprecated as they were for previous template of et
	////////////////////////////////////// homepage.

	@Test(description = "Verify Home Page top scroll section", groups = { "Home Page" }, priority = 0, enabled = false)
	public void verifyHomeTopSection() {
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("TabNames").split("\\s*,\\s*")));
		List<String> actualMenuItemList = homePageMethods.getTopSectionHeaders();
		Assert.assertTrue(VerificationUtil.listActualInExpected(expectedMenuItemList, actualMenuItemList),
				"Actual list of menu items is not matching the expected list of menu items.Missing items are->"
						+ VerificationUtil.getMissingMenuOptionList());
	}

	@Test(description = "Verify recency of articles", groups = { "Home Page" }, priority = 1, enabled = false)
	public void verifyTopNewsRecency() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		int numberDays = 2;
		softAssert = new SoftAssert();
		String tabsRecency = "Home";
		List<String> headlineRecency = homePageMethods.getOldStories(tabsRecency, numberDays);
		for (String entry : headlineRecency) {
			softAssert.assertTrue(false, "Article Recency verification for " + entry + " failed, article dated "
					+ homePageMethods.getArticleDate(entry));
		}
		softAssert.assertAll();
	}

	@Test(description = "Verify top news story pages", groups = { "Home Page" }, priority = 2, enabled = false)
	public void verifyTopNewsStoryPage() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		softAssert = new SoftAssert();
		List<String> headlinesHref = wapListingPageMethods.getAllHeadlineLinks();
		headlinesHref.forEach(href -> {
			int responseCode = HTTPResponse.checkResponseCode(href);
			softAssert.assertTrue(responseCode == 200, "<br> Response code for the url <a href=" + href + "> " + href
					+ "</a>is not 200,instead is " + responseCode);
		});
		// Map<String, String> headlineLink =
		// wapListingPageMethods.getHeadlinesLink(wapListingPageMethods.getHomeHeadlines());
		// Map<String, Boolean> storyPageHMap =
		// storyPageMethods.checkStoryPage(headlineLink);
		/*
		 * for (Map.Entry<String, Boolean> entry : storyPageHMap.entrySet()) {
		 * softAssert.assertTrue(entry.getValue(),
		 * "Headline not found on story page, listing page headline is " +
		 * entry.getKey()); }
		 */
		softAssert.assertAll();
	}

	@Test(description = "Verify Slideshow on home page", groups = { "Home Page" }, priority = 3, enabled = false)
	public void verifySlideshowHome() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		softAssert = new SoftAssert();
		List<WebElement> li = homePageMethods.getSlideShows();
		Assert.assertTrue(li.size() > 0, "Slideshow count on home page is zero");
		li.forEach(el -> {
			String href = el.getAttribute("href");
			int responseCode = HTTPResponse.checkResponseCode(href);
			softAssert.assertTrue(HTTPResponse.checkResponseCode(href) == 200, "Slideshow <a href=" + href + ">"
					+ href.substring(0, (href.length() / 2)) + "..</a> is throwing " + responseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the prime widget on home page", groups = {
			"Home Page" }, priority = 4, enabled = false)
	public void verifyPrimeWidget() {
		softAssert = new SoftAssert();
		int articleCount = 2;
		Assert.assertTrue(homePageMethods.isPrimeWidgetPresent(), "Prime Widget missing from home page");
		String headerUrl = homePageMethods.getSectionHeaderHref();
		softAssert.assertTrue(headerUrl.contains(primeUrl),
				"Link under of Prime Widget is not of prime instead " + headerUrl);
		Set<Map.Entry<String, String>> news = homePageMethods.getPrimeNews().entrySet();
		softAssert.assertTrue((news.size() > articleCount),
				"Prime headlines are not more than " + articleCount + " as expected");
		for (Map.Entry<String, String> map : news) {
			int newsResponseCode = HTTPResponse.checkResponseCode(map.getValue());
			softAssert.assertEquals(newsResponseCode, 200, "<br>- Prime news link <a href=" + map.getValue() + ">"
					+ map.getKey() + "</a> is throwing " + newsResponseCode);
		}
		String moreHref = homePageMethods.getPrimeMoreLinkHref();
		int resCode = HTTPResponse.checkResponseCode(moreHref);
		softAssert.assertEquals(resCode, 200,
				"<br>- Prime news link <a href=" + moreHref + ">" + moreHref + "</a> is throwing " + resCode);

		softAssert.assertAll();

	}

	@Test(description = "Verify Top Mutual Funds data widget", groups = { "Home Page" }, priority = 5, enabled = false)
	public void verifyMFWidget() {
		WebBaseMethods.scrollToTop();
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		softAssert = new SoftAssert();
		List<String> tabData = new ArrayList<>();
		Assert.assertTrue(homePageMethods.isMFWidgetVisible(), "MF widget not present on home page");
		String sectionLink = homePageMethods.getMfHeadingLinkHref();
		softAssert.assertTrue(sectionLink.contains("mffundsbycategory"),
				"<br>- Link under heading of MF Widget is not leading to MF page, instead is:" + sectionLink);
		List<String> mfTabNames = homePageMethods.getMfSubSectionHeaders();
		for (String mfTab : mfTabNames) {
			homePageMethods.openMfSubMenu(mfTab.toLowerCase());
			tabData = homePageMethods.getSubMenuMfEntriesHref(mfTab.toLowerCase());
			// System.out.println(tabData.size());
			if (tabData.size() > 0) {
				for (String href : tabData) {
					int linkResponseCode = HTTPResponse.checkResponseCode(href);
					softAssert.assertEquals(linkResponseCode, 200, "<br>- " + mfTab + " link <a href=" + href + ">"
							+ href + "</a> is throwing " + linkResponseCode);
				}
			} else if (tabData.size() == 0) {
				// System.out.println(mfTab);
				if (!mfTab.equalsIgnoreCase("equity")) {
					softAssert.assertTrue(
							homePageMethods.getNoResultText(mfTab.toLowerCase()).contains("THERE IS NO DATA FOR")
									|| homePageMethods.getNoResultText(mfTab.toLowerCase()).contains("NO 5 STAR"),
							"No data displayed for " + mfTab + " tab and no appropriate message displayed for the same."
									+ homePageMethods.getNoResultText(mfTab.toLowerCase()));
				} else
					softAssert.assertTrue(
							homePageMethods.getNoResultText("Equity").contains("THERE IS NO DATA FOR")
									|| homePageMethods.getNoResultText(mfTab.toLowerCase()).contains("NO 5 STAR"),
							"No data displayed for " + mfTab + " tab and no appropriate message displayed for the same."
									+ homePageMethods.getNoResultText("Equity"));
			}

		}
		softAssert.assertAll();
	}

	@Test(description = "Verify Markets data widget", groups = { "Home Page" }, priority = 7, enabled = false)
	public void verifyMarketsDataWidget() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		softAssert = new SoftAssert();
		int benchmarkCount = 4;
		double ETWAPSensexVal = 0.00;
		double ETWAPNiftyVal = 0.00;
		String NSESiteNiftyVal;
		String BSESiteSensexVal;
		double ETWAPGoldVal = 0.00;
		String McxSiteGoldVal;
		double ETWAPUsdVal = 0.00;
		String usdSiteValue;
		List<String> subMenuList = new LinkedList<>();
		Assert.assertTrue(homePageMethods.isMarketsWidgetVisible(), "Markets widget not present on home page");
		int count = homePageMethods.getMarketDataBenchMark().size();
		softAssert.assertEquals(count, benchmarkCount,
				"<br>- Benchmark data should be " + benchmarkCount + " in number, instead found " + count);
		List<String> marketsTabNames = homePageMethods.getSubSectionHeaders();
		for (String marketsTab : marketsTabNames) {
			if (marketsTab.equalsIgnoreCase("benchmarks")) {
				softAssert.assertTrue(homePageMethods.getSensexLinkHref().contains("sensex"),
						"<br>- Link under sensex column of the section is not of Sensex, instead found "
								+ homePageMethods.getSensexLinkHref());
				softAssert.assertTrue(homePageMethods.getNiftyLinkHref().contains("nifty"),
						"<br>- Link under nifty column of the section is not of Nifty, instead found "
								+ homePageMethods.getNiftyLinkHref());
				softAssert.assertTrue(homePageMethods.getGoldLinkHref().contains("GOLD"),
						"<br>- Link under gold column of the section is not of Gold page, instead found "
								+ homePageMethods.getGoldLinkHref());
				softAssert.assertTrue(homePageMethods.getForexLinkHref().contains("forex"),
						"<br>- Link under forex column of the section is not of Forex,instead found "
								+ homePageMethods.getForexLinkHref());
				softAssert.assertTrue(homePageMethods.getAllBenchMarkLinkHref().contains("indices"),
						"<br>- Link under all bookmarks link of the section is not of markets,instead found "
								+ homePageMethods.getAllBenchMarkLinkHref());

				ETWAPSensexVal = homePageMethods.getSensexValue();
				BSESiteSensexVal = BusinessLogicMethods.getSensexCurrentPrice();
				if (BSESiteSensexVal.startsWith("Error")) {
					softAssert.assertFalse(false,
							"Unable to fetch value for comparison from BSE India due to some error on their website.");
				} else {
					softAssert.assertTrue(
							VerificationUtil.valueIsInRange(ETWAPSensexVal, Double.parseDouble(BSESiteSensexVal), 40),
							"Market index value on ET WAP and BSE site is not matching , and is beyond the expected difference of "
									+ 40 + " ETWAPsiteValue:" + ETWAPSensexVal + " ,BSESiteValue: " + BSESiteSensexVal);
				}

				ETWAPNiftyVal = homePageMethods.getNIftyValue();
				NSESiteNiftyVal = BusinessLogicMethods.getNiftyCurrentPrice();
				if (NSESiteNiftyVal.startsWith("Error")) {
					softAssert.assertFalse(false,
							"Unable to fetch value for comparison from NSE India due to some error on their website.");
				} else {
					softAssert.assertTrue(
							VerificationUtil.valueIsInRange(ETWAPNiftyVal, Double.parseDouble(NSESiteNiftyVal), 20),
							"Market index value on ET WAP and NSE site is not matching , and is beyond the expected difference of "
									+ 20 + " ETWAPsiteValue:" + ETWAPNiftyVal + " ,NSESiteValue: " + NSESiteNiftyVal);
				}

				ETWAPGoldVal = homePageMethods.getGoldValue();
				McxSiteGoldVal = BusinessLogicMethods.getGoldPrice();
				if (McxSiteGoldVal.startsWith("Error")) {
					softAssert.assertFalse(false,
							"Unable to fetch value for comparison from MCX India due to some error on their website.");
				} else {
					softAssert.assertTrue(
							VerificationUtil.valueIsInRange(ETWAPGoldVal, Double.parseDouble(McxSiteGoldVal), 50),
							"Market index value on ET and mcx site is not matching , and is beyond the expected difference of "
									+ 50 + " ETWAPsiteValue:" + ETWAPGoldVal + " ,MCXSiteValue: " + McxSiteGoldVal);
				}

				ETWAPUsdVal = homePageMethods.getForexValue();
				usdSiteValue = BusinessLogicMethods.getUSDINR();
				if (usdSiteValue.startsWith("Error")) {
					Assert.assertFalse(false,
							"Unable to fetch value for comparison from Mecklai due to some error on their website.");
				} else {
					softAssert.assertTrue(
							VerificationUtil.valueIsInRange(ETWAPUsdVal, Double.parseDouble(usdSiteValue), 0.5),
							"Market index value on ET and mecklai site is not matching , and is beyond the expected difference of "
									+ 0.5 + "% ETWAPsiteValue:" + ETWAPUsdVal + " ,Mecklai siteValue: " + usdSiteValue);
				}

				for (String href : homePageMethods.getAllBenchmarkLinksHref()) {
					int benchmarkLinkResponseCode = HTTPResponse.checkResponseCode(href);
					softAssert.assertEquals(benchmarkLinkResponseCode, 200, "<br>- Benchmark link <a href=" + href + ">"
							+ href + "</a> is throwing " + benchmarkLinkResponseCode);
				}
			} else {
				int subMenuCount = 5;
				homePageMethods.openMarketSubMenu(marketsTab);
				subMenuList = homePageMethods.getSubMenuEntriesHref();
				System.out.println(marketsTab + "   " + subMenuList);
				softAssert.assertEquals(subMenuList.size(), subMenuCount, "<br>- " + marketsTab + " data should be "
						+ subMenuCount + " in number, instead found " + subMenuList.size());
			}
			for (String href : subMenuList) {
				int linkResponseCode = HTTPResponse.checkResponseCode(href);
				softAssert.assertEquals(linkResponseCode, 200, "<br>- " + marketsTab + " link <a href=" + href + ">"
						+ href + "</a> is throwing " + linkResponseCode);
			}
			String url = homePageMethods.getMoreDataLinkHref();
			int moreLinkResponseCode = HTTPResponse.checkResponseCode(url);
			softAssert.assertEquals(moreLinkResponseCode, 200, "<br>- " + marketsTab + " more data link <a href=" + url
					+ ">" + url + "</a> is throwing " + moreLinkResponseCode);
		}
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page Industry section", groups = { "Home Page" }, priority = 11, enabled = false)
	public void verifyHomeIndustrySection() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		softAssert = new SoftAssert();
		int articleCount = 5;
		homePageMethods.moveToIndustrySection();
		String sectionLink = homePageMethods.getIndustryNewsLink();
		softAssert.assertTrue(sectionLink.contains("industry"),
				"<br>- Link under heading of the section is not of industry, instead is:" + sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		for (WebElement ele : homePageMethods.getIndustryTopSectionHeaders()) {
			homePageMethods.moveToIndustrySection();
			homePageMethods.openSubSection(ele);
			List<String> industryNewsStories = homePageMethods.getIndustryNewsHeadlines();
			int count = industryNewsStories.size();
			softAssert.assertTrue(count >= articleCount, "<br>- Headlines under industry-subsection, " + ele.getText()
					+ " should be more than " + articleCount + " in number, instead found " + count);
			List<String> industryNewsDup = VerificationUtil.isListUnique(industryNewsStories);
			softAssert.assertTrue(industryNewsDup.isEmpty(), "<br>- Industry under " + ele.getText()
					+ " has duplicate stories, repeating story(s)->" + industryNewsDup);
			homePageMethods.getIndustryTop10Href(ele).forEach(keyword -> {
				int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
				softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Link under section " + ele.getText()
						+ ": <a href=" + keyword + "> " + keyword + " </a> is throwing " + topLinksResponseCode);
			});
			if (!ele.getText().equalsIgnoreCase("Featured")) {
				softAssert.assertTrue(
						homePageMethods.getMoreIndustryNewsHref(ele)
								.contains(ele.getText().replaceAll("\\s+/\\s+", "/")),
						"More link is not taking to " + ele.getText() + " page,instead it takes to "
								+ homePageMethods.getMoreIndustryNewsHref(ele));
			}
		}
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page Wealth section", groups = { "Home Page" }, priority = 12, enabled = false)
	public void verifyHomeWealthSection() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		softAssert = new SoftAssert();
		int articleCount = 5;
		String section = "wealth";
		String sectionLink = homePageMethods.getSectionNewsLink("wealth");
		softAssert.assertTrue(sectionLink.contains("personal-finance"),
				"<br>- Link under heading of the section is not of wealth, instead is:" + sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> wealthNewsStories = VerificationUtil
				.getLinkTextList(homePageMethods.getSectionNewsHeadlines(section));
		int count = wealthNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under wealth sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> wealthNewsDup = VerificationUtil.isListUnique(wealthNewsStories);
		softAssert.assertTrue(wealthNewsDup.isEmpty(),
				"<br>- Wealth is having duplicate stories, repeating story(s)->" + wealthNewsDup);
		homePageMethods.getSectionTop10Href(section).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top 10 wealth link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertTrue(homePageMethods.getMoreSectionNewsHref(section).contains("personal-finance"),
				"More link is not taking to wealth page,instead it takes to "
						+ homePageMethods.getMoreSectionNewsHref(section));
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page Panache section", groups = { "Home Page" }, priority = 15, enabled = false)
	public void verifyHomePanacheSection() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		softAssert = new SoftAssert();
		int articleCount = 5;
		homePageMethods.moveToPanacheSection();
		String sectionLink = homePageMethods.getPanacheNewsLink();
		softAssert.assertTrue(sectionLink.contains("panache"),
				"<br>- Link under heading of the section is not of panache, instead is:" + sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		for (WebElement ele : homePageMethods.getPanacheTopSectionHeaders()) {
			homePageMethods.moveToPanacheSection();
			homePageMethods.openSubSection(ele);
			List<String> panacheNewsStories = homePageMethods.getPanacheNewsHeadlines();
			int count = panacheNewsStories.size();
			softAssert.assertTrue(count >= articleCount, "<br>- Headlines under panache-subsection, " + ele.getText()
					+ " should be more than " + articleCount + " in number, instead found " + count);
			List<String> panacheNewsDup = VerificationUtil.isListUnique(panacheNewsStories);
			softAssert.assertTrue(panacheNewsDup.isEmpty(), "<br>- Panache under " + ele.getText()
					+ " has duplicate stories, repeating story(s)->" + panacheNewsDup);
			homePageMethods.getPanacheTop10Href(ele).forEach(keyword -> {
				int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
				softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Link under section " + ele.getText()
						+ ": <a href=" + keyword + "> " + keyword + " </a> is throwing " + topLinksResponseCode);
			});
			String moreLink = homePageMethods.getMorePanacheNewsHref(ele);

			Assert.assertTrue(!moreLink.isEmpty(), "More link not found");
			int rCode = HTTPResponse.checkResponseCode(moreLink);
			softAssert.assertEquals(rCode, 200, "<br>- <a href=" + moreLink + "> more news link </a> of "
					+ ele.getText() + " is throwing " + rCode);
			if (!ele.getText().equalsIgnoreCase("between the lines"))
				softAssert.assertTrue(moreLink.contains("panache"),
						"More link is not taking to " + ele.getText() + " page,instead it takes to " + moreLink);

		}
		softAssert.assertAll();
	}

	@Test(description = "Verify Home Page Industry top scroll section", groups = {
			"Home Page" }, priority = 10, enabled = false)
	public void verifyIndustryHomeTopSection() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		List<String> expectedMenuItemList = new ArrayList<>(
				Arrays.asList(industryHomeTopScroll.get("IndustryHomeNavigation").split("\\s*,\\s*")));
		homePageMethods.moveToIndustrySection();
		List<String> actualMenuItemList = homePageMethods.getIndustrySectionHeaders();
		Assert.assertTrue(VerificationUtil.listActualInExpected(actualMenuItemList, expectedMenuItemList),
				"Actual list of menu items is not matching the expected list of menu items. Actual List "
						+ actualMenuItemList + " Expected List " + expectedMenuItemList);
	}

	@Test(description = "Verify custom sub sections", groups = { "Home Page" }, priority = 17, enabled = false)
	public void verifyCustomSubSections() {
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 5);
		softAssert = new SoftAssert();
		int articleCount = 3;
		int sectionCount = 1;
		homePageMethods.moveToSpotlightSection();
		for (String ele : homePageMethods.getSectionHeaders()) {
			List<String> sectionStories = VerificationUtil
					.getLinkTextList(homePageMethods.getSpotlightSectionHeadlines(sectionCount));
			int count = sectionStories.size();
			softAssert.assertTrue(count >= articleCount, "<br>- Headlines under " + ele
					+ " section should be more than " + articleCount + " in number, instead found " + count);
			List<String> sectionDup = VerificationUtil.isListUnique(sectionStories);
			softAssert.assertTrue(sectionDup.isEmpty(),
					"<br>- Spotlight section " + ele + " has duplicate stories, repeating story(s)->" + sectionDup);
			homePageMethods.getSpotlightSectionHref(sectionCount).forEach(keyword -> {
				int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
				softAssert.assertEquals(topLinksResponseCode, 200,
						"<br>- Top story link <a href=" + keyword + ">" + keyword + "</a> is throwing "
								+ topLinksResponseCode + " for " + ele + " section in spotlight");
			});
			sectionCount++;
		}
		softAssert.assertAll();
	}

	@Test(description = " This test verifies the Google and Colobia ads on Home page", priority = 18, enabled = false)
	public void verifyAds() {
		SoftAssert softAssert = new SoftAssert();

		WaitUtil.sleep(5000);
		WebBaseMethods.navigateTimeOutHandle(driver, wapUrl, 2);

		WaitUtil.sleep(2000);
		Map<String, String> expectedIdsMap = Config.getAllKeys("wap_adIdLocation");
		softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0, "No google ads shown on home page");
		if (adTechMethods.getDisplayedAdIds().size() > 0) {
			expectedIdsMap.forEach((K, V) -> {
				if (K.contains("HP"))
					softAssert.assertTrue(adTechMethods.matchIdsWithKey(K),
							"Following ad(s) is/are not shown " + expectedIdsMap.get(K) + " on Home Page");
			});
		}
		List<String> ctnAds = adTechMethods.getAllColombiaAds();
		if (ctnAds.size() > 0) {
			List<String> missingAds = adTechMethods.getMissingColumbiaAds();
			softAssert.assertFalse(missingAds.size() > 0, "Following colombia ad(s) is/are not shown " + missingAds);
		} else
			softAssert.assertTrue(false, "No colombia Ads found on Home Page");

		softAssert.assertAll();
	}

}