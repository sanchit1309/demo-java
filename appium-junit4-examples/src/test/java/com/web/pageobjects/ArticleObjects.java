package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import common.launchsetup.BaseTest;

public class ArticleObjects {

	@FindBy(className = "postComment")
	private WebElement PostCommentLink;

	@FindBy(xpath = "//form[@id='commentOnArticle']//textarea ")
	private WebElement CommentTextField;

	@FindBy(xpath = "//form[@id='commentOnArticle']//input[@placeholder='Name']")
	private WebElement CommentNameTextField;

	@FindBy(xpath = "//form[@id='commentOnArticle']//input[@placeholder='Email']")
	private WebElement CommentEmailTextField;

	@FindBy(xpath = "//form[@id='commentOnArticle']//input[@placeholder='Location']")
	private WebElement CommentLocationTextField;

	@FindBy(xpath = "//form[@id='commentOnArticle']//input[@value='Post Comment']")
	private WebElement PostCommentButton;

	@FindBy(xpath = "//div[@id='postCmtBox']//div[contains(@class,'hidden cmtMsg')]//h6")
	private WebElement PostConfirmMsg;

	@FindBy(xpath = "//div[@class='cmtTitle']//a[text()='Newest']")
	private WebElement NewestMsgTab;

	@FindBy(xpath = "//*[contains(@class,'cmtBox' )]")
	private List<WebElement> NoOfComments;

	@FindBy(xpath = "//*[contains(@class,'cmtBox')]")
	private List<WebElement> CommentBox;

	@FindBy(xpath = "//*[contains(@*,'userName')]/*")
	private WebElement UserName;

	@FindBy(xpath = "//div[contains(@class,'cmtText')]/*")
	private WebElement CommentText;

	@FindBy(xpath = "//div[@id='shareHorz']//a[contains(@title,'Increase/Decrease font size')]")
	private WebElement incDecFontSizeHoz;

	@FindBy(xpath = "//a[contains(@title,'Increase/Decrease font size')]")
	private WebElement incDecFontSize;

	@FindBy(xpath = "//section[contains(@class,'pageContent')]//h1")
	private WebElement articleTitle;

	@FindBy(xpath = "//a[contains(@class,'twt') and contains(@class,'shareSprite')]")
	private List<WebElement> twitterSharing;

	@FindBy(xpath = "//textarea [@id='status']")
	private WebElement twitterStatus;

	@FindBy(xpath = "//*[contains(@onclick,'plus.google') or contains(@class,'gplus') ] [not(contains(@id,'goFooterIcon'))]")
	private WebElement googleSharingBtn;

	@FindBy(xpath = "//*[@id='KxoNje']/a/span")
	private WebElement gPlusArticleSharedLink;

	@FindBy(xpath = "//a[contains(@class,'fb') and contains(@class,'shareSprite')]")
	private List<WebElement> facebookSharing;

	/*
	 * @FindAll({ @FindBy(xpath =
	 * "//div[@class='unclickable']//span/div[2]//a[contains(@onclick,'LinkshimAsyncLink')]"
	 * ), @FindBy( xpath =
	 * "//div[@class='unclickable']//div[contains(@class,'clearfix _2r3x')]/div[2]//a"
	 * ) })
	 */
	@FindBy(xpath = "//a[@data-ad-preview='headline']")
	private WebElement facebookSharedLink;

	@FindBy(xpath = ".//section[@id='pageContent']//*[contains(@class,'shareMore')]")
	private List<WebElement> moreSharingBtn;

	@FindBy(xpath = "//div[contains(@class,'artText')]")
	private WebElement articleText;

	@FindBy(css = ".clearfix.title")
	private WebElement headline;

	@FindAll({ @FindBy(css = ".publish_on.flt"), @FindBy(css = ".byline"), @FindBy(xpath = ".//span[@class='date']") })
	private WebElement publishingInfo;

	@FindBy(css = ".section1 .Normal")
	private WebElement description;

	@FindBy(xpath = "//ul[@class='articles']//li//a")
	private List<WebElement> streamingArticleHeading;

	@FindBy(xpath = "//ul[@class='articles']//li//a")
	private List<WebElement> streamBandArticleList;

	@FindBy(css = ".sharingBox>.comment_count>span")
	private WebElement commentTab;

	@FindBy(xpath = "//div[@class='cmtTitle']//h4//a")
	private WebElement liveBlogComment;

	private String alsoRead = "//div[contains(@class,'article_block')][%s]//section[contains(@class,'rel-art')]";

	@FindBy(xpath = "//div[@class='clearfix sharingBox shareVert']//a[@class='bookmark-icon']")
	private WebElement bookmarkIcon;

	@FindBy(xpath = "//a[@id='savedStories']")
	private WebElement saveStoriesTab;

	@FindBy(xpath = "(//article)[1]")
	private WebElement articleBody;

	public WebElement getAlsoRead(int index) {
		return BaseTest.driver.findElement(By.xpath(String.format(alsoRead, index)));
	}

	public WebElement getLiveBlogComment() {
		return liveBlogComment;
	}

	public WebElement getCommentTab() {
		return commentTab;
	}

	public List<WebElement> getStreamBandArticleList() {
		return streamBandArticleList;
	}

	public List<WebElement> getStreamingArticleHeading() {
		return streamingArticleHeading;
	}

	public WebElement getPostCommentLink() {
		return PostCommentLink;
	}

	public WebElement getCommentTextField() {
		return CommentTextField;
	}

	public WebElement getCommentNameTextField() {
		return CommentNameTextField;
	}

	public WebElement getCommentEmailTextField() {
		return CommentEmailTextField;
	}

	public WebElement getCommentLocationTextField() {
		return CommentLocationTextField;
	}

	public WebElement getPostCommentButton() {
		return PostCommentButton;
	}

	public WebElement getPostConfirmMsg() {
		return PostConfirmMsg;
	}

	public WebElement getNewestMsgTab() {
		return NewestMsgTab;
	}

	public List<WebElement> getNoOfComments() {
		return NoOfComments;
	}

	public List<WebElement> getCommentBox() {
		return CommentBox;
	}

	public WebElement getUserName() {
		return UserName;
	}

	public WebElement getCommentText() {
		return CommentText;
	}

	public WebElement getIncDecFontSizeHoz() {
		return incDecFontSizeHoz;
	}

	public WebElement getIncDecFontSize() {
		return incDecFontSize;
	}

	public WebElement getArticleTitle() {
		return articleTitle;
	}

	public List<WebElement> getTwitterSharing() {
		return twitterSharing;
	}

	public WebElement gettwitterStatus() {
		return twitterStatus;
	}

	public WebElement getGoogleSharingBtn() {
		return googleSharingBtn;
	}

	public WebElement getGPlusArticleSharedLink() {
		return gPlusArticleSharedLink;
	}

	public List<WebElement> getFacebookSharing() {
		return facebookSharing;
	}

	public WebElement getfacebookSharedLink() {
		return facebookSharedLink;
	}

	public List<WebElement> getMoreSharingBtn() {
		return moreSharingBtn;
	}

	public WebElement getTwitterStatus() {
		return twitterStatus;
	}

	public WebElement getFacebookSharedLink() {
		return facebookSharedLink;
	}

	public WebElement getArticleText() {
		return articleText;
	}

	public WebElement getHeadline() {
		return headline;
	}

	public WebElement getPublishingInfo() {
		return publishingInfo;
	}

	public WebElement getDescription() {
		return description;
	}

	public WebElement getgPlusArticleSharedLink() {
		return gPlusArticleSharedLink;
	}

	public String getAlsoRead() {
		return alsoRead;
	}

	public WebElement getBookmarkIcon() {
		return bookmarkIcon;
	}

	public WebElement getSaveStoriesTab() {
		return saveStoriesTab;
	}

	public WebElement getArticleBody() {
		return articleBody;
	}

	///////// page objects for comparing live articleshow with dev//////////
	// *********************************************************************//
	// *********************************************************************//
	// *********************************************************************//
	// *********************************************************************//

	@FindBy(xpath = "(//article//h2[@class='title2'])[1]")
	private WebElement articleSynopsis;

	@FindBy(xpath = "(//figcaption)[1]")
	private WebElement articleImageSynopsis;

	@FindBy(xpath = "(//div[@class='artText']//div//div[@class='Normal'])[1]")
	private WebElement articleTextBody;

	@FindBy(xpath = "(//div[@class='publisher'])[1]")
	private WebElement articleByLineText;

	@FindBy(xpath = "(//div[@class='publish_on'])[1]")
	private WebElement articleDateTimeText;

	@FindBy(xpath = "(//section[contains(@class,'pageContent')]//h1)[1]")
	private WebElement articleTitleHeading;

	

	@FindBy(xpath = "(//span[@class='readanchore'])[1]/div/a")
	private List<WebElement> articleReadMoreOnLinks;

	@FindBy(xpath = "(//section[contains(@class,'rel-art')])[1]//p//a")
	private List<WebElement> articleAlsoReadLinks;

	

	///////////////////// getters///////////////

	public List<WebElement> getArticleAlsoReadLinks() {
		return articleAlsoReadLinks;
	}

	public List<WebElement> getArticleReadMoreOnLinks() {
		return articleReadMoreOnLinks;
	}

	public WebElement getArticleTitleHeading() {
		return articleTitleHeading;
	}

	public WebElement getArticleDateTimeText() {
		return articleDateTimeText;
	}

	public WebElement getArticleByLineText() {
		return articleByLineText;
	}

	public WebElement getArticleTextBody() {
		return articleTextBody;
	}

	public WebElement getArticleImageSynopsis() {
		return articleImageSynopsis;
	}

	public WebElement getArticleSynopsis() {
		return articleSynopsis;
	}

	
	
}