package prime.wap.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PrimeHeaderPageObjects {

	@FindBy(xpath = "//nav[contains(@itemtype,'SiteNavigationElement')]//a")
	private List<WebElement> allHeaderLinks;


	//////// ********getters*********///////////

	public List<WebElement> getAllHeaderLinks() {
		return allHeaderLinks;
	}

}
