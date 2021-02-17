package prime.web.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyLibraryPageObjects {
	private WebDriver driver;
	
	public MyLibraryPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//ul[@class='mylib_tabs mylib-nav']/li/a[text()='Saved']")
	private WebElement savedLink;
	
	@FindBy(xpath = "//span[@class='arrowB active']")
	private WebElement saveBtn;
	
	/* ************** Getters *************** */
	
	
	public WebElement getSavedLink() {
		return savedLink;
	}

	public WebElement getSavedArticleStory(String storyTitle)
	{
		return driver.findElement(By.xpath("//div[@class='listBox savedList']//a[contains(text(),'"+storyTitle+"')]"));
	}
	
	public WebElement getUnsaveArticleStory(String storyTitle)
	{
		return driver.findElement(By.xpath("//div[@class='listBox savedList']//a[contains(text(),'"+storyTitle+"')]/parent::div/following-sibling::div[@class='time_blk']/span[@class='cSprite bookmark-icon flr saved']"));
	}
	
	public WebElement getSaveBtn()
	{
		return saveBtn;
	}
	
}
