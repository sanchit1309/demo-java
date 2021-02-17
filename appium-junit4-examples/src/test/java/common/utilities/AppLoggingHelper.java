package common.utilities;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.logging.LogEntry;

import io.appium.java_client.AppiumDriver;

public class AppLoggingHelper {
	private static String logPath = System.getProperty("user.dir") + "/androidlogs/";

	public static void captureLog(AppiumDriver<?> appDriver, String testName) throws Exception {
		DateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH-mm-ss");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		System.out.println(appDriver.getSessionId() + ": Saving device crash log...");
		List<LogEntry> allLogEntries = appDriver.manage().logs().get("logcat").filter(Level.ALL);
		List<LogEntry> logEntries=allLogEntries.subList((allLogEntries.size()-10), allLogEntries.size());
		if (!logEntries.isEmpty()) {
			File logFile = new File(logPath + reportDate + "_" + testName + ".txt");
			logFile.createNewFile();
			PrintWriter log_file_writer = new PrintWriter(logFile);
			log_file_writer.println(logEntries);
			log_file_writer.flush();
			System.out.println(appDriver.getSessionId() + ": Saving device crash log - Done.");
			log_file_writer.close();
		}
	}

	public static void cleanAllLogs() {
			File logs = new File(logPath);
			if (logs.exists()) {
				try {
					FileUtils.cleanDirectory(logs);
				} catch (IOException e) {
					e.printStackTrace();
				
			}
		}
	}

}
