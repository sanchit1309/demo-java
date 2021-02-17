package wap.pagemethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import wap.pageobjects.WapListingPageObjects;
import web.pagemethods.WebBaseMethods;

public class WapListingPageMethods {

	private WapListingPageObjects wapListingPageObjects;
	private static String articleTypes = "slideshow,articleshow,videoshow";
	private static List<String> listArticleTypes = Arrays.asList(articleTypes.split("\\s*,\\s*"));
	private WebDriver driver;
	private StoryPageMethods storyPageMethods;

	public WapListingPageMethods(WebDriver driver) {
		this.driver = driver;
		wapListingPageObjects = PageFactory.initElements(driver, WapListingPageObjects.class);
		storyPageMethods = new StoryPageMethods(driver);
	}

	public List<WebElement> getListOfHeadings() {
		List<WebElement> topHeadlines = new ArrayList<>();
		// WaitUtil.explicitWaitByVisibilityOfAllElements(driver, 5,
		// wapListingPageObjects.getHomeTopStories());
		topHeadlines.addAll(wapListingPageObjects.getHomeTopStories());
		// WaitUtil.explicitWaitByVisibilityOfAllElements(driver, 5,
		// wapListingPageObjects.getTopPageStories());
		topHeadlines.addAll(wapListingPageObjects.getTopPageStories());
		return topHeadlines;
	}

	public void clickFirstHeadline() {
		List<WebElement> li = getTopHeadlines();
		li.get(0).click();
	}

	public List<WebElement> getHomeHeadlines() {
		List<WebElement> homeHeadlines = new ArrayList<>();
		homeHeadlines.addAll(wapListingPageObjects.getHomeTopStories());
		homeHeadlines.addAll(wapListingPageObjects.getNewsHeadings());
		return homeHeadlines;
	}

	public List<WebElement> getMoreHeadlines() {
		List<WebElement> moreHeadlines = new ArrayList<>();
		moreHeadlines.addAll(getListOfHeadings());
		WebBaseMethods.moveToElement(wapListingPageObjects.getMoreNewsTab());
		WaitUtil.sleep(2000);
		WebBaseMethods.clickElementUsingJSE(wapListingPageObjects.getMoreNewsTab());
		WaitUtil.sleep(2000);
		moreHeadlines.addAll(wapListingPageObjects.getMoreNewsList());
		return moreHeadlines;
	}

	public Map<String, String> getHeadlinesLink(List<WebElement> headlineList) {
		Map<String, String> headlineLinks = new HashMap<>();
		for (WebElement ele : headlineList) {
			String eleText = "";
			eleText = WebBaseMethods.getTextUsingJSE(ele).length() > 0 ? ele.getText() : "Top Headline";

			if (!headlineLinks.containsKey(eleText)) {
				headlineLinks.put(eleText, WebBaseMethods.getHrefUsingJSE(ele));
			}
		}
		return headlineLinks;
	}

	public List<String> getAllHeadlineLinks() {
		List<WebElement> headlineList = getHomeHeadlines();
		return WebBaseMethods.getListHrefUsingJSE(headlineList);
	}

	public boolean navigateToTabAndClick(List<WebElement> tabList, String tabName) {
		try {
			for (WebElement ele : tabList) {
				if (ele.getText().equalsIgnoreCase(tabName)) {
					WebBaseMethods.clickElementUsingJSE(ele.findElement(By.cssSelector("a")));
					WaitUtil.sleep(2000);
					break;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isBlog() {
		try {
			if (wapListingPageObjects.getBlogHeader().getText().equalsIgnoreCase("Blog"))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isBrief(String url) {
		if (url.contains("morningbrief") || url.contains("evening_brief"))
			return true;
		else
			return false;
	}

	public Map<String, Boolean> checkRecency(int days) {
		Map<String, Boolean> recencyMap = new HashMap<String, Boolean>();
		String urlMain;
		urlMain = driver.getCurrentUrl();
		recencyMap = checkHomeTabRecency(days);
		driver.get(urlMain);
		return recencyMap;
	}

	public Map<String, Boolean> checkHomeTabRecency(int days) {
		Map<String, Boolean> tabRecencyMap = new HashMap<String, Boolean>();
		boolean status = false;
		try {
			Map<String, String> headlineLinks = getHeadlinesLink(getMoreHeadlines());
			for (Map.Entry<String, String> entry : headlineLinks.entrySet()) {
				if (entry.getValue().contains("morning-brief") || !entry.getValue().contains(".cms"))
					continue;
				status = checkRecencyArticle(entry.getValue(), days);
				tabRecencyMap.put(entry.getKey(), status);
				if (status == false)
					Reporter.log("Old Article :: " + entry.getKey());
			}
			return tabRecencyMap;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Check date of the article
	 * 
	 * @param URL
	 * @param days
	 * @return
	 */
	public boolean checkRecencyArticle(String URL, int days) {
		try {
			driver.get(URL);
			String date = storyPageMethods.getArticleDate();
			return VerificationUtil.isLatest(date, days);
		} catch (Exception e) {
			return false;
		}

	}

	////////////////

	public List<String> getSectionNewsHref(String section) {
		List<WebElement> news = new LinkedList<>();
		List<String> hrefList = new LinkedList<>();
		news.addAll(wapListingPageObjects.getSectionNewsHref(section));
		news.forEach(action -> {
			hrefList.add(action.getAttribute("href"));
		});
		System.out.println(hrefList);
		return hrefList;
	}

	public List<WebElement> getSectionNewsHeadlines(String sectionDiv) {
		List<WebElement> sectionHeadlines = new ArrayList<>();
		// sectionHeadlines.addAll(risePageObjects.getImageLink(sectionDiv));
		try {
			sectionHeadlines.addAll(wapListingPageObjects.getSectionNews(sectionDiv));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sectionHeadlines;
	}

	public List<String> getTopSectionHeaders() {
		List<String> topSectionHeaders = new ArrayList<String>();
		try {
			for (WebElement ele : wapListingPageObjects.getPageTopSections()) {
				topSectionHeaders.add(ele.getText().replaceAll(".*\\n", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(topSectionHeaders);
		return topSectionHeaders;
	}

	public List<WebElement> getTopHeadlines() {
		List<WebElement> sectionHeadlines = new ArrayList<>();
		// sectionHeadlines.addAll(risePageObjects.getImageLink(sectionDiv));
		try {
			sectionHeadlines.addAll(wapListingPageObjects.getTopHeadlinesHref());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sectionHeadlines;
	}

	public List<String> getTopHeadlinesHref() {
		List<WebElement> news = new LinkedList<>();
		List<String> hrefList = new LinkedList<>();
		news.addAll(wapListingPageObjects.getTopHeadlinesHref());
		news.forEach(action -> {
			hrefList.add(action.getAttribute("href"));
		});
		System.out.println(hrefList);
		return hrefList;
	}

	public List<String> getTopHeadlinesAndSubNewsText() {

		List<String> hrefList = WebBaseMethods
				.getListTextUsingJSE(wapListingPageObjects.getTopHeadlinesAndSubNewsHref());

		System.out.println(hrefList);
		return hrefList;
	}

	public List<String> getTopHeadlinesAndSubNewsHref() {

		List<String> hrefList = WebBaseMethods
				.getListHrefUsingJSE(wapListingPageObjects.getTopHeadlinesAndSubNewsHref());

		System.out.println(hrefList);
		return hrefList;
	}

	public List<String> getTopSectionsLink() {

		List<String> hrefList = VerificationUtil.getLinkHrefList(wapListingPageObjects.getPageTopSectionsOld());

		System.out.println(hrefList);
		return hrefList;
	}

	public void removeGoogleVignetteAd() {
		try {
			if (driver.getCurrentUrl().contains("#google_vignette")) {
				WebBaseMethods.switchToFrame(wapListingPageObjects.getOuterIframeGooglevignetteAd());
				WebBaseMethods.switchToFrame(wapListingPageObjects.getGoogleVignetteAdIframe());

				wapListingPageObjects.getDismissGoogleVignetteAd().click();
				WebBaseMethods.switchToDefaultContext();
				WaitUtil.waitForLoad(driver);
				System.out.println(driver.getCurrentUrl());
			}
		} catch (Exception ee) {

			ee.printStackTrace();
		}
	}

	public List<String> getReadMoreFromSectionlinks() {
		List<String> readMoreLinks = new ArrayList<>();
		// sectionHeadlines.addAll(risePageObjects.getImageLink(sectionDiv));
		try {
			readMoreLinks = VerificationUtil.getLinkHrefList(wapListingPageObjects.getReadMoreFromSectionLinks());
			System.out.println(readMoreLinks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readMoreLinks;
	}

	public List<String> getOldViewListingPageTopStoriesHref() {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods.getListHrefUsingJSE(wapListingPageObjects.getOldViewListingPageTopStoriesHref());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public List<String> getOldViewListingPageTopStoriesText() {
		List<String> textList = new LinkedList<String>();
		try {
			textList = WebBaseMethods.getListTextUsingJSE(wapListingPageObjects.getOldViewListingPageTopStoriesHref());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		System.out.println(textList);
		return textList;
	}

	public List<String> getPanaceListingSubSectionPageStoriesHref(String sectionDiv) {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods
					.getListHrefUsingJSE(wapListingPageObjects.getPanacheSubSectionNewsHref(sectionDiv));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public List<String> getPanaceListingSubSectionPageStoriesText(String sectionDiv) {
		List<String> textList = new LinkedList<String>();
		try {
			textList = WebBaseMethods
					.getListTextUsingJSE(wapListingPageObjects.getPanacheSubSectionNewsHref(sectionDiv));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		System.out.println(textList);
		return textList;
	}

	public String getSubSectionHeadingLink(String sectionName) {
		String sectionHeadingLink = "";
		try {
			sectionHeadingLink = WebBaseMethods
					.getHrefUsingJSE(wapListingPageObjects.getSubSectionHeading(sectionName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return sectionHeadingLink;
	}

	public String getSubSectionMoreLink(String sectionName) {
		String sectionHeadingLink = "";
		try {
			sectionHeadingLink = WebBaseMethods
					.getHrefUsingJSE(wapListingPageObjects.getSubSectionMoreLink(sectionName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return sectionHeadingLink;
	}

	public List<String> getTechSubSectionNewsHref(String section) {
		List<WebElement> news = new LinkedList<>();
		List<String> hrefList = new LinkedList<>();
		news.addAll(wapListingPageObjects.getTechSubSectionHeading(section));
		news.forEach(action -> {
			hrefList.add(action.getAttribute("href"));
		});
		System.out.println(hrefList);
		return hrefList;
	}

	public List<String> getTechSubSectionNewsText(String section) {
		List<String> textList = new LinkedList<String>();
		try {
			textList = WebBaseMethods.getListTextUsingJSE(wapListingPageObjects.getTechSubSectionHeading(section));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		System.out.println(textList);
		return textList;
	}

	public List<String> getTechMoreFromSectionlinks() {
		List<String> readMoreLinks = new ArrayList<>();
		// sectionHeadlines.addAll(risePageObjects.getImageLink(sectionDiv));
		try {
			readMoreLinks = VerificationUtil.getLinkHrefList(wapListingPageObjects.getTechMoreFromSectionLinks());
			System.out.println(readMoreLinks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readMoreLinks;
	}
	public List<String> getTechTopNewsText() {
		List<String> textList = new LinkedList<String>();
		try {
			textList = WebBaseMethods.getListTextUsingJSE(wapListingPageObjects.getTechPageTopNews());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		System.out.println(textList);
		return textList;
	}

	public List<String> getTechTopNewslinks() {
		List<String> topNewsLinks = new ArrayList<>();
		// sectionHeadlines.addAll(risePageObjects.getImageLink(sectionDiv));
		try {
			topNewsLinks = VerificationUtil.getLinkHrefList(wapListingPageObjects.getTechPageTopNews());
			System.out.println(topNewsLinks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return topNewsLinks;
	}
	
	public List<String> getTechTopTrendinglinks() {
		List<String> trendingLinks = new ArrayList<>();
		// sectionHeadlines.addAll(risePageObjects.getImageLink(sectionDiv));
		try {
			trendingLinks = VerificationUtil.getLinkHrefList(wapListingPageObjects.getTechTopTrendingTerms());
			System.out.println(trendingLinks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trendingLinks;
	}

}