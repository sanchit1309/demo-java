package com.web.tests;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.WaitUtil;
import web.pagemethods.NewsLetterSubjectAndDateMethods;

public class NewsLetterSubjectAndDate extends BaseTest{
	NewsLetterSubjectAndDateMethods gmailNewslettersMethods;
	String newsletterType;
	String emailId;
	String password;

	@Parameters({ "newsletterType", "emailId", "password" })
	@BeforeClass
	public void launchBrowser(@Optional("Daily Newsletter") String newsletterType,
			@Optional("vikasmathur19876@gmail.com") String emailId, @Optional("Times@123") String password) {
		System.out.println("RunTime values ===> newsletterType:" + newsletterType + ", email:" + emailId + ", password:"
				+ password);
		this.newsletterType = newsletterType;
		this.emailId = emailId;
		this.password = password;
		try {
			launchBrowser("https://gmail.com");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gmailNewslettersMethods = new NewsLetterSubjectAndDateMethods(driver);
	}

	@Test(description = "Verifies Newsletter recieved on expected date and subject line is not repeating")
	public void verifySubjectLineReceiving() throws ParseException {
		Assert.assertTrue(gmailNewslettersMethods.gmailLogIn(emailId, password), "Unable to sign in on gmail");
		Assert.assertTrue(gmailNewslettersMethods.searchMail(), "Unable to search mail box by sender");
		Assert.assertTrue(gmailNewslettersMethods.openNewsLetter(), "Unable to open first newsletter");
		WaitUtil.sleep(4000);
		String newsletterSubjectLine = gmailNewslettersMethods.subjectLineNewsLetters();
		System.out.println(newsletterSubjectLine);
		String newsLetterReceivedDate = gmailNewslettersMethods.newsletterReceivedDate();
		System.out.println("newsletter recived date :" + newsLetterReceivedDate);
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		String currentDateFormated = sdf.format(currentDate);
		Date todaysDate = sdf.parse(currentDateFormated);
		System.out.println("current date : " + todaysDate);
		if (newsLetterReceivedDate != null && todaysDate.compareTo(sdf.parse(newsLetterReceivedDate)) != 0) {
			Assert.assertTrue(false, "Newsletter : " + newsletterType
					+ " : is not received today on expected time, last recieved date is:" + newsLetterReceivedDate);
		}
		
		// gmailNewslettersMethods.fetchSubjectLinesFromDb(newsletterType).forEach((subject)
		// -> {
		// System.out.println("subject : " + subject);
		// });
		if (newsletterSubjectLine.trim().length() > 0
				&& gmailNewslettersMethods.fetchSubjectLinesFromDb(newsletterType).contains(newsletterSubjectLine)) {
			Assert.assertTrue(false, "Subject line of newsletter : " + newsletterType + " is not unqiue"
					+ newsletterSubjectLine + " for newsletter recieved on: " + newsLetterReceivedDate);
		}
		gmailNewslettersMethods.insertDataInDBForNewsletter(newsletterType, newsLetterReceivedDate,
				newsletterSubjectLine);
	}

	@Parameters({ "recipient", "failureRecipient", "emailType" })
	@AfterClass(alwaysRun = true)
	public void closeUp(@Optional("navdeep.gill@timesinternet.in") String recipient,
			@Optional("navdeep.gill@timesinternet.in") String failureRecipient, @Optional("nMail") String emailType) {
		Config.writePropertiesFile("./maven.properties", "mailSubject", newsletterType.replaceAll("\\s", ""));
	}

}
