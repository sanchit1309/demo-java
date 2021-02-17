package prime.web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePageObjects {
	private WebDriver driver;

	public HomePageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//a[@id='pricePlan']")
	private WebElement startYourTrialBtn;
	
	@FindBy(xpath ="//a[@class='signInLink']")
	private WebElement signInBtn;

	@FindBy(xpath = "//div[@class='dib signInOut prel signOut']")
	private WebElement userLoggedIn;

	@FindBy(xpath = "//a[@id='pricePlan' and text()='Start Your Trial']")
	private WebElement startYourTrialBox;

	@FindBy(xpath = "//div[@class='dib signInOut prel signOut']/div[@class='signMenu']/a[text()='Logout']")
	private WebElement logoutBtn;

	@FindBy(xpath = "//a[@class='dib subScribe']")
	private WebElement subscribeButton;

	@FindBy(xpath = "//a[@id='signUpBtn' and text()='Sign up for Free Read']")
	private WebElement signUpForFreeReadBtn;

	@FindBy(xpath = "//a[@id='mmbr_btn']")
	private WebElement becomeMemberButton;
	
	@FindBy(xpath = "//div[@id='topStories']//a[@itemprop='url']")
	private List<WebElement> topStories;

	@FindBy(xpath = "//div[@id='topStories']//div[@class='time_blk']/span[@class='read_time']")
	private List<WebElement> minuteReadTopStories;

	@FindBy(xpath = "//div[@id='popularReadersSlider']")
	private WebElement popularWithReaderWidget;

	@FindBy(xpath = "//ul[@id='popularStories']/li//a")
	private List<WebElement> allLinksUnderPopularWithWidget;

	@FindBy(xpath = "//ul[@id='popularStories']/li")
	private List<WebElement> storiesUnderPopularWithWidget;

	@FindBy(xpath = "//div[@id='ctgryWrapper']")
	private WebElement topCategoriesWidget;

	@FindBy(xpath = "//div[@id='ctgryWrapper']//ul[@class='listing_wrapper']/li")
	private List<WebElement> topCategoriesCount;

	@FindBy(xpath = "//div[@class='rhs_wrapper hero_sbstry']/div[@pos='3' and @class='stry bg_black']")
	private WebElement blackbackgroundStoryOfTopStories;

	@FindBy(xpath = "//div[@class='hero_stry']//div[@itemprop='itemListElement']//h1/a")
	private WebElement heroStoryOfTopStories;

	@FindBy(xpath = "//div[@class='hero_stry']//div[@itemprop='itemListElement']/div[@class='time_blk']//span[@class='cSprite bookmark-icon flr']")
	private WebElement saveBtnOnHeroStoryOfTopStories;

	@FindBy(xpath = "//div[@class='hero_stry']//div[@itemprop='itemListElement']/div[@class='time_blk']//span[@class='cSprite bookmark-icon flr saved']")
	private WebElement unsaveBtnOnHeroStoryOfTopStories;

	@FindBy(xpath = "//div[@class='dib signInOut prel signOut']/div[@class='signMenu']/a[text()='Saved Stories']")
	private WebElement savedStoriesBtnUnderMenu;

	@FindBy(xpath = "//div[@id='topnavBlk']")
	private WebElement headerSection;
	
	@FindBy(xpath = "//div[@id='topnavBlk']/following-sibling::div[@class='sbnv_wrapper w1']")
	private WebElement subHeaderSection;

	@FindBy(xpath = "//div[@id='topnavBlk']//div[@id='sideMenu']")
	private WebElement hamburgerMenu;

	@FindBy(xpath = "//div[@class='nav_block clr']//nav[@id='sideBarNav']/ul/li")
	private List<WebElement> sideBarNavItems;

	@FindBy(xpath = "//div[@id='topnavBlk']//a[contains(@href,'/prime') and @class='navP medium cSprite_b current']")
	private WebElement currentPrimeHomeFromHeader;

	@FindBy(xpath = "//a[@id='etPrimeLogo']")
	private WebElement etPrimeLogo;

	@FindBy(xpath = "//div[@class='nav_block clr']//div[@id='searchBar']/span")
	private WebElement searchIconInHeader;

	@FindBy(xpath = "//div[@class='subheader_wrapper hide']//div[@class='flt']")
	private WebElement subHeaderWrapperText;

	@FindBy(xpath = "//div[@id='topnavBlk']/following-sibling::div[@class='sbnv_wrapper w1']//a[@itemprop='url']")
	private List<WebElement> categoryListInHeader;

	@FindBy(xpath = "//div[@id='primePlusBlock']")
	private WebElement primeStoriesWidget;

	@FindBy(xpath = "//div[@id='primePlusBlock']//*[@class='section_heading uc']")
	private WebElement recentStoriesText;
	
	@FindBy(xpath = "//div[@id='primePlusBlock']//a[@data-ga-onclick='More - href']")
	private WebElement morePrimeStoriesLink;

	@FindBy(xpath = "//div[@id='primePlusStories']//ul[@class='listing_wrapper']//a[starts-with(@href,'/prime')  and not(@class='pp_img') and not(@class='prel zi2')] ")
	private List<WebElement> articleOnRecentStories;
	
	@FindBy(xpath = "(//div[@id='primePlusStories']//ul[@class='listing_wrapper']//a[starts-with(@href,'/prime')  and not(@class='pp_img') and not(@class='prel zi2')])[1]")
	private WebElement heroStoryOfRecentStories;

	@FindBy(xpath = "(//div[@id='primePlusBlock']//div[@class='pp_mainstry_content']/div[@class='ml15']//span[@class='cSprite bookmark-icon flr'])[1]")
	private WebElement saveBtnOnFirstStoryOfPrimeWidgetStories;
	
	@FindBy(xpath = "(//div[@id='primePlusBlock']//div[@class='pp_mainstry_content']/div[@class='ml15']//span[@class='cSprite bookmark-icon flr saved'])[1]")
	private WebElement unsaveBtnOnFirstStoryOfRecentStories;

	@FindBy(xpath = "//div[@id='primePlusStories']//span[@ class='read_time']")
	private List<WebElement> minuteReadOnStoriesOfPrimeWidgetStories;

	@FindBy(xpath = "//a[@id='pricePlan' and text()='Start your Membership']")
	private WebElement startYourMembershipBox;
	
	@FindBy(xpath = "//ul[@id='popularStories']/li//span[@class='read_time']")
	private List<WebElement> minReadPopularWithReaders;
	
	@FindBy(xpath = "//ul[@id='popularStories']/li//a[@class='title font_faus']")
	private List<WebElement> articlePopularWithReaders;
	
	@FindBy(xpath = "//div[@id='popularReadersSlider']//h3[@class='section_heading fs40']")
	private WebElement popularWithReaderWidgetText;
	
	@FindBy(xpath = "//div[@id='popularReadersSlider']//span[@class='cSprite bookmark-icon flr'] | //div[@id='popularReadersSlider']//span[@class='cSprite bookmark-icon flr saved']")
	private List<WebElement> saveIconOnPopularWithReaders;
	
	@FindBy(xpath = "//div[@id='ctgryWrapper']//h3[@class='section_heading fs40']")
	private WebElement topCategoriesText;
	
	@FindBy(xpath ="//div[@id='ctgryWrapper']//ul[@class='listing_wrapper']/li//p[@class='name']")
	private List<WebElement> topCategoriesTitleName;
	
	@FindBy(xpath = "//div[@id='ctgryWrapper']//ul[@class='listing_wrapper']/li//p[@class='details']")
	private List<WebElement> topCategoriesTitleDetails;
	
	@FindBy(xpath = "//div[@id='ctgryWrapper']//ul[@class='listing_wrapper']/li//a")
	private List<WebElement> topCategoriesLinks;
	
	@FindBy(xpath = "//div[@class='btmFooter clearfix']//a")
	private List<WebElement> footerLinks;
	
	@FindBy(xpath = "//div[@id='authorsListing']")
	private WebElement authorsWidget;
	
	@FindBy(xpath = "//div[@id='authorsListing']//h3")
	private WebElement authorsWidgetText;
	
	@FindBy(xpath = "//div[@id='authorsListing']//ul[@class='listing_wrapper']/li/p[@class='name']/a")
	private List<WebElement> authorsNameListOnAuthorsWidget;
	
	@FindBy(xpath = "//div[@id='authorsListing']//ul[@class='listing_wrapper']/li/p[@class='designation']")
	private List<WebElement> authorsDesignationListOnAuthorsWidget;
	
	@FindBy(xpath = "//div[@id='authorsListing']//ul[@class='listing_wrapper']/li/p[@class='details']")
	private List<WebElement> authorsDetailsListOnAuthorsWidget;
	
	@FindBy(xpath = "//div[@id='authorsListing']//ul[@class='listing_wrapper']/li/img[@class='lazy']")
	private List<WebElement> authorsImageList;
	
	@FindBy(xpath = "//div[@class='readers_wrapper']")
	private WebElement esteemedReadersWidget;
	
	@FindBy(xpath = "//div[@class='readers_wrapper']/h3")
	private WebElement readersWidgetText;
	
	@FindBy(xpath = "//div[@class='readers_wrapper']/h5")
	private WebElement readersSubHeadingWidgetText;
	
	@FindBy(xpath = "//div[@class='readers_wrapper']/div[@class='tac']/div")
	private List<WebElement> readersList;
	
	@FindBy(xpath = "//div[@id='searchBar']")
	private WebElement searchIcon;
	
	@FindBy(xpath = "//div[@id='topStories']//div[@class='time_blk']/span[@class='read_time']/span[contains(text(),'By')]")
	private List<WebElement> authorsNameOnTopStoriesSection;
	
	@FindBy(xpath = "//div[@id='topStories']//a[@itemprop='url' and contains(@href,'primearticleshow')]")
	private List<WebElement> primeArticlesTopStories;
	
	@FindBy(xpath = "//div[@id='topStories']//a[@itemprop='url' and not(@data-hostid='318')]")
	private List<WebElement > premiumArtcilesTopStories;
	
	@FindBy(xpath = "//a[@class='audio_btn cSprite_b']/ancestor::div[@itemprop='itemListElement']//a[@itemprop='url']")
	private List<WebElement> audioSummaryStory;
	
	@FindBy(xpath = "//div[@id='topStories']//span[@class='read_time' and not(span)]/ancestor::div[@itemprop='itemListElement']//a[@itemprop='url']")
	private List<WebElement> nonAuthorStoryTopStories;
	
	@FindBy(xpath = "//div[@id='topStories']//span[@class='read_time']/span[@class='dot']/ancestor::div/div[@itemprop='itemListElement']/parent::div")
	private List<WebElement> allAuthorStoryTopStories;
	
	
	
	/* Getters */
	public WebElement getSignInBtn()
	{
		return signInBtn;
	}

	public WebElement getUserLoggedIn()
	{
		return userLoggedIn;
	}

	public WebElement getStartYourTrialBox()
	{
		return startYourTrialBox;
	}

	public WebElement getLogoutBtn()
	{
		return logoutBtn;
	}

	public WebElement getSubscribeButton()
	{
		return subscribeButton;
	}

	public List<WebElement> getTopStories()
	{
		return topStories;
	}

	public WebElement getSignUpForFreeReadBtn()
	{
		return signUpForFreeReadBtn;
	}

	public WebElement getPopularWithReaderWidget()
	{
		return popularWithReaderWidget;
	}

	public List<WebElement> getminuteReadTopStories()
	{
		return minuteReadTopStories;
	}

	public List<WebElement> getallLinksUnderPopularWithWidget()
	{
		return allLinksUnderPopularWithWidget;
	}

	public List<WebElement> getStoriesUnderPopularWithWidget()
	{
		return storiesUnderPopularWithWidget;
	}

	public WebElement getTopCategoriesWidget()
	{
		return topCategoriesWidget;
	}

	public List<WebElement> getTopCategoriesCount()
	{
		return topCategoriesCount;
	}

	public WebElement getBlackbackgroundStoryOfTopStories()
	{
		return blackbackgroundStoryOfTopStories;
	}

	public WebElement getHeroStoryOfTopStories()
	{
		return heroStoryOfTopStories;
	}

	public WebElement getSaveBtnOnHeroStoryOfTopStories()
	{
		return saveBtnOnHeroStoryOfTopStories;
	}

	public WebElement getUnsaveBtnOnHeroStoryOfTopStories()
	{
		return unsaveBtnOnHeroStoryOfTopStories;
	}

	public WebElement getSavedStoriesBtnUnderMenu()
	{
		return savedStoriesBtnUnderMenu;
	}

	public WebElement getHeaderSection()
	{
		return headerSection;
	}

	public List<WebElement> getSideBarNavItems()
	{
		return sideBarNavItems;
	}

	public WebElement getCurrentPrimeHomeFromHeader()
	{
		return currentPrimeHomeFromHeader;
	}

	public WebElement getETPrimeLogo()
	{
		return etPrimeLogo;
	}

	public WebElement getSearchIconInHeader()
	{
		return searchIconInHeader;
	}

	public WebElement getSubHeaderWrapperText()
	{
		return subHeaderWrapperText;
	}

	public WebElement getHamburgerMenu()
	{
		return hamburgerMenu;
	}

	public List<WebElement> getCategoryListInHeader()
	{
		return categoryListInHeader;
	}

	public WebElement getPrimeStoriesWidget()
	{
		return primeStoriesWidget;
	}

	public WebElement getMorePrimeStoriesLink()
	{
		return morePrimeStoriesLink;
	}

	public List<WebElement> getArticleOnRecentStories()
	{
		return articleOnRecentStories;
	}

	public WebElement getSaveBtnOnFirstStoryOfPrimeWidgetStories()
	{
		return saveBtnOnFirstStoryOfPrimeWidgetStories;
	}

	public List<WebElement> getMinuteReadOnStoriesOfPrimeWidgetStories()
	{
		return minuteReadOnStoriesOfPrimeWidgetStories;
	}


	public WebElement getStartYourMembershipBox()
	{
		return startYourMembershipBox;
	}

	public WebElement getStartYourTrialBtn() 
	{
		return startYourTrialBtn;
	}
	
	public WebElement getRecentStoriesText()
	{
		return recentStoriesText;
	}
	
	public WebElement getHeroStoryOfRecentStories()
	{
		return heroStoryOfRecentStories;
	}
	
	public WebElement getUnsaveBtnOnFirstStoryOfRecentStories()
	{
		return unsaveBtnOnFirstStoryOfRecentStories;
	}
	
	public List<WebElement> getMinReadPopularWithReaders()
	{
		return minReadPopularWithReaders;
	}
	
	public List<WebElement> getArticlePopularWithReaders()
	{
		return articlePopularWithReaders;
	}
	
	public WebElement getPopularWithReaderWidgetText()
	{
		return popularWithReaderWidgetText;
	}
	
	public List<WebElement> getSaveIconOnPopularWithReaders()
	{
		return saveIconOnPopularWithReaders;
	}
	
	public WebElement getTopCategoriesText()
	{
		return topCategoriesText;
	}
	
	public List<WebElement> getTopCategoriesTitleName()
	{
		return topCategoriesTitleName;
	}
	
	public List<WebElement> getTopCategoriesTitleDetails()
	{
		return topCategoriesTitleDetails;
	}
	
	public List<WebElement> getTopCategoriesLinks()
	{
		return topCategoriesLinks;
	}
	
	public WebElement getCategoryWidgetByName(String name)
	{
		return driver.findElement(By.xpath("//h3[text()='"+name+"']/parent::div/parent::div"));
	}
	
	public WebElement getCategoryTextByName(String name)
	{
		return getCategoryWidgetByName(name).findElement(By.xpath(".//h3"));
	}
	
	public WebElement getMoreFromLinkTextByName(String name)
	{
		return getCategoryWidgetByName(name).findElement(By.xpath(".//a[@data-ga-onclick='More - href']"));
	}
	
	public List<WebElement> getArticleLinksfromCategoryByName(String name)
	{
		return driver.findElements(By.xpath("//h3[text()='"+name+"']/parent::div/parent::div//a[@class='heading font_faus'] | //h3[text()='"+name+"']/parent::div/parent::div//a[@class='title font_faus']"));
	}
	
	public List<WebElement> getSubCategoryOfCategoryByName(String name)
	{
		return driver.findElements(By.xpath("//h3[text()='"+name+"']/parent::div/parent::div//a[@class='ctgry']"));
	}
	
	public List<WebElement> getMinReadCategoryByName(String name)
	{
		return driver.findElements(By.xpath("//h3[text()='"+name+"']/parent::div/parent::div//span[@class='read_time']"));
	}
	
	public List<WebElement> getSaveIconfromCategoryByName(String name)
	{
		return driver.findElements(By.xpath("//h3[text()='"+name+"']/parent::div/parent::div//span[@class='cSprite bookmark-icon flr']"));
	}
	
	public List<WebElement> getFooterLinks()
	{
		return footerLinks;
	}
	
	public WebElement getAuthorsWidget()
	{
		return authorsWidget;
	}
	
	public WebElement getAuthorsWidgetText()
	{
		return authorsWidgetText;
	}
	
	public List<WebElement> getAuthorsListOnAuthorsWidget()
	{
		return authorsNameListOnAuthorsWidget;
	}
	
	public List<WebElement> getAuthorsDesignationListOnAuthorsWidget()
	{
		return authorsDesignationListOnAuthorsWidget;
	}
	
	public List<WebElement> getAuthorsDetailsListOnAuthorsWidget()
	{
		return authorsDetailsListOnAuthorsWidget;
	}
	
	public List<WebElement> getAuthorsImageList()
	{
		return authorsImageList;
	}
	
	public WebElement getEsteemedReadersWidget()
	{
		return esteemedReadersWidget;
	}
	
	public WebElement getReadersWidgetText()
	{
		return readersWidgetText;
	}
	
	public WebElement getReadersSubHeadingWidgetText()
	{
		return readersSubHeadingWidgetText;
	}
	
	public List<WebElement> getReadersList()
	{
		return readersList;
	}
	
	public WebElement getBecomeMemberButton()
	{
		return becomeMemberButton;
	}
	
	public WebElement getSearchIcon()
	{
		return searchIcon;
	}
	
	public List<WebElement> getAuthorsNameOnTopStoriesSection()
	{
		return authorsNameOnTopStoriesSection;
	}
	
	public List<WebElement> getPrimeArticlesTopStories()
	{
		return primeArticlesTopStories;
	}
	
	public List<WebElement> getPremiumArticlesTopStories()
	{
		return premiumArtcilesTopStories;
	}
	
	public List<WebElement> getAudioSummaryStory()
	{
		return audioSummaryStory;
	}
	
	public List<WebElement> getNonAuthorStoryTopStories()
	{
		return nonAuthorStoryTopStories;
	}
	
	public List<WebElement> getAllAuthorStoryTopStories()
	{
		return allAuthorStoryTopStories;
	}
	
	public WebElement getAuthorNameFromStory(WebElement story)
	{
		return story.findElement(By.xpath(".//span[@class='dot']"));
	}
	
	public WebElement getStoryLinkFromStory(WebElement story)
	{
		return story.findElement(By.xpath(".//a[@itemprop='url']"));
	}
	
	public WebElement getSubHeaderSection()
	{
		return subHeaderSection;
	}
}
