package web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.JobPageObjects;

public class JobPageMethods {
	private WebDriver driver;
	private JobPageObjects jobPageObjects;

	public JobPageMethods(WebDriver driver) {
		this.driver = driver;
		jobPageObjects = PageFactory.initElements(driver, JobPageObjects.class);

	}

	public List<String> getJobETArticleListLinks() {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(jobPageObjects.getJobArticleListLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

	public List<String> getJobETPrimeArticleListLinks() {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(jobPageObjects.getPrimeSectionArticleLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

	public List<String> getWorklifeWidgetLinks() {
		List<String> listHref = new LinkedList<>();
		try {
			listHref = WebBaseMethods.getListHrefUsingJSE(jobPageObjects.getWorkLifeWidgetLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return listHref;

	}

	public boolean clickThePrimeSectionTab() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(jobPageObjects.getEtPrimeSectionLink());
			WaitUtil.sleep(3000);
			 
		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}
		return flag;

	}
}
