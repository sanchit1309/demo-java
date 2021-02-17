package app.pagemethods;

import org.openqa.selenium.support.PageFactory;

import app.pageobjects.ManageNewsletterPageObjects;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import static common.launchsetup.BaseTest.iAppCommonMethods;

public class ManageNewsletterMethods {
    AppiumDriver<?> appDriver;
    ManageNewsletterPageObjects manageNewsletterPageObjects;
    public static String email = "test@gmail.com";

    public ManageNewsletterMethods(AppiumDriver<?> driver) {
        appDriver = driver;
        manageNewsletterPageObjects = new ManageNewsletterPageObjects();
        PageFactory.initElements(new AppiumFieldDecorator(appDriver), manageNewsletterPageObjects);

    }

    public boolean validateManageNewsletterHeading() {
        boolean flag = false;
        try {
            String header = manageNewsletterPageObjects.getNewsletterHeading().getText();
            System.out.println(header);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public String getNewsletterAvailableOnStoryPage() {
        String newsletterHeadingOnStoryPage = manageNewsletterPageObjects.getNewsletterAvailableOnArticleshowPage().getText();
        System.out.println("Story Page newsletter : " + newsletterHeadingOnStoryPage);
        return newsletterHeadingOnStoryPage;
    }

    public boolean clickOnBrowseNewsletterLink() {
        boolean flag = false;
        try {
            manageNewsletterPageObjects.getBrowseMoreNewsletter().click();
            WaitUtil.sleep(10000);
            if (manageNewsletterPageObjects.getNewsletterHeading().isDisplayed())
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean clickOnSubscribeButton() {
        boolean flag = false;
        try {
            manageNewsletterPageObjects.getSubscribeButton().click();
            WaitUtil.sleep(5000);
            if (manageNewsletterPageObjects.getBrowseMoreNewsletter().isDisplayed())
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean validateEmailOnManageNewsletterPage(String email) {
        boolean flag = false;
        try {
            String emailOnNewsletterPage = manageNewsletterPageObjects.getEmailHeading().getText().split("into")[1];
            System.out.println("email : " + emailOnNewsletterPage);
            if (email.trim().equalsIgnoreCase(emailOnNewsletterPage.trim())) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean validateNewsletterSubscribedShown(String newsletterHeading) {
        boolean flag = false;
        iAppCommonMethods.scrollUpToElement(newsletterHeading);
        flag = true;
        return flag;
    }

    public boolean validateUnsubscribeNewsletterFromManageNEwsletterPage() {
        boolean flag = false;
        try {

            if (manageNewsletterPageObjects.getUnsubscribeButton().size() > 0) {
                manageNewsletterPageObjects.getUnsubscribeButton().get(0).click();
                WaitUtil.sleep(8000);
                if (manageNewsletterPageObjects.getSubscribeButtonOnManageNEwletterPage().size() > 0)
                    flag = true;
                iAppCommonMethods.navigateBack(appDriver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean clickOnSubscribeButtonLogOutState() {
        WaitUtil.sleep(5000);
        boolean flag = false;
        try {
            manageNewsletterPageObjects.getSubscribeButtonOnManageNEwletterPage().get(0).click();
            WaitUtil.sleep(2000);
            if (manageNewsletterPageObjects.getSubmitButton().size() > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }

    public boolean validateEmailAndSubmitClick() {
        boolean flag = false;
        try {
            manageNewsletterPageObjects.getEmailEnterField().sendKeys(email);
            WaitUtil.sleep(1000);
            manageNewsletterPageObjects.getSubmitButton().get(0).click();
            if (manageNewsletterPageObjects.getSubscribedLetter().size() > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
