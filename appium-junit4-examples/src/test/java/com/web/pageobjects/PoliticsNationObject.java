package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class PoliticsNationObject {
	
	@FindAll({@FindBy(css=".featured>h2>a"),
		@FindBy(css=".top-news>ul>li>a"),
		@FindBy(css=".featured>h3>a")})
	private List<WebElement> featuredStories;
	
	@FindBy(css=".bThumb>ul>li>a:nth-child(2)")
	private List<WebElement> thumbImages;
	
	@FindBy(css=".wrap-pair>a")
	private List<WebElement> voices;
	
	@FindBy(css="#voicesSlNo li:not(.active)")
	private List<WebElement> voicesNext;
	
	@FindBy(xpath="//*[@id='mostPopular']//div[contains(@class,'list')and "
			+ "(not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display')))]/a[not(contains(@href,'clmbtech')) and not(contains(@href,'apsalar'))]")
	private List<WebElement> notToMissStories;
	
	@FindBy(xpath=".//*[@id='sliderSlidesVideos']//a[1]")
	private List<WebElement> newsNotToMiss;
	
	@FindBy(css=".botplData.flt>h3>a")
	private List<WebElement> listArticles;
	
	@FindBy(xpath="//div[@class='vedioSec']//ul/li//h4/a")
	private List<WebElement> videoSlides;
	
	@FindBy(xpath="//h2/a[contains(text(),'Assembly Elections')]/../../div[@class='list']/a[2]")
	private List<WebElement> assemblyElectionNews;
	
	@FindBy(xpath="//h2/a[contains(text(),'Assembly Elections')]/../../div[contains(@class,'list')]/a[contains(text(),'More from')]")
	private WebElement moreAssemblyElections; 
	
	//////////////////////////

	public List<WebElement> getFeaturedStories() {
		return featuredStories;
	}

	public List<WebElement> getThumbImages() {
		return thumbImages;
	}

	public List<WebElement> getVoices() {
		return voices;
	}

	public List<WebElement> getNotToMissStories() {
		return notToMissStories;
	}

	public List<WebElement> getListArticles() {
		return listArticles;
	}

	public List<WebElement> getVoicesNext() {
		return voicesNext;
	}

	public List<WebElement> getNewsNotToMiss() {
		return newsNotToMiss;
	}
	
	public List<WebElement> getVideoSlides(){
		return videoSlides;
	}

	public List<WebElement> getAssemblyElectionNews() {
		return assemblyElectionNews;
	}

	public WebElement geMoreAssemblyElections() {
		return moreAssemblyElections;
	}
	
	

}
