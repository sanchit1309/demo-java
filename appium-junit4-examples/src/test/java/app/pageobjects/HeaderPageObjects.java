package app.pageobjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class HeaderPageObjects {
	
    @iOSXCUITFindBy(iOSNsPredicate="name == 'ic singular search'")
    @AndroidFindBy(id = "com.et.reader.activities:id/action_search")
	private MobileElement searchIcon;

	@AndroidFindBy(id = "com.et.reader.activities:id/action_notifications")
	private MobileElement notificationIcon;

//	@AndroidFindBy(xpath = "//*[contains(@content-desc,'Drawer Open')]")
	@AndroidFindBy(xpath = "//*[contains(@content-desc,'Navigate up')]")
	@iOSXCUITFindBy(iOSNsPredicate = "name BEGINSWITH 'Acc_menu' && visible==1")
	private MobileElement menuButton;
	

	@AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@resource-id,'toolbar') or contains(@resource-id,'toolBar')]//android.widget.TextView")
	@iOSXCUITFindBy(iOSNsPredicate  = "name BEGINSWITH 'Acc_menu' && visible==1")
	private MobileElement topHeading;



	@AndroidFindBy(id = "com.et.reader.activities:id/tabs")
	@iOSXCUITFindBy(className = "XCUIElementTypeScrollView")
	private MobileElement topTabBar;

	

	@AndroidFindBy(id = "com.et.reader.activities:id/action_bookmark")
	@iOSXCUITFindBy(accessibility="ic singular bookmark unselecte")
	private MobileElement bookmarkButton;
	
	@AndroidFindBy(id = "com.et.reader.activities:id/story_custom_options")
	@iOSXCUITFindBy(accessibility="g")
	private MobileElement moreIconButton;
	
	
	@AndroidFindBy(id = "com.et.reader.activities:id/tv_action_2")
	@iOSXCUITFindBy(accessibility="Sign In")
	private MobileElement headerSignin;





	/////// Getters start here/////////
	

	public MobileElement getSearchIcon() {
		return searchIcon;
	}

	public MobileElement getNotificationIcon() {
		return notificationIcon;
	}

	public MobileElement getMenuButton() {
		return menuButton;
	}

	public MobileElement getTopHeading() {
		return topHeading;
	}

	

	

	/*
	 * public MobileElement getHeaderText() { return headerText; }
	 */

	public MobileElement getBookmarkButton() {
		return bookmarkButton;
	}

	

	public MobileElement getTopTabBar() {
		return topTabBar;
	}
	
	public MobileElement getMoreIconButton() {
		return moreIconButton;
	}
	

	public MobileElement getHeaderSignin() {
		return headerSignin;
	}
}
