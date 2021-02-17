package app.pagemethods;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

import app.pageobjects.AdTechObjects;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import static common.launchsetup.BaseTest.iAppCommonMethods;

public class AdTechMethods {
	AppiumDriver<?> appDriver;
	AdTechObjects adTechObjects;

	
	public AdTechMethods(AppiumDriver<?> driver){
		appDriver =  driver;
		adTechObjects = new AdTechObjects();
		
		PageFactory.initElements(new AppiumFieldDecorator(appDriver),adTechObjects);
	}

	public boolean isAdDisplayed(String adType){
		boolean flag = false;
		MobileElement element = null;
		try{
			WaitUtil.sleep(5000);
			switch (adType){
			case "Header":
				element = adTechObjects.getTopAd();
				break;
			case "Colombia":
				//iAppCommonMethods.swipeByScreenPercentage(0.55, 0.30);
				element = adTechObjects.getColombiaAd();
				break;
			case "Footer":
				element = adTechObjects.getFooterAd();
				break;
			case "HeaderArticle":
				element = adTechObjects.getHeaderAdArticle();
				break;
			case "Mrec Listing":
				element = adTechObjects.getMrecListing();
				break;

			}
			flag = (element!=null);
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
		
	}

	public boolean isMrecAdDisplayed(){
		boolean flag = false;
		for(int i=0; i < 4; i++){
			try{
				adTechObjects.getMrecAdArticle();
				flag = true;
				break;
			}catch(NoSuchElementException e){
				iAppCommonMethods.scrollUp();
				e.printStackTrace();
				flag = false;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println("MREC Flag ="+ flag);
		return flag;
	}


}
