package web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MutualFundsPageObjects {
	
	private WebDriver driver;

	public MutualFundsPageObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//section[@id='featuredNews']//section[contains(@class,'halfWidth')]//a[string-length()>0 and not(parent::h4[not(@itemscope)])]")
	private List<WebElement> topStoriesOnMutualFundsPage;

	@FindBy(xpath = "//div[@class='flt mTop10']//a[string-length()>0]")
	private List<WebElement> mutualFundTopKeywords;

	@FindBy(xpath = "//a[text()='Mutual Funds Learn  ']/../following-sibling::ul//li//a[string-length()>0]")
	private List<WebElement> mutualFundsLearnSectionLinks;

	@FindBy(xpath = "//a[text()='Mutual Funds News Analysis']/../following-sibling::ul//li//a[string-length()>0]")
	private List<WebElement> mutualFundsAnalysisSectionLinks;

	@FindBy(xpath = "//a[text()='Mutual Funds News']/../following-sibling::ul//li//a[string-length()>0]")
	private List<WebElement> mutualFundsNewsSectionLinks;

	@FindBy(xpath = "//div[@class='tabcontent']//li[contains(@class,'img-wrap') or contains(@class,'dplist')]//a[string-length()>0]")
	private List<WebElement> mutualFundNewsAndUpdates;

	/////////// Getters///////////

	public List<WebElement> getTopStoriesOnMutualFundsPage() {
		return topStoriesOnMutualFundsPage;
	}

	public List<WebElement> getMutualFundTopKeywords() {
		return mutualFundTopKeywords;
	}

	public List<WebElement> getMutualFundsLearnSectionLinks() {
		return mutualFundsLearnSectionLinks;
	}

	public List<WebElement> getMutualFundsAnalysisSectionLinks() {
		return mutualFundsAnalysisSectionLinks;
	}

	public List<WebElement> getMutualFundsNewsSectionLinks() {
		return mutualFundsNewsSectionLinks;
	}

	public List<WebElement> getMutualFundNewsAndUpdates() {
		return mutualFundNewsAndUpdates;
	}
	
	public WebElement getTheSectionHeadingLinks(String sectionName){
		return driver.findElement(By.xpath("//h2//a[text()='"+sectionName+"']"));
	}

}
