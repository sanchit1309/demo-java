package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

public class LiveBlogPageObjects {
	
		
	@FindBy(xpath="(//ul[@class='newsList']//li/a[2])[1]")
	private WebElement liveBlogList;
	
	@FindBy(xpath="//h1[@class='lbHeadline']")
	private WebElement liveBlogHeadline;
	
	@FindBy(xpath="//*[@class='byLine']/time")
	private WebElement liveBlogTime;
	
	@FindBy(xpath="//div[contains(@class,'blogDesc')]")
	private WebElement liveBlogDesc;
	
	@FindBy(xpath="//*[@class='lbCont']")
	private List<WebElement> liveBlogSnippetList;
	
	@FindBy(xpath="//div[@id='updateInfo']")
	private WebElement updateBanner;
	
	@FindBy(xpath="//*[@class='lbCont']//span[@class='date']")
	private WebElement snippetTime;
	
	
	
	////////////////////////////////////////////GETTERS////////////////////////////////////////////////////
	
	public WebElement getLiveBlogList() {
		return liveBlogList;
	}


	public WebElement getLiveBlogHeadline() {
		return liveBlogHeadline;
	}


	public WebElement getLiveBlogTime() {
		return liveBlogTime;
	}


	public WebElement getLiveBlogDesc() {
		return liveBlogDesc;
	}


	public List<WebElement> getLiveBlogSnippetList() {
		return liveBlogSnippetList;
	}


	public WebElement getUpdateBanner() {
		return updateBanner;
	}



	
	public WebElement getSnippetTime(String liveBlogId) {
		snippetTime = driver.findElement(By.xpath("//*[contains(@id,'"+liveBlogId+"')]//span[contains(@class,'date')]"));
		return snippetTime;
	}
	
	

}
