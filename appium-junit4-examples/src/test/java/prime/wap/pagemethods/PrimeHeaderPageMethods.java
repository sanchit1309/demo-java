package prime.wap.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.VerificationUtil;
import prime.wap.pageobjects.PrimeHeaderPageObjects;

public class PrimeHeaderPageMethods {

	private WebDriver driver;
	PrimeHeaderPageObjects primeHeaderPageObjects;

	public PrimeHeaderPageMethods(WebDriver driver) {
		primeHeaderPageObjects = PageFactory.initElements(driver, PrimeHeaderPageObjects.class);
		this.driver = driver;
	}

	public List<String> getAllHeadersHrefList() {
		List<String> hrefList = new LinkedList<>();
		try {
			hrefList = VerificationUtil.getLinkHrefList(primeHeaderPageObjects.getAllHeaderLinks());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}
	
	public List<String> getAllHeadersNameList() {
		List<String> hrefList = new LinkedList<>();
		try {
			hrefList = VerificationUtil.getLinkTextList(primeHeaderPageObjects.getAllHeaderLinks());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}
	
}
