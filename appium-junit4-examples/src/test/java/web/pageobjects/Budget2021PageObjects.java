package web.pageobjects;

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
		sectionNews = driver.findElements(
				By.xpath("//div[@id= '" + sectionDiv + "' ]//a[(contains(@href,'show') or contains(@href,'podcast'))  and string-length()>0]"));
		return sectionNews;
	}

	public List<WebElement> getSectorInFocusSectionNews(String sectionDiv) {
		List<WebElement> sectionNews;
		sectionNews = new LinkedList<WebElement>();
		sectionNews = driver.findElements(By.xpath("//div[@id='sectorFocus']//h3//a[text()='" + sectionDiv
				+ "']/../following-sibling::div[@class='stry_cont']//a[string-length()>0]"));
		return sectionNews;
	}

	@FindBy(xpath = "//div[@id='pageContent']//a[string-length()>0]")
	private List<WebElement> allUrls;

	@FindBy(xpath = "//a[contains(@data-ga-onclick,'More From') or (@class='bold')]")
	private List<WebElement> moreFromSectionLinks;

	@FindBy(xpath = "//div[contains(@class,'seoWidget')]//a[string-length()>0]")
	private List<WebElement> budgetTrendingWidgetLinks;

	@FindBy(xpath = "//div[contains(@class,'budArchrive')]//a[string-length()>0]")
	private List<WebElement> budgetArchiveWidgetLinks;

	public List<WebElement> getBudgetArchiveWidgetLinks() {
		return budgetArchiveWidgetLinks;
	}

	public List<WebElement> getBudgetTrendingWidgetLinks() {
		return budgetTrendingWidgetLinks;
	}

	public List<WebElement> getMoreFromSectionLinks() {
		return moreFromSectionLinks;
	}

	public List<WebElement> getAllUrls() {
		return allUrls;
	}
}
