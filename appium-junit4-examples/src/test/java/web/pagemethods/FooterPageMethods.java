package web.pagemethods;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import web.pageobjects.FooterObjects;

public class FooterPageMethods {
	private FooterObjects footerObjects;

	public FooterPageMethods(WebDriver driver) {

		footerObjects = PageFactory.initElements(driver, FooterObjects.class);
	}
	
	public FooterObjects getFooterObjects() {
		return footerObjects;
	}
	public List<WebElement> getInCaseYouMissedItRightInks(){
		return footerObjects.getInCaseYouMissedItRightLinks();	
			
		}
	public List<WebElement> getInCaseYouMissedItLeftInks(){
		return footerObjects.getInCaseYouMissedItLeftLinks();	
			
		}
	
	
	public List<WebElement> getInCaseYouMissedIt(){
	return footerObjects.getInCaseYouMissedItLinks();	
		
	}

}
