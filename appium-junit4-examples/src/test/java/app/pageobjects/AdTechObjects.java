package app.pageobjects;


import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindByAllSet;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class AdTechObjects {
	
	@AndroidFindBy(id="com.et.reader.activities:id/llParentAdBottom")
	@iOSXCUITFindBy(iOSNsPredicate = "name='Banner_ad_bottom'")
	private MobileElement footerAd;
	
	@AndroidFindBy(id="com.et.reader.activities:id/InPagetopAd")
	@iOSXCUITFindBy(iOSNsPredicate = "name='Banner_ad_top'")
	private MobileElement topAd;
	
	@AndroidFindBy(id="com.et.reader.activities:id/llParentDfp")
	@iOSXCUITFindBy(iOSNsPredicate = "name='Acc_advertisement_big'")
	private MobileElement mrecAd;
			
	@AndroidFindByAllSet(value ={ @AndroidFindAll(value = { 
		@AndroidBy(id="com.et.reader.activities:id/nativeColomIcon"),
		@AndroidBy(id="com.et.reader.activities:id/tvadLabel")
	})})
	@iOSXCUITFindBy(iOSNsPredicate  = "name='Acc_adv_small_title'")
	private MobileElement colombiaAd;
	
	@AndroidFindBy(id="com.et.reader.activities:id/inPageBanner")
	private MobileElement headerAdArticle;
	
	@iOSXCUITFindBy(iOSNsPredicate  = "name='News_detail_ad_big'")
	@AndroidFindBy(id="com.et.reader.activities:id/llInStoryAd")
	private MobileElement mrecAdArticle;
	
	@iOSXCUITFindBy(iOSNsPredicate = "name='Acc_advertisement_big' && visible == 1")
	@AndroidFindBy(id="com.et.reader.activities:id/llParentDfp")
	private MobileElement mrecListing;

	//////////////////////////////////////////////////
	
	
	public MobileElement getFooterAd() {
		return footerAd;
	}

	public MobileElement getTopAd() {
		return topAd;
	}

	public MobileElement getMrecAd() {
		return mrecAd;
	}

	public MobileElement getColombiaAd() {
		return colombiaAd;
	}

	public MobileElement getHeaderAdArticle() {
		return headerAdArticle;
	}

	public MobileElement getMrecAdArticle() {
		return mrecAdArticle;
	}

	public MobileElement getMrecListing() {
		return mrecListing;
	}

	

}
