package pwa.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

public class IndustryPageObjects {
	
//	@FindBy(xpath = "//*[@id=\"subMenu\"]/ul/li/a")
//	private List<WebElement> industrySubMenu;

	@FindBy(className = "headName")
	private WebElement mainHeading;

	private WebElement sectionHeading;


	private List<WebElement> sectionNews;

	////////// getters


	public WebElement getMainHeading() {
		return mainHeading;
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
