package wap.pagemethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import wap.pageobjects.StoryPageObjects;
import web.pagemethods.WebBaseMethods;

public class StoryPageMethods {

	private WebDriver driver;
	private StoryPageObjects storyPageObjects;
	private LoginPageMethods loginPageMethods;

	public StoryPageMethods(WebDriver driver) {
		this.driver = driver;
		loginPageMethods = new LoginPageMethods(driver);
		storyPageObjects = PageFactory.initElements(driver, StoryPageObjects.class);
	}

	public boolean verifyVisibilityOfFbShareIcon() {
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 5, storyPageObjects.getFbShareIcon());
		if (storyPageObjects.getFbShareIcon().isDisplayed())
			return true;
		else
			return false;
	}

	public boolean verifyFbLogin(String email, String password) {
		boolean flag = false;
		try {
			storyPageObjects.getFbShareIcon().click();
			/*if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
				WebBaseMethods.switchToChildWindow(5);*/
			//WaitUtil.explicitWaitByVisibilityOfElement(driver, 40, loginPageMethods.getFbEmailField());
			loginPageMethods.facebookActivity(email, password);
			WaitUtil.sleep(3000);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, storyPageObjects.getFbPostButton());
			if (storyPageObjects.getFbPostButton().isDisplayed())
				flag = true;
		} catch (Exception e) {
			System.out.println(driver.getCurrentUrl());
		}
		return flag;
	}

	public boolean verifySharingMessage(String articleHeading, String fbArticleHeading) {
		boolean flag = false;
		boolean compare1 = false;
		boolean compare2 = false;
		try {
			compare1 = articleHeading.contains(fbArticleHeading);
			compare2 = fbArticleHeading.contains(articleHeading);
			if (compare1 || compare2) {
				flag = true;

			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari")) {
				WebBaseMethods.switchToParentClosingChilds();
			} else {
				driver.close();
				WaitUtil.sleep(5000);
				WAPHelper.genericSwitchToWebView((AppiumDriver<?>) driver);
			}
		}
		return flag;
	}

	public boolean verifyArticleSharingFacebook() {
		try {
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 30, storyPageObjects.getFbPostButton());
			storyPageObjects.getFbPostButton().click();
			if (driver.getWindowHandles().size() > 1)
				return false;
			else {
				WebBaseMethods.switchToParentWindow();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebBaseMethods.closeAllExceptParentWindow();
			return false;
		}
	}

	public boolean verifyArticleSharingGoogle() {
		try {
			WaitUtil.explicitWaitByElementToBeClickable(driver, 50, storyPageObjects.getGooglePostButton());
			storyPageObjects.getGooglePostButton().click();
			WaitUtil.sleep(1000);
			if (driver.getWindowHandles().size() > 1)
				return false;
			else {
				WebBaseMethods.switchToParentWindow();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebBaseMethods.closeAllExceptParentWindow();
			return false;
		}
	}

	public boolean verifyArticleSharingLinkedIn() {
		boolean flag = false;
		try {
			storyPageObjects.getLinkedInPostButton().click();
			WaitUtil.sleep(1000);
			if (storyPageObjects.getLinkedInSuccessMessage().isDisplayed()) {
				flag = true;
				WebBaseMethods.closeAllExceptParentWindow();
				WebBaseMethods.switchToParentWindow();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (storyPageObjects.getLinkedInErrorMessage().isDisplayed()) {
				flag = false;
				WebBaseMethods.closeAllExceptParentWindow();
				WebBaseMethods.switchToParentWindow();
			}
		}
		return flag;
	}

	

	public String getArticleDate() {
		try {
			return storyPageObjects.getArticleDate().getText();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean verifyVisibilityOfLinkedInShareIcon() {
		WaitUtil.sleep(2000);
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 5, storyPageObjects.getLinkedInSharIcon());
		if (storyPageObjects.getLinkedInSharIcon().isDisplayed())
			return true;
		else
			return false;
	}

	public boolean verifyLinkedInLogin(String email, String password) {
		if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
			WAPHelper.getWebViewCount((AppiumDriver<?>) driver);

		boolean flag = false;
		try {
			storyPageObjects.getLinkedInSharIcon().click();
			WaitUtil.sleep(2000);
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
				WebBaseMethods.switchToChildWindow(5);
			else
				WAPHelper.waitForOpenWebContextChange(5,(AppiumDriver<?>) driver);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageMethods.getLinkedInEmailField());
			loginPageMethods.linkedInActivity(email, password);
			WaitUtil.sleep(2000);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, storyPageObjects.getLinkedInPostButton());
			if (storyPageObjects.getLinkedInPostButton().isDisplayed())
				flag = true;

		} catch (Exception e) {
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari")) {
				WebBaseMethods.switchToParentClosingChilds();
			} else {
				WaitUtil.sleep(2000);
				WAPHelper.waitForOpenWebContextChange(5,(AppiumDriver<?>) driver);
			}

		}

		return flag;
	}

	public boolean verifyVisibilityOfTwitterShareIcon() {
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 5, storyPageObjects.getTwitterShareIcon());
		if (storyPageObjects.getTwitterShareIcon().isDisplayed())
			return true;
		else
			return false;
	}

	public boolean verifyTwitterLogin(String email, String password) {
		boolean flag = false;
		loginPageMethods.twitterActivity(email, password);
		if (storyPageObjects.getTwitterLoggeInUserIcon().isDisplayed()) {
			flag = true;
		} else {
			flag = false;
			WebBaseMethods.closeAllExceptParentWindow();
		}
		WebBaseMethods.closeAllExceptParentWindow();
		return flag;
	}

	public boolean verifyArticleSharingTwitter() {
		boolean flag = false;
		try {
			if (driver.getWindowHandles().size() == 1) {
				flag = true;
				WebBaseMethods.switchToParentWindow();
			} else if (storyPageObjects.getTwitterSuccessMessage().isDisplayed()) {
				flag = true;
				WebBaseMethods.closeAllExceptParentWindow();
				WebBaseMethods.switchToParentWindow();
			}
		} catch (Exception e) {
			if (storyPageObjects.getTwitterErrorMessage().getText().equalsIgnoreCase("You have already sent this Tweet.")) {
				flag = true;
				WebBaseMethods.closeAllExceptParentWindow();
				WebBaseMethods.switchToParentWindow();
			} else {
				flag = false;
				WebBaseMethods.closeAllExceptParentWindow();
				WebBaseMethods.switchToParentWindow();
			}
		}
		return flag;
	}

	public boolean verifyVisibilityOfGPlusShareIcon() {
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 5, storyPageObjects.getGoogleShareIcon());
		if (storyPageObjects.getGoogleShareIcon().isDisplayed())
			return true;
		else
			return false;
	}

	public boolean verifyGPlusLogin(String email, String password) {
		boolean flag = false;
		if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
			WAPHelper.getWebViewCount((AppiumDriver<?>) driver);
		try {
			storyPageObjects.getGoogleShareIcon().click();
			WaitUtil.sleep(2000);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 40, loginPageMethods.getGPlusEmailField());
			if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari"))
				WebBaseMethods.switchToChildWindow(5);
			else
				WAPHelper.waitForOpenWebContextChange(5,(AppiumDriver<?>) driver);
			loginPageMethods.gplusActivity(email, password);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, storyPageObjects.getLinkedInPostButton());
			if (storyPageObjects.getGooglePostButton().isDisplayed())
				flag = true;
		} catch (Exception e) {
			System.out.println(driver.getCurrentUrl());

		}
		return flag;
	}

	public String getArticleHeading() {
		return storyPageObjects.getArticleHeading().getText();
	}

	public String getSharedHeading() {
		return storyPageObjects.getSharedHeading().getText();
	}

	public String getLinkedInArticleHeadline() {
		WaitUtil.sleep(2000);
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, storyPageObjects.getLinkedInArticleHeadline());
		return storyPageObjects.getLinkedInArticleHeadline().getText();
	}

	public void openTwitterShare() {
		storyPageObjects.getTwitterShareIcon().click();
		if (!((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().equalsIgnoreCase("safari")) {
			WebBaseMethods.switchToChildWindow(5);
		}
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, loginPageMethods.getTwitterEmailField());
	}

	public String getGoogleArticeHeadline() {
		return storyPageObjects.getGoogleArticeHeadline().getText();
	}

	public String getTwitterArticeHeadlineStr() {
		
		WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, storyPageObjects.getTwitterArticeHeadline());
		return storyPageObjects.getTwitterArticeHeadline().getText();
	}
	
	public String getTwitterArticeHeadline(String twtEmail, String twtPwd) {
		String articleHeadline="";
		try {
			WaitUtil.sleep(2000);
			storyPageObjects.getTwitterEmail().sendKeys(twtEmail);
			storyPageObjects.getTwitterPwd().sendKeys(twtPwd);
			WaitUtil.sleep(2000);
			WebBaseMethods.clickElementUsingJSE(storyPageObjects.getTwitterLogin());
			WaitUtil.sleep(3000);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 20, storyPageObjects.getTwitterArticeHeadline());
			articleHeadline=storyPageObjects.getTwitterArticeHeadline().getText();
		}catch(Exception e) {
			e.printStackTrace();
		}

		return articleHeadline;
	}
	
	public boolean clickReadMoreButton() {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(storyPageObjects.getReadMoreButton());
			flag = true;
			
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public Map<String, String> getPrimeArticleLink() {
		Map<String, String> url= new HashMap<>();
		try {
			url.put(storyPageObjects.getPrimeArticle().getAttribute("href"), storyPageObjects.getPrimeArticle().getText());
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}

	public boolean isPrimeArticleDisplayed() {
		boolean flag = false;
		try {
			WebBaseMethods.scrollElementIntoViewUsingJSE(storyPageObjects.getPrimeArticle());
			WebBaseMethods.clickElementUsingJSE(storyPageObjects.getReadMoreButton());
			flag = WebBaseMethods.isDisplayed(storyPageObjects.getPrimeArticle());
		}catch(NoSuchElementException e) {
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean isMoreFromETSectionDisplayed() {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(storyPageObjects.getReadMoreButton());
			flag = WebBaseMethods.isDisplayed(storyPageObjects.getMoreFromET());
		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public List<String> getMoreFromArticleLink() {
		List<String> url= new ArrayList<>();
		List<WebElement> element = storyPageObjects.getMoreFromET().findElements(By.xpath(".//a//h4"));
		try {
			element.forEach(ele->{
				url.add(ele.getText());

			});
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}

	public boolean isPromotedSectionDisplayed() {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(storyPageObjects.getReadMoreButton());
			WebBaseMethods.moveToElement(storyPageObjects.getPrimeArticle());
			flag = WebBaseMethods.isDisplayed(storyPageObjects.getPromotedStories());
		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public List<String> getPromotedArticleLink() {
		List<String> url= new ArrayList<>();
		List<WebElement> element = storyPageObjects.getMoreFromET().findElements(By.xpath(".//a//h4"));
		try {
			element.forEach(ele->{
				url.add(ele.getText());

			});
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}
	
	public boolean clickHeaderSharingButton() {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(storyPageObjects.getHeaderSharingIcon());
			flag = true;
			
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public List<WebElement> getSectionNewsHeadlines(String sectionDiv) {
		List<WebElement> sectionHeadlines = new ArrayList<>();
		// sectionHeadlines.addAll(risePageObjects.getImageLink(sectionDiv));
		try {
			sectionHeadlines.addAll(storyPageObjects.getSectionNews(sectionDiv));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sectionHeadlines;
	}
	
	public List<String> getSectionNewsHref(String section) {
		List<WebElement> news = new LinkedList<>();
		List<String> hrefList = new LinkedList<>();
		news.addAll(storyPageObjects.getSectionNewsHref(section));
		news.forEach(action -> {
			hrefList.add(action.getAttribute("href"));
		});
		System.out.println(hrefList);
		return hrefList;
	}
	
}