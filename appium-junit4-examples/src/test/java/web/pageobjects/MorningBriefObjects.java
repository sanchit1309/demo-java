package web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MorningBriefObjects {
	
	@FindBy(xpath="//div[contains(@class,'BriefSlider')]//h2/parent::a")
	private List<WebElement> slidesAnchorTags;
	
	@FindBy(xpath=".//*[@id='pageContent']//span[contains(text(),'Next')]")
	private WebElement nextButton;

	@FindBy(css=".rdfullstry a")
	private WebElement fullStory;
	/////////////////////

	public List<WebElement> getSlidesAnchorTag() {
		return slidesAnchorTags;
	}

	public WebElement getNextButton() {
		return nextButton;
	}

	public WebElement getFullStory() {
		return fullStory;
	}
	
	
}
