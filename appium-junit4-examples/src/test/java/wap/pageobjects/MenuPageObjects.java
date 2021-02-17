package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

/**
 * 
 * Menu Option Page Elements WAP
 *
 */

//// div[@id='mainMenu']//ul[@id="ul_li_"]/li/a/span[string-length(text())>0]
public class MenuPageObjects {

	@FindBy(className = "menulist menuHome")
	private WebElement homeIcon;

	@FindBy(xpath = "//div[@id='mainMenu']//ul[@id='ul_li_']/li/a")
	private List<WebElement> menuListL1;

	@FindBy(xpath = "//div[@id='mainMenu']//ul[@id='ul_li_']/li/a/span[string-length(text())>0]")
	private List<WebElement> menuListL1Text;

	@FindBy(xpath = "//span[@class='nextArrow']")
	private List<WebElement> subMenuLink;

	@FindBy(xpath = "//div[contains(@class,'active')]/span")
	private WebElement prevLink;

	@FindBy(xpath = "//div[contains(@class,'0a21c71')]")
	private WebElement kebabMenuIcon;

	@FindBy(xpath = "//a[@id='footer-menu']")
	private WebElement footerMenuIconNonReact;

	@FindBy(xpath = "//span[text()='My Subscription']")
	private WebElement mySubscriptions;
	
	@FindBy(xpath = "//div[@id='mainMenu']//img[contains(@src,'https://img.etimg.com/photo/77066493.cms')]")
	private WebElement etPrimeIconUnderName;
	
	public WebElement getHomeIcon() {
		return homeIcon;
	}

	public List<WebElement> getMenuListL1() {
		return menuListL1;
	}

	public List<WebElement> getMenuListL2(String menuName) {
		return driver.findElements(By.xpath(".//div[@id='mainMenu']//*[text()='" + menuName
				+ "']/../../ul[contains(@id,'ul_li_')]/li/a | //div[@id='mainMenu']//*[text()='" + menuName
				+ "']/../../ul[contains(@id,'ul_li_')]//ul[contains(@id,'ul_vm_')]/li/a"));
	}

	public List<WebElement> getMenuListL2Text(String menuName) {
		return driver.findElements(By.xpath(".//div[@id='mainMenu']//*[text()='" + menuName
				+ "']/../../ul[contains(@id,'ul_li_')]/li/a/span[string-length(text())>0] | //div[@id='mainMenu']//*[text()='"
				+ menuName
				+ "']/../../ul[contains(@id,'ul_li_')]//ul[contains(@id,'ul_vm_')]/li/a/span[string-length(text())>0]"));
	}

	/*
	 * public List<WebElement> getMenuListL3(String menuName) { return
	 * driver.findElements(By.xpath(
	 * ".//div[@class='populateMenu']//*[contains(text(),'" + menuName +
	 * "')]//..//ul[@class='l3']/li/a")); }
	 */

	public List<WebElement> getMenuListL3(String menuName) {
		return driver.findElements(By.xpath(".//a[@data-label='" + menuName
				+ "']/following-sibling::ul[contains(@id,'ul_li')]/li/ul[contains(@id,'ul_li')]/li/a | //a[@data-label='"
				+ menuName
				+ "']/following-sibling::ul[contains(@id,'ul_li')]/li/ul[contains(@id,'ul_vm')]/li/ul[contains(@id,'ul_li')]/li/a"));
	}

	public List<WebElement> getSubMenuLink() {
		return subMenuLink;
	}

	public WebElement getDynamicMenuOption(String menuName) {
		return driver.findElement(
				By.xpath("//*[contains(@class,'populateMenu')]/ul[contains(@class,'l1')]/li/a[contains(text(),'"
						+ menuName + "')]"));
	}

	public List<WebElement> getSubMenuOptions(String menuName) {
		return driver.findElements(
				By.xpath("//div[@class='populateMenu']//div[text()='" + menuName + "']/following-sibling::ul/li/a"));
	}

	public WebElement getPrevLink() {
		return prevLink;
	}

	public WebElement getDynamicMenuOptionReact(String menuName) {
		return driver.findElement(By.xpath("//ul/ul/li/a[@data-label='" + menuName + "']"));
	}

	public WebElement getKebabMenuIcon() {
		return kebabMenuIcon;
	}

	public List<WebElement> getMenuListL1Text() {
		return menuListL1Text;
	}

	public WebElement getFooterMenuIconNonReact() {
		return footerMenuIconNonReact;
	}
	
	public WebElement getmySubscriptions() {
		return mySubscriptions;
	}

	public WebElement getEtPrimeIconUnderName() {
		return etPrimeIconUnderName;
	}
}
