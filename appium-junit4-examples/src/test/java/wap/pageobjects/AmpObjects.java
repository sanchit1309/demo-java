package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AmpObjects {

	@FindBy(className = "topnewsText")
	private WebElement topNews;

	@FindBy(xpath = "//a[contains(text(),'View in App')]")
	private List<WebElement> viewApp;

	@FindBy(xpath = "//div[contains(@class,'artText')]/a[contains(@class,'blue')]")
	private List<WebElement> hyperLinks;

	@FindBy(xpath = "//section[@class='relatedArticle']//ul/li")
	private List<WebElement> relatedArticles;

	@FindBy(xpath = "//section[@class='readmore']/a")
	private List<WebElement> readMoreLink;

	@FindBy(xpath="//div[contains(@class,'etpWidget')]//a")
	private List<WebElement> etPrimeWidgetLinks; 
	

	
	///// Getters
	public WebElement getTopNews() {
		return topNews;
	}

	public List<WebElement> getViewApp() {
		return viewApp;
	}

	public List<WebElement> getHyperLinks() {
		return hyperLinks;
	}

	public List<WebElement> getRelatedArticles() {
		return relatedArticles;
	}

	public List<WebElement> getReadMoreLink() {
		return readMoreLink;
	}
	
	public List<WebElement> getETPrimeWidgetLinks(){
		return etPrimeWidgetLinks;
	}

}
