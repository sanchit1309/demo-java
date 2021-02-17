package prime.web.pagemethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import prime.web.pageobjects.MyLibraryPageObjects;
import web.pagemethods.WebBaseMethods;
import web.pageobjects.HeaderPageObjects;
import web.pageobjects.LoginPageObjects;


public class MyLibraryPageMethods {
	private WebDriver driver;
	private MyLibraryPageObjects myLibraryPageObjects;
	private HeaderPageObjects headerObjects;
	private LoginPageObjects loginPageObjects;

	public MyLibraryPageMethods(WebDriver driver) {
		this.driver = driver;
		myLibraryPageObjects = PageFactory.initElements(driver, MyLibraryPageObjects.class);
		headerObjects = PageFactory.initElements(driver, HeaderPageObjects.class);
		loginPageObjects = PageFactory.initElements(driver, LoginPageObjects.class);
	}
	
	public boolean isSavedStoryPresentOnMyLibraryPage(String storyTitle)
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(myLibraryPageObjects.getSavedArticleStory(storyTitle), 3);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public boolean unsaveArticleOnMyLibraryPage(String storyTitle)
	{
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(myLibraryPageObjects.getUnsaveArticleStory(storyTitle));
			driver.navigate().refresh();
			flag = WebBaseMethods.isDisplayed(myLibraryPageObjects.getSavedArticleStory(storyTitle), 3);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
			flag = true;
		}
		return flag;
	}
	
}
