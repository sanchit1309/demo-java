package api.tests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.utilities.ApiHelper;
import common.utilities.FileUtility;

public class AppNotificationsFeedTest {
	private SoftAssert softAssert;
	private String date = new SimpleDateFormat("yyyy-mm-dd").format(new Date());

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "recipient", "dbFlag", "emailType", "buildVersion", "platform", "failureRecipient" })
	public void setUp(@Optional("sakshi.sharma@timesinternet.in") String recipient, @Optional("false") String dbFlag,
			@Optional("nMail") String emailType, @Optional("iphver=301") String buildVersion,
			@Optional("web") String platformName, @Optional("sakshi.sharma@timesinternet.in") String failureRecipient) {
		FileUtility.writeIntoPropertiesFile(recipient, failureRecipient, "","", emailType, dbFlag);
	}
	
	@Test(description = "This test verifies duplicity and urls in app notifications feed", dataProvider = "getFeedUrl")
	public void verifyNotificationsFeed(String app, String feedUrl) {
		System.out.println("Running test for " + app + " app...");
		String output = "";
		softAssert = new SoftAssert();
		List<String> notifications;
		try {
			output = ApiHelper.getAPIResponse(feedUrl);
		} catch (RuntimeException e) {
			Assert.assertTrue(false, e.getMessage());
		}
		notifications = ApiHelper.getListOfAllValues_contains(output, "appText", "timeStamp", date, "pushDetailsList");
		Assert.assertTrue(notifications.isEmpty(), "<br>Notifications not found for date: <strong>" + date
				+ "</strong> in <a href='" + feedUrl + "'>notifications feed</a>.<br>");

		for (int i = 0; i < notifications.size(); i++) {
			if (notifications.get(i).isEmpty() || notifications.get(i) == null) {
				softAssert.assertTrue(false,
						"<br>Blank notification found in <a href='" + feedUrl + "'>notifications feed</a>.<br>");
			} else {
				List<String> notificationLink;
				List<String> notificationImageLink;
				notificationLink = ApiHelper.getListOfAllValues_contains(output, "webUrl", "appText", notifications.get(i),
						"pushDetailsList");
				if (!notificationLink.isEmpty())
					softAssert.assertFalse(ApiHelper.httpResponseCode(notificationLink.get(0)) == 404,
							"<br><a href='" + notificationLink.get(0) + "'>webUrl</a> for notification: <strong>"
									+ notifications.get(i) + "</strong> is throwing 404.");
				notificationImageLink = ApiHelper.getListOfAllValues_contains(output, "imageUrl", "appText",
						notifications.get(i), "pushDetailsList");
				if (!notificationImageLink.isEmpty())
					softAssert.assertFalse(ApiHelper.httpResponseCode(notificationImageLink.get(0)) == 404,
							"<br><a href='" + notificationImageLink.get(0) + "'>imageUrl</a> for notification: <strong>"
									+ notifications.get(i) + "</strong> is throwing 404.");
			}
		}
	}
	
	/*@Test(description = "This test verifies duplicity and urls in notifications feed of ET Main iOS app.", priority = 1)
	public void verifyNotificationsFeed_EtMainIos() {
		String output = "";
		softAssert = new SoftAssert();
		List<String> notifications;
		String feedUrl = "http://economictimes.indiatimes.com/notificationfeed_app.cms?feedtype=etjson&app=et&platform=ios";
		try {
			output = ApiHelper.getAPIResponse(feedUrl);
		} catch (RuntimeException e) {
			Assert.assertTrue(false, e.getMessage());
		}
		notifications = ApiHelper.getListOfAllValues_contains(output, "appText", "timeStamp", date, "pushDetailsList");
		Assert.assertTrue(notifications.isEmpty(), "<br>Notifications not found for date: <strong>" + date
				+ "</strong> in <a href='" + feedUrl + "'>notifications feed</a>.<br>");

		for (int i = 0; i < notifications.size(); i++) {
			if (notifications.get(i).isEmpty() || notifications.get(i) == null) {
				softAssert.assertTrue(false,
						"<br>Blank notification found in <a href='" + feedUrl + "'>notifications feed</a>.<br>");
			} else {
				List<String> notificationLink;
				List<String> notificationImageLink;
				notificationLink = ApiHelper.getListOfAllValues_contains(output, "webUrl", "appText", notifications.get(i),
						"pushDetailsList");
				if (!notificationLink.isEmpty())
					softAssert.assertFalse(ApiHelper.httpResponseCode(notificationLink.get(0)) == 404,
							"<br><a href='" + notificationLink.get(0) + "'>webUrl</a> for notification: <strong>"
									+ notifications.get(i) + "</strong> is throwing 404.");
				notificationImageLink = ApiHelper.getListOfAllValues_contains(output, "imageUrl", "appText",
						notifications.get(i), "pushDetailsList");
				if (!notificationImageLink.isEmpty())
					softAssert.assertFalse(ApiHelper.httpResponseCode(notificationImageLink.get(0)) == 404,
							"<br><a href='" + notificationImageLink.get(0) + "'>imageUrl</a> for notification: <strong>"
									+ notifications.get(i) + "</strong> is throwing 404.");
			}
		}
	}
	
	@Test(description = "This test verifies duplicity and urls in notifications feed of ET Markets Android app.", priority = 2)
	public void verifyNotificationsFeed_EtMarketsAndroid() {
		String output = "";
		softAssert = new SoftAssert();
		List<String> notifications;
		String feedUrl = "http://etp.economictimes.indiatimes.com/apppushnotificationdetails/pushednotificationdetails?days=30&recordeperPage=20&channel=etmarket&platform=android";
		try {
			output = ApiHelper.getAPIResponse(feedUrl);
		} catch (RuntimeException e) {
			Assert.assertTrue(false, e.getMessage());
		}
		notifications = ApiHelper.getListOfAllValues_contains(output, "appText", "timeStamp", date, "pushDetailsList");
		Assert.assertTrue(notifications.isEmpty(), "<br>Notifications not found for date: <strong>" + date
				+ "</strong> in <a href='" + feedUrl + "'>notifications feed</a>.<br>");

		for (int i = 0; i < notifications.size(); i++) {
			if (notifications.get(i).isEmpty() || notifications.get(i) == null) {
				softAssert.assertTrue(false,
						"<br>Blank notification found in <a href='" + feedUrl + "'>notifications feed</a>.<br>");
			} else {
				List<String> notificationLink;
				List<String> notificationImageLink;
				notificationLink = ApiHelper.getListOfAllValues_contains(output, "webUrl", "appText", notifications.get(i),
						"pushDetailsList");
				if (!notificationLink.isEmpty())
					softAssert.assertFalse(ApiHelper.httpResponseCode(notificationLink.get(0)) == 404,
							"<br><a href='" + notificationLink.get(0) + "'>webUrl</a> for notification: <strong>"
									+ notifications.get(i) + "</strong> is throwing 404.");
				notificationImageLink = ApiHelper.getListOfAllValues_contains(output, "imageUrl", "appText",
						notifications.get(i), "pushDetailsList");
				if (!notificationImageLink.isEmpty())
					softAssert.assertFalse(ApiHelper.httpResponseCode(notificationImageLink.get(0)) == 404,
							"<br><a href='" + notificationImageLink.get(0) + "'>imageUrl</a> for notification: <strong>"
									+ notifications.get(i) + "</strong> is throwing 404.");
			}
		}
	}
	
	@Test(description = "This test verifies duplicity and urls in notifications feed of ET Markets iOS app.", priority = 3)
	public void verifyNotificationsFeed_EtMarketsIos() {
		String output = "";
		softAssert = new SoftAssert();
		List<String> notifications;
		String feedUrl = "http://economictimes.indiatimes.com/notificationfeed_app.cms?feedtype=etjson&app=etmarket&platform=ios";
		try {
			output = ApiHelper.getAPIResponse(feedUrl);
		} catch (RuntimeException e) {
			Assert.assertTrue(false, e.getMessage());
		}
		notifications = ApiHelper.getListOfAllValues_contains(output, "appText", "timeStamp", date, "pushDetailsList");
		Assert.assertTrue(notifications.isEmpty(), "<br>Notifications not found for date: <strong>" + date
				+ "</strong> in <a href='" + feedUrl + "'>notifications feed</a>.<br>");

		for (int i = 0; i < notifications.size(); i++) {
			if (notifications.get(i).isEmpty() || notifications.get(i) == null) {
				softAssert.assertTrue(false,
						"<br>Blank notification found in <a href='" + feedUrl + "'>notifications feed</a>.<br>");
			} else {
				List<String> notificationLink;
				List<String> notificationImageLink;
				notificationLink = ApiHelper.getListOfAllValues_contains(output, "webUrl", "appText", notifications.get(i),
						"pushDetailsList");
				if (!notificationLink.isEmpty())
					softAssert.assertFalse(ApiHelper.httpResponseCode(notificationLink.get(0)) == 404,
							"<br><a href='" + notificationLink.get(0) + "'>webUrl</a> for notification: <strong>"
									+ notifications.get(i) + "</strong> is throwing 404.");
				notificationImageLink = ApiHelper.getListOfAllValues_contains(output, "imageUrl", "appText",
						notifications.get(i), "pushDetailsList");
				if (!notificationImageLink.isEmpty())
					softAssert.assertFalse(ApiHelper.httpResponseCode(notificationImageLink.get(0)) == 404,
							"<br><a href='" + notificationImageLink.get(0) + "'>imageUrl</a> for notification: <strong>"
									+ notifications.get(i) + "</strong> is throwing 404.");
			}
		}
	}*/
	
	@DataProvider
	public static Object[][] getFeedUrl() {
		Object[][] data = {
				{ "EtMainAndroid",
						"http://etp.economictimes.indiatimes.com/apppushnotificationdetails/pushednotificationdetails?days=30&recordeperPage=20&channel=et&platform=android" },
				{ "EtMainIos",
						"http://economictimes.indiatimes.com/notificationfeed_app.cms?feedtype=etjson&app=et&platform=ios" },
				{ "EtMarketsAndroid",
						"http://etp.economictimes.indiatimes.com/apppushnotificationdetails/pushednotificationdetails?days=30&recordeperPage=20&channel=etmarket&platform=android" },
				{ "EtMarketsIos",
						"http://economictimes.indiatimes.com/notificationfeed_app.cms?feedtype=etjson&app=etmarket&platform=ios" } };
		return data;
	}
}
