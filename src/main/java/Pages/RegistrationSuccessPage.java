package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import BaseTest.BasePage;

public class RegistrationSuccessPage extends BasePage 
{
	public RegistrationSuccessPage(WebDriver driver) 
	{
		super(driver);
	}
	
	@FindBy(xpath="(//h1[normalize-space()='Your Account Has Been Created!'])[1]")
	WebElement txtMessage;	
	public String getSuccessAcknowledge()
	{
		 
		String message = txtMessage.getText();
		return message;
		
		
	}
    	
	

}
