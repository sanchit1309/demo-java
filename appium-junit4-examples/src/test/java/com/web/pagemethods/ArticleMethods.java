package com.web.pagemethods;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pageobjects.ArticleObjects;

public class ArticleMethods {
	private WebDriver driver;
	private ArticleObjects articleObjects;
	private String href;

	public ArticleMethods(WebDriver driver) {
		this.driver = driver;
		articleObjects = PageFactory.initElements(driver, ArticleObjects.class);
	}

	public boolean verifyArticleShowPageFontSize() throws InterruptedException {
		try {
			WebBaseMethods.clickElementUsingJSE(articleObjects.getIncDecFontSize());
			WaitUtil.sleep(2000);
			String fontcheck = articleObjects.getArticleText().getAttribute("style");
			WebBaseMethods.switchToParentWindow();
			if (fontcheck.contains("font-size: 18px;")) {
				return true;
			}

		} catch (Exception ee) {

			ee.printStackTrace();

		}
		return false;
	}

	public String getArticleTitle() {
		return articleObjects.getArticleTitle().getText().trim();
	}

	public boolean navigateToTwitterSharing() {
		boolean flag = false;
		List<WebElement> twitter = articleObjects.getTwitterSharing();
		if (twitter.size() > 0) {
			twitter.get(0).click();
			WebBaseMethods.switchChildIfPresent();
			flag = true;
		}
		return flag;
	}

	public boolean clickGoogleSharingBtn() {
		boolean flag = false;
		if (articleObjects.getMoreSharingBtn().size() > 0) {
			WebBaseMethods.clickElementUsingJSE(articleObjects.getMoreSharingBtn().get(0));

			WebBaseMethods.clickElementUsingJSE(articleObjects.getGoogleSharingBtn());
			flag = true;
		}
		return flag;
	}

	public boolean clickFacebookSharingBtn() {
		boolean flag = false;
		if (articleObjects.getFacebookSharing().size() > 0) {
			WebBaseMethods.getDisplayedItemFromList(articleObjects.getFacebookSharing()).get(0).click();
			flag = true;
		}
		return flag;

	}

	public String clickArticleShowReturnHeadline(WebElement el) {
		String headline = "";
		try {
			WebBaseMethods.clickElementUsingJSE(el);
			WaitUtil.sleep(3000);
			href = el.getAttribute("href");
			headline = WebBaseMethods.getListTextUsingJSE(el);
		} catch (NoSuchElementException e) {

		}
		return headline;
	}

	public String getGoogleSharedLinkTitle() {
		WaitUtil.explicitWaitByElementToBeClickable(driver, 60, articleObjects.getGPlusArticleSharedLink());
		String sharedTitle = articleObjects.getGPlusArticleSharedLink().getText().trim();
		// WebBaseMethods.switchToChildWindow(1);
		WebBaseMethods.switchToParentClosingChilds();
		return sharedTitle;

	}

	public String getFacebookSharedLinkTitle() {
		String fbTitle = "";
		try {
			fbTitle = articleObjects.getfacebookSharedLink().getText().trim();
		} catch (NoSuchElementException e) {

		}
		return fbTitle;
	}

	public String getTwitterSharedLinkTitle() {
		String sharedTitle = "";
		try {
			String stg = articleObjects.gettwitterStatus().getText().trim();
			sharedTitle = stg.split("http")[0].trim();
			WebBaseMethods.switchToParentClosingChilds();
		} catch (Exception e) {
			sharedTitle = "notFound";
		}
		return sharedTitle;
	}

	public String getArticleDate() {
		String[] pubInfo = articleObjects.getPublishingInfo().getText().split("\\|");
		String date = "";
		date = pubInfo.length > 1 ? pubInfo[1].replaceAll("(?i)Updated:", "")
				: pubInfo[0].replaceAll("(?i)Updated:", "");
		return date;

	}

	public List<WebElement> getStreamBandArticleLink() {
		return articleObjects.getStreamBandArticleList();

	}

	public List<String> articleHeading() {
		List<WebElement> streamingArticleHeading = articleObjects.getStreamingArticleHeading();
		List<String> headingList = new LinkedList<>();
		streamingArticleHeading.forEach(heading -> {
			headingList.add(WebBaseMethods.getListTextUsingJSE(heading));
		});

		return headingList;
	}

	public List<String> getAlsoReadStories(int i) {
		List<WebElement> el = null;
		try {
			el = articleObjects.getAlsoRead(i + 1).findElements(By.xpath(".//a"));
		} catch (NoSuchElementException e) {
			// TODO: handle exception
		}
		if (el != null)
			return WebBaseMethods.getListHrefUsingJSE(el);
		else
			return new LinkedList<String>();

	}

	public int commentTabCount() {
		int count = 0;
		try {
			WaitUtil.sleep(2000);
			if (driver.getCurrentUrl().contains("liveblog")) {

				WaitUtil.sleep(2000);
				WebBaseMethods.scrollElementIntoViewUsingJSE(articleObjects.getLiveBlogComment());
				WebBaseMethods.JSHoverOver(articleObjects.getLiveBlogComment());
				WaitUtil.sleep(2000);
				String commentCount = WebBaseMethods.getListTextUsingJSE(articleObjects.getLiveBlogComment())
						.replaceAll("\\(", "").replaceAll("\\)", "");
				count = Integer.parseInt(commentCount);
			} else {
				int counter = 0;
				do {
					WaitUtil.sleep(2000);
					WebBaseMethods.scrollElementIntoViewUsingJSE(articleObjects.getCommentTab());
					WebBaseMethods.JSHoverOver(articleObjects.getCommentTab());
					WaitUtil.sleep(2000);
					count = Integer.parseInt(WebBaseMethods.getListTextUsingJSE(articleObjects.getCommentTab()));
					counter++;
				} while (!(count > 0) && counter < 4);
			}

		} catch (NoSuchElementException | NumberFormatException ee) {

		}
		return count;
	}

	public WebElement getBookmarkIcon() {
		// TODO Auto-generated method stub
		return articleObjects.getBookmarkIcon();
	}

	public WebElement getSaveStoriesTab() {
		// TODO Auto-generated method stub
		return articleObjects.getSaveStoriesTab();
	}

	public String getHref() {
		return href;
	}

	public String getArticleShowBodyContentText() {

		String text = "";

		text = WebBaseMethods.getListTextUsingJSE(articleObjects.getArticleBody());
		if (text == null) {
			text = "The articleshow body content is not found";

		}

		return text;

	}

	public HashMap<String, List<String>> checkLinksForHtmlTag(List<String> urlList) {
		HashMap<String, List<String>> failureLinksMap = new HashMap<>();
		List<String> urls = urlList;
		urls.forEach(url -> {
			driver.get(url);
			WaitUtil.waitForLoad(driver);
			WaitUtil.sleep(2000);
			List<String> htmlTags = new LinkedList<>();

			try {

				String bodyContent = getArticleShowBodyContentText();
				System.out.println(" The body content is " + bodyContent.substring(0, 25));

				if (bodyContent.length() == 0 || bodyContent.contains("The articleshow body content is not found")) {
					htmlTags.add("The articleshow body content is not found on artilceshow");
				}

				Pattern p = Pattern.compile("<[^<^>]+>");
				Matcher m = p.matcher(bodyContent);
				while (m.find()) {
					htmlTags.add(m.group());
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			if (htmlTags.size() > 0) {
				failureLinksMap.put(url, htmlTags);
			}
		});
		return failureLinksMap;
	}

	public String getArticleTitleHeading() {
		String title = "";
		try {

			title = articleObjects.getArticleTitleHeading().getText();
		} catch (Exception ee) {
			title = "Title of the article is not found";
			ee.printStackTrace();
		}
		return title;

	}

	public String getArticleSynopsis() {
		String text = "";
		try {

			text = articleObjects.getArticleSynopsis().getText();
		} catch (Exception ee) {
			text = "Synopsis of the article is not found";
			ee.printStackTrace();
		}
		return text;

	}

	public String getArticleByLineText() {
		String text = "";
		try {

			text = WebBaseMethods.getTextUsingJSE(articleObjects.getArticleByLineText());
		} catch (Exception ee) {
			text = "By line text of the article is not found";
			ee.printStackTrace();
		}
		return text;

	}

	public String getArticleDateTime() {
		String text = "";
		try {

			text = articleObjects.getArticleDateTimeText().getText();
		} catch (Exception ee) {
			text = "Date time of the article is not found";
			ee.printStackTrace();
		}
		return text;

	}

	public String getArticleBodyText() {
		String text = "";
		try {

			text = articleObjects.getArticleTextBody().getText();
		} catch (Exception ee) {
			text = "Article body text of the article is not found";
			ee.printStackTrace();
		}
		return text;

	}

	public String getArticleImageSynopsis() {
		String text = "";
		try {

			text = articleObjects.getArticleImageSynopsis().getText();
		} catch (Exception ee) {
			text = "Figure caption of the article is not found";
			ee.printStackTrace();
		}
		return text;

	}

	public List<String> getArticleReadMoreLinks() {
		List<String> textList = new LinkedList<String>();
		try {

			textList = VerificationUtil.getLinkTextList(articleObjects.getArticleReadMoreOnLinks());
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return textList;

	}

	public List<String> getArticleAlsoReadLinks() {
		List<String> textList = new LinkedList<String>();
		try {

			textList = VerificationUtil.getLinkTextList(articleObjects.getArticleAlsoReadLinks());
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return textList;

	}

	public Map<String, String> getArticleAllElements() {
		Map<String, String> articleDetails = new HashMap<>();
		articleDetails.put("articleTitle", getArticleTitleHeading());
		articleDetails.put("articleSynopsis", getArticleSynopsis());
		articleDetails.put("articleFigureSynopsis", getArticleImageSynopsis());
		// articleDetails.put("articleByLineText", getArticleByLineText());
		articleDetails.put("articleTimeLineText", getArticleDateTime());
		articleDetails.put("articleBodyText", getArticleBodyText());
		return articleDetails;
	}

}