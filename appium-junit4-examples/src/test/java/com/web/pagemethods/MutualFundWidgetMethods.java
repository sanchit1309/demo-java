package com.web.pagemethods;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.MutualFundWidgetObjects;

public class MutualFundWidgetMethods {
	private MutualFundWidgetObjects mutualFundWidgetObjects;
	private String featuredScheme;

	public MutualFundWidgetMethods(WebDriver driver) {
		mutualFundWidgetObjects = PageFactory.initElements(driver, MutualFundWidgetObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
	}

	public int getTopMutualFundsList() {
		return mutualFundWidgetObjects.getTopMutualFundsList().size();
	}

	public List<WebElement> getMutualFundTabList() {
		return mutualFundWidgetObjects.getMutualFundWidgetTab();

	}
	
	public void hoverOnMfCat (String catName) {
		WebBaseMethods.JSHoverOver(mutualFundWidgetObjects.getMfCategoryTab(catName));
	}
	
	public boolean hoverIfMfCategoryVisible(String catName) {
		boolean flag = false;
		List<WebElement> elements = getMutualFundTabList();
		System.out.println("size " + elements.size());
		for(int i = 0; i < elements.size(); i++) {
			System.out.println("Element text: " + elements.get(i).getText().trim());
			if (elements.get(i).getText().trim().equalsIgnoreCase(catName)) {
				flag = true;
				hoverOnMfCat(catName);
				break;
			} else {
				continue;
			}
		}
		return flag;
	}
	
	public boolean areSchemesVisible() {
		return !WebBaseMethods.getDisplayedItemFromList(mutualFundWidgetObjects.getMutualFundSchemeNameLink()).isEmpty();
	}
	
	public boolean areBuyBtnVisible() {
		return !WebBaseMethods.getDisplayedItemFromList(mutualFundWidgetObjects.getBuyNowButton()).isEmpty();
	}

	public List<WebElement> getMutualFundTabSubmenuList(String tabName) {
		List<WebElement> li = new LinkedList<WebElement>();
		if (mutualFundWidgetObjects.getMutualFundWidgetTabSubmenu(tabName).size() > 0)
			li = mutualFundWidgetObjects.getMutualFundWidgetTabSubmenu(tabName);
		else
			li.add(null);
		return li;
	}

	public Map<String, LinkedHashMap<String, String>> getMutualFundSchemeWithData() {
		List<WebElement> schemeNameList = new ArrayList<>();
		Map<String, LinkedHashMap<String, String>> mfSchemeLinksWithData = new LinkedHashMap<String, LinkedHashMap<String, String>>();

		// System.out.println(mutualFundWidgetObjects.getMutualFundSchemeName().getText());
		schemeNameList = WebBaseMethods.getDisplayedItemFromList(mutualFundWidgetObjects.getMutualFundSchemeNameLink());

		System.out.println(schemeNameList.size());
		if (schemeNameList.size() > 0) {
			for (int i = 0; i < schemeNameList.size(); i++) {
				boolean flag = false;
				System.out.println("Passing i,schemeNameList:" + i);
				int counter = 0;
				String schemeName = "";
				while (counter < 5) {
					flag = false;
					try {
						schemeName = schemeNameList.get(i).getText();
						if (schemeName.trim().trim().length() > 0) {
							flag = true;
							break;
						} else {
							counter++;
							System.out.println("Retrying");
							continue;
						}
					} catch (StaleElementReferenceException e) {

					}
					counter++;
				}
				if (flag)
					mfSchemeLinksWithData.put(schemeName, getMFSchemeData(i));

			}
		}
		return mfSchemeLinksWithData;
	}

	public LinkedHashMap<String, String> getMFSchemeData(int i) {
		LinkedHashMap<String, String> mfSchemeData = new LinkedHashMap<String, String>();
		String value = "";
		int counter = 0;
		do {
			try {
				value = WebBaseMethods.getDisplayedItemFromList(mutualFundWidgetObjects.getMutualFundScheme1Mnthtab())
						.get(i).getText().trim();
				if (value.trim().length() > 0)
					break;
				else {
					counter++;
					continue;
				}
			} catch (StaleElementReferenceException | NoSuchElementException | IndexOutOfBoundsException e) {

				System.out.println(e.getMessage());
			}

			counter++;
		} while (counter < 5);
		mfSchemeData.put("1 month", value);
		counter = 0;
		value = "";
		do {

			try {
				value = WebBaseMethods.getDisplayedItemFromList(mutualFundWidgetObjects.getMutualFundScheme3Mnthtab())
						.get(i).getText().trim();
				if (value.trim().length() > 0)
					break;
				else {
					counter++;
					continue;
				}
			} catch (StaleElementReferenceException | NoSuchElementException | IndexOutOfBoundsException e) {

				System.out.println(e.getMessage());
			}

			counter++;
		} while (counter < 5);
		mfSchemeData.put("3 month", value);
		counter = 0;
		value = "";
		do {

			try {
				value = WebBaseMethods.getDisplayedItemFromList(mutualFundWidgetObjects.getMutualFundScheme6Mnthtab())
						.get(i).getText().trim();
				if (value.trim().length() > 0)
					break;
				else {
					counter++;
					continue;
				}
			} catch (StaleElementReferenceException | NoSuchElementException | IndexOutOfBoundsException e) {

				System.out.println(e.getMessage());
			}
			counter++;
		} while (counter < 5);
		mfSchemeData.put("6 month", value);
		counter = 0;
		value = "";
		do {

			try {
				value = WebBaseMethods.getDisplayedItemFromList(mutualFundWidgetObjects.getMutualFundScheme1Yrtab())
						.get(i).getText().trim();
				if (value.trim().length() > 0)
					break;
				else {
					counter++;
					continue;
				}
			} catch (StaleElementReferenceException | NoSuchElementException | IndexOutOfBoundsException e) {

				System.out.println(e.getMessage());
			}
			counter++;
		} while (counter < 5);
		mfSchemeData.put("1 yr", value);
		counter = 0;
		value = "";
		do {

			try {
				value = WebBaseMethods.getDisplayedItemFromList(mutualFundWidgetObjects.getMutualFundScheme3Yrstab())
						.get(i).getText().trim();
				if (value.trim().length() > 0)
					break;
				else {
					counter++;
					continue;
				}
			} catch (StaleElementReferenceException | NoSuchElementException | IndexOutOfBoundsException e) {

				System.out.println(e.getMessage());
			}
			counter++;
		} while (counter < 5);
		mfSchemeData.put("3 yrs", value);
		return mfSchemeData;
	}

	public WebElement getMutualFundNoSchemeNameFound() {
		return mutualFundWidgetObjects.getMutualFundNoSchemeNameFound();
	}

	public void clickSubMenu(WebElement we) {
		for (int i = 0; i < 4; i++) {
			try {
				WebBaseMethods.clickElementUsingJSE(we);
				break;
			} catch (StaleElementReferenceException ee) {
			}
		}
	}

	public boolean isMFWidgetDisplayed() {
		boolean flag = false;
		try {
			mutualFundWidgetObjects.getMutualFundTab().isDisplayed();
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public WebElement getMutualFundWidget() {
		WebElement el = null;
		try {
			el = mutualFundWidgetObjects.getMutualFundTab();
		} catch (NoSuchElementException e) {

		}
		return el;
	}

	public boolean clicktMutualFundTabs(String tabName) {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(mutualFundWidgetObjects.getMutualFundTabs(tabName));
			
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public Map<String, LinkedHashMap<String, String>> getMFWidgetSubtabWithData(String tabName) {
		Map<String, LinkedHashMap<String, String>> mfSchemeLinksWithData = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		List<WebElement> schemeNameList = mutualFundWidgetObjects.getMFWidgetSubTabSchemeName(tabName);
		System.out.println(schemeNameList.size());
		int counter = 0;
		for (WebElement el : schemeNameList) {
			mfSchemeLinksWithData.put(el.getText(), getMFWidgetSubTabSchemeData(counter, tabName));
			counter++;
		}

		return mfSchemeLinksWithData;
	}

	public LinkedHashMap<String, String> getMFWidgetSubTabSchemeData(int i, String tabName) {
		LinkedHashMap<String, String> mfSchemeData = new LinkedHashMap<String, String>();
		List<WebElement> mfWidgetHeaderDataTabs = mutualFundWidgetObjects.getMFWidgetHeaderDataTabs(tabName);
		List<WebElement> mfWidgetSubTabSchemeData = mutualFundWidgetObjects.getMFWidgetSubTabSchemeData(tabName);
		for (int j = 0; j < mfWidgetHeaderDataTabs.size(); j++) {
			try {
				mfSchemeData.put(mfWidgetHeaderDataTabs.get(j).getText(),
						mfWidgetSubTabSchemeData.get(j + (i * mfWidgetHeaderDataTabs.size())).getText().trim());
			} catch (NoSuchElementException e) {
				mfSchemeData.put(mfWidgetHeaderDataTabs.get(j).getText(), "");
				System.out.println(e.getMessage());
			} catch (IndexOutOfBoundsException e) {

			}
		}

		return mfSchemeData;
	}

	public List<String> verifyCTAOnClickOfInvestNow(WebDriver driver) {
		List<String> schemesRedirectingIncorrectly = new LinkedList<>();
		List<WebElement> allBuyButtons = WebBaseMethods
				.getDisplayedItemFromList(mutualFundWidgetObjects.getBuyNowButton());

		for (int i = 0; i < allBuyButtons.size(); i++) {
			WebElement buyBtn = WebBaseMethods.getDisplayedItemFromList(mutualFundWidgetObjects.getBuyNowButton())
					.get(i);

			String buyBtnHref = "";
			try {
				buyBtnHref = buyBtn.getAttribute("href").trim();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			String schemeName = "";
			try {
				schemeName = mutualFundWidgetObjects.getPromotedSchemeName(buyBtnHref).getText();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			try {
				WebBaseMethods.clickElementUsingJSE(buyBtn);
				WaitUtil.sleep(1000);
				int size = WebBaseMethods.getWindowsSize();
				if (size > 1) {
					WebBaseMethods.switchChildIfPresent();
					WaitUtil.sleep(1000);
					String redirectedPageUrl = WebBaseMethods.getUrlPage(driver).trim();
					if (!buyBtnHref.equalsIgnoreCase(redirectedPageUrl)) {
						schemesRedirectingIncorrectly.add("Invest now button on scheme: " + schemeName
								+ " is not redirecting to correct url.<br>href on button: " + buyBtnHref
								+ "<br>redirected url: " + redirectedPageUrl + "<br>");
					}
					WaitUtil.sleep(500);
					WebBaseMethods.switchToParentClosingChilds();
					WaitUtil.sleep(500);
				} else {
					schemesRedirectingIncorrectly.add("Invest now button at position: " + (i + 1) + " not clickable.<br>");
				}
				WebBaseMethods.acceptIfAlertPresent();
				WaitUtil.sleep(3000);
			} catch (Exception e) {
				schemesRedirectingIncorrectly.add("Exception occured while testing Invest now button at position: " + (i + 1) + "<br>");
				System.out.println("Exception occured while testing Invest now button at position: " + (i + 1));
				e.printStackTrace();
			}
		}
		return schemesRedirectingIncorrectly;
	}

/*	public boolean checkIfSchemeChanges() {
		System.out.println("checkifschemechanges method running.");
		String tempScheme = "";
		boolean flag = false;
		List<WebElement> mfs = mutualFundWidgetObjects.getPromotedScheme();
		System.out.println("mfs: " + mfs.size());
		List<WebElement> displayedEl = WebBaseMethods.getDisplayedItemFromList(mfs);
		System.out.println("displayedmfs: " + displayedEl.size());
		for (int i = 0; i <= 1; i++) {
			if (displayedEl.size() > 0) {
				featuredScheme = WebBaseMethods.getDisplayedItemFromList(mfs).get(0).getText();
				if (featuredScheme.length() == 0) {
					WaitUtil.sleep(2000);
					featuredScheme = WebBaseMethods.getDisplayedItemFromList(mfs).get(0).getText();
				}
				if (i == 0) {
					WaitUtil.sleep(5000);
					continue;
				} else {
					tempScheme = WebBaseMethods.getDisplayedItemFromList(mfs).get(0).getText();
					if (tempScheme.length() == 0) {
						WaitUtil.sleep(2000);
						tempScheme = WebBaseMethods.getDisplayedItemFromList(mfs).get(0).getText();
					}
					System.out.println("Featured:" + featuredScheme + "  TempScheme:" + tempScheme);
					flag = !(featuredScheme.equalsIgnoreCase(tempScheme));
				}
			} else {
				WaitUtil.sleep(5000);
				displayedEl = WebBaseMethods.getDisplayedItemFromList(mfs);
				flag = (displayedEl.size() > 0);
				i--;
				continue;
			}

		}
		return flag;
	}*/
	
}
