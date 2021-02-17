package web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MarketPageObjects {

	private WebDriver driver;

	public MarketPageObjects(WebDriver driver) {
		this.driver = driver;

	}

	public WebElement getMarketsTabName(String tabName) {

		return driver.findElement(By.xpath("//*[@data-content='" + tabName + "']/a"));

	}

	public WebElement getSubTabLink(String name) {
		return driver.findElement(By.xpath("//*[@id='markets']//span[contains(text(),'" + name + "')]"));

	}

	public List<WebElement> getStoriesLink(String idName) {
		return driver.findElements(By.xpath("//*[@id='" + idName
				+ "']//*[self::a[string-length(text())>0] or self::li[string-length(@data-href)>0]]"));
	}

	public WebElement getSectionLinkHeading(String name) {
		return driver.findElement(By.xpath("//*[@id='" + name + "']//*[contains(@class,'name')]"));

	}

	public List<WebElement> getSectionData(String name) {
		return driver.findElements(By.xpath(".//*[@id='" + name + "']//*[contains(@class,'name')]/following-sibling::*"));

	}

}
