package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

public class NewsPageObjects {
	
	@FindBy(xpath="//*[@id='subMenu']/ul/li/a")
	private List<WebElement> newsSubMenu;
	
	private WebElement sectionHeading;
	
	private List<WebElement> imageLink;
	
	private List<WebElement> sectionNews;

	//////getters
	
	public List<WebElement> getNewsSubMenu() {
		return newsSubMenu;
	}

	public WebElement getSectionHeading(String sectionName) {
		try{
		sectionHeading = driver.findElement(By.xpath("//section[@id='subSections']/section//a[contains(text(),'"+sectionName+"')]"));
		}catch(NoSuchElementException e){
			
		}
		return sectionHeading;
	}

	public List<WebElement> getImageLink(String sectionName) {
		imageLink = driver.findElements(By.xpath("//section[@id='subSections']/section//a[contains(text(),'"+sectionName+"')]/../../div/a"));
		return imageLink;
	}

	public List<WebElement> getSectionNews(String sectionName) {
		sectionNews = driver.findElements(By.xpath("//section[@id='subSections']/section//a[contains(text(),'"+sectionName+"')]/../../ul/li/a"));
		return sectionNews;
	}

}

