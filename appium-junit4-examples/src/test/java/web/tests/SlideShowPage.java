package web.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
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
import web.pagemethods.AdTechMethods;
import web.pagemethods.HomePageMethods;
import web.pagemethods.SlideShowPageMethods;
import web.pagemethods.WebBaseMethods;

public class SlideShowPage extends BaseTest {
	private String baseUrl;
	private String slideshowUrl;
	HomePageMethods homePageMethods;
	SlideShowPageMethods slideshowPageMethods;
	AdTechMethods adTechMethods;
	boolean iterationFlag;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		slideshowUrl = "https://economictimes.indiatimes.com/slide-shows";
		iterationFlag = Boolean.parseBoolean(Config.fetchConfigProperty("SlideShowAllSections"));
		homePageMethods = new HomePageMethods(driver);
		slideshowPageMethods = new SlideShowPageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
	}

	@Deprecated
	@Test(enabled = false, description = "This test verifies slideshow play and pause ")
	public void slideShowPlayAndPause() throws InterruptedException {
		softAssert = new SoftAssert();
		slideshowPageMethods.openFirstSlide();
		WaitUtil.waitForLoad(driver);
		Assert.assertTrue(slideshowPageMethods.veifyPlayShowFromMain(),
				"Play Slide Show is not working from Main Play Button");
		Assert.assertTrue(slideshowPageMethods.verifyPauseSlideShowFromMain(),
				"Slide Show is not stoped from Main Pause Button");
		Assert.assertTrue(slideshowPageMethods.verifyPlaySlideShowFromLeftTop(),
				"Play Slide Show is not working from Left Top Play Button");
		Assert.assertTrue(slideshowPageMethods.verifyPauseSlideShowFromLeftTop(),
				"Slide Show is not stoped from Left Top Pause Button");
		Assert.assertTrue(slideshowPageMethods.verifyPlaySlideShowFromRightTop(),
				"Play Slide Show is not working from Right Top Play Button");
		Assert.assertTrue(slideshowPageMethods.verifyPauseSlideShowFromRightTop(),
				"Slide Show is not stoped from Right Top Pause Button");

		softAssert.assertAll();
	}

	@Deprecated
	@Test(enabled = false, description = "This test verifies slideshow next and previous slide ")
	public void verifySlideShowNextAndPrevious() throws InterruptedException {
		softAssert = new SoftAssert();
		slideshowPageMethods.openFirstSlide();
		WaitUtil.waitForLoad(driver);
		Assert.assertTrue(slideshowPageMethods.verifyNextSlideShowFromMain(),
				"Next Slide Show is not working from Next Main Button");
		Assert.assertTrue(slideshowPageMethods.verifyPreviousSlideShowFromMain(),
				"Previous Slide Show is not working from Previous Main Button");
		Assert.assertTrue(slideshowPageMethods.verifyNextSlideShowFromTopRight(),
				"Next Slide Show is not working from Next Top Right Button");
		Assert.assertTrue(slideshowPageMethods.verifyPreviousSlideShowFromTopRight(),
				"Previous Slide Show is not working from Previous Top Right Button");
		Assert.assertTrue(slideshowPageMethods.verifyNextSlideShowFromSlide(),
				"Next Slide Show is not working from Slide Button");
		Assert.assertTrue(slideshowPageMethods.verifyPreviousSlideShowFromSlide(),
				"Previous Slide Show is not working from Previous Slide Button");

		softAssert.assertAll();
	}

	@Deprecated
	@Test(enabled = false, description = "This test verifies slideshow from paging bar ")
	public void verifySlidePagingBar() throws InterruptedException {
		softAssert = new SoftAssert();
		slideshowPageMethods.openFirstSlide();
		WaitUtil.waitForLoad(driver);
		Assert.assertTrue(slideshowPageMethods.verifyNextSlideShowFromPagingBar(),
				"Next Slide Show is not working from Next Paging Bar Button");
		Assert.assertTrue(slideshowPageMethods.verifyPreviousSlideShowFromPagingBar(),
				"Previous Slide Show is not working from Previous Paging Bar Button");
		Assert.assertTrue(slideshowPageMethods.verifyChangingSlideShowFromPagingBar(),
				"Slide Show is not working for Paging Button");

		softAssert.assertAll();
	}

	@Deprecated
	@Test(enabled = false, description = "This test verifies slideshow speed ")
	public void verifySlideShowSpeed() throws InterruptedException {
		softAssert = new SoftAssert();
		slideshowPageMethods.openFirstSlide();
		WaitUtil.waitForLoad(driver);
		Assert.assertTrue(slideshowPageMethods.verifySlideAtMediumSpeed(),
				"Slide Show is not working with Medium Speed");
		Assert.assertTrue(slideshowPageMethods.verifySlideAtSlowSpeed(), "Slide Show is not working with Slow Speed");
		Assert.assertTrue(slideshowPageMethods.verifySlideAtFastSpeed(), "Slide Show is not working with Fast Speed");

		softAssert.assertAll();
	}

	@Deprecated
	@Test(enabled = false, description = "This test verifies slideshow showing error page ")
	public void verifyAllSlideshowSections() {
		WebBaseMethods.navigateTimeOutHandle(baseUrl);
		softAssert = new SoftAssert();
		// ArrayList<String> errorPage = new ArrayList<>();
		for (String s : slideshowPageMethods.getSlideshowTabList()) {
			WebBaseMethods.navigateTimeOutHandle(s);
			slideshowPageMethods.getAllSlideshowLinks().forEach(href -> {
				int code = HTTPResponse.checkResponseCode(href);
				softAssert.assertEquals(code, 200, "<br>" + href + " is throwing " + code);
			});
		}

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the number of slides present in slideshow ")
	public void verifyVerticalSlideshow() {
		
		WebBaseMethods.navigateTimeOutHandle(slideshowUrl);
		softAssert = new SoftAssert();
		List<String> li = slideshowPageMethods.getSlideshowTabList();
		Assert.assertTrue(!li.isEmpty(), " Slideshow tabs are not shown");
		int temp = iterationFlag ? li.size() : (li.size() / 4);
		for (int i = 0; i < temp; i++) {
			// System.out.println(li.get(i));
			WebBaseMethods.navigateTimeOutHandle(li.get(i));
			WaitUtil.sleep(2000);
			Assert.assertTrue(slideshowPageMethods.clickFirstSlideShow(), "Unable to click first slide show");
			String slideUrl = driver.getCurrentUrl();
			int respCode = HTTPResponse.checkResponseCode(slideUrl);
			softAssert.assertTrue(slideshowPageMethods.getSlidesCount() > 0,
					"<br>-No slides found on " + slideUrl + ", page response code is " + respCode);
			boolean flag = slideshowPageMethods.verifySlideshowCount();
			softAssert.assertTrue(flag, "<br>Slides on " + slideUrl + " are not equal to expected number");
		}

		softAssert.assertAll();
	}

	@Test(description = "This test verifies response code of the links present")
	public void verifyLinksOnPage() {
		softAssert = new SoftAssert();
		List<WebElement> slideshowLinks = driver.findElements(By.xpath("//a[contains(@href,'slideshow')]"));
		List<String> hrefLinks = VerificationUtil.getLinkHrefList(slideshowLinks);
		hrefLinks.forEach(href -> {
			if (href.contains("economictimes")) {
				int tempCode = HTTPResponse.checkResponseCode(href);
				softAssert.assertTrue(tempCode == 200, "<a href =" + href + ">" + href.substring(0, href.length() / 2)
						+ "...</a> is throwing " + tempCode);
			}
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies that slideshow newsletter is present on third slide or not", enabled = false)
	public void verifySlideshowNewsletter() {
		WebBaseMethods.navigateTimeOutHandle(slideshowUrl);
		softAssert = new SoftAssert();
		boolean flag = false;
		int temp = 0;
		List<String> li = slideshowPageMethods.getSlideshowTabList();
		Assert.assertTrue(!li.isEmpty(), " Slideshow tabs are not shown");
		temp = iterationFlag ? li.size() : (li.size() / 4);
		for (int i = 0; i < temp; i++) {
			WebBaseMethods.navigateTimeOutHandle(li.get(i));
			Assert.assertTrue(slideshowPageMethods.clickFirstSlideShow(), "Unable to click first slide show");
			String slideUrl = driver.getCurrentUrl();
			int respCode = HTTPResponse.checkResponseCode(slideUrl);
			softAssert.assertTrue(slideshowPageMethods.getSlidesCount() > 0,
					"<br>-No slides found on " + slideUrl + ", page response code is " + respCode);
			flag = slideshowPageMethods.verifySlideshowNewsletter();
			softAssert.assertTrue(flag,
					"<br>Slideshow doesn't show newsletter subscription widget after 3rd slide of slideshow on  "
							+ slideUrl);

		}

		softAssert.assertAll();
	}

	@Test(description = "This test verifies that next slideshow is shown on the page on scrolling down", enabled = false)
	public void verifyNextSlideshow() {
		WebBaseMethods.navigateTimeOutHandle(slideshowUrl);
		softAssert = new SoftAssert();
		boolean flag = false;
		int temp = 0;
		List<String> li = slideshowPageMethods.getSlideshowTabList();
		Assert.assertTrue(!li.isEmpty(), " Slideshow tabs are not shown");
		temp = iterationFlag ? li.size() : (li.size() / 4);
		for (int i = 0; i < temp; i++) {
			WebBaseMethods.navigateTimeOutHandle(li.get(i));
			Assert.assertTrue(slideshowPageMethods.clickFirstSlideShow(), "Unable to click first slide show");
			String slideUrl = driver.getCurrentUrl();
			int respCode = HTTPResponse.checkResponseCode(slideUrl);
			softAssert.assertTrue(slideshowPageMethods.getSlidesCount() > 0,
					"<br>-No slides found on " + slideUrl + ", page response code is " + respCode);
			flag = slideshowPageMethods.verifyNextSlideshow();
			softAssert.assertTrue(flag, "<br>-Next slide show is not shown on  " + slideUrl);
		}
		softAssert.assertAll();
	}

	@Test(description = " This test verifies the ads on slide show page")
	public void verifyAds() {
		WebBaseMethods.navigateTimeOutHandle(slideshowUrl);
		softAssert = new SoftAssert();
		int temp = 0;
		List<String> li = slideshowPageMethods.getSlideshowTabList();
		Assert.assertTrue(!li.isEmpty(), " Slideshow tabs are not shown");
		temp = iterationFlag ? li.size() : (li.size() / 4);
		for (int i = 0; i < temp; i++) {
			WebBaseMethods.navigateTimeOutHandle(li.get(i));
			Assert.assertTrue(slideshowPageMethods.clickFirstSlideShow(), "Unable to click first slide show");
			String slideUrl = driver.getCurrentUrl();
			if (slideUrl.contains("slideshow")) {
				WaitUtil.sleep(2000);
				Map<String, String> expectedIdsMap = Config.getAllKeys("adIdLocation");
				softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0,
						"No google ads shown for slide <a href='" + slideUrl + "'>" + slideUrl + "</a>");
				if (adTechMethods.getDisplayedAdIds().size() > 0) {
					expectedIdsMap.forEach((K, V) -> {
						if (K.contains("_SS_")) {
							softAssert.assertTrue(adTechMethods.matchIdsWithKey(K),
									"Following ad(s) is/are not shown " + expectedIdsMap.get(K)
											+ " for slideshow url <a href='" + slideUrl + "'>" + slideUrl + "</a>");
						}
					});
				}
				List<String> ctnAd = adTechMethods.getMissingColumbiaAds();
				softAssert.assertFalse(ctnAd.size() > 0, "Following colombia ad(s) is/are not shown " + ctnAd
						+ " for slideshow url <a href='" + slideUrl + "'>" + slideUrl + "</a>");
			}
		}
		softAssert.assertAll();
	}

}
