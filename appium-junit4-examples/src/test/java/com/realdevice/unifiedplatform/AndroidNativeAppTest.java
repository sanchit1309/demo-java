package com.realdevice.unifiedplatform;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class AndroidNativeAppTest {
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
                "app_release-4.1.3_2102072100.apk");

        driver = new AndroidDriver<>(
                new URL("https://" + System.getenv("SAUCE_USERNAME") + ":" +
                        System.getenv("SAUCE_ACCESS_KEY") +
                        "@ondemand.us-west-1.saucelabs.com/wd/hub"),
                capabilities);
        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void shouldOpenApp() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10000);
        WebElement loginField = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("test-Username")));
        assertTrue(loginField.isDisplayed());

//        driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id, 'et_account_verification_phone_number')]")).sendKeys("9999999999"); //Enter Mobile No
//        driver.hideKeyboard(); //Hide keyboard
    }
}
