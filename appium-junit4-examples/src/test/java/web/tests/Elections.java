package web.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import web.pagemethods.ElectionsPageMethods;
import web.pagemethods.WebBaseMethods;

public class Elections extends BaseTest {
	private String electionUrl;
	ElectionsPageMethods electionsPageMethods;
	SoftAssert softAssert = new SoftAssert();;
	List<String> sectionNames = new LinkedList<>();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		electionUrl = BaseTest.baseUrl + Config.fetchConfigProperty("ElectionUrl");
		launchBrowser(electionUrl);
		electionsPageMethods = new ElectionsPageMethods(driver);

	}

	@Test(description = "This test verifies the section on the election homepage",enabled=false)
	public void verifySections() {
		softAssert = new SoftAssert();
		String sections = "Telangana, Chhattisgarh, Madhya Pradesh, Rajasthan, Opinion, Big Fights, Mizoram, Politics and Nation";
		sectionNames = Arrays.asList(sections.split("\\s*,\\s*"));
		sectionNames.forEach(section -> {
			int size = electionsPageMethods.getSpecificHeadlineLinks(section).size();
			System.out.println("Headline size:"+size);
			softAssert.assertTrue(size > 3,
					"The section " + section + " have stories less than 3 on election homepage");

		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies if there is any broken link on the elections homepage",dataProvider="pageUrls")
	public void verifyBrokenLinks(String pageLink) {
		WebBaseMethods.navigateTimeOutHandle(pageLink);
		List<String> allUrls = electionsPageMethods.getAllUrls();
		Set<String> uniqueSet= new HashSet<>();
		uniqueSet.addAll(allUrls);
		allUrls= new LinkedList<>();
		allUrls.addAll(uniqueSet);
		System.out.println("All headline size:"+allUrls.size());
		List<String> errorUrls = new LinkedList<>();
		allUrls.forEach(url -> {
			if (url.contains("https://economictimes") && !((url.contains("javascript"))||(url.contains("marketstats")))) {
				int responseCode = HTTPResponse.checkResponseCode(url);
				if (!(responseCode == 200)) {
					errorUrls.add(url);
				}
				
			}
			
		});
		Set<String> set= new HashSet<>();
		set.addAll(errorUrls);
		List<String> myList= new ArrayList<>();
		myList.addAll(set);
//		System.out.println(myList);
		softAssert.assertTrue(myList.size() == 0,
				"The following urls are having response code other than 200:- " + myList);

		softAssert.assertAll();
	}

	@Test(description = "This test verifies the top headline section on the election homepage",enabled=false)
	public void verifyTopHeadlines() {
		softAssert = new SoftAssert();
		int topNews = 10;
		softAssert = new SoftAssert();
		List<String> topHeadlineLinks = electionsPageMethods.getTopHeadlinesLinks();
		softAssert.assertTrue(topHeadlineLinks.size() >= topNews, "<br>- Less than " + topNews
				+ " articles in the top news section,actual count " + topHeadlineLinks.size());
		topHeadlineLinks.forEach(href -> {
			int responseCode = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(responseCode, 200, href + " is throwing " + responseCode);
		});
		List<String> topNewsDup = VerificationUtil.isListUnique(electionsPageMethods.getTopHeadlinesText());
		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>- Top news is having duplicate stories, repeating story(s)->" + topNewsDup);
		softAssert.assertAll();

	}
	@DataProvider(name="pageUrls")
	public String[] getAllPages() {
		String[] arr=new String[3];
		arr[0]="https://economictimes.com/news/elections";
		arr[1]="https://economictimes.com/news/elections/assembly";
		arr[2]="https://economictimes.com/news/elections/lok-sabha";
		return arr;

	}
}
