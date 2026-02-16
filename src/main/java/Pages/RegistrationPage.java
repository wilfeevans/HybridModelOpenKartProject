package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import BaseTest.BasePage;

public class RegistrationPage extends BasePage{

	public RegistrationPage(WebDriver driver) 
	{
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-firstname']")
	WebElement txtFirstname; 
	public void setFirstName(String fname)
	{
		txtFirstname.sendKeys(fname);
	}
	
	@FindBy(xpath="//input[@id='input-lastname']")
	WebElement txtLastname; 
	public void setLastName(String lname)
	{
		txtLastname.sendKeys(lname);
	}
	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtEmail; 
	public void setEmail(String email)
	{
		txtEmail.sendKeys(email);
	}
	
	@FindBy(xpath="//input[@id='input-telephone']")
	WebElement txtTelephone; 
	public void setTelephone(String telephone)
	{
		txtTelephone.sendKeys(telephone);
	}
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword; 
	public void setPassword(String password)
	{
		txtPassword.sendKeys(password);
	}
	
	@FindBy(xpath="//input[@id='input-confirm']")
	WebElement txtConfirmPassword; 
	public void setConfirmPassword(String cpassword)
	{
		txtConfirmPassword.sendKeys(cpassword);
	}
	
	@FindBy(xpath="//input[@name='agree']")
	WebElement chkPrivacy; 
	public void checkPrivacy()
	{
		chkPrivacy.click();
	}
	
	@FindBy(xpath="//input[@value='Continue']")
	WebElement clkContinue; 
	public RegistrationSuccessPage doContinue()
	{
		clkContinue.click();
		return new RegistrationSuccessPage(driver);
	}
	
	
	

}
