package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

public class WapListingPageObjects {

	@FindBy(xpath = ".//*[@id='topStoriesData']/ul[1]/li[string-length(@class) =0 or contains(@class,'bold') or contains(@class,'no')]/a")
	private List<WebElement> newsHeadings;

	// @FindBy(xpath=".//*[@id='topStoriesData']/ul[1]/li[1]/a")
	@FindBy(xpath = "//*[@id='topStories']/div[1]/a")
	private List<WebElement> homeTopStories;

	// @FindBy(xpath="//*[@id='topStoriesMissed']/div/ul[1]/li[not(contains(@class,'midCont
	// slideOverlay') or
	// contains(@id,'advert'))]/a[not(contains(@text,'m.economicstimes.com'))]")
	// private List<WebElement> homeTopStoriesMissed;

	@FindBy(id = "openNavbarMenu")
	private WebElement blogHeader;

	@FindBy(xpath = "//*[@id='topStories']/ul[1]/li[not(contains (@class, 'colombiaAd colombiatracked reqDone') or contains(@id, 'div-clmb'))]/a")
	private List<WebElement> smallBizStories;

	@FindBy(xpath = "//*[@id='topStories']/ul[1]/li[not(contains (@class, 'colombiaAd colombiatracked reqDone') or contains(@id, 'div-clmb'))]/a[1]")
	// @FindBy(xpath="//*[@id='topStories']/div[1]/a")
	private List<WebElement> topPageStories;

	@FindBy(xpath = "//nav/a")
	private List<WebElement> pageTopSections;

	@FindBy(xpath = "//*[@id='topStories']/div[3]/div[2]")
	private WebElement moreNewsTab;

	@FindBy(xpath = "//*[@id='moreTopNews']/li/a")
	private List<WebElement> moreNewsList;

	@FindBy(xpath = "//a[@data-sectiontype='Top News']")
	private List<WebElement> topHeadlinesHref;

	@FindBy(xpath = "//li[@data-sectiontype='SubNews']//a | //a[@data-sectiontype='Top News']")
	private List<WebElement> topHeadlinesAndSubNewsHref;

	// https://m.economictimes.com/#google_vignette
	@FindBy(xpath = "//div[@id='dismiss-button']")
	private WebElement dismissGoogleVignetteAd;

	@FindBy(xpath = "//iframe[@id='google_ads_frame_rsra_0']")
	private WebElement googleVignetteAdIframe;

	@FindBy(xpath = "//ins//ins//iframe[@id='aswift_1']")
	private WebElement outerIframeGooglevignetteAd;

	@FindBy(xpath = "//a[contains(@data-sectiontype,'Read more from ')]")
	private List<WebElement> readMoreFromSectionLinks;

	@FindBy(xpath = "//section[@id='topStories']//*//a[string-length()>0 and not(contains(@href,'clmbtech'))]")
	private List<WebElement> oldViewListingPageTopStoriesHref;

	@FindBy(xpath = "//section[@id='topStories']//*//a[string-length()>0]//text()[1][not(parent::span)]")
	private List<WebElement> oldViewListingPageTopStoriesText;

	@FindBy(xpath = "//a[contains(@data-ga-onclick,'More ')]")
	private List<WebElement> techMoreFromSectionLinks;

	@FindBy(xpath = "//div[contains(@data-ga-category,'Top News')]//a[string-length()>0 and contains(@href, 'show')]")
	private List<WebElement> techPageTopNews;

	@FindBy(xpath = "//div[@id='trending']//a[string-length()>0]")
	private List<WebElement> techTopTrendingTerms;

	public List<WebElement> getNewsHeadings() {
		return newsHeadings;
	}

	public List<WebElement> getHomeTopStories() {
		return homeTopStories;
	}

	public List<WebElement> getSectionNewsHref(String sectionDiv) {
		List<WebElement> sectionNews;
		sectionNews = driver.findElements(By.xpath("//li/a[@data-sectiontype=\"" + sectionDiv + "\"]"));
		return sectionNews;
	}

	public List<WebElement> getSectionNews(String sectionDiv) {
		List<WebElement> sectionNews;
		sectionNews = driver
				.findElements(By.xpath("//li/a[@data-sectiontype=\"" + sectionDiv + "\"]//*[string-length(text())>0]"));
		return sectionNews;
	}

	@FindBy(xpath = "//meta[@content='Home']/../a")
	private List<WebElement> pageTopSectionsOld;

	/*
	 * public List<WebElement> getHomeTopStoriesMissed() { return
	 * homeTopStoriesMissed; }
	 */

	public List<WebElement> getPageTopSectionsOld() {
		return pageTopSectionsOld;
	}

	public WebElement getBlogHeader() {
		return blogHeader;
	}

	public List<WebElement> getSmallBizStories() {
		return smallBizStories;
	}

	public List<WebElement> getTopPageStories() {
		return topPageStories;
	}

	public WebElement getMoreNewsTab() {
		return moreNewsTab;
	}

	public List<WebElement> getMoreNewsList() {
		return moreNewsList;
	}

	public List<WebElement> getPageTopSections() {
		return pageTopSections;
	}

	public List<WebElement> getTopHeadlinesHref() {
		return topHeadlinesHref;
	}

	public List<WebElement> getTopHeadlinesAndSubNewsHref() {
		return topHeadlinesAndSubNewsHref;
	}

	public WebElement getDismissGoogleVignetteAd() {
		return dismissGoogleVignetteAd;
	}

	public WebElement getGoogleVignetteAdIframe() {
		return googleVignetteAdIframe;
	}

	public WebElement getOuterIframeGooglevignetteAd() {
		return outerIframeGooglevignetteAd;
	}

	public List<WebElement> getReadMoreFromSectionLinks() {
		return readMoreFromSectionLinks;
	}

	public List<WebElement> getOldViewListingPageTopStoriesText() {
		return oldViewListingPageTopStoriesText;
	}

	public List<WebElement> getOldViewListingPageTopStoriesHref() {
		return oldViewListingPageTopStoriesHref;
	}

	public List<WebElement> getPanacheSubSectionNewsHref(String sectionDiv) {
		List<WebElement> sectionNews;
		sectionNews = driver.findElements(
				By.xpath("//h2//a[text()='" + sectionDiv + "']/ancestor::div//ul//li//a[string-length()>0]"));
		return sectionNews;
	}

	public WebElement getSubSectionHeading(String sectionDiv) {
		WebElement sectionHeading;
		sectionHeading = driver.findElement(By.xpath("//h2//a[text()='" + sectionDiv + "']"));
		return sectionHeading;
	}

	public WebElement getSubSectionMoreLink(String sectionDiv) {
		WebElement sectionHeading;
		sectionHeading = driver.findElement(
				By.xpath("//h2//a[text()='" + sectionDiv + "']/ancestor::div//div[@class='viewAll hide']//a"));
		return sectionHeading;
	}

	public List<WebElement> getTechSubSectionHeading(String sectionName) {

		return driver.findElements(By.xpath("//h2[@class='secHead' and text()='" + sectionName
				+ "']/..//a[string-length()>0 and contains(@href,'show')]"));

	}

	public List<WebElement> getTechMoreFromSectionLinks() {
		return techMoreFromSectionLinks;
	}

	public List<WebElement> getTechPageTopNews() {
		return techPageTopNews;
	}

	public List<WebElement> getTechTopTrendingTerms() {
		return techTopTrendingTerms;
	}
}