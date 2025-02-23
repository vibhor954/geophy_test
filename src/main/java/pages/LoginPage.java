package pages;

import logger.Log;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.CommonFunctions;
import utils.Constants;
import utils.GlobalVars;

public class LoginPage
{
    private  WebDriver driver;
    private CommonFunctions oCommonFunctions;
    public LoginPage() {
    }
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        oCommonFunctions=new CommonFunctions();

    }


    @FindBy(id = "email")
    private static WebElement emailTextBox;
    @FindBy(name = "password")
    private static WebElement passwordTextBox;
    @FindBy(xpath = "//button[text()='Log in']")
    private static WebElement loginButton;
    @FindBy(xpath = "//label[contains(text(),'error with your e-mail or password')]")
    private static WebElement loginErrorMessage;
    @FindBy(xpath = "//a[text()='Sign up']")
    private static WebElement signUpButton;
    
    
    public void login(String username, String password) {
        try {
            oCommonFunctions.sendKey(emailTextBox, username, 5);
            oCommonFunctions.sendKey(passwordTextBox, password, 5);
            oCommonFunctions.clickElement(loginButton, 20);
        } catch (Exception e) {
            Log.error("Exception occurred in Login method"+e.getMessage());
        }
    }

    public boolean verifySearchPagePostLogin() {
        boolean isUserLoggedIn=false;
        try {
        	isUserLoggedIn=driver.getPageSource().contains("Welcome");
        } catch (Exception e) {
            Log.error("Exception occurred in Login method"+e.getMessage());
        }
        return isUserLoggedIn;
    }
    
    public boolean isLoginErrorMessageDisplayed() {
        boolean isLoginErrorMessageDisplayed=false;
        try {
        	isLoginErrorMessageDisplayed=oCommonFunctions.isElementDisplayed(loginErrorMessage, 10);
        } catch (Exception e) {
            Log.error("Exception occurred in Login method"+e.getMessage());
        }
        return isLoginErrorMessageDisplayed;
    }
    
    public void clickSignUp() {
        try {
        	oCommonFunctions.clickElement(signUpButton, 10);
        	oCommonFunctions.waitForURLContains(GlobalVars.prop.getProperty(Constants.signupPAGEURL), 10);
        } catch (Exception e) {
            Log.error("Exception occurred in Login method"+e.getMessage());
        }
        
    }
}

