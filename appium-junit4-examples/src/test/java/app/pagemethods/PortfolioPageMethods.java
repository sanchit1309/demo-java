package app.pagemethods;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

import app.pageobjects.PortfolioPageObjects;
import common.launchsetup.BaseTest;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PortfolioPageMethods {

	AppiumDriver<?> appDriver;
	PortfolioPageObjects portfolioPageObjects = new PortfolioPageObjects();
	String portfolioName;

	public PortfolioPageMethods(AppiumDriver<?> driver) {
		appDriver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(appDriver), portfolioPageObjects);
	}

	public boolean isPortfolioSectionDisplayed() {
		boolean flag = false;
		try {
			if (portfolioPageObjects.getCreatePortfolioIcon().isDisplayed())
				flag = true;
		} catch (NoSuchElementException e) {
			flag = false;
		}
		return flag;
	}

	public String createPortfolio() {
		try {
			portfolioPageObjects.getCreatePortfolioIcon().click();
			portfolioName = "MyPortfolio" + VerificationUtil.getRandomNumber();
			portfolioPageObjects.getPortfolioNameField().sendKeys(portfolioName);
			if (BaseTest.platform.contains("ios"))
				appDriver.hideKeyboard();
			portfolioPageObjects.getPortfolioSaveButton().click();
			WaitUtil.sleep(10000);
		} catch (NoSuchElementException e) {

		}
		return portfolioName;
	}

	public MobileElement getPortfolioEditButton(String portfolio) {
		MobileElement editButton = null;
		try {
			String elementIOSNsPredicate = "Acc_edit_button_" + portfolio;
			BaseTest.iAppCommonMethods.scrollUpToElement(elementIOSNsPredicate);
			editButton = BaseTest.iAppCommonMethods.getElementByText(elementIOSNsPredicate);
		} catch (NoSuchElementException e) {

		}
		return editButton;
	}

	public boolean deletePortfolio() {
		boolean flag = false;
		try {
			portfolioPageObjects.getPortfolioDeleteButton().click();
			portfolioPageObjects.getDeleteAlertYes().click();
			flag = true;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public MobileElement getEditButtonElement(String portfolio) {
		MobileElement editButton = null;
		try {
			BaseTest.iAppCommonMethods.scrollUpToElement(portfolio);
			if (BaseTest.platform.equalsIgnoreCase("iosApp")) {
				String elementIOSNsPredicate = "Acc_edit_button_" + portfolio;
				BaseTest.iAppCommonMethods.scrollUpToElement(elementIOSNsPredicate);
				editButton = BaseTest.iAppCommonMethods.getElementByText(elementIOSNsPredicate);
			} else {
				editButton = (MobileElement) appDriver.findElement(By.xpath("//android.widget.TextView[@text='"
						+ portfolio + "']/../../android.widget.ImageView[contains(@resource-id,'edit_portfolio')]"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return editButton;
	}

	public boolean clickonWatchListIcon() {
		boolean flag = false;
		try {
			WaitUtil.sleep(3000);
			portfolioPageObjects.getWatchlistIcon().click();
			WaitUtil.sleep(3000);
			if (portfolioPageObjects.getWatchlistaddIcon().isDisplayed())
				flag = true;
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
			// TODO: handle exception
		}
	}

	public boolean clickonWatchListaddIcon() {
		boolean flag = false;
		try {
			portfolioPageObjects.getWatchlistaddIcon().click();
			if (portfolioPageObjects.getWatchlistSave().isDisplayed())
				flag = true;
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
			// TODO: handle exception
		}
	}

	public boolean nameWatchlistandSave() {
		boolean flag = false;
		try {

			portfolioPageObjects.getWatchlistName().sendKeys("TestWatchlist");
			if (BaseTest.platform.contains("ios"))
				appDriver.hideKeyboard();
			portfolioPageObjects.getWatchlistSave().click();
			flag = true;
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
			// TODO: handle exception
		}
	}

	public boolean verifyWatchlistAdded() {
		boolean flag = false;
		try {

			portfolioPageObjects.getwatchlistactionBar().click();
			if (BaseTest.iAppCommonMethods.scrollUpToElement("TestWatchlist")) {
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
			// TODO: handle exception
		}
	}

	public boolean verifyDeleteWatchlist() {
		boolean flag = false;
		try {
			BaseTest.iAppCommonMethods.navigateBack(appDriver);
			portfolioPageObjects.geteditWatchlistIcon().click();
			String watchlist = portfolioPageObjects.geteditWatchlistText().getText();
			System.out.println("text=====" + watchlist);
//			 portfolioPageObjects.geteditWatchlistText().click();
//			portfolioPageObjects.geteditWatchlistText().clear();
//			 portfolioPageObjects.geteditWatchlistText().sendKeys("TestWatchlist");
			portfolioPageObjects.getdeleteWatchlistIcon().click();
			WaitUtil.sleep(1000);
			portfolioPageObjects.getwatchlistactionBar().click();
			if (!BaseTest.iAppCommonMethods.scrollUpToElement(watchlist)) {
				flag = true;

			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
			// TODO: handle exception
		}
	}
	
	public boolean clickOnCheckNowIcon() {
		boolean flag = false;
		try {
			portfolioPageObjects.getCheckNowIcon().click();
			flag = true;
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
			// TODO: handle exception
		}
	}
	
	public boolean clickPortfolioMenuIcon() {
		boolean flag = false;
		try {
			portfolioPageObjects.getportfolioMenuIcon().click();
			flag = true;
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
			// TODO: handle exception
		}
	}
}
