package prime.web.tests;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.reporting.ScreenShots;
import prime.web.pagemethods.HomePageMethods;
import prime.web.pagemethods.MySubscriptionPageMethod;
import web.pagemethods.WebBaseMethods;


public class PrimeRedirections extends BaseTest {	
	HomePageMethods homePageMethods;
	ScreenShots screenshots;
	SoftAssert softAssert;
	private String primeUrl;
	MySubscriptionPageMethod mySubscriptionPageMethod;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		launchBrowser();
		primeUrl = BaseTest.primeUrl;
		homePageMethods = new HomePageMethods(driver);
		screenshots = new ScreenShots();

	}

	@AfterMethod()
	public void clearBrowserSession()
	{
		WebBaseMethods.clearBrowserSessionCookie(driver);
		WebBaseMethods.clearSessionStorage();
		driver.navigate().refresh();
	}

	
	@Test(enabled= true,description="This test case verifies ETPrime.com Redirection to Prime")
	public void verifyETPrimeToPrimeRedirection() {
		softAssert = new SoftAssert();
		driver.get("https://etprime.com/");
		softAssert.assertTrue(homePageMethods.isSubscribeButtonDisplaying(),"<br>Subscribe button is not displaying for non logged in user <br>");
		softAssert.assertTrue(homePageMethods.isStartYourTrialBoxDisplayed(),"<br>Start your trial button is not displaying for non logged in user <br>");
		softAssert.assertTrue(driver.getCurrentUrl().equals("https://economictimes.indiatimes.com/prime"),"<br> ETPrime.com is redirecting to "+driver.getCurrentUrl());
		softAssert.assertAll();
	}
	

}
