package wap.pagemethods;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import wap.pageobjects.SearchObjects;
import web.pagemethods.WebBaseMethods;

public class SearchMethods {
	private SearchObjects searchObjects;
	private WebDriver driver;

	public SearchMethods(WebDriver driver) {
		this.driver = driver;
		searchObjects = PageFactory.initElements(driver, SearchObjects.class);
	}

	public List<String> getSearchResultUrl() {
		List<String> urlList = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			try {
				urlList = VerificationUtil.getLinkHrefList(searchObjects.getSearchResultUrl());
			} catch (StaleElementReferenceException e) {
				e.toString();
				Reporter.log("Trying to recover from a stale element" + e.getMessage());
			}
		}
		return urlList;
	}

	public WebElement getSearchBar() {
		return searchObjects.getSearchBar();
	}

	public String openSeachBar() {
		try {
			WaitUtil.sleep(5000);
			//WaitUtil.explicitWaitByElementToBeClickable(driver, 10, searchObjects.getSearchIcon());
			WebBaseMethods.clickElementUsingJSE(searchObjects.getSearchIcon());
			return "";
		}catch(NoSuchElementException e) {
			return null;
		}
	}

	public List<String> getFailSearchResult(String search) {
		List<String> searchResult = WebBaseMethods.getListTextUsingJSE(searchObjects.getSearchResultUrl());
		List<String> result = WebBaseMethods.getListTextUsingJSE(searchObjects.getSearchNewsResultUrl());
		searchResult.removeAll(result);
		List<String> failResult = new LinkedList<>();
		searchResult.forEach(action -> {
			action = action.toLowerCase();
			if (!action.contains(search)) {
				failResult.add(action);
			}
		});
		return failResult;
	}

	public boolean checkVisibilityOfFirstElement() {
		boolean flag = false;
		try {
			WebElement result = searchObjects.getSearchList();
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 5, result);
			flag = true;
		} catch (TimeoutException ex) {
			flag = false;
		}

		return flag;
	}

	public List<String> verifyNewsUrl(String search) {
		List<String> newsUrl = new LinkedList<>();

		List<String> url = VerificationUtil.getLinkHrefList(searchObjects.getSearchNewsResultUrl());
		url.forEach(action -> {
			driver.navigate().to(action);
			if (!driver.getPageSource().toLowerCase().contains(search.toLowerCase())) {
				newsUrl.add(action);
			}

		});
		return newsUrl;
	}

	public Map<String, List<String>> getSearchResultText() {
		List<WebElement> searchText = searchObjects.getSearchListings();
		System.out.println(searchText.size());
		Map<String, List<String>> searchResults = new LinkedHashMap<>();
		for (int itr = 0; itr < 5; itr++) {
			try {
				int i = 0;
				int k = i + 1;
				do {
					k = i + 1;
					String temp = searchText.size() > 0 ? searchText.get(i).getAttribute("class") : "";
					List<String> liItems = new LinkedList<>();
					if (temp.contains("head")) {
						for (; k < searchText.size(); k++) {
							temp = searchText.get(k).getAttribute("class");
							if (temp.length() == 0) {
								liItems.add(searchText.get(k).getText());
								if (k == (searchText.size() - 1)) {
									searchResults.put(searchText.get(i).getText(), liItems);
									break;
								}
								continue;
							} else if (temp.contains("head")) {
								searchResults.put(searchText.get(i).getText(), liItems);
								break;
							}

						}
					}
					i = k;
				} while (i < searchText.size());
			} catch (StaleElementReferenceException e) {
				e.toString();
				System.out.println("Trying to recover from a stale element" + e.getMessage());
			}
		}
		return searchResults;
	}
}