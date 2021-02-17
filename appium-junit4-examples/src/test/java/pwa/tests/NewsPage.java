package pwa.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import pwa.pagemethods.HomePageMethods;
import pwa.pagemethods.MenuPageMethods;
import pwa.pagemethods.NewsPageMethods;
import pwa.pagemethods.PwaListingPageMethods;


public class NewsPage extends BaseTest{
	private String wapUrl;
	private NewsPageMethods newsPageMethods;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	String excelPath = "MenuSubMenuData";
	Map<String, String> testData;
	SoftAssert softAssert;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		newsPageMethods = new NewsPageMethods(driver);
		homePageMethods = new HomePageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyNewsTopNavigation", 1);
		Assert.assertTrue(openNewsSection(),"Unable to click news tab");
	}
	
	
	
	public boolean openNewsSection(){
		homePageMethods.clickMenuIcon();
		WaitUtil.sleep(2000);
		return menuPageMethods.clickMenuOption("News");
	}
	
	/*@Test(description="Verify News Page top scroll section", groups={"News Page"}, priority=1)
	public void verifyNewsTopSection(){
		softAssert = new SoftAssert();
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("NewsPageTopNavigation").split("\\s*,\\s*")));
		
		List<String> actualMenuItemList = newsPageMethods.getTopSectionHeaders();
		Assert.assertTrue(VerificationUtil.listActualInExpected( expectedMenuItemList,actualMenuItemList),
						"Actual list of menu items is not matching the expected list of menu items. Actual List " + actualMenuItemList + " Expected List "
								+ expectedMenuItemList);		
	}*/
	
	@Test(description="Verify Top news of News Page", groups = {"News Page"}, priority = 0)
	public void verifyNewsTopNews() {
		softAssert = new SoftAssert();
		int articleCount = 10;
		
		List<String> topNewsStories = VerificationUtil.getLinkHrefList(newsPageMethods.getTopSectionNews());
		int count = topNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under top section should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> topNewsDup = VerificationUtil.isListUnique(topNewsStories);
		softAssert.assertTrue(topNewsDup.isEmpty(), "<br>- Top News Section has duplicate stories, repeating story(s)->" + topNewsDup);
		newsPageMethods.getTopSectionNewsHref().forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top News link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}
	
	@Test(description ="Verify News page Company section", groups={"News Page"}, priority=2)
	public void verifyNewsCompanySection() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionDiv= "Company";
//		
//		String sectionLink = newsPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("company"), "<br>- Link under heading of the section is not of company");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> companyNewsStories = VerificationUtil.getLinkTextList(newsPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = companyNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under company sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> companyNewsDup = VerificationUtil.isListUnique(companyNewsStories);
		softAssert.assertTrue(companyNewsDup.isEmpty(), "<br>- Company Section has duplicate stories, repeating story(s)->" + companyNewsDup);
		newsPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Company link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}
	
	@Test(description ="Verify News page Economy section", groups={"News Page"}, priority=3)
	public void verifyNewsEconomySection() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionDiv= "Economy";
		
//		String sectionLink = newsPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("economy"), "<br>- Link under heading of the section is not of economy");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> economyNewsStories = VerificationUtil.getLinkTextList(newsPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = economyNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Economy sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> economyNewsDup = VerificationUtil.isListUnique(economyNewsStories);
		softAssert.assertTrue(economyNewsDup.isEmpty(), "<br>- Economy Section has duplicate stories, repeating story(s)->" + economyNewsDup);
		newsPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Economy link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}
	
	@Test(description ="Verify News page Politics and Nation section", groups={"News Page"}, priority=4)
	public void verifyNewsPoliticsNationSection() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionDiv= "Politics and Nation";
		
//		String sectionLink = newsPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("politicsnation"), "<br>- Link under heading of the section is not of politics and nation");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> politicsNationNewsStories = VerificationUtil.getLinkTextList(newsPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = politicsNationNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under politics and Nation sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> politicsNationNewsDup = VerificationUtil.isListUnique(politicsNationNewsStories);
		softAssert.assertTrue(politicsNationNewsDup.isEmpty(), "<br>- Politics and Nation Section has duplicate stories, repeating story(s)->" + politicsNationNewsDup);
		newsPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Poltics and Nation link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}
	
	
	@Test(description ="Verify News page Brandwire section", groups={"News Page"}, priority=5)
	public void verifyNewsBrandwireSection() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionDiv= "Brandwire";
		
//		String sectionLink = newsPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("brandwire"), "<br>- Link under heading of the section is not of Brandwire");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> brandwireNewsStories = VerificationUtil.getLinkTextList(newsPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = brandwireNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under brandwire sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> brandwireNewsDup = VerificationUtil.isListUnique(brandwireNewsStories);
		softAssert.assertTrue(brandwireNewsDup.isEmpty(), "<br>- Brandwire Section has duplicate stories, repeating story(s)->" + brandwireNewsDup);
		newsPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Brandwire link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}
	
	@Test(description="Verify News page Defence section", groups={"News Page"}, priority=6)
	public void verifyNewsDefenceSection(){
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionDiv= "Defence";
//		
//		String sectionLink = newsPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("defence"), "<br>- Link under heading of the section is not of Defence");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> defenceNewsStories = VerificationUtil.getLinkTextList(newsPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = defenceNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Defence sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> defenceNewsDup = VerificationUtil.isListUnique(defenceNewsStories);
		softAssert.assertTrue(defenceNewsDup.isEmpty(), "<br>- Defence Section has duplicate stories, repeating story(s)->" + defenceNewsDup);
		newsPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Defence link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}
	
	@Test(description="Verify News page International section", groups={"News Page"}, priority=7)
	public void verifyNewsInternationalSection(){
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionDiv= "International";
//		
//		String sectionLink = newsPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("international"), "<br>- Link under heading of the section is not of International");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> internationalNewsStories = VerificationUtil.getLinkTextList(newsPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = internationalNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under international section should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> internationalNewsDup = VerificationUtil.isListUnique(internationalNewsStories);
		softAssert.assertTrue(internationalNewsDup.isEmpty(), "<br>- International Section has duplicate stories, repeating story(s)->" + internationalNewsDup);
		newsPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top International link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}
	
	@Test(description="Verify News page India Unlimited section", groups={"News Page"}, priority=8)
	public void verifyNewsServicesSection(){
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionDiv= "Services";
//		
//		String sectionLink = newsPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("india-unlimited"), "<br>- Link under heading of the section is not of India Unlimited");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> indiaUnlimitedNewsStories = VerificationUtil.getLinkTextList(newsPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = indiaUnlimitedNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under India Unlimited sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> indiaUnlimitedNewsDup = VerificationUtil.isListUnique(indiaUnlimitedNewsStories);
		softAssert.assertTrue(indiaUnlimitedNewsDup.isEmpty(), "<br>- India Unlimited Section has duplicate stories, repeating story(s)->" + indiaUnlimitedNewsDup);
		newsPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top India Unlimited link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}
	
	@Test(description="Verify News page Sports section", groups={"News Page"}, priority=9)
	public void verifyNewsSportsSection(){
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionDiv= "Sports";
//		
//		String sectionLink = newsPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("sports"), "<br>- Link under heading of the section is not of Sports");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> sportsNewsStories = VerificationUtil.getLinkTextList(newsPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = sportsNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Sports sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> sportsNewsDup = VerificationUtil.isListUnique(sportsNewsStories);
		softAssert.assertTrue(sportsNewsDup.isEmpty(), "<br>- Sports Section has duplicate stories, repeating story(s)->" + sportsNewsDup);
		newsPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Sports link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}
	
	@Test(description="Verify News page Science section", groups={"News Page"}, priority=10)
	public void verifyNewsScienceSection(){
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionDiv= "Science";
		
//		String sectionLink = newsPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("science"), "<br>- Link under heading of the section is not of Science");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> scienceNewsStories = VerificationUtil.getLinkTextList(newsPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = scienceNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Science sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> scienceNewsDup = VerificationUtil.isListUnique(scienceNewsStories);
		softAssert.assertTrue(scienceNewsDup.isEmpty(), "<br>- Science Section has duplicate stories, repeating story(s)->" + scienceNewsDup);
		newsPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Science link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}
		
	
	@Test(description="Verify News page Environment section", groups={"News Page"}, priority=11, enabled = true)
	public void verifyNewsEnvironmentSection(){
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionDiv= "Environment";
//		
//		String sectionLink = newsPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("environment"), "<br>- Link under heading of the section is not of Environment");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> envNewsStories = VerificationUtil.getLinkTextList(newsPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = envNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Environment sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> envNewsDup = VerificationUtil.isListUnique(envNewsStories);
		softAssert.assertTrue(envNewsDup.isEmpty(), "<br>- Environment Section has duplicate stories, repeating story(s)->" + envNewsDup);
		newsPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Environment link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}
	
	@Test(description="Verify recency of articles",groups={"News Page"}, priority=12, enabled=true)
	public void verifyTopNewsRecency(){
		softAssert = new SoftAssert();
		
		Map<String, Boolean> headlineRecency = new PwaListingPageMethods(driver).checkRecency(1);
		for (Map.Entry<String, Boolean> entry : headlineRecency.entrySet()) {
			softAssert.assertTrue(entry.getValue(),"Article Recency verification for " + entry.getKey());
		}
		softAssert.assertAll();
	}
	
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
