package wap.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import wap.pageobjects.PodcastPageObjects;
import wap.pageobjects.StoryPageObjects;
import web.pagemethods.WebBaseMethods;

public class PodcastPageMethods {
	private WebDriver driver;
	private PodcastPageObjects podcastPageObjects;
	private StoryPageObjects storyPageObjects;
	private LoginPageMethods loginPageMethods;

	public PodcastPageMethods(WebDriver driver) {
		this.driver = driver;
		podcastPageObjects = PageFactory.initElements(driver, PodcastPageObjects.class);
		loginPageMethods = new LoginPageMethods(driver);
		storyPageObjects = PageFactory.initElements(driver, StoryPageObjects.class);
	}

	public List<String> getPodcastListingLinks() {
		List<String> href = new LinkedList<>();
		try {
			href = VerificationUtil.getLinkHrefList(podcastPageObjects.getPodcastListingLinks());
		} catch (NoSuchElementException e) {

		}
		return href;

	}

	public String getActivePcOnLiHeadline() {
		String activePcHeadingLi = "";
		try {
			activePcHeadingLi = WebBaseMethods.getTextUsingJSE(podcastPageObjects.getActivePcOnLi());

		} catch (Exception ee) {
			activePcHeadingLi = "Element not found";
		}
		return activePcHeadingLi;
	}

	public String getPodcastHeadlineLanding(String msid) {
		String pcHeadingLanding = "";
		try {
			pcHeadingLanding = WebBaseMethods.getTextUsingJSE(podcastPageObjects.getPcHeadingLanding(msid));

		} catch (Exception ee) {
			pcHeadingLanding = "Element not found";
		}
		return pcHeadingLanding;
	}

	public String getActivePcHeadline() {
		String activePcHeading = "";
		try {
			activePcHeading = WebBaseMethods.getTextUsingJSE(podcastPageObjects.getActivePCHeading());

		} catch (Exception ee) {
			activePcHeading = "Element not found";
		}
		return activePcHeading;
	}

	// clicks the facebook sharing button on the active podcast on listing page
	public boolean clickFbBtnOnActivePcLi() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getFbSharingActivePcOnLi());
			WaitUtil.sleep(4000);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public boolean clickFbBtnOnPcLanding(String msid) {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getFbBtnOnPcLanding(msid));

		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/*
	 * public String getFacebookSharedLinkTitle() { String fbTitle = ""; try {
	 * fbTitle = podcastPageObjects.getFacebookSharedLink().getText().trim(); }
	 * catch (NoSuchElementException e) {
	 * 
	 * } return fbTitle; }
	 */

	public boolean navigateToTwitterSharing() {

		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getTwtSharingActivePcOnLi());
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public boolean navigateToTwitterSharingPcLanding(String msid) {

		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getTwtBtnOnPcLanding(msid));
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/*
	 * public String getTwitterSharedLinkTitle() { String sharedTitle = ""; try
	 * { String stg =
	 * podcastPageObjects.getTwitterStatus().getText().trim().split("Podcast:")[
	 * 1]; sharedTitle = stg.split("http")[0].trim();
	 * WebBaseMethods.switchToParentClosingChilds(); } catch (Exception e) {
	 * sharedTitle = "notFound"; } return sharedTitle; }
	 */

	public boolean clickFirstPodcast(String listingUrl) {
		driver.navigate().to(listingUrl);
		boolean flag = false;
		try {
			boolean clicked = WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getPodcastListingLinks().get(0));
			WaitUtil.sleep(5000);
			if (driver.getCurrentUrl().contains("/podcast/") && clicked)
				flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	public List<String> getAlsoListenLinks(String msid) {
		List<String> href = new LinkedList<>();
		try {
			href = VerificationUtil.getLinkHrefList(podcastPageObjects.getAlsoListenLinks(msid));
		} catch (NoSuchElementException e) {

		}
		return href;

	}

	public List<String> getRelatedArticlesLinks(String msid) {
		List<String> href = new LinkedList<>();
		try {
			href = VerificationUtil.getLinkHrefList(podcastPageObjects.getRelatedArticlesLinks(msid));
		} catch (NoSuchElementException e) {

		}
		return href;

	}

	public List<String> getMarketsVideosLinks(String msid) {
		List<String> href = new LinkedList<>();
		try {
			href = VerificationUtil.getLinkHrefList(podcastPageObjects.getMarketsVideosLinks(msid));
		} catch (NoSuchElementException e) {

		}
		return href;

	}

	public boolean checkPodcastAutoplay(String msid) {
		boolean flag = false;
		try {

			WaitUtil.sleep(4000);
			String runTime = podcastPageObjects.getRunTimePc().getText();

			if (!runTime.equalsIgnoreCase("00:00")) {
				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return flag;
	}

	public boolean checkPodcastAutoplay() {
		boolean flag = false;
		try {
			podcastPageObjects.getListenBtnLi().click();
			WaitUtil.sleep(4000);
			String runTime = podcastPageObjects.getRunTimePc().getText();

			if (!runTime.equalsIgnoreCase("00:00")) {
				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return flag;
	}

	public boolean checkPodcastPause(String msid) {
		boolean flag = false;
		String runTime = "00:00", runTimePause = "";
		try {
			WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getPlayPauseBtnPc());
			runTime = podcastPageObjects.getRunTimePc().getText();
			WaitUtil.sleep(5000);
			runTimePause = podcastPageObjects.getRunTimePc().getText();
			if (runTime.equalsIgnoreCase(runTimePause)) {
				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public boolean checkPodcastPause() {
		boolean flag = false;
		String runTime = "00:00", runTimePause = "";
		try {
			WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getPlayPauseBtnPc());
			runTime = podcastPageObjects.getRunTimePc().getText();
			WaitUtil.sleep(5000);
			runTimePause = podcastPageObjects.getRunTimePc().getText();
			if (runTime.equalsIgnoreCase(runTimePause)) {
				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public boolean checkPodcastPlay(String msid) {
		boolean flag = false;
		String runTime = "00:00", runTimePlay = "";
		try {
			WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getPlayPauseBtnPc());
			runTime = podcastPageObjects.getRunTimePc().getText();
			WaitUtil.sleep(5000);
			runTimePlay = podcastPageObjects.getRunTimePc().getText();
			if (!runTime.equalsIgnoreCase(runTimePlay)) {
				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public boolean checkPodcastPlay() {
		boolean flag = false;
		String runTime = "00:00", runTimePlay = "";
		try {
			WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getPlayPauseBtnPc());
			runTime = podcastPageObjects.getRunTimePc().getText();
			WaitUtil.sleep(5000);
			runTimePlay = podcastPageObjects.getRunTimePc().getText();
			if (!runTime.equalsIgnoreCase(runTimePlay)) {
				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public boolean clickNextPodcastBtn(String msid) {

		boolean flag = false;
		try {
			// WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getListenBtnPc(msid));

			flag = WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getNextBtnPc());

		} catch (Exception ee) {

			ee.printStackTrace();
		}

		return flag;
	}

	public boolean clickNextPodcastBtn() {

		boolean flag = false;
		try {

			WaitUtil.sleep(3000);

			flag = WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getNextBtnPc());

		} catch (Exception ee) {

			ee.printStackTrace();
		}

		return flag;
	}

	public boolean clickListenBtn() {
		boolean flag = false;
		try {
			podcastPageObjects.getListenBtnLi().click();
			flag = true;
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;
	}

	public boolean verifyFbLogin(String email, String password) {
		boolean flag = false;
		try {

			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
				WebBaseMethods.switchToChildWindow(5);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 40, loginPageMethods.getFbEmailField());
			loginPageMethods.facebookActivity(email, password);
			WaitUtil.sleep(3000);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, podcastPageObjects.getFbPostButton());
			if (podcastPageObjects.getFbPostButton().isDisplayed())
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(driver.getCurrentUrl());
		}
		return flag;
	}

	public String getFacebookSharing() {
		String sharedTitle = "";

		try {
			sharedTitle = podcastPageObjects.getFacebookSharedTitle().getText();

		} catch (Exception ee) {

			sharedTitle = "Element not found";
		}
		return sharedTitle;
	}

}
