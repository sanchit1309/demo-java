package pwa.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageObjects {


	@FindBy(xpath="//*[contains(text(), 'Sign In')]")
	private WebElement menuSignIn;
	
	@FindBy(css=".loginText .signUp")
	private WebElement menuSignUp;
	
	@FindBy(xpath="//span[text()='Continue with Email or Mobile']")
	private WebElement emailLoginButton;
	
	@FindBy(xpath="//div[@id='lg_login']//input[@name='email']")
	private WebElement emailId;
	
	@FindBy(xpath="//div[@id='lg_password']//input[@type='password']")
	private WebElement password;
	
	@FindBy(xpath="//input[contains(@onclick,'loginIdCheck')]")
	private WebElement signInBtn;
	
	@FindBy(className="fb")
	private WebElement fbLoginButton;
	
	@FindBy(xpath="//input[contains(@placeholder, 'password')]")
	private List<WebElement> inputField;
	
	//*[@id="u_0_1"]/div[4]/div/div[1]/input
	@FindBy(xpath="//input[@*[contains(.,'mail')]]")
	private WebElement fbEmail;
	
	@FindBy(xpath="//div[@id='lg_password']//input[@value='Continue']")
	private WebElement lastStepSignIn;
	
	@FindBy(xpath="//input[@*[contains(.,'password')]]")
	private List<WebElement> fbPassword;
	
	@FindBy(xpath="//*[@*[contains(.,'Log In')]]")
	private WebElement fbSubmit;
	
	@FindBy(xpath="//input[@*[contains(.,'Continue')]]")
	private List<WebElement> fbContinue;
	
	@FindBy(id = "gpRedsigninLong")
	private WebElement gplusSignInButton;
	
	@FindBy(id="identifierId")
	private WebElement gplusEmail;
	
	@FindBy(xpath="//*[@id=\"password\"]/div[1]/div/div[1]/input")
	private WebElement gplusPassword;
	
	@FindBy(xpath="//*[@id=\"identifierNext\"]/content/span")
	private WebElement gplusEmailNext;
	
	@FindBy(xpath="//*[@id=\"passwordNext\"]/content/span")
	private WebElement gplusPassNext;
	
	@FindBy(id="submit_approve_access")
	private WebElement gplusAllow;
	
	@FindBy(id="submit_deny_access")
	private WebElement gplusDeny;
	
	@FindBy(xpath="//*[@id=\"tp_name\"]")
	private WebElement menuLoggedInUserDetails;
	
	@FindBy(className="profilePicture")
	private WebElement profilePicture;
	
	@FindBy(css=".logout a")
	private WebElement menuLogout;
	
	@FindBy(xpath ="//*[@id=\"session_key-connectLoginForm\"]")
	private WebElement linkedInEmail;
	
	@FindBy(xpath ="//*[@id=\"session_password-oauthAuthorizeForm\"]")
	private WebElement linkedInPwd;
	
	@FindBy(xpath = "//*[@id=\"body\"]/div/form/div/ul/li[1]/input")
	private WebElement linkedInLogin;
	
	@FindBy(xpath = "//*[@id=\"username_or_email\"]")
	private WebElement twitterEmail;
	
	@FindBy(xpath = "//*[@id=\"password\"]")
	private WebElement twitterPassword;
	
	@FindBy(xpath ="//*[@id=\"update-form\"]/div[3]/fieldset[2]/input[2]")
	private WebElement twitterLogin;
	
	@FindBy(xpath ="//*[@id=\"u_0_1\"]/div[3]/div/div[1]/input")
	private WebElement emailFb;
	
//	@FindBy(xpath="//*[@id=\"u_0_1\"]/div[5]/div[1]/button")
	@FindBy(xpath="//button")
	private List<WebElement> nextBtton;
	
	@FindBy(xpath="//*[@id=\"u_0_7\"]")
	private WebElement loginFb;
	
	@FindBy(xpath="//div[contains(text(), 'Sign in')]")
	private WebElement newMenuSignIn;
	
	@FindBy(xpath ="//span[contains(text(),'Sign in with Email or Mobile')]")
	private WebElement newEmailLoginBtn;
	
	@FindBy(xpath = "//div[text()='Sign out']")
	private WebElement newSignOutBtn;
	
	@FindBy(xpath = "//div[@id='mainMenu']/div[1]/div[1]")
	private WebElement newUserName;
	
	@FindBy(xpath = "//div[text()='Sign out']")
	private WebElement logout;
	
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

	public WebElement getLastStepSignIn() {
		return lastStepSignIn;
	}

	public WebElement getEmailLoginButton() {
		return emailLoginButton;
	}
	
	public WebElement getNewMenuSignIn() {
		return newMenuSignIn;
	}
	
	public WebElement getNewEmailLoginBtn() {
		return newEmailLoginBtn;
	}
	
	public WebElement getNewSignOutBtn() {
		return newSignOutBtn;
	}
	
	public WebElement getnewUserName() {
		return newUserName;
	}
	
	public WebElement getLogout() {
		return logout;
	}
}
