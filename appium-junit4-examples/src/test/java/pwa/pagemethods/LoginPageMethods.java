package pwa.pagemethods;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import pwa.pageobjects.LoginPageObjects;
import pwa.pageobjects.NewHomePageObjects;
import wap.pagemethods.WAPHelper;
import web.pagemethods.WebBaseMethods;

public class LoginPageMethods {
	
	private LoginPageObjects loginPageObjects;
	private WebDriver driver;
	private NewHomePageObjects newHomePageObjects;
	
	public LoginPageMethods(WebDriver driver) {
		this.driver = driver;
		loginPageObjects = PageFactory.initElements(driver, LoginPageObjects.class);
		newHomePageObjects = PageFactory.initElements(driver, NewHomePageObjects.class);
	}
	
	
	public boolean registeredUserLogin(String email, String password) {
		boolean flag = false;
		if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
			WaitUtil.sleep(2000);
		
		try {
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageObjects.getMenuSignIn());
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getMenuSignIn());
			WaitUtil.sleep(5000);
			/*if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
				WebBaseMethods.switchToChildWindow(4);
			else {
				WAPHelper.waitForOpenWebContextChange(5,(AppiumDriver<?>) driver);
				WAPHelper.genericSwitchToWebView((AppiumDriver<?>) driver);
			}*/
			WaitUtil.explicitWaitByElementToBeClickable(driver, 40, loginPageObjects.getEmailLoginButton());
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getEmailLoginButton());
			WaitUtil.sleep(2000);
			try {
				WaitUtil.explicitWaitByVisibilityOfElement(driver, 40, loginPageObjects.getEmailId());
				loginPageObjects.getEmailId().sendKeys(email);
			}catch(Exception e) {
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
			} else{
				WAPHelper.waitForOpenWebContextChange(5,(AppiumDriver<?>) driver);
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
			if (loginPageObjects.getMenuLoggedInUserDetails().isDisplayed() && loginPageObjects.getProfilePicture().isDisplayed()) {
				WebBaseMethods.clickElementUsingJSE(loginPageObjects.getMenuLogout());
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
	 * @throws NoSuchElementException
	 */
	public void facebookActivity(String email, String password) throws NoSuchElementException {
		// System.out.println(loginPageObjects.getInputField().size());
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
				WAPHelper.waitForOpenWebContextChange(5,(AppiumDriver<?>) driver);
			
			loginPageObjects.getFbLoginButton().click();
			WaitUtil.sleep(10000);
			facebookActivity(email, password);
			// try{
			// if (loginPageObjects.getFbContinue().size()>0)
			// loginPageObjects.getFbContinue().get(0).click();
			// }catch(Exception e){
			//
			// }
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari")) {
				
				if(driver.getWindowHandles().size()>1) {
					driver.findElement(By.xpath("//button[@type='submit']/span[contains(text(),'Continue')]")).click();
				}
					
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
	 * @throws NoSuchElementException
	 */
	public void gplusActivity(String email, String password) throws NoSuchElementException {
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 50, loginPageObjects.getGplusEmail());
		loginPageObjects.getGplusEmail().sendKeys(email);
		WaitUtil.sleep(5000);
		WaitUtil.explicitWaitByElementToBeClickable(driver, 50, loginPageObjects.getGplusEmailNext());
		loginPageObjects.getGplusEmailNext().click();
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageObjects.getGplusPassword());
		loginPageObjects.getGplusPassword().sendKeys(password);
		WaitUtil.explicitWaitByElementToBeClickable(driver, 50, loginPageObjects.getGplusPassNext());
		loginPageObjects.getGplusPassNext().click();
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
				WAPHelper.waitForOpenWebContextChange(5,(AppiumDriver<?>) driver);
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
				WAPHelper.waitForOpenWebContextChange(5,(AppiumDriver<?>) driver);
				WAPHelper.genericSwitchToWebView((AppiumDriver<?>) driver);
			}
			flag = true;

		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public void linkedInActivity(String email, String password) throws NoSuchElementException {
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
	
	public boolean newRegisteredUserLogin(String email, String password) {
		boolean flag = false;
		if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
			WaitUtil.sleep(2000);
		
		try {
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageObjects.getNewMenuSignIn());
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getNewMenuSignIn());
			WaitUtil.sleep(5000);
			/*if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
				WebBaseMethods.switchToChildWindow(4);
			else {
				WAPHelper.waitForOpenWebContextChange(5,(AppiumDriver<?>) driver);
				WAPHelper.genericSwitchToWebView((AppiumDriver<?>) driver);
			}*/
			WaitUtil.explicitWaitByElementToBeClickable(driver, 40, loginPageObjects.getNewEmailLoginBtn());
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getNewEmailLoginBtn());
			WaitUtil.sleep(2000);
			try {
				WaitUtil.explicitWaitByVisibilityOfElement(driver, 40, loginPageObjects.getEmailId());
				loginPageObjects.getEmailId().sendKeys(email);
			}catch(Exception e) {
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
			} else{
				WAPHelper.waitForOpenWebContextChange(5,(AppiumDriver<?>) driver);
				WAPHelper.genericSwitchToWebView((AppiumDriver<?>) driver);
			}

			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	public boolean newCheckLoginOnly() {
		WaitUtil.sleep(2000);
		try {
			if (loginPageObjects.getNewSignOutBtn().isDisplayed() && loginPageObjects.getnewUserName().getText().length()>0) {
				WaitUtil.sleep(2000);
				return true;
			} else
				return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean doLogout() {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getLogout());
			WaitUtil.sleep(2000);
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, newHomePageObjects.getMenuIcon());
			WebBaseMethods.clickElementUsingJSE(newHomePageObjects.getMenuIcon());
			flag = WebBaseMethods.isDisplayed(loginPageObjects.getNewMenuSignIn(),8);
		} catch (Exception e)
		{
			
		}
		return flag;
	}

}
