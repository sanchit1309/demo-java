package app.pageobjects;

import java.time.temporal.ChronoUnit;
import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class AlertsPromptsObjects {
	@AndroidFindBy(id = "com.et.reader.activities:id/tv_tap_to_listen")
	private MobileElement tapToListen;

	@AndroidFindBy(id = "com.et.reader.activities:id/iv_popup_tp_cancel")
	private MobileElement timesPointPopupCancel;

	@AndroidFindBy(id = "com.et.reader.activities:id/coach_mark_layout")
	private MobileElement coachMark;

	@AndroidFindBy(id = "com.et.reader.activities:id/bnews_cancel_icon")
	private MobileElement bNewsCancelIcon;

	@WithTimeout(time = 20, chronoUnit = ChronoUnit.SECONDS)
	@AndroidFindBy(id = "com.et.reader.activities:id/popup_search_layout")
	private MobileElement notificationIntroLayout;

	@iOSXCUITFindBy(iOSNsPredicate = "name='Skip'")
	@AndroidFindBy(id = "com.et.reader.activities:id/tv_skip")
	private MobileElement skipButton;

	@AndroidFindBy(id = "com.et.reader.activities:id/rating_close")
	@iOSXCUITFindBy(iOSNsPredicate = "name='DO YOU LIKE READING ECONOMIC TIMES?'")
	private MobileElement ratingCloseIcon;
	
	
	@AndroidFindBy(id = "com.et.reader.activities:id/iv_cube_close")
	private MobileElement cubeCloseIcon;
	
	@HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
	@AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Interstitial close button']")
	@AndroidFindBy(xpath = "//android.view.View[@text='NO THANKS']")
	@AndroidFindBy(xpath = "//android.view.View[@index=2 ][@clickable='true']")
	@iOSXCUITFindBy(accessibility = "Close Advertisement")

	private MobileElement adCloseIcon;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Choose an account']")
	private MobileElement googleSignInpopup;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='CANCEL']")
	private MobileElement chooseAccountCancelButton;


	@AndroidFindBy(id="com.et.reader.activities:id/skip")
	private List<MobileElement> myEtSkipButton;

	// Getters//
	
	public MobileElement getCoachMark() {
		return coachMark;
	}

	public MobileElement getTapToListen() {
		return tapToListen;
	}

	public MobileElement getTimesPointPopupCancel() {
		return timesPointPopupCancel;
	}

	public MobileElement getBNewsCancelIcon() {
		return bNewsCancelIcon;
	}

	public MobileElement getNotificationIntroLayout() {
		return notificationIntroLayout;
	}

	public MobileElement getbNewsCancelIcon() {
		return bNewsCancelIcon;
	}

	public MobileElement getSkipButton() {
		return skipButton;
	}

	public MobileElement getRatingCloseIcon() {
		return ratingCloseIcon;
	}

	public MobileElement getCubeCloseIcon() {
		return cubeCloseIcon;
	}
	
	public MobileElement getadCloseIcon() {
		return adCloseIcon;
	}
	
	public MobileElement getGoogleSignInpopup() {
		return googleSignInpopup;
	}
	
	public MobileElement getChooseAccountCancelButton() {
		return chooseAccountCancelButton;
	}

	public List<MobileElement> getMyEtSkipButton() {
		return myEtSkipButton;
	}
}
