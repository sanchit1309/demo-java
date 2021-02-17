package com.web.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.CommonMethods;
import common.utilities.ExcelUtil;
import common.utilities.FileUtility;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pagemethods.AdTechMethods;
import web.pagemethods.ArticleMethods;
import web.pagemethods.Articleshow_newPageMethods;
import web.pagemethods.CompareArticleMethods;
import web.pagemethods.HomePageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;

public class CompareArticleshowContent extends BaseTest {

	private String baseUrl;
	HomePageMethods homePageMethods;
	LoginPageMethods loginPageMethods;
	CommonMethods commonMethods;
	WebBaseMethods webBaseMethods;
	ArticleMethods articleMethods;
	Articleshow_newPageMethods articleshow_NewPageMethods;
	CompareArticleMethods compareArticleMethods;
	Map<String, String> youtubeEmebedOnDev, youtubeEmebedOnLive;
	Map<String, String> slikeVideoEmebedOnDev, slikeVideoEmebedOnLive;
	Map<String, String> facebookEmbedOnDev, facebookEmbedOnLive;
	Map<String, String> twitterEmbedOnDev, twitterEmbedOnLive;
	Map<String, String> imagesEmbededOnDev, imagesEmbededOnLive;
	Map<String, String> instagramEmbedsOnDev, instagramEmbedsOnLive;

	SoftAssert softAssert;

	AdTechMethods adTechMethods;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		homePageMethods = new HomePageMethods(driver);
		articleMethods = new ArticleMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		articleshow_NewPageMethods = new Articleshow_newPageMethods(driver);
		compareArticleMethods = new CompareArticleMethods(driver);
		commonMethods = new CommonMethods(driver);

	}

	@Test(description = "this test verifies the content of the articleshow on et dev with et live", priority = 0)
	public void checkArticleshowContent() {
		softAssert = new SoftAssert();
		List<String> urls = getAllUrlList().subList(0, 150);
		urls.forEach(url -> {

			// to check the content on live//

			WebBaseMethods.navigateToUrl(driver, url);
			WaitUtil.waitForLoad(driver);
			WaitUtil.sleep(5000);
			Map<String, String> contentFromLive = articleMethods.getArticleAllElements();
			List<String> alsoReadLinksOnLive = articleMethods.getArticleAlsoReadLinks();
			List<String> readMoreLinksOnLive = articleMethods.getArticleReadMoreLinks();
			// System.out.println("links on live:" + readMoreLinksOnLive);
			// System.out.println("content from live" + contentFromLive);

			// to check the content on dev//
			WebBaseMethods.navigateToUrl(driver, compareArticleMethods.getDevLink(url));
			WaitUtil.waitForLoad(driver);
			WaitUtil.sleep(5000);
			Map<String, String> contentFromDev = articleshow_NewPageMethods.getArticleAllElements();
			List<String> alsoReadlinksOnDev = articleshow_NewPageMethods.getArticleAlsoReadLinks();
			List<String> readMoreLinksOnDev = articleshow_NewPageMethods.getArticleReadMoreLinks();
			// System.out.println("links on dev:" + readMoreLinksOnDev);
			// System.out.println("content from dev" + contentFromDev);

			contentFromLive.forEach((key, value) -> {
				String textFromLive = contentFromLive.get(key).trim();
				// System.out.println(key + " on live ---" + textFromLive);
				String textFromDev = contentFromDev.get(key).trim();
				// System.out.println(key + " on dev ---" + textFromDev);
				if (key.equalsIgnoreCase("articleBodyText")) {
					System.out.println(textFromLive.replaceAll("\\s", ""));
					System.out.println(textFromDev.replaceAll("\\s", ""));
					List<String> diffLines = FileUtility.getDiffBetweenString(textFromLive.replaceAll("\\s", ""),
							textFromDev.replaceAll("\\s", ""));
					softAssert.assertTrue(diffLines.size() == 0, "<br>-The " + key
							+ " text on live is not matching with the " + key + " text on dev for url:" + url);
					System.out.println("checked article text body");
				} else {
					softAssert.assertTrue(textFromLive.equalsIgnoreCase(textFromDev),
							"<br>-The " + key + " text on live: " + textFromLive + " is not matching with the " + key
									+ " text on dev: " + textFromDev + " for url:" + url);
					System.out.println("checked " + key);
				}
			});
			softAssert.assertTrue(VerificationUtil.listActualInExpected(alsoReadLinksOnLive, alsoReadlinksOnDev),
					"<br>-The also read links are not matching from live to dev on url:" + url);
			softAssert.assertTrue(VerificationUtil.listActualInExpected(readMoreLinksOnLive, readMoreLinksOnDev),
					"<br>-The read more links are not matching from live to dev on url:" + url);
		});
		softAssert.assertAll();

	}

	@Test(description = "this test verifies the embeds in the articleshow on et dev with et live", enabled = true, priority = 1)
	public void checkArticleshowEmbeds() {
		softAssert = new SoftAssert();
		Map<String, Boolean> embedsMap = new HashMap();
		List<String> urls = getAllUrlListFromPanache();
		urls.forEach(url -> {

			// to check the content on live//
			WebBaseMethods.navigateToUrl(driver, url);
			WaitUtil.waitForLoad(driver);
			WaitUtil.sleep(5000);
			boolean youtubeFlag = compareArticleMethods.isYoutubeEmbedIsFoundOnLive();
			boolean slikeVideoFlag = compareArticleMethods.isSlikeVideoEmbedIsFoundOnLive();
			boolean facebookEmbedFlag = compareArticleMethods.isFaceBookEmbedFoundOnLive();
			boolean twitterEmbedFlag = compareArticleMethods.isTwitterEmbedFoundOnLive();
			boolean imageEmbedFlag = compareArticleMethods.isImagesrEmbedFoundOnLive();
			boolean instagramEmbedFlag = compareArticleMethods.isInstagramEmbedFoundOnLive();

			System.out.println(
					"youtube:" + youtubeFlag + " slikevideo: " + slikeVideoFlag + " facebook: " + facebookEmbedFlag
							+ " twitter: " + twitterEmbedFlag + " imageEmbed: " + imageEmbedFlag + " url:" + url);
			embedsMap.put("Youtube", youtubeFlag);
			embedsMap.put("SlikeVideo", slikeVideoFlag);
			embedsMap.put("Facebook", facebookEmbedFlag);
			embedsMap.put("Twitter", twitterEmbedFlag);
			embedsMap.put("ImagesEmbeded", imageEmbedFlag);
			embedsMap.put("Instagram", instagramEmbedFlag);
			if (embedsMap.containsValue(true)) {
				WebBaseMethods.scrollMultipleTimes(10, "bottom", 750);

				if (youtubeFlag) {
					youtubeEmebedOnLive = compareArticleMethods.youtubeEmbedDetails();
				}
				if (slikeVideoFlag) {
					slikeVideoEmebedOnLive = compareArticleMethods.slikeVideoEmbedDetails();
				}
				if (facebookEmbedFlag) {
					facebookEmbedOnLive = compareArticleMethods.facebookEmbedDetails();
				}
				if (twitterEmbedFlag) {
					twitterEmbedOnLive = compareArticleMethods.twitterEmbedDetails();
				}
				if (imageEmbedFlag) {

					imagesEmbededOnLive = compareArticleMethods.imagesEmbeddedDetails();
				}
				if (instagramEmbedFlag) {

					instagramEmbedsOnLive = compareArticleMethods.instagramEmbedDetails();
				}

				// to check the content on dev//

				WebBaseMethods.navigateToUrl(driver, compareArticleMethods.getDevLink(url));
				WaitUtil.waitForLoad(driver);
				WebBaseMethods.scrollMultipleTimes(10, "bottom", 750);
				// webBaseMethods.scrollToBottom();
				WaitUtil.sleep(2000);
				if (youtubeFlag) {
					youtubeEmebedOnDev = compareArticleMethods.youtubeEmbedDetails();
				}
				if (slikeVideoFlag) {
					slikeVideoEmebedOnDev = compareArticleMethods.slikeVideoEmbedDetails();
				}
				if (facebookEmbedFlag) {
					facebookEmbedOnDev = compareArticleMethods.facebookEmbedDetails();
				}
				if (twitterEmbedFlag) {
					twitterEmbedOnDev = compareArticleMethods.twitterEmbedDetails();
				}
				if (imageEmbedFlag) {

					imagesEmbededOnDev = compareArticleMethods.imagesEmbeddedDetails();
				}
				if (instagramEmbedFlag) {

					instagramEmbedsOnDev = compareArticleMethods.instagramEmbedDetails();
				}

				verifyTheEmbeds(softAssert, embedsMap, url);

			}
		});
		softAssert.assertAll();

	}

	@Test(description = "", enabled = false)
	public void checkDevArticle() {
		WebBaseMethods.navigateToUrl(driver, "https://etdev8243.indiatimes.com/panache/articleshow_new/74638236.cms");
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(6000);
		// WebBaseMethods.scrollToBottom();
		Map<String, String> contentFromDev = articleshow_NewPageMethods.getArticleAllElements();
		List<String> alsoReadlinksOnDev = articleshow_NewPageMethods.getArticleAlsoReadLinks();
		List<String> readMoreLinksOnDev = articleshow_NewPageMethods.getArticleReadMoreLinks();

		System.out.println("content from dev" + contentFromDev);
		System.out.println("also read on dev" + alsoReadlinksOnDev);

		System.out.println("Read more on on dev" + readMoreLinksOnDev);

	}

	public SoftAssert verifyTheEmbeds(SoftAssert softAssert, Map<String, Boolean> embedsMap, String url) {
		try {
			if (embedsMap.get("Youtube") == true) {
				youtubeEmebedOnLive.forEach((key, value) -> {
					String textFromLive = youtubeEmebedOnLive.get(key).trim();
					// System.out.println(key + " on live ---" + textFromLive);
					String textFromDev = youtubeEmebedOnDev.get(key).trim();
					// System.out.println(key + " on dev ---" + textFromDev);

					softAssert.assertTrue(textFromLive.equalsIgnoreCase(textFromDev),
							"<br>-The " + key + " value for youtube embed on live is not matching with the " + key
									+ " value for youtube embed on dev for url: " + url);

				});

			}
			if (embedsMap.get("SlikeVideo") == true) {
				slikeVideoEmebedOnLive.forEach((key, value) -> {
					String textFromLive = slikeVideoEmebedOnLive.get(key).trim();
					// System.out.println(key + " on live ---" + textFromLive);
					String textFromDev = slikeVideoEmebedOnDev.get(key).trim();
					// System.out.println(key + " on dev ---" + textFromDev);

					softAssert.assertTrue(textFromLive.equalsIgnoreCase(textFromDev),
							"<br>-The " + key + " value: " + textFromLive
									+ " for slikevideo embed on live is not matching with the " + key + " value: "
									+ textFromDev + " for slikevideo embed on dev for url: " + url);

				});

			}
			if (embedsMap.get("Facebook") == true) {
				facebookEmbedOnLive.forEach((key, value) -> {
					String textFromLive = facebookEmbedOnLive.get(key).trim();
					// System.out.println(key + " on live ---" + textFromLive);
					String textFromDev = facebookEmbedOnDev.get(key).trim();
					// System.out.println(key + " on dev ---" + textFromDev);

					softAssert.assertTrue(textFromLive.equalsIgnoreCase(textFromDev),
							"<br>-The " + key + " value: " + textFromLive
									+ " for facebook embed on live is not matching with the " + key + " value: "
									+ textFromDev + " for facebook embed on dev for url: " + url);

				});

			}

			if (embedsMap.get("Twitter") == true) {
				twitterEmbedOnLive.forEach((key, value) -> {
					String textFromLive = twitterEmbedOnLive.get(key).trim();
					// System.out.println(key + " on live ---" + textFromLive);
					String textFromDev = twitterEmbedOnDev.get(key).trim();
					// System.out.println(key + " on dev ---" + textFromDev);

					softAssert.assertTrue(textFromLive.equalsIgnoreCase(textFromDev),
							"<br>-The " + key + " value: " + textFromLive
									+ " for Twitter embed on live is not matching with the " + key + " value: "
									+ textFromDev + " for Twitter embed on dev for url: " + url);

				});

			}

			if (embedsMap.get("ImagesEmbeded") == true) {
				imagesEmbededOnLive.forEach((key, value) -> {
					String textFromLive = imagesEmbededOnLive.get(key).trim();
					// System.out.println(key + " on live ---" + textFromLive);
					String textFromDev = imagesEmbededOnDev.get(key).replace(",quality-100", "").trim();
					// System.out.println(key + " on dev ---" + textFromDev);

					softAssert.assertTrue(textFromLive.equalsIgnoreCase(textFromDev),
							"<br>-The " + key + " value: " + textFromLive
									+ " for images embed on live is not matching with the " + key + " value: "
									+ textFromDev + " for images embed on dev for url: " + url);

				});

			}
			if (embedsMap.get("Instagram") == true) {
				instagramEmbedsOnLive.forEach((key, value) -> {
					String textFromLive = instagramEmbedsOnLive.get(key).trim();
					// System.out.println(key + " on live ---" + textFromLive);
					String textFromDev = instagramEmbedsOnDev.get(key).trim();
					// System.out.println(key + " on dev ---" + textFromDev);

					softAssert.assertTrue(textFromLive.equalsIgnoreCase(textFromDev),
							"<br>-The " + key + " value: " + textFromLive
									+ " for instagram embed on live is not matching with the " + key + " value: "
									+ textFromDev + " for instagram embed on dev for url: " + url);

				});

			}
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "Embeds are not loaded properly");
		}
		return softAssert;

	}

	public List<String> getAllUrlList() {
		List<String> urlList = new LinkedList<String>();
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(10000);
		try {
			urlList = homePageMethods.getArticleshowLinksFromHomePage();

		} catch (Exception ee) {

			ee.printStackTrace();
		}

		return urlList;
	}

	public List<String> getAllUrlListFromPanache() {
		List<String> urlList = new LinkedList<String>();
		driver.get("https://economictimes.indiatimes.com/magazines/panache/entertainment");
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(10000);
		try {
			urlList = homePageMethods.getArticleshowLinksFromHomePage();

		} catch (Exception ee) {

			ee.printStackTrace();
		}

		return urlList;
	}

}
