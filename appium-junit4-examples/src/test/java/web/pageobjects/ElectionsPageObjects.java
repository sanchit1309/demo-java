package web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ElectionsPageObjects {
	private WebDriver driver;

	public ElectionsPageObjects(WebDriver driver) {

		this.driver = driver;

	}

	@FindBy(xpath = "//a[string-length(@href)>0]")
	private List<WebElement> allUrls;

	@FindBy(xpath = "//div[contains(@class,'top-news')]//div//li//a[string-length(text())>0]")
	private List<WebElement> topHeadlines;
	
	
	/////// ******************GETTERS******************************/////////////////

	public List<WebElement> getTopHeadlines() {
		return topHeadlines;
	}

	public List<WebElement> getAllUrls() {
		return allUrls;
	}

	public List<WebElement> getSpecificHeadlineLinks(String sectionName) {

		return driver.findElements(
				By.xpath("//a[text()='" + sectionName + "']/../following-sibling::ul/li//a[contains(text(),' ')]"));

	}

}
