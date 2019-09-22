package utils;

import base.TestBase;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import logger.Log;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CommonFunctions extends TestBase {

    /*Function to click on a web element*/
    public void clickElement(WebElement element, int timeOutInSsec){

        
        WebDriverWait wait = null;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);//Setting the implicit wait as zero as implicit and explicit wait do not work together
            wait=new WebDriverWait(GlobalVars.driver, timeOutInSsec);
                   
                    wait.until(ExpectedConditions.visibilityOf(element));
                    element.click();
        }
            catch (Exception e) {
            	 driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
                 Log.error("Exception occurred in clickElement method: "+e.getMessage());
			}
        driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
         

    }

    /*Function to send text to a web element*/
    public void sendKey(WebElement element, String key,  int timeOutInSsec){

        
        WebDriverWait wait = null;
        try {
            wait=new WebDriverWait(GlobalVars.driver, timeOutInSsec);
                   driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                    wait.until(ExpectedConditions.visibilityOf(element));
                    element.clear();
                    element.sendKeys(key);  
        }
         catch (Exception e) {
        	 driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
            Log.error("Exception occurred in sendKey method"+e.getMessage());
        }
        driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
    }

    /*Function to check if an element is displayed*/
    public boolean isElementDisplayed(WebElement element, int timeOutInSsec){

        boolean isElementDisplayed=false;
        WebDriverWait wait = null;
        try {
        	driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            wait=new WebDriverWait(GlobalVars.driver, timeOutInSsec);
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                    wait.until(ExpectedConditions.visibilityOf(element));
                    isElementDisplayed=element.isDisplayed();
        }

         catch (Exception e) {
            Log.error("Exception occurred in isElementDisplayed method"+e.getMessage());
            driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
            isElementDisplayed=false;
        }
        driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
        return isElementDisplayed;
    }

    /*Function to get the text of a web element*/
    public String getElementText(WebElement element, int timeOutInSsec){
        WebDriverWait wait = null;
        String text="";
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);//Setting the implicit wait as zero as implicit and explicit wait do not work together
            wait=new WebDriverWait(GlobalVars.driver, timeOutInSsec);
           
                    wait.until(ExpectedConditions.visibilityOf(element));
                    text=element.getText();
                   
            Utils.logFunctionLevelLogs(true, "Text of the element is : "+text);
        }
         catch (Exception e) {
            Log.error("Exception occurred in getElementText method: "+e.getMessage());
            driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
        }
        driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
        return text;

    }
    /*Function to wait for a web element visibility*/
    public boolean waitForElementvisible(WebElement element,int timeOutInSsec ){
		try{
		
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			WebDriverWait wait = null;
			wait=new WebDriverWait(GlobalVars.driver, timeOutInSsec);
			wait.until(ExpectedConditions.visibilityOf(element));
		}catch(Exception e){
			driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
			return false;
		}
		driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
		return true;
	}
    
    /*Function to wait for a URL Contains given text*/
    public boolean waitForURLContains(String text,int timeOutInSsec ){
		try{
		
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			WebDriverWait wait = null;
			wait=new WebDriverWait(GlobalVars.driver, timeOutInSsec);
			wait.until(ExpectedConditions.urlContains(text));
		}catch(Exception e){
			driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
			return false;
		}
		driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
		return true;
	}
    
    /*Function to handle key press*/
    public boolean keyPress(String key){
		try{
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			Actions act = new Actions(driver); 
			Thread.sleep(2000);
		    
			switch(key){
			case "DOWN":
				act.sendKeys(Keys.DOWN).perform();
				Thread.sleep(2000); 
			break;
			case "ENTER":
				act.sendKeys(Keys.ENTER).perform();
				Thread.sleep(2000);
			break;
			case "UP":
				act.sendKeys(Keys.UP).perform();
			break;
			default:
                throw new IllegalStateException("Unexpected value: " + key);
			}
			
		}catch(Exception e){
			driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
			return false;
		}
		driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
		return true;
	}
    
    /*Function to get current date*/
    public String getCurrentDate()
    {
    	Date date = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    return formatter.format(date);
    }
    
 // Assert text present
 	public boolean isTextPresent(WebElement element, String str) {
 		Assert.assertTrue(isElementPresent(element), "Element Locator :"
 				+ element + " Not found");
 		String message = element.getText();
 		if (message.contains(str)) {
 			return true;
 		} else {
 			return false;
 		}
 	}
 	
 // Assert element present
 	public static Boolean isElementPresent(WebElement element) {
 		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
 		Boolean result = false;
 		try {
 			element.isDisplayed();
 			result = true;
 		} catch (Exception e) {
 			Log.error("Exception occurred in isElementPresent method"+e.getMessage());
 			driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
 		}
 		driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
 		return result;
 	}
 	
 // Get attribute
 	public String getAttribute(WebElement element, String attribute,int timeOutInSsec) {
 		String text=null;
 		try{
 		 driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);//Setting the implicit wait as zero as implicit and explicit wait do not work together
 		WebDriverWait wait = null;
         wait=new WebDriverWait(GlobalVars.driver, timeOutInSsec);
        
                 wait.until(ExpectedConditions.visibilityOf(element));
 		 text = element.getAttribute(attribute);
 		}catch(Exception e){
 			driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
 			
 		}
 		driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
		return text;
 	}

    public void logStepInfo(ExtentTest test, boolean isResult, String stepInfo, int stepNumber)
    {
        if(isResult)
            test.log(Status.PASS, "Step-"+stepNumber+": "+stepInfo+" | Status: Pass");
        else
			test.log(Status.FAIL, "Step-" + stepNumber + ": " + stepInfo
					+ " | Status: Fail");
	}
}
