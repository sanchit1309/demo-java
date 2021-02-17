package wap.pagemethods;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.DBUtil;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import wap.pageobjects.LiveBlogPageObjects;
import web.pagemethods.WebBaseMethods;

public class LiveBlogPageMethods {

	private LiveBlogPageObjects liveBlogPageObjects;
	
	WebDriver driver;
	public LiveBlogPageMethods(WebDriver driver) {
		this.driver=driver;
		liveBlogPageObjects = PageFactory.initElements(driver, LiveBlogPageObjects.class);
	}
	
	public void openFirstLiveBlog() {
		WaitUtil.explicitWaitByElementToBeClickable(driver, 10, liveBlogPageObjects.getLiveBlogList());
		try {
			liveBlogPageObjects.getLiveBlogList().click();
		}catch(Exception e) {
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
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return urls;
	}
	

	public boolean isLiveBlogTodays() {
		boolean flag= false;
		
		try {
			//openFirstLiveBlog();
			String date= liveBlogPageObjects.getLiveBlogTime().getText();
			flag = VerificationUtil.isLatest(date, 0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
		
	}
	
	public String getLiveBlogDate() {
		String liveBlogDate = "";
		try {
			WebBaseMethods.switchToChildWindow(20);
			liveBlogDate= liveBlogPageObjects.getLiveBlogTime().getText();
		}catch(Exception e){
			e.printStackTrace();
		}
		return liveBlogDate;
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

		

}
