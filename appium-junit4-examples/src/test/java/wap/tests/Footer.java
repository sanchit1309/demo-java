package wap.tests;

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
import common.utilities.WaitUtil;
import wap.pagemethods.FooterMethods;
import wap.pagemethods.HomePageMethods;
import web.pagemethods.WebBaseMethods;

public class Footer extends BaseTest {

	private String wapUrl;
	private FooterMethods footerMethods;
	private String excelPath = "MenuSubMenuData";
	private Map<String, String> pages;
	private HomePageMethods homePageMethods;

	private SoftAssert softAssert;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl =BaseTest.baseUrl;
		launchBrowser(wapUrl);
		footerMethods = new FooterMethods(driver);
		homePageMethods = new HomePageMethods(driver);

		pages = ExcelUtil.getTestDataRow(excelPath, "verifyFooterSection", 1);
	}
	
	public boolean openPage(String page){
		WaitUtil.sleep(2000);
		WebBaseMethods.navigateToUrl(driver,wapUrl);
		return homePageMethods.openTopSection(page);

		
	}
	
	@Test(description = "Verify footer sections", groups ="Footer Page")
	public void verifyFooterLinks(){
		softAssert = new SoftAssert();
		List<String> pagesList = new ArrayList<>(Arrays.asList(pages.get("Main Pages").split("\\s*,\\s*")));
		int sectionCount = 15;
		for(String page:pagesList){
			WebBaseMethods.scrollToTop();
			softAssert.assertTrue(openPage(page),"Unable to navigate to menu option");
			List<String> sectionLink = footerMethods.getFooterLinkHref();
			int count = sectionLink.size();
			softAssert.assertTrue(count>=sectionCount, "<br>- Links in Footer for "+page+" should be more than " + sectionCount
					+ " in number, instead found " + count);
			
		}
		List<String> sectionLink = footerMethods.getFooterLinkHref();
		sectionLink.forEach(keyword -> {
			if(keyword.contains("economictimes"))
			{
				System.out.println(keyword);
				int linksResponseCode = HTTPResponse.checkResponseCode(keyword);
				softAssert.assertEquals(linksResponseCode, 200, "<br>- Footer link <a href=" + keyword + ">link</a> is throwing "
						+ linksResponseCode);
			}
		});
		softAssert.assertAll();
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
