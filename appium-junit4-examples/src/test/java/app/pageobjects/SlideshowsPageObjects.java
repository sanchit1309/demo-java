package app.pageobjects;

import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * 
 * This class contains Slideshows page elements
 *
 */

public class SlideshowsPageObjects {

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").index(1)")
	private MobileElement slideshowHeader;

	@AndroidFindBy(id = "com.et.reader.activities:id/headline")
	@iOSXCUITFindBy(iOSNsPredicate = "name='SlideShow_detail_title' && visible==1")
	private List<MobileElement> slideshowArticle;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.et.reader.activities:id/headline\")")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='News_Headline' && visible ==1")
	private List<MobileElement> listPageSlideShows;
	
	@AndroidFindBy(xpath = "//*[contains(@class,'android.support.v7.app.ActionBar$Tab') and contains(@class,'android.widget.TextView')]")
	private List<MobileElement> slideshowTabs;

	@AndroidFindBy(id = "com.et.reader.activities:id/slidePosition")
	private MobileElement slide;

	@iOSXCUITFindBy(iOSNsPredicate="name='NEXT SLIDESHOW' && visible==1")
	@AndroidFindBy(id = "com.et.reader.activities:id/tv_next_header")
	private MobileElement nextSlideshow;

	@iOSXCUITFindBy(iOSNsPredicate="name endswith '_number' && visible ==1")
	@AndroidFindBy(id = "com.et.reader.activities:id/slidePosition")
	private  List<MobileElement> slideNumber;

	@iOSXCUITFindBy(iOSNsPredicate="name='NEXT SLIDESHOW' && visible==1")

	@AndroidFindBy(id = "com.et.reader.activities:id/tv_next_header")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='NEXT SLIDESHOW' && visible==1")
	private MobileElement nextSlideShowStrip;

	@AndroidFindBy(xpath = "//*[contains(@resource-id,'headline')]")
	@iOSXCUITFindBy(iOSNsPredicate = "name matches '\\d+_\\d+_title' && visible==1")
	private MobileElement slideHeadline;

	@iOSXCUITFindBy(iOSNsPredicate = "name contains '_description' && visible==1")
	private MobileElement slideDetail;
	

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.et.reader.activities:id/headline\")")
	@iOSXCUITFindBy(iOSNsPredicate="name =='Acc_slideshow_title' && visible==1")
	private List<MobileElement>slideShowListPageIdentifier;



	public MobileElement getSlideHeadline() {
		return slideHeadline;
	}

	public List<MobileElement> getSlideshowArticle() {
		return slideshowArticle;
	}

	public List<MobileElement> getSlideshowTabs() {
		return slideshowTabs;
	}

	public List<MobileElement> getSlideNumber() {
		return slideNumber;
	}

	public MobileElement getNextSlideShowStrip() {
		return nextSlideShowStrip;
	}

	public MobileElement getSlideshowHeader() {
		return slideshowHeader;
	}

	public List<MobileElement> getSlideshowArticles() {
		return slideshowArticle;
	}

	public List<MobileElement> getSlideshowTab() {
		return slideshowTabs;
	}

	public MobileElement getSlide() {
		try {
			return slide;
		} catch (Exception e) {
			return null;
		}
	}

	public MobileElement getNextSlideshow() {
		return nextSlideshow;
	}

	public MobileElement getSlideDetail() {
		return slideDetail;
	}

	public List<MobileElement> getSlideShowListPageIdentifier() {
		return slideShowListPageIdentifier;
	}

	public void setSlideShowListPageIdentifier(List<MobileElement> slideShowListPageIdentifier) {
		this.slideShowListPageIdentifier = slideShowListPageIdentifier;
	}
	public List<MobileElement> getListPageSlideShows(){
		return listPageSlideShows;
	}

}
