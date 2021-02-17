package web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchObjects {
	
	@FindBy(xpath = "//div[@id='topSearch']//input")
	private WebElement searchBar;

	// @FindBy(xpath = "//aside[@id='searchBar']/div/ul/li")
	@FindBy(xpath = "//div[@id='topSearch']/div/ul/li")
	private List<WebElement> searchResult;

	// @FindBy(xpath =
	// "//aside[@id='searchBar']/div/ul/li//a[not(contains(text(),'more'))]")
	@FindBy(xpath = "//div[@id='topSearch']/div/ul/li//a[not(contains(text(),'more'))]")
	private List<WebElement> searchResultUrl;

	// @FindBy(xpath = "//*[@id='searchBar']/div/ul")
	@FindBy(xpath = "//*[@id='topSearch']/div/ul")
	private WebElement searchList;

	@FindBy(xpath = "//*[contains(text(),'NEWS')]/../following-sibling::li//a[contains(@href,'/articleshow')]")
	private List<WebElement> searchNewsResultUrl;

	@FindBy(xpath = "//*[contains(text(),'MUTUAL FUNDS')]/../following-sibling::li//a[contains(@href,'scheme')]")
	private List<WebElement> searchMfResultUrl;

	@FindBy(xpath = "//*[contains(text(),'BUSINESS LISTINGS')]/../following-sibling::li//a[contains(@href,'sme')]")
	private List<WebElement> searchSmeResultUrl;

	@FindBy(xpath = "//*[contains(text(),'DEFINITIONS')]/../following-sibling::li//a[contains(@href,'definition')]")
	private List<WebElement> searchDefinitionResultUrl;

	@FindBy(xpath = "//*[contains(text(),'PRIME STORIES')]/../following-sibling::li//a[contains(@href,'/primearticleshow')]")
	private List<WebElement> searchPrimeStoriesResultUrl;

	// @FindBy(xpath = "//*[@id='searchBar']/div/ul/li")
	@FindBy(xpath = "//*[@id='topSearch']/div/ul/li")
	private List<WebElement> searchListings;

	////////////////////////////////////////////////

	public List<WebElement> getSearchListings() {
		return searchListings;
	}

	public List<WebElement> getSearchNewsResultUrl() {
		return searchNewsResultUrl;
	}

	public List<WebElement> getSearchResult() {
		return searchResult;
	}

	public List<WebElement> getSearchResultUrl() {
		return searchResultUrl;
	}

	public WebElement getSearchBar() {
		return searchBar;
	}

	public WebElement getSearchList() {
		return searchList;
	}

	public List<WebElement> getSearchPrimeStoriesResultUrl() {
		return searchPrimeStoriesResultUrl;
	}
}
