package prime.web.pagemethods;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import prime.web.pageobjects.CategoryListingPageObjects;
import prime.web.pageobjects.MyLibraryPageObjects;
import web.pagemethods.WebBaseMethods;
import web.pageobjects.HeaderPageObjects;


public class CategoryListingPageMethods {
	private WebDriver driver;
	private MyLibraryPageObjects myLibraryPageObjects;
	private HeaderPageObjects headerObjects;
	private CategoryListingPageObjects categoryListingPageObjects;

	public CategoryListingPageMethods(WebDriver driver) {
		this.driver = driver;
		myLibraryPageObjects = PageFactory.initElements(driver, MyLibraryPageObjects.class);
		headerObjects = PageFactory.initElements(driver, HeaderPageObjects.class);
		categoryListingPageObjects = PageFactory.initElements(driver, CategoryListingPageObjects.class);
	}
	
	public String getCategoryTitleText() {
		String title = "";
		try {
			title = WebBaseMethods.getTextUsingJSE(categoryListingPageObjects.getCategoryTitle());
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return title;
	}
	
	public int getTotalStoriesCount() {
		int count = 0;
		try {
			count = categoryListingPageObjects.getStoriesOnCategoryPage().size();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return count;
	}
	
	public int getTotalSubCategoriesCount() {
		int count = 0;
		try {
			count = categoryListingPageObjects.getSubcategoriesOnCategoryPage().size();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return count;
	}
	
	public int getTotalMinuteReadCount() {
		int count = 0;
		try {
			count = categoryListingPageObjects.getMinuteReadOnStories().size();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return count;
	}
	
	public int getTotalSaveIconCount() {
		int count = 0;
		try {
			count = categoryListingPageObjects.getSaveIconsOnStories().size();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return count;
	}
	
	public Map<String,String> getAllStoryHrefAndTitle() {
	//	boolean flag = false;
		Map<String, String> titleAndHrefs = new LinkedHashMap<String, String>();
		try {
			categoryListingPageObjects.getStoriesOnCategoryPage().forEach(element -> {
				titleAndHrefs.put(element.getAttribute("href"),WebBaseMethods.getTextUsingJSE(element));
			});
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return titleAndHrefs;
	}
	
	public Map<String, String> getAllSubCategoriesTitleAndHref() {
		Map<String, String> titleAndHrefs = new HashMap<String,String>();
		try {
			categoryListingPageObjects.getSubcategoriesOnCategoryPage().forEach(element -> {
				titleAndHrefs.put(WebBaseMethods.getTextUsingJSE(element),element.getAttribute("href"));
			});
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return titleAndHrefs;
	}
}
