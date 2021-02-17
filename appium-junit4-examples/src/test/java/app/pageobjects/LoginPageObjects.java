package app.pageobjects;

import java.util.List;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginPageObjects {

	@AndroidFindBy(id = "com.et.reader.activities:id/tvContinueWithEmail")
	@iOSXCUITFindBy(accessibility = "            Continue with Email")
	private MobileElement continueEmailButton;

	@AndroidFindBy(id = "com.et.reader.activities:id/input_email")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='text_field_email'")
	private MobileElement emailField;

	@AndroidFindBy(id = "com.et.reader.activities:id/button_continue")
	@iOSXCUITFindBy(accessibility = "CONTINUE")
	private MobileElement continueBtn;

	@AndroidFindBy(id = "com.et.reader.activities:id/input_password")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='text_field_password'")
	private MobileElement passwordField;

	@AndroidFindBy(id = "com.et.reader.activities:id/button_password_continue")
	@iOSXCUITFindBy(iOSNsPredicate = "name =='continue'")
	// @iOSXCUITFindBy(id="Login Now l")
	private MobileElement loginButton;

	@AndroidFindBy(xpath = "//*[@id=\"u_0_1\"]/div[1]/div/input")
	private MobileElement fbEmailField;

	@AndroidFindBy(xpath = "//*[@id=\"u_0_2\"]")
	private MobileElement fbPassword;

	@AndroidFindBy(xpath = "//*[@id=\"u_0_6\"]")
	private MobileElement fbLogin;

	@AndroidFindBy(xpath = "//*[@text='Login with Facebook'and @id='com.et.reader.activities:id/tv_button]")
	private MobileElement fbLoginButton;

	@AndroidFindBy(xpath = "//*[@id=\"platformDialogContent\"]/div/div[2]/footer/div/div/div[1]/button")
	private MobileElement fbConfirmCancel;

	@AndroidFindBy(xpath = "//*[@id=\"u_0_3\"]")
	private MobileElement fbConfirmOK;

	@AndroidFindBy(className = "android.widget.ImageView")
	private MobileElement closeWebView;

	@AndroidFindBy(id = "com.et.reader.activities:id/tv_settings")
	@iOSXCUITFindBy(iOSNsPredicate="name =='SettingsLHS'")
	private MobileElement settingsButton;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='My Subscriptions']")
	@iOSXCUITFindBy(accessibility = "My Subscriptions")
	private MobileElement mysubscriptionButton;

	@AndroidFindBy(id = "com.et.reader.activities:id/subscription_status")
	private MobileElement subscriptionStatus;

	@AndroidFindBy(id = "com.et.reader.activities:id/tv_error")
	@iOSXCUITFindBy(accessibility = "You haven't purchased any subscription yet.")
	private MobileElement noSubsctiptionError;

	@AndroidFindBy(id = "com.et.reader.activities:id/tv_signIn")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Sign in'")
	private MobileElement signInFromSettingsPage;

	@AndroidFindBy(id = "com.et.reader.activities:id/tv_action_2")
	@iOSXCUITFindBy(iOSNsPredicate = "name = '    Sign In    '")
	private MobileElement topHeadersignIn;

	@AndroidFindBy(id = "com.et.reader.activities:id/tv_action_1")
	private MobileElement topHeaderSubscribe;
	
	@AndroidFindBy(id = "com.et.reader.activities:id/tv_action_3")
	@iOSXCUITFindBy(accessibility = "    Subscribe to ET Prime    ")
	private List<MobileElement> topHeaderSubscribetoEtPrime;

	@AndroidFindBy(id = "com.et.reader.activities:id/cardview_subscription_item")
	private MobileElement planPage;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='SUBSCRIBE FOR ₹3599']")
	private MobileElement TwoyearlyPlan;

	@AndroidFindBy(xpath = "//android.view.View[@text='Credit Card']")
	private MobileElement creditCard;

	@AndroidFindBy(xpath = "//android.widget.EditText[@index='0']")
	private List<MobileElement> cardDetailsList;

	// //android.widget.TextView[@text='SUBSCRIBE FOR ₹3599']
	//
	/////// Getters start here////////
	public MobileElement getEmailField() {
		return emailField;
	}

	public MobileElement getPasswordField() {
		return passwordField;
	}

	public MobileElement getLoginButton() {
		return loginButton;
	}

	public MobileElement getFbLoginButton() {
		return fbLoginButton;
	}

	public MobileElement getFbEmailField() {
		return fbEmailField;
	}

	public MobileElement getFbPassword() {
		return fbPassword;
	}

	public MobileElement getFbLogin() {
		return fbLogin;
	}

	public MobileElement getFbConfirmCancel() {
		return fbConfirmCancel;
	}

	public MobileElement getFbConfirmOK() {
		return fbConfirmOK;
	}

	public MobileElement getCloseWebView() {
		return closeWebView;
	}

	public MobileElement getContinueEmailButton() {
		return continueEmailButton;
	}

	public MobileElement getContinueBtn() {
		return continueBtn;
	}

	public MobileElement getSettingsbutton() {
		return settingsButton;
	}

	public MobileElement getMySubscriptionButton() {
		return mysubscriptionButton;
	}

	public MobileElement getsubscriptionStatus() {
		return subscriptionStatus;
	}

	public MobileElement getNoSubscriptionError() {
		return noSubsctiptionError;
	}

	public MobileElement getSignInFromSettingsPage() {
		return signInFromSettingsPage;
	}

	public MobileElement getTopHeaderSignInButton() {
		return topHeadersignIn;

	}

	public MobileElement getTopHeadersubscribeButton() {
		return topHeaderSubscribe;
	}

	public List<MobileElement> getSubscribeToETPrimeButton() {
		return topHeaderSubscribetoEtPrime;
	}

	public MobileElement getPlanPage() {
		return planPage;
	}

	public MobileElement getTwoEarlyPlan() {
		return TwoyearlyPlan;
	}

	public MobileElement getCreditCard() {
		return creditCard;
	}

	public List<MobileElement> getcardDetialsList() {
		return cardDetailsList;
	}
	
	public MobileElement getSubscriptionOption(AppiumDriver<?> driver, String optionName) {
		return (MobileElement) driver.findElement(MobileBy.xpath(
				"//*[(@type='XCUIElementTypeStaticText') and (@value='" + optionName + "')]"));
	}
}
