package web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TopicPageObjects {

	private WebDriver driver;

	public TopicPageObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//div[@id='categorywise']//a[text()='All']")
	private WebElement allTab;

	@FindBy(xpath = "//div[@id='categorywise']//a[text()='News']")
	private WebElement newsTab;

	@FindBy(xpath = "//div[@id='categorywise']//a[text()='Videos']")
	private WebElement videosTab;

	@FindBy(xpath = "//div[@id='categorywise']//a[text()='Photos']")
	private WebElement photosTab;

	@FindBy(xpath = "//ul[@class='data']//li[@class='active']//a[string-length()>0]")
	private List<WebElement> activeListUrl;

	@FindBy(xpath = "//section/div[contains(@class,'topicstry') and not(contains(@id,'categorywise'))]//a[string-length()>0 and contains(@href,'show')]")
	private List<WebElement> mainStoryUrl;

	@FindBy(xpath = "//li[@class='active' and @data-type = 'companies']//h3//a")
	private WebElement companyLink;

	@FindBy(xpath = "//li[@class='active' and @data-type = 'companies']//div[contains(@class,'liveData')]//b")
	private WebElement currentCompanyPrice;

	public WebElement getAllTab() {
		return allTab;
	}

	public WebElement getNewsTab() {
		return newsTab;
	}

	public WebElement getVideosTab() {
		return videosTab;
	}

	public WebElement getPhotosTab() {
		return photosTab;
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
		return driver.findElement(By.xpath("//div[@id='categorywise']//a[text()='" + sectionName + "']"));
	}

	public WebElement getCurrentCompanyPrice() {
		return currentCompanyPrice;
	}

}
