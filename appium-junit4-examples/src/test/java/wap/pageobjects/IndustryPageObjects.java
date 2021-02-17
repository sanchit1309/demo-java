package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

public class IndustryPageObjects {

	@FindBy(xpath = "//*[@id=\"subMenu\"]/ul/li/a")
	private List<WebElement> industrySubMenu;

	@FindBy(className = "headName")
	private WebElement mainHeading;

	private WebElement sectionHeading;

	private List<WebElement> imageLink;

	private List<WebElement> sectionNews;

	////////// getters

	public List<WebElement> getIndustrySubMenu() {
		return industrySubMenu;
	}

	public WebElement getMainHeading() {
		return mainHeading;
	}

	public WebElement getSectionHeading(String sectionDiv) {
		sectionHeading = driver.findElement(By.xpath("//*[@id='subSections']//*[contains(text(),'" + sectionDiv + "')]/ancestor::div//h2/a"));
		return sectionHeading;
	}

	public List<WebElement> getSectionNews(String sectionDiv) {
		sectionNews = driver.findElements(By.xpath("//*[@id='subSections']//*[contains(text(),'" + sectionDiv
				+ "')]/ancestor::div//a//ancestor::*[contains(@class,'Image') or contains(@class,'newsList') ]//a"));
		return sectionNews;
	}

	public List<WebElement> getImageLink(String sectionDiv) {
		imageLink = driver.findElements(By.xpath("//*[@id='subSections']/div[" + sectionDiv + "]/div/a"));
		return imageLink;

	}

}
