	package api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.urlRepo.FeedRepo;
import common.utilities.FileUtility;
import common.utilities.VerificationUtil;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

    public class MarketsFeedTest {
        SoftAssert softAssert;
        RestAssuredConfig config;

        @BeforeSuite(alwaysRun = true)
        @Parameters({ "recipient", "dbFlag", "emailType", "failureRecipient" })
        public void setUp(@Optional("lavish.mehta@timesinternet.in") String recipient, @Optional("false") String dbFlag,
                @Optional("nMail") String emailType, @Optional("lavish.mehta@timesinternet.in") String failureRecipient) {
            FileUtility.writeIntoPropertiesFile(recipient, failureRecipient, "","", emailType, dbFlag);
            config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
                    .setParam("CONNECTION_MANAGER_TIMEOUT", 1000).setParam("SO_TIMEOUT", 1000));

            config.getConnectionConfig().closeIdleConnectionsAfterEachResponse();
        }

        @Test(description = "This test verifies the benchmarks section on ET-Main android app")
        public void benchmarksFeedAndroid() {
            System.out.println("Checking:" + FeedRepo.HOMEPAGE_BENCHMARK);
            Response response = given().when().config(config).get(FeedRepo.HOMEPAGE_BENCHMARK);
            response.then().assertThat().statusCode(200).and().time(lessThan(5000L));
            String output = response.getBody().asString();
            output = output.replace("marketlivedata", "").replaceAll("[\\[()\\]]*", "");
            Assert.assertEquals((JsonPath.given(output).getMap("$.").size()), 8,
                    "Either of sensex,nifty,gold,silver,usd/inr,dollar spot rate,eur/inr or market stat not found,api"
                            + FeedRepo.HOMEPAGE_BENCHMARK);
        }

        @Test(description = "This test verifies the benchmarks section on ET-Main ios app")
        public void benchmarksFeedIOS() {
            System.out.println("Checking:" + FeedRepo.HOMEPAGE_IOS_BENCHMARK);
            softAssert = new SoftAssert();
            Response response = given().header("Accept", "application/json").config(config).when().config(config)
                    .get(FeedRepo.HOMEPAGE_IOS_BENCHMARK);
            response.then().assertThat().statusCode(200).and().time(lessThan(4000L));
            response.then().assertThat().body(matchesJsonSchemaInClasspath("marketsBenchmarksIOS.json"));
            softAssert
                    .assertTrue(
                            JsonPath.given(response.getBody().asString())
                                    .getList("MARKET_DETAIL.MARKET_STATUS.MarketStatus").get(0).toString().length() > 0,
                            "Market status not found,api:" + FeedRepo.HOMEPAGE_IOS_BENCHMARK);

            softAssert.assertEquals(
                    JsonPath.given(response.getBody().asString()).getList("STOCK_NIFTY.INDEX_DETAIL.IndexName"),
                    Arrays.asList("NIFTY 50"),
                    "The value of index value code is not NIFTY 50:,api: " + FeedRepo.HOMEPAGE_BENCHMARK);
            softAssert.assertEquals(
                    JsonPath.given(response.getBody().asString()).getList("COMMODITY_MARKET.COMMODITY_ITEMS").size(), 3,
                    "3 commodities either gold/silver or crude oil are not found,api:" + FeedRepo.HOMEPAGE_BENCHMARK);
            softAssert.assertEquals(
                    JsonPath.given(response.getBody().asString()).getList("STOCK_SENSX.INDEX_DETAIL.IndexName"),
                    Arrays.asList("SENSEX"),
                    "The value of index value code is not SENSEX,api:" + FeedRepo.HOMEPAGE_BENCHMARK);
            softAssert.assertEquals(
                    JsonPath.given(response.getBody().asString()).getList("COMMODITY_MARKET.COMMODITY_ITEMS").size(), 3,
                    "Atleast 3 commodities expected- Gold,Silver,Crude Oil,api:" + FeedRepo.HOMEPAGE_BENCHMARK);
            softAssert.assertAll();
        }

        @Test(description = "This test verifies the gainers section on ET-Main app", dataProvider = "exchange_values")
        public void hpGainersFeed(String exchangeName, int exchange_id) {
            System.out.println(FeedRepo.HOMEPAGE_GAINERS);
            given().header("Content-Type", "application/json").queryParam("exchange", exchange_id).when()
                    .get(FeedRepo.HOMEPAGE_GAINERS).then().assertThat().statusCode(200).and()
                    .body("stocks.size()", equalTo(5)).and().time(lessThan(5000L));
        }

        @Test(description = "This test verifies the losers section on ET-Main app", dataProvider = "exchange_values")
        public void hpLosersFeed(String exchangeName, int exchange_id) {
            System.out.println(FeedRepo.HOMEPAGE_LOSERS);
            given().header("Content-Type", "application/json").queryParam("exchange", exchange_id).when()
                    .get(FeedRepo.HOMEPAGE_LOSERS).then().assertThat().statusCode(200).and()
                    .body("stocks.size()", equalTo(5)).and().time(lessThan(5000L));
        }

        @Test(description = "This test verifies the movers section on ET-Main app", dataProvider = "exchange_values")
        public void hpMoversFeed(String exchangeName, int exchange_id) {
            System.out.println(FeedRepo.HOMEPAGE_MOVERS);
            given().header("Content-Type", "application/json").queryParam("exchange", exchange_id).when()
                    .get(FeedRepo.HOMEPAGE_MOVERS).then().assertThat().statusCode(200).and()
                    .body("stocks.size()", equalTo(5)).and().time(lessThan(5000L));
        }

        @Test(description = "This test verifies the top 5 commodities section on ET-Main app")
        public void commoditiesTop5Feed() {
            System.out.println(FeedRepo.COMMODITIES_TOP);
            given().header("Content-Type", "application/json").when().config(config).get(FeedRepo.COMMODITIES_TOP).then()
                    .assertThat().statusCode(200).and().body("commodities.size()", equalTo(5)).and().time(lessThan(5000L));
        }

        @Test(description = "This test verifies the commodities future gainers on ET-Main app")
        public void commoditiesFutureGainersFeed() {
            System.out.println(FeedRepo.COMMODITYGAINERS);
            Response response = given().header("Accept", "application/json").when().config(config)
                    .get(FeedRepo.COMMODITYGAINERS);
            response.then().assertThat().statusCode(200).and().and().time(lessThan(5000L));
            Assert.assertTrue(JsonPath.given(response.getBody().asString()).getList("commodities").size() > 0,
                    "Zero commodity gainers found,api:" + FeedRepo.COMMODITYGAINERS);
        }

        @Test(description = "This test verifies the commodities future losers on ET-Main app")
        public void commoditiesFutureLosersFeed() {
            System.out.println(FeedRepo.COMMODITYLOSERS);
            Response response = given().header("Accept", "application/json").when().config(config)
                    .get(FeedRepo.COMMODITYLOSERS);
            response.then().assertThat().statusCode(200).and().and().time(lessThan(5000L));
            Assert.assertTrue(JsonPath.given(response.getBody().asString()).getList("commodities").size() > 0,
                    "Zero commodity losers found,api:" + FeedRepo.COMMODITYLOSERS);
        }

        @Test(description = "This test verifies the commodities future movers on ET-Main app")
        public void commoditiesFutureMoversFeed() {
            System.out.println("Checking:" + FeedRepo.COMMODITYMOVERS);
            Response response = given().header("Accept", "application/json").when().config(config)
                    .get(FeedRepo.COMMODITYMOVERS);
            response.then().assertThat().statusCode(200).and().and().time(lessThan(5000L));
            int actualSize = JsonPath.given(response.getBody().asString()).getList("commodities").size();
            Assert.assertTrue(actualSize > 5,
                    "Less than 5 commodity movers found,actual:" + actualSize + ",api:" + FeedRepo.COMMODITYMOVERS);
        }

        @Test(description = "This test verifies the sensex detail page on ET-Main app")
        public void sensexDetailFeed() {
            System.out.println("Checking:" + FeedRepo.SENSEXDETAILPAGE);
            Response response = given().header("Accept", "application/json").when().config(config)
                    .get(FeedRepo.SENSEXDETAILPAGE);
            response.then().assertThat().statusCode(200).and().time(lessThan(4000L));
            Assert.assertEquals(
                    JsonPath.given(response.getBody().asString()).get("index_details.scripCode2GivenByExchange"), "SENSEX",
                    "The value of script code is not SENSEX,api:" + FeedRepo.SENSEXDETAILPAGE);
            int actualSize = JsonPath.given(response.getBody().asString()).getList("stocks").size();
            Assert.assertEquals(actualSize, 15,
                    "Size of stock from the API is not 15,actual:" + actualSize + "api:" + FeedRepo.SENSEXDETAILPAGE);
        }

        @Test(description = "This test verifies the NIFTY futures detail page on ET-Main app")
        public void niftyFuturesFeed() {
            System.out.println(FeedRepo.NIFTYFUTURES);
            Response response = given().header("Accept", "application/json").when().config(config)
                    .get(FeedRepo.NIFTYFUTURES);
            response.then().assertThat().statusCode(200).and().time(lessThan(5000L));
            Assert.assertEquals(JsonPath.given(response.getBody().asString()).getList("searchresult.symbol"),
                    Arrays.asList("NIFTY"), "The value of script code is not NIFTY,api:" + FeedRepo.NIFTYFUTURES);
        }

        @Test(description = "This test verifies the NIFTY-50 overview page on ET-Main app")
        public void niftyOverviewFeed() {
            System.out.println(FeedRepo.NIFTY50DETAILPAGE);
            Response response = given().header("Accept", "application/json").when().config(config)
                    .get(FeedRepo.NIFTY50DETAILPAGE);
            response.then().assertThat().statusCode(200).and().time(lessThan(5000L));
            Assert.assertEquals(
                    JsonPath.given(response.getBody().asString()).get("index_details.scripCode2GivenByExchange"), "NIFTY",
                    "The value of script code is not NSE-50,api:" + FeedRepo.NIFTY50DETAILPAGE);
            int actualSize = JsonPath.given(response.getBody().asString()).getList("stocks").size();
            Assert.assertEquals(actualSize, 15, "Size of stock from the API is not 15,actual size:" + actualSize + ",api:"
                    + FeedRepo.NIFTY50DETAILPAGE);
        }

        @Test(description = "This test verifies the commodity GOLD detail page on ET-Main app")
        public void commodityGoldFeed() {
            System.out.println(FeedRepo.GOLDDETAILPAGE);
            Response response = given().header("Accept", "application/json").when().config(config)
                    .get(FeedRepo.GOLDDETAILPAGE);
            response.then().assertThat().statusCode(200);
            String expiryDate = "" + (JsonPath.given(response.getBody().asString()))
                    .getList("commodities.expiryDatesNew.expirydate[0]").get(0);
            String formattedDate = getExpiryDate(expiryDate);
            given().queryParam("expiry", formattedDate).when().config(config).get(FeedRepo.GOLDDETAILPAGE).then()
                    .assertThat().statusCode(200).and().time(lessThan(5000L));
            Assert.assertEquals(JsonPath.given(response.getBody().asString()).getList("commodities.spotSymbol"),
                    Arrays.asList("GOLD"), "The value of spot symbol is not GOLD, api:" + FeedRepo.GOLDDETAILPAGE);
            Assert.assertFalse(
                    JsonPath.given(response.getBody().asString()).getList("commodities.lastTradedPrice").isEmpty(),
                    "LTP Not present for expiry date " + formattedDate + " ,api:" + FeedRepo.GOLDDETAILPAGE);
        }

        @Test(description = "This test verifies the USD/INR page on ET-Main app")
        public void USDINRDetail() {
            System.out.println(FeedRepo.USDINRDETAILPAGE);
            Response response = given().header("Accept", "application/json").when().config(config)
                    .get(FeedRepo.USDINRDETAILPAGE);
            response.then().assertThat().statusCode(200).and().time(lessThan(5000L));
            Assert.assertTrue(JsonPath.given(response.getBody().asString()).get("searchresult.dataUpdated") != null,
                    "Date update not present,api:" + FeedRepo.USDINRDETAILPAGE);
            Assert.assertTrue(JsonPath.given(response.getBody().asString()).get("searchresult.spotRate") != null,
                    "Spot rate not present,api:" + FeedRepo.USDINRDETAILPAGE);
        }

        @Test(description = "This test verifies market open status")
        public void checkMarketStatus() {
            System.out.println(FeedRepo.HOLIDAYLIST);
            List<String> status = new LinkedList<String>();
            status.add("OPEN");
            status.add("CLOSED");
            status.add("Live");
            status.add("ON");
            status.add("OFF");
            Response response = given().header("Accept", "application/json").when().config(config)
                    .get(FeedRepo.HOLIDAYLIST);
            response.then().assertThat().statusCode(200).and().time(lessThan(5000L));
            Assert.assertTrue(
                    status.stream()
                            .anyMatch(s -> s.equalsIgnoreCase(
                                    JsonPath.given(response.getBody().asString()).get("currentMarketStatus"))),
                    "Unable to fetch market status,api:" + FeedRepo.HOLIDAYLIST);
        }

        @Test(description = "This test verifies Gainers intraday page", dataProvider = "exchange_values")
        public void gainersIntraday(String exchangeName, int exchange_id) {
            System.out.println(FeedRepo.GAINERSLISTINTRADAYPAGE);
            Response response = given().header("Accept", "application/json").queryParam("exchange", exchange_id).when()
                    .get(FeedRepo.GAINERSLISTINTRADAYPAGE);
            response.then().assertThat().statusCode(200).and().time(lessThan(5000L));
            int actualSize = JsonPath.given(response.getBody().asString()).getList("stocks").size();
            Assert.assertEquals(actualSize, 15, "Size of stock from the API is not 15, instead found " + actualSize
                    + ",api:" + FeedRepo.GAINERSLISTINTRADAYPAGE);
        }

        @Test(description = "This test verifies Gainers One Month page:"
                + FeedRepo.GAINERSLISTONEMONTH, dataProvider = "exchange_values")
        public void gainersOneMonth(String exchangeName, int exchange_id) {
            System.out.println(FeedRepo.GAINERSLISTONEMONTH);
            Response response = given().header("Accept", "application/json").queryParam("exchange", exchange_id).when()
                    .get(FeedRepo.GAINERSLISTONEMONTH);
            response.then().assertThat().statusCode(200).and().time(lessThan(5000L));
            int actualSize = JsonPath.given(response.getBody().asString()).getList("stocks").size();
            Assert.assertEquals(actualSize, 15,
                    "Size of stock from the API is not 15,actual:" + actualSize + ",api:" + FeedRepo.GAINERSLISTONEMONTH);
        }

        @Test(description = "This test verifies Losers intraday page", dataProvider = "exchange_values")
        public void losersIntraday(String exchangeName, int exchange_id) {
            System.out.println(FeedRepo.LOSERSLISTINTRADAYPAGE);
            Response response = given().header("Accept", "application/json").queryParam("exchange", exchange_id).when()
                    .get(FeedRepo.LOSERSLISTINTRADAYPAGE);
            response.then().assertThat().statusCode(200).and().time(lessThan(5000L));
            int actualSize = JsonPath.given(response.getBody().asString()).getList("stocks").size();
            Assert.assertEquals(actualSize, 15, "Size of stock from the API is not 15,actual:" + actualSize + ",api:"
                    + FeedRepo.LOSERSLISTINTRADAYPAGE);
        }

        @Test(description = "This test verifies Movers page", dataProvider = "exchange_values")
        public void moversData(String exchangeName, int exchange_id) {
            System.out.println(FeedRepo.VOLUMEMOVERSLISTPAGE);
            Response response = given().header("Accept", "application/jsosn").queryParam("exchange", exchange_id).when()
                    .get(FeedRepo.VOLUMEMOVERSLISTPAGE);
            response.then().assertThat().statusCode(200).and().time(lessThan(5000L));
            int size = JsonPath.given(response.getBody().asString()).getList("stocks").size();
            Assert.assertEquals(size, 15,
                    "Size of stock from the API is not 15,actual size:" + size + ",api:" + FeedRepo.VOLUMEMOVERSLISTPAGE);
        }

        @Test(description = "This test verifies company detail page")
        public void companyDetailPage() {
            System.out.println(FeedRepo.COMPANYDETAILPAGE);
            Response response = given().header("Accept", "application/json").when().config(config)
                    .get(FeedRepo.COMPANYDETAILPAGE);
            response.body().print();
            response.then().assertThat().statusCode(200).and().time(lessThan(5000L));
            int size = JsonPath.given(response.getBody().asString()).getList("searchresult").size();
            Assert.assertEquals(size, 2,
                    "Size of search result from the API is not 2,actual:" + size + ",api:" + FeedRepo.COMPANYDETAILPAGE);
        }

        @Test(description = "This test verifies ICICI prudential MF Data")
        public void MFPageData() {
            System.out.println(FeedRepo.MF_FACTSHEET_ICICPRU);
            Response response = given().header("Accept", "application/json").when().config(config)
                    .get(FeedRepo.MF_FACTSHEET_ICICPRU);
            response.then().assertThat().statusCode(200).and().time(lessThan(5000L));
            String responseBody = response.getBody().asString();
            Assert.assertTrue(JsonPath.given(responseBody).get("searchresult.latestNav") != null,
                    "Last NAV price is empty for ICICI prudential, api: " + FeedRepo.MF_FACTSHEET_ICICPRU);
            int dayOfWeek = new DateTime().getDayOfWeek();

            final int allowedStaleness;
            switch (dayOfWeek) {
            case 7:
                allowedStaleness = 2;
                break;
            case 1:
                allowedStaleness = 3;
                break;
            default:
                allowedStaleness = 1;

            }
            List<String> dateAPI = JsonPath.given(responseBody).getList("searchresult.latestNavDateFormated");
            Assert.assertTrue(
                    dateAPI.size() > 0 && VerificationUtil.diffDays(
                            VerificationUtil.convertDateToGivenFormat(dateAPI.get(0), "dd MMM yyyy"),
                            new Date()) <= allowedStaleness,
                    "<a href='" + FeedRepo.MF_FACTSHEET_ICICPRU + "'>" + "ICICI prudential MF"
                            + "</a> Nav date is not updated. Found val: " + dateAPI);

        }

        @Test(description = "This test verifies ICICI prudential MF performance data")
        public void MFPerformanceData() {
            System.out.println(FeedRepo.MF_PERFORMANCE_ICICPRU);
            Response response = given().header("Accept", "application/json").when().config(config)
                    .get(FeedRepo.MF_PERFORMANCE_ICICPRU);
            response.body().print();
            response.then().assertThat().statusCode(200).and().time(lessThan(5000L)).body("categoryPerformance.size()",
                    equalTo(1));
            // Assert.assertEquals(JsonPath.given(response.getBody().asString()).getList("consolidatedperfformance.categoryPerformance.").size(),6,
            // "Size of category performance from API is not 6");
        }

        @Test(description = "This test verifies ICICI prudential MF portfolio data")
        public void MFPortfolioData() {
            System.out.println(FeedRepo.MF_PORTFOLIO_ICICPRU);
            Response response = given().header("Accept", "application/json").when().config(config)
                    .get(FeedRepo.MF_PORTFOLIO_ICICPRU);
            response.body().print();
            response.then().assertThat().statusCode(200).and().time(lessThan(5000L));
            int size = JsonPath.given(response.getBody().asString()).getList("topsectorforportfolio").size();
            Assert.assertEquals(size, 5,
                    "Size of MF Portfolio from API is not 5,actual:" + size + ",api:" + FeedRepo.MF_PORTFOLIO_ICICPRU);
        }

        @DataProvider(name = "exchange_values")
        public Object[][] getExchangeValues() {
            Object[][] exchangeArray = new Object[2][2];
            exchangeArray[0][0] = "nse";
            exchangeArray[0][1] = 47;
            exchangeArray[1][0] = "bse";
            exchangeArray[1][1] = 50;
            return exchangeArray;
        }

        private String getExpiryDate(String date) {
            DateTimeParser[] parsers = { DateTimeFormat.forPattern("dd-MMM-yyyy").getParser() };
            DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(null, parsers).toFormatter();
            return formatter.parseDateTime(date) + "".replaceAll("T.*", "");
        }
    }
