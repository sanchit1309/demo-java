package web.tests;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.VerificationUtil;
import web.pagemethods.ArticleMethods;
import web.pagemethods.MorningBriefMethods;
import web.pagemethods.WebBaseMethods;

public class MorningBrief extends BaseTest {
	MorningBriefMethods morningBriefMethods;
	ArticleMethods articleMethods;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		String pageUrl = BaseTest.baseUrl+Config.fetchConfigProperty("MorningBrief");
		launchBrowser(pageUrl);
		morningBriefMethods = new MorningBriefMethods(driver);
		articleMethods=new ArticleMethods(driver);
	}

	@Test(description="This test verifies morning brief articles are of today")
	public void verifyMorningBriefAreLatest() {
		softAssert=new SoftAssert();
		List<String> morningBriefHref=morningBriefMethods.getMorningBriefUrls();
		morningBriefHref.forEach(url->{
			System.out.println(url);
			WebBaseMethods.navigateTimeOutHandle(url);
			String date=articleMethods.getArticleDate();
			softAssert.assertTrue(VerificationUtil.isLatest(date, 0),"Morning brief is not having today's story.This story: ->"+url+" is of "+date);
		});
		softAssert.assertAll();
	}
}
