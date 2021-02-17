package pwa.tests;

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
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import pwa.pagemethods.HomePageMethods;
import pwa.pagemethods.PwaListingPageMethods;
import web.pagemethods.WebBaseMethods;

public class HomePage extends BaseTest {

	private String wapUrl;
	private HomePageMethods homePageMethods;
	private PwaListingPageMethods pwaListingPageMethods;
	private String excelPath = "MenuSubMenuData";
	private Map<String, String> testData;
	private Map<String, String> industryHomeTopScroll;
	private SoftAssert softAssert;
	private String primeUrl;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		WebBaseMethods.slowScroll(40);
		// WebBaseMethods.scrollFixedHeightMultipleTimes(10, "Bottom");
		homePageMethods = new HomePageMethods(driver);
		pwaListingPageMethods = new PwaListingPageMethods(driver);
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyHomePage", 1);
		industryHomeTopScroll = ExcelUtil.getTestDataRow(excelPath, "verifyIndustryHomeNavigation", 1);
		primeUrl = Config.fetchConfigProperty("primeUrl");
		Assert.assertTrue(checkIsPWA(), "Skipping as page is not pwa");
	}

	@Test(description = "Verify Add to screen icon is visible on Header", groups = { "Home Page" }, priority = 0)
	public void isAddToScreenIconVisible() {
		Assert.assertTrue(homePageMethods.isAddToScreenIconVisible(),
				"Add to screen option missing from header on PWA");
	}

	@Test(description = "Verify Home Page top scroll section", groups = { "Home Page" }, priority = 1)
	public void verifyHomeTopSection() {
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("TabNames").split("\\s*,\\s*")));
		List<String> actualMenuItemList = homePageMethods.getTopSectionHeaders();
		Assert.assertTrue(VerificationUtil.listActualInExpected(expectedMenuItemList, actualMenuItemList),
				"Actual list of menu items is not matching the expected list of menu items.Missing items are->"
						+ VerificationUtil.getMissingMenuOptionList());
	}

	@Test(description = "Verify top news story pages", groups = { "Home Page" }, priority = 2, enabled = true)
	public void verifyTopNewsStoryPage() {
		softAssert = new SoftAssert();
		List<String> headlinesHref = VerificationUtil.getLinkHrefList(pwaListingPageMethods.getHomeHeadlines());
		headlinesHref.forEach(href -> {
			int responseCode = HTTPResponse.checkResponseCode(href);
			softAssert.assertTrue(responseCode == 200, "<br> Response code for the url <a href=" + href + "> " + href
					+ "</a>is not 200,instead is " + responseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Slideshow on home page", groups = { "Home Page" }, priority = 3)
	public void verifySlideshowHome() {
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

	@Test(description = "This test verifies the prime widget on home page", priority = 4)
	public void verifyPrimeWidget() {
		softAssert = new SoftAssert();
		int articleCount = 2;
		WebBaseMethods.scrollToTop();
		WebBaseMethods.slowScroll(10);
		Assert.assertTrue(homePageMethods.isPrimeWidgetPresent(), "Prime Widget missing from home page");
		String headerUrl = homePageMethods.getSectionHeaderHref();
		softAssert.assertTrue(headerUrl != null && headerUrl.contains(primeUrl),
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

	@Test(description = "Verify Top Mutual Funds data widget", groups = { "Home Page" }, priority = 5)
	public void verifyMFWidget() {
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
							homePageMethods.getNoResultText(mfTab.toLowerCase()).contains("THERE IS NO DATA FOR"),
							"No data displayed for " + mfTab + " tab and no appropriate message displayed for the same."
									+ homePageMethods.getNoResultText(mfTab.toLowerCase()));
				} else
					softAssert.assertTrue(homePageMethods.getNoResultText("Equity").contains("THERE IS NO DATA FOR"),
							"No data displayed for " + mfTab + " tab and no appropriate message displayed for the same."
									+ homePageMethods.getNoResultText("Equity"));
			}

		}
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page Markets section", groups = { "Home Page" }, priority = 7)
	public void verifyHomeMarketsSection() {
		int articleCount = 3;
		String section = "marketsNews";
		softAssert = new SoftAssert();
		String sectionId = homePageMethods.getSectionDataId(section);
		String sectionLink = homePageMethods.getSectionNewsLink(section);
		// softAssert.assertTrue(sectionLink.contains("markets"), "<br>- Link
		// under heading of the section is not of markets, instead
		// is:"+sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> marketNewsStories = VerificationUtil
				.getLinkTextList(homePageMethods.getSectionNewsHeadlines(section));
		int count = marketNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under market sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> marketNewsDup = VerificationUtil.isListUnique(marketNewsStories);
		softAssert.assertTrue(marketNewsDup.isEmpty(),
				"<br>- Markets section has duplicate stories, repeating story(s)->" + marketNewsDup);
		homePageMethods.getSectionTop10Href(section).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top 10 market link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertTrue(homePageMethods.getMoreSectionNewsHref(section).endsWith(sectionId),
				"More link does not have same data-id as section heading " + sectionId
						+ " instead data id in more link is " + homePageMethods.getMoreSectionNewsHref(section));
		softAssert.assertAll();
	}

	@Test(description = "Verify Markets data widget", groups = { "Home Page" }, priority = 6)
	public void verifyMarketsDataWidget() {
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
				softAssert.assertTrue(VerificationUtil.valueIsInRange(ETWAPSensexVal, Double.parseDouble(BSESiteSensexVal), 40),
						"Market index value on ET WAP and BSE site is not matching , and is beyond the expected difference of "
								+ 40 + " ETWAPsiteValue:" + ETWAPSensexVal + " ,BSESiteValue: "
								+ BSESiteSensexVal);
				}
				
				ETWAPNiftyVal = homePageMethods.getNIftyValue();
				NSESiteNiftyVal = BusinessLogicMethods.getNiftyCurrentPrice();
				if (NSESiteNiftyVal.startsWith("Error")) {
					softAssert.assertFalse(false,
							"Unable to fetch value for comparison from NSE India due to some error on their website.");
				} else {
				softAssert.assertTrue(VerificationUtil.valueIsInRange(ETWAPNiftyVal, Double.parseDouble(NSESiteNiftyVal), 20),
						"Market index value on ET WAP and NSE site is not matching , and is beyond the expected difference of "
								+ 20 + " ETWAPsiteValue:" + ETWAPNiftyVal + " ,NSESiteValue: "
								+ NSESiteNiftyVal);
				}
				
				ETWAPGoldVal = homePageMethods.getGoldValue();
				McxSiteGoldVal = BusinessLogicMethods.getGoldPrice();
				if(McxSiteGoldVal.startsWith("Error")) {
					softAssert.assertFalse(false,
							"Unable to fetch value for comparison from MCX India due to some error on their website.");
				} else {
				softAssert.assertTrue(VerificationUtil.valueIsInRange(ETWAPGoldVal, Double.parseDouble(McxSiteGoldVal), 50),
						"Market index value on ET and mcx site is not matching , and is beyond the expected difference of "
								+ 50 + " ETWAPsiteValue:" + ETWAPGoldVal + " ,MCXSiteValue: "
								+ McxSiteGoldVal);
				}
				
				ETWAPUsdVal = homePageMethods.getForexValue();
				usdSiteValue = BusinessLogicMethods.getUSDINR();
				if (usdSiteValue.startsWith("Error")) {
					Assert.assertFalse(false,
							"Unable to fetch value for comparison from Mecklai due to some error on their website.");
				} else {
				softAssert.assertTrue(VerificationUtil.valueIsInRange(ETWAPUsdVal, Double.parseDouble(usdSiteValue), 0.5),
						"Market index value on ET and mecklai site is not matching , and is beyond the expected difference of "
								+ 0.5 + "% ETWAPsiteValue:" + ETWAPUsdVal + " ,Mecklai siteValue: "
								+ usdSiteValue);
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

	@Test(description = "Verify Home page Politics section", groups = { "Home Page" }, priority = 8)
	public void verifyHomePoliticsSection() {
		int articleCount = 3;
		String section = "politicsNews";
		softAssert = new SoftAssert();
		String sectionId = homePageMethods.getSectionDataId(section);
		String sectionLink = homePageMethods.getSectionNewsLink(section);
		// softAssert.assertTrue(sectionLink.contains("politics"), "<br>- Link
		// under heading of the section is not of politics, instead
		// is:"+sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> politicsNewsStories = VerificationUtil
				.getLinkTextList(homePageMethods.getSectionNewsHeadlines(section));
		int count = politicsNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under politics sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> politicsNewsDup = VerificationUtil.isListUnique(politicsNewsStories);
		softAssert.assertTrue(politicsNewsDup.isEmpty(),
				"<br>- Politics has duplicate stories, repeating story(s)->" + politicsNewsDup);
		homePageMethods.getSectionTop10Href(section).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top 10 politics link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertTrue(homePageMethods.getMoreSectionNewsHref(section).endsWith(sectionId),
				"More link does not have same data-id as section heading " + sectionId
						+ " instead data id in more link is " + homePageMethods.getMoreSectionNewsHref(section));
		softAssert.assertAll();
	}

	@Test(description = "Verify Home Page Industry top scroll section", groups = { "Home Page" }, priority = 9)
	public void verifyIndustryHomeTopSection() {
		String section = "industry";
		List<String> expectedMenuItemList = new ArrayList<>(
				Arrays.asList(industryHomeTopScroll.get("IndustryHomeNavigation").split("\\s*,\\s*")));
		homePageMethods.moveToIndustrySection(section);
		List<String> actualMenuItemList = homePageMethods.getIndustrySectionHeaders();
		Assert.assertTrue(VerificationUtil.listActualInExpected(actualMenuItemList, expectedMenuItemList),
				"Actual list of menu items is not matching the expected list of menu items. Actual List "
						+ actualMenuItemList + " Expected List " + expectedMenuItemList);
	}

	@Test(description = "Verify Home page Industry section", groups = { "Home Page" }, priority = 10)
	public void verifyHomeIndustrySection() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		String section = "industry";
		// homePageMethods.moveToIndustrySection(section);
		String sectionId = homePageMethods.getSectionDataId(section + "News");
		String sectionLink = homePageMethods.getSectionNewsLink(section);
		// String sectionLink = homePageMethods.getIndustryNewsLink();
		// softAssert.assertTrue(sectionLink.contains("industry"), "<br>- Link
		// under heading of the section is not of industry, instead
		// is:"+sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		for (WebElement ele : homePageMethods.getIndustryTopSectionHeaders()) {
			// homePageMethods.moveToIndustrySection(section);
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
			// if (!ele.getText().equalsIgnoreCase("Featured") ) {
			// int moreLinkResponseCode =
			// HTTPResponse.checkResponseCode(homePageMethods.getMoreIndustryNewsHref(ele));
			// softAssert.assertTrue(moreLinkResponseCode);
			// }else {
			// softAssert.assertTrue(arg0, arg1);
			// }
		}
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page Wealth section", groups = { "Home Page" }, priority = 11)
	public void verifyHomeWealthSection() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		String section = "wealthNews";
		String sectionId = homePageMethods.getSectionDataId(section);
		String sectionLink = homePageMethods.getSectionNewsLink(section);
		// softAssert.assertTrue(sectionLink.contains("personal-finance"),
		// "<br>- Link under heading of the section is not of wealth, instead
		// is:"+sectionLink);
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
		softAssert.assertTrue(homePageMethods.getMoreSectionNewsHref(section).endsWith(sectionId),
				"More link does not have same data-id as section heading " + sectionId
						+ " instead data id in more link is " + homePageMethods.getMoreSectionNewsHref(section));
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page Mutual Funds section", groups = { "Home Page" }, priority = 12)
	public void verifyHomeMFSection() {
		int articleCount = 3;
		String section = "mutualfundsNews";
		softAssert = new SoftAssert();
		String sectionId = homePageMethods.getSectionDataId(section);
		String sectionLink = homePageMethods.getSectionNewsLink(section);
		// softAssert.assertTrue(sectionLink.contains("mutual_funds"), "<br>-
		// Link under heading of the section is not of MF, instead
		// is:"+sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> mFNewsStories = VerificationUtil.getLinkTextList(homePageMethods.getSectionNewsHeadlines(section));
		int count = mFNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under MF sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> mFNewsDup = VerificationUtil.isListUnique(mFNewsStories);
		softAssert.assertTrue(mFNewsDup.isEmpty(),
				"<br>- MF section has duplicate stories, repeating story(s)->" + mFNewsDup);
		homePageMethods.getSectionTop10Href(section).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top 10 MF link <a href=" + keyword + ">" + keyword
					+ "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertTrue(homePageMethods.getMoreSectionNewsHref(section).endsWith(sectionId),
				"More link does not have same data-id as section heading " + sectionId
						+ " instead data id in more link is " + homePageMethods.getMoreSectionNewsHref(section));
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page RISE section", groups = { "Home Page" }, priority = 13)
	public void verifyHomeRiseSection() {
		int articleCount = 3;
		String section = "riseNews";
		softAssert = new SoftAssert();
		String sectionId = homePageMethods.getSectionDataId(section);
		String sectionLink = homePageMethods.getSectionNewsLink(section);
		// softAssert.assertTrue(sectionLink.contains("small-biz"), "<br>- Link
		// under heading of the section is not of Sm, instead is:"+sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> smallBizNewsStories = VerificationUtil
				.getLinkTextList(homePageMethods.getSectionNewsHeadlines(section));
		int count = smallBizNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Small Biz sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> smallBizNewsDup = VerificationUtil.isListUnique(smallBizNewsStories);
		softAssert.assertTrue(smallBizNewsDup.isEmpty(),
				"<br>- Small Biz is having duplicate stories, repeating story(s)->" + smallBizNewsDup);
		homePageMethods.getSectionTop10Href(section).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top 10 Small Biz link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertTrue(homePageMethods.getMoreSectionNewsHref(section).endsWith(sectionId),
				"More link does not have same data-id as section heading " + sectionId
						+ " instead data id in more link is " + homePageMethods.getMoreSectionNewsHref(section));
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page Tech section", groups = { "Home Page" }, priority = 14)
	public void verifyHomeTechSection() {
		int articleCount = 3;
		String section = "techNews";
		softAssert = new SoftAssert();
		String sectionId = homePageMethods.getSectionDataId(section);
		String sectionLink = homePageMethods.getSectionNewsLink(section);
		// softAssert.assertTrue(sectionLink.contains("tech"), "<br>- Link under
		// heading of the section is not of Tech, instead is:"+sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> techNewsStories = VerificationUtil
				.getLinkTextList(homePageMethods.getSectionNewsHeadlines(section));
		int count = techNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Tech sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> techNewsDup = VerificationUtil.isListUnique(techNewsStories);
		softAssert.assertTrue(techNewsDup.isEmpty(),
				"<br>- Tech is having duplicate stories, repeating story(s)->" + techNewsDup);
		homePageMethods.getSectionTop10Href(section).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top 10 Tech link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});

		softAssert.assertTrue(homePageMethods.getMoreSectionNewsHref(section).endsWith(sectionId),
				"More link does not have same data-id as section heading " + sectionId
						+ " instead data id in more link is " + homePageMethods.getMoreSectionNewsHref(section));
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page Panache section", groups = { "Home Page" }, priority = 14)
	public void verifyHomePanacheSection() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		String section = "panache";
		String sectionId = homePageMethods.getSectionDataId(section + "News");
		String sectionLink = homePageMethods.getSectionNewsLink(section);
		// softAssert.assertTrue(sectionLink.contains("panache"), "<br>- Link
		// under heading of the section is not of panache, instead
		// is:"+sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		for (WebElement ele : homePageMethods.getPanacheTopSectionHeaders()) {
			// homePageMethods.moveToPanacheSection();
			System.out.println(ele.getText());
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
			/*
			 * String moreLink = homePageMethods.getMorePanacheNewsHref(ele);
			 * 
			 * Assert.assertTrue(!moreLink.isEmpty(), "More link not found");
			 * int rCode = HTTPResponse.checkResponseCode(moreLink);
			 * softAssert.assertEquals(rCode, 200, "<br>- <a href=" + moreLink +
			 * "> more news link </a> of " + ele.getText() + " is throwing " +
			 * rCode); if (!ele.getText().equalsIgnoreCase("between the lines"))
			 * softAssert.assertTrue(moreLink.contains("panache"),
			 * "More link is not taking to " + ele.getText() +
			 * " page,instead it takes to " + moreLink);
			 */
		}
		softAssert.assertAll();
	}

	@Test(description = "Verify Home page Economy section", groups = { "Home Page" }, priority = 15)
	public void verifyHomeEconomySection() {
		int articleCount = 3;
		String section = "economy";
		softAssert = new SoftAssert();
		String sectionId = homePageMethods.getSectionDataId(section);
		String sectionLink = homePageMethods.getSectionNewsLink(section);
		// softAssert.assertTrue(sectionLink.contains("economy"), "<br>- Link
		// under heading of the section is not of Economy, instead
		// is:"+sectionLink);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> economyNewsStories = VerificationUtil
				.getLinkTextList(homePageMethods.getSectionNewsHeadlines(section));
		int count = economyNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Economy sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> economyNewsDup = VerificationUtil.isListUnique(economyNewsStories);
		softAssert.assertTrue(economyNewsDup.isEmpty(),
				"<br>- Economy is having duplicate stories, repeating story(s)->" + economyNewsDup);
		homePageMethods.getSectionTop10Href(section).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top 10 Economy link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertTrue(homePageMethods.getMoreSectionNewsHref(section).endsWith(sectionId),
				"More link does not have same data-id as section heading " + sectionId
						+ " instead data id in more link is " + homePageMethods.getMoreSectionNewsHref(section));
		softAssert.assertAll();
	}

	@Test(description = "Verify custom sub sections", groups = { "Home Page" }, priority = 16)
	public void verifyCustomSubSections() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		int sectionCount = 1;
		Assert.assertTrue(homePageMethods.moveToSpotlightSection(),
				"Could not navigate to Spotlight section on home page");
		if (homePageMethods.getSectionHeaders().size() != 0) {
			for (String ele : homePageMethods.getSectionHeaders()) {
				WaitUtil.sleep(1000);
				List<String> sectionStories = VerificationUtil
						.getLinkTextList(homePageMethods.getSpotlightSectionHeadlines(sectionCount));
				int count = sectionStories.size();
				softAssert.assertTrue(count >= articleCount, "<br>- Headlines under " + ele
						+ " section should be more than " + articleCount + " in number, instead found " + count);
				if (count != 0) {
					List<String> sectionDup = VerificationUtil.isListUnique(sectionStories);
					softAssert.assertTrue(sectionDup.isEmpty(), "<br>- Spotlight section " + ele
							+ " has duplicate stories, repeating story(s)->" + sectionDup);
					homePageMethods.getSpotlightSectionHref(sectionCount).forEach(keyword -> {
						int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
						softAssert.assertEquals(topLinksResponseCode, 200,
								"<br>- Top story link <a href=" + keyword + ">" + keyword + "</a> is throwing "
										+ topLinksResponseCode + " for " + ele + " section in spotlight");
					});
				}
				sectionCount++;
			}
		} else
			Assert.assertTrue(false, "Could not find any spotlight sub sections");

		softAssert.assertAll();
	}

	@Test(description = "Verify recency of articles", groups = { "Home Page" }, priority = 20, enabled = true)
	public void verifyTopNewsRecency() {
		int numberDays = 2;
		softAssert = new SoftAssert();
		driver.get(wapUrl);
		String tabsRecency = "Home";
		List<String> headlineRecency = homePageMethods.getOldStories(tabsRecency, numberDays);
		for (String entry : headlineRecency) {
			softAssert.assertTrue(false, "Article Recency verification for " + entry + " failed, article dated "
					+ homePageMethods.getArticleDate(entry));
		}
		softAssert.assertAll();
	}

	private boolean checkIsPWA() {
		boolean flag = false;
		if (driver.getPageSource().contains("pwa")) {
			flag = true;
		}
		WebBaseMethods.refreshTimeOutHandle();
		flag = driver.getPageSource().contains("pwa");
		return flag;
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
