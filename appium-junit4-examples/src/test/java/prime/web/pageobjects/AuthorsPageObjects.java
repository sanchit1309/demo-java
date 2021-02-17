package prime.web.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthorsPageObjects {
	private WebDriver driver;
	
	public AuthorsPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//div[@class='biodata']/p[@class='name']")
	private WebElement authorName;

	
	/* ************** Getters *************** */
	
	
	public WebElement getStoryByText(String story) {
		return driver.findElement(By.xpath("//div[@class='listing_wrapper']//a[text()='"+story+"']"));
	}
	
	public WebElement getAuthorName() {
		return authorName;
	}

	
	
}
