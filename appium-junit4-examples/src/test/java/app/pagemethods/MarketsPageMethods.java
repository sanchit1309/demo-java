package app.pagemethods;

import org.openqa.selenium.support.PageFactory;

import app.pageobjects.MarketsPageObjects;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class MarketsPageMethods {

	AppiumDriver<?> appdDriver;
	MarketsPageObjects marketsPageObjects;

	public MarketsPageMethods(AppiumDriver<?> driver) {
		appdDriver = driver;
		marketsPageObjects = new MarketsPageObjects();
		PageFactory.initElements(new AppiumFieldDecorator(appdDriver), marketsPageObjects);
	}

	public MarketsPageObjects getMarketsPageObjects() {
		return marketsPageObjects;
	}

	/*public boolean isWAP() {
		boolean flag = false;
		WaitUtil.sleep(1000);
		try {
			marketsPageObjects.getWapView().isDisplayed();
			flag = true;
		} catch (NoSuchElementException e) {
			// TODO: handle exception
		}
		return flag;
	}
*/
}
