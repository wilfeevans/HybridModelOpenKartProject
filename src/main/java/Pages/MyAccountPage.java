package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import BaseTest.BasePage;

public class MyAccountPage extends BasePage
{

	public MyAccountPage(WebDriver driver) 
	{
		super(driver);
	}

	@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']")
	WebElement clkLogout;
	public void doLogout()
	{
		clkLogout.click();
	}
	
	public String TitleValidation()
	{
	    String text =  driver.getTitle();
	    return text;
	}
	
	
	
	
}
