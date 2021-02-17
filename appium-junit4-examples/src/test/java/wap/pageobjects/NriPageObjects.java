package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NriPageObjects {

	@FindBy(xpath = "//a[@data-sectiontype='Trending terms' and contains(@href,'show')]")
	private List<WebElement> trendingInUSSectionLinks;

	@FindBy(xpath = "//li[text()='TOP TRENDING TERMS']/..//a[string-length()>0]")
	private List<WebElement> nriTopTrendingTermsLinks;

	public List<WebElement> getTrendingInUSSectionLinks() {

		return trendingInUSSectionLinks;

	}

	public List<WebElement> getNriTopTrendingTermsLinks() {
		return nriTopTrendingTermsLinks;
	}
}
