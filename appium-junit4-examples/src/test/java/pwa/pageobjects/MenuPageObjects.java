package pwa.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

public class MenuPageObjects {
	
	
	@FindBy(className="menulist menuHome")
	private WebElement homeIcon;
	
	@FindBy(xpath="//div[contains(@class,'childItem')]/a")
	private List<WebElement> menuListL1;
	
	@FindBy(xpath="//div[@class='menulist childItem']/span")
	private List<WebElement> menuListChild;
	
	@FindBy(xpath="//span[@class='nextArrow']")
	private List<WebElement> subMenuLink;
	
	@FindBy(xpath="//div[contains(@class,'active')]/span")
	private WebElement prevLink;

	public WebElement getHomeIcon(){
		return homeIcon;
	}
	
	public List<WebElement> getMenuListL1() {
		return menuListL1;
	}

	public List<WebElement> getMenuListL3(String menuName){
		return driver.findElements(By.xpath(".//div[@class='populateMenu']//*[contains(text(),'"+menuName+"')]//..//ul[@class='l3']/li/a"));
	}

	public WebElement getMenuNextArrow(String menuName) {
		return driver.findElement(By.xpath("//a[text()='"+menuName+"']/ancestor::div/span[contains(@class,'nextArrow')]"));
	}
	
	public List<WebElement> getSubMenuLink() {
		return subMenuLink;
	}

	
	public List<WebElement> getSubMenuOptions(String menuName){
		return driver.findElements(By.xpath("//div[@class='populateMenu']//div[text()='"+menuName+"']/following-sibling::ul/li/a"));
	}

	public WebElement getPrevLink() {
		return prevLink;
	}
	
	public WebElement getDynamicMenuOption(String menuName){
		return driver.findElement(By.xpath("//*[contains(@class,'populateMenu')]//a[contains(text(),'"+menuName+"')]"));
	}

	public List<WebElement> getMenuListChild() {
		return menuListChild;
	}
}
