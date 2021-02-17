package app.pagemethods;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

import app.pageobjects.MenuPageObjects;
import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import static common.launchsetup.BaseTest.iAppCommonMethods;

public class MenuPageMethods {
	AppiumDriver<?> appiumDriver;
	MenuPageObjects menuPageObjects;

	public MenuPageMethods(AppiumDriver<?> driver) {
		appiumDriver = driver;
		menuPageObjects = new MenuPageObjects();
		PageFactory.initElements(new AppiumFieldDecorator(driver), menuPageObjects);
	}

	public MenuPageObjects getMenuPageObjects() {
		return menuPageObjects;
	}

	public void clickOnActionMarket() {
	}

	public void clickSignInIcon() {
		menuPageObjects.getLogInIcon().click();
	}

	public boolean isLoginDisplayed() {
		Boolean flag = false;
		try {
			menuPageObjects.getLogInIcon().isDisplayed();
			flag = true;
		} catch (Exception e) {

		}
		return flag;
	}

	public boolean clickLogout() {
		try {
			if (BaseTest.platform.contains("ios")) {
				// menuPageObjects.getSettingButton().click();
				BaseTest.iAppCommonMethods.swipeUp();
				menuPageObjects.getLogOutIcon().click();
			} else {
				menuPageObjects.getSettingButton().click();
				menuPageObjects.getLogOutIcon().click();
			}
			if (menuPageObjects.getYesButton().size() > 0)
				menuPageObjects.getYesButton().get(0).click();

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean clickLogoutSettingsPage() {
		try {
			String signOut = BaseTest.platform.contains("ios") ? "Sign out" : "SIGN OUT";
			if (BaseTest.platform.contains("ios")) {
				WaitUtil.sleep(3000);
				iAppCommonMethods.swipeUp();
				menuPageObjects.getLogOutIcon().click();
				if (menuPageObjects.getYesButton().size() > 0) {
					menuPageObjects.getYesButton().get(0).click();
					BaseTest.iAppCommonMethods.swipeDown();
					return true;
				}
			} else {
				MobileElement el = iAppCommonMethods.getElementByText(signOut);
				el.click();
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public void clickIntroLayouts() {
		List<MobileElement> introLayout = menuPageObjects.getIntroLayout();
		while (introLayout.size() > 0) {
			introLayout.get(0).click();
		}
	}

	public void clickSearchLayout() {
		try {
			menuPageObjects.getIntroDialogSearch().click();
		} catch (Exception e) {
			// do nothing
		}
	}

	public boolean clickSettingsIcon() {
		boolean isSettingsIcon = false;
		try {
			menuPageObjects.getSettingButton().click();
			isSettingsIcon = true;
		} catch (NoSuchElementException e) {

		}
		return isSettingsIcon;
	}

	/** After iOS integration **/
	public boolean scrollToMenuOptionClick(String text, boolean isClickNeeded) {
		boolean flag = false;
		flag = iAppCommonMethods.scrollUpToElement(text);
		if (!flag) {
			iAppCommonMethods.scrollDown();
			flag = iAppCommonMethods.scrollUpToElement(text);
		}
		if (isClickNeeded && flag) {
			flag = iAppCommonMethods.clickElement(iAppCommonMethods.getElementByText(text));
		}
		WaitUtil.sleep(2000);
		return flag;
	}

	public boolean closeL1MenuOption(String text) {
		boolean flag = false;
		flag = scrollToMenuOptionClick(text, true);
		return flag;
	}

	public boolean scrollSubMenuItemToView(String text) {
		boolean flag = false;
		flag = iAppCommonMethods.isElementDisplayed(iAppCommonMethods.getElementByText(text));
		if (BaseTest.platform.contains("ios") && !flag)
			iAppCommonMethods.swipeDown();
		if (!flag) {
			iAppCommonMethods.swipeUp();
			flag = iAppCommonMethods.isElementDisplayed(iAppCommonMethods.getElementByText(text));
			if (!flag) {
				iAppCommonMethods.scrollDown();
				flag = iAppCommonMethods.isElementDisplayed(iAppCommonMethods.getElementByText(text));
			}
		}
		return flag;
	}

	public boolean clickMenuByLabel(String label) {
		boolean flag = scrollToMenuOptionClick(label, true);
		return flag;
	}

	public boolean clickL1L2MenuByLabel(String L1Name, String L2Menu) {
		boolean flag = scrollToMenuOptionClick(L1Name, true);
		if (flag) {
			WaitUtil.sleep(2000);
			flag = scrollToMenuOptionClick(L2Menu, true);
		}
		return flag;
	}

	public boolean closeMenuIcon() {
		boolean flag = false;
		try {
			iAppCommonMethods.tapScreenRightSide(appiumDriver);
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public Boolean isExpandIconPresent(String menuOption) {
		Boolean flag = false;
		try {
			menuPageObjects.getMenuExpandIcon();
			flag = true;
		} catch (Exception e) {

		}
		return flag;
	}

	public boolean isTimesPointShown() {
		return BaseTest.iAppCommonMethods.isElementDisplayed(menuPageObjects.getTimesPoint());
	}

	public boolean isRedeemableShown() {
		return BaseTest.iAppCommonMethods.isElementDisplayed(menuPageObjects.getRedeemNow());
	}
	
	
	public boolean clickL2MenuByName(String subMenu) {
		boolean flag = false;
		int counter = 0;
		try {
			while (counter < 5 && !menuPageObjects.getSubMenuOption(appiumDriver, subMenu).isDisplayed()) {
				iAppCommonMethods.swipeByScreenPercentage(0.80, 0.65);
				counter++;
			}
			if (menuPageObjects.getSubMenuOption(appiumDriver, subMenu).isDisplayed()) {
				{
					menuPageObjects.getSubMenuOption(appiumDriver, subMenu).click();
					flag = true;
				}
			}
		} catch (NoSuchElementException e) {

		}
		return flag;
	}
	
	
	public boolean scrollDownToSettingIcon() {
		boolean flag = false;
		int counter = 0;
		try {
			while (counter < 5) {
				if (iAppCommonMethods.isElementDisplayed(menuPageObjects.getSettingButton())) {
					flag = true;
					break;
				}
				counter++;
				iAppCommonMethods.scrollDown();
			}
		} catch (NoSuchElementException e) {

		}
		return flag;
	}
	
}
