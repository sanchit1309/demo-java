package com.pagemethods;

import java.util.List;

import org.openqa.selenium.support.PageFactory;

import app.pageobjects.BookmarksPageObjects;
import app.pageobjects.PrimeSectionObjects;
import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import static common.launchsetup.BaseTest.iAppCommonMethods;

/**
 * 
 * Methods for Bookmarks Page
 *
 */
public class BookmarksPageMethods {

	AppiumDriver<?> appDriver;
	BookmarksPageObjects bookmarksPageObjects;
	PrimeSectionMethods primeSectionMethods;
	PrimeSectionObjects primeSectionObjects;
	HeaderPageMethods headerPageMethods;

	public BookmarksPageMethods(AppiumDriver<?> driver) {
		appDriver = driver;
		bookmarksPageObjects = new BookmarksPageObjects();
		primeSectionMethods = new PrimeSectionMethods(driver);
		primeSectionObjects = new PrimeSectionObjects();
		headerPageMethods=new HeaderPageMethods(driver);
		PageFactory.initElements(new AppiumFieldDecorator(appDriver), bookmarksPageObjects);
	}

	public boolean verifyBookmarksArticle(int count) {
		boolean flag = false;
		try {
			if (count != bookmarksPageObjects.getBookmarksList().size())
				flag = false;
			else
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		appDriver.navigate().back();
		return flag;
	}

	public int getBookmarkedCount() {
		int count = 0;
		try {
			count = bookmarksPageObjects.getBookmarksList().size();
		} catch (Exception e) {

		}
		return count;
	}

	public boolean verifyDeleteArticle() {
		boolean flag = false;
		int count = 0;
		try {
			count = bookmarksPageObjects.getBookmarksList().size();
			if (BaseTest.platform.contains("ios")) {
				bookmarksPageObjects.getBookmarkIconIOS(appDriver).get(0).click();
				BaseTest.iAppCommonMethods.scrollDown();
				WaitUtil.sleep(5000);
			} else {
				bookmarksPageObjects.getBookmarkIcon(appDriver).get(0).click();
				WaitUtil.sleep(2000);
			}
			if (count - 1 != bookmarksPageObjects.getBookmarksList().size())
				flag = false;
			else
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public boolean verifyMyLibraryIcons() {
		boolean flag = false;
		try {
			if (bookmarksPageObjects.getSavedStoriesTab().isDisplayed()
					&& bookmarksPageObjects.getHistoryTab().isDisplayed()
					&& bookmarksPageObjects.getSearchIcon().isDisplayed())
				flag = true;
			return flag;
		} catch (Exception e) {
		}
		return flag;
	}

	public boolean clickHistoryTab() {
		boolean flag = false;
		try {
			if (bookmarksPageObjects.getHistoryTab().isDisplayed()) {
				bookmarksPageObjects.getHistoryTab().click();
				flag = true;
				return flag;
			}
		} catch (Exception e) {
		}
		return flag;
	}

	public boolean clickLoginIcon() {
		boolean flag = false;
		try {
			if (bookmarksPageObjects.getLoginIcon().isDisplayed()) {
				bookmarksPageObjects.getLoginIcon().click();
				flag = true;
				return flag;
			}
		} catch (Exception e) {
		}
		return flag;
	}

	public boolean clickBookmarkIconOnArticle() {
		boolean flag = false;
		try {
			WaitUtil.sleep(10000);
			bookmarksPageObjects.getBookmarkIcon().click();
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	public String getPrimeArticleHeading() {
		WaitUtil.sleep(2000);
		String heading = primeSectionMethods.getPrimeStoryHeadingFromListing();
		return heading;
	}

	public boolean getbookmarkedArticle(String headingOnArticleshow) {
		WaitUtil.sleep(2000);
		boolean flag = false;
		try {
			List<MobileElement> li = bookmarksPageObjects.getBookmarkArticlesList();
			for (MobileElement mobileElement : li) {
				System.out.println("storiesss ::: " + mobileElement.getText());
				if (mobileElement.getText().equalsIgnoreCase(headingOnArticleshow)) {
					if (BaseTest.platform.contains("android"))
						mobileElement.click();
					flag = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}
	
	public boolean clickRemoveBookmarkIconOnArticle() {
		boolean flag = false;
		try {
			if (BaseTest.platform.contains("ios")) {
				bookmarksPageObjects.getBookmarkIconIOS(appDriver).get(0).click();
				BaseTest.iAppCommonMethods.scrollDown();
				WaitUtil.sleep(7000);
			}
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public boolean clickFirstStory(List<MobileElement> ls) {
		boolean flag=false;
		String heading=ls.get(0).getText();
		System.out.println("Heading in listing : " + heading);
		ls.get(0).click();
		WaitUtil.sleep(2000);
		AlertsPromptsMethods alertsPromptsMethods= new AlertsPromptsMethods(appDriver);
		alertsPromptsMethods.dismissGoogleSignInpopup();
		if(bookmarksPageObjects.getHeadingList().size()>0)
		return true;
		else 
			return false;
		
	}
	public void bookmarkFirstArticle() {
	   
		headerPageMethods.clickBookMarkIcon();
        iAppCommonMethods.navigateBack(appDriver);
        WaitUtil.sleep(2000);
	}
}
