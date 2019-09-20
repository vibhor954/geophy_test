package pages;

import logger.Log;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonFunctions;

public class SignupPage {
	private static WebDriver driver;
	static CommonFunctions oCommonFunctions = null;

	public SignupPage() {
	}

	public SignupPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		oCommonFunctions = new CommonFunctions();

	}

	@FindBy(id = "first-name")
	private static WebElement firstNameTextBox;
	@FindBy(name = "last_name")
	private static WebElement lastNameTextBox;
	@FindBy(xpath = "//input[@id='company']")
	private static WebElement emailTextBox;
	@FindBy(id = "password")
	private static WebElement passwordTextBox;
	@FindBy(id = "accept_terms")
	private static WebElement termsCheckBox;
	@FindBy(xpath = "//button[text()='Sign up']")
	private static WebElement signUpButton;

	public void login(String username, String password) {
		try {

		} catch (Exception e) {
			Log.error("Exception occurred in Login method" + e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean verifySearchPagePostLogin() {
		boolean isUserLoggedIn = false;
		try {
			isUserLoggedIn = driver.getPageSource().contains("Welcome");
		} catch (Exception e) {
			Log.error("Exception occurred in Login method" + e.getMessage());
			e.printStackTrace();
		}
		return isUserLoggedIn;
	}

}
