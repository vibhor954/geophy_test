package pages;

import logger.Log;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.CommonFunctions;
import utils.Constants;
import utils.GlobalVars;

public class ResetPasswordPage {

	private WebDriver driver;
	private CommonFunctions oCommonFunctions;

	public ResetPasswordPage() {
	}

	public ResetPasswordPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		oCommonFunctions = new CommonFunctions();

	}

	@FindBy(id = "current_password")
	private static WebElement currentPasswordTextBox;
	@FindBy(name = "new_password")
	private static WebElement newPasswordTextBox;
	@FindBy(name = "confirm_password")
	private static WebElement confirmPasswordTextBox;
	@FindBy(xpath = "//nav[@role='navigation']//span")
	private static WebElement welcomeText;
	@FindBy(xpath = "//input[@value='Update']")
	private static WebElement updateButton;
	@FindBy(xpath = "//label[text()='Your password has been updated']")
	private static WebElement passwordUpdatedText;

	public boolean resetPassword(String password) {
		boolean isPasswordReset = false;
		try {

			oCommonFunctions.sendKey(currentPasswordTextBox, password, 5);
			oCommonFunctions.sendKey(newPasswordTextBox, password, 5);
			oCommonFunctions.sendKey(confirmPasswordTextBox, password, 5);
			oCommonFunctions.clickElement(updateButton, 5);
			isPasswordReset = oCommonFunctions.isElementDisplayed(
					passwordUpdatedText, 5);

		} catch (Exception e) {
			Log.error("Exception occurred in updateProfile method"
					+ e.getMessage());
		}
		return isPasswordReset;
	}

}
