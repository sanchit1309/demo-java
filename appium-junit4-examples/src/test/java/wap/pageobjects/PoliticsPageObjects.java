package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static common.launchsetup.BaseTest.driver;

public class PoliticsPageObjects {
	
	private List<WebElement> imageLink;
	
	private List<WebElement> sectionNews;
	
	public List<WebElement> getImageLink(String sectionDiv) {
		imageLink = driver.findElements(By.xpath("//*[@id='subSections']/div["+sectionDiv+"]/div/a"));
		return imageLink;
	}

	public List<WebElement> getSectionNews() {
		sectionNews = driver.findElements(By.xpath("//*[@id='subSections']//ul/li/a"));
		return sectionNews;
	}

}
