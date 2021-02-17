package com.pagemethods;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;

import api.tests.AuthAPITest;
import app.pageobjects.PrimeSectionObjects;
import common.launchsetup.BaseTest;
import common.urlRepo.AppFeeds;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static common.launchsetup.BaseTest.iAppCommonMethods;
import static io.restassured.RestAssured.given;

public class PrimeSectionMethods {
	AppiumDriver<?> appiumDriver;
	PrimeSectionObjects primeSectionObjects;
	AuthAPITest authAPIObj;
	private List<String> resultList = new ArrayList<String>();
	private AppListingPageMethods appListingPageMethods;
	private int i;
	String ticketID = "";
	String token = "";

	public enum ArticleType {
		IS_PRIME("153"), PRIME_PLUS("318");

		public String value;

		private ArticleType(String value) {
			this.value = value;
		}
	}

	public PrimeSectionMethods(AppiumDriver<?> driver) {
		appiumDriver = driver;
		primeSectionObjects = new PrimeSectionObjects();
		appListingPageMethods = new AppListingPageMethods(appiumDriver);
		authAPIObj = new AuthAPITest();
		PageFactory.initElements(new AppiumFieldDecorator(driver), primeSectionObjects);
	}

	public PrimeSectionObjects getPrimeSectionObjects() {
		return primeSectionObjects;
	}

	public boolean goToHomeTab() {
		boolean flag = false;
		try {
			iAppCommonMethods.scrollDown();
			primeSectionObjects.getPrimeHomeTab().click();
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean goToHomeTabFromTop() {
		boolean flag = false;
		try {
			iAppCommonMethods.swipeDown();
			iAppCommonMethods.clickElement(primeSectionObjects.getEtLogo());
			iAppCommonMethods.clickElement(primeSectionObjects.getPrimeHomepageIcon());
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean clickFirstPrimeAuthorStory() {
		boolean flag = false;
		try {
			primeSectionObjects.getPrimeAuthorStories().get(0).click();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public boolean clickPrimeIcon() {
		boolean flag = false;
		try {
			primeSectionObjects.getPrimeHomepageIcon().click();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public boolean clickAuthorsTab() {
		boolean flag = false;
		try {
			primeSectionObjects.getPrimeAuthorsTab().click();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		WaitUtil.sleep(2000);
		return flag;
	}

	public boolean clickAuthorsStoriesTab() {
		boolean flag = false;
		try {
			primeSectionObjects.getPrimeAuthorsStoriesTab().click();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public boolean clickAuthorsContributionsTab() {
		boolean flag = false;
		try {
			primeSectionObjects.getPrimeAuthorsContributionsTab().click();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public boolean clickBrowseTab() {
		boolean flag = false;
		try {
			primeSectionObjects.getPrimeBrowseTab().click();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public String primeArticleHeadline() {
		try {
			return primeSectionObjects.getPrimeArticleshowHeading().getText();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public String primeArticleDate() {
		try {
			return primeSectionObjects.getPrimeArticleshowDate().getText();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public String primeArticleshowSummary() {
		try {
			return primeSectionObjects.getPrimeArticleshowSummary().getText();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public String primeArticleshowAuthor() {
		try {
			return primeSectionObjects.getPrimeArticleshowAuthor().getText();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public String primeArticleshowPrimaryTag() {
		try {
			return primeSectionObjects.getPrimeArticleshowPrimaryTag().getText();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public MobileElement getPrimeHomepageIcon() {
		return primeSectionObjects.getPrimeHomepageIcon();
	}

	public boolean clickPrimeHomepageFirstStory() {
		boolean flag = false;
		try {
			WaitUtil.sleep(1000);
			if (primeSectionObjects.getAdCloseButton().size() > 0) {
				primeSectionObjects.getAdCloseButton().get(0).click();
			}
			primeSectionObjects.getPrimeHomepageStories().get(0).click();
			flag = true;
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;
	}

	public List<MobileElement> getPrimeHomepageStories() {
		return primeSectionObjects.getPrimeHomepageStories();
	}

	public MobileElement getViewProfileTab(String authorName) {

		return primeSectionObjects.getViewProfileTab(appiumDriver, authorName);
	}

	public MobileElement getPrimeAuthorsStoriesTab() {
		return primeSectionObjects.getPrimeAuthorsStoriesTab();
	}

	public boolean clickPrimeAuthorsContributionsTab() {
		boolean flag = false;
		try {
			primeSectionObjects.getPrimeAuthorsContributionsTab().click();
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean clickAuthorsNameTab(String authorName) {
		boolean flag = false;
		try {
			primeSectionObjects.getAuthorsNameTab(appiumDriver, authorName);
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public MobileElement getAuthorsNameSection(String authorName) {

		return primeSectionObjects.getAuthorsNameSection(appiumDriver, authorName);
	}

	public MobileElement getBrowseSection(String sectionName) {

		return primeSectionObjects.getSectionNameTab(appiumDriver, sectionName);
	}

	public boolean isPrimeContentComingSoonShown() {
		return iAppCommonMethods.isElementDisplayed(primeSectionObjects.getPrimeContentComingSoonRed());
	}

	public boolean primeSignInLinkClick(String linkIdentifier) {
		boolean flag = false;
		try {
			BaseTest.globalFlag2 = false;
			iAppCommonMethods.scrollUpToElement(linkIdentifier);
			WaitUtil.sleep(1000);
			flag = iAppCommonMethods.tapElementRightSide(appiumDriver,
					iAppCommonMethods.getElementByText(linkIdentifier));
			BaseTest.globalFlag2 = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public MobileElement getPrimeBrowseTab() {
		return primeSectionObjects.getPrimeBrowseTab();
	}

	public List<MobileElement> getPrimeSectionStories() {
		return primeSectionObjects.getPrimeSectionStories();
	}

	public int getPrimeSectionStoriesNumber() {
		int size = 0;
		try {
			size = primeSectionObjects.getPrimeSectionStories().size();
			return size;
		} catch (Exception ee) {

			return size;

		}
	}

	public boolean getAuthorCommentPostedOnHeading() {
		boolean flag = false;
		try {
			flag = primeSectionObjects.getAuthorCommentPostedOnHeading().getText().length() > 1;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean clickFirstAuthorViewFullProfileLink() {
		boolean flag = false;
		try {
			WaitUtil.sleep(1000);
			primeSectionObjects.getAuthorViewFullProfile().click();
			flag = true;
		} catch (NoSuchElementException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		if (!flag) {
			flag = iAppCommonMethods.scrollUpToElement("View Full Profile");
			if (flag) {
				try {
					primeSectionObjects.getAuthorViewFullProfile().click();
					WaitUtil.sleep(2000);
					flag = true;
				} catch (NoSuchElementException | IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	public MobileElement getPrimeHomeTab() {
		return primeSectionObjects.getPrimeHomeTab();
	}

	public boolean clickPrimeHomeTab() {

		try {
			primeSectionObjects.getPrimeHomeTab().click();
			return true;
		} catch (NoSuchElementException ee) {
			return false;

		}

	}

	public boolean scrollToSection(String section) {
		boolean flag = false;
		String text = section;
		System.out.println("**Section Name**" + text);
		try {
			if (BaseTest.platform.contains("ios")) {
				text = "name CONTAINS '" + section + "'";
			}
			BaseTest.globalFlag2 = null;
			iAppCommonMethods.scrollUpToElement(text);
			BaseTest.globalFlag2 = true;
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		int counter = 20;
		if (!flag) {
			while (counter > 0) {
				iAppCommonMethods.swipeByScreenPercentage(0.30, 0.95);
				counter--;
			}
		}
		return flag;
	}

	public boolean scrollToSectionFromTop(String sectionName) {
		boolean flag = false;
		System.out.println("**Section Name**" + sectionName);
		flag = iAppCommonMethods.scrollUpToElement(sectionName);
		System.out.println("Flag " + flag + " while scrolling section:" + sectionName);
		int counter = 20;
		if (!flag) {
			while (counter > 0) {
				System.out.println("Scrolling down");
				iAppCommonMethods.swipeByScreenPercentage(0.40, 0.95);
				counter--;
			}
			System.out.println("Scrolling to element again");
			flag = iAppCommonMethods.scrollUpToElement(sectionName);
			System.out.println("After checking again the flag is:" + flag);
		}
		if (!flag) {
			int i = 20;
			while (i > 0) {
				iAppCommonMethods.swipeByScreenPercentage(0.40, 0.95);
				i--;
			}
		} else {
			iAppCommonMethods.scrollElementTopToHeader(iAppCommonMethods.getElementByText(sectionName),
					primeSectionObjects.getHomePageTopHeder(), 1500);
		}
		return flag;
	}

	public String authorProfileLink(String authorName) {
		if (BaseTest.platform == "iosApp") {
			return authorName + ", primeAuthorContributor_authorName_label";
		} else {
			return authorName;
		}
	}

	public String browseSectionList(String sections) {
		if (BaseTest.platform.contains("ios")) {

			return sections;
		} else {

			return sections.toUpperCase();

		}

	}

	public String getPrimeArticleshowDateText() {
		String date = "";
		try {
			date = primeSectionObjects.getPrimeArticleshowDate().getText() + "00:00";
			return date;
		} catch (NoSuchElementException ee) {

			return date;

		}

	}

	public boolean clickBrowseSectionLink(String section) {

		try {
			iAppCommonMethods.getElementByText(section).click();
			WaitUtil.sleep(5000);
			iAppCommonMethods.swipeDown();
			return true;
		} catch (Exception ee) {

			return false;

		}

	}

	/*
	 * public boolean clickSearchIcon() { if (clickedIcon) return true; try {
	 * primeSectionObjects.getPrimeSeachIcon().click(); clickedIcon = true; } catch
	 * (NoSuchElementException e) {
	 * 
	 * } return clickedIcon; }
	 */

	public boolean enterValueInSearchBox(String value) {
		boolean flag = false;
		try {
			iAppCommonMethods.scrollDown();
			primeSectionObjects.getPrimeSearchFeild().clear();
			primeSectionObjects.getPrimeSearchFeild().sendKeys(value);
			// appiumDriver.getKeyboard().sendKeys(value);
			flag = true;
			appiumDriver.hideKeyboard();
		} catch (WebDriverException e) {
			// e.printStackTrace();
			e.printStackTrace();
		}
		return flag;
	}

	public List<String> getSearchResults() {

		while (i < 2) {
			primeSectionObjects.getPrimeSearchResult().forEach(result -> {
				String resultVal = result.getText();
				if (!resultList.contains(resultVal))
					resultList.add(resultVal);
			});

			iAppCommonMethods.scrollUp();
			i++;
			getSearchResults();
		}
		iAppCommonMethods.scrollDown();
		return resultList;
	}

	public boolean verifyPrimeSubscribeWidget() {
		boolean flag = false;
		int tryTimes = 0;
		while (tryTimes < 5) {
			try {
				iAppCommonMethods.scrollUp();
				if (primeSectionObjects.getprimeSubscribeWidget().isDisplayed())
					flag = true;
				break;
			} catch (NoSuchElementException e) {
				flag = false;
			} finally {
				tryTimes++;
			}
		}
		return flag;
	}

	public boolean scrollToCategory(String sectionName) {
		AlertsPromptsMethods alertsPromptsMethods = new AlertsPromptsMethods(appiumDriver);
		boolean flag = false;
		while (!iAppCommonMethods.isElementDisplayed(primeSectionObjects.getPrimeHomeEndsection())) {
			if (iAppCommonMethods.isElementDisplayed(iAppCommonMethods.getElementByText(sectionName)))
				return true;
			iAppCommonMethods.swipeByScreenPercentage(0.9, 0.3);
			alertsPromptsMethods.clickRatingCloseIcon();
		}
		if (iAppCommonMethods.isElementDisplayed(primeSectionObjects.getPrimeHomeEndsection())
				&& sectionName.equals(iAppCommonMethods.getElementText(primeSectionObjects.getPrimeHomeEndsection())))
			flag = true;

		return flag;
	}

	public Boolean verifyViewSectionLink(String sectionname, boolean isClickNeeded) {
		Boolean flag = false;
		try {
			flag = iAppCommonMethods.isElementDisplayed(primeSectionObjects.getViewSectionLink());
			if (flag) {
				String section = primeSectionObjects.getViewSectionLink().getText();
				if (section.contains(sectionname) && isClickNeeded == false)
					return true;
				if (section.contains(sectionname) && isClickNeeded == true) {
					primeSectionObjects.getViewSectionLink().click();
					return true;
				}
			} else {
				iAppCommonMethods.swipeByScreenPercentage(0.8, 0.5);
				if (iAppCommonMethods.isElementDisplayed(primeSectionObjects.getViewSectionLink())) {
					String section = primeSectionObjects.getViewSectionLink().getText();
					if (section.contains(sectionname) && isClickNeeded == true) {
						primeSectionObjects.getViewSectionLink().click();
						return true;
					}
				}

			}
		} catch (NoSuchElementException e) {
			System.out.println("View all " + sectionname + " not displayed ");
		}

		return false;
	}

	public boolean verifyTopGridsHeadline(String firstHeadline) {
		System.out.println("First headline === " + firstHeadline);
		boolean flag = iAppCommonMethods.scrollUpToElement(firstHeadline);
		return flag;
	}

	public boolean verifyCatgeoryName(String sectionName) {
		return scrollToCategory(sectionName);
	}

	public void generateAuthenticationTokens(String email, String password) {
		ticketID = authAPIObj.generateTicketID(email, password);
		System.out.println("ticket id" + ticketID);
		token = authAPIObj.getUserToken(ticketID, "access_token");
		System.out.println(" token id" + token);
	}

	public ArticleType getArticleType(ArrayList<String> newsItem) {
		String isPrime = newsItem.get(3);
		System.out.println("isprime.." + isPrime);
		String primePlus = newsItem.get(2);
		System.out.println("prime plus  ... " + primePlus);
		if (isPrime.equals("true"))
			return ArticleType.IS_PRIME;
		else if (primePlus.equals("true"))
			return ArticleType.PRIME_PLUS;
		else
			return null;
	}

	public HashMap<String, String> getKryptonAPIData(String ID, ArticleType type) {
		HashMap<String, String> apiResponseList = new LinkedHashMap<String, String>();
		String URL = String.format(AppFeeds.kryptonUrl, type.value, ID);
		System.out.println("---kryton URL--" + URL);
		Response krytonresponse = authAPIObj.getKrytonAPIResponse(URL, ticketID, token);
		String story = JsonPath.given(krytonresponse.getBody().asString()).getString("NewsItem.Story");
		String content = Jsoup.parse(story).text();
		apiResponseList.put("hl",
				formatText(JsonPath.given(krytonresponse.getBody().asString()).getString("NewsItem.hl")));
		apiResponseList.put("imcn",
				formatText(JsonPath.given(krytonresponse.getBody().asString()).getString("NewsItem.imcn")));
		apiResponseList.put("author",
				JsonPath.given(krytonresponse.getBody().asString()).getString("NewsItem.dtline1"));
		String minsToread = JsonPath.given(krytonresponse.getBody().asString()).getString("NewsItem.minutestoread");
		if (minsToread != null && minsToread != "") {
			apiResponseList.put("date", minsToread + " Mins Read, "
					+ JsonPath.given(krytonresponse.getBody().asString()).getString("NewsItem.dtline2"));
		} else {
			apiResponseList.put("date",
					JsonPath.given(krytonresponse.getBody().asString()).getString("NewsItem.dtline2"));
		}
		apiResponseList.put("summary",
				formatText(JsonPath.given(krytonresponse.getBody().asString()).getString("NewsItem.syn")));
		apiResponseList.put("content", formatText(content));
		System.out.println("api response from krypton api..." + apiResponseList);
		getEmbedCount(story, apiResponseList);
		return apiResponseList;
	}

	public static String formatText(String text) {
		if (text == null)
			return text;
		text = text.replaceAll("\\\\n", "").replaceAll("[^a-zA-Z0-9]", "");
		try {
			byte[] d = text.getBytes("cp1252");
			text = new String(d, Charset.forName("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return text;
	}

	public void getEmbedCount(String content, HashMap<String, String> apiResponseList) {
		apiResponseList.put("twitter", count(content, "<twitter") + "");
		apiResponseList.put("slideshow", count(content, "<slideshow") + "");
		// apiResponseList.put("audio", count(content, "<audio") +"");
		// apiResponseList.put("img", count(content, "<img") +"");
	}

	public static int count(String text, String find) {
		int index = 0, count = 0, length = find.length();
		while ((index = text.indexOf(find, index)) != -1) {
			index += length;
			count++;
		}
		return count;
	}

	public boolean isL2categoryShown(String sectionName) {
		try {
			iAppCommonMethods.isElementDisplayed(primeSectionObjects.getSectionNameTab(appiumDriver, sectionName));
			return true;
		} catch (NoSuchElementException e) {
		}
		return false;
	}

	public boolean clickOnPrimeListingStory() {
		boolean flag = false;
		try {
			WaitUtil.sleep(10000);
			primeSectionObjects.getPrimeStoriesHeadingList().get(0).click();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean clickOnSignInButton() {
		boolean flag = false;
		try {
			WaitUtil.sleep(1000);
			iAppCommonMethods.scrollUpToElement("START FREE TRIAL");
			WaitUtil.sleep(1000);
			if (BaseTest.platform.contains("android")) {
				int x = iAppCommonMethods.getElementsXCoordinates(primeSectionObjects.getSignInButtonOnBlockerLayer());
				x = x + 700;
				int y = iAppCommonMethods.getElementsYCoordinates(primeSectionObjects.getSignInButtonOnBlockerLayer());
				WaitUtil.sleep(2000);
				iAppCommonMethods.tapByCoordinates(appiumDriver, x, y);
				flag = true;

			} else {
				flag = iAppCommonMethods.tapElementRightSide(appiumDriver,
						iAppCommonMethods.getElementByText("Already a member? Sign in Now"));
			}

			WaitUtil.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean validatePlanPageRedirectionFromFooter() {
		boolean flag = false;
		try {
			primeSectionObjects.getSubscribeToEtPrimefooter().click();
			WaitUtil.sleep(1000);
			if (primeSectionObjects.getPlanPage().size() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean verifyStartFreeTrialWidgetPresent() {
		boolean flag = false;
		try {
			WaitUtil.sleep(1000);
			iAppCommonMethods.scrollUpToElement("START FREE TRIAL");
			if (primeSectionObjects.getStartYourTrailWidget().size() > 0) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean verifyStartMembershipWidgetPresent() {
		boolean flag = false;
		try {
			WaitUtil.sleep(1000);
			iAppCommonMethods.scrollUpToElement("START YOUR MEMBERSHIP");
			WaitUtil.sleep(1000);
			if (primeSectionObjects.getStartMembershipWidget().size() > 0) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String getPrimeStoryHeadingFromListing() {
		String articleHeading = null;
		try {
			articleHeading = primeSectionObjects.getPrimeStoriesHeadingList().get(0).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articleHeading;
	}

	public List<MobileElement> getPrimeHeadingsize() {

		try {
			return primeSectionObjects.getPrimeStoriesHeadingList();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	public boolean scrollToNewPlanPaywall() {
		boolean flag = false;
		int swipe = 0;
		if (primeSectionObjects.getPlanPaywall().size() > 0) {
			return true;

		} else {
			while (swipe < 4) {
				iAppCommonMethods.swipeByScreenPercentage(0.60, 0.40);
				swipe++;
				if (primeSectionObjects.getPlanPaywall().size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	public void scrollToTopPage() {
		int swipe = 4;
		while (swipe > 0) {
			iAppCommonMethods.swipeByScreenPercentage(0.40, 0.60);
			swipe--;
		}
	}

	public boolean scrollToSubscribeButton() {
		boolean flag = false;
		int swipe = 0;
		if (primeSectionObjects.getSubscribeButton().size() > 0) {
			return true;

		} else {
			while (swipe < 2) {
				iAppCommonMethods.swipeByScreenPercentage(0.60, 0.40);
				swipe++;
				if (primeSectionObjects.getSubscribeButton().size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	public void clickOnSubscribeButton() {
		try {
			primeSectionObjects.getSubscribeButton().get(0).click();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
