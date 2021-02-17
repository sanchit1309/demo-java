package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchObjects {
	
	@FindBy(xpath="//*[@class='topSearchBox']/input")
	private WebElement searchBar;
       
	@FindBy(xpath="//*[contains(@class,'searchIcon')]")
	private WebElement searchIcon;
	
	@FindBy(xpath="//*[contains(@class,'SearchBar flt hide')]/form/ul")
	private WebElement searchList;
	
	@FindBy(xpath ="//*[contains(@class,'SearchBar flt hide')]/form/ul/li")
	private List<WebElement> searchListings;
	
	@FindBy(xpath ="//*[contains(@class,'SearchBar flt hide')]/form/ul/li//a[not(contains(text(),'more'))]")
	private List<WebElement> searchResultUrl;
	
	@FindBy(xpath = "//*[contains(text(),'NEWS')]/../following-sibling::li//a[contains(@href,'articleshow')]")
	private List<WebElement> searchNewsResultUrl;
	////getters
	
	public WebElement getSearchBar() {
		return searchBar;
	}
	
	public WebElement getSearchIcon() {
		return searchIcon;
	}

	public WebElement getSearchList() {
		return searchList;
	}

	public List<WebElement> getSearchListings() {
		return searchListings;
	}

	public List<WebElement> getSearchResultUrl() {
		return searchResultUrl;
	}

	public List<WebElement> getSearchNewsResultUrl() {
		return searchNewsResultUrl;
	}
}
