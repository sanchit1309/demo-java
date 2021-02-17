package com.pagemethods;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;

import app.pageobjects.SearchPageObjects;
import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import static common.launchsetup.BaseTest.iAppCommonMethods;

public class SearchPageMethods {
	private static final String TestDataSheet = null;
	AppiumDriver<?> appDriver;
	SearchPageObjects searchPageObjects;
	ExcelUtil excelUtil;
	int DEFAULTCOUNT = 3;
	private int i;

	public SearchPageMethods(AppiumDriver<?> driver) {
		appDriver = driver;
		excelUtil = new ExcelUtil(null);
		searchPageObjects = new SearchPageObjects();
		PageFactory.initElements(new AppiumFieldDecorator(appDriver), searchPageObjects);
	}

	public boolean enterValueInSearchBox(String value) {
		boolean flag = false;
		try {
			iAppCommonMethods.scrollDown();
			searchPageObjects.getSearchBar().click();
			WaitUtil.sleep(1000);
			searchPageObjects.getSearchBar().clear();
			try {
				// appDriver.getKeyboard().sendKeys(value);
				searchPageObjects.getSearchBar().click();
				WaitUtil.sleep(1000);
				searchPageObjects.getSearchBar().sendKeys(value);
				WaitUtil.sleep(1000);
			} catch (UnsupportedCommandException use) {
				searchPageObjects.getSearchBar().sendKeys(value);
			}
			flag = true;
			appDriver.hideKeyboard();
		} catch (WebDriverException e) {
			// e.printStackTrace();

		}
		return flag;
	}

	public boolean isHeaderPresent(String text, int counter) {
		boolean flag = false;

		int swipeCounter = counter;

		List<String> headerList = new LinkedList<>();
		searchPageObjects.getSearchHeaders().forEach(header -> {
			String textVal = header.getText();
			if (!headerList.contains(textVal))
				headerList.add(textVal);
		});
		if (!headerList.stream().anyMatch(header -> header.equals(text))) {
			flag = iAppCommonMethods.scrollUpToElement(text);

			if (swipeCounter > 2 && !flag) {
				swipeCounter--;
				flag = isHeaderPresent(text, swipeCounter);
			}

		} else
			flag = true;

		return flag;
	}

	List<String> resultList = new LinkedList<>();

	public List<String> getSearchResults() {

		while (i < 2) {
			searchPageObjects.getSearchResult().forEach(result -> {
				String resultVal = result.getText();
				if (!resultList.contains(resultVal))
					resultList.add(resultVal);
			});

			iAppCommonMethods.scrollUp();
			i++;
			getSearchResults();
		}
		iAppCommonMethods.scrollDown();
		return resultList;
	}

	public MobileElement getFirstHeader() {
		return searchPageObjects.getSearchHeaders().get(0);
	}

	public List<MobileElement> getSearchResultHeading(String keyValue) {

		List<MobileElement> li;
		switch (keyValue) {

		case "Company":
			li = searchPageObjects.getCompanyName();
			break;

		case "Commodity":
			li = searchPageObjects.getCommodityName();
			break;

		case "Mutual Fund":
			li = searchPageObjects.getMfName();
			break;

		default:
			li = new LinkedList<>();
			break;

		}
		return li;
	}

	public boolean clickNewsTab() {
		boolean flag = false;
		try {
			searchPageObjects.getNewstab().click();
			flag = true;
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;

	}

	public boolean clickCompanyTab() {
		boolean flag = false;
		try {
			searchPageObjects.getCompanytab().click();
			flag = true;
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;
	}

	public boolean clickEtPrimeTab() {
		boolean flag = false;
		try {
			searchPageObjects.getEtPrimeTab().click();
			flag = true;
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;

	}
	public boolean clickCommodityTab() {
		boolean flag = false;
		try {
			searchPageObjects.getCommodityTab().click();
			flag = true;
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;
	}

	public boolean clickMutualFundsTab() {
		boolean flag = false;
		try {
			searchPageObjects.getMutualFundTab().click();
			flag = true;
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;
	}

	public boolean clickForexTab() {
		boolean flag = false;
		try {
			searchPageObjects.getForextab().click();
			flag = true;
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;
	}

	public boolean clickOnFirstNews() {
		boolean flag = false;
		try {
			searchPageObjects.getNewsItems().get(0).click();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public List<String> getcompanyName() {
		ArrayList<String> companyName = new ArrayList<String>();
		searchPageObjects.getCompanyNames().forEach((comp) -> {
			companyName.add(comp.getText());
		});
		return companyName;

	}

	public List<String> getCompanyLtp() {
		ArrayList<String> ltpPrices = new ArrayList<String>();
		searchPageObjects.getLtpPrices().forEach((ltp) -> {
			ltpPrices.add(ltp.getText());

		});
		return ltpPrices;
	}

	public List<String> getmutulFundsName() {
		ArrayList<String> mutualName = new ArrayList<String>();
		searchPageObjects.getMutualFundName().forEach((comp) -> {
			mutualName.add(comp.getText().trim().replaceAll("\\s\\s", " "));
		});
		return mutualName;

	}

	public List<String> getMutualNaV() {
		ArrayList<String> ltpPrices = new ArrayList<String>();
		searchPageObjects.getMutualFundNav().forEach((mtl) -> {

			ltpPrices.add(mtl.getText());

		});
		return ltpPrices;
	}

	public List<String> getCommodityName() {
		ArrayList<String> commodityName = new ArrayList<String>();
		searchPageObjects.getCommodityName().forEach((comp) -> {
			commodityName.add(comp.getText());
		});
		return commodityName;

	}

	public List<String> getcommodityLtp() {
		ArrayList<String> ltpPrices = new ArrayList<String>();
		searchPageObjects.getcommodityLtp().forEach((mtl) -> {

			ltpPrices.add(mtl.getText());

		});
		return ltpPrices;
	}

	public List<String> getForexName() {
		ArrayList<String> forexName = new ArrayList<String>();
		searchPageObjects.getCommodityName().forEach((comp) -> {
			forexName.add(comp.getText());
		});
		return forexName;

	}

	public void scrollToRelatedCompanies() {
		BaseTest.globalFlag = false;
		iAppCommonMethods.scrollUpToElement("Related Topics :");
		iAppCommonMethods.swipeByScreenPercentage(80, 30);
	}

}
