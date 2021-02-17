package wap.pageobjects;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

public class Budget2021PageObjects {

	public List<WebElement> getSectionNews(String sectionDiv) {
		List<WebElement> sectionNews;
		sectionNews = new LinkedList<WebElement>();
		sectionNews = driver.findElements(By.xpath("//*[contains(@data-ga-action,'" + sectionDiv
				+ "')]//a[string-length()>0 and  (contains(@href,'show') or contains(@href,'podcast'))]"));
		return sectionNews;
	}

	public List<WebElement> getSectorInFocusSectionNews(String sectionDiv) {
		List<WebElement> sectionNews;
		sectionNews = new LinkedList<WebElement>();
		sectionNews = driver.findElements(By.xpath("//section[@id='sector']//h3//a[text()='" + sectionDiv
				+ "']/../following-sibling::ul//li//a[string-length()>0]"));
		return sectionNews;
	}

	@FindBy(xpath = "//a[contains(@data-ga-onclick,'More ')]")
	private List<WebElement> moreFromSectionLinks;

	@FindBy(xpath = "//div[contains(@class,'seoWidget')]//a[string-length()>0]")
	private List<WebElement> budgetTrendingWidgetLinks;

	@FindBy(xpath = "//*[contains(@data-ga-action,'Budget Archives')]//a[string-length()>0]")
	private List<WebElement> budgetArchivesWidgetLinks;

	@FindBy(xpath = "//div//*//a[contains(@href,'/hindi/') and string-length()>0]")
	private List<WebElement> budgetInHindiLinks;

	@FindBy(xpath = "//section[@id='mainPage']//a[string-length()>0]")
	private List<WebElement> allUrls;

	public List<WebElement> getBudgetInHindiLinks() {
		return budgetInHindiLinks;
	}

	public List<WebElement> getBudgetTrendingWidgetLinks() {
		return budgetTrendingWidgetLinks;
	}

	public List<WebElement> getMoreFromSectionLinks() {
		return moreFromSectionLinks;
	}

	public List<WebElement> getBudgetArchivesWidgetLinks() {
		return budgetArchivesWidgetLinks;
	}

	public List<WebElement> getAllUrls() {
		return allUrls;
	}

}
