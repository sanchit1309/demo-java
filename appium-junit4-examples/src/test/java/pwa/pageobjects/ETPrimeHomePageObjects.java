package pwa.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ETPrimeHomePageObjects {
	private WebDriver driver;

	public ETPrimeHomePageObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//a[@data-sectiontype='Top News']")
	private List<WebElement> topStoriesBlock;

	////////////// **********Getter*********///////

	public List<WebElement> getTopStoriesBlock() {
		return topStoriesBlock;
	}

}
