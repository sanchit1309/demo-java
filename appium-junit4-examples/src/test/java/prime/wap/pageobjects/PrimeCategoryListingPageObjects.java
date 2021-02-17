package prime.wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class PrimeCategoryListingPageObjects {
	private WebDriver driver;
	
	public PrimeCategoryListingPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//h3[@class='section_heading']")
	private WebElement categoryTitle;
	
	@FindBy(xpath = "//p[contains(@class,'XNG')] | //p[contains(@class,'UAZ')] | //p[contains(@class,'d33')] | //h1[contains(@class,'b2q')] | //p[contains(@class,'Prv')]")
	private List<WebElement> storiesOnCategoryPage;
	
	@FindBy(xpath = "//span[@data-pagename='PrimeArticleList' and not(contains(@class,'3875')) and not(contains(@class,'Ref'))]")
	private List<WebElement> subcategoriesOnCategoryPage;
	
	@FindBy(xpath = "//span[contains(@class,'L57')]")
	private List<WebElement> minuteReadOnStories;
	
	@FindBy(xpath = "//div[contains(@class,'clearfix')]//span[@class='cSprite bookmark-icon flr' or @class='cSprite bookmark-icon flr saved']")
	private List<WebElement> saveIconsOnStories;
	
	@FindBy(xpath = "//a[@data-pagename='PrimeArticleList']")
	private List<WebElement> allArticles;
	
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
	
	public List<WebElement> getAllArticles() {
		return allArticles;
	}
	
	public WebElement getFilterForArticle(WebElement wb) {
		return wb.findElement(By.xpath("./p/span[@data-pagename='PrimeArticleList']"));
	}
	
	public WebElement getFilterByNumber(int number) {
		return driver.findElement(By.xpath("(//span[@data-pagename='PrimeArticleList' and not(contains(@class,'3875')) and not(contains(@class,'nYL57')) and not(contains(@class,'_1Ref_'))])["+number+"]"));
	}
}
