

package web.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import busineslogic.BusinessLogicMethods;
import common.launchsetup.BaseTest;
import common.urlRepo.FeedRepo;
import common.utilities.VerificationUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import web.pagemethods.HomePageMethods;
import web.pagemethods.MarketBandPageMethods;
import web.pagemethods.WebBaseMethods;

import static io.restassured.RestAssured.given;

public class MarketBand extends BaseTest {
	HomePageMethods homePageMethods;
	SoftAssert softAssert;
	WebBaseMethods webBaseMethods;
	MarketBandPageMethods marketBandPageMethods;
	boolean marketOpen = false;

	/*@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws IOException {
		//BaseTest.driver.get(baseUrl);
		homePageMethods = new HomePageMethods(driver);
		marketBandPageMethods = new MarketBandPageMethods(driver);
	}*/

	@Test(description = "This test verifies sensex value on ET Home Page from API against the value on bse site", priority = 3)
	public void verifySensexCurrentValue() {
		//WebBaseMethods.scrollToTop();
		Double etSensexValue;
		String bseSiteSensexValue;
		int allowedDiff = 20;
		softAssert = new SoftAssert();
		
		bseSiteSensexValue= BusinessLogicMethods.getSensexCurrentPrice();
		Response response = given().header("Content-Type", "application/json").
				when().get(FeedRepo.MARKETWATCHBAND_DATA);
		        response.then().assertThat().statusCode(200);
		        
		String json = response.getBody().asString().replace("marketlivedata(", "").replace(")", "");
		JsonPath jsonPath = new JsonPath(json);
		etSensexValue= jsonPath.getDouble("marketBandList[0].bandServiceMetas[1].currentIndexValue");
		
		Assert.assertFalse(bseSiteSensexValue.startsWith("Error"),
				"Unable to fetch value for comparison from BSE India due to some error on their website.");
		System.out.println("ET value: " + etSensexValue + " || " + "BSE value: " + bseSiteSensexValue);
		boolean isDiffExpected = VerificationUtil.valueIsInRange(etSensexValue,
				Double.parseDouble(bseSiteSensexValue), allowedDiff);
		softAssert.assertTrue(isDiffExpected,
				"Market index value on ET and BSE site is not matching , and is beyond the expected difference of "
						+ allowedDiff + "<br>ETsiteValue:" + etSensexValue + " ,BSESiteValue: " + bseSiteSensexValue);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies nifty value on ET Home Page from API against the value on nse site", priority = 1)
	public void verifyNiftyCurrentValue() {
		Double etNifty50Value;
		String nseSiteNifty50Value;
		int allowedDiff = 20;
		softAssert = new SoftAssert();
		//etNifty50Value = marketBandPageMethods.getNifyValue();
		nseSiteNifty50Value = BusinessLogicMethods.getNiftyCurrentPrice();
		
		Response response = given().header("Content-Type", "application/json").
				when().get(FeedRepo.MARKETWATCHBAND_DATA);
		        response.then().assertThat().statusCode(200);
		        
		String json = response.getBody().asString().replace("marketlivedata(", "").replace(")", "");
		JsonPath jsonPath = new JsonPath(json);
		etNifty50Value = jsonPath.getDouble("marketBandList[0].bandServiceMetas[0].currentIndexValue");
		Assert.assertFalse(nseSiteNifty50Value.startsWith("Error"),
				"Unable to fetch value for comparison from NSE India due to some error on their website.");
		System.out.println("ET value: " + etNifty50Value + " || " + "NSE value: " + nseSiteNifty50Value);
		boolean isDiffExpected = VerificationUtil.valueIsInRange((etNifty50Value),
				Double.parseDouble(nseSiteNifty50Value), allowedDiff);
		softAssert.assertTrue(isDiffExpected,
				"Market index value on ET and NSE site is not matching , and is beyond the expected difference of "
						+ allowedDiff + "<br>ETsiteValue:" + etNifty50Value + " ,NSESiteValue: " + nseSiteNifty50Value);
		softAssert.assertAll();
	}

	@Test(enabled = false, description = "This test verifies Gold value on ET Home Page against the value on mcx site", priority = 2)
	public void verifyGoldCurrentValue() {
		WebBaseMethods.scrollToTop();
		String etGoldValue;
		String mcxSiteGoldValue;
		int allowedDiff = 50;
		softAssert = new SoftAssert();
		etGoldValue = marketBandPageMethods.getGoldValue();
		mcxSiteGoldValue = BusinessLogicMethods.getGoldPrice();
		Assert.assertFalse(mcxSiteGoldValue.startsWith("Error"),
				"Unable to fetch value for comparison from MCX India due to some error on their website.");
		System.out.println("ET value: " + etGoldValue + " || " + "MCX value: " + mcxSiteGoldValue);
		boolean isDiffExpected = VerificationUtil.valueIsInRange(Double.parseDouble(etGoldValue),
				Double.parseDouble(mcxSiteGoldValue), allowedDiff);
		softAssert.assertTrue(isDiffExpected,
				"Market index value on ET and mcx site is not matching , and is beyond the expected difference of "
						+ allowedDiff + "<br>ETsiteValue:" + etGoldValue + " ,MCXSiteValue: " + mcxSiteGoldValue);
		softAssert.assertAll();
	}

	@Test(enabled = false, description = "This test verifies usd value on ET Home Page against the value on mecklai site", priority = 4)
	public void verifyUsdInrCurrentValue() {
		WebBaseMethods.scrollToTop();
		String etUsdValue;
		String mecklaiSiteUsdValue;
		double allowedDiffPercent = 0.5;
		softAssert = new SoftAssert();
		etUsdValue = marketBandPageMethods.getUsdValue();
		mecklaiSiteUsdValue = BusinessLogicMethods.getUSDINR();
		Assert.assertFalse(mecklaiSiteUsdValue.startsWith("Error"),
				"Unable to fetch value for comparison from Mecklai due to some error on their website.");
		System.out.println("ET value: " + etUsdValue + " || " + "Mecklai value: " + mecklaiSiteUsdValue);
		boolean isDiffExpected = VerificationUtil.valueIsInRange(Double.parseDouble(etUsdValue),
				Double.parseDouble(mecklaiSiteUsdValue), allowedDiffPercent);
		softAssert.assertTrue(isDiffExpected,
				"Market index value on ET and mecklai site is not matching , and is beyond the expected difference of "
						+ allowedDiffPercent + "%.<br>ETsiteValue:" + etUsdValue + " ,Mecklai siteValue: "
						+ mecklaiSiteUsdValue);
		softAssert.assertAll();
	}

	/*@Test(description = "This test verifies the date and time on Markets Watch Band", priority = 0)
	public void verifyDateTime() {
		softAssert = new SoftAssert();
		isMarketOpen();
		if (marketOpen) {
			WebBaseMethods.navigateTimeOutHandle("https://economictimes.indiatimes.com/markets");

			DateTime pageDateTime = marketBandPageMethods.getFormattedDate();
			DateTime systemDate = new DateTime();

			Assert.assertTrue(pageDateTime != null, "Date time is not shown on Markets Watch Band");
			Assert.assertTrue(systemDate.getDayOfYear() == pageDateTime.getDayOfYear(),
					"The date shown on Markets Watch Band is not the current date instead is showing " + pageDateTime);
			softAssert.assertTrue(
					(systemDate.getMinuteOfDay() - pageDateTime.getMinuteOfDay() <= 5
							&& systemDate.getMinuteOfDay() - pageDateTime.getMinuteOfDay() >= 0),
					"Timestamp incorrect on Market Watch Band.<br>Timestamp on band: <strong>" + pageDateTime
							+ "</strong><br>Current system time: <strong>" + systemDate.toString("MMM dd, yyyy hh:mm a")
							+ "</strong>");

		} else {
			softAssert.assertTrue(true);
		}
		softAssert.assertAll();
	}

	public Boolean isMarketOpen() {
		try {
			String response = ApiHelper
					.getAPIResponse("https://json.bselivefeeds.indiatimes.com/ET_Community/holidaylist");
			String currentMarketStatus = response.split("currentMarketStatus")[1].replaceAll("[^0-9A-Za-z]", "").trim();
			int Time = Integer
					.parseInt(new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis()).substring(8));
			System.out.println("Market status : " + currentMarketStatus);
			if (currentMarketStatus.equalsIgnoreCase("LIVE") && !(Time <= 917)) {
				marketOpen = true;
			}

		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();

		}
		return marketOpen;
	}*/
	
	// @AfterMethod
		public void loginAfterMethod() throws IOException {
			driver.quit();
		}
}
