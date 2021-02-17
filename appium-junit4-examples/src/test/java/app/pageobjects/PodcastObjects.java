package app.pageobjects;

import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class PodcastObjects {

	@AndroidFindBy(id = "com.et.reader.activities:id/pocast_et_radio")
	private MobileElement podcastHomepage;

	@AndroidFindBy(id = "com.et.reader.activities:id/pod_cast_item_view")
	private List<MobileElement> podcastListing;

	@AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'LISTENING •••')]")
	private MobileElement podcastPlaying;

	@AndroidFindBy(id = "com.et.reader.activities:id/share_whatsapp")
	private MobileElement whatsappShareIcon;

	@AndroidFindBy(id = "com.et.reader.activities:id/share_facebook")
	private MobileElement facebookShareIcon;

	@AndroidFindBy(id = "com.et.reader.activities:id/share_twitter")
	private MobileElement twitteShareIcon;

	@AndroidFindBy(id = "com.et.reader.activities:id/share_sms")
	private MobileElement smsShareIcon;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='ALSO LISTEN']")
	private MobileElement alsoListenHeader;

	@AndroidFindBy(xpath = "//android.widget.TextView[@index='1']")
	private List<MobileElement> podcastListingHeading;

	@AndroidFindBy(xpath = "//android.widget.TextView[@index='0']")
	private MobileElement podcastPause;

	@AndroidFindBy(xpath = "//android.widget.TextView[@index='1']")
	private List<MobileElement> firstPodcastinfo;

	@AndroidFindBy(xpath = "//android.widget.TextView[@index='2']")
	private MobileElement podcastHeadingOnDetailPage;
	// Getter Methods
	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.et.reader.activities:id/btn_play']/..")
	private List<MobileElement> alsoListenTagList;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.et.reader.activities:id/btn_play']/..//android.widget.TextView")
	private List<MobileElement> HeaderTextForAlsoListenTagList;

	// Getter Methods

	public MobileElement getPodcastHome() {
		return podcastHomepage;
	}

	public List<MobileElement> getPodcastListing() {
		return podcastListing;
	}

	public MobileElement getPodcastPlaying() {
		return podcastPlaying;
	}

	public MobileElement getFbShareIcon() {
		return facebookShareIcon;
	}

	public MobileElement getTwitterShareIcon() {
		return twitteShareIcon;
	}

	public MobileElement getWhatsAppShareIcon() {
		return whatsappShareIcon;
	}

	public MobileElement getSmsShareIcon() {
		return smsShareIcon;
	}

	public MobileElement getAlsoListenTag() {
		return alsoListenHeader;
	}

	public List<MobileElement> getPodcastListingHeading() {
		return podcastListing;
	}

	public MobileElement getPodcastPauseIcon() {
		return podcastPause;
	}

	public List<MobileElement> getFirstPodcastInfo() {
		return firstPodcastinfo;
	}

	public MobileElement getPodcastHeadingOnDetailPage() {
		return podcastHeadingOnDetailPage;
	}

	public List<MobileElement> getAlsoElementList() {
		return alsoListenTagList;

	}

	public List<MobileElement> getAlsoListenHeader() {
		return HeaderTextForAlsoListenTagList;
	}
}
