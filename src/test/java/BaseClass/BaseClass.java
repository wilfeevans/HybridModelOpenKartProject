package BaseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import Utilities.ExcelReader;

public class BaseClass 
{
		
	 public static WebDriver driver;
	 
	 public ExcelReader excel = new ExcelReader("./src/test/resources/TestData/TestData.xlsx");
	 
	// String browser = "chrome";
	 public Logger log;
	 
	 public FileInputStream fis;
	 public Properties prop;
	 	 
	 @BeforeMethod(groups={"Sanity","Regression"})
	 @Parameters({"os","browser"})
	 public void setUp(String os,String browser) throws IOException
	 {
		  fis= new FileInputStream("./src/test/resources/Properties/Config.properties");
		  prop= new Properties();
		  prop.load(fis);
          		 
		  System.setProperty("log4j.configurationFile","./src/test/resources/properties/log4j2.xml");
	      log = LogManager.getLogger(this.getClass());
	  
	      // Remote Execution - Code
	      
	      if(prop.getProperty("Execute_From").equalsIgnoreCase("remote"))
	      {
	    	  DesiredCapabilities cap = new DesiredCapabilities();
	    	  switch(os.toLowerCase())
	    	  {
	    	  case "windows" :
	    		   cap.setPlatform(Platform.WIN11);
	    		   break;
	    	  case "mac" :
	    		   cap.setPlatform(Platform.MAC);
	    		   break;
	    	  case "linux" :
	    		   cap.setPlatform(Platform.LINUX);
	    		   break;
	    	  default: 
	    		   System.out.println("No Matching OS");
	    	  }
	    	  
	    	  switch(browser.toLowerCase())
	    	  {
	    	  case "chrome":
	    		   cap.setBrowserName("chrome");
	    		   break;
	    		   
	    	  case "firefox":
	    		   cap.setBrowserName("firefox");
	    		   break;
	    		   
	    	  case "microsoftedge":
	    		    cap.setBrowserName("MicrosoftEdge");
	    		    break;
	    		    
	    	  default:
	    		    System.out.println("Browser Not Found");
	    	  }
	    	  	    	  
	    	  driver = new RemoteWebDriver(new URL("http://localhost:4444"),cap);
	    	      	  
	       }
                    
	     // Local Execution 
	      
	      if(prop.getProperty("Execute_From").equalsIgnoreCase("local"))
	      {
	    	  
	    	  switch(browser.toLowerCase())
		      {
		      case "chrome" :
		    	   driver = new ChromeDriver();
		    	   break;
		    	   
		      case "firefox" :
		    	   driver = new FirefoxDriver();
		    	   break;
		    	   
		      case "microsoftedge" :
		    	   driver = new EdgeDriver();
		    	   break;
		    	   
		      default: 
		    	    
		    	  System.out.println("No such browser!");
		    	  
		      
		      }
		          	  
	      }
	      
	     driver.get(prop.getProperty("URL"));
		 driver.manage().window().maximize();
		 driver.manage().deleteAllCookies();
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		 	
		 
	 }
	
	
	
	public String getAlphabet()
	{
		String alphabet = RandomStringUtils.randomAlphabetic(5);
		return alphabet;
	}
	
	public String getNumber()
	{
		String number = RandomStringUtils.randomNumeric(4); //we can handle like this also
		return number;
	}
	
	public String getAlphaNumberic()
	{
		String alphanumeric =  getAlphabet() + getNumber();
		return alphanumeric;		
	}
	
	
	public static String captureScreen(String tname) throws IOException {

	    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

	    TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
	    File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

	    // Define folder path
	    String targetFolderPath = System.getProperty("user.dir") 
	            + "/src/test/resources/ScreenShot/";

	    // Create folder if it doesn't exist
	    File folder = new File(targetFolderPath);
	    if (!folder.exists()) {
	        folder.mkdirs();
	    }

	    String targetFilePath = targetFolderPath + tname + "_" + timeStamp + ".png";

	    File targetFile = new File(targetFilePath);

	    FileUtils.copyFile(sourceFile, targetFile);

	    return targetFilePath;
	}

	@AfterMethod(groups= {"Sanity","Regression"})
	public void tearDown() throws InterruptedException
	{
				
		if(driver!=null)
		{
			Thread.sleep(4000);
			driver.quit();
			System.out.println("Quitting Driver");
		}
		
	}
	

}
