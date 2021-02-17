package web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RisePageObjects {
	private WebDriver driver;

	public RisePageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//h2//a[contains(text(),'SME')]")
	private WebElement smeLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Startups')]")
	private WebElement startupsLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Policy')]")
	private WebElement policyLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Entrepreneurship')]")
	private WebElement entrepreneurshipLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Money')]")
	private WebElement moneyLink;

	@FindBy(xpath = "//h2//a[contains(text(),'IT')]")
	private WebElement securitytechLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Marketing')]")
	private WebElement marketingLink;

	@FindBy(xpath = "//h2//a[contains(text(),'HR')]")
	private WebElement hrLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Legal')]")
	private WebElement legalLink;

	@FindBy(xpath = "//h2//a[contains(text(),'SME')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> smeHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Startups')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> startupsHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Policy')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> policyHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Entrepreneurship')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> entrepreneurshipHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Money')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> moneyHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'IT')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> securitytechHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Marketing')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> marketingHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'HR')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> hrHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Legal')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> legalHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Trade')]")
	private WebElement tradeLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Trade')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> tradeHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'ProductLine')]")
	private WebElement productLineLink;

	@FindBy(xpath = "//h2//a[contains(text(),'ProductLine')]/../..//li[(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]//a[contains(text(),' ')]")
	private List<WebElement> productLineHeadlines;

	@FindBy(xpath = "//div[@class='flt mTop10']//b//a[text()='RISE']/../following-sibling::a[string-length()>0]")
	private List<WebElement> riseKeywordsUnderTopStories;

	@FindBy(xpath = "//div[@class='flt mTop10']//b//a[text()='RISE Biz Listings']/../following-sibling::a[string-length()>0]")
	private List<WebElement> riseBizListingKeywordsUnderTopStories;

	public List<WebElement> getSmeHeadlines() {
		return smeHeadlines;
	}

	public WebElement getSmeLink() {
		return smeLink;
	}

	public WebElement getStartupsLink() {
		return startupsLink;
	}

	public List<WebElement> getStartupsHeadlines() {
		return startupsHeadlines;
	}

	public WebElement getPolicyLink() {
		return policyLink;
	}

	public WebElement getEntrepreneurshipLink() {
		return entrepreneurshipLink;
	}

	public WebElement getMoneyLink() {
		return moneyLink;
	}

	public WebElement getSecuritytechLink() {
		return securitytechLink;
	}

	public WebElement getMarketingLink() {
		return marketingLink;
	}

	public WebElement getHrLink() {
		return hrLink;
	}

	public WebElement getLegalLink() {
		return legalLink;
	}

	public List<WebElement> getPolicyHeadlines() {
		return policyHeadlines;
	}

	public List<WebElement> getEntrepreneurshipHeadlines() {
		return entrepreneurshipHeadlines;
	}

	public List<WebElement> getMoneyHeadlines() {
		return moneyHeadlines;
	}

	public List<WebElement> getSecuritytechHeadlines() {
		return securitytechHeadlines;
	}

	public List<WebElement> getMarketingHeadlines() {
		return marketingHeadlines;
	}

	public List<WebElement> getHrHeadlines() {
		return hrHeadlines;
	}

	public List<WebElement> getLegalHeadlines() {
		return legalHeadlines;
	}

	public WebElement getTradeLink() {
		return tradeLink;
	}

	public List<WebElement> getTradeHeadlines() {
		return tradeHeadlines;
	}

	public List<WebElement> getProductLineHeadlines() {
		return productLineHeadlines;
	}

	public WebElement getProductLineLink() {
		return productLineLink;
	}

	public List<WebElement> getRiseKeywordsUnderTopStories() {
		return riseKeywordsUnderTopStories;
	}

	public List<WebElement> getRiseBizListingKeywords() {
		return riseBizListingKeywordsUnderTopStories;
	}

}
