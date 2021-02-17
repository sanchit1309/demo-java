package web.pagemethods;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.OnBoardingFlowObjects;

public class OnBoardingFlowMethods {

	OnBoardingFlowObjects onboardingFlowObjects;

	public OnBoardingFlowMethods(WebDriver driver) {

		onboardingFlowObjects = PageFactory.initElements(driver, OnBoardingFlowObjects.class);

	}

	public boolean clickEditPreferencesButton() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(onboardingFlowObjects.getEditPreferencesButton());

		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;

	}

	public boolean clickWelcomeScreenContinueButton() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(onboardingFlowObjects.getWelcomeScreenContinueButton());

		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;

	}

	public boolean fillDataOnPersonaliseYourETExperienceScreen(Map<String, String> userPref) {
		boolean flag = false;
		Map<String, String> userPreferences = userPref;
		try {
			WebBaseMethods.selectDropDownByValue(userPref.get("industry"),
					onboardingFlowObjects.getSelectIndustryDropDown());
			WebBaseMethods.selectDropDownByValue(userPref.get("jobRole"),
					onboardingFlowObjects.getSelectJobRoleDropDown());
			WebBaseMethods.selectDropDownByValue(userPref.get("experience"),
					onboardingFlowObjects.getSelectExperienceDropDown());
			WebBaseMethods.selectDropDownByValue(userPref.get("jobLevel"),
					onboardingFlowObjects.getSelectJobLevelDropDown());
			flag = WebBaseMethods
					.clickElementUsingJSE(onboardingFlowObjects.getPersonaliseEtExperienceScreenContinueButton());

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}

		return flag;

	}

	public boolean fillDataOnIndustriesOnYourScreen(Map<String, String> userPref) {
		boolean flag = false;
		Map<String, String> userPreferences = userPref;
		try {
			deSelectAlltheCheckBoxes(onboardingFlowObjects.getScreen3InputBox());
			WaitUtil.sleep(3000);
			WebBaseMethods.clickElementUsingJSE(
					onboardingFlowObjects.getIndustriesCheckBox(userPreferences.get("categories")));
			flag = WebBaseMethods.clickElementUsingJSE(onboardingFlowObjects.getCategoriesScreenContinueButton());

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}

		return flag;

	}

	public boolean fillDataOnTopicsOfYourInterestScreen(Map<String, String> userPref) {
		boolean flag = false;
		Map<String, String> userPreferences = userPref;
		try {
			deSelectAlltheCheckBoxes(onboardingFlowObjects.getScreen4InputBox());
			WaitUtil.sleep(3000);
			WebBaseMethods.clickElementUsingJSE(
					onboardingFlowObjects.getTopicOfInterestcheckBox(userPreferences.get("topicOfInterest")));
			flag = WebBaseMethods.clickElementUsingJSE(onboardingFlowObjects.getTopicOfInterestScreenContinueButton());

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}

		return flag;

	}

	public boolean fillDataOnWhenYouPreferToRead(Map<String, String> userPref) {
		boolean flag = false;

		Map<String, String> userPreferences = userPref;
		try {
			WebBaseMethods.selectDropDownByValue(userPreferences.get("weekdays"),
					onboardingFlowObjects.getSelectWeekdaysDropDown());
			WebBaseMethods.selectDropDownByValue(userPreferences.get("weekend"),
					onboardingFlowObjects.getSelectWeekendDropDown());
			flag = WebBaseMethods.clickElementUsingJSE(onboardingFlowObjects.getSubmitButton());

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}

		return flag;

	}

	public Map<String, String> getUserFinalPreferencesMap() {
		Map<String, String> userPreferences = new HashedMap();
		try {
			String[] fields = { "industry", "jobRole", "experience", "jobLevel", "categories", "topicOfInterest",
					"weekdays", "weekend" };
			List<String> fieldsLi = Arrays.asList(fields);
			fieldsLi.forEach(field -> {
				String value = WebBaseMethods
						.getTextUsingJSE(onboardingFlowObjects.getFieldsDataFromEditPreferencesPage(field));
				userPreferences.put(field, value);
			});
		} catch (Exception ee) {
			ee.printStackTrace();

		}

		System.out.println("this map is from the UI");
		System.out.println(userPreferences);

		return userPreferences;

	}

	public void deSelectAlltheCheckBoxes(WebElement element) {

		try {
			WebElement parent = element;

			List<WebElement> children = parent.findElements(By.cssSelector("input:checked[type='checkbox']"));

			children.forEach(we -> {
				WebBaseMethods.clickElementUsingJSE(we);
			});

		} catch (Exception ee) {

			ee.printStackTrace();
		}

	}

}
