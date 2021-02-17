package pwa.pagemethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import pwa.pageobjects.PortfolioPageObjects;

public class PortfolioPageMethods {

	private PortfolioPageObjects portfolioPageObjects;
	WebDriver driver;
	
	public PortfolioPageMethods(WebDriver driver) {
		this.driver=driver;
		portfolioPageObjects = PageFactory.initElements(driver, PortfolioPageObjects.class);
	}
	

	public boolean isPortfolioSectionDisplayed() {
		boolean flag = false;
		try {
			WaitUtil.sleep(2000);
			if(portfolioPageObjects.getPortfolioHeading().isDisplayed())
				flag= true;
		}catch(Exception e) {
			e.printStackTrace();
		}
			return flag;
	}
	
}
