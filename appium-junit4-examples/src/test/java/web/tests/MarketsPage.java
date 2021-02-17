package web.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import common.utilities.reporting.ScreenShots;
import web.pagemethods.AdTechMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.MarketsPageMethods;
import web.pagemethods.WebBaseMethods;

public class MarketsPage extends BaseTest {

	private String baseUrl;
	MarketsPageMethods marketsPageMethods;
	HeaderPageMethods headerPageMethods;
	AdTechMethods adTechMethods;
	SoftAssert softAssert;
	SoftAssert anotherSoftAssert;
	boolean retryFlag = false;
	int retryCounter = 0;
	String data = "";

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl + Config.fetchConfigProperty("MarketsUrl");
		launchBrowser(baseUrl);
		marketsPageMethods = new MarketsPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		WebBaseMethods.scrollToBottom();
	}

	@Test(description = " This test verifies the ads on Markets Listing Page")
	public void verifyAds() {
		softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		Map<String, String> expectedIdsMap = Config.getAllKeys("adIdLocation");
		softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0,
				"No google ads shown on Markets Listing Page.");
		if (adTechMethods.getDisplayedAdIds().size() > 0) {
			expectedIdsMap.forEach((K, V) -> {
				if (K.contains("ET_ROS") && !(K.contains("_AS_"))) {
					softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K),
							"Following ad(s) is/are not shown " + expectedIdsMap.get(K) + " on Markets Listing Page.");
				}
			});
		}
		List<String> ctnAd = adTechMethods.getMissingColumbiaAds();
		softAssert.assertFalse(ctnAd.size() > 0,
				"Following colombia ad(s) is/are not shown " + ctnAd + " on Markets Listing Page.");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies market news on markets page", groups = { "monitoring" })
	public void verifyMarketsNewsSections() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		// List<String> subTab = new LinkedList<String>();
		String tabName = "NEWS,IPO,RECO,EARNING";
		List<String> tabNames = Arrays.asList(tabName.split(","));
		tabNames.forEach(parentTabName -> {
			Assert.assertTrue(marketsPageMethods.isTabShow(parentTabName),
					"<br>- The left side news section is not showing tab " + parentTabName);
			marketsPageMethods.clickTabName(parentTabName);
			WaitUtil.sleep(5000);
			String subTabs = parentTabName.equalsIgnoreCase("News") ? "Top_Stories,Read,Videos,Just In" : "Top_Stories";
			List<String> subTabNames = Arrays.asList(subTabs.split(","));

			subTabNames.forEach(subTabName -> {
				if (!subTabName.equalsIgnoreCase("Top_Stories")) {
					Assert.assertTrue(marketsPageMethods.getSubTabLink(subTabName) != null,
							"<br>- Market News section, tab " + parentTabName + " is not having sub-tab " + subTabName);
					WebBaseMethods.clickElementUsingJSE(marketsPageMethods.getSubTabLink(subTabName));
					WaitUtil.sleep(5000);
				}
				String idName = subTabNames.size() > 1 ? subTabName : parentTabName;
				List<String> tabStories = VerificationUtil
						.getLinkTextList(marketsPageMethods.getStoriesLink(idName.toLowerCase().replaceAll(" ", "")));

				softAssert.assertTrue(tabStories.size() >= articleCount,
						"<br>- Headlines under " + parentTabName + " section are not more than " + articleCount
								+ " in number and total found is " + tabStories.size() + ".");
				String attributeName = subTabName.equalsIgnoreCase("videos") ? "data-href" : "href";
				List<String> tabStoriesHref = WebBaseMethods
						.getListAnyAttributeUsingJSE(marketsPageMethods.getStoriesLink(idName), attributeName);
				tabStoriesHref.forEach(href -> {
					int response = HTTPResponse.checkResponseCode(href);
					softAssert.assertEquals(response, 200, "<br>- <a href=" + href + "> Story</a> in markets "
							+ parentTabName + " section is throwing " + response);
				});
				// List<String> tabStoriesDup =
				// VerificationUtil.isListUnique(tabStories);
				// softAssert.assertTrue(tabStoriesDup.isEmpty(), "<br>-" +
				// parentTabName + "-" + subTabName
				// + " section is having duplicate stories, repeating
				// story(s)->" + tabStoriesDup);

			});

		});
		softAssert.assertAll();

	}

	@Test(description = "This test verifies section under market dashboard", groups = { "monitoring" })
	public void verifySectionMarketDashboard() {
		anotherSoftAssert = new SoftAssert();
		String tabName = "gainer,loser,commodity_gainer,mf_direct_buy,commodity_loser,currency,id_52w_high,top_mutual_fund,top_nps_schemes,etf,ipo_sec,quarterly_result";
		List<String> sectionNames = Arrays.asList(tabName.split(","));
		sectionNames.forEach(section -> {
			softAssert = new SoftAssert();
			retryCounter = 0;
			retryFlag = false;
			do {
				if (retryFlag) {
					softAssert = new SoftAssert();
					retryFlag = false;
				}

				String name = "";
				try {
					softAssert.assertTrue(marketsPageMethods.getSectionLinkHeading(section) != null,
							"<br>Company name/scheme name/currency name not shown in section " + section);
					WebElement el = marketsPageMethods.getSectionLinkHeading(section);
					name = WebBaseMethods.getTextUsingJSE(el);
					System.out.println(name);
					anotherSoftAssert.assertTrue(name.trim().length() > 0,
							"<br>The section " + section + " does not show any name.");
					if (name.trim().length() < 1) {
						return;
					}
					List<WebElement> dataTag = marketsPageMethods.getSectionData(section);

					// softAssert.assertTrue(dataTag.size() > 0, "<br>No data
					// found under section: " + section);
					if (!section.equals("mf_direct_buy")) {
						data = "";
						dataTag.forEach(indVal -> {
							data += WebBaseMethods.getTextUsingJSE(indVal);

						});
						if (data.trim().length() < 1) {
							new ScreenShots().takeScreenshotAnotherDriver(driver, "verifySectionMarketDashboard_blank");
						}

						softAssert.assertTrue(data.length() > 0,
								"<br>Data is not displayed for " + name + " under section " + section);
					}
				} catch (StaleElementReferenceException e) {
					System.out.println(
							"Stale Element error while getting value for " + name + " attempt " + (retryCounter + 1));
					retryFlag = true;
					retryCounter++;
				}

			} while (retryFlag && retryCounter < 5);
			softAssert.assertAll();
		});

		anotherSoftAssert.assertAll();
	}

	@Test(description = "This test verifies date and time shown on header on markets page")
	public void verifyDateTime() {
		softAssert = new SoftAssert();
		String dateTimeN = headerPageMethods.getDateTimeTab();

		DateTime systemDate = new DateTime();

		DateTime date = WebBaseMethods.convertDateMethod(
				dateTimeN.replaceAll("\\|", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\.", ":"));

		Assert.assertTrue(dateTimeN != null, "Date time is not shown on header on markets page");
		softAssert.assertTrue(systemDate.getDayOfYear() == date.getDayOfYear(),
				"The date shown on markets page is not the current date instead is showing " + dateTimeN);
		softAssert.assertTrue(
				(systemDate.getMinuteOfDay() - date.getMinuteOfDay() <= 20
						&& systemDate.getMinuteOfDay() - date.getMinuteOfDay() >= 0),
				"on markets  page the time shown " + dateTimeN
						+ " is having difference of more than 20 mins from current time"
						+ systemDate.toString("MMM dd, yyyy hh:mm a"));
		softAssert.assertAll();

	}

	

}
