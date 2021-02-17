package com.web.pagemethods;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.SaveAndHistoryPageObjects;

public class SaveAndHistoryPageMethods {

	private WebDriver driver;
	private SaveAndHistoryPageObjects saveAndHistoryPageObjects;

	public SaveAndHistoryPageMethods(WebDriver driver) {
		this.driver = driver;
		saveAndHistoryPageObjects = PageFactory.initElements(driver, SaveAndHistoryPageObjects.class);

	}
	
	
	public boolean isSavedStoryPresentOnMyLibraryPage(String storyTitle)
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(saveAndHistoryPageObjects.getSavedArticleStory(storyTitle), 3);
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
			WebBaseMethods.clickElementUsingJSE(saveAndHistoryPageObjects.getUnsaveArticleStory(storyTitle));
			driver.navigate().refresh();
			flag = WebBaseMethods.isDisplayed(saveAndHistoryPageObjects.getSavedArticleStory(storyTitle), 3);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
			flag = true;
		}
		return flag;
	}
	
	public boolean isVisitedArticlePresentInTheHistory(String articleTitle){
		boolean flag = false;
		try{
		WebBaseMethods.clickElementUsingJSE(saveAndHistoryPageObjects.getHistoryTab());
		WaitUtil.explicitWaitByVisibilityOfAllElements(driver, 5, saveAndHistoryPageObjects.getArticlesUnderHistoryTab());
		List<String> articleUnderHistoryTitle = WebBaseMethods.getListTextUsingJSE(saveAndHistoryPageObjects.getArticlesUnderHistoryTab());	
		flag = articleUnderHistoryTitle.contains(articleTitle);
		
		}catch(Exception ee){
			ee.printStackTrace();
		}
		
		return flag;
	}
	
	public boolean isSavedArticlePresentUnderTheSave(String articleTitle){
		boolean flag = false;
		try{
		
		WaitUtil.explicitWaitByVisibilityOfAllElements(driver, 5, saveAndHistoryPageObjects.getArticlesUnderSavedTab());
		List<String> articleUnderHistoryTitle = WebBaseMethods.getListTextUsingJSE(saveAndHistoryPageObjects.getArticlesUnderSavedTab());	
		flag = articleUnderHistoryTitle.contains(articleTitle);
		
		}catch(Exception ee){
			ee.printStackTrace();
		}
		
		return flag;
	}
	
	public boolean isSavedRemovedFromTheSave(String articleTitle){
		boolean flag = false;
		try{
		
		WaitUtil.explicitWaitByVisibilityOfAllElements(driver, 5, saveAndHistoryPageObjects.getArticlesUnderSavedTab());
		List<String> articleUnderHistoryTitle = WebBaseMethods.getListTextUsingJSE(saveAndHistoryPageObjects.getArticlesUnderSavedTab());	
		flag = !(articleUnderHistoryTitle.contains(articleTitle));
		
		}catch(Exception ee){
			ee.printStackTrace();
		}
		
		return flag;
	}
	
	
}
