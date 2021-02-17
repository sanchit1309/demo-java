package wap.pagemethods;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import wap.pageobjects.MenuPageObjects;
import web.pagemethods.WebBaseMethods;

public class MenuPageMethods {

	MenuPageObjects menuPageObjects;
	Actions builder;
	WebDriver driver;

	public MenuPageMethods(WebDriver driver) {
		this.driver = driver;
		menuPageObjects = PageFactory.initElements(driver, MenuPageObjects.class);
		builder = new Actions(driver);

	}

	public List<String> getMenuListL1() {
		List<String> menuList = new ArrayList<String>();
		for (WebElement ele : menuPageObjects.getMenuListL1Text()) {
			System.out.println(ele.getText());
			menuList.add(ele.getText().replaceAll(".*\\n", ""));
		}
		return menuList;
	}

	/*
	 * public List<String> getMenuListL2(String l1MenuName) {
	 * 
	 * List<String> menuList = new ArrayList<String>(); List<WebElement> eleList
	 * = menuPageObjects.getMenuListL2Text(l1MenuName);
	 * System.out.println(eleList.size()); for (WebElement ele :
	 * menuPageObjects.getMenuListL2Text(l1MenuName)) {
	 * System.out.println(ele.getText());
	 * menuList.add(ele.getText().replaceAll(".*\\n", "")); } return menuList;
	 * 
	 * }
	 */

	public List<String> getMenuListL2(String l1MenuName) {
		List<String> menuList = new ArrayList<String>();
		menuList = WebBaseMethods.getListTextUsingJSE(menuPageObjects.getMenuListL2Text(l1MenuName));
		System.out.println(menuList);
		return menuList;
	}

	public List<String> getMenuListL3(String l1MenuName) {
		List<String> menuList = new ArrayList<String>();
		menuList = WebBaseMethods.getListTextUsingJSE(menuPageObjects.getMenuListL3(l1MenuName));
		return menuList;
	}

	public List<String> getMenuHrefListL1() {
		List<String> menuList = new ArrayList<String>();
		for (WebElement ele : menuPageObjects.getMenuListL1()) {
			menuList.add(ele.getAttribute("href"));
		}
		return menuList;
	}

	public List<String> getMenuHrefListL2(String l1MenuName) {
		List<String> menuList = new ArrayList<String>();
		for (WebElement ele : menuPageObjects.getMenuListL2(l1MenuName)) {
			menuList.add(ele.getAttribute("href"));
		}

		System.out.println(menuList);
		return menuList;
	}

	public List<String> getMenuHrefListL3(String l1MenuName) {
		List<String> menuList = new ArrayList<String>();
		for (WebElement ele : menuPageObjects.getMenuListL3(l1MenuName)) {
			menuList.add(ele.getAttribute("href"));
		}
		System.out.println(menuList);
		return menuList;
	}

	public List<WebElement> getSubMenuList(String menuOptions) {
		return menuPageObjects.getSubMenuOptions(menuOptions);
	}

	public WebElement moveToTab(String tabName) throws NoSuchElementException {
		for (WebElement ele : menuPageObjects.getMenuListL1()) {
			if (ele.getAttribute("href").contains(tabName.toLowerCase()))
				return ele;
		}
		return null;
	}

	public boolean checkSubMenu(String tabName) {
		try {
			WebElement tab = moveToTab(tabName);
			builder.moveToElement(tab).build().perform();
			tab.findElement(By.className("nextArrow")).click();
			// openSubMenu();
		} catch (Exception e) {

		}
		return true;
	}

	public void openSubMenu() {
		try {
			menuPageObjects.getSubMenuLink().get(0).click();
		} catch (Exception e) {

		}
	}

	public void clickSubMenuOption(String subMenu) {
		try {

		} catch (Exception e) {

		}
	}

	public boolean clickMenuOption(String tabName) throws NoSuchElementException {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(menuPageObjects.getDynamicMenuOption(tabName));
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean clickMenuOptionReact(String tabName) throws NoSuchElementException {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(menuPageObjects.getDynamicMenuOptionReact(tabName));
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean clickKebabMenuIcon() {

		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(menuPageObjects.getKebabMenuIcon());
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;

	}

	public boolean clickFooterMenuIconNonReact() {

		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(menuPageObjects.getFooterMenuIconNonReact());
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
		
	}
	
	public boolean clickMySubscriptionButton() {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(menuPageObjects.getmySubscriptions());
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}
	
	public boolean isPrimeIconForSubscribedUserShown() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(menuPageObjects.getEtPrimeIconUnderName(), 10);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

}