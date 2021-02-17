package wap.pagemethods;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import wap.pageobjects.PrimeShowPageObjects;
import web.pagemethods.WebBaseMethods;

public class PrimeShowPageMethods {

	private PrimeShowPageObjects primeShowPageObjects;
	private WapListingPageMethods wapListingPageMethods;

	public PrimeShowPageMethods(WebDriver driver) {
		primeShowPageObjects = PageFactory.initElements(driver, PrimeShowPageObjects.class);
		wapListingPageMethods = new WapListingPageMethods(driver);
	}

	public WebElement getPrimeShowBodyContent() {

		try {
			return primeShowPageObjects.getPrimeShowContentBody();

		} catch (Exception ee) {
			ee.printStackTrace();
			return null;
		}
	}

	public String getPrimeShowBodyContentText() {

		try {
			return WebBaseMethods.getListTextUsingJSE(primeShowPageObjects.getPrimeShowContentBody());

		} catch (Exception ee) {
			ee.printStackTrace();
			return "The primeshow body content is not found";
		}
	}

	public List<String> getAllETPrimeShowLinks() {

		List<String> primeUrls = new LinkedList<>();
		ArrayList<String> newList = new ArrayList<String>();
		try {
			primeUrls = WebBaseMethods.getListHrefUsingJSE(primeShowPageObjects.getAllETpremiumLinks());
			for (String element : primeUrls) {

				if (!newList.contains(element)) {

					newList.add(element);
				}
			}

		} catch (Exception ee) {
			ee.printStackTrace();

		}
		System.out.println(newList);
		return newList;
	}
}
