package pwa.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

public class NewsPageObjects {


	@FindBy(xpath = "//*[@id='subMenu']/ul/li")
	private List<WebElement> riseSubMenu;

	private WebElement sectionHeading;


	private List<WebElement> sectionNews;

	///////////////////////////

	public List<WebElement> getRiseSubMenu() {
		return riseSubMenu;
	}

	public WebElement getSectionHeading(String sectionDiv) {
		sectionHeading = driver.findElement(By.xpath("//*[@id='subSections']//a[text()='" + sectionDiv + "']"));
		return sectionHeading;
	}

	public List<WebElement> getSectionNews(String sectionDiv) {
		sectionNews = driver.findElements(By.xpath("//*[@id='subSections']//a[contains(text(),'"+ sectionDiv+"')]/ancestor::div/div[contains(@class,'card')]/a"));
		return sectionNews;
	}
}
