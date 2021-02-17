package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

public class StoryPageObjects {

	// @FindBy(xpath = "//*[@id=\"mainPage\"]/div[1]/div[2]/article/h1")
	@FindBy(xpath = "//article//h1")
	private WebElement articleHeading;

	@FindBy(xpath = "//*[@id='shareOptions']//a[@class='message']")
	private WebElement messageShareIcon;

	@FindBy(xpath = "//div[@id='error_msg']/following-sibling::div//span[contains(@data-action,'Facebook')]")
	private WebElement fbShareIcon;

	@FindBy(xpath = "//*[@id='shareOptions']//a[@class='gplus']")
	private WebElement googleShareIcon;

	@FindBy(xpath = " //div[@id='error_msg']/following-sibling::div//span[contains(@data-action,'Twitter')]")
	private WebElement twitterShareIcon;

	@FindBy(xpath = "//div[@id='error_msg']/following-sibling::div//span[contains(@data-action,'Linkedin')]")
	private WebElement linkedInSharIcon;

	@FindBy(xpath = "//div[@id='platformDialogContent']//div[@class='sharerAttachmentTitle']/span")
	private WebElement sharedHeading;//

	@FindBy(xpath = "//*[@id=\"u_0_12\"]")
	private WebElement fbPostButton;

	@FindBy(xpath = ".//*[@id='yDmH0d']/c-wiz/div[4]/div/c-wiz/c-wiz/content/div[2]/div[4]/content")
	private WebElement googlePostButton;

	@FindBy(xpath = "//*[@id=\"KxoNje\"]/a")
	private WebElement googleArticeHeadline;

	@FindBy(xpath = "//div/textarea")
	private WebElement twitterArticeHeadline;

	@FindBy(xpath = "//*[@id=\"share-view-title\"]")
	private WebElement linkedInArticleHeadline;

	@FindBy(xpath = "//*[@id=\"yui-gen4\"]/div[3]/input")
	private WebElement linkedInPostButton;

	@FindBy(xpath = "//*[@id=\"update-form\"]/div[3]/fieldset/input[2]")
	private WebElement twitterPostButton;

	@FindBy(xpath = "//*[@id=\"post-error\"]")
	private WebElement twitterErrorMessage;

	@FindBy(xpath = "//*[@id=\"mainPage\"]//time")
	private WebElement articleDate;

	@FindBy(xpath = "//*[@id='m-future-page-header-title']")
	private WebElement fbSharePageMessage;

	@FindBy(className = "error")
	private WebElement linkedInErrorMessage;

	@FindBy(className = "success")
	private WebElement linkedInSuccessMessage;

	@FindBy(xpath = "//*[@id=\"session\"]/h2/a/img")
	private WebElement twitterLoggeInUserIcon;

	@FindBy(xpath = "//*[@id=\"bd\"]/div[1]")
	private WebElement twitterSuccessMessage;

	@FindBy(xpath = "//*[@id='showreadmore']")
	private WebElement readMoreButton;

	@FindBy(xpath = "//h3[@class='show_prime']/a")
	private WebElement primeArticle;

	@FindBy(xpath = "//div[@id='colombiaAdBox']/div[1]")
	private WebElement moreFromET;

	@FindBy(xpath = "\"(//h2[contains(text(),'Promoted Stories')]//following-sibling::div)[1]")
	private WebElement promotedStories;

	@FindBy(xpath = "//input[@name='session[username_or_email]']")
	private WebElement twitterEmail;

	@FindBy(xpath = "//input[@name='session[password]']")
	private WebElement twitterPwd;

	@FindBy(xpath = "//span[contains(text(), 'Log in')]")
	private WebElement twitterLogin;

	@FindBy(xpath = "(//textarea)[1]")
	private WebElement twitterHeadline;
	
	@FindBy(xpath="//a/following-sibling::div/div[contains(@class,'2dd6bfafab')]")
	private WebElement headerSharingIcon;

	

	//// Getters
	public WebElement getMessageShareIcon() {
		return messageShareIcon;
	}

	public WebElement getFbShareIcon() {
		return fbShareIcon;
	}

	public WebElement getArticleHeading() {
		return articleHeading;
	}

	public WebElement getSharedHeading() {
		return sharedHeading;
	}

	public WebElement getTwitterShareIcon() {
		return twitterShareIcon;
	}

	public WebElement getLinkedInSharIcon() {
		return linkedInSharIcon;
	}

	public WebElement getFbPostButton() {
		return fbPostButton;
	}

	public WebElement getGoogleShareIcon() {
		return googleShareIcon;
	}

	public WebElement getTwitterArticeHeadline() {
		return twitterArticeHeadline;
	}

	public WebElement getGoogleArticeHeadline() {
		return googleArticeHeadline;
	}

	public WebElement getLinkedInPostButton() {
		return linkedInPostButton;
	}

	public WebElement getLinkedInArticleHeadline() {
		return linkedInArticleHeadline;
	}

	public WebElement getGooglePostButton() {
		return googlePostButton;
	}

	public WebElement getTwitterPostButton() {
		return twitterPostButton;
	}

	public WebElement getTwitterErrorMessage() {
		return twitterErrorMessage;
	}

	public WebElement getFbSharePageMessage() {
		return fbSharePageMessage;
	}

	public WebElement getArticleDate() {
		return articleDate;
	}

	public WebElement getLinkedInSuccessMessage() {
		return linkedInSuccessMessage;
	}

	public WebElement getLinkedInErrorMessage() {
		return linkedInErrorMessage;
	}

	public WebElement getTwitterLoggeInUserIcon() {
		return twitterLoggeInUserIcon;
	}

	public WebElement getTwitterSuccessMessage() {
		return twitterSuccessMessage;
	}

	public WebElement getReadMoreButton() {
		return readMoreButton;
	}

	public WebElement getPrimeArticle() {
		return primeArticle;
	}

	public WebElement getMoreFromET() {
		return moreFromET;
	}

	public WebElement getPromotedStories() {
		return promotedStories;
	}

	public WebElement getTwitterEmail() {
		return twitterEmail;
	}

	public WebElement getTwitterPwd() {
		return twitterPwd;
	}

	public WebElement getTwitterLogin() {
		return twitterLogin;
	}

	public WebElement getTwitterHeadline() {
		return twitterHeadline;
	}

	
	public WebElement getHeaderSharingIcon() {
		return headerSharingIcon;
	}
	
	
	public List<WebElement> getSectionNews(String sectionDiv) {
		List<WebElement> sectionNews;
		sectionNews = driver
				.findElements(By.xpath("//li/a[@data-sectiontype=\"" + sectionDiv + "\"]/*[string-length(text())>0]"));
		return sectionNews;
	}
	
	
	public List<WebElement> getSectionNewsHref(String sectionDiv) {
		List<WebElement> sectionNews;
		sectionNews = driver.findElements(By.xpath("//li/a[@data-sectiontype=\"" + sectionDiv + "\"]"));
		return sectionNews;
	}
}
