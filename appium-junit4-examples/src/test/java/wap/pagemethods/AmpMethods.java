package wap.pagemethods;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import wap.pageobjects.AmpObjects;
import web.pagemethods.WebBaseMethods;
import web.pageobjects.ETSharedObjects;

public class AmpMethods {
	AmpObjects ampObjects;
	ETSharedObjects etSharedObjects;
	WebDriver driver;

	public AmpMethods(WebDriver driver) {
		this.driver = driver;
		ampObjects = PageFactory.initElements(driver, AmpObjects.class);
		etSharedObjects = PageFactory.initElements(driver, ETSharedObjects.class);
	}

	public boolean checkDownloadAppLink() {
		boolean flag = false;
		List<WebElement> viewInApp = ampObjects.getViewApp();
		for (WebElement el : viewInApp) {
			int initialCount=driver.getWindowHandles().size();
			WebBaseMethods.clickElementUsingJSE(el);
			flag = flag == false ? driver.getWindowHandles().size()>initialCount : true;
		}
		WaitUtil.sleep(2000);
		WebBaseMethods.switchToParentClosingChilds();
		return flag;
	}

	public List<String> getEmbeddedLinks() {
		List<WebElement> embeddLink = ampObjects.getHyperLinks();
		List<String> hrefList = VerificationUtil.getLinkHrefList(embeddLink);
		return hrefList;
	}

	public List<String> getRelatedArticles() {
		List<WebElement> relatedArticles = ampObjects.getRelatedArticles();
		List<String> hrefLi = VerificationUtil.getLinkHrefList(relatedArticles);
		hrefLi.removeAll(Collections.singleton(null));
		return hrefLi;
	}

	public List<String> getETPrimeWidgetLinks() {
		List<WebElement> primeWidgetLinks = ampObjects.getETPrimeWidgetLinks();
		List<String> hrefLi = VerificationUtil.getLinkHrefList(primeWidgetLinks);
		return hrefLi;
	}

	public List<String> getArticleRefs() {
		List<String> hrefLi = new LinkedList<>();
		List<WebElement> articles = etSharedObjects.getArticleShows();
		if (articles.size() > 0)
			hrefLi.add(articles.get(0).getAttribute("href").replace("economictimes.indiatimes", "m.economictimes").replace("articleshow", "amp_articleshow"));
		articles = etSharedObjects.getVideoShows();
		if (articles.size() > 0)
			hrefLi.add(articles.get(0).getAttribute("href").replace("economictimes.indiatimes", "m.economictimes").replace("videoshow", "amp_videoshow"));
		articles = etSharedObjects.getSlideShows();
		if (articles.size() > 0)
			hrefLi.add(articles.get(0).getAttribute("href").replace("economictimes.indiatimes", "m.economictimes").replace("slideshow", "amp_slideshow"));
		articles = etSharedObjects.getLiveblogs();
		if (articles.size() > 0)
			hrefLi.add(articles.get(0).getAttribute("href").replace("economictimes.indiatimes", "m.economictimes").replace("liveblog", "amp_liveblog"));
		return hrefLi;
	}

}
