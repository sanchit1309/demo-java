package prime.wap.pagemethods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.utilities.ApiHelper;
import common.utilities.WaitUtil;
import prime.wap.pageobjects.PrimeArticleShowPageObjects;
import web.pagemethods.WebBaseMethods;
import web.pageobjects.HeaderPageObjects;


public class PrimeArticleShowPageMethods {
	private WebDriver driver;
	private HeaderPageObjects headerObjects;
	private PrimeArticleShowPageObjects articleShowPageObjects;

	public PrimeArticleShowPageMethods(WebDriver driver) {
		this.driver = driver;
		headerObjects = PageFactory.initElements(driver, HeaderPageObjects.class);
		articleShowPageObjects = PageFactory.initElements(driver, PrimeArticleShowPageObjects.class);
	}

	public String getSubCategoryTitleText() {
		String title = "";
		try {
			title = articleShowPageObjects.getSubCategoryTitle().getText();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return title;
	}

	public boolean isPrimeIconAboveStoryTitleDisplayed() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(articleShowPageObjects.getPrimeIconAboveStoryTitle(), 5);

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public String getStoryTitleText() {
		String title = "";
		try {
			title = articleShowPageObjects.getStoryTitle().getText();
			
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return title;
	}

	public String getSynopsisText() {
		String txt = "";
		try {
			txt = articleShowPageObjects.getSynopsis().getText();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return txt;
	}

	public String getArticleText() {
		String txt = "";
		try {
			txt = articleShowPageObjects.getArticleBody().getText();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return txt;
	}

	public String getMinuteReadText() {
		String txt = "";
		try {
			txt= articleShowPageObjects.getMinRead().getText();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return txt;
	}


	public int  getShareArticleListCount() {
		int count = 0;
		try {
			count = articleShowPageObjects.getShareArticleList().size();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return count;
	}

	public List<String> getGiftCommentFontSaveTexts() {
		List<String> texts = new ArrayList<String> ();
		try {
			articleShowPageObjects.getGiftFontSaveCommentList().forEach(icon -> {
				texts.add(WebBaseMethods.getTextUsingJSE(icon));
			});
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return texts;
	}

	public boolean isPrimePaywalBlockerShown() {
		boolean flag = false;
		try {
			scrollToArticleBody();
			WaitUtil.sleep(3000);
			flag = WebBaseMethods.isDisplayed(articleShowPageObjects.getEtPrimePaywallBlocker(), 10);
		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;

		}
		return flag;

	}

	public boolean scrollToArticleBody() {
		boolean flag = false;
		try {
			WebBaseMethods.scrollingToElementofAPage(articleShowPageObjects.getArticleBody());
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isAddCommentButtonDisplayed() {
		boolean flag = false;
		try {
			WebBaseMethods.scrollingToElementofAPage(articleShowPageObjects.getPopularWithReaderSection());
			WaitUtil.sleep(3000);
			flag = WebBaseMethods.isDisplayed(articleShowPageObjects.getAddCommentbutton());

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isPopularWithReadersWidgetDisplayed() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(articleShowPageObjects.getPopularWithReaderSection(),5);

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isMoreStoriesWidgetDisplayed() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(articleShowPageObjects.getMoreStoriesTitle(),5);

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public String getMoreStoriesTitleText() {
		String txt = "";
		try {
			txt = articleShowPageObjects.getMoreStoriesTitle().getText();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return txt;
	}

	public boolean clickGiftArticleIcon() {
		boolean flag = false;
		try {
			if(WebBaseMethods.isDisplayed(articleShowPageObjects.getGiftIcon(),15))
			{
				flag = 	WebBaseMethods.clickElementUsingJSE(articleShowPageObjects.getGiftIcon());
				
			}

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isGiftPopupDisplayed() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(articleShowPageObjects.getGiftPopup(),20);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isGiftLimitReachedDisplayed() {
		boolean flag = false;
		try {
			if(WebBaseMethods.isDisplayed(articleShowPageObjects.getGiftLimitReach(),15))
				flag = true;
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean validateMaxLimitOfEmailInGifting()
	{
		boolean flag = false;
		String finalEmailsStored="";
		List<String> emails = new ArrayList<String>();
		try {
			int count = getRemainingGiftCount();
			if(count >= 10)
				count =11;
			else
				count = count+1;
			WaitUtil.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(articleShowPageObjects.getGiftArticleEmailFeild()));

			for(int i =0;i<count;i++)
			{
				WaitUtil.sleep(1000);
				String todayDatetime =new SimpleDateFormat("mmss").format(System.currentTimeMillis());
				String email = "etTest" + todayDatetime + "@gmail.com";

				WebBaseMethods.moveToElementAndClick(articleShowPageObjects.getGiftArticleEmailFeild());
				articleShowPageObjects.getGiftArticleEmailFeild().sendKeys(email);
				articleShowPageObjects.getGiftArticleEmailFeild().sendKeys(Keys.SPACE);
				emails.add(email);
			}
			WaitUtil.sleep(2000);
			finalEmailsStored = articleShowPageObjects.getGiftEmailTextBox().getText();
			for(String s : emails)
			{
				if(!finalEmailsStored.contains(s))
				{
					flag = true;
				}
			}

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean verifyUserGiftingAnArticle( String receiverEmail, String msg)
	{
		boolean flag = false;
		try {
			int count = getRemainingGiftCount();
			if(count >= 1)
			{
				WebBaseMethods.moveToElementAndClick(articleShowPageObjects.getGiftArticleEmailFeild());
				articleShowPageObjects.getGiftArticleEmailFeild().sendKeys(receiverEmail);
				articleShowPageObjects.getGiftArticleEmailFeild().sendKeys(Keys.SPACE);
				WebBaseMethods.clickElementUsingJSE(articleShowPageObjects.getGiftArticleMessge());
				articleShowPageObjects.getGiftArticleMessge().sendKeys(msg);
				WebBaseMethods.clickElementUsingJSE(articleShowPageObjects.getSendGiftButton());
				WaitUtil.sleep(3000);
				Alert alert =  driver.switchTo().alert();
				if(alert.getText().contains("Successfully gifted"))
					flag = true;
			}
			else
				throw new Exception("Gift Article Limit is Reached for the user");
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;

	}

	public boolean verifyUserEmailMoreThanOne() 
	{
		boolean flag = false;
		String finalEmailsStored="";
		int count = getRemainingGiftCount();
		List<String> emails = new ArrayList<String>();
		try {
			WaitUtil.sleep(1000);
			if(count > 0)
			{
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.elementToBeClickable(articleShowPageObjects.getGiftArticleEmailFeild()));
				WaitUtil.sleep(1000);
				String todayDatetime =new SimpleDateFormat("mmss").format(System.currentTimeMillis());
				String email = "etTest" + todayDatetime + "@zetmail.com";

				WebBaseMethods.moveToElementAndClick(articleShowPageObjects.getGiftArticleEmailFeild());
				articleShowPageObjects.getGiftArticleEmailFeild().sendKeys(email);
				articleShowPageObjects.getGiftArticleEmailFeild().sendKeys(Keys.SPACE);

				articleShowPageObjects.getGiftArticleEmailFeild().sendKeys(email);
				articleShowPageObjects.getGiftArticleEmailFeild().sendKeys(Keys.SPACE);
				WaitUtil.sleep(2000);
				finalEmailsStored = articleShowPageObjects.getGiftEmailTextBox().getText();

				if(finalEmailsStored.length()<30)
					flag = true;
				else
					flag = false;
			}

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

		return flag;
	}

	public boolean verifyUserGiftingArticleToHimself(String emailId)
	{
		boolean flag = false;
		String finalEmailsStored="";
		int count = getRemainingGiftCount();
		int value = 0;
		try {
			WaitUtil.sleep(1000);
			if(count > 0)
			{
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.elementToBeClickable(articleShowPageObjects.getGiftArticleEmailFeild()));
				WaitUtil.sleep(1000);
				WebBaseMethods.moveToElementAndClick(articleShowPageObjects.getGiftArticleEmailFeild());
				articleShowPageObjects.getGiftArticleEmailFeild().sendKeys(emailId);
				articleShowPageObjects.getGiftArticleEmailFeild().sendKeys(Keys.SPACE);
				WebBaseMethods.clickElementUsingJSE(articleShowPageObjects.getSendGiftButton());
				if(WebBaseMethods.getTextUsingJSE(articleShowPageObjects.getGiftToItselfTxt()).equals("You can't gift the article to yourself."))
					flag = true;
			}

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

		return flag;
	}
	public int getRemainingGiftCount()
	{
		int count = 0;
		try {
			String value = WebBaseMethods.getIntegerValueFromAlphanumericString(articleShowPageObjects.getRemainingGiftCount().getText());
			count = Integer.parseInt(value);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return count;
	}

	public boolean clickClosePopupBtn()
	{
		boolean flag = false;
		try {
			WebBaseMethods.isDisplayed(articleShowPageObjects.getClosePopUp(),10);
			WaitUtil.sleep(4000);
			flag =	WebBaseMethods.clickElementUsingJSE(articleShowPageObjects.getClosePopUp());
			 
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean verifyGiftArticleMessage()
	{
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(articleShowPageObjects.getGiftArticleMessge());
			articleShowPageObjects.getGiftArticleMessge().clear();
			WaitUtil.sleep(2000);
			String message = WebBaseMethods.getRandomCharactersUptoLength(301);
			articleShowPageObjects.getGiftArticleMessge().sendKeys(message);
			String val = articleShowPageObjects.getGiftArticleMessge().getAttribute("value");
			if(!(message.length() == val.length()))
				flag = true;
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean enterGiftArticleMessage(String msg)
	{
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(articleShowPageObjects.getGiftArticleMessge());
			articleShowPageObjects.getGiftArticleMessge().sendKeys(msg);
			flag = true;
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public String getGiftedEmailLink(String email, String storyUrl, String emailSubject, String message, long currentEpocTime) 
	{
		String giftUrl="";
		String strippedText="";
		try {
			String emailHTMLBody = ApiHelper.getEmailContentFromGetNada(email, emailSubject, currentEpocTime);
			if(emailHTMLBody.length() > 1)
			{
				String copyEmailHTMLBody = emailHTMLBody;
				strippedText = emailHTMLBody.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", "").replaceAll("\\n", "").replaceAll("\\t", "");
				List<String> ls = ApiHelper.extractUrls(copyEmailHTMLBody);
				if(!(strippedText.contains(message)))
					throw new Exception("Same Message is not received");
				for(String url: ls)
				{
					if(url.contains(storyUrl) && url.contains("transcode"))
					{
						giftUrl= url;
						break;
					}
				}
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return giftUrl;

	}

	public boolean isGiftPaywallDisplayed()
	{
		boolean flag = false;
		try {
			scrollToArticleBody();
			flag = WebBaseMethods.isDisplayed(articleShowPageObjects.getGiftPaywall(),10);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isPaywallAndTextsDisplayedForNonLoggedInUser()
	{
		boolean paywallDisplayed = false;
		List<String> articleBoxTexts = new ArrayList<String>();
		articleBoxTexts.add("To Read the Full Story, Become an ET Prime Member");
		articleBoxTexts.add("Access the exclusive Economic Times stories, Editorial and Expert opinion");
		articleBoxTexts.add("START FREE TRIAL");
		articleBoxTexts.add("Already a member?");
		articleBoxTexts.add("Sign in now");
		boolean paywallTextDisplayed=true;
		boolean whyETPrimeBoxDisplayed= true;
		WaitUtil.sleep(5000);
		try {
			paywallDisplayed = isPrimePaywalBlockerShown();
			if(paywallDisplayed)
			{
				System.out.println(articleShowPageObjects.getEtPrimePaywallBlocker().getText());
				for(String texts: articleBoxTexts)
				{
					if(!(articleShowPageObjects.getEtPrimePaywallBlocker().getText().contains(texts)))
					{
						paywallTextDisplayed = false;
						throw new Exception ("Text "+ texts+" is not appearing on Paywall Screen");
					}

				}
				if(!(WebBaseMethods.isDisplayed(articleShowPageObjects.getWhyETPrimeBox())))
				{
					whyETPrimeBoxDisplayed = false;
					throw new Exception("Why ET Prime Box is not appearing on Paywall screen");
				}
			}
			else
				throw new Exception ("Paywall is not Displayed for Non LoggedIn user");
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return paywallDisplayed && paywallTextDisplayed && whyETPrimeBoxDisplayed;
	}

	public boolean isPaywallAndTextsDisplayedForExpiredUser()
	{
		boolean paywallDisplayed = false;
		List<String> articleBoxTexts = new ArrayList<String>();
		articleBoxTexts.add("To Read the Full Story, Become an ET Prime Member");
		articleBoxTexts.add("Access the exclusive Economic Times stories, Editorial and Expert opinion");
		articleBoxTexts.add("START YOUR MEMBERSHIP");
		articleBoxTexts.add("Already subscribed?");
		articleBoxTexts.add("Restore Purchase");
		boolean paywallTextDisplayed=true;
		boolean whyETPrimeBoxDisplayed= true;
		WaitUtil.sleep(5000);
		try {
			paywallDisplayed = isPrimePaywalBlockerShown();
			if(paywallDisplayed)
			{
				for(String texts: articleBoxTexts)
				{
					//System.out.println(articleShowPageObjects.getEtPrimePaywallBlocker().getText());
					if(!(articleShowPageObjects.getEtPrimePaywallBlocker().getText().contains(texts)))
					{
						paywallTextDisplayed = false;
						throw new Exception ("Text "+ texts+" is not appearing on Paywall Screen");
					}

				}
				if(!(WebBaseMethods.isDisplayed(articleShowPageObjects.getWhyETPrimeBox())))
				{
					whyETPrimeBoxDisplayed = false;
					throw new Exception("Why ET Prime Box is not appearing on Paywall screen");
				}
			}
			else
				throw new Exception ("Paywall is not Displayed for Non LoggedIn user");
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return paywallDisplayed && paywallTextDisplayed && whyETPrimeBoxDisplayed;
	}

	public boolean isPaywallAndTextsDisplayedForNonSubscribedUser()
	{
		boolean paywallDisplayed = false;
		List<String> articleBoxTexts = new ArrayList<String>();
		articleBoxTexts.add("To Read the Full Story, Become an ET Prime Member");
		articleBoxTexts.add("Access the exclusive Economic Times stories, Editorial and Expert opinion");
		articleBoxTexts.add("START FREE TRIAL");
		articleBoxTexts.add("Already subscribed?");
		articleBoxTexts.add("Restore Purchase");
		boolean paywallTextDisplayed=true;
		boolean whyETPrimeBoxDisplayed= true;
		WaitUtil.sleep(5000);
		try {
			paywallDisplayed = isPrimePaywalBlockerShown();
			if(paywallDisplayed)
			{
				for(String texts: articleBoxTexts)
				{
					//System.out.println(articleShowPageObjects.getEtPrimePaywallBlocker().getText());
					if(!(articleShowPageObjects.getEtPrimePaywallBlocker().getText().contains(texts)))
					{
						paywallTextDisplayed = false;
						throw new Exception ("Text "+ texts+" is not appearing on Paywall Screen");
					}

				}
				if(!(WebBaseMethods.isDisplayed(articleShowPageObjects.getWhyETPrimeBox())))
				{
					whyETPrimeBoxDisplayed = false;
					throw new Exception("Why ET Prime Box is not appearing on Paywall screen");
				}
			}
			else
				throw new Exception ("Paywall is not Displayed for Non LoggedIn user");
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return paywallDisplayed && paywallTextDisplayed && whyETPrimeBoxDisplayed;
	}

	public boolean isAudioSummaryIconDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(articleShowPageObjects.getAudioSummaryIcon(),20);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isAuthorNameDisplaying()
	{
		boolean flag = false;
		try {
			//	WaitUtil.sleep(6000);
			scrollToArticleBody();
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 25, articleShowPageObjects.getAuthorName());
			flag = WebBaseMethods.isDisplayed(articleShowPageObjects.getAuthorName(),15);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean clickAuthorName()
	{
		boolean flag = false;
		try {
			WebBaseMethods.moveToElementAndClick(articleShowPageObjects.getAuthorName());
			WaitUtil.sleep(3000);
		
			flag = true;

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public String getAuthorsName()
	{
		String name = "";
		try {
			name = WebBaseMethods.getTextUsingJSE(articleShowPageObjects.getAuthorName());
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return name;
	}
	
	public String getAuthorName()
	{
		String name = "By ";
		try {

			WaitUtil.explicitWaitByVisibilityOfElement(driver, 25, articleShowPageObjects.getAuthorName());
			name = name + WebBaseMethods.getTextUsingJSE(articleShowPageObjects.getAuthorName());
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return name;
	}
	
	public boolean isErrorBlockDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(articleShowPageObjects.getErrorBlock(),20);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public boolean isShareIconDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(articleShowPageObjects.getShareArticleIcon(),20);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public boolean isSaveIconDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(articleShowPageObjects.getSaveIcon(),20);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public boolean isFontIconDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(articleShowPageObjects.getFontIcon(),20);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public boolean isCommentIconDisplaying()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(articleShowPageObjects.getCommentIcon(),20);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
}
