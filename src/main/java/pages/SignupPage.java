package pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import logger.Log;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.CommonFunctions;

public class SignupPage {
	private WebDriver driver;
	private CommonFunctions oCommonFunctions;
	LoginPage oLoginPage;

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
	private static WebElement companyTextBox;
	@FindBy(id = "email")
	private static WebElement emailTextBox;
	@FindBy(id = "password")
	private static WebElement passwordTextBox;
	@FindBy(xpath = "//span[@class='checkmark']")
	private static WebElement termsCheckBox;
	@FindBy(xpath = "//button[text()='Sign up']") 
	private static WebElement signUpButton;
	@FindBy(xpath = "//div[contains(text(),'Thank you for your request')]")
	private static WebElement signUpSuccessMessageText;
	@FindBy(xpath = "//li[text()='The email has already been taken.']")
	private static WebElement emailAlreadyTakenMessageText;

	public boolean signUp(String firstname, String lastname,String company,String email,String password) {
		boolean isSignUpSuccess=false;
		try {
			
			 oCommonFunctions.sendKey(firstNameTextBox, firstname, 5);
			 oCommonFunctions.sendKey(lastNameTextBox, lastname, 5);
			 oCommonFunctions.sendKey(companyTextBox, company, 5);
			 DateFormat dateFormat = new SimpleDateFormat("yy_MM_dd_hh_mm_ss");
			 Date date = new Date();
			 email=dateFormat.format(date)+email;
			 oCommonFunctions.sendKey(emailTextBox, email, 5);
			 oCommonFunctions.sendKey(passwordTextBox, password, 5);
			 oCommonFunctions.clickElement(termsCheckBox, 5);
			 oCommonFunctions.clickElement(signUpButton, 5);
			 isSignUpSuccess=oCommonFunctions.isElementDisplayed(signUpSuccessMessageText, 20);
			

		} catch (Exception e) {
			Log.error("Exception occurred in signUp method" + e.getMessage());
		}
		return isSignUpSuccess;
	}
	
	public boolean signUpFromExistingEmail(String firstname, String lastname,String company,String email,String password) {
		boolean isEmailAlreadyTakenMessageDisplayed=false;
		try {
			
			 oCommonFunctions.sendKey(firstNameTextBox, firstname, 5);
			 oCommonFunctions.sendKey(lastNameTextBox, lastname, 5);
			 oCommonFunctions.sendKey(companyTextBox, company, 5);
			 oCommonFunctions.sendKey(emailTextBox, email, 5);
			 oCommonFunctions.sendKey(passwordTextBox, password, 5);
			 oCommonFunctions.clickElement(termsCheckBox, 5);
			 oCommonFunctions.clickElement(signUpButton, 5);
			 isEmailAlreadyTakenMessageDisplayed=oCommonFunctions.isElementDisplayed(emailAlreadyTakenMessageText, 20);
			

		} catch (Exception e) {
			Log.error("Exception occurred in signUpFromExistingEmail method" + e.getMessage());
		}
		return isEmailAlreadyTakenMessageDisplayed;
	}

}
