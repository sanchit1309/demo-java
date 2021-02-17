package web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewsletterSubsPageObjects {
	private WebDriver driver;

	public NewsletterSubsPageObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//span[@class='unsubscribe']")
	private List<WebElement> unSubscribeButton;

	@FindBy(xpath = "//button[@class='unsub-btn']")
	private WebElement unSubscribeButtonPopUp;

	@FindBy(xpath = "//span[text()='Close']")
	private WebElement unSubscribePopUpCloseBtn;

	@FindBy(xpath = "//div[@class='subscribedList']//ul//li//div//span[@class='blue']/a")
	private List<WebElement> subscribedNewsletterList;

	@FindBy(xpath = "//div[@class='checkbox']//input[@id='check_all']/../span")
	private WebElement selectAllSubsCheckbox;

	@FindBy(xpath = "//form//input[@name='email']")
	private WebElement emailIdBox;

	@FindBy(xpath = "//form//input[@id='submitEmail']")
	private WebElement submitButton;

	@FindBy(xpath = "//div[@class='submitted']//div//p[@class='messageHead']")
	private WebElement thankYouMessage;

	@FindBy(xpath = "//div[@class='checkNewsLetters']//div//ul//li//span[@class='label_blue']")
	private List<WebElement> unsubscribedNewsletterList;

	// nl(newsletter) xpath is common for economy tech etc. so type1
	@FindBy(xpath = "//p//following-sibling::a[contains(text(),' ')]")
	private List<WebElement> nlArticleListType1;

	@FindBy(xpath = "//table[@class='footerSecNav']//tr//td/p[contains(text(),'')]")
	private WebElement nlDateTabType1;

	// nl(newsletter) xpath is common for smallbiz, defence etc. so type2
	@FindBy(xpath = "//div[@class='fsize22']//p")
	private WebElement nlDateTabType2;

	@FindBy(xpath = "//td[@align='center']//table//tr//td//a")
	private List<WebElement> nlArticleListType2;

	// nl(newsletter) xpath is common for slideshows, sunday wrap etc. so type2
	@FindBy(xpath = "//tr//td/div[not(contains(@align,'center'))]/a")
	private List<WebElement> nlArticleListType3;

	@FindBy(xpath = "//div[@class='fsize22']")
	private WebElement nlDateTabType3;
	
	@FindBy(xpath="//p[@class='date']")
	private WebElement morningMarketsDate;

	@FindBy(xpath = "//tr//td//a//span[contains(text(),' ')]")
	private List<WebElement> nlArticleListSlideshows;

	@FindBy(xpath = "//a[contains(@href,'https')]")
	private List<WebElement> allUrls;

	/////// Getters////////

	public List<WebElement> getAllUrls() {
		return allUrls;
	}

	public List<WebElement> getNlArticleListSlideshows() {
		return nlArticleListSlideshows;
	}

	public List<WebElement> getNlArticleListType3() {
		return nlArticleListType3;
	}

	public WebElement getNlDateTabType3() {
		return nlDateTabType3;
	}
	
	public WebElement getMorningMarketsDate(){
		return morningMarketsDate;
	}

	public List<WebElement> getNlArticleListType2() {
		return nlArticleListType2;
	}

	public WebElement getNlDateTabType2() {
		return nlDateTabType2;
	}

	public WebElement getNlDateTabType1() {
		return nlDateTabType1;
	}

	public List<WebElement> getNlArticleListType1() {
		return nlArticleListType1;
	}

	public List<WebElement> getUnsubscribedNewsletterList() {
		return unsubscribedNewsletterList;
	}

	public WebElement getThankYouMessage() {
		return thankYouMessage;
	}

	public WebElement getSubmitButton() {
		return submitButton;
	}

	public WebElement getEmailIdBox() {
		return emailIdBox;
	}

	public WebElement getSelectAllSubsCheckbox() {
		return selectAllSubsCheckbox;
	}

	public List<WebElement> getSubscribedNewsletterList() {
		return subscribedNewsletterList;
	}

	public WebElement getUnSubscribePopUpCloseBtn() {
		return unSubscribePopUpCloseBtn;
	}

	public WebElement getUnSubscribeButtonPopUp() {
		return unSubscribeButtonPopUp;
	}

	public List<WebElement> getUnSubscribeButton() {
		return unSubscribeButton;
	}

	public List<WebElement> getTableHeaderName(String tableName) {
		return driver.findElements(By.xpath("//table//tr/*[text()='" + tableName + "']"));
	}

}