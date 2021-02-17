package web.pagemethods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.DBUtil;
import common.utilities.WaitUtil;
import web.pageobjects.NewsLetterSubjectAndDateObjects;

public class NewsLetterSubjectAndDateMethods {
	WebDriver driver;
	NewsLetterSubjectAndDateObjects gmailNewsLetters;

	public NewsLetterSubjectAndDateMethods(WebDriver driver) {
		this.driver = driver;
		gmailNewsLetters = PageFactory.initElements(driver, NewsLetterSubjectAndDateObjects.class);
	}

	public boolean gmailLogIn(String emailID, String password) {
		boolean flag = false;
		try {
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, gmailNewsLetters.getGmailEMailField());
			gmailNewsLetters.getGmailEMailField().sendKeys(emailID);
			gmailNewsLetters.getGmailEMailField().sendKeys(Keys.ENTER);
			WaitUtil.sleep(2000);
			WaitUtil.explicitWaitByPresenceOfElement(driver, 20, gmailNewsLetters.getGmailPasswordField());
			gmailNewsLetters.getGmailPasswordField().sendKeys(password);
			gmailNewsLetters.getGmailPasswordField().sendKeys(Keys.ENTER);
			WaitUtil.sleep(2000);
			flag = true;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean openNewsLetter() {
		boolean flag = true;
		List<WebElement> el = WebBaseMethods.getDisplayedItemFromList(gmailNewsLetters.getFirstNewsletterInMailBox());

		try {
			WaitUtil.explicitWaitByPresenceOfElement(driver, 40, el.get(0));
			WebBaseMethods.clickElementUsingJSE(el.get(0));
		} catch (IndexOutOfBoundsException e) {
			flag = false;
		}
		return flag;

	}

	public String subjectLineNewsLetters() {
		try {
			return gmailNewsLetters.getSubjectLineNewsLetters().getText();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	public String newsletterReceivedDate() throws ParseException {
		String[] dateUnformated = gmailNewsLetters.getDateTimeNewsletter().getAttribute("title").split(",");
		String dateTime = null;
		Date time = null;
		try {
			time = new SimpleDateFormat("hh:mm a").parse(dateUnformated[2].trim());

			String formatedTime = new SimpleDateFormat("HH:mm:ss").format(time);
			String date = dateUnformated[0] + "," + dateUnformated[1].trim() + "," + formatedTime;
			Date date1 = new SimpleDateFormat("MMM dd,yyyy,hh:mm:ss").parse(date);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
			dateTime = sdf.format(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateTime;
	}

	public List<String> fetchSubjectLinesFromDb(String newsletterType) {
		DBUtil.dbInsertRegression();
		ArrayList<String[]> listOfSubjects = DBUtil.getResultsAsArrayList(
				"SELECT SUBJECT FROM newsletter WHERE NewsletterName='" + newsletterType + "'ORDER BY id desc LIMIT 5");
		List<String> subLi = new LinkedList<String>();
		listOfSubjects.forEach(subject -> {
			subLi.add(subject[0]);
		});
		return subLi;
	}

	public void insertDataInDBForNewsletter(String newsletterType, String newsLetterReceivedDate,
			String newsletterSubjectLine) {
//		DBUtil.dbInsertRegression();
		DBUtil.dbInsertNewsletter(newsletterType, newsLetterReceivedDate, newsletterSubjectLine);
	}

	public boolean searchMail() {
		boolean flag = false;
		try {
			WaitUtil.sleep(2000);
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, gmailNewsLetters.getSearchMail());
			String mailCountBeforeSearch = gmailNewsLetters.getMailsCount().get(0).getText();
			gmailNewsLetters.getSearchMail().sendKeys("from:etnotifications@indiatimes.com");
			gmailNewsLetters.getSearchMail().sendKeys(Keys.ENTER);
			String mailCountAfterSearch = WebBaseMethods.getDisplayedItemFromList(gmailNewsLetters.getMailsCount())
					.get(0).getText();
			int counter = 0;
			while (mailCountBeforeSearch.equalsIgnoreCase(mailCountAfterSearch) && counter < 50) {
				WaitUtil.sleep(2000);
				mailCountAfterSearch = WebBaseMethods.getDisplayedItemFromList(gmailNewsLetters.getMailsCount()).get(0)
						.getText();
				counter++;
			}
			flag = !(mailCountBeforeSearch.equalsIgnoreCase(mailCountAfterSearch));
		} catch (NoSuchElementException | IndexOutOfBoundsException e) {

		}
		return flag;
	}
}
