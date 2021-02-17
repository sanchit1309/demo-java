package app.pageobjects;

import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ManageNewsletterPageObjects {
    @AndroidFindBy(xpath = "//android.view.View[@text='NEVER MISS A STORY THAT MATTERS']")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='NEVER MISS A STORY THAT MATTERS'")
    private MobileElement newsletterHeading;

    @AndroidFindBy(id = "com.et.reader.activities:id/tv_browse_newsletters")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='Browse more Newsletters'  && visible ==1")
    private MobileElement BrowseMoreNewsletter;

    @AndroidFindBy(xpath = "//android.view.View[contains(text(),'Subscribe to our newsletters and get business news delivered straight into ']")
    private MobileElement emailHeading;

    @AndroidFindBy(id = "com.et.reader.activities:id/heading_tv")
    private MobileElement newsletterAvailableOnArticleshowPage;

    @AndroidFindBy(id = "com.et.reader.activities:id/btn_subscribe")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='SUBSCRIBE'")
    private MobileElement subscribeButton;

    @AndroidFindBy(xpath = "//android.view.View[@text='Unsubscribe']")
    private List<MobileElement> unsubscribeButton;


    @AndroidFindBy(xpath = "//android.widget.Button[@text='SUBSCRIBE']")
    private List<MobileElement> subscribeButtonOnManageNEwletterPage;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='SUBMIT']")
    private List<MobileElement> submitButton;


    @AndroidFindBy(xpath = "//android.widget.EditText[@index='0']")
    private MobileElement email;


    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Subscribed']")
    private List<MobileElement> subscribedLetter;

//Getters

    public MobileElement getNewsletterHeading() {
        return newsletterHeading;
    }

    public MobileElement getBrowseMoreNewsletter() {
        return BrowseMoreNewsletter;
    }

    public MobileElement getEmailHeading() {
        return emailHeading;
    }

    public MobileElement getSubscribeButton() {
        return subscribeButton;
    }

    public MobileElement getNewsletterAvailableOnArticleshowPage() {
        return newsletterAvailableOnArticleshowPage;
    }

    public List<MobileElement> getUnsubscribeButton() {
        return unsubscribeButton;
    }


    public List<MobileElement> getSubscribeButtonOnManageNEwletterPage() {
        return subscribeButtonOnManageNEwletterPage;
    }

    public List<MobileElement> getSubmitButton() {
        return submitButton;
    }

    public MobileElement getEmailEnterField() {
        return email;
    }

    public List<MobileElement> getSubscribedLetter() {
        return subscribedLetter;
    }

}

