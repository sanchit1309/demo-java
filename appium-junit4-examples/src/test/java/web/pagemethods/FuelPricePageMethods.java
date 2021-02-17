package web.pagemethods;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pageobjects.FuelPricePageObjects;

public class FuelPricePageMethods {
	
	FuelPricePageObjects fuelPricePageObjects;
	 WebDriver driver;
	public FuelPricePageMethods(WebDriver driver) {
		this.driver = driver;
		fuelPricePageObjects = PageFactory.initElements(driver, FuelPricePageObjects.class);
	}
	
	
	public List<String> getMetroNameListLinks(){
		List<String> metroNameList = new ArrayList<String>();
		try {
			metroNameList = WebBaseMethods.getListHrefUsingJSE(fuelPricePageObjects.getMetroNameList());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return metroNameList;
	}
	
	public List<String> getFuelPriceList(){
		List<String> fuelPriceList = new ArrayList<String>();
		try {
			fuelPriceList = WebBaseMethods.getListTextUsingJSE(fuelPricePageObjects.getMetroPriceList());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return fuelPriceList;
	}
	
	
	public List<String> getCityListLinks(){
		List<String> cityNameList = new ArrayList<String>();
		try {
			cityNameList = WebBaseMethods.getListHrefUsingJSE(fuelPricePageObjects.getCityLinksList());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return cityNameList;
	}
	
	public List<String> getStateListLinks(){
		List<String> stateNameList = new ArrayList<String>();
		try {
			stateNameList = WebBaseMethods.getListHrefUsingJSE(fuelPricePageObjects.getStateLinksList());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return stateNameList;
	}

	public List<String> getFuelNewsLinks(){
		List<String> newsList = new ArrayList<String>();
		try {
			newsList = WebBaseMethods.getListHrefUsingJSE(fuelPricePageObjects.getFuelNewsList());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return newsList;
	}
	
	public boolean openFuelTypePage(String type) {
		boolean flag = false; 
		try {
			fuelPricePageObjects.getFuelType(driver, type).click();
			WaitUtil.sleep(2000);
			if(driver.getCurrentUrl().contains(type)) {
				flag = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean openDieselPricesPage() {
		boolean flag = false;
		try {
			fuelPricePageObjects.getDieselPageLink().click();
			WaitUtil.sleep(2000);
			String url = driver.getCurrentUrl();
			if(url.contains("diesel")) {
				flag = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public String getSelectedLoc(String locType) {
		String locName = "";
		try {
			WebBaseMethods.scrollElementIntoViewUsingJSE(fuelPricePageObjects.getFindFuelPriceWidget());
			Select locDropDown = new Select(driver.findElement(By.name(locType)));
			locDropDown.selectByIndex(randomNumber(locDropDown.getOptions().size()+1));
			locName = locDropDown.getFirstSelectedOption().getText();
			System.out.println(locName);
			fuelPricePageObjects.getGetPriceBtn().click();
			WaitUtil.sleep(2000);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return locName;
		
	}
	
	
	
	private  int randomNumber(int size) {
		Random rand = new Random();
		int n = rand.nextInt(size) ;
		return n;
	}


	public boolean isFuelPriceWidgetVisible() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(fuelPricePageObjects.getFindFuelPriceWidget());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean isLineChartDisplayed() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(fuelPricePageObjects.getLineChart());
			flag = true;
		}catch(NoSuchElementException e) {
			if(WebBaseMethods.isDisplayed(fuelPricePageObjects.getNoRecFoundMsg()))
				flag = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public List<String> getStateDateList() {
			List<String> dates = new ArrayList<>();
			try {
				List<WebElement> li = fuelPricePageObjects.getDateList();
				dates = VerificationUtil.getLinkTextList(li);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return dates;
	}

}
