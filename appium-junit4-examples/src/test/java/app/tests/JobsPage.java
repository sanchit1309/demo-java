package app.tests;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.AdTechMethods;
import app.pagemethods.AppListingPageMethods;
import app.pagemethods.HeaderPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.StoryPageMethods;
import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import common.utilities.reporting.ScreenShots;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class JobsPage extends BaseTest {
    private AppListingPageMethods appListingPageMethods;
    private HeaderPageMethods headerPageMethods;
    private StoryPageMethods storyPageMethods;
    private SoftAssert softAssert;
    private AppiumDriver<?> appDriver;
    private int storiesToCheck;
    private int listPageSwipes;
    private int initialCount;
    private AdTechMethods adTechMethods;
    private boolean mrecAdFlag = false;
    @BeforeClass(alwaysRun = true)
    @Parameters({ "storiesDetailsTocheck", "listPageStoriesTillSwipe" })
    public void launchApp(@Optional("2") int storiesDetailsTocheck, @Optional("3") int noOfSwipes) throws IOException {
        launchBrowser();
        appDriver = iAppCommonMethods.getDriver();
        appListingPageMethods = new AppListingPageMethods(appDriver);
        storyPageMethods = new StoryPageMethods(appDriver);
        headerPageMethods = new HeaderPageMethods(appDriver);
        adTechMethods = new AdTechMethods(appDriver);
        initialCount = storiesDetailsTocheck;
        listPageSwipes = noOfSwipes;
        Assert.assertTrue(navigateToJobsTab());
    }

    @Test(description = "Verifies Jobs header", priority = 0)
    public void verifyJobsHeader() {
        String headerText = headerPageMethods.getHeaderText();
        Assert.assertEquals(headerPageMethods.getHeaderText(), "Jobs", "Actual header text:" + headerText);
    }

    @Test(description = "Verifies news in top news section", priority = 1)
    public void verifyTopNewsJobs() {

        storiesToCheck = initialCount;
        int swipesCounter = listPageSwipes;
        boolean headerAdFlag = false;
        softAssert = new SoftAssert();
        WaitUtil.sleep(2000);
        for(int i=0; i < 7; i++){
            headerAdFlag = adTechMethods.isAdDisplayed("Header");
            if(headerAdFlag)
                break;
            else {
                iAppCommonMethods.swipeByScreenPercentage(0.20, 0.80);
                WaitUtil.sleep(5000);
            }
        }

        softAssert.assertTrue(headerAdFlag, "Header ad missing from Jobs section");
        softAssert.assertTrue(adTechMethods.isAdDisplayed("Footer"), "Footer ad missing from Jobs Section");
        boolean isStoryFound = true;
        boolean flag = false;
        boolean colombiaAdFlag = false;
        while (swipesCounter >= 0) {
            if(!colombiaAdFlag){
                colombiaAdFlag = adTechMethods.isAdDisplayed("Colombia");
            }
            if(!mrecAdFlag) {
                mrecAdFlag = adTechMethods.isAdDisplayed("Mrec Listing");
            }
            Map<String, MobileElement> mapNews = appListingPageMethods.getTopNews();
            flag = !flag ? mapNews.size() > 0 : true;
            mapNews.forEach((headline, story) -> {
                verifyHeadlineNDate(story, headline);
            });
            isStoryFound = mapNews.size() > 0;
            System.out.println(isStoryFound + "<-isStoryFound," + flag + "<-flag,mapNews.size->" + mapNews.size());
            softAssert.assertTrue(flag,
                    "<br>-Zero stories found in top news, tab: Jobs in swipe:" + (listPageSwipes - swipesCounter));
            swipesCounter = appListingPageMethods.swipeUpListPage(false, swipesCounter);
        }
        softAssert.assertTrue(colombiaAdFlag, "Colombia ad missing from Jobs Section");
        softAssert.assertTrue(mrecAdFlag, "Mrec ad missing from Jobs Section");
        softAssert.assertAll();
    }

    private boolean navigateToJobsTab() {
        headerPageMethods.clickMenuIcon();
        return new MenuPageMethods(appDriver).clickMenuByLabel("Jobs");
    }

    private void verifyHeadlineNDate(MobileElement news, String headline) {
        softAssert.assertTrue(!headline.equals("null"), "Headline not found under Jobs");
        if (storiesToCheck > 0) {
            Boolean clickFlag = appListingPageMethods.clickNewsHeadline(news);
            if (clickFlag != null)
                softAssert.assertTrue(clickFlag, "<br>-Unable to click story:" + headline);
            Boolean headlineFlag = storyPageMethods.checkHeadlineLength();
            softAssert.assertTrue(headlineFlag != null, "<br>-Headline not found on clicking:" + headline);
            if (headlineFlag != null)
                softAssert.assertTrue(headlineFlag,
                        "<br>Headline not shown on story page, list page headline is:" + headline);
            if(headlineFlag==null ||!headlineFlag)
                new ScreenShots().seleniumNativeScreenshot(appDriver,"headlineMissingJobs");
            WaitUtil.sleep(1000);
            iAppCommonMethods.swipeUp();
            Boolean dateFlag = storyPageMethods.checkIfDateinRange(2);
            softAssert.assertTrue(dateFlag != null, "<br>-Date not found for story:" + headline);
            iAppCommonMethods.swipeDown();
            if (storyPageMethods.validatePrimeLogoOnArticleshow()) {
                softAssert.assertTrue(storyPageMethods.verifyPrimeSubscribeWidget(),
                        "App Blocker Subscribe ET prime widget is missing for prime article:" + headline);
                WaitUtil.sleep(1000);
            } else {
                Boolean widgetflag = appListingPageMethods.verifyETprimeWidget();
                softAssert.assertTrue(widgetflag, "ET prime support widget not displayed ");
                Boolean trialButton = appListingPageMethods.verifyStartFreeTrialLink();
                softAssert.assertTrue(trialButton, "ET prime support widget Start Free Trial Link  not displayed ");
            }
            storyPageMethods.navigateBackToListPage(appDriver);
            storiesToCheck--;
        }
    }

    @AfterClass(alwaysRun = true)
    public void quit() throws Exception {
        appDriver.quit();
    }

}
