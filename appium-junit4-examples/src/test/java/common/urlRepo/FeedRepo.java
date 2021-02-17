package common.urlRepo;

public class FeedRepo {

	/* APP Feeds */
	public static final String MarketsDashboardData_NSE = "https://json.bselivefeeds.indiatimes.com/markets_nse.json";
	public static final String MarketsDashboardData_BSE = "https://json.bselivefeeds.indiatimes.com/markets_bse.json";

	public static final String BSE_COMPANYDATA = "https://mobilelivefeeds.indiatimes.com/ETMobileApps/liveindices?indexid=2365&exchange=47&pagesize=50&sortorder=desc&sortby=percentagechange&company=true&language=ENG";
	public static final String NSE_50_COMPANYDATA = "https://mobilelivefeeds.indiatimes.com/ETMobileApps/liveindices?indexid=2369&pagesize=50&sortorder=desc&sortby=percentagechange&company=true&language=ENG";
	public static final String TOPFUND = "https://mcxlivefeeds.indiatimes.com/mf/topfund.htm?count=5&fivestarreturn=true&";

	public static final String ETFLIVEPRICE_NSE = "https://json.bselivefeeds.indiatimes.com/ET_Community/MFJsonController?pid=141&pagesize=100&filtervalue=all&sortby=percentchange&sortorder=desc&category=all&outputtype=json";
	public static final String ETFLIVEPRICE_BSE = "https://json.bselivefeeds.indiatimes.com/ET_Community/MFJsonController?pid=141&pagesize=100&filtervalue=all&sortby=percentchange&sortorder=desc&category=all&outputtype=json&exchange=47";

	public static final String OTHERINDICES_NSE = "https://json.bselivefeeds.indiatimes.com/ET_Community/indexsummary?pid=40&exchange=nse&pagesize=15";
	public static final String OTHERINDICES_BSE = "https://json.bselivefeeds.indiatimes.com/ET_Community/indexsummary?pid=40&exchange=bse&pagesize=15";
	public static final String OTHERINDICES_ALL = "https://json.bselivefeeds.indiatimes.com/ET_Community/indexsummary?pid=40&exchange=all&pagesize=15";

	public static final String GAINERS_NSE = "https://mobilelivefeeds.indiatimes.com/ETMobileApps/Gain?ismobile=true&language=ENG&exchange=NSE";
	public static final String GAINERS_BSE = "https://mobilelivefeeds.indiatimes.com/ETMobileApps/Gain?ismobile=true&language=ENG&exchange=BSE";
	public static final String MFDATA_TOPRATED = "https://mcxlivefeeds.indiatimes.com/mf/topfund.htm?count=5&fivestarreturn=true&primaryobj=Equity&secondaryobj=Banking";
	public static final String COMMODITYDATA = "https://mcxlivefeeds.indiatimes.com/ET_MCX/mcxjson?type=fando&symbol=";
	public static final String SECTORPERFORMANCE_NSE = "https://json.bselivefeeds.indiatimes.com/ET_Community/sectorperformance?pagesize=25&exchange=NSE&pid=255&sortorder=desc&sortby=marketcappercentchange";
	public static final String SECTORPERFORMANCE_BSE = "https://json.bselivefeeds.indiatimes.com/ET_Community/sectorperformance?pagesize=25&exchange=BSE&pid=255&sortorder=desc&sortby=marketcappercentchange";
	public static final String INDUSTRIESPERFORMANCE_NSE = "https://bselivefeeds.indiatimes.com/ET_Community/industryListingController?pagesize=25&pid=256&exchange=NSE&pageno=1&tab=const&totalpages=8";
	public static final String INDUSTRIESPERFORMANCE_BSE = "https://json.bselivefeeds.indiatimes.com/ET_Community/industryListingController?pagesize=25&pageno=1&exchange=BSE&pid=256&tab=const";
	public static final String FOREX_CURRENCYPAIRRATE = "http://mfapps.economictimes.indiatimes.com/ET_Forex/currencyspotpair";
	public static String FOREX_GLOBALCURRENCY = "http://mfapps.economictimes.indiatimes.com/ET_Forex/globalcurrency?sortby=countryname&continent=%s&currencycode=%s&period=%s";
	
	/* ET Web Markets Feeds*/
	public static String MARKETWATCHBAND_DATA = "https://json.bselivefeeds.indiatimes.com/marketband.json";
	

	/* Feeds in monitoring */
	/* ET-Main ANDROID */
	public static final String HOMEPAGE_BENCHMARK = "http://json.bselivefeeds.indiatimes.com/homepagedatanew.json";
	public static final String HOMEPAGE_IOS_BENCHMARK="http://mobilelivefeeds.indiatimes.com/ETMobileApps/MarketLiveMobileTile?language=ENG&exchange=NSE&device=APP&tile=1";
	public static final String HOMEPAGE_GAINERS = "http://mobilelivefeeds.indiatimes.com/ETMobileApps/Gain?ismobile=true&pagesize=5";
	public static final String HOMEPAGE_LOSERS = "http://mobilelivefeeds.indiatimes.com/ETMobileApps/Losers?ismobile=true&pagesize=5";
	public static final String HOMEPAGE_MOVERS = "http://mobilelivefeeds.indiatimes.com/ETMobileApps/BuyerSellerMover?service=volumemovers&ismobile=true&pagesize=5";
	public static final String COMMODITIES_TOP = "http://mcxlivefeeds.indiatimes.com/ET_MCX/mcxetmobile?symbol=GOLD,SILVER,CRUDEOIL,COPPER,CPO&type=fando";
	public static final String COMMODITYLOSERS = "http://mcxlivefeeds.indiatimes.com/ET_MCX/MCXLiveController?pagesize=10&pageno=1&sortby=percentchange&sortorder=asc&statstype=losers&ismobile=true";
	public static final String COMMODITYGAINERS = "http://mcxlivefeeds.indiatimes.com/ET_MCX/MCXLiveController?pagesize=10&pageno=1&sortby=percentchange&sortorder=desc&statstype=gainers&ismobile=true";
	public static final String COMMODITYMOVERS = "http://mcxlivefeeds.indiatimes.com/ET_MCX/MCXLiveController?pagesize=10&pageno=1&sortby=volume&sortorder=desc&statstype=movers&ismobile=true";
	public static final String SENSEXDETAILPAGE = "http://mobilelivefeeds.indiatimes.com/ETMobileApps/liveindicesmobile?exchangeid=47&indexid=2365&sortby=percentChange&sortorder=desc&pageno=1&pagesize=15";
	public static final String NIFTYFUTURES = "http://etfeedscache.indiatimes.com/ETService/indexderivative?instrumentname=FUTIDX&indexid=2369&ismobile=true";
	public static final String NIFTY50DETAILPAGE = "http://mobilelivefeeds.indiatimes.com/ETMobileApps/liveindicesmobile?exchangeid=50&indexid=2369&sortby=percentChange&sortorder=desc&pageno=1&pagesize=15";
	public static final String GOLDDETAILPAGE = "http://mcxlivefeeds.indiatimes.com/ET_MCX/mcxetmobile?symbol=GOLD&type=fando";
	public static final String USDINRDETAILPAGE = "http://mfapps.economictimes.indiatimes.com/ET_Forex/currencyspotpair?currency=USD/INR";
	public static final String HOLIDAYLIST = "https://json.bselivefeeds.indiatimes.com/ET_Community/holidaylist";
	public static final String GAINERSLISTINTRADAYPAGE = "http://mobilelivefeeds.indiatimes.com/ETMobileApps/Gain?ismobile=true&sort=intraday&sortby=percentChange&sortorder=desc&pageno=1&pagesize=15";
	public static final String GAINERSLISTONEMONTH = "http://mobilelivefeeds.indiatimes.com/ETMobileApps/Gain?ismobile=true&sort=1month&sortby=percentChange&sortorder=desc&pageno=1&pagesize=15";
	public static final String LOSERSLISTINTRADAYPAGE = "http://mobilelivefeeds.indiatimes.com/ETMobileApps/Losers?ismobile=true&sort=intraday&sortby=percentChange&sortorder=asc&pageno=1&pagesize=15";
	public static final String VOLUMEMOVERSLISTPAGE = "http://mobilelivefeeds.indiatimes.com/ETMobileApps/BuyerSellerMover?service=volumemovers&ismobile=true&sort=intraday&sortby=volume&sortorder=desc&pageno=1&pagesize=15";
	public static final String COMPANYDETAILPAGE = "http://mobilelivefeeds.indiatimes.com/ETMobileApps/bsensejson?companyid=11984&ismobile=true";
	public static final String MF_FACTSHEET_ICICPRU = "http://mcxlivefeeds.indiatimes.com/mf/dailyupdate.htm?schemeid=15822";
	public static final String MF_PERFORMANCE_ICICPRU = "http://mcxlivefeeds.indiatimes.com/mf/consolidatedperformance.htm?outputtype=json&schemeid=15822";
	public static final String MF_PORTFOLIO_ICICPRU = "http://mcxlivefeeds.indiatimes.com/mf/topsectorforportfolio.htm?outputtype=json&schemeid=15822&count=5";
	public static final String TopMutualCategoryList = "https://etmarketsapis.indiatimes.com/ET_MfScreeners/getCategoryList";
	public static final String TopMutuafundCategoryfeed = "https://etmarketsapis.indiatimes.com/ET_MfScreeners/topfund?sortedField=r3Year&pageSize=4&primaryObjectiveManual=%s";
}
