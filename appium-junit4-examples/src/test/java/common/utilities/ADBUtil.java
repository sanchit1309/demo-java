package common.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.launchsetup.Config;

public class ADBUtil {
	String packageName = "";
	ArrayList<String> deviceId = new ArrayList<String>();
	ArrayList<String> deviceName = new ArrayList<String>();

	/***
	 * To get device list [Android]
	 * 
	 * @throws IOException
	 */
	public ADBUtil() {
		packageName = Config.fetchConfigProperty("cap_appPackage");
	}

	public Map<String, ArrayList<String>> getConnectedDevices() {
		Map<String, ArrayList<String>> deviceInfo = new HashMap<String, ArrayList<String>>();

		Process p = null;
		try {
			p = Runtime.getRuntime().exec("adb devices");
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				if (line.isEmpty())
					break;
				deviceId.add(line.split("\\t+")[0]);
				deviceName.add(line.split("\\t+")[1]);
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
		deviceInfo.put("deviceId", deviceId);
		deviceInfo.put("deviceName", deviceName);
		return deviceInfo;
	}

	public void uninstallAndroidApp() {
		Process p = null;
		System.out.println("- - - - - - - - Uninstalling APP - - - - - - - - ");
		try {
			p = Runtime.getRuntime().exec("adb uninstall " + packageName);
			p.waitFor();
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

	public String getAppVersion() {
		Process p = null;
		String appVersion = null;
		System.out.println("- - - - - - - - Getting App Version- - - - - - - - ");
		try {
			p = Runtime.getRuntime()
					.exec(String.format("adb shell dumpsys package %s | grep versionName", packageName));
			InputStream stdin = p.getInputStream();
			InputStreamReader isr = new InputStreamReader(stdin);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.isEmpty())
					break;
				appVersion = line.split("=")[1].trim();
				System.out.println("<-------App Version:" + appVersion + "---------->");
			}
			int exitVal = p.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return appVersion != null ? appVersion.replace(".", "") : null;
	}

	public String getDevicePlatform() {
		Process p = null;
		String platformVersion = null;
		System.out.println("- - - - - - - - Getting Platform Version- - - - - - - - ");
		try {
			p = Runtime.getRuntime().exec("adb shell getprop ro.build.version.release");
			InputStream stdin = p.getInputStream();
			InputStreamReader isr = new InputStreamReader(stdin);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.isEmpty())
					break;
				platformVersion = line;
				System.out.println("<-------Platform Version:" + line + "---------->");
			}
			int exitVal = p.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return platformVersion;
	}
	
}
