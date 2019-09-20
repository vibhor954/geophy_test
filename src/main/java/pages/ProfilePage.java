package pages;

import logger.Log;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import utils.CommonFunctions;
import utils.Constants;
import utils.GlobalVars;

public class ProfilePage {

	private static WebDriver driver;
	static CommonFunctions oCommonFunctions = null;

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
	private static WebElement EmailTextBox;
	@FindBy(xpath = "//nav[@role='navigation']//span")
	private static WebElement welcomeText;
	
	
	public void navigateToProfilePage(){
		driver.navigate().to(GlobalVars.prop.getProperty(Constants.PROFILE_PAGE_URL));
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
			String firstName_welcomeMessage = arr1[1].trim();
			String lastName_welcomeMessage = arr1[2].trim().replace(".", "");
			String email = oCommonFunctions.getAttribute(EmailTextBox, "value",
					10);

			String firstName = oCommonFunctions.getAttribute(firstNameTextBox,
					"value", 10);
			String lastName = oCommonFunctions.getAttribute(lastNameTextBox,
					"value", 10);
			boolean isFirstNameMatched = firstName_welcomeMessage
					.equals(firstName);
			boolean isLastNameMatched = lastName_welcomeMessage
					.equals(lastName);
			boolean isEmailMatched = email.equals(username);

			if (isFirstNameMatched == true && isLastNameMatched == true
					&& isEmailMatched == true) {
				isProfileDetailsValidated = true;
			}

		}

		catch (Exception e) {
			Log.error("Exception occurred in verifySearchHistory method"
					+ e.getMessage());
			e.printStackTrace();
		}

		return isProfileDetailsValidated;
	}

}
