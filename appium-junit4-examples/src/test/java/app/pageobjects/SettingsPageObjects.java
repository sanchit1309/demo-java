package app.pageobjects;

import org.openqa.selenium.By;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SettingsPageObjects {

	@AndroidFindBy(xpath = "//*[@text='LOGOUT']")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Sign out'")
	private MobileElement logOutIcon;

	private final String ByFeedback = "com.et.reader.activities:id/settings_sendfeedback";
	@AndroidFindBy(id = ByFeedback)
	private MobileElement feedBackLink;
	
	@AndroidFindBy(id = "com.et.reader.activities:id/tv_timespoint")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'My TimesPoints'")
	private MobileElement timesPointOption;
	
	
	/////// Getters start here////////
	public MobileElement getLogoutLink() {
		return logOutIcon;
	}

	public MobileElement getFeedBackLink() {
		return feedBackLink;
	}

	public By getFeedbackBy() {
		return By.id(ByFeedback);
	}

	public String getByFeedback() {
		return ByFeedback;
	}

	public MobileElement getTimesPoints(){
		return timesPointOption;
	}
	
}
