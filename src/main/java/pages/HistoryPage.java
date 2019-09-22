package pages;

import logger.Log;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonFunctions;
import utils.Constants;
import utils.GlobalVars;

public class HistoryPage {

	private WebDriver driver;
	private CommonFunctions oCommonFunctions;

	public HistoryPage() {
	}

	public HistoryPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		oCommonFunctions = new CommonFunctions();
	}

	@FindBy(id = "search")
	private static WebElement searchHistoryTextBox;
	@FindBy(xpath = "//table[@id='tblValuationRequests']//tr[1]/td[3]/a[1]")
	private static WebElement propertyText;
	@FindBy(xpath = "//*[@id='tblValuationRequests']//tr[1]/td[2]")
	private static WebElement createdOnText;
	@FindBy(xpath = "//*[@id='tblValuationRequests']//tr[1]/td[4]")
	private static WebElement noiText;
	@FindBy(xpath = "//*[@id='tblValuationRequests']//tr[1]/td[5]")
	private static WebElement yearOfConstructionText;
	@FindBy(xpath = "//*[@id='tblValuationRequests']//tr[1]/td[6]")
	private static WebElement unitsText;

	public void navigateToHistoryPage() {
		driver.navigate().to(
				GlobalVars.prop.getProperty(Constants.historyPAGEURL));
	}

	public boolean verifySearchHistory(String address,
			String netOperatingIncome, String yearOfConstruction,
			String numberOfUnits) {
		boolean isRecentPropertyDisplayed = false;
		boolean isRecentCreatedOnDisplayed = false;
		boolean isRecentYearOfConstructionDisplayed = false;
		boolean isRecentUnitsDisplayed = false;
		boolean isRecentSearchDisplayed = false;
		boolean isRecentNetOperatingIncome = false;
		try {

			isRecentPropertyDisplayed = oCommonFunctions.isTextPresent(
					propertyText, address);
			String date = oCommonFunctions.getCurrentDate();
			isRecentCreatedOnDisplayed = oCommonFunctions.isTextPresent(
					createdOnText, date);
			String noi = oCommonFunctions.getElementText(noiText, 10);
			noi = noi.replace(",", "");
			netOperatingIncome = netOperatingIncome.replace(" ", "").trim();
			if (noi.contains(netOperatingIncome)) {
				isRecentNetOperatingIncome = true;
			}
			
			isRecentYearOfConstructionDisplayed = oCommonFunctions
					.isTextPresent(yearOfConstructionText, yearOfConstruction);
			isRecentUnitsDisplayed = oCommonFunctions.isTextPresent(unitsText,
					numberOfUnits);
			if (isRecentPropertyDisplayed && isRecentCreatedOnDisplayed
					&& isRecentYearOfConstructionDisplayed
					&& isRecentUnitsDisplayed && isRecentNetOperatingIncome) {
				isRecentSearchDisplayed = true;
			}

		} catch (Exception e) {
			Log.error("Exception occurred in verifySearchHistory method"
					+ e.getMessage());
		}
		return isRecentSearchDisplayed;
	}

}
