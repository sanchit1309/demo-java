package api.tests;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.urlRepo.AppFeeds;
import common.utilities.DBUtil;
import common.utilities.FileUtility;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AuthAPITest {

	SoftAssert softAssert;
	RestAssuredConfig config;
	String oAuth = "https://oauth.economictimes.indiatimes.com/api/token/generate";
	String sso = "https://jsso.indiatimes.com/sso/crossapp/identity/web/verifyLoginOtpPassword";
	String email = "etapp377@teleworm.us";
	String password = "india@123";
	String ticketID = "";

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "recipient", "dbFlag", "emailType", "failureRecipient" })
	public void setUp(@Optional("sakshi.sharma@timesinternet.in") String recipient, @Optional("false") String dbFlag,
			@Optional("nMail") String emailType, @Optional("sakshi.sharma@timesinternet.in") String failureRecipient) {
		FileUtility.writeIntoPropertiesFile(recipient, failureRecipient, "","", emailType, dbFlag);
		config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
				.setParam("CONNECTION_MANAGER_TIMEOUT", 1000).setParam("SO_TIMEOUT", 1000));

		config.getConnectionConfig().closeIdleConnectionsAfterEachResponse();
	}
	
	@Test(description = "This test verifies response of oAuth API", dataProvider="getGrantType")
	public void oAuthAPI(String grantType) {

		softAssert = new SoftAssert();
		ticketID = getKeyValueFromDb(email);
		System.out.println(ticketID);
		Map<String, String> statusCodeAndr = getAuthAPIresponse(grantType, "android");
		Map<String, String> statusCodeIOS = getAuthAPIresponse(grantType, "ios");
		System.out.println(statusCodeAndr);
		System.out.println(statusCodeIOS);
		for(Map.Entry m : statusCodeAndr.entrySet()) {
			softAssert.assertEquals(m.getKey(), "200","oAuth access token API response is "+m.getKey()+ "with error message : "+m.getValue());
		}
		for(Map.Entry m : statusCodeIOS.entrySet()) {
			softAssert.assertEquals(m.getKey(), "200","oAuth access token API response is "+m.getKey()+ "with error message : "+m.getValue());
		}
		softAssert.assertAll();
	}
	

	@Test(description = "This test verifies response of Krypton API for different plan codes for iOS", dataProvider="getGroupCode")
	public void verifyPlanDataForIOS(String planGroupCode) {

		softAssert = new SoftAssert();
		String clientID = "7bi38ph9028de021754on41e7n61170e";
		ticketID = getKeyValueFromDb(email);
		String token = getUserToken(ticketID, "access_token");
		
		HashMap<String, String> apiResponseList = new LinkedHashMap<String, String>();
		HashMap<String, String> subApiResponseDataList = getSubscriptionAPIData(ticketID, token, planGroupCode, clientID);
		String URL = String.format(AppFeeds.kryptonUrl, 318, 79006431);
		System.out.println("---kryton URL--" + URL);
	
		Response krytonresponse = getKrytonAPIResponse(URL, ticketID, token, planGroupCode, clientID);
			apiResponseList.put("planId",
					JsonPath.given(krytonresponse.getBody().asString()).getString("subscriptionPlanData.planId"));
			apiResponseList.put("planName",
					JsonPath.given(krytonresponse.getBody().asString()).getString("subscriptionPlanData.planName"));	
		
		for (String apiKey : apiResponseList.keySet()) {
			System.out.println("KEY-  " + apiKey);
			String apiValue = apiResponseList.get(apiKey);
			System.out.println("**apiValue**- " + apiValue);
			String subApiValue = subApiResponseDataList.get(apiKey);
			System.out.println("**subApiiValue**- " + subApiValue);
			softAssert.assertNotNull(apiValue);
			if (apiValue.isEmpty()) {
				softAssert.assertNull(subApiValue, "No subscription plan data fetched in krypton api "+ URL);
			} else {
				softAssert.assertNotNull(subApiValue, "Subscription API value is null for Key " + apiKey + " API value " + apiValue);
				System.out.println("Result-- " + apiValue.equals(subApiValue));
				//VerificationUtil.differenceTwoLists(actual, expected)
				softAssert.assertTrue(subApiValue.contains(apiValue), "For plan group--" + planGroupCode + " - plan Name " + apiKey
						+ " API value " + apiValue + "--does not match with Subscription API Value--" + subApiValue);
			}
		}
		softAssert.assertAll();
		}
	
	@Test(description = "This test verifies response of Krypton API for different plan codes for Android", dataProvider="getGroupCode")
	public void verifyPlanDataForAndroid(String planGroupCode) {

		softAssert = new SoftAssert();
		String clientID = "3bf38ae9028de021754be41c7b61170f";
		ticketID = getKeyValueFromDb(email);
		String token = getUserToken(ticketID, "access_token");
		HashMap<String, String> apiResponseList = new LinkedHashMap<String, String>();
		HashMap<String, String> subApiResponseDataList = getSubscriptionAPIData(ticketID, token, planGroupCode, clientID);
		String URL = String.format(AppFeeds.kryptonUrl, 318, 79006431);
		System.out.println("---kryton URL--" + URL);
	
		Response krytonresponse = getKrytonAPIResponse(URL, ticketID, token, planGroupCode, clientID);
			apiResponseList.put("planId",
					JsonPath.given(krytonresponse.getBody().asString()).getString("subscriptionPlanData.planId"));
			apiResponseList.put("planName",
					JsonPath.given(krytonresponse.getBody().asString()).getString("subscriptionPlanData.planName"));	
		
		
		for (String apiKey : apiResponseList.keySet()) {
			System.out.println("KEY-  " + apiKey);
			String apiValue = apiResponseList.get(apiKey);
			System.out.println("**apiValue**- " + apiValue);
			String subApiValue = subApiResponseDataList.get(apiKey);
			System.out.println("**subApiiValue**- " + subApiValue);
			softAssert.assertNotNull(apiValue);
			if (apiValue.isEmpty()) {
				softAssert.assertNull(subApiValue, "No subscription plan data fetched in krypton api "+ URL);
			} else {
				softAssert.assertNotNull(subApiValue, "Subscription API value is null for Key " + apiKey + " API value " + apiValue);
				System.out.println("Result-- " + apiValue.equals(subApiValue));
				//VerificationUtil.differenceTwoLists(actual, expected)
				softAssert.assertTrue(subApiValue.contains(apiValue), "For plan group--" + planGroupCode + " - plan Name " + apiKey
						+ " API value " + apiValue + "--does not match with Subscription API Value--" + subApiValue);
			}
		}
		softAssert.assertAll();
		}

	@Test(description = "This test verifies response of user history API")
	public void verifyUserHistoryIOS() {
		softAssert = new SoftAssert();
		String ssoID = "4x398c8sx8reduupztiser4f7";
		String size = "100";
		Response response = getUserHistoryAPIResponse( size, ssoID);
		String val = JsonPath.given(response.getBody().asString()).getMap("elasticResponse.hits.total").get("value").toString();
		int iSize =Integer.parseInt(size);
		while(iSize<Integer.parseInt(val)) {
			iSize+=100;
		}
		response = getUserHistoryAPIResponse( String.valueOf(iSize), ssoID);;
		List<String> msids = JsonPath.given(response.getBody().asString()).getList("elasticResponse.hits.hits.msid");
		softAssert.assertEquals(val, msids.size(), "History API doesn't have same number of stories as expected");
		softAssert.assertAll();
	}
	
	public String getKeyValueFromDb(String keyVal) {
		String fetchedValue = "";
		try {
			fetchedValue = DBUtil.getRecords("Auth", "EmailID ='"  + keyVal+"'").iterator().next()[2];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(fetchedValue);
		return fetchedValue;
	}
	
	
	public String updateValueFromDB(String keyVal) {
		String fetchedValue = "";
		try {
			 DBUtil.dbUpdateColumnValues("Auth", "EmailID", email, "TicketID",keyVal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Value updated");
		return fetchedValue;
	}
	
	public Response getAuthAPIResponse(String ticketID, String grantType, String platform) {
		String clientID = "3bf38ae9028de021754be41c7b61170f";
		String deviceID = "ZY2244WRQP";
		if(platform.equals("iOS")) {
			clientID ="7bi38ph9028de021754on41e7n61170e";
			deviceID ="78E72643-AC83-40E3-9233-18F004A4DABC";
		}
		Response response = given().header("Content-Type", "application/x-www-form-urlencoded")
				.header("X-CLIENT-ID",clientID)
				.header("X-DEVICE-ID",deviceID)
				.header("X-TICKET-ID",ticketID)
				.formParam("grant_type", grantType)
				.when().config(config).post(oAuth);	
		return response;
	}

	public String generateTicketID(String email,String password) {
		JSONObject obj = new JSONObject();
		obj.put("email", email);
		obj.put("password", password);
		Response response = given().header("Content-Type", "application/json")
				.header("channel", "toi")
				.body(obj.toJSONString())
				.when().config(config).post(sso);
		response.then().assertThat().statusCode(200).and().and().time(lessThan(5000L));
		return JsonPath.given(response.getBody().asString()).getString("data.ticketId");
	}
	
	public String getUserToken(String ticketID, String grantType) {
		Response response = given().header("Content-Type", "application/x-www-form-urlencoded")
				.header("X-CLIENT-ID","3bf38ae9028de021754be41c7b61170f")
				.header("X-DEVICE-ID","ZY2244WRQP")
				.header("X-TICKET-ID",ticketID)
				.formParam("grant_type", grantType)
				.when().config(config).post(oAuth);	
		response.then().assertThat().statusCode(200).and().and().time(lessThan(5000L));
		return JsonPath.given(response.getBody().asString()).getString("data.token");
	}
	
	public Response getKrytonAPIResponse(String Uri, String ticketID, String token) {
		Response response = given().header("Accept", "application/json")
				.header("Accept-Charset", "UTF-8")
				.header("Content-Type", "application/json;charset=utf-8")
				.header("X-CLIENT-ID","3bf38ae9028de021754be41c7b61170f")
				.header("X-DEVICE-ID","ZY2244WRQP")
				.header("X-TICKET-ID",ticketID)
				.header("X-TOKEN",token)
				.header("User-Agent", "ET/400")
				.when().config(config).get(Uri);	
		response.then().assertThat().statusCode(200).and().and().time(lessThan(5000L));
		return response;
	}
	
	public Response getKrytonAPIResponse(String Uri, String ticketID, String token, String plan, String clientID) {
		Response response = given().header("Accept", "application/json")
				.header("Accept-Charset", "UTF-8")
				.header("Content-Type", "application/json;charset=utf-8")
				.header("X-CLIENT-ID",clientID)
				.header("X-DEVICE-ID","ZY2244WRQP")
				.header("X-TICKET-ID",ticketID)
				.header("X-TOKEN",token)
				.header("User-Agent", "ET/400")
				.formParam("planGroupCode", plan)
				.when().config(config).get(Uri);	
		response.then().assertThat().statusCode(200).and().and().time(lessThan(5000L));
		return response;
	}

	public HashMap<String, String> getSubscriptionAPIData(String ID, String token, String planGroupCode, String clientID) {	
		HashMap<String,String> apiResponseList = new HashMap<String,String>();
		String subURL = String.format(AppFeeds.subscriptionUrl, planGroupCode);
		System.out.println("---subscription URL--" + subURL);
		Response subResponse = getKrytonAPIResponse(subURL, ID, token, planGroupCode, clientID);	
		apiResponseList.put("planId",
				JsonPath.given(subResponse.getBody().asString()).getString("planId"));
		apiResponseList.put("planName",
				JsonPath.given(subResponse.getBody().asString()).getString("planName"));

		return apiResponseList;
	}
	public Response getUserDetails() {
		Response response = given()
				.header("content-type", "application/json")
				.header("ssec","LRo4BqMzsODZzrzwTLcjc2AY2OMomaZ0gXM8ZYdj-z4")
				.header("platform","ios")
				.header("channel","et-mobile")
				.header("deviceid","61765A94-F2CD-430E-B577-3BE3A9F356F9")
				.header("ticketid","944115a829fb40a8a8cdc2c9390bdc45")
				.when().config(config).post("https://jsso.indiatimes.com/sso/crossapp/identity/native/getUserDetails");	
		response.then().assertThat().statusCode(200).and().and().time(lessThan(5000L));
		System.out.println(response.getBody().asString());
		return response;
	}
	
	
	public Response getUserHistoryAPIResponse(String size,String ssoID) {
		String URL = String.format(AppFeeds.UserHistoryFeed, ssoID,"0",size );
		Response response = given()
				.when().config(config).get(URL);
		response.then().assertThat().statusCode(200).and().and().time(lessThan(5000L));
		System.out.println(response.getBody().asString());
		return response;
	}

	public Map<String, String> getAuthAPIresponse(String grantType, String platform) {
		Response response = getAuthAPIResponse(ticketID, grantType, platform);
		response.then().assertThat().statusCode(200).and().and().time(lessThan(5000L));
		String statusCode = JsonPath.given(response.getBody().asString()).getString("code");
		String errMessage = JsonPath.given(response.getBody().asString()).getString("message");
		if(statusCode!="200" && errMessage.equalsIgnoreCase("INVALID_TICKET_ID")) {
			JSONObject obj = new JSONObject();
			obj.put("email", email);
			obj.put("password", password);
			Response res = given().header("Content-Type", "application/json")
					.header("channel","toi").body(obj.toJSONString())
					.post(sso);	
					res.then().assertThat().statusCode(200).and().and().time(lessThan(5000L));							
			ticketID = 	JsonPath.given(res.getBody().asString()).getString("data.ticketId");	
			response = getAuthAPIResponse(ticketID, grantType,platform);
			statusCode = JsonPath.given(response.getBody().asString()).getString("code");
			updateValueFromDB(ticketID);
		}
		Map<String, String> authAPIRes = new HashMap<>();
		authAPIRes.put(statusCode, errMessage);
		return authAPIRes;
	}
	
	@DataProvider
	public static Object[][] getGrantType() {
		Object[][] data = new Object[2][1];
		data[0][0] = "access_token";
		data[1][0] = "refresh_token";

		return data;
	}
	
	@DataProvider
	public static Object[][] getGroupCode() {
		Object[][] data = new Object[3][1];
		data[0][0] = "plangroup_a";
		data[1][0] = "plangroup_b";
		data[2][0] = "plangroup_c";

		return data;
	}
	
	
	
}
