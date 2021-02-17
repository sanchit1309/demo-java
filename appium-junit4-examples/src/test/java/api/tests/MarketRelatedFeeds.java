package api.tests;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.common.collect.Streams;

import common.launchsetup.Config;
import common.utilities.ApiHelper;
import common.utilities.FileUtility;
import common.utilities.VerificationUtil;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class MarketRelatedFeeds {
	SoftAssert softAssert;
	private String appVerison;
	private String baseUrl = new Config()
			.fetchConfig(new File("./src/main/resources/properties/Web.properties"), "HomeUrl")
			.replace("https", "http");
	RestAssuredConfig config;

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "recipient", "dbFlag", "emailType", "failureRecipient", "appVerison" })
	public void setUp(@Optional("sakshi.sharma@timesinternet.in") String recipient, @Optional("false") String dbFlag,
			@Optional("nMail") String emailType, @Optional("sakshi.sharma@timesinternet.in") String failureRecipient,
			@Optional("310") String appVerison) {
		this.appVerison = appVerison;
		FileUtility.writeIntoPropertiesFile(recipient, failureRecipient, "","", emailType, dbFlag);
		config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
				.setParam("CONNECTION_MANAGER_TIMEOUT", 1000).setParam("SO_TIMEOUT", 1000));

		config.getConnectionConfig().closeIdleConnectionsAfterEachResponse();
	}

	@Test(description = "This test verfies the lhs feed", priority = 0)
	public void lhsFeeds() {
		softAssert = new SoftAssert();
		String feed = String.format(LHSFEED, appVerison);
		System.out.println("LHS API:" + feed);
		Response response = given().when().config(config).get(feed);
		int status1 = response.statusCode();
		softAssert.assertEquals(status1, 200, "Feed:" + feed + " is not giving 200, instead is " + status1);
		long time1 = response.time();
		softAssert.assertTrue(time1 < 5000L,
				"Feed:" + feed + " is not giving response in 5 secs, instead is taking " + time1);
		String output = response.getBody().asString();
		softAssert.assertTrue((JsonPath.given(output).getMap("$.").size()) > 0,
				"Feed:" + feed + " is giving blank response");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the lhs section on ET-Main android app", dataProvider = "lhs", priority = 1)
	public void lhsSection(String action) {
		softAssert = new SoftAssert();
		if (action.contains("economictimes.com") || action.trim().length() < 1)
			return;
		action = !action.toString().startsWith("http") ? baseUrl + action : action;
		String api = (String) action;
		System.out.println("Running for:" + api);
		if (api.contains("globalcurrency")) {
			api = api.replace("{continent}", "ASIA").replace("{currencycode}", "INR").replace("{period}", "1D");
		}
		try {
			Response response2 = given().when().get(api);
			int status2 = response2.statusCode();
			softAssert.assertEquals(status2, 200, "Feed:" + api + " is not giving 200, instead is " + status2);
			long time2 = response2.time();
			softAssert.assertTrue(time2 < 5000L,
					"Feed:" + api + " is not giving response in 5 secs, instead is taking " + time2);
			String output2 = response2.getBody().asString();

			if (JsonPath.given(output2).get("$.").getClass().getSimpleName().equals("HashMap")) {
				softAssert.assertTrue(JsonPath.given(output2).getMap("$.").size() > 0,
						"Feed:" + api + " is giving blank response");
			} else if (JsonPath.given(output2).get("$.").getClass().getSimpleName().equals("ArrayList")) {
				softAssert.assertTrue(JsonPath.given(output2).getList("$.").size() > 0,
						"Feed:" + api + " is giving blank response");
			}

			if (api.contains("homefeed_markets")) {
				String pages = JsonPath.given(output2).get("pn.tp");
				int counter = Integer.valueOf(pages);
				int i = 1;
				while (i <= counter) {
					String temp = api + "&curpg=" + i;
					System.out.println(temp);
					Response response3 = given().when().get(temp);
					String output3 = response3.getBody().asString();
					softAssert.assertTrue((JsonPath.given(output3).getList("Item.sn").size()) > 0,
							"Feed:" + api + " is giving blank response is not showing expected section on page:" + i);
					int status3 = response3.statusCode();
					softAssert.assertEquals(status3, 200, "Feed:" + temp + " is not giving 200, instead is " + status3);
					long time3 = response3.time();
					softAssert.assertTrue(time3 < 5000L,
							"Feed:" + temp + " is not giving response in 5 secs, instead is taking " + time3);
					i++;
				}
			} else if ((api.contains("BuyerSellerMover") || api.contains("52Weeks") || api.contains("Shocker")
					|| api.contains("gainers") || api.contains("losers") || api.contains("movers")) && !api.contains("mcx")) {
				// api = api.replace("pageno=1&", "");
				int pageCount = getTotalPages(output2);
				int i = 1;
				List<String> compNames = new LinkedList<String>();
				int dupcount = 0;
				while (i <= pageCount && dupcount < 9) {
					String temp = i > 1 ? api + "&pageno=" + i : api;
					System.out.println("Paginated:"+temp);
					Response response3 = given().when().get(temp);
					String output3 = response3.getBody().asString();
					List<String> compShortName = JsonPath.given(output3).getList("stocks.companyShortName");
					if(compShortName==null)
						compShortName = JsonPath.given(output3).getList("commodities.commodityName");
					if(compShortName==null)
						compShortName = JsonPath.given(output3).getList("searchresult.symbol");
					softAssert.assertTrue(compShortName!=null &&(compShortName.size()) > 0,
							"Feed:" + api + " is giving blank response is not showing expected comp name on page:" + i);
					int status3 = response3.statusCode();
					softAssert.assertEquals(status3, 200, "Feed:" + temp + " is not giving 200, instead is " + status3);
					long time3 = response3.time();
					softAssert.assertTrue(time3 < 5000L,
							"Feed:" + temp + " is not giving response in 5 secs, instead is taking " + time3);
					i++;

					compNames.addAll(compShortName);
					List<String> dupCompanies = VerificationUtil.isListUnique(compNames);
					dupcount = dupCompanies.size();
					softAssert.assertTrue(dupcount < 9, "Duplicate entries found in " + temp + "->" + dupCompanies);

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			softAssert.assertTrue(false, "Exception occured:" + e.getMessage());
		}
		softAssert.assertAll();
	}

	@DataProvider(name = "lhs")
	public Object[] getUrlsFromLHS() {
		String feed = String.format(LHSFEED, appVerison);
		Response response = given().when().config(config).get(feed);
		String output = response.getBody().asString();
		List<String> listOne = JsonPath.given(output).getList("Item.ss.du");
		List<String> listTwo = JsonPath.given(output).getList("Item.ss.ss.du");
		System.out.println(listOne + "\n" + listTwo);
		if (listOne.size() > 0)
			listOne = getItemsOutAsList(listOne);
		if (listTwo.size() > 0)
			listTwo = getItemsOutAsList(listTwo);
		List<String> requestUrls = Streams.concat(listOne.stream(), listTwo.stream()).collect(Collectors.toList());
		requestUrls.removeAll(Collections.singleton(null));
		System.out.println(requestUrls);
		return requestUrls.toArray();
	}

	private List<String> getItemsOutAsList(List<?> li) {
		li.removeAll(Collections.singleton(null));
		List<String> subList = new LinkedList<>();

		li.forEach(el -> {
			if (el.getClass().getSimpleName().equals("ArrayList")) {
				// subList.addAll(Arrays.asList(((String) el).split(",")));
				List<String> temp = (List<String>) el;

				subList.addAll(getItemsOutAsList(temp));

			} else {
				subList.add((String) el);
			}

		});
		return subList;
	}
	public static void main(String[] args) {
		String api="http://mcxlivefeeds.indiatimes.com/ET_MCX/MCXLiveController?pagesize=10&pageno=1&statstype=gainers&ismobile=true&language=ENG";
		String output=ApiHelper.getAPIResponse(api);
		System.out.println(getTotalPages(output));
	}

	private static int getTotalPages(String output) {
		int totalno = 0;
		try {
			String no = JsonPath.given(output).getString("pageSummary.totalPages");
			System.out.println(no);
			if (no == null) {
				no = JsonPath.given(output).getString("pagesummary.totalpages");
			}
			totalno = Integer.valueOf(no);
		} catch (NumberFormatException e) {
			e.printStackTrace();

		}
		return totalno;
	}

	private final String LHSFEED = "http://economictimes.indiatimes.com/dynamicfeed_marketsnew.cms?feedtype=etjson&nav=lhs&language=ENG&platform=mktandroid&andver=%s";

}