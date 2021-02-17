package prime.web.pagemethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import prime.web.pageobjects.HomePageObjects;
import prime.web.pageobjects.MyLibraryPageObjects;
import web.pagemethods.WebBaseMethods;
import web.pageobjects.HeaderPageObjects;


public class HomePageMethods {
	private WebDriver driver;
	private HomePageObjects homePageObjects;
	private HeaderPageObjects headerObjects;
	private MyLibraryPageObjects myLibraryPageObjects;

	public HomePageMethods(WebDriver driver) {
		this.driver = driver;
		homePageObjects = PageFactory.initElements(driver, HomePageObjects.class);
		headerObjects = PageFactory.initElements(driver, HeaderPageObjects.class);
		myLibraryPageObjects = PageFactory.initElements(driver, MyLibraryPageObjects.class);
	}

	public boolean isBecomeAMemberButtonVisible() {
		return WebBaseMethods.isElementVisible(homePageObjects.getStartYourTrialBtn());
	}

	public boolean clickStartYourTrialBtn() {
		boolean flag= false;
		try {
			isBecomeAMemberButtonVisible();
			WebBaseMethods.clickElementUsingJSE(homePageObjects.getStartYourTrialBtn());
			flag= true;
		} catch (NoSuchElementException ee) {

		}
		return flag;
	}
	public boolean clickOnSignInBtn()
	{
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(homePageObjects.getSignInBtn());
			flag = true;
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

//	public boolean signInOnly(String email, String password) {
//		boolean signInFlag = false;
//		try {
//			if(clickOnSignInBtn())
//			{
//				WaitUtil.sleep(2000);
//				if (WebBaseMethods.isElementVisible(etMainLoginPageObjects.getSignInWithEmailButton())) {
//					WebBaseMethods.clickElementUsingJSE(etMainLoginPageObjects.getSignInWithEmailButton());
//					WebBaseMethods.clickElementUsingJSE(etMainLoginPageObjects.getEmailTxtBox());
//					etMainLoginPageObjects.getEmailTxtBox().sendKeys(email);
//					WaitUtil.sleep(2000);
//					WebBaseMethods.clickElementUsingJSE(etMainLoginPageObjects.getContinueButton());
//					WaitUtil.sleep(3000);
//					WaitUtil.explicitWaitByVisibilityOfElement(driver, 5, etMainLoginPageObjects.getPasswordTxtBx());
//					etMainLoginPageObjects.getPasswordTxtBx().sendKeys(password);
//					WaitUtil.sleep(2000);
//					WebBaseMethods.clickElementUsingJSE(etMainLoginPageObjects.getContinueBtnLogin());
//					WaitUtil.sleep(3000);
//					signInFlag = WebBaseMethods.isDisplayed(homePageObjects.getUserLoggedIn());
//				} else {
//					signInFlag = false;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			signInFlag = false;
//		}
//		return signInFlag;
//	}

	public boolean signOut() {
		boolean signOutFlag = false;
		try {
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 10, homePageObjects.getUserLoggedIn());
			WebBaseMethods.clickElementUsingJSE(homePageObjects.getLogoutBtn());
			WaitUtil.sleep(3000);
			if(WebBaseMethods.isDisplayed(homePageObjects.getSignInBtn(),10))
				signOutFlag = true;
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return signOutFlag;
	}


	public boolean isStartYourTrialBoxDisplayed()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isElementVisible(homePageObjects.getStartYourTrialBox(), 4);

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isStartYourMembershipBoxDisplayed()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isElementVisible(homePageObjects.getStartYourMembershipBox(), 4);

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isSubscribeButtonDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isElementVisible(homePageObjects.getSubscribeButton(), 10);

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isSignUpForFreeReadButtonDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isElementVisible(homePageObjects.getSignUpForFreeReadBtn(), 4);

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public boolean isBecomeMemberButtonDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isElementVisible(homePageObjects.getBecomeMemberButton(), 5);

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isPopularWithReaderWidgetDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isElementVisible(homePageObjects.getPopularWithReaderWidget(), 15);

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isTopCategoriesWidgetDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isElementVisible(homePageObjects.getTopCategoriesWidget(), 5);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;

	}

	public int getTopStoriesCount()
	{
		int size = 0;
		try {
			size = homePageObjects.getTopStories().size();

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return size;
	}

	public Map<String, String> getArticleNameAndMinuteReadTopStories()
	{
		Map<String, String> articleNameAndMinuteRead = new HashMap<String, String>();
		try {

			List<WebElement> minuteReadTopStorieslist = homePageObjects.getminuteReadTopStories();
			List<WebElement> articleTopStorieslist = homePageObjects.getTopStories();
			for(int i=0;i<articleTopStorieslist.size();i++)
			{
				articleNameAndMinuteRead.put(articleTopStorieslist.get(i).getText(), minuteReadTopStorieslist.get(i).getText());
			}

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return articleNameAndMinuteRead;
	}

	public int getAuthorsCountOnTopStoriesSection()
	{
		int count = 0;
		try {
			count = homePageObjects.getAuthorsNameOnTopStoriesSection().size();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return count;
	}
	
	public Map<String, String> getArticleNameAndHrefsOfTopStories()
	{

		Map<String, String> urls = new LinkedHashMap<String, String>();
		homePageObjects.getTopStories().forEach(element -> {
			urls.put(element.getText(), element.getAttribute("href"));
		});
		return urls;
	}

	public boolean validateTopStoriesCountForLayouts(int layoutnumber)
	{
		boolean flag = false;
		try {
			switch(layoutnumber)
			{
			case 1:
				if(getTopStoriesCount() == 6)
					flag = true;
				break;
			case 2:
				if(getTopStoriesCount() == 8)
					flag= true;
				break;
			case 3:
				if(getTopStoriesCount() == 9 )
					flag = true;
				break;
			default:
				throw new Exception("Invalid Layout Number");
			}

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean validateTopStoriesCount()
	{
		boolean flag = false;
		try {
			if(getTopStoriesCount() == 6 || getTopStoriesCount() == 8 || getTopStoriesCount() == 9)
				flag = true;

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean validateBackgroundColorForTopStories()
	{
		boolean flag = true;
		try {

			if(getTopStoriesCount() == 8 || getTopStoriesCount() == 9)
			{
				flag = WebBaseMethods.isDisplayed(homePageObjects.getBlackbackgroundStoryOfTopStories());
			}
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public Map<String, String> getAllLinksUnderPopularWithReaderWidget()
	{
		Map<String, String> allLinks = new HashMap<String, String>();
		try {
			homePageObjects.getallLinksUnderPopularWithWidget().forEach(element -> {
				allLinks.put(element.getText(), element.getAttribute("href"));
			});

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return allLinks;
	}

	public boolean validateNumberOfStoriesUnderPopularWithReaders()
	{
		boolean flag = false;
		try {
			int numberOfStories = homePageObjects.getStoriesUnderPopularWithWidget().size();
			if(numberOfStories == 20)
				flag = true;

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean validatetopCategoriesCount()
	{
		boolean flag = false;
		try {
			int numberOfStories = homePageObjects.getTopCategoriesCount().size();
			if(numberOfStories == 12)
				flag = true;

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean  clickSavebtnOnHeroStoryOfTopStories()
	{
		boolean flag = false;
		try {

			if(WebBaseMethods.isDisplayed(homePageObjects.getSaveBtnOnHeroStoryOfTopStories(),5))
			{
				WebBaseMethods.clickElementUsingJSE(homePageObjects.getSaveBtnOnHeroStoryOfTopStories());
				WaitUtil.sleep(2000);
				driver.navigate().refresh();
			}
			else if(WebBaseMethods.isDisplayed(homePageObjects.getUnsaveBtnOnHeroStoryOfTopStories(), 5))
			{
				WebBaseMethods.clickElementUsingJSE(homePageObjects.getUnsaveBtnOnHeroStoryOfTopStories());
				WaitUtil.sleep(2000);
				driver.navigate().refresh();
				if(WebBaseMethods.isDisplayed(homePageObjects.getSaveBtnOnHeroStoryOfTopStories(),5))
				{
					WebBaseMethods.clickElementUsingJSE(homePageObjects.getSaveBtnOnHeroStoryOfTopStories());
					WaitUtil.sleep(2000);
					driver.navigate().refresh();
				}
				else
					throw new Exception ("Unable to Unsave and then Save a Story");
			}
			flag = WebBaseMethods.isDisplayed(homePageObjects.getUnsaveBtnOnHeroStoryOfTopStories());
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public String getStoryTitleOfHeroStoryOfTopStory()
	{
		String title ="";
		try {
			title = homePageObjects.getHeroStoryOfTopStories().getText();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return title;
	}

	public boolean clickOnSavedStoriesButtonFromMenu()
	{
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(homePageObjects.getSavedStoriesBtnUnderMenu());
			WaitUtil.explicitWaitByElementToBeClickable(driver, 30, myLibraryPageObjects.getSaveBtn());
			if(driver.getCurrentUrl().equals("https://economictimes.indiatimes.com/bookmarkslist"))
				flag = true;
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isSaveButtonAppearingOnHeroStoryOfTopStories()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(homePageObjects.getSaveBtnOnHeroStoryOfTopStories(),5);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isHeaderSectionDisplaying()
	{
		boolean flag = false;
		boolean subHeaderFlag = false;
		try {
			flag = WebBaseMethods.isDisplayed(homePageObjects.getHeaderSection());
			subHeaderFlag = WebBaseMethods.isDisplayed(homePageObjects.getSubHeaderSection());

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag && subHeaderFlag;
	}

	public boolean validateHamburgerMenuInHeader()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(homePageObjects.getHamburgerMenu());
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isPrimeHomeTabSelected()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(homePageObjects.getCurrentPrimeHomeFromHeader());

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isETPrimeLogoAppearing()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(homePageObjects.getETPrimeLogo());
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isSearchIconDisplayingInHeaderSection()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(homePageObjects.getSearchIconInHeader());
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isSubHeaderWrapperTextAppearing()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(homePageObjects.getSubHeaderWrapperText());
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public String getSubHeaderWrapperText()
	{
		String txt="";
		try {
			WaitUtil.sleep(3000);
			boolean flag = isSubHeaderWrapperTextAppearing();
			if(flag)
				txt = homePageObjects.getSubHeaderWrapperText().getText();
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return txt;
	}

	public List<String> getCategoriesListFromHeaderSection()
	{
		List<String> categoryList = new ArrayList<String>();
		try {
			List<WebElement> catList = homePageObjects.getCategoryListInHeader();
			for(WebElement wb: catList)
				categoryList.add(WebBaseMethods.getTextUsingJSE(wb));


		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return categoryList;
	}

	public Map<String, String> getCategoriesTitlesAndHrefsFromHeaderSections() {
		Map<String, String> titleAndHrefs = new LinkedHashMap<String, String>();
		homePageObjects.getCategoryListInHeader().forEach(story -> {
			titleAndHrefs.put(story.getText(), story.getAttribute("href"));
		});
		return titleAndHrefs;
	}

	public boolean isPrimeStoriesWidgetDisplayed()
	{
		boolean flag = false;
		try {

			flag = WebBaseMethods.isDisplayed(homePageObjects.getPrimeStoriesWidget());
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isMorePrimeStoriesLinkDisplayed()
	{
		boolean flag = false;
		try {

			flag = WebBaseMethods.isDisplayed(homePageObjects.getMorePrimeStoriesLink());
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean checkMorePrimeStoriesLinkRedirection()
	{
		boolean flag = false;
		try {

			if(homePageObjects.getMorePrimeStoriesLink().getAttribute("href").contains("/prime/news"))
				flag = true;
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public Map<String, String> getStoryTitlesAndHrefsFromPrimeStorySection() {
		Map<String, String> titleAndHrefs = new LinkedHashMap<String, String>();
		homePageObjects.getArticleOnRecentStories().forEach(story -> {
			titleAndHrefs.put(story.getText(), story.getAttribute("href"));
		});
		return titleAndHrefs;
	}

	public boolean  clickSavebtnOnFirstStoryOfRecentStories()
	{
		boolean flag = false;
		try {

			if(WebBaseMethods.isDisplayed(homePageObjects.getSaveBtnOnFirstStoryOfPrimeWidgetStories(),5))
			{
				WebBaseMethods.clickElementUsingJSE(homePageObjects.getSaveBtnOnFirstStoryOfPrimeWidgetStories());
				WaitUtil.sleep(2000);
				driver.navigate().refresh();
			}
			else if(WebBaseMethods.isDisplayed(homePageObjects.getUnsaveBtnOnFirstStoryOfRecentStories(), 5))
			{
				WebBaseMethods.clickElementUsingJSE(homePageObjects.getUnsaveBtnOnFirstStoryOfRecentStories());
				WaitUtil.sleep(2000);
				driver.navigate().refresh();
				if(WebBaseMethods.isDisplayed(homePageObjects.getSaveBtnOnFirstStoryOfPrimeWidgetStories(),5))
				{
					WebBaseMethods.clickElementUsingJSE(homePageObjects.getSaveBtnOnFirstStoryOfPrimeWidgetStories());
					WaitUtil.sleep(2000);
					driver.navigate().refresh();
				}
				else
					throw new Exception ("Unable to Unsave and then Save a Story");
			}
			flag = WebBaseMethods.isDisplayed(homePageObjects.getUnsaveBtnOnFirstStoryOfRecentStories());
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public Map<String, String> getArticleNameAndMinuteReadPrimeWidgetStories()
	{
		Map<String, String> articleNameAndMinuteRead = new HashMap<String, String>();
		try {

			List<WebElement> minuteReadRecentStorieslist = homePageObjects.getMinuteReadOnStoriesOfPrimeWidgetStories();
			List<WebElement> articleRecentStorieslist = homePageObjects.getArticleOnRecentStories();
			for(int i=0;i<articleRecentStorieslist.size();i++)
			{
				articleNameAndMinuteRead.put(WebBaseMethods.getTextUsingJSE(articleRecentStorieslist.get(i)), WebBaseMethods.getTextUsingJSE(minuteReadRecentStorieslist.get(i)));
			}

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return articleNameAndMinuteRead;
	}
	
	public boolean validateRecentStoriesText()
	{
		boolean flag=false;
		try {
			WaitUtil.sleep(3000);
			if(homePageObjects.getRecentStoriesText().getText().equalsIgnoreCase("Recent Stories"))
				flag = true;
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public String getStoryTitleOfHeroStoryOfRecentStory()
	{
		String title ="";
		try {
			title = homePageObjects.getHeroStoryOfRecentStories().getText();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return title;
	}
	
	public boolean isSaveButtonAppearingOnHeroStoryOfRecentStories()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(homePageObjects.getSaveBtnOnFirstStoryOfPrimeWidgetStories(),2);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public Map<String, String> getArticleNameAndMinuteReadPopularWithReaderStories()
	{
		Map<String, String> articleNameAndMinuteRead = new HashMap<String, String>();
		try {

			List<WebElement> minuteReadPopularWithReaderStorieslist = homePageObjects.getMinReadPopularWithReaders();
			List<WebElement> articlePopularWithReaderStorieslist = homePageObjects.getArticlePopularWithReaders();
			for(int i=0;i<articlePopularWithReaderStorieslist.size();i++)
			{
				articleNameAndMinuteRead.put(WebBaseMethods.getTextUsingJSE(articlePopularWithReaderStorieslist.get(i)), WebBaseMethods.getTextUsingJSE(minuteReadPopularWithReaderStorieslist.get(i)));
			}

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return articleNameAndMinuteRead;
	}
	
	public boolean validatePopularWithReaderStoriesText()
	{
		boolean flag=false;
		try {
			if(homePageObjects.getPopularWithReaderWidgetText().getText().equalsIgnoreCase("POPULAR WITH READERS"))
				flag = true;
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public int getSaveIconCountOnPopularWithReaderSection()
	{
		int size = 0;
		try {
			size = homePageObjects.getSaveIconOnPopularWithReaders().size();

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return size;
	}
	
	public boolean validateTopCategoriesText()
	{
		boolean flag=false;
		try {
			if(homePageObjects.getTopCategoriesText().getText().equalsIgnoreCase("Top Categories"))
				flag = true;
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public List<String> getCategoriesListFromTopCategoriesSection()
	{
		List<String> categoryList = new ArrayList<String>();
		try {
			List<WebElement> catList = homePageObjects.getTopCategoriesTitleName();
			for(WebElement wb: catList)
				categoryList.add(WebBaseMethods.getTextUsingJSE(wb));


		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return categoryList;
	}
	
	public List<String> getCategoriesListDetailsFromTopCategoriesSection()
	{
		List<String> categoryList = new ArrayList<String>();
		try {
			List<WebElement> catList = homePageObjects.getTopCategoriesTitleDetails();
			for(WebElement wb: catList)
				categoryList.add(WebBaseMethods.getTextUsingJSE(wb));


		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return categoryList;
	}
	
	public Map<String, String> getTitlesAndHrefsFromTopStorySection() {
		Map<String, String> titleAndHrefs = new LinkedHashMap<String, String>();
		homePageObjects.getTopCategoriesLinks().forEach(story -> {
			titleAndHrefs.put(WebBaseMethods.getTextUsingJSE(story), story.getAttribute("href"));
		});
		return titleAndHrefs;
	}
	
	public boolean isCategoryAppearingOnHomePage(String category) {
		boolean flag = false;
		try {
			//flag = homePageObjects.getCategoryWidgetByName(category).isDisplayed();
			flag = WebBaseMethods.isDisplayed( homePageObjects.getCategoryWidgetByName(category));
			
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public String getCategoryTitle(String category) {
		String name = "";
		try {
			
			name = homePageObjects.getCategoryTextByName(category).getText();
			
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return name;
	}
	
	public String getCategoryMoreFromName(String category) {
		String name = "";
		try {
			
			name = homePageObjects.getMoreFromLinkTextByName(category).getText();
			
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return name;
	}
	
	public String getCategoryMoreLinkFromName(String category) {
		String name = "";
		try {
			
			name = homePageObjects.getMoreFromLinkTextByName(category).getAttribute("href");
			
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return name;
	}
	
	public Map<String, String> getTitlesAndHrefsFromDefaultCategorySection(String category) {
		Map<String, String> titleAndHrefs = new LinkedHashMap<String, String>();
		homePageObjects.getArticleLinksfromCategoryByName(category).forEach(story -> {
			titleAndHrefs.put(WebBaseMethods.getTextUsingJSE(story), story.getAttribute("href"));
		});
		return titleAndHrefs;
	}
	
	public int getTotalArticleCountFromDefaultCategorySection(String category) {
		return homePageObjects.getArticleLinksfromCategoryByName(category).size();
		
	}
	
	public Map<String, String> getTitlesAndHrefsForSubCategoryFromDefaultCategorySection(String category) {
		Map<String, String> titleAndHrefs = new LinkedHashMap<String, String>();
		homePageObjects.getSubCategoryOfCategoryByName(category).forEach(story -> {
			titleAndHrefs.put(WebBaseMethods.getTextUsingJSE(story), story.getAttribute("href"));
		});
		return titleAndHrefs;
	}
	
	public int getTotalSubCategoryCountFromDefaultCategorySection(String category) {
		return homePageObjects.getSubCategoryOfCategoryByName(category).size();
		
	}
	
	public Map<String, String> getArticleNameAndMinuteReadFromDefaultCategory(String category)
	{
		Map<String, String> articleNameAndMinuteRead = new HashMap<String, String>();
		try {

			List<WebElement> minuteReadList = homePageObjects.getMinReadCategoryByName(category);
			List<WebElement> articleList = homePageObjects.getArticleLinksfromCategoryByName(category);
			for(int i=0;i<articleList.size();i++)
			{
				articleNameAndMinuteRead.put(articleList.get(i).getText(), minuteReadList.get(i).getText());
			}

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return articleNameAndMinuteRead;
	}

	public int getSaveIconCountOnDefaultCategory(String category)
	{
		int size = 0;
		try {
			size = homePageObjects.getSaveIconfromCategoryByName(category).size();
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return size;
	}
	
	public Map<String, String> getFooterLinkHrefs()
	{
		WaitUtil.sleep(3000);
		Map<String, String> titleAndHrefs = new LinkedHashMap<String, String>();
		homePageObjects.getFooterLinks().forEach(story -> {
			titleAndHrefs.put(WebBaseMethods.getTextUsingJSE(story), story.getAttribute("href"));
		});
		return titleAndHrefs;
	}
	
	public boolean isAuthorsWidgetDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(homePageObjects.getAuthorsWidget());
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public String getAuthorsWidgetText()
	{
		String txt ="";
		try {
			txt = WebBaseMethods.getTextUsingJSE(homePageObjects.getAuthorsWidgetText());
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return txt;
	}
	
	public Map<String, String> getAuthorsNameAndHref()
	{
		Map<String, String> titleAndHrefs = new LinkedHashMap<String, String>();
		try {
			homePageObjects.getAuthorsListOnAuthorsWidget().forEach(story -> {
				titleAndHrefs.put(WebBaseMethods.getTextUsingJSE(story), story.getAttribute("href"));
			});
			
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return titleAndHrefs;
	}
	
	public List<String> getAuthorsDesignationList()
	{
		List<String> lst = new ArrayList<String>();
		try {
			homePageObjects.getAuthorsDesignationListOnAuthorsWidget().forEach(item -> {
				lst.add(WebBaseMethods.getTextUsingJSE(item));
			});
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return lst;
	}
	
	public Set<String> getAuthorsDetailsList()
	{
		Set<String> lst = new HashSet<String>();
		try {
			homePageObjects.getAuthorsDetailsListOnAuthorsWidget().forEach(item -> {
				lst.add(WebBaseMethods.getTextUsingJSE(item));
			});
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return lst;
	}

	public List<String> getAuthorsImageList()
	{
		List<String> lst = new ArrayList<String>();
		try {
			homePageObjects.getAuthorsImageList().forEach(item -> {
				lst.add(item.getAttribute("src"));
			});
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return lst;
	}
	
	public boolean isReadersWidgetDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(homePageObjects.getEsteemedReadersWidget());
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public String getReadersWidgetText()
	{
		String txt ="";
		try {
			txt = WebBaseMethods.getTextUsingJSE(homePageObjects.getReadersWidgetText());
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return txt;
	}
	
	public String getReadersSubHeadingWidgetText()
	{
		String txt ="";
		try {
			txt = WebBaseMethods.getTextUsingJSE(homePageObjects.getReadersSubHeadingWidgetText());
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return txt;
	}
	
	public int readersCountOnAuthorsWidget()
	{
		int count= 0;
		try {
			count= homePageObjects.getReadersList().size();
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return count;
	}
	
	public Map<String,String> getTopStoriesHref()
	{
		Map<String, String> titleAndHrefs = new HashMap<String, String>();
		try {
			homePageObjects.getTopStories().forEach(story -> {
				titleAndHrefs.put(WebBaseMethods.getTextUsingJSE(story), story.getAttribute("href"));
			});

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return titleAndHrefs;
	}
	
	public boolean clickingOnArticleNumberOfTopStories(int number)
	{
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(homePageObjects.getTopStories().get(number));
			flag = true;
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public Map<String,String> getPrimeArticlesTopStories()
	{
		Map<String, String> titleAndHrefs = new HashMap<String, String>();
		try {
			homePageObjects.getPrimeArticlesTopStories().forEach(story -> {
				titleAndHrefs.put(WebBaseMethods.getTextUsingJSE(story), story.getAttribute("href"));
			});

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return titleAndHrefs;
	}
	
	public Map<String,String> getPremiumArticlesTopStories()
	{
		Map<String, String> titleAndHrefs = new HashMap<String, String>();
		try {
			homePageObjects.getPremiumArticlesTopStories().forEach(story -> {
				titleAndHrefs.put(WebBaseMethods.getTextUsingJSE(story), story.getAttribute("href"));
			});

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return titleAndHrefs;
	}
	
	public boolean isAudioSummaryAppearingOnHomePage()
	{
		boolean flag = false;
		try {
			if(homePageObjects.getAudioSummaryStory().size()>0)
				flag = true;
			
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public Map<String,String> getAudioSummaryArticlesOnTopStories()
	{
		Map<String, String> titleAndHrefs = new HashMap<String, String>();
		try {
			homePageObjects.getAudioSummaryStory().forEach(story -> {
				titleAndHrefs.put(WebBaseMethods.getTextUsingJSE(story), story.getAttribute("href"));
			});

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return titleAndHrefs;
	}
	
	public boolean isArticleWithNoAuthorAppearingOnTopStoriesHomePage()
	{
		boolean flag = false;
		try {
			if(homePageObjects.getNonAuthorStoryTopStories().size()>0)
				flag = true;
			
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public Map<String,String> getArticlesWithNoAuthorOnTopStories()
	{
		Map<String, String> titleAndHrefs = new HashMap<String, String>();
		try {
			homePageObjects.getNonAuthorStoryTopStories().forEach(story -> {
				titleAndHrefs.put(WebBaseMethods.getTextUsingJSE(story), story.getAttribute("href"));
			});

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return titleAndHrefs;
	}
	
	public boolean isArticleWithAuthorAppearingOnTopStoriesHomePage()
	{
		boolean flag = false;
		try {
			WaitUtil.sleep(5000);
			if(homePageObjects.getAllAuthorStoryTopStories().size()>0)
				flag = true;
			
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public Map<String,String> getArticlesWithAuthorOnTopStories()
	{
		Map<String, String> titleAndHrefs = new HashMap<String, String>();
		try {
			homePageObjects.getAllAuthorStoryTopStories().forEach(story -> {
				titleAndHrefs.put(WebBaseMethods.getTextUsingJSE(homePageObjects.getStoryLinkFromStory(story)), homePageObjects.getStoryLinkFromStory(story).getAttribute("href"));
			});

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return titleAndHrefs;
	}
	
	public Map<String,String> getAuthorNameAndStoryOnTopStories()
	{
		Map<String, String> authorNameAndHrefs = new LinkedHashMap<String, String>();
		try {
			homePageObjects.getAllAuthorStoryTopStories().forEach(story -> {
				
				authorNameAndHrefs.put(WebBaseMethods.getTextUsingJSE(homePageObjects.getAuthorNameFromStory(story)), homePageObjects.getStoryLinkFromStory(story).getAttribute("href"));
			});

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return authorNameAndHrefs;
	}

}
