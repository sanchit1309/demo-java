package app.pageobjects;

import org.openqa.selenium.By;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class PortfolioPageObjects {

	@AndroidFindBy(id = "com.et.reader.activities:id/addPortfoliBg")
	@iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeStaticText' && name == 'Create New Portfolio' && visible ==1")
	private MobileElement createPortfolioIcon;

	@AndroidFindBy(id = "com.et.reader.activities:id/input_watchlist_name")
	@iOSXCUITFindBy(xpath = "//*[@type='XCUIElementTypeTextField' and @value='enter portfolio name']")
	private MobileElement portfolioNameField;

	@AndroidFindBy(id = "com.et.reader.activities:id/btn_create_watchlist")
	@iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeButton' && name == 'save' && visible == 1")
	private MobileElement portfolioSaveButton;

	@AndroidFindBy(id = "com.et.reader.activities:id/delete_portfolio")
	@iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeButton' && name == 'Acc_delete_button' && visible ==1")
	private MobileElement portfolioDeleteButton;

	@AndroidFindBy(id = "com.et.reader.activities:id/dialog.button.positive")
	@iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeButton' && name == 'YES' && visible ==1")
	private MobileElement deleteAlertYes;

	@AndroidBy(id = "com.et.reader.activities:id/edit_portfolio")
	private By editPortfolio;
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text='Watchlist']")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'WATCHLIST'")
	private MobileElement watchlistIcon;
	
	@AndroidFindBy(id="com.et.reader.activities:id/ll_action_add_new_stock")
	@HowToUseLocators(iOSXCUITAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'CREATE'")
	private MobileElement addWatchlist;
	
	@AndroidFindBy(id="com.et.reader.activities:id/input_watchlist_name")
	@iOSXCUITFindBy(xpath = "//*[@type='XCUIElementTypeTextField' and @value='enter watchlist name']")
	private MobileElement WatchlistName;
	
	@AndroidFindBy(id="com.et.reader.activities:id/btn_create_watchlist")
	@iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeButton' && name == 'save' && visible == 1")
	private MobileElement saveWatchlist;

	@AndroidFindBy(id="com.et.reader.activities:id/actionbar_tv_parent")
	@HowToUseLocators(iOSXCUITAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'watchListDownArrow'")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'user'")
	private MobileElement watchlistActionBar;
	
	@AndroidFindBy(id="com.et.reader.activities:id/ll_action_edit_watchlist")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'EDIT'")
	private MobileElement editWatchlistIcon;
	
	@AndroidFindBy(id="com.et.reader.activities:id/edit_wathlist")
	@iOSXCUITFindBy(xpath = "//*[@type='XCUIElementTypeTextView' and @value='TestWatchlist']")
	private MobileElement editWatchlistText;

	
	@AndroidFindBy(id="com.et.reader.activities:id/ll_action_delete_watchlist")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'deleteWatchList'")
	private MobileElement deleteWatchlistIcon;
	
	
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Check Now l'")
	private MobileElement checkNowIcon;
	
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'portfolioLeftMenu'")
	private MobileElement portfolioMenuIcon;


	public By getEditPortfolioBy() {
		return editPortfolio;
	}

	public MobileElement getCreatePortfolioIcon() {
		return createPortfolioIcon;
	}

	public MobileElement getPortfolioNameField() {
		return portfolioNameField;
	}

	public MobileElement getPortfolioSaveButton() {
		return portfolioSaveButton;
	}

	public MobileElement getPortfolioDeleteButton() {
		return portfolioDeleteButton;
	}

	public MobileElement getDeleteAlertYes() {
		return deleteAlertYes;
	}

	// public MobileElement
	public MobileElement getWatchlistIcon() {
		return watchlistIcon;
	}
	public MobileElement getWatchlistName() {
		return WatchlistName;
	}
	public MobileElement getWatchlistSave() {
		return saveWatchlist;
	}
  public MobileElement getwatchlistactionBar() {
	  return watchlistActionBar;
  }

  public MobileElement getWatchlistaddIcon() {
	  return addWatchlist;
  }

  public MobileElement geteditWatchlistIcon() {
	  return editWatchlistIcon;
  } 
  public MobileElement geteditWatchlistText() {
	  return editWatchlistText;
  } 
  public MobileElement getdeleteWatchlistIcon() {
	  return deleteWatchlistIcon;
  } 
  
  public MobileElement getCheckNowIcon() {
	  return checkNowIcon;
  } 
  
  public MobileElement getportfolioMenuIcon() {
	  return portfolioMenuIcon;
  }
}
