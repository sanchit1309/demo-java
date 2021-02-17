package prime.wap.pagemethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import prime.wap.pageobjects.MySubscriptionPageObjects;
import web.pagemethods.WebBaseMethods;
import web.pageobjects.HeaderPageObjects;


public class MySubscriptionPageMethod {
	private WebDriver driver;
	private MySubscriptionPageObjects mySubscriptionPageObjects;
	private HeaderPageObjects headerObjects;

	public MySubscriptionPageMethod(WebDriver driver) {
		this.driver = driver;
		headerObjects = PageFactory.initElements(driver, HeaderPageObjects.class);
		mySubscriptionPageObjects = PageFactory.initElements(driver, MySubscriptionPageObjects.class);
	}


	public boolean isSubscriptionBoxDisplayed()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(mySubscriptionPageObjects.getSubscriptionBox(),20);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isSubscriptionBoxContainsText(String text)
	{
		boolean flag = false;
		try {
			if(mySubscriptionPageObjects.getSubscriptionDetails().getText().contains(text))
				flag = true;

		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public boolean isSubscribeNowButtonDisplayed()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(mySubscriptionPageObjects.getSubscribeNowButton(),20);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public boolean isRenewButtonDisplayed()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(mySubscriptionPageObjects.getRenewButton(),10);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public boolean isCancelButtonDisplayed()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(mySubscriptionPageObjects.getCancelButton(),10);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public boolean clickCancelButton()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(mySubscriptionPageObjects.getCancelButton());
			
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public boolean clickOtherOptionInCancelSuvery()
	{
		boolean flag = false;
		try {
			WebBaseMethods.isDisplayed(mySubscriptionPageObjects.getOtherOptionInCancelSurvey(), 10);
			WebBaseMethods.clickElementUsingJSE(mySubscriptionPageObjects.getOtherOptionInCancelSurvey());
			flag = true;
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public boolean clickNextButtonInSurvey()
	{
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(mySubscriptionPageObjects.getNextButtonInSurvey());
			flag = true;
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public boolean isCancelMembershipButtonDisplayed()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(mySubscriptionPageObjects.getCancelMembershipButton(),10);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public boolean isCancelMembershipWrapperDisplayed()
	{
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(mySubscriptionPageObjects.getCancelMembershipWrapper(),10);
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}

}
