
import org.testng.annotations.Test;
import pages.HistoryPage;
import pages.LoginPage;
import pages.ProfilePage;
import pages.ResetPasswordPage;
import pages.SearchPage;
import pages.SignupPage;
import base.TestBase;
import utils.*;
import org.testng.Assert;

public class DemoTest extends TestBase {

	/**
	 * @summary:
	 * 
	 *           Step-1: Go to login page, enter the user name and password and
	 *           click login button 
	 *           Step-2: Verify the user has successfully logged
	 *           in
	 *
	 */
	@Test(priority = 1)
	public void loginTestwithValidCredentials() {
		String username = "";
		String password = "";
		String currentMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		oLoginPage = new LoginPage(driver);
		oSearchPage = new SearchPage(driver);
		username = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[0];
		password = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[1];
		oLoginPage.login(username, password);
		boolean isUserLoggedIn = oLoginPage.verifySearchPagePostLogin();
		Utils.logStepInfo(isUserLoggedIn, "Login and Verify Post Login");
		Assert.assertTrue(isUserLoggedIn);
		oSearchPage.logout();
		oLoginPage = null;
		oSearchPage = null;
	}
	
	/**
	 * @summary:
	 * 
	 *          Step-1: Go to login page, enter the invalid credentials and
	 *           click login button
	 *          Step-2: Verify the error message display
	 *
	 */

	@Test(priority = 2)
	public void loginTestwithInValidCredentials() {
		String username = "";
		String password = "";
		oLoginPage = new LoginPage(driver);
		String currentMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		username = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[0];
		password = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[1];
		oLoginPage.login(username, password);
		boolean isLoginErrorMessageDisplayed = oLoginPage
				.isLoginErrorMessageDisplayed();
		Utils.logStepInfo(isLoginErrorMessageDisplayed,
				"Validate Error Message on Invalid Credentials");
		Assert.assertTrue(isLoginErrorMessageDisplayed);
	}
	
	/**
	 * @summary:
	 * 
	 *          Step-1: Login to the application
	 *          Step-2: Enter Address, Net Operating Income, Number of Units, Year of Construction and click Run Valuation button
	 *          Step-3: Verify the successful valuation run
	 *
	 */

	@Test(priority = 3)
	public void runValuationTest() {
		String username = "";
		String password = "";
		String address = "";
		String netOperatingIncome = "";
		String numberOfUnits = "";
		String yearOfConstruction = "";
		String occupancy = "";
		oLoginPage = new LoginPage(driver);
		oSearchPage = new SearchPage(driver);
		boolean isrunValuationSuccess = false;
		String currentMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();

		address = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[2];
		netOperatingIncome = dataElementMap.get(currentMethodName).getParams()
				.trim().split(",")[3];
		numberOfUnits = dataElementMap.get(currentMethodName).getParams()
				.trim().split(",")[4];
		yearOfConstruction = dataElementMap.get(currentMethodName).getParams()
				.trim().split(",")[5];
		occupancy = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[6];

		username = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[0];
		password = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[1];
		oLoginPage.login(username, password);

		isrunValuationSuccess = oSearchPage.runValuation(address,
				netOperatingIncome, numberOfUnits, yearOfConstruction,
				occupancy);

		Utils.logStepInfo(isrunValuationSuccess,
				"Validation for Run Valuation Success");
		Assert.assertTrue(isrunValuationSuccess);
		oSearchPage.logout();

	}
	
	/**
	 * @summary:
	 * 
	 *          Step-1: Login to the application
	 *          Step-2: Run Valuation
	 *          Step-3: Go to History Page
	 *          Step-4: Verify the recent search is displayed on History Page
	 *
	 */

	@Test(priority = 4)
	public void verifySearchHistoryTest() {
		String username = "";
		String password = "";
		String address = "";
		String netOperatingIncome = "";
		String numberOfUnits = "";
		String yearOfConstruction = "";
		String occupancy = "";
		String addressSearchTerm = "";
		oLoginPage = new LoginPage(driver);
		oSearchPage = new SearchPage(driver);
		oHistoryPage = new HistoryPage(driver);
		boolean isRecentSearchDisplayed = false;
		String currentMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();

		address = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[2];
		netOperatingIncome = dataElementMap.get(currentMethodName).getParams()
				.trim().split(",")[3];
		numberOfUnits = dataElementMap.get(currentMethodName).getParams()
				.trim().split(",")[4];
		yearOfConstruction = dataElementMap.get(currentMethodName).getParams()
				.trim().split(",")[5];
		occupancy = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[6];
		addressSearchTerm = dataElementMap.get(currentMethodName).getParams()
				.trim().split(",")[7];

		username = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[0];
		password = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[1];
		oLoginPage.login(username, password);

		oSearchPage.runValuation(address, netOperatingIncome, numberOfUnits,
				yearOfConstruction, occupancy);
		oHistoryPage.navigateToHistoryPage();
		oHistoryPage.verifySearchHistory(addressSearchTerm, netOperatingIncome,
				yearOfConstruction, numberOfUnits);
		isRecentSearchDisplayed = oHistoryPage.verifySearchHistory(
				addressSearchTerm, netOperatingIncome, yearOfConstruction,
				numberOfUnits);
		Utils.logStepInfo(isRecentSearchDisplayed,
				"Validation for Recent Search Display");
		Assert.assertTrue(isRecentSearchDisplayed);
		oSearchPage.logout();

	}
	
	/**
	 * @summary:
	 * 
	 *          Step-1: Login to the application
	 *          Step-2: Go to Profile Page
	 *          Step-3: Verify the profile fields are getting displayed in accordance with the logged in user
	 *
	 */

	@Test(priority = 5)
	public void verifyProfileDetailsTest() {
		String username = "";
		String password = "";
		oLoginPage = new LoginPage(driver);
		oProfilePage = new ProfilePage(driver);
		boolean isProfileDetailsValidated = false;
		String currentMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();

		username = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[0];
		password = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[1];
		oLoginPage.login(username, password);
		isProfileDetailsValidated = oProfilePage.verifyProfileDetails(username);

		Utils.logStepInfo(isProfileDetailsValidated,
				"Profile Details Validation ");
		Assert.assertTrue(isProfileDetailsValidated);
		oSearchPage.logout();
	}

	/**
	 * @summary:
	 * 
	 *          Step-1: Login to the application
	 *          Step-2: Click Logout
	 *          Step-3: Verify the successful logout
	 *
	 */
	@Test(priority = 6)
	public void logoutTest() {
		String username = "";
		String password = "";
		oLoginPage = new LoginPage(driver);
		oSearchPage = new SearchPage(driver);
		boolean isUserLoggedOut = false;
		String currentMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();

		username = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[0];
		password = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[1];
		oLoginPage.login(username, password);
		isUserLoggedOut = oSearchPage.logout();

		Utils.logStepInfo(isUserLoggedOut, "User Logout Validation ");
		Assert.assertTrue(isUserLoggedOut);

	}
	/**
	 * @summary:
	 * 
	 *          Step-1: Go to Sign up page
	 *          Step-2: Enter First Name, Last Name, Company, Email and Password
	 *          Step-3: Click Sign up button
	 *          Step-4: Verify the successful sign up message
	 *
	 */
	@Test(priority = 7)
	public void signUpTest() {
		String firstName = "";
		String lastName = "";
		String company = "";
		String email = "";
		String password = "";
		oLoginPage = new LoginPage(driver);
		oSignupPage = new SignupPage(driver);
		boolean isSignUpSuccess = false;
		String currentMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();

		firstName = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[0];
		lastName = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[1];
		company = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[2];
		email = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[3];
		password = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[4];
		oLoginPage.clickSignUp();
		isSignUpSuccess = oSignupPage.signUp(firstName, lastName, company,
				email, password);

		Utils.logStepInfo(isSignUpSuccess, "Sign Up Validation ");
		Assert.assertTrue(isSignUpSuccess);

	}
	/**
	 * @summary:
	 * 
	 *          Step-1: Go to Sign up page
	 *          Step-2: Enter First Name, Last Name, Company, Email(already existing) and Password
	 *          Step-3: Click Sign up button
	 *          Step-4: Verify the email id already exists message
	 *
	 */

	@Test(priority = 8)
	public void signUpFromExistingEmailIdTest() {
		String firstName = "";
		String lastName = "";
		String company = "";
		String email = "";
		String password = "";
		oLoginPage = new LoginPage(driver);
		oSignupPage = new SignupPage(driver);
		boolean isEmailAlreadyTakenMessageDisplayed = false;
		String currentMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();

		firstName = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[0];
		lastName = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[1];
		company = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[2];
		email = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[3];
		password = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[4];
		oLoginPage.clickSignUp();
		isEmailAlreadyTakenMessageDisplayed = oSignupPage
				.signUpFromExistingEmail(firstName, lastName, company, email,
						password);

		Utils.logStepInfo(isEmailAlreadyTakenMessageDisplayed,
				"Signup from Existing Email Id Validation ");
		Assert.assertTrue(isEmailAlreadyTakenMessageDisplayed);
	}
	
	/**
	 * @summary:
	 * 
	 *          Step-1: Login to the application
	 *          Step-2: Go to profile page
	 *          Step-3: Enter values in the fields to be updated
	 *          Step-4: Verify the success message
	 *
	 */

	@Test(priority = 9)
	public void profileUpdateTest() {
		String username = "";
		String password = "";
		String company = "";
		oLoginPage = new LoginPage(driver);
		oProfilePage = new ProfilePage(driver);
		boolean isProfileUpdated = false;
		String currentMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();

		username = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[0];
		password = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[1];
		company = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[2];
		oLoginPage.login(username, password);
		oProfilePage.navigateToProfilePage();
		isProfileUpdated = oProfilePage.updateProfile(company);

		Utils.logStepInfo(isProfileUpdated, "Profile Update Test ");
		Assert.assertTrue(isProfileUpdated);
	}
	/**
	 * @summary:
	 * 
	 *          Step-1: Login to the application
	 *          Step-2: Go to profile page 
	 *          Step-3: Click Reset password button
	 *          Step-4: Enter current password, new password, and confirm password
	 *          Step-5: Click update
	 *          Step-6: Verify password reset success message
	 *
	 */
	@Test(priority = 10)
	public void resetPasswordTest() {
		String username = "";
		String password = "";
		oLoginPage = new LoginPage(driver);
		oProfilePage = new ProfilePage(driver);
		oResetPasswordPage = new ResetPasswordPage(driver);
		boolean isPasswordReset = false;
		String currentMethodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();

		username = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[0];
		password = dataElementMap.get(currentMethodName).getParams().trim()
				.split(",")[1];
		oLoginPage.login(username, password);
		oProfilePage.navigateToProfilePage();
		oProfilePage.clickResetPassword();
		isPasswordReset = oResetPasswordPage.resetPassword(password);

		Utils.logStepInfo(isPasswordReset, "Password Reset Test ");
		Assert.assertTrue(isPasswordReset);
	}
}
