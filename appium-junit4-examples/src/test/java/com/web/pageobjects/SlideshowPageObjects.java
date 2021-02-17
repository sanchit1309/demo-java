package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;;

public class SlideshowPageObjects {

	@FindBy(xpath = "//div[contains(@class,'currentSlide')]")
	private WebElement currentSlide;

	@FindBy(id = "playPauseTxtBtn")
	private WebElement playSlideMainButton;

	@FindBy(id = "autoPlayTT")
	private WebElement playSlideSideTopButton;

	@FindBy(id = "framePlayer")
	private WebElement playSlideRightTopButton;

	@FindBy(xpath = "//div[@id='nxtPrevAuto']//a[@title='Next']")
	private WebElement slideNextTopRightButton;

	@FindBy(xpath = "//div[@id='slideRight']//a[text()='Prev']")
	private WebElement slidePreviousMainButton;

	@FindBy(xpath = "//div[@id='nxtPrevAuto']//a")
	private WebElement slidePreviousTopRightButton;

	@FindBy(xpath = "//section[@id='slideImg']//a[@class='nextSImg']")
	private WebElement slideNextLeftButton;

	@FindBy(xpath = "//div[@id='slideRight']//a[text()='Next']")
	private WebElement slideNextMainButton;

	@FindBy(xpath = "//section[@id='slideImg']//a[@class='prevSImg']")
	private WebElement slidePreviousLeftButton;

	@FindBy(xpath = "//ul[@class='nSlides']//a[@class='next']")
	private WebElement slidePagingNextButton;

	@FindBy(xpath = "//ul[@class='nSlides']//a[@class='prev']")
	private WebElement slidePagingPreviousButton;

	@FindBy(xpath = "//a[@id='SlowSpeed']")
	private WebElement slideSlowSpeedButton;

	@FindBy(xpath = "//section[@id='nOfSlides']//a[text()='1']")
	private WebElement firstSlideSlowButton;

	@FindBy(xpath = "//a[@id='FastSpeed']")
	private WebElement slideFastSpeedButton;

	@FindBy(xpath = "//ul[@class='nSlides']/li")
	private List<WebElement> slidePagingElements;

	@FindBy(xpath = "//div[@id='paging']//a")
	private List<WebElement> slideshowListingPagination;

	@FindBy(xpath = "//section[@id='pageContent']//h3//a")
	private List<WebElement> slideshowListingHeading;

	@FindBy(xpath = "//main[@id='pageContent']//h1")
	private WebElement slideshowHeading;
	
	@FindBy(xpath = "(//section[@id='pageContent']//h3//a)[1]")
	private WebElement firstSlideshowLink;
	
	@FindBy(xpath = "//div[@class='slidesCount']")
	private List<WebElement> slideshowCount;
	

	@FindBy(xpath = "//div[@class =  'newsBandMiddle' ]")
	private WebElement slideshowNewsletter;
	
	
	@FindBy(xpath = "(//div[@class='slidesCount'])[3]")
	private WebElement thirdSlide;

	
	@FindBy(className="nextStory")
	private WebElement slideshowNext;
	
	

	

	//////////////////////////////////////////////
	public WebElement getCurrentSlide() {

		return currentSlide;
	}

	public WebElement getPlaySlideMainButton() {
		return playSlideMainButton;
	}

	public WebElement getPlaySlideSideTopButton() {
		return playSlideSideTopButton;
	}

	public WebElement getPlaySlideRightTopButton() {
		return playSlideRightTopButton;
	}

	public WebElement getSlideNextMainButton() {
		return slideNextMainButton;
	}

	public WebElement getslidePreviousMainButton() {
		return slidePreviousMainButton;
	}

	public WebElement getSlideNextTopRightButton() {
		return slideNextTopRightButton;
	}

	public WebElement getslidePreviousTopRightButton() {
		return slidePreviousTopRightButton;
	}

	public WebElement getSlideNextLeftButton() {
		return slideNextLeftButton;
	}

	public WebElement getSlidePreviousLeftButton() {
		return slidePreviousLeftButton;
	}

	public WebElement getSlidePagingNextButton() {
		return slidePagingNextButton;
	}

	public WebElement getSlidePagingPreviousButton() {
		return slidePagingPreviousButton;
	}

	public WebElement getSlideSlowSpeedButton() {
		return slideSlowSpeedButton;
	}

	public WebElement getFirstSlideSlowButton() {
		return firstSlideSlowButton;
	}

	public WebElement getSlideFastSpeedButton() {
		return slideFastSpeedButton;
	}

	public List<WebElement> getSlidePagingElements() {
		return slidePagingElements;
	}

	public List<WebElement> getSlideshowListingPagination() {
		return slideshowListingPagination;
	}

	public List<WebElement> getSlideshowListingHeading() {
		return slideshowListingHeading;
	}

	public WebElement getSlideshowHeading() {
		return slideshowHeading;
	}
	public WebElement getFirstSlideshowLink() {
		return firstSlideshowLink;
	}
	public List<WebElement> getSlideshowCount() {
		return slideshowCount;
	}
	public WebElement getSlideshowNewsletter() {
		return slideshowNewsletter;
	}
	public WebElement getThirdSlide() {
		return thirdSlide;
	}

	public WebElement getSlideshowNext() {
		return slideshowNext;
	}


}
