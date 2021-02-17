package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PodcastPageObjects {

	private WebDriver driver;

	public PodcastPageObjects(WebDriver driver) {

		this.driver = driver;
	}

	@FindBy(xpath = "//div[contains(@class,'eachStory')]//h2//a")
	private List<WebElement> podcastListingLinks;

	@FindBy(xpath = "//div[contains(@class,'activeStory')]//h2//a")
	private WebElement activePcOnLi;

	@FindBy(xpath = "//div[contains(@class,'activeStory')]//a[@class='facebook']")
	private WebElement fbSharingActivePcOnLi;

	@FindBy(xpath = "//div[contains(@class,'activeStory')]//a[@class='twitter']")
	private WebElement twtSharingActivePcOnLi;

	@FindBy(xpath = "//div[@class='spl_timeL']")
	private WebElement runTimePc;

	@FindBy(xpath = "//div[contains(@class,'spl_play')]")
	private WebElement playPauseBtnPc;

	@FindBy(xpath = "//div[contains(@class,'spl_next')]")
	private WebElement nextBtnPc;

	@FindBy(xpath = "//div[contains(@class,'activeStory')]//div//span//span[@class='pause_state']")
	private WebElement listenBtnLi;

	@FindBy(xpath = "//div[contains(@class,'activeStory')]//div//span//span[@class='play_state']")
	private WebElement listeningBtnLi;

	@FindBy(xpath = "//div[@class='spl_tHdng spl_el']")
	private WebElement activePCHeading;

	@FindBy(xpath = "//*[@id=\"u_0_p\"]")
	private WebElement fbPostButton;

	@FindBy(xpath = "//div[@class='sharerAttachmentTitle']//span")
	private WebElement facebookSharedTitle;
	// ******************Getters*******************//

	public WebDriver getDriver() {
		return driver;
	}

	public List<WebElement> getPodcastListingLinks() {
		return podcastListingLinks;
	}

	public WebElement getActivePcOnLi() {
		return activePcOnLi;
	}

	public WebElement getFbSharingActivePcOnLi() {
		return fbSharingActivePcOnLi;
	}

	public WebElement getTwtSharingActivePcOnLi() {
		return twtSharingActivePcOnLi;
	}

	public WebElement getRunTimePc() {
		return runTimePc;
	}

	public WebElement getPlayPauseBtnPc() {
		return playPauseBtnPc;
	}

	public WebElement getNextBtnPc() {
		return nextBtnPc;
	}

	public WebElement getListenBtnLi() {
		return listenBtnLi;
	}

	public WebElement getListeningBtnLi() {
		return listeningBtnLi;
	}

	public WebElement getActivePCHeading() {
		return activePCHeading;
	}

	public WebElement getFbPostButton() {
		return fbPostButton;
	}

	public WebElement getFacebookSharedTitle() {
		return facebookSharedTitle;
	}

	public List<WebElement> getAlsoListenLinks(String msid) {
		return driver
				.findElements(By.xpath("//div[@data-msid='" + msid + "']//div[contains(@class,'more_audio')]//h3//a"));
	}

	public List<WebElement> getRelatedArticlesLinks(String msid) {
		return driver.findElements(
				By.xpath("//div[@data-msid='" + msid + "']//div[contains(@class,'related_article')]//h3//a"));

	}

	public List<WebElement> getMarketsVideosLinks(String msid) {
		return driver.findElements(
				By.xpath("//div[@data-msid='" + msid + "']//div[contains(@class,'markets_videos')]//h3//a"));

	}

	public WebElement getPcHeadingLanding(String msid) {
		return driver.findElement(By.xpath("//div[@data-msid='" + msid + "']//h1[@class='pc_heading']"));

	}

	public WebElement getFbBtnOnPcLanding(String msid) {
		return driver.findElement(By.xpath("//div[@data-msid='" + msid
				+ "']//div[contains(@class,'pc_social')]//div[@class='socialLinks']//a[@class='fb']"));

	}

	public WebElement getTwtBtnOnPcLanding(String msid) {
		return driver.findElement(By.xpath("//div[@data-msid='" + msid
				+ "']//div[contains(@class,'pc_social')]//div[@class='socialLinks']//a[@class='twt']"));

	}

	public WebElement getListenBtnPc(String msid) {
		return driver.findElement(By.xpath("//div[@data-msid='" + msid + "']//span[@class='pause_state']"));

	}

	public WebElement getListeningBtnPc(String msid) {
		return driver.findElement(By.xpath("//div[@data-msid='" + msid + "']//span[@class='play_state']"));

	}

}
