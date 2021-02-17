package app.pagemethods;

import java.util.List;

import org.openqa.selenium.support.PageFactory;

import app.pageobjects.MarketDataPageObjects;
import common.launchsetup.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MarketDataPageMethods {

	AppiumDriver<?> appDriver;
	MarketDataPageObjects marketDataPageObjects = new MarketDataPageObjects();

	public MarketDataPageMethods(AppiumDriver<?> driver) {
		appDriver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(appDriver), marketDataPageObjects);
	}

	public boolean verifySensexDataPresent() {
		try {
			removePopUp();
			if (marketDataPageObjects.getNoContentMsg().size() == 0) {
				String sensexData = marketDataPageObjects.getMarketValue().getText();
				if (!sensexData.isEmpty())
					return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public double getCurrentSensexPrice() {
		double sensex = 0.0;
		try {
			String sensexData = marketDataPageObjects.getMarketValue().getText();
			sensex = Double.parseDouble(sensexData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sensex;
	}

	public boolean verifyNiftyDataPresent() {
		try {
			removePopUp();
			if (marketDataPageObjects.getNoContentMsg().size() == 0) {
				String niftyData = marketDataPageObjects.getMarketValue().getText();
				if (!niftyData.isEmpty())
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public double getCurrentNiftyPrice() {
		double nifty = 0.0;
		try {
			String niftyData = marketDataPageObjects.getMarketValue().getText();
			nifty = Double.parseDouble(niftyData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nifty;
	}

	public boolean verifyForexDataPresent() {
		try {
			if (marketDataPageObjects.getNoContentMsg().size() == 0) {
				List<MobileElement> forexData = marketDataPageObjects.getForexRate();
				for (MobileElement rate : forexData) {
					if (!rate.getText().isEmpty())
						return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean verifyCommodityDataPresent() {
		try {
			if (marketDataPageObjects.getNoContentMsg().size() == 0) {
				String comData = marketDataPageObjects.getComValue().getText();
				if (!comData.isEmpty())
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean verifyMFDataPresent() {
		try {
			if (marketDataPageObjects.getNoContentMsg().size() == 0) {
				List<MobileElement> mfData = marketDataPageObjects.getMFEquity();
				for (MobileElement fund : mfData) {
					if (!fund.getText().isEmpty())
						return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean verifyGainersDataPresent() {
		try {
			if (marketDataPageObjects.getNoContentMsg().size() == 0) {
				List<MobileElement> gainersData = marketDataPageObjects.getPriceValue();
				for (MobileElement price : gainersData) {
					if (!price.getText().isEmpty())
						return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean verifyLosersDataPresent() {
		try {
			if (marketDataPageObjects.getNoContentMsg().size() == 0) {
				List<MobileElement> losersData = marketDataPageObjects.getPriceValue();
				for (MobileElement price : losersData) {
					if (!price.getText().isEmpty())
						return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean verifyMoversDataPresent() {
		try {
			if (marketDataPageObjects.getNoContentMsg().size() == 0) {
				List<MobileElement> moversData = marketDataPageObjects.getPriceValue();
				for (MobileElement price : moversData) {
					if (!price.getText().isEmpty())
						return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void removePopUp() {
		BaseTest.iAppCommonMethods.tapByCoordinates(appDriver, 100, 100);
	}
}
