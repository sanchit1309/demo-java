package app.pageobjects;

import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;


public class FeedbackPageObjects {

	@AndroidFindBy(className="android.widget.TextView")
	private List<MobileElement> androidTextViews;
	
	@AndroidFindBy(id="com.et.reader.activities:id/hs__start_new_conversation")
	private MobileElement send;
	
	@AndroidFindBy(id="com.et.reader.activities:id/hs__attach_screenshot")
	private MobileElement attachScreenshot;
	
	@AndroidFindBy(id="com.et.reader.activities:id/hs__conversationDetail")
	private MobileElement conversationDetails;
	
	@AndroidFindBy(id="com.et.reader.activities:id/hs__username")
	private MobileElement username;
	
	@AndroidFindBy(id="com.et.reader.activities:id/hs__email")
	private MobileElement email;
	
	//@AndroidFindBy(id="android:id/text1")
	@AndroidFindBy(id="com.et.reader.activities:id/user_message_text")
	private MobileElement feedBackText;
	
	public MobileElement getHeader() {
		return androidTextViews.get(0);
	}

	public MobileElement getSend() {
		return send;
	}

	public MobileElement getAttachScreenshot() {
		return attachScreenshot;
	}

	public MobileElement getConversationDetails() {
		return conversationDetails;
	}

	public MobileElement getUsername() {
		return username;
	}

	public MobileElement getEmail() {
		return email;
	}

	public MobileElement getFeedBackText() {
		return feedBackText;
	}
	
}

