package com.pagemethods;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import app.pageobjects.NotificationHubObjects;
import common.launchsetup.BaseTest;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class NotificationHubMethods {
	AppiumDriver<?> appDriver;
	NotificationHubObjects notificationHubObjects;

	public NotificationHubMethods(AppiumDriver<?> driver) {
		appDriver = driver;
		notificationHubObjects = new NotificationHubObjects();
		PageFactory.initElements(new AppiumFieldDecorator(appDriver), notificationHubObjects);
	}

	public boolean checkLinkedNotification() {
		boolean flag = false;
		MobileElement notificationIcon = null;
		try {
			int notificationSize = notificationHubObjects.getLinkedNotification().size();
			for (int j = 0; j < notificationSize; j++) {
				try {
					notificationIcon = notificationHubObjects.getLinkedNotification().get(j);
					if (notificationIcon.isDisplayed()) {
						flag = true;
						return flag;
					}
					BaseTest.iAppCommonMethods.swipeByScreenPercentage(0.80, 0.20);
				} catch (Exception e) {
					flag = false;
				}
			}
		} catch (Exception e) {
			// do nothing
		}
		return flag;
	}

	public boolean isNotificationListShown() {
		List<MobileElement> li = notificationHubObjects.getNotificationHeading();
		return li.size() > 0;
	}

	public boolean verifyLatestNotification(int hrs) {
		Boolean isLatest = false;
		Integer postedTime = null;

		String timeVal = notificationHubObjects.getFirstNotificationDate().getText().replaceAll("\\s", "");

		if (BaseTest.platform.contains("android")) {
			timeVal = timeVal.replace("ago", "");
			if (timeVal.contains("hr")) {
				postedTime = Integer.parseInt(timeVal.replaceAll("\\D+", ""));
			} else if (timeVal.contains("min")) {
				postedTime = Integer.parseInt(timeVal.replaceAll("\\D+", "").trim()) / 60;
			} else if (timeVal.contains("day")) {
				postedTime = Integer.parseInt(timeVal.replaceAll("\\D+", "").trim()) * 24;
			}
			if (postedTime != null && postedTime <= hrs)
				isLatest = true;
			else
				isLatest = false;
		} else {
			if (!timeVal.contains("2019")) {
				timeVal = timeVal.replace("f", "");
				SimpleDateFormat formatter = new SimpleDateFormat("ddMMYYYY");
				Date date = new Date();
				timeVal = formatter.format(date) + timeVal;
				System.out.println(timeVal);
			}
			timeVal = timeVal.replace("IST", "").trim();
			DateTime dateTime = VerificationUtil.convertDateMethod(timeVal);
			DateTime currTime = new DateTime();
			if (dateTime != null && Hours.hoursBetween(dateTime, currTime).getHours() <= hrs)
				isLatest = true;
			else
				isLatest = false;
		}
		return isLatest;
	}

	public boolean verifyLinkedNotification() {
		Boolean flag = false;
		List<MobileElement> notificationHeadings = notificationHubObjects.getNotificationHeading();
		block: for (MobileElement notification : notificationHeadings) {
			try {
				notification.click();
				String pageSource = appDriver.getPageSource();
				System.out.println(pageSource);
				Pattern pattern = null;
				if (BaseTest.platform.contains("ios"))
					pattern = Pattern.compile("<XCUIElementTypeTextView.*\\svalue=\"[^//>]+");
				else

					pattern = Pattern.compile("<android.widget.TextView\\D*\\d*\\b\" text=\"[^//]*");
				Matcher matcher = pattern.matcher(pageSource);
				int counter = 0;
				while (matcher.find()) {
					counter++;
					String findOutFrom = matcher.group();
					System.out.println(findOutFrom);
					if (BaseTest.platform.contains("ios"))
						findOutFrom = findOutFrom.split("value=\"")[1].split("\"")[0];
					else
						findOutFrom = findOutFrom.split("text=\"")[1].split("\"")[0];

					if (findOutFrom.length() > 20 && counter > 2)
						flag = true;
					if (flag)
						break block;
				}
			} finally {
				BaseTest.iAppCommonMethods.navigateBack(appDriver);
			}

		}
		return flag;
	}

	public Boolean verifyLinkedNotificationText() {
		StoryPageMethods storyPageMethods = new StoryPageMethods(appDriver);
		String notificationText = null;
		HeaderPageMethods headerPageMethods = new HeaderPageMethods(appDriver);
		Boolean flag = false;
		List<MobileElement> allLinkedNot = notificationHubObjects.getNotificationHeading();
		for (int i = 0; i < allLinkedNot.size(); i++) {
			try {
				MobileElement ele = allLinkedNot.get(i);
				notificationText = ele.getText();
				System.out.println("notificationText: " + notificationText);
				ele.click();
				WaitUtil.sleep(5000);
				String clickedStoryPageHeadline = null;

				String headerText = null;

				headerText = headerPageMethods.getTopHeading();

				if (headerText != null
						&& (headerText.equalsIgnoreCase("LiveBlog") || headerText.equalsIgnoreCase("Notification")))
					clickedStoryPageHeadline = storyPageMethods.getHeadlineText();

				System.out.println("notificationTextClick : " + clickedStoryPageHeadline);
				if (clickedStoryPageHeadline == null)
					continue;
				else if (clickedStoryPageHeadline.length() > 0)
					return flag = true;

				else
					return flag = false;

			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				try {
					new StoryPageMethods(appDriver).clickVideoCloseIcon();
				} catch (Exception e) {

				}

			}
			if (flag)
				break;

		}
		return flag;
	}

	public boolean primeIconRedirection() {
		boolean flag = false;
		try {
			notificationHubObjects.getEtPrimeIcon().click();
			WaitUtil.sleep(1000);
			if (notificationHubObjects.getPrimeHome().isDisplayed()) {
				flag = true;
			}

			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
			// TODO: handle exception
		}
	}

	public boolean validateShareIcons() {
		int count = 0;
		boolean flag = false, fbFlag = false, twitterflag = false, smsFlag = false, WpFlag = false, mailFlag = false;
		try {
			if (BaseTest.platform.contains("android")) {
				List<MobileElement> notifications = notificationHubObjects.getNotificationList();

				for (int i = 0; i < notifications.size(); i++) {
					List<MobileElement> fb = notifications.get(i)
							.findElements(By.id("com.et.reader.activities:id/notification_hub_item_share_fb"));
					if (fb.size() > 0) {
						fbFlag = true;
					}

					List<MobileElement> twitter = notifications.get(i)
							.findElements(By.id("com.et.reader.activities:id/notification_hub_item_share_twitter"));
					if ((twitter.size() > 0)) {
						twitterflag = true;
					}

					List<MobileElement> wp = notifications.get(i)
							.findElements(By.id("com.et.reader.activities:id/notification_hub_item_share_whatsapp"));

					if (wp.size() > 0) {
						WpFlag = true;
					}

					List<MobileElement> mail = notifications.get(i)
							.findElements(By.id("com.et.reader.activities:id/notification_hub_item_share_mail"));

					if (mail.size() > 0) {
						mailFlag = true;
					}

					List<MobileElement> sms = notifications.get(i)
							.findElements(By.id("com.et.reader.activities:id/notification_hub_item_share_sms"));
					if (sms.size() > 0) {
						smsFlag = true;
					}

				}
				if ((smsFlag && mailFlag && WpFlag && twitterflag && fbFlag) == true) {
					flag = true;
				}
			} else {
				if (notificationHubObjects.getMailShareIcon().isDisplayed())
					flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
			// TODO: handle exception
		}
	}
}
