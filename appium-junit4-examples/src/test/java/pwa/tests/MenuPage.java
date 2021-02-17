package pwa.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import pwa.pagemethods.HomePageMethods;
import pwa.pagemethods.MenuPageMethods;


public class MenuPage extends BaseTest{
	
	private String wapUrl;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	String excelPath = "MenuSubMenuData";
	Map<String, String> testData;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl =BaseTest.baseUrl;
		launchBrowser(wapUrl);
		homePageMethods = new HomePageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
	}

	@Test(description = "Verifiy Side Menu Option List", groups = { "Menu Option" }, priority = 0)
	public void verifyMenuOptions() {
		softAssert = new SoftAssert();
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyMenuOption", 1);
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("Main Menu").split("\\s*,\\s*")));
		homePageMethods.clickMenuIcon();
		WaitUtil.sleep(1000);
		List<String> actualMenuItemList = menuPageMethods.getMenuListL1();
		List<String> actualMenuListHref = menuPageMethods.getMenuHrefListL1();
		actualMenuListHref.forEach(keyword -> {
			if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
				// System.out.println(keyword);
				int resCode = HTTPResponse.checkResponseCode(keyword);
				softAssert.assertEquals(resCode, 200, "<br>- Sub Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
			}
		});
		softAssert.assertTrue(VerificationUtil.listActualInExpected(expectedMenuItemList, actualMenuItemList),
				"Actual list of menu items is not matching the expected list of menu items, missing options:" + VerificationUtil
						.getMissingMenuOptionList() + "<br>Actual List " + actualMenuItemList + " Expected List " + expectedMenuItemList);
		softAssert.assertAll();
	}

	@Test(description = "Verifiy Markets Submenu Option List", groups = { "Menu Option" }, priority = 1)
	public void verifyMarketsSubMenu() {
		String menuName = "Markets";
		softAssert = new SoftAssert();
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyMarketsSubMenu", 1);
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("MarketsSubMenu").split("\\s*,\\s*")));

		List<String> actualMenuItemList = menuPageMethods.getMenuListL2(menuName);
		if(actualMenuItemList.size()>0) {

			List<String> actualMenuListHref = menuPageMethods.getMenuHrefListL2(menuName);
			actualMenuListHref.forEach(keyword -> {
				if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
					// System.out.println(keyword);

					int resCode = HTTPResponse.checkResponseCode(keyword);
					softAssert.assertEquals(resCode, 200, "<br>- Sub Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
				}
			});

			  softAssert.assertTrue(VerificationUtil.listActualInExpected(
			  expectedMenuItemList, actualMenuItemList),
			  "Actual list of menu items is not matching the expected list of menu items. Actual List "
			  + actualMenuItemList + " Expected List " + expectedMenuItemList);
			List<String> actualMenuListL3Href = menuPageMethods.getMenuHrefListL3(menuName);
			if(actualMenuListL3Href.size()>0) {

				actualMenuListL3Href.forEach(keyword -> {
					if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
						// System.out.println(keyword);
						int resCode = HTTPResponse.checkResponseCode(keyword);
						softAssert.assertEquals(resCode, 200, "<br>- L3 Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
					}
				});
			}else {

				WaitUtil.sleep(1000);
				menuPageMethods.moveToPreviousMenu();
			}
		}

		 
		softAssert.assertTrue(actualMenuItemList.size() > 0, "No submenu found for brand solutions menu");
		softAssert.assertAll();
	}

	@Test(description = "Verifiy News Submenu Option List", groups = { "Menu Option" }, priority = 2)
	public void verifyNewsSubMenu() {
		softAssert = new SoftAssert();
		String menuName = "News";
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyNewsSubMenu", 1);
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("NewsSubMenu").split("\\s*,\\s*")));

		List<String> actualMenuItemList = menuPageMethods.getMenuListL2(menuName);
		if(actualMenuItemList.size()>0) {

			List<String> actualMenuListHref = menuPageMethods.getMenuHrefListL2(menuName);
			actualMenuListHref.forEach(keyword -> {
				if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
					// System.out.println(keyword);

					int resCode = HTTPResponse.checkResponseCode(keyword);
					softAssert.assertEquals(resCode, 200, "<br>- Sub Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
				}
			});

			  softAssert.assertTrue(VerificationUtil.listActualInExpected(
			  expectedMenuItemList, actualMenuItemList),
			  "Actual list of menu items is not matching the expected list of menu items. Actual List "
			  + actualMenuItemList + " Expected List " + expectedMenuItemList);
			List<String> actualMenuListL3Href = menuPageMethods.getMenuHrefListL3(menuName);
			if(actualMenuListL3Href.size()>0) {

				actualMenuListL3Href.forEach(keyword -> {
					if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
						// System.out.println(keyword);
						int resCode = HTTPResponse.checkResponseCode(keyword);
						softAssert.assertEquals(resCode, 200, "<br>- L3 Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
					}
				});
			}else {

				WaitUtil.sleep(1000);
				menuPageMethods.moveToPreviousMenu();
			}
		}

		 
		softAssert.assertTrue(actualMenuItemList.size() > 0, "No submenu found for brand solutions menu");
		softAssert.assertAll();
	}

	@Test(description = "Verifiy Industry Submenu Option List", groups = { "Menu Option" }, priority = 3)
	public void verifyIndustrySubMenu() {
		softAssert = new SoftAssert();
		String menuName = "Industry";
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyIndustrySubMenu", 1);
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("IndustrySubMenu").split("\\s*,\\s*")));

		List<String> actualMenuItemList = menuPageMethods.getMenuListL2(menuName);
		if(actualMenuItemList.size()>0) {
			List<String> actualMenuListHref = menuPageMethods.getMenuHrefListL2(menuName);
			actualMenuListHref.forEach(keyword -> {
				if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
					// System.out.println(keyword);

					int resCode = HTTPResponse.checkResponseCode(keyword);
					softAssert.assertEquals(resCode, 200, "<br>- Sub Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
				}
			});

			  softAssert.assertTrue(VerificationUtil.listActualInExpected(
			  expectedMenuItemList, actualMenuItemList),
			  "Actual list of menu items is not matching the expected list of menu items. Actual List "
			  + actualMenuItemList + " Expected List " + expectedMenuItemList);
			List<String> actualMenuListL3Href = menuPageMethods.getMenuHrefListL3(menuName);
			if(actualMenuListL3Href.size()>0) {
				actualMenuListL3Href.forEach(keyword -> {
					if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
						// System.out.println(keyword);
						int resCode = HTTPResponse.checkResponseCode(keyword);
						softAssert.assertEquals(resCode, 200, "<br>- L3 Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
					}
				});
			}else {
				WaitUtil.sleep(1000);
				menuPageMethods.moveToPreviousMenu();
			}
		}

		 
		softAssert.assertTrue(actualMenuItemList.size() > 0, "No submenu found for brand solutions menu");
		softAssert.assertAll();
	}

	@Test(description = "Verifiy Small Biz Submenu Option List", groups = { "Menu Option" }, priority = 4)
	public void verifySmallBizSubMenu() {
		String menuName = "RISE";
		softAssert = new SoftAssert();
		testData = ExcelUtil.getTestDataRow(excelPath, "verifySmallBizSubMenu", 1);
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("SmallBizSubMenu").split("\\s*,\\s*")));

		List<String> actualMenuItemList = menuPageMethods.getMenuListL2(menuName);
		if(actualMenuItemList.size()>0) {
			List<String> actualMenuListHref = menuPageMethods.getMenuHrefListL2(menuName);
			actualMenuListHref.forEach(keyword -> {
				if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
					// System.out.println(keyword);
					int resCode = HTTPResponse.checkResponseCode(keyword);
					softAssert.assertEquals(resCode, 200, "<br>- Sub Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
				}
			});

			  softAssert.assertTrue(VerificationUtil.listActualInExpected(
			  expectedMenuItemList, actualMenuItemList),
			  "Actual list of menu items is not matching the expected list of menu items. Actual List "
			  + actualMenuItemList + " Expected List " + expectedMenuItemList);
			List<String> actualMenuListL3Href = menuPageMethods.getMenuHrefListL3(menuName);
			if(actualMenuListL3Href.size()>0) {
				actualMenuListL3Href.forEach(keyword -> {
					if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
						// System.out.println(keyword);
						int resCode = HTTPResponse.checkResponseCode(keyword);
						softAssert.assertEquals(resCode, 200, "<br>- L3 Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
					}
				});
			}else {
				WaitUtil.sleep(1000);
				menuPageMethods.moveToPreviousMenu();
			}
		}

		 
		softAssert.assertTrue(actualMenuItemList.size() > 0, "No submenu found for brand solutions menu");
		softAssert.assertAll();
	}

	@Test(description = "Verifiy Wealth Submenu Option List", groups = { "Menu Option" }, priority = 5)
	public void verifyWealthSubMenu() {
		String menuName = "Wealth";
		softAssert = new SoftAssert();
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyWealthSubMenu", 1);
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("WealthSubMenu").split("\\s*,\\s*")));

		List<String> actualMenuItemList = menuPageMethods.getMenuListL2(menuName);
		if(actualMenuItemList.size()>0) {
			List<String> actualMenuListHref = menuPageMethods.getMenuHrefListL2(menuName);
			actualMenuListHref.forEach(keyword -> {
				if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
					// System.out.println(keyword);
					int resCode = HTTPResponse.checkResponseCode(keyword);
					softAssert.assertEquals(resCode, 200, "<br>- Sub Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
				}
			});

			  softAssert.assertTrue(VerificationUtil.listActualInExpected(
			  expectedMenuItemList, actualMenuItemList),
			  "Actual list of menu items is not matching the expected list of menu items. Actual List "
			  + actualMenuItemList + " Expected List " + expectedMenuItemList);
			List<String> actualMenuListL3Href = menuPageMethods.getMenuHrefListL3(menuName);
			if(actualMenuListL3Href.size()>0) {
				actualMenuListL3Href.forEach(keyword -> {
					if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
						// System.out.println(keyword);
						int resCode = HTTPResponse.checkResponseCode(keyword);
						softAssert.assertEquals(resCode, 200, "<br>- L3 Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
					}
				});
			}else {
				WaitUtil.sleep(1000);
				menuPageMethods.moveToPreviousMenu();
			}
		}

		 
		softAssert.assertTrue(actualMenuItemList.size() > 0, "No submenu found for brand solutions menu");
		softAssert.assertAll();
	}

	@Test(description = "Verifiy Brand Solutions Submenu Option List", groups = { "Menu Option" }, priority = 6)
	public void verifyBrandSolutionsSubMenu() {
		String menuName = "Brand Solutions";
		softAssert = new SoftAssert();
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyBrandSolutionsSubMenu", 1);
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("BrandSolutionsSubMenu").split("\\s*,\\s*")));

		List<String> actualMenuItemList = menuPageMethods.getMenuListL2(menuName);
		if(actualMenuItemList.size()>0) {
			List<String> actualMenuListHref = menuPageMethods.getMenuHrefListL2(menuName);
			actualMenuListHref.forEach(keyword -> {
				if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
					// System.out.println(keyword);
					int resCode = HTTPResponse.checkResponseCode(keyword);
					softAssert.assertEquals(resCode, 200, "<br>- Sub Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
				}
			});

			  softAssert.assertTrue(VerificationUtil.listActualInExpected(
			  expectedMenuItemList, actualMenuItemList),
			  "Actual list of menu items is not matching the expected list of menu items. Actual List "
			  + actualMenuItemList + " Expected List " + expectedMenuItemList);
			List<String> actualMenuListL3Href = menuPageMethods.getMenuHrefListL3(menuName);
			if(actualMenuListL3Href.size()>0) {
				actualMenuListL3Href.forEach(keyword -> {
					if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
						// System.out.println(keyword);
						int resCode = HTTPResponse.checkResponseCode(keyword);
						softAssert.assertEquals(resCode, 200, "<br>- L3 Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
					}
				});
			}else {
				WaitUtil.sleep(1000);
				menuPageMethods.moveToPreviousMenu();
			}
		}
				 
		softAssert.assertTrue(actualMenuItemList.size() > 0, "No submenu found for brand solutions menu");
		softAssert.assertAll();
	}

	@Test(description = "Verifiy MF Submenu Option List", groups = { "Menu Option" }, priority = 7)
	public void verifyMFSubMenu() {
		String menuName = "MF";
		softAssert = new SoftAssert();
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyMFSubMenu", 1);
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("MFSubMenu").split("\\s*,\\s*")));

		List<String> actualMenuItemList = menuPageMethods.getMenuListL2(menuName);
		if(actualMenuItemList.size()>0) {
			List<String> actualMenuListHref = menuPageMethods.getMenuHrefListL2(menuName);
			actualMenuListHref.forEach(keyword -> {
				if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
					int resCode = HTTPResponse.checkResponseCode(keyword);
					softAssert.assertEquals(resCode, 200, "<br>- Sub Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
				}
			});

			  softAssert.assertTrue(VerificationUtil.listActualInExpected(
			  expectedMenuItemList, actualMenuItemList),
			  "Actual list of menu items is not matching the expected list of menu items. Actual List "
			  + actualMenuItemList + " Expected List " + expectedMenuItemList);
			List<String> actualMenuListL3Href = menuPageMethods.getMenuHrefListL3(menuName);
			if(actualMenuListL3Href.size()>0) {
				actualMenuListL3Href.forEach(keyword -> {
					if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
						// System.out.println(keyword);
						int resCode = HTTPResponse.checkResponseCode(keyword);
						softAssert.assertEquals(resCode, 200, "<br>- L3 Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
					}
				});
			}else {
				WaitUtil.sleep(1000);
				menuPageMethods.moveToPreviousMenu();
			}
		}
		softAssert.assertTrue(actualMenuItemList.size() > 0, "No submenu found for brand solutions menu");
		softAssert.assertAll();
	}

	@Test(description = "Verifiy Tech Submenu Option List", groups = { "Menu Option" }, priority = 8)
	public void verifyTechSubMenu() {
		String menuName = "Tech";
		softAssert = new SoftAssert();
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyTechSubMenu", 1);
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("TechSubMenu").split("\\s*,\\s*")));

		List<String> actualMenuItemList = menuPageMethods.getMenuListL2(menuName);
		if(actualMenuItemList.size()>0) {

			List<String> actualMenuListHref = menuPageMethods.getMenuHrefListL2(menuName);
			actualMenuListHref.forEach(keyword -> {
				if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
					// System.out.println(keyword);
					int resCode = HTTPResponse.checkResponseCode(keyword);
					softAssert.assertEquals(resCode, 200, "<br>- Sub Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
				}
			});

			  softAssert.assertTrue(VerificationUtil.listActualInExpected(
			  expectedMenuItemList, actualMenuItemList),
			  "Actual list of menu items is not matching the expected list of menu items. Actual List "
			  + actualMenuItemList + " Expected List " + expectedMenuItemList);
			List<String> actualMenuListL3Href = menuPageMethods.getMenuHrefListL3(menuName);
			if(actualMenuListL3Href.size()>0) {
				actualMenuListL3Href.forEach(keyword -> {
					if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
						// System.out.println(keyword);
						int resCode = HTTPResponse.checkResponseCode(keyword);
						softAssert.assertEquals(resCode, 200, "<br>- L3 Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
					}
				});
			}else {
				WaitUtil.sleep(1000);
				menuPageMethods.moveToPreviousMenu();
			}
		}
		softAssert.assertTrue(actualMenuItemList.size() > 0, "No submenu found for brand solutions menu");
		softAssert.assertAll();
	}

	@Test(description = "Verifiy Opinion Submenu Option List", groups = { "Menu Option" }, priority = 9)
	public void verifyOpinionSubMenu() {
		String menuName = "Opinion";
		softAssert = new SoftAssert();
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyOpinionSubMenu", 1);
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("OpinionSubMenu").split("\\s*,\\s*")));

		List<String> actualMenuItemList = menuPageMethods.getMenuListL2(menuName);
		if(actualMenuItemList.size()>0) {
			List<String> actualMenuListHref = menuPageMethods.getMenuHrefListL2(menuName);
			actualMenuListHref.forEach(keyword -> {
				if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
					int resCode = HTTPResponse.checkResponseCode(keyword);
					softAssert.assertEquals(resCode, 200, "<br>- Sub Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
				}
			});

			  softAssert.assertTrue(VerificationUtil.listActualInExpected(
			  expectedMenuItemList, actualMenuItemList),
			  "Actual list of menu items is not matching the expected list of menu items. Actual List "
			  + actualMenuItemList + " Expected List " + expectedMenuItemList);
			List<String> actualMenuListL3Href = menuPageMethods.getMenuHrefListL3(menuName);
			if(actualMenuListL3Href.size()>0) {
				actualMenuListL3Href.forEach(keyword -> {
					if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
						// System.out.println(keyword);
						int resCode = HTTPResponse.checkResponseCode(keyword);
						softAssert.assertEquals(resCode, 200, "<br>- L3 Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
					}
				});
			}else {
				WaitUtil.sleep(1000);
				menuPageMethods.moveToPreviousMenu();
			}
		}
		softAssert.assertTrue(actualMenuItemList.size() > 0, "No submenu found for brand solutions menu");
		softAssert.assertAll();
	}

	@Test(description = "Verifiy NRI Submenu Option List", groups = { "Menu Option" }, priority = 10)
	public void verifyNRISubMenu() {
		String menuName = "NRI";
		softAssert = new SoftAssert();
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyNRISubMenu", 1);
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("NRISubMenu").split("\\s*,\\s*")));

		List<String> actualMenuItemList = menuPageMethods.getMenuListL2(menuName);
		if(actualMenuItemList.size()>0) {
			List<String> actualMenuListHref = menuPageMethods.getMenuHrefListL2(menuName);
			actualMenuListHref.forEach(keyword -> {
				if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
					// System.out.println(keyword);
					int resCode = HTTPResponse.checkResponseCode(keyword);
					softAssert.assertEquals(resCode, 200, "<br>- Sub Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
				}
			});

			  softAssert.assertTrue(VerificationUtil.listActualInExpected(
			  expectedMenuItemList, actualMenuItemList),
			  "Actual list of menu items is not matching the expected list of menu items. Actual List "
			  + actualMenuItemList + " Expected List " + expectedMenuItemList);
			List<String> actualMenuListL3Href = menuPageMethods.getMenuHrefListL3(menuName);
			if(actualMenuListL3Href.size()>0) {
				actualMenuListL3Href.forEach(keyword -> {
					if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
						// System.out.println(keyword);
						int resCode = HTTPResponse.checkResponseCode(keyword);
						softAssert.assertEquals(resCode, 200, "<br>- L3 Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
					}
				});
			}else {
				WaitUtil.sleep(1000);
				menuPageMethods.moveToPreviousMenu();
			}
		}

		 
		softAssert.assertTrue(actualMenuItemList.size() > 0, "No submenu found for brand solutions menu");
		softAssert.assertAll();
	}

	@Test(description = "Verifiy Magazines Submenu Option List", groups = { "Menu Option" }, priority = 11)
	public void verifyMagazinesSubMenu() {
		String menuName = "Magazines";
		softAssert = new SoftAssert();
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyMagazinesSubMenu", 1);
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("MagazinesSubMenu").split("\\s*,\\s*")));
		List<String> actualMenuItemList = menuPageMethods.getMenuListL2(menuName);
		if(actualMenuItemList.size()>0) {
			List<String> actualMenuListHref = menuPageMethods.getMenuHrefListL2(menuName);
			actualMenuListHref.forEach(keyword -> {
				if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
					int resCode = HTTPResponse.checkResponseCode(keyword);
					softAssert.assertEquals(resCode, 200, "<br>- Sub Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
				}
			});

			  softAssert.assertTrue(VerificationUtil.listActualInExpected(
			  expectedMenuItemList, actualMenuItemList),
			  "Actual list of menu items is not matching the expected list of menu items. Actual List "
			  + actualMenuItemList + " Expected List " + expectedMenuItemList);
			List<String> actualMenuListL3Href = menuPageMethods.getMenuHrefListL3(menuName);
			if(actualMenuListL3Href.size()>0) {
				actualMenuListL3Href.forEach(keyword -> {
					if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
						// System.out.println(keyword);
						int resCode = HTTPResponse.checkResponseCode(keyword);
						softAssert.assertEquals(resCode, 200, "<br>- L3 Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
					}
				});
			}else {
				WaitUtil.sleep(1000);
				menuPageMethods.moveToPreviousMenu();
			}
		}

		 
		softAssert.assertTrue(actualMenuItemList.size() > 0, "No submenu found for brand solutions menu");
		softAssert.assertAll();
	}

	@Test(alwaysRun = true, description = "Verifiy Slideshows Submenu Option List", groups = { "Menu Option" }, priority = 12)
	public void verifySlideshowsSubMenu() {
		String menuName = "Slideshows";
		softAssert = new SoftAssert();
		testData = ExcelUtil.getTestDataRow(excelPath, "verifySlideshowsSubMenu", 1);
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("SlideshowsSubMenu").split("\\s*,\\s*")));
		List<String> actualMenuItemList = menuPageMethods.getMenuListL2(menuName);
		if(actualMenuItemList.size()>0) {
			List<String> actualMenuListHref = menuPageMethods.getMenuHrefListL2(menuName);
			actualMenuListHref.forEach(keyword -> {
				if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
					// System.out.println(keyword);
					int resCode = HTTPResponse.checkResponseCode(keyword);
					softAssert.assertEquals(resCode, 200, "<br>- Sub Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
				}
			});

			  softAssert.assertTrue(VerificationUtil.listActualInExpected(
			  expectedMenuItemList, actualMenuItemList),
			  "Actual list of menu items is not matching the expected list of menu items. Actual List "
			  + actualMenuItemList + " Expected List " + expectedMenuItemList);
			List<String> actualMenuListL3Href = menuPageMethods.getMenuHrefListL3(menuName);
			if(actualMenuListL3Href.size()>0) {
				actualMenuListL3Href.forEach(keyword -> {
					if ((keyword.contains("economictimes") || keyword.contains("indiatimes")) && !keyword.contains("javascript")) {
						// System.out.println(keyword);
						int resCode = HTTPResponse.checkResponseCode(keyword);
						softAssert.assertEquals(resCode, 200, "<br>- L3 Menu link <a href=" + keyword + ">" + keyword + "</a> is throwing " + resCode);
					}
				});
			}else {
				WaitUtil.sleep(1000);
				menuPageMethods.moveToPreviousMenu();
			}
		}

		 
		softAssert.assertTrue(actualMenuItemList.size() > 0, "No submenu found for brand solutions menu");
		softAssert.assertAll();
	}

	// @AfterMethod
	public void goToHomePage() {
		// /WebBaseMethods.clearBrowserSessionCookie(driver);
		driver.get(wapUrl);
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
