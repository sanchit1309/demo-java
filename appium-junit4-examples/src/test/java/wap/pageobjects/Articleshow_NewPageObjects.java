package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

public class Articleshow_NewPageObjects {

	@FindBy(xpath = "//span[@data-label='Facebook Share']")
	private WebElement facebookSharingIcon;

	@FindBy(xpath = "//span[@data-label='Twitter Share']")
	private WebElement twitterSharingIcon;

	@FindBy(xpath = "//div[text()='Font Size']//following-sibling::div//li[text()='Small']")
	private WebElement fontSizeSmall;

	@FindBy(xpath = "//div[text()='Font Size']//following-sibling::div//li[text()='Normal']")
	private WebElement fontSizeNormal;

	@FindBy(xpath = "//div[text()='Font Size']//following-sibling::div//li[text()='Large']")
	private WebElement fontSizeLarge;

	@FindBy(xpath = "//article//h1")
	private WebElement articleHeading;

	@FindBy(xpath = "//*[@id=\"u_0_12\"]")
	private WebElement fbPostButton;

	@FindBy(xpath = "//div[@id='platformDialogContent']//div[@class='sharerAttachmentTitle']/span")
	private WebElement fbSharedHeading;

	@FindBy(xpath = "//h3[text()='Summary']/following-sibling::div")
	private WebElement articleSummary;

	@FindBy(xpath = "//figcaption/p")
	private WebElement articleFigCaption;

	@FindBy(xpath = "//article//time")
	private WebElement articleDateTimeText;

	@FindBy(xpath = "//article//div[contains(@class,'1d66b419be3937b7492 ')]")
	private WebElement articleTextBody;

	@FindBy(xpath = "//div//a[contains(@href,'/comment/')]")
	private WebElement commentIcon;

	@FindBy(xpath = "//textarea[@name = 'cmtMsg']")
	private WebElement commentMessageBox;

	///////// getters//////////////

	public WebElement getCommentMessageBox() {
		return commentMessageBox;
	}

	public WebElement getCommentIcon() {
		return commentIcon;
	}

	public WebElement getArticleFigCaption() {
		return articleFigCaption;
	}

	public WebElement getArticleSummary() {
		return articleSummary;
	}

	public WebElement getFbSharedHeading() {
		return fbSharedHeading;
	}

	public WebElement getFbPostButton() {
		return fbPostButton;
	}

	public WebElement getFacebookSharingIcon() {
		return facebookSharingIcon;
	}

	public WebElement getTwitterSharingIcon() {
		return twitterSharingIcon;
	}

	public WebElement getFontSizeSmall() {
		return fontSizeSmall;
	}

	public WebElement getFontSizeNormal() {
		return fontSizeNormal;
	}

	public WebElement getFontSizeLarge() {
		return fontSizeLarge;
	}

	public WebElement getArticleHeading() {
		return articleHeading;
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

	public WebElement getArticleDateTimeText() {
		return articleDateTimeText;
	}

	public WebElement getArticleTextBody() {
		return articleTextBody;
	}

}
