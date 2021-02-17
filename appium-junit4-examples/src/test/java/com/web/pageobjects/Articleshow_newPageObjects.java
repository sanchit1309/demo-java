package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Articleshow_newPageObjects {

	@FindBy(xpath = "(//h1[@class='artTitle font_faus'])[1]")
	private WebElement articleTitleHeading;

	@FindBy(xpath = "(//h2[@class='summary'])[1]")
	private WebElement articleSynopsis;

	@FindBy(xpath = "(//div[contains(@class,'artByline')])[1]//span")
	private WebElement articleByLineText;

	@FindBy(xpath = "(//div[contains(@class,'artByline')])[1]//time")
	private WebElement articleTimeLineText;

	@FindBy(xpath = "(//div[@class='socialShare']//div/a[contains(@class,'fb')])[1]")
	private WebElement fbSharingIcon;

	@FindBy(xpath = "(//div[@class='socialShare']//div/a[contains(@class,'in')])[1]")
	private WebElement linkedInSharingIcon;

	@FindBy(xpath = "(//div[@class='socialShare']//div/a[contains(@class,'twt')])[1]")
	private WebElement twitterSharingIcon;

	@FindBy(xpath = "(//div[@class='fontSize']//div//span[text()='Small'])[1]")
	private WebElement fontSizeSmallIcon;

	@FindBy(xpath = "(//div[@class='fontSize']//div//span[text()='Medium'])[1]")
	private WebElement fontSizeMediumIcon;

	@FindBy(xpath = "(//div[@class='fontSize']//div//span[text()='Large'])[1]")
	private WebElement fontSizeLargeIcon;

	@FindBy(xpath = "(//div[@class='bookMarks']//span[@title='Bookmark this article'])[1]")
	private WebElement bookmarkThisArticleIcon;

	@FindBy(xpath = "(//div[contains(@class,'artText')])[1]")
	private WebElement articleTextBody;

	@FindBy(xpath = "(//h4[(@class='uc') and (text()='Read More News on')] )[1]/following-sibling::div/a")
	private List<WebElement> readMoreLinks;

	@FindBy(xpath = "(//h3[text()='Also Read '])[1]/following-sibling::ul/li/a")
	private List<WebElement> alsoReadLinks;

	@FindBy(xpath = "(//figcaption[@class='figCap'])[1]")
	private WebElement figureCaption;

	@FindBy(xpath = "(//div[@class='comments open_cmnt'])[1]/span[text()='Comment']")
	private WebElement commentIcon;

	@FindBy(xpath = "//textarea[@name='cmtMsg']")
	private WebElement commentMessageBox;

	@FindBy(xpath = "//input[@value='Post Comment']")
	private WebElement postCommentBtn;

	@FindBy(xpath = "//h6[@class='thankText']")
	private WebElement thankYouTextForCommentPost;

	@FindBy(xpath = "(//a[@id='savedStories'])[1]")
	private WebElement saveStoriesTab;
	
	@FindBy(xpath = "(//div[@data-contents='true']//span[@data-text='true'])[1]")
	private WebElement twitterSharedTitle;
	
	@FindBy(xpath = "(//div[@class='bookMarks']//span[@class='asSprite bookmark-icon s_act saved'])[1]")
	private WebElement unsaveThisArticleIcon;

	////////////////////////////// getters////////////////////////////////////////

	public WebElement getCommentMessageBox() {
		return commentMessageBox;
	}

	public WebElement getPostCommentBtn() {
		return postCommentBtn;
	}

	public WebElement getThankYouTextForCommentPost() {
		return thankYouTextForCommentPost;
	}

	public WebElement getCommentIcon() {
		return commentIcon;
	}

	public WebElement getArticleTitleHeading() {
		return articleTitleHeading;
	}

	public WebElement getArticleSynopsis() {
		return articleSynopsis;
	}

	public WebElement getArticleByLineText() {
		return articleByLineText;
	}

	public WebElement getArticleTimeLineText() {
		return articleTimeLineText;
	}

	public WebElement getFbSharingIcon() {
		return fbSharingIcon;
	}

	public WebElement getLinkedInSharingIcon() {
		return linkedInSharingIcon;
	}

	public WebElement getTwitterSharingIcon() {
		return twitterSharingIcon;
	}

	public WebElement getFontSizeSmallIcon() {
		return fontSizeSmallIcon;
	}

	public WebElement getFontSizeMediumIcon() {
		return fontSizeMediumIcon;
	}

	public WebElement getFontSizeLargeIcon() {
		return fontSizeLargeIcon;
	}

	public WebElement getBookmarkThisArticleIcon() {
		return bookmarkThisArticleIcon;
	}

	public WebElement getArticleTextBody() {
		return articleTextBody;
	}

	public List<WebElement> getReadMoreLinks() {
		return readMoreLinks;
	}

	public List<WebElement> getAlsoReadLinks() {
		return alsoReadLinks;
	}

	public WebElement getFigureCaption() {
		return figureCaption;
	}

	public WebElement getSaveStoriesTab() {
		return saveStoriesTab;
	}
	
	public WebElement getTwitterSharedTitle() {
		return twitterSharedTitle;
	}
	
	public WebElement getUnsaveThisArticleIcon() {
		return unsaveThisArticleIcon;
	}
}
