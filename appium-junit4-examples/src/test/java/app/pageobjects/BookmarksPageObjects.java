package app.pageobjects;

import java.util.List;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * 
 * Bookmarks Page elements
 * 
 */
public class BookmarksPageObjects {

	@AndroidFindBy(className = "android.widget.ImageButton")
	private MobileElement backNavigate;

	@AndroidFindBy(id = "com.et.reader.activities:id/edit_bookmark")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='Edit'")
	private MobileElement editButton;

	@AndroidFindBy(id = "com.et.reader.activities:id/headingTV")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='News_Headline' && visible ==1")
	private List<MobileElement> bookmarksList;

	@AndroidFindBy(id = "com.et.reader.activities:id/iv_select_bookmark_news")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='editUnSelected'")
	private List<MobileElement> selectionList;

	@AndroidFindBy(id = "com.et.reader.activities:id/bookmark_iv")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='ic singular bookmark selected'")
	private List<MobileElement> removeBookmarksButton;

	public List<MobileElement> getBookmarkIcon(AppiumDriver<?> driver) {
		return (List<MobileElement>) driver.findElements(MobileBy.id("com.et.reader.activities:id/bookmark_iv"));
	}

	public List<MobileElement> getBookmarkIconIOS(AppiumDriver<?> driver) {
		return (List<MobileElement>) driver.findElements(MobileBy.AccessibilityId("ic singular bookmark selected"));
	}
	
	@AndroidFindBy(xpath = "//*[contains(@content-desc,'Saved Stories')]")
	@iOSXCUITFindBy(accessibility = "Saved Stories")
	private MobileElement saveStoriesTab;

	@AndroidFindBy(xpath = "//*[contains(@content-desc,'History')]")
	@iOSXCUITFindBy(accessibility = "History")
	private MobileElement historyTab;

	@AndroidFindBy(id = "com.et.reader.activities:id/action_search")
	@iOSXCUITFindBy(accessibility = "ic singular search")
	private MobileElement searchIcon;

	@AndroidFindBy(id = "com.et.reader.activities:id/signInButton")
	@iOSXCUITFindBy(accessibility = "LOGIN TO VIEW")
	private MobileElement loginIcon;

	@AndroidFindBy(id = "com.et.reader.activities:id/action_bookmark")
	@iOSXCUITFindBy(accessibility = "ic singular bookmark unselecte")
	private MobileElement bookmarkOnArticle;

	@AndroidFindBy(xpath = "//android.widget.TextView[@index='1']")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='News_Headline' && visible ==1")
	private List<MobileElement> bookmarkArticlesList;
	
	@AndroidFindBy(id="com.et.reader.activities:id/ll_container")
	private List<MobileElement> headerlist;
	/////////////////////////////////////

	public MobileElement getBackNavigate() {
		return backNavigate;
	}

	public MobileElement getEditButton() {
		return editButton;
	}

	public List<MobileElement> getBookmarksList() {
		return bookmarksList;
	}

	public List<MobileElement> getSelectionList() {
		return selectionList;
	}

	public List<MobileElement> getRemoveBookmarksButton() {
		return removeBookmarksButton;
	}

	public MobileElement getSavedStoriesTab() {
		return saveStoriesTab;
	}

	public MobileElement getHistoryTab() {
		return historyTab;
	}

	public MobileElement getSearchIcon() {
		return searchIcon;
	}

	public MobileElement getLoginIcon() {
		return loginIcon;
	}

	public MobileElement getBookmarkIcon() {
		return bookmarkOnArticle;
	}

	public List<MobileElement> getBookmarkArticlesList() {
		return bookmarkArticlesList;
	}
    
	public List<MobileElement> getHeadingList(){
		return headerlist;
	}
}
