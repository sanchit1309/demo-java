package web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import web.pageobjects.PanacheProfilePageObjects;

public class PanacheProfilePageMethods {

	public PanacheProfilePageObjects panacheProfilePageObjects;

	public PanacheProfilePageMethods(WebDriver driver) {

		panacheProfilePageObjects = PageFactory.initElements(driver, PanacheProfilePageObjects.class);

	}

	public String getProfileTitle() {
		String value = "";
		try {
			value = WebBaseMethods.getTextUsingJSE(panacheProfilePageObjects.getProfileTitle());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		System.out.println("Profile title:- " + value);
		return value;

	}

	public String getProfileDesignation() {
		String value = "";
		try {
			value = WebBaseMethods.getTextUsingJSE(panacheProfilePageObjects.getProfileDesignation());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		System.out.println("Profile designation:- " + value);
		return value;

	}

	public String getProfileBirthDetails() {
		String value = "";
		try {
			value = WebBaseMethods.getTextUsingJSE(panacheProfilePageObjects.getBirthDetails());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		System.out.println("Profile Birth details:- " + value);
		return value;

	}

	public String getProfileNationality() {
		String value = "";
		try {
			value = WebBaseMethods.getTextUsingJSE(panacheProfilePageObjects.getNationality());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		System.out.println("Profile Nationality:- " + value);
		return value;

	}

	public String getProfileLeague() {
		String value = "";
		try {
			value = WebBaseMethods.getTextUsingJSE(panacheProfilePageObjects.getLeague());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		System.out.println("Profile League:- " + value);
		return value;

	}

	public String getProfileEducation() {
		String value = "";
		try {
			value = WebBaseMethods.getTextUsingJSE(panacheProfilePageObjects.getEducation());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		System.out.println("Profile education:- " + value);
		return value;

	}

	public String getProfileNetWorth() {
		String value = "";
		try {
			value = WebBaseMethods.getTextUsingJSE(panacheProfilePageObjects.getNetWorth());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		System.out.println("Profile net worth:- " + value);
		return value;

	}

	public List<String> getProfileNewsStoryList() {
		List<String> li = new LinkedList();
		try {
			li = WebBaseMethods.getListHrefUsingJSE(panacheProfilePageObjects.getProfileNewsStoryList());
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return li;

	}

	public List<String> getProfileSummaryDetails() {
		List<String> li = new LinkedList();
		try {
			li = WebBaseMethods.getListTextUsingJSE(panacheProfilePageObjects.getProfileSummaryDetailsList());
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return li;

	}

	public List<String> getTrendingInProfile() {
		List<String> li = new LinkedList<String>();
		try {
			li = WebBaseMethods.getListHrefUsingJSE(panacheProfilePageObjects.getTrendingInProfilesWidget());
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return li;

	}

	public List<String> getBeforeYouGoContentList() {
		List<String> li = new LinkedList();
		try {
			li = WebBaseMethods.getListTextUsingJSE(panacheProfilePageObjects.getContentListUnderBeforeYouGo());
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		System.out.println(li);
		return li;

	}

	public List<String> getJourneySoFarContentList() {
		List<String> li = new LinkedList();
		try {
			li = WebBaseMethods.getListTextUsingJSE(panacheProfilePageObjects.getContentListUnderJourneySoFar());
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		System.out.println(li);
		return li;

	}

}
