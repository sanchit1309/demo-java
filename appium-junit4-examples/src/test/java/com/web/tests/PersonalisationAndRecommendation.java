package com.web.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.urlRepo.PersonalisationAPIRepo;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;

public class PersonalisationAndRecommendation extends BaseTest {

	RestAssuredConfig config;
	Config configObject = new Config();
	public static Map<String, Map<String, String>> articleData = new HashMap<String, Map<String, String>>();
	SoftAssert softAssert;
	public static String ssoid = "";
	public static String uuid = "";
	public static String articleMsidUsed = "";
	Map<String, String> TestData = new HashMap<String, String>();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {

		config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
				.setParam("CONNECTION_MANAGER_TIMEOUT", 1000).setParam("SO_TIMEOUT", 1000));

		config.getConnectionConfig().closeIdleConnectionsAfterEachResponse();
		Random rand01 = new Random();
		int randomNumber = rand01.nextInt(4);
		TestData = ExcelUtil.getTestDataRow("TestDataSheetForPersonalisationAPI", "VerifyPersonalisationAPI",
				randomNumber + 1);

		ssoid = TestData.get("SSOID");
		uuid = TestData.get("UUID").replaceAll("\"", "");

		System.out.println("random user is " + randomNumber + "  the ssoid is : " + ssoid + " the uuid is: " + uuid);

	}

	/*
	 * This test is to verify the article data feed(etprecos feed). It verifies
	 * if the data is available in feed or not. From this we select one msid
	 * randomly and use it through other tests as well.
	 */

	@Test(description = "This test verifies the response and presence of data in the api - etprecos feed", priority = 0)
	public void verifyArticleDataFeed() {
		softAssert = new SoftAssert();
		try {
			String recosFeed = PersonalisationAPIRepo.ArticlesDetailsFeed;
			Response response = given().when().config(config).param("page", "1").param("perpage", "50")
					.param("prefix", "ta:v3").get(recosFeed);
			int statusCode = response.statusCode();
			Assert.assertTrue(statusCode == 200,
					"<br>the status code for the api - etprecos feed is not having status code 200 instead found"
							+ statusCode);
			long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
			System.out.println("-> the response time of the etprecos feed  is: " + responseTime);
			softAssert.assertTrue(responseTime < 4000,
					"the response time of the api - etprecos feed should be less than 4000ms instead found: "
							+ responseTime + " the user details are ssoid: " + ssoid + ", uuid is: " + uuid);

			List<String> msidList = response.jsonPath().getList("recosList.msid");
			System.out.println(msidList.size() + "----" + msidList);
			Assert.assertTrue(msidList != null && msidList.size() > 0,
					"<br>the article data is not there in the api - etprecos feed");
			int pageSize = Integer.valueOf(response.jsonPath().getString("page.pageSize"));
			Random rand = new Random();
			int randomIndex = rand.nextInt(pageSize);
			articleMsidUsed = msidList.get(randomIndex);

			System.out.println("random index is: " + randomIndex + " article msid is: " + articleMsidUsed);

			Map<String, String> articleDetail = response.path("recosList.find{it.msid=='" + articleMsidUsed + "'}", "");
			System.out.println(articleDetail.get("webUrl"));

			articleData.put(articleMsidUsed, articleDetail);
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "Exception occured during the test run");

		}
		softAssert.assertAll();

	}

	/*
	 * This test uses the articlemsid selected in the first test and uses it for
	 * the post activity through the capture user activity api, and this test
	 * checks weather the data is captured or not by validating the status in
	 * the response
	 */

	@Test(description = "This test verifies the response time and check that user activity is captured or not for the capture user activity API", dependsOnMethods = {
			"verifyArticleDataFeed" }, priority = 1)
	public void verifyPostUserActivityFeed() {
		try {
			softAssert = new SoftAssert();
			String articleURL = articleData.get(articleMsidUsed).get("webUrl");
			String subsec1 = articleData.get(articleMsidUsed).get("subsec1");
			String subsec2 = articleData.get(articleMsidUsed).get("subsec2");
			String subsec3 = articleData.get(articleMsidUsed).get("subsec3");
			String[] articleNames = articleData.get(articleMsidUsed).get("seolocation").split("/");
			String articleName = articleNames[articleNames.length - 1];
			System.out.println("articleName of this article is " + articleName);
			String articleMsid = articleData.get(articleMsidUsed).get("msid");

			String userActivityAPI = PersonalisationAPIRepo.CaptureUserActivityFeed;

			String jsonBody = "{    \"uuid\": \"" + uuid + "\",    \"ssoId\": \"" + ssoid
					+ "\",    \"source\": 0,    \"userIdType\": 0,    \"pageUrlDetail\": {        \"url\": \""
					+ articleURL
					+ "\",        \"header\": \"economictimes.indiatimes.com\",        \"assetType\": \"Story\",        \"isDefaultPage\": false,        \"sections\": [            \""
					+ subsec1 + "\",    \"" + subsec2 + "\",        \"" + subsec3
					+ "\"        ],        \"referrer\": \"https://economictimes.indiatimes.com/\",        \"msid\": \""
					+ articleMsid + "\",        \"pageName\": \"articleshow\",        \"articleName\": \"" + articleName
					+ "\"    }}";

			Response response = given().when().config(config).contentType("application/json").body(jsonBody)
					.post(userActivityAPI);

			int statusCode = response.getStatusCode();
			System.out.println("-> the status code of capture user activity is: " + statusCode);
			Assert.assertTrue(statusCode == 200,
					"The status code of the capture user activity is not 200 instead found: " + statusCode
							+ " the user details are ssoid: " + ssoid + ", uuid is: " + uuid
							+ "<br>The article Msid is " + articleMsidUsed);

			long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
			System.out.println("-> the response time of capture user activity is: " + responseTime);
			softAssert.assertTrue(responseTime < 2500,
					"the response time of the capture user activity api should be less than 2500ms instead found: "
							+ responseTime + " the user details are ssoid: " + ssoid + ", uuid is: " + uuid
							+ "<br>The article Msid is " + articleMsidUsed);
			String status = response.jsonPath().getString("status");
			System.out.println("-> the status of capture user activity is: " + status);
			softAssert.assertTrue(status.equalsIgnoreCase("Success"),
					"the capture user activity api is not working as the status in response is not showing success instead is showing: "
							+ status + " the user details are ssoid: " + ssoid + ", uuid is: " + uuid
							+ "<br>The article Msid is " + articleMsidUsed);
			String message = response.jsonPath().getString("message");

			System.out.println(message);
			System.out.println(response.body().asString());
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "Exception occured during the test run");

		}

		softAssert.assertAll();

	}

	/*
	 * this test checks the reponse of the get user acitivity api and validates
	 * that the articlemsid which was used in second test to check capture user
	 * activiry is present in the hits array of the api response or not
	 */

	@Test(description = "this test verifies the get user activity API response time and check the user activity is captured or not", dependsOnMethods = {
			"verifyArticleDataFeed" }, priority = 2)
	public void verifyGetApiUserActivity() {
		try {
			WaitUtil.sleep(180000);
			softAssert = new SoftAssert();
			int articleMsid = Integer.valueOf(articleMsidUsed);
			String getUserActivityApi = PersonalisationAPIRepo.GetUserHistoryFeed;
			Response response = given().when().config(config).param("ssoid", ssoid).param("start", "0")
					.param("perpage", "10").get(getUserActivityApi);

			int status = response.getStatusCode();
			System.out.println("-> the status code of get user activity is: " + status);

			Assert.assertTrue(status == 200,
					"the status code for get user activity api is not 200 instead is " + status
							+ " the user details are ssoid: " + ssoid + ", uuid is: " + uuid
							+ "<br>The article Msid is " + articleMsidUsed);

			long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
			System.out.println("-> the response Time of get user activity is:" + responseTime);

			softAssert.assertTrue(responseTime < 3000,
					"the response time of the get user activity api should be less than 3000ms but found: "
							+ responseTime + " the user details are ssoid: " + ssoid + ", uuid is: " + uuid
							+ "<br>The article Msid is " + articleMsidUsed);

			List<Integer> hitsMsids = response.jsonPath().getList("elasticResponse.hits.hits._source.msid");
			System.out.println(hitsMsids);
			Assert.assertTrue(hitsMsids != null && hitsMsids.size() > 0,
					"The API - get user activity is not having hits in the respose. The user details are ssoid: "
							+ ssoid + ", uuid is: " + uuid + "<br>The article Msid is " + articleMsidUsed);
			System.out.println(hitsMsids.size() + "------" + hitsMsids);
			System.out.println(hitsMsids.contains(articleMsid));

			softAssert.assertTrue(hitsMsids.contains(articleMsid),
					"the msid of the article " + articleMsid
							+ " on which user activity was done is not found the in get user activity API response, the user details are ssoid: "
							+ ssoid + ", uuid is: " + uuid);
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "Exception occured during the test run");

		}
		softAssert.assertAll();
	}

	/*
	 * this test verifies the reponse of the recommendation api used on ET
	 * homepage and checks if records are present or not. Through this test we
	 * validate is user is served the personalised content or not
	 */

	@Test(description = "this test verifies the response time and records count of the get recommendation api", priority = 3)
	public void verifyRecommendationFeed() {
		try {
			softAssert = new SoftAssert();
			String getRecommendationsFeed = PersonalisationAPIRepo.RecommendationDataFeed;
			Response response = given().when().config(config).param("page", "1").param("perpage", "86")
					.param("prefix", "WR:sk1:").param("uuid", uuid).get(getRecommendationsFeed);

			int statusCode = response.getStatusCode();
			System.out.println("the status code of get recommendations api is:" + statusCode);

			Assert.assertTrue(statusCode == 200, "status code of the  get recommendations API is not 200 instead found "
					+ statusCode + " the user details are ssoid: " + ssoid + ", uuid is: " + uuid);

			long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
			System.out.println("the response Time of get recommendations api is:" + responseTime);
			softAssert.assertTrue(responseTime < 4000,
					"the response time of the get recommendation api should be less than 4000ms but found: "
							+ responseTime + " the user details are ssoid: " + ssoid + ", uuid is: " + uuid);

			String pageRecords = response.jsonPath().getString("page.totalRecords");
			int records = 0;
			if (pageRecords != null) {
				records = Integer.valueOf(pageRecords);
			}

			softAssert.assertTrue(records >= 20,
					"the number of records " + records + " found in the get recommendation api is less than 20 "
							+ " the user details are ssoid: " + ssoid + ", uuid is: " + uuid);
			/*
			 * List<String> msidList =
			 * response.jsonPath().getList("recosList.msid");
			 * 
			 * System.out.println(msidList.size() + "--" + msidList);
			 */

			System.out.println(records);
			System.out.println(response.getBody().asString());
			if (records > 0) {
				List<String> recommendationType = response.jsonPath().getList("recosList.type");
				softAssert.assertTrue(recommendationType.contains("personalised"),
						"The stories(type) shown in the recommendation API is not having personalised content"
								+ " the user details are ssoid: " + ssoid + ", uuid is: " + uuid);
				System.out.println(recommendationType.size() + "-----" + recommendationType);
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "Exception occured during the test run");

		}
		softAssert.assertAll();
	}

}