package web.pagemethods;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.HomePageObjects;
import web.pageobjects.SlideshowPageObjects;

public class SlideShowPageMethods {
	private WebDriver driver;
	private HomePageObjects homePageObjects;

	private SlideshowPageObjects slideshowPageObjects;
	HomePageMethods homePageMethods;

	public SlideShowPageMethods(WebDriver driver) {
		this.driver = driver;
		homePageObjects = PageFactory.initElements(driver, HomePageObjects.class);
		slideshowPageObjects = PageFactory.initElements(driver, SlideshowPageObjects.class);
	}

	public boolean checkSlideshow() {
		boolean flag = false;
		try {

			WaitUtil.poolingWaitForElt(driver, homePageObjects.getFirstSlideShow(), 3);
			for (int i = 1; i <= 6; i++) {
				String slideShow = homePageObjects.getFirstSlideShow().getAttribute("href");
				System.out.println(slideShow);
				// String slideShow =
				// "http://economictimes.indiatimes.com/slideshows/auto/honda-wr-v-top-6-things-worth-knowing/launch-date-revealed/slideshow/57429005.cms";
				if ((slideShow.contains("slideshows") || slideShow.contains("slideshow"))) {
					flag = true;
					break;

				} else {
					WaitUtil.sleep(10000);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public void openFirstSlide() throws InterruptedException {
		if (checkSlideshow()) {
			homePageObjects.getFirstSlideShow().click();

		} else {
			WaitUtil.sleep(2000);
		}
		WebBaseMethods.switchToChildWindow(5);
		WaitUtil.sleep(3000);

	}

	public boolean isSlideVideo() {
		boolean flag = true;
		String value = slideshowPageObjects.getPlaySlideRightTopButton().getAttribute("style");
		if (value.equals("display: block;")) {
			flag = false;
		}
		return flag;
	}

	public boolean veifyPlayShowFromMain() {
		// boolean flag = false;
		try {
			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			int currentSlide = Integer.parseInt(currentSlides[0]);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getPlaySlideMainButton());
			WaitUtil.sleep(2000);
			String[] currentSlideAfterPlay = slideshowPageObjects.getCurrentSlide().getText().split("/");
			int currentSlide1 = Integer.parseInt(currentSlideAfterPlay[0]);
			if (currentSlide < currentSlide1)
				return true;
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return false;
	}

	public boolean verifyPauseSlideShowFromMain() {
		// boolean flag = false;
		try {

			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			// int currentSlide = Integer.parseInt(currentSlides[0]);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getPlaySlideMainButton());

			WaitUtil.sleep(2000);

			String[] currentSlideAfterPause = slideshowPageObjects.getCurrentSlide().getText().split("/");
			// int currentSlide1 = Integer.parseInt(currentSlideAfterPause[0]);
			if (currentSlides[0].equals(currentSlideAfterPause[0]))
				return true;

		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return false;
	}

	public boolean verifyPlaySlideShowFromLeftTop() {
		// boolean flag = false;
		try {

			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			int currentSlide = Integer.parseInt(currentSlides[0]);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getPlaySlideSideTopButton());
			WaitUtil.sleep(2000);

			String[] currentSlidesAfterPlay = slideshowPageObjects.getCurrentSlide().getText().split("/");

			int currentSlideAfterPlay = Integer.parseInt(currentSlidesAfterPlay[0]);
			if (currentSlide < currentSlideAfterPlay)
				return true;

		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return false;
	}

	public boolean verifyPauseSlideShowFromLeftTop() {
		// boolean flag = false;
		try {

			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			// int currentSlide = Integer.parseInt(currentSlides[0]);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getPlaySlideSideTopButton());
			WaitUtil.sleep(2000);
			;
			String[] currentSlideAfterPause = slideshowPageObjects.getCurrentSlide().getText().split("/");

			Integer.parseInt(currentSlideAfterPause[0]);
			if (currentSlides[0].equals(currentSlideAfterPause[0]))
				return true;

		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return false;
	}

	public boolean verifyPlaySlideShowFromRightTop() {
		// boolean flag = false;
		try {

			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			int currentSlide = Integer.parseInt(currentSlides[0]);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getPlaySlideRightTopButton());
			WaitUtil.sleep(2000);
			;
			String[] currentSlideAfterPlay = slideshowPageObjects.getCurrentSlide().getText().split("/");

			int currentSlide1 = Integer.parseInt(currentSlideAfterPlay[0]);
			if (currentSlide < currentSlide1)
				return true;

		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return false;
	}

	public boolean verifyPauseSlideShowFromRightTop() {
		// boolean flag = false;
		try {

			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getPlaySlideRightTopButton());

			String[] currentSlideAfterPause = slideshowPageObjects.getCurrentSlide().getText().split("/");

			if (currentSlides[0].equals(currentSlideAfterPause[0]))
				return true;

		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return false;
	}

	public boolean verifyNextSlideShowFromMain() {
		// boolean flag = false;
		try {

			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			int currentSlide = Integer.parseInt(currentSlides[0]);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getSlideNextMainButton());
			WaitUtil.sleep(2000);
			String[] currentSlidesAfterNext = slideshowPageObjects.getCurrentSlide().getText().split("/");

			int currentSlideAfterNext = Integer.parseInt(currentSlidesAfterNext[0]);
			if (currentSlide < currentSlideAfterNext)
				return true;

		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return false;
	}

	public boolean verifyPreviousSlideShowFromMain() {
		// boolean flag = false;
		try {

			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			int currentSlide = Integer.parseInt(currentSlides[0]);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getslidePreviousMainButton());
			WaitUtil.sleep(2000);
			String[] currentSlidesAfterPrev = slideshowPageObjects.getCurrentSlide().getText().split("/");

			int currentSlideAfterPrev = Integer.parseInt(currentSlidesAfterPrev[0]);
			if (currentSlide > currentSlideAfterPrev)
				return true;

		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return false;
	}

	public boolean verifyNextSlideShowFromTopRight() {
		// boolean flag = false;
		try {

			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			int currentSlide = Integer.parseInt(currentSlides[0]);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getSlideNextTopRightButton());
			WaitUtil.sleep(2000);
			String[] currentSlidesAfterNext = slideshowPageObjects.getCurrentSlide().getText().split("/");

			int currentSlideAfterNext = Integer.parseInt(currentSlidesAfterNext[0]);
			if (currentSlide < currentSlideAfterNext)
				return true;

		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return false;
	}

	public boolean verifyPreviousSlideShowFromTopRight() {
		// boolean flag = false;
		try {

			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			int currentSlide = Integer.parseInt(currentSlides[0]);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getslidePreviousTopRightButton());
			WaitUtil.sleep(2000);
			String[] currentSlidesAfterPrev = slideshowPageObjects.getCurrentSlide().getText().split("/");

			int currentSlideAfterPrev = Integer.parseInt(currentSlidesAfterPrev[0]);
			if (currentSlide > currentSlideAfterPrev)
				return true;

		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return false;
	}

	public boolean verifyNextSlideShowFromSlide() {
		// boolean flag = false;
		try {

			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			int currentSlide = Integer.parseInt(currentSlides[0]);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getSlideNextLeftButton());
			WaitUtil.sleep(2000);
			String[] currentSlidesAfterNext = slideshowPageObjects.getCurrentSlide().getText().split("/");

			int currentSlideAfterNext = Integer.parseInt(currentSlidesAfterNext[0]);
			if (currentSlide < currentSlideAfterNext)
				return true;

		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return false;
	}

	public boolean verifyPreviousSlideShowFromSlide() {
		// boolean flag = false;
		try {

			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			int currentSlide = Integer.parseInt(currentSlides[0]);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getSlidePreviousLeftButton());
			WaitUtil.sleep(2000);
			String[] currentSlidesAfterPrev = slideshowPageObjects.getCurrentSlide().getText().split("/");

			int currentSlideAfterPrev = Integer.parseInt(currentSlidesAfterPrev[0]);
			if (currentSlide > currentSlideAfterPrev)
				return true;

		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return false;
	}

	public boolean verifyNextSlideShowFromPagingBar() {
		// boolean flag = false;
		try {

			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			int currentSlide = Integer.parseInt(currentSlides[0]);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getSlidePagingNextButton());
			WaitUtil.sleep(2000);

			String[] currentSlidesAfterNext = slideshowPageObjects.getCurrentSlide().getText().split("/");

			int currentSlideAfterNext = Integer.parseInt(currentSlidesAfterNext[0]);
			if (currentSlide < currentSlideAfterNext)
				return true;

		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return false;
	}

	public boolean verifyPreviousSlideShowFromPagingBar() {
		// boolean flag = false;
		try {

			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			int currentSlide = Integer.parseInt(currentSlides[0]);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getSlidePagingPreviousButton());
			WaitUtil.sleep(2000);
			String[] currentSlidesAfterPrev = slideshowPageObjects.getCurrentSlide().getText().split("/");

			int currentSlideAfterPrev = Integer.parseInt(currentSlidesAfterPrev[0]);
			if (currentSlide > currentSlideAfterPrev)
				return true;

		} catch (Exception ee) {

			ee.printStackTrace();
		}
		return false;
	}

	public boolean verifyChangingSlideShowFromPagingBar() {
		boolean flag = false;

		try {

			int size = slideshowPageObjects.getSlidePagingElements().size();
			List<WebElement> listPaging = slideshowPageObjects.getSlidePagingElements();
			for (int i = 2; i <= size - 2; i++) {
				String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
				int currentSlide = Integer.parseInt(currentSlides[0]);
				listPaging.get(i).findElement(By.xpath("a")).click();
				WaitUtil.sleep(2000);
				String[] currentSlidesAfterPrev = slideshowPageObjects.getCurrentSlide().getText().split("/");
				int currentSlideAfterClick = Integer.parseInt(currentSlidesAfterPrev[0]);
				if (currentSlide < currentSlideAfterClick) {
					System.out.println("Slide Show is working for Paging Button");
					flag = true;

				} else
					return false;

			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;

	}

	public boolean verifySlideAtMediumSpeed() {

		boolean flag = false;
		try {
			String[] currentSlides = slideshowPageObjects.getCurrentSlide().getText().split("/");
			Integer.parseInt(currentSlides[0]);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getPlaySlideMainButton());
			for (int i = 1; i <= 20; i++) {
				String[] currentSlidesAfterPrev = slideshowPageObjects.getCurrentSlide().getText().split("/");
				int currentSlideAfterPrev = Integer.parseInt(currentSlidesAfterPrev[0]);
				if (currentSlideAfterPrev > 2) {
					System.out.println(currentSlideAfterPrev);
					flag = true;
					break;
				} else {
					WaitUtil.sleep(1000);

				}

			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;

	}

	public boolean verifySlideAtSlowSpeed() {

		boolean f = false;
		try {
			WaitUtil.sleep(2000);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getPlaySlideMainButton());
			WaitUtil.sleep(2000);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getFirstSlideSlowButton());
			;
			WaitUtil.sleep(2000);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getSlideSlowSpeedButton());
			;
			WaitUtil.sleep(2000);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getPlaySlideMainButton());
			;
			for (int i = 1; i <= 20; i++) {
				String[] currentSlidesAfterPrev = slideshowPageObjects.getCurrentSlide().getText().split("/");
				int currentSlideAfterPrev = Integer.parseInt(currentSlidesAfterPrev[0]);
				if (currentSlideAfterPrev > 2) {
					System.out.println(currentSlideAfterPrev);
					f = true;
					break;
				}
				WaitUtil.sleep(2000);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return f;
	}

	public boolean verifySlideAtFastSpeed() {

		boolean f = false;
		try {
			WaitUtil.sleep(2000);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getPlaySlideMainButton());
			WaitUtil.sleep(2000);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getFirstSlideSlowButton());
			;
			WaitUtil.sleep(2000);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getSlideFastSpeedButton());
			;
			WaitUtil.sleep(2000);
			WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getPlaySlideMainButton());
			;
			for (int i = 1; i <= 20; i++) {
				String[] currentSlidesAfterPrev = slideshowPageObjects.getCurrentSlide().getText().split("/");
				int currentSlideAfterPrev = Integer.parseInt(currentSlidesAfterPrev[0]);
				if (currentSlideAfterPrev > 3) {
					System.out.println(currentSlideAfterPrev);
					f = true;
					break;
				}
				WaitUtil.sleep(2000);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return f;
	}

	public List<String> getSlideshowTabList() {
		List<String> slideshowSections = new LinkedList<>();
		try {
			// WebBaseMethods.JSHoverOver(homePageObjects.getSlideshowTab());
			slideshowSections = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getSlideshowSections());
		} catch (Exception e) {

		}
		return slideshowSections;
	}

	public List<String> getAllSlideshowLinks() {
		String msg;

		ArrayList<String> slideshowLinks = new ArrayList<>();

		ArrayList<String> errorSlideshowLinksHeading = new ArrayList<>();
		ArrayList<String> paginationLinks = new ArrayList<>();

		// ArrayList<String> slideshowLinks = new ArrayList<>();
		List<WebElement> paginationLinksElement = slideshowPageObjects.getSlideshowListingPagination();
		for (WebElement we : paginationLinksElement) {
			paginationLinks.add(we.getAttribute("href"));

		}

		for (int i = 1; i < paginationLinks.size() - 1; i++) {
			driver.navigate().to(paginationLinks.get(i));
			ArrayList<String> slideshowLinksHeading = new ArrayList<>();
			List<WebElement> paginationLinksHeading = slideshowPageObjects.getSlideshowListingHeading();
			ArrayList<String> slideshowLinksHeadingTitle = new ArrayList<>();
			for (WebElement we : paginationLinksHeading) {

				slideshowLinksHeading.add(we.getAttribute("href"));
				slideshowLinksHeadingTitle.add(we.getText());
			}
			for (int k = 0; k < slideshowLinksHeading.size() - 1; k++) {
				driver.navigate().to(slideshowLinksHeading.get(k));
				try {
					if (slideshowPageObjects.getSlideshowHeading().isDisplayed()) {
						continue;

					}

				} catch (Exception ee) {
					msg = "the page num is " + i + " having error link ,heading is "
							+ slideshowLinksHeadingTitle.get(k);
					errorSlideshowLinksHeading.add(msg);

				}

			}
		}
		slideshowLinks.addAll(errorSlideshowLinksHeading);

		for (String s : slideshowLinks) {
			System.out.println(s);
		}

		return slideshowLinks;

	}

	public boolean verifySlideshowCount() {
		try {
			List<WebElement> slideCount = slideshowPageObjects.getSlideshowCount();
			String[] count = slideCount.get(0).getText().split("/");
			int numOfSlides = Integer.parseInt(count[count.length - 1]);

			if (numOfSlides == slideCount.size())
				return true;

			else
				return false;

		} catch (Exception e) {
			return false;
		}

	}

	public boolean verifySlideshowNewsletter() {
		WaitUtil.sleep(2000);
		try {
			WebBaseMethods.scrollingToElementofAPage(slideshowPageObjects.getThirdSlide());
			if (slideshowPageObjects.getSlideshowNewsletter().isDisplayed())

				return true;
			else
				return false;

		} catch (Exception ee) {
			System.out.println("element not found");
			return false;

		}

	}

	public boolean verifyNextSlideshow() {
		List<WebElement> slideCount = slideshowPageObjects.getSlideshowCount();
		int size = slideCount.size();
		WebBaseMethods.scrollUpDownUsingJSE(0, 1000);
		WaitUtil.sleep(2000);
		WebBaseMethods.scrollToBottom();
		WaitUtil.sleep(3000);
		List<WebElement> slideCounts = slideshowPageObjects.getSlideshowCount();
		int sizeNew = slideCounts.size();
		try {
			if (slideshowPageObjects.getSlideshowNext().isDisplayed() && sizeNew > size) {
				return true;
			}
		} catch (Exception ee) {
			System.out.println("false");
			ee.printStackTrace();
		}
		return false;
	}

	public boolean clickFirstSlideShow() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(slideshowPageObjects.getFirstSlideshowLink());
			 
		} catch (NoSuchElementException e) {

		}
		WaitUtil.sleep(5000);
		WaitUtil.waitForLoad(driver);
		return flag;
	}

	public int getSlidesCount() {
		return slideshowPageObjects.getSlideshowCount().size();
	}
}