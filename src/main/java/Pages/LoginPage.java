package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import BaseTest.BasePage;

public class LoginPage extends BasePage
{

	public LoginPage(WebDriver driver)
	{
		super(driver);
	}
		
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtEmail;
	public void Email(String email)
	{
		txtEmail.sendKeys(email);
	}
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;
	public void Password(String password)
	{
		txtPassword.sendKeys(password);
		
	}
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement clkLogin;
	public MyAccountPage loginAction()
	{
		clkLogin.click();	
		return new MyAccountPage(driver);
	}
	
	
	

}
