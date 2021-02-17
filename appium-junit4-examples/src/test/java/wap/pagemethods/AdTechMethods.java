package wap.pagemethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.launchsetup.Config;
import common.utilities.HTTPResponse;
import common.utilities.WaitUtil;
import wap.pageobjects.AdTechObjects;
import web.pagemethods.WebBaseMethods;

public class AdTechMethods {
	private Config config;
	private final String ConfigPathAppender;
	private String configName;
	private File configFile;
	private int adsCount = 0;
	private WebDriver driver;
	private AdTechObjects adTechObjects;
	private String result = "";
	private List<String> notFound = new LinkedList<>();

	public AdTechMethods(WebDriver driver) {
		this.driver = driver;
		config = new Config();
		ConfigPathAppender = "./src/main/resources/properties/";
		configName = ConfigPathAppender + "adTech.properties";
		configFile = new File(configName);
		adTechObjects = PageFactory.initElements(driver, AdTechObjects.class);

	}

	public Config getConfig() {
		return config;
	}

	public String getConfigPathAppender() {
		return ConfigPathAppender;
	}

	public String getConfigName() {
		return configName;
	}

	public File getConfigFile() {
		return configFile;
	}

	public void openHomePage() {
		driver.navigate().to(config.fetchConfig(new File(ConfigPathAppender + "proxyBrowser.properties"), "url"));
		WebBaseMethods.scrollToBottom();
		WaitUtil.sleep(5000);

	}

	public void doBrowserActions(String email, String password) {
		WebBaseMethods.clickElementUsingJSE(driver.findElement(By.xpath("//a[contains(text(),'SIGN-IN')]")));
		WebBaseMethods.switchToChildWindow(10);
		driver.findElement(By.id("emailId")).sendKeys(email);
		driver.findElement(By.id("signInButtonDiv")).click();
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("signInButtonDiv")).click();
		WaitUtil.sleep(2000);
		WebBaseMethods.switchToParentWindow();
		WaitUtil.sleep(2000);
	}

	public int findColumbiaOtherSponsored() {
		adsCount = 0;
		List<WebElement> li = adTechObjects.getColombiaAdIframe();
		li.addAll(driver.findElements(By.className("attribution")));
		li.forEach(action -> {
			if (action.isDisplayed() && action.isEnabled()) {
				System.out.println(action.getText());
				adsCount++;
			}

		});
		return adsCount;
	}

	public int getAdsIFrameCount() {
		adsCount = 0;
		try{
		List<WebElement> li = adTechObjects.getAdIframe();
		for (int i = 0; i < li.size(); i++) {
			if (li.get(i).isDisplayed() && li.get(i).isEnabled())
				adsCount++;
		}}catch(WebDriverException e){
			
		}
		return adsCount;
	}

	public List<String> getDisplayedAdIds() {
		adsCount = 0;
		List<String> idList = new LinkedList<String>();
		try {
			
			List<WebElement> li = adTechObjects.getDivIdAds();
			idList = WebBaseMethods.getListAnyAttributeUsingJSE(li, "id");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return idList;
	}
	
	public List<String> getAdIframeIds() {
		adsCount = 0;
		List<String> idList = new LinkedList<String>();
		try {
			List<WebElement> li = adTechObjects.getIframeIdAds();
			li= WebBaseMethods.getDisplayedItemFromList(li);
			idList = WebBaseMethods.getListAnyAttributeUsingJSE(li, "id");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return idList;
	}
	
	
	

	public List<String> returnAdNotShown() {
		WaitUtil.sleep(2000);
		WebBaseMethods.scrollToBottom();
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(2000);
		List<String> ids = getDisplayedAdIds();
		System.out.println("displayed ads:"+ids);
		Map<String, String> expectedIdsMap = Config.getAllKeys("adIdLocation");
		System.out.println("expected ids:"+expectedIdsMap);
		expectedIdsMap.forEach((K, V) -> {
			boolean flag = ids.stream().anyMatch(x -> x.toLowerCase().contains(K.toLowerCase()));
			if (!flag) {
				notFound.add(V);
			}
		});
		return notFound;
	}

	public List<WebElement> getAdsIFrameList() {
		List<WebElement> li = adTechObjects.getAdIframe();
		List<WebElement> diplayedList = new LinkedList<>();
		li.forEach(action -> {
			if (action.isDisplayed() && action.isEnabled())
				diplayedList.add(action);
		});
		return diplayedList;
	}

	public boolean checkAdResponse(WebElement li) {

		driver.switchTo().frame(li.getAttribute("id"));
		List<WebElement> addImg = driver.findElements(By.xpath("//body//img"));
		addImg.addAll(driver.findElements(By.xpath("//body//video")));

		addImg.forEach(action1 -> {
			String src = action1.getAttribute("src").trim().length() > 1 ? action1.getAttribute("src")
					: (action1.getAttribute("sources").trim().length() > 1 ? action1.getAttribute("sources") : "");
			if (src.trim().length() > 1) {
				System.out.println(src);

				int response = HTTPResponse.checkResponseCode(src);
				System.out.println(response);
				boolean flag = response == 200 || response == 204 ? true : false;
				result += Boolean.toString(flag);
			}
		});
		driver.switchTo().defaultContent();
		System.out.println(result);
		WaitUtil.sleep(3000);
		if (result.contains("false")) {
			return false;
		}

		return true;
	}
	
	public static Map<String, String> fetchMatchingProperty(String pattern, File file) {
		InputStream inputStream = null;
		Map<String, String> keyValueMap = new LinkedHashMap<String, String>();
		try {
			Properties properties = new Properties();
			inputStream = new FileInputStream(file);
			properties.load(inputStream);
			Enumeration<?> e = properties.propertyNames();
			while (e.hasMoreElements()) {
				String prop = (String) e.nextElement();
				if (!prop.startsWith(pattern))
					continue;
				String val = properties.getProperty(prop);
				System.out.println(val);
				if (val.equals("<NULL>"))
					val = "";
				keyValueMap.put(prop, val);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keyValueMap;
	}

	public Map<String,String> getPageSpecificAdIframes(String pageName, File file){
		
		Map<String, String> adIframe = fetchMatchingProperty(pageName,file);
		
		return adIframe;
		
	}
	
	public List<String> getMissingColumbiaAds() {
		List<String> colombiaAdList = new ArrayList<>();
		colombiaAdList = getAllColombiaAds();
		List<String> tempList = new ArrayList<>();
		try {
			if(colombiaAdList.size()>0) {
				for(String id: colombiaAdList) {
					if(!(id==null) && !id.contains("div-clmb-ctn")) {
						tempList.add(id);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return tempList;
	}
	
	public List<String> getAllColombiaAds(){
		List<String> colombiaAds = new ArrayList<>();
		try {
			colombiaAds = WebBaseMethods.getListAnyAttributeUsingJSE(adTechObjects.getColombiaAds(), "id");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return colombiaAds;
	}

	public boolean matchIdsWithRegex(String k) {
		List<String> ids = getDisplayedAdIds();
		boolean result = false;
		Pattern pattern = Pattern.compile(k);
		if(!ids.isEmpty()) {
			for(int i = 0; i<ids.size();i++) {
				Matcher m=  pattern.matcher(ids.get(i));
				if(m.find()) {
					result= true;
				}
			}
		}
		return result;
	}
	
	public boolean matchIdsWithKey(String k) {
		List<String> ids = getDisplayedAdIds();
		boolean flag= false;
		if(!ids.isEmpty())
			flag = ids.stream().anyMatch(x -> x.toLowerCase().contains(k.toLowerCase()));
		return flag;

	}

}
