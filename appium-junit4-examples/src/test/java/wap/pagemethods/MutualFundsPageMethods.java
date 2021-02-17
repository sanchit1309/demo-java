package wap.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import wap.pageobjects.MutualFundPageObjects;
import web.pagemethods.WebBaseMethods;

public class MutualFundsPageMethods {
	private WapListingPageMethods wapListingPageMethods;
	private MutualFundPageObjects mutualFundsPageObjects;

	public MutualFundsPageMethods(WebDriver driver) {
		wapListingPageMethods = new WapListingPageMethods(driver);
		mutualFundsPageObjects = PageFactory.initElements(driver, MutualFundPageObjects.class);
	}

	public List<String> getSubSectionStoriesHref(String sectionName) {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods.getListHrefUsingJSE(mutualFundsPageObjects.getSubSectionLinks(sectionName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public List<String> getSubSectionStoriesText(String sectionName) {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods.getListTextUsingJSE(mutualFundsPageObjects.getSubSectionLinks(sectionName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public List<String> getTopMutualFundsSchemeHref(String sectionName) {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods
					.getListHrefUsingJSE(mutualFundsPageObjects.getTopMutualFundsSchemeLinks(sectionName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public List<String> getTopMutualFundsSchemeText(String sectionName) {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods
					.getListTextUsingJSE(mutualFundsPageObjects.getTopMutualFundsSchemeLinks(sectionName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public List<String> getTopETFSchemeHref(String sectionName) {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods.getListHrefUsingJSE(mutualFundsPageObjects.getETFSchemeLinks(sectionName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public List<String> getTopETFSchemeText(String sectionName) {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods.getListTextUsingJSE(mutualFundsPageObjects.getETFSchemeLinks(sectionName));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return hrefList;
	}

	public boolean clickETFSubSectionNseBseTab(String sectionName, String tabName) {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(mutualFundsPageObjects.getETFSubSectionNseBseTab(sectionName, tabName));
			WaitUtil.sleep(3000);
			String status = mutualFundsPageObjects.getETFSubSectionNseBseTab(sectionName, tabName)
					.getAttribute("class");
			if (status.equalsIgnoreCase("active")) {
				flag = true;
			}
		} catch (Exception ee) {

			ee.printStackTrace();
		}

		return flag;
	}

}
