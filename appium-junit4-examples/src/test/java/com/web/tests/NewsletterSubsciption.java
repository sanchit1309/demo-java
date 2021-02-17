
package com.web.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import busineslogic.NewsletterURL;
import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.ExcelUtil;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pagemethods.ETSharedMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.NewsletterSubsMethod;
import web.pagemethods.WebBaseMethods;
import web.pageobjects.NewsletterSubsPageObjects;

public class NewsletterSubsciption extends BaseTest {
	private String baseUrl;
	NewsletterSubsMethod newsletterSubsMethods;
	Map<String, String> TestData = new HashMap<String, String>();
	HeaderPageMethods headerPageMethods;
	LoginPageMethods loginPageMethods;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = Config.fetchConfigProperty("HomeUrl");
		launchBrowser(baseUrl);
		newsletterSubsMethods = new NewsletterSubsMethod(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyLogin", 1);

	}

	@Test(description = "This test verifies the newsletter subscription", enabled = false)
	public void verifyNewsletterSubscription() {
		SoftAssert softAssert = new SoftAssert();
		String email = "ettesting23@gmail.com";
		String password = "Qacheck@123";
		String newsLetters = "Executive Wrap, Daily Newsletter, Markets Watch, Wealth, Slideshows, ET Sunday Wrap, ET Mutual Funds, ET Investment opportunities, ET Panache, Markets Morning, ET Rise, ET Defence, Politics and Nation, ET Tech, ET Economy, Breaking News";
		List<String> newsLetterNames = Arrays.asList(newsLetters.split("\\s*,\\s*"));

		driver.get("https://economictimes.indiatimes.com/subscription.cms");

		ETSharedMethods.declineNotifications();
		newsletterSubsMethods.getSelectAllSubsCheckbox().click();
		newsletterSubsMethods.getEmailIdBox().sendKeys(email);
		newsletterSubsMethods.getSubmitButton().click();
		WaitUtil.sleep(2000);
		try {
			softAssert.assertTrue(newsletterSubsMethods.checkPresenceOfThankyouElement(),
					"The user is not getting subscribed in 5 sec");
			Assert.assertTrue(newsletterSubsMethods.getThankYouMessage().contains("Thank you for Subscribing!"),
					"The thankyou for subscribing message is not shown on subscribing for newsletters");
		} catch (Exception ee) {
			Assert.assertTrue(false, "The thankyou message is not shown and subsciption is not working properly");
			;
			ee.printStackTrace();
		}
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");
		Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(2000);
		Assert.assertTrue(headerPageMethods.getLoggedInUserImage(), "Logged in user image not shown");
		WaitUtil.sleep(2000);
		List<String> subscribedNewsletterList = newsletterSubsMethods.getSubscribedNewsletterList();

		List<String> newslettersMissedSubs = VerificationUtil.differenceTwoLists(subscribedNewsletterList,
				newsLetterNames);
		softAssert.assertTrue(newslettersMissedSubs.size() == 0 && subscribedNewsletterList.size() >= 16,
				"All news letters are not subscribed, newsletters which are not subscribed are"
						+ newslettersMissedSubs);

		newsletterSubsMethods.unsubscribeAllNewsletter();
		WaitUtil.sleep(2000);

		List<String> unsubscribedNewsletterList = newsletterSubsMethods.getUnSubscribedNewsletterList();
		List<String> newslettersMissedUnSubs = VerificationUtil.differenceTwoLists(unsubscribedNewsletterList,
				newsLetterNames);

		softAssert.assertTrue(newslettersMissedUnSubs.size() == 0 && unsubscribedNewsletterList.size() >= 16,
				"All news letters are not unsubscribed, newsletters which are not unsubscribed are "
						+ newslettersMissedUnSubs);

		softAssert.assertAll();

	}

	@Test(description = "This test verifies the template of the ECONOMY newsletter")
	public void verifyEconomyNlTemplate() {
		String name = "ET ECONOMY";
		int articleCount = 10;
		SoftAssert softAssert = new SoftAssert();
		driver.get(NewsletterURL.etEconomy);
		WaitUtil.sleep(3000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getNlArticleListType1());
		softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
				"Links in " + name + " newsletter template is not having response code 200"
						+ newsletterSubsMethods.getErrorResponseCodeLinks(href));
		verifyTimeAndDate(newsletterSubsMethods.getNlDateTabType1(), name, softAssert);
		List<String> nlStories = VerificationUtil.getLinkTextList(newsletterSubsMethods.getNlArticleListType1());
		softAssert.assertTrue(nlStories.size() >= articleCount,
				"<br>- Headlines under  " + name + " newsletter template should be more than " + articleCount
						+ " in number, instead found " + nlStories.size());
		List<String> nlStoriesDup = VerificationUtil.isListUnique(nlStories);
		softAssert.assertTrue(nlStoriesDup.isEmpty(), "<br>-  " + name
				+ " newsletter template is having duplicate stories, repeating story(s)->" + nlStoriesDup);

		softAssert.assertAll();

	}

	@Test(description = "This test verifies the template of the Polictics and Nation newsletter")
	public void verifyPoliticsAndNationNlTemplate() {
		String name = "Politics and Nation";
		int articleCount = 10;
		SoftAssert softAssert = new SoftAssert();
		driver.get(NewsletterURL.politcsAndNation);
		WaitUtil.sleep(3000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getNlArticleListType1());
		softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
				"Links in " + name + " newsletter template is not having response code 200"
						+ newsletterSubsMethods.getErrorResponseCodeLinks(href));
		verifyTimeAndDate(newsletterSubsMethods.getNlDateTabType1(), name, softAssert);
		List<String> nlStories = VerificationUtil.getLinkTextList(newsletterSubsMethods.getNlArticleListType1());
		softAssert.assertTrue(nlStories.size() >= articleCount,
				"<br>- Headlines under  " + name + " newsletter template should be more than " + articleCount
						+ " in number, instead found " + nlStories.size());
		List<String> nlStoriesDup = VerificationUtil.isListUnique(nlStories);
		softAssert.assertTrue(nlStoriesDup.isEmpty(), "<br>-  " + name
				+ " newsletter template is having duplicate stories, repeating story(s)->" + nlStoriesDup);

		softAssert.assertAll();

	}

	@Test(description = "This test verifies the template of the TECH newsletter")
	public void verifyTechNlTemplate() {
		String name = "ET TECH";
		int articleCount = 10;
		SoftAssert softAssert = new SoftAssert();
		driver.get(NewsletterURL.etTech);
		WaitUtil.sleep(3000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getNlArticleListType1());
		softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
				"Links in " + name + " newsletter template is not having response code 200"
						+ newsletterSubsMethods.getErrorResponseCodeLinks(href));
		verifyTimeAndDate(newsletterSubsMethods.getNlDateTabType1(), name, softAssert);
		List<String> nlStories = VerificationUtil.getLinkTextList(newsletterSubsMethods.getNlArticleListType1());
		softAssert.assertTrue(nlStories.size() >= articleCount,
				"<br>- Headlines under  " + name + " newsletter template should be more than " + articleCount
						+ " in number, instead found " + nlStories.size());
		List<String> nlStoriesDup = VerificationUtil.isListUnique(nlStories);
		softAssert.assertTrue(nlStoriesDup.isEmpty(), "<br>-  " + name
				+ " newsletter template is having duplicate stories, repeating story(s)->" + nlStoriesDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the template of the ET Defence newsletter")
	public void verifyDefenceNlTemplate() {
		String name = "ET Defence";
		int articleCount = 10;
		SoftAssert softAssert = new SoftAssert();
		driver.get(NewsletterURL.etDefence);
		WaitUtil.sleep(3000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getNlArticleListType2());
		softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
				"Links in " + name + " newsletter template is not having response code 200"
						+ newsletterSubsMethods.getErrorResponseCodeLinks(href));
		verifyTimeAndDate(newsletterSubsMethods.getNlDateTabType2(), name, softAssert);
		List<String> nlStories = VerificationUtil.getLinkTextList(newsletterSubsMethods.getNlArticleListType2());
		softAssert.assertTrue(nlStories.size() >= articleCount,
				"<br>- Headlines under  " + name + " newsletter template should be more than " + articleCount
						+ " in number, instead found " + nlStories.size());
		List<String> nlStoriesDup = VerificationUtil.isListUnique(nlStories);
		softAssert.assertTrue(nlStoriesDup.isEmpty(), "<br>-  " + name
				+ " newsletter template is having duplicate stories, repeating story(s)->" + nlStoriesDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the template of the Small Biz newsletter")
	public void verifySmallBizNlTemplate() {
		String name = "ET Rise";
		int articleCount = 10;
		SoftAssert softAssert = new SoftAssert();
		driver.get(NewsletterURL.etRise);
		WaitUtil.sleep(3000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getNlArticleListType2());
		softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
				"Links in " + name + " newsletter template is not having response code 200"
						+ newsletterSubsMethods.getErrorResponseCodeLinks(href));
		verifyTimeAndDate(newsletterSubsMethods.getNlDateTabType2(), name, softAssert);
		List<String> nlStories = VerificationUtil.getLinkTextList(newsletterSubsMethods.getNlArticleListType2());
		softAssert.assertTrue(nlStories.size() >= articleCount,
				"<br>- Headlines under  " + name + " newsletter template should be more than " + articleCount
						+ " in number, instead found " + nlStories.size());
		List<String> nlStoriesDup = VerificationUtil.isListUnique(nlStories);
		softAssert.assertTrue(nlStoriesDup.isEmpty(), "<br>-  " + name
				+ " newsletter template is having duplicate stories, repeating story(s)->" + nlStoriesDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the template of the ET Sunday Wrap newsletter")
	public void verifyEtSundayWrapNlTemplate() {
		String name = "ET Sunday Wrap";
		int articleCount = 10;
		SoftAssert softAssert = new SoftAssert();
		driver.get(NewsletterURL.etSundayWrap);
		WaitUtil.sleep(3000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getNlArticleListType3());
		softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
				"Links in " + name + " newsletter template is not having response code 200"
						+ newsletterSubsMethods.getErrorResponseCodeLinks(href));
		verifyTimeAndDate(newsletterSubsMethods.getNlDateTabType3(), name, softAssert);
		List<String> nlStories = VerificationUtil.getLinkTextList(newsletterSubsMethods.getNlArticleListType3());
		softAssert.assertTrue(nlStories.size() >= articleCount,
				"<br>- Headlines under  " + name + " newsletter template should be more than " + articleCount
						+ " in number, instead found " + nlStories.size());
		List<String> nlStoriesDup = VerificationUtil.isListUnique(nlStories);
		softAssert.assertTrue(nlStoriesDup.isEmpty(), "<br>-  " + name
				+ " newsletter template is having duplicate stories, repeating story(s)->" + nlStoriesDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the template of the Small Biz newsletter")
	public void verifySlideshowNlTemplate() {
		String name = "Slideshows";
		int articleCount = 5;
		SoftAssert softAssert = new SoftAssert();
		driver.get(NewsletterURL.slideshowsNewsletter);
		WaitUtil.sleep(3000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getNlArticleListSlideshows());
		softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
				"Links in " + name + " newsletter template is not having response code 200"
						+ newsletterSubsMethods.getErrorResponseCodeLinks(href));
		verifyTimeAndDate(newsletterSubsMethods.getNlDateTabType3(), name, softAssert);
		List<String> nlStories = VerificationUtil.getLinkTextList(newsletterSubsMethods.getNlArticleListSlideshows());
		softAssert.assertTrue(nlStories.size() >= articleCount,
				"<br>- Headlines under  " + name + " newsletter template should be more than " + articleCount
						+ " in number, instead found " + nlStories.size());
		List<String> nlStoriesDup = VerificationUtil.isListUnique(nlStories);
		softAssert.assertTrue(nlStoriesDup.isEmpty(), "<br>-  " + name
				+ " newsletter template is having duplicate stories, repeating story(s)->" + nlStoriesDup);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the template of the Executive Wrap newsletter")
	public void verifyExecutiveWrapTemplate() {
		String name = "Executive Wrap";
		SoftAssert softAssert = new SoftAssert();
		driver.get(NewsletterURL.executiveWrap);
		WaitUtil.sleep(2000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getAllUrls());
		softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
				"Links in " + name + " newsletter template is not having response code 200"
						+ newsletterSubsMethods.getErrorResponseCodeLinks(href));
		verifyTimeAndDate(newsletterSubsMethods.getNlDateTabType3(), name, softAssert);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the template of the Markets Watch newsletter")
	public void verifyMarketsWatchTemplate() {
		String name = "Markets Watch";
		SoftAssert softAssert = new SoftAssert();

		driver.get(NewsletterURL.marketsWatch);
		WaitUtil.sleep(2000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getAllUrls());
		softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
				"Links in " + name + " newsletter template is not having response code 200"
						+ newsletterSubsMethods.getErrorResponseCodeLinks(href));

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the template of the ET Wealth newsletter")
	public void verifyEtWealthTemplate() {
		String name = "ET Wealth";
		SoftAssert softAssert = new SoftAssert();
		String tableHeaderName = "Top Mutual Funds";
		List<String> tableName = Arrays.asList(tableHeaderName.split("\\s*,\\s*"));

		driver.get(NewsletterURL.wealthNewsletter);
		WaitUtil.sleep(2000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getAllUrls());
		
			softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
					"Links in " + name + " newsletter template is not having response code 200"
							+ newsletterSubsMethods.getErrorResponseCodeLinks(href));
	
		verifyTimeAndDate(newsletterSubsMethods.getNlDateTabType3(), name, softAssert);
		softAssert.assertTrue(newsletterSubsMethods.verifyTablePresent(tableName).size() == 0,
				"The Table " + newsletterSubsMethods.verifyTablePresent(tableName) + " is not present in the " + name
						+ " Newsletter Template");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the template of the Markets Morning newsletter")
	public void verifyMarketsMorningTemplate() {
		String name = "Markets Morning";
		SoftAssert softAssert = new SoftAssert();
		String tableHeaderName = "Gainers, 52W - Highs";
		List<String> tableName = Arrays.asList(tableHeaderName.split("\\s*,\\s*"));

		driver.get(NewsletterURL.marketsMorning);
		WaitUtil.sleep(2000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getAllUrls());
		softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
				"Links in " + name + " newsletter template is not having response code 200"
						+ newsletterSubsMethods.getErrorResponseCodeLinks(href));
		verifyTimeAndDate(newsletterSubsMethods.getMorningMarketDate(), name, softAssert);
		softAssert.assertTrue(newsletterSubsMethods.verifyTablePresent(tableName).size() == 0,
				"The Table " + newsletterSubsMethods.verifyTablePresent(tableName) + " is not present in the " + name
						+ " Newsletter Template");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the template of the ET Investment Template newsletter")
	public void verifyETInvestmentTemplate() {
		String name = "ET Investment";
		SoftAssert softAssert = new SoftAssert();
		String tableHeaderName = "Top Mutual Funds, Initial Public Offer (IPOs), NFO (FMPs), NFO - Non FMPs, Views and Recommendations";
		List<String> tableName = Arrays.asList(tableHeaderName.split("\\s*,\\s*"));
		driver.get(NewsletterURL.etInvestmentOpportunities);
		WaitUtil.sleep(2000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getAllUrls());
		softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
				"Links in " + name + " newsletter template is not having response code 200"
						+ newsletterSubsMethods.getErrorResponseCodeLinks(href));
		verifyTimeAndDate(newsletterSubsMethods.getNlDateTabType3(), name, softAssert);
		softAssert.assertTrue(newsletterSubsMethods.verifyTablePresent(tableName).size() == 0,
				"The Table " + newsletterSubsMethods.verifyTablePresent(tableName) + " is not present in the " + name
						+ " Newsletter Template");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the template of the ET Mutual Fund newsletter")
	public void verifyETMutualFundTemplate() {
		String name = "ET Mutual Fund";
		SoftAssert softAssert = new SoftAssert();
		String tableHeaderName = "Top Mutual Funds, New Fund Offers, Forthcoming Dividend, Weekly Mutual Funds Activity, Top Mutual Funds of the week (5-Star Rating), Top Mutual Funds (All Ratings)";
		List<String> tableName = Arrays.asList(tableHeaderName.split("\\s*,\\s*"));
		driver.get(NewsletterURL.etMutualFunds);
		WaitUtil.sleep(2000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getAllUrls());
		softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
				"Links in " + name + " newsletter template is not having response code 200"
						+ newsletterSubsMethods.getErrorResponseCodeLinks(href));
		verifyTimeAndDate(newsletterSubsMethods.getNlDateTabType3(), name, softAssert);
		softAssert.assertTrue(newsletterSubsMethods.verifyTablePresent(tableName).size() == 0,
				"The Table " + newsletterSubsMethods.verifyTablePresent(tableName) + " is not present in the " + name
						+ " Newsletter Template");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the template of the Daily newsletter")
	public void verifyDailyNewsletterTemplate() {
		String name = "Daily";
		SoftAssert softAssert = new SoftAssert();
		String tableHeaderName = "Gainers";
		List<String> tableName = Arrays.asList(tableHeaderName.split("\\s*,\\s*"));
		driver.get(NewsletterURL.dailyNewsletter);
		WaitUtil.sleep(2000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getAllUrls());
		softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
				"Links in " + name + " newsletter template is not having response code 200"
						+ newsletterSubsMethods.getErrorResponseCodeLinks(href));
		verifyTimeAndDate(newsletterSubsMethods.getNlDateTabType3(), name, softAssert);
		softAssert.assertTrue(newsletterSubsMethods.verifyTablePresent(tableName).size() == 0,
				"The Table " + newsletterSubsMethods.verifyTablePresent(tableName) + " is not present in the " + name
						+ " Newsletter Template");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the template of the ET Panache newsletter")
	public void verifyETPanacheNewsletterTemplate() {
		String name = "ET Panache";
		SoftAssert softAssert = new SoftAssert();
		driver.get(NewsletterURL.etPanache);
		WaitUtil.sleep(2000);
		List<String> href = VerificationUtil.getLinkHrefList(newsletterSubsMethods.getAllUrls());
		softAssert.assertTrue(newsletterSubsMethods.getErrorResponseCodeLinks(href).size() == 0,
				"Links in " + name + " newsletter template is not having response code 200"
						+ newsletterSubsMethods.getErrorResponseCodeLinks(href));
		softAssert.assertAll();
	}

	public void verifyTimeAndDate(WebElement dateTab, String nlName, SoftAssert softAssert) {
		WebElement dateTimeNew = dateTab;
		String[] dateTimeOld = dateTimeNew.getText().split("IST");
		String dateTimeN = dateTimeOld[0].replaceAll("\\n", "") + "IST";
		DateTime systemDate = new DateTime();
		DateTime date = WebBaseMethods.convertDateMethod(dateTimeN.replaceAll("\\|", "").replaceAll(" ", "")
				.replaceAll(",", "").replaceAll("\\.", ":").replace("\\n", ""));
		System.out.println(date);
		Assert.assertTrue(dateTimeN.length() > 0,
				"Date time is not shown on header on " + nlName + "newsletter template");
		softAssert.assertTrue(systemDate.getDayOfYear() == date.getDayOfYear(), "The date shown on " + nlName
				+ "newsletter template is not the current date instead is showing " + dateTimeN);
		softAssert.assertTrue(
				(systemDate.getMinuteOfDay() - date.getMinuteOfDay() < 25
						&& systemDate.getMinuteOfDay() - date.getMinuteOfDay() >= 0),
				"on " + nlName + " newsletter template  page the time shown " + dateTimeN
						+ " is having difference of more than 20 mins from current time"
						+ systemDate.toString("MMM dd, yyyy hh:mm a"));

	}

}