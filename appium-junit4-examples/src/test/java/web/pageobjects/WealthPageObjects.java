package web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WealthPageObjects {
	private WebDriver driver;

	public WealthPageObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//h2//a[contains(text(),'Tax')]")
	private WebElement taxLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Save')]")
	private WebElement saveLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Invest')]")
	private WebElement investLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Insure')]")
	private WebElement insureLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Spend')]")
	private WebElement spendLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Borrow')]")
	private WebElement borrowLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Earn')]")
	private WebElement earnLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Plan')]")
	private WebElement planLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Real Estate')]")
	private WebElement realEstateLink;

	@FindBy(xpath = "//h2//a[contains(text(),'P2P')]")
	private WebElement p2pLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Tax')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> taxHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Save')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> saveHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Invest')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> investHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Insure')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> insureHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Spend')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> spendHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Borrow')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> borrowHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Earn')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> earnHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Plan')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> planHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Real Estate')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> realEstateHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'P2P')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> p2pHeadlines;

	@FindBy(xpath = "//section[@id='featuredNews']//section//article//a[string-length()>0 and not(parent::h4[parent::article[@class='newslist hidden' and not(@style)]])]")
	private List<WebElement> topStoriesOnWealthPage;

	@FindBy(xpath = "//div[@class='flt mTop10']//b//a[text()='Wealth']/../following-sibling::a[string-length()>0]")
	private List<WebElement> keywordsUnderTopStories;

	@FindBy(xpath = "//div[@id='whatsHot']//ul//li//a[string-length()>0]")
	private List<WebElement> whatsHotSectionLinks;

	@FindBy(xpath = "//div[@id='EditWidget']//ul//li//a[string-length()>0]")
	private List<WebElement> thisWeeksEditionListLinks;

	@FindBy(xpath = "//div[@class='multimedia_slide']//ul//li[@class='mainLi']//a[string-length()>0]")
	private List<WebElement> etWealthClassroomWidgetLinks;

	@FindBy(xpath = "//div[@class='headDiv']/following-sibling::ul//li//a[string-length()>0 and child::h3]")
	private List<WebElement> etWealthMagazineArchiveWidgetLinks;

	@FindBy(xpath = "//ul[@id='this_week']//a[string-length()>0]")
	private List<WebElement> newAndUpdatesWidgetLinksRHS;

	//////////////////////////////////////////////////////////////////////////////

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getTaxLink() {
		return taxLink;
	}

	public WebElement getSaveLink() {
		return saveLink;
	}

	public WebElement getInvestLink() {
		return investLink;
	}

	public WebElement getInsureLink() {
		return insureLink;
	}

	public WebElement getSpendLink() {
		return spendLink;
	}

	public WebElement getBorrowLink() {
		return borrowLink;
	}

	public WebElement getEarnLink() {
		return earnLink;
	}

	public WebElement getPlanLink() {
		return planLink;
	}

	public WebElement getRealEstateLink() {
		return realEstateLink;
	}

	public List<WebElement> getTaxHeadlines() {
		return taxHeadlines;
	}

	public List<WebElement> getSaveHeadlines() {
		return saveHeadlines;
	}

	public List<WebElement> getInvestHeadlines() {
		return investHeadlines;
	}

	public List<WebElement> getInsureHeadlines() {
		return insureHeadlines;
	}

	public List<WebElement> getSpendHeadlines() {
		return spendHeadlines;
	}

	public List<WebElement> getBorrowHeadlines() {
		return borrowHeadlines;
	}

	public List<WebElement> getEarnHeadlines() {
		return earnHeadlines;
	}

	public List<WebElement> getPlanHeadlines() {
		return planHeadlines;
	}

	public List<WebElement> getRealEstateHeadlines() {
		return realEstateHeadlines;
	}

	public WebElement getP2pLink() {
		return p2pLink;
	}

	public List<WebElement> getP2pHeadlines() {
		return p2pHeadlines;
	}

	public List<WebElement> getTopStoriesOnWealthPage() {
		return topStoriesOnWealthPage;
	}

	public List<WebElement> getKeywordsUnderTopStories() {
		return keywordsUnderTopStories;
	}

	public List<WebElement> getWhatsHotSectionLinks() {
		return whatsHotSectionLinks;
	}

	public List<WebElement> getThisWeeksEditionListLinks() {
		return thisWeeksEditionListLinks;
	}

	public List<WebElement> getEtWealthClassroomWidgetLinks() {
		return etWealthClassroomWidgetLinks;
	}

	public List<WebElement> getEtWealthMagazineArchiveWidgetLinks() {
		return etWealthMagazineArchiveWidgetLinks;
	}

	public List<WebElement> getNewAndUpdatesWidgetLinksRHS() {
		return newAndUpdatesWidgetLinksRHS;
	}

	public List<WebElement> getKnowAllAboutLinksOfSection(String sectionName) {
		return driver.findElements(By.xpath(
				"//span[text()='KNOW All ABOUT " + sectionName + ": ']/following-sibling::span//a[string-length()>0]"));
	}
}
