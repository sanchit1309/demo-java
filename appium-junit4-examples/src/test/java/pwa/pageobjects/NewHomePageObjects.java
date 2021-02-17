package pwa.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewHomePageObjects {
	private WebDriver driver;

	public NewHomePageObjects(WebDriver driver) {
		this.driver = driver;

	}
	
	@FindBy(id = "hamberIcon")
	private WebElement menuIcon;

	@FindBy(xpath = "//a[@data-sectiontype='Top News']")
	private List<WebElement> topStoriesListETHomepage;

	@FindBy(xpath = "//span[contains(@class,'prime_plus_icon')]/parent::p/parent::a")
	private List<WebElement> etPrimeWidgetStoriesList;

	@FindBy(xpath = "//div[@id='topStories']//ul//li//a[@target='_blank' and contains(@href,'articleshow') and not(@data-conttype)]")
	private List<WebElement> topStoriesFreeArticleListETHomepage;

	@FindBy(xpath = "//a[@data-name='ETPrime']")
	private WebElement etPrimeNavBarLink;

	/////// **********GETTERS******//////////////////////

	
	public WebElement getMenuIcon() {
		return menuIcon;
	}
	
	public List<WebElement> getTopStoriesListETHomepage() {
		return topStoriesListETHomepage;
	}

	public List<WebElement> getEtPrimeWidgetStoriesList() {
		return etPrimeWidgetStoriesList;
	}

	public List<WebElement> getTopStoriesFreeArticleListETHomepage() {
		return topStoriesFreeArticleListETHomepage;
	}

	public WebElement getEtPrimeNavBarLink() {
		return etPrimeNavBarLink;
	}

}
