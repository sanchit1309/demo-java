package prime.wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

public class PrimeHomePageObjects {

	@FindBy(xpath = "//div[@data-sectiontype='Our team']//a[string-length()>0]")
	private List<WebElement> ourTeamAuthorLinks;

	@FindBy(xpath = "//h2[text()='Our Esteemed Readers']/../div/div//h4")
	private List<WebElement> ourEsteemedReadersSectionList;

	@FindBy(xpath = "//a[@data-sectiontype='Popular Categories']")
	private List<WebElement> popularCategoriesSectionLink;

	@FindBy(xpath = "//a[@data-sectiontype='Popular Categories']//p[string-length()>0]")
	private List<WebElement> popularCategoriesSectionLinkText;
	
	@FindBy(xpath = "//p[text()='Subscribe ']")
	private WebElement subscribeBtn;
	
	@FindBy(xpath = "//div[text()='Sign in']")
	private WebElement signInBtn;
		
	@FindBy(xpath = "//p[text()='Start Your Membership']")
	private WebElement startYourMembershipBtn;
	
	@FindBy(xpath = "//a[@data-sectiontype='Top News' and contains(@href,'/articleshow/')]")
	private List<WebElement> allTopStoriesPremiumArticles;
	
	@FindBy(xpath = "//a[@data-sectiontype='Top News' and contains(@href,'/primearticleshow/')]")
	private List<WebElement> allTopStoriesPrimeArticles;

	//////// ********getters*********///////////

	public List<WebElement> getOurTeamAuthorLinks() {
		return ourTeamAuthorLinks;
	}

	public List<WebElement> getOurEsteemedReadersSectionList() {
		return ourEsteemedReadersSectionList;
	}

	public List<WebElement> getPopularCategoriesSectionLink() {
		return popularCategoriesSectionLink;
	}

	public List<WebElement> getPopularCategoriesSectionLinkText() {
		return popularCategoriesSectionLinkText;
	}

	public List<WebElement> getSectionStoriesHref(String sectionName) {

		return driver.findElements(By.xpath("//li//a[@data-sectiontype='" + sectionName + "' and string-length()>0]"));

	}

	public WebElement getViewAllFromSectionLink(String sectionName) {

		return driver.findElement(
				By.xpath("//p[contains(text(),'View All')]/ancestor::a[@data-sectiontype='" + sectionName + "']"));
	}
	
	public WebElement getSubscribeBtn() {
		return subscribeBtn;
	}
	
	public WebElement getSignInBtn() {
		return signInBtn;
	}
	
	public WebElement getStartYourMembershipBtn() {
		return startYourMembershipBtn;
	}
	
	public WebElement getL1CategoryName(WebElement wb) {
		return wb.findElement(By.xpath("./p/span[@data-pagename='Prime HP']"));
	}
	
	public WebElement getMinuteReadForWebElement(WebElement wb) {
		return wb.findElement(By.xpath("./p/span[contains(@class,'217')]"));
	}

	public WebElement getViewAllCategoryName(String sectionName) {
		return driver.findElement(By.xpath("//a[@data-sectiontype='" + sectionName + "' and string-length()>0]//p[contains(text(),'View All')]"));
	}
	
	public WebElement getStoryTitleOfWebElement(WebElement wb) {
		return wb.findElement(By.xpath(".//*[contains(@class,'_3db2q')]"));
	}
	
	public List<WebElement> getAllTopStoriesPremiumArticles() {
		return allTopStoriesPremiumArticles;
	}
	
	public List<WebElement> getAllTopStoriesPrimeArticles() {
		return allTopStoriesPrimeArticles;
	}
	
	public WebElement getCategoryByNameFromHeader(String categoryName) {
		return driver.findElement(By.xpath("//nav[contains(@class,'Sm9')]//a[contains(text(),'"+categoryName+"')]"));
	}
	
	public WebElement getSelectedCategoryFromHeader(String categoryName) {
		return driver.findElement(By.xpath("//nav[contains(@class,'25S')]//a[contains(@class,'RgK') and contains(text(),'"+categoryName+"')]"));
	}
}
