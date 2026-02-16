package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import BaseTest.BasePage;

public class HomePage extends BasePage
{
	public HomePage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath="//a[@title='My Account']")
	WebElement btnMyAccount;
		
	@FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Login']")
	WebElement btnLogin;
	
	@FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Register']")
	WebElement btnRegistration;
	
	public void myAccount()
	{
		btnMyAccount.click();		
	}
	
	public LoginPage doLogin()
	{
	   	
		btnLogin.click();
		return new LoginPage(driver);
		
	}
	
	public RegistrationPage doRegistration()
	{
		btnRegistration.click();
		return new RegistrationPage(driver);
	}
	
	
	

}
