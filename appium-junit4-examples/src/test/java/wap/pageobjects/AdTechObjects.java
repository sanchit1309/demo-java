package wap.pageobjects;

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
	
	@FindBy(xpath = "//section//div[contains(@id,'google_ads')]//iframe[@*[contains(.,'google_ads_iframe') and not(contains(style,'display'))]]")
	private List<WebElement> iframeIdAds;
	
	@FindBy(xpath = "//*[contains(@class,'ads') or contains(@class,'colombiaAd')]")
	private List<WebElement> divAdsPwa;
	
	@FindBy(xpath= "//*[contains(@class,'colombiaAd')]")
	private List<WebElement> colombiaAds;
	
	/////////////////////////////////

	public List<WebElement> getIframeIdAds() {
		return iframeIdAds;
	}

	public List<WebElement> getColombiaAdIframe() {
		return colombiaAdIframe;
	}

	public List<WebElement> getAdIframe() {
		return adIframe;
	}

	public List<WebElement> getDivIdAds() {
		return divIdAds;
	}

	public List<WebElement> getDivAdsPwa() {
		return divAdsPwa;
	}
	
	public List<WebElement> getColombiaAds() {
		return colombiaAds;
	}

}
