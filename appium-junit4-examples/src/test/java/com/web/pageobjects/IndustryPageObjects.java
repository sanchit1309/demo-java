package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IndustryPageObjects {

	private WebDriver driver;

	public IndustryPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//h2//a[contains(text(),'Auto')]")
	private WebElement autoLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Banking/Finance')]")
	private WebElement bankingFinanceLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Cons. Products')]")
	private WebElement consProductsLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Energy')]")
	private WebElement energyLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Goods/Svs')]")
	private WebElement indlGoodsSvsLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Healthcare/Biotech')]")
	private WebElement healthcareBiotechLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Services')]")
	private WebElement servicesLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Media/Entertainment')]")
	private WebElement mediaEntertainmentLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Transportation')]")
	private WebElement transportationLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Telecom')]")
	private WebElement telecomLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Tech')]")
	private List<WebElement> techLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Miscellaneous')]")
	private List<WebElement> miscellaneousLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Defence')]")
	private List<WebElement> defenceLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Sports')]")
	private List<WebElement> sportsLink;

	@FindBy(xpath = "//h2//a[contains(text(),'CSR')]")
	private List<WebElement> csrLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Environment')]")
	private List<WebElement> environmentLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Auto')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> autoHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Banking/Finance')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> bankingFinanceHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Cons. Products')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> consProductsHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Energy')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> energyHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Goods/Svs')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> indlGoodsSvsHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Healthcare/Biotech')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> healthcareBiotechHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Services')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> servicesHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Media/Entertainment')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> MediaEntertainmentHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Transportation')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> transportationHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Telecom')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> telecomHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Tech')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> techHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Defence')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> defenceHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Sports')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> sportsHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Environment')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> environmentHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'CSR')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> csrHeadlines;
	
	@FindBy(xpath = "//h2//a[contains(text(),'Miscellaneous')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> miscellaneousHeadlines;
	
	
	/////////////////////////////////////////////////////////////////////////////////////
	

	public List<WebElement> getTechLink() {
		return techLink;
	}

	public List<WebElement> getMiscellaneousLink() {
		return miscellaneousLink;
	}

	public List<WebElement> getDefenceLink() {
		return defenceLink;
	}

	public List<WebElement> getSportsLink() {
		return sportsLink;
	}

	public List<WebElement> getCsrLink() {
		return csrLink;
	}

	public List<WebElement> getEnvironmentLink() {
		return environmentLink;
	}

	public List<WebElement> getTechHeadlines() {
		return techHeadlines;
	}

	public List<WebElement> getDefenceHeadlines() {
		return defenceHeadlines;
	}

	public List<WebElement> getSportsHeadlines() {
		return sportsHeadlines;
	}

	public List<WebElement> getEnvironmentHeadlines() {
		return environmentHeadlines;
	}

	public List<WebElement> getCsrHeadlines() {
		return csrHeadlines;
	}

	public List<WebElement> getMiscellaneousHeadlines() {
		return miscellaneousHeadlines;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getAutoLink() {
		return autoLink;
	}

	public WebElement getBankingFinanceLink() {
		return bankingFinanceLink;
	}

	public WebElement getConsProductsLink() {
		return consProductsLink;
	}

	public WebElement getEnergyLink() {
		return energyLink;
	}

	public WebElement getIndlGoodsSvsLink() {
		return indlGoodsSvsLink;
	}

	public WebElement getHealthcareBiotechLink() {
		return healthcareBiotechLink;
	}

	public WebElement getServicesLink() {
		return servicesLink;
	}

	public WebElement getMediaEntertainmentLink() {
		return mediaEntertainmentLink;
	}

	public WebElement getTransportationLink() {
		return transportationLink;
	}

	public WebElement getTelecomLink() {
		return telecomLink;
	}

	public List<WebElement> getAutoHeadlines() {
		return autoHeadlines;
	}

	public List<WebElement> getBankingFinanceHeadlines() {
		return bankingFinanceHeadlines;
	}

	public List<WebElement> getConsProductsHeadlines() {
		return consProductsHeadlines;
	}

	public List<WebElement> getEnergyHeadlines() {
		return energyHeadlines;
	}

	public List<WebElement> getIndlGoodsSvsHeadlines() {
		return indlGoodsSvsHeadlines;
	}

	public List<WebElement> getHealthcareBiotechHeadlines() {
		return healthcareBiotechHeadlines;
	}

	public List<WebElement> getServicesHeadlines() {
		return servicesHeadlines;
	}

	public List<WebElement> getMediaEntertainmentHeadlines() {
		return MediaEntertainmentHeadlines;
	}

	public List<WebElement> getTransportationHeadlines() {
		return transportationHeadlines;
	}

	public List<WebElement> getTelecomHeadlines() {
		return telecomHeadlines;
	}

}
