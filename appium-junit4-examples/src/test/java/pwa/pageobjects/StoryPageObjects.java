package pwa.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StoryPageObjects {
	
	@FindBy(xpath="(//*[@class='byLine']//time)[2]")
	private WebElement articleDate;

	public WebElement getArticleDate() {
		return articleDate;
	}

}
