package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static common.launchsetup.BaseTest.driver;

public class WealthPageObjects {

	public List<WebElement> getSubSectionLinks(String sectionName) {
		return driver.findElements(
				By.xpath("//h2//a[text()='" + sectionName + "']/../following-sibling::*//a[string-length()>0]"));
	}

	public List<WebElement> getSliderSubSectionLinks(String sectionName) {
		return driver.findElements(By.xpath("//*[@id = '" + sectionName + "']//ul//a[string-length()>0]"));
	}

	
	public List<WebElement> getEditionSubSectionLinks(String sectionName) {
		return driver.findElements(By.xpath(
				"//section[@id='" + sectionName + "']//div[contains(@class,'weekStory')]//a[string-length()>0]"));
	}

}
