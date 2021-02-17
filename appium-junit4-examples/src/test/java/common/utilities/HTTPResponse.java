package common.utilities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import common.launchsetup.BaseTest;

public class HTTPResponse {
	static URL url = null;
	static String target = null;

	public static String getLocationFor301Or302StatusCode(String currentUrl) {
		target = "";
		System.setProperty("jsse.enableSNIExtension", "false");
		String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36";
		if (BaseTest.platform != null && BaseTest.platform.equalsIgnoreCase("WAP"))
			userAgent = "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Mobile Safari/537.36";
		URL url = null;
		try {
			url = new URL(currentUrl);
		} catch (MalformedURLException e) {
			System.out.println("Failed while checking for ==> " + currentUrl);
			e.printStackTrace();
		}
		HttpURLConnection http = null;
		try {
			http = (HttpURLConnection) url.openConnection();
			http.setInstanceFollowRedirects(false);
			HttpURLConnection.setFollowRedirects(false);
			http.setConnectTimeout(15000);
		} catch (IOException e1) {
			System.out.println("Failed while checking for ==> " + currentUrl);
			e1.printStackTrace();
		}
		try {
			http.setRequestMethod("GET");
		} catch (ProtocolException e) {
			System.out.println("Failed while checking for ==> " + currentUrl);
			e.printStackTrace();
		}
		http.addRequestProperty("User-Agent", userAgent);
		try {
			http.connect();
		} catch (IOException e) {
			System.out.println("Failed while checking for ==> " + currentUrl);
			e.printStackTrace();
		}
		int status = 0;

		try {
			status = http.getResponseCode();

			System.out.println("status:" + status);
			try {
				if (status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_MOVED_TEMP) {
					target = http.getHeaderField("Location");
					return getLocationFor301Or302StatusCode(target);
				} else {
					target = status == 200 ? currentUrl : "";
				}
			} catch (Exception e) {
				System.out.println("Failed while checking for ==> " + currentUrl);
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("this is the target returned: " + target);
		return target;
	}

	public static int checkResponseCode(String href) {
		if (!href.startsWith("http")) {
			href = "https:" + href;
		}
		System.setProperty("jsse.enableSNIExtension", "false");
		String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36";

		if (BaseTest.platform != null && BaseTest.platform.equalsIgnoreCase("WAP"))
			userAgent = "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Mobile Safari/537.36";

		try {
			url = new URL(href);
		} catch (MalformedURLException | IllegalArgumentException e) {
			System.out.println("Failed while checking for ==> " + href);
			e.printStackTrace();
		}
		HttpURLConnection http = null;
		try {
			http = (HttpURLConnection) url.openConnection();
			http.setConnectTimeout(15000);
			http.setInstanceFollowRedirects(false);
		} catch (IOException | IllegalArgumentException e1) {
			System.out.println("Failed while checking for ==> " + href);
			e1.printStackTrace();
		}
		try {
			http.setRequestMethod("GET");
		} catch (ProtocolException | IllegalArgumentException e) {
			System.out.println("Failed while checking for ==> " + href);
			e.printStackTrace();
		}
		http.addRequestProperty("User-Agent", userAgent);

		try {
			http.connect();
		} catch (IOException | IllegalArgumentException e) {
			System.out.println("Failed while checking for ==> " + href);
			e.printStackTrace();
			return 0;
		}
		int responseCode = 0;
		try {
			responseCode = http.getResponseCode();
		} catch (IOException | IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int counter = 0;
			while (responseCode != 200 && counter < 5) {

				if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
						|| responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
					URL base = new URL(href);
					String location = http.getHeaderField("Location");
					URL next = new URL(base, location);
					href = next.toExternalForm();
					url = new URL(href);
					http = (HttpURLConnection) url.openConnection();
					http.connect();
				}
				try {
					responseCode = http.getResponseCode();
				} catch (Exception e) {

				}
				counter++;
			}
		} catch (Exception e) {
			System.out.println("Failed while checking for ==> " + href);
			e.printStackTrace();
		}
		return responseCode;
	}

	public static URL getUrl() {
		return url;
	}

}
