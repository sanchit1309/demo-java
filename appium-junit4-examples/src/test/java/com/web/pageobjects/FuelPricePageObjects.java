package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FuelPricePageObjects {
	
	@FindBy(xpath = "//span[@class='cityname']//a")
	private List<WebElement> metroNameList;
	
	@FindBy(xpath = "//li//span[@class = 'fPrice']")
	private List<WebElement> metroPriceList;
	
	@FindBy(xpath = "//div[@class='cityPrice locwiselist']//tbody//tr//a")
	private List<WebElement> cityLinksList;
	
	@FindBy(xpath = "//div[@class='statePrice locwiselist']//tbody//tr//a")
	private List<WebElement> stateLinksList;
	
	@FindBy(xpath = "//div[@class = 'banknews-wrap']//h3/a")
	private List<WebElement> fuelNewsList;
	
	@FindBy(xpath = "//p[@class='currTime']")
	private WebElement fuelPageDate;

	@FindBy(xpath ="//input[@value='Get Price']" )
	private WebElement getPriceBtn;
	
	@FindBy(xpath = "//div[@id='line_chart']")
	private WebElement lineChart;
	
	@FindBy(xpath ="//div[@class='priceList locwiselist']/table/tbody/tr[1]/td[1]")
	private WebElement latestDate;
	
	@FindBy(xpath = "//div[@class='norec']")
	private WebElement noRecFoundMsg;
	
	@FindBy(xpath = "//div[@class = 'fuelTFilter']/a[contains(text(),'Diesel')]")
	private WebElement dieselPageLink;
	
	@FindBy(xpath = "//div[@class='pricefinder']")
	private WebElement findFuelPriceWidget;
	
	@FindBy(xpath = "//div[contains(@class, 'priceList')]/table//td[1]")
	private List<WebElement> dateList;

	public List<WebElement> getMetroNameList() {
		return metroNameList;
	}

	public List<WebElement> getMetroPriceList() {
		return metroPriceList;
	}

	public List<WebElement> getCityLinksList() {
		return cityLinksList;
	}

	public List<WebElement> getStateLinksList() {
		return stateLinksList;
	}

	public List<WebElement> getFuelNewsList() {
		return fuelNewsList;
	}

	public WebElement getFuelPageDate() {
		return fuelPageDate;
	}

	public WebElement getGetPriceBtn() {
		return getPriceBtn;
	}
	
	public WebElement getLineChart() {
		return lineChart;
	}

	public WebElement getLatestDate() {
		return latestDate;
	}

	public WebElement getNoRecFoundMsg() {
		return noRecFoundMsg;
	}

	public WebElement getFindFuelPriceWidget() {
		return findFuelPriceWidget;
	}

	public WebElement getDieselPageLink() {
		return dieselPageLink;
	}

	public List<WebElement> getDateList() {
		return dateList;
	}

	public WebElement getFuelType(WebDriver driver, String type) {
		WebElement ele = driver.findElement(By.xpath("//div[@class='fuelTFilter']//a[@data-base = '"+type+"']"));
		return ele;
	}
	
	public WebElement getHeadingDetails(WebDriver driver, String detailType) {
		List<WebElement> ele = driver.findElements(By.xpath("//div[@class='heading moretext']//span[@class="+detailType+"]"));
		return ele.get(0);
	}
}
