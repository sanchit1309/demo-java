package app.pageobjects;

import java.time.temporal.ChronoUnit;
import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.WithTimeout;

public class QuickReadsPageObjects {
	
	@AndroidFindBy(id="com.et.reader.activities:id/index")
	private MobileElement totalCount;
	
	@AndroidFindBy(id="com.et.reader.activities:id/read_next_story")
	private MobileElement nextStoryButton;
	
	@AndroidFindBy(id="com.et.reader.activities:id/tv_feed_text_title")
	private MobileElement advTitle;
	
	@AndroidFindBy(id="com.et.reader.activities:id/read_more")
	private List<MobileElement> readMore;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.TextView\").index(1)")
	private MobileElement heading;
	
	@AndroidFindBy(id="com.et.reader.activities:id/story_headline")
	private MobileElement headline;
	
	@WithTimeout(time = 5, chronoUnit = ChronoUnit.SECONDS)
	@AndroidFindBy(id = "com.et.reader.activities:id/rl_ad_top_view")
	private MobileElement adView;
	
/////////////////////getters
	
	public MobileElement getTotalCount() {
		return totalCount;
	}

	public MobileElement getNextStoryButton() {
		return nextStoryButton;
	}

	public MobileElement getAdvTitle() {
		return advTitle;
	}

	public List<MobileElement> getReadMore() {
		return readMore;
	}

	public MobileElement getHeading() {
		return heading;
	}

	public MobileElement getHeadline() {
		return headline;
	}

	public MobileElement getAdView() {
		return adView;
	}
	

}
