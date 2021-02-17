package com.web.pagemethods;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.HomePageObjects;
import web.pageobjects.IndustryPageObjects;

public class IndustryPageMethods {

	private WebDriver driver;
	private HomePageObjects homePageObjects;
	private IndustryPageObjects industryPageObjects;

	public IndustryPageMethods(WebDriver driver) {
		this.driver = driver;
		homePageObjects = PageFactory.initElements(driver, HomePageObjects.class);
		industryPageObjects = PageFactory.initElements(driver, IndustryPageObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
	}

	public IndustryPageObjects getIndustryPageObjects() {
		return industryPageObjects;
	}

	public String getAutoLink() {
		return industryPageObjects.getAutoLink().getAttribute("href");
	}

	public List<WebElement> getAutoHeadlines() {
		return industryPageObjects.getAutoHeadlines();
	}

	public String getBankingFinanceLink() {
		return industryPageObjects.getBankingFinanceLink().getAttribute("href");
	}

	public List<WebElement> getBankingFinanceHeadlines() {
		return industryPageObjects.getBankingFinanceHeadlines();
	}

	public String getConsProductsLink() {
		return industryPageObjects.getConsProductsLink().getAttribute("href");
	}

	public List<WebElement> getConsProductsHeadlines() {
		return industryPageObjects.getConsProductsHeadlines();
	}

	public String getEnergyLink() {
		return industryPageObjects.getEnergyLink().getAttribute("href");
	}

	public List<WebElement> getEnergyHeadlines() {
		return industryPageObjects.getEnergyHeadlines();
	}

	public String getHealthcareBiotechLink() {
		return industryPageObjects.getHealthcareBiotechLink().getAttribute("href");
	}

	public List<WebElement> getHealthcareBiotechHeadlines() {
		return industryPageObjects.getHealthcareBiotechHeadlines();
	}

	public String getServicesLink() {
		return industryPageObjects.getServicesLink().getAttribute("href");
	}

	public List<WebElement> getServicesHeadlines() {
		return industryPageObjects.getServicesHeadlines();
	}

	public String getMediaEntertainmentLink() {
		return industryPageObjects.getMediaEntertainmentLink().getAttribute("href");
	}

	public List<WebElement> getMediaEntertainmentHeadlines() {
		return industryPageObjects.getMediaEntertainmentHeadlines();
	}

	public String getTransportationLink() {
		return industryPageObjects.getTransportationLink().getAttribute("href");
	}

	public List<WebElement> getTransportationHeadlines() {
		return industryPageObjects.getTransportationHeadlines();
	}

	public String getTechLink() {
		return industryPageObjects.getTechLink().size() > 0 ? industryPageObjects.getTechLink().get(0).getAttribute("href") : "";


	}

	public List<WebElement> getTechHeadlines() {
		return industryPageObjects.getTechHeadlines();
	}

	public String getTelecomLink() {
		return industryPageObjects.getTelecomLink().getAttribute("href");
	}

	public List<WebElement> getTelecomHeadlines() {
		return industryPageObjects.getTelecomHeadlines();
	}

	public String getMiscellaneousLink() {
		return industryPageObjects.getMiscellaneousLink().size() > 0 ? industryPageObjects.getMiscellaneousLink().get(0).getAttribute("href") : "";

	}

	public List<WebElement> getMiscellaneousHeadlines() {
		return industryPageObjects.getMiscellaneousHeadlines();
	}

	public String getSportsLink() {
		return industryPageObjects.getSportsLink().size() > 0 ? industryPageObjects.getSportsLink().get(0).getAttribute("href") : "";
	}

	public List<WebElement> getSportsHeadlines() {
		return industryPageObjects.getSportsHeadlines();
	}

	public String getDefenceLink() {
		return industryPageObjects.getDefenceLink().size() > 0 ? industryPageObjects.getDefenceLink().get(0).getAttribute("href") : "";
	}

	public List<WebElement> getDefenceHeadlines() {
		return industryPageObjects.getDefenceHeadlines();
	}

	public String getCsrLink() {
		return industryPageObjects.getCsrLink().size() > 0 ? industryPageObjects.getCsrLink().get(0).getAttribute("href") : "";

	}

	public List<WebElement> getCsrHeadlines() {
		return industryPageObjects.getCsrHeadlines();
	}

	public String getEnvironmentLink() {
		return industryPageObjects.getEnvironmentLink().size() > 0 ? industryPageObjects.getEnvironmentLink().get(0).getAttribute("href") : "";
	}

	public List<WebElement> getEnvironmentHeadlines() {
		return industryPageObjects.getEnvironmentHeadlines();
	}

	public String getIndlGoodsSvsLink() {
		return industryPageObjects.getIndlGoodsSvsLink().getAttribute("href");
	}

	public List<WebElement> getIndlGoodsSvsHeadlines() {
		return industryPageObjects.getIndlGoodsSvsHeadlines();
	}

}
