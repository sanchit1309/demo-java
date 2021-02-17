package pwa.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PoliticsPageObjects {
	
	@FindBy(xpath="//*[@id='subSections']//div[contains(@class,'card')]/a")
	private List<WebElement> sectionNews;
	

	public List<WebElement> getSectionNews() {
		return sectionNews;
	}

}
