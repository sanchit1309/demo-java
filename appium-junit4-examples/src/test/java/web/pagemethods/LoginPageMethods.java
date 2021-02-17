package web.pagemethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.utilities.WaitUtil;
import web.pageobjects.LoginPageObjects;

public class LoginPageMethods {
	private LoginPageObjects loginPageObjects;
	private WebDriver driver;

	public LoginPageMethods(WebDriver driver) {
		this.driver = driver;
		loginPageObjects = PageFactory.initElements(driver, LoginPageObjects.class);
	}

	public boolean clickGPlusButton() {
		boolean flag = false;
		try {
			loginPageObjects.getGplusSignInButton().click();
			flag = true;
		} catch (Exception e) {

		}
		return flag;
	}

	public boolean googlePlusLogin(String email, String password) {
		boolean flag = false;
		try {
			loginPageObjects.getGooglePlusEmail().sendKeys(email);
			loginPageObjects.getNext().click();
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, loginPageObjects.getGooglePlusPassword());
			loginPageObjects.getGooglePlusPassword().sendKeys(password);
			loginPageObjects.getGooglePlusSignIn().click();

			WaitUtil.sleep(2000);
			System.out.println("googlePlusLogin");
			if (driver.getWindowHandles().size() > 1) {
				flag = WebBaseMethods.getCookieValue("CONSENT").length() > 0 ? true : false;
			} else {
				WebBaseMethods.switchToParentWindow();
				flag = WebBaseMethods.getCookieValue("ssoid").length() > 0 ? true : false;

			}

			return flag;
		} catch (Exception ee) {
			ee.printStackTrace();
			return false;
		}
	}

	public boolean gmailLogin(String email, String password) {
		boolean flag = false;
		try {
			loginPageObjects.getGooglePlusEmail().sendKeys(email);
			loginPageObjects.getNext().click();
			WaitUtil.sleep(2000);
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, loginPageObjects.getGmailPasswordSection());
			loginPageObjects.getGmailPasswordSection().sendKeys(password);
			loginPageObjects.getGooglePlusSignIn().click();

			WaitUtil.sleep(2000);
			System.out.println("googlePlusLogin");

			flag = WebBaseMethods.getCookieValue("CONSENT").length() > 0 ? true : false;
			return flag;
		} catch (Exception ee) {
			ee.printStackTrace();
			return false;
		}
	}

	public boolean clickFbButton() {
		boolean flag = false;
		try {
			loginPageObjects.getFbSignInButton().click();
			flag = true;
		} catch (WebDriverException e) {

		}
		return flag;
	}

	public boolean facebookLogin(String email, String password) {
		boolean flag = false;
		int counter = 0;
		String cookie = "";
		try {
			WebBaseMethods.switchToLastWindow();

			try {
				cookie = WebBaseMethods.getCookieValue("c_user");
				System.out.println(cookie);
				if (cookie.length() > 0) {
					return true;
				}
			} catch (Exception e) {

				e.printStackTrace();
				cookie = "";
			}
			WaitUtil.sleep(2000);
			loginPageObjects.getFbEmail().sendKeys(email);
			loginPageObjects.getFbPassword().sendKeys(password);
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getFbLogin());

			int winCount2 = driver.getWindowHandles().size();
			System.out.println("winSize before waiting:" + winCount2);
			while (driver.getWindowHandles().size() == winCount2 && counter < 5) {
				WaitUtil.sleep(2000);
				System.out.println("waiting for win size to change");
				counter++;
			}
			System.out.println("winSize:" + driver.getWindowHandles().size());
			WaitUtil.sleep(5000);
			WebBaseMethods.switchToLastWindow();
			WaitUtil.sleep(5000);

			if (driver.getWindowHandles().size() > 1) {

				try {
					cookie = WebBaseMethods.getCookieValue("c_user");
					System.out.println(cookie);
				} catch (Exception e) {
					e.printStackTrace();
				}
				flag = cookie.length() > 0 ? true : false;

			} else {
				try {
					cookie = WebBaseMethods.getCookieValue("ssoid");
					System.out.println(cookie);
				} catch (Exception e) {
					e.printStackTrace();
				}
				flag = cookie.length() > 0 ? true : false;

			}

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}
		return flag;

	}

	public boolean facebookLoginMain(String email, String password) {
		boolean flag = false;
		int counter = 0;
		try {
			WebBaseMethods.switchChildIfPresent();

			WaitUtil.sleep(2000);
			loginPageObjects.getFbEmail().sendKeys(email);
			loginPageObjects.getFbPassword().sendKeys(password);
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getFbLogin());

			int winCount2 = driver.getWindowHandles().size();
			System.out.println("winSize before waiting:" + winCount2);
			while (driver.getWindowHandles().size() == winCount2 && counter < 5) {
				WaitUtil.sleep(2000);
				System.out.println("waiting for win size to change");
				counter++;
			}
			System.out.println("winSize:" + driver.getWindowHandles().size());
			WebBaseMethods.switchToLastWindow();
			WaitUtil.sleep(5000);
			String cookie = "";
			if (driver.getWindowHandles().size() > 1) {

				try {
					cookie = WebBaseMethods.getCookieValue("c_user");
					System.out.println(cookie);
				} catch (Exception e) {
					e.printStackTrace();
				}
				flag = cookie.length() > 0 ? true : false;

			} else {
				try {
					cookie = WebBaseMethods.getCookieValue("ssoid");
					System.out.println(cookie);
				} catch (Exception e) {
					e.printStackTrace();
				}
				flag = cookie.length() > 0 ? true : false;

			}

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}
		return flag;

	}

	public boolean registeredUserLogin(String email, String password) {
		boolean flag = false;
		try {
			WebBaseMethods.switchToChildWindow(5);
			WaitUtil.sleep(4000);
			System.out.println("Current URL Before Waiting for continue with email is " +driver.getCurrentUrl());
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageObjects.getContinueWithEmail());
			loginPageObjects.getContinueWithEmail().click();
			System.out.println("Current URL Before entering email id is " +driver.getCurrentUrl());
			WaitUtil.sleep(6000);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageObjects.getEmail());
			loginPageObjects.getEmail().sendKeys(email);
			loginPageObjects.getContinuebtn().click();
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageObjects.getPassword());
			loginPageObjects.getPassword().sendKeys(password);
			System.out.println("USername and password entering are " +email+ password);
			System.out.println("Current URL after entering password is " +driver.getCurrentUrl());
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getLastStepSignIn());
			new WebDriverWait(driver, 20).until(ExpectedConditions.numberOfWindowsToBe(1));
			WebBaseMethods.switchToParentWindow();
			WaitUtil.sleep(3000);
			flag = true;
			System.out.println("Sign In Successfull ");
		} catch (WebDriverException e) {
			e.printStackTrace();

		}
		return flag;
	}

	public boolean clickContinueEmailMobile() {
		boolean flag = false;
		try {
			WaitUtil.sleep(8000);
			flag =WebBaseMethods.clickElementUsingJSE(loginPageObjects.getContinueWithEmail());
			 
		} catch (WebDriverException | NullPointerException e) {

		}
		WaitUtil.sleep(5000);
		return flag;
	}

	public boolean findEmailSetValue(String email) {
		boolean flag = false;
		try {
			WaitUtil.sleep(5000);
			loginPageObjects.getEmail().sendKeys(email);
			flag = true;
		} catch (WebDriverException e) {

		}
		return flag;
	}

	public boolean clickContinueButton() {
		boolean flag = false;
		WaitUtil.sleep(5000);
		try {
			flag =WebBaseMethods.clickElementUsingJSE(loginPageObjects.getContinuebtn());
			 
		} catch (WebDriverException e) {
			e.printStackTrace();
		}
		WaitUtil.sleep(5000);
		return flag;
	}

	public boolean findPasswordSetValue(String password) {
		boolean flag = false;
		try {
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, loginPageObjects.getPassword());
			loginPageObjects.getPassword().sendKeys(password);
			flag = true;
		} catch (WebDriverException e) {

		}
		return flag;
	}

	public boolean clickContinueButtonFinal() {
		boolean flag = false;
		WaitUtil.sleep(2000);
		try {
			flag =WebBaseMethods.clickElementUsingJSE(loginPageObjects.getLastStepSignIn());
			 
		} catch (WebDriverException e) {

		}
		WaitUtil.sleep(5000);
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
			loginPageObjects.getOneTapButton().click();
			flag = true;
		} catch (WebDriverException e) {

		}
		return flag;
	}

	public boolean clickOneTapCloseButton() {
		boolean flag = false;
		try {
			loginPageObjects.getOneTapCloseButton().click();
			WaitUtil.sleep(3000);
			flag = true;
		} catch (WebDriverException e) {

		}
		return flag;
	}

	public boolean linkedInLoginActivity(String email, String password) {
		boolean flag = false;
		try {
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageObjects.getLinkedInUserName());
			loginPageObjects.getLinkedInUserName().sendKeys(email);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 10, loginPageObjects.getLinkedInpassword());
			loginPageObjects.getLinkedInpassword().sendKeys(password);
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getLinkedInSignInBtn());
			WaitUtil.sleep(3000);
			flag = true;
		} catch (WebDriverException e) {
			e.printStackTrace();

		}
		return flag;
	}
	
public boolean twitterLoginActivity(String email, String password){
		
		boolean flag = false;
		try {
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageObjects.getTwitterEMailField());
			loginPageObjects.getTwitterEMailField().sendKeys(email);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 10, loginPageObjects.getTwitterPasswordField());
			loginPageObjects.getTwitterPasswordField().sendKeys(password);
			WebBaseMethods.clickElementUsingJSE(loginPageObjects.getTwitterLoginButton());
			WaitUtil.sleep(3000);
			flag = true;
		} catch (WebDriverException e) {
			e.printStackTrace();

		}
		return flag;
	}
}
