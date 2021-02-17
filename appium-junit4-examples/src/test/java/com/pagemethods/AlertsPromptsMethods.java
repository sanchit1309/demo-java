package com.pagemethods;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

import app.pageobjects.AlertsPromptsObjects;
import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import static common.launchsetup.BaseTest.driver;
import static common.launchsetup.BaseTest.iAppCommonMethods;

public class AlertsPromptsMethods {
	AppiumDriver<?> appiumDriver;
	AlertsPromptsObjects alertPromptObjects;

	public AlertsPromptsMethods(AppiumDriver<?> driver) {
		appiumDriver = driver;
		alertPromptObjects = new AlertsPromptsObjects();
		PageFactory.initElements(new AppiumFieldDecorator(appiumDriver), alertPromptObjects);
	}

	/**
	 * Accept OS level prompts
	 */
	public void acceptAlerts() {
		boolean reRun = true;
		while (reRun) {
			try {
				appiumDriver.switchTo().alert().accept();
				WaitUtil.sleep(5000);
			} catch (Exception e) {
				reRun = false;
			}
		}
	}

	/**
	 * clicks skip on android & ios welcome screen
	 */
	public void clickSkipEducationLayer() {
		try {
			alertPromptObjects.getSkipButton().click();
			System.out.println("Skipped Sign in");
		} catch (NoSuchElementException e) {
			System.out.println("Skip button not found");
		}
	}

	/**
	 * Dismiss tap to listen layout on brief
	 */
	public void dismissTapToListenPopup() {
		try {
			alertPromptObjects.getTapToListen().click();
		} catch (Exception e) {

		}
	}

	/**
	 * Dismiss times point pop up on home page
	 */
	public void dismissTimesPointPopUp() {
		try {
			alertPromptObjects.getTimesPointPopupCancel().click();
		} catch (Exception e) {
			System.out.println("Times point pop up not found");
		}
	}

	/**
	 * Dismiss brief educative screen
	 */
	public void dismissCoachMark() {
		try {
			alertPromptObjects.getCoachMark().click();
		} catch (Exception e) {

		}
		dismissTapToListenPopup();
	}
	
	
	public void clickCubeCloseIcon(){
		try {
			alertPromptObjects.getCubeCloseIcon().click();
		} catch (NoSuchElementException e) {
			System.out.println("Cube not found");
		}
	}
	
	public void clickChooseAccountCancelButton(){
		try {
			alertPromptObjects.getChooseAccountCancelButton().click();
			System.out.println("Choose account for ET popup closed");
		} catch (NoSuchElementException e) {
			System.out.println("Choose Account popup not found");
		}
	}

	
	public void clickAdCloseIcon(){
		try {
				alertPromptObjects.getadCloseIcon().click();
				System.out.println("Closed Ad");
			} 		
		catch (NoSuchElementException e)
			{
				System.out.println("Ad not found");
			}
	}
	
	public void dismissGoogleSignInpopup(){
		try {   
			if(alertPromptObjects.getGoogleSignInpopup().isDisplayed())
				{
					if(BaseTest.platform.contains("android")){
						driver.navigate().back();
					}
					else {
						iAppCommonMethods.tapByCoordinates(appiumDriver, 90, 90);
						System.out.println("Dismiss SignIn popup");
					}
				WaitUtil.sleep(2000);
				}
			} catch (NoSuchElementException e) {
			System.out.println("SignIn popup not found");
			}
	}
	
	
	/**
	 * Method to dismiss pop-ups on home screen
	 */
	public void dismissAllPopups() {
		WaitUtil.sleep(3000);
		clickSkipButtonOnSplash();
		clickSkipEducationLayer();
		dismissTimesPointPopUp();
		dismissNotificationIntro();
		clickRatingCloseIcon();
		//clickBreakNewsCloseIcon();
		clickChooseAccountCancelButton();
		clickCubeCloseIcon();
		// clickBreakNewsCloseIcon();
	}

	public void clickBreakNewsCloseIcon() {
		try {
			alertPromptObjects.getBNewsCancelIcon().click();
		} catch (Exception e) {
			System.out.println("Breaking news icon not found");
		}
	}

	public void dismissNotificationIntro() {
		try {
			alertPromptObjects.getNotificationIntroLayout().click();
		} catch (Exception e) {
			System.out.println("Intro notification icon not found");
		}

	}

	public void clickRatingCloseIcon() {
		try {
			WaitUtil.sleep(2000);
			if (alertPromptObjects.getRatingCloseIcon().isDisplayed()) {
				if (BaseTest.platform.contains("android")) {
					alertPromptObjects.getRatingCloseIcon().click();
				} else {
					iAppCommonMethods.tapByCoordinates(appiumDriver, 90, 90);
					System.out.println("Dismiss rating popup");
				}
				WaitUtil.sleep(2000);
			}
		}

		catch (NoSuchElementException e) {

		}

	}
	
	public boolean isRatingCloseIconDisplayed() {
		try {
			alertPromptObjects.getRatingCloseIcon().isDisplayed();
			return true;

		} catch (NoSuchElementException e) {
		}
		return false;
	}
	public void clickSkipButtonOnSplash(){
		try {
			if(BaseTest.platform.contains("android"))
			if(alertPromptObjects.getMyEtSkipButton().size()>0)
			{
				alertPromptObjects.getMyEtSkipButton().get(0).click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
