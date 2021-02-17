package web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PanacheObjects {

	@FindBy(xpath=".//*[@id='topblk-cont']/div/div//a")
	private List<WebElement> topStories;
	
	@FindBy(xpath=".//*[@class='sliderContent']//a")
	private List<WebElement> slideShowStories;
	
	@FindBy(xpath=".//*[@class='midcontent']//a[not(contains(@href,'clmbtech'))]")
	private List<WebElement> allStories;
	
	@FindBy(xpath=".//*[@class='bigImg']//a")
	private List<WebElement>  bigStories;

	@FindBy(xpath="//section[@class='btl_widget']")
	private WebElement betweenLinesSection;
	
	@FindBy(xpath="//section[@class='btl_widget']//div[@class='title']/h3/a")
	private WebElement btlSectionHeading;
	
	@FindBy(xpath="//section[@class='btl_widget']//*[contains(@class,'artTitle')]/a")
	private List<WebElement> btlArticleTitles;
	
	@FindBy(xpath="//*[contains(@class,'slide_btn')]/li[contains(@class,'circle') and not(contains(@class,'active'))]")
	private WebElement nextBTLSlider;
	
	///////////////////////////////////
	public List<WebElement> getTopStories() {
		return topStories;
	}

	public List<WebElement>  getSlideShowStories() {
		return slideShowStories;
	}

	public List<WebElement>  getAllStories() {
		return allStories;
	}

	public List<WebElement>  getBigStories() {
		return bigStories;
	}

	public WebElement getBetweenLinesSection() {
		return betweenLinesSection;
	}

	public WebElement getBtlSectionHeading() {
		return btlSectionHeading;
	}

	public List<WebElement> getBtlArticleTitles() {
		return btlArticleTitles;
	}

	public WebElement getNextBTLSlider() {
		return nextBTLSlider;
	}
	
	
}
