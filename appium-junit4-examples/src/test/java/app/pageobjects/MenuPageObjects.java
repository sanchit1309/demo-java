package app.pageobjects;

import java.util.List;

import common.launchsetup.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class MenuPageObjects {

	@AndroidFindBy(id = "com.et.reader.activities:id/tv_signIn")
	@iOSXCUITFindBy(accessibility = "ic login menu")
	public MobileElement logInIcon;

	@AndroidFindBy(xpath = "//*[@text='LOGOUT']")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Sign out'")
	private MobileElement logOutIcon;

	@AndroidFindBy(id = "com.et.reader.activities:id/tv_tp_lhs_loginNclaim_redeem_value")
	@iOSXCUITFindBy(iOSNsPredicate = "name ENDSWITH 'TIMESPOINTS' &&  type='XCUIElementTypeStaticText'")
	private MobileElement timesPoint;

	@AndroidFindBy(id = "com.et.reader.activities:id/tv_tp_lhs_claim_redeem")
	@iOSXCUITFindBy(iOSNsPredicate = "(name BEGINSWITH 'Redeem Now' || name BEGINSWITH 'My Points') &&  type='XCUIElementTypeButton'")
	private MobileElement redeemNow;

	@AndroidFindBy(id = "com.et.reader.activities:id/tv_settings")
	@iOSXCUITFindBy(iOSNsPredicate="name =='SettingsLHS'")
	private MobileElement settingButton;


	// @AndroidFindBy(id = "com.et.reader.activities:id/introTextSearch")
	@AndroidFindBy(id = "com.et.reader.activities:id/introNotifyTextBottom")
	private MobileElement introDialogSearch;

	@AndroidFindBy(id = "com.et.reader.activities:id/intro_parent_layout")
	private List<MobileElement> introLayout;

	@AndroidFindBy(id = "com.et.reader.activities:id/expandIcon")
	private MobileElement menuExpandIcon;
	
	@AndroidFindBy(id="android:id/button1")
	@iOSXCUITFindBy(iOSNsPredicate="name== 'Yes'")
	private List<MobileElement> yesButton;
	
	@AndroidFindBy(id="com.et.reader.activities:id/leftmenu")
	private List<MobileElement> menuName;
	
	/////// Getters start here////////

	public MobileElement getLogInIcon() {
		return logInIcon;
	}

	public MobileElement getLogOutIcon() {
		return logOutIcon;
	}

	public MobileElement getSettingButton() {
		return settingButton;
	}



	public MobileElement getIntroDialogSearch() {
		return introDialogSearch;
	}

	public List<MobileElement> getIntroLayout() {
		return introLayout;
	}

	public MobileElement getMenuExpandIcon() {
		return menuExpandIcon;
	}

	public MobileElement getSpecificMenuOption(AppiumDriver<?> driver, String optionName) {
		return (MobileElement) driver.findElement(MobileBy.xpath(
				"//*[contains(@resource-id,'com.et.reader.activities:id/leftmenu') and (@text='" + optionName + "')]"));
	}

	public MobileElement hasMenuSubmenu(AppiumDriver<?> driver, String optionName) {
		return (MobileElement) driver.findElement(MobileBy
				.xpath("//*[contains(@resource-id,'com.et.reader.activities:id/leftmenu') and (@text='" + optionName
						+ "')]/following-sibling::*//[contains(@resource-id,'com.et.reader.activities:id/expandIcon')]"));
	}
	
	public MobileElement getSubMenuOption(AppiumDriver<?> driver, String optionName) {
		if (BaseTest.platform.contains("android")) {
			return (MobileElement) driver.findElement(
					MobileBy.xpath("//*[contains(@resource-id,'com.et.reader.activities:id/submenu') and (@text='"
							+ optionName + "')]"));
		} else {
			return (MobileElement) driver.findElement(
					MobileBy.xpath("//*[(@type='XCUIElementTypeStaticText') and (@value='" + optionName + "')]"));
		}

	}

	
	public MobileElement getTimesPoint() {
		return timesPoint;
	}

	public MobileElement getRedeemNow() {
		return redeemNow;
	}
	
	public List<MobileElement> getYesButton(){
		return yesButton;
	}
	
	public List<MobileElement> getMenuName() {
		return menuName;
	}
	
	
}
