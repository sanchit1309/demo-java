package web.tests;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import web.pagemethods.BudgetPageMethods;
import web.pagemethods.ETSharedMethods;
import web.pagemethods.WebBaseMethods;

public class BudgetPage extends BaseTest {

	private String budgetUrl;
	BudgetPageMethods budgetPageMethods;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		budgetUrl = BaseTest.baseUrl + Config.fetchConfigProperty("BudgetUrl");
		launchBrowser(budgetUrl);
		budgetPageMethods = new BudgetPageMethods(driver);
		WebBaseMethods.scrollToBottom();
	}

	@Test(description = "This test verifies Budget headlines section on Budget page", enabled = true)
	public void verifyBudgetHeadlinesSection() {
		int articleCount = 24;
		softAssert = new SoftAssert();
		List<WebElement> BudgetHeadlines = budgetPageMethods.getBudgetHeadlines();
		List<String> BudgetStories = VerificationUtil.getLinkTextList(budgetPageMethods.getBudgetHeadlines());
		softAssert.assertTrue(BudgetStories.size() >= articleCount,
				"<br>- Headlines under Budget headlines sections should be more than " + articleCount
						+ " in number found:" + BudgetStories.size());
		List<String> BudgetStoriesHref = WebBaseMethods.getListHrefUsingJSE(BudgetHeadlines);
		BudgetStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Budget headlines section is throwing " + response);
		});
		List<String> BudgetNewsDup = VerificationUtil.isListUnique(BudgetStories);
		softAssert.assertTrue(BudgetNewsDup.isEmpty(),
				"<br>- Budget headlines is having duplicate stories, repeating story(s)->" + BudgetNewsDup);

		softAssert.assertAll();
	}

	@Test(description = "this test verifies the economy section on budget page", enabled = true)
	public void verifyEconomySection() {
		int articleCount = 3;
		softAssert = new SoftAssert();
		String sectionLink = budgetPageMethods.getEconomyLink();
		String moreFromSectionLink = budgetPageMethods.getMoreFromSectionLink("Economy");
		int moreFromResponseCode = HTTPResponse.checkResponseCode(moreFromSectionLink);
		softAssert.assertEquals(moreFromResponseCode, 200, "<br>- <a href=" + moreFromSectionLink
				+ ">Link under heading </a> is throwing " + moreFromResponseCode);

		softAssert.assertTrue(sectionLink.contains("economy") && moreFromSectionLink.contains("economy"),
				"<br>- Link under heading of the section is not of Economy");

		int responseCode = HTTPResponse.checkResponseCode(sectionLink);

		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> economyHeadlines = budgetPageMethods.getEconomyHeadlines();
		List<String> economyStories = VerificationUtil.getLinkTextList(budgetPageMethods.getEconomyHeadlines());
		System.out.println(economyStories);
		softAssert.assertTrue(economyStories.size() >= articleCount,
				"<br>- Headlines under economy sections should be more than " + articleCount + " in number found:"
						+ economyStories.size());
		List<String> economyStoriesHref = WebBaseMethods.getListHrefUsingJSE(economyHeadlines);
		economyStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in economy section is throwing " + response);
		});
		List<String> economyNewsDup = VerificationUtil.isListUnique(economyStories);
		softAssert.assertTrue(economyNewsDup.isEmpty(),
				"<br>- economy is having duplicate stories, repeating story(s)->" + economyNewsDup);

		softAssert.assertAll();

	}

	@Test(description = "This test verifies Power Stocks auto slider section on homepage ", enabled = true)
	public void verifyPowerStocksSection() {
		int powerStocks = 15;
		softAssert = new SoftAssert();

		List<String> powerStocksStories = budgetPageMethods
				.getSliderHeadlines(budgetPageMethods.getBudgetPageObjects().getPowerStocksLink());
		System.out.println(powerStocksStories);
		Assert.assertTrue(powerStocksStories.size() > 0, "No stories are shown in Power Stocks section");
		softAssert.assertTrue(powerStocksStories.size() >= powerStocks,
				"<br>- Expected story count: " + powerStocks + " found: " + powerStocksStories.size());
		List<String> powerStocksDup = VerificationUtil.isListUnique(powerStocksStories);
		softAssert.assertTrue(powerStocksDup.isEmpty(),
				"<br>- Power Stocks is having duplicate stories, repeating story(s)->" + powerStocksDup);
		List<String> powerStocksStoriesHref = WebBaseMethods
				.getListHrefUsingJSE(budgetPageMethods.getBudgetPageObjects().getPowerStocksLink());
		powerStocksStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Power stocks section is throwing " + response);
		});

		List<String> beforeClickHeadlines = ETSharedMethods
				.getDisplayedElementsList(budgetPageMethods.getBudgetPageObjects().getPowerStocksLink());
		budgetPageMethods.clickNonActiveCarouselTab(
				budgetPageMethods.getBudgetPageObjects().getNonActivePowerStocksCarouselTab());
		List<String> afterClickHeadlines = ETSharedMethods
				.getDisplayedElementsList(budgetPageMethods.getBudgetPageObjects().getPowerStocksLink());
		softAssert.assertFalse(VerificationUtil.areListsEqual(beforeClickHeadlines, afterClickHeadlines),
				"Stories did not change after clicking next");
		softAssert.assertAll();
	}

	@Test(description = "This test Verifies the indutry impact tab on budget page", enabled = true)
	public void verifyIndutryImpactTab() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		List<WebElement> li = budgetPageMethods.getIndustryImpactTabs();
		li.forEach(tab -> {
			String sectionName = tab.getText();
			System.out.println(sectionName);
			WebElement we = budgetPageMethods.getMoreFromIndustryTabLink();
			String sectionLinkName = sectionName.toLowerCase();
			List<WebElement> storyList = budgetPageMethods.getIndustryTabStories();
			WebBaseMethods.clickElementUsingJSE(tab);
			verifySectionDuplicateStories(storyList, sectionName, articleCount);
			verifySectionLinkResponse(sectionName, storyList);
			verifyMoreFromSectionLink(we, sectionName, sectionLinkName);

		});

	}

	@Test(description = "This test verifies Expert Views section on Budget page", enabled = true)
	public void verifyExpertViewsSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();
		String sectionLink = budgetPageMethods.getExpertViewsLink();
		String moreFromSectionLink = budgetPageMethods.getMoreFromSectionLink("Expert Views");
		int moreFromResponseCode = HTTPResponse.checkResponseCode(moreFromSectionLink);
		softAssert.assertEquals(moreFromResponseCode, 200, "<br>- <a href=" + moreFromSectionLink
				+ ">Link under heading </a> is throwing " + moreFromResponseCode);

		softAssert.assertTrue(
				sectionLink.contains("markets-expert-views") && moreFromSectionLink.contains("markets-expert-views"),
				"<br>- Link under heading of the section is not of Expert Views");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> ExpertViewsHeadlines = budgetPageMethods.getExpertViewsHeadlines();
		List<String> expertViewsStories = VerificationUtil.getLinkTextList(budgetPageMethods.getExpertViewsHeadlines());
		System.out.println(expertViewsStories);
		softAssert.assertTrue(expertViewsStories.size() >= articleCount,
				"<br>- Headlines under Expert Views sections should be more than " + articleCount + " in number found:"
						+ expertViewsStories.size());
		List<String> ExpertViewsStoriesHref = WebBaseMethods.getListHrefUsingJSE(ExpertViewsHeadlines);
		ExpertViewsStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Expert Views section is throwing " + response);
		});
		List<String> ExpertViewsNewsDup = VerificationUtil.isListUnique(expertViewsStories);
		softAssert.assertTrue(ExpertViewsNewsDup.isEmpty(),
				"<br>- Expert Views is having duplicate stories, repeating story(s)->" + ExpertViewsNewsDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies NewsSection section on Budget page", enabled = true)
	public void verifyNewsSectionSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();
		String sectionLink = budgetPageMethods.getNewsSectionLink();
		System.out.println(sectionLink);
		String moreFromSectionLink = budgetPageMethods.getMoreFromSectionLink("More from News");
		System.out.println(moreFromSectionLink);
		int moreFromResponseCode = HTTPResponse.checkResponseCode(moreFromSectionLink);
		softAssert.assertEquals(moreFromResponseCode, 200, "<br>- <a href=" + moreFromSectionLink
				+ ">Link under heading </a> is throwing " + moreFromResponseCode);

		softAssert.assertTrue(sectionLink.contains("markets-news") && moreFromSectionLink.contains("markets-news"),
				"<br>- Link under heading of the section is not of News Section");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> NewsSectionHeadlines = budgetPageMethods.getNewsSectionHeadlines();
		List<String> NewsSectionStories = VerificationUtil.getLinkTextList(budgetPageMethods.getNewsSectionHeadlines());
		softAssert.assertTrue(NewsSectionStories.size() >= articleCount,
				"<br>- Headlines under NewsSection sections should be more than " + articleCount + " in number found:"
						+ NewsSectionStories.size());
		List<String> NewsSectionStoriesHref = WebBaseMethods.getListHrefUsingJSE(NewsSectionHeadlines);
		NewsSectionStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in News Section section is throwing " + response);
		});
		List<String> NewsSectionNewsDup = VerificationUtil.isListUnique(NewsSectionStories);
		softAssert.assertTrue(NewsSectionNewsDup.isEmpty(),
				"<br>- NewsSection is having duplicate stories, repeating story(s)->" + NewsSectionNewsDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Budget in Hindi section on Budget page", enabled = true)
	public void verifyBudgetInHindiSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();
		String sectionLink = budgetPageMethods.getHindiBudgetLink();
		String moreFromSectionLink = budgetPageMethods.getMoreFromSectionLink("बजट");
		int moreFromResponseCode = HTTPResponse.checkResponseCode(moreFromSectionLink);
		softAssert.assertEquals(moreFromResponseCode, 200, "<br>- <a href=" + moreFromSectionLink
				+ ">Link under heading </a> is throwing " + moreFromResponseCode);

		softAssert.assertTrue(sectionLink.contains("hindi") && moreFromSectionLink.contains("hindi"),
				"<br>- Link under heading of the section is not of Budget in Hindi");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> HindiBudgetHeadlines = budgetPageMethods.getHindiBudgetHeadlines();
		List<String> HindiBudgetStories = VerificationUtil.getLinkTextList(budgetPageMethods.getHindiBudgetHeadlines());
		System.out.println(HindiBudgetStories);
		softAssert.assertTrue(HindiBudgetStories.size() >= articleCount,

				"<br>- Headlines under  Budget in Hindi sections should be more than " + articleCount
						+ " in number found:" + HindiBudgetStories.size());

		List<String> HindiBudgetStoriesHref = WebBaseMethods.getListHrefUsingJSE(HindiBudgetHeadlines);
		HindiBudgetStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Budget in Hindi section is throwing " + response);
		});
		List<String> HindiBudgetNewsDup = VerificationUtil.isListUnique(HindiBudgetStories);
		softAssert.assertTrue(HindiBudgetNewsDup.isEmpty(),
				"<br>- Hindi Budget is having duplicate stories, repeating story(s)->" + HindiBudgetNewsDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the budget faq section on budget page", enabled = true)
	public void verifyBudgetFaqsSection() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionName = "Budget FAQs";
		String sectionLinkName = "budget-faqs";
		WebElement wee = budgetPageMethods.getBudgetPageObjects().getBudgetFaqLink();
		List<WebElement> li = budgetPageMethods.getBudgetPageObjects().getBudgetFaqArticleLinks();
		WebElement we = budgetPageMethods.getMoreFromSectionLinkElement(sectionName);
		verifySectionLink(wee, sectionName, sectionLinkName);
		verifySectionLinkResponse(sectionName, li);
		verifySectionDuplicateStories(li, sectionName, articleCount);
		verifyMoreFromSectionLink(we, sectionName, sectionLinkName);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies if there is any broken link on the elections homepage")
	public void verifyBrokenLinks() {
		softAssert = new SoftAssert();
		List<String> allUrls = budgetPageMethods.getAllUrls();
		System.out.println(allUrls);
		System.out.println(allUrls.size());
		List<String> errorUrls = new LinkedList<>();
		allUrls.forEach(url -> {
			if (url.contains("economictimes") && url.contains(".cms")) {
				int responseCode = HTTPResponse.checkResponseCode(url);
				if (!(responseCode == 200)) {
					errorUrls.add(url);
				}
			}
			softAssert.assertTrue(errorUrls.size() == 0,
					"The following urls are having response code other than 200:- " + errorUrls);

		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Tech section on Budget page", enabled = false)
	public void verifyTechSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();
		String sectionLink = budgetPageMethods.getTechLink();
		String moreFromSectionLink = budgetPageMethods.getMoreFromSectionLink("Tech");
		int moreFromResponseCode = HTTPResponse.checkResponseCode(moreFromSectionLink);
		softAssert.assertEquals(moreFromResponseCode, 200, "<br>- <a href=" + moreFromSectionLink
				+ ">Link under heading </a> is throwing " + moreFromResponseCode);

		softAssert.assertTrue(sectionLink.contains("tech") && moreFromSectionLink.contains("tech"),
				"<br>- Link under heading of the section is not of Tech");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> TechHeadlines = budgetPageMethods.getTechHeadlines();
		List<String> TechStories = VerificationUtil.getLinkTextList(budgetPageMethods.getTechHeadlines());
		softAssert.assertTrue(TechStories.size() >= articleCount,
				"<br>- Headlines under Tech sections should be more than " + articleCount + " in number found:"
						+ TechStories.size());
		List<String> TechStoriesHref = WebBaseMethods.getListHrefUsingJSE(TechHeadlines);
		TechStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Tech section is throwing " + response);
		});
		List<String> TechNewsDup = VerificationUtil.isListUnique(TechStories);
		softAssert.assertTrue(TechNewsDup.isEmpty(),
				"<br>- Tech is having duplicate stories, repeating story(s)->" + TechNewsDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Budget for MF section on Budget page", enabled = false)
	public void verifyBudgetForMFSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();
		String sectionLink = budgetPageMethods.getBudgetForMFLink();
		String moreFromSectionLink = budgetPageMethods.getMoreFromSectionLink("Budget for MF");
		int moreFromResponseCode = HTTPResponse.checkResponseCode(moreFromSectionLink);
		softAssert.assertEquals(moreFromResponseCode, 200, "<br>- <a href=" + moreFromSectionLink
				+ ">Link under heading </a> is throwing " + moreFromResponseCode);

		softAssert.assertTrue(
				sectionLink.contains("budget-impact-on-mutual-fund-investments")
						&& moreFromSectionLink.contains("budget-impact-on-mutual-fund-investments"),
				"<br>- Link under heading of the section is not of Budget for MF");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> BudgetForMFHeadlines = budgetPageMethods.getBudgetForMFHeadlines();
		List<String> BudgetForMFStories = VerificationUtil.getLinkTextList(budgetPageMethods.getBudgetForMFHeadlines());
		softAssert.assertTrue(BudgetForMFStories.size() >= articleCount,
				"<br>- Headlines under Budget for MF sections should be more than " + articleCount + " in number found:"
						+ BudgetForMFStories.size());
		List<String> BudgetForMFStoriesHref = WebBaseMethods.getListHrefUsingJSE(BudgetForMFHeadlines);
		BudgetForMFStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Budget for MF section is throwing " + response);
		});
		List<String> BudgetForMFNewsDup = VerificationUtil.isListUnique(BudgetForMFStories);
		softAssert.assertTrue(BudgetForMFNewsDup.isEmpty(),
				"<br>- Budget for MF is having duplicate stories, repeating story(s)->" + BudgetForMFNewsDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Think Tank section on Budget page", enabled = false)
	public void verifyThinkTankSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();
		String sectionLink = budgetPageMethods.getThinkTankLink();
		String moreFromSectionLink = budgetPageMethods.getMoreFromSectionLink("Think Tank");
		int moreFromResponseCode = HTTPResponse.checkResponseCode(moreFromSectionLink);
		softAssert.assertEquals(moreFromResponseCode, 200, "<br>- <a href=" + moreFromSectionLink
				+ ">Link under heading </a> is throwing " + moreFromResponseCode);

		softAssert.assertTrue(
				sectionLink.contains("budget-think-tank-opinion-by-experts")
						&& moreFromSectionLink.contains("budget-think-tank-opinion-by-experts"),
				"<br>- Link under heading of the section is not of Think Tank");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> ThinkTankHeadlines = budgetPageMethods.getThinkTankHeadlines();
		List<String> ThinkTankStories = VerificationUtil.getLinkTextList(budgetPageMethods.getThinkTankHeadlines());
		softAssert.assertTrue(ThinkTankStories.size() >= articleCount,
				"<br>- Headlines under Think Tank sections should be more than " + articleCount + " in number found:"
						+ ThinkTankStories.size());
		List<String> ThinkTankStoriesHref = WebBaseMethods.getListHrefUsingJSE(ThinkTankHeadlines);
		ThinkTankStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Think Tank section is throwing " + response);
		});
		List<String> ThinkTankNewsDup = VerificationUtil.isListUnique(ThinkTankStories);
		softAssert.assertTrue(ThinkTankNewsDup.isEmpty(),
				"<br>- Think Tank is having duplicate stories, repeating story(s)->" + ThinkTankNewsDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Taxes & You section on Budget page", enabled = false)
	public void verifyTaxesNYouSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();
		String sectionLink = budgetPageMethods.getTaxesNYouLink();
		String moreFromSectionLink = budgetPageMethods.getMoreFromSectionLink("Taxes & You");
		int moreFromResponseCode = HTTPResponse.checkResponseCode(moreFromSectionLink);
		softAssert.assertEquals(moreFromResponseCode, 200, "<br>- <a href=" + moreFromSectionLink
				+ ">Link under heading </a> is throwing " + moreFromResponseCode);

		softAssert.assertTrue(
				sectionLink.contains("budget-impact-on-income-tax-slabs")
						&& moreFromSectionLink.contains("budget-impact-on-income-tax-slabs"),
				"<br>- Link under heading of the section is not of Taxes & You");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> TaxesNYouHeadlines = budgetPageMethods.getTaxesNYouHeadlines();
		List<String> TaxesNYouStories = VerificationUtil.getLinkTextList(budgetPageMethods.getTaxesNYouHeadlines());
		softAssert.assertTrue(TaxesNYouStories.size() >= articleCount,
				"<br>- Headlines under Taxes & You sections should be more than " + articleCount + " in number found:"
						+ TaxesNYouStories.size());
		List<String> TaxesNYouStoriesHref = WebBaseMethods.getListHrefUsingJSE(TaxesNYouHeadlines);
		TaxesNYouStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Taxes & You section is throwing " + response);
		});
		List<String> TaxesNYouNewsDup = VerificationUtil.isListUnique(TaxesNYouStories);
		softAssert.assertTrue(TaxesNYouNewsDup.isEmpty(),
				"<br>- Taxes N You is having duplicate stories, repeating story(s)->" + TaxesNYouNewsDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Budget For SME section on Budget page", enabled = false)
	public void verifyBudgetForSMESection() {
		int articleCount = 3;
		softAssert = new SoftAssert();
		String sectionLink = budgetPageMethods.getBudgetForSMELink();
		String moreFromSectionLink = budgetPageMethods.getMoreFromSectionLink("Budget for SME");
		int moreFromResponseCode = HTTPResponse.checkResponseCode(moreFromSectionLink);
		softAssert.assertEquals(moreFromResponseCode, 200, "<br>- <a href=" + moreFromSectionLink
				+ ">Link under heading </a> is throwing " + moreFromResponseCode);

		softAssert.assertTrue(sectionLink.contains("small-biz") && moreFromSectionLink.contains("small-biz"),
				"<br>- Link under heading of the section is not of Budget For SME");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> BudgetForSMEHeadlines = budgetPageMethods.getBudgetForSMEHeadlines();
		List<String> BudgetForSMEStories = VerificationUtil
				.getLinkTextList(budgetPageMethods.getBudgetForSMEHeadlines());
		softAssert.assertTrue(BudgetForSMEStories.size() >= articleCount,
				"<br>- Headlines under Budget For SME sections should be more than " + articleCount
						+ " in number found:" + BudgetForSMEStories.size());
		List<String> BudgetForSMEStoriesHref = WebBaseMethods.getListHrefUsingJSE(BudgetForSMEHeadlines);
		BudgetForSMEStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Budget For SME section is throwing " + response);
		});
		List<String> BudgetForSMENewsDup = VerificationUtil.isListUnique(BudgetForSMEStories);
		softAssert.assertTrue(BudgetForSMENewsDup.isEmpty(),
				"<br>- Budget For SME is having duplicate stories, repeating story(s)->" + BudgetForSMENewsDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Slideshows section on Budget page", enabled = false)
	public void verifySlideshowsSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();
		String sectionLink = budgetPageMethods.getSlideshowsLink();
		String moreFromSectionLink = budgetPageMethods.getMoreFromSectionLink("Slideshows");
		int moreFromResponseCode = HTTPResponse.checkResponseCode(moreFromSectionLink);
		softAssert.assertEquals(moreFromResponseCode, 200, "<br>- <a href=" + moreFromSectionLink
				+ ">Link under heading </a> is throwing " + moreFromResponseCode);

		softAssert.assertTrue(
				sectionLink.contains("budget-slideshows") && moreFromSectionLink.contains("budget-slideshows"),
				"<br>- Link under heading of the section is not of Slideshows");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> SlideshowsHeadlines = budgetPageMethods.getSlideshowHeadlines();
		List<String> SlideshowsStories = VerificationUtil.getLinkTextList(budgetPageMethods.getSlideshowHeadlines());
		softAssert.assertTrue(SlideshowsStories.size() >= articleCount,
				"<br>- Headlines under Slideshows sections should be more than " + articleCount + " in number found:"
						+ SlideshowsStories.size());
		List<String> SlideshowsStoriesHref = WebBaseMethods.getListHrefUsingJSE(SlideshowsHeadlines);
		SlideshowsStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Slideshows section is throwing " + response);
		});
		List<String> SlideshowsNewsDup = VerificationUtil.isListUnique(SlideshowsStories);
		softAssert.assertTrue(SlideshowsNewsDup.isEmpty(),
				"<br>- Slideshows is having duplicate stories, repeating story(s)->" + SlideshowsNewsDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Budget Videos section on Budget page", enabled = false)
	public void verifyBudgetVideosSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();
		String sectionLink = budgetPageMethods.getBudgetVideosLink();
		String moreFromSectionLink = budgetPageMethods.getMoreFromSectionLink("Budget Videos");
		int moreFromResponseCode = HTTPResponse.checkResponseCode(moreFromSectionLink);
		softAssert.assertEquals(moreFromResponseCode, 200, "<br>- <a href=" + moreFromSectionLink
				+ ">Link under heading </a> is throwing " + moreFromResponseCode);

		softAssert.assertTrue(
				sectionLink.contains("budget-2018-videos") && moreFromSectionLink.contains("budget-2018-videos"),
				"<br>- Link under heading of the section is not of Budget Videos");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> BudgetVideosHeadlines = budgetPageMethods.getBudgetVideos();
		List<String> BudgetVideosStories = VerificationUtil.getLinkTextList(budgetPageMethods.getBudgetVideos());
		softAssert.assertTrue(BudgetVideosStories.size() >= articleCount,
				"<br>- Headlines under Budget Videos sections should be more than " + articleCount + " in number found:"
						+ BudgetVideosStories.size());
		List<String> BudgetVideosStoriesHref = WebBaseMethods.getListHrefUsingJSE(BudgetVideosHeadlines);
		BudgetVideosStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Budget Videos section is throwing " + response);
		});
		List<String> BudgetVideosNewsDup = VerificationUtil.isListUnique(BudgetVideosStories);
		softAssert.assertTrue(BudgetVideosNewsDup.isEmpty(),
				"<br>- Budget Videos is having duplicate stories, repeating story(s)->" + BudgetVideosNewsDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Corner Office section on Budget page", enabled = false)
	public void verifyCornerOfficeSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();
		String sectionLink = budgetPageMethods.getCornerOfficeLink();
		String moreFromSectionLink = budgetPageMethods.getMoreFromSectionLink("Corner Office");
		int moreFromResponseCode = HTTPResponse.checkResponseCode(moreFromSectionLink);
		softAssert.assertEquals(moreFromResponseCode, 200, "<br>- <a href=" + moreFromSectionLink
				+ ">Link under heading </a> is throwing " + moreFromResponseCode);

		softAssert.assertTrue(
				sectionLink.contains("budget-analysis-by-experts")
						&& moreFromSectionLink.contains("budget-analysis-by-experts"),
				"<br>- Link under heading of the section is not of Corner Office");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> CornerOfficeHeadlines = budgetPageMethods.getCornerOfficeHeadlines();
		List<String> CornerOfficeStories = VerificationUtil
				.getLinkTextList(budgetPageMethods.getCornerOfficeHeadlines());
		softAssert.assertTrue(CornerOfficeStories.size() >= articleCount,
				"<br>- Headlines under Corner Office sections should be more than " + articleCount + " in number found:"
						+ CornerOfficeStories.size());
		List<String> CornerOfficeStoriesHref = WebBaseMethods.getListHrefUsingJSE(CornerOfficeHeadlines);
		CornerOfficeStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Corner Office section is throwing " + response);
		});
		List<String> CornerOfficeNewsDup = VerificationUtil.isListUnique(CornerOfficeStories);
		softAssert.assertTrue(CornerOfficeNewsDup.isEmpty(),
				"<br>- Corner Office is having duplicate stories, repeating story(s)->" + CornerOfficeNewsDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Slider Budget Headlines section on Budget page", enabled = false)
	public void verifySliderBudgetHeadlinesSection() {
		int articleCount = 3;
		softAssert = new SoftAssert();
		List<WebElement> sliderBudgetHeadlines = budgetPageMethods.getSliderBudgetHeadlines();
		List<String> sliderBudgetHeadlinesStories = WebBaseMethods
				.getListTextUsingJSE(budgetPageMethods.getSliderBudgetHeadlines());
		softAssert.assertTrue(sliderBudgetHeadlinesStories.size() >= articleCount,
				"<br>- Headlines under Slider Budget Headlines sections should be more than " + articleCount
						+ " in number found:" + sliderBudgetHeadlinesStories.size());
		List<String> sliderBudgetHeadlinesStoriesHref = WebBaseMethods.getListHrefUsingJSE(sliderBudgetHeadlines);
		sliderBudgetHeadlinesStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Slider Budget Headlines section is throwing " + response);
		});
		List<String> sliderBudgetHeadlinesNewsDup = VerificationUtil.isListUnique(sliderBudgetHeadlinesStories);
		softAssert.assertTrue(sliderBudgetHeadlinesNewsDup.isEmpty(),
				"<br>- Slider Budget Headlines is having duplicate stories, repeating story(s)->"
						+ sliderBudgetHeadlinesNewsDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Common Mans Budget auto slider section on budget page  ", enabled = false)
	public void verifycommonMansBudgetSection() {
		int commonMansBudget = 6;
		softAssert = new SoftAssert();

		List<String> commonMansBudgetStories = budgetPageMethods
				.getSliderHeadlines(budgetPageMethods.getBudgetPageObjects().getCommonMansBudgetHeadlines());
		Assert.assertTrue(commonMansBudgetStories.size() > 0, "No stories are shown in Common Mans Budget section");
		softAssert.assertEquals(commonMansBudgetStories.size(), commonMansBudget,
				"<br>- Expected story count: " + commonMansBudget + " found: " + commonMansBudgetStories.size());
		List<String> commonMansBudgetDup = VerificationUtil.isListUnique(commonMansBudgetStories);
		softAssert.assertTrue(commonMansBudgetDup.isEmpty(),
				"<br>- Common Mans Budget is having duplicate stories, repeating story(s)->" + commonMansBudgetDup);
		List<String> commonMansBudgetStoriesHref = WebBaseMethods
				.getListHrefUsingJSE(budgetPageMethods.getBudgetPageObjects().getCommonMansBudgetHeadlinesLink());

		commonMansBudgetStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Common Mans Budget section is throwing " + response);
		});
		List<String> beforeClickHeadlines = ETSharedMethods
				.getDisplayedElementsList(budgetPageMethods.getBudgetPageObjects().getCommonMansBudgetHeadlines());
		budgetPageMethods.clickNonActiveCarouselTab(
				budgetPageMethods.getBudgetPageObjects().getNonActiveCommonMansBudgetCarouselTab());
		List<String> afterClickHeadlines = ETSharedMethods
				.getDisplayedElementsList(budgetPageMethods.getBudgetPageObjects().getCommonMansBudgetHeadlines());
		softAssert.assertFalse(VerificationUtil.areListsEqual(beforeClickHeadlines, afterClickHeadlines),
				"Stories did not change after clicking next");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the Auto section on budget page", enabled = false)
	public void verifyAutoSection() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionName = "Auto";
		String sectionLinkName = "auto";
		WebElement wee = budgetPageMethods.getBudgetPageObjects().getAutoLink();
		List<WebElement> li = budgetPageMethods.getBudgetPageObjects().getAutoSectionHeadlines();
		WebElement we = budgetPageMethods.getMoreFromSectionLinkElement(sectionName);

		verifySectionLink(wee, sectionName, sectionLinkName);
		verifySectionLinkResponse(sectionName, li);
		verifySectionDuplicateStories(li, sectionName, articleCount);
		verifyMoreFromSectionLink(we, sectionName, sectionLinkName);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the Cons.Product section on budget page", enabled = false)
	public void verifyConsProductSection() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionName = "Cons. Product";
		String sectionLinkName = "cons-products";
		WebElement wee = budgetPageMethods.getBudgetPageObjects().getConsProductsLink();
		List<WebElement> li = budgetPageMethods.getBudgetPageObjects().getConsProductsSectionHeadlines();
		WebElement we = budgetPageMethods.getMoreFromSectionLinkElement(sectionName);

		verifySectionLink(wee, sectionName, sectionLinkName);
		verifySectionLinkResponse(sectionName, li);
		verifySectionDuplicateStories(li, sectionName, articleCount);
		verifyMoreFromSectionLink(we, sectionName, sectionLinkName);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the Economic Survey section on budget page", enabled = false)
	public void verifyEconomicSurveySection() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionName = "Economic Survey";
		String sectionLinkName = "economic-survey";
		WebElement wee = budgetPageMethods.getBudgetPageObjects().getEconomicSurveyLink();
		List<WebElement> li = budgetPageMethods.getBudgetPageObjects().getEconomicSurveySectionHeadlines();
		WebElement we = budgetPageMethods.getMoreFromSectionLinkElement(sectionName);

		verifySectionLink(wee, sectionName, sectionLinkName);
		verifySectionLinkResponse(sectionName, li);
		verifySectionDuplicateStories(li, sectionName, articleCount);
		verifyMoreFromSectionLink(we, sectionName, sectionLinkName);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the If I were FM section on budget page", enabled = false)
	public void verifyIfIwereFMSection() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionName = "If I were FM";
		String sectionLinkName = "if-i-were-fm";
		WebElement wee = budgetPageMethods.getBudgetPageObjects().getIfIwereFMLink();
		List<WebElement> li = budgetPageMethods.getBudgetPageObjects().getIfIwereFMSectionHeadlines();
		WebElement we = budgetPageMethods.getMoreFromSectionLinkElement(sectionName);
		verifySectionLink(wee, sectionName, sectionLinkName);
		verifySectionLinkResponse(sectionName, li);
		verifySectionDuplicateStories(li, sectionName, articleCount);
		verifyMoreFromSectionLink(we, sectionName, sectionLinkName);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the Ind'l Goods/Svs section on budget page", enabled = false)
	public void verifyGoodsSvsSection() {
		softAssert = new SoftAssert();
		int articleCount = 3;
		String sectionName = "Ind'l Goods/Svs";
		String sectionLinkName = "indl-goods/svs";
		WebElement wee = budgetPageMethods.getBudgetPageObjects().getGoodsSvsLink();
		List<WebElement> li = budgetPageMethods.getBudgetPageObjects().getGoodsSvsSectionHeadlines();
		WebElement we = budgetPageMethods.getMoreFromSectionLinkElement(sectionName);
		verifySectionLink(wee, sectionName, sectionLinkName);
		verifySectionLinkResponse(sectionName, li);
		verifySectionDuplicateStories(li, sectionName, articleCount);
		verifyMoreFromSectionLink(we, sectionName, sectionLinkName);

		softAssert.assertAll();
	}

	/*
	 * @Test(
	 * description="This test verifies the markets live widget on the budget page"
	 * ) public void verifyMarketsLiveWidget(){ List<WebElement> li =
	 * budgetPageMethods.getMarketsLiveTabs(); int articleCount = 3;
	 * li.forEach(tab->{ WebBaseMethods.clickElementUsingJSE(tab); String
	 * sectionName = tab.getText(); String sectionLinkName =
	 * sectionName.toLowerCase(); WebElement we = budgetPage List<WebElement>
	 * storyList = budgetPageMethods.getMarketsLiveCompanies();
	 * verifySectionDuplicateStories(storyList, sectionName, articleCount);
	 * verifySectionLinkResponse(sectionName, storyList);
	 * verifyMoreFromSectionLink(we, sectionName, sectionLinkName);
	 * 
	 * });
	 * 
	 * 
	 * 
	 * }
	 */

	public void verifySectionLink(WebElement wee, String sectionName, String sectionLinkName) {
		String sectionLink = wee.getAttribute("href");
		System.out.println(sectionLink);
		softAssert.assertTrue(sectionLink.contains(sectionLinkName),
				"<br>- Link under heading of the section is not of " + sectionName);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);

	}

	public void verifySectionLinkResponse(String sectionName, List<WebElement> li) {
		budgetPageMethods.getSectionNewsHref(li).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top " + sectionName + " link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});

	}

	public void verifySectionDuplicateStories(List<WebElement> li, String sectionName, int articleCount) {

		List<String> sectionNewsStories = VerificationUtil.getLinkTextList(li);
		// System.out.println(sectionNewsStories);
		int count = sectionNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under " + sectionName
				+ " sections should be more than " + articleCount + " in number, instead found " + count);
		List<String> sectionNewsDup = VerificationUtil.isListUnique(sectionNewsStories);
		softAssert.assertTrue(sectionNewsDup.isEmpty(),
				"<br>- " + sectionName + " Section has duplicate stories, repeating story(s)->" + sectionNewsDup);

	}

	public void verifyMoreFromSectionLink(WebElement we, String sectionName, String sectionLinkName) {
		String sectionLink = we.getAttribute("href");
		softAssert.assertTrue(sectionLink.contains(sectionLinkName),
				"<br>- Link under heading of More From the section is not of " + sectionName);
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);

	}

	public void verifyAutoSlider(List<WebElement> list, List<WebElement> sliderList) {
		List<String> beforeClickHeadlines = ETSharedMethods.getDisplayedElementsList(list);
		budgetPageMethods.clickNonActiveCarouselTab(sliderList);
		List<String> afterClickHeadlines = ETSharedMethods.getDisplayedElementsList(list);
		softAssert.assertFalse(VerificationUtil.areListsEqual(beforeClickHeadlines, afterClickHeadlines),
				"Stories did not change after clicking next");

	}
	@AfterClass
	public void doClean(){
		driver.quit();
	}


}
