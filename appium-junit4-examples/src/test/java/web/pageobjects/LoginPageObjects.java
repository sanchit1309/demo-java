package web.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageObjects {

	@FindBy(xpath = "//span[contains(text(),'with Email or Mobile')]")
	private WebElement continueWithEmail;

	@FindBy(name = "email")
	private WebElement email;

	@FindBy(name = "lg_login")
	private WebElement continuebtn;

	@FindBy(name = "password")
	private WebElement password;

	@FindBy(id = "signInButtonDiv")
	private WebElement signInBtn;

	@FindBy(css = ".lg_fb")
	private WebElement fbSignInButton;

	@FindBy(id = "gpRedsigninLong")
	private WebElement gplusSignInButton;

	@FindBy(xpath = "//input[@class='lg_btn' and @onclick='objUserLogin.login();']")
	private WebElement lastStepSignIn;

	@FindBy(xpath = "//*[contains(@id,'Email') or contains(@id,'email') or contains(@id,'identifierId')]")
	private WebElement googlePlusEmail;

	@FindBy(xpath = "//*[contains(@id,'Password') or contains(@id,'password') or contains(@id,'Passwd')]")
	private WebElement googlePlusPassword;

	@FindBy(xpath = "//*[contains(@id,'next') or contains(@id,'Next')]")
	private WebElement next;

	@FindBy(xpath = "//*[contains(@id,'Next') or contains(@id,'signIn')]")
	private WebElement googlePlusSignIn;

	@FindBy(xpath = "//*[@id='email']")
	private WebElement fbEmail;

	@FindBy(xpath = "//*[@id='pass']")
	private WebElement fbPassword;

	@FindBy(xpath = "//*[@type='submit' and @name='login']")
	private WebElement fbLogin;

	@FindBy(xpath = "//*[@id='password']//input")
	private WebElement gmailPasswordSection;

	@FindBy(xpath = "//iframe[contains(@src,'https://smartlock.google.com/iframe')]")
	private WebElement oneTapLoginIframe;

	@FindBy(xpath = "//div/button[@id='continue-as']")
	private WebElement oneTapButton;

	@FindBy(xpath = "//div[@id='close']")
	private WebElement oneTapCloseButton;

	@FindBy(xpath = "//input[@id='username']")
	private WebElement linkedInUserName;

	@FindBy(xpath = "//input[@id='password']")
	private WebElement linkedInpassword;

	@FindBy(xpath = "//button[text()='Sign in']")
	private WebElement linkedInSignInBtn;

	@FindBy(xpath = "//input[@name='session[username_or_email]']")
	private WebElement twitterEMailField;

	@FindBy(xpath = "//input[@name='session[password]']")
	private WebElement twitterPasswordField;

	@FindBy(xpath = "//div[@role='button']//span[text()='Log in']")
	private WebElement twitterLoginButton;
	
	//////////////////////////////////////////
	public WebElement getFbEmail() {
		return fbEmail;
	}

	public WebElement getFbPassword() {
		return fbPassword;
	}

	public WebElement getFbLogin() {
		return fbLogin;
	}

	public WebElement getGooglePlusEmail() {
		return googlePlusEmail;
	}

	public WebElement getGooglePlusPassword() {
		return googlePlusPassword;
	}

	public WebElement getNext() {
		return next;
	}

	public WebElement getGooglePlusSignIn() {
		return googlePlusSignIn;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getSignInBtn() {
		return signInBtn;
	}

	public WebElement getLastStepSignIn() {
		return lastStepSignIn;
	}

	public WebElement getFbSignInButton() {
		return fbSignInButton;
	}

	public WebElement getGplusSignInButton() {
		return gplusSignInButton;
	}

	public WebElement getContinueWithEmail() {
		return continueWithEmail;
	}

	public WebElement getContinuebtn() {
		return continuebtn;
	}

	public WebElement getGmailPasswordSection() {
		return gmailPasswordSection;
	}

	public WebElement getOneTapLoginIframe() {
		return oneTapLoginIframe;
	}

	public WebElement getOneTapButton() {
		return oneTapButton;
	}

	public WebElement getOneTapCloseButton() {
		return oneTapCloseButton;
	}

	public WebElement getLinkedInUserName() {
		return linkedInUserName;
	}

	public WebElement getLinkedInpassword() {
		return linkedInpassword;
	}

	public WebElement getLinkedInSignInBtn() {
		return linkedInSignInBtn;
	}

	public WebElement getTwitterEMailField() {
		return twitterEMailField;
	}

	public WebElement getTwitterPasswordField() {
		return twitterPasswordField;
	}

	public WebElement getTwitterLoginButton() {
		return twitterLoginButton;
	}
}
