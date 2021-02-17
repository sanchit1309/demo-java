package wap.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.PodcastPageMethods;
import wap.pagemethods.StoryPageMethods;
import wap.pagemethods.WapListingPageMethods;
import web.pagemethods.WebBaseMethods;

public class PodcastPage extends BaseTest {
	private String wapPodcastUrl;
	private StoryPageMethods storyPageMethods;
	private WapListingPageMethods wapListingPageMethods;
	private HomePageMethods homePageMethods;
	private PodcastPageMethods podcastPageMethods;
	private SoftAssert softAssert;
	Map<String, String> TestData;
	Map<String, String> TestDateTwt;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapPodcastUrl = BaseTest.baseUrl + Config.fetchConfigProperty("WapPodcastUrl");
		launchBrowser(wapPodcastUrl);
		storyPageMethods = new StoryPageMethods(driver);
		wapListingPageMethods = new WapListingPageMethods(driver);
		homePageMethods = new HomePageMethods(driver);
		podcastPageMethods = new PodcastPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyLogin", 1);
		TestDateTwt = ExcelUtil.getTestDataRow("TestDataSheet", "verifyTwitter", 1);

	}

	public String getCurrentPodcastMsid() {
		String msid = "";
		String currentUrl = driver.getCurrentUrl();
		try {
			Pattern pattern = Pattern.compile("[\\d]*.cms$");
			Matcher matcher = pattern.matcher(currentUrl);
			matcher.find();
			msid = matcher.group(0).replaceAll(".cms", "");
		} catch (IllegalStateException ec) {
			msid = "";
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return msid;
	}

	@Test(description = "This test verifies the autoplay and play pause functionality on active podcast on listing page", priority = 1)
	public void verifyPlayPausePCListing() {
		softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		softAssert.assertTrue(podcastPageMethods.checkPodcastAutoplay(),
				"Podcast is not autoplaying on active podcast on listing page");
		WaitUtil.sleep(1500);
		softAssert.assertTrue(podcastPageMethods.checkPodcastPause(),
				"Pause functionality is not working on active podcast on listing page");
		WaitUtil.sleep(1500);
		softAssert.assertTrue(podcastPageMethods.checkPodcastPlay(),
				"Play functionality is not working on active podcast on listing page");
		softAssert.assertAll();

	}

	// pc is used for podcast
	@Test(description = "This test verifies the sharing of active podcast on listing page through facebook", priority = 2)
	public void verifyActivePcOnListingSharingByFb() {

		String pcTitle = podcastPageMethods.getActivePcOnLiHeadline();
		Assert.assertTrue(podcastPageMethods.clickFbBtnOnActivePcLi(), "Unable to click fb sharing btn");
		Assert.assertTrue(podcastPageMethods.verifyFbLogin(TestData.get("Email"), TestData.get("Password")),
				"Facebook Login failed");
		String fbPodcastHeading = podcastPageMethods.getFacebookSharing();
		WebBaseMethods.switchToParentClosingChilds();
		WaitUtil.sleep(5000);
		Assert.assertTrue(storyPageMethods.verifySharingMessage(pcTitle, fbPodcastHeading),
				"Facebook shared Podcast heading should be " + pcTitle + ", instead found " + fbPodcastHeading);

	}

	@Test(description = "This test verifies the sharing of active podcast on listing page through twitter", priority =0, enabled = true)
	public void verifyActivePcOnListingSharingByTwitter() {

		String pcTitle = podcastPageMethods.getActivePcOnLiHeadline().substring(0, 30);
		Assert.assertTrue(podcastPageMethods.navigateToTwitterSharing(), "Unable to navigate to twitter sharing");
		WebBaseMethods.switchToChildWindow(5);
		String sharedTitle = storyPageMethods.getTwitterArticeHeadline(TestDateTwt.get("Email"), TestDateTwt.get("Password"));
		WebBaseMethods.switchToParentClosingChilds();
		boolean flagTitle = pcTitle.contains(sharedTitle);
		Assert.assertTrue(storyPageMethods.verifySharingMessage(pcTitle, sharedTitle),
				"Twitter shared Podcast heading should be " + pcTitle + ", instead found " + sharedTitle);

	}

	@Test(description = "This test verifies the next podcast functionality on active podcast on listing page", priority = 3)
	public void verifyNextPodcastListing() {
		String nextPcTitle = "";
		softAssert = new SoftAssert();
		podcastPageMethods.clickListenBtn();
		String pcTitle = podcastPageMethods.getActivePcHeadline();
		System.out.println(pcTitle);
		Assert.assertTrue(podcastPageMethods.clickNextPodcastBtn(),
				"Next podcast button is not clickable. on active podcast");
		WaitUtil.sleep(2000);

		nextPcTitle = podcastPageMethods.getActivePcHeadline();
		WaitUtil.sleep(2000);
		System.out.println(nextPcTitle);
		Assert.assertTrue(!pcTitle.equalsIgnoreCase(nextPcTitle),
				"Next podcast functionality is not working on active podcast on listing page.");
		softAssert.assertTrue(podcastPageMethods.checkPodcastPause(),
				"Pause functionality is not working on active podcast on listing page.");
		softAssert.assertTrue(podcastPageMethods.checkPodcastPlay(),
				"Play functionality is not working on active podcast on listing page.");
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the podcasts on the podcast listing page", priority = 4)
	public void verifyPodcastListing() {
		softAssert = new SoftAssert();
		List<String> podcastListingLinks = podcastPageMethods.getPodcastListingLinks();
		Assert.assertTrue(podcastListingLinks.size() > 0, "<br>- No podcast is found on the podcast listing page ");
		podcastListingLinks.forEach(href -> {
			System.out.println(href);
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Podcast</a> in podcast listing is throwing " + response);
		});
		softAssert.assertAll();
	}

	// landing page testcases are below

	@Test(description = "This test verifies also listen section under the podcast", priority = 5)
	public void verifyAlsoListenSection() {
		softAssert = new SoftAssert();
		boolean flag = podcastPageMethods.clickFirstPodcast(wapPodcastUrl);
		Assert.assertTrue(flag, "Unable to open first podcast");
		String msid = getCurrentPodcastMsid();
		List<String> alsoListenLinks = podcastPageMethods.getAlsoListenLinks(msid);
		Assert.assertTrue(alsoListenLinks.size() >= 4,
				"<br>- The count of also listen podcast links should be equal to 4 but found" + alsoListenLinks.size());
		alsoListenLinks.forEach(href -> {
			System.out.println(href);
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- <a href=" + href
					+ "> Link </a> under also listen section in podcast is throwing " + response);
		});
		List<String> alsoListenDup = VerificationUtil.isListUnique(alsoListenLinks);
		softAssert.assertTrue(alsoListenDup.isEmpty(),
				"<br>- Also listen section under first podcast is having duplicate links, repeating link(s)->"
						+ alsoListenDup);

		softAssert.assertAll();

	}

	@Test(description = "This test verifies the related article section under the podcast", priority = 6)
	public void verifyRelatedArticlesSection() {
		softAssert = new SoftAssert();
		boolean flag = podcastPageMethods.clickFirstPodcast(wapPodcastUrl);
		Assert.assertTrue(flag, "Unable to open first podcast");
		String msid = getCurrentPodcastMsid();
		List<String> relatedArticleLinks = podcastPageMethods.getRelatedArticlesLinks(msid);
		Assert.assertTrue(relatedArticleLinks.size() >= 4,
				"<br>- The count of related articles podcast links should be equal to 4 but found"
						+ relatedArticleLinks.size());
		relatedArticleLinks.forEach(href -> {
			System.out.println(href);
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- <a href=" + href
					+ "> Link </a> under related article section in podcast is throwing" + response);
		});
		List<String> relatedArticleDup = VerificationUtil.isListUnique(relatedArticleLinks);
		softAssert.assertTrue(relatedArticleDup.isEmpty(),
				"<br>- Related articles section under first podcast is having duplicate links, repeating link(s)->"
						+ relatedArticleDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the markets videos section under the podcast", priority = 7)
	public void verifyMarketsVideosSection() {
		softAssert = new SoftAssert();
		boolean flag = podcastPageMethods.clickFirstPodcast(wapPodcastUrl);
		Assert.assertTrue(flag, "Unable to open first podcast");
		String msid = getCurrentPodcastMsid();
		List<String> marketsVideoLinks = podcastPageMethods.getMarketsVideosLinks(msid);
		Assert.assertTrue(marketsVideoLinks.size() >= 4,
				"<br>- The count of markets videos podcast links should be equal to 4 but found"
						+ marketsVideoLinks.size());
		marketsVideoLinks.forEach(href -> {
			System.out.println(href);
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- <a href=" + href
					+ "> Link </a> under markets video section in podcast is throwing " + response);
		});
		List<String> marketsVideosDup = VerificationUtil.isListUnique(marketsVideoLinks);
		softAssert.assertTrue(marketsVideosDup.isEmpty(),
				"<br>- markets videos section under first podcast is having duplicate links, repeating link(s)->"
						+ marketsVideosDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the sharing of podcast on landing page through facebook", priority = 8)
	public void verifyPcSharingByFb() {
		boolean flag = podcastPageMethods.clickFirstPodcast(wapPodcastUrl);
		Assert.assertTrue(flag, "Unable to open first podcast");
		String msid = getCurrentPodcastMsid();
		String pcTitle = podcastPageMethods.getPodcastHeadlineLanding(msid);
		Assert.assertTrue(podcastPageMethods.clickFbBtnOnPcLanding(msid), "Unable to click fb sharing btn");
	//	Assert.assertTrue(podcastPageMethods.verifyFbLogin(TestData.get("Email"), TestData.get("Password")),
	//			"Facebook Login failed");
		String fbPodcastHeading = podcastPageMethods.getFacebookSharing();
		WebBaseMethods.switchToParentClosingChilds();
		WaitUtil.sleep(5000);
		Assert.assertTrue(storyPageMethods.verifySharingMessage(pcTitle, fbPodcastHeading),
				"Facebook shared Podcast heading should be " + pcTitle + ", instead found " + fbPodcastHeading);

	}

	@Test(description = "This test verifies the sharing of first podcast on landing page through twitter", priority = 9)
	public void verifyPcLandingSharingByTwitter() {
		boolean flag = podcastPageMethods.clickFirstPodcast(wapPodcastUrl);
		Assert.assertTrue(flag, "Unable to open first podcast");
		String msid = getCurrentPodcastMsid();
		String pcTitle = podcastPageMethods.getPodcastHeadlineLanding(msid);
		Assert.assertTrue(podcastPageMethods.navigateToTwitterSharingPcLanding(msid),
				"Unable to navigate to twitter sharing");
		WebBaseMethods.switchToChildWindow(5);
		String sharedTitle = storyPageMethods.getTwitterArticeHeadline(TestDateTwt.get("Email"), TestDateTwt.get("Password"));
		WebBaseMethods.switchToParentClosingChilds();
		// boolean flagTitle = pcTitle.contains(sharedTitle);
		Assert.assertTrue(storyPageMethods.verifySharingMessage(pcTitle, sharedTitle),
				"Twitter shared Podcast heading should be " + pcTitle + ", instead found " + sharedTitle);

	}

	@Test(description = "This test verifies the autoplay and play pause functionality on podcast landing page", priority = 10)
	public void verifyPlayPausePCLanding() {
		softAssert = new SoftAssert();
		boolean flag = podcastPageMethods.clickFirstPodcast(wapPodcastUrl);
		Assert.assertTrue(flag, "Unable to open first podcast");
		String msid = getCurrentPodcastMsid();
		softAssert.assertTrue(podcastPageMethods.checkPodcastAutoplay(msid),
				"Podcast is not autoplaying. Pocast id is : " + msid);
		WaitUtil.sleep(1500);
		softAssert.assertTrue(podcastPageMethods.checkPodcastPause(msid),
				"Pause functionality is not working. Podcast id is :" + msid);
		WaitUtil.sleep(1500);
		softAssert.assertTrue(podcastPageMethods.checkPodcastPlay(msid),
				"Play functionality is not working. Podcast id is: " + msid);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the next podcast functionality on the podcast landing page", priority = 11)
	public void verifyNextPodcastLanding() {
		softAssert = new SoftAssert();
		String nextPodcastMsid = "";
		boolean flag = podcastPageMethods.clickFirstPodcast(wapPodcastUrl);
		Assert.assertTrue(flag, "Unable to open first podcast");
		String msid = getCurrentPodcastMsid();
		System.out.println(msid);
		Assert.assertTrue(podcastPageMethods.clickNextPodcastBtn(msid),
				"Next podcast button is not clickable. Podcast id is: " + msid);
		WaitUtil.sleep(2000);
		nextPodcastMsid = getCurrentPodcastMsid();
		WaitUtil.sleep(2000);
		System.out.println(nextPodcastMsid);
		Assert.assertTrue(!msid.equalsIgnoreCase(nextPodcastMsid),
				"Next podcast functionality is not working. Podcast id is: " + msid);

	}

}
