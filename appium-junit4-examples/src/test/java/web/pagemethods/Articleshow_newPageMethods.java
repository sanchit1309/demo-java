package web.pagemethods;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pageobjects.Articleshow_newPageObjects;

public class Articleshow_newPageMethods {

	private WebDriver driver;
	private Articleshow_newPageObjects articleshow_newPageObjects;
	private LoginPageMethods loginPageMethods;

	public Articleshow_newPageMethods(WebDriver driver) {
		this.driver = driver;
		loginPageMethods = new LoginPageMethods(driver);
		articleshow_newPageObjects = PageFactory.initElements(driver, Articleshow_newPageObjects.class);
	}

	public String getArticleTitle() {
		String title = "";
		try {
			WaitUtil.waitForLoad(driver);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 4, articleshow_newPageObjects.getArticleTitleHeading());
			title = articleshow_newPageObjects.getArticleTitleHeading().getText();
		} catch (Exception ee) {
			title = "Title of the article is not found";
			ee.printStackTrace();
		}
		return title;

	}

	public String getArticleSynopsis() {
		String text = "";
		try {

			text = articleshow_newPageObjects.getArticleSynopsis().getText();
		} catch (Exception ee) {
			text = "Synopsis of the article is not found";
			ee.printStackTrace();
		}
		return text;

	}

	public String getArticleByLineText() {
		String text = "";
		try {

			text = articleshow_newPageObjects.getArticleByLineText().getText();
		} catch (Exception ee) {
			text = "By line text of the article is not found";
			ee.printStackTrace();
		}
		return text;

	}

	public String getArticleDateTime() {
		String text = "";
		try {

			text = articleshow_newPageObjects.getArticleTimeLineText().getText();
		} catch (Exception ee) {
			text = "Date time of the article is not found";
			ee.printStackTrace();
		}
		return text;

	}

	public String getArticleBodyText() {
		String text = "";
		try {

			text = articleshow_newPageObjects.getArticleTextBody().getText();
		} catch (Exception ee) {
			text = "Article body text of the article is not found";
			ee.printStackTrace();
		}
		return text;

	}

	public String getArticleImageSynopsis() {
		String text = "";
		try {

			text = articleshow_newPageObjects.getFigureCaption().getText();
		} catch (Exception ee) {
			text = "Figure caption of the article is not found";
			ee.printStackTrace();
		}
		return text;

	}

	public List<String> getArticleReadMoreLinks() {
		List<String> textList = new LinkedList<String>();
		try {

			textList = VerificationUtil.getLinkTextList(articleshow_newPageObjects.getReadMoreLinks());
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return textList;

	}

	public List<String> getArticleReadMoreLinksHref() {
		List<String> textList = new LinkedList<String>();
		try {

			textList = VerificationUtil.getLinkHrefList(articleshow_newPageObjects.getReadMoreLinks());
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return textList;

	}

	public List<String> getArticleAlsoReadLinks() {
		List<String> textList = new LinkedList<String>();
		try {

			textList = VerificationUtil.getLinkTextList(articleshow_newPageObjects.getAlsoReadLinks());
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return textList;

	}

	public List<String> getArticleAlsoReadLinksHref() {
		List<String> textList = new LinkedList<String>();
		try {

			textList = VerificationUtil.getLinkHrefList(articleshow_newPageObjects.getAlsoReadLinks());
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return textList;

	}

	public Map<String, String> getArticleAllElements() {
		Map<String, String> articleDetails = new HashMap<>();

		articleDetails.put("articleTitle", getArticleTitle());
		articleDetails.put("articleSynopsis", getArticleSynopsis());
		articleDetails.put("articleFigureSynopsis", getArticleImageSynopsis());
		// articleDetails.put("articleByLineText", getArticleByLineText());
		articleDetails.put("articleTimeLineText", getArticleDateTime());
		articleDetails.put("articleBodyText", getArticleBodyText());

		return articleDetails;
	}

	public boolean navigateToTwitterSharing() {
		boolean flag = false;
		try {
			WebElement twitter = articleshow_newPageObjects.getTwitterSharingIcon();

			WebBaseMethods.clickElementUsingJSE(twitter);
			WebBaseMethods.switchChildIfPresent();
			flag = true;
		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return flag;
	}

	public boolean navigateToFacebookSharing() {
		boolean flag = false;
		try {
			WebElement fb = articleshow_newPageObjects.getFbSharingIcon();

			flag =	WebBaseMethods.clickElementUsingJSE(fb);
			
		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return flag;
	}

	public boolean navigateToTheNewTemplate() {
		boolean flag = false;
		try {
			String url = driver.getCurrentUrl().replace("articleshow", "articleshow_new");
			WebBaseMethods.navigateToUrl(driver, url);
			WaitUtil.waitForLoad(driver);
			flag = true;
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return flag;

	}

	public boolean checkSmallTextSize() {
		boolean flag = false;
		try {
			String fontSize = "";
			WebBaseMethods.clickElementUsingJSE(articleshow_newPageObjects.getFontSizeSmallIcon());
			WaitUtil.sleep(3000);
			fontSize = articleshow_newPageObjects.getArticleTextBody().getAttribute("class");
			System.out.println("this is the font " + fontSize);
			if (fontSize.contains("small")) {
				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return flag;

	}

	public boolean checkLargeTextSize() {
		boolean flag = false;
		try {
			String fontSize = "";
			WebBaseMethods.clickElementUsingJSE(articleshow_newPageObjects.getFontSizeLargeIcon());
			WaitUtil.sleep(3000);
			fontSize = articleshow_newPageObjects.getArticleTextBody().getAttribute("class");
			System.out.println("this is the font " + fontSize);
			if (fontSize.contains("large")) {
				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return flag;

	}

	public boolean checkMediumTextSize() {
		boolean flag = false;
		try {
			String fontSize = "";
			WebBaseMethods.clickElementUsingJSE(articleshow_newPageObjects.getFontSizeMediumIcon());
			WaitUtil.sleep(3000);
			fontSize = articleshow_newPageObjects.getArticleTextBody().getAttribute("class");
			System.out.println("this is the font " + fontSize);
			if (fontSize.equalsIgnoreCase("artText medium")) {
				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return flag;

	}

	public boolean clickCommentIcon() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(articleshow_newPageObjects.getCommentIcon());
			WaitUtil.sleep(3000);
			
			// articleshow_newPageObjects.getCommentIcon().click();

		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;
	}

	public boolean verifyCommentIsPosted() {
		boolean flag = false;
		try {
			articleshow_newPageObjects.getCommentMessageBox().sendKeys("Nice Article");

			WaitUtil.sleep(2000);
			WebBaseMethods.clickElementUsingJSE(articleshow_newPageObjects.getPostCommentBtn());
			WaitUtil.sleep(2000);
			String text = articleshow_newPageObjects.getThankYouTextForCommentPost().getText();
			System.out.println(text);
			if (text.equalsIgnoreCase("Thank you!")) {

				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;
	}

	public boolean clickBookmarkIcon() {
		boolean flag = false;
		try {
			flag = 	WebBaseMethods.clickElementUsingJSE(articleshow_newPageObjects.getBookmarkThisArticleIcon());
			WaitUtil.sleep(3000);
			
			

		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;
	}

	public boolean clickSavedStories() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(articleshow_newPageObjects.getSaveStoriesTab());
			WaitUtil.sleep(3000);
			
			

		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;
	}

	public List<String> verifyPresenceOfAllElements() {
		List<String> failedElements = new LinkedList<>();
		try {
			Map<String, String> articleshowElements = getArticleAllElements();
			System.out.println("The size of map is" + articleshowElements.size());
			articleshowElements.forEach((ele, value) -> {
				System.out.println(ele + articleshowElements.get(ele));
				if (!ele.contains("Synopsis")) {
					if (value.length() == 0 || value.contains("of the article is not found")) {
						failedElements.add(ele + "is not shown on the articleshow");
					}
				}
			});

		} catch (Exception ee) {
		}

		return failedElements;
	}

	public String getTwitterSharedLinkTitle() {
		String sharedTitle = "";
		try {
			sharedTitle = articleshow_newPageObjects.getTwitterSharedTitle().getText().trim();

			WebBaseMethods.switchToParentClosingChilds();
		} catch (Exception e) {
			sharedTitle = "notFound";
		}
		return sharedTitle;
	}
	
	public boolean clickBookmarkIconToUnsave() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(articleshow_newPageObjects.getUnsaveThisArticleIcon());
			WaitUtil.sleep(3000);
			

		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;
	}
}
