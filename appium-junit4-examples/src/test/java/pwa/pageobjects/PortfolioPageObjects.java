package pwa.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PortfolioPageObjects {
	
	@FindBy(xpath="//*[@class='fs12 bld flt pfSelct ng-scope']//a")
	private WebElement portfolioHeading;

	public WebElement getPortfolioHeading() {
		return portfolioHeading;
	}

}
