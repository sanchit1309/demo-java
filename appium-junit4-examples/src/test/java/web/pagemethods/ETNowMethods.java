package web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.ETNowObjects;

public class ETNowMethods {
	private WebDriver driver;
	public ETNowObjects etNowObjects;
	private JavascriptExecutor jse;
	private Number playedTime;

	public ETNowMethods(WebDriver driver) {
		this.driver = driver;
		etNowObjects = PageFactory.initElements(driver, ETNowObjects.class);
		jse = (JavascriptExecutor) driver;
	}

	public void switchToVideoFrame() {
		WebBaseMethods.switchToFrame(etNowObjects.getVideoIFrame());
		// WebBaseMethods.switchToFrame(etNowObjects.getSlikePlayer());
	}

	public Boolean playVideo() {
		jse.executeScript("arguments[0].play();", etNowObjects.getTvWindow());
		WaitUtil.sleep(60000);
		Number playedTime2 = ((Number) jse.executeScript("return arguments[0].duration;", etNowObjects.getTvWindow()))
				.intValue();
		System.out
				.println(playedTime2 + " " + playedTime + " diff " + (playedTime2.intValue() - playedTime.intValue()));
		return (((playedTime2.intValue() - playedTime.intValue()) > 40) ? true : false);

	}

	public Boolean pauseVideo() {
		playedTime = ((Number) jse.executeScript("return arguments[0].duration;", etNowObjects.getTvWindow()))
				.intValue();
		jse.executeScript("arguments[0].pause();", etNowObjects.getTvWindow());
		return (Boolean) jse.executeScript("return arguments[0].paused;", etNowObjects.getTvWindow());
	}

	public Boolean hasAdvtCompleted() {
		int counter = 0;
		Boolean flag = false;
		do {
			WaitUtil.sleep(20000);
			System.out.println(
					"startVal" + jse.executeScript("return arguments[0].played.length;", etNowObjects.getTvWindow()));
			flag = (Long) jse.executeScript("return arguments[0].played.length;", etNowObjects.getTvWindow()) > 0 ? true
					: false;
			System.out.println("counter:" + counter);
			counter++;
		} while (counter < 30 && !flag);
		return flag;
	}

	public boolean clickMostViewedTab() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(etNowObjects.getMostViewedTab());

		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public List<WebElement> getMostViewedVideos() {
		return etNowObjects.getMostVideos();
	}

	public int getViewCount() {
		int count = 0;
		WaitUtil.sleep(2000);
		try {
			count = Integer.parseInt(etNowObjects.getVideoViews().getText().replaceAll("[\\D]+", ""));
		} catch (Exception e) {
			return -1;
		}
		return count;
	}

	public Boolean playOnly() {
		try {
			jse.executeScript("arguments[0].play();", etNowObjects.getTvWindow());
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	public List<String> getSectionVideoLinks(String sectionName) {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(etNowObjects.getSectionVideoLinks(sectionName));

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

	public String getSectionHeadingLink(String sectionName) {
		String href = "";
		try {
			href = WebBaseMethods.getHrefUsingJSE(etNowObjects.getSectionHeading(sectionName));
			System.out.println("Section heading is: " + href);
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return href;
	}

	public boolean clickLatestNewsTab() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(etNowObjects.getLatestNewsWidgetTab());
			
		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public boolean clickTopNewsTab() {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(etNowObjects.getTopNewsWidgetTab());
			flag = true;
		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public boolean clickLatestVideosTab() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(etNowObjects.getLatestVideosWidgetTab());
			 
		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public boolean clickMostViewedWidgetTab() {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(etNowObjects.getMostViewedWidgetTab());
			flag = true;
		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public List<String> getLatestNewsLinks() {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(etNowObjects.getLatestNewsWidgetLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

	public List<String> getTopNewsLinks() {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(etNowObjects.getTopNewsWidgetLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

	public List<String> getLatestVideosLinks() {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(etNowObjects.getLatestVideosWidgetLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

	public List<String> getMostViewedWidgetLinks() {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(etNowObjects.getMostViewedWidgetLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

}
