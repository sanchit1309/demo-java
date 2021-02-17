package web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PrimeShowStoryPageObjects {

	@FindBy(xpath = "//div[@class='primeArt']")
	private WebElement primeShowContentBody;

	@FindBy(xpath = "//article[contains(@class,'desktop-view')]//h2//a[string-length(text())>50]")
	private List<WebElement> allETpremiumLinks;

	/////////////////////////////

	public List<WebElement> getAllETpremiumLinks() {
		return allETpremiumLinks;
	}

	public WebElement getPrimeShowContentBody() {
		return primeShowContentBody;
	}

}
//