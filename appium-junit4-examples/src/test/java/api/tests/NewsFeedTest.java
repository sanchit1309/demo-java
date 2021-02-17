package api.tests;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.Config;
import common.urlRepo.AppFeeds;
import common.utilities.ApiHelper;
import common.utilities.DBUtil;
import common.utilities.FileUtility;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;

public class NewsFeedTest {
	private SoftAssert softAssert;
	private List<String> headlines = new LinkedList<>();
	private List<String> urls = new LinkedList<>();
	private List<String> dates = new LinkedList<>();
	private String output;
	private String lhsFeedOutput = "";
	private String tabsFeedOutput = "";
	private String feedUrl = AppFeeds.iosSearchFeed;
	private int counter = 0;
	private int subSectionCounter = 0;
	private String hpTabLink = "";
	private int allowedstalenessDays;
	private String storyUrls;
	private String appVersion;

	private String platform;
	private String baseUrl = new Config().fetchConfig(new File("./src/main/resources/properties/Web.properties"),
			"HomeUrl");

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "recipient", "dbFlag", "emailType", "buildVersion", "platform", "failureRecipient" })
	public void setUp(@Optional("sakshi.sharma@timesinternet.in") String recipient, @Optional("false") String dbFlag,
			@Optional("nMail") String emailType, @Optional("andver=409") String buildVersion,
			@Optional("android") String platformName, @Optional("sakshi.sharma@timesinternet.in") String failureRecipient) {
		appVersion = buildVersion;
		platform = platformName;
		System.out.println(String.format(AppFeeds.lhsFeed, platform, appVersion));
		lhsFeedOutput = ApiHelper.getAPIResponse(String.format(AppFeeds.lhsFeed, platform, appVersion));
		tabsFeedOutput = ApiHelper.getAPIResponse(String.format(AppFeeds.tabsFeed, platform, appVersion));
		int counter = 1;
		while (counter < 5 && lhsFeedOutput == null) {
			System.out.println("LHS feed null response,attempt " + (counter + 1));
			lhsFeedOutput = ApiHelper.getAPIResponse(String.format(AppFeeds.lhsFeed, platform, appVersion));
			counter++;
		}
		if (lhsFeedOutput == null)
			throw new RuntimeException("LHS Feed Response is null");
		System.out.println("emailType " + emailType);
		FileUtility.writeIntoPropertiesFile(recipient, failureRecipient, "","", emailType, dbFlag);
	}

	@Test(description = "This test verifies all home page sections")
	public void verifyHomePageTopNews() {
		softAssert = new SoftAssert();

		String url = String.format(AppFeeds.homeFeed, appVersion, platform);
		String multiMediaSection = "More from Partners,MultiMedia";
		List<String> mutimedia = Arrays.asList(multiMediaSection.split("\\s*,\\s*"));

		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			List<String> sectionNames = ApiHelper.getNewsItems(output, "Item", "", "sn");
			System.out.println(sectionNames + " for home page");
			sectionNames.forEach(section -> {
				counter = 0;
				String sectionName = section.trim().length() > 1 ? section : "Top News";
				headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
				Assert.assertTrue(headlines.size() > 0,
						"<br>In section: " + sectionName + " headlines count is equal to 0, count found "
								+ headlines.size() + " for platform:" + platform);
				urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");

				dates = mutimedia.stream().filter(o -> sectionName.contains(o)).findFirst().isPresent()
						? new LinkedList<>() : ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

				// dates verification
				int allowedstalenessDays = 20;

				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"<br>In section: " + sectionName + ",  " + headlines.get(counter)
									+ " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date)
									+ " for platform:" + platform);
					counter++;
				});
				// Headlines duplication verification
				List<String> topNewsDup = VerificationUtil.isListUnique(urls);
				List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
				Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

				softAssert.assertTrue(topNewsDup.isEmpty(),
						"<br>- Section: " + sectionName + ", is having duplicate stories, repeating story(s)->"
								+ topNewsDup + " for platform:" + platform);
				storyUrls = "";
				List<String> temp = urls.stream().filter(o -> o.contains("ad.")).collect(Collectors.toList());
				urls.removeAll(temp);
				urls.forEach(link -> {
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					if (link.contains("app:"))
						return;
					link = link.replaceAll("\\?utm_source.*", "");
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					int responseCode = HTTPResponse.checkResponseCode(link);
					softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link + ">Story</a> in " + sectionName
							+ " is throwing " + responseCode + " for platform:" + platform);
				});

				List<String> uniqueUrl = new LinkedList<>();
				indexedDup.forEach((K, V) -> {
					V.forEach(action -> {
						storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
						String link = urls.get(new Integer(action));
						link = link.startsWith("http") ? link
								: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
						uniqueUrl.add(link);
					});
					if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
						Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
								: false;
						softAssert.assertTrue(!flag,
								"<br>- Section: " + sectionName + ", is having multipublished stories, headline: " + K
										+ " urls:  " + storyUrls + " for platform:" + platform);
					}
				});
				// urls verification

			});
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies Home Page tabs", groups = { "topmonitor" })
	public void verifyHomePageTopTabs() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		output = tabsFeedOutput;
		String multiMediaSection = "More from Partners,MultiMedia";
		List<String> mutimedia = Arrays.asList(multiMediaSection.split("\\s*,\\s*"));

		List<String> tabNames = ApiHelper.getNewsItems(output, "Item", "", "nm");
		List<String> tabLink = ApiHelper.getNewsItems(output, "Item", "", "du");
		tabLink.forEach(action -> {
			hpTabLink = action;
			if (hpTabLink.contains("m.economictimes.com")) {
				subSectionCounter++;
				return;
			}

			action = baseUrl + action;
			output = ApiHelper.getAPIResponse(action);

			List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
			// int pno = Integer.parseInt(pageNumbers.get(0)) >= 3 ? 3 :
			// Integer.parseInt(pageNumbers.get(0));
			int pno = pageNumbers.size() > 0
					? (hpTabLink.contains("homefeed") ? Integer.parseInt(pageNumbers.get(0)) : 1) : 1;
			for (int i = 1; i <= pno; i++) {
				String url1 = action.replaceAll("&curpg=[\\d]+", "");
				url1 += "&curpg=" + i;
				if (i > 1)
					output = ApiHelper.getAPIResponse(url1);

				List<String> sectionNames = new LinkedList<>();

				if (!hpTabLink.contains("quickread"))
					sectionNames = ApiHelper.getNewsItems(output, "Item", "", "sn");
				sectionNames = sectionNames.size() > 0 ? sectionNames : new ArrayList<String>(Arrays.asList("News"));

				sectionNames.forEach(section -> {
					counter = 0;
					String sectionName = section.trim().length() > 1 ? section : "Top News";
					if (!hpTabLink.contains("quickread")) {
						headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
						urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
						dates = mutimedia.stream().filter(o -> sectionName.contains(o)).findFirst().isPresent()
								? new LinkedList<>() : ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");
					} else {
						headlines = ApiHelper.getNewsItems(output, "NewsItem", "", "hl");
						urls = ApiHelper.getNewsItems(output, "NewsItem", "", "wu");
						dates = ApiHelper.getNewsItems(output, "NewsItem", "", "da");
					}
					System.out.println(sectionName + "\n" + urls + "\n" + dates);
					Assert.assertTrue(headlines.size() > 0, "<br>On tab " + tabNames.get(subSectionCounter) + " "
							+ sectionName + " headlines count is 0");

					// dates verification
					allowedstalenessDays = setDaysStaleness(tabNames.get(subSectionCounter), sectionName);
					/*
					 * System.out.println(allowedstalenessDays + " days " +
					 * tabNames.get(subSectionCounter) + " subsection   " +
					 * sectionName + " section ");
					 */
					String tabName = tabNames.get(subSectionCounter);
					if (!(tabName.equalsIgnoreCase("Brandwire") || tabName.equalsIgnoreCase("Opinion"))) {
						dates.forEach(date -> {
							softAssert.assertTrue(
									VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
									"<br>On tab " + tabName + " " + sectionName + ",  " + headlines.get(counter)
											+ " : article is not latest, article date " + (date.endsWith("0000")
													? new DateTime(Long.parseLong(date)) : date));
							counter++;
						});
					}
					// Headlines duplication verification
					List<String> topNewsDup = VerificationUtil.isListUnique(urls);
					List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
					Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines,
							headlinesDup);

					softAssert.assertTrue(topNewsDup.isEmpty(), "<br>- Section: " + sectionName
							+ ", is having duplicate stories, repeating story(s)->" + topNewsDup);
					indexedDup.forEach((K, V) -> {
						storyUrls = "";
						List<String> uniqueUrl = new LinkedList<>();
						V.forEach(indexes -> {
							storyUrls += "<br> " + urls.get(new Integer(indexes)) + " ";
							String link = urls.get(new Integer(indexes));
							link = link.startsWith("http") ? link
									: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
							uniqueUrl.add(link);
						});

						if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
							System.out.println("urls are not dup");
							Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1
									? true : false;
							System.out.println("flag articleshow count>1 " + flag);
							softAssert.assertTrue(!flag, "<br>- Section: " + sectionName
									+ ", is having multipublished stories, headline: " + K + " urls:  " + storyUrls);
						}
					});

					// urls verification
					List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp"))
							.collect(Collectors.toList());
					urls.removeAll(temp);
					urls.forEach(link -> {
						link = link.replaceAll("\\?utm_source.*", "");
						link = link.startsWith("http") ? link
								: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
						if (link.contains("ad.") || link.contains("etapp"))
							return;

						int responseCode = HTTPResponse.checkResponseCode(link);
						softAssert.assertEquals(responseCode, 200,
								"<br>- <a href=" + link + ">Story</a> Tab " + tabNames.get(subSectionCounter) + " "
										+ sectionName + "  is throwing " + responseCode);
					});

				});
			}
			subSectionCounter++;
		});
		softAssert.assertAll();

	}

	@Test(description = "This test verifies morning briefs", groups = { "briefs" })
	public void verifyMorningBriefFeed() {
		int allowedstalenessDays = 1;
		counter = 0;
		softAssert = new SoftAssert();
		String sectionName = "Morning Brief";
		String url = String.format(AppFeeds.morningBriefFeed, appVersion, platform);
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "NewsItem", "", "hl");
		Assert.assertTrue(headlines.size() > 0, "<br>In section: " + sectionName + " headlines count is equal to 0");

		urls = ApiHelper.getNewsItems(output, "NewsItem", "", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);

		dates = ApiHelper.getNewsItems(output, "NewsItem", "", "da");

		dates.forEach(date -> {
			softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
					"<br>In section: " + sectionName + ",  " + headlines.get(counter)
							+ " : article is not latest, article date "
							+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
			counter++;
		});
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>- Section: " + sectionName + ", is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();

			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});
			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				softAssert.assertTrue(!flag, "<br>- Section: " + sectionName
						+ ", is having multipublished stories, headline: " + K + " urls:  " + storyUrls);
			}

		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			if (link.contains("ad.") || link.contains("etapp"))
				return;
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> in " + sectionName + " is throwing " + responseCode);

		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies evening briefs", groups = { "briefs" })
	public void verifyEveningBriefFeed() {
		counter = 0;
		DateTime dateTime = new DateTime();
		int allowedstalenessDays = dateTime.getHourOfDay() > 15 ? 0 : 1;
		softAssert = new SoftAssert();
		String sectionName = "Evening Brief";
		String url = String.format(AppFeeds.eveningBriefFeed, appVersion, platform);
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "NewsItem", "", "hl");
		Assert.assertTrue(headlines.size() > 0, "<br>In section: " + sectionName + " headlines count is equal to 0");

		urls = ApiHelper.getNewsItems(output, "NewsItem", "", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);

		dates = ApiHelper.getNewsItems(output, "NewsItem", "", "da");

		dates.forEach(date -> {
			softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
					"<br>In section: " + sectionName + ",  " + headlines.get(counter)
							+ " : article is not latest, article date "
							+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
			counter++;
		});
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>- Section: " + sectionName + ", is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();

			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});

			Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				softAssert.assertTrue(!flag, "<br>- Section: " + sectionName
						+ ", is having multipublished stories, headline: " + K + " urls:  " + storyUrls);
			}

		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			if (link.contains("ad.") || link.contains("etapp"))
				return;
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> in " + sectionName + " is throwing " + responseCode);

		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies search suggestions and topic search results", groups = { "topmonitor" })
	public void verifySearch() {
		String q = "tcs";
		String topicq = "tata";
		String compName = "Tata Consultancy Services Ltd.";
		String compId = "8345";
		String url = String.format(AppFeeds.searchFeed, platform, appVersion) + "&query=" + q;
		System.out.println(url);
		output = ApiHelper.getAPIResponse(url);
		softAssert = new SoftAssert();
		List<String> companyName = ApiHelper.getValuesFromJSONResponse(output, "nm", "", "", "Item", "company");
		softAssert.assertEquals(companyName.get(0), compName,
				"Company name not matching, via feed: " + companyName.get(0) + " ,expected: " + compName);
		List<String> companyId = ApiHelper.getValuesFromJSONResponse(output, "id", "", "", "Item", "company");
		softAssert.assertEquals(companyId.get(0), compId,
				"Company Id not matching, via feed: " + companyId.get(0) + " ,expected: " + compId);
		// for topic search
		url = String.format(AppFeeds.topicFeed, platform, appVersion) + "&query=" + topicq;
		output = ApiHelper.getAPIResponse(url);

		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On topic search page no. " + i + " of: " + topicq + " no stories are shown,api url " + url);
			urls = ApiHelper.getValuesFromJSONResponse(output, "wu", "", "", "Item", "NewsItem");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			softAssert.assertTrue(topNewsDup.isEmpty(), "<br>- Topic search page for  " + topicq
					+ " , is having duplicate stories, repeating story(s)->" + topNewsDup);
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> in topic search page for  " + topicq + " is throwing " + responseCode);
			});
		}
		softAssert.assertAll();

	}
/////////////////////////////////Multimedia//////////////////////////
	

	@Test(description = "This test verifies Multimedia Home section ")
	public void verifyMultimediaHomeSection() {
		counter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String topNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Multimedia", "nm", "Multimedia Home",
				"du");
		url = baseUrl + topNews;
		int allowedstalenessDays = 120;
		/**
		 * The code to check across all pages has been written and marked
		 * commented, so whenever it has to be run remove assigned value of 1 to
		 * i
		 **/
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Multimedia Home page no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Multimedia Home page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-  Multimedia Home page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Industry-Top News is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Multimedia Home page " + headlines.get(counter)
								+ " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Multimedia Markets section ")
	public void verifyMultimediaMarketsSection() {
		counter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String sectionNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Multimedia", "nm", "Markets",
				"du");
		url = baseUrl + sectionNews + "&contenttype=m";
		int allowedstalenessDays = 120;
		/**
		 * The code to check across all pages has been written and marked
		 * commented, so whenever it has to be run remove assigned value of 1 to
		 * i
		 **/
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Multimedia- Markets page no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Multimedia Markets page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-  Multimedia - Markets page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Multimedia - Markets News is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Multimedia - Markets page " + headlines.get(counter)
								+ " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}
	
	
	@Test(description = "This test verifies Multimedia Auto section ")
	public void verifyMultimediaAutoSection() {
		counter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String sectionNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Multimedia", "nm", "Auto",
				"du");
		url = baseUrl + sectionNews + "&contenttype=m" ;
		System.out.println(url);
		int allowedstalenessDays = 120;
		/**
		 * The code to check across all pages has been written and marked
		 * commented, so whenever it has to be run remove assigned value of 1 to
		 * i
		 **/
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Multimedia - Auto page no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Multimedia - Auto page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-  Multimedia - Auto page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Multimedia - Auto News is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Multimedia - Auto page " + headlines.get(counter)
								+ " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}
	
	@Test(description = "This test verifies Multimedia - Defence section ")
	public void verifyMultimediaDefenceSection() {
		counter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String sectionNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Multimedia", "nm", "Defence",
				"du");
		url = baseUrl + sectionNews + "&contenttype=m" ;
		System.out.println(url);
		int allowedstalenessDays = 120;
		/**
		 * The code to check across all pages has been written and marked
		 * commented, so whenever it has to be run remove assigned value of 1 to
		 * i
		 **/
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Multimedia - Defence page no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Multimedia - Defence page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-  Multimedia - Defence page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Multimedia Defence News is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Multimedia - Defence page " + headlines.get(counter)
								+ " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}
	
	@Test(description = "This test verifies Multimedia News section ")
	public void verifyMultimediaNewsSection() {
		counter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String sectionNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Multimedia", "nm", "News",
				"du");
		url = baseUrl + sectionNews + "&contenttype=m" ;
		System.out.println(url);
		int allowedstalenessDays = 120;
		/**
		 * The code to check across all pages has been written and marked
		 * commented, so whenever it has to be run remove assigned value of 1 to
		 * i
		 **/
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Multimedia - News page no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Multimedia - News page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-  Multimedia - News page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Multimedia - News section is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Multimedia - News page " + headlines.get(counter)
								+ " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}
	
	@Test(description = "This test verifies Multimedia Banking & Finance section ")
	public void verifyMultimediaBankingFinanceSection() {
		counter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String sectionNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Multimedia", "nm", "Banking & Finance",
				"du");
		url = baseUrl + sectionNews + "&contenttype=m" ;
		System.out.println(url);
		int allowedstalenessDays = 120;
		/**
		 * The code to check across all pages has been written and marked
		 * commented, so whenever it has to be run remove assigned value of 1 to
		 * i
		 **/
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Multimedia - Banking & Finance page no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Multimedia - Banking & Finance page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-  Multimedia - Banking & Finance page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Multimedia Banking & Finance News is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Multimedia - Banking & Finance page " + headlines.get(counter)
								+ " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}
	
	@Test(description = "This test verifies Multimedia Tech section ")
	public void verifyMultimediaTechSection() {
		counter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String sectionNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Multimedia", "nm", "Tech",
				"du");
		url = baseUrl + sectionNews + "&contenttype=m" ;
		System.out.println(url);
		int allowedstalenessDays = 120;
		/**
		 * The code to check across all pages has been written and marked
		 * commented, so whenever it has to be run remove assigned value of 1 to
		 * i
		 **/
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Multimedia - Tech page no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Multimedia - Tech page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-  Multimedia - Tech page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Multimedia - Tech News is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Multimedia - Tech page " + headlines.get(counter)
								+ " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}
	
	@Test(description = "This test verifies Multimedia Industry section ")
	public void verifyMultimediaIndustrySection() {
		counter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String sectionNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Multimedia", "nm", "Industry",
				"du");
		url = baseUrl + sectionNews + "&contenttype=m" ;
		System.out.println(url);
		int allowedstalenessDays = 120;
		/**
		 * The code to check across all pages has been written and marked
		 * commented, so whenever it has to be run remove assigned value of 1 to
		 * i
		 **/
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Multimedia - Industry page no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Multimedia - Industry page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-  Multimedia - Industry page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Multimedia - Industry News is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Multimedia - Industry page " + headlines.get(counter)
								+ " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}

	@Test(description = "This test verifies podcast listing page ", groups = { "" })
	public void verifyPodcastListingFeed() {
		counter = 0;
//		DateTime dateTime = new DateTime();
//		int allowedstalenessDays = dateTime.getHourOfDay() > 15 ? 0 : 1;
		softAssert = new SoftAssert();
		String sectionName = "Podcast";
		String url = String.format(AppFeeds.podcastListingFeed, appVersion, platform);
		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		
		Assert.assertTrue(headlines.size() > 0, "<br>In section: " + sectionName + " headlines count is equal to 0");

		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);

//		dates = ApiHelper.getNewsItems(output, "NewsItem", "", "da");
//
//		dates.forEach(date -> {
//			softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
//					"<br>In section: " + sectionName + ",  " + headlines.get(counter)
//							+ " : article is not latest, article date "
//							+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
//			counter++;
//		});
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>- Section: " + sectionName + ", is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();

			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});

			Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				softAssert.assertTrue(!flag, "<br>- Section: " + sectionName
						+ ", is having multipublished stories, headline: " + K + " urls:  " + storyUrls);
			}

		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			if (link.contains("ad.") || link.contains("etapp"))
				return;
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Podcast</a> in " + sectionName + " is throwing " + responseCode);

		});
		softAssert.assertAll();
	}
	
	@Test(description = "This test verifies Multimedia Personal Finance section ")
	public void verifyMultimediaPersonalFinanceSection() {
		counter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String sectionNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Multimedia", "nm", "Personal Finance",
				"du");
		url = baseUrl + sectionNews + "&contenttype=m" ;
		System.out.println(url);
		int allowedstalenessDays = 120;
		/**
		 * The code to check across all pages has been written and marked
		 * commented, so whenever it has to be run remove assigned value of 1 to
		 * i
		 **/
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Multimedia - Personal Finance page no stories are shown, api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Multimedia - Personal Finance page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-  Multimedia - Personal Finance page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Multimedia - Personal Finance News is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Multimedia - Personal Finance page " + headlines.get(counter)
								+ " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Multimedia Company section ")
	public void verifyMultimediaCompanySection() {
		counter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String sectionNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Multimedia", "nm", "Company",
				"du");
		url = baseUrl + sectionNews + "&contenttype=m" ;
		System.out.println(url);
		int allowedstalenessDays = 120;
		/**
		 * The code to check across all pages has been written and marked
		 * commented, so whenever it has to be run remove assigned value of 1 to
		 * i
		 **/
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Multimedia - Company page no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Multimedia - Company page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-  Multimedia - Company page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Multimedia - Company News is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Multimedia - Company page " + headlines.get(counter)
								+ " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}
	
	@Test(description = "This test verifies Multimedia Biz Entrepreneurship section ")
	public void verifyMultimediaBizEntrepreneurshipSection() {
		counter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String sectionNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Multimedia", "nm", "Biz Entrepreneurship",
				"du");
		url = baseUrl + sectionNews + "&contenttype=m" ;
		System.out.println(url);
		int allowedstalenessDays = 120;
		/**
		 * The code to check across all pages has been written and marked
		 * commented, so whenever it has to be run remove assigned value of 1 to
		 * i
		 **/
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Multimedia - Biz page no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Multimedia - Biz page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-  Multimedia - Biz page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Multimedia - Biz News is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Multimedia - Biz page " + headlines.get(counter)
								+ " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Multimedia Jobs section ")
	public void verifyMultimediaJobsSection() {
		counter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String sectionNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Multimedia", "nm", "Jobs",
				"du");
		url = baseUrl + sectionNews + "&contenttype=m" ;
		System.out.println(url);
		int allowedstalenessDays = 120;
		/**
		 * The code to check across all pages has been written and marked
		 * commented, so whenever it has to be run remove assigned value of 1 to
		 * i
		 **/
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Multimedia - Jobs page no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Multimedia - Jobs page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-  Multimedia - Jobs page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Multimedia - Jobs News is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Multimedia - Jobs page " + headlines.get(counter)
								+ " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}


	/********************** News ********************************/
	@Test(description = "This test verifies News-Company section ")
	private void verifyNewsCompanySection() {
		softAssert = new SoftAssert();

		String url = String.format(AppFeeds.newsbycompanyFeed, appVersion, platform);
		output = ApiHelper.getAPIResponse(url);

		subSectionCounter = 0;
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionName = ApiHelper.getNewsItems(output, "Item", "", "nm");
		subSections.forEach(requesturl -> {
			counter = 0;
			requesturl = requesturl.startsWith("http") ? requesturl
					: (requesturl = requesturl.startsWith("/") ? baseUrl + requesturl : baseUrl + "/" + requesturl);
			output = ApiHelper.getAPIResponse(requesturl);

			int i = 1;

			/*
			 * requesturl = requesturl.replaceAll("&curpg=[\\d]+", "");
			 * requesturl += "&curpg=" + i; if(i>1) output =
			 * ApiHelper.getAPIResponse(requesturl);
			 */
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Company page in " + subSectionName.get(subSectionCounter)
					+ " section on page no. " + i + " no stories are shown ,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Company page in " + subSectionName.get(subSectionCounter) + " section on page no. " + i
							+ " , is having duplicate stories, duplicate story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag, "<br>- On Company page in " + subSectionName.get(subSectionCounter)
							+ " section on page no. " + i + " , has mutipublished stories, repeating story(s)-> " + K
							+ " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link + ">Story</a> on Company page in "
						+ subSectionName.get(subSectionCounter) + " is throwing " + responseCode);
			});

			// }
			subSectionCounter++;
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies News-Top News section", groups = { "topmonitor" })
	public void verifyNewsTopNews() {
		counter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String topNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "News", "nm", "Top News", "du");
		url = baseUrl + topNews;
		int allowedstalenessDays = 2;
		/**
		 * The code to check across all pages has been written and marked
		 * commented, so whenever it has to be run remove assigned value of 1 to
		 * i
		 **/
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On News-Top News page no stories are shown over " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-News-Top News page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-  News-Top News page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on News-Top News is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On News-Top News page " + headlines.get(counter) + " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies News-Economy section ")
	private void verifyNewsEconomySection() {
		softAssert = new SoftAssert();
		int allowedstalenessDays = 30;
		String url = String.format(AppFeeds.economyFeed, appVersion, platform);
		output = ApiHelper.getAPIResponse(url);
		counter = 0;
		subSectionCounter = 0;
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionName = ApiHelper.getNewsItems(output, "Item", "", "nm");
		System.out.println(subSectionName + " for economy");
		subSections.forEach(requesturl -> {
			counter = 0;
			requesturl = requesturl.startsWith("http") ? requesturl
					: (requesturl = requesturl.startsWith("/") ? baseUrl + requesturl : baseUrl + "/" + requesturl);
			output = ApiHelper.getAPIResponse(requesturl);

			int i = 1;
			// requesturl = requesturl.replaceAll("&curpg=[\\d]+", "");
			// requesturl += "&curpg=" + i;

			// output = ApiHelper.getAPIResponse(requesturl);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Company page in " + subSectionName.get(subSectionCounter)
					+ " section on page no. " + i + " no stories are shown, api url " + requesturl);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Economy page in " + subSectionName.get(subSectionCounter) + " section on page no. " + i
							+ " , is having duplicate stories, repeating story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				if (VerificationUtil.isListUnique(V).isEmpty()) {
					V.forEach(action -> {
						storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
						String link = urls.get(new Integer(action));
						link = link.startsWith("http") ? link
								: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
						uniqueUrl.add(link);
					});

					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
						softAssert.assertTrue(!flag,
								"<br>- Economy page, has mutipublished stories, repeating story(s)-> " + K + " urls:  "
										+ storyUrls);
					}

				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link + ">Story</a> on Economy page in "
						+ subSectionName.get(subSectionCounter) + " is throwing " + responseCode);
			});
			if (i == 1) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");
				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Economy page, in section " + subSectionName.get(subSectionCounter) + ",  "
									+ headlines.get(counter) + " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
			subSectionCounter++;
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies News-Politics Nation section ")
	public void verifyNewsPoliticsNationSection() {
		counter = 0;
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String politicsNation = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "News", "nm",
				"Politics and Nation", "du");
		String url = baseUrl + politicsNation;
		int allowedstalenessDays = 2;
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Politics-Nation page no stories are shown, api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> PolNationDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(PolNationDup.isEmpty(),
				"<br>-Politics-Nation page, is having duplicate stories, repeating story(s)->" + PolNationDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-Politics And Nation-Top News page, has mutipublished stories, repeating story(s)-> "
									+ K + " urls:  " + storyUrls);
				}

			}
		});
		;
		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Politics-Nation is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Politics-Nation page " + headlines.get(counter) + " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}

	@Test(description = "This test verifies News-Defence section ")
	public void verifyNewsDefenceSection() {
		counter = 0;
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String defence = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "News", "nm", "Defence", "du");
		String url = baseUrl + defence;
		int allowedstalenessDays = 5;
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;

		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Defence page no stories are shown, api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> defenceDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(defenceDup.isEmpty(),
				"<br>-Defence page, is having duplicate stories, repeating story(s)->" + defenceDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>- Defence-Top News page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Defence is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Defence page " + headlines.get(counter) + " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}

	@Test(description = "This test verifies News-International section ")
	private void verifyNewsInternationalSection() {
		softAssert = new SoftAssert();
		int allowedstalenessDays = 5;
		String url = String.format(AppFeeds.internationalFeed, appVersion, platform);
		output = ApiHelper.getAPIResponse(url);
		counter = 0;
		subSectionCounter = 0;
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionName = ApiHelper.getNewsItems(output, "Item", "", "nm");
		System.out.println(subSectionName + " for international");
		subSections.forEach(requesturl -> {
			counter = 0;
			requesturl = requesturl.startsWith("http") ? requesturl
					: (requesturl = requesturl.startsWith("/") ? baseUrl + requesturl : baseUrl + "/" + requesturl);
			output = ApiHelper.getAPIResponse(requesturl);
			/**
			 * The code to check across all pages has been written and marked
			 * commented, so whenever it has to be run remove assigned value of
			 * 1 to i
			 **/
			// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn",
			// "", "tp");
			// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
			// for (int i = 1; i <= Integer.parseInt(pno); i++) {
			int i = 1;
			requesturl = requesturl.replaceAll("&curpg=[\\d]+", "");
			requesturl += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(requesturl);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 1, "On International page in " + subSectionName.get(subSectionCounter)
					+ " section on page no. " + i + " no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On International page in " + subSectionName.get(subSectionCounter) + " section on page no. "
							+ i + " , is having duplicate stories, repeating story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				if (VerificationUtil.isListUnique(V).isEmpty()) {
					V.forEach(action -> {
						storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
						String link = urls.get(new Integer(action));
						link = link.startsWith("http") ? link
								: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
						uniqueUrl.add(link);
					});

					if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
						Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
								: false;
						softAssert.assertTrue(!flag, "<br>- On International page in "
								+ subSectionName.get(subSectionCounter) + " section on page no. " + i
								+ " , has multipublished stories, repeating story(s)->" + K + " urls:  " + storyUrls);
					}
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on International page in "
								+ subSectionName.get(subSectionCounter) + " is throwing " + responseCode);
			});
			if (i == 1) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");
				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Economy page, in section " + subSectionName.get(subSectionCounter) + ",  "
									+ headlines.get(counter) + " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
			subSectionCounter++;
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies News-India Unlimited section ")
	private void verifyNewsIndiaUnlimitedSection() {
		softAssert = new SoftAssert();
		String url = String.format(AppFeeds.indiaUnlimitedFeed, appVersion, platform);
		output = ApiHelper.getAPIResponse(url);
		counter = 0;
		subSectionCounter = 0;
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionName = ApiHelper.getNewsItems(output, "Item", "", "nm");
		System.out.println(subSectionName + " for india unlimited");
		subSections.forEach(requesturl -> {
			counter = 0;
			requesturl = requesturl.startsWith("http") ? requesturl
					: (requesturl = requesturl.startsWith("/") ? baseUrl + requesturl : baseUrl + "/" + requesturl);
			output = ApiHelper.getAPIResponse(requesturl);
			/**
			 * The code to check across all pages has been written and marked
			 * commented, so whenever it has to be run remove assigned value of
			 * 1 to i
			 **/
			// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn",
			// "", "tp");
			// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
			// for (int i = 1; i <= Integer.parseInt(pno); i++) {
			int i = 1;
			requesturl = requesturl.replaceAll("&curpg=[\\d]+", "");
			requesturl += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(requesturl);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Company page in " + subSectionName.get(subSectionCounter)
					+ " section on page no. " + i + " no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On India Unlimited page in " + subSectionName.get(subSectionCounter)
							+ " section on page no. " + i + " , is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				if (VerificationUtil.isListUnique(V).isEmpty()) {
					V.forEach(action -> {
						storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
						String link = urls.get(new Integer(action));
						link = link.startsWith("http") ? link
								: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
						uniqueUrl.add(link);
					});

					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
						softAssert.assertTrue(!flag,
								"<br>- India Unlimited News page, has mutipublished stories, repeating story(s)-> " + K
										+ " urls:  " + storyUrls);
					}

				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on India Unlimited page in "
								+ subSectionName.get(subSectionCounter) + " is throwing " + responseCode);
			});
			// }
			subSectionCounter++;
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies News-Sports section ")
	public void verifyNewsSportsSection() {
		counter = 0;
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String sports = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "News", "nm", "Sports", "du");
		String url = baseUrl + sports;
		int allowedstalenessDays = 5;
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;

		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Sports page no stories are shown, api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Sports page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag, "<br>- Sports page, has mutipublished stories, repeating story(s)-> "
							+ K + " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Sports is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Sports page " + headlines.get(counter) + " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}

	@Test(description = "This test verifies News-Science section ")
	public void verifyNewsScienceSection() {
		counter = 0;
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String science = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "News", "nm", "Science", "du");
		String url = baseUrl + science;
		int allowedstalenessDays = 100;
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Science page no stories are shown, api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Science page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>- Science News page, has mutipublished stories, repeating story(s)-> " + K + " urls:  "
									+ storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Science is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Science page " + headlines.get(counter) + " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}

	@Test(description = "This test verifies News-Press Release section ")
	public void verifyNewsPressReleaseSection() {

		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String pressRelease = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "News", "nm",
				"Press Release", "du");
		String url = baseUrl + pressRelease;

		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;

		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Press Release page no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Press Release page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>- Press Release page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Press Release is throwing " + responseCode);
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies News-Environment section ")
	private void verifyNewsEnvironmentSection() {
		softAssert = new SoftAssert();
		String url = String.format(AppFeeds.environmentFeed, appVersion, platform);
		output = ApiHelper.getAPIResponse(url);
		counter = 0;
		subSectionCounter = 0;
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionName = ApiHelper.getNewsItems(output, "Item", "", "nm");
		System.out.println(subSectionName + " for environment");
		subSections.forEach(requesturl -> {
			counter = 0;
			requesturl = requesturl.startsWith("http") ? requesturl
					: (requesturl = requesturl.startsWith("/") ? baseUrl + requesturl : baseUrl + "/" + requesturl);
			output = ApiHelper.getAPIResponse(requesturl);
			/**
			 * The code to check across all pages has been written and marked
			 * commented, so whenever it has to be run remove assigned value of
			 * 1 to i
			 **/
			// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn",
			// "", "tp");
			// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
			// for (int i = 1; i <= Integer.parseInt(pno); i++) {
			int i = 1;
			requesturl = requesturl.replaceAll("&curpg=[\\d]+", "");
			requesturl += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(requesturl);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Environment page in " + subSectionName.get(subSectionCounter)
					+ " section on page no. " + i + " no stories are shown api url " + requesturl);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Environment page in " + subSectionName.get(subSectionCounter) + " section on page no. "
							+ i + " , is having duplicate stories, repeating story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				if (VerificationUtil.isListUnique(V).isEmpty()) {
					V.forEach(action -> {
						storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
						String link = urls.get(new Integer(action));
						link = link.startsWith("http") ? link
								: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
						uniqueUrl.add(link);
					});

					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
						softAssert.assertTrue(!flag,
								"<br>- Environment News page, has mutipublished stories, repeating story(s)-> " + K
										+ " urls:  " + storyUrls);
					}

				}
			});

			// urls verification
//			urls.forEach(link -> {
//				link = link.replaceAll("\\?utm_source.*", "");
//				link = link.startsWith("http") ? link
//						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
//				int responseCode = HTTPResponse.checkResponseCode(link);
//				softAssert.assertEquals(responseCode, 200,
//						"<br>- <a href=" + link + ">Story</a> on Environment page in "
//								+ subSectionName.get(subSectionCounter) + " is throwing " + responseCode);
//			});
			// }
			subSectionCounter++;
		});
		softAssert.assertAll();
	}

	/********************** Industry ********************************/
	@Test(description = "This test verifies Industry-Top News section ", groups = { "topmonitor" })
	public void verifyIndustryTopNews() {
		counter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String topNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Industry", "nm", "Top News",
				"du");
		url = baseUrl + topNews;
		int allowedstalenessDays = 2;
		/**
		 * The code to check across all pages has been written and marked
		 * commented, so whenever it has to be run remove assigned value of 1 to
		 * i
		 **/
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// for (int i = 1; i <= Integer.parseInt(pno); i++) {
		int i = 1;
		url = url.replaceAll("&curpg=[\\d]+", "");
		url += "&curpg=" + i;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Industry-Top News page no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Industry-Top News page, is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			if (VerificationUtil.isListUnique(V).isEmpty()) {
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					softAssert.assertTrue(!flag,
							"<br>-  Industry-Top News page, has mutipublished stories, repeating story(s)-> " + K
									+ " urls:  " + storyUrls);
				}

			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Industry-Top News is throwing " + responseCode);
		});
		if (i == 1) {
			dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

			dates.forEach(date -> {
				softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
						"On Industry-Top News page " + headlines.get(counter)
								+ " : article is not latest, article date "
								+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
				counter++;
			});
		}

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Industry-Auto section ")
	public void verifyIndustryAutoSection() {
		subSectionCounter = 0;
		int allowedstalenessDays = 15;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String auto = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Industry", "nm", "Auto", "l3");
		url = baseUrl + auto;
		output = ApiHelper.getAPIResponse(url);
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionNames = ApiHelper.getNewsItems(output, "Item", "", "dn");

		subSections.forEach(action -> {
			counter = 0;
			action = baseUrl + action;
			output = ApiHelper.getAPIResponse(action);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Industry-Auto page, in section "
					+ subSectionNames.get(subSectionCounter) + " no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> autoDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(autoDup.isEmpty(),
					"<br>-Industry-Auto page , in section " + subSectionNames.get(subSectionCounter)
							+ " is having duplicate stories, repeating story(s)->" + autoDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				if (VerificationUtil.isListUnique(V).isEmpty()) {
					V.forEach(index -> {
						storyUrls += "<br> " + urls.get(new Integer(index)) + " ";
						String link = urls.get(new Integer(index));
						link = link.startsWith("http") ? link
								: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
						uniqueUrl.add(link);
					});

					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
						softAssert.assertTrue(!flag,
								"<br>-  Industry-Auto page, has mutipublished stories, repeating story(s)-> " + K
										+ " urls:  " + storyUrls);
					}

				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on Industry-Auto page , in section "
								+ subSectionNames.get(subSectionCounter) + " is throwing " + responseCode);
			});
			if (subSectionNames.get(subSectionCounter).equals("Top News")) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Industry-Auto page , in section " + subSectionNames.get(subSectionCounter) + "  "
									+ headlines.get(counter) + " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
			subSectionCounter++;
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Industry-Banking & Finance section ")
	public void verifyIndustryBankingSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String banking = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Industry", "nm",
				"Banking & Finance", "l3");
		url = baseUrl + banking;
		int allowedstalenessDays = 15;
		output = ApiHelper.getAPIResponse(url);
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionNames = ApiHelper.getNewsItems(output, "Item", "", "dn");

		subSections.forEach(action -> {
			counter = 0;
			action = baseUrl + action;
			output = ApiHelper.getAPIResponse(action);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Industry-Banking & Finance page, in section "
					+ subSectionNames.get(subSectionCounter) + " no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>-Industry-Banking & Finance page, in section " + subSectionNames.get(subSectionCounter)
							+ " is having duplicate stories, repeating story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				if (VerificationUtil.isListUnique(V).isEmpty()) {
					V.forEach(index -> {
						storyUrls += "<br> " + urls.get(new Integer(index)) + " ";
						String link = urls.get(new Integer(index));
						link = link.startsWith("http") ? link
								: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
						uniqueUrl.add(link);
					});

					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
						softAssert.assertTrue(!flag,
								"<br>- Industry Banking page, has mutipublished stories, repeating story(s)-> " + K
										+ " urls:  " + storyUrls);
					}

				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on Industry-Banking & Finance page, in section "
								+ subSectionNames.get(subSectionCounter) + " is throwing " + responseCode);
			});
			if (subSectionNames.get(subSectionCounter).equals("Top News")) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Industry-Banking & Finance page, in section " + subSectionNames.get(subSectionCounter)
									+ " " + headlines.get(counter) + " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
			subSectionCounter++;
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Industry-Consumer Products section ")
	public void verifyIndustryConsProdSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String consumer = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Industry", "nm",
				"Consumer Products", "l3");
		url = baseUrl + consumer;
		int allowedstalenessDays = 15;
		output = ApiHelper.getAPIResponse(url);
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionNames = ApiHelper.getNewsItems(output, "Item", "", "dn");

		subSections.forEach(action -> {
			counter = 0;
			action = baseUrl + action;
			output = ApiHelper.getAPIResponse(action);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Industry-Consumer Products, in section "
					+ subSectionNames.get(subSectionCounter) + " no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>-Industry-Consumer Products page,  section " + subSectionNames.get(subSectionCounter)
							+ " is having duplicate stories, repeating story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				if (VerificationUtil.isListUnique(V).isEmpty()) {
					V.forEach(index -> {
						storyUrls += "<br> " + urls.get(new Integer(index)) + " ";
						String link = urls.get(new Integer(index));
						link = link.startsWith("http") ? link
								: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
						uniqueUrl.add(link);
					});

					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
						softAssert.assertTrue(!flag,
								"<br>- Industry-Cons. products page, has mutipublished stories, repeating story(s)-> "
										+ K + " urls:  " + storyUrls);
					}

				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on Industry-Consumer Products , in section "
								+ subSectionNames.get(subSectionCounter) + " is throwing " + responseCode);
			});
			if (subSectionNames.get(subSectionCounter).equals("Top News")) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Industry-Consumer Products page , in section " + subSectionNames.get(subSectionCounter)
									+ " " + headlines.get(counter) + " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
			subSectionCounter++;
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Industry-Energy section ")
	public void verifyIndustryEnergySection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String energy = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Industry", "nm", "Energy",
				"l3");
		url = baseUrl + energy;
		int allowedstalenessDays = 30;
		output = ApiHelper.getAPIResponse(url);
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionNames = ApiHelper.getNewsItems(output, "Item", "", "dn");

		subSections.forEach(action -> {
			counter = 0;
			action = baseUrl + action;
			output = ApiHelper.getAPIResponse(action);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Industry-Energy page, in section "
					+ subSectionNames.get(subSectionCounter) + " no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>-Industry-Energy page, in section " + subSectionNames.get(subSectionCounter)
							+ " is having duplicate stories, repeating story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				if (VerificationUtil.isListUnique(V).isEmpty()) {
					V.forEach(index -> {
						storyUrls += "<br> " + urls.get(new Integer(index)) + " ";
						String link = urls.get(new Integer(index));
						link = link.startsWith("http") ? link
								: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
						uniqueUrl.add(link);
					});

					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
						softAssert.assertTrue(!flag,
								"<br>-Industry Energy page, has mutipublished stories, repeating story(s)-> " + K
										+ " urls:  " + storyUrls);
					}

				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on Industry-Energy page, in section "
								+ subSectionNames.get(subSectionCounter) + " is throwing " + responseCode);
			});
			if (subSectionNames.get(subSectionCounter).equals("Top News")) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Industry-Energy page , in section " + subSectionNames.get(subSectionCounter) + " "
									+ headlines.get(counter) + " : article is not latest, article date "
									+ (date.trim().endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
			subSectionCounter++;
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Industry-Industrial Goods & Svs section ")
	public void verifyIndustrialGoodsSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String industrialGoods = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Industry", "nm",
				"Industrial Goods & Svs", "l3");
		url = baseUrl + industrialGoods;
		int allowedstalenessDays = 15;
		output = ApiHelper.getAPIResponse(url);
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionNames = ApiHelper.getNewsItems(output, "Item", "", "dn");

		subSections.forEach(action -> {
			counter = 0;
			action = baseUrl + "/" + action;
			output = ApiHelper.getAPIResponse(action);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Industry-Industrial Goods & Svs page, in section "
					+ subSectionNames.get(subSectionCounter) + " no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>-Industry-Industrial Goods & Svs section " + subSectionNames.get(subSectionCounter)
							+ " is having duplicate stories, repeating story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				if (VerificationUtil.isListUnique(V).isEmpty()) {
					V.forEach(index -> {
						storyUrls += "<br> " + urls.get(new Integer(index)) + " ";
						String link = urls.get(new Integer(index));
						link = link.startsWith("http") ? link
								: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
						uniqueUrl.add(link);
					});

					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
						softAssert.assertTrue(!flag,
								"<br>- Industrial Goods SVS page, has mutipublished stories, repeating story(s)-> " + K
										+ " urls:  " + storyUrls);
					}

				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on Industry-Industrial Goods & Svs , in section "
								+ subSectionNames.get(subSectionCounter) + " is throwing " + responseCode);
			});
			if (subSectionNames.get(subSectionCounter).equals("Top News")) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Industry-Industrial Goods & Svs , in section " + subSectionNames.get(subSectionCounter)
									+ " " + headlines.get(counter) + " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
			subSectionCounter++;
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Industry-Healthcare & Biotech section ")
	public void verifyIndustryHealthcareSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String healthcare = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Industry", "nm",
				"Healthcare & Biotech", "l3");
		url = baseUrl + healthcare;
		int allowedstalenessDays = 15;
		output = ApiHelper.getAPIResponse(url);
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionNames = ApiHelper.getNewsItems(output, "Item", "", "dn");

		subSections.forEach(action -> {
			counter = 0;
			action = baseUrl + "/" + action;
			output = ApiHelper.getAPIResponse(action);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Industry-Healthcare & Biotech, in section "
					+ subSectionNames.get(subSectionCounter) + "no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>-Industry-Healthcare & Biotech section " + subSectionNames.get(subSectionCounter)
							+ ", is having duplicate stories, repeating story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(index -> {
					storyUrls += "<br> " + urls.get(new Integer(index)) + " ";
					String link = urls.get(new Integer(index));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>-Industry-Healthcare & Biotech section " + subSectionNames.get(subSectionCounter)
									+ ", has multipublished stories, repeating story(s)->" + K + " urls:  "
									+ storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on Industry-Healthcare & Biotech page, in section "
								+ subSectionNames.get(subSectionCounter) + " is throwing " + responseCode);
			});
			if (subSectionNames.get(subSectionCounter).equals("Top News")) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");
				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Industry-Healthcare & Biotech page, in section "
									+ subSectionNames.get(subSectionCounter) + headlines.get(counter)
									+ " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
			subSectionCounter++;
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Industry-Services section ")
	public void verifyIndustryServicesSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String services = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Industry", "nm", "Services",
				"l3");
		url = baseUrl + services;
		int allowedstalenessDays = 30;
		output = ApiHelper.getAPIResponse(url);
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionNames = ApiHelper.getNewsItems(output, "Item", "", "dn");

		subSections.forEach(action -> {
			counter = 0;
			action = baseUrl + "/" + action;
			output = ApiHelper.getAPIResponse(action);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Industry-Services page, in section "
					+ subSectionNames.get(subSectionCounter) + " no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>-Industry-Services page, in section " + subSectionNames.get(subSectionCounter)
							+ "duplicate stories are shown, repeating story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(index -> {
					storyUrls += "<br> " + urls.get(new Integer(index)) + " ";
					String link = urls.get(new Integer(index));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>-Industry-Services page, in section " + subSectionNames.get(subSectionCounter)
									+ "duplicate stories are shown, multipublished story(s)->" + K + " urls:  "
									+ storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on Industry-Services page, in section "
								+ subSectionNames.get(subSectionCounter) + " is throwing " + responseCode);
			});
			if (subSectionNames.get(subSectionCounter).equals("Top News")) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");
				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Industry-Services page , in section " + subSectionNames.get(subSectionCounter) + " "
									+ headlines.get(counter) + " is not latest, article date is " + date);
					counter++;
				});
			}
			subSectionCounter++;
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Industry-Media & Entertainment section ")
	public void verifyIndMediaEntertainmentSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String mediaEntertainment = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Industry", "nm",
				"Media & Entertainment", "l3");
		url = baseUrl + mediaEntertainment;
		output = ApiHelper.getAPIResponse(url);
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionNames = ApiHelper.getNewsItems(output, "Item", "", "dn");

		subSections.forEach(action -> {
			counter = 0;
			action = baseUrl + "/" + action;
			output = ApiHelper.getAPIResponse(action);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Industry-Media & Entertainment page, in section "
					+ subSectionNames.get(subSectionCounter) + " no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>-Industry-Media & Entertainment page, in section " + subSectionNames.get(subSectionCounter)
							+ " is having duplicate stories, repeating story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(index -> {
					storyUrls += "<br> " + urls.get(new Integer(index)) + " ";
					String link = urls.get(new Integer(index));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag, "<br>-Industry-Media & Entertainment page, in section "
							+ subSectionNames.get(subSectionCounter)
							+ " is having multipublished stories, repeating story(s)->" + K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on Industry-Media & Entertainment , in section "
								+ subSectionNames.get(subSectionCounter) + " is throwing " + responseCode);
			});
			subSectionCounter++;
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Industry-Transportation section ")
	public void verifyIndustryTransportationSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String transportation = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Industry", "nm",
				"Transportation", "l3");
		url = baseUrl + transportation;
		int allowedstalenessDays = 10;
		output = ApiHelper.getAPIResponse(url);
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionNames = ApiHelper.getNewsItems(output, "Item", "", "dn");

		subSections.forEach(action -> {
			counter = 0;
			action = baseUrl + "/" + action;
			output = ApiHelper.getAPIResponse(action);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Industry-Transportation page, in section "
					+ subSectionNames.get(subSectionCounter) + " no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>-Industry-Transportation page, in section " + subSectionNames.get(subSectionCounter)
							+ " is having duplicate stories, repeating story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(index -> {
					storyUrls += "<br> " + urls.get(new Integer(index)) + " ";
					String link = urls.get(new Integer(index));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>-Industry-Transportation page, in section " + subSectionNames.get(subSectionCounter)
									+ " is having duplicate stories, multipublished story(s)->" + K + " urls:  "
									+ storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on Industry-Transportation , in section "
								+ subSectionNames.get(subSectionCounter) + " is throwing " + responseCode);
			});
			if (subSectionNames.get(subSectionCounter).equals("Top News")) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Industry-Transportation page , in section " + subSectionNames.get(subSectionCounter)
									+ headlines.get(counter) + " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
			subSectionCounter++;
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Industry-Telecom section ")
	public void verifyIndusTelecomSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String telecom = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Industry", "nm", "Telecom",
				"l3");
		url = baseUrl + telecom;
		int allowedstalenessDays = 3;
		output = ApiHelper.getAPIResponse(url);
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionNames = ApiHelper.getNewsItems(output, "Item", "", "dn");

		subSections.forEach(action -> {
			counter = 0;
			action = baseUrl + "/" + action;
			action = action.replaceAll("[\\s]+", "%20");
			output = ApiHelper.getAPIResponse(action);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Industry-Telecom page, in section "
					+ subSectionNames.get(subSectionCounter) + " no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>-Industry-Telecom page, in section " + subSectionNames.get(subSectionCounter)
							+ " is having duplicate stories, repeating story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(index -> {
					storyUrls += "<br> " + urls.get(new Integer(index)) + " ";
					String link = urls.get(new Integer(index));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>-Industry-Telecom page, in section " + subSectionNames.get(subSectionCounter)
									+ " is having multipublished stories, repeating story(s)->" + K + " urls:  "
									+ storyUrls);
				}
			});

			if (subSectionNames.get(subSectionCounter).equals("Top News")) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Industry-Telecom page , in section " + subSectionNames.get(subSectionCounter)
									+ headlines.get(counter) + " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
			subSectionCounter++;
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Industry-Education section ")
	public void verifyIndusEducationSection() {
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String education = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Industry", "nm", "Education",
				"du");
		url = baseUrl + education;
		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Industry-Education page no stories are shown, api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>-Industry-Education page, in section is having duplicate stories, repeating story(s)->"
						+ topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});
			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				softAssert.assertTrue(!flag,
						"<br>-Industry-Education page, in section is having multipublished stories, repeating story(s)->"
								+ K + " urls:  " + storyUrls);
			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
					+ ">Story</a> on Industry-Education , in section  is throwing " + responseCode);
		});

		softAssert.assertAll();
	}

	/********************** Markets ********************************/
	@Test(description = "This test verifies articles under markets top news", groups = { "topmonitor" })
	private void verifyMarketTopNews() {
		DateTime dateTime = new DateTime();
		int allowedstalenessDays = ((dateTime.dayOfWeek().getAsText().equalsIgnoreCase("Sunday")) ? 2
				: (((dateTime.dayOfWeek().getAsText().equalsIgnoreCase("Saturday") ? 1 : 1))));
		softAssert = new SoftAssert();
		String url = String.format(AppFeeds.marketNewsFeed, appVersion, platform);
		output = ApiHelper.getAPIResponse(url);

		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			counter = 0;
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Markets page in Top News section on page no. " + i + " no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(), "<br>- On Markets page in Top News section on page no. " + i
					+ " , is having duplicate stories, repeating story(s)->" + topNewsDup);

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Markets page in Top News section, is throwing " + responseCode);
			});
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Markets page in Top News section , has multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			if (i == 1) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");
				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Markets Page Top News section " + headlines.get(counter)
									+ " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
		}

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Markets-Stocks section ")
	public void verifyMarketsStocksSection() {
		subSectionCounter = 0;
		DateTime dateTime = new DateTime();
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String stocks = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Markets", "nm", "Stocks", "l3");
		url = baseUrl + stocks;
		int allowedstalenessDays = ((dateTime.dayOfWeek().getAsText().equalsIgnoreCase("Sunday")) ? 2
				: (((dateTime.dayOfWeek().getAsText().equalsIgnoreCase("Saturday") ? 1 : 0))));
		output = ApiHelper.getAPIResponse(url);
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionNames = ApiHelper.getNewsItems(output, "Item", "", "dn");

		subSections.forEach(action -> {
			counter = 0;
			action = baseUrl + action;
			output = ApiHelper.getAPIResponse(action);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On Markets-Stocks page, in section "
					+ subSectionNames.get(subSectionCounter) + " no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>-Markets-Stocks page, in section " + subSectionNames.get(subSectionCounter)
							+ " is having duplicating stories, repeating story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(index -> {
					storyUrls += "<br> " + urls.get(new Integer(index)) + " ";
					String link = urls.get(new Integer(index));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);

				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>-Markets-Stocks page, in section " + subSectionNames.get(subSectionCounter)
									+ " is having multipublished stories, repeating story(s)->" + K + " urls:  "
									+ storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on Markets-Stocks , in section "
								+ subSectionNames.get(subSectionCounter) + " is throwing " + responseCode);
			});
			if (subSectionNames.get(subSectionCounter).equals("Top News")) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");

				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Markets-Stocks page , in section " + subSectionNames.get(subSectionCounter)
									+ headlines.get(counter) + " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
			subSectionCounter++;
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Markets-IPOs & FPOs section ")
	public void verifyMarketsIPOsFPOsSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String iPOsFPOs = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Markets", "nm", "IPOs & FPOs",
				"du");
		url = baseUrl + iPOsFPOs;
		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0,
				"On Markets-IPOs & FPOs page, in Top News no stories are shown, api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(), "<br>-Markets-IPOs & FPOs page, in section Top News"
				+ " is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});
			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				softAssert.assertTrue(!flag, "<br>-Markets-IPOs & FPOs page, in section Top News"
						+ " is having multipublished stories, repeating story(s)->" + K + " urls:  " + storyUrls);
			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
					+ ">Story</a> on Markets-IPOs & FPOs , in Top News is throwing " + responseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Markets-Commodities section ")
	public void verifyMarketsCommoditiesSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String commodities = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Markets", "nm",
				"Commodities", "du");
		url = baseUrl + commodities;
		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0,
				"On Markets-Commodities page, in Top News no stories are shown, api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(), "" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);

			});
			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				softAssert.assertTrue(!flag, "<br>-Markets-Commodities page, in section Top News"
						+ " is having multipublished stories, repeating story(s)->" + K + " urls:  " + storyUrls);
			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
					+ ">Story</a> on Markets-Commodities, in Top News is throwing " + responseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Markets-Forex section ")
	public void verifymraketsForexSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String forex = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Markets", "nm", "Forex", "du");
		url = baseUrl + forex;
		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0,
				"On Markets-Forex page, in Top News no stories are shown, api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(), "<br>-Markets-Forex page, in section Top News"
				+ " is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});
			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				softAssert.assertTrue(!flag, "<br>-Markets-Forex page, in section Top News"
						+ " is having multipublished stories, repeating story(s)->" + K + " urls:  " + storyUrls);
			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Markets-Forex, in Top News is throwing " + responseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Markets-Bonds section ")
	public void verifyMarketsBondsSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String bonds = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Markets", "nm", "Bonds", "du");
		url = baseUrl + bonds;
		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0,
				"On Markets-Bonds page, in Top News no stories are shown, api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(), "<br>-Markets-Bonds page, in section Top News"
				+ " is having duplicate stories, repeating story(s)->" + topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});

			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				softAssert.assertTrue(!flag, "<br>-Markets-Bonds page, in section Top News"
						+ " is having multipublished stories, repeating story(s)->" + K + " urls:  " + storyUrls);
			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Markets-Bonds, in Top News is throwing " + responseCode);
		});
		softAssert.assertAll();
	}
	
	
	@Test(description = "This test verifies Markets-Expert View section ")
	public void verifyMarketsExpertViewSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String view = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Markets", "nm", "Expert View", "du");
		url = baseUrl + view;
		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0,
				"On Markets- Expert View page no stories are shown, api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> viewNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(viewNewsDup.isEmpty(), "<br>-On Markets page, in section Expert View"
				+ " is having duplicate stories, repeating story(s)->" + viewNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});

			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				softAssert.assertTrue(!flag, "<br>-On Markets page, in section Expert View"
						+ " is having multipublished stories, repeating story(s)->" + K + " urls:  " + storyUrls);
			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Markets-Expert View is throwing " + responseCode);
		});
		softAssert.assertAll();
	}


	/********************** Rise ********************************/
	@Test(description = "This test verifies articles under Rise-Top news", groups = { "topmonitor" })
	private void verifyRiseTopNews() {

		int allowedstalenessDays = 30;
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String topNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "RISE", "nm", "Top News", "du");
		String url = baseUrl + topNews;
		output = ApiHelper.getAPIResponse(url);

		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 3 ? "3" : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			counter = 0;
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Rise page in Top News section on page no. " + i + " no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(), "<br>- On Rise page in Top News section on page no. " + i
					+ " , is having duplicate stories, repeating story(s)->" + topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Rise page in Top News section , has multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Rise page in Top News section, is throwing " + responseCode);
			});
			if (i == 1) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");
				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Rise Page Top News section " + headlines.get(counter)
									+ " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Rise-SME section ")
	private void verifyRiseSME() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String sme = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "RISE", "nm", "SME", "du");
		String url = baseUrl + sme;
		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Rise page in SME section no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> SMEDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(SMEDup.isEmpty(),
				"<br>- On Rise page in SME section, is having duplicate stories, repeating story(s)->" + SMEDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});
			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				softAssert.assertTrue(!flag,
						"<br>- On Rise page in SME section, is having multipublished stories, multipublished story(s)->"
								+ K + " urls:  " + storyUrls);
			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Rise page in SME section, is throwing " + responseCode);
		});

		softAssert.assertAll();
	}

	

	@Test(description = "This test verifies articles under Rise-Policy section ")
	private void verifyRisePolicy() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String policy = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "RISE", "nm", "Policy", "du");
		String url = baseUrl + policy;
		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Rise page in Policy section no stories are shown, api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> riseDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(riseDup.isEmpty(),
				"<br>- On Rise page in Policy section, is having duplicate stories, repeating story(s)->" + riseDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});
			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				softAssert.assertTrue(!flag,
						"<br>- On Rise page in Policy section, is having multipublished stories, repeating story(s)->"
								+ K + " urls:  " + storyUrls);
			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Rise page in Policy section, is throwing " + responseCode);
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Rise-Entreprenuership section ")
	private void verifyRiseEntreprenuership() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String entreprenuership = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "RISE", "nm",
				"Entreprenuership", "du");
		String url = baseUrl + entreprenuership;
		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0,
				"On Rise page in Entreprenuership section no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> entreprenuershipDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(entreprenuershipDup.isEmpty(),
				"<br>- On Rise page in Entreprenuership section, is having duplicate stories, repeating story(s)->"
						+ entreprenuershipDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});
			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				softAssert.assertTrue(!flag,
						"<br>- On Rise page in Entreprenuership section, is having multipublished stories, repeating story(s)->"
								+ K + " urls:  " + storyUrls);
			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
					+ ">Story</a> on Rise page in Entreprenuership section, is throwing " + responseCode);
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Rise-Money section ")
	private void verifyRiseMoney() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String money = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "RISE", "nm", "Money", "du");
		String url = baseUrl + money;
		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Rise page in Money section no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> moneyDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(moneyDup.isEmpty(),
				"<br>- On Rise page in Money section, is having duplicate stories, repeating story(s)->" + moneyDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});
			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				softAssert.assertTrue(!flag,
						"<br>- On Rise page in Money section, is having multipublished stories, repeating story(s)->"
								+ K + " urls:  " + storyUrls);
			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Rise page in Money section, is throwing " + responseCode);
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Rise-Security Tech section ")
	public void verifyRiseSecurityTechSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String securityTech = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "RISE", "dn",
				"Security-Tech", "l3");
		url = baseUrl + securityTech;
		output = ApiHelper.getAPIResponse(url);
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionNames = ApiHelper.getNewsItems(output, "Item", "", "dn");

		subSections.forEach(action -> {
			counter = 0;
			action = baseUrl + action;
			output = ApiHelper.getAPIResponse(action);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On SME-Security-Tech page, in section "
					+ subSectionNames.get(subSectionCounter) + " no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> securityTechDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(securityTechDup.isEmpty(),
					"<br>-SME-Security-Tech page , in section " + subSectionNames.get(subSectionCounter)
							+ " is having duplicate stories, repeating story(s)->" + securityTechDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(index -> {
					storyUrls += "<br> " + urls.get(new Integer(index)) + " ";
					String link = urls.get(new Integer(index));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>-SME-Security-Tech page , in section " + subSectionNames.get(subSectionCounter)
									+ " is having multipublished stories, repeating story(s)->" + K + " urls:  "
									+ storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on SME-Security-Tech page , in section "
								+ subSectionNames.get(subSectionCounter) + " is throwing " + responseCode);
			});
			subSectionCounter++;
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Rise-Marketing section ")
	public void verifyRiseMarketing() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String marketing = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "RISE", "dn", "Marketing",
				"l3");
		url = baseUrl + marketing;
		output = ApiHelper.getAPIResponse(url);
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionNames = ApiHelper.getNewsItems(output, "Item", "", "dn");

		subSections.forEach(action -> {
			counter = 0;
			action = baseUrl + action;
			output = ApiHelper.getAPIResponse(action);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On SME-Marketing page, in section "
					+ subSectionNames.get(subSectionCounter) + " no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> marketingDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(marketingDup.isEmpty(),
					"<br>-SME-Marketing page , in section " + subSectionNames.get(subSectionCounter)
							+ " is having duplicate stories, repeating story(s)->" + marketingDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(index -> {
					storyUrls += "<br> " + urls.get(new Integer(index)) + " ";
					String link = urls.get(new Integer(index));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>-SME-Marketing page , in section " + subSectionNames.get(subSectionCounter)
									+ " is having multipublished stories, repeating story(s)->" + K + " urls:  "
									+ storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on SME-Marketing page , in section "
								+ subSectionNames.get(subSectionCounter) + " is throwing " + responseCode);
			});
			subSectionCounter++;
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies Rise-HR section ")
	public void verifyRiseHRSection() {
		subSectionCounter = 0;
		softAssert = new SoftAssert();
		String url;
		output = lhsFeedOutput;
		String hr = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "RISE", "nm", "HR", "l3");
		url = baseUrl + hr;
		output = ApiHelper.getAPIResponse(url);
		List<String> subSections = ApiHelper.getNewsItems(output, "Item", "", "du");
		List<String> subSectionNames = ApiHelper.getNewsItems(output, "Item", "", "dn");

		subSections.forEach(action -> {
			counter = 0;
			action = baseUrl + action;
			output = ApiHelper.getAPIResponse(action);

			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On SME-HR page, in section "
					+ subSectionNames.get(subSectionCounter) + " no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> hrDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(hrDup.isEmpty(),
					"<br>-SME-HR page , in section " + subSectionNames.get(subSectionCounter)
							+ " is having duplicate stories, repeating story(s)->" + hrDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(index -> {
					storyUrls += "<br> " + urls.get(new Integer(index)) + " ";
					String link = urls.get(new Integer(index));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>-SME-HR page , in section " + subSectionNames.get(subSectionCounter)
									+ " is having multipublished stories, repeating story(s)->" + K + " urls:  "
									+ storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on SME-HR page , in section "
								+ subSectionNames.get(subSectionCounter) + " is throwing " + responseCode);
			});
			subSectionCounter++;
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Rise-Legal section ")
	private void verifyRiseLegal() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String legal = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "RISE", "dn", "Legal", "du");
		String url = baseUrl + legal;
		output = ApiHelper.getAPIResponse(url);

		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Rise page in Legal section no stories are shown, api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> legalDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(legalDup.isEmpty(),
				"<br>- On Rise page in Legal section, is having duplicate stories, repeating story(s)->" + legalDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});
			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				softAssert.assertTrue(!flag,
						"<br>- On Rise page in Legal section, is having multipublished stories, repeating story(s)->"
								+ K + " urls:  " + storyUrls);
			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Rise page in Legal section, is throwing " + responseCode);
		});

		softAssert.assertAll();
	}
	
	
	@Test(description = "This test verifies articles under Rise-GST section ")
	private void verifyRiseGST() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String gst = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "RISE", "nm", "GST", "du");
		String url = baseUrl + gst;
		output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0, "On Rise page in GST section no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> gstDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(gstDup.isEmpty(),
				"<br>- On Rise page in GST section, is having duplicate stories, repeating story(s)->" + gstDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});
			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				softAssert.assertTrue(!flag,
						"<br>- On Rise page in GST section, is having multipublished stories, multipublished story(s)->"
								+ K + " urls:  " + storyUrls);
			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + link + ">Story</a> on Rise page in GST section, is throwing " + responseCode);
		});

		softAssert.assertAll();
	}

	/********************** Politics ********************************/
	@Test(description = "This test verifies articles under HP-Politics tab")
	private void verifyPoliticsNews() {
		int allowedstalenessDays = 2;
		softAssert = new SoftAssert();
		output = tabsFeedOutput;
		String news = ApiHelper.getChildValueInheritTree(output, "Item", "", "nm", "Politics", "", "", "du");
		String url = baseUrl + news;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Politics page in News section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> newsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(newsDup.isEmpty(),
					"<br>- On Politics page in News section, is having duplicate stories, repeating story(s)->"
							+ newsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Politics page in News section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Politics page in News section, is throwing " + responseCode);
			});
			if (i == 1) {
				counter = 0;
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");
				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Politics Page Top News section " + headlines.get(counter)
									+ " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}

		}
		softAssert.assertAll();
	}

	/********************** Wealth ********************************/
	@Test(description = "This test verifies articles under Wealth-Top News section ", groups = { "topmonitor" })
	private void verifyWealthTopNews() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		int allowedstalenessDays = 5;
		String topNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Wealth", "dn", "Top News",
				"du");
		String url = baseUrl + topNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			counter = 0;
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			if (i > 3)
				continue;
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Wealth page in Top News section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Wealth page in Top News section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>-On Wealth page in Top News section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Wealth page in Top News section, is throwing " + responseCode);
			});
			if (i == 1) {
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");
				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Wealth Page Top News section " + headlines.get(counter)
									+ " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies articles under Wealth-Tax section ")
	private void verifyWealthTax() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String tax = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Wealth", "dn", "Tax", "du");
		String url = baseUrl + tax;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Wealth page in Tax section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Wealth page in Tax section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Wealth page in Tax section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Wealth page in Tax section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies articles under Wealth-Save section ")
	private void verifyWealthSave() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String save = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Wealth", "dn", "Save", "du");
		String url = baseUrl + save;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Wealth page in Save section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Wealth page in Save section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});

				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Wealth page in Save section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Wealth page in Save section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies articles under Wealth-Invest section ")
	private void verifyWealthInvest() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String invest = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Wealth", "dn", "Invest", "du");
		String url = baseUrl + invest;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Wealth page in Invest section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Wealth page in Invest section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Wealth page in Invest section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Wealth page in Invest section on page , is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Wealth-Insure section ")
	private void verifyWealthInsure() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String insure = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Wealth", "dn", "Invest", "du");
		String url = baseUrl + insure;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Wealth page in Invest section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Wealth page in Invest section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Wealth page in Invest section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Wealth page in Invest section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Wealth-Spend section ")
	private void verifyWealthSpend() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String spend = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Wealth", "dn", "Spend", "du");
		String url = baseUrl + spend;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Wealth page in Spend section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Wealth page in Spend section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Wealth page in Spend section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Wealth page in Spend section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Wealth-Borrow section ")
	private void verifyWealthBorrow() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String borrow = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Wealth", "dn", "Borrow", "du");
		String url = baseUrl + borrow;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Wealth page in Borrow section no stories are shown, api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Wealth page in Borrow section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Wealth page in Borrow section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Wealth page in Borrow section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Wealth-Earn section ")
	private void verifyWealthEarn() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String earn = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Wealth", "dn", "Earn", "du");
		String url = baseUrl + earn;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Wealth page in Earn section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Wealth page in Earn section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Wealth page in Earn section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Wealth page in Earn section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Wealth-Plan section ")
	private void verifyWealtPlan() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String plan = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Wealth", "dn", "Plan", "du");
		String url = baseUrl + plan;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Wealth page in Plan section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Wealth page in Plan section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Wealth page in Plan section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Wealth page in Plan section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Wealth-Real Estate section ")
	private void verifyWealtRealEstate() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String realEstate = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Wealth", "dn",
				"Real Estate", "du");
		String url = baseUrl + realEstate;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Wealth page in Real Estate section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Wealth page in Real Estate section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Wealth page in Real Estate section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Wealth page in Real Estate section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Wealth-Mutual Funds section ")
	private void verifyWealtMutualFunds() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String mutualFunds = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Wealth", "dn",
				"Mutual Funds", "du");
		String url = baseUrl + mutualFunds;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Wealth page in Mutual Funds section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Wealth page in Mutual Funds section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Wealth page in Mutual Funds section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Wealth page in Mutual Funds section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Wealth-Personal Finance News section ")
	private void verifyWealtPersonalFinanceNews() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String personalFinance = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Wealth", "dn",
				"Personal Finance News", "du");
		String url = baseUrl + personalFinance;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Wealth page in Personal Finance News section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Wealth page in Personal Finance News section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Wealth page in Personal Finance News section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Wealth page in Personal Finance News section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	/********************** MF ********************************/
	@Test(description = "This test verifies articles under Mutual Funds-Top News section ", groups = { "topmonitor" })
	private void verifyMutualFundsTopNews() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		int allowedstalenessDays = 30;
		String topNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Mutual Funds", "dn",
				"Top News", "du");
		String url = baseUrl + topNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Mutual Funds page in Top News section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Mutual Funds page in Top News section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Mutual Funds page in Top News section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Mutual Funds page in Top News section, is throwing " + responseCode);
			});
			if (i == 1) {
				counter = 0;
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");
				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Mutual Funds Page Top News section " + headlines.get(counter)
									+ " : article is not latest, article date " + (date.endsWith("0000")
											? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies articles under Mutual Funds-Learn section ")
	private void verifyMutualFundsLearn() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String learn = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Mutual Funds", "dn", "Learn",
				"du");
		String url = baseUrl + learn;
		output = ApiHelper.getAPIResponse(url);
		// List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "",
		// "tp");
		// String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		// output = ApiHelper.getAPIResponse(url);
		headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
		Assert.assertTrue(headlines.size() > 0,
				"On Mutual Funds page in Learn section no stories are shown,api url " + url);
		urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
		List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
		urls.removeAll(temp);
		// Headlines duplication verification
		List<String> topNewsDup = VerificationUtil.isListUnique(urls);
		List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
		Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>- On Mutual Funds page in Learn section, is having duplicate stories, repeating story(s)->"
						+ topNewsDup);
		indexedDup.forEach((K, V) -> {
			storyUrls = "";
			List<String> uniqueUrl = new LinkedList<>();
			V.forEach(action -> {
				storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
				String link = urls.get(new Integer(action));
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				uniqueUrl.add(link);
			});
			if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
				Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true : false;
				softAssert.assertTrue(!flag,
						"<br>- On Mutual Funds page in Learn section, is having multipublished stories, repeating story(s)->"
								+ K + " urls:  " + storyUrls);
			}
		});

		// urls verification
		urls.forEach(link -> {
			link = link.replaceAll("\\?utm_source.*", "");
			link = link.startsWith("http") ? link
					: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
			int responseCode = HTTPResponse.checkResponseCode(link);
			softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
					+ ">Story</a> on Mutual Funds page in Learn section, is throwing " + responseCode);
		});

		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Mutual Funds-Analysis section ")
	private void verifyMutualFundsAnalysis() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String analysis = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Mutual Funds", "dn",
				"Analysis", "du");
		String url = baseUrl + analysis;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Mutual Funds page in Analysis section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Mutual Funds page in Analysis section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Mutual Funds page in Analysis section, is having multipublished stories, repeating story(s)-> "
									+ K + " urls:  " + storyUrls);
				}
			});
			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Mutual Funds page in Analysis section, is having multipublished stories, repeating story(s)->"
							+ topNewsDup);
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Mutual Funds page in Analysis section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Mutual Funds-MF News section ")
	private void verifyMutualFundsMFNews() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String mf = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Mutual Funds", "dn", "MF News",
				"du");
		String url = baseUrl + mf;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Mutual Funds page in MF News section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			topNewsDup.addAll(VerificationUtil.isListUnique(headlines));
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Mutual Funds page in MF News section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Mutual Funds page in MF News section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);

				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Mutual Funds page in MF News section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}
	
	

	/********************** Panache ********************************/
	@Test(description = "This test verifies articles under Panache- Top News section " , groups = { "topmonitor" })
	private void verifyPanacheTopNews() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		int allowedstalenessDays = 30;
		String topNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Panache", "dn",
				"Panache Top News", "du");
		String url = baseUrl + topNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Panache page in  Top News section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Panache page in  Top News section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Panache page in  Top News section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Panache page in  Top News section, is throwing " + responseCode);
			});
			if (i == 1) {
				counter = 0;
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");
				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Panache page in  Top News section " + headlines.get(counter)
									+ " : article is not latest, article date " + (date.endsWith("0000")
											? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Panache- Tech and Gadgets section ")
	private void verifyPanacheTechandGadgets() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String techNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Panache", "dn",
				"Tech and Gadgets", "du");
		String url = baseUrl + techNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Panache page in  Tech and Gadgets section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> techNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(techNewsDup.isEmpty(),
					"<br>- On Panache page in  Tech and Gadgets section, is having duplicate stories, repeating story(s)->"
							+ techNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Panache page in  Tech and Gadgets section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Panache page in  Tech and Gadgets section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Panache- Worklife section ")
	private void verifyPanacheWorklife() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String worklifeNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Panache", "dn",
				"Worklife", "du");
		String url = baseUrl + worklifeNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Panache page in  Worklife section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> worklifeNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(worklifeNewsDup.isEmpty(),
					"<br>- On Panache page in  Worklife section, is having duplicate stories, repeating story(s)->"
							+ worklifeNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Panache page in  Worklife section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Panache page in  Worklife section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();

	}
	
	
	@Test(description = "This test verifies articles under Panache- Cars & Bikes section ")
	private void verifyPanacheCarsBikes() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String carsbikesNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Panache", "dn",
				"Cars & Bikes", "du");
		String url = baseUrl + carsbikesNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Panache page in  Cars & Bikes section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> carsbikesNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(carsbikesNewsDup.isEmpty(),
					"<br>- On Panache page in  Cars & Bikes section, is having duplicate stories, repeating story(s)->"
							+ carsbikesNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Panache page in  Cars & Bikes section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Panache page in  Cars & Bikes section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	
	@Test(description = "This test verifies articles under Panache- Lifestyle section ")
	private void verifyPanacheLifestyle() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String lifestyleNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Panache", "dn",
				"Lifestyle", "du");
		String url = baseUrl + lifestyleNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Panache page in  Lifestyle section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> lifestyleNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(lifestyleNewsDup.isEmpty(),
					"<br>- On Panache page in  Lifestyle section, is having duplicate stories, repeating story(s)->"
							+ lifestyleNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Panache page in  Lifestyle section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Panache page in  Lifestyle section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Panache- Food & Drinks section ")
	private void verifyPanacheFoodDrinks() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String foodNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Panache", "dn",
				"Food & Drinks", "du");
		String url = baseUrl + foodNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Panache page in  Food & Drinks section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> foodNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(foodNewsDup.isEmpty(),
					"<br>- On Panache page in  Food & Drinks section, is having duplicate stories, repeating story(s)->"
							+ foodNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Panache page in  Food & Drinks section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Panache page in  Food & Drinks section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	
	@Test(description = "This test verifies articles under Panache- Health section ")
	private void verifyPanacheHealth() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String healthNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Panache", "dn",
				"Healths", "du");
		String url = baseUrl + healthNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Panache page in  Health section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> healthNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(healthNewsDup.isEmpty(),
					"<br>- On Panache page in  Health section, is having duplicate stories, repeating story(s)->"
							+ healthNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Panache page in  Health section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Panache page in  Health section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Panache- People section ")
	private void verifyPanachePeople() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String peopleNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Panache", "dn",
				"People", "du");
		String url = baseUrl + peopleNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Panache page in  People section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> peopleNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(peopleNewsDup.isEmpty(),
					"<br>- On Panache page in  People section, is having duplicate stories, repeating story(s)->"
							+ peopleNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Panache page in  People section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Panache page in  People section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Panache- Entertainment section ")
	private void verifyPanacheEntertainment() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String entertainmentNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Panache", "dn",
				"Entertainment", "du");
		String url = baseUrl + entertainmentNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Panache page in  Entertainment section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> entertainmentNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(entertainmentNewsDup.isEmpty(),
					"<br>- On Panache page in  Entertainment section, is having duplicate stories, repeating story(s)->"
							+ entertainmentNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Panache page in  Entertainment section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Panache page in  Entertainment section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	
	@Test(description = "This test verifies articles under Panache- Books section ")
	private void verifyPanacheBooks() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String booksNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Panache", "dn",
				"Books", "du");
		String url = baseUrl + booksNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Panache page in  Books section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> booksNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(booksNewsDup.isEmpty(),
					"<br>- On Panache page in  Books section, is having duplicate stories, repeating story(s)->"
							+ booksNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Panache page in  Books section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Panache page in  Books section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	
	@Test(description = "This test verifies articles under Panache- City Life section ")
	private void verifyPanacheCityLife() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String cityNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Panache", "dn",
				"City Life", "du");
		String url = baseUrl + cityNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Panache page in  City Life section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> cityNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(cityNewsDup.isEmpty(),
					"<br>- On Panache page in  City Life section, is having duplicate stories, repeating story(s)->"
							+ cityNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Panache page in  City Life section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Panache page in  City Life section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Panache- Humour section ")
	private void verifyPanacheHumour() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String humourNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Panache", "dn",
				"Humour", "du");
		String url = baseUrl + humourNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Panache page in  Humour section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> humourNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(humourNewsDup.isEmpty(),
					"<br>- On Panache page in  Humour section, is having duplicate stories, repeating story(s)->"
							+ humourNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Panache page in  Humour section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Panache page in  Humour section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	
	@Test(description = "This test verifies articles under Panache- Between The Lines section ")
	private void verifyPanacheBetweenTheLines() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String linesNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Panache", "dn",
				"Between The Lines", "du");
		String url = baseUrl + linesNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Panache page in  Between The Lines section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> linesNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(linesNewsDup.isEmpty(),
					"<br>- On Panache page in  Between The Lines section, is having duplicate stories, repeating story(s)->"
							+ linesNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Panache page in  Between The Lines section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Panache page in  Between The Lines section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	
	
	@Test(description = "This test verifies articles under Panache- More Magazines section ")
	private void verifyPanacheMoreMagazines() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String maganizesNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Panache", "dn",
				"More Magazines", "du");
		String url = baseUrl + maganizesNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Panache page in  More Magazines section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> maganizesNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(maganizesNewsDup.isEmpty(),
					"<br>- On Panache page in  More Magazines section, is having duplicate stories, repeating story(s)->"
							+ maganizesNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Panache page in  More Magazines section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Panache page in  More Magazines section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();
}
	
	
   /*********************************NRI*********************************/
	
	@Test(description = "This test verifies articles under NRI-Top News section " , groups = { "topmonitor" })
	private void verifyNRITopNews() {
		softAssert = new SoftAssert(); 
		output = lhsFeedOutput;
		int allowedstalenessDays = 360;
		String topnews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "NRI", "dn", "Top News", "du");
		String url = baseUrl + topnews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On NRI page in Top News section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topnewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topnewsDup.isEmpty(),
					"<br>- On NRI page in Top News section, is having duplicate stories, repeating story(s)->"
							+ topnewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On NRI page in Top News section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on NRI page in Top News section, is throwing " + responseCode);
			});

			if (i == 1) {
				counter = 0;
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");
				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On NRI Page Top News section " + headlines.get(counter)
									+ " : article is not latest, article date "
									+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
		}
		softAssert.assertAll();

	}

	
	@Test(description = "This test verifies articles under NRI- Migrate section ")
	private void verifyNRIMigrate() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String topnews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "NRI", "dn", "Migrate",
				"du");
		String url = baseUrl + topnews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On NRI page in  Migrate section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> newsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(newsDup.isEmpty(),
					"<br>- On NRI page in  Migrate section, is having duplicate stories, repeating story(s)->" + newsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On NRI page in Migrate section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on NRI Page Migrate section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under NRI- Work section ")
	private void verifyNRIWork() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String topnews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "NRI", "dn", "Work",
				"du");
		String url = baseUrl + topnews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0, "On NRI page in  Work section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> newsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(newsDup.isEmpty(),
					"<br>- On NRI page in  Work section, is having duplicate stories, repeating story(s)->" + newsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On NRI page in Work section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on NRI Page Work section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}


	
	@Test(description = "This test verifies articles under NRI- Study section ")
	private void verifyNRIStudy() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String realEstatenews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "NRI", "dn",
				"Study", "du");
		String url = baseUrl + realEstatenews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On NRI page in  Study section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> realEstatenewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(realEstatenewsDup.isEmpty(),
					"<br>- On NRI page in  Study section, is having duplicate stories, repeating story(s)->"
							+ realEstatenewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On NRI page in Study section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on NRI page in Study section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies articles under NRI- Invest section ")
	private void verifyNRIInvest() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String investmentsnews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "NRI", "dn",
				"Invest", "du");
		String url = baseUrl + investmentsnews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On NRI page in Invest section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> investmentsnewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(investmentsnewsDup.isEmpty(),
					"<br>- On NRI page in Invest section, is having duplicate stories, repeating story(s)->"
							+ investmentsnewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On NRI page in Invest section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on NRI page in Invest section, is throwing " + responseCode);
			});

			
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies articles under NRI- Visit section ")
	private void verifyNRIVisit() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String taxnews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "NRI", "dn", "Visit", "du");
		String url = baseUrl + taxnews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On NRI page in Visit section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> taxnewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(taxnewsDup.isEmpty(),
					"<br>- On NRI page in Visit section, is having duplicate stories, repeating story(s)->"
							+ taxnewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On NRI page in Visit section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200,
						"<br>- <a href=" + link + ">Story</a> on NRI page in Visit section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}

	
	/***********************Opinion*****************************/
	
	@Test(description = "This test verifies articles under Opinion- Top News section " , groups = { "topmonitor" })
	private void verifyOpinionTopNew() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		int allowedstalenessDays = 120;
		String topNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Opinion", "dn",
				"Top News", "du");
		String url = baseUrl + topNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Opinion page in   Top News section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Opinion page in   Top News section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Opinion page in   Top News section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Opinion page in   Top News section, is throwing " + responseCode);
			});
			if (i == 1) {
				counter = 0;
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");
				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Opinion page in   Top News section  " + headlines.get(counter)
									+ " : article is not latest, article date " + (date.endsWith("0000")
											? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
		}
		softAssert.assertAll();

	}
	
	
	@Test(description = "This test verifies articles under Opinion- ET View section ")
	private void verifyOpinionETView() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String etViewNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Opinion", "dn",
				"ET View", "du");
		String url = baseUrl + etViewNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Opinion page in  ET View section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> etViewNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(etViewNewsDup.isEmpty(),
					"<br>- On Opinion page in ET View section, is having duplicate stories, repeating story(s)->"
							+ etViewNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Opinion page in  ET View section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Opinion page in  ET View section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Opinion- Poke Me section ")
	private void verifyOpinionPokeMe() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String pokeMeNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Opinion", "dn",
				"Poke Me", "du");
		String url = baseUrl + pokeMeNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Opinion page in  Poke Me section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> pokeMeNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(pokeMeNewsDup.isEmpty(),
					"<br>- On Opinion page in Poke Me section, is having duplicate stories, repeating story(s)->"
							+ pokeMeNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Opinion page in  Poke Me section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Opinion page in  Poke Me section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Opinion- ET Commentary section ")
	private void verifyOpinionETCommentary() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String etCommentaryNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Opinion", "dn",
				"ET Commentary", "du");
		String url = baseUrl + etCommentaryNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Opinion page in ET Commentary section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> etCommentaryNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(etCommentaryNewsDup.isEmpty(),
					"<br>- On Opinion page in ET Commentary section, is having duplicate stories, repeating story(s)->"
							+ etCommentaryNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Opinion page in ET Commentary section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Opinion page in  ET Commentary section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Opinion- ET Editorial section ")
	private void verifyOpinionETEditorial() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String etEditorialNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Opinion", "dn",
				"ET Editorial", "du");
		String url = baseUrl + etEditorialNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Opinion page in  ET Editorial section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> etEditorialNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(etEditorialNewsDup.isEmpty(),
					"<br>- On Opinion page in ET Editorial section, is having duplicate stories, repeating story(s)->"
							+ etEditorialNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Opinion page in  ET Editorial section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Opinion page in  ET Editorial section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Opinion- Blogs section ")
	private void verifyOpinionBlogs() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String blogsNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Opinion", "dn",
				"Blogs", "du");
		String url = baseUrl + blogsNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Opinion page in  Blogs section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> blogsNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(blogsNewsDup.isEmpty(),
					"<br>- On Opinion page in Blogs section, is having duplicate stories, repeating story(s)->"
							+ blogsNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Opinion page in  Blogs section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Opinion page in  Blogs section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Opinion- ET Citings section ")
	private void verifyOpinionETCitings() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String etCitingsNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Opinion", "dn",
				"ET Citings", "du");
		String url = baseUrl + etCitingsNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Opinion page in  ET Citings section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> etCitingsNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(etCitingsNewsDup.isEmpty(),
					"<br>- On Opinion page in ET Citings section, is having duplicate stories, repeating story(s)->"
							+ etCitingsNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Opinion page in  ET Citings section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Opinion page in  ET Citings section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Opinion- Interviews section ")
	private void verifyOpinionInterviews() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String interviewsNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Opinion", "dn",
				"Interviews", "du");
		String url = baseUrl + interviewsNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Opinion page in  Interviews section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> etViewNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(etViewNewsDup.isEmpty(),
					"<br>- On Opinion page in Interviews section, is having duplicate stories, repeating story(s)->"
							+ etViewNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Opinion page in  Interviews section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Opinion page in  Interviews section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	
/********************** Tech ********************************/
	
	@Test(description = "This test verifies articles under Tech-Top News section " , groups = { "topmonitor" })
	private void verifyTechTopNews() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		int allowedstalenessDays = 30;
		String topNews = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Tech", "dn",
				"Top News", "du");
		String url = baseUrl + topNews;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Tech page in Top News section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Tech page in Top News section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Tech page in Top News section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Tech page in Top News section, is throwing " + responseCode);
			});
			if (i == 1) {
				counter = 0;
				dates = ApiHelper.getNewsItems(output, "Item", "NewsItem", "da");
				dates.forEach(date -> {
					softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
							"On Tech Page Top News section " + headlines.get(counter)
									+ " : article is not latest, article date " + (date.endsWith("0000")
											? new DateTime(Long.parseLong(date)) : date));
					counter++;
				});
			}
		}
		softAssert.assertAll();

	}
	
	
	
	@Test(description = "This test verifies articles under Tech- Tech & Internet section ", enabled = false)
	private void verifyTechInternet() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String internet = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Tech", "dn",
				"Tech & Internet", "du");
		String url = baseUrl + internet;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Tech page in Tech & Internet section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> internetDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(internetDup.isEmpty(),
					"<br>- On Tech page in Tech & Internet section, is having duplicate stories, repeating story(s)->"
							+ internetDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Tech page in Tech & Internet section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Tech page in Tech & Internet section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Tech-ITeS section ")
	private void verifyTechITeS() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String ites = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Tech", "dn",
				"ITES", "du");
		String url = baseUrl + ites;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Tech page in ITeS section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> itesDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(itesDup.isEmpty(),
					"<br>- On Tech page in ITeS section, is having duplicate stories, repeating story(s)->"
							+ itesDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Tech page in ITeS section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Tech page in ITeS section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}

	
	@Test(description = "This test verifies articles under Tech-Funding section ")
	private void verifyTechFunding() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String ites = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Tech", "dn",
				"Funding", "du");
		String url = baseUrl + ites;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Tech page in Funding section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> itesDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(itesDup.isEmpty(),
					"<br>- On Tech page in Funding section, is having duplicate stories, repeating story(s)->"
							+ itesDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Tech page in Funding section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Tech page in Funding section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Tech-Startups section ")
	private void verifyTechStartups() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String ites = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Tech", "dn",
				"Funding", "du");
		String url = baseUrl + ites;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Tech page in Startups section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> itesDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(itesDup.isEmpty(),
					"<br>- On Tech page in Startups section, is having duplicate stories, repeating story(s)->"
							+ itesDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Tech page in Startups section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Tech page in Startups section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies articles under Tech- Tech Bytes section ")
	private void verifyTechBytes() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String ites = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "Tech", "dn",
				"Tech Bytes", "du");
		String url = baseUrl + ites;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Tech page in Tech Bytes section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> itesDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(itesDup.isEmpty(),
					"<br>- On Tech page in Tech Bytes section, is having duplicate stories, repeating story(s)->"
							+ itesDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Tech page in Tech Bytes section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> on Tech page in Tech Bytes section, is throwing " + responseCode);
			});
			
		}
		softAssert.assertAll();

	}
	
	//////////////////Market Data/////////////////////////////////
	
	@Test(description = "This test verifies articles under Sensex-News section ")
	private void verifySensexNews() {
		softAssert = new SoftAssert();
		String url = String.format(AppFeeds.SensxNewsDetail, appVersion, platform);
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			System.out.println(headlines);
			Assert.assertTrue(headlines.size() > 0,
					"On Sensex page in News section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> newsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(newsDup.isEmpty(),
					"<br>- On Sensex page in News section, is having duplicate stories, repeating story(s)->"
							+ newsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Sensex page in News section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Sensex page in News section,, is throwing " + responseCode);
			});

		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies articles under Nifty-News section ")
	private void verifyNiftyNews() {
		softAssert = new SoftAssert();
		String url = String.format(AppFeeds.NiftyNewsDetail, appVersion, platform);
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			System.out.println(headlines);
			Assert.assertTrue(headlines.size() > 0,
					"On Nifty page in News section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> itesDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(itesDup.isEmpty(),
					"<br>- On Nifty page in News section, is having duplicate stories, repeating story(s)->" + itesDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Nifty page in News section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Nifty page in News section, is throwing " + responseCode);
			});

		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies articles under Commodity-News section ")
	private void verifyCommodityNews() {
		softAssert = new SoftAssert();
		String url = String.format(AppFeeds.CommodityNewsDetail, appVersion, platform);
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			System.out.println(headlines);
			Assert.assertTrue(headlines.size() > 0,
					"On Commodity page in News section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> newsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(newsDup.isEmpty(),
					"<br>- On Commodity page in News section, is having duplicate stories, repeating story(s)->"
							+ newsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Commodity page in News section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Commodity page in News section, is throwing " + responseCode);
			});

		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies articles under Forex-News section ")
	private void verifyForexNews() {
		softAssert = new SoftAssert();
		String url = String.format(AppFeeds.ForexNewsDetail, appVersion, platform);
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			System.out.println(headlines);
			Assert.assertTrue(headlines.size() > 0,
					"On Forex page in News section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> newsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(newsDup.isEmpty(),
					"<br>- On Forex page in News section, is having duplicate stories, repeating story(s)->" + newsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Forex page in News section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});

			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Forex page in News section, is throwing " + responseCode);
			});

		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies duplicity and urls in app notifications feed", dataProvider = "getFeedUrl", groups = {
			"topmonitor" })
	public void verifyNotificationsFeed(String app, String feedUrl) {
		if(new DateTime().getHourOfDay()>9){
		System.out.println("Running test for " + app + " app...");
		String output = "";
		softAssert = new SoftAssert();
		List<String> notifications;
		try {
			output = ApiHelper.getAPIResponse(feedUrl);
		} catch (RuntimeException e) {
			Assert.assertTrue(false, e.getMessage());

		}
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		notifications = ApiHelper.getListOfAllValues_contains(output, "appText", "timeStamp", date, "pushDetailsList");
		System.out.println(notifications);
		Assert.assertTrue(!notifications.isEmpty(), "<br>Notifications not found for date: <strong>" + date
				+ "</strong> in <a href='" + feedUrl + "'>notifications feed</a>.<br>");

		for (int i = 0; i < notifications.size(); i++) {
			if (notifications.get(i).isEmpty() || notifications.get(i) == null) {
				softAssert.assertTrue(false,
						"<br>Blank notification found in <a href='" + feedUrl + "'>notifications feed</a>.<br>");
			} else {
				List<String> notificationLink;
				List<String> notificationImageLink;
				notificationLink = ApiHelper.getListOfAllValues_contains(output, "webUrl", "appText",
						notifications.get(i), "pushDetailsList");
				if (!notificationLink.isEmpty())
					softAssert.assertFalse(ApiHelper.httpResponseCode(notificationLink.get(0)) == 404,
							"<br><a href='" + notificationLink.get(0) + "'>webUrl</a> for notification: <strong>"
									+ notifications.get(i) + "</strong> is throwing 404.");
				notificationImageLink = ApiHelper.getListOfAllValues_contains(output, "imageUrl", "appText",
						notifications.get(i), "pushDetailsList");
				if (!notificationImageLink.isEmpty())
					softAssert.assertFalse(ApiHelper.httpResponseCode(notificationImageLink.get(0)) == 404,
							"<br><a href='" + notificationImageLink.get(0) + "'>imageUrl</a> for notification: <strong>"
									+ notifications.get(i) + "</strong> is throwing 404.");
			}
		}
		}
	}

	@Test(description = "This test verifies search feed", groups = { "topmonitor" })
	public void verifySearchResults() {
		if (platform.contains("android"))
			feedUrl = AppFeeds.androidSearchFeed;
		softAssert = new SoftAssert();
		Map<String, List<String>> searchKeywords = searchKeywordsAndParams();
		searchKeywords.forEach((K, V) -> {
			String keywordAPI = String.format(feedUrl, appVersion, K);
			System.out.println("Hitting API:" + keywordAPI);
			String output = ApiHelper.getAPIResponse(keywordAPI);
			V.forEach(key -> {
				List<String> getLiResults=new LinkedList<>();
				if(platform.contains("ios"))
					getLiResults = ApiHelper.getListOfAllValues(output, "nm", "", "", "Item", key,"list");
				else
					getLiResults= ApiHelper.getListOfAllValues(output, "nm", "", "", "Item", key);
				
				System.out.println(getLiResults);
				softAssert.assertTrue(getLiResults!=null &&!getLiResults.isEmpty(), "No results found in the " + key + " section for: " + K+" in api:"+keywordAPI);
			});
		});
		softAssert.assertAll();
	}

	
///////////////////////////ET Prime////////////////////////////////////////////////

	@Test(description = "This test verifies articles under  ET Prime Home page section")
	public void verifyPrimeHomeFeeds() {
		softAssert = new SoftAssert();
		String url = String.format(AppFeeds.primeHomeFeed, appVersion, platform);
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			List<String> sectionNames = ApiHelper.getNewsItems(output, "Item", "", "sn");
			System.out.println(sectionNames + " for home page");
			sectionNames.forEach(section -> {
				if (!sectionNames.contains("Popular With Readers")) {
					counter = 0;
					String sectionName = section.trim().length() > 1 ? section : "Top News";
					headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
					Assert.assertTrue(headlines.size() > 0,
							"<br>In section: " + sectionName + " headlines count is equal to 0, count found "
									+ headlines.size() + " for platform:" + platform);
					urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
					// dates verification
					int allowedstalenessDays = 20;
					dates.forEach(date -> {
						softAssert.assertTrue(VerificationUtil.isLatest(date.replace("hrs", ""), allowedstalenessDays),
								"<br>In section: " + sectionName + ",  " + headlines.get(counter)
										+ " : article is not latest, article date "
										+ (date.endsWith("0000") ? new DateTime(Long.parseLong(date)) : date)
										+ " for platform:" + platform);
						counter++;
					});
					// Headlines duplication verification
					List<String> topNewsDup = VerificationUtil.isListUnique(urls);
					List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
					Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines,
							headlinesDup);

					softAssert.assertTrue(topNewsDup.isEmpty(),
							"<br>- Section: " + sectionName + ", is having duplicate stories, repeating story(s)->"
									+ topNewsDup + " for platform:" + platform);
					storyUrls = "";
					List<String> temp = urls.stream().filter(o -> o.contains("ad.")).collect(Collectors.toList());
					urls.removeAll(temp);
					urls.forEach(link -> {
						link = link.startsWith("http") ? link
								: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
						if (link.contains("app:"))
							return;
						link = link.replaceAll("\\?utm_source.*", "");
						link = link.startsWith("http") ? link
								: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
						int responseCode = HTTPResponse.checkResponseCode(link);
						softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link + ">Story</a> in "
								+ sectionName + " is throwing " + responseCode + " for platform:" + platform);
					});

					List<String> uniqueUrl = new LinkedList<>();
					indexedDup.forEach((K, V) -> {
						V.forEach(action -> {
							storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
							String link = urls.get(new Integer(action));
							link = link.startsWith("http") ? link
									: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
							uniqueUrl.add(link);
						});
						if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
							Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1
									? true : false;
							softAssert.assertTrue(!flag,
									"<br>- Section: " + sectionName + ", is having multipublished stories, headline: "
											+ K + " urls:  " + storyUrls + " for platform:" + platform);
						}
					});
				}
			});
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies articles under Prime-Tech section ")
	private void verifyPrimeTechSection() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String tech = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "ET Prime", "dn", "Tech", "du");
		String url = baseUrl + tech;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Prime page in Tech section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);
			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Prime page in Tech section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Prime page in Tech section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Prime page in Tech section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Prime-Consumer section ")
	private void verifyPrimeConsumerSection() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String consumer = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "ET Prime", "dn", "Consumer",
				"du");
		String url = baseUrl + consumer;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Prime page in Consumer section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Prime page in Consumer section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Prime page in Consumer section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Prime page in Consumer section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Prime-Markets section ")
	private void verifyPrimeMarketsSection() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String markets = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "ET Prime", "dn", "Markets",
				"du");
		String url = baseUrl + markets;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Prime page in Markets section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Prime page in Markets section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Prime page in Markets section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Prime page in Markets section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Prime-Corp Gov section ")
	private void verifyPrimeCorpGovSection() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String corpgov = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "ET Prime", "dn", "Corporate Governance",
				"du");
		String url = baseUrl + corpgov;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Prime page in Corp Gov section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Prime page in Corp Gov section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Prime page in Corp Gov section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Prime page in Corp Gov section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Prime-Telecom + OTT section ")
	private void verifyPrimeTelecomSection() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String telecom = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "ET Prime", "dn",
				"Telecom + OTT", "du");
		String url = baseUrl + telecom;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Prime page in Telecom + OTT section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Prime page in Telecom + OTT section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Prime page in Telecom + OTT section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Prime page in Telecom + OTT section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Prime-Auto + Aviation section ")
	private void verifyPrimeAutoAviationSection() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String auto = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "ET Prime", "dn",
				"Auto + Aviation", "du");
		String url = baseUrl + auto;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Prime page in Auto + Aviation section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Prime page in Auto + Aviation section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Prime page in Auto + Aviation section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Prime page in Auto + Aviation section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Prime-Pharma section ")
	private void verifyPrimePharmaSection() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String pharma = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "ET Prime", "dn", "Pharma",
				"du");
		String url = baseUrl + pharma;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Prime page in Pharma section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Prime page in Pharma section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Prime page in Pharma section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Prime page in Pharma section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Prime-Fintech section ")
	private void verifyPrimeFintechSection() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String fintech = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "ET Prime", "dn", "Fintech + BFSI",
				"du");
		String url = baseUrl + fintech;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Prime page in Fintech section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Prime page in Fintech section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Prime page in Fintech section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Prime page in Fintech section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Prime-Infra section ")
	private void verifyPrimeInfraSection() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String infra = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "ET Prime", "dn", "Infra", "du");
		String url = baseUrl + infra;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Prime page in Infra section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Prime page in Infra section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Prime page in Infra section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Prime page in Infra section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Prime-Economy section ")
	private void verifyPrimeEconomySection() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String economy = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "ET Prime", "dn", "Economy",
				"du");
		String url = baseUrl + economy;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Prime page in Economy section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Prime page in Economy section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Prime page in Economy section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Prime page in Economy section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Prime-Environment section ")
	private void verifyPrimeEnvironmentSection() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String environment = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "ET Prime", "dn",
				"Environment", "du");
		String url = baseUrl + environment;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Prime page in Environment section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Prime page in Environment section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Prime page in Environment section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Prime page in Environment section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies articles under Prime-Energy section ")
	private void verifyPrimeEnergySection() {
		softAssert = new SoftAssert();
		output = lhsFeedOutput;
		String energy = ApiHelper.getChildValueInheritTree(output, "Item", "ss", "nm", "ET Prime", "dn", "Energy",
				"du");
		String url = baseUrl + energy;
		output = ApiHelper.getAPIResponse(url);
		List<String> pageNumbers = ApiHelper.getNewsItems(output, "pn", "", "tp");
		String pno = pageNumbers.size() > 0 ? pageNumbers.get(0) : "1";
		for (int i = 1; i <= Integer.parseInt(pno); i++) {
			url = url.replaceAll("&curpg=[\\d]+", "");
			url += "&curpg=" + i;
			if (i > 1)
				output = ApiHelper.getAPIResponse(url);
			headlines = ApiHelper.getNewsItems(output, "Item", "NewsItem", "hl");
			Assert.assertTrue(headlines.size() > 0,
					"On Prime page in Energy section no stories are shown,api url " + url);
			urls = ApiHelper.getNewsItems(output, "Item", "NewsItem", "wu");
			List<String> temp = urls.stream().filter(o -> o.contains("etandroidapp")).collect(Collectors.toList());
			urls.removeAll(temp);
			// Headlines duplication verification
			List<String> topNewsDup = VerificationUtil.isListUnique(urls);
			List<String> headlinesDup = VerificationUtil.isListUnique(headlines);
			Map<String, List<Integer>> indexedDup = VerificationUtil.getDupMapWithIndexes(headlines, headlinesDup);

			softAssert.assertTrue(topNewsDup.isEmpty(),
					"<br>- On Prime page in Energy section, is having duplicate stories, repeating story(s)->"
							+ topNewsDup);
			indexedDup.forEach((K, V) -> {
				storyUrls = "";
				List<String> uniqueUrl = new LinkedList<>();
				V.forEach(action -> {
					storyUrls += "<br> " + urls.get(new Integer(action)) + " ";
					String link = urls.get(new Integer(action));
					link = link.startsWith("http") ? link
							: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
					uniqueUrl.add(link);
				});
				if (VerificationUtil.isListUnique(uniqueUrl).isEmpty()) {
					Boolean flag = VerificationUtil.getSubstringOccurences(storyUrls, "/articleshow/") > 1 ? true
							: false;
					softAssert.assertTrue(!flag,
							"<br>- On Prime page in Energy section, is having multipublished stories, repeating story(s)->"
									+ K + " urls:  " + storyUrls);
				}
			});
			// urls verification
			urls.forEach(link -> {
				link = link.replaceAll("\\?utm_source.*", "");
				link = link.startsWith("http") ? link
						: (link = link.startsWith("/") ? baseUrl + link : baseUrl + "/" + link);
				int responseCode = HTTPResponse.checkResponseCode(link);
				softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + link
						+ ">Story</a> On Prime page in Energy section, is throwing " + responseCode);
			});
		}
		softAssert.assertAll();
	}
	@DataProvider(name="getFeedUrl")
	public static Object[][] getFeedUrl() {
		Object[][] data = {
				{ "EtMainAndroid",
						"http://etp.economictimes.indiatimes.com/apppushnotificationdetails/pushednotificationdetails?days=30&recordeperPage=20&channel=et&platform=android" },
				{ "EtMainIos",
						"http://economictimes.indiatimes.com/notificationfeed_app.cms?feedtype=etjson&app=et&platform=ios" },
				{ "EtMarketsAndroid",
						"http://etp.economictimes.indiatimes.com/apppushnotificationdetails/pushednotificationdetails?days=30&recordeperPage=20&channel=etmarket&platform=android" },
				{ "EtMarketsIos",
						"http://economictimes.indiatimes.com/notificationfeed_app.cms?feedtype=etjson&app=etmarket&platform=ios" } };
		return data;
	}

	private Map<String, List<String>> searchKeywordsAndParams() {
		Map<String, List<String>> searchMap = new HashMap<>();
		searchMap.put("gold", Arrays.asList(("prime,mutualfund,commodity,news").split(",")));
		searchMap.put("indigo", Arrays.asList(("prime,company,news").split(",")));
		searchMap.put("icici%20prudential", Arrays.asList(("prime,mutualfund,company,news").split(",")));
		searchMap.put("technology", Arrays.asList(("prime,mutualfund,news").split(",")));
		searchMap.put("politics", Arrays.asList(("prime,news").split(",")));
		searchMap.put("copper", Arrays.asList(("prime,commodity,news").split(",")));
		return searchMap;
	}

	private int setDaysStaleness(String subSectionName, String tabName) {
		System.out.println(subSectionName + " " + tabName);
		int allowedstalenessDays = 10;
		switch (subSectionName) {
		case ("Top News"):
			allowedstalenessDays = 5;
			break;
		case ("Jobs"):
			allowedstalenessDays = 120;
			break;
		case ("Brandwire"):
			allowedstalenessDays = 360;
			break;
		case ("Opinion"):
			allowedstalenessDays = 120;
			break;
		case ("NRI"):
			allowedstalenessDays = 360;
			break;
		case ("RISE"):
			allowedstalenessDays = 90;
		default:
			allowedstalenessDays = 90;
		}
		switch (tabName) {
		case ("Markets"):
			allowedstalenessDays = (new DateTime().getDayOfWeek() == 1 || new DateTime().getDayOfWeek() == 7) ? 5 : 3;
			break;
		case ("Mutual Funds"):
			allowedstalenessDays = (new DateTime().getDayOfWeek() == 1 || new DateTime().getDayOfWeek() == 7) ? 5 : 3;
			break;
		}
		return allowedstalenessDays;
	}

	/**
	 * This test records response time
	 */

	@Test(description = "This test verifies api response time", groups = { "topmonitor" }, enabled = false)
	public void testResponseTime() {
		String api = "http://economictimes.indiatimes.com/homefeed.cms?feedtype=etjson&curpg=1";
		Long startTime = System.currentTimeMillis();
		ApiHelper.getAPIResponse(api);
		Long endTime = System.currentTimeMillis();
		Long totalTime = endTime - startTime;
		DBUtil.dbInsertApi(new Date(), api, totalTime.doubleValue());
	}
}