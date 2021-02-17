package wap.pageobjects;

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

	@FindBy(xpath = "//div[@id='topStoriesData']/ul/li/a")
	private List<WebElement> topHeadlines;

	///////////// ******************Getters******************//////////////

	public List<WebElement> getAllUrls() {
		return allUrls;
	}

	public List<WebElement> getTopHeadlines() {
		return topHeadlines;
	}

	public List<WebElement> getSpecificHeadlineLinks(String sectionName) {

		return driver.findElements(By.xpath("//h2/a[text()='" + sectionName + "']/../following-sibling::ul//li//a"));

	}
}
