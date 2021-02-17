package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static common.launchsetup.BaseTest.driver;

public class MutualFundPageObjects {

	public List<WebElement> getSubSectionLinks(String sectionName) {
		return driver.findElements(
				By.xpath("//h2//a[text()='" + sectionName + "']/../following-sibling::*//a[string-length()>0]"));
	}
	
	public List<WebElement> getTopMutualFundsSchemeLinks(String sectionName) {
		return driver.findElements(
				By.xpath("//div[contains(@class,'"+sectionName+"')]//a[contains(@pg,'topMF')]"));
	}
	
	public List<WebElement> getETFSchemeLinks(String sectionName) {
		return driver.findElements(
				By.xpath("//div[contains(@class,'"+sectionName+" scheme')]//div[@class='fx_row']/a[string-length()>0]"));
	}
	
	public WebElement getETFSubSectionNseBseTab(String sectionName, String tabName){
	
		return driver.findElement(By.xpath("//div[contains(@class,'"+sectionName+" scheme')]//span[text()='"+tabName+"']"));
	}
	
	
	
	

}
