package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

//This class will have all the global variables to be used across the classes and packages
public class GlobalVars {
	public static WebDriver driver;
	public static Properties prop;
	public static String workingDir;
	public static String browser;
	public static String chromedriver;
	public static String geckodriver;
	public static Map<String, DataElements> dataElementMap = null;
	public static String url;
	public static List<String> mailRecipientList = new ArrayList<String>();
	public static boolean jiraFlag = true;
	public static boolean mailFlag = false;
}
