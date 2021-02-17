package web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import web.pageobjects.ETPrimeHomePageObjects;

public class ETPrimeHomePageMethods {

	private WebDriver driver;
	private ETPrimeHomePageObjects etPrimeHomePageObjects;

	public ETPrimeHomePageMethods(WebDriver driver) {
		this.driver = driver;
		etPrimeHomePageObjects = PageFactory.initElements(driver, ETPrimeHomePageObjects.class);

	}

	public List<String> topStoriesListHref() {
		List<String> topStoriesListhref = new LinkedList<String>();
		try {
			topStoriesListhref = WebBaseMethods.getListHrefUsingJSE(etPrimeHomePageObjects.getTopStoriesBlock());

		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return topStoriesListhref;

	}

	public boolean clickFirstPrimeArticleFromPrimeHomepage() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(etPrimeHomePageObjects.getTopStoriesBlock().get(0));
			 

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;

		}

		return flag;

	}

}
