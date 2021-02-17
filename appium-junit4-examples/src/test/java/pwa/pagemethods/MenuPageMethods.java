package pwa.pagemethods;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import pwa.pageobjects.MenuPageObjects;
import web.pagemethods.WebBaseMethods;

public class MenuPageMethods {
	
	private MenuPageObjects menuPageObjects;
	Actions builder;
	
	public MenuPageMethods(WebDriver driver) {
		menuPageObjects = PageFactory.initElements(driver, MenuPageObjects.class);
	}
	
	
	public boolean clickMenuOption(String tabName) throws NoSuchElementException {
	boolean flag=false;
		try {
			 WebBaseMethods.clickElementUsingJSE(menuPageObjects.getDynamicMenuOption(tabName));
			 flag=true;
		} catch (NoSuchElementException e) {
			
		}
		return flag;
	}
	public List<String> getMenuListL1() {
		List<String> menuList = new ArrayList<String>();
		for (WebElement ele : menuPageObjects.getMenuListL1()) {
			menuList.add(ele.getText().replaceAll(".*\\n", ""));
		}
		return menuList;
	}

	public List<String> getMenuListL2(String l1MenuName) {
		WebBaseMethods.clickElementUsingJSE(menuPageObjects.getMenuNextArrow(l1MenuName));
		List<String> subMenuNames = new ArrayList<String>();
		subMenuNames.addAll(WebBaseMethods.getListTextUsingJSE(menuPageObjects.getMenuListL1()));
		//List<WebElement> subMenuL2 = menuPageObjects.getMenuListChild();
		//System.out.println(subMenuL2.size());
	/*	for(int i=0;i<subMenuL3.size();i++) {
			WaitUtil.sleep(1000);
			subMenuL3.get(i).click();
			subMenuNames.addAll(WebBaseMethods.getListTextUsingJSE(menuPageObjects.getMenuListL1()));
			System.out.println(subMenuNames);
			WaitUtil.sleep(1000);
			menuPageObjects.getPrevLink().click();
		}
		*/
		//menuPageObjects.getPrevLink().click();
		return subMenuNames;
	}

	public List<String> getMenuListL3(String l1MenuName) {
		List<String> menuList = new ArrayList<String>();
		menuList=WebBaseMethods.getListTextUsingJSE(menuPageObjects.getMenuListL3(l1MenuName));
		return menuList;
	}

	public List<String> getMenuHrefListL1() {
		List<String> menuList = new ArrayList<String>();
		for (WebElement ele : menuPageObjects.getMenuListL1()) {
			menuList.add(ele.getAttribute("href"));
		}
		System.out.println(menuList.size());
		return menuList;
	}

	public List<String> getMenuHrefListL2(String l1MenuName) {
		WaitUtil.sleep(1000);
		//menuPageObjects.getMenuNextArrow(l1MenuName).click();
		WaitUtil.sleep(1000);
		List<String> menuList = new ArrayList<String>();
		for (WebElement ele : menuPageObjects.getMenuListL1()) {
			menuList.add(ele.getAttribute("href"));
		}
		WaitUtil.sleep(1000);
		//menuPageObjects.getPrevLink().click();
		return menuList;
	}

	public List<String> getMenuHrefListL3(String l1MenuName) {
		//List<String> menuList = new ArrayList<String>();
		WaitUtil.sleep(1000);
		List<WebElement> subMenuL3 = menuPageObjects.getMenuListChild();
		List<String> subMenuHref = new ArrayList<String>();
		System.out.println(subMenuL3.size());
		if(subMenuL3.size()>0) {
			for(int i=0;i<subMenuL3.size();i++) {
				WaitUtil.sleep(1000);
				subMenuL3.get(i).click();
				for (WebElement ele : menuPageObjects.getMenuListL1()) {
					subMenuHref.add(ele.getAttribute("href"));
				}
				//subMenuNames.addAll(WebBaseMethods.getListTextUsingJSE(menuPageObjects.getMenuListL1()));
				System.out.println(subMenuHref);
				WaitUtil.sleep(1000);
				WebBaseMethods.clickElementUsingJSE(menuPageObjects.getPrevLink());
			}
		}
	
		WaitUtil.sleep(1000);
		WebBaseMethods.clickElementUsingJSE(menuPageObjects.getPrevLink());
		return subMenuHref;
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
			WebBaseMethods.clickElementUsingJSE(menuPageObjects.getSubMenuLink().get(0));
		} catch (Exception e) {

		}
	}


	public void moveToPreviousMenu() {
		try {
			menuPageObjects.getPrevLink();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
