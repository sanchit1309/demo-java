package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SmallBizPageObjects {
	
	@FindBy(xpath="//*[@id='subMenu']/ul/li")
	private List<WebElement> smallBizSubMenu;
	
	@FindBy(xpath = "//*[@id=\"subSections\"]/div[1]/h2/a")
	private WebElement sMESectorSection;
	
	@FindBy(xpath = "//*[@id='subSections']/div[1]/ul")
	private List<WebElement> sMESectorNews;
	
	@FindBy(xpath = "//*[@id='subMenu']/ul/li[2]/a")
	private WebElement sMEHighlighted;
	
	@FindBy(xpath="//*[@id='subSections']/div[3]/h2/a")
	private WebElement startupsSection;
	
	@FindBy(xpath="//*[@id='subSections']/div[3]/ul")
	private List<WebElement> startupsNews;
	
	@FindBy(xpath="//*[@id='subMenu']/ul/li[3]/a")
	private WebElement startupsHighlighted;
	
	@FindBy(xpath="//*[@id='subSections']/div[5]/h2/a")
	private WebElement policySection;;
	
	@FindBy(xpath="//*[@id='subSections']/div[5]/ul")
	private List<WebElement> policyNews;
	
	@FindBy(xpath="//*[@id='subMenu']/ul/li[4]/a")
	private WebElement policyHighlighted;
	
	@FindBy(xpath ="//*[@id='subSections']/div[7]/h2/a")
	private WebElement entrepreneurshipSection;
	
	@FindBy(xpath ="//*[@id='subSections']/div[7]/ul")
	private List<WebElement> entrepreneurshipNews;
	
	@FindBy(xpath ="//*[@id='subMenu']/ul/li[5]/a")
	private WebElement entrepreneurshipHighlighted;
	
	@FindBy(xpath ="//*[@id='subSections']/div[9]/h2/a")
	private WebElement moneySection;
	
	@FindBy(xpath ="//*[@id='subSections']/div[9]/ul")
	private List<WebElement> moneyNews;
	
	@FindBy(xpath ="//*[@id='subMenu']/ul/li[6]/a")
	private WebElement moneyHighlighted;
	
	@FindBy(xpath="//*[@id='subSections']/div[11]/h2/a")
	private WebElement securitySection;
	
	@FindBy(xpath="//*[@id='subSections']/div[11]/ul")
	private List<WebElement> securityNews;
	
	@FindBy(xpath="//*[@id='subMenu']/ul/li[3]/a")
	private WebElement securityHighlighted;
	
	@FindBy(xpath="//*[@id='subSections']/div[13]/h2/a")
	private WebElement marketingSection;
	
	@FindBy(xpath="//*[@id='subSections']/div[13]/ul")
	private List<WebElement> marketingNews;
	
	@FindBy(xpath="//*[@id='subMenu']/ul/li[1]/a")
	private WebElement marketingHighlighted;
	
	@FindBy(xpath="//*[@id='subSections']/div[15]/h2/a")	
	private WebElement hrLeadershipSection;
	
	@FindBy(xpath="//*[@id='subSections']/div[15]/ul")
	private List<WebElement> hrLeadershipNews;
	
	@FindBy(xpath="//*[@id='subMenu']/ul/li[1]/a")
	private WebElement hrLeadershipHighlighted;
	
	@FindBy(xpath="//*[@id='subSections']/div[17]/h2/a")
	private WebElement legalSection;
	
	@FindBy(xpath="//*[@id='subSections']/div[17]/ul")
	private List<WebElement> legalNews;
	
	@FindBy(xpath="//*[@id='subMenu']/ul/li[10]/a")
	private WebElement legalHighlighted;
	
	@FindBy(xpath = "//*[@id='header']/section/div[2]/h1")
	private WebElement mainHeading;
		
///////////////////////////
	
	public List<WebElement> getSmallBizSubMenu() {
		return smallBizSubMenu;
	}


	public WebElement getSMESectorSection() {
		return sMESectorSection;
	}


	public List<WebElement> getSMESectorNews() {
		return sMESectorNews;
	}


	public WebElement getSMEHighlighted() {
		return sMEHighlighted;
	}


	public WebElement getStartupsSection() {
		return startupsSection;
	}


	public List<WebElement> getStartupsNews() {
		return startupsNews;
	}


	public WebElement getStartupsHighlighted() {
		return startupsHighlighted;
	}


	public WebElement getPolicySection() {
		return policySection;
	}


	public List<WebElement> getPolicyNews() {
		return policyNews;
	}


	public WebElement getPolicyHighlighted() {
		return policyHighlighted;
	}


	public WebElement getEntrepreneurshipSection() {
		return entrepreneurshipSection;
	}


	public List<WebElement> getEntrepreneurshipNews() {
		return entrepreneurshipNews;
	}


	public WebElement getEntrepreneurshipHighlighted() {
		return entrepreneurshipHighlighted;
	}


	public WebElement getMoneySection() {
		return moneySection;
	}


	public List<WebElement> getMoneyNews() {
		return moneyNews;
	}


	public WebElement getMoneyHighlighted() {
		return moneyHighlighted;
	}


	public WebElement getSecuritySection() {
		return securitySection;
	}


	public List<WebElement> getSecurityNews() {
		return securityNews;
	}


	public WebElement getSecurityHighlighted() {
		return securityHighlighted;
	}


	public WebElement getMarketingSection() {
		return marketingSection;
	}


	public List<WebElement> getMarketingNews() {
		return marketingNews;
	}


	public WebElement getMarketingHighlighted() {
		return marketingHighlighted;
	}


	public WebElement getHrLeadershipSection() {
		return hrLeadershipSection;
	}


	public List<WebElement> getHrLeadershipNews() {
		return hrLeadershipNews;
	}


	public WebElement getHrLeadershipHighlighted() {
		return hrLeadershipHighlighted;
	}


	public WebElement getLegalSection() {
		return legalSection;
	}


	public List<WebElement> getLegalNews() {
		return legalNews;
	}


	public WebElement getLegalHighlighted() {
		return legalHighlighted;
	}


	public WebElement getMainHeading() {
		return mainHeading;
	}

}
