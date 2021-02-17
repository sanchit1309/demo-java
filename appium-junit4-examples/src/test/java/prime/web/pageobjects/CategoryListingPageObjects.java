package prime.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class CategoryListingPageObjects {
	private WebDriver driver;
	
	public CategoryListingPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//h3[@class='section_heading']")
	private WebElement categoryTitle;
	
	@FindBy(xpath = "//div[contains(@class,'clearfix')]//a[@class='heading font_faus']")
	private List<WebElement> storiesOnCategoryPage;
	
	@FindBy(xpath = "//div[contains(@class,'clearfix')]//a[@class='ctgry']")
	private List<WebElement> subcategoriesOnCategoryPage;
	
	@FindBy(xpath = "//div[contains(@class,'clearfix')]//span[@class='read_time']")
	private List<WebElement> minuteReadOnStories;
	
	@FindBy(xpath = "//div[contains(@class,'clearfix')]//span[@class='cSprite bookmark-icon flr' or @class='cSprite bookmark-icon flr saved']")
	private List<WebElement> saveIconsOnStories;
	/* ************** Getters *************** */
	
	
	public WebElement getCategoryTitle() {
		return categoryTitle;
	}
	
	public List<WebElement> getStoriesOnCategoryPage() {
		return storiesOnCategoryPage;
	}
	
	public List<WebElement> getSubcategoriesOnCategoryPage() {
		return subcategoriesOnCategoryPage;
	}
	
	public List<WebElement> getMinuteReadOnStories() {
		return minuteReadOnStories;
	}
	
	public List<WebElement> getSaveIconsOnStories() {
		return saveIconsOnStories;
	}
	
}
