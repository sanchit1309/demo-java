package wap.pagemethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import wap.pageobjects.PortfolioPageObjects;

public class PortfolioPageMethods {

	private PortfolioPageObjects portfolioPageObjects;
	private WebDriver driver;

	public PortfolioPageMethods(WebDriver driver) {
		this.driver = driver;
		portfolioPageObjects = PageFactory.initElements(driver, PortfolioPageObjects.class);
	}

	public boolean isPortfolioSectionDisplayed() {
		boolean flag = false;
		try {
			WaitUtil.explicitWaitByPresenceOfElement(driver, 20, portfolioPageObjects.getPortfolioHeading());

			if (portfolioPageObjects.getPortfolioHeading().isDisplayed()) {
				flag = true;
			}

			else
				flag = false;
		} catch (WebDriverException | NullPointerException e) {

		}
		return flag;
	}

}
