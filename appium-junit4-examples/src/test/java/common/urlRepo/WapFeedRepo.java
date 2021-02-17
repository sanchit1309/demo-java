package common.urlRepo;

public class WapFeedRepo {

	public static final String HomepageWidgetsSubSection_Feed = "http://etpwaapi.economictimes.com/request";
	public static final String HomepageMarketsWidgetBenchmark_Feed = "https://mobilelivefeeds.indiatimes.com/ETMobileApps/markets/mobile/benchmark?pagesize=4&ismobile=true";
	public static final String HomepageMarketsWidgetGainers_Feed = "https://mobilelivefeeds.indiatimes.com/ETMobileApps/Gain?pagesize=4&ismobile=true";
	public static final String HomepageMarketsWidgetLosers_Feed = "https://mobilelivefeeds.indiatimes.com/ETMobileApps/Losers?pagesize=4&ismobile=true";
	public static final String HomepageMarketsWidgetMovers_Feed = "https://mobilelivefeeds.indiatimes.com/ETMobileApps/BuyerSellerMover?pagesize=4&ismobile=true&service=volumemovers";

	public static String getMarketWidgetFeedRepo(String sectionName) {
		String section = sectionName;

		switch (section) {

		case "Gainers":
			return HomepageMarketsWidgetGainers_Feed;

		case "Losers":
			return HomepageMarketsWidgetLosers_Feed;
		case "Movers":
			return HomepageMarketsWidgetMovers_Feed;

		case "Benchmark":
			return HomepageMarketsWidgetBenchmark_Feed;

		default:
			return HomepageMarketsWidgetBenchmark_Feed;
		}

	}
}
