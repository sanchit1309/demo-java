package app.pageobjects;

import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BriefPageObjects {

	@AndroidFindBy(id = "com.et.reader.activities:id/headline")
	@iOSXCUITFindBy(iOSNsPredicate = "name beginswith 'Acc_label' && visible==1")
	private MobileElement headline;

	@AndroidFindBy(id = "com.et.reader.activities:id/synopsis")
	@iOSXCUITFindBy(iOSNsPredicate = "name =='Acc_brief_description' && visible==1")
	private MobileElement synopsis;

	@AndroidFindBy(id = "com.et.reader.activities:id/daily_brief_pager")
	@iOSXCUITFindBy(iOSNsPredicate = "name='Read Full Article'")
	private MobileElement briefPageIdentifier;

	@AndroidFindBy(id = "com.et.reader.activities:id/index")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='Acc_brief_currentIndex' && visible==1")
	private MobileElement totalCount;

	@AndroidFindBy(id = "com.et.reader.activities:id/read_next_story")
	@iOSXCUITFindBy(iOSNsPredicate = "name='Read Next Story' && visible==1")
	private MobileElement nextStoryButton;

	@iOSXCUITFindBy(iOSNsPredicate = "name='Acc_ad_view'&&visible==1")
	@AndroidFindBy(id = "com.et.reader.activities:id/tv_feed_text_title")
	private MobileElement advTitle;

	@AndroidFindBy(id = "com.et.reader.activities:id/read_more")
	@iOSXCUITFindBy(iOSNsPredicate = "name BEGINSWITH 'Acc_read_full_story' && visible==1")
	private List<MobileElement> readMore;

	@iOSXCUITFindBy(iOSNsPredicate = "name='t' && visible==1")
	private MobileElement briefsCloseButton;

	@AndroidFindBy(id = "com.et.reader.activities:id/rl_ad_top_view")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='Acc_ad_view' && visible==1")
	private MobileElement briefsAdView;

	@iOSXCUITFindBy(iOSNsPredicate = "name='Acc_brief_title' && visible==1")
	private MobileElement briefPageHeadline;

	public MobileElement getTotalCount() {
		return totalCount;
	}

	public MobileElement getNextStoryButton() {
		return nextStoryButton;
	}

	public MobileElement getAdvTitle() {
		return advTitle;
	}

	public MobileElement getHeadline() {
		return headline;
	}

	public List<MobileElement> getReadMore() {
		return readMore;
	}

	public MobileElement getBriefsAdView() {
		return briefsAdView;
	}

	public MobileElement getBriefPageHeadline() {
		return briefPageHeadline;
	}

	public MobileElement getBriefPageIdentifier() {
		return briefPageIdentifier;
	}

	public MobileElement getBriefsCloseButton() {
		return briefsCloseButton;
	}

	public MobileElement getSynopsis() {
		return synopsis;
	}
}
