package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * 
 * Login Page Object class for WAP
 *
 */
public class LoginPageObjects {

	@FindBy(xpath = "//*[contains(text(), 'Sign In')]")
	private WebElement menuSignIn;

	@FindBy(css = ".loginText .signUp")
	private WebElement menuSignUp;

	@FindBy(xpath = "//div[contains(@class,'sso')]")
	private WebElement emailLoginButton;

	@FindBy(xpath = "//div[@id='lg_login']//input[@name='email']")
	private WebElement emailId;

	@FindBy(xpath = "//div[@id='lg_password']//input[@type='password']")
	private WebElement password;

	@FindBy(xpath = "//div[@id='lg_login']//input[@type='submit']")
	private WebElement signInBtn;

	@FindBy(xpath = "//div[contains(@class,'fb')]")
	private WebElement fbLoginButton;

	@FindBy(xpath = "//div[@id='lg_password']//input[@value='Continue']")
	private WebElement lastStepSignIn;

	@FindBy(xpath = "//input[contains(@placeholder, 'password')]")
	private List<WebElement> inputField;

	// *[@id="u_0_1"]/div[4]/div/div[1]/input
	@FindBy(xpath = "//input[@*[contains(.,'mail')]]")
	private WebElement fbEmail;

	@FindBy(xpath = "//input[@*[contains(.,'password')]]")
	private List<WebElement> fbPassword;

	@FindBy(xpath = "//*[@*[contains(.,'Log In')]]")
	private WebElement fbSubmit;

	@FindBy(xpath = "//input[@*[contains(.,'Continue')]]")
	private List<WebElement> fbContinue;

	@FindBy(xpath = "//button[@name='__CONFIRM__']")
	private WebElement fbConfirm;

	@FindBy(xpath = "//span[text()='Sign in with Google']")
	private WebElement gplusSignInButton;

	@FindBy(id = "identifierId")
	private WebElement gplusEmail;

	@FindBy(xpath = "//*[@id=\"password\"]/div[1]/div/div[1]/input")
	private WebElement gplusPassword;

	@FindBy(xpath = "//*[@id=\"identifierNext\"]//span[text()='Next']")
	private WebElement gplusEmailNext;

	@FindBy(xpath = "//*[@id=\"passwordNext\"]//span[text()='Next']")
	private WebElement gplusPassNext;

	@FindBy(id = "submit_approve_access")
	private WebElement gplusAllow;

	@FindBy(id = "submit_deny_access")
	private WebElement gplusDeny;

	@FindBy(xpath = "//*[@id=\"tp_name\"]")
	private WebElement menuLoggedInUserDetails;

	@FindBy(className = "profilePicture")
	private WebElement profilePicture;

	@FindBy(css = ".logout a")
	private WebElement menuLogout;

	@FindBy(xpath = "//*[@id=\"session_key-connectLoginForm\"]")
	private WebElement linkedInEmail;

	@FindBy(xpath = "//*[@id=\"session_password-oauthAuthorizeForm\"]")
	private WebElement linkedInPwd;

	@FindBy(xpath = "//*[@id=\"body\"]/div/form/div/ul/li[1]/input")
	private WebElement linkedInLogin;

	@FindBy(xpath = "//input[@type='text']")
	private WebElement twitterEmail;

	@FindBy(xpath = "//*[@id=\"password\"]")
	private WebElement twitterPassword;

	@FindBy(xpath = "//*[@id=\"update-form\"]/div[3]/fieldset[2]/input[2]")
	private WebElement twitterLogin;

	@FindBy(xpath = "//*[@id=\"u_0_1\"]/div[3]/div/div[1]/input")
	private WebElement emailFb;

	// @FindBy(xpath="//*[@id=\"u_0_1\"]/div[5]/div[1]/button")
	@FindBy(xpath = "//button")
	private List<WebElement> nextBtton;

	@FindBy(xpath = "//*[@id=\"u_0_7\"]")
	private WebElement loginFb;

	@FindBy(xpath = "//div[@isopen='true' and @id='mainMenu']//div[text()='Sign in']")
	private WebElement footerMenuSignInIcon;

	@FindBy(xpath = "//div[@isopen='true' and @id='mainMenu']//div[text()='Sign out']")
	private WebElement footerMenuSignOutIcon;

	@FindBy(xpath = "//iframe[contains(@src,'https://smartlock.google.com/iframe')]")
	private WebElement oneTapLoginIframe;

	@FindBy(xpath = "//div/button[@id='continue-as']")
	private WebElement oneTapButton;

	@FindBy(xpath = "//div[@id='close']")
	private WebElement oneTapCloseButton;

	@FindBy(xpath = "//a[@class='login signIn']//div[text()='Sign out']")
	private WebElement nonReactSignOutLink;

	@FindBy(xpath = "//span[text()='Continue']")
	private WebElement fbLoginContinueButton;

	public WebElement getFooterMenuSignInIcon() {
		return footerMenuSignInIcon;
	}

	public WebElement getFooterMenuSignOutIcon() {
		return footerMenuSignOutIcon;
	}

	public WebElement getMenuSignIn() {
		return menuSignIn;
	}

	public WebElement getMenuSignUp() {
		return menuSignUp;
	}

	public WebElement getEmailId() {
		return emailId;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getSignInBtn() {
		return signInBtn;
	}

	public WebElement getFbLoginButton() {
		return fbLoginButton;
	}

	public WebElement getFbEmail() {
		return fbEmail;
	}

	public List<WebElement> getFbPassword() {
		return fbPassword;
	}

	public WebElement getFbSubmit() {
		return fbSubmit;
	}

	public WebElement getGplusSignInButton() {
		return gplusSignInButton;
	}

	public WebElement getGplusAllow() {
		return gplusAllow;
	}

	public WebElement getGplusDeny() {
		return gplusDeny;
	}

	public WebElement getMenuLoggedInUserDetails() {
		return menuLoggedInUserDetails;
	}

	public WebElement getProfilePicture() {
		return profilePicture;
	}

	public WebElement getMenuLogout() {
		return menuLogout;
	}

	public List<WebElement> getFbContinue() {
		return fbContinue;
	}

	public WebElement getGplusEmail() {
		return gplusEmail;
	}

	public WebElement getGplusPassword() {
		return gplusPassword;
	}

	public WebElement getGplusEmailNext() {
		return gplusEmailNext;
	}

	public WebElement getGplusPassNext() {
		return gplusPassNext;
	}

	public WebElement getLinkedInEmail() {
		return linkedInEmail;
	}

	public WebElement getLinkedInPwd() {
		return linkedInPwd;
	}

	public WebElement getLinkedInLogin() {
		return linkedInLogin;
	}

	public WebElement getTwitterEmail() {
		return twitterEmail;
	}

	public WebElement getTwitterPassword() {
		return twitterPassword;
	}

	public WebElement getTwitterLogin() {
		return twitterLogin;
	}

	public List<WebElement> getInputField() {
		return inputField;
	}

	public WebElement getEmailFb() {
		return emailFb;
	}

	public List<WebElement> getNextBtton() {
		return nextBtton;
	}

	public WebElement getLoginFb() {
		return loginFb;
	}

	public WebElement getEmailLoginButton() {
		return emailLoginButton;
	}

	public WebElement getLastStepSignIn() {
		return lastStepSignIn;
	}

	public WebElement getFbConfirm() {
		return fbConfirm;
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

	public WebElement getNonReactSignOutLink() {
		return nonReactSignOutLink;
	}

	public WebElement getFbLoginContinueButton() {
		return fbLoginContinueButton;
	}

}
