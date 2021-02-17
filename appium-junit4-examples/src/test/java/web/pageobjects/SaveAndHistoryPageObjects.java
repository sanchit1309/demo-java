package web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SaveAndHistoryPageObjects {
	private WebDriver driver;

	public SaveAndHistoryPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "// div[@class='tabs']//span[text()='History']")
	private WebElement historyTab;

	@FindBy(xpath = "// div[contains(@class,'historyList')]//a[contains(@class,'heading')]")
	private List<WebElement> articlesUnderHistoryTab;

	@FindBy(xpath = "// div[contains(@class,'savedList')]//a[contains(@class,'heading')]")
	private List<WebElement> articlesUnderSavedTab;

	@FindBy(xpath = "//ul[@class='mylib_tabs mylib-nav']/li/a[text()='Saved']")
	private WebElement savedLink;

	@FindBy(xpath = "//span[@class='arrowB active']")
	private WebElement saveBtn;

	//////////////////////////////////////////////////

	public WebElement getSavedLink() {
		return savedLink;
	}

	public WebElement getSavedArticleStory(String storyTitle) {
		return driver
				.findElement(By.xpath("//div[@class='listBox savedList']//a[contains(text(),'" + storyTitle + "')]"));
	}

	public WebElement getUnsaveArticleStory(String storyTitle) {
		return driver.findElement(By.xpath("//div[@class='listBox savedList']//a[contains(text(),'" + storyTitle
				+ "')]/parent::div/following-sibling::div[@class='time_blk']/span[@class='cSprite bookmark-icon flr saved']"));
	}

	public WebElement getSaveBtn() {
		return saveBtn;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getHistoryTab() {
		return historyTab;
	}

	public List<WebElement> getArticlesUnderHistoryTab() {
		return articlesUnderHistoryTab;
	}

	public List<WebElement> getArticlesUnderSavedTab() {
		return articlesUnderSavedTab;
	}

}
