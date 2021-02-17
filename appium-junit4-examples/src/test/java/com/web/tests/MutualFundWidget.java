package com.web.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.mutable.Mutable;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import common.utilities.reporting.ScreenShots;
import web.pagemethods.MutualFundWidgetMethods;
import web.pagemethods.WebBaseMethods;

public class MutualFundWidget extends BaseTest {
	private String baseUrl;
	MutualFundWidgetMethods mutualFundWidgetMethods;
	SoftAssert softAssert;
	Map<String, String> testData;
	String excelPath = "TestDataSheet";
	List<String> subMenuOptions = new LinkedList<>();
	String subMenuOption = "N.A.";

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		mutualFundWidgetMethods = new MutualFundWidgetMethods(driver);
	}

	@Test(description = "This test verifies the mutual fund widget on homepage", priority = 0)
	public void verifyTopMutualFundWidget() {
		testData = ExcelUtil.getTestDataRow(excelPath, "VerifyTopMutualFundWidget", 1);
		softAssert = new SoftAssert();
		Assert.assertTrue(mutualFundWidgetMethods.isMFWidgetDisplayed(), "Mutual funds widget not shown");
		WebBaseMethods.scrollElementIntoViewUsingJSE(mutualFundWidgetMethods.getMutualFundWidget());
		List<WebElement> mutualFundWidgetTab = mutualFundWidgetMethods.getMutualFundTabList();
		if (mutualFundWidgetTab.size() != 6) {
			System.out.println(mutualFundWidgetTab.size());
			softAssert.assertTrue(false,
					"Either of Tabs Equity/Hybrid/Debt/Commodities/All/Featured Scheme is not shown");
			new ScreenShots().takeScreenshotAnotherDriver(driver, "verifyTopMutualFundWidget_tabMissing");
		}
		// clicking tab
		for (int i = 0; i < mutualFundWidgetTab.size(); i++) {
			WebElement action = mutualFundWidgetTab.get(i);
			String tabName = action.getText();
			WebBaseMethods.JSHoverOver(action);
			System.out.println("\n\ntabName " + tabName);
			if(tabName.trim().length()==0)
				continue;
			if (!tabName.equalsIgnoreCase("FEATURED SCHEMES")) {
				subMenuOptions = Arrays.asList(testData.get(tabName).split("\\s*,\\s*"));
				System.out.println("testData" + subMenuOptions);
			}
			WebBaseMethods.clickElementUsingJSE(action);

			WaitUtil.sleep(1000);
			List<WebElement> submenuList = mutualFundWidgetMethods.getMutualFundTabSubmenuList(tabName);
			if (!tabName.equalsIgnoreCase("FEATURED SCHEMES"))
				softAssert.assertTrue(submenuList != null, "No sub-menu option found under " + tabName);
			// clicking submenu
			submenuList.forEach(submenu -> {

				if (submenu != null) {
					subMenuOption = WebBaseMethods.getTextUsingJSE(submenu);
					if (!subMenuOptions.stream().filter(o -> o.equals(subMenuOption)).findFirst().isPresent())
						return;

					System.out.println("\nClicking " + subMenuOption);

					mutualFundWidgetMethods.clickSubMenu(submenu);
					WaitUtil.sleep(2000);
					WebBaseMethods.JSHoverOver(action);
				}
				Map<String, LinkedHashMap<String, String>> schemeTabWithData = mutualFundWidgetMethods
						.getMutualFundSchemeWithData();
				if (schemeTabWithData.size() == 0) {
					new ScreenShots().takeScreenshotAnotherDriver(driver,
							"verifyTopMutualFundWidget" + tabName + "_" + subMenuOption);
					softAssert.assertTrue(false, "No data found under tab: " + tabName + " sub menu:" + subMenuOption);
				}

				/*
				 * System.out.println("MF data " + schemeTabWithData.keySet());
				 * for(String s:schemeTabWithData.keySet()){ String key =
				 * s.toString(); softAssert.assertTrue((key.length() > 0 &
				 * (!key.contains("undefined"))), "The company name " + key +
				 * " for tab: " + tabName + "submenu option: " + subMenuOption +
				 * " is undefined.");
				 * 
				 * LinkedHashMap<String, String> mapData =
				 * schemeTabWithData.get(key); // Iterator<String> iterator1 =
				 * mapData.keySet().iterator(); for (String k:mapData.keySet())
				 * { String key1 = k.toString(); String value =
				 * mapData.get(key1).toString(); softAssert.assertTrue(
				 * (value.length() > 0 & (!value.contains("undefined")) &
				 * (!value.contains("NaN"))), "The value " + value +
				 * " for company " + key + " under tab: " + tabName +
				 * " submenu option: " + subMenuOption + " is not a number."); }
				 * }
				 */
			});

		}

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the TOP NPS, TOP ULIP, TOP ETF, Listed Bonds on mutual fund widget on homepage ", priority = 1)
	public void verifyMutualFundWidgetSubTab() {
		softAssert = new SoftAssert();
		Assert.assertTrue(mutualFundWidgetMethods.isMFWidgetDisplayed(), "Mutual funds widget not shown");
		WebBaseMethods.scrollElementIntoViewUsingJSE(mutualFundWidgetMethods.getMutualFundWidget());
		WebBaseMethods.JSHoverOver(mutualFundWidgetMethods.getMutualFundWidget());

		String tabNames = "nps, ulip, etf, bond";
		List<String> tabNameHeading = Arrays.asList(tabNames.split("\\s*,\\s*"));

		tabNameHeading.forEach(tabName -> {
			Assert.assertTrue(mutualFundWidgetMethods.clicktMutualFundTabs(tabName), "Unable to click tab " + tabName);
			WaitUtil.sleep(2000);
			Map<String, LinkedHashMap<String, String>> schemeTabWithData = mutualFundWidgetMethods
					.getMFWidgetSubtabWithData(tabName);
			if (schemeTabWithData.size() == 0) {
				softAssert.assertTrue(false, "No data found under tab " + tabName + " after 2 seconds");
				new ScreenShots().takeScreenshotAnotherDriver(driver, "verifyMutualFundWidgetSubTab_noData" + tabName);
			}
			/*
			 * Iterator<String> iterator =
			 * schemeTabWithData.keySet().iterator();
			 * 
			 * while (iterator.hasNext()) { String key =
			 * iterator.next().toString(); softAssert.assertTrue((key.length() >
			 * 0 & (!key.contains("undefined"))), "The company name " + key +
			 * " under tab: " + tabName +
			 * " of Mutual fund widget is undefined");
			 * 
			 * // System.out.println(key + "\n"); LinkedHashMap<String, String>
			 * mapData = schemeTabWithData.get(key); if (mapData.size() == 0) {
			 * softAssert.assertTrue(false, "No data found for company " + key);
			 * new ScreenShots().takeScreenshotAnotherDriver(driver,
			 * "verifyMutualFundWidgetSubTab_empty" + tabName); }
			 * Iterator<String> iterator1 = mapData.keySet().iterator();
			 * 
			 * while (iterator1.hasNext()) { String key1 =
			 * iterator1.next().toString(); String value =
			 * mapData.get(key1).toString(); softAssert.assertTrue(
			 * (value.length() > 0 & (!value.contains("undefined")) &
			 * (!value.contains("NaN"))), "For company: " + key + " value: " +
			 * value + " for field: " + key1 + " is not a number  under " +
			 * tabName + " of Mutual fund widget"); // System.out.println(key1 +
			 * " " + value + "\n"); }
			 * 
			 * }
			 */
			WaitUtil.sleep(1000);
		});
		softAssert.assertAll();
	}
	
	@Test(description = "This test verifies Invest Now CTA of Top MF widget on ET Homepage", priority = 2)
	public void verifyInvestNowCTA() {
		softAssert = new SoftAssert();
		BaseTest.driver.get(baseUrl);
		List<String> subCatsWithNoSchemes = new LinkedList<>();
		Assert.assertTrue(mutualFundWidgetMethods.isMFWidgetDisplayed(),
				"Mutual funds widget not appearing on ET Homepage.");
		WebBaseMethods.scrollElementIntoViewUsingJSE(mutualFundWidgetMethods.getMutualFundWidget());
		WaitUtil.sleep(5000);

		String categories = "Equity,Debt,Hybrid";
		String[] mfCats = categories.split(",");
		for (int i = 0; i < mfCats.length; i++) {
			String catName = mfCats[i];
			System.out.println("Verifying tab: " + catName);
			if (mutualFundWidgetMethods.hoverIfMfCategoryVisible(catName)) {
				WaitUtil.sleep(1500);
				List<WebElement> subCats = mutualFundWidgetMethods.getMutualFundTabSubmenuList(catName);
				if (subCats != null) {
					subCats.forEach(subCat -> {
						String subCatName = WebBaseMethods.getTextUsingJSE(subCat);
						System.out.println("Clicking " + subCatName);
						mutualFundWidgetMethods.clickSubMenu(subCat);
						WaitUtil.sleep(2000);
						if (mutualFundWidgetMethods.areSchemesVisible()) {
							if (mutualFundWidgetMethods.areBuyBtnVisible()) {
//								List<String> failures = mutualFundWidgetMethods.verifyCTAOnClickOfInvestNow(driver);
//								softAssert.assertTrue(failures.isEmpty(),
//										"<strong>" + catName + "-" + subCatName + "</strong>:<br>" + failures + "<br>");
							} else {
								System.out.println(
										"Buy buttons were not found on " + catName + "-" + subCatName + " tab.");
							}
						} else {
							subCatsWithNoSchemes.add(catName + "-" + subCatName);
						}
						mutualFundWidgetMethods.hoverOnMfCat(catName);
						WaitUtil.sleep(500);
					});
				} else {
					softAssert.assertTrue(false, "<br>No Sub-categories are appearing under " + catName
							+ " tab of Top Mutual Funds widget on ET Homepage.<br>");
				}
			} else {
				softAssert.assertTrue(false,
						"<br>" + catName + " tab is not appearing under Top Mutual Funds widget on ET Homepage.<br>");
				System.out.println(catName + " tab not appearing.");
			}
		}
		softAssert.assertTrue(subCatsWithNoSchemes.isEmpty(),
				"<br>No schemes are visible under below sub categories of Mutual Funds widget on ET Homepage.<br>"
						+ subCatsWithNoSchemes);
		softAssert.assertAll();
	}
	
/*	//@Test
	public void verifyInvestNowCTA() {
		String tabNames = "Equity- ELSS:Large Cap:Multi Cap:Mid Cap:Large & MidCap:Small Cap,Debt- Dynamic Bond:Low Duration:Short Duration,Hybrid- Dynamic Asset Allocation:Multi Asset Allocation:Equity Savings,Commodities-Gold";
		String[] individualTabOptions = tabNames.split(",");
		for (int i = 0; i < individualTabOptions.length; i++) {
			String[] tabData = tabNames.split("-");
			String[] options = tabData[1].split(":");

		Assert.assertTrue(mutualFundWidgetMethods.isMFWidgetDisplayed(), "Mutual funds widget not shown");
		WebBaseMethods.scrollElementIntoViewUsingJSE(mutualFundWidgetMethods.getMutualFundWidget());
			
		Assert.assertTrue(mutualFundWidgetMethods.areBuyBtnVisible(), "Buy Now or Start SIP or Invest Now buttons not visible in Mutual Fund Widget on Homepage.");
		Assert.assertTrue(mutualFundWidgetMethods.verifyCTAOnClickOfInvestNow(driver),
				"On click on invest now button the ET money layer or the landing url is not correct:"
						+ mutualFundWidgetMethods.getSchemesRedirectingIncorrectly());
		Assert.assertTrue(mutualFundWidgetMethods.checkIfSchemeChanges(),
				"The featured scheme is not changing on flip");

	}*/
}

	
	

