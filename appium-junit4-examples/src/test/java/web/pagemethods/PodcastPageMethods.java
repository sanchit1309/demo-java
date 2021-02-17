package web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pageobjects.PodcastPageObjects;

public class PodcastPageMethods {

	private WebDriver driver;
	private PodcastPageObjects podcastPageObjects;

	public PodcastPageMethods(WebDriver driver) {
		this.driver = driver;
		podcastPageObjects = PageFactory.initElements(driver, PodcastPageObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
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

	// clicks the facebook sharing button on the active podcast on listing page
	public boolean clickFbBtnOnActivePcLi() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getFbSharingActivePcOnLi());

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

	public String getFacebookSharedLinkTitle() {
		String fbTitle = "";
		try {
			fbTitle = podcastPageObjects.getFacebookSharedLink().getText().trim();
		} catch (NoSuchElementException e) {

		}
		return fbTitle;
	}

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

	public String getTwitterSharedLinkTitle() {
		String sharedTitle = "";
		try {
			String stg = podcastPageObjects.getTwitterStatus().getText().trim().split("Podcast:")[1];
			sharedTitle = stg.split("http")[0].trim();
			WebBaseMethods.switchToParentClosingChilds();
		} catch (Exception e) {
			sharedTitle = "notFound";
		}
		return sharedTitle;
	}

	public boolean clickFirstPodcast(String listingUrl) {
		driver.navigate().to(listingUrl);
		WaitUtil.sleep(3000);
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
			String runTime = podcastPageObjects.getRunTimePcLanding(msid).getText();
			WaitUtil.sleep(5000);
			String runTimePlay = podcastPageObjects.getRunTimePcLanding(msid).getText();

			if (!runTime.equalsIgnoreCase(runTimePlay)) {
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
			WaitUtil.sleep(4000);
			String runTime = podcastPageObjects.getRunTimePcListing().getText();
			WaitUtil.sleep(5000);
			String runTimePause = podcastPageObjects.getRunTimePcListing().getText();
			if (!runTime.equalsIgnoreCase(runTimePause)) {
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
		ignoreAutoplayAndPlayPodcastListing(msid);
		try {
			// WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getPlayPauseBtnPcLanding(msid));
			podcastPageObjects.getPlayPauseBtnPcLanding(msid).click();
			runTime = podcastPageObjects.getRunTimePcLanding(msid).getText();
			WaitUtil.sleep(5000);
			runTimePause = podcastPageObjects.getRunTimePcLanding(msid).getText();
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
		ignoreAutoplayAndPlayPodcastListing();
		try {
			// WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getPlayPauseBtnPcListing());
			podcastPageObjects.getPlayPauseBtnPcListing().click();
			WaitUtil.sleep(2000);
			runTime = podcastPageObjects.getRunTimePcListing().getText();
			WaitUtil.sleep(5000);
			runTimePause = podcastPageObjects.getRunTimePcListing().getText();
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
		ignoreAutoplayAndPausePodcastListing(msid);
		try {
			// WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getPlayPauseBtnPcLanding(msid));
			podcastPageObjects.getPlayPauseBtnPcLanding(msid).click();
			runTime = podcastPageObjects.getRunTimePcLanding(msid).getText();
			WaitUtil.sleep(5000);
			runTimePlay = podcastPageObjects.getRunTimePcLanding(msid).getText();
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
		ignoreAutoplayAndPausePodcastListing();
		try {
			//WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getPlayPauseBtnPcListing());
			podcastPageObjects.getPlayPauseBtnPcListing().click();
			runTime = podcastPageObjects.getRunTimePcListing().getText();
			WaitUtil.sleep(5000);
			runTimePlay = podcastPageObjects.getRunTimePcListing().getText();
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
			flag = WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getNextBtnPcLanding(msid));

		} catch (Exception ee) {

			ee.printStackTrace();
		}

		return flag;
	}

	public boolean clickNextPodcastBtn() {

		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getNextBtnPcListing());

		} catch (Exception ee) {

			ee.printStackTrace();
		}

		return flag;
	}

	// this functions ignore autoplay (as autoplay sometimes work and sometime
	// it does not) and plays the podcast
	public void ignoreAutoplayAndPlayPodcastListing(String msid) {

		try {
			boolean flag = checkPodcastAutoplay(msid);
			if (!flag) {
				WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getPlayPauseBtnPcLanding(msid));
			}

		} catch (Exception ee) {

			ee.printStackTrace();

		}
	}

	public void ignoreAutoplayAndPlayPodcastListing() {

		try {
			boolean flag = checkPodcastAutoplay();
			if (!flag) {
				// WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getPlayPauseBtnPcListing());
				podcastPageObjects.getPlayPauseBtnPcListing().click();
				WaitUtil.sleep(3000);

			}

		} catch (Exception ee) {

			ee.printStackTrace();

		}
	}

	public void ignoreAutoplayAndPausePodcastListing(String msid) {

		try {
			boolean flag = checkPodcastAutoplay(msid);
			if (flag) {
				// WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getPlayPauseBtnPcLanding(msid));
				podcastPageObjects.getPlayPauseBtnPcLanding(msid).click();
			}

		} catch (Exception ee) {

			ee.printStackTrace();

		}
	}

	public void ignoreAutoplayAndPausePodcastListing() {

		try {
			boolean flag = checkPodcastAutoplay();
			if (flag) {
				// WebBaseMethods.clickElementUsingJSE(podcastPageObjects.getPlayPauseBtnPcListing());
				podcastPageObjects.getPlayPauseBtnPcListing().click();
			}

		} catch (Exception ee) {

			ee.printStackTrace();

		}
	}
}
