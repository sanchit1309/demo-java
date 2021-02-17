package app.pageobjects;

import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SearchPageObjects {
	
	@iOSXCUITFindBy(iOSNsPredicate="type == 'XCUIElementTypeTextField'")
	@AndroidFindBy(id ="com.et.reader.activities:id/search_src_text")
	private MobileElement searchBar;
	
	@iOSXCUITFindBy(accessibility="Acc_title_search")
	@AndroidFindBy (id = "com.et.reader.activities:id/search_news_text_tv")
	private List<MobileElement> searchResult;
	
	@iOSXCUITFindBy(iOSNsPredicate="type == 'XCUIElementTypeStaticText' && name='Acc_title_search_header'")
	@AndroidFindBy (className = "android.widget.TextView")
	private List<MobileElement> searchHeaders;
	
	////Stock Page/////
	@AndroidFindBy (id = "com.et.reader.activities:id/companyName")
	private List<MobileElement> companyName;
	
	
	/////Commodity Page////
	@AndroidFindBy (xpath = "//android.widget.TextView)[1]" )
	private List<MobileElement> commodityName;
	
	
	/////MF Page////
	@AndroidFindBy (id = "com.et.reader.activities:id/mutual_fund_name")
	private List<MobileElement> MfName;	
	
	
	
	///// ALL TAB///
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='All']")
	@AndroidFindBy(xpath ="//android.widget.TextView[@text='ALL']")
	private MobileElement allTab;
	
	
	///// ET PRIME TAB///
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='ET Prime']")
	@AndroidFindBy(xpath ="//android.widget.TextView[@text='ET PRIME']")
	private MobileElement etPrimeTab;
	
	
	////NEWS TAB////
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='News']")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='NEWS']")	
	private MobileElement newstab;
	
	
	////NEWS ITEMS////
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Acc_title_search' && visible ==1")
	@AndroidFindBy(id = "com.et.reader.activities:id/search_news_text")
	private List<MobileElement> newsItems;
	
	
	//////COMPANY TAB/////
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Company']")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='COMPANY']")
	private MobileElement companyTab;
	
	/////COMPANY NAME////
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Acc_title_search' && visible ==1")
	@AndroidFindBy(id ="com.et.reader.activities:id/companyName")
	private List<MobileElement> companyNames;
	
	
	
	/////LTP PRICES///
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'acc_last_traded_price_company'")
	@AndroidFindBy(id= "com.et.reader.activities:id/tradePrice")
	private List<MobileElement> ltpPrices;
	
	
	///// MUTUAL FUND TAB///
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Mutual Funds']")
	@AndroidFindBy(xpath ="//android.widget.TextView[@text='MUTUAL FUND']")
	private MobileElement mutualFundTab;
	
	
	//////MUTUAL FUNDS////
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Acc_title_search' && visible ==1")
	@AndroidFindBy(id= "com.et.reader.activities:id/mutual_fund_name")
	private List<MobileElement> mutualFundName;
	
	
	/////MUTUAL FUNDS NAVs////
	@iOSXCUITFindBy(iOSNsPredicate ="name = 'acc_last_traded_price_mutual_funds")
	@AndroidFindBy(id= "com.et.reader.activities:id/nav_value")
	private List<MobileElement> mutualFundNav;
	
	
	///// COMMODITY TAB///
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Commodity']")
	@AndroidFindBy(xpath ="//android.widget.TextView[@text='COMMODITY']")
	private MobileElement commodityTab;
	
	
	/////COMMODITY LTP////
	@iOSXCUITFindBy(iOSNsPredicate ="name = 'acc_last_traded_price_mutual_funds")
	@AndroidFindBy(id= "com.et.reader.activities:id/nav_value")
	private List<MobileElement> commodityLtp;
	
	
	
	///// FOREX TAB///
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Forex']")
	@AndroidFindBy(xpath ="//android.widget.TextView[@text='FOREX']")
	private MobileElement forexTab;
	
	
	
	
/////// Getters start here////////
	
	public MobileElement getAllTab() {
		return allTab;
	}

	public MobileElement getEtPrimeTab() {
		return etPrimeTab;
	}

	public MobileElement getNewstab() {
		return newstab;
	}

	public MobileElement getCompanytab() {
		return companyTab;
	}

	public List<MobileElement> getCompanyNames() {
		return companyNames;
	}

	public List<MobileElement> getLtpPrices() {
		return ltpPrices;
	}

	public MobileElement getMutualFundTab() {
		return mutualFundTab;
	}

	public List<MobileElement> getMutualFundName() {
		return mutualFundName;
	}

	public List<MobileElement> getMutualFundNav() {
		return mutualFundNav;
	}

	public MobileElement getCommodityTab() {
		return commodityTab;
	}
	public List<MobileElement> getcommodityLtp() {
		return commodityLtp;
	}

	public MobileElement getForextab() {
		return forexTab;
	}

	public MobileElement getSearchBar(){
		return searchBar;
		
	}
	
	public List<MobileElement> getSearchResult(){
		return searchResult;
	}
	
	public List<MobileElement> getSearchHeaders(){
		return searchHeaders;
	}
	
	public List<MobileElement> getCompanyName(){
		return companyName;
	}
	
	public List<MobileElement> getCommodityName(){
		return commodityName;
	}

	public List<MobileElement> getMfName(){
		return MfName;
	}	
	
	public List <MobileElement> getNewsItems(){
		return newsItems;
	}
}
