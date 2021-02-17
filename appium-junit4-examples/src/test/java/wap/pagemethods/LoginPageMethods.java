package wap.pagemethods;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import wap.pageobjects.LoginPageObjects;
import web.pagemethods.WebBaseMethods;

/**
 * Login Page Method Class for WAP
 *
 */
public class LoginPageMethods {
	private WebDriver driver;
	private LoginPageObjects loginPageObjects;

	public LoginPageMethods(WebDriver driver) {
		this.driver = driver;
		loginPageObjects = PageFactory.initElements(driver, LoginPageObjects.class);
	}

	/**
	 * Register User Login Method
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public boolean clickSignInMenu() {
		boolean flag = false;
		try {
			WaitUtil.sleep(1000);
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getMenuSignIn());
			flag = true;
		} catch (WebDriverException e) {

		}
		return flag;
	}

	public boolean clickContinueEmailMobile() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(loginPageObjects.getEmailLoginButton());
			 
		} catch (WebDriverException e) {

		}
		WaitUtil.sleep(2000);
		return flag;
	}

	public boolean findEmailSetValue(String email) {
		boolean flag = false;
		try {
			WaitUtil.explicitWaitByPresenceOfElement(driver, 20, loginPageObjects.getEmailId());
			loginPageObjects.getEmailId().sendKeys(email);
			flag = true;
		} catch (WebDriverException e) {

		}
		return flag;
	}

	public boolean clickContinueButton() {
		boolean flag = false;
		try {
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, loginPageObjects.getSignInBtn());
			flag =	WebBaseMethods.clickElementUsingJSE(loginPageObjects.getSignInBtn());
			 
		} catch (WebDriverException e) {

		}
		WaitUtil.sleep(2000);
		return flag;
	}

	public boolean findPasswordSetValue(String password) {
		boolean flag = false;
		try {
			loginPageObjects.getPassword().sendKeys(password);
			flag = true;
		} catch (WebDriverException e) {

		}
		return flag;
	}

	public boolean clickContinueButtonFinal() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(loginPageObjects.getLastStepSignIn());
			
		} catch (WebDriverException e) {

		}
		WaitUtil.sleep(2000);
		return flag;
	}

	public boolean registeredUserLogin(String email, String password) {
		boolean flag = false;
		if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
			WaitUtil.sleep(2000);
		try {
			// WaitUtil.explicitWaitByVisibilityOfElement(driver, 20,
			// loginPageObjects.getMenuSignIn());
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, loginPageObjects.getMenuSignIn());
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getMenuSignIn());
			WaitUtil.sleep(5000);
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
				WebBaseMethods.switchToChildWindow(4);
			else {
				WAPHelper.waitForOpenWebContextChange(5, (AppiumDriver<?>) driver);
				WAPHelper.genericSwitchToWebView((AppiumDriver<?>) driver);
			}
			WaitUtil.explicitWaitByElementToBeClickable(driver, 40, loginPageObjects.getEmailLoginButton());
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getEmailLoginButton());
			WaitUtil.sleep(2000);
			try {
				WaitUtil.explicitWaitByVisibilityOfElement(driver, 40, loginPageObjects.getEmailId());
				loginPageObjects.getEmailId().sendKeys(email);
			} catch (Exception e) {
				e.printStackTrace();
			}

			WaitUtil.sleep(5000);
			WaitUtil.explicitWaitByElementToBeClickable(driver, 40, loginPageObjects.getSignInBtn());
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getSignInBtn());
			WaitUtil.sleep(2000);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageObjects.getPassword());
			loginPageObjects.getPassword().sendKeys(password);
			WaitUtil.sleep(2000);
			WaitUtil.explicitWaitByElementToBeClickable(driver, 10, loginPageObjects.getLastStepSignIn());
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getLastStepSignIn());
			WaitUtil.sleep(5000);
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari")) {
				new WebDriverWait(driver, 20).until(ExpectedConditions.numberOfWindowsToBe(1));
				WebBaseMethods.switchToParentWindow();
				WaitUtil.waitForLoad(driver);
			} else {
				WAPHelper.waitForOpenWebContextChange(5, (AppiumDriver<?>) driver);
				WAPHelper.genericSwitchToWebView((AppiumDriver<?>) driver);
			}

			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * Method to check user login state and sign out
	 * 
	 * @return
	 */
	public boolean checkLogin() {
		WaitUtil.sleep(2000);
		try {
			if (loginPageObjects.getMenuLoggedInUserDetails().isDisplayed()
					&& loginPageObjects.getProfilePicture().isDisplayed()) {
				loginPageObjects.getMenuLogout().click();
				WaitUtil.sleep(2000);
				return true;
			} else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Method to handle FaceBook page login
	 * 
	 * @param email
	 * @param password
	 * @throws WebDriverException
	 */
	public void facebookActivity(String email, String password) throws WebDriverException {
		// System.out.println(loginPageObjects.getInputField().size());
		WebBaseMethods.switchToChildWindow(5);
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 40, loginPageObjects.getFbEmail());
		/*
		 * if (loginPageObjects.getInputField().get(0).isDisplayed()) {
		 * loginPageObjects.getFbEmail().sendKeys(email);
		 * loginPageObjects.getFbPassword().sendKeys(password);
		 * loginPageObjects.getFbSubmit().click(); } else {
		 * loginPageObjects.getFbEmail().sendKeys(email);
		 * loginPageObjects.getNextBtton();
		 * WaitUtil.explicitWaitByVisibilityOfElement(driver, 20,
		 * loginPageObjects.getFbPassword());
		 * loginPageObjects.getFbPassword().sendKeys(password);
		 * loginPageObjects.getLoginFb().click(); }
		 */
		loginPageObjects.getFbEmail().sendKeys(email);
		if (loginPageObjects.getFbPassword().size() == 0)
			loginPageObjects.getNextBtton().get(0).click();
		loginPageObjects.getFbPassword().get(0).sendKeys(password);
		loginPageObjects.getFbSubmit().click();
		WebBaseMethods.clickElementUsingJSE(loginPageObjects.getFbLoginContinueButton());
		// loginPageObjects.getFbContinue().click();
		// loginPageObjects.getFbConfirm().click();
		WaitUtil.sleep(2000);

	}

	/**
	 * Login with FaceBook
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public boolean loginWithFB(String email, String password) {
		boolean flag = false;
		WaitUtil.sleep(1000);
		if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
			WAPHelper.getWebViewCount((IOSDriver<?>) driver);
		try {
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, loginPageObjects.getMenuSignIn());
			loginPageObjects.getMenuSignIn().click();
			WaitUtil.sleep(2000);
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
				WebBaseMethods.switchToChildWindow(5);
			else
				WAPHelper.waitForOpenWebContextChange(5, (AppiumDriver<?>) driver);

			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getFbLoginButton());
			WaitUtil.sleep(10000);
			facebookActivity(email, password);
			// try{
			// if (loginPageObjects.getFbContinue().size()>0)
			// loginPageObjects.getFbContinue().get(0).click();
			// }catch(Exception e){
			//
			// }
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari")) {
				new WebDriverWait(driver, 20).until(ExpectedConditions.numberOfWindowsToBe(1));
				WebBaseMethods.switchToParentWindow();
				WaitUtil.waitForLoad(driver);
			} else {
				WaitUtil.sleep(5000);
				// AppBaseMethods.waitForOpenWebContextChange(5);
				WAPHelper.genericSwitchToWebView((AppiumDriver<?>) driver);
			}
			flag = true;

		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * Method to handle G+ page login
	 * 
	 * @param email
	 * @param password
	 * @throws WebDriverException
	 */
	public void gplusActivity(String email, String password) throws WebDriverException {
		WebBaseMethods.switchToChildWindow(5);
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 50, loginPageObjects.getGplusEmail());
		loginPageObjects.getGplusEmail().sendKeys(email);
		WaitUtil.sleep(5000);
		WaitUtil.explicitWaitByElementToBeClickable(driver, 50, loginPageObjects.getGplusEmailNext());
		WebBaseMethods.clickElementUsingJSE(loginPageObjects.getGplusEmailNext());
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageObjects.getGplusPassword());
		loginPageObjects.getGplusPassword().sendKeys(password);
		WaitUtil.explicitWaitByElementToBeClickable(driver, 50, loginPageObjects.getGplusPassNext());
		WebBaseMethods.clickElementUsingJSE(loginPageObjects.getGplusPassNext());
	}

	/**
	 * Login with G+
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public boolean loginWithGPlus(String email, String password) {
		boolean flag = false;
		WaitUtil.sleep(5000);
		if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
			WAPHelper.getWebViewCount((AppiumDriver<?>) driver);
		try {
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, loginPageObjects.getMenuSignIn());
			loginPageObjects.getMenuSignIn().click();
			WaitUtil.sleep(5000);
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
				WebBaseMethods.switchToChildWindow(5);
			else
				WAPHelper.waitForOpenWebContextChange(5, (AppiumDriver<?>) driver);
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, loginPageObjects.getGplusSignInButton());
			loginPageObjects.getGplusSignInButton().click();
			WaitUtil.sleep(2000);
			gplusActivity(email, password);
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari")) {
				new WebDriverWait(driver, 20).until(ExpectedConditions.numberOfWindowsToBe(1));
				WebBaseMethods.switchToParentWindow();
				WaitUtil.waitForLoad(driver);
			} else {
				WaitUtil.sleep(5000);
				WAPHelper.waitForOpenWebContextChange(5, (AppiumDriver<?>) driver);
				WAPHelper.genericSwitchToWebView((AppiumDriver<?>) driver);
			}
			flag = true;

		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public boolean loginWithGPlusReact(String email, String password) {
		boolean flag = false;
		WaitUtil.sleep(5000);
		if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
			WAPHelper.getWebViewCount((AppiumDriver<?>) driver);
		try {
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, loginPageObjects.getFooterMenuSignInIcon());
			loginPageObjects.getFooterMenuSignInIcon().click();
			WaitUtil.sleep(5000);
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
				WebBaseMethods.switchToChildWindow(5);
			else
				WAPHelper.waitForOpenWebContextChange(5, (AppiumDriver<?>) driver);
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, loginPageObjects.getGplusSignInButton());
			loginPageObjects.getGplusSignInButton().click();
			WaitUtil.sleep(2000);
			gplusActivity(email, password);
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari")) {
				new WebDriverWait(driver, 20).until(ExpectedConditions.numberOfWindowsToBe(1));
				WebBaseMethods.switchToParentWindow();
				WaitUtil.waitForLoad(driver);
			} else {
				WaitUtil.sleep(5000);
				WAPHelper.waitForOpenWebContextChange(5, (AppiumDriver<?>) driver);
				WAPHelper.genericSwitchToWebView((AppiumDriver<?>) driver);
			}
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public void linkedInActivity(String email, String password) throws WebDriverException {
		loginPageObjects.getLinkedInEmail().sendKeys(email);
		loginPageObjects.getLinkedInPwd().sendKeys(password);
		loginPageObjects.getLinkedInLogin().click();
	}

	public void twitterActivity(String email, String password) throws NoSuchElementException {
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageObjects.getTwitterEmail());
		loginPageObjects.getTwitterEmail().sendKeys(email);
		loginPageObjects.getTwitterPassword().sendKeys(password);
		loginPageObjects.getTwitterLogin().click();
	}

	public WebElement getFbEmailField() {
		return loginPageObjects.getFbEmail();
	}

	public WebElement getLinkedInEmailField() {
		return loginPageObjects.getLinkedInEmail();
	}

	public WebElement getTwitterEmailField() {
		return loginPageObjects.getTwitterEmail();
	}

	public WebElement getGPlusEmailField() {
		return loginPageObjects.getGplusEmail();
	}

	public boolean loginWithFBReact(String email, String password) {
		boolean flag = false;
		WaitUtil.sleep(1000);
		if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
			WAPHelper.getWebViewCount((IOSDriver<?>) driver);
		try {
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, loginPageObjects.getFooterMenuSignInIcon());
			loginPageObjects.getFooterMenuSignInIcon().click();
			WaitUtil.sleep(2000);
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
				WebBaseMethods.switchToChildWindow(5);
			else
				WAPHelper.waitForOpenWebContextChange(5, (AppiumDriver<?>) driver);

			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getFbLoginButton());
			WaitUtil.sleep(10000);
			facebookActivity(email, password);
			// try{
			// if (loginPageObjects.getFbContinue().size()>0)
			// loginPageObjects.getFbContinue().get(0).click();
			// }catch(Exception e){
			//
			// }
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari")) {
				new WebDriverWait(driver, 20).until(ExpectedConditions.numberOfWindowsToBe(1));
				WebBaseMethods.switchToParentWindow();
				WaitUtil.waitForLoad(driver);
			} else {
				WaitUtil.sleep(5000);
				// AppBaseMethods.waitForOpenWebContextChange(5);
				WAPHelper.genericSwitchToWebView((AppiumDriver<?>) driver);
			}
			flag = true;

		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public boolean checkLoginReact() {

		WaitUtil.sleep(2000);
		try {
			if (loginPageObjects.getFooterMenuSignOutIcon().isDisplayed()) {
				loginPageObjects.getFooterMenuSignOutIcon().click();
				WaitUtil.sleep(2000);
				return true;
			} else
				return false;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean clickFooterMenuSignIn() {
		boolean flag = false;
		try {
			WaitUtil.sleep(1000);
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getFooterMenuSignInIcon());
			flag = true;
		} catch (WebDriverException e) {

		}
		WaitUtil.sleep(2000);
		return flag;
	}

	public boolean loginOnGmail(String email, String password) {
		boolean flag = false;
		try {
			gplusActivity(email, password);
			flag = true;

		} catch (Exception ee) {

			flag = false;

		}
		WaitUtil.waitForLoad(driver);
		return flag;

	}

	public WebElement getOneTapLoginIframe() {
		return loginPageObjects.getOneTapLoginIframe();
	}

	public boolean isOneTapDisplayed() {
		boolean flag = false;
		int retry = 0;

		while (flag == false && retry < 3) {
			try {
				retry++;
				System.out.println(retry);
				flag = loginPageObjects.getOneTapButton().isDisplayed();

			} catch (WebDriverException e) {
				WebBaseMethods.refreshTimeOutHandle();
				WaitUtil.sleep(2000);
				WebBaseMethods.removeInterstitial();
				WaitUtil.waitForLoad(driver);
			}

		}
		return flag;
	}

	public boolean clickOneTapLoginButton() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(loginPageObjects.getOneTapButton());
			
		} catch (WebDriverException e) {

		}
		return flag;
	}

	public boolean clickOneTapCloseButton() {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getOneTapCloseButton());
			WaitUtil.sleep(3000);
			flag = true;
		} catch (WebDriverException e) {

		}
		return flag;
	}

	public boolean checkLoginNonReact() {

		WaitUtil.sleep(2000);
		try {
			if (loginPageObjects.getNonReactSignOutLink().isDisplayed()) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			return false;
		}

	}

}