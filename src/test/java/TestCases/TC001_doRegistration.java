package TestCases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import BaseClass.BaseClass;
import Pages.RegistrationSuccessPage;
import Pages.HomePage;
import Pages.RegistrationPage;
import Utilities.ExcelUtil;

public class TC001_doRegistration extends BaseClass
{
	 @Test(dataProviderClass = ExcelUtil.class, dataProvider = "dp",groups="Sanity")	
	 public void myRegistration(String firstname, String lastname,String telephone)
	 {
		 log.info("**************TC001_doRegistration*****************");
		 
		 try
		 {
		 HomePage homepage= new HomePage(driver);
		    homepage.myAccount();
		    RegistrationPage registrationpage = homepage.doRegistration();
		
		 log.info("Providing Customer Details......");
		    
		    registrationpage.setFirstName(firstname);
		    registrationpage.setLastName(lastname);
		    registrationpage.setEmail(getAlphaNumberic()+"@gmail.com");
		    registrationpage.setTelephone(telephone);
		    
		    String password = getAlphaNumberic();
		    String confirmpassword = password;		    
		    
		    registrationpage.setPassword(password);
		    registrationpage.setConfirmPassword(confirmpassword);
		    registrationpage.checkPrivacy();
		    RegistrationSuccessPage  accountsuccesspage = registrationpage.doContinue();
		    
		    log.info("Validating the Success Message....");
		    String actualmessage = accountsuccesspage.getSuccessAcknowledge();
		    Assert.assertEquals(actualmessage,"Your Account Has Been Created!");
		    
		 }
		 
		 catch(Exception e)
		 {
			 log.error("Failed Test....");
			 log.debug("Debug Test.....");
			 Assert.fail();
		 }
		 
		 log.info("***********Finished TC001_doRegistration***************");
	}
		
		

}
