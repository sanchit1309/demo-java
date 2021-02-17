package web.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import web.pagemethods.FooterPageMethods;
import web.pagemethods.WebBaseMethods;

public class Footer extends BaseTest {

	private String baseUrl;
    FooterPageMethods footerPageMethods;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		footerPageMethods = new FooterPageMethods(driver);
		WebBaseMethods.scrollToBottom();
			}
	@Test(description = "This test verifies In case you missed it on footer")
	public void verifyInCaseYouMissedItSection() {
		int articleCount = 2;
		softAssert = new SoftAssert();
		Map<String, String> testData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyNotToBeMissedSection", 1);
		List<String> pageName = Arrays.asList(testData.get("Text").split("\\s*,\\s*"));
		pageName.forEach(action->{
		WebBaseMethods.navigateTimeOutHandle(baseUrl+action);
		WebBaseMethods.scrollToBottom();
		softAssert.assertTrue(footerPageMethods.getInCaseYouMissedItLeftInks().size()>=articleCount, "<br>- The article count for left side of In Case You Missed It "+footerPageMethods.getInCaseYouMissedItLeftInks().size()+" is less than the expected count "+articleCount
				+" on "+action);
		softAssert.assertTrue(footerPageMethods.getInCaseYouMissedItRightInks().size()>=articleCount, "<br>- The article count for right side of In Case You Missed It "+footerPageMethods.getInCaseYouMissedItLeftInks().size()+" is less than the expected count "+articleCount+" on "+action);
		
		List<WebElement> inCaseYouMissedItHeadlines = footerPageMethods.getInCaseYouMissedIt();
		List<String> inCaseYouMissedItStories = VerificationUtil.getLinkTextList(footerPageMethods.getInCaseYouMissedIt());
		softAssert.assertTrue(inCaseYouMissedItStories.size() >= articleCount*2, "<br>- Headlines under In Case You Missed It section should be more than " + articleCount*2
				+ " in number on "+action);
		List<String> inCaseYouMissedItHref = WebBaseMethods.getListHrefUsingJSE(inCaseYouMissedItHeadlines);
		inCaseYouMissedItHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- Story <a href=" + href + ">"+href.substring(0, (href.length()/2))+"...</a> in 'In Case You Missed It' is throwing " + response+" on <a href='"+(baseUrl+action)+"'>"+(baseUrl+action)+"</a>");
		});
		List<String> inCaseYouMissedItDup = VerificationUtil.isListUnique(inCaseYouMissedItStories);
		softAssert.assertTrue(inCaseYouMissedItDup.isEmpty(), "<br>- In Case You Missed It is having duplicate stories, repeating story(s)->" + inCaseYouMissedItDup+" on <a href='"+(baseUrl+action)+"'>"+(baseUrl+action)+"</a>");
		
		});
		softAssert.assertAll();
	}

	

	
	
	
}
