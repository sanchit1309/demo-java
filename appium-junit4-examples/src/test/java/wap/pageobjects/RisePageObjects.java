package wap.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static common.launchsetup.BaseTest.driver;

public class RisePageObjects {

	// @FindBy(xpath = "//*[@id='subMenu']/ul/li")
	// @FindBy(xpath = "//nav[@class='scrollMenu']/a")
	@FindBy(xpath = "//nav//a[contains(@data-gapath,'/small-biz')]")
	private List<WebElement> riseSubMenu;

	private WebElement sectionHeading;

	// private List<WebElement> imageLink;

	private List<WebElement> sectionNews;

	///////////////////////////

	public List<WebElement> getRiseSubMenu() {
		return riseSubMenu;
	}

	public WebElement getSectionHeading(String sectionDiv) {

		// sectionHeading =
		// driver.findElement(By.xpath("//*[@class='sections']/h2/*[contains(text(),'"
		// + sectionDiv + "')]/ancestor::div//h2/a"));
		sectionHeading = driver
				.findElement(By.xpath("//section[@id='subSections']//h2/a[contains(text(),'" + sectionDiv + "')]"));
		return sectionHeading;
	}

	// public List<WebElement> getImageLink(String sectionDiv) {
	// imageLink =
	// driver.findElements(By.xpath("//section[@id='subSections']//h2/a[contains(text(),'"
	// + sectionDiv+ "')]/ancestor::div/ul/li[1]/a"));
	// return imageLink;
	// }

	/*
	 * public List<WebElement> getSectionNews(String sectionDiv) { sectionNews =
	 * driver.findElements(By.xpath(
	 * "//section[@id='subSections']//h2/a[contains(text(),'" + sectionDiv +
	 * "')]/ancestor::div//li/a")); return sectionNews; }
	 */

	public List<WebElement> getSectionNews(String sectionDiv) {
		sectionNews = driver
				.findElements(By.xpath("//li/a[@data-sectiontype='" + sectionDiv + "']/*[string-length(text())>0]"));
		return sectionNews;
	}

}