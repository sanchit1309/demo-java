package common.utilities.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.xerces.impl.dv.util.Base64;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReporterNG implements IReporter {
	private ExtentReports extent;

	public synchronized void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		extent = new ExtentReports(outputDirectory + File.separator + suites.get(0).getXmlSuite().getName() + ".html", true);
		extent.loadConfig(new File(System.getProperty("user.dir") + File.separator + "extent-config.xml"));
		extent.assignProject(suites.get(0).getXmlSuite().getName());

		// String host = GenericMethods.getHostName();
		// if (host.equalsIgnoreCase("automationjenkins.localdomain"))
		extent.x("http://etqa.monitoring.indiatimes.com:8080/job/ET-Main_Web/ws/screenshots/");

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();
				buildTestNodes(context.getPassedTests(), LogStatus.PASS);
				buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
				buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
			}
		}

		extent.flush();
		extent.close();
	}

	private synchronized void buildTestNodes(IResultMap tests, LogStatus status) {
		ExtentTest test;
		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				test = extent.startTest(result.getMethod().getMethodName()).assignCategory(getTestClassName(result.getTestClass().getName()));

				if (result.getMethod().getDescription() != null)
					test.getTest().setDescription(result.getMethod().getDescription());

				test.getTest().setStartedTime(getTime(result.getStartMillis()));
				test.getTest().setEndedTime(getTime(result.getEndMillis()));

				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				if (result.getThrowable() != null) {
					String imagepath = "test" + ".png";
					File f = new File(imagepath);
					if (f.exists()) {
						String message = result.getThrowable().getMessage();
						String image = test.addBase64ScreenShot(Base64image(imagepath));
						test.log(status, message + image);
					} else {
						String message = result.getThrowable().getMessage();
						test.log(status, message);
					}

				} else {
					test.log(status, "Test " + status.toString().toLowerCase() + "ed");
				}
				extent.endTest(test);
			}
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	public String getTestClassName(String testName) {
		String[] reqTestClassname = testName.split("\\.");
		int i = reqTestClassname.length - 1;
		return reqTestClassname[i];
	}

	public String Base64image(String imagepath) {
		String imagestring = null;
		File file = new File(imagepath);
		try {
			FileInputStream inputStream = new FileInputStream(file);
			byte imageData[] = new byte[(int) file.length()];
			inputStream.read(imageData);

			imagestring = "data:image/png;base64," + Base64.encode(imageData);

			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imagestring;
	}
}