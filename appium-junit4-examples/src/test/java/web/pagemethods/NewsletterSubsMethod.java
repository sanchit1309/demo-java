package web.pagemethods;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.HTTPResponse;
import common.utilities.WaitUtil;
import web.pageobjects.NewsletterSubsPageObjects;

public class NewsletterSubsMethod {
	private WebDriver driver;
	private NewsletterSubsPageObjects newsletterSubsPageObjects;

	public NewsletterSubsMethod(WebDriver driver) {
		this.driver = driver;
		newsletterSubsPageObjects = PageFactory.initElements(driver, NewsletterSubsPageObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
	}

	public NewsletterSubsPageObjects getNewsletterSubsPageObjects() {
		return newsletterSubsPageObjects;
	}

	public void unsubscribeAllNewsletter() {
		driver.get("https://economictimes.indiatimes.com/subscription");
		WaitUtil.sleep(6000);
		System.out.println(getSubscribedNewsletterList());
		List<WebElement> unsubscribeButton = newsletterSubsPageObjects.getUnSubscribeButton();
		unsubscribeButton.forEach(button -> {
			WebBaseMethods.clickElementUsingJSE(button);
			WebBaseMethods.clickElementUsingJSE(newsletterSubsPageObjects.getUnSubscribeButtonPopUp());
			WaitUtil.sleep(2500);
			WebBaseMethods.clickElementUsingJSE(newsletterSubsPageObjects.getUnSubscribePopUpCloseBtn());
		});

	}

	public List<String> getSubscribedNewsletterList() {
		List<String> subscribedNewsletter = new ArrayList<>();
		List<WebElement> subNewsletter = newsletterSubsPageObjects.getSubscribedNewsletterList();
		subNewsletter.forEach(newsletter -> {
			subscribedNewsletter.add(newsletter.getText());
		});

		return subscribedNewsletter;
	}

	public List<String> getUnSubscribedNewsletterList() {
		List<String> unSubscribedNewsletter = new ArrayList<>();
		List<WebElement> UnSubNewsletter = newsletterSubsPageObjects.getUnsubscribedNewsletterList();
		UnSubNewsletter.forEach(newsletter -> {
			unSubscribedNewsletter.add(newsletter.getText());
		});

		return unSubscribedNewsletter;
	}

	public String getThankYouMessage() {
		return newsletterSubsPageObjects.getThankYouMessage().getText();
	}

	public WebElement getSubmitButton() {
		return newsletterSubsPageObjects.getSubmitButton();
	}

	public WebElement getEmailIdBox() {
		return newsletterSubsPageObjects.getEmailIdBox();
	}

	public WebElement getSelectAllSubsCheckbox() {
		return newsletterSubsPageObjects.getSelectAllSubsCheckbox();
	}

	public boolean checkPresenceOfThankyouElement() {
		boolean flag = false;
		try {
			WebElement result = newsletterSubsPageObjects.getThankYouMessage();
			WaitUtil.explicitWaitByPresenceOfElement(driver, 5, result);
			flag = true;

		} catch (TimeoutException ex) {
			flag = false;

		}

		return flag;
	}

	public List<String> getErrorResponseCodeLinks(List<String> href) {
		List<String> errorResponseLinks = new ArrayList<>();
		List<String> articleLinks = href;
		articleLinks.forEach(link -> {
			if(link.contains("apsalar"))
				return;
			int responseCode = HTTPResponse.checkResponseCode(link);
			if (responseCode != 200) {
				errorResponseLinks.add(link);
			}

		});

		return errorResponseLinks;

	}

	public WebElement getNlDateTabType1() {
		return newsletterSubsPageObjects.getNlDateTabType1();
	}

	public List<WebElement> getNlArticleListType2() {
		return newsletterSubsPageObjects.getNlArticleListType2();
	}

	public WebElement getNlDateTabType2() {
		return newsletterSubsPageObjects.getNlDateTabType2();
	}

	public List<WebElement> getNlArticleListType3() {
		return newsletterSubsPageObjects.getNlArticleListType3();
	}

	public WebElement getNlDateTabType3() {
		return newsletterSubsPageObjects.getNlDateTabType3();
	}
	
	public WebElement getMorningMarketDate(){
		return newsletterSubsPageObjects.getMorningMarketsDate();
	}
	public List<WebElement> getNlArticleListSlideshows() {
		return newsletterSubsPageObjects.getNlArticleListSlideshows();
	}

	public List<WebElement> getAllUrls() {
		return newsletterSubsPageObjects.getAllUrls();
	}
	public List<String> getListHref(List<WebElement> elements) {
		List<String> href = new ArrayList<String>();
		List<WebElement> ele = elements;
		ele.forEach(action -> {
			href.add(action.getAttribute("href"));

		});

		return href;
	}

	public List<WebElement> getNlArticleListType1() {

		return newsletterSubsPageObjects.getNlArticleListType1();
	}
	
	public List<String> verifyTablePresent(List<String> tableList){
	List<String> tableNameList = tableList;	
	List<String> tableNotPresentList =  new ArrayList<>();
	tableNameList.forEach(tableName->{
	List<WebElement> tableHeader = newsletterSubsPageObjects.getTableHeaderName(tableName);
	
	if(tableHeader.size()!=1){
		tableNotPresentList.add(tableName);
		
	}	
	});
		return tableNotPresentList;
	}
	
	
}
