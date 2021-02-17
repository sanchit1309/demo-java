package com.web.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
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
import web.pagemethods.CommonL1L2PagesMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.PoliticsNationMethods;
import web.pagemethods.WebBaseMethods;

public class PoliticsPage extends BaseTest {
	PoliticsNationMethods politicsNationMethods;
	HeaderPageMethods headerPageMethods;
	private AdTechMethods adTechMethods;
	CommonL1L2PagesMethods commonL1L2PagesMethods;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		String pageUrl = BaseTest.baseUrl+Config.fetchConfigProperty("PoliticsNationUrl");
		launchBrowser(pageUrl);
		politicsNationMethods = new PoliticsNationMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		commonL1L2PagesMethods = new CommonL1L2PagesMethods(driver);
		WebBaseMethods.scrollToBottom();
	}

	@Test(description = " This test verifies the ads on Politics Listing Page")
	public void verifyAds() {
		softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		Map<String, String> expectedIdsMap = Config.getAllKeys("adIdLocation");
		softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size()>0, "No google ads shown on Politics Listing Page.");
		if(adTechMethods.getDisplayedAdIds().size()>0) {
			expectedIdsMap.forEach((K,V) -> {
				if(K.contains("ET_ROS") && !( K.contains("_AS_"))){
				softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K), "Following ad(s) is/are not shown " + expectedIdsMap.get(K)+ " on Politics Listing Page.");
				}
			});
		}
		List<String> ctnAd = adTechMethods.getMissingColumbiaAds();
		softAssert.assertFalse(ctnAd.size()>0, "Following colombia ad(s) is/are not shown "+ ctnAd+ " on Politics Listing Page.");			
		softAssert.assertAll();
	}
	
	@Test(description = "This test verifies top news section")
	public void verifyTopNewsSection() {
		int latestNews = 10;
		softAssert = new SoftAssert();
		List<String> featuredHrefList = politicsNationMethods.getTopNewsHref();
		softAssert.assertTrue(featuredHrefList.size() >= latestNews, "<br>- Less than "+latestNews+" articles in the top news section,actual count "+featuredHrefList.size());
		featuredHrefList.forEach(href->{
			int responseCode=HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(responseCode,200,href+" is throwing "+responseCode);
		});
		List<String> topNewsDup = VerificationUtil.isListUnique(featuredHrefList);
		softAssert.assertTrue(topNewsDup.isEmpty(), "<br>- Top news is having duplicate stories, repeating story(s)->" + topNewsDup);
		softAssert.assertAll();
	}
	@Test(description = "This test verifies thumb image section")
	public void verifyThumbImageSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		List<String> thumbImageHrefList = politicsNationMethods.getThumbImageHref();
		softAssert.assertTrue(thumbImageHrefList.size() >= articleCount, "<br>- Less than "+articleCount+" articles in the thumb image news section");
		thumbImageHrefList.forEach(href->{
			int responseCode=HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(responseCode,200,href+" is throwing "+responseCode);
		});
		List<String> thumbImageDup = VerificationUtil.isListUnique(thumbImageHrefList);
		softAssert.assertTrue(thumbImageDup.isEmpty(), "<br>- Thumb image section is having duplicate stories, repeating story(s)->" + thumbImageDup);
		softAssert.assertAll();
	}
	@Test(description="This test verifies voices section")
	public void verifyVoicesSection(){
		int voicesCount=10;
		softAssert=new SoftAssert();
		List<String> voicesSectionHref=politicsNationMethods.getVoicesHref();
		softAssert.assertTrue(voicesSectionHref.size() >= voicesCount, "<br>- Less than "+voicesCount+" articles in the voices section, actual count "+voicesSectionHref.size() );
		voicesSectionHref.forEach(href->{
			int responseCode=HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(responseCode,200,href+" is throwing "+responseCode);
		});
		List<String> voicesDup = VerificationUtil.isListUnique(voicesSectionHref);
		softAssert.assertTrue(voicesDup.isEmpty(), "<br>- Thumb image section is having duplicate stories, repeating story(s)->" + voicesDup);
		List<String> displayedNewsBefore=politicsNationMethods.getVoicesDisplayed();
		politicsNationMethods.clickVoicesFirst();
		List<String> displayedNewsAfter=politicsNationMethods.getVoicesDisplayed();
		softAssert.assertFalse(displayedNewsBefore.equals(displayedNewsAfter),"Slides in voices section did not change after clicking next");
		softAssert.assertAll();
	}
	
	@Test(description="This test verifies not to be missed section")
	public void verifyNotToBeMissedSection(){
		int articleCount=10;
		softAssert=new SoftAssert();
		List<String> notMissedHref=politicsNationMethods.getNotToBeMissedHref();
		softAssert.assertTrue(notMissedHref.size()>=articleCount,"Article list is less than "+articleCount+", actual count "+notMissedHref.size());;
		notMissedHref.forEach(href->{
			int responseCode=HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(responseCode,200,href+" is throwing "+responseCode);
		});
		List<String> storyList=politicsNationMethods.getNewsNotToMiss();
		List<String> notMissedDup = VerificationUtil.isListUnique(storyList);
		softAssert.assertTrue(notMissedDup.isEmpty(), "<br>- Not to be missed section is having duplicate stories, repeating story(s)->" + notMissedDup);
		softAssert.assertAll();
	}
	
	@Test(description="This test verifies news not to miss section",enabled=false)
	public void verifyNewsNotToMiss(){
		int articleCount=8;
		softAssert=new SoftAssert();
		List<String> notToMissHref=politicsNationMethods.getNewsNotToMiss();
		softAssert.assertTrue(notToMissHref.size()>=articleCount,"Article list is less than "+articleCount+" actual count "+notToMissHref.size());
		notToMissHref.forEach(href->{
			int responseCode=HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(responseCode,200,href+" is throwing "+responseCode);
		});
		List<String> notMissedDup = VerificationUtil.isListUnique(notToMissHref);
		softAssert.assertTrue(notMissedDup.isEmpty(), "<br>- News not to be missed section is having duplicate stories, repeating story(s)->" + notMissedDup);
		softAssert.assertAll();
	}
	@Test(description="This test verifies article list on news politics section")
	public void verifyArticleList(){
		int articleCount=40;
		softAssert=new SoftAssert();
		List<String> articleList=politicsNationMethods.getNewsList();
		softAssert.assertTrue(articleList.size()>=articleCount,"Article list is less than "+articleCount+" instead is "+articleList.size());
		articleList.forEach(href->{
			int responseCode=HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(responseCode,200,href+" is throwing "+responseCode);
		});
		List<String> articleListDup = VerificationUtil.isListUnique(politicsNationMethods.getNewsListStories());
		softAssert.assertTrue(articleListDup.isEmpty(), "<br>- Article list is having duplicate stories, repeating story(s)->" + articleListDup);
		softAssert.assertAll();
	}
	@Test(description = "This test verifies date and time shown on header on politics page")
	public void verifyDateTime(){
		softAssert = new SoftAssert();
		String dateTimeN = headerPageMethods.getDateTimeTab();

		DateTime systemDate = new DateTime();

		DateTime date = WebBaseMethods.convertDateMethod(dateTimeN.replaceAll("\\|", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\.",
				":"));

		Assert.assertTrue(dateTimeN != null, "Date time is not shown on header on politics page");
		softAssert.assertTrue(systemDate.getDayOfYear() == date.getDayOfYear(),
				"The date shown on politics page is not the current date instead is showing " + dateTimeN);
		softAssert.assertTrue((systemDate.getMinuteOfDay() - date.getMinuteOfDay() <= 20 && systemDate.getMinuteOfDay() - date.getMinuteOfDay() >= 0),
				"on politics  page the time shown " + dateTimeN + " is having difference of more than 20 mins from current time"+ systemDate.toString("MMM dd, yyyy hh:mm a"));
		softAssert.assertAll();
		
	}
	
	@Test(description="This test verifies videos in slider")
	public void verifyVideoSlider(){
		softAssert=new SoftAssert();
		int articleCount=5;
		List<WebElement>allVideoSlides=politicsNationMethods.getAllVideoSlides();
		List<String> allVideoHeadlines = WebBaseMethods.getListTextUsingJSE(allVideoSlides);
		softAssert.assertTrue(allVideoHeadlines.size() >= articleCount,
				"<br>- Headlines under Video section should be more than " + articleCount + " in number");
		List<String> videosHref = WebBaseMethods.getListHrefUsingJSE(allVideoSlides);
		videosHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Videos section is throwing " + response);
		});
		List<String> marketNewsDup = VerificationUtil.isListUnique(allVideoHeadlines);
		softAssert.assertTrue(marketNewsDup.isEmpty(),
				"<br>- Videos section is having duplicate stories, repeating story(s)->" + marketNewsDup);
		softAssert.assertAll();
	}
	

	@Test(description = "This test verifies the top trending widget on the Politics page")
	public void verifyTopTrendingWidgetInRhs() {
		softAssert = new SoftAssert();
		try {

			List<String> topTrendingWidgetLinks = commonL1L2PagesMethods.getTopTrendingLinksHref();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(topTrendingWidgetLinks);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(topTrendingWidgetLinks);
			softAssert.assertTrue(topTrendingWidgetLinks.size() >= 5,
					"The number of terms under the top trending widget should be more than equal to 5 but found: "
							+ topTrendingWidgetLinks.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the top trending terms widget. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the top trending widget in RHS: " + dupLinks);
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}
	
	
}
