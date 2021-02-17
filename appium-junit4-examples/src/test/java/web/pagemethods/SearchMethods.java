package web.pagemethods;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.SearchObjects;

public class SearchMethods {

	private WebDriver driver;
	private SearchObjects searchObjects;
	private List<String> result = new LinkedList<>();
	private String keys = "";

	public SearchMethods(WebDriver driver) {
		this.driver = driver;
		searchObjects = PageFactory.initElements(driver, SearchObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
	}

	public SearchObjects getSearchObjects() {
		return searchObjects;
	}

	public List<String> getSearchResultUrl() {
		List<String> urlList = new LinkedList<>();
		int counter = 0;
		boolean rerun = false;
		do {
			try {
				urlList = WebBaseMethods.getListHrefUsingJSE(searchObjects.getSearchResultUrl());
				counter = 0;
				rerun = false;
			} catch (Exception e) {
				rerun = true;
				counter++;
			}
		} while (rerun && counter < 5);
		return urlList;
	}

	public boolean getSearchBarType(String keyword) {
		boolean flag = false;
		try {
			searchObjects.getSearchBar().clear();
			searchObjects.getSearchBar().sendKeys(keyword);
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public List<WebElement> getSearchResult() {

		return searchObjects.getSearchResult();
	}

	public List<String> getFailSearchResult(String search) {
		List<String> searchResult = WebBaseMethods.getListTextUsingJSE(searchObjects.getSearchResultUrl());
		List<String> result = WebBaseMethods.getListTextUsingJSE(searchObjects.getSearchNewsResultUrl());
		List<String> primeResult = WebBaseMethods.getListTextUsingJSE(searchObjects.getSearchPrimeStoriesResultUrl());
		searchResult.removeAll(result);
		searchResult.removeAll(primeResult);

		// System.out.println(searchResult);
		List<String> failResult = new LinkedList<>();
		searchResult.forEach(action -> {
			action = action.toLowerCase();
			// System.out.println(action);
			if (!action.contains(search)) {
				failResult.add(action);
			}
		});
		return failResult;
	}

	public boolean checkPresenceOfFirstElement() {
		boolean flag = false;
		try {
			WebElement result = searchObjects.getSearchList();
			WaitUtil.explicitWaitByPresenceOfElement(driver, 5, result);
			flag = true;

		} catch (WebDriverException | NullPointerException ex) {
			flag = false;

		}

		return flag;
	}

	public List<String> verifyNewsUrl(String search) {
		List<String> newsUrl = new LinkedList<>();

		// List<String> url =
		// VerificationUtil.getLinkHrefList(searchObjects.getSearchNewsResultUrl());
		List<String> url = WebBaseMethods.getListHrefUsingJSE(searchObjects.getSearchNewsResultUrl());

		url.forEach(action -> {
			try {
			WebBaseMethods.navigateTimeOutHandle(action);
			WaitUtil.sleep(4000);
			if (!driver.getPageSource().toLowerCase().contains(search)) {
				newsUrl.add(action);
			}
			} catch(Exception e)
			{
				newsUrl.add(action);
				e.printStackTrace();
			}

		});
		return newsUrl;
	}

	public Map<String, List<String>> getSearchResultText() {
		// List<String> result =
		// WebBaseMethods.getListHrefUsingJSE(searchPageObjects.getSearchResult());
		List<WebElement> searchText = searchObjects.getSearchListings();
		Map<String, List<String>> searchResults = new LinkedHashMap<>();
		int i = 0;
		int counter = 0;
		boolean retry = false;
		int k = i + 1;
		try {
			do {
				k = i + 1;
				String temp = "0";
				do {
					try {
						temp = searchObjects.getSearchListings().get(i).getAttribute("class");
						counter = 0;
						retry = false;
					} catch (StaleElementReferenceException e) {
						retry = true;
						counter++;
					} catch (IndexOutOfBoundsException e) {
						searchResults.put(" ", new LinkedList<>());
						return searchResults;
					}
				} while (retry && counter < 5);
				List<String> liItems = new LinkedList<>();
				if (temp.contains("head")) {
					for (; k < searchText.size(); k++) {
						do {
							try {
								temp = searchObjects.getSearchListings().get(k).getAttribute("class");
								retry = false;
							} catch (StaleElementReferenceException e) {
								retry = true;
								counter++;
							} catch (IndexOutOfBoundsException e) {
								searchResults.put(" ", new LinkedList<>());
								return searchResults;
							}
						} while (retry && counter < 5);
						if (temp.length() == 0) {
							liItems.add(searchText.get(k).getText());
							if (k == (searchText.size() - 1)) {

								searchResults.put(searchText.get(i).getText(), liItems);
								break;
							}
							continue;
						} else if (temp.contains("head")) {
							searchResults.put(searchText.get(i).getText().replace("more", ""), liItems);
							break;
						}

					}
				}
				i = k;
			} while (i < searchText.size());

			/*
			 * for (Map.Entry<String, List<String>> entry :
			 * searchResults.entrySet()) { String key =
			 * entry.getKey().toString(); List<String> lis = entry.getValue();
			 * System.out.println("key, " + key + " value " + lis); }
			 */
		} catch (Exception e) {

		}
		return searchResults;
	}
	
	public boolean isSearchBarDisplayed() {
		boolean flag = false;
		try {
			flag = searchObjects.getSearchBar().isDisplayed();

		} catch (NoSuchElementException e) {

		}
		return flag;
	}

}
