package wap.pagemethods;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import wap.pageobjects.FooterObjects;
import web.pagemethods.WebBaseMethods;

public class FooterMethods{

	private FooterObjects footerObjects;

	public FooterMethods(WebDriver driver){
		footerObjects = PageFactory.initElements(driver, FooterObjects.class);
	}
	
	public List<String> getFooterLinkHref(){
		WebBaseMethods.scrollToBottom();
//		WebBaseMethods.moveToElement(footerObjects.getFooterSectionLinks().get(0));
		List<String> footerLinks = new ArrayList<>();
		WaitUtil.sleep(2000);
		footerLinks = VerificationUtil.getLinkHrefList(footerObjects.getFooterSectionLinks());
		return footerLinks;
	}
}
