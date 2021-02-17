package api.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.urlRepo.NotificationDispatcherAPIRepo;
import common.utilities.WaitUtil;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;

public class NotificationDispathcerTest extends BaseTest{
	RestAssuredConfig config;
	Config configObject = new Config();
	SoftAssert softAssert;
	String notificationID;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
				.setParam("CONNECTION_MANAGER_TIMEOUT", 1000).setParam("SO_TIMEOUT", 1000));

		config.getConnectionConfig().closeIdleConnectionsAfterEachResponse();

	}

	@Test(description = "This test verifies the response of send notification api ", priority = 0)
	public void verifySendDummyNotificationApi() {

		softAssert = new SoftAssert();
		try {
			String sendDummyNotificationAPI = NotificationDispatcherAPIRepo.SendDummyNotificationAPI;
			String jsonBody = "{    \"uuid\": 223223360591472,    \"msid\": \"75022394\",    \"synopsis\": \"But it is important to note that scarves and masks that have not been ?fitted up? properly will have gaps between its contours and the face. Contamination can come in over time through these gaps. Hence, it is important to continue to avoid proximity to others ? stay six feet away and keep the interaction as short as possible.\",    \"subject\": \"View: Let's do our bit to contain the coronavirus. Let's cover ourselves\",    \"storyDate\": \"2020-06-23 05:19:12\",    \"cnTypeId\": \"-1\",    \"idType\": \"-1\",    \"uuidType\": \"3\",    \"itemTypeId\": 0,    \"webUrl\": \"https://economictimes.indiatimes.com/industry/healthcare/biotech/healthcare/view-lets-do-our-bit-to-contain-the-coronavirus-lets-cover-ourselves/articleshow/75022394.cms\",    \"sourceId\": 1,    \"deviceType\": 0,    \"rankType\": 6,    \"assetType\": 3,    \"itemIdType\": 0,    \"alertTypeId\": 0,    \"postText\": \"View: Let's do our bit to contain the coronavirus. Let's cover ourselves\",    \"jsonText\": \"{\\\"audience\\\":{\\\"channel\\\":\\\"##channelid##\\\"},\\\"device_types\\\":[\\\"android\\\",\\\"ios\\\"],\\\"notification\\\":{\\\"android\\\":{\\\"alert\\\":\\\"Call Girish Gaurav now if you got this.\\\", \\\"time_to_live\\\": 7200, \\\"extra\\\":{\\\"category\\\":\\\"ETAppNotificationRealtime\\\",\\\"action\\\":\\\"Personalized\\\",\\\"label\\\":\\\"precos\\\",\\\"os\\\":\\\"0\\\",\\\"appName\\\":\\\"2\\\"},\\\"style\\\":{\\\"type\\\":\\\"big_picture\\\",\\\"big_picture\\\":\\\"https://etapps.indiatimes.com/photo/75022394.cms\\\",\\\"title\\\":\\\"ET\\\",\\\"summary\\\":\\\"View: Sample Text.\\\"}},\\\"ios\\\":{\\\"alert\\\":\\\"View: Sample Text.\\\", \\\"expiry\\\": 7200, \\\"extra\\\":{\\\"category\\\":\\\"ETAppNotificationRealtime\\\",\\\"action\\\":\\\"Personalized\\\",\\\"label\\\":\\\"precos\\\",\\\"os\\\":\\\"1\\\",\\\"appName\\\":\\\"2\\\"},\\\"mutable_content\\\":true,\\\"media_attachment\\\":{\\\"url\\\":\\\"https://etapps.indiatimes.com/photo/75022394.cms\\\",\\\"options\\\":{\\\"crop\\\":{\\\"x\\\":0,\\\"y\\\":0,\\\"width\\\":1,\\\"height\\\":1}}}},\\\"actions\\\":{\\\"share\\\":\\\"View: Let's do our bit to contain the coronavirus. Let's cover ourselves - https://economictimes.indiatimes.com/articleshow/75022394.cms\\\",\\\"open\\\":{\\\"type\\\":\\\"deep_link\\\",\\\"content\\\":\\\"articleshow/75022394\\\"}},\\\"interactive\\\":{\\\"type\\\":\\\"ua_share\\\",\\\"button_actions\\\":{\\\"share\\\":{\\\"share\\\":\\\"View: Let's do our bit to contain the coronavirus. Let's cover ourselves- https://economictimes.indiatimes.com/articleshow/75022394.cms\\\"}}}},\\\"options\\\": {\\\"expiry\\\": 7200}}\",    \"alertSourceId\": 20}";
			Response response = given().when().config(config).contentType("application/json").body(jsonBody)
					.post(sendDummyNotificationAPI);
			int statusCode = response.getStatusCode();
			Assert.assertTrue(statusCode == 200,
					"The status code: " + statusCode + " of the POST send dummy notification API is not 200");
			System.out.println(response.getBody().asString());
			ArrayList<String> notificationList = response.jsonPath().get("notificationList");
			Assert.assertTrue(notificationList.size() == 1,
					"The response of the POST send dummy notification API is not having id generated for the notification sent");
			System.out.println(notificationList.get(0));
			notificationID = notificationList.get(0);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "Exception occured during the test run");

		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the status of the notification sent, status code and response code of the API", dependsOnMethods = "verifySendDummyNotificationApi", priority = 1)
	public void verifyStatusOfDummyNotificationSent() {
		softAssert = new SoftAssert();
		try {
			String checkSentDummyNotificationStatus = NotificationDispatcherAPIRepo.CheckStatusOfSentNotificationAPI;
			String responseBody = "";
			int notificationStatus = -999;
			Response response = null;
			int counter = 0;
			boolean flag = false;

			while (counter < 2 && flag == false) {
				if (counter > 0) {
					System.out.println("I am waiting for 5 min countterValue" + counter);
					WaitUtil.sleep(300000);
				}
				counter++;

				response = given().when().config(config).param("id", notificationID)
						.get(checkSentDummyNotificationStatus);
				responseBody = response.getBody().asString();
				System.out.println(response.getBody().asString());
				int statusCode = response.getStatusCode();
				System.out.println(
						"The status code of the GET check sent dummy notification status api is:" + statusCode);
				Assert.assertTrue(statusCode == 200, "The status code: " + statusCode
						+ " of the GET check sent dummy notification status api is not 200");

				try {
					notificationStatus = response.jsonPath().getInt(notificationID.toString());
				} catch (NullPointerException ee) {
					ee.printStackTrace();
				}
				System.out.println("The notification status of the sent notification id:" + notificationID + " is "
						+ notificationStatus);
				if (notificationStatus != -999 && notificationStatus != -2) {
					flag = true;
				}
			}
			softAssert.assertTrue(notificationStatus != -999 && notificationStatus != -2,
					"The notification status for the dummy notification sent with notification ID: " + notificationID
							+ " is -2 or notification id is not found in the response<br>" + responseBody);
			String queueSize = response.jsonPath().getString("notificationQueueSize");
			System.out.println("The Queue size is: " + queueSize);
			softAssert.assertTrue(queueSize.equalsIgnoreCase("clear"), "The notification queue size is shown as "
					+ queueSize + " for sent notification with id: " + notificationID);
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "Exception occured during the test run");

		}

		softAssert.assertAll();
	}

}
