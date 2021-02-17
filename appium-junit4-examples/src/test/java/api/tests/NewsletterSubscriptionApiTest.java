package api.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.urlRepo.NewsletterSubscriptionService;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;

public class NewsletterSubscriptionApiTest extends BaseTest {

	RestAssuredConfig config;
	Config configObject = new Config();
	SoftAssert softAssert;
	String emailId = "";
	int serviceId = 1;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
				.setParam("CONNECTION_MANAGER_TIMEOUT", 1000).setParam("SO_TIMEOUT", 1000));

		config.getConnectionConfig().closeIdleConnectionsAfterEachResponse();
		emailId = getRandomUserEmailId();
		serviceId = Integer.parseInt(getRandomNewsletterServiceId());

	}

	@Test(description = "This test verifies the newsletter subscription and unsubscription for non logged in user")
	public void verifyNewsLetterSubscriptionServiceNonLoggedInUser() {
		softAssert = new SoftAssert();

		try {
			String statusCodeOfServiceId = checkStatusOfNewsletterServiceId(softAssert, serviceId, emailId);
			if (statusCodeOfServiceId.equalsIgnoreCase("1") || statusCodeOfServiceId.equalsIgnoreCase("5")) {
				String unsubscribeStatusCode = checkUnsubscribeNewsletterService(softAssert, serviceId, emailId);
				softAssert.assertTrue(unsubscribeStatusCode.equalsIgnoreCase("0"),
						"The subscriptionstatus code for the user emailid: " + emailId + " with serviceId: " + serviceId
								+ " is not 0 after unsubscription instead found: " + unsubscribeStatusCode);
			}
			String subscriptionService = NewsletterSubscriptionService.NewsletterSubscriptionService;
			String jsonBody = "{\"userId\":\"" + emailId + "\",\"serviceIds\":\"" + serviceId
					+ "\",\"ssoCheck\":\"false\",\"sourcePage\":1,\"src\":0}";
			Response response = given().when().config(config).contentType("application/json").body(jsonBody)
					.post(subscriptionService);
			System.out.println(response.body().asString());

			int statusCode = response.getStatusCode();
			System.out.println("-> the status code of post news letter subscription API is : " + statusCode);
			Assert.assertTrue(statusCode == 200,
					"the status code of post news letter subscription API is not 200 instead found " + statusCode);
			Map<String, String> subscriptionDetails = response
					.path("subscriptionStatus.subscriptions.find { it.serviceId == " + serviceId + " }", "");
			String subscriptionStatus = String.valueOf(subscriptionDetails.get("subscriptionStatus"));
			System.out.println(subscriptionStatus);
			softAssert.assertTrue(subscriptionStatus.equalsIgnoreCase("5"),
					"The subscription status code for the newsletter with service Id:" + serviceId
							+ " for user email id:" + emailId + " is not 5 instead is: " + subscriptionStatus);
			WaitUtil.sleep(50);
			String checkStatus = checkStatusOfNewsletterServiceId(softAssert, serviceId, emailId);
			System.out.println(checkStatus + " subscription status fetched from the check status api");
			softAssert.assertTrue(checkStatus.equalsIgnoreCase("5"),
					"The subscription status code from check status api for the newsletter with service Id:" + serviceId
							+ " for user email id:" + emailId + " is not 5 instead is: " + checkStatus);

			String unsubscribeStatus = checkUnsubscribeNewsletterService(softAssert, serviceId, emailId);
			System.out.println(unsubscribeStatus + "status after unsubscription");
			softAssert.assertTrue(unsubscribeStatus.equalsIgnoreCase("0"),
					"The subscription status code after unsubscription for the newsletter with service Id:" + serviceId
							+ " for user email id:" + emailId + " is not 0 instead is: " + unsubscribeStatus);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "Exception occured while the test run");
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the newsletter subscription and unsubscription for the logged in user")
	public void verifyNewsLetterSubscriptionServiceLoggedInUser() {
		softAssert = new SoftAssert();

		try {
			String statusCodeOfServiceId = checkStatusOfNewsletterServiceId(softAssert, serviceId, emailId);
			if (statusCodeOfServiceId.equalsIgnoreCase("1") || statusCodeOfServiceId.equalsIgnoreCase("5")) {
				String unsubscribeStatusCode = checkUnsubscribeNewsletterService(softAssert, serviceId, emailId);
				softAssert.assertTrue(unsubscribeStatusCode.equalsIgnoreCase("0"),
						"The subscriptionstatus code for the user emailid: " + emailId + " with serviceId: " + serviceId
								+ " is not 0 after unsubscription instead found: " + unsubscribeStatusCode);
			}
			String subscriptionService = NewsletterSubscriptionService.NewsletterSubscriptionService;
			String jsonBody = "{\"userId\":\"" + emailId + "\",\"serviceIds\":\"" + serviceId
					+ "\",\"ssoCheck\":\"true\",\"sourcePage\":1,\"src\":0}";
			Response response = given().when().config(config).contentType("application/json").body(jsonBody)
					.post(subscriptionService);
			System.out.println(response.body().asString());

			int statusCode = response.getStatusCode();
			System.out.println("-> the status code of post news letter subscription API is : " + statusCode);
			Assert.assertTrue(statusCode == 200,
					"the status code of post news letter subscription API is not 200 instead found " + statusCode);
			Map<String, String> subscriptionDetails = response
					.path("subscriptionStatus.subscriptions.find { it.serviceId == " + serviceId + " }", "");
			String subscriptionStatus = String.valueOf(subscriptionDetails.get("subscriptionStatus"));
			System.out.println(subscriptionStatus);
			softAssert.assertTrue(subscriptionStatus.equalsIgnoreCase("1"),
					"The subscription status code for the newsletter with service Id:" + serviceId
							+ " for user email id:" + emailId + " is not 1 instead is: " + subscriptionStatus);
			WaitUtil.sleep(50);
			String checkStatus = checkStatusOfNewsletterServiceId(softAssert, serviceId, emailId);
			System.out.println(checkStatus + " subscription status fetched from the check status api");
			softAssert.assertTrue(checkStatus.equalsIgnoreCase("1"),
					"The subscription status code from check status api for the newsletter with service Id:" + serviceId
							+ " for user email id:" + emailId + " is not 1 instead is: " + checkStatus);

			String unsubscribeStatus = checkUnsubscribeNewsletterService(softAssert, serviceId, emailId);
			System.out.println(unsubscribeStatus + "status after unsubscription");
			softAssert.assertTrue(unsubscribeStatus.equalsIgnoreCase("0"),
					"The subscription status code after unsubscription for the newsletter with service Id:" + serviceId
							+ " for user email id:" + emailId + " is not 0 instead is: " + unsubscribeStatus);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "Exception occured while the test run");
		}
		softAssert.assertAll();
	}

	public String checkStatusOfNewsletterServiceId(SoftAssert softAssert, int serviceId, String emailId) {
		String status = "";
		String checkSubscriptionStatusApi = NewsletterSubscriptionService.NewsletterSubscriptionStatusService;

		try {
			String jsonBody = "{   \"key\" : \"\",   \"ssoId\" : \"" + emailId + "\"}";
			Response response = given().when().config(config).contentType("application/json").body(jsonBody)
					.post(checkSubscriptionStatusApi);
			System.out.println(response.body().asString());

			int statusCode = response.getStatusCode();
			System.out.println("-> the status code of Check subscription status API is : " + statusCode);
			Assert.assertTrue(statusCode == 200,
					"the status code of Check subscription status API is not 200 instead found " + statusCode);
			Map<String, String> subscriptionDetails = response
					.path("subscriptionStatus.subscriptions.find { it.serviceId == " + serviceId + " }", "");
			status = String.valueOf(subscriptionDetails.get("subscriptionStatus"));
			System.out.println(status);
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false,
					"Exception occured while checking the status of subscription for emailId: " + emailId);
		}
		return status;
	}

	public String checkUnsubscribeNewsletterService(SoftAssert softAssert, int serviceId, String emailId) {
		String status = "";
		String unsubscribeNewsletterService = NewsletterSubscriptionService.NewsletterUnSubscriptionService;

		try {
			String jsonBody = "{   \"userId\" : \"" + emailId + "\",   \"serviceId\" :\"" + serviceId
					+ "\",   \"src\":0}";
			Response response = given().when().config(config).contentType("application/json").body(jsonBody)
					.post(unsubscribeNewsletterService);
			System.out.println(response.body().asString());

			int statusCode = response.getStatusCode();
			System.out.println("-> the status code of Unsubscribe newsletter API is : " + statusCode);
			Assert.assertTrue(statusCode == 200,
					"the status code of Unsubscribe newsletter API is not 200 instead found " + statusCode);
			Map<String, String> subscriptionDetails = response
					.path("subscriptionStatus.subscriptions.find { it.serviceId == " + serviceId + " }", "");
			status = String.valueOf(subscriptionDetails.get("subscriptionStatus"));
			System.out.println(status);
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false,
					"Exception occured while checking the status of unsubscription for emailId: " + emailId);
		}
		return status;
	}

	@Test(description = "This test check the number of recommended newsletter shown in the api ")
	public void verifyGetNewsSuggestApi() {
		softAssert = new SoftAssert();
		Random rand01 = new Random();
		int randomNumber = rand01.nextInt(6);
		int limit = randomNumber;

		String newsLetterSuggestionApi = NewsletterSubscriptionService.NewsletterSuggestionFeed;
		String jsonBody = "{\"secname\":\"Home Page\",\"limit\":" + limit + ",\"src\":1}";
		Response response = given().when().config(config).contentType("application/json").body(jsonBody)
				.post(newsLetterSuggestionApi);
		System.out.println(response.body().asString());

		int statusCode = response.getStatusCode();
		System.out.println("-> the status code of  news letter suggestion API is : " + statusCode);
		Assert.assertTrue(statusCode == 200,
				"the status code of post news letter subscription API is not 200 instead found " + statusCode);
		List<String> serviceIdList = response.jsonPath().getList("newsFeedList.serviceId");
		int size = serviceIdList.size();
		System.out.println(serviceIdList.size());
		System.out.println(serviceIdList);
		softAssert.assertTrue(size == limit, "The number of service id in the newsfeedlist is not equal to the limit: "
				+ limit + " instead found :" + size);
		softAssert.assertAll();
	}

	public String getRandomUserEmailId() {
		String userId = "";
		Map<String, String> testData = new HashMap<String, String>();
		try {
			Random rand01 = new Random();
			int randomNumber = rand01.nextInt(5);
			testData = ExcelUtil.getTestDataRow("NewsletterSubscriptionService", "verifyNewsletterServices",
					randomNumber + 1);
			userId = testData.get("Email");
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		System.out.println(userId);
		return userId;
	}

	public String getRandomNewsletterServiceId() {
		String serviceId = "";
		int randomNumber = 0;
		try {
			Random rand01 = new Random();
			randomNumber = rand01.nextInt(22);
			String[] serviceIdArr = "1, 2, 7, 28, 66, 96, 97, 108, 110, 1169, 1673, 1722, 2070, 2072, 2951, 3116, 3117, 3118, 3119, 3124, 3125, 3128"
					.split(", ");
			serviceId = serviceIdArr[randomNumber];
		} catch (Exception ee) {

		}
		System.out.println("The random number is:" + randomNumber + " the service id is " + serviceId);
		return serviceId;

	}
}
