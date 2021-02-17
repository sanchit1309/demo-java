package prime.wap.pagemethods;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import prime.wap.pageobjects.PrimeCategoryListingPageObjects;
import web.pagemethods.WebBaseMethods;


public class CategoryListingPageMethods {
	private WebDriver driver;
	private PrimeCategoryListingPageObjects categoryListingPageObjects;

	public CategoryListingPageMethods(WebDriver driver) {
		this.driver = driver;
		categoryListingPageObjects = PageFactory.initElements(driver, PrimeCategoryListingPageObjects.class);
	}

	public String getCategoryTitleText() {
		String title = "";
		try {
			title = categoryListingPageObjects.getCategoryTitle().getText();
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
			categoryListingPageObjects.getAllArticles().forEach(element -> {
				titleAndHrefs.put(element.getAttribute("href"),element.getText());
			});
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return titleAndHrefs;
	}

	public Map<String, WebElement> getAllSubCategoriesTitleAndHref() {
		Map<String, WebElement> titleAndElement = new HashMap<String,WebElement>();
		WaitUtil.sleep(3000);
		try {
			categoryListingPageObjects.getSubcategoriesOnCategoryPage().forEach(element -> {
				titleAndElement.put(element.getText(),element);
			});
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return titleAndElement;
	}

	public Map<String,String> getAllFiltersHrefAndTitle() {
		//	boolean flag = false;
		Map<String, String> titleAndHrefs = new LinkedHashMap<String, String>();
		try {
			categoryListingPageObjects.getAllArticles().forEach(element -> {
				titleAndHrefs.put(categoryListingPageObjects.getFilterForArticle(element).getText(),element.getAttribute("href"));
			});
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return titleAndHrefs;
	}

	public boolean clickFilterByNumber(int number) {
		boolean flag = false;
		try {
			WebBaseMethods.isDisplayed(categoryListingPageObjects.getFilterByNumber(number),15);
			WebBaseMethods.clickElementUsingJSE(categoryListingPageObjects.getFilterByNumber(number));
			flag = true;
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public String getFilterNameByNumber(int number) {
		String txt = "";
		try {
			WebBaseMethods.isDisplayed(categoryListingPageObjects.getFilterByNumber(number),15);
			txt= categoryListingPageObjects.getFilterByNumber(number).getText();
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return txt;
	}
}
