package app.pageobjects;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.openqa.selenium.support.FindBy;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class StoryPageObjects {

	private String byStoryHeadline = "//*[contains(@resource-id,'headline') and @text='%s']";

	@FindBy(tagName = "h1")
	private MobileElement h1;

	@HowToUseLocators(iOSXCUITAutomation = LocatorGroupStrategy.ALL_POSSIBLE, androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
	@AndroidFindBy(id = "com.et.reader.activities:id/story_headline")
	@AndroidFindBy(id = "com.et.reader.activities:id/showcase_headline")
	@AndroidFindBy(id = "com.et.reader.activities:id/headline")
	@AndroidFindBy(id = "com.et.reader.activities:id/tv_news_detail_title")
	@AndroidFindBy(xpath = "//android.widget.TextView[@index='1']")
	@iOSXCUITFindBy(accessibility = "story_headline")
	@iOSXCUITFindBy(iOSNsPredicate = "type =='XCUIElementTypeTextView' && name == 'acc_lbl_headline'")
	@iOSXCUITFindBy(iOSNsPredicate = "type =='XCUIElementTypeStaticText' && name == 'SlideShow_detail_title' && visible==1")
	@iOSXCUITFindBy(iOSNsPredicate = "type =='XCUIElementTypeStaticText' && name == 'Acc_liveBlog_title' && visible==1")
	@iOSXCUITFindBy(iOSNsPredicate = "type =='XCUIElementTypeStaticText' && name == 'Acc_brief_title' && visible==1")
	@iOSXCUITFindBy(iOSNsPredicate = "type =='XCUIElementTypeStaticText' &&  name beginswith 'Acc_label' && visible==1")
	private MobileElement storyHeadline;

	@AndroidFindBy(id = "com.et.reader.activities:id/coach_mark_layout")
	@WithTimeout(time = 2, chronoUnit = ChronoUnit.MILLIS)
	private MobileElement educativeGuide;

	@HowToUseLocators(iOSXCUITAutomation = LocatorGroupStrategy.ALL_POSSIBLE, androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
	@AndroidFindBy(id = "com.et.reader.activities:id/story_agency")
	@AndroidFindBy(id = "com.et.reader.activities:id/showcase_dateline")
	@AndroidFindBy(id = "com.et.reader.activities:id/tv_news_date")
	@AndroidFindBy(id = "com.et.reader.activities:id/publishTime")
	@iOSXCUITFindBy(accessibility = "publish_time")
	@iOSXCUITFindBy(iOSNsPredicate = "type =='XCUIElementTypeStaticText' && name == 'acc_lbl_date'")
	@iOSXCUITFindBy(iOSNsPredicate = "type =='XCUIElementTypeStaticText' && name =='SlideShow_detail_date'")
	@iOSXCUITFindBy(iOSNsPredicate = "type =='XCUIElementTypeStaticText' && name =='Acc_liveBlog_date'")
	private MobileElement storyInfo;

	@WithTimeout(time = 2, chronoUnit = ChronoUnit.SECONDS)
	@AndroidFindBy(xpath = "//*[contains(@resource-id,'com.android.chrome')]")
	@HowToUseLocators(iOSXCUITAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
	@iOSXCUITFindBy(iOSNsPredicate = "name contains 'BrowserView' && visible==1")
	private MobileElement wapView;

	@AndroidFindBy(xpath = "//*[@*[contains(.,'video')]]")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='Video' && visible==1")
	@WithTimeout(time = 2, chronoUnit = ChronoUnit.MILLIS)
	private MobileElement videoView;

	@WithTimeout(time = 2, chronoUnit = ChronoUnit.MILLIS)
	@iOSXCUITFindBy(iOSNsPredicate = "(name=='m' || name=='t') && visible==1")
	private MobileElement videoCloseIcon;

	@AndroidFindBy(id = "com.et.reader.activities:id/ll_container")
	private MobileElement nativeContainer;

	@AndroidFindBy(id = "com.android.email:id/instructions")
	private List<MobileElement> emailSetupInstructions;

	@AndroidFindBy(id = "com.et.reader.activities:id/share_facebook_top")
	private MobileElement fbShare;

	@AndroidFindBy(id = "com.et.reader.activities:id/share_twitter_top")
	private MobileElement twitterShare;

	@AndroidFindBy(id = "com.et.reader.activities:id/share_mail_top")
	private MobileElement emailShare;

	@AndroidFindBy(id = "com.et.reader.activities:id/share_sms_top")
	private MobileElement smsShare;

	@AndroidFindBy(id = "com.google.android.gm:id/subject")
	private MobileElement emailSubject;

	@AndroidFindBy(id = "com.facebook.katana:id/login_username")
	private MobileElement fbNativeUserName;

	@AndroidFindBy(id = "com.facebook.katana:id/login_password")
	private MobileElement fbNativePassword;

	@AndroidFindBy(className = "android.widget.Button")
	private MobileElement fbNativeSignIn;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").index(0)")
	private MobileElement fbWebViewUName;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").index(1)")
	private MobileElement fbWebViewPwd;

	@AndroidFindBy(id = "u_0_6")
	private MobileElement fbWebViewSignIn;

	@AndroidFindBy(xpath = "//*[@id=\"u_0_p\"]/div/div[2]/header/h5")
	private MobileElement fbArticleName;

	@AndroidFindBy(xpath = "//*[@id=\"u_0_d\"]")
	private MobileElement fbArticlePost;

	@AndroidFindBy(id = "com.android.mms:id/embedded_text_editor")
	private MobileElement smsTextArea;

	@AndroidFindBy(id = "com.et.reader.activities:id/card_view")
	private List<MobileElement> commentCard;

	@AndroidFindBy(id = "com.et.reader.activities:id/comments_count")
	private MobileElement commentsCount;

	@AndroidFindBy(id = "android:id/text1")
	private List<MobileElement> message;

	@AndroidFindBy(id = "android:id/home")
	private MobileElement messageBackButton;

	@AndroidFindBy(id = "android:id/button1")
	private MobileElement okButton;

	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"NEWS\")")
	private MobileElement pageHeader;

	@AndroidFindBy(xpath = "//*[@id='mainPage']/div[1]/div[1]/div[1]/div[2]")
	private MobileElement webBriefHeading;

	@AndroidFindBy(id = "com.et.reader.activities:id/dp_slot_header")
	private MobileElement moreFromOurPartners;

	@AndroidFindBy(id = "com.et.reader.activities:id/dp_slot_headline")
	private List<MobileElement> moreFromPartnerItems;

	@AndroidFindBy(id = "com.et.reader.activities:id/tv_advertiser")
	private List<MobileElement> aroundTheWebItems;

	@AndroidFindBy(id = "com.et.reader.activities:id/story_more_from_web")
	private MobileElement aroundTheWeb;

	@AndroidFindBy(id = "com.et.reader.activities:id/related_topics_header")
	private MobileElement relatedTopicHeader;

	@AndroidFindBy(xpath = "//android.widget.LinearLayout[@resource-id='com.et.reader.activities:id/ll_read_more_buttons']//android.widget.TextView")
	private List<MobileElement> relatedTopics;

	@AndroidFindBy(id = "com.et.reader.activities:id/action_comment_count_tv")
	@iOSXCUITFindBy(accessibility = "ic singular comment")
	private MobileElement commentIcon;

	@AndroidFindBy(id = "com.et.reader.activities:id/comment_edit_text")
	@iOSXCUITFindBy(accessibility = "comment_newComment_textView_label")
	private MobileElement commentTextField;

	@AndroidFindBy(id = "com.et.reader.activities:id/comment_post")
	@iOSXCUITFindBy(accessibility = "Post")
	private MobileElement commentPostButton;

	@iOSXCUITFindBy(accessibility = "comment_postNewComment_label")
	private MobileElement commentEditButton;

	@AndroidFindBy(id = "com.et.reader.activities:id/snackbar_text")
	private MobileElement successMessage;

	@AndroidFindBy(id = "com.et.reader.activities:id/story_caption")
	private MobileElement articleshowStoryCaption;

	@iOSXCUITFindBy(iOSNsPredicate = "name ='primeDetail_author_label'")
	@AndroidFindBy(id = "com.et.reader.activities:id/author_name")
	private MobileElement articleshowAuthor;

	@AndroidFindBy(id = "com.et.reader.activities:id/publishTime")
	private MobileElement articleshowDate;

	@AndroidFindBy(id = "com.et.reader.activities:id/tv_hlt")
	private MobileElement articleshowSummary;

	@AndroidFindBy(id = "com.et.reader.activities:id/progressBar")
	private MobileElement progressBar;

	@AndroidFindBy(id = "com.et.reader.activities:id/news_letter_container")
	private MobileElement newsletterContainer;

	@AndroidFindBy(id = "com.et.reader.activities:id/btn_subscribe")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='SUBSCRIBE'")
	private List<MobileElement> newsletterSubscribeButton;

	@AndroidFindBy(id = "com.et.reader.activities:id/edit_email")
	@HowToUseLocators(iOSXCUITAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
	@iOSXCUITFindBy(iOSNsPredicate = "value=='Enter your email id")
	@iOSXCUITFindBy(xpath = "//*[contains(@type,'XCUIElementTypeTextField')]")
	private MobileElement newsletterEmailField;

	@AndroidFindBy(id = "com.et.reader.activities:id/heading_tv")
	private MobileElement newsletterHeading;

	@AndroidFindBy(id = "com.et.reader.activities:id/story_section")
	private MobileElement storySection;

	@HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
	@AndroidFindBy(id = "com.et.reader.activities:id/headingTV")
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.et.reader.activities:id/headline\")")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='News_Headline' && visible ==1")
	private List<MobileElement> newsHeadings;

	@AndroidFindBy(id = "com.et.reader.activities:id/subscribe_footer_container")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='SUBSCRIBE TO ET PRIME'")
	private List<MobileElement> primeStoryFooter;

	@AndroidFindBy(id = "com.et.reader.activities:id/tv_thanks_heading")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='Thank you for subscribing'")
	private List<MobileElement> newsletterConfirmationLinkWidget;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='More From Our Partners']")
	private MobileElement MoreFromPartners;

	@AndroidFindBy(id = "com.et.reader.activities:id/prime_story_header_logo")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='ic_singular_prime_logo'")
	private List<MobileElement> primeLogoOnArticleshow;

	@iOSXCUITFindBy(iOSNsPredicate = "name=='ADD COMMENT'")
	private MobileElement addComment;

	@HowToUseLocators(iOSXCUITAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
	@iOSXCUITFindBy(accessibility = "START FREE TRIAL")
	@iOSXCUITFindBy(accessibility = "SUBSCRIBE NOW")
	@AndroidFindBy(id = "com.et.reader.activities:id/block_story_container")
	private MobileElement primeSubscribeWidget;

	/* Getters */

	public List<MobileElement> getRelatedTopics() {
		return relatedTopics;
	}

	public MobileElement getRelatedTopicHeader() {
		return relatedTopicHeader;
	}

	public List<MobileElement> getMoreFromPartnerItems() {
		return moreFromPartnerItems;
	}

	public MobileElement getH1() {
		return h1;
	}

	public String getByStoryHeadline() {
		return byStoryHeadline;
	}

	public MobileElement getMessageBackButton() {
		return messageBackButton;
	}

	public List<MobileElement> getMessage() {
		return message;
	}

	public MobileElement getCommentsCount() {
		return commentsCount;
	}

	public MobileElement getSmsTextArea() {
		return smsTextArea;
	}

	public MobileElement getFbNativeSignIn() {
		return fbNativeSignIn;
	}

	public MobileElement getStoryHeadline() {
		return storyHeadline;
	}

	public MobileElement getEducativeGuide() {
		return educativeGuide;
	}

	public MobileElement getStoryInfo() {
		return storyInfo;
	}

	public MobileElement getWapView() {
		return wapView;
	}

	public MobileElement getVideoView() {
		return videoView;
	}

	public MobileElement getNativeContainer() {
		return nativeContainer;
	}

	public MobileElement getFbShare() {
		return fbShare;
	}

	public MobileElement getTwitterShare() {
		return twitterShare;
	}

	public MobileElement getEmailShare() {
		return emailShare;
	}

	public MobileElement getEmailSubject() {
		return emailSubject;
	}

	public List<MobileElement> getEmailSetupInstructions() {
		return emailSetupInstructions;
	}

	public MobileElement getFbNativeUserName() {
		return fbNativeUserName;
	}

	public MobileElement getFbNativePassword() {
		return fbNativePassword;
	}

	public MobileElement getFbWebViewUName() {
		return fbWebViewUName;
	}

	public MobileElement getFbWebViewPwd() {
		return fbWebViewPwd;
	}

	public MobileElement getFbWebViewSignIn() {
		return fbWebViewSignIn;
	}

	public MobileElement getFbArticleName() {
		return fbArticleName;
	}

	public MobileElement getFbArticlePost() {
		return fbArticlePost;
	}

	public MobileElement getSmsShare() {
		return smsShare;
	}

	public List<MobileElement> getCommentCard() {
		return commentCard;
	}

	public MobileElement getOkButton() {
		return okButton;
	}

	public MobileElement getPageHeader() {
		return pageHeader;
	}

	public MobileElement getWebBriefHeading() {
		return webBriefHeading;
	}

	public MobileElement getVideoCloseIcon() {
		return videoCloseIcon;
	}

	public MobileElement getMoreFromOurPartners() {
		return moreFromOurPartners;
	}

	public List<MobileElement> getAroundTheWebItems() {
		return aroundTheWebItems;
	}

	public MobileElement getAroundTheWeb() {
		return aroundTheWeb;
	}

	public MobileElement getCommentIcon() {
		return commentIcon;
	}

	public MobileElement getCommentTextField() {
		return commentTextField;
	}

	public MobileElement getCommentPostButton() {
		return commentPostButton;
	}

	public MobileElement getSuccessMessage() {
		return successMessage;
	}

	public MobileElement getArticleshowStoryCaption() {
		return articleshowStoryCaption;
	}

	public MobileElement getCommentEditButton() {
		return commentEditButton;
	}

	public MobileElement getArticleshowAuthor() {
		return articleshowAuthor;
	}

	public MobileElement getArticleshowDate() {
		return articleshowDate;
	}

	public MobileElement getArticleshowSummary() {
		return articleshowSummary;
	}

	public List<MobileElement> gethtmlContent(AppiumDriver<?> driver) {
		return (List<MobileElement>) driver.findElements(MobileBy.id("com.et.reader.activities:id/html_texview"));
	}

	public List<MobileElement> getprimeArticleshowInfogramEmbed(AppiumDriver<?> driver) {
		return (List<MobileElement>) driver.findElements(MobileBy.id("com.et.reader.activities:id/imgView"));
	}

	public List<MobileElement> getprimeArticleshowTwitterEmbed(AppiumDriver<?> driver) {
		return (List<MobileElement>) driver.findElements(MobileBy.id("com.et.reader.activities:id/tw__twitter_logo"));
	}

	public List<MobileElement> getprimeArticleshowSlideshowEmbed(AppiumDriver<?> driver) {
		return (List<MobileElement>) driver
				.findElements(MobileBy.id("com.et.reader.activities:id/story_slide_show_container"));
	}

	public List<MobileElement> getprimeArticleshowAudioEmbed(AppiumDriver<?> driver) {
		return (List<MobileElement>) driver
				.findElements(MobileBy.id("com.et.reader.activities:id/story_slide_show_container"));
	}

	public MobileElement getprogressbar() {
		return progressBar;
	}

	public MobileElement getNewsletterEmailField() {
		return newsletterEmailField;
	}

	public List<MobileElement> getNewsletterSubscribeButton() {
		return newsletterSubscribeButton;
	}

	public MobileElement getNewsletterContainer() {
		return newsletterContainer;
	}

	public MobileElement getNewsletterHeading() {
		return newsletterHeading;
	}

	public MobileElement getStorySection() {
		return storySection;
	}

	public List<MobileElement> getNewsHeadings() {
		return newsHeadings;
	}

	public List<MobileElement> getPrimeStoryFooter() {
		return primeStoryFooter;
	}

	public List<MobileElement> getNewsletterConfirmationLinkWidget() {
		return newsletterConfirmationLinkWidget;
	}

	public MobileElement getMoreFromPartners() {
		return MoreFromPartners;
	}

	public List<MobileElement> getPrimeLogoOnArticleshow() {
		return primeLogoOnArticleshow;
	}

	public MobileElement getAddComment() {
		return addComment;
	}

	public MobileElement getprimeSubscribeWidget() {
		return primeSubscribeWidget;
	}
}