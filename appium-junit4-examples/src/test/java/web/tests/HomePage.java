package web.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import common.utilities.reporting.ScreenShots;
import web.pagemethods.AdTechMethods;
import web.pagemethods.ArticleMethods;
import web.pagemethods.ETSharedMethods;
import web.pagemethods.HomePageMethods;
import web.pagemethods.WebBaseMethods;

public class HomePage extends BaseTest {
	private String baseUrl;
	private int countMostSections = 7;
	int counterNewsIndustry = 1;
	HomePageMethods homePageMethods;
	ArticleMethods articleMethods;
	ScreenShots screenShots;
	SoftAssert softAssert;
	Map<String, String> notFound;
	private List<Boolean> flag = new LinkedList<Boolean>();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		screenShots = new ScreenShots();
		launchBrowser(baseUrl);
		articleMethods = new ArticleMethods(driver);
		homePageMethods = new HomePageMethods(driver);
		WebBaseMethods.scrollToBottom();
		WebBaseMethods.scrollToTop();

	}

	@Test(description = "This test verifies latest news section on homepage")
	public void verifyLatestNewsSection() {
		int latestNews = 17;
		softAssert = new SoftAssert();

		homePageMethods.clickLatestNewsTab();
		WaitUtil.sleep(2000);
		int latestNewsActualSize = homePageMethods.checkLatestNewsListSize();
		System.out.println(latestNewsActualSize);
		Assert.assertTrue(latestNewsActualSize > 0, "No news shown in latest news section");
		softAssert.assertTrue(homePageMethods.checkLatestNewsListSize() == latestNews,
				"<br>- Article count in latest news is not equal to expected value" + latestNews);
		List<String> latestNewsStories = homePageMethods.getLatestNewsStories();
		latestNewsStories = homePageMethods.getLatestNewsStories();
		List<String> latestNewsDup = VerificationUtil.isListUnique(latestNewsStories);
		softAssert.assertTrue(latestNewsDup.isEmpty(),
				"<br>- Latest news is having duplicate stories, repeating story(s)->" + latestNewsDup);

		homePageMethods.clickMoreLatestNews();
		latestNewsStories = homePageMethods.getLatestNewsStories();
		latestNewsStories.forEach(href -> {
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertTrue(response == 200,
					"<br><a href='" + href + "'>" + href.substring(0, href.length() / 2) + "...</a> is broken");
		});
		int actualNewsCount = homePageMethods.checkMoreFromLatestNewsPage();
		softAssert.assertTrue(actualNewsCount > 20,
				"<br>- Less than 20 articles on the landing page, instead is: " + actualNewsCount);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies top slider images", groups = { "monitoring" })
	public void verifyTopSlider() {
		int topSlider = 10;
		softAssert = new SoftAssert();

		int topSliderSize = homePageMethods.getTopSliderLinks().size();
		Assert.assertTrue(topSliderSize > 0, "No slideshow/videoshow in top slider section");
		List<String> sliderHref = WebBaseMethods.getListHrefUsingJSE(homePageMethods.getTopSliderLinks());
		softAssert.assertTrue(sliderHref.size() >= topSlider,
				"<br>- Top Slider is having less than" + topSlider + " slides instead has " + sliderHref.size());
		sliderHref.forEach(link -> {
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Link under top slider section </a> is throwing " + responseCode);
			softAssert.assertTrue(VerificationUtil.isNews(link),
					"<br>-Link in top news is not of news type, instead found <a href=" + link + ">" + link + "</a>");
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies top news section on homepage and more top news page for presence of different sections")
	public void verifyTopNewsSection() {
		int topNews = 22;
		Map<String, String> testData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifySectionTopNews", 1);
		List<String> tabName = Arrays.asList(testData.get("Text").split("\\s*,\\s*"));
		softAssert = new SoftAssert();

		homePageMethods.clickTopNewsSection();
		int actualTopNewsSize = homePageMethods.checkTopNewsSectionSize();
		Assert.assertTrue(actualTopNewsSize > 0, "No news shown in top news section");
		softAssert.assertTrue(actualTopNewsSize >= topNews,
				"<br>- Article count in top news is not equal to expected value " + topNews + " actual value "
						+ actualTopNewsSize);
		List<String> topNewsStories = homePageMethods.getTopNewsStories();
		List<String> topNewsDup = VerificationUtil.isListUnique(topNewsStories);
		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>- Top news is having duplicate stories, repeating story(s)->" + topNewsDup);
		topNewsStories.forEach(href -> {
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertTrue(response == 200,
					"<br><a href='" + href + "'>" + href.substring(0, href.length() / 2) + "...</a> is broken");
		});
		Assert.assertTrue(homePageMethods.clickTopNewsMoreLink(), "More Top News link not found");
		Assert.assertTrue(driver.getCurrentUrl().contains("headlines"),
				"Unable to navigate to headlines page in 4 secs");
		tabName.forEach(action -> {
			if (!(homePageMethods.getTopHeadlineDivs(action) > 0
					|| homePageMethods.getTopSectionsArticleList(action) > 0))
				screenShots.takeScreenshotAnotherDriver(driver, "verifyTopNewsSection" + action);
			softAssert.assertTrue(homePageMethods.getTopHeadlineDivs(action) > 0,
					action + ": section not shown on top news list page");
			softAssert.assertTrue(homePageMethods.getTopSectionsArticleList(action) > 0,
					action + ": article not shown in " + action);
		});
		WebBaseMethods.switchToParentClosingChilds();
		WebBaseMethods.navigateTimeOutHandle(baseUrl);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies most shared section and its landing page")
	public void verifyDisplayEtMostSharedSection() {
		softAssert = new SoftAssert();
		homePageMethods.clickTopSharedTab();
		WaitUtil.sleep(2000);
		Assert.assertTrue(homePageMethods.getArticleCountTopShared() > 0, "Most Shared Section is having 0 stories");
		softAssert.assertTrue(homePageMethods.getArticleCountTopShared() == countMostSections,
				"Article count in most shared section is not equal to expected value " + countMostSections
						+ ", instead is " + homePageMethods.getArticleCountTopShared());
		List<String> topSharedStoriesHome = homePageMethods.getTopSharedStories();
		List<String> topSharedsDup = VerificationUtil.isListUnique(topSharedStoriesHome);
		softAssert.assertTrue(topSharedsDup.isEmpty(),
				"<br>- Top shared is having duplicate stories, repeating story(s)->" + topSharedsDup);
		String url = homePageMethods.clickMoreTopShared();
		Assert.assertTrue(url.contains("most-shared"), "<br>- Url does not contain most-shared, instead is " + url);
		List<String> topSharedStoriesMore = homePageMethods.getArticleListMoreTopShared();
		if (topSharedStoriesMore.size() < 20) {
			screenShots.takeScreenshotAnotherDriver(driver, "verifyDisplayEtMostSharedSection");
		}
		softAssert.assertTrue(topSharedStoriesMore.size() > 15,
				"<br>- Article count on more shared page should atleast more than 15,instead is "
						+ topSharedStoriesMore.size());
		List<String> differenceLists = VerificationUtil.differenceTwoLists(topSharedStoriesMore, topSharedStoriesHome);
		softAssert.assertEquals(differenceLists.size(), 0,
				"<br>- Articles on home page are not found on more page ->" + differenceLists);
		topSharedStoriesHome.forEach(href -> {
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertTrue(response == 200,
					"<br><a href='" + href + "'>" + href.substring(0, href.length() / 2) + "...</a> is broken");
		});
		List<String> topSharedMoreDup = VerificationUtil.isListUnique(topSharedStoriesMore);
		// softAssert.assertTrue(topSharedMoreDup.isEmpty(),"<br>- Top shared
		// more is having duplicate stories, repeating story(s)->" +
		// topSharedMoreDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies most commented section and its landing page")
	public void verifyDisplayEtMostCommentedSection() {
		softAssert = new SoftAssert();

		homePageMethods.clickTopCommentedTab();
		List<String> topCommentedStoryLink = homePageMethods.getTopCommentedStoryLink();

		System.out.println(topCommentedStoryLink.size());
		System.out.println(topCommentedStoryLink);
		softAssert.assertTrue(homePageMethods.getArticleCountTopCommented() == countMostSections,
				"Article count in most commented section is not equal to expected value " + countMostSections
						+ ", but found " + homePageMethods.getArticleCountTopCommented());
		List<String> topCommentedStoriesHome = homePageMethods.getTopCommentedStories();
		Assert.assertTrue(topCommentedStoriesHome.size() > 0, "No stories shown in most commented section");
		List<String> topCommentedDup = VerificationUtil.isListUnique(topCommentedStoriesHome);
		softAssert.assertTrue(topCommentedDup.isEmpty(),
				"<br>- Top shared is having duplicate stories, repeating story(s)->" + topCommentedDup);
		String url = homePageMethods.clickMoreTopCommented();
		Assert.assertTrue(url.contains("most-commented"),
				"<br>- Url does not contain most-commented, instead is " + url);
		List<String> topCommentedMore = homePageMethods.getArticleListMoreTopShared();
		if (topCommentedMore.size() < 20) {
			screenShots.takeScreenshotAnotherDriver(driver, "verifyDisplayEtMostCommentedSection");
		}
		softAssert.assertTrue(topCommentedMore.size() > 20,
				"<br>- Article count on more shared page is less than 20, instead is " + topCommentedMore.size());
		List<String> differenceLists = VerificationUtil.differenceTwoLists(topCommentedMore, topCommentedStoriesHome);
		softAssert.assertEquals(differenceLists.size(), 0,
				"<br>- Articles on home page are not found on more page ->" + differenceLists);
		topCommentedStoriesHome.forEach(href -> {
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertTrue(response == 200,
					"<br><a href='" + href + "'>" + href.substring(0, href.length() / 2) + "...</a> is broken");
		});
		List<String> topCommentedMoreDup = VerificationUtil.isListUnique(topCommentedMore);
		/*
		 * softAssert.assertTrue(topCommentedMoreDup.isEmpty()
		 * ,"<br>- Top commented more is having duplicate stories, repeating story(s)->"
		 * + topCommentedMoreDup);
		 */

		topCommentedStoryLink.forEach(link -> {
			WebBaseMethods.navigateTimeOutHandle(link);
			int commentsCount = articleMethods.commentTabCount();
			softAssert.assertTrue(commentsCount > 0, "The count of comment for the story : " + link + " is 0");

		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies most read section on homepage and its landing page")
	public void verifyDisplayEtMostReadSection() {
		softAssert = new SoftAssert();
		softAssert.assertTrue(homePageMethods.getMostReadArticleList() == countMostSections,
				"Article count in most read section is not equal to expected value " + countMostSections);
		List<String> topReadtoriesHome = homePageMethods.getTopMostReadStories();
		Assert.assertTrue(topReadtoriesHome.size() > 0, "No stories shown in top reads section");
		List<String> topReadDup = VerificationUtil.isListUnique(topReadtoriesHome);
		softAssert.assertTrue(topReadDup.isEmpty(),
				"<br>- Top read is having duplicate stories, repeating story(s)->" + topReadDup);
		String url = homePageMethods.clickMoreTopRead();
		Assert.assertTrue(url.contains("most-read"), "<br>- Url does not contain most-read,instead url is " + url);
		List<String> topReadMore = homePageMethods.getArticleCountMoreTopRead();
		if (topReadMore.size() < 20) {
			screenShots.takeScreenshotAnotherDriver(driver, "verifyDisplayEtMostReadSection");
		}
		softAssert.assertTrue(topReadMore.size() > 20,
				"<br>- Article count on more read page is less than 20, instead is " + topReadMore.size());
		List<String> differenceLists = VerificationUtil.differenceTwoLists(topReadMore, topReadtoriesHome);
		softAssert.assertEquals(differenceLists.size(), 0,
				"<br>- Articles on home page are not found on more page ->" + differenceLists);
		topReadtoriesHome.forEach(href -> {
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertTrue(response == 200,
					"<br><a href='" + href + "'>" + href.substring(0, href.length() / 2) + "...</a> is broken");
		});
		List<String> topReadMoreDup = VerificationUtil.isListUnique(topReadMore);
		// softAssert.assertTrue(topReadMoreDup.isEmpty(),"<br>- Top read more
		// is having duplicate stories, repeating story(s)->" + topReadMoreDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Editor's Pick auto slider section on homepage ")
	public void verifyEditorsPickSection() {
		int editorPick = 20;
		softAssert = new SoftAssert();

		List<String> editorsPickStories = homePageMethods.getStoriesEditorsPick();
		Assert.assertTrue(editorsPickStories.size() > 0, "No stories are shown in editor's pick section");
		softAssert.assertEquals(editorsPickStories.size(), editorPick,
				"<br>- Expected story count: " + editorPick + " found: " + editorsPickStories.size());
		List<String> editorsPickDup = VerificationUtil.isListUnique(editorsPickStories);
		softAssert.assertTrue(editorsPickDup.isEmpty(),
				"<br>- Editors pick is having duplicate stories, repeating story(s)->" + editorsPickDup);
		List<String> beforeClickHeadlines = ETSharedMethods
				.getDisplayedElementsList(homePageMethods.getHomePageObjects().getEditorPickArticleHeadings());
		homePageMethods.clickPaginationEditorsSection();
		List<String> afterClickHeadlines = ETSharedMethods
				.getDisplayedElementsList(homePageMethods.getHomePageObjects().getEditorPickArticleHeadings());
		softAssert.assertFalse(VerificationUtil.areListsEqual(beforeClickHeadlines, afterClickHeadlines),
				"Stories did not change after clicking next");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies left side headlines section")
	public void verifyLeftSideNewsSection() {
		int storyCount = 12;
		softAssert = new SoftAssert();

		List<String> leftSideStories = homePageMethods.getLeftSideStories();
		System.out.println(leftSideStories.size());
		Assert.assertTrue(leftSideStories.size() > 0, "No stories found in left side section");
		softAssert.assertTrue(leftSideStories.size() >= storyCount,
				"<br>- Expected story count: " + storyCount + " found: " + leftSideStories.size());
		List<String> rightSideDup = VerificationUtil.isListUnique(leftSideStories);
		softAssert.assertTrue(rightSideDup.isEmpty(),
				"<br>- Left side headlines is having duplicate stories, repeating story(s)->" + rightSideDup);
		List<String> leftHeadlineStoriesHref = homePageMethods.getLeftSideStoriesHref();
		System.out.println(leftHeadlineStoriesHref.size() + "---" + leftHeadlineStoriesHref);
		leftHeadlineStoriesHref.forEach(action -> {
			int response = HTTPResponse.checkResponseCode(action);
			softAssert.assertEquals(response, 200,
					"<br>-link <a href=" + action + "> Story</a> in left section headlines is throwing " + response);
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies top flash news", groups = { "monitoring" }, enabled = false)
	public void verifyTopFlashNews() {
		softAssert = new SoftAssert();

		List<String> flashNewsStories = homePageMethods.getTopFlashNews();
		Assert.assertTrue(flashNewsStories.size() > 0, "<br>- No stories in flash news section");
		List<String> flashNewsDup = VerificationUtil.isListUnique(flashNewsStories);
		softAssert.assertTrue(flashNewsDup.isEmpty(),
				"<br>- Flash News is having duplicate stories, repeating story(s)->" + flashNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies news in voices section")
	public void verifyVoicesSection() {
		int voicesNewsCount = 5;
		softAssert = new SoftAssert();

		List<String> voicesNews = homePageMethods.getVoiceNews();
		Assert.assertTrue(!voicesNews.isEmpty(), "No news in voices section");
		softAssert.assertEquals(voicesNews.size(), voicesNewsCount,
				"<br>- Expected story count: " + voicesNewsCount + " found: " + voicesNews.size());
		List<String> voiceNewsDup = VerificationUtil.isListUnique(voicesNews);
		softAssert.assertTrue(voiceNewsDup.isEmpty(),
				"<br>- Voice News is having duplicate stories, repeating story(s)->" + voiceNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies news by industry section")
	public void verifyIndustryNewsSections() {
		softAssert = new SoftAssert();
		int articleCount = 6;
		List<WebElement> tabs = homePageMethods.getIndustryNewsTabList();
		tabs.forEach(action -> {
			WebBaseMethods.clickElementUsingJSE(action);
			String tabName = WebBaseMethods.getTextUsingJSE(action);
			List<String> industryNewsStories = homePageMethods.getNewsByIndusrtyTabsLink(counterNewsIndustry);
			if (industryNewsStories.size() == 1) {
				softAssert.assertFalse(false,
						action.findElement(By.tagName("a")).getText() + industryNewsStories.get(0));
				screenShots.takeScreenshotAnotherDriver(driver, "verifyIndustryNewsSections");
				industryNewsStories.clear();
			}
			softAssert.assertEquals(industryNewsStories.size(), articleCount, "<br>- Expected story count: "
					+ articleCount + " found: " + industryNewsStories.size() + " on tab " + tabName);
			List<String> industryNewsDup = VerificationUtil.isListUnique(industryNewsStories);
			softAssert.assertTrue(industryNewsDup.isEmpty(), "<br>- News by Industry on tab name " + tabName
					+ " is having duplicate stories, repeating story(s)->" + industryNewsDup);

			counterNewsIndustry++;
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies panache section")
	public void verifyPanacheSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();

		String sectionLink = homePageMethods.getPancheHeadingLink();
		Assert.assertTrue(sectionLink.trim().length() > 1, "Panache section not shown");
		List<String> panacheNewsStories = homePageMethods.getPanacheHeadlinesText();
		Assert.assertTrue(!panacheNewsStories.isEmpty(), "No stories shown in panache section");
		softAssert.assertTrue(sectionLink.contains("panache"),
				"<br>- Link under heading of the section is not of panache, instead is " + sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>- Section link <a href=" + sectionLink + ">"
				+ sectionLink.substring(0, (sectionLink.length() / 2)) + "</a> is throwing " + responseCode);
		int foundStoryCount = panacheNewsStories.size();
		softAssert.assertTrue(foundStoryCount >= articleCount, "<br>- Headlines under panache sections are not "
				+ articleCount + " in number, found " + foundStoryCount);
		List<String> panacheNewsDup = VerificationUtil.isListUnique(panacheNewsStories);
		softAssert.assertTrue(panacheNewsDup.isEmpty(),
				"<br>- Panache is having duplicate stories, repeating story(s)->" + panacheNewsDup);
		homePageMethods.getPancheKeywordsHref().forEach(keyword -> {
			softAssert.assertTrue(keyword.contains("panache"),
					"<br>- Trending tweets are not having articles of panache section,found: " + keyword);
			int tweetResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(tweetResponseCode, 200,
					"<br>- Trending tweets <a href=" + keyword + ">link</a> is throwing " + tweetResponseCode);
		});
		homePageMethods.getPancheSpotLightHref().forEach(href -> {
			int spotlightResponse = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(spotlightResponse, 200,
					"<br>- Panache story <a href=" + href + ">link</a> is throwing " + spotlightResponse);
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies spotlight section")
	public void verifySpotLightSection() {
		int articleCount = 12;
		softAssert = new SoftAssert();

		List<String> spotLightNewsStories = homePageMethods.getSpotLightAllNews();
		Assert.assertTrue(!spotLightNewsStories.isEmpty(), "No stories shown in spotlight section ");

		softAssert.assertTrue(spotLightNewsStories.size() >= articleCount,
				"<br>- Expected story count, to be atleast " + articleCount + " found: " + spotLightNewsStories.size());
		spotLightNewsStories.forEach(action -> {
			int responseCode = HTTPResponse.checkResponseCode(action);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + action + ">Link </a> under spotlight is throwing " + responseCode);
		});
		List<String> spotLightNewsDup = VerificationUtil.isListUnique(spotLightNewsStories);
		/*
		 * softAssert.assertTrue(spotLightNewsDup.isEmpty(),
		 * "<br>- SpotLight section is having duplicate stories, repeating story(s)->"
		 * + spotLightNewsDup);
		 */
		String article1 = homePageMethods.getSpotLightHeadLine();
		homePageMethods.clickNextSpotLight();
		String article2 = homePageMethods.getSpotLightHeadLine();
		softAssert.assertFalse(article1.equals(article2), "<br>- Article has not changed after clicking next");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies market news section")
	public void verifyMarketNewsSection() {
		int articleCount = 10;
		softAssert = new SoftAssert();

		String sectionLink = homePageMethods.getMarketsNewsLink();
		Assert.assertTrue(sectionLink.trim().length() > 0, "Markets section is not shown");
		List<String> marketNewsStories = VerificationUtil.getLinkTextList(homePageMethods.getMarketsNewsHeadlines());
		Assert.assertTrue(!marketNewsStories.isEmpty(), "No stories shown in markets section");
		softAssert.assertTrue(sectionLink.contains("markets"),
				"<br>- Link under heading of the section is not of markets");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>- Section link <a href=" + sectionLink + ">"
				+ sectionLink.substring(0, (sectionLink.length() / 2)) + "</a> is throwing " + responseCode);
		int count = marketNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under market sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> marketNewsDup = VerificationUtil.isListUnique(marketNewsStories);
		softAssert.assertTrue(marketNewsDup.isEmpty(),
				"<br>- Markets is having duplicate stories, repeating story(s)->" + marketNewsDup);
		homePageMethods.getMarketsTop10Href().forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>-Other market links <a href=" + keyword + ">"
					+ keyword.substring(0, (keyword.length() / 2)) + "...</a> is throwing " + topLinksResponseCode);
		});
		List<String> topGainers = homePageMethods.getTopGainerList();
		softAssert.assertEquals(topGainers.size(), 5,
				"<br>- Top Gainers shown should be  atleast 5, instead found " + topGainers.size());
		softAssert.assertTrue(homePageMethods.getMoreMarketNewsHref().contains("markets"),
				"More link is not taking to markets page,instead it takes to "
						+ homePageMethods.getMoreMarketNewsHref());
		softAssert.assertAll();
	}

	@Test(description = "This test verifies politics and nation section")
	public void verifyPoliticsNationSection() {
		int articleCount = 8;
		softAssert = new SoftAssert();

		String sectionLink = homePageMethods.getPoliticsNationLink();
		Assert.assertTrue(sectionLink.trim().length() > 0, "Politics Nation section is not shown");
		List<String> polNatNewsStories = VerificationUtil.getLinkTextList(homePageMethods.getPoliticsNationHeadlines());
		Assert.assertTrue(!polNatNewsStories.isEmpty(), "No stories shown in politics and nation section");
		softAssert.assertTrue(sectionLink.contains("politics"),
				"<br>- Link under heading of the section is not of politics, instead is " + sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>-Section link <a href=" + sectionLink + ">" + sectionLink + " </a> is throwing " + responseCode);
		softAssert.assertTrue(polNatNewsStories.size() >= articleCount,
				"<br>- Headlines under market sections  than " + articleCount + " in number");
		List<String> polNatNewsDup = VerificationUtil.isListUnique(polNatNewsStories);
		softAssert.assertTrue(polNatNewsDup.isEmpty(),
				"<br>- Politics and Nation is having duplicate stories, repeating story(s)->" + polNatNewsDup);
		List<String> politicsNationStoriesHref = VerificationUtil
				.getLinkHrefList(homePageMethods.getPoliticsNationHeadlines());
		System.out.println(politicsNationStoriesHref.size() + "---" + politicsNationStoriesHref);

		politicsNationStoriesHref.forEach(action -> {
			int response = HTTPResponse.checkResponseCode(action);
			softAssert.assertEquals(response, 200, "<br>-link <a href=" + action
					+ "> Story</a> in politics nations headlines is throwing " + response);
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies economy section")
	public void verifyEconomySection() {
		int articleCount = 6;
		softAssert = new SoftAssert();

		String sectionLink = homePageMethods.getEconomyLink();
		Assert.assertTrue(sectionLink.trim().length() > 0, "Economy section is not shown");
		List<String> economyStories = VerificationUtil.getLinkTextList(homePageMethods.getEconomyHeadlines());
		Assert.assertTrue(!economyStories.isEmpty(), "No stories shown in economy section");
		softAssert.assertTrue(sectionLink.contains("economy"),
				"<br>- Link under heading of the section is not of economy,instead is " + sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>-Section link <a href=" + sectionLink + ">"
				+ sectionLink.substring(0, (sectionLink.length() / 2)) + "</a> is throwing " + responseCode);
		softAssert.assertTrue(economyStories.size() >= articleCount,
				"<br>- Headlines under economy sections should be more than " + articleCount
						+ " in number, actual found " + economyStories.size());
		List<String> ecoNewsDup = VerificationUtil.isListUnique(economyStories);
		softAssert.assertTrue(ecoNewsDup.isEmpty(),
				"<br>- Economy is having duplicate stories, repeating story(s)->" + ecoNewsDup);
		softAssert.assertTrue(homePageMethods.getEconomyMoreHref().contains("economy"),
				"<br>- More link is not taking to economy page,instead it takes to "
						+ homePageMethods.getEconomyMoreHref());
		List<String> economyStoriesHref = VerificationUtil.getLinkHrefList(homePageMethods.getEconomyHeadlines());
		System.out.println(economyStoriesHref.size() + "---" + economyStoriesHref);
		economyStoriesHref.forEach(action -> {
			int response = HTTPResponse.checkResponseCode(action);
			softAssert.assertEquals(response, 200,
					"<br>-link <a href=" + action + "> Story</a> in Economy headlines is throwing " + response);
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies small biz section")
	public void verifySmallBizSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();

		String sectionLink = homePageMethods.getSmallBizLink();
		Assert.assertTrue(sectionLink.trim().length() > 0, "Small Biz section is not shown");
		List<String> smallBizStories = VerificationUtil.getLinkTextList(homePageMethods.getSmallBizHeadlines());
		Assert.assertTrue(!smallBizStories.isEmpty(), "No stories shown under small biz stories");
		softAssert.assertTrue(sectionLink.contains("small-biz"),
				"<br>- Link under heading of the section is not of small biz,instead is " + sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>-Section link <a href=" + sectionLink + ">"
				+ sectionLink.substring(0, (sectionLink.length() / 2)) + "</a> is throwing " + responseCode);
		softAssert.assertTrue(smallBizStories.size() >= articleCount,
				"<br>- Headlines under small biz section are not more than " + articleCount
						+ " in number, actual found " + smallBizStories.size());
		List<String> smallBizNewsDup = VerificationUtil.isListUnique(smallBizStories);
		softAssert.assertTrue(smallBizNewsDup.isEmpty(),
				"<br>- Small Biz is having duplicate stories, repeating story(s)->" + smallBizNewsDup);
		int spLightArticle = 18;
		int actualCount = homePageMethods.getSmallBizSpotLightHeadlines().size();
		softAssert.assertTrue(actualCount >= spLightArticle,
				"<br>- Article count in section is less than " + spLightArticle + ", actual count is " + actualCount);
		WebBaseMethods.getListHrefUsingJSE(homePageMethods.getSmallBizSpotLightHeadlines()).forEach(href -> {

			int spotLightResponseCode = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(spotLightResponseCode, 200, "<br>- Small Biz spotlight article <a href=" + href
					+ ">link</a> is throwing " + spotLightResponseCode);
		});
		List<String> smallBizSptLtStories = WebBaseMethods
				.getListTextUsingJSE(homePageMethods.getSmallBizSpotLightHeadlines());
		List<String> smallBizSptLtDup = VerificationUtil.isListUnique(smallBizSptLtStories);
		softAssert.assertTrue(smallBizSptLtDup.isEmpty(),
				"<br>- Small Biz spotlight is having duplicate stories, repeating story(s)->" + smallBizNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies wealth section")
	public void verifyWealthSection() {
		int articleCount = 4;
		softAssert = new SoftAssert();

		String sectionLink = homePageMethods.getWealthLink();
		Assert.assertTrue(sectionLink.trim().length() > 0, "Wealth section is not shown");
		List<String> wealthStories = VerificationUtil.getLinkTextList(homePageMethods.getWealthHeadlines());
		Assert.assertTrue(!wealthStories.isEmpty(), "No stories shown under wealth section");
		softAssert.assertTrue(sectionLink.contains("finance"),
				"<br>- Link under heading of the section is not of wealth,instead is " + sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>-Section link <a href=" + sectionLink + ">"
				+ sectionLink.substring(0, (sectionLink.length() / 2)) + "</a> is throwing " + responseCode);
		softAssert.assertTrue(wealthStories.size() >= articleCount,
				"<br>- Headlines under wealth section are not more than " + articleCount + " in number, actual found "
						+ wealthStories.size());
		List<String> wealthStoriesHref = VerificationUtil.getLinkHrefList(homePageMethods.getWealthHeadlines());
		wealthStoriesHref.forEach(action -> {
			int response = HTTPResponse.checkResponseCode(action);
			softAssert.assertEquals(response, 200,
					"<br>- Link <a href=" + action + "> Story</a> in wealth section is throwing " + responseCode);
		});
		List<String> wealthNewsDup = VerificationUtil.isListUnique(wealthStories);
		softAssert.assertTrue(wealthNewsDup.isEmpty(),
				"<br>- Wealth is having duplicate stories, repeating story(s)->" + wealthNewsDup);
		String wealthMoreLink = homePageMethods.getWealthMoreHref();
		softAssert.assertTrue(wealthMoreLink.contains("finance"),
				"<br>- Link under heading of the section is not of wealth");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies mutual funds section")
	public void verifyMutualFundsSection() {
		int articleCount = 2;
		softAssert = new SoftAssert();

		WebBaseMethods.scrollElementIntoViewUsingJSE(homePageMethods.getMutualFundWidget());
		String sectionLink = homePageMethods.getMutualFundsLink();
		Assert.assertTrue(sectionLink.trim().length() > 0, "Section MF not show");
		softAssert.assertTrue(sectionLink.contains("mutual"),
				"<br>- Link under heading of the section is not of mutual funds, instead is " + sectionLink);
		List<String> mutualFundStories = VerificationUtil.getLinkTextList(homePageMethods.getMutualFundsHeadlines());
		Assert.assertTrue(!mutualFundStories.isEmpty(), "No stories shown under MF section");
		softAssert.assertTrue(homePageMethods.getTopMutualFundsList() > 0, "Top mutual funds list is not shown");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>-Section link <a href=" + sectionLink + ">"
				+ sectionLink.substring(0, (sectionLink.length() / 2)) + " </a> is throwing " + responseCode);
		softAssert.assertTrue(mutualFundStories.size() >= articleCount,
				"<br>- Headlines under mutual fund section are not more than " + articleCount
						+ " in number, instead found " + mutualFundStories.size());
		List<String> mutualFundStoriesHref = VerificationUtil
				.getLinkHrefList(homePageMethods.getMutualFundsHeadlines());
		mutualFundStoriesHref.forEach(action -> {
			int response = HTTPResponse.checkResponseCode(action);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + action + "> Story</a> in mutual fund section is throwing " + response);
		});
		/*
		 * List<String> mutualFundNewsDup =
		 * VerificationUtil.isListUnique(mutualFundStories);
		 * softAssert.assertTrue(mutualFundNewsDup.isEmpty(),
		 * "<br>- Mutual fund is having duplicate stories, repeating story(s)->"
		 * + mutualFundNewsDup);
		 */
		String mutualFundMoreLink = homePageMethods.getMutualFundsMoreHref();
		softAssert.assertTrue(mutualFundMoreLink.contains("mutual"),
				"<br>- Link under heading of the section is not of mutual");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies from around the web section", groups = { "monitoring" })
	public void verifyFromAroundTheWebSection() {
		Assert.assertTrue(homePageMethods.aroundWebIsDisplayed(), "From around web is not displayed");
	}

	@Test(description = "This test verifies not to be missed section")
	public void verifyNotToBeMissedSection() {
		int articleCount = 10;
		softAssert = new SoftAssert();

		List<String> notToBeMissedHeadlines = WebBaseMethods
				.getListTextUsingJSE(ETSharedMethods.getArticleListNotToBeMissedHeadlines());
		softAssert.assertTrue(notToBeMissedHeadlines.size() >= articleCount,
				"<br>- Headlines under not to be missed section are not more than " + articleCount
						+ " in number, actual " + notToBeMissedHeadlines.size());
		List<String> notMissedNewsDup = VerificationUtil.isListUnique(notToBeMissedHeadlines);

		softAssert.assertTrue(notMissedNewsDup.isEmpty(),
				"<br>- Not to be missed is having duplicate stories, repeating story(s)->" + notMissedNewsDup);

		List<String> hrefNotToBeMissed = VerificationUtil
				.getLinkHrefList(ETSharedMethods.getArticleListNotToBeMissedUrls());
		hrefNotToBeMissed.forEach(action -> {
			int response = HTTPResponse.checkResponseCode(action);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + action + ">" + action.substring(0, (action.length() / 2))
							+ "...</a> in not to be missed section is throwing " + response);
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies InfoTech section on home page")
	public void verifyInfotechSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();

		String sectionLink = homePageMethods.getInfoTechLink();
		Assert.assertTrue(sectionLink.trim().length() > 0, "Infotech section is not shown");
		List<String> infoTechStoriesHref = WebBaseMethods.getListHrefUsingJSE(homePageMethods.getInfoTechHeadlines());
		Assert.assertTrue(!infoTechStoriesHref.isEmpty(), "No stories shown under Infotech section");
		softAssert.assertTrue(sectionLink.contains("tech"),
				"<br>- Link under heading of the section is not of InfoTech page");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>- Section link <a href=" + sectionLink + ">"
				+ sectionLink.substring(0, (sectionLink.length() / 2)) + "</a> is throwing " + responseCode);
		List<String> infotechStories = WebBaseMethods.getListTextUsingJSE(homePageMethods.getInfoTechHeadlines());
		softAssert.assertTrue(infotechStories.size() >= articleCount,
				"<br>- Headlines under InfoTech section are not more than " + articleCount + " in number, actual "
						+ infotechStories.size());

		infoTechStoriesHref.forEach(action -> {
			int response = HTTPResponse.checkResponseCode(action);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + action + "> Story</a> in InfoTech section is throwing " + response);
		});
		List<String> infoTechNewsDup = VerificationUtil.isListUnique(infotechStories);
		softAssert.assertTrue(infoTechNewsDup.isEmpty(),
				"<br>- InfoTech is having duplicate stories, repeating story(s)->" + infoTechNewsDup);
		String infoTechMoreLink = homePageMethods.getInfoTechMoreHref();
		softAssert.assertTrue(infoTechMoreLink.contains("tech"),
				"<br>- Link under heading of the section is not of InfoTech");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Defence section")
	public void verifyDefenceSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();

		String sectionLink = homePageMethods.getDefenceLink();
		Assert.assertTrue(sectionLink.trim().length() > 0, "Section Defence is not shown");
		List<WebElement> defenceHeadlines = homePageMethods.getDefenceHeadlines();
		Assert.assertTrue(!defenceHeadlines.isEmpty(), "No stories shown under defence section");
		softAssert.assertTrue(sectionLink.contains("defence"),
				"<br>- Link under heading of the section is not of Defence page,instead is " + sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>-Section link <a href=" + sectionLink + ">"
				+ sectionLink.substring(0, (sectionLink.length() / 2)) + " </a> is throwing " + responseCode);

		List<String> defenceStories = WebBaseMethods.getListTextUsingJSE(homePageMethods.getDefenceHeadlines());
		softAssert.assertTrue(defenceStories.size() >= articleCount,
				"<br>- Headlines under Defence section should be more than " + articleCount
						+ " in number, instead found " + defenceStories.size());
		List<String> defenceStoriesHref = WebBaseMethods.getListHrefUsingJSE(defenceHeadlines);
		defenceStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Defence section is throwing " + response);
		});
		List<String> defenceTechNewsDup = VerificationUtil.isListUnique(defenceStoriesHref);
		softAssert.assertTrue(defenceTechNewsDup.isEmpty(),
				"<br>- Defence is having duplicate stories, repeating story(s)->" + defenceTechNewsDup);
		String defenceMoreLink = homePageMethods.getDefenceMoreHref();
		softAssert.assertTrue(defenceMoreLink.contains("defence"),
				"<br>- Link under more of the section is not of Defence, instead is " + defenceMoreLink);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies lead story images", groups = { "monitoring" })
	public void verifyLeadStoryImageSlider() {
		int articleCount = 10;
		softAssert = new SoftAssert();

		List<String> topSliderStoriesHref = WebBaseMethods.getListHrefUsingJSE(homePageMethods.getTopLeadStories());
		softAssert.assertTrue(topSliderStoriesHref.size() >= articleCount,
				"<br>- Headlines under top lead story section should be more than " + articleCount
						+ " in number, instead found " + topSliderStoriesHref.size());
		topSliderStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- <a href=" + href + "> "
					+ href.substring(0, (href.length() / 2)) + "...</a> in lead section is throwing " + response);
		});
		List<String> leadStoriesDup = VerificationUtil.isListUnique(topSliderStoriesHref);
		softAssert.assertTrue(leadStoriesDup.isEmpty(),
				"<br>- Lead stories slider is having duplicate stories, repeating story(s)->" + leadStoriesDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies slides and videos", groups = { "monitoring" })
	public void verifySlidesVideosSection() {
		int articleCount = 12;
		boolean flag = false;
		softAssert = new SoftAssert();

		List<String> slidesVideosHref = WebBaseMethods.getListHrefUsingJSE(homePageMethods.getSlidesVideoTopLinks());
		String slidesHref = slidesVideosHref.get(0);

		int slidesResponse = HTTPResponse.checkResponseCode(slidesHref);
		softAssert.assertEquals(slidesResponse, 200,
				"<br>- <a href=" + slidesHref + "> " + slidesHref.substring(0, (slidesHref.length() / 2))
						+ "...</a> in section heading is throwing " + slidesResponse);
		String videosHref = slidesVideosHref.get(1);
		int videoResponse = HTTPResponse.checkResponseCode(videosHref);
		softAssert.assertEquals(videoResponse, 200,
				"<br>- <a href=" + videosHref + ">" + videosHref.substring(0, (videosHref.length() / 2))
						+ "...</a> in section heading is throwing " + videoResponse);
		List<String> slidesVideosStories = WebBaseMethods.getListTextUsingJSE(homePageMethods.getSlideVideoStories());
		softAssert.assertTrue(slidesVideosStories.size() >= articleCount,
				"<br>- Headlines under Slides Videos section should be more than " + articleCount
						+ " in number, instead found " + slidesVideosStories.size());
		List<String> slideVideoDup = VerificationUtil.isListUnique(slidesVideosStories);
		softAssert.assertTrue(slideVideoDup.isEmpty(),
				"<br>- Slides Videos section is having duplicate stories, repeating story(s)->" + slideVideoDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Opinion,Interview and Blogs")
	public void verifyOpinionBlogIntervSection() {
		int articleCount = 7;
		softAssert = new SoftAssert();

		Assert.assertTrue(homePageMethods.getOpinionLink() != null, "Opinion section is not shown");
		String opinion = homePageMethods.getOpinionLink().getAttribute("href");
		softAssert.assertTrue(opinion.contains("opinion"),
				"<br>- Link under opinion section is not having word opinion");
		int opinionResponse = HTTPResponse.checkResponseCode(opinion);
		softAssert.assertEquals(opinionResponse, 200,
				"<br>- Link under section heading of Opinion is throwing " + opinionResponse);
		opinion = homePageMethods.getInterviewsMoreLink().getAttribute("href");
		softAssert.assertTrue(opinion.contains("opinion"),
				"<br>- More link under opinion section is not having word opinion");
		opinionResponse = HTTPResponse.checkResponseCode(opinion);
		softAssert.assertEquals(opinionResponse, 200,
				"<br>- More link of section opinion is throwing " + opinionResponse);

		String interviews = homePageMethods.getInterviewsLink().getAttribute("href");
		softAssert.assertTrue(interviews.contains("interviews"),
				"<br>- Link under interviews section is not having word interviews");
		int interviewResponse = HTTPResponse.checkResponseCode(interviews);
		softAssert.assertEquals(interviewResponse, 200,
				"<br>- Link under section heading of Interviews is throwing " + interviewResponse);
		interviews = homePageMethods.getInterviewsMoreLink().getAttribute("href");
		softAssert.assertTrue(interviews.contains("interviews"),
				"<br>- More link under opinion section is not having word interviews");
		interviewResponse = HTTPResponse.checkResponseCode(interviews);
		softAssert.assertEquals(interviewResponse, 200,
				"<br>- More link of section interviews is throwing " + interviewResponse);

		String blogs = homePageMethods.getBlogsLink().getAttribute("href");
		softAssert.assertTrue(blogs.contains("blogs"), "<br>- Link under blogs section is not having word blogs");
		int blogsResponse = HTTPResponse.checkResponseCode(blogs);
		softAssert.assertEquals(blogsResponse, 200,
				"<br>- Link under section heading of Blogs is throwing " + blogsResponse);
		blogs = homePageMethods.getBlogsMoreLink().getAttribute("href");
		softAssert.assertTrue(blogs.contains("blogs"),
				"<br>- More link under opinion section is not having word blogs");
		blogsResponse = HTTPResponse.checkResponseCode(blogs);
		softAssert.assertEquals(blogsResponse, 200, "<br>- More link of section Opinion is throwing  " + blogsResponse);

		List<String> allArticlesHref = WebBaseMethods.getListHrefUsingJSE(homePageMethods.getOpinionIntervBlogLinks());
		allArticlesHref.forEach(href -> {
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in opinion section is throwing " + response);
		});

		List<String> allStories = WebBaseMethods.getListTextUsingJSE(homePageMethods.getOpinionIntervBlogLinks());
		softAssert.assertTrue(allStories.size() >= articleCount, "<br>- Headlines under Opinion should be more than "
				+ articleCount + " in number, instead found " + allStories.size());
		List<String> allStoriesDup = VerificationUtil.isListUnique(allStories);
		softAssert.assertTrue(allStoriesDup.isEmpty(),
				"<br>- Opinion section is having duplicate stories, repeating story(s)->" + allStories);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies there are no broken HTML tags on Home Page", groups = { "monitoring" })
	public void verifybrokenHTMLTags() {
		softAssert = new SoftAssert();

		List<String> brokenList = ETSharedMethods.getBrokenTags();
		softAssert.assertEquals(brokenList.size(), 0, "Broken tags found ->" + brokenList);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the ad count on ET Homepage")
	public void verifyGoogleAds() {
		SoftAssert softAssert = new SoftAssert();
		AdTechMethods adTechMethods = new AdTechMethods(driver);
		/*
		 * notFound = new HashMap<String, String>(); List<String> actualIds =
		 * adTechMethods.getDisplayedAdIds(); Boolean isAdCountCorrect =
		 * actualIds.size() >= 5 && adTechMethods.getAdsIFrameCount() >= 5;
		 * System.out.println(" " + isAdCountCorrect + "\n" + (actualIds.size()
		 * >= 5) + "\n" + (adTechMethods.getAdsIFrameCount() >= 5));
		 */
		List<String> missingAd = adTechMethods.returnAdNotShown();
		softAssert.assertFalse(missingAd.size() > 0, "Following ad(s) is/are not shown" + missingAd);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the date time on header on ET Homepage")
	public void verifyTimeAndDate() {
		softAssert = new SoftAssert();
		WebElement dateTimeNew = homePageMethods.getDateTimeTab();
		String dateTimeN = dateTimeNew.getText().replaceAll("\\n", "");
		DateTime systemDate = new DateTime();
		DateTime date = WebBaseMethods.convertDateMethod(dateTimeN.replaceAll("\\|", "").replaceAll(" ", "")
				.replaceAll(",", "").replaceAll("\\.", ":").replace("\\n", ""));
		Assert.assertTrue(dateTimeN.length() > 0, "Date time is not shown on header on industry page");
		softAssert.assertTrue(systemDate.getDayOfYear() == date.getDayOfYear(),
				"The date shown on industry page is not the current date instead is showing " + dateTimeN);
		softAssert.assertTrue(
				(systemDate.getMinuteOfDay() - date.getMinuteOfDay() <= 20
						&& systemDate.getMinuteOfDay() - date.getMinuteOfDay() >= 0),
				"on industry  page the time shown " + dateTimeN
						+ " is having difference of more than 20 mins from current time"
						+ systemDate.toString("MMM dd, yyyy hh:mm a"));
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the prime widget on header", groups = { "regression" })
	public void verifyPrimeHeaderWidget() {
		softAssert = new SoftAssert();
		WebBaseMethods.JSHoverOver(homePageMethods.getEtPrimeTab());
		int dayOfWeek = new DateTime().getDayOfWeek();

		final int allowedStaleness;
		switch (dayOfWeek) {
		case 6:
			allowedStaleness = 1;
			break;
		case 7:
			allowedStaleness = 2;
			break;
		default:
			allowedStaleness = 0;

		}
		int articleCount = 4;
		String goToPrimeLink = homePageMethods.getGoToPrimeLink();
		Assert.assertTrue(goToPrimeLink != null && goToPrimeLink.trim().length() > 0, "Go to prime link is not shown");
		softAssert.assertTrue(goToPrimeLink.contains("prime"),
				"<br>- Go to prime link shown is not of prime instead it is " + goToPrimeLink);
		int responseCode = HTTPResponse.checkResponseCode(goToPrimeLink);
		softAssert.assertEquals(responseCode, 200, "<br>-Go to prime link <a href=" + goToPrimeLink + "> "
				+ goToPrimeLink + "</a> is throwing " + responseCode);

		List<WebElement> primeHeadlines = homePageMethods.getPrimeHeadlines();
		List<String> etPrimeStories = WebBaseMethods.getListTextUsingJSE(primeHeadlines);
		System.out.println(etPrimeStories);
		softAssert.assertTrue(etPrimeStories.size() >= articleCount,
				"<br>- Headlines on ET PRIME header should be more than " + articleCount + " in number, instead found "
						+ etPrimeStories.size());
		List<String> etPrimeStoriesHref = WebBaseMethods.getListHrefUsingJSE(primeHeadlines);

		etPrimeStoriesHref.forEach(href -> {
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Et Prime Header section is throwing " + response);

			if (responseCode == 200) {
				WebBaseMethods.navigateTimeOutHandle(href);
				WaitUtil.sleep(3000);

				String storyHeadline = homePageMethods.getStoryHeadline();
				softAssert.assertTrue(storyHeadline != null && storyHeadline.length() > 1,
						"<br>Headline not visible on story-" + href);

				String storySummary = homePageMethods.getStorySummary();

				softAssert.assertTrue(storySummary != null && storySummary.length() > 1,
						"<br>Summary not visible on story-" + href);

				String authorName = homePageMethods.getAuthorName();
				softAssert.assertTrue(authorName != null && authorName.length() > 1,
						"<br>Author name not visible on story - <a href='" + href + "'>Story link</a>");

				String storyBody = homePageMethods.getStoryBody();
				System.out.println(storyBody);
				softAssert.assertTrue(storyBody != null && storyBody.length() > 1,
						"<br>Story body not visible on story-" + href);

				WaitUtil.sleep(4000);

				String publishDate = homePageMethods.getPublishDate();
				softAssert.assertTrue(publishDate != null && publishDate.length() > 1,
						"<br>Publish date not visible on story-" + href);
				publishDate += "00:00";

				System.out.println(publishDate);
				if (publishDate != null) {
					flag.add(VerificationUtil.isLatest(publishDate, allowedStaleness));
				}
				// System.out.println(homePageMethods.getCvrImageDimensions());
			}
		});
		softAssert.assertTrue(flag.stream().filter(l -> l.equals(true)).count() > 0,
				"<br>None of the story is of current date<br>");
		List<String> etPrimeHeaderNewsDup = VerificationUtil.isListUnique(etPrimeStoriesHref);
		softAssert.assertTrue(etPrimeHeaderNewsDup.isEmpty(),
				"<br>- ET prime header is having duplicate stories, repeating story(s)->" + etPrimeHeaderNewsDup);
		softAssert.assertAll();

	}

	@Test(description = "this test verifies the	international section in RHS")
	public void verifyInternationalSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();

		String sectionLink = homePageMethods.getInternationalLink();
		Assert.assertTrue(sectionLink.trim().length() > 0, "Section International is not shown");
		List<WebElement> internationalHeadlines = homePageMethods.getInternationalHeadlines();
		Assert.assertTrue(!internationalHeadlines.isEmpty(), "No stories shown under International section");
		softAssert.assertTrue(sectionLink.contains("international"),
				"<br>- Link under heading of the section is not of international page,instead is " + sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>-Section link <a href=" + sectionLink + ">"
				+ sectionLink.substring(0, (sectionLink.length() / 2)) + " </a> is throwing " + responseCode);

		List<String> internationalStories = WebBaseMethods
				.getListTextUsingJSE(homePageMethods.getInternationalHeadlines());
		softAssert.assertTrue(internationalStories.size() >= articleCount,
				"<br>- Headlines under International section should be more than " + articleCount
						+ " in number, instead found " + internationalStories.size());
		List<String> internationalStoriesHref = WebBaseMethods.getListHrefUsingJSE(internationalHeadlines);
		internationalStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in international section is throwing " + response);
		});
		List<String> internationalNewsDup = VerificationUtil.isListUnique(internationalStoriesHref);
		softAssert.assertTrue(internationalNewsDup.isEmpty(),
				"<br>- International is having duplicate stories, repeating story(s)->" + internationalNewsDup);
		String internationalMoreLink = homePageMethods.getInternationalMoreHref();
		softAssert.assertTrue(internationalMoreLink.contains("international"),
				"<br>- Link under more of the section is not of International, instead is " + internationalMoreLink);
		softAssert.assertAll();
	}

	@Test(description = "this test verifies the	jobs/careers section in RHS")
	public void verifyJobsNCareersSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();

		String sectionLink = homePageMethods.getJobsNCareersLink();
		Assert.assertTrue(sectionLink.trim().length() > 0, "Section Jobs/Careers is not shown");
		List<WebElement> jobsNCareersHeadlines = homePageMethods.getJobsNCareersHeadlines();
		Assert.assertTrue(!jobsNCareersHeadlines.isEmpty(), "No stories shown under Jobs/Careers section");
		softAssert.assertTrue(sectionLink.contains("jobs"),
				"<br>- Link under heading of the section is not of Jobs/Careers page,instead is " + sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>-Section link <a href=" + sectionLink + ">"
				+ sectionLink.substring(0, (sectionLink.length() / 2)) + " </a> is throwing " + responseCode);

		List<String> jobsNCareersStories = WebBaseMethods
				.getListTextUsingJSE(homePageMethods.getJobsNCareersHeadlines());
		softAssert.assertTrue(jobsNCareersStories.size() >= articleCount,
				"<br>- Headlines under Jobs/Careers section should be more than " + articleCount
						+ " in number, instead found " + jobsNCareersStories.size());
		List<String> jobsStoriesHref = WebBaseMethods.getListHrefUsingJSE(jobsNCareersHeadlines);
		jobsStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Jobs/Careers section is throwing " + response);
		});
		List<String> jobsNCareersNewsDup = VerificationUtil.isListUnique(jobsStoriesHref);
		softAssert.assertTrue(jobsNCareersNewsDup.isEmpty(),
				"<br>- Jobs/careers is having duplicate stories, repeating story(s)->" + jobsNCareersNewsDup);
		String jobsNCareersMoreLink = homePageMethods.getJobsNCareersMoreHref();
		softAssert.assertTrue(jobsNCareersMoreLink.contains("jobs"),
				"<br>- Link under more of the section is not of Jobs/Careers, instead is " + jobsNCareersMoreLink);
		softAssert.assertAll();
	}

	@BeforeMethod
	public void goToHome() {
		try {

			WebBaseMethods.navigateToUrl(driver, baseUrl);
			WaitUtil.sleep(2000);

			if (driver.getCurrentUrl().contains("interstitial")) {
				ETSharedMethods.clickETLinkInterstitialPage();

			}

			WaitUtil.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
