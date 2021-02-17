package common.launchsetup;
/**
 * This class starts Appium service  
 * 
 *  @author Pooja.Gupta1
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.Platform;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumService {

	private final String appiumProp = "/src/main/resources/properties/appiumService.properties";
	AppiumDriverLocalService service = null;
	
	
	/**
	 * Method set path of node depending on system OS 
	 * @return
	 * @throws IOException
	 */
	private File findCustomNode() throws IOException {
		File file = new File(System.getProperty("user.dir") + appiumProp);
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);
		fileInput.close();
		Platform platform = Platform.getCurrent();
		if(platform.is(Platform.WINDOWS))
			return new File(String.valueOf(properties.get("node.path.win")));
		
		if(platform.is(Platform.MAC))
			return new File(String.valueOf(properties.get("node.path.mac")));
		
		return new File(String.valueOf(properties.get("node.path.linux")));
	}
	/**
	 * Method returns the path of appium main depending on system OS
	 * @return
	 * @throws IOException
	 */
	private File appiumJsPath() throws IOException {
		File file = new File(System.getProperty("user.dir") + appiumProp);
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);
		fileInput.close();
		Platform platform = Platform.getCurrent();
		if(platform.is(Platform.WINDOWS))
			return new File(String.valueOf(properties.get("appiumJS.path.win")));
		
		if(platform.is(Platform.MAC))
			return new File(String.valueOf(properties.get("appiumJS.path.mac")));
		
		return new File(String.valueOf(properties.get("appiumJS.path.linux")));
	}
	
	/**
	 * Returns an Appium Driver Local Service 
	 * @return
	 * @throws IOException
	 */
	public AppiumDriverLocalService startAppiumService() throws IOException {
		
		AppiumServiceBuilder buildService ;
		
		try {
				
					buildService = new AppiumServiceBuilder().usingDriverExecutable(findCustomNode())
															 .withAppiumJS(appiumJsPath());
															 
					
					service = AppiumDriverLocalService.buildService(buildService);
					
					if(!service.isRunning()){
						System.out.println("- - - - - - - - Starting Appium Server- - - - - - - - ");
//						service.start();
					}
		}catch(IOException e){
			e.getMessage();
			System.out.println("Appium Service Failed to start !");
		}finally {
			System.clearProperty(AppiumServiceBuilder.APPIUM_PATH);
		}
		return service;
	}
	
	/**
	 * Stops Appium Service
	 */
	public void stopAppiumService(){
		System.out.println("- - - - - - - - Stopping Appium Server- - - - - - - - ");
		if(service!=null)
		service.stop();
	}
}
