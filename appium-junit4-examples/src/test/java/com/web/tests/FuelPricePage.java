package com.web.tests;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import web.pagemethods.FuelPricePageMethods;
import web.pagemethods.WebBaseMethods;

public class FuelPricePage extends BaseTest{
	
	String url = "https://economictimes.indiatimes.com/wealth/fuel-price";
	SoftAssert softAssert;
	FuelPricePageMethods fuelPricePageMethods;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		launchBrowser(baseUrl);
		fuelPricePageMethods = new FuelPricePageMethods(driver);

	}
	
	@BeforeMethod(alwaysRun = true)
	public void openFuelPricePage() {
		driver.get(url);
	}
	
	@Test(description = "This test case verifies the Fuel Price page is not empty", priority = 0)
	public void verifyFuelPricePage() {
		softAssert = new SoftAssert();
		//fuelPricePageMethods.test();
		//driver.get(url);
		int i = 0;
		boolean flag = true;
		String fuelType = "Petrol";
		do {
			if(flag) {
				softAssert.assertTrue(fuelPricePageMethods.getMetroNameListLinks().size()>0,"Metro city "+fuelType+" price list is empty" );
				fuelPricePageMethods.getMetroNameListLinks().forEach(key->{
					int resCode = HTTPResponse.checkResponseCode(key);
					softAssert.assertEquals(resCode, 200, "Link "+key+" for the Metro city is throwing "+resCode+" expected 200");
				});
				softAssert.assertTrue(!fuelPricePageMethods.getFuelPriceList().isEmpty(), fuelType+" Price List is empty for Metro cities");
				softAssert.assertTrue(fuelPricePageMethods.getCityListLinks().size()>0,"City "+fuelType+" price list is empty" );
				fuelPricePageMethods.getCityListLinks().forEach(key->{
					int resCode = HTTPResponse.checkResponseCode(key);
					softAssert.assertEquals(resCode, 200, "Link "+key+" for the city is throwing "+resCode+" expected 200");
				});
				softAssert.assertTrue(fuelPricePageMethods.getCityListLinks().size()>0,"State "+fuelType+" price list is empty" );
				fuelPricePageMethods.getCityListLinks().forEach(key->{
					int resCode = HTTPResponse.checkResponseCode(key);
					softAssert.assertEquals(resCode, 200, "Link "+key+" for the state is throwing "+resCode+" expected 200");
				});
				
				softAssert.assertTrue(fuelPricePageMethods.getFuelNewsLinks().size()>0,"State "+fuelType+" price list is empty" );
				fuelPricePageMethods.getFuelNewsLinks().forEach(key->{
					int resCode = HTTPResponse.checkResponseCode(key);
					softAssert.assertEquals(resCode, 200, "Story link "+key+" is throwing "+resCode+" expected 200");
				});
			}else {
				Assert.assertTrue(false, "Unable to open "+fuelType + "data page");
			}
			flag = fuelPricePageMethods.openDieselPricesPage();
			if(flag) {
				i++;
				fuelType = "Diesel";
			}else
				break;
			
		}while (i<2);

	
		softAssert.assertAll();
	}
	
	@Test(description = "This test case verifies Find fuel price functionalty, latest update in petrol and Diesel based on city or state selected from dropdown menu",dataProvider ="locationType", priority = 1)
	public void verifyFindFuelPriceForm(String locationType) {
		softAssert = new SoftAssert();
		Assert.assertTrue(fuelPricePageMethods.isFuelPriceWidgetVisible(), "Find Fuel Price widget not present on Fuel price page");
		String selectedLoc = fuelPricePageMethods.getSelectedLoc(locationType);
		//System.out.println("Select Location     "+ selectedLoc);
		if(!selectedLoc.isEmpty()) {
			softAssert.assertTrue(driver.getCurrentUrl().contains(selectedLoc.toLowerCase().replaceAll("\\s", "-")), "Fuel page is not of selected location: "+selectedLoc+" instead "+driver.getCurrentUrl());
			softAssert.assertTrue(fuelPricePageMethods.isLineChartDisplayed(), "No line chart displayed for the select location "+selectedLoc);
			List<String> dateList = fuelPricePageMethods.getStateDateList();
			if(!dateList.isEmpty()) {
				softAssert.assertEquals(dateList.size(), 10, "Fuel price for Location is not of last 10 days instead "+dateList.size()+ " for location "+ selectedLoc); 
				List<String> dateDup = VerificationUtil.isListUnique(dateList);
				softAssert.assertTrue(dateDup.isEmpty(), "<br>- Selected Location has duplicate dates data, repeating date(s)->" + dateDup);
			}else
				softAssert.assertTrue(false,"Date wise fuel price table is empty");
		}else
			softAssert.assertTrue(false,"No location selected in drop down");
		softAssert.assertAll();
	}
	
	@DataProvider(name = "locationType")
	public Object[] locationType() {
		String[] locationType = {"state", "city"};
		return locationType;
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
