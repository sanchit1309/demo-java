package wap.pagemethods;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.VerificationUtil;
import wap.pageobjects.NriPageObjects;

public class NriPageMethods {

	WebDriver driver;
	NriPageObjects nriPageObjects;

	public NriPageMethods(WebDriver driver) {
		this.driver = driver;
		nriPageObjects = PageFactory.initElements(driver, NriPageObjects.class);

	}

	public List<String> getTrendingInUslinks() {
		List<String> trendingLinks = new ArrayList<>();
		// sectionHeadlines.addAll(risePageObjects.getImageLink(sectionDiv));
		try {
			trendingLinks = VerificationUtil.getLinkHrefList(nriPageObjects.getTrendingInUSSectionLinks());
			System.out.println(trendingLinks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trendingLinks;
	}
	
	public List<String> getTopTrendinglinks() {
		List<String> trendingLinks = new ArrayList<>();
		// sectionHeadlines.addAll(risePageObjects.getImageLink(sectionDiv));
		try {
			trendingLinks = VerificationUtil.getLinkHrefList(nriPageObjects.getNriTopTrendingTermsLinks());
			System.out.println(trendingLinks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trendingLinks;
	}

}
