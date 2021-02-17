package com.pagemethods;

import java.util.List;

import org.openqa.selenium.support.PageFactory;

import app.pageobjects.PodcastObjects;
import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PodcastPageMethods {

	AppiumDriver<?> appDriver;
	PodcastObjects podcastObjects;
	public static String podcastHeadingInListing;
	public static String podcastTimeInListing;

	public PodcastPageMethods(AppiumDriver<?> driver) {
		appDriver = driver;
		podcastObjects = new PodcastObjects();
		PageFactory.initElements(new AppiumFieldDecorator(appDriver), podcastObjects);
	}

	public boolean validatePodcastRedirection() {
		boolean flag = false;
		WaitUtil.sleep(1000);
		try {
			String headerText = podcastObjects.getPodcastHome().getAttribute("text");
			if (headerText.equals("ET Radio")) {
				flag = true;
			}
			return flag;

		} catch (Exception e) {
			e.printStackTrace();
			return flag;
			// TODO: handle exception
		}
	}

	public boolean validatePostcastListSize() {
		boolean flag = false;
		try {
			List<MobileElement> podcastList = podcastObjects.getPodcastListing();
			if (podcastList.size() > 0) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
	}

	public boolean clickPodcast() {

		boolean flag = false;
		podcastObjects.getPodcastListing().get(0).click();
		WaitUtil.sleep(3000);
		if (podcastObjects.getPodcastPlaying().isDisplayed()) {
			flag = true;
		}

		return flag;

	}

	public boolean validatePodcastHeaderOnListing() {
		boolean flag = false;
		try {
			String headerText = podcastObjects.getPodcastHome().getAttribute("text");
			if (headerText.equals("ET Radio")) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;

		}
	}

	public boolean verifyPodcastPause() {
		boolean flag = false;
		try {
			podcastObjects.getPodcastPlaying().click();
			WaitUtil.sleep(2000);
			if (podcastObjects.getPodcastPauseIcon().getAttribute("text").contains("LISTEN")) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
			// TODO: handle exception
		}
	}

	public boolean verifyAutoPlayFunctionalityOfPodcast() {
		boolean flag = false;
		try {
			String podPlay = podcastObjects.getPodcastPlaying().getAttribute("text");
			if (podPlay.equalsIgnoreCase("LISTENING •••")) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
	}

	public boolean verifySharingIconPresent() {
		boolean flag = false;
		if ((podcastObjects.getSmsShareIcon().isDisplayed()) && (podcastObjects.getFbShareIcon().isDisplayed())
				&& (podcastObjects.getTwitterShareIcon().isDisplayed())
				&& (podcastObjects.getWhatsAppShareIcon().isDisplayed())) {
			flag = true;
		}
		return flag;

	}

	public boolean getHeaderAndDurationOnListing() {
		boolean flag = false;

		try {

			podcastTimeInListing = podcastObjects.getFirstPodcastInfo().get(2).getAttribute("text");
			podcastHeadingInListing = podcastObjects.getFirstPodcastInfo().get(3).getAttribute("text");
			if (!podcastHeadingInListing.equals(null) && !podcastTimeInListing.equals(null)) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;

		}
	}

	public boolean validatePodcastDurationAndHeadingInDetailPage() {
		boolean flag = false, durationflag = false, HeaderFlag = false;

		String durationOnDetail = podcastObjects.getPodcastPauseIcon().getAttribute("text").split("•")[1];

		if (durationOnDetail.trim().equalsIgnoreCase(podcastTimeInListing.trim())) {
			durationflag = true;
		}

		if (podcastHeadingInListing.trim()
				.equalsIgnoreCase(podcastObjects.getPodcastHeadingOnDetailPage().getAttribute("text").trim())) {
			HeaderFlag = true;
		}
		if (durationflag && HeaderFlag) {
			flag = true;
		}
		return flag;
	}

	public boolean validateAlsoListenTagPresent() {
		boolean flag = false;
		try {
			BaseTest.iAppCommonMethods.scrollUpToElement("ALSO LISTEN");
			if (podcastObjects.getAlsoListenTag().getAttribute("text").equals("ALSO LISTEN")) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
	}

	public boolean validateAlsoListenList() {
		boolean flag = false;
		try {
			if (podcastObjects.getAlsoElementList().size() > 0) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
			// TODO: handle exception
		}
	}

	public boolean validateRedirectiontoAlsoListPodcast() {
		boolean flag = false;
		try {
			String header = podcastObjects.getAlsoListenHeader().get(0).getText().trim();
			podcastObjects.getAlsoElementList().get(0).click();
			WaitUtil.sleep(1000);
			String headerDetail = podcastObjects.getPodcastHeadingOnDetailPage().getAttribute("text").trim();
			if (header.equalsIgnoreCase(headerDetail)) {
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
