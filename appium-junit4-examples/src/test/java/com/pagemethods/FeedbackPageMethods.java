package com.pagemethods;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

import app.pageobjects.FeedbackPageObjects;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * This class list method for feedback page on App
 * 
 * @author Pooja.Gupta1
 *
 */
public class FeedbackPageMethods {

	AppiumDriver<?> appDriver;
	FeedbackPageObjects feedbackPageObjects;

	public FeedbackPageMethods(AppiumDriver<?> driver) {
		appDriver = driver;
		feedbackPageObjects = new FeedbackPageObjects();
		PageFactory.initElements(new AppiumFieldDecorator(appDriver), feedbackPageObjects);
	}

	/**
	 * Method returns header text
	 * 
	 * @return
	 */
	public String getFeedbackHeader() {
		String text = "";
		try {
			text = feedbackPageObjects.getHeader().getText();
		} catch (Exception e) {

		}
		return text;
	}

	/**
	 * Method sends user feedback
	 * 
	 * @param feedback
	 * @param username
	 * @param email
	 * @return
	 */
	public boolean sendFeedback(String feedback, String username, String email) {
		try {
			feedbackPageObjects.getConversationDetails().sendKeys(feedback);
			feedbackPageObjects.getUsername().sendKeys(username);
			feedbackPageObjects.getEmail().sendKeys(email);
			feedbackPageObjects.getSend().click();
			WaitUtil.sleep(5000);
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
		return true;
	}

	public String getFeedBackText() {
		String text = "";
		try {
			text = feedbackPageObjects.getFeedBackText().getText();
		} catch (NoSuchElementException e) {

		}
		return text;
	}
}
