package web.pagemethods;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.DBUtil;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pageobjects.LiveBlogPageObjects;

public class LiveBlogPageMethods {

	private LiveBlogPageObjects liveBlogPageObjects;

	WebDriver driver;

	public LiveBlogPageMethods(WebDriver driver) {
		this.driver = driver;
		liveBlogPageObjects = PageFactory.initElements(driver, LiveBlogPageObjects.class);
	}

	public void openFirstLiveBlog() {
		WaitUtil.explicitWaitByElementToBeClickable(driver, 10, liveBlogPageObjects.getLiveBlogList().get(0));
		try {
			liveBlogPageObjects.getLiveBlogList().get(0).click();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<String> getLiveBlogUrlsFromSiteMap() {
		WaitUtil.sleep(2000);
		String pageSource = driver.getPageSource();
		System.out.println(pageSource.contains("liveblog"));
		Pattern pattern = Pattern.compile("[^\"]*\\bliveblog\\/\\d+\\b.cms");
		WaitUtil.sleep(5000);
		List<String> urls = new ArrayList<>();

		try {
			Matcher m = pattern.matcher(pageSource);
			System.out.println(m);

			while (m.find()) {
				System.out.println("here");
				urls.add(m.group());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return urls;
	}

	public boolean isLiveBlogTodays() {
		boolean flag = false;

		try {
			// openFirstLiveBlog();
			String date = liveBlogPageObjects.getLiveBlogTime().getText();
			date = date.replaceAll("\\|", "").replaceAll(" ", "").replaceAll(",", "");
			date = date.replace("IST", "");
			flag = VerificationUtil.isLatest(date, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}

	public String getLiveBlogDate() {
		String liveBlogDate = "";
		try {
			// WebBaseMethods.switchToChildWindow(1000);
			// WaitUtil.sleep(2000);
			WaitUtil.explicitWaitByPresenceOfElement(driver, 20, liveBlogPageObjects.getLiveBlogTime());
			liveBlogDate = liveBlogPageObjects.getLiveBlogTime().getText();
			liveBlogDate = WebBaseMethods.getTextUsingJSE(liveBlogPageObjects.getLiveBlogTime());
			System.out.println(liveBlogDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return liveBlogDate;
	}

	public String getKeyValueFromDb(String keyVal) {
		String fetchedValue = "";
		try {
			fetchedValue = DBUtil.getRecords("liveblog", "liveBlogKey='"  + keyVal+"'").iterator().next()[1];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fetchedValue;
	}

	public void writeIntoTable(String... value) {
		DBUtil.dbInsertColumnValues("liveblog", value);
	}
	
	public void updateTable(String whereColumnValue,String setColumnValue) {
		DBUtil.dbUpdateColumnValues("liveblog","liveBlogKey", whereColumnValue,"date",setColumnValue);
	}
	
	
	public void truncateTable(){
		DBUtil.truncateTable("liveblog");
	}
}
