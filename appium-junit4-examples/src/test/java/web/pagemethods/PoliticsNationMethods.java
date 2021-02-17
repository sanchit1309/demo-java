package web.pagemethods;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.PoliticsNationObject;

public class PoliticsNationMethods {

	private WebDriver driver;
	private PoliticsNationObject politicsNationObject;

	public PoliticsNationMethods(WebDriver driver) {
		this.driver = driver;
		politicsNationObject = PageFactory.initElements(driver, PoliticsNationObject.class);
		WebBaseMethods.scrollToBottom();
	}
	
	public List<String> getTopNewsHref(){
		List<String> newsHref=WebBaseMethods.getListHrefUsingJSE(politicsNationObject.getFeaturedStories());
		return newsHref;
	}
	
	public List<String> getThumbImageHref(){
		List<String> thumbImageHrefList=WebBaseMethods.getListHrefUsingJSE(politicsNationObject.getThumbImages());
		return thumbImageHrefList;
	}
	public List<String> getVoicesHref(){
		List<String> voicesHrefList=WebBaseMethods.getListHrefUsingJSE(politicsNationObject.getVoices());
		return voicesHrefList;
	}
	public List<String> getVoicesDisplayed(){
		List<String> voicesDisplayed=WebBaseMethods.getListHrefUsingJSE(WebBaseMethods.getDisplayedItemFromList(politicsNationObject.getVoices()));
		return voicesDisplayed;
	}
	
	/**
	 * Out of the unselected list, the first radio is clicked
	 */
	public void clickVoicesFirst(){
	WebBaseMethods.clickElementUsingJSE(politicsNationObject.getVoicesNext().get(0));
	WaitUtil.sleep(2000);
	}
	
	public List<String> getNotToBeMissedHref(){
		List<String> notToBeMissedHref=WebBaseMethods.getListHrefUsingJSE(politicsNationObject.getNotToMissStories());
		return notToBeMissedHref;
	}
	public List<String> getNewsNotToMiss(){
		List<String> notToMissHref=WebBaseMethods.getListHrefUsingJSE(politicsNationObject.getNewsNotToMiss());
		return notToMissHref;
	}
	
	public List<String> getNewsList(){
		int i=0;
		while(i<3){
		WebBaseMethods.scrollToBottom();
		WaitUtil.sleep(3000);
		i++;
		}
		List<String> newsList=WebBaseMethods.getListHrefUsingJSE(politicsNationObject.getListArticles());
		return newsList;
	}
	public List<String> getNewsListStories(){
		return WebBaseMethods.getListTextUsingJSE(politicsNationObject.getListArticles());
	}
	public List<WebElement> getAllVideoSlides(){
		return politicsNationObject.getVideoSlides();
	}
	public List<WebElement> getAllAssemblyElectionsNews(){
		return politicsNationObject.getAssemblyElectionNews();
	}
	public WebElement getMoreAssemblyLink(){
		return politicsNationObject.geMoreAssemblyElections();
	}
}
