package com.web.pagemethods;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import web.pageobjects.ArticleObjects;
import web.pageobjects.Articleshow_newPageObjects;
import web.pageobjects.CompareArticleObjects;

public class CompareArticleMethods {

	private WebDriver driver;
	private ArticleObjects articleShowPageObjects_Live;
	private Articleshow_newPageObjects articleShowPageObjects_DEV;
	private CompareArticleObjects compareArticleObjects;
	int imageCount = 0;

	public CompareArticleMethods(WebDriver driver) {
		this.driver = driver;
		articleShowPageObjects_Live = PageFactory.initElements(driver, ArticleObjects.class);
		articleShowPageObjects_DEV = PageFactory.initElements(driver, Articleshow_newPageObjects.class);
		compareArticleObjects = PageFactory.initElements(driver, CompareArticleObjects.class);
	}

	public boolean isYoutubeEmbedIsFoundOnLive() {
		boolean flag = false;
		try {
			flag = compareArticleObjects.getArticleYoutubeVideoEmbeds().size() > 0;
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;

	}

	public boolean isSlikeVideoEmbedIsFoundOnLive() {
		boolean flag = false;
		try {
			flag = compareArticleObjects.getSlikeVideoEmbeds().size() > 0;
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;

	}

	public boolean isFaceBookEmbedFoundOnLive() {
		boolean flag = false;
		try {
			flag = compareArticleObjects.getArticleFacebookPostEmbeds().size() > 0;
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;

	}

	public boolean isTwitterEmbedFoundOnLive() {
		boolean flag = false;
		try {
			flag = compareArticleObjects.getArticleTwitterEmbeds().size() > 0;
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;

	}

	public boolean isImagesrEmbedFoundOnLive() {
		boolean flag = false;
		try {
			flag = compareArticleObjects.getArticleImageEmbeds().size() > 0;
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;

	}

	public boolean isInstagramEmbedFoundOnLive() {
		boolean flag = false;
		try {
			flag = compareArticleObjects.getInstagramEmbeds().size() > 0;
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;

	}

	public Map<String, String> youtubeEmbedDetails() {
		WebBaseMethods.switchToFrame(compareArticleObjects.getArticleYoutubeVideoEmbeds().get(0));
		Map<String, String> embedDetails = new HashMap<String, String>();
		try {
			embedDetails.put("EmbedSource", compareArticleObjects.getYouTubeEmbedTitle().getAttribute("href"));
			embedDetails.put("EmbedTitle", compareArticleObjects.getYouTubeEmbedTitle().getText());
			embedDetails.put("EmbedLogoHref", compareArticleObjects.getYoutubeEmbedChannelLogo().getAttribute("href"));
			embedDetails.put("EmbedOverlayImage",
					compareArticleObjects.getYoutubeEmbedOverlayImage().getAttribute("style"));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		WebBaseMethods.switchToDefaultContext();
		return embedDetails;
	}

	public Map<String, String> slikeVideoEmbedDetails() {
		Map<String, String> embedDetails = new HashMap<String, String>();
		try {
			embedDetails.put("video data-id",
					compareArticleObjects.getSlikeVideoEmbeds().get(0).getAttribute("data-id"));
			embedDetails.put("video title",
					compareArticleObjects.getSlikeVideoEmbeds().get(0).getAttribute("data-title"));
			embedDetails.put("video contentId",
					compareArticleObjects.getSlikeVideoEmbeds().get(0).getAttribute("data-contentid"));
			embedDetails.put("video OverlayImage",
					compareArticleObjects.getSlikeVideoOverlayImage().getAttribute("src"));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return embedDetails;
	}

	public Map<String, String> facebookEmbedDetails() {
		WebBaseMethods.switchToFrame(compareArticleObjects.getArticleFacebookPostEmbeds().get(0));

		Map<String, String> embedDetails = new HashMap<String, String>();
		try {
			embedDetails.put("Facebook description", compareArticleObjects.getFacebookPostEmbedMessage().getText());
			embedDetails.put("Facebook source",
					compareArticleObjects.getFacebookEmbedSourceLink().getAttribute("href"));
			embedDetails.put("Facebook EmbedLink", compareArticleObjects.getFacebookEmbedLinks().getAttribute("href"));
			embedDetails.put("Facebook EmbedLinkTitle",
					compareArticleObjects.getFacebookEmbedLinks().getAttribute("aria-label"));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		WebBaseMethods.switchToDefaultContext();
		return embedDetails;
	}

	public Map<String, String> twitterEmbedDetails() {
		Map<String, String> embedDetails = new HashMap<String, String>();
		try {
			embedDetails.put("Twitter data-handle",
					compareArticleObjects.getArticleTwitterEmbeds().get(0).getAttribute("data-handle"));
			embedDetails.put("Twitter data-image",
					compareArticleObjects.getArticleTwitterEmbeds().get(0).getAttribute("data-image"));
			embedDetails.put("Twitter data-status",
					compareArticleObjects.getArticleTwitterEmbeds().get(0).getAttribute("data-status"));
			embedDetails.put("Twitter data-createdat",
					compareArticleObjects.getArticleTwitterEmbeds().get(0).getAttribute("data-createdat"));
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return embedDetails;
	}

	public Map<String, String> imagesEmbeddedDetails() {

		Map<String, String> embedDetails = new HashMap<String, String>();
		imageCount = 0;
		try {
			System.out.println(compareArticleObjects.getArticleImageEmbeds().size());
			compareArticleObjects.getArticleImageEmbeds().forEach(embed -> {

				embedDetails.put("image" + imageCount, embed.getAttribute("src"));
				imageCount++;

			});

		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return embedDetails;

	}

	public Map<String, String> instagramEmbedDetails() {
		WebBaseMethods.switchToFrame(compareArticleObjects.getInstagramEmbeds().get(0));

		Map<String, String> embedDetails = new HashMap<String, String>();
		try {
			embedDetails.put("Instagram userName", compareArticleObjects.getInstagramEmbedUserName().getText());
			embedDetails.put("Instagram profileName",
					compareArticleObjects.getInstagramProfileButton().getAttribute("href"));
			embedDetails.put("Instagram caption", compareArticleObjects.getInstagramCaption().getText());

		} catch (Exception ee) {
			ee.printStackTrace();
		}
		WebBaseMethods.switchToDefaultContext();
		return embedDetails;
	}

	public String getDevLink(String url) {
		String link = "";
		try {
			link = url.replace("economictimes.indiatimes.com", "etdev8243.indiatimes.com").replace("articleshow",
					"articleshow_new");

		} catch (Exception ee) {

		}
		return link;
	}

}
