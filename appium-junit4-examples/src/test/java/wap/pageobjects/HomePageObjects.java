package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import static common.launchsetup.BaseTest.driver;

/**
 * 
 * Home Page Object class WAP
 *
 */
public class HomePageObjects {

	@FindBy(xpath = "//*[contains(@class,'menuIcon') or contains(@class,'menu-icon')]")
	private WebElement menuIcon;

	@FindBy(css = ".scrollMenu a")
	private List<WebElement> homeSubMenu;

	@FindBy(xpath = "//*[@id=\"header\"]/section/div[3]")
	private WebElement homeSearchIcon;

	@FindBy(xpath = "//*[@id=\"marketStrip\"]/ul")
	private List<WebElement> marketBand;

	@FindBy(id = "marketNews")
	private WebElement homeMarketsSection;

	@FindBy(xpath = "//*[@id=\"marketNews\"]/h2/a")
	private WebElement homeMarketsHeading;

	@FindBys({ @FindBy(id = "marketNews"), @FindBy(className = "newsList") })
	private List<WebElement> homeMarketsNews;

	@FindBy(xpath = "//*[@id=\"marketNews\"]/ul[1]/li/a")
	private List<WebElement> marketNews;

	@FindBy(xpath = "//*[@id=\"subMenu\"]/ul/li[2]/a")
	private WebElement marketsHighlighted;

	@FindBy(id = "politics")
	private WebElement homePoliticsSection;

	@FindBy(xpath = "//*[@id=\"politics\"]/h2/a")
	private WebElement homePoliticsHeading;

	@FindBys({ @FindBy(id = "politics"), @FindBy(className = "newsList") })
	private List<WebElement> homePoliticsNews;

	@FindBy(xpath = "//*[@id=\"politics\"]/ul[1]/li/a")
	private List<WebElement> politicsNews;

	@FindBy(xpath = "//*[@id=\"subMenu\"]/ul/li[7]/a")
	private WebElement politicsHighlighted;

	@FindBy(id = "newsByIndustry")
	private WebElement homeIndustrySection;

	@FindBy(xpath = "//*[@id=\"industryContent\"]/ul[1]/li/a")
	private List<WebElement> industryNews;

	@FindBy(xpath = "//*[@id=\"newsByIndustry\"]/h2/a")
	private WebElement homeIndustryHeading;

	@FindBy(xpath = "//*[@id=\"subMenu\"]/ul/li[1]/a")
	private WebElement industryHighlighted;

	@FindBys({ @FindBy(id = "newsByIndustry"), @FindBy(className = "newsList") })
	private List<WebElement> homeIndustryNews;

	@FindBy(id = "wealth")
	private WebElement homeWealthSection;

	@FindBy(xpath = "//*[@id=\"wealth\"]/h2/a")
	private WebElement homeWealthHeading;

	@FindBy(xpath = "//*[@id=\"subMenu\"]/ul/li[1]/a")
	private WebElement wealthHighlighted;

	@FindBys({ @FindBy(id = "wealth"), @FindBy(className = "newsList") })
	private List<WebElement> homeWealthNews;

	@FindBy(id = "mf")
	private WebElement homeMFSection;

	@FindBy(xpath = "//*[@id=\"mf\"]/h2/a")
	private WebElement homeMFHeading;

	@FindBy(xpath = "//*[@id=\"subMenu\"]/ul/li[1]/a")
	private WebElement MFHighlighted;

	@FindBys({ @FindBy(id = "mf"), @FindBy(className = "newsList") })
	private List<WebElement> homeMFNews;

	@FindBy(id = "smallBiz")
	private WebElement homeSmallBizSection;

	@FindBy(xpath = "//*[@id=\"smallBiz\"]/h2/a")
	private WebElement homeSmallBizHeading;

	@FindBy(xpath = "//*[@id=\"subMenu\"]/ul/li[1]/a")
	private WebElement smallBizHighlighted;

	@FindBys({ @FindBy(id = "smallBiz"), @FindBy(className = "newsList") })
	private List<WebElement> homeSmallBizNews;

	@FindBy(id = "tech")
	private WebElement homeTechSection;

	@FindBy(xpath = "//*[@id=\"tech\"]/h2/a")
	private WebElement homeTechHeading;

	@FindBy(xpath = "//*[@id=\"subMenu\"]/ul/li[1]/a")
	private WebElement techHighlighted;

	@FindBys({ @FindBy(id = "tech"), @FindBy(className = "newsList") })
	private List<WebElement> homeTechNews;

	@FindBy(id = "panache")
	private WebElement homePanacheSection;

	@FindBy(xpath = "//*[@id=\"panache\"]/h2/a")
	private WebElement homePanacheHeading;

	@FindBy(xpath = "//*[@id=\"subMenu\"]/ul/li[1]/a")
	private WebElement panacheHighlighted;

	@FindBys({ @FindBy(id = "panache"), @FindBy(className = "newsList") })
	private List<WebElement> homePanacheNews;

	@FindBy(id = "economy")
	private WebElement homeEconomySection;

	@FindBy(xpath = "//*[@id=\"economy\"]/h2/a")
	private WebElement homeEconomyHeading;

	@FindBy(xpath = "//*[@id=\"subMenu\"]/ul/li[1]/a")
	private WebElement economyHighlighted;

	@FindBys({ @FindBy(id = "economy"), @FindBy(className = "newsList") })
	private List<WebElement> homeEconomyNews;

	@FindBy(className = "headName")
	private WebElement mainHeading;

	@FindBy(css = ".midCont a")
	private List<WebElement> slideshows;

	@FindBy(xpath = ".//*[@id='industryContent']//div[@class='moreText']")
	private WebElement readMoreTabIndustry;

	@FindBy(xpath = "//*[@id=\"industryContent\"]/ul[2]/li/a")
	private List<WebElement> industryMoreNews;

	@FindBy(xpath = "//*[@id='panacheContent']/ul[1]/li/a")
	private List<WebElement> panacheNews;

	@FindBy(xpath = ".//*[@id='panacheContent']/div[2]/div[2]")
	private WebElement readMoreTabPanache;

	@FindBy(xpath = "//*[@id='panacheContent']/ul[2]/li/a")
	private List<WebElement> panacheMoreNews;

	@FindBy(xpath = "//*[@id='industryMenu']/a")
	private List<WebElement> industrySubMenu;

	@FindBy(xpath = "//*[@id='panacheMenu']/ul/li")
	private List<WebElement> panacheSubMenu;

	@FindBy(xpath = "//*[@id='industryContent']/div[4]/a")
	private WebElement industryMoreNewsButtonAuto;

	@FindBy(xpath = "//*[@id='industryContent']/div/div[1]")
	private WebElement industryMoreNewsButton;

	@FindBy(xpath = "//*[@id='sensexData']/div[2]")
	private WebElement sensexStockChangeValue;

	@FindBy(xpath = "//p[contains(text(),'View All')]/ancestor::a[@data-sectiontype='Markets Stories']")
	private WebElement marketsMoreButton;

	@FindBy(xpath = "//a[string-length(@href)>0]")
	private List<WebElement> allHref;

	private WebElement genericMoreNewsButton;

	private WebElement homeSectionGeneric;

	private WebElement sectionHeading;

	private List<WebElement> sectionNews;

	private WebElement sectionReadMoreTab;

	private List<WebElement> sectionMoreNews;

	@FindBy(xpath = "//*[contains(@class,'sections')]//h4")
	private List<WebElement> spotlightSections;

	private List<WebElement> sectionHeadlines;

	@FindBy(id = "spotlight")
	private WebElement spotlightSection;

	@FindBy(css = ".marketData")
	private WebElement etMarkets;

	@FindBy(xpath = "//*[@class='marketData']/div[2]//ul//a")
	private List<WebElement> stocksList;

	@FindBy(xpath = "//*[contains(@class,'mktData')]/div[2]")
	private List<WebElement> marketData;

	@FindBy(xpath = "//*[@class='marketData']/div[2]//ul/a[1]")
	private WebElement sensexLink;

	@FindBy(xpath = "//*[@class='marketData']/div[2]//ul/a[2]")
	private WebElement niftyLink;

	@FindBy(xpath = "//*[@class='marketData']/div[2]//ul/a[3]")
	private WebElement goldLink;

	@FindBy(xpath = "//*[@class='marketData']/div[2]//ul/a[4]")
	private WebElement forexLink;

	@FindBy(xpath = "//*[@class='marketsEnd']/a")
	private WebElement allBenchMarkLink;

	@FindBy(xpath = "//*[@id='niftyData']/div[2]")
	private WebElement niftyStockChangeValue;

	@FindBy(xpath = "//*[@id='goldData']/div[2]")
	private WebElement goldStockChangeValue;

	@FindBy(xpath = "//*[@id='usdData']/div[2]")
	private WebElement forexValue;

	@FindBy(xpath = "//*[@id='marketMenu']/ul/li/a")
	private List<WebElement> marketsSubMenu;

	@FindBy(xpath = "//*[@class='stockName']/a")
	private List<WebElement> subMenuData;

	@FindBy(xpath = "//*[@class='marketsEnd']/a")
	private WebElement moreDataLink;

	@FindBy(xpath = "//*[@id='topMf']/h2/a")
	private WebElement mFWidget;

	@FindBy(xpath = "//*[@id='topMf']/h2/a")
	private WebElement mfHeading;

	@FindBy(xpath = "//*[@id='topMf']/div[1]/ul/li")
	private List<WebElement> mfSubSections;

	private List<WebElement> companiesLinks;

	@FindBy(xpath = "//*[contains(@class,'no-result')]/span[1]")
	private WebElement noResult;

	@FindBy(xpath = "//*[@id='panacheContent']/div[3]/a")
	private WebElement panacheMoreNewsButton;

	@FindBy(css = ".prime")
	private WebElement primeWidget;

	@FindBy(xpath = "//*[@class='prime']/h2/a")
	private WebElement primeSectionHeader;

	@FindBy(xpath = "//*[@class='prime']/ul//a")
	private List<WebElement> primeNewsList;

	@FindBy(xpath = "//*[@class='morePrimeText']/a")
	private WebElement primeMoreLink;

	@FindBy(xpath = "//li[@class='semibold']/a")
	private List<WebElement> topNews;

	@FindBy(xpath = "//li[@class='bigImage']/a")
	private WebElement bigImageNews;

	@FindBy(xpath = "//p/span[@id='hamberIcon']")
	private WebElement footerMenuIcon;

	@FindBy(xpath = "//a[contains(@href,'primeshow')]")
	private List<WebElement> allPrimeshowLinks;

	@FindBy(xpath = "//h2[text()='Multimedia']/following-sibling::ul/li/a")
	private List<WebElement> multimediaStoryLinks;

	@FindBy(xpath = "//div[@id='primeWidget_exclusive']//a[string-length()>0 and @data-sectiontype='Prime Widget']")
	private List<WebElement> primeStoryLinks;

	@FindBy(xpath = "//h2[text()='Podcasts']/following-sibling::ul/li/a")
	private List<WebElement> podcastStoryLinks;

	@FindBy(xpath = "//a[@data-sectiontype= 'Top News' or @data-sectiontype='Top Sub News'][string-length()>0]")
	private List<WebElement> allTopSectionStories;

	@FindBy(xpath = "//h2[text()='Spotlight']/following-sibling::div/ul/li/div[string-length(text())>0]")
	private List<WebElement> spotlightSectionsText;

	@FindBy(xpath = "//div[contains(@class,'_2w1AX') or @class='_1ldM0' or @class = '_1VaUF']//a")
	private List<WebElement> getMarketsWidgetLinks;

	@FindBy(xpath = "//p/span[@id='hamberIcon']")
	private WebElement pwaHamberMenuIcon;

	@FindBy(xpath = "//a[string-length()>0 and @data-sectiontype='ET Market Live Widget']")
	private List<WebElement> marketsWidgetTopStoriesLinks;

	@FindBy(xpath = "//section[@id='marketStrip']//ul//li//a")
	private List<WebElement> marketStripDataLinksOnMarketWidgetTopStories;

	@FindBy(xpath = "//section[@id='marketStrip']//ul//li//a//p[2]")
	private List<WebElement> marketStripIndexDataOnMarketWidgetTopStories;

	@FindBy(xpath = "//a[@data-sectiontype= 'START YOUR FREE TRIAL']")
	private WebElement startYourFreeTrialPrimeWidgetLink;
	////////////////// getters////////////////

	public WebElement getPwaHamberMenuIcon() {
		return pwaHamberMenuIcon;
	}

	public WebElement getFooterMenuIcon() {
		return footerMenuIcon;
	}

	public WebElement getMenuIcon() {
		return menuIcon;
	}

	public List<WebElement> getHomeSubMenu() {
		return homeSubMenu;
	}

	public WebElement getHomeSearchIcon() {
		return homeSearchIcon;
	}

	public WebElement getHomeMarketsSection() {
		return homeMarketsSection;
	}

	public WebElement getHomeMarketsHeading() {
		return homeMarketsHeading;
	}

	public List<WebElement> getHomeMarketsNews() {
		return homeMarketsNews;
	}

	public WebElement getHomePoliticsSection() {
		return homePoliticsSection;
	}

	public WebElement getHomePoliticsHeading() {
		return homePoliticsHeading;
	}

	public List<WebElement> getHomePoliticsNews() {
		return homePoliticsNews;
	}

	public WebElement getHomeIndustrySection() {
		return homeIndustrySection;
	}

	public WebElement getHomeIndustryHeading() {
		return homeIndustryHeading;
	}

	public List<WebElement> getHomeIndustryNews() {
		return homeIndustryNews;
	}

	public WebElement getHomeWealthSection() {
		return homeWealthSection;
	}

	public WebElement getHomeWealthHeading() {
		return homeWealthHeading;
	}

	public List<WebElement> getHomeWealthNews() {
		return homeWealthNews;
	}

	public WebElement getHomeMFSection() {
		return homeMFSection;
	}

	public WebElement getHomeMFHeading() {
		return homeMFHeading;
	}

	public WebElement getMFHighlighted() {
		return MFHighlighted;
	}

	public List<WebElement> getHomeMFNews() {
		return homeMFNews;
	}

	public WebElement getHomeSmallBizSection() {
		return homeSmallBizSection;
	}

	public WebElement getHomeSmallBizHeading() {
		return homeSmallBizHeading;
	}

	public List<WebElement> getHomeSmallBizNews() {
		return homeSmallBizNews;
	}

	public WebElement getHomeTechSection() {
		return homeTechSection;
	}

	public WebElement getHomeTechHeading() {
		return homeTechHeading;
	}

	public List<WebElement> getHomeTechNews() {
		return homeTechNews;
	}

	public WebElement getHomePanacheSection() {
		return homePanacheSection;
	}

	public WebElement getHomePanacheHeading() {
		return homePanacheHeading;
	}

	public List<WebElement> getHomePanacheNews() {
		return homePanacheNews;
	}

	public WebElement getHomeEconomySection() {
		return homeEconomySection;
	}

	public WebElement getHomeEconomyHeading() {
		return homeEconomyHeading;
	}

	public List<WebElement> getHomeEconomyNews() {
		return homeEconomyNews;
	}

	public WebElement getMainHeading() {
		return mainHeading;
	}

	public List<WebElement> getSlideshows() {
		return slideshows;
	}

	public List<WebElement> getMarketNews() {
		return marketNews;
	}

	public List<WebElement> getPoliticsNews() {
		return politicsNews;
	}

	public List<WebElement> getIndustryNews() {
		return industryNews;
	}

	public WebElement getReadMoreTabIndustry() {
		return readMoreTabIndustry;
	}

	public List<WebElement> getIndustryMoreNews() {
		return industryMoreNews;
	}

	public List<WebElement> getPanacheNews() {
		return panacheNews;
	}

	public WebElement getReadMoreTabPanache() {
		return readMoreTabPanache;
	}

	public List<WebElement> getPanacheMoreNews() {
		return panacheMoreNews;
	}

	public WebElement getGenericMoreNewsButton(String section) {
		// genericMoreNewsButton = driver.findElement(By.xpath("//*[@id='" +
		// section + "']//a[contains(text(),'More from')]"));
		genericMoreNewsButton = driver.findElement(By
				.xpath("//p[contains(text(),'View All')]/ancestor::a[contains(@data-sectiontype,'" + section + "')]"));
		return genericMoreNewsButton;
	}

	public WebElement getHomeSectionGeneric(String section) {
		homeSectionGeneric = driver.findElement(By.xpath("//*[@id='" + section + "']"));
		return homeSectionGeneric;
	}

	public WebElement getSectionHeading(String section) {
		sectionHeading = driver.findElement(By.xpath("//*[@id='" + section + "']/h2/a"));
		return sectionHeading;
	}

	public List<WebElement> getIndustrySubMenu() {
		return industrySubMenu;
	}

	public WebElement getIndustryMoreNewsButtonAuto() {
		return industryMoreNewsButtonAuto;
	}

	public WebElement getIndustryMoreNewsButton() {
		return industryMoreNewsButton;
	}

	public List<WebElement> getSpotlightSections() {
		return spotlightSections;
	}

	//// li/a[@data-sectiontype='Economy']/*[string-length(text())>0]
	public List<WebElement> getSectionNews(String section) {
		sectionNews = driver
				.findElements(By.xpath("//li/a[@data-sectiontype='" + section + "']//*[string-length(text())>0]"));
		return sectionNews;
	}

	public List<WebElement> getSectionNewsLinks(String section) {
		sectionNews = driver.findElements(By.xpath("//li/a[@data-sectiontype='" + section + "']"));
		return sectionNews;
	}

	public WebElement getSectionReadMoreTab(String section) {
		sectionReadMoreTab = driver.findElement(By.xpath("//*[@id='" + section + "']/div[1]/div[2]"));
		return sectionReadMoreTab;
	}

	public List<WebElement> getSectionMoreNews(String section) {
		sectionMoreNews = driver.findElements(By.xpath("//*[@id='" + section + "']/ul[2]/li/a"));
		return sectionMoreNews;
	}

	public List<WebElement> getSectionHeadlines(int count) {
		sectionHeadlines = driver
				.findElements(By.xpath("(//*[contains(@class,'sections')]//h4)[" + count + "]/..//ul//a"));
		return sectionHeadlines;
	}

	public WebElement getSpotlightSection() {
		return spotlightSection;

	}

	public WebElement getEtMarkets() {
		return etMarkets;
	}

	public List<WebElement> getStocksList() {
		return stocksList;
	}

	public List<WebElement> getMarketData() {
		return marketData;
	}

	public WebElement getSensexLink() {
		return sensexLink;
	}

	public WebElement getNiftyLink() {
		return niftyLink;
	}

	public WebElement getGoldLink() {
		return goldLink;
	}

	public WebElement getForexLink() {
		return forexLink;
	}

	public WebElement getAllBenchMarkLink() {
		return allBenchMarkLink;
	}

	public WebElement getSensexStockChangeValue() {
		return sensexStockChangeValue;
	}

	public WebElement getNiftyStockChangeValue() {
		return niftyStockChangeValue;
	}

	public WebElement getGoldStockChangeValue() {
		return goldStockChangeValue;
	}

	public WebElement getForexValue() {
		return forexValue;
	}

	public List<WebElement> getMarketsSubMenu() {
		return marketsSubMenu;
	}

	public List<WebElement> getSubMenuData() {
		return subMenuData;
	}

	public WebElement getMoreDataLink() {
		return moreDataLink;
	}

	public WebElement getMFWidget() {
		return mFWidget;
	}

	public List<WebElement> getCompaniesLinks(String tabName) {
		companiesLinks = driver.findElements(By.xpath("//*[@id='topMf']//div[contains(@class,'" + tabName
				+ "') and not(contains(@style,'none'))]//li//a[contains(@href,'schemeid')]"));
		return companiesLinks;
	}

	public WebElement getNoResult(String tabName) {
		return driver.findElement(By.xpath("//*[@class='" + tabName + "']//div[contains(@class,'no-result')]/span[1]"));
	}

	public WebElement getMfHeading() {
		return mfHeading;
	}

	public List<WebElement> getMfSubSections() {
		return mfSubSections;
	}

	public List<WebElement> getPanacheSubMenu() {
		return panacheSubMenu;
	}

	public WebElement getPanacheMoreNewsButton() {
		return panacheMoreNewsButton;
	}

	public WebElement getMFWidgetTabDropDown(String mfTab) {
		return driver.findElement(By.xpath("//select[@class='" + mfTab.toLowerCase() + "']"));
	}

	public List<WebElement> getTabSubMenuList(String mfTab) {
		return driver.findElements(By.xpath("//select[@class='" + mfTab.toLowerCase() + "']/option"));
	}

	public WebElement getPrimeWidget() {
		return primeWidget;
	}

	public WebElement getPrimeSectionHeader() {
		return primeSectionHeader;
	}

	public List<WebElement> getPrimeNewsList() {
		return primeNewsList;
	}

	public WebElement getPrimeMoreLink() {
		return primeMoreLink;
	}

	public List<WebElement> getTopNews() {
		return topNews;
	}

	public WebElement getBigImageNews() {
		return bigImageNews;
	}

	public WebElement getMarketsMoreButton() {
		return marketsMoreButton;
	}

	public List<WebElement> getAllPrimeshowLinks() {
		return allPrimeshowLinks;
	}

	public List<WebElement> getAllHref() {
		return allHref;
	}

	public List<WebElement> getMultimediaStoryLinks() {
		return multimediaStoryLinks;
	}

	public List<WebElement> getPrimeWidgetStoryLinks() {
		return primeStoryLinks;
	}

	public WebElement getSliderWidgetSubSecLink(String mainSection, String section) {

		return driver.findElement(By.xpath("//h2[text()='" + mainSection
				+ "']/following-sibling::div/ul/li/span[contains(text(),\"" + section + "\")]"));
	}

	public List<WebElement> getSliderWidgetSubSecStoryLinks(String mainSection) {
		return driver.findElements(By.xpath("//ul//li//a[@data-sectiontype='" + mainSection + "']"));
	}

	public WebElement getSliderWidgetSubSectionMoreLink(String mainSection) {
		return driver.findElement(By.xpath("//h2[text()='" + mainSection
				+ "']/following-sibling::div//p[contains(text(),'View All')]/ancestor::a"));
	}

	public List<WebElement> getPodcastStoryLinks() {
		return podcastStoryLinks;
	}

	public List<WebElement> getAllTopSectionStories() {
		return allTopSectionStories;
	}

	public List<WebElement> getSpotlightSectionsText() {
		return spotlightSectionsText;
	}

	public List<WebElement> getSpotlightSubSectionLinks(String section) {
		return driver.findElements(By.xpath("//div[text()='" + section + "']/following-sibling::div/ul/li/a"));
	}

	public WebElement getTheMarketSubSectionTab(String section) {

		return driver.findElement(By.xpath("//li[@data-subsection='" + section + "']"));

	}

	public List<WebElement> getGetMarketsWidgetLinks() {
		return getMarketsWidgetLinks;
	}

	public List<WebElement> getMarketsWidgetTopStoriesLinks() {
		return marketsWidgetTopStoriesLinks;
	}

	public List<WebElement> getMarketStripDataLinksOnMarketWidgetTopStories() {
		return marketStripDataLinksOnMarketWidgetTopStories;
	}

	public List<WebElement> getMarketStripIndexDataOnMarketWidgetTopStories() {
		return marketStripIndexDataOnMarketWidgetTopStories;
	}

	public WebElement getStartYourFreeTrialPrimeWidgetLink() {
		return startYourFreeTrialPrimeWidgetLink;
	}
}
