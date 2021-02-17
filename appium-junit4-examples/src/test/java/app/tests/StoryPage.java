package app.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.AdTechMethods;
import app.pagemethods.AlertsPromptsMethods;
import app.pagemethods.HeaderPageMethods;
import app.pagemethods.HomePageMethods;
import app.pagemethods.LoginPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.StoryPageMethods;
import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;

public class StoryPage extends BaseTest {

    private SoftAssert softAssert;
    //private AppListingPageMethods appListingPageMethods;
    private StoryPageMethods storyPageMethods;
    private HomePageMethods homePageMethods;
    private AdTechMethods adTechMethods;
    private Map<String, String> loginData;
    private AppiumDriver<?> appDriver;
    private String menuName = "Mutual Funds";
    private HeaderPageMethods headerPageMethods;
    private MenuPageMethods menuPageMethods;
    private LoginPageMethods loginPageMethods;

    @BeforeClass(alwaysRun = true)
    public void launchApp() throws IOException {
        launchBrowser();
        //appListingPageMethods= new AppListingPageMethods(appDriver);
        appDriver = iAppCommonMethods.getDriver();
        storyPageMethods = new StoryPageMethods(appDriver);
        homePageMethods = new HomePageMethods(appDriver);
        adTechMethods = new AdTechMethods(appDriver);
        loginData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyLogin", 1);
        new AlertsPromptsMethods((AppiumDriver<?>) driver).dismissAllPopups();
//		appListingPageMethods.clickNewsImageByPosition(0);;
        homePageMethods.getAllHeadlines().get(0).click();
        headerPageMethods = new HeaderPageMethods(appDriver);
        menuPageMethods = new MenuPageMethods(appDriver);
        loginPageMethods = new LoginPageMethods(appDriver);
    }

    @Test(description = "Verifies article sharing by email", priority = 0, enabled = false)
    public void verifyArticleShowSharingWithEmail() {
        softAssert = new SoftAssert();
        Assert.assertTrue(storyPageMethods.verifyArticleSharingEmail(), "Could not verify Email Sharing for article");
        softAssert.assertAll();
    }

    //Login with webview and app pending
    @Test(description = "Verifies article sharing by facebook", priority = 1, enabled = false)
    public void verifyArticleShowSharingWithFacebook() {
        softAssert = new SoftAssert();
        Assert.assertTrue(storyPageMethods.verifyArticleSharingFb(loginData.get("Email"), loginData.get("Password")), "Could not verify Facebook Sharing for article");
        softAssert.assertAll();
    }

    @Test(description = "Verifies article sharing by SMS", priority = 2, enabled = false)
    public void verifyArticleShowSharingWithSMS() {
        softAssert = new SoftAssert();
        Assert.assertTrue(storyPageMethods.verifyArticleSharingSMS(), "Could not verify SMS Sharing for article");
        softAssert.assertAll();
    }

    @Test(description = "Verifies Header, footer and MREC ads on article detail page", priority = 3)
    public void verifyAds() {
        softAssert = new SoftAssert();
        String headline = storyPageMethods.getHeadlineText();
        WaitUtil.sleep(2000);
        softAssert.assertTrue(adTechMethods.isAdDisplayed("Header"), "Header ad missing from Article show Page of article : " + headline);
        softAssert.assertTrue(adTechMethods.isMrecAdDisplayed(), "MREC ad is missing from Article show page of article : " + headline);
        //softAssert.assertTrue(adTechMethods.isAddDisplayed("Colombia"), "ColombiaAd missing from Top News on Home Page");
        softAssert.assertTrue(adTechMethods.isAdDisplayed("Footer"), "Footer ad missing from Article show Page of article : " + headline);
        softAssert.assertAll();
    }

    @Test(description = "Verifies More from our Partners and around the web sections on Article Detail Page", priority = 4)
    public void verifyArticleSections() {
        softAssert = new SoftAssert();
        if (BaseTest.platform.contains("android")) {
            List<String> moreFromPartnerList = storyPageMethods.getMoreFromPartnerHeadlineList();
            if (!moreFromPartnerList.isEmpty()) {
                softAssert.assertTrue(moreFromPartnerList.size() > 0, "No stories listed under more from our partners section");
            } else
                softAssert.assertTrue(false, "More from our partners section missing from story page");
            List<String> aroundTheWeb = storyPageMethods.geFromArndWebHeadlineList();
            if (!aroundTheWeb.isEmpty()) {
                softAssert.assertTrue(aroundTheWeb.size() > 0, "No stories listed under around the web section");
            } else
                softAssert.assertTrue(false, "From Around The Web section missing from story page");
        }
        iAppCommonMethods.navigateBack(appDriver);
        iAppCommonMethods.swipeByScreenPercentage(0.50, 0.70);
        softAssert.assertAll();
    }

    private boolean navigateToListPage(String menuLabel) {
        headerPageMethods.clickMenuIcon();
        return menuPageMethods.clickL1L2MenuByLabel(menuLabel, "Learn");


    }
}