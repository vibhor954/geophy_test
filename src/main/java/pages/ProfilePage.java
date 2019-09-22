package pages;

import logger.Log;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.CommonFunctions;
import utils.Constants;
import utils.GlobalVars;

public class ProfilePage {

	private WebDriver driver;
	private CommonFunctions oCommonFunctions;

	public ProfilePage() {
	}

	public ProfilePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		oCommonFunctions = new CommonFunctions();

	}

	@FindBy(id = "first_name")
	private static WebElement firstNameTextBox;
	@FindBy(name = "last_name")
	private static WebElement lastNameTextBox;
	@FindBy(id = "email")
	private static WebElement emailTextBox;
	@FindBy(xpath = "//nav[@role='navigation']//span")
	private static WebElement welcomeText;
	@FindBy(id = "company")
	private static WebElement companyTextBox;
	@FindBy(xpath = "//input[@value='Update']")
	private static WebElement updateButton;
	@FindBy(xpath = "//label[text()='Your profile has been updated!']")
	private static WebElement profileUpdatedText;
	@FindBy(xpath = "//a[text()='Reset Password']")
	private static WebElement resetPasswordLink;

	public void navigateToProfilePage() {
		driver.navigate().to(
				GlobalVars.prop.getProperty(Constants.profilePAGEURL));
	}

	public boolean verifyProfileDetails(String username) {
		boolean isProfileDetailsValidated = false;

		try {
			navigateToProfilePage();
			String welcomeMessageText = oCommonFunctions.getElementText(
					welcomeText, 20);
			String[] arr = welcomeMessageText.split(",");
			String arrayText = arr[1];
			String[] arr1 = arrayText.split(" ");
			String firstNameWelcomeMessage = arr1[1].trim();
			String lastNameWelcomeMessage = arr1[2].trim().replace(".", "");
			String email = oCommonFunctions.getAttribute(emailTextBox, "value",
					10);

			String firstName = oCommonFunctions.getAttribute(firstNameTextBox,
					"value", 10);
			String lastName = oCommonFunctions.getAttribute(lastNameTextBox,
					"value", 10);
			boolean isFirstNameMatched = firstNameWelcomeMessage
					.equals(firstName);
			boolean isLastNameMatched = lastNameWelcomeMessage.equals(lastName);
			boolean isEmailMatched = email.equals(username);

			if (isFirstNameMatched && isLastNameMatched && isEmailMatched) {
				isProfileDetailsValidated = true;
			}

		}

		catch (Exception e) {
			Log.error("Exception occurred in verifySearchHistory method"
					+ e.getMessage());
		}

		return isProfileDetailsValidated;
	}

	public boolean updateProfile(String company) {
		boolean isProfileUpdated = false;
		try {

			oCommonFunctions.sendKey(companyTextBox, company, 5);
			oCommonFunctions.clickElement(updateButton, 5);
			isProfileUpdated = oCommonFunctions.isElementDisplayed(
					profileUpdatedText, 5);

		} catch (Exception e) {
			Log.error("Exception occurred in updateProfile method"
					+ e.getMessage());
		}
		return isProfileUpdated;
	}

	public void clickResetPassword() {
		oCommonFunctions.clickElement(resetPasswordLink, 10);
	}

}
