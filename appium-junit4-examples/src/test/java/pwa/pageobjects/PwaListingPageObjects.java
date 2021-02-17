package pwa.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PwaListingPageObjects {
	
	@FindBy(xpath = "//div[@class='flt100']//div[contains(@class,'card') and not(contains(@class,'colombia'))]/a")
	private List<WebElement> newsHeadings;

	@FindBy(xpath = "//*[@id='topStories']//div[not(contains (@class, 'colombiaAd colombiatracked reqDone') or contains(@id, 'div-clmb'))]/a[1]")
	private List<WebElement> topPageStories;
	
	@FindBy(xpath="//*[@id='topStories']/div[1]/a")
	private List<WebElement> homeTopStories;
	
	@FindBy(xpath = "//*[@id='topStories']/div[3]/div[2]")
	private WebElement moreNewsTab;
	
	@FindBy(xpath ="//*[@id='moreTopNews']/li/a")
	private List<WebElement> moreNewsList;
	
	public List<WebElement> getNewsHeadings() {
		return newsHeadings;
	}

	public List<WebElement> getTopPageStories() {
		return topPageStories;
	}

	public List<WebElement> getHomeTopStories() {
		return homeTopStories;
	}

	public WebElement getMoreNewsTab() {
		return moreNewsTab;
	}

	public List<WebElement> getMoreNewsList() {
		return moreNewsList;
	}
}
