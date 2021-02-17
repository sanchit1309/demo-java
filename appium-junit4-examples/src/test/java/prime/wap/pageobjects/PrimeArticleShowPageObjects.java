package prime.wap.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class PrimeArticleShowPageObjects {
	private WebDriver driver;
	
	public PrimeArticleShowPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//span[contains(@class,'_1H-v_')]")
	private WebElement subCategoryTitle;
	
	@FindBy(xpath = "//span[contains(@class,'_3bxJn')]/img | //span[contains(@class,'_2x8ht')]/img")
	private WebElement primeIconAboveStoryTitle;
	
	@FindBy(xpath = "//h1[contains(@class,'_2d4r0')] | //h1[contains(@class,'_1Uh-e')]")
	private WebElement storyTitle;
	
	@FindBy(xpath = "//div[contains(@class,'_1Q6Un')] | //div[contains(@class,'K6HMD')]")
	private WebElement synopsis;
	
	@FindBy(xpath = "//div[@class='primeContent col s_col font_faus artText paywall' or @class='article_content col s_col paywall']//div")
	private WebElement articleText;
	
	@FindBy(xpath = "//div[@id='metainfo2'] | //div[contains(@class,'CYwM8')]/time")
	private WebElement minRead;
	
	@FindBy(xpath = "//div[contains(@id,'articlebody')]")
	private WebElement articleBody;
	
	@FindBy(xpath = "//div[@id='metainfo2']/div[@displaytype='GDPR']//div[contains(@class,'9c1')] | "
			+ "//div[@id='metainfo2']/div[@displaytype='GDPR']//span[contains(@class,'946')] | "
			+ "//div[@id='metainfo2']/div[@displaytype='GDPR']//span[contains(@class,'baa')] | "
			+ "//div[@id='metainfo2']/div[@displaytype='GDPR']//a[@id='commentIcon'] | "
			+ "//div[contains(@class,'f226')]//div[contains(@class,'9c1')] | "
			+ "//div[contains(@class,'f226')]//span[contains(@class,'946')] | "
			+ "//div[contains(@class,'f226')]//span[contains(@class,'baa')] | "
			+ "//div[contains(@class,'f226')]//div[contains(@class,'9c1')] | "
			+ "//div[contains(@class,'f226')]//a[@id='commentIcon']")
	private List<WebElement> shareArticleList;
	
	@FindBy(xpath = "//ul[@class='act_list tac']/li/span[@class='act_txt']")
	private List<WebElement> giftFontSaveCommentList;
	
	@FindBy(xpath = "//div[contains(@class,'_25G_8')]")
	private WebElement etPrimePaywallBlocker;
	
	@FindBy(xpath = "//p[text()='ADD COMMENTS'] | //p[text()='ADD COMMENT']")
	private WebElement addCommentbutton;
	
	@FindBy(xpath  = "//*[text()='Popular with Readers']")
	private WebElement popularWithReaderSection;
	
	@FindBy(xpath = "//div[@class='clearfix mr_str  prel ']")
	private WebElement moreStories;
	
	@FindBy(xpath = "//h2[contains(@class,'_3pvQM') and contains(text(),'More')]")
	private WebElement moreStoriesTitle;
	
	@FindBy(xpath = "//img[contains(@src,'gift_icon')]")
	private WebElement giftIcon;
	
	@FindBy(xpath = "//img[contains(@src,'gift_banner')] | //img[contains(@src,'gift_limit')]")
	private WebElement giftPopup;
	
	@FindBy(xpath = "//div[@class='gift_popup']//div[@class='popup_form']")
	private WebElement giftPopupForm;
	
	@FindBy(xpath="//div[contains(@class,'_3rKCU')]/textarea")
	private WebElement giftArticleMessge;
	
	@FindBy(xpath="//div[contains(@class,'react')]/input")
	private WebElement giftArticleEmailFeild;
	
	@FindBy(xpath="//p[contains(@class,'_184Fu')]")
	private WebElement sendGiftButton;
	
	@FindBy(xpath="//div[@class='popupmsg_wrap tac']")
	private WebElement giftArticlSuccess;
	
	@FindBy(xpath="//p[contains(@class,'_2Xl17')]")
	private WebElement closePopUp;
	
	@FindBy(xpath = "//p[contains(@class,'_2GQZS')]")
	private WebElement remainingGiftCount;
	
	@FindBy(xpath = "//div[contains(@class,'react')]")
	private WebElement giftEmailTextBox;
	
	@FindBy(xpath = "//div[@class='err_box']")
	private WebElement giftToItselfTxt;
	
	@FindBy(xpath = "//p[contains(text(),' got an article as a gift')]")
	private WebElement giftPaywall;
	
	@FindBy(xpath = "//div[contains(@class,'021a')]")
	private WebElement whyETPrimeBox;
	
	@FindBy(xpath = "//div[@class='article_pod']/div[@data-type='audio']")
	private WebElement audioSummaryIcon;
	
	@FindBy(xpath = "//div[@class='p_byline']/div[@class='p_auth_list tac']")
	private WebElement authorName;
	
	@FindBy(xpath = "//div[@class='errorBlock']")
	private WebElement errorBlock;
	
	@FindBy(xpath = "//img[contains(@src,'gift_limit')]/../following-sibling::div/p[text()='Limit Reached']")
	private WebElement giftLimitReach;
	
	@FindBy(xpath = "//div[@id='metainfo2']//div[@class='_31OgY'] | //div[@class='CYwM8']//div[@class='_2HHX_']")
	private WebElement shareArticleIcon;

	@FindBy(xpath = "//div[@id='metainfo2']//span[@class='_16KlU'] | //div[@class='CYwM8']//span[@class='_16KlU']")
	private WebElement fontIcon;
	
	@FindBy(xpath = "//div[@id='metainfo2']//span[@class='_2GaMI '] | //div[@class='CYwM8']//span[@class='_2GaMI ']")
	private WebElement saveIcon;
	
	@FindBy(xpath = "//div[@id='metainfo2']//span[@class='_4fNaf'] | //div[@class='CYwM8']//span[@class='_4fNaf']")
	private WebElement commentIcon;
	
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
	
	public WebElement getArticleBody() {
		return articleBody;
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
	
	public WebElement getGiftLimitReach() {
		return giftLimitReach;
	}
	
	public WebElement getFontIcon() {
		return fontIcon;
	}
	
	public WebElement getShareArticleIcon() {
		return shareArticleIcon;
	}
	
	public WebElement getSaveIcon() {
		return saveIcon;
	}
	
	public WebElement getCommentIcon() {
		return commentIcon;
	}
}
