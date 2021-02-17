package web.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

public class OnBoardingFlowObjects {// div[@class='signMenu']//a[text()='My
									// Preferences']

	@FindBy(xpath = "//div[@class='btnBox']//span[text()='Edit Preferences']")
	private WebElement editPreferencesButton;

	@FindBy(xpath = "//li[@data-ques='Welcome']//div[contains(@class,'btnBox')]//button[text()='CONTINUE']")
	private WebElement welcomeScreenContinueButton;

	@FindBy(xpath = "//input[@id='name']")
	private WebElement getETUserName;

	@FindBy(xpath = "//div[@class='screen 2']//button[@class='btn btn_ctn'][normalize-space()='CONTINUE']")
	private WebElement personaliseEtExperienceScreenContinueButton;

	@FindBy(xpath = "//select[@id='industry']")
	private WebElement selectIndustryDropDown;

	@FindBy(xpath = "//select[@id='jobRole']")
	private WebElement selectJobRoleDropDown;

	@FindBy(xpath = "//select[@id='experience']")
	private WebElement selectExperienceDropDown;

	@FindBy(xpath = "//select[@id='jobLevel']")
	private WebElement selectJobLevelDropDown;

	@FindBy(xpath = "//select[@id='weekdays']")
	private WebElement selectWeekdaysDropDown;

	@FindBy(xpath = "//select[@id='weekend']")
	private WebElement selectWeekendDropDown;

	@FindBy(xpath = "//li[@data-ques='categories']//button[@class='btn btn_ctn'][normalize-space()='CONTINUE']")
	private WebElement categoriesScreenContinueButton;

	@FindBy(xpath = "//li[@data-ques='topicOfInterest']//button[@class='btn btn_ctn'][normalize-space()='CONTINUE']")
	private WebElement topicOfInterestScreenContinueButton;

	@FindBy(xpath = "//button[normalize-space()='SUBMIT']")
	private WebElement submitButton;

	@FindBy(xpath = "//div[@class='screen 3']//div[@class='inputBox clearfix']")
	private WebElement screen3InputBox;

	@FindBy(xpath = "//div[@class='screen4 clearfix']")
	private WebElement screen4InputBox;

	public WebElement getWelcomeScreenContinueButton() {
		return welcomeScreenContinueButton;
	}

	public WebElement getPersonaliseEtExperienceScreenContinueButton() {
		return personaliseEtExperienceScreenContinueButton;
	}

	public WebElement getSelectIndustryDropDown() {
		return selectIndustryDropDown;
	}

	public WebElement getSelectJobRoleDropDown() {
		return selectJobRoleDropDown;
	}

	public WebElement getSelectExperienceDropDown() {
		return selectExperienceDropDown;
	}

	public WebElement getSelectJobLevelDropDown() {
		return selectJobLevelDropDown;
	}

	public WebElement getSelectWeekdaysDropDown() {
		return selectWeekdaysDropDown;
	}

	public WebElement getSelectWeekendDropDown() {
		return selectWeekendDropDown;
	}

	public WebElement getCategoriesScreenContinueButton() {
		return categoriesScreenContinueButton;
	}

	public WebElement getTopicOfInterestScreenContinueButton() {
		return topicOfInterestScreenContinueButton;
	}

	public WebElement getSubmitButton() {
		return submitButton;
	}

	public WebElement getGetETUserName() {
		return getETUserName;
	}

	public WebElement getCurrentIndustrySelectMenuOption(String sectionName) {

		return driver
				.findElement(By.xpath("//li[@data-ques='industry']//select//option[@value='" + sectionName + "']"));
	}

	public WebElement getEditPreferencesButton() {
		return editPreferencesButton;
	}

	public WebElement getFieldsDataFromEditPreferencesPage(String field) {

		return driver.findElement(By.xpath("//div[@data-value='" + field + "']"));
	}

	public WebElement getIndustriesCheckBox(String industryName) {

		return driver.findElement(By.xpath("//h4[normalize-space()='" + industryName + "']"));
	}

	public WebElement getTopicOfInterestcheckBox(String topic) {

		return driver.findElement(By.xpath("//div[contains(text(),'" + topic + "')]"));
	}

	public WebElement getScreen3InputBox() {
		return screen3InputBox;
	}

	public WebElement getScreen4InputBox() {
		return screen4InputBox;
	}
}
