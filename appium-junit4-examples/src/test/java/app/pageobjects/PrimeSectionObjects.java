package app.pageobjects;

import java.util.List;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindByAllSet;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class PrimeSectionObjects {

	// @AndroidFindBy(id = "com.et.reader.activities:id/action_et_prime")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='ET Prime']")
	@iOSXCUITFindBy(xpath = "//*[contains(@name,'ET Prime')]")
	private MobileElement primeHomepageIcon;

	@iOSXCUITFindBy(iOSNsPredicate = "name ='prime_headline_label' && visible ==1")
	@AndroidFindByAllSet(value = { @AndroidFindAll(value = { @AndroidBy(id = "com.et.reader.activities:id/tv_news_big"),
			@AndroidBy(id = "com.et.reader.activities:id/tv_news_small"),
			@AndroidBy(id = "com.et.reader.activities:id/tv_title_news") }) })

	private List<MobileElement> primeHomepageStories;

	@iOSXCUITFindBy(className = "XCUIElementTypeScrollView")
	@AndroidFindBy(className = "android.widget.HorizontalScrollView")
	private MobileElement homePageTopHeder;

	@iOSXCUITFindBy(iOSNsPredicate = "name ='primeDetail_headline_label'")
	@AndroidFindBy(id = "com.et.reader.activities:id/story_headline")
	private MobileElement primeArticleshowHeading;

	@iOSXCUITFindBy(iOSNsPredicate = "name ='primeDetail_date_label'")
	@AndroidFindBy(id = "com.et.reader.activities:id/publishTime")
	private MobileElement primeArticleshowDate;

	@iOSXCUITFindBy(iOSNsPredicate = "name ='primeDetail_summary_label'")
	@AndroidFindBy(id = "com.et.reader.activities:id/tv_hlt")
	private MobileElement primeArticleshowSummary;

	@AndroidFindBy(id = "com.et.reader.activities:id/story_caption")
	private MobileElement primeArticleshowStoryCaption;

	@iOSXCUITFindBy(iOSNsPredicate = "name ='primeDetail_author_label'")
	@AndroidFindBy(id = "com.et.reader.activities:id/author_name")
	private MobileElement primeArticleshowAuthor;

	@iOSXCUITFindBy(iOSNsPredicate = "name ='primeDetail_tag_label'")
	@AndroidFindBy(id = "com.et.reader.activities:id/tv_primary_tag")
	private MobileElement primeArticleshowPrimaryTag;

	@iOSXCUITFindBy(accessibility = "AUTHORS")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='AUTHORS']")
	private MobileElement primeAuthorsTab;

	@iOSXCUITFindBy(accessibility = "STORIES")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='STORIES']")
	private MobileElement primeAuthorsStoriesTab;

	@iOSXCUITFindBy(accessibility = "BROWSE")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='BROWSE']")
	private MobileElement primeBrowseTab;

	@iOSXCUITFindBy(xpath = "//*[contains(@name,'ET Prime')]")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='ET Prime']")
	private MobileElement primeHomeTab;

	@iOSXCUITFindBy(accessibility = "CONTRIBUTIONS")
	@AndroidFindBy(uiAutomator = "new UiSelector().text(\"CONTRIBUTIONS\")")
	private MobileElement primeAuthorsContributionsTab;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Content coming soon...' && visible ==1")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Content coming soon...']")
	private MobileElement primeContentComingSoonRed;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'COMMENT POSTED ON' && visible ==1")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='COMMENT POSTED ON']")
	private MobileElement authorCommentPostedOnHeading;

	@iOSXCUITFindBy(iOSNsPredicate = "name ='prime_headline_label'")
	@AndroidFindBy(id = "com.et.reader.activities:id/tv_title_news")
	private List<MobileElement> primeSectionStories;

	@iOSXCUITFindBy(iOSNsPredicate = "name='primeAuthorContributor_viewFullProfile_label' && visible ==1")
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='View Full Profile']")
	private MobileElement authorViewFullProfile;

	@iOSXCUITFindBy(accessibility = "primeAuthor_article_title_label")
//	@AndroidFindByAllSet(value = { @AndroidFindAll(value = { @AndroidBy(id = "com.et.reader.activities:id/tv_news_big"),
	// @AndroidBy(id = "com.et.reader.activities:id/tv_news_small") }) })
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.et.reader.activities:id/headline\")")
	private List<MobileElement> primeAuthorStories;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'ic et logo pink'")
	@AndroidFindBy(id = "com.et.reader.activities:id/et_home")
	private MobileElement etLogo;

	@iOSXCUITFindBy(iOSNsPredicate = "name BEGINSWITH prime_header")
	@AndroidFindBy(id = "com.et.reader.activities:id/tv_header")
	private MobileElement sectionHeader;

	@iOSXCUITFindBy(accessibility = "prime_search_textfield")
	@AndroidFindBy(id = "com.et.reader.activities:id/edt_search")
	private MobileElement primeSearchFeild;

	@iOSXCUITFindBy(iOSNsPredicate = "name ='prime_search_item'")
	@AndroidFindBy(id = "com.et.reader.activities:id/search_news_text_tv")
	private List<MobileElement> primeSearchResult;

	@iOSXCUITFindBy(accessibility = "Search_Navigation_Bar_Prime")
	@AndroidFindBy(id = "com.et.reader.activities:id/prime_search")
	private MobileElement primeSeachIcon;

	@iOSXCUITFindBy(accessibility = "START FREE TRIAL")
	@AndroidFindBy(id = "com.et.reader.activities:id/block_story_container")
	private MobileElement primeSubscribeWidget;

	@AndroidFindBy(id = "com.et.reader.activities:id/ib_close_add_comment")
	private List<MobileElement> closeAd;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Our Esteemed Readers']")
	@iOSXCUITFindBy(xpath = "//*[contains(@name,'Our Esteemed Readers')]")
	private MobileElement primeHomeEndsection;

	@AndroidFindBy(id = "com.et.reader.activities:id/imgView")
	private MobileElement primeArticleshowInfogramEmbed;

	@AndroidFindBy(id = "com.et.reader.activities:id/tw__twitter_logo")
	private MobileElement primeArticleshowTwitterEmbed;

	@AndroidFindBy(id = "com.et.reader.activities:id/story_slide_show_container")
	private MobileElement primeArticleshowSlideshowEmbed;

	@AndroidFindBy(id = "com.et.reader.activities:id/action_font")
	private MobileElement primeArticleshowFontIcon;

	@AndroidFindBy(id = "com.et.reader.activities:id/tv_option_small")
	private MobileElement primeArticleshowSmallFontIcon;

	@AndroidFindBy(id = "com.et.reader.activities:id/view_all_tv")
	private MobileElement viewSectionLink;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='START FREE TRIAL']")
	@iOSXCUITFindBy(accessibility = "START FREE TRIAL")
	private List<MobileElement> startFreeTrialWidget;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='START YOUR MEMBERSHIP']")
	@iOSXCUITFindBy(accessibility = "START YOUR MEMBERSHIP")
	private List<MobileElement> startMembershipWidget;

	@AndroidFindBy(id = "com.et.reader.activities:id/headingTV")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='News_Headline' && visible ==1")
	private List<MobileElement> primeStoriesHeadingList;

	@AndroidFindBy(xpath = "//android.widget.TextView[@index='3']")
	@iOSXCUITFindBy(accessibility = "Already a member? Sign in Now")
	private MobileElement signInbuttonOnBlockerLayer;

	@AndroidFindBy(id = "com.et.reader.activities:id/subscribe_to")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='SUBSCRIBE TO ET PRIME'")
	private MobileElement subscribeToETPrimeFooter;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='CHOOSE A PLAN']")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='ET.PrimeBecomeAMemberView'")
	private List<MobileElement> planPage;

	@AndroidFindBy(id = "com.et.reader.activities:id/plans_container")
	private List<MobileElement> planPayWall;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='CONTINUE WITH ₹1']")
	private List<MobileElement> subscribeButton;

	////////// Getters starts here///////////

	public List<MobileElement> getPrimeAuthorStories() {
		return primeAuthorStories;
	}

	public MobileElement getAuthorCommentPostedOnHeading() {
		return authorCommentPostedOnHeading;
	}

	public MobileElement getAuthorViewFullProfile() {
		return authorViewFullProfile;
	}

	public List<MobileElement> getPrimeSectionStories() {
		return primeSectionStories;
	}

	public MobileElement getPrimeBrowseTab() {
		return primeBrowseTab;
	}

	public MobileElement getPrimeHomeTab() {
		return primeHomeTab;
	}

	public MobileElement getPrimeContentComingSoonRed() {
		return primeContentComingSoonRed;
	}

	public MobileElement getPrimeAuthorsStoriesTab() {
		return primeAuthorsStoriesTab;
	}

	public MobileElement getPrimeAuthorsContributionsTab() {
		return primeAuthorsContributionsTab;
	}

	public MobileElement getPrimeAuthorsTab() {
		return primeAuthorsTab;
	}

	public MobileElement getPrimeArticleshowHeading() {
		return primeArticleshowHeading;
	}

	public MobileElement getPrimeArticleshowDate() {
		return primeArticleshowDate;
	}

	public MobileElement getPrimeArticleshowSummary() {
		return primeArticleshowSummary;
	}

	public MobileElement getPrimeArticleshowAuthor() {
		return primeArticleshowAuthor;
	}

	public MobileElement getPrimeArticleshowPrimaryTag() {
		return primeArticleshowPrimaryTag;
	}

	public MobileElement getPrimeHomepageIcon() {
		return primeHomepageIcon;
	}

	public List<MobileElement> getPrimeHomepageStories() {
		return primeHomepageStories;
	}

	public MobileElement getViewProfileTab(AppiumDriver<?> driver, String authorName) {
		return (MobileElement) driver.findElement(MobileBy.xpath(
				"//*[contains(@text,'" + authorName + "')]/following-sibling::*[contains(@text,'View Full Profile')]"));
	}

	public MobileElement getAuthorsNameSection(AppiumDriver<?> driver, String authorName) {
		return (MobileElement) driver.findElement(MobileBy.xpath("//*[contains(@text,'" + authorName + "')]/../.."));
	}

	public MobileElement getAuthorsNameTab(AppiumDriver<?> driver, String authorName) {
		return (MobileElement) driver
				.findElement(MobileBy.xpath("//android.widget.TextView[@text='" + authorName + "']"));
	}

	public MobileElement getSectionNameTab(AppiumDriver<?> driver, String sectionName) {
		return (MobileElement) driver
				.findElement(MobileBy.xpath("//android.widget.TextView[@text='" + sectionName + "']"));
	}

	public MobileElement getHomePageTopHeder() {
		return homePageTopHeder;
	}

	public MobileElement getEtLogo() {
		return etLogo;
	}

	public MobileElement getSectionHeader() {
		return sectionHeader;
	}

	public MobileElement getPrimeSeachIcon() {
		return primeSeachIcon;
	}

	public void setPrimeSeachIcon(MobileElement primeSeachIcon) {
		this.primeSeachIcon = primeSeachIcon;
	}

	public MobileElement getPrimeSearchFeild() {
		return primeSearchFeild;
	}

	public List<MobileElement> getPrimeSearchResult() {
		return primeSearchResult;
	}

	public MobileElement getprimeSubscribeWidget() {
		return primeSubscribeWidget;
	}

	public List<MobileElement> getAdCloseButton() {
		return closeAd;
	}

	public MobileElement getPrimeHomeEndsection() {
		return primeHomeEndsection;
	}

	public MobileElement getprimeArticleshowStoryCaption() {
		return primeArticleshowStoryCaption;
	}

	public MobileElement getprimeArticleshowFontIcon() {
		return primeArticleshowFontIcon;
	}

	public MobileElement getprimeArticleshowSmallFontIcon() {
		return primeArticleshowSmallFontIcon;
	}

	public MobileElement getViewSectionLink() {
		return viewSectionLink;
	}

	public List<MobileElement> getStartYourTrailWidget() {
		return startFreeTrialWidget;
	}

	public List<MobileElement> getStartMembershipWidget() {
		return startMembershipWidget;
	}

	public List<MobileElement> getPrimeStoriesHeadingList() {
		return primeStoriesHeadingList;
	}

	public MobileElement getSignInButtonOnBlockerLayer() {
		return signInbuttonOnBlockerLayer;
	}

	public MobileElement getSubscribeToEtPrimefooter() {
		return subscribeToETPrimeFooter;
	}

	public List<MobileElement> getPlanPage() {
		return planPage;
	}

	public List<MobileElement> getPlanPaywall() {
		return planPayWall;
	}

	public List<MobileElement> getSubscribeButton() {
		return subscribeButton;
	}
}
