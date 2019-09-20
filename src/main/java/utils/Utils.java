package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import logger.Log;
import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.util.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import reporters.ExtentManager;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
	public static final long IMPLICIT_WAIT = 45;
	protected static ArrayList<String> methodToBeExecuted = new ArrayList<>();
	private static ExtentTest test;
	private static ExtentReports extent;

	/* This function will initialize the ExtentReport object */
	public static void initializeExtentReport() {
		extent = ExtentManager.getReporter();
	}

	/* This function will initialize the ExtentTest object */
	public static void initializeExtentTest(String methodName) {
		test = extent.createTest(methodName + " | " + GlobalVars.browser,
				methodName);
		// test.assignAuthor("TTN || TestUser");
		// test.assignCategory(this.getClass().getSimpleName());
	}

	/*
	 * This function will be called after every test method
	 */
	public static void closeExtentTest() {
		test.getExtent().flush();
	}

	/* This function will log function level logs based on the result */
	public static void logFunctionLevelLogs(boolean result, String functionName) {
		if (result)
			Log.info(functionName + ": successful!");
		else
			Log.error(functionName + ": failed!");
	}

	/* This function will log each step of a test case */
	public static void logStepInfo(String message) {
		test.log(Status.PASS, message);
		Log.info("Message: " + message);
		Reporter.log(message);
	}

	/* Function to assert and log the steps info in extent report */
	public static void assertAndlogStepInfo(boolean isResult,
			boolean isSoftAssert, String stepInfo) throws IOException,
			InterruptedException {
		logStepInfo(isResult, stepInfo);
		if (isSoftAssert) {
			SoftAssert sAssert = new SoftAssert();
			sAssert.assertTrue(isResult, stepInfo);
		} else {
			Assert.isTrue(isResult, stepInfo);
		}
	}

	/* Function to log the steps info in extent report */
	public static void logStepInfo(boolean isResult, String stepInfo) {
		if (isResult)
			test.log(Status.PASS, stepInfo + " | Status: Pass");
		else
			test.log(Status.FAIL, stepInfo + " | Status: Fail");
	}

	/*
	 * This function will return the formatted file name with date and time
	 * appended
	 */
	public static String getFileName(String file) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		Calendar cal = Calendar.getInstance();
		return file + dateFormat.format(cal.getTime());
	}

	/* This function will return the absolute path */
	public static String getPath() {
		String path = "";
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("\\\\+", "/");
		return path;
	}

	/* capturing screenshot */
	public static void captureScreenshot(ITestResult result)
			throws IOException, InterruptedException {
		try {
			String screenshotName = Utils.getFileName(result.getName());
			File screenshot = ((TakesScreenshot) GlobalVars.driver)
					.getScreenshotAs(OutputType.FILE);
			String path = Utils.getPath();
			String screen = path + "/screenshots/" + screenshotName + ".png";
			File screenshotLocation = new File(screen);
			FileUtils.copyFile(screenshot, screenshotLocation);
			Thread.sleep(2000);
			InputStream is = new FileInputStream(screenshotLocation);
			byte[] imageBytes = IOUtils.toByteArray(is);
			Thread.sleep(2000);
			Reporter.log("<a href= '" + screen + "'target='_blank' ><img src='"
					+ screen + "'>" + screenshotName + "</a>");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	/**
	 * This function runs the adb shell command to clear previous logs
	 */
	public static void clearPreviousLogs() {
		try {
			ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "adb",
					"logcat", "-c");
			;
			Process pc = pb.start();
			try {
				pc.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This function runs the adb shell command to get the adb logs
	 * 
	 * @Pre-requisite: Android build must be a debug build with log enabled.
	 * @throws InterruptedException
	 */
	public static String getAnalyticsEventsLogs(String commandFilter) {

		StringBuilder buffer = new StringBuilder();
		try {
			clearPreviousLogs();
			ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "adb",
					"logcat", "-d", "-e", commandFilter);
			Process pc = pb.start();
			InputStream is = pc.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String output = null;

			while ((output = br.readLine()) != null) {
				buffer.append(output);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return buffer.toString();
	}

	public static int getMaxRetryCount() {
		try {
			return Integer.parseInt(GlobalVars.prop
					.getProperty(Constants.MAX_RETRY));
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

}
