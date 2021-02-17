
package com.web.pagemethods;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.HeaderPageObjects;

public class HeaderPageMethods {
	private HeaderPageObjects headerPageObjects;
	private WebDriver driver;

	public HeaderPageMethods(WebDriver driver) {
		this.driver = driver;
		headerPageObjects = PageFactory.initElements(driver, HeaderPageObjects.class);
	}

	public boolean checkUserLoggedInState() {
		boolean flag = false;
		try {
			WaitUtil.sleep(5000);
			if (WebBaseMethods.isDisplayed(headerPageObjects.getLoggedInUserTab(), 20)) {
				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public Boolean clickOnSearchButton() {
		Boolean flag = false;
		try {
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, headerPageObjects.getSearchButton());
			WebBaseMethods.clickElementUsingJSE(headerPageObjects.getSearchButton());
			flag = true;
		} catch (WebDriverException e) {

		}
		return flag;
	}

	public boolean logOutIfUserIsLoggedIn() {
		boolean flag = false;
		try {
			if (checkUserLoggedInState()) {
				WaitUtil.sleep(3000);
				WebBaseMethods.moveToElement(headerPageObjects.getDropDownMenuOnSignIn());
				WaitUtil.sleep(3000);
				WaitUtil.explicitWaitByElementToBeClickable(driver, 20,
						headerPageObjects.getLogOutLinkUnderLoggedInMenu());
				WebBaseMethods.clickElementUsingJSE(headerPageObjects.getLogOutLinkUnderLoggedInMenu());
				WaitUtil.sleep(3000);
				ETSharedMethods.clickETLinkInterstitialPage();
				WaitUtil.sleep(3000);

				flag = headerPageObjects.getSignInLink().isDisplayed();

			} else {
				flag = true;
			}
		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}

		return flag;

	}

	public boolean clickMyPreferencesTab() {
		boolean flag = false;

		try {
			WebBaseMethods.moveToElement(headerPageObjects.getDropDownMenuOnSignIn());
			WaitUtil.sleep(3000);
			flag = WebBaseMethods.clickElementUsingJSE(headerPageObjects.getMyPreferencesTab());

		} catch (Exception ee) {

		}

		return flag;
	}
	//////////// before odin code is below/////////////////

	public Boolean clickOnSignInLink() {
		Boolean flag = false;
		try {
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, headerPageObjects.getSignInLink());
			WebBaseMethods.clickElementUsingJSE(headerPageObjects.getSignInLink());
			flag = true;
		} catch (WebDriverException e) {

		}
		return flag;
	}

	public boolean isSignInLinkShown() {
		Boolean flag = false;
		try {
			flag = headerPageObjects.getSignInLink().isDisplayed();
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean getLoggedInUserImage() {
		WaitUtil.sleep(2000);
		for (int i = 0; i < 3; i++) {
			try {
				headerPageObjects.getLoggedInUserImage().isDisplayed();
				return true;
			} catch (NoSuchElementException | StaleElementReferenceException e) {

			}
		}
		return false;
	}

	public String getDateTimeTab() {
		try {

			return headerPageObjects.getDateTimetab().getText();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public WebElement getLoggedInUsersImage() {
		// TODO Auto-generated method stub
		return headerPageObjects.getLoggedInUserImage();
	}

	public boolean doLogout() {
		boolean flag = false;
		try {
			WebBaseMethods.JSHoverOver(headerPageObjects.getDD());
			WebBaseMethods.clickElementUsingJSE(headerPageObjects.getLogoutLink());
			flag = true;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean clickSearchIconIfVisible() {
		WebBaseMethods.scrollToTop();
		if (WebBaseMethods.isElementVisible(headerPageObjects.getSearchIcon())) {
			try {
				headerPageObjects.getSearchIcon().click();
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean isSearchFieldVisible() {
		return WebBaseMethods.isElementVisible(headerPageObjects.getSearchField());
	}

	public void sendQueryInSearchField(String query) {
		try {
			headerPageObjects.getSearchField().clear();
			headerPageObjects.getSearchField().sendKeys(query);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean areSearchResultsVisible() {
		return !headerPageObjects.getPrimeSearchResults().isEmpty();
	}

	public Map<String, String> getResultTitlesAndHrefs() {
		Map<String, String> titleAndHrefs = new LinkedHashMap<String, String>();
		headerPageObjects.getPrimeSearchResults().forEach(result -> {
			titleAndHrefs.put(result.getText(), result.getAttribute("href"));
		});
		return titleAndHrefs;
	}

	public boolean clickCategoryOnHeader(String category) {
		boolean flag = false;
		try {
			// WaitUtil.explicitWaitByPresenceOfElement(driver, 25,
			// headerPageObjects.getCategoryByNameFromHeader(category));
			WebBaseMethods.isDisplayed(headerPageObjects.getCategoryByNameFromHeader(category), 25);
			WebBaseMethods.clickElementUsingJSE(headerPageObjects.getCategoryByNameFromHeader(category));
			flag = WebBaseMethods.isDisplayed(headerPageObjects.getSelectedCategoryFromHeader(category), 25);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean validateCategoryIsSelected(String category) {
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(headerPageObjects.getSelectedCategoryFromHeader(category), 10);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isPrimeIconForSubscribedUserShown() {
		Boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(headerPageObjects.getPrimeIconForSubscribedUser());
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean isETPrimeLogoShown() {
		Boolean flag = false;
		try {
			WaitUtil.sleep(4500);
			flag = WebBaseMethods.isDisplayed(headerPageObjects.getEtPrimeLogoOnTop(), 20);
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean clickMySubscription() {
		boolean flag = false;
		try {
			WebBaseMethods.JSHoverOver(headerPageObjects.getDD());
			WebBaseMethods.clickElementUsingJSE(headerPageObjects.getMySubscriptions());
			flag = true;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return flag;
	}

}
