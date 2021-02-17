package wap.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import wap.pagemethods.ElectionsPageMethods;
import web.pagemethods.WebBaseMethods;

public class ElectionsPage extends BaseTest {
	private String electionUrl;
	ElectionsPageMethods electionsPageMethods;
	SoftAssert softAssert = new SoftAssert();;
	List<String> sectionNames = new LinkedList<>();
	List<String> allUrls = new ArrayList<String>();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		electionUrl = BaseTest.baseUrl + Config.fetchConfigProperty("ElectionUrl");
		launchBrowser(electionUrl);
		electionsPageMethods = new ElectionsPageMethods(driver);

	}

	@Test(description = "This test verifies the section on the election homepage", enabled = false)
	public void verifySections() {
		softAssert = new SoftAssert();
		String sections = "Telangana, Chhattisgarh, Madhya Pradesh, Rajasthan, Mizoram";
		sectionNames = Arrays.asList(sections.split("\\s*,\\s*"));
		sectionNames.forEach(section -> {
			int size = electionsPageMethods.getSpecificHeadlineLinks(section).size();
			System.out.println(size);
			softAssert.assertTrue(size > 3,
					"The section " + section + " have stories less than 3 on election homepage");

		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies if there is any broken link on the elections homepage", dataProvider = "pageUrls")
	public void verifyBrokenLinks(String pageLink) {
		List<String> tempList = new ArrayList<>();
		WebBaseMethods.navigateTimeOutHandle(pageLink);
		List<String> intersect = new LinkedList<>();
		if (allUrls.size() == 0) {
			allUrls = electionsPageMethods.getAllUrls();
			intersect = allUrls;
		} else {
			tempList = electionsPageMethods.getAllUrls();
			intersect = allUrls.stream().filter(tempList::contains).collect(Collectors.toList());
		}
		Set<String> uniqueSet = new HashSet<>();
		uniqueSet.addAll(intersect);
		intersect = new LinkedList<>();
		intersect.addAll(uniqueSet);
		System.out.println(intersect.size());
		List<String> errorUrls = new LinkedList<>();
		intersect.forEach(url -> {
			if (((url.contains("https://m.economictimes") || url.contains("https://economictimes")))
					&& !((url.contains("javascript"))||(url.contains("marketstats")))) {
				System.out.println("checking for:" + url);
				int responseCode = HTTPResponse.checkResponseCode(url);
				if (!(responseCode == 200)) {
					errorUrls.add(url);
				}
			}
		});
			Set<String> set = new HashSet<>();
			set.addAll(errorUrls);
			List<String> myList = new ArrayList<>();
			myList.addAll(set);

			softAssert.assertTrue(myList.size() == 0,
					"The following urls are having response code other than 200:- " + myList);

		
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the top headline section on the election homepage", enabled = false)
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

	@DataProvider(name = "pageUrls")
	public String[] getAllPages() {
		String[] arr = new String[3];
		arr[0] = "https://m.economictimes.com/news/elections";
		arr[1] = "https://m.economictimes.com/news/elections/assembly";
		arr[2] = "https://m.economictimes.com/news/elections/lok-sabha";
		return arr;

	}
}
