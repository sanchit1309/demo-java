package app.pagemethods;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

import app.pageobjects.LoginPageObjects;
import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPageMethods {

	AppiumDriver<?> appDriver;
	LoginPageObjects loginPageObjects;

	public LoginPageMethods(AppiumDriver<?> driver) {
		appDriver = driver;
		loginPageObjects = new LoginPageObjects();
		PageFactory.initElements(new AppiumFieldDecorator(appDriver), loginPageObjects);
	}

	public boolean enterCredentials(String email, String password) {
		boolean flag = false;
		try {
			WaitUtil.sleep(4000);
			BaseTest.iAppCommonMethods.clickElement(loginPageObjects.getContinueEmailButton());
			WaitUtil.sleep(3000);
			// appDriver.getKeyboard().sendKeys(email);
			BaseTest.iAppCommonMethods.sendKeys(loginPageObjects.getEmailField(), email);
			WaitUtil.sleep(2000);
			if (BaseTest.platform.contains("android"))
				BaseTest.iAppCommonMethods.navigateBack(appDriver);
			BaseTest.iAppCommonMethods.clickElement(loginPageObjects.getContinueBtn());
			WaitUtil.sleep(3000);
			BaseTest.iAppCommonMethods.sendKeys(loginPageObjects.getPasswordField(), password);
			if (BaseTest.platform.contains("android"))
				BaseTest.iAppCommonMethods.navigateBack(appDriver);
			loginPageObjects.getLoginButton().click();
			WaitUtil.sleep(5000);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public boolean isEmailDisplayed() {
		try {
			loginPageObjects.getEmailField().isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Method for FB Login from side menu
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public boolean fbLoginMenu(String email, String password) {
		boolean flag = false;
		try {
			loginPageObjects.getFbLoginButton().click();
			if (loginWithFBWebView(email, password) == true) {
				loginPageObjects.getFbConfirmOK().click();
				flag = true;
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return flag;
	}

	/**
	 * Method for FB Login in WebView
	 * 
	 * @param email
	 * @param password
	 */
	public boolean loginWithFBWebView(String email, String password) {
		try {
			BaseTest.iAppCommonMethods.switchToWebView(appDriver, "WEBVIEW");
			loginPageObjects.getFbEmailField().sendKeys(email);
			loginPageObjects.getFbPassword().sendKeys(password);
			loginPageObjects.getFbLogin().click();
			WaitUtil.sleep(1000);
			return true;
		} catch (Exception e) {
			loginPageObjects.getCloseWebView().click();
			BaseTest.iAppCommonMethods.navigateBack(appDriver);
			return false;
		}

	}

	public boolean clickOnSettingButton() {
		Boolean flag = false;
		try {
			loginPageObjects.getSettingsbutton().click();
			WaitUtil.sleep(1000);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}
	}

	public boolean clickOnMysubscriptionButton() {
		Boolean flag = false;
		try {			
			WaitUtil.sleep(1000);
			loginPageObjects.getMySubscriptionButton().click();
			WaitUtil.sleep(1000);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}
	}

	public boolean checkSubscription(String status) {
		Boolean flag = false;
		String statusin = "";
		try {
			if (BaseTest.platform.contains("ios")) {
				WaitUtil.sleep(1000);
				statusin = loginPageObjects.getSubscriptionOption(appDriver, status).getText();
			} else {
				statusin = loginPageObjects.getsubscriptionStatus().getText();
			}
			System.out.println("Status : " + status);
			if (status.equalsIgnoreCase(statusin)) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean checkNoSubscriptionError() {
		boolean flag = false;
		try {
			if (BaseTest.platform.contains("ios")) {
				flag=loginPageObjects.getNoSubscriptionError().isDisplayed();
				 return flag;
			}
			else {
			String text = loginPageObjects.getNoSubscriptionError().getText();
			System.out.println(" error : " + text);
			if (text.equalsIgnoreCase("no subscription found")) {
				flag = true;
			}
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
			// TODO: handle exception
		}

	}

	public void backAndmenuClick() {
		try {
			HeaderPageMethods headerPageMethods = new HeaderPageMethods(appDriver);
			BaseTest.iAppCommonMethods.navigateBack(appDriver);
			BaseTest.iAppCommonMethods.navigateBack(appDriver);
			headerPageMethods.clickMenuIcon();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public boolean clickOnSignInFromSettingsPage() {
		boolean flag = false;
		try {
			if (BaseTest.platform.contains("ios")) {
				BaseTest.iAppCommonMethods.swipeUp();
			}
			loginPageObjects.getSignInFromSettingsPage().click();
			if (loginPageObjects.getContinueEmailButton().isDisplayed()) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
	}

	public boolean clickOnTopHeadersignInButton() {
		boolean flag = false;
		try {
			WaitUtil.sleep(3000);
			loginPageObjects.getTopHeaderSignInButton().click();
			WaitUtil.sleep(1000);
			if (loginPageObjects.getContinueEmailButton().isDisplayed()) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	public Boolean validateSubscribeToETButtonOnHeader() {
		boolean flag = false;
		try {
			if (loginPageObjects.getSubscribeToETPrimeButton().size() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	
}
