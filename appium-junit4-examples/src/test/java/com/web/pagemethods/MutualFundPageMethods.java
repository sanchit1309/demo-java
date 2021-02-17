package com.web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import web.pageobjects.MutualFundsPageObjects;
import web.pageobjects.NewHomePageObjects;

public class MutualFundPageMethods {
	private WebDriver driver;
	private MutualFundsPageObjects mutualFundPageObjects;

	public MutualFundPageMethods(WebDriver driver) {
		this.driver = driver;
		mutualFundPageObjects = PageFactory.initElements(driver, MutualFundsPageObjects.class);

	}

	public List<String> getTopStoriesOnMutualFundsPage() {
		List<String> topNewsHrefList = new LinkedList<>();
		try {
			topNewsHrefList = WebBaseMethods
					.getListHrefUsingJSE(mutualFundPageObjects.getTopStoriesOnMutualFundsPage());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return topNewsHrefList;

	}

	public List<String> getMutualFundTopKeywords() {
		List<String> topMutualFundsHrefList = new LinkedList<>();
		try {
			topMutualFundsHrefList = WebBaseMethods
					.getListHrefUsingJSE(mutualFundPageObjects.getMutualFundTopKeywords());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return topMutualFundsHrefList;

	}

	public List<String> getMutualFundsLearnSectionLinks() {
		List<String> li = new LinkedList<>();
		try {
			li = WebBaseMethods.getListHrefUsingJSE(mutualFundPageObjects.getMutualFundsLearnSectionLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return li;

	}

	public List<String> getMutualFundsAnalysisSectionLinks() {
		List<String> li = new LinkedList<>();
		try {
			li = WebBaseMethods.getListHrefUsingJSE(mutualFundPageObjects.getMutualFundsAnalysisSectionLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return li;
	}

	public List<String> getMutualFundsNewsSectionLinks() {
		List<String> li = new LinkedList<>();
		try {
			li = WebBaseMethods.getListHrefUsingJSE(mutualFundPageObjects.getMutualFundsNewsSectionLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return li;
	}

	public List<String> getMutualFundNewsAndUpdates() {
		List<String> li = new LinkedList<>();
		try {
			li = WebBaseMethods.getListHrefUsingJSE(mutualFundPageObjects.getMutualFundNewsAndUpdates());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return li;
	}

	public String getSectionHeadingLink(String sectionName) {
		String href = "";
		try {
			href = WebBaseMethods.getHrefUsingJSE(mutualFundPageObjects.getTheSectionHeadingLinks(sectionName));
			System.out.println("Section heading is: " + href);
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return href;
	}

}
