package app.pageobjects;

import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class MarketDataPageObjects {
	
	@AndroidFindBy(id = "com.et.reader.activities:id/tv_no_internet")
	private List<MobileElement> noContentMsg;
	
	@AndroidFindBy(id = "com.et.reader.activities:id/lastTradedValue")
	@iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeStaticText' && name == 'Acc_current_value_label' && visible ==1")
    private MobileElement marketValue;
	
	@AndroidFindBy(id = "com.et.reader.activities:id/rates_value")
	@iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeStaticText' && name == 'Acc_title_search' && visible ==1")
    private List<MobileElement> forexRate;
	
	@AndroidFindBy(id = "com.et.reader.activities:id/ticker_tab_volume")
    private MobileElement comValue;
	
	@AndroidFindBy(id = "com.et.reader.activities:id/price")
    private List<MobileElement> priceValue;
	
	@AndroidFindBy(id = "com.et.reader.activities:id/introTextBottom")
	private List<MobileElement> introDialogChart;
	
	@AndroidFindBy(id = "com.et.reader.activities:id/fund_name")
    private List<MobileElement> MFEquity;
	
	/***** Getters start here *********/
	public MobileElement getMarketValue() {
		return marketValue;
	}

	public List<MobileElement> getForexRate() {
		return forexRate;
	}

	public MobileElement getComValue() {
		return comValue;
	}
	
	public List<MobileElement> getPriceValue() {
		return priceValue;
	}
	
	public List<MobileElement> getIntroDialogChart() {
		return introDialogChart;
	}

	public List<MobileElement> getMFEquity() {
		return MFEquity;
	}

	public List<MobileElement> getNoContentMsg() {
		return noContentMsg;
	}
}
