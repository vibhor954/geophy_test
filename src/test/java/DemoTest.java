import org.testng.annotations.Test;

import pages.HistoryPage;
import pages.LoginPage;
import pages.ProfilePage;
import pages.SearchPage;
import org.testng.asserts.SoftAssert;
import base.TestBase;
import utils.*;
import org.testng.Assert;


public class DemoTest extends TestBase{
	
    /**
     * @param
     * @summary:
     
     * Step-1: Go to login page, enter the user name and password and click login button
     * 		   Verify that the user has successfully logged in.
     *
     */
    @Test(priority=1)
    public void loginTestwithValidCredentials() {
        SoftAssert softAssert= new SoftAssert();
        String username="";
        String password="";
        String currentMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
       // testLinkID.add(226032);
       // testLink.set(testLinkID);
        oLoginPage =new LoginPage(driver);
        oSearchPage=new SearchPage(driver);
        username=dataElementMap.get(currentMethodName).getParams().trim().split(",")[0];
        password=dataElementMap.get(currentMethodName).getParams().trim().split(",")[1];
        oLoginPage.login(username, password);
       boolean isUserLoggedIn= oLoginPage.verifySearchPagePostLogin();
       Utils.logStepInfo(isUserLoggedIn, "Login and Verify Post Login");
       Assert.assertTrue(isUserLoggedIn==true);
       oSearchPage.logout();
       oLoginPage=null;
       oSearchPage=null;
    }

    @Test(priority=2)
    public void loginTestwithInValidCredentials() {
        SoftAssert softAssert= new SoftAssert();
        String username="";
        String password="";
        oLoginPage =new LoginPage(driver);
        String currentMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
        //oCommonLoginPage =new CommonLoginPage(driver); //comment done
        username=dataElementMap.get(currentMethodName).getParams().trim().split(",")[0];
        password=dataElementMap.get(currentMethodName).getParams().trim().split(",")[1];
        oLoginPage.login(username, password);
        boolean isLoginErrorMessageDisplayed=oLoginPage.isLoginErrorMessageDisplayed();
        Utils.logStepInfo(isLoginErrorMessageDisplayed, "Validate Error Message on Invalid Credentials");
        Assert.assertTrue(isLoginErrorMessageDisplayed==true);
    }
    
    @Test(priority=3)
    public void runValuationTest() {
        SoftAssert softAssert= new SoftAssert();
        String username="";
        String password="";
        String address="";
        String net_operating_income="";
        String number_of_units="";
        String year_of_construction="";
        String occupancy="";
        oLoginPage =new LoginPage(driver);
        oSearchPage=new SearchPage(driver);
        boolean isrunValuationSuccess=false;
        String currentMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
        
        address=dataElementMap.get(currentMethodName).getParams().trim().split(",")[2];
        net_operating_income=dataElementMap.get(currentMethodName).getParams().trim().split(",")[3];
        number_of_units=dataElementMap.get(currentMethodName).getParams().trim().split(",")[4];
        year_of_construction=dataElementMap.get(currentMethodName).getParams().trim().split(",")[5];
        occupancy=dataElementMap.get(currentMethodName).getParams().trim().split(",")[6];
        
        username=dataElementMap.get(currentMethodName).getParams().trim().split(",")[0];
        password=dataElementMap.get(currentMethodName).getParams().trim().split(",")[1];
        oLoginPage.login(username, password);
        
        isrunValuationSuccess= oSearchPage.runValuation(address,net_operating_income,number_of_units,year_of_construction,occupancy);
        
        Utils.logStepInfo(isrunValuationSuccess, "Validation for Run Valuation Success");
        Assert.assertTrue(isrunValuationSuccess==true);
        oSearchPage.logout();
        
    }
    
    @Test(priority=4)
    public void verifySearchHistoryTest() {
        SoftAssert softAssert= new SoftAssert();
        String username="";
        String password="";
        String address="";
        String net_operating_income="";
        String number_of_units="";
        String year_of_construction="";
        String occupancy="";
        String address_search_term="";
        oLoginPage =new LoginPage(driver);
        oSearchPage=new SearchPage(driver);
        oHistoryPage=new HistoryPage(driver);
        boolean isRecentSearchDisplayed=false;
        String currentMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
        
        address=dataElementMap.get(currentMethodName).getParams().trim().split(",")[2];
        net_operating_income=dataElementMap.get(currentMethodName).getParams().trim().split(",")[3];
        number_of_units=dataElementMap.get(currentMethodName).getParams().trim().split(",")[4];
        year_of_construction=dataElementMap.get(currentMethodName).getParams().trim().split(",")[5];
        occupancy=dataElementMap.get(currentMethodName).getParams().trim().split(",")[6];
        address_search_term=dataElementMap.get(currentMethodName).getParams().trim().split(",")[7];
        
        username=dataElementMap.get(currentMethodName).getParams().trim().split(",")[0];
        password=dataElementMap.get(currentMethodName).getParams().trim().split(",")[1];
        oLoginPage.login(username, password);
        
        oSearchPage.runValuation(address,net_operating_income,number_of_units,year_of_construction,occupancy);
        isRecentSearchDisplayed= oHistoryPage.verifySearchHistory(address_search_term,net_operating_income,year_of_construction,number_of_units);
        
        Utils.logStepInfo(isRecentSearchDisplayed, "Validation for Recent Search Display");
        Assert.assertTrue(isRecentSearchDisplayed==true);
        oSearchPage.logout();
        
    }
    
    @Test(priority=5)
    public void verifyProfileDetailsTest() {
        SoftAssert softAssert= new SoftAssert();
        String username="";
        String password="";
        oLoginPage =new LoginPage(driver);
        oProfilePage=new ProfilePage(driver);
        boolean isProfileDetailsValidated=false;
        String currentMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
        
        username=dataElementMap.get(currentMethodName).getParams().trim().split(",")[0];
        password=dataElementMap.get(currentMethodName).getParams().trim().split(",")[1];
        oLoginPage.login(username, password);
        isProfileDetailsValidated=oProfilePage.verifyProfileDetails(username);
        
        Utils.logStepInfo(isProfileDetailsValidated, "Profile Details Validation ");
        Assert.assertTrue(isProfileDetailsValidated==true);
        oSearchPage.logout();
    }
        
        @Test(priority=6)
        public void logoutTest() {
            SoftAssert softAssert= new SoftAssert();
            String username="";
            String password="";
            oLoginPage =new LoginPage(driver);
            oSearchPage=new SearchPage(driver);
            boolean isUserLoggedOut=false;
            String currentMethodName=Thread.currentThread().getStackTrace()[1].getMethodName();
            
            username=dataElementMap.get(currentMethodName).getParams().trim().split(",")[0];
            password=dataElementMap.get(currentMethodName).getParams().trim().split(",")[1];
            oLoginPage.login(username, password);
            isUserLoggedOut=oSearchPage.logout();
            
            Utils.logStepInfo(isUserLoggedOut, "User Logout Validation ");
            Assert.assertTrue(isUserLoggedOut==true);
            
        
    }


}
