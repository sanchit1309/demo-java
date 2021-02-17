package com.pagemethods;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;

import app.pageobjects.StoryPageObjects;
import common.launchsetup.BaseTest;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import common.utilities.reporting.ScreenShots;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import static common.launchsetup.BaseTest.iAppCommonMethods;

public class StoryPageMethods {

    AppiumDriver<?> appDriver;
    StoryPageObjects storyPageObjects;
    private AlertsPromptsMethods alertsPromptsMethods;

    public StoryPageMethods(AppiumDriver<?> driver) {
        appDriver = driver;
        storyPageObjects = new StoryPageObjects();
        alertsPromptsMethods = new AlertsPromptsMethods(appDriver);
        PageFactory.initElements(new AppiumFieldDecorator(appDriver), storyPageObjects);
        tapEducativeScreen();


    }

    // check headline length and skip if it is wap view
    public Boolean checkHeadlineLength() {
        Boolean headlineValidate = null;
        try {
			WaitUtil.sleep(2000);
            String dpHeadline = storyPageObjects.getStoryHeadline().getText();
            System.out.println("dpHeadline: " + dpHeadline);
            headlineValidate = dpHeadline.length() > 0 ? true : false;

        } catch (NoSuchElementException | NullPointerException e) {
            headlineValidate = null;
        }
        return headlineValidate;
    }

    public String getHeadlineText() {
        try {
            return storyPageObjects.getStoryHeadline().getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean getHeadline() {
        boolean flag = false;
        try {
            storyPageObjects.getStoryHeadline().getText();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void tapEducativeScreen() {
        try {
            storyPageObjects.getEducativeGuide().click();
        } catch (NoSuchElementException e) {
            // TODO: handle exception
        }
    }

    public boolean isMorningBrief() {
        try {
            storyPageObjects.getEducativeGuide().isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getArticleDate() {
        String date = null;
        String readText = "";

        try {
            readText = storyPageObjects.getStoryInfo().getText();
            System.out.println("date:" + readText);
            readText = readText.trim().replaceAll("hrs", "").replaceAll("[,\\s]", "").replaceAll("[.]", ":");
            List<String> monthsSplit = Arrays
                    .asList("Jan,JAN,Feb,FEB,Mar,MAR,Apr,APR,MAY,May,Jun,JUN,Jul,JUL,Aug,AUG,Sep,SEP,Oct,OCT,Nov,NOV,Dec,DEC"
                            .split("\\s*,\\s*"));
            for (String month : monthsSplit) {
                Pattern p = Pattern.compile((String.format("\\s*\\d*%s\\s*\\d+\\s*\\d+", month)));
                Matcher m = p.matcher(readText);
                if (m.find()) {
                    date = m.group();
                    Pattern p1 = Pattern.compile("20\\d{1}\\d{1}+");
                    Matcher m1 = p1.matcher(readText);
                    String year = "2018";
                    if (m1.find()) {
                        year = m1.group();
                    }
                    date = date.replaceAll(year + ".*", year);
                    break;
                }
            }
        } catch (Exception e) {
            new ScreenShots().seleniumNativeScreenshot(appDriver, "exceptionDate");
            date = null;
        }
        return date;

    }

    public Boolean checkIfDateinRange(int days) {
        Boolean flag = false;
        try {
            String sDate = null;
            sDate = getArticleDate();
            sDate = sDate == null ? ((new BriefPageMethods(appDriver).isBriefPageShown()) == true
                    ? new SimpleDateFormat("MMMddyyyyhh:mma").format(new Date()) : null) : sDate;
            if (sDate == null)
                return null;
            flag = VerificationUtil.isLatest(sDate, days);
        } catch (IllegalStateException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;

    }

    public boolean skipWAP() {
        boolean flag = false;
        try {
            storyPageObjects.getWapView().isDisplayed();
            flag = true;
            iAppCommonMethods.navigateBack(appDriver);
        } catch (NoSuchElementException e) {
        }
        /*
         * try{ if(flag){ storyPageObjects.getNativeContainer().isDisplayed();
         * flag = true; } } catch (NoSuchElementException e) { flag = false; }
         */
        return flag;

    }

    public boolean isBrief() {
        Boolean flag = false;
        try {
            new AlertsPromptsMethods(appDriver).dismissCoachMark();
            flag = new HeaderPageMethods(appDriver).getHeaderText().contains("Brief") ? true : false;
        } catch (NoSuchElementException e) {

        }
        return flag;
    }

    public boolean verifyArticleSharingEmail() {
        boolean compare1 = false;
        boolean compare2 = false;
        try {
            WaitUtil.sleep(1000);
            String articleName = getHeadlineText();
            storyPageObjects.getEmailShare().click();
            WaitUtil.sleep(1000);
            if (storyPageObjects.getMessage().size() > 0) {
                iAppCommonMethods.tapScreenRightSide(appDriver);
                /*
                 * TouchAction swipe = new TouchAction(appDriver);
                 * swipe.tap(100, 100).perform();
                 */
                return false;
            } else {
                String emailSubject = storyPageObjects.getEmailSubject().getText().trim();
                compare1 = articleName.contains(emailSubject);
                compare2 = emailSubject.contains(articleName);
                if (compare1 || compare2) {
                    iAppCommonMethods.navigateBack(appDriver);
                    iAppCommonMethods.navigateBack(appDriver);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifyArticleSharingSMS() {
        boolean flag = false;
        boolean compare1 = false;
        boolean compare2 = false;
        try {
            WaitUtil.sleep(1000);

            String articleName = getHeadlineText();
            storyPageObjects.getSmsShare().click();
            WaitUtil.sleep(1000);
            String smsContent = storyPageObjects.getSmsTextArea().getText().trim();
            compare1 = articleName.contains(smsContent);
            compare2 = smsContent.contains(articleName);
            if (compare1 || compare2) {
                flag = true;
            }
            storyPageObjects.getMessageBackButton().click();
            storyPageObjects.getOkButton().click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean verifyArticleSharingFb(String email, String pwd) {
        try {
            String articleName = getHeadlineText();
            WaitUtil.sleep(1000);
            storyPageObjects.getFbShare().click();
            new LoginPageMethods(appDriver).loginWithFBWebView(email, pwd);
            WaitUtil.sleep(1000);
            String fbArticleName1 = storyPageObjects.getFbArticleName().getText();
            if (VerificationUtil.matchHeadline(articleName, fbArticleName1)) {
                storyPageObjects.getFbArticlePost().click();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int bookmarkArticle(List<MobileElement> articleHeadlines) {
        int count = 0;
        HeaderPageMethods headerPageMethods = new HeaderPageMethods(appDriver);
        AppListingPageMethods appListingPageMethods = new AppListingPageMethods(appDriver);
        try {
            for (MobileElement aHeadline : articleHeadlines) {
                appListingPageMethods.clickNewsHeadline(aHeadline);
                WaitUtil.sleep(1000);
                alertsPromptsMethods.dismissGoogleSignInpopup();
                headerPageMethods.clickBookMarkIcon();
                count++;
                iAppCommonMethods.navigateBack(appDriver);
                WaitUtil.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public Boolean isPageHeaderPresent() {
        Boolean flag;

        try {
            flag = storyPageObjects.getPageHeader().isDisplayed() ? true : false;
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    public String briefheading() {
        tapEducativeScreen();
        try {
            return storyPageObjects.getWebBriefHeading().getText();
        } catch (Exception e) {
            return "";
        }
    }

    public By getStoryHeadlineBy(String headline) {
        System.out.println(String.format(storyPageObjects.getByStoryHeadline(), headline));
        return By.xpath(String.format(storyPageObjects.getByStoryHeadline(), headline));
    }

    public String getHeadlineWebView() {
        iAppCommonMethods.switchToWebView(appDriver, "chrome");
        String text = "";
        try {
            text = storyPageObjects.getH1().getText();
        } catch (WebDriverException e) {

        }
        iAppCommonMethods.navigateBack(appDriver);
        iAppCommonMethods.switchToNativeView(appDriver);
        return text;
    }

    public void clickVideoCloseIcon() {
        try {
            storyPageObjects.getVideoCloseIcon().click();
        } catch (NoSuchElementException e) {

        }
    }

    public void navigateBackToListPage(AppiumDriver<?> appiumDriver) {
        if (BaseTest.platform.contains("ios")) {
            new StoryPageMethods(appiumDriver).clickVideoCloseIcon();
            iAppCommonMethods.swipeDown();
            WaitUtil.sleep(1000);
            iAppCommonMethods.navigateBack(appiumDriver);
        }
        if (BaseTest.platform.contains("android")) {
            iAppCommonMethods.swipeDown();
            WaitUtil.sleep(4000);
            new AlertsPromptsMethods(appiumDriver).clickAdCloseIcon();
            WaitUtil.sleep(3000);
            iAppCommonMethods.navigateBack(appiumDriver);
            WaitUtil.sleep(1000);
            new AlertsPromptsMethods(appiumDriver).clickRatingCloseIcon();
        }
        WaitUtil.sleep(2000);
    }

    public String getRelatedTopic() {
        String text = null;
        try {
            text = storyPageObjects.getRelatedTopicHeader().getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public List<String> gettopicsName() {
        ArrayList<String> topicsNames = new ArrayList<String>();
        storyPageObjects.getRelatedTopics().forEach((comp) -> {
            topicsNames.add(comp.getText());
        });
        return topicsNames;

    }

    public List<String> getMoreFromPartnerHeadlineList() {
        List<String> moreFromPartnerHeadlines = new ArrayList<>();
        try {
            iAppCommonMethods.scrollUpToElement("MORE FROM OUR PARTNERS");
            WaitUtil.sleep(2000);
            if(BaseTest.platform.contains("android")){
                while(!storyPageObjects.getMoreFromPartners().isDisplayed()){
                    iAppCommonMethods.swipeByScreenPercentage(0.50, 0.60);
                }
            }
            else {
                iAppCommonMethods.swipeByScreenPercentage(0.50, 0.60);
            }
            WaitUtil.sleep(10000);
            iAppCommonMethods.scrollElementTopToHeader(storyPageObjects.getMoreFromOurPartners(),
                    new HeaderPageMethods(appDriver).getTopHeadingEl(), 2000);
            storyPageObjects.getMoreFromPartnerItems().forEach(ele -> {
                moreFromPartnerHeadlines.add(ele.getText());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moreFromPartnerHeadlines;
    }

    public List<String> geFromArndWebHeadlineList() {
        List<String> arndTheWebHeadlines = new ArrayList<>();
        try {
            iAppCommonMethods.scrollUpToElement("FROM AROUND THE WEB");
            WaitUtil.sleep(2000);
            iAppCommonMethods.swipeByScreenPercentage(0.50, 0.60);
            WaitUtil.sleep(10000);

            iAppCommonMethods.scrollElementTopToHeader(storyPageObjects.getAroundTheWeb(),
                    new HeaderPageMethods(appDriver).getTopHeadingEl(), 2000);
            storyPageObjects.getAroundTheWebItems().forEach(ele -> {
                arndTheWebHeadlines.add(ele.getText());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arndTheWebHeadlines;
    }

    public boolean enterValueInCommentBox() {
        String value = "Nice Article";
        boolean flag = false;
        WaitUtil.sleep(2000);
        storyPageObjects.getCommentIcon().click();
        WaitUtil.sleep(2000);
        if (BaseTest.platform.contains("ios"))
            storyPageObjects.getCommentEditButton().click();
        try {
            WaitUtil.sleep(2000);
            storyPageObjects.getCommentTextField().clear();
            BaseTest.iAppCommonMethods.clickElement(storyPageObjects.getCommentTextField());
            WaitUtil.sleep(2000);
            BaseTest.iAppCommonMethods.sendKeys(storyPageObjects.getCommentTextField(), value);
            WaitUtil.sleep(2000);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean clickPostButton() {
        boolean flag = false;
        try {
            storyPageObjects.getCommentPostButton().click();
            flag = true;
        } catch (NoSuchElementException e) {
            flag = false;
            System.out.println("Post button not visible");
        }
        return flag;
    }

    public String getMessagePostComment() {
        boolean flag = false;
        String message = "";
        try {
            flag = storyPageObjects.getSuccessMessage().isDisplayed();
            if (flag) {
                message = storyPageObjects.getSuccessMessage().getText();
            }
        } catch (NoSuchElementException e) {
            System.out.println("No success message");
        }
        return message;
    }

    public HashMap<String, String> getUIArticleDetails() {
        HashMap<String, String> articleShowData = new LinkedHashMap<String, String>();
        String finalContent = "";
        int imgEmbedCount = 0;
        int twitterEmbedCount = 0;
        int slideshowEmbedCount = 0;
        int audioEmbedCount = 0;

        WaitUtil.sleep(1000);
        articleShowData.put("hl", formatText(iAppCommonMethods.getElementText(storyPageObjects.getStoryHeadline())));
        iAppCommonMethods.swipeByScreenPercentage(0.80, 0.20);
        if (iAppCommonMethods.isElementDisplayed(storyPageObjects.getArticleshowStoryCaption()))
            articleShowData.put("imcn", formatText(storyPageObjects.getArticleshowStoryCaption().getText()));
        if (iAppCommonMethods.isElementDisplayed(storyPageObjects.getArticleshowAuthor()))
            articleShowData.put("author", storyPageObjects.getArticleshowAuthor().getText());
        if (iAppCommonMethods.isElementDisplayed(storyPageObjects.getArticleshowDate()))
            articleShowData.put("date", storyPageObjects.getArticleshowDate().getText());
        if (iAppCommonMethods.isElementDisplayed(storyPageObjects.getArticleshowSummary()))
            articleShowData.put("summary", formatText(storyPageObjects.getArticleshowSummary().getText()));
        iAppCommonMethods.swipeByScreenPercentage(0.80, 0.50);

        while (storyPageObjects.gethtmlContent(appDriver).size() > 0) {
            finalContent += getContent(finalContent);
            imgEmbedCount += storyPageObjects.getprimeArticleshowInfogramEmbed(appDriver).size();
            twitterEmbedCount += storyPageObjects.getprimeArticleshowTwitterEmbed(appDriver).size();
            slideshowEmbedCount += storyPageObjects.getprimeArticleshowSlideshowEmbed(appDriver).size();
            audioEmbedCount += storyPageObjects.getprimeArticleshowAudioEmbed(appDriver).size();
            iAppCommonMethods.swipeByScreenPercentage(0.90, 0.35);
        }
        finalContent = finalContent.replaceAll("[^a-zA-Z0-9]", "");
        if (!finalContent.equals(""))
            articleShowData.put("content", formatText(finalContent.trim()));
        // articleShowData.put("img", imgEmbedCount +"");
        articleShowData.put("twitter", twitterEmbedCount + "");
        articleShowData.put("slideshow", slideshowEmbedCount + "");
        // articleShowData.put("audio", audioEmbedCount+"");

        return articleShowData;
    }

    public static String formatText(String text) {
        if (text == null)
            return text;
        text = text.replaceAll("\\\\n", "").replaceAll("[^a-zA-Z0-9]", "");
        try {
            byte[] d = text.getBytes("cp1252");
            text = new String(d, Charset.forName("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return text;
    }

    public String getContent(String finalContent) {
        List<MobileElement> tmpList = storyPageObjects.gethtmlContent(appDriver);
        for (MobileElement e : tmpList) {
            String text = e.getText();
            if (!finalContent.contains(text)) {
                return text;
            }
        }
        return "";
    }

    public List<MobileElement> getHeadlineList() {
        return storyPageObjects.getNewsHeadings();
    }

    public boolean scrollToFirstArticle(List<MobileElement> articleHeadlines) {
        boolean flag = false;
        int count = 0;
        try {
            articleHeadlines.get(0).click();
            alertsPromptsMethods.dismissGoogleSignInpopup();
            WaitUtil.sleep(1000);
            if ((storyPageObjects.getPrimeStoryFooter().isEmpty())) {
                flag = true;
            } else {
                while ((storyPageObjects.getPrimeStoryFooter().size() > 0) && count < 7) {
                    iAppCommonMethods.scrollLeft(storyPageObjects.getPrimeStoryFooter().get(0));
                    count++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean scrollToNewsletter() {
        boolean flag = false;
        try {
        	if(BaseTest.platform.contains("android") )
        	{
            iAppCommonMethods.scrollUpToElement("ADD COMMENT");
            iAppCommonMethods.swipeByScreenPercentage(0.75, 0.55);
            }
            else
            {
            	boolean flag1=scrollToETMutualFundWidget();
            }
            System.out.println("swipeeeeeee start====");
            if (storyPageObjects.getNewsletterSubscribeButton().size()>0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean enterEmailAndSubscribe() {
        boolean flag = false;
        try {
            storyPageObjects.getNewsletterEmailField().sendKeys("et03092019@gmail.com");
            storyPageObjects.getNewsletterSubscribeButton().get(0).click();
            WaitUtil.sleep(1000);
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean validateNewsletterConfirmationWidget() {
        boolean flag = false;
        try {
            WaitUtil.sleep(1000);
            if (storyPageObjects.getNewsletterConfirmationLinkWidget().size() > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean navigateToHomePage() {
        boolean flag = false;
        try {
            iAppCommonMethods.navigateBack(appDriver);
            alertsPromptsMethods.dismissTimesPointPopUp();
            iAppCommonMethods.navigateBack(appDriver);
            alertsPromptsMethods.dismissTimesPointPopUp();
            iAppCommonMethods.navigateBack(appDriver);
            alertsPromptsMethods.dismissTimesPointPopUp();
            iAppCommonMethods.swipeByScreenPercentage(0.50, 0.70);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean validateEmailPrefilledInNewsletter(String prefilledEmail) {
        boolean flag = false;
        try {
            String email = storyPageObjects.getNewsletterEmailField().getText().trim();
            if (email.equalsIgnoreCase(prefilledEmail.trim())) {
                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

	public boolean validatePrimeLogoOnArticleshow() {
		boolean flag = false;
		try {
			WaitUtil.sleep(1000);
			if (storyPageObjects.getPrimeLogoOnArticleshow().size() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean verifyPrimeSubscribeWidget() {
		boolean flag = false;
		int tryTimes = 0;
		while (tryTimes < 5) {
			try {
				iAppCommonMethods.scrollUp();
				if (storyPageObjects.getprimeSubscribeWidget().isDisplayed())
					flag = true;
				break;
			} catch (NoSuchElementException e) {
				flag = false;
			} finally {
				tryTimes++;
			}
		}
		return flag;
	}
	
	public Boolean scrollToETMutualFundWidget() {
		Boolean flag = false;
		int counter = 0;
		while (counter < 15) {
			if (iAppCommonMethods.isElementDisplayed(storyPageObjects.getAddComment())) {
				flag = true;
				break;
			}
			iAppCommonMethods.swipeByScreenPercentage(0.90, 0.45);
			WaitUtil.sleep(1000);
			counter++;
		}
		return flag;
	}
}