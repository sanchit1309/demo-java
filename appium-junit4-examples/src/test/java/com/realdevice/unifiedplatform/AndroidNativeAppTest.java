package com.realdevice.unifiedplatform;

import app.pagemethods.HeaderPageMethods;
import app.pagemethods.LoginPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.SettingsPageMethods;
import app.tests.LoginPage;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class AndroidNativeAppTest {

    private HeaderPageMethods headerPageMethods;
    private MenuPageMethods menuPageMethods;
    private LoginPageMethods loginPageMethods;
    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    //This rule allows us to set test status with Junit
    @Rule
    public SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher();
    public AppiumDriver<MobileElement> driver;
    public AppiumDriver<MobileElement> getDriver() {
        return driver;
    }

    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();

        /*
        * Pick your device
        * */
        //Not specifying platformVersion or the exact device is the most likely to
        //find a device in the cloud
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("deviceName", "Samsung Galaxy S9.*");

        capabilities.setCapability("idleTimeout", "150");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("newCommandTimeout", "150");
        capabilities.setCapability("appActivity", "com.et.reader.activities.SplashActivity");
        capabilities.setCapability("appWaitActivity", "com.et.reader.activities.LoginActivity");
        capabilities.setCapability("appPackage", "com.et.reader.activities");
        capabilities.setCapability("name", name.getMethodName());
        capabilities.setCapability("app", "storage:filename=" +
                "app_release-3.6.6.apk");

        driver = new AndroidDriver<>(
                new URL("https://" + System.getenv("SAUCE_USERNAME") + ":" +
                        System.getenv("SAUCE_ACCESS_KEY") +
                        "@ondemand.us-west-1.saucelabs.com/wd/hub"),
                capabilities);
        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);

        headerPageMethods = new HeaderPageMethods(driver);
        menuPageMethods = new MenuPageMethods(driver);
        loginPageMethods = new LoginPageMethods(driver);
    }

    @Ignore
    public void shouldOpenApp() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10000);
        WebElement loginField =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tv_skip")));
        driver.findElement(By.id("tv_skip")).click();
        assertTrue(loginField.isDisplayed());
        //driver.hideKeyboard(); //Hide keyboard
    }

    @Test
    public void verifyTimesPointWidget() {

        SoftAssert softAssert = new SoftAssert();
        boolean flag = new LoginPage().doLogin(softAssert, headerPageMethods, menuPageMethods, loginPageMethods);
        if (flag) {
            headerPageMethods.clickMenuIcon();
            softAssert.assertTrue(menuPageMethods.isTimesPointShown(), "<br>-Times Points not shown");
            softAssert.assertTrue(menuPageMethods.isRedeemableShown(),
                                  "<br>Button to redeem or my points is not shown");
            softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
            WaitUtil.sleep(2000);
            softAssert.assertTrue(new SettingsPageMethods(driver).isMyTimesPointOptionPresent(),
                                  "On settings page, my times point option is not shown");
            softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
        }

        softAssert.assertAll();
    }
}
