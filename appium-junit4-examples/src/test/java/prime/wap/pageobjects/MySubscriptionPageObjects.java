package prime.wap.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MySubscriptionPageObjects {
	private WebDriver driver;
	
	public MySubscriptionPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//div[@id='planL']/div")
	private WebElement subscriptionBox;

	@FindBy(xpath = "(//div[@id='planL']/div)[1]")
	private WebElement subscriptionDetails;
	
	@FindBy(xpath = "//a[@id='subscriptionLink']")
	private WebElement subscribeNowButton;
	
	@FindBy(xpath = "//a[contains(text(),'Renew')]")
	private WebElement renewButton;
	
	@FindBy(xpath = "//div[@class='cancel_btn']/a")
	private WebElement cancelButton;
	
	@FindBy(xpath = "//div[@class='sv__point sv__single-survey-point']//div[@class='sv__single-choice']//div[text()='Others']/../../div[@class='sv__checkbox sv__circle sv-bg-bw sv-border-top-bw']")
	private WebElement otherOptionInCancelSurvey;
	
	@FindBy(xpath = "//button[@class='sv__submit-button  sv-bg-bs sv-col-fs']")
	private WebElement nextButtonInSurvey;
	
	@FindBy(xpath ="//span[@class='btn_tg']")
	private WebElement cancelMembershipButton;
	
	@FindBy(xpath = "//div[@class='cancel_wrp']")
	private WebElement cancelMembershipWrapper;
	
	/* ************** Getters *************** */
	

	public WebElement getSubscriptionBox() {
		return subscriptionBox;
	}

	public WebElement getSubscriptionDetails() {
		return subscriptionDetails;
	}
	
	public WebElement getSubscribeNowButton() {
		return subscribeNowButton;
	}
	
	public WebElement getRenewButton() {
		return renewButton;
	}
	
	public WebElement getCancelButton() {
		return cancelButton;
	}
	
	public WebElement getOtherOptionInCancelSurvey() {
		return otherOptionInCancelSurvey;
	}
	
	public WebElement getNextButtonInSurvey() {
		return nextButtonInSurvey;
	}
	
	public WebElement getCancelMembershipButton() {
		return cancelMembershipButton;
	}
	
	public WebElement getCancelMembershipWrapper() {
		return cancelMembershipWrapper;
	}
}
