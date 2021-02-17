package app.pageobjects;

import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class NotificationHubObjects {
	

	
	@AndroidFindBy(id = "com.et.reader.activities:id/notification_hub_item_share_fb")
	@iOSXCUITFindBy(iOSNsPredicate="name == 'e' && visible ==1")
	private List<MobileElement>  linkedNotification;
	
	@AndroidFindBy(id = "com.et.reader.activities:id/notification_hub_item_headline")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='Acc_synopsis_label' && visible=1")
	private List<MobileElement> notificationHeading;
	
	
	@AndroidFindBy(id="com.et.reader.activities:id/notification_hub_item_dateline")
	@iOSXCUITFindBy(iOSNsPredicate="name=='Acc_date_label'")
	private MobileElement firstNotificationDate;
	
	@AndroidFindBy(id="com.et.reader.activities:id/notification_hub_item_parent")
	private List<MobileElement> notificationList;
	
	@AndroidFindBy(id="com.et.reader.activities:id/notification_hub_item_share_fb")
	private MobileElement fbShare;
	
	@AndroidFindBy(id="com.et.reader.activities:id/notification_hub_item_share_twitter")
	private MobileElement twitterShare;
	
	@AndroidFindBy(id="com.et.reader.activities:id/notification_hub_item_share_whatsapp")
	private MobileElement wpShare;
	
	@AndroidFindBy(id="com.et.reader.activities:id/notification_hub_item_share_mail")
    @iOSXCUITFindBy(iOSNsPredicate="name == 'e'")
	private MobileElement mailShare;
	
	@AndroidFindBy(id="com.et.reader.activities:id/notification_hub_item_share_sms")
	private MobileElement smsShare;
	
	@AndroidFindBy(id="com.et.reader.activities:id/tv_menu_etprime")
	private MobileElement etPrimeIcon;
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text='CURRENT EDITION']")
	private MobileElement primehome;
/////// Getters start here////////
	
	
	public List<MobileElement>  getLinkedNotification() {
		return linkedNotification;
	}
	
	public List<MobileElement>  getNotificationHeading(){
		return notificationHeading;
	}

	public MobileElement getFirstNotificationDate() {
		return firstNotificationDate;
	}

	public void setFirstNotificationDate(MobileElement firstNotificationDate) {
		this.firstNotificationDate = firstNotificationDate;
	}
	public List<MobileElement> getNotificationList(){
		return notificationList;
	}
	
	
	public MobileElement getFbShareIcon() {
		return fbShare;
	}
	
	public MobileElement gettwitterShareIcon() {
		return twitterShare;
	}
	
	public MobileElement getWpShareIcon() {
		return wpShare;
	}
	
	public MobileElement getSmsShareIcon() {
		return smsShare;
	}
	
	public MobileElement getMailShareIcon() {
		return mailShare;
	}
	public MobileElement getEtPrimeIcon() {
		return etPrimeIcon;
	}
	public MobileElement getPrimeHome() {
		return primehome;
	}
}
