package com.lexmark.automation.test.helper;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * This is the listener class for capturing browser screen shot for any test failure and store them into a 
 * configurable folder path.
 *
 */
public class CaptureScreenshotOnFailureListener extends TestListenerAdapter
{
	private static final Logger logger = Logger.getLogger(CaptureScreenshotOnFailureListener.class);
	
    
	/**
	 * This method would be called by TestNG framework if there is any test method failure and 
	 * the 'CaptureScreenshotOnFailureListener' is associated with the corresponding test class.
	 */
	@Override
    public void onTestFailure (ITestResult testResult)
    {
    	
        // call the superclass
        super.onTestFailure(testResult);
        
       //get the browser type passed from testNG xml configuration file
       String browserType = testResult.getTestContext().getSuite().getParameter("browser-type");
       
       logger.debug("Test failure for Browser:"+browserType);
       
       WebDriver driver =  null;
       
       Object testClassObject = testResult.getInstance();
       
       if(testClassObject instanceof SeleniumTestNgBaseTest){
    	   driver = ((SeleniumTestNgBaseTest)testClassObject).getDriver();
       }
        
       

        /*
         * We can only take screen shots for those browsers that support screen shot capture, html unit
         * does not support screenshots as part of its capabilities.
         */
        if ( driver != null)
        {
            // Create a calendar object so we can create a date and time for the screenshot
            Calendar calendar = Calendar.getInstance();

            // Get the screen shots folder destination
            
            String screenShotsFolder = TestSetup.getPathForScreenShot();
            

            // The file includes the the test method and the test class
            String testMethodAndTestClass = testResult.getMethod().getMethodName() + "(" + testResult.getTestClass().getName() + ")";

            logger.debug(" *** This is where the capture file is created for the Test \n" + testMethodAndTestClass );

            // Create the filename for the screen shots
            StringBuilder filenameBuilder = new StringBuilder().append(screenShotsFolder).append(browserType)
            									.append(AutomationTestConstant.HYPHEN).append(testMethodAndTestClass).append(AutomationTestConstant.HYPHEN)
            									.append(calendar.get(Calendar.YEAR)).append(AutomationTestConstant.HYPHEN)
                                                .append(calendar.get(Calendar.MONTH)).append(AutomationTestConstant.HYPHEN)
                                                .append(calendar.get(Calendar.DAY_OF_MONTH)).append(AutomationTestConstant.HYPHEN)
                                                .append(calendar.get(Calendar.HOUR_OF_DAY)).append(AutomationTestConstant.HYPHEN)
                                                .append(calendar.get(Calendar.MINUTE)).append(AutomationTestConstant.HYPHEN)
                                                .append(calendar.get(Calendar.SECOND)).append(AutomationTestConstant.HYPHEN)
                                                .append(calendar.get(Calendar.MILLISECOND))
                                                .append(".png");
            

            // Take the screen shot and then copy the file to the screen shot folder
            File scrFile = null;
			try {
				scrFile = getScreenShot(driver);
			} catch (WebDriverException e1) {
				logger.error("Error occured while capturing screenshot",e1);
			}
			
			logger.debug("Screenshot captured successful, it would be copied into the specefic folder");

           if(scrFile != null){
        	   try  {
                   FileUtils.copyFile(scrFile, new File(filenameBuilder.toString()));
               }
               catch (IOException e)
               {
                   logger.error("Error occured while storing screenshot image into the folder",e);
               }
           }
           
           logger.debug("Screenshot copied, file :"+filenameBuilder);
			

        }else{
        	logger.error("Error occured during screen capture - WebDriver object is null");
        }

    }
    
    private File getScreenShot(WebDriver webDriver){
    	
    	File scrFile = null;
    	
    	if(webDriver instanceof TakesScreenshot){
    		scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
    	}else{
    		 // RemoteWebDriver does not implement the TakesScreenshot class
            // if the driver does have the Capabilities to take a screenshot
            // then Augmenter will add the TakesScreenshot methods to the instance
    		WebDriver augmentedDriver = new Augmenter().augment(webDriver);
    		scrFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
    	}
    	
    	
    	return scrFile;
    }

} // enf of class
