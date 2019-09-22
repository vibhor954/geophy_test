package pages;

import logger.Log;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.CommonFunctions;
import utils.Constants;
import utils.GlobalVars;

public class SearchPage {
	
	 private  WebDriver driver;
	 private CommonFunctions oCommonFunctions;
	    public SearchPage() {
	    }
	    public SearchPage(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        oCommonFunctions=new CommonFunctions();

	    }


	    @FindBy(xpath = "//a[text()='Logout']")
	    private static WebElement logoutLink;
	    @FindBy(id = "address_input")
	    private static WebElement addressTextBox;
	    @FindBy(css = "input#noi")
	    private static WebElement netOperatingIncomeTextBox;
	    @FindBy(css = "input[name=number_of_units]")
	    private static WebElement numberOfUnitsTextBox; 
	    @FindBy(name = "year_built")
	    private static WebElement yearOfConstruction;
	    @FindBy(name = "occupancy")
	    private static WebElement occupancyTextBox; 
	    @FindBy(xpath = "//button[contains(text(),'Run Valuation')]")  
	    private static WebElement runValuationButton;
	    @FindBy(xpath = "//span[contains(text(),'Save Report')]")  
	    private static WebElement saveReportButton;
	    
	    
	    

	    
	    public boolean logout() {
	    	boolean isUserLoggedOut=false;
	        try {
	            oCommonFunctions.clickElement(logoutLink, 20);
	            isUserLoggedOut=driver.getCurrentUrl().equals(GlobalVars.prop.getProperty(Constants.loginPAGEURL));
	        } catch (Exception e) {
	            Log.error("Exception occurred in logout method"+e.getMessage());
	        }
			return isUserLoggedOut;
	       
	    }

	    public boolean verifySearchPagePostLogin() {
	        boolean isUserLoggedIn=false;
	        try {
	        	isUserLoggedIn=driver.getCurrentUrl().equals(GlobalVars.prop.getProperty(Constants.searchPageURL));
	        } catch (Exception e) {
	            Log.error("Exception occurred in verifySearchPagePostLogin method"+e.getMessage());
	        }
	        return isUserLoggedIn;
	    }
	    
	    public boolean runValuation(String address,String noi,String nou,String year,String occupancy) {
	    	boolean isrunValuationSuccess=false;
	        try {
	        	oCommonFunctions.sendKey(addressTextBox, address, 10);
	        	oCommonFunctions.keyPress("DOWN");
	        	oCommonFunctions.keyPress("ENTER");
	        	oCommonFunctions.sendKey(netOperatingIncomeTextBox, noi, 10);
	        	oCommonFunctions.sendKey(numberOfUnitsTextBox, nou, 10);
	        	oCommonFunctions.sendKey(yearOfConstruction, year, 10);
	        	oCommonFunctions.sendKey(occupancyTextBox, occupancy, 10);
	        	oCommonFunctions.clickElement(runValuationButton, 10);
	        	isrunValuationSuccess=oCommonFunctions.waitForURLContains("report", 20);
	        } catch (Exception e) {
	            Log.error("Exception occurred in runValuation method"+e.getMessage());
	        }
			return isrunValuationSuccess;
	        
	    }

}
