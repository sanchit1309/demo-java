package web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class NewsLetterSubjectAndDateObjects {

	WebDriver driver;

	public NewsLetterSubjectAndDateObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//a[contains(text(),'Sign In')]")
	private WebElement gmailSIgnIn;

	@FindBy(xpath = "//input[@type='email']")
	private WebElement gmailEMailField;

	@FindBy(xpath = "//input[@type='password']")
	private WebElement gmailPasswordField;

	@FindBy(xpath = "//input[@placeholder='Search mail']")
	private WebElement searchMail;

	@FindBy(xpath = "//table[@class='F cf zt']//tr[1]")
	private List<WebElement> firstNewsletterInMailBox;

	@FindBy(xpath = "//h2[@class='hP']")
	private WebElement subjectLineNewsLetters;

	@FindAll({ @FindBy(xpath = "//p[contains(@class,'size26')]"),
			@FindBy(xpath = "//p[contains(@class,'logo_title')]") })
	private WebElement newsletterType;

	@FindBy(xpath = "//span[@class='g3']")
	private WebElement dateTimeNewsletter;
	@FindAll({ @FindBy(xpath = "//tr[@class='zA zE']"), @FindBy(xpath = "//tr[@class='zA yO']") })
	private List<WebElement> newsLetterList;
    
	@FindBy(xpath="//span[@class='Dj']")
	private List<WebElement> mailsCount;
	
	// Getters//

	public List<WebElement> getMailsCount() {
		return mailsCount;
	}

	public WebElement getDateTimeNewsletter() {
		return dateTimeNewsletter;
	}

	public List<WebElement> getNewsLetterList() {
		return newsLetterList;
	}

	public WebElement getNewsletterType() {
		return newsletterType;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getSearchMail() {
		return searchMail;
	}


	public List<WebElement> getFirstNewsletterInMailBox() {
		return firstNewsletterInMailBox;
	}

	public WebElement getSubjectLineNewsLetters() {
		return subjectLineNewsLetters;
	}

	public WebElement getGmailSIgnIn() {
		return gmailSIgnIn;
	}

	public WebElement getGmailEMailField() {
		return gmailEMailField;
	}

	public WebElement getGmailPasswordField() {
		return gmailPasswordField;
	}

}
