package app.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.AlertsPromptsMethods;
import app.pagemethods.AppListingPageMethods;
import app.pagemethods.BookmarksPageMethods;
import app.pagemethods.HeaderPageMethods;
import app.pagemethods.LoginPageMethods;
import app.pagemethods.ManageNewsletterMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.PrimeSectionMethods;
import app.pagemethods.StoryPageMethods;
import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class ManageNewsletterPage extends BaseTest {
    MenuPageMethods menuPageMethods;
    HeaderPageMethods headerPageMethods;
    AppListingPageMethods appListingPageMethods;
    BookmarksPageMethods bookmarksPageMethods;
    StoryPageMethods storyPageMethods;
    private LoginPageMethods loginPageMethods;
    AppiumDriver<?> appDriver;
    private String menuName = "Mutual Funds";
    private SoftAssert softAssert;
    AlertsPromptsMethods alertsPromptsMethods;
    LoginPage loginPage;
    PrimeSectionMethods primeSectionMethods;
    ManageNewsletterMethods manageNewsletterMethods;
    public String email;
    public boolean newsletter = false;


    @BeforeClass(alwaysRun = true)
    public void launchApp() throws IOException {
        launchBrowser();
        appDriver = iAppCommonMethods.getDriver();
        menuPageMethods = new MenuPageMethods(appDriver);
        headerPageMethods = new HeaderPageMethods(appDriver);
        appListingPageMethods = new AppListingPageMethods(appDriver);
        bookmarksPageMethods = new BookmarksPageMethods(appDriver);
        storyPageMethods = new StoryPageMethods(appDriver);
        loginPageMethods = new LoginPageMethods(appDriver);
        primeSectionMethods = new PrimeSectionMethods(appDriver);
        alertsPromptsMethods = new AlertsPromptsMethods(appDriver);
        manageNewsletterMethods = new ManageNewsletterMethods(appDriver);
    }

    @Test(description = "Validate Newsletter present on articleshow", enabled = true, priority = 1)
    public void validateNewsletterWidgetPresentOnArticlePage() {
        softAssert = new SoftAssert();
        Assert.assertTrue(headerPageMethods.clickMenuIcon(), "Clicking On menu Icon failed");
        Assert.assertTrue(loginPageMethods.clickOnSettingButton(), "clicking on settings Button failed");
        Assert.assertTrue(loginPageMethods.clickOnSignInFromSettingsPage(),
                "Clicking On Sign In On Setting page failed");
        Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyActiveUserSignIn", 1);
        Boolean status = loginPageMethods.enterCredentials(TestData.get("Email"), TestData.get("Password"));
        email = TestData.get("Email");
        softAssert.assertTrue(status, "Unable to enter login credentials");
        if (status == true) {
            Assert.assertTrue(navigateToListPage(menuName));
            List<MobileElement> headLinesList = storyPageMethods.getHeadlineList();
            softAssert.assertTrue(headLinesList.size() > 0, "No news found on " + menuName);
            softAssert.assertTrue(storyPageMethods.scrollToFirstArticle(headLinesList), "Unable to scroll the non prime article");
            newsletter = storyPageMethods.scrollToNewsletter();
            if (!newsletter) {
                System.out.println("false");
                iAppCommonMethods.navigateBack(appDriver);
                iAppCommonMethods.swipeByScreenPercentage(0.50, 0.70);
                headerPageMethods.clickMenuIcon();
                menuPageMethods.clickMenuByLabel("Manage Newsletters");
                WaitUtil.sleep(7000);
                softAssert.assertTrue(manageNewsletterMethods.validateUnsubscribeNewsletterFromManageNEwsletterPage(), "Unsubscribe From Manage Newsletter failed");

            }
        }
        softAssert.assertAll();
    }

    @Test(description = "Validate  manage newsletter Page for loggedin user", priority = 2, enabled = true)
    public void subscribeNewsletterFromLoggedInUser() {
        softAssert = new SoftAssert();
        if (BaseTest.platform.contains("android")) {
            if (newsletter) {
                String newsletterHeading = manageNewsletterMethods.getNewsletterAvailableOnStoryPage();
                softAssert.assertTrue(storyPageMethods.validateEmailPrefilledInNewsletter(email), "Hello");
                softAssert.assertTrue(manageNewsletterMethods.clickOnSubscribeButton(), "Click on subscribe Newsletter Button failed");
                softAssert.assertTrue(manageNewsletterMethods.clickOnBrowseNewsletterLink(), "Click on Browse Newsletter Link Failed");
                softAssert.assertTrue(manageNewsletterMethods.validateEmailOnManageNewsletterPage(email), "Email validation of user and on Manage Newsletter Page fails");
                softAssert.assertTrue(manageNewsletterMethods.validateNewsletterSubscribedShown(newsletterHeading), "Hello");

            } else {
                List<MobileElement> headLinesList = storyPageMethods.getHeadlineList();
                softAssert.assertTrue(headLinesList.size() > 0, "No news found on " + menuName);
                softAssert.assertTrue(storyPageMethods.scrollToFirstArticle(headLinesList), "Unable to scroll the non prime article");
                String newsletterHeading = manageNewsletterMethods.getNewsletterAvailableOnStoryPage();
                softAssert.assertTrue(storyPageMethods.validateEmailPrefilledInNewsletter(email), "Hello");
                softAssert.assertTrue(manageNewsletterMethods.clickOnSubscribeButton(), "Click on subscribe Newsletter Button failed");
                softAssert.assertTrue(manageNewsletterMethods.clickOnBrowseNewsletterLink(), "Click on Browse Newsletter Link Failed");
                softAssert.assertTrue(manageNewsletterMethods.validateEmailOnManageNewsletterPage(email), "Email validation of user and on Manage Newsletter Page fails");
                softAssert.assertTrue(manageNewsletterMethods.validateNewsletterSubscribedShown(newsletterHeading), "Hello");

            }
        }
            softAssert.assertTrue(storyPageMethods.navigateToHomePage(), "Hello");
            iAppCommonMethods.swipeByScreenPercentage(0.50, 0.70);
            headerPageMethods.clickMenuIcon();
            menuPageMethods.scrollDownToSettingIcon();
            softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
            softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
            softAssert.assertAll();
        
    }
    @Test(description = "Validate newsletter widget for loggedout user", enabled = true, priority = 3)
    public void susbcribeNewsletter() {
        Assert.assertTrue(navigateToHome());
        Assert.assertTrue(navigateToListPage(menuName));
        softAssert = new SoftAssert();
        List<MobileElement> headLinesList = storyPageMethods.getHeadlineList();
        softAssert.assertTrue(headLinesList.size() > 0, "No news found on " + menuName);
        softAssert.assertTrue(storyPageMethods.scrollToFirstArticle(headLinesList), "Unable to scroll the non prime article");
        softAssert.assertTrue(storyPageMethods.scrollToNewsletter(), "Hello");
        softAssert.assertTrue(storyPageMethods.enterEmailAndSubscribe(), "Unable to enter email and click on subscribe button");
        softAssert.assertTrue(manageNewsletterMethods.clickOnBrowseNewsletterLink(), "Click on Browse Newsletter Link Failed");
        softAssert.assertTrue(manageNewsletterMethods.clickOnSubscribeButtonLogOutState(), "Click on subscribe Button On Manage Newsletter Failed");
        softAssert.assertTrue(manageNewsletterMethods.validateEmailAndSubmitClick(), "Entering email abd submit button click Failed");
        softAssert.assertAll();
    }

    private boolean navigateToManageNewsletters() {
        Boolean flag;
        flag = headerPageMethods.clickMenuIcon();
        flag = flag && menuPageMethods.clickMenuByLabel("Manage Newsletters");
        return flag;
    }

    private boolean navigateToListPage(String menuLabel) {
    	headerPageMethods.clickMenuIcon();
		return new MenuPageMethods(appDriver).clickL1L2MenuByLabel("Mutual Funds", "Learn");
    }
    
    private boolean navigateToHome() {
		new HeaderPageMethods(appDriver).clickMenuIconSwipeUpFirst();
		return new MenuPageMethods(appDriver).clickMenuByLabel("Home");
	}
}
