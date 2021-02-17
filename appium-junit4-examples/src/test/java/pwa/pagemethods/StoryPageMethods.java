package pwa.pagemethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pwa.pageobjects.StoryPageObjects;

public class StoryPageMethods {
	
	private StoryPageObjects storyPageObjects;
	
	public StoryPageMethods(WebDriver driver) {
		storyPageObjects = PageFactory.initElements(driver, StoryPageObjects.class);
	}
	
	public String getArticleDate() {
		try {
			return storyPageObjects.getArticleDate().getText();
		} catch (Exception e) {
			return null;
		}
	}

}
