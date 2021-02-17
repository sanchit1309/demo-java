package web.pagemethods;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import web.pageobjects.AuthorPageObjects;

public class AuthorPageMethods {

	private WebDriver driver;
	AuthorPageObjects authorPageObjects;

	public AuthorPageMethods(WebDriver driver) {
		this.driver = driver;
		authorPageObjects = PageFactory.initElements(driver, AuthorPageObjects.class);
	}

	public List<String> getAuthorArticlesHref() {
		List<String> hrefList = new LinkedList<>();
		try {
			hrefList = WebBaseMethods.getListHrefUsingJSE(authorPageObjects.getArticleList());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return hrefList;

	}

	public List<String> checkIfListIsUnique(List<String> hrefList) {
		List<String> dupLinks = new LinkedList<String>();
		try {
			dupLinks = VerificationUtil.isListUnique(hrefList);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return dupLinks;
	}

	public List<String> getAllErrorLinks(List<String> hrefList) {
		List<String> errorLinks = new LinkedList<String>();
		System.out.println(hrefList);
		System.out.println(hrefList.size());
		hrefList.forEach(href -> {
			try {
				int response = HTTPResponse.checkResponseCode(href);
				System.out.println(href + "-------" + response);
				if (!(response == 200)) {
					errorLinks.add(href);
				}
			} catch (Exception ee) {
				ee.printStackTrace();
				errorLinks.add(href);
			}
		});
		return errorLinks;
	}

	public String getAuthorName() {
		String authorName = "";
		try {
			authorName = WebBaseMethods.getTextUsingJSE(authorPageObjects.getAuthorName());
		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return authorName;
	}
	
	public String getAuthorDescription() {
		String authorDesc = "";
		try {
			authorDesc = WebBaseMethods.getTextUsingJSE(authorPageObjects.getAuthorFullBio());
		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return authorDesc;
	}
	
	public LinkedHashMap<String, String> getOldArticlesShownOnThePage(int allowedDaysRange) {
		LinkedHashMap<String, String> oldArticles = new LinkedHashMap<>();
		try {
			LinkedHashMap<String, String> articleWIthDate = getArticleWithDate();
			Set<String> artLi = articleWIthDate.keySet();
			List<String> articleLi = new ArrayList<String>(artLi);
			for (int i = 0; i < 6; i++) {
				
				String article = "";
				String date = "";
				try{
				article = articleLi.get(i);
				
				date = articleWIthDate.get(article);
				if (!VerificationUtil.isLatest(date, allowedDaysRange)) {
					oldArticles.put(article, date);
				}
				}catch(Exception ee){
					ee.printStackTrace();
					oldArticles.put(article, date);
				}

			}
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return oldArticles;
	}

	public LinkedHashMap<String, String> getArticleWithDate() {
		LinkedHashMap<String, String> articlesWithDate = new LinkedHashMap<String, String>();
		try {
			List<WebElement> li = authorPageObjects.getArticleList();
			li.forEach(article -> {
				String articleText = "";
				String articleDate = "";

				articleText = WebBaseMethods.getTextUsingJSE(article);
				articleDate = WebBaseMethods.getTextUsingJSE(authorPageObjects.getDateTimeOfAticle(article));

				articlesWithDate.put(articleText, articleDate);

			});

		} catch (Exception ee) {
			ee.printStackTrace();
		}
		// articlesWithDate.forEach((key, value) -> {
		// System.out.println(key + "------" + value);
		//
		// });
		return articlesWithDate;
	}
	
	
}
