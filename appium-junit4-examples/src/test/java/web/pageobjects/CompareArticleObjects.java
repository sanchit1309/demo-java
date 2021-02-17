package web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CompareArticleObjects {

	@FindBy(xpath = "//div[@class = 'ytp-title-text']/a")
	private WebElement youTubeEmbedTitle;

	@FindBy(xpath = "//a[@class = 'ytp-title-channel-logo']")
	private WebElement YoutubeEmbedChannelLogo;

	@FindBy(xpath = "//div[@class='ytp-cued-thumbnail-overlay-image']")
	private WebElement YoutubeEmbedOverlayImage;

	@FindBy(xpath = "(//div[contains(@class,'artText')])[1]//div[@data-type='twitter']")
	private List<WebElement> articleTwitterEmbeds;

	@FindBy(xpath = "(//div[contains(@class,'artText')])[1]//img[contains(@class,'gwt-Image')]")
	private List<WebElement> articleImageEmbeds;

	@FindBy(xpath = "(//div[contains(@class,'artText')])[1]//iframe[@class='youtubeVideo']")
	private List<WebElement> articleYoutubeVideoEmbeds;

	@FindBy(xpath = "(//div[contains(@class,'artText')])[1]//div[@class='fbPost']/iframe")
	private List<WebElement> articleFacebookPostEmbeds;

	@FindBy(xpath = "(//div[contains(@class,'artText')])[1]//div[@data-type='video']")
	private List<WebElement> slikeVideoEmbeds;

	@FindBy(xpath = "//div[@data-type='video']//img")
	private WebElement slikeVideoOverlayImage;

	@FindBy(xpath = "//div[@data-testid='post_message']/p")
	private WebElement facebookPostEmbedMessage;

	@FindBy(xpath = "//div[@class='lfloat _ohe']//div[@class='_6ks']//a")
	private WebElement facebookEmbedLinks;

	@FindBy(xpath = "//img[@class='img']/..")
	private WebElement facebookEmbedSourceLink;

	@FindBy(xpath = "//div[@data-type='image']//img")
	private List<WebElement> imageEmbededSrc;

	@FindBy(xpath = "//iframe[contains(@class,'instagram-media')]")
	private List<WebElement> instagramEmbeds;

	@FindBy(xpath = "//a[@class='Username']//span[@class='UsernameText']")
	private WebElement instagramEmbedUserName;

	@FindBy(xpath = "//a[@class='ViewProfileButton']")
	private WebElement instagramProfileButton;

	@FindBy(xpath = "//div[@class='Caption']")
	private WebElement instagramCaption;

	////////// getters/////////////////////

	public List<WebElement> getInstagramEmbeds() {
		return instagramEmbeds;
	}

	public WebElement getYouTubeEmbedTitle() {
		return youTubeEmbedTitle;
	}

	public WebElement getYoutubeEmbedChannelLogo() {
		return YoutubeEmbedChannelLogo;
	}

	public WebElement getYoutubeEmbedOverlayImage() {
		return YoutubeEmbedOverlayImage;
	}

	public List<WebElement> getArticleTwitterEmbeds() {
		return articleTwitterEmbeds;
	}

	public List<WebElement> getArticleImageEmbeds() {
		return articleImageEmbeds;
	}

	public List<WebElement> getArticleYoutubeVideoEmbeds() {
		return articleYoutubeVideoEmbeds;
	}

	public List<WebElement> getArticleFacebookPostEmbeds() {
		return articleFacebookPostEmbeds;
	}

	public List<WebElement> getSlikeVideoEmbeds() {
		return slikeVideoEmbeds;
	}

	public WebElement getSlikeVideoOverlayImage() {
		return slikeVideoOverlayImage;
	}

	public WebElement getFacebookPostEmbedMessage() {
		return facebookPostEmbedMessage;
	}

	public WebElement getFacebookEmbedLinks() {
		return facebookEmbedLinks;
	}

	public WebElement getFacebookEmbedSourceLink() {
		return facebookEmbedSourceLink;
	}

	public List<WebElement> getImageEmbededSrc() {
		return imageEmbededSrc;
	}

	public WebElement getInstagramProfileButton() {
		return instagramProfileButton;
	}

	public WebElement getInstagramEmbedUserName() {
		return instagramEmbedUserName;
	}

	public WebElement getInstagramCaption() {
		return instagramCaption;
	}
}
