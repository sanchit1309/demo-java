package web.tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import web.pagemethods.WebBaseMethods;

public class ArchivePage extends BaseTest {
	String url = "http://economictimes.indiatimes.com/archivelist/year-%s,month-%s,starttime-%s.cms";
	String baseUrl;
	String dataSheet = "./src/main/resources/testdata/web/ArhiveData.xlsx";

	@BeforeClass(alwaysRun = true)
	public void launchSetUp() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
	}

	@Test
	public void goDoIt() {
		File outFile = new File(dataSheet);
		ExcelUtil excel = new ExcelUtil(outFile);
		int year = 2001;
		int month = 01;
		int day = 01;
		DateTime date = new DateTime("" + year + "-" + month + "-" + day);
		int hashedDate = 36892;
		int counterToRun = hashedDate + 1200;
		for (; hashedDate < counterToRun; hashedDate++) {
			day = date.getDayOfMonth();
			month = date.getMonthOfYear();
			year = date.getYear();
			String navigateTo = String.format(url, year, month, hashedDate);
			System.out.println(navigateTo);
			WebBaseMethods.navigateToUrl(driver,navigateTo);
			List<WebElement> li=driver.findElements(By.xpath("//ul[@class='content']//a"));
			boolean liSize=li.size()>0;
			String message = liSize ? "Headlines Shown" : "No Headlines Shown";
			if(liSize)
				WebBaseMethods.navigateToUrl(driver,li.get(0).getAttribute("href"));
			List<WebElement> articleText=driver.findElements(By.xpath("//div[@class='artText']//*[string-length(text()>0)]"));
			String artmessage = articleText.size()>0 ? "Article Body Shown" : "Article Body Not Shown";
			excel.setCellData("Sheet1",0, excel.getSheet().getPhysicalNumberOfRows() + 1,date.toString(),navigateTo, message,artmessage);
			date = date.plusDays(1);
		}
	}

}
