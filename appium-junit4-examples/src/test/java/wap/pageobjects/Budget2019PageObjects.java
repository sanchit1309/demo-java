package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Budget2019PageObjects {

	
	
	@FindBy(xpath = "//a[string-length(@href)>0 and contains(@href,'.cms')]")
	private List<WebElement> allUrls;
	
///////////// ******************Getters******************//////////////

	public List<WebElement> getAllUrls() {
		return allUrls;
	}
}
