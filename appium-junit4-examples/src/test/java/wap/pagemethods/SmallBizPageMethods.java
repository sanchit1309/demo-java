package wap.pagemethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import common.utilities.VerificationUtil;
import wap.pageobjects.SmallBizPageObjects;
import web.pagemethods.WebBaseMethods;

public class SmallBizPageMethods {
	
	private SmallBizPageObjects smallBizPageObjects;
	private StoryPageMethods storyPageMethods;
	private WapListingPageMethods wapListingPageMethods;
	private Actions builder;
	private WebDriver driver;
	
	public SmallBizPageMethods(WebDriver driver){
		this.driver = driver;
		smallBizPageObjects = PageFactory.initElements(driver,SmallBizPageObjects.class);
		storyPageMethods = new StoryPageMethods(driver);
		wapListingPageMethods = new WapListingPageMethods(driver);
		builder = new Actions(driver);
	}

	public List<String> getTopSectionHeaders(){
		List<String> topSectionHeaders = new ArrayList<String>();
		for(WebElement ele: smallBizPageObjects.getSmallBizSubMenu()){
			topSectionHeaders.add(ele.getText().replaceAll(".*\\n",""));
		}
		return topSectionHeaders;
	}

	public boolean checkSMESectorSection() {
		boolean flag = true;	
		try{
			builder.moveToElement(smallBizPageObjects.getSMESectorSection()).build().perform();
			if(!smallBizPageObjects.getSMESectorSection().isDisplayed()){
				Reporter.log("SME Sector Section Not Displayed !");
				flag=false;
			}
			else{
				if(smallBizPageObjects.getSMESectorNews().size() <= 0){
					Reporter.log("No News in SME Sector Section !");
					flag = false;
				}
			}
		}catch(Exception e){
			e.getMessage();
			flag = false;
		}
		return flag;
	}

	public String getSMESectorHeading() {
		return smallBizPageObjects.getSMESectorSection().getText();
	}

	public boolean checkSMESectorLink() {
		try{
			builder.moveToElement(smallBizPageObjects.getSMESectorSection()).build().perform();
			WebBaseMethods.clickElementUsingJSE(smallBizPageObjects.getSMESectorSection());
			if(smallBizPageObjects.getMainHeading().getText().equalsIgnoreCase("SME Sector")||smallBizPageObjects.getSMEHighlighted().getText().equalsIgnoreCase("SME Sector"))
					return true;
			else 
				return false;
		}catch(Exception e){
			e.getMessage();
			return false;
		}
	}

	public boolean checkStartupsSection() {
		boolean flag = true;	
		try{
			builder.moveToElement(smallBizPageObjects.getStartupsSection()).build().perform();
			if(!smallBizPageObjects.getStartupsSection().isDisplayed()){
				Reporter.log("Startups Section Not Displayed !");
				flag=false;
			}
			else{
				if(smallBizPageObjects.getStartupsNews().size() <= 0){
					Reporter.log("No News in Startups Section !");
					flag = false;
				}
			}
		}catch(Exception e){
			e.getMessage();
			flag = false;
		}
		return flag;
	}

	public String getStartupsHeading() {
		return smallBizPageObjects.getStartupsSection().getText();
	}

	public boolean checkStartupsLink() {
		try{
			builder.moveToElement(smallBizPageObjects.getStartupsSection()).build().perform();
			WebBaseMethods.clickElementUsingJSE(smallBizPageObjects.getStartupsSection());
			if(smallBizPageObjects.getMainHeading().getText().equalsIgnoreCase("Startups")||smallBizPageObjects.getStartupsHighlighted().getText().equalsIgnoreCase("Startups"))
					return true;
			else 
				return false;
		}catch(Exception e){
			e.getMessage();
			return false;
		}
	}

	public boolean checkPolicySection() {
		boolean flag = true;	
		try{
			builder.moveToElement(smallBizPageObjects.getPolicySection()).build().perform();
			if(!smallBizPageObjects.getPolicySection().isDisplayed()){
				Reporter.log("Policy & Trends Section Not Displayed !");
				flag=false;
			}
			else{
				if(smallBizPageObjects.getPolicyNews().size() <= 0){
					Reporter.log("No News in Policy & Trends Section !");
					flag = false;
				}
			}
		}catch(Exception e){
			e.getMessage();
			flag = false;
		}
		return flag;
	}

	public String getPolicyHeading() {
		return smallBizPageObjects.getPolicySection().getText();
	}

	public boolean checkPolicyLink() {
		try{
			builder.moveToElement(smallBizPageObjects.getPolicySection()).build().perform();
			WebBaseMethods.clickElementUsingJSE(smallBizPageObjects.getPolicySection());
			if(smallBizPageObjects.getMainHeading().getText().equalsIgnoreCase("Policy & Trends")||smallBizPageObjects.getPolicyHighlighted().getText().equalsIgnoreCase("Policy & Trends"))
					return true;
			else 
				return false;
		}catch(Exception e){
			e.getMessage();
			return false;
		}
	}

	public boolean checkEntrepreneurshipSection() {
		boolean flag = true;	
		try{
			builder.moveToElement(smallBizPageObjects.getEntrepreneurshipSection()).build().perform();
			if(!smallBizPageObjects.getEntrepreneurshipSection().isDisplayed()){
				Reporter.log("Entrepreneurship Section Not Displayed !");
				flag=false;
			}
			else{
				if(smallBizPageObjects.getEntrepreneurshipNews().size() <= 0){
					Reporter.log("No News in Entrepreneurship Section !");
					flag = false;
				}
			}
		}catch(Exception e){
			e.getMessage();
			flag = false;
		}
		return flag;
	}

	public String getEntrepreneurshipHeading() {
		return smallBizPageObjects.getEntrepreneurshipSection().getText();
	}

	public boolean checkEntrepreneurshipLink() {
		try{
			builder.moveToElement(smallBizPageObjects.getEntrepreneurshipSection()).build().perform();
			WebBaseMethods.clickElementUsingJSE(smallBizPageObjects.getEntrepreneurshipSection());
			if(smallBizPageObjects.getMainHeading().getText().equalsIgnoreCase("Entrepreneurship")||smallBizPageObjects.getEntrepreneurshipHighlighted().getText().equalsIgnoreCase("Entrepreneurship"))
					return true;
			else 
				return false;
		}catch(Exception e){
			e.getMessage();
			return false;
		}
	}

	public boolean checkMoneySection() {
		boolean flag = true;	
		try{
			builder.moveToElement(smallBizPageObjects.getMoneySection()).build().perform();
			if(!smallBizPageObjects.getMoneySection().isDisplayed()){
				Reporter.log("Money Section Not Displayed !");
				flag=false;
			}
			else{
				if(smallBizPageObjects.getMoneyNews().size() <= 0){
					Reporter.log("No News in Money Section !");
					flag = false;
				}
			}
		}catch(Exception e){
			e.getMessage();
			flag = false;
		}
		return flag;
	}

	public String getMoneyHeading() {
		return smallBizPageObjects.getMoneySection().getText();
	}

	public boolean checkMoneyLink() {
		try{
			builder.moveToElement(smallBizPageObjects.getMoneySection()).build().perform();
			WebBaseMethods.clickElementUsingJSE(smallBizPageObjects.getMoneySection());
			if(smallBizPageObjects.getMainHeading().getText().equalsIgnoreCase("Money")||smallBizPageObjects.getMoneyHighlighted().getText().equalsIgnoreCase("Money"))
					return true;
			else 
				return false;
		}catch(Exception e){
			e.getMessage();
			return false;
		}
	}

	public boolean checkSecuritySection() {
		boolean flag = true;	
		try{
			builder.moveToElement(smallBizPageObjects.getSecuritySection()).build().perform();
			if(!smallBizPageObjects.getSecuritySection().isDisplayed()){
				Reporter.log("Money Section Not Displayed !");
				flag=false;
			}
			else{
				if(smallBizPageObjects.getSecurityNews().size() <= 0){
					Reporter.log("No News in Money Section !");
					flag = false;
				}
			}
		}catch(Exception e){
			e.getMessage();
			flag = false;
		}
		return flag;
	}

	public String getSecurityHeading() {
		return smallBizPageObjects.getSecuritySection().getText();
	}

	public boolean checkSecurityLink() {
		try{
			builder.moveToElement(smallBizPageObjects.getSecuritySection()).build().perform();
			WebBaseMethods.clickElementUsingJSE(smallBizPageObjects.getSecuritySection());
			if(smallBizPageObjects.getMainHeading().getText().equalsIgnoreCase("Security-Tech")||smallBizPageObjects.getSecurityHighlighted().getText().equalsIgnoreCase("Security-Tech"))
					return true;
			else 
				return false;
		}catch(Exception e){
			e.getMessage();
			return false;
		}
	}

	public boolean checkMarketingSection() {
		boolean flag = true;	
		try{
			builder.moveToElement(smallBizPageObjects.getMarketingSection()).build().perform();
			if(!smallBizPageObjects.getMarketingSection().isDisplayed()){
				Reporter.log("Marketing-Branding Section Not Displayed !");
				flag=false;
			}
			else{
				if(smallBizPageObjects.getMarketingNews().size() <= 0){
					Reporter.log("No News in Marketing-Branding Section !");
					flag = false;
				}
			}
		}catch(Exception e){
			e.getMessage();
			flag = false;
		}
		return flag;
	}

	public String getMarketingHeading() {
		return smallBizPageObjects.getMarketingSection().getText();
	}

	public boolean checkMarketingLink() {
		try{
			builder.moveToElement(smallBizPageObjects.getMarketingSection()).build().perform();
			WebBaseMethods.clickElementUsingJSE(smallBizPageObjects.getMarketingSection());
			if(smallBizPageObjects.getMainHeading().getText().equalsIgnoreCase("Marketing-Branding")||smallBizPageObjects.getMarketingHighlighted().getText().equalsIgnoreCase("Marketing-Branding"))
					return true;
			else 
				return false;
		}catch(Exception e){
			e.getMessage();
			return false;
		}
	}

	public boolean checkHrLeadershipSection() {
		boolean flag = true;	
		try{
			builder.moveToElement(smallBizPageObjects.getHrLeadershipSection()).build().perform();
			if(!smallBizPageObjects.getHrLeadershipSection().isDisplayed()){
				Reporter.log("HR-Leadership Section Not Displayed !");
				flag=false;
			}
			else{
				if(smallBizPageObjects.getHrLeadershipNews().size() <= 0){
					Reporter.log("No News in HR-Leadership Section !");
					flag = false;
				}
			}
		}catch(Exception e){
			e.getMessage();
			flag = false;
		}
		return flag;
	}

	public String getHrLeadershipHeading() {
		return smallBizPageObjects.getHrLeadershipSection().getText();
	}

	public boolean checkHrLeadershipLink() {
		try{
			builder.moveToElement(smallBizPageObjects.getHrLeadershipSection()).build().perform();
			WebBaseMethods.clickElementUsingJSE(smallBizPageObjects.getHrLeadershipSection());
			if(smallBizPageObjects.getMainHeading().getText().equalsIgnoreCase("HR-Leadership")||smallBizPageObjects.getHrLeadershipHighlighted().getText().equalsIgnoreCase("HR-Leadership"))
				return true;
			else 
				return false;
		}catch(Exception e){
			e.getMessage();
			return false;
		}
	}

	public boolean checkLegalSection() {
		boolean flag = true;	
		try{
			builder.moveToElement(smallBizPageObjects.getLegalSection()).build().perform();
			if(!smallBizPageObjects.getLegalSection().isDisplayed()){
				Reporter.log("Legal Section Not Displayed !");
				flag=false;
			}
			else{
				if(smallBizPageObjects.getHrLeadershipNews().size() <= 0){
					Reporter.log("No News in Legal Section !");
					flag = false;
				}
			}
		}catch(Exception e){
			e.getMessage();
			flag = false;
		}
		return flag;
	}

	public String getLegalHeading() {
		return smallBizPageObjects.getLegalSection().getText();
	}

	public boolean checkLegalLink() {
		try{
			builder.moveToElement(smallBizPageObjects.getLegalSection()).build().perform();
			WebBaseMethods.clickElementUsingJSE(smallBizPageObjects.getLegalSection());
			if(smallBizPageObjects.getMainHeading().getText().equalsIgnoreCase("Legal")||smallBizPageObjects.getLegalHighlighted().getText().equalsIgnoreCase("Legal"))
				return true;
			else 
				return false;
		}catch(Exception e){
			e.getMessage();
			return false;
		}
	}
	
	/**
	 * Method checks recency of specific tabs
	 * @param tabs
	 * @param days
	 * @return
	 */
	public Map<String, Boolean> checkRecency(List<String> tabs,int days){
		Map<String,Boolean> recencyMap = new HashMap<String,Boolean>();
		List<WebElement> tabList = smallBizPageObjects.getSmallBizSubMenu();
		String urlMain;
			for(String tabName : tabs){
			wapListingPageMethods.navigateToTabAndClick(tabList, tabName);
			if(tabName.equalsIgnoreCase("Small Biz")){
				urlMain = driver.getCurrentUrl();
				recencyMap = checkHomeTabRecency(days);
				driver.get(urlMain);
			}			
		}
		return recencyMap;	
	}
	
	/**
	 * Method to check home page articles recency
	 * @param days
	 * @return
	 */
	public Map<String,Boolean> checkHomeTabRecency(int days){
		Map<String,Boolean> tabRecencyMap = new HashMap<String,Boolean>();
		boolean status = false;
		try{
			 Map<String,String> headlineLinks = wapListingPageMethods.getHeadlinesLink(wapListingPageMethods.getListOfHeadings());
			 for(Map.Entry<String,String> entry : headlineLinks.entrySet()){
				 if(entry.getValue().contains("morning-brief"))
					 continue;
				 status = checkRecencyArticle(entry.getValue(),days);
				 tabRecencyMap.put(entry.getKey(),status);
				 if(status == false)
					 Reporter.log("Old Article :: " + entry.getKey());
			 }
			 return tabRecencyMap;		 
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Check date of the article
	 * @param URL
	 * @param days
	 * @return
	 */
	public boolean checkRecencyArticle(String URL,int days){
		try {
			driver.get(URL);
			String date = storyPageMethods.getArticleDate();
			return VerificationUtil.isLatest(date,days);
		}catch(Exception e){
			return false;
		}
		
	}


}
