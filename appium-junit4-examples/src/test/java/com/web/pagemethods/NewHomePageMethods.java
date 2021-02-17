package com.web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;

import web.pageobjects.NewHomePageObjects;

public class NewHomePageMethods {
	private WebDriver driver;
	private NewHomePageObjects newHomePageObjects;

	public NewHomePageMethods(WebDriver driver) {
		this.driver = driver;
		newHomePageObjects = PageFactory.initElements(driver, NewHomePageObjects.class);

	}

	public List<String> getAllTopNewsHref() {
		List<String> topNewsHrefList = new LinkedList<>();
		try {
			topNewsHrefList = WebBaseMethods.getListHrefUsingJSE(newHomePageObjects.getTopStoriesListETHomepage());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return topNewsHrefList;

	}

	public List<String> getEtPrimeWidgetStoriesHref() {
		List<String> etPrimeWidgetStoriesHrefList = new LinkedList<>();
		try {
			etPrimeWidgetStoriesHrefList = WebBaseMethods
					.getListHrefUsingJSE(newHomePageObjects.getEtPrimeWidgetStoriesList());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return etPrimeWidgetStoriesHrefList;

	}

	public boolean clickFirstETFreeArticle() {
		boolean flag = false;
		List<WebElement> topStoriesFreeArticleLink = new LinkedList<>();
		try {
			topStoriesFreeArticleLink = newHomePageObjects.getTopStoriesFreeArticleListETHomepage();
			if (topStoriesFreeArticleLink.size() > 0) {
				flag =WebBaseMethods.clickElementUsingJSE(topStoriesFreeArticleLink.get(0));
				
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}

		return flag;
	}

	public boolean clickETPrimeNavBarLink() {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(newHomePageObjects.getEtPrimeNavBarLink());
			WaitUtil.sleep(2000);
			if (driver.getCurrentUrl().contains("prime"))
				flag = true;
		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;

		}
		return flag;
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

	public boolean checkStatusOfLinkIs200orNot(String href) {
		boolean flag = false;
		try {
			int response = HTTPResponse.checkResponseCode(href);
			if (response == 200) {
				flag = true;
			}
		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return flag;

	}

	public List<String> getHrefList(List<WebElement> weList) {
		List<String> hrefList = new LinkedList<String>();
		try {
			hrefList = WebBaseMethods.getListHrefUsingJSE(weList);
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

	public List<WebElement> getWealthSectionArticleshowLinks() {
		return newHomePageObjects.getWealthSectionArticleshowLinks();
	}

	public List<WebElement> getMutualFundSectionArticleshowLinks() {
		return newHomePageObjects.getMutualFundSectionArticleshowLinks();
	}

	public List<WebElement> getEconomySectionArticleshowLinks() {
		return newHomePageObjects.getEconomySectionArticleshowLinks();
	}

	public List<WebElement> getInternationalSectionArticleshowLinks() {
		return newHomePageObjects.getInternationalSectionArticleshowLinks();
	}

	public List<WebElement> getJobsSectionArticleshowLinks() {
		return newHomePageObjects.getJobsSectionArticleshowLinks();
	}

	public List<WebElement> getPanacheSectionArticleshowLinks() {
		return newHomePageObjects.getPanacheSectionArticleshowLinks();
	}

	public List<WebElement> getOpinonSectionArticleshowLinks() {
		return newHomePageObjects.getOpinonSectionArticleshowLinks();
	}

	public List<WebElement> getTopNewsUnderPrimeWidgetArticleshowLinks() {
		return newHomePageObjects.getTopNewsUnderPrimeWidgetArticleshowLinks();
	}

	public List<WebElement> getLatestNewsUnderPrimeWidgetArticleshowLinks() {
		return newHomePageObjects.getLatestNewsUnderPrimeWidgetArticleshowLinks();
	}

	public List<WebElement> getPodcastWidgetLinks() {
		return newHomePageObjects.getPodcastWidgetLinks();
	}

	public List<WebElement> getMostReadSectionArticleshowLinks() {
		return newHomePageObjects.getMostReadSectionArticleshowLinks();
	}

	public List<WebElement> getMostSharedSectionArticleshowLinks() {
		return newHomePageObjects.getMostSharedSectionArticleshowLinks();
	}

	public List<WebElement> getMostCommentedSectionArticleshowLinks() {
		return newHomePageObjects.getMostCommentedSectionArticleshowLinks();
	}

	public List<WebElement> getPoliticsSectionArticleshowLinks() {
		return newHomePageObjects.getPoliticsSectionArticleshowLinks();
	}

	public List<WebElement> getInterviewSectionArticleshowLinks() {
		return newHomePageObjects.getInterviewSectionArticleshowLinks();
	}

	public List<WebElement> getBlogsSectionArticleshowLinks() {
		return newHomePageObjects.getBlogsSectionArticleshowLinks();
	}

	public WebElement getMostReadSharedCommWidget_MostReadtab() {
		return newHomePageObjects.getMostReadSharedCommWidget_MostReadtab();
	}

	public WebElement getMostReadSharedCommWidget_MostSharedtab() {
		return newHomePageObjects.getMostReadSharedCommWidget_MostSharedtab();
	}

	public WebElement getMostReadSharedCommWidget_MostCommentedtab() {
		return newHomePageObjects.getMostReadSharedCommWidget_MostCommentedtab();
	}

	public List<WebElement> getTopTrendingTermLinks() {
		return newHomePageObjects.getTopTrendingTermLinks();
	}

	public List<WebElement> getTopStoriesListETHomepage() {
		return newHomePageObjects.getTopStoriesListETHomepage();
	}

	public List<WebElement> getEtPrimeWidgetStoriesList() {
		return newHomePageObjects.getEtPrimeWidgetStoriesList();
	}

	public List<WebElement> getTopStoriesFreeArticleListETHomepage() {
		return newHomePageObjects.getTopStoriesFreeArticleListETHomepage();
	}

	public WebElement getEtPrimeNavBarLink() {
		return newHomePageObjects.getEtPrimeNavBarLink();
	}

	public List<WebElement> getRiseSectionArticleshowLinks() {
		return newHomePageObjects.getRiseSectionArticleshowLinks();
	}

	public List<WebElement> getMarketsKeywordLinks() {
		return newHomePageObjects.getMarketsKeywordLinks();
	}

	public List<WebElement> getRiseKeywordLinks() {
		return newHomePageObjects.getRiseKeywordLinks();
	}

	public List<WebElement> getWealthKeywordLinks() {
		return newHomePageObjects.getWealthKeywordLinks();
	}

	public List<WebElement> getMutualFundKeywordLinks() {
		return newHomePageObjects.getMutualFundKeywordLinks();
	}

	public List<WebElement> getTopNewsKeywordsLinks() {
		return newHomePageObjects.getTopNewsKeywordsLinks();
	}

	public List<WebElement> getInformationTechnologySectionArticleshowLinks() {
		return newHomePageObjects.getInformationTechnologySectionArticleshowLinks();
	}

	public boolean clickLatestNewsTab() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(newHomePageObjects.getLatestNewsTab());
			 
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;

	}

	public boolean clickMostReadTab() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(newHomePageObjects.getMostReadSharedCommWidget_MostReadtab());
			 
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;

	}

	public boolean clickMostSharedTab() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(newHomePageObjects.getMostReadSharedCommWidget_MostSharedtab());
		
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;

	}

	public boolean clickMostCommentedTab() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(newHomePageObjects.getMostReadSharedCommWidget_MostCommentedtab());
			 
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;

	}

	public List<WebElement> getNotToBeMissedSectionLinks() {
		return newHomePageObjects.getNotToBeMissedSectionLinks();
	}

	public List<WebElement> getExpertViewsSectionLinks() {
		return newHomePageObjects.getExpertViewsSectionLinks();
	}

	public List<WebElement> getMarketsSectionArticleshowLinks() {
		return newHomePageObjects.getMarketsSectionArticleshowLinks();
	}

	public List<WebElement> getEtSpecailSectionArticleshowLinks() {
		return newHomePageObjects.getEtSpecailSectionArticleshowLinks();
	}

	public List<WebElement> getGreatReadsSectionArticleshowLinks() {
		return newHomePageObjects.getGreatReadsSectionArticleshowLinks();
	}

	public List<WebElement> getVideoSliderWidgetLinks() {
		return newHomePageObjects.getVideoSliderWidgetLinks();
	}

	public List<WebElement> getSlideshowSliderWidgetLinks() {
		return newHomePageObjects.getSlideshowSliderWidgetLinks();
	}

	public List<WebElement> getEditorsPickWidgetLinks() {
		return newHomePageObjects.getEditorsPickWidgetLinks();
	}

	public boolean clickEditorsPickNonActivePaginations() {
		boolean flag = false;
		try {
			WebElement paginationCarousel = newHomePageObjects.getEditorsPickNonActivePagination().get(0);
			WebBaseMethods.scrollElementIntoViewUsingJSE(paginationCarousel);
			flag =	WebBaseMethods.clickElementUsingJSE(paginationCarousel);

			 
		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return flag;

	}

	public List<WebElement> getPanacheSlideshowVideoshowSliderWidget() {
		return newHomePageObjects.getPanacheSlideshowVideoshowSliderWidget();
	}

	public List<WebElement> getNewsByIndustryWidgetTabs() {
		return newHomePageObjects.getNewsByIndustryWidgetTabs();
	}

	public List<WebElement> getNewsByIndustryWidgetActiveListArticleshowLinks() {
		return newHomePageObjects.getNewsByIndustryWidgetActiveListArticleshowLinks();
	}

	public List<WebElement> getSectionH2HeadingLinks() {
		return newHomePageObjects.getSectionH2HeadingLinks();
	}

	public List<WebElement> getSubsectionH4HeadingLinks() {
		return newHomePageObjects.getSubsectionH4HeadingLinks();
	}
	
	public List<WebElement> getSectionMoreLinks() {
		return newHomePageObjects.getSectionMoreLinks();
	}

	public boolean clickAndSwitchTOFirstETFreeArticle() {
		boolean flag = false;
		List<WebElement> topStoriesFreeArticleLink = new LinkedList<>();
		try {
			topStoriesFreeArticleLink = newHomePageObjects.getTopStoriesFreeArticleListETHomepage();
			if (topStoriesFreeArticleLink.size() > 0) {
				WebBaseMethods.clickElementUsingJSE(topStoriesFreeArticleLink.get(0));
				WaitUtil.sleep(2000);
				WebBaseMethods.switchChildIfPresent();
				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}

		return flag;
	}
	
	public boolean clickFirstETPremiumArticle() {
		boolean flag = false;
		List<WebElement> premiumArticleLink = new LinkedList<>();
		try {
			premiumArticleLink = newHomePageObjects.getEtPremiumArticleshowLinks();
			if (premiumArticleLink.size() > 0) {
				WebBaseMethods.clickElementUsingJSE(premiumArticleLink.get(0));
				WaitUtil.sleep(2000);
				WebBaseMethods.switchChildIfPresent();
				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}

		return flag;
	}
	
}
