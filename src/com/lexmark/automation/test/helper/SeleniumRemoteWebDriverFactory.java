package com.lexmark.automation.test.helper;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * 
 * This is the factory class for creating remote web driver for different browsers
 *
 */
public class SeleniumRemoteWebDriverFactory {
	
	private static final Logger logger = Logger.getLogger(SeleniumRemoteWebDriverFactory.class);

	/**
	 * Create Web Driver based on the browser type passed as the input parameter.
	 * 
	 * @param browserTypeEnum - the browser type
	 * 
	 * @return the Web Driver instance
	 */
	public static WebDriver getWebDriver(BrowserTypeEnum browserTypeEnum) throws MalformedURLException{
		
		logger.debug("Get Remote Web Driver for "+browserTypeEnum);

		Capabilities capabilities = getDesiredCapabilities(browserTypeEnum);
		
		String hubURL = TestSetup.getTestHubURL();
		
		logger.debug("Hub URL is : "+hubURL);
		
		if(hubURL == null || "".equals(hubURL.trim())){
			throw new RuntimeException("Hub URL is null or empty");
		}
		
		WebDriver webDriver = new RemoteWebDriver(new URL(hubURL), capabilities);
		
		logger.debug("Going to maximize Remote Web browser for "+browserTypeEnum);
		
		webDriver.manage().window().maximize();
		
		
		return webDriver;
	}
	
	 /**
	  * Helper method to call appropriate methods on DesiredCapabilities object.  
	  * This will likely need to be updated as the tests want
	  * to call on more specific capabilities.
	  */
	 private static DesiredCapabilities getDesiredCapabilities(BrowserTypeEnum browserTypeEnum){
	        DesiredCapabilities capabilities = null;	        
	        
	        switch (browserTypeEnum) {
			case IE8:
				capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setBrowserName("internet explorer 8");
				break;
			case IE9:
				capabilities = DesiredCapabilities.internetExplorer();
				break;
			case CHROME:
				capabilities = DesiredCapabilities.chrome();
				break;
			case FIREFOX:
				capabilities = DesiredCapabilities.firefox();
				break;
			case SAFARI:
				capabilities = DesiredCapabilities.safari();
				break;
			case OPERA:
				capabilities = DesiredCapabilities.opera();
				break;
			case IPHONE:
				capabilities = DesiredCapabilities.iphone();
				break;
			case IPAD:
				capabilities = DesiredCapabilities.ipad();
				break;
			case ANDROID:
				capabilities = DesiredCapabilities.android();
				break;

			default:
				throw new RuntimeException("Invalid BrowserType passed");
			}
	        
	        return capabilities;
	   }
	
}
