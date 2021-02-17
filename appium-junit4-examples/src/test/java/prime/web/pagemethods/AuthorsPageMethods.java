package prime.web.pagemethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import prime.web.pageobjects.AuthorsPageObjects;
import web.pagemethods.WebBaseMethods;
import web.pageobjects.HeaderPageObjects;


public class AuthorsPageMethods {
	private WebDriver driver;
	private AuthorsPageObjects authorPageObjects;
	private HeaderPageObjects headerObjects;

	public AuthorsPageMethods(WebDriver driver) {
		this.driver = driver;
		headerObjects = PageFactory.initElements(driver, HeaderPageObjects.class);
		authorPageObjects = PageFactory.initElements(driver, AuthorsPageObjects.class);
	}
	

	public boolean isStoryPresentOnAuthorPage(String storyTitle)
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(authorPageObjects.getStoryByText(storyTitle), 10);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public String getAuthorName()
	{
		String name = "";
		try {
			name = WebBaseMethods.getTextUsingJSE(authorPageObjects.getAuthorName());
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return name;
	}
	
}
