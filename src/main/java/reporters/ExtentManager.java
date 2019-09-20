package reporters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

	private static ExtentReports extent;
	private static String extentpath;

	private ExtentManager() {
		
	}
	public static String getExtentpath() {
		return extentpath;
	}

	public static void setExtentpath(String extentpath) {
		ExtentManager.extentpath = extentpath;
	}

	public static synchronized ExtentReports getReporter() {
		if (extent == null) {
			DateFormat dateFormat = new SimpleDateFormat("yy_MM_dd_hh_mm_ss");
			Date date = new Date();

			extentpath = System.getProperty("user.dir") + "/ExtentReports/"
					+ "ExtentReport_" + dateFormat.format(date) + "_" + ".html";
			ExtentHtmlReporter html = new ExtentHtmlReporter(extentpath);

			extent = new ExtentReports();
			extent.attachReporter(html);

		}
		return extent;

	}

}
