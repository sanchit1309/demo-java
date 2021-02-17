package web.pagemethods;

import org.joda.time.DateTime;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;

import web.pageobjects.MarketBandObjects;

public class MarketBandPageMethods {

	WebBaseMethods webBaseMethods;
	MarketBandObjects marketBandObjects;

	public MarketBandPageMethods(WebDriver driver) {
		marketBandObjects = PageFactory.initElements(driver, MarketBandObjects.class);
	}

	public String getGoldValue() {
		String goldValue = "0";
		int counter = 0;
		boolean flag = false;
		do {
			System.out.println(counter);
			try {
				if (WebBaseMethods.isDisplayed(marketBandObjects.getGoldTitle(), 5))
					goldValue = marketBandObjects.getGoldValue().getAttribute("data-item_1").split("~")[1].trim();
				flag = goldValue.equals("0") ? false : true;
			} catch (StaleElementReferenceException e) {
				WebBaseMethods.stopPageLoad();
				WebBaseMethods.refreshTimeOutHandle();
				System.out.println("Stale element encountered " + (counter + 1) + " time");
			} catch (Exception e) {
				e.printStackTrace();
			}
			counter++;
		} while (!flag && counter < 5);
		return goldValue;
	}

	public String getUsdValue() {
		boolean flag = false;
		int counter = 0;
		String usdValue = "0";
		do {
			System.out.println(counter);
			try {
				if (WebBaseMethods.isDisplayed(marketBandObjects.getUsdinrTitle(), 5))
					usdValue = marketBandObjects.getUsdValue().getAttribute("data-item_2").split("~")[1].trim();
				flag = usdValue.equals("0") ? false : true;
			} catch (StaleElementReferenceException e) {
				WebBaseMethods.stopPageLoad();
				WebBaseMethods.refreshTimeOutHandle();
				System.out.println("Stale element encountered " + (counter + 1) + " time");
			} catch (Exception e) {
				e.printStackTrace();
			}
			counter++;
		} while (!flag && counter < 5);
		return usdValue;
	}

	public String getNifyValue() {
		boolean flag = false;
		int counter = 0;
		String niftyValue = "0";
		do {
			try {
				if (WebBaseMethods.isDisplayed(marketBandObjects.getNiftyTitle(), 1))
					niftyValue = marketBandObjects.getNifty().getAttribute("data-item_1").split("~")[1].trim();
				flag = niftyValue.equals("0") ? false : true;
			} catch (StaleElementReferenceException e) {
				WebBaseMethods.stopPageLoad();
				WebBaseMethods.refreshTimeOutHandle();
				System.out.println("Stale element encountered " + (counter + 1) + " time");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			counter++;
		} while (!flag && counter < 5);
		return niftyValue;
	}

	public String getSensexValue() {
		boolean flag = false;
		int counter = 0;
		String sensexValue = "0";
		do {
			System.out.println(counter);
			try {
				if (WebBaseMethods.isDisplayed(marketBandObjects.getSensexTitle(), 1))
					sensexValue = marketBandObjects.getSensex().getAttribute("data-item_2").split("~")[1].trim();
				flag = sensexValue.equals("0") ? false : true;
			} catch (StaleElementReferenceException e) {
				WebBaseMethods.stopPageLoad();
				WebBaseMethods.refreshTimeOutHandle();
				System.out.println("Stale element encountered " + (counter + 1) + " time");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			counter++;
		} while (!flag && counter < 5);
		return sensexValue;
	}

	public String getDateTimeText() {
		try {
			return marketBandObjects.getTimeDateElement().getText();
		} catch (WebDriverException e) {
			return "";
		}
	}

	public DateTime getFormattedDate() {
		String dateTime = getDateTimeText();
		String[] dateTimes = dateTime.split("\\|");
		String dateTimeN = dateTimes[1] + " 2018 |" + dateTimes[0];
		System.out.println(dateTimeN);

		DateTime date = WebBaseMethods.convertDateMethod(
				dateTimeN.replaceAll("\\|", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\.", ":"));
		return date;

	}
}
