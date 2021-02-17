package app.pageobjects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MarketsPageObjects {

	@AndroidFindBy(xpath = "//*[@resource-id='com.et.reader.activities:id/name' and @text='SENSEX']/parent::android.widget.LinearLayout//*[@resource-id='com.et.reader.activities:id/currentValue']")
	private MobileElement sensexCurrentVal;

	@AndroidFindBy(xpath = "//*[@resource-id='com.et.reader.activities:id/name' and @text='NIFTY 50']/parent::android.widget.LinearLayout//*[@resource-id='com.et.reader.activities:id/currentValue']")
	private MobileElement nifty50;

	@AndroidFindBy(xpath = "//*[@resource-id='com.et.reader.activities:id/name' and @text='GOLD']/parent::android.widget.LinearLayout//*[@resource-id='com.et.reader.activities:id/currentValue']")
	private MobileElement gold;

	@AndroidFindBy(xpath = "//*[@resource-id='com.et.reader.activities:id/name' and @text='USD/INR']/parent::android.widget.LinearLayout//*[@resource-id='com.et.reader.activities:id/currentValue']")
	private MobileElement USDINR;
	
	@AndroidFindBy(className = "android.webkit.WebView")
	private MobileElement wapView;

	/***** Getters start here *********/
	public MobileElement getSensexCurrentVal() {
		return sensexCurrentVal;
	}

	public MobileElement getNifty50() {
		return nifty50;
	}

	public MobileElement getGold() {
		return gold;
	}

	public MobileElement getUSDINR() {
		return USDINR;
	}

	public MobileElement getWapView() {
		return wapView;
	}

}
