package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

public class TopicPageObjects {

	@FindBy(xpath = "//*[@id='mainPage']/h2")
	private WebElement topicHeading;

	@FindBy(xpath = "//*[@id='topicsMenu']/ul/li/a")
	private List<WebElement> topicMenu;

	@FindBy(xpath = "//*[@id='topStories']/div[1]/a")
	private WebElement topStory;

	@FindBy(xpath = "//li[@class='stryData active']//a[string-length()>0]")
	private List<WebElement> activeListUrl;

	@FindBy(xpath = "//section[@id='topStories']//a[string-length()>0 and contains(@href,'show')]")
	private List<WebElement> mainStoryUrl;

	@FindBy(xpath = "//div[@id='stocksData']//div[@id='companyWidget']//div[contains(@class,'active')]//h3//a")
	private WebElement companyLink;

	@FindBy(xpath = "//div[@id='stocksData']//div[@id='companyWidget']//div[contains(@class,'active')]//div[@class='curPrice']")
	private WebElement currentCompanyPrice;

	public WebElement getTopicHeading() {
		return topicHeading;
	}

	public List<WebElement> getTopicMenu() {
		return topicMenu;
	}

	public List<WebElement> getMenuItemsList(String menu) {
		return driver.findElements(By.xpath("//*[@id='" + menu
				+ "Data']/ul/li[not(contains (@class, 'colombiaAd colombiatracked reqDone') or contains(@data-cb, 'adwidget'))]/a"));
	}

	public WebElement getErrorMessage(String menu) {
		return driver.findElement(By.xpath("//*[@id='" + menu + "Data']//div[contains(@class,'no-result')]"));
	}

	public WebElement getTopStory() {
		return topStory;
	}

	public List<WebElement> getActiveListUrl() {
		return activeListUrl;
	}

	public List<WebElement> getMainStoryUrl() {
		return mainStoryUrl;
	}

	public WebElement getCompanyLink() {
		return companyLink;
	}

	public WebElement getSectionTab(String sectionName) {
		return driver.findElement(By.xpath("//div[@class='scrollMenu']//li//a[text()='" + sectionName + "']"));
	}

	public WebElement getCurrentCompanyPrice() {
		return currentCompanyPrice;
	}
}
