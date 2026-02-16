package TestCases;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import BaseClass.BaseClass;
import ExtentListeners.ExtentReportManager;
import Pages.HomePage;
import Pages.LoginPage;
import Pages.MyAccountPage;
import Utilities.ExcelUtil;

/*   Data is valid - login success - test pass - logout
 *   Data is valid - login failed - test fail 
 *   
 *   Data is invalid - login success - test fail - logout 
 *   Data is invalid - login failed - test pass  
 * 
 */

public class TC002_doLogin extends BaseClass
{
		
	public LoginPage loginpage;
	public  MyAccountPage myaccountpage;
	
	 @Test(dataProviderClass = ExcelUtil.class, dataProvider = "dp", groups = "Regression")
	 public void myLogin(String email, String password, String validate) throws InterruptedException
	 {
		 log.info("*****************Login Activity Started*********");
		 HomePage homepage= new HomePage(driver);
		 homepage.myAccount();
		 loginpage = homepage.doLogin();
		 
		 log.info("Entering Email and Password........");
		 
		 ExtentReportManager.testReport.get().info("Entering the user Email");
		 loginpage.Email(email);
		 
		 ExtentReportManager.testReport.get().info("Entering the user Password");
		 loginpage.Password(password);
		 
		 log.info("Clicking on continue....");
		 
		 ExtentReportManager.testReport.get().info("User click on Continue Button");
		 myaccountpage =loginpage.loginAction();
		 
		 
		 switch(validate.toLowerCase())
		 {
		 case "valid":
						     
			     if(myaccountpage.TitleValidation().equals("My Account"))
			     {
			    	 System.out.println("Title------>" +myaccountpage.TitleValidation());
			    	 Assert.assertTrue(true);			    	 
			    	 myaccountpage.doLogout();
			    	 break;
			    	 
			     }
			     else
			     {
			    	 Assert.assertTrue(false);
			    	 break;
			     }
			     
			     
			     			     
		 case "invalid" :
			    
			 if(myaccountpage.TitleValidation().equalsIgnoreCase("My Account"))
		     {
				 Thread.sleep(Duration.ofSeconds(5));;
				 System.out.println("Title------>" +myaccountpage.TitleValidation());
		    	 Assert.assertTrue(false);
		    	 myaccountpage.doLogout();
		    	 break;
		     }
			 else
			 {
				Assert.assertTrue(true);
				break;
			 }
			 
			 
			 
		 default:
			 
			      System.out.println("Unrecognized Term.....");
		 
		 
		 }
		 
		 
		 
		 
	 }
	
	
	
	

}
