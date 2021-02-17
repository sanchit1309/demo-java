package prime.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class ArticleShowPageObjects {
	private WebDriver driver;
	
	public ArticleShowPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//a[@class='artSec']")
	private WebElement subCategoryTitle;
	
	@FindBy(xpath = "//span[@class='cSprite icon_pp']")
	private WebElement primeIconAboveStoryTitle;
	
	@FindBy(xpath = "//h1[@class='artTitle font_faus']")
	private WebElement storyTitle;
	
	@FindBy(xpath = "//h2[@class='artSyn tac' or @class='artSyn tac font_mon']")
	private WebElement synopsis;
	
	@FindBy(xpath = "//div[@class='primeContent col s_col font_faus artText paywall' or @class='article_content col s_col paywall']//div")
	private WebElement articleText;
	
	@FindBy(xpath = "//div[@class='mins_read tac']")
	private WebElement minRead;
	
	@FindBy(xpath = "//div[@class='p_art_shr']/div[@class='shr_head']")
	private WebElement shareArticleText;
	
	@FindBy(xpath = "//div[@class='p_art_shr']/div[@class='shr_list tac']/a")
	private List<WebElement> shareArticleList;
	
	@FindBy(xpath = "//ul[@class='act_list tac']/li/span[@class='act_txt']")
	private List<WebElement> giftFontSaveCommentList;
	
	@FindBy(xpath = "//section[contains(@class,'prime_paywall')]")
	private WebElement etPrimePaywallBlocker;
	
	@FindBy(xpath = "//button[@class='addCmnt open_cmnt']")
	private WebElement addCommentbutton;
	
	@FindBy(xpath  = "//div[@id='popularReadersSlider']")
	private WebElement popularWithReaderSection;
	
	@FindBy(xpath = "//div[@class='clearfix mr_str  prel ']")
	private WebElement moreStories;
	
	@FindBy(xpath = "//div[@class='clearfix mr_str  prel ']//h3")
	private WebElement moreStoriesTitle;
	
	@FindBy(xpath = "//li[@class='gift_act']")
	private WebElement giftIcon;
	
	@FindBy(xpath = "//div[@class='gift_popup']//*[text()='Limit Reached']")
	private WebElement giftPopupLimitReached;
	
	@FindBy(xpath = "//div[@class='gift_popup']//div[@class='popup_form']")
	private WebElement giftPopupForm;
	
	@FindBy(xpath="//div[@class='gift_msg']//textarea")
	private WebElement giftArticleMessge;
	
	@FindBy(xpath="//input[@id='tagbox']")
	private WebElement giftArticleEmailFeild;
	
	@FindBy(xpath="//span[@class='btn send_gift']")
	private WebElement sendGiftButton;
	
	@FindBy(xpath="//div[@class='popupmsg_wrap tac']")
	private WebElement giftArticlSuccess;
	
	@FindBy(xpath="//b[@class='popup-close']")
	private WebElement closePopUp;
	
	@FindBy(xpath = "//div[@class='popupmsg_wrap tac']/p")
	private WebElement remainingGiftCount;
	
	@FindBy(xpath = "//div[@class='tagvalue']")
	private WebElement giftEmailTextBox;
	
	@FindBy(xpath = "//div[@class='err_box']")
	private WebElement giftToItselfTxt;
	
	@FindBy(xpath = "//h3[@class='paywall_msg_gift' and text()='You’ve got this Prime Story as a Free Gift']")
	private WebElement giftPaywall;
	
	@FindBy(xpath = "//div[@class='f_list']")
	private WebElement whyETPrimeBox;
	
	@FindBy(xpath = "//div[@class='article_pod']/div[@data-type='audio']")
	private WebElement audioSummaryIcon;
	
	@FindBy(xpath = "//div[@class='p_byline']/div[@class='p_auth_list tac']")
	private WebElement authorName;
	
	@FindBy(xpath = "//div[@class='errorBlock']")
	private WebElement errorBlock;
	
	@FindBy(xpath = "//div[@class='gift_popup']")
	private WebElement giftPopup;
	/* ************** Getters *************** */
	
	
	public WebElement getSubCategoryTitle() {
		return subCategoryTitle;
	}
	
	public WebElement getPrimeIconAboveStoryTitle() { 
		return primeIconAboveStoryTitle;
	}
	
	public WebElement getStoryTitle() {
		return storyTitle;
	}
	
	public WebElement getSynopsis() {
		return synopsis;
	}
	
	public WebElement getArticleText() {
		return articleText;
	}
	
	public WebElement getMinRead() {
		return minRead;
	}
	
	public WebElement getShareArticleText() {
		return shareArticleText;
	}
	
	public List<WebElement> getShareArticleList() {
		return shareArticleList;
	}
	
	public List<WebElement> getGiftFontSaveCommentList() {
		return giftFontSaveCommentList;
	}
	
	public WebElement getEtPrimePaywallBlocker() {
		return etPrimePaywallBlocker;
	}
	
	public WebElement getAddCommentbutton() {
		return addCommentbutton;
	}
	
	public WebElement getPopularWithReaderSection() {
		return popularWithReaderSection;
	}
	
	public WebElement getMoreStories() {
		return moreStories;
	}
	
	public WebElement getMoreStoriesTitle() {
		return moreStoriesTitle;
	}
	
	public WebElement getGiftIcon() {
		return giftIcon;
	}
	
	public WebElement getGiftPopup() {
		return giftPopup;
	}
	
	public WebElement getGiftPopupForm() {
		return giftPopupForm;
	}
	
	public WebElement getGiftArticleMessge() {
		return giftArticleMessge;
	}

	public WebElement getGiftArticleEmailFeild() {
		return giftArticleEmailFeild;
	}

	public WebElement getSendGiftButton() {
		return sendGiftButton;
	}

	public WebElement getGiftArticlSuccess() {
		return giftArticlSuccess;
	}

	public WebElement getClosePopUp() {
		return closePopUp;
	}
	
	public WebElement getRemainingGiftCount() {
		return remainingGiftCount;
	}
	
	public WebElement getGiftEmailTextBox() {
		return giftEmailTextBox;
	}
	
	public WebElement getGiftToItselfTxt() {
		return giftToItselfTxt;
	}
	
	public WebElement getGiftPaywall() {
		return giftPaywall;
	}
	
	public WebElement getWhyETPrimeBox() {
		return whyETPrimeBox;
	}
	
	public WebElement getAudioSummaryIcon() {
		return audioSummaryIcon;
	}
	
	public WebElement getAuthorName() {
		return authorName;
	}
	
	public WebElement getErrorBlock() {
		return errorBlock;
	}
	
	public WebElement getGiftPopupLimitReached() {
		return giftPopupLimitReached;
	}
}
