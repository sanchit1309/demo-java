package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FooterObjects {

	@FindBy(xpath="//*[contains(@class,'section-names')]//a")
	private List<WebElement> footerSectionLinks;

	public List<WebElement> getFooterSectionLinks() {
		return footerSectionLinks;
	}
}
