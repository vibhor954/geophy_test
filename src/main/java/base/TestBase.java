package base;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import logger.Log;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import pages.HistoryPage;
import pages.LoginPage;
import pages.ProfilePage;
import pages.ResetPasswordPage;
import pages.SearchPage;
import pages.SignupPage;
import utils.*;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase extends GlobalVars {

	private static ExtentTest test;
	private static final Logger logger = LoggerFactory
			.getLogger(TestBase.class);
	String className = null;
	protected Map<String, DataElements> dataElementMap = null;
	protected LoginPage oLoginPage = null;
	protected SearchPage oSearchPage = null;
	protected HistoryPage oHistoryPage = null;
	protected ProfilePage oProfilePage = null;
	protected SignupPage oSignupPage = null;
	protected ResetPasswordPage oResetPasswordPage=null;
	DataReader oDataReader = null;
	TestBase oTestBase = null;
	CommonFunctions ocommonFunctions = null;
	static String path = null;
	private static final String USER_DIR = "user.dir";

	public TestBase() {

		initGlobalVars();
		initializeDriver();
	}

	public static void initGlobalVars() {
		try {
			prop = new Properties();
			workingDir = System.getProperty(USER_DIR);
			String configPropFilePath = workingDir
					+ "//src//main//java//utils//config.properties";
			FileInputStream ip = new FileInputStream(configPropFilePath);
			prop.load(ip);
			// This is to initialize the test data before execution of any test
			// case
			DataReader oDataReader = new DataReader();
			oDataReader.setupDataSheet();
		} catch (Exception e) {
			logger.error("Exception in initGlobalVars ::: ", e);
		}
	}

	public static void initializeDriver() {

		switch (browser) {
		case "chrome":
			path = System.getProperty(USER_DIR) + "/" + GlobalVars.chromedriver;
			try {
				if (driver == null) {
					System.setProperty("webdriver.chrome.driver", path);
					driver = new ChromeDriver();
					driver.manage().window().maximize();
					driver.manage()
							.timeouts()
							.implicitlyWait(Utils.IMPLICIT_WAIT,
									TimeUnit.SECONDS);
				}
			} catch (Exception e) {
				logger.error("Exception in initializeDriver ::: ", e);
			}
			break;

		case "firefox":
			path = System.getProperty(USER_DIR) + "/" + GlobalVars.geckodriver;
			try {
				if (driver == null) {
					System.setProperty("webdriver.chrome.driver", path);
					driver = new FirefoxDriver();
					driver.manage()
							.timeouts()
							.implicitlyWait(Utils.IMPLICIT_WAIT,
									TimeUnit.SECONDS);
				}
			}

			catch (Exception e) {
				logger.error("Exception in initializeDriver ::: ", e);
			}
			break;
			
		case "edge":
			path = System.getProperty(USER_DIR) + "/" + GlobalVars.msedgedriver;
			try {
				if (driver == null) {
					System.setProperty("webdriver.edge.driver", path);
					driver = new EdgeDriver();
					driver.manage()
							.timeouts()
							.implicitlyWait(Utils.IMPLICIT_WAIT,
									TimeUnit.SECONDS);
				}
			}

			catch (Exception e) {
				logger.error("Exception in initializeDriver ::: ", e);
				e.printStackTrace();
			}
			break;

		default:
			throw new IllegalStateException("Unexpected value: " + browser);

		}
		driver.get(GlobalVars.url);
	}

	@BeforeSuite
	public void before() {
		Utils.initializeExtentReport();
		DOMConfigurator.configure("log4j.xml");
		Log.initializeLogProperties();
	}

	@BeforeClass
	public void classDataInitializer() {
		className = this.getClass().getSimpleName();
		oDataReader = new DataReader();
		dataElementMap = oDataReader.getClassData(className);
	}

	@BeforeMethod
	public void initializeExtentTest(Method method) {
		Utils.initializeExtentTest(method.getName());
		Log.startTestCase(method.getName());
	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException,
			InterruptedException {

		if (result.getStatus() == ITestResult.FAILURE) {
			Utils.captureScreenshot(result);
		}
		Log.endTestCase(result.getTestName());
		logger.info("****************************************");
	}

	@AfterSuite
	public void tearDownSuite(ITestContext context) {
		Utils.closeExtentTest();
		// calling the mail send method only if mail flag is true
		if (mailFlag)
			SendMailSSLWithAttachmentUtil.sendEmail(context);
		driver.quit();

	}

	public void logStepInfo(String message) {
		test.log(Status.PASS, message);
		logger.info("Message: {}", message);
		Reporter.log(message);
	}

}
