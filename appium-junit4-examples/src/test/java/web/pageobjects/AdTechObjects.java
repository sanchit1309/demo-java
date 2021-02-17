package web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdTechObjects {

	@FindBy(xpath = "//iframe[@*[contains(.,'google_ads_iframe') and not(contains(.,'conversion'))  and not(contains(.,'_OP_'))]]")
	private List<WebElement> adIframe;

	@FindBy(xpath = "//*[(contains(text(),'Ad:') or contains(text(),'Sponsored by')) and not (contains(@*,'script')) ]")
	private List<WebElement> colombiaAdIframe;
	
	@FindBy(xpath = "//iframe[@*[contains(.,'google_ads_iframe') and not(contains(.,'conversion'))  and not(contains(.,'_OP_'))]]/..")
	private List<WebElement> divIdAds;

	@FindBy(xpath = "//div[contains(@class,'colombiaAd colombiatracked')]")
	private List<WebElement> fromAroundWeb;
	
	@FindBy(xpath = "//h2[text()='From Across the Web']")
	private List<WebElement> fromAcrossWeb;
	
	@FindBy(xpath= "//*[contains(@class,'colombiaAd')]")
	private List<WebElement> colombiaAds;
	
	@FindBy(xpath="//div[contains(@class,'colombiaAd') and contains(@class, 'colombiaPhoto')]")
	private List<WebElement> colombiaPhotoAds;
	
	/////////////////////////////////

	public List<WebElement> getColombiaAdIframe() {
		return colombiaAdIframe;
	}

	public List<WebElement> getAdIframe() {
		return adIframe;
	}

	public List<WebElement> getDivIdAds() {
		return divIdAds;
	}

	public List<WebElement> getFromAroundWeb() {
		return fromAroundWeb;
	}

	public List<WebElement> getFromAcrossWeb() {
		return fromAcrossWeb;
	}

	public List<WebElement> getColombiaAds() {
		return colombiaAds;
	}

}
