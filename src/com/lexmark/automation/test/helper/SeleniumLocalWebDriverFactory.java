package com.lexmark.automation.test.helper;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.opera.core.systems.OperaDriver;

/**
 * 
 * This is the factory class for creating local web driver for different browsers
 *
 */
public class SeleniumLocalWebDriverFactory {
	
	private static final Logger logger = Logger.getLogger(SeleniumLocalWebDriverFactory.class);
	
	static{
		//registering web driver path into the system property
		System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
		
		//adjust chromedriver path for Linux boxes
		String operatingSystem = System.getProperty("os.name");
		String chromeDriverPath=null;
		logger.info("OS=" + operatingSystem); 
						
		if (operatingSystem.contains("Linux")){
			chromeDriverPath = "drivers/chromedriver";
		}else{
			chromeDriverPath = "drivers/chromedriver.exe";
		}

		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			
		//TODO set system property for Safari, Opera web driver
	}
	/**
	 * Create Web Driver based on the browser type passed as the input parameter.
	 * 
	 * @param browserTypeEnum - the browser type
	 * 
	 * @return the Web Driver instance
	 */
	public static WebDriver getWebDriver(BrowserTypeEnum browserTypeEnum){
		
		logger.debug("Get Local Web Driver for "+browserTypeEnum);
		
		WebDriver webDriver = null;
		switch (browserTypeEnum) {
		case IE8:
		case IE9:
			webDriver = new InternetExplorerDriver();
			break;
		case CHROME:
			webDriver = new ChromeDriver();
			break;
		case FIREFOX:
			webDriver = new FirefoxDriver();
			break;
		case SAFARI:
			webDriver = new SafariDriver();
			break;
		case OPERA:
			webDriver = new OperaDriver();
			break;
		case IPHONE:
			webDriver = new FirefoxDriver();
			//TODO dimension value need to be verified 
			Dimension iphoneDimension = new Dimension(30, 50);
			webDriver.manage().window().setSize(iphoneDimension);
			break;
		case IPAD:
			webDriver = new FirefoxDriver();
			//TODO dimension value need to be verified 
			Dimension ipadDimension = new Dimension(80, 40);
			webDriver.manage().window().setSize(ipadDimension);
			break;
		case ANDROID:
			webDriver = new FirefoxDriver();
			//TODO dimension value need to be verified 
			Dimension dimension = new Dimension(10, 20);
			webDriver.manage().window().setSize(dimension);
			break;

		default:
			throw new RuntimeException("Invalid BrowserType passed");
		}
		
		//maximize the window size
		if(browserTypeEnum == BrowserTypeEnum.IE8 || browserTypeEnum == BrowserTypeEnum.IE9 
				|| browserTypeEnum == BrowserTypeEnum.CHROME || browserTypeEnum == BrowserTypeEnum.FIREFOX 
				|| browserTypeEnum == BrowserTypeEnum.SAFARI || browserTypeEnum == BrowserTypeEnum.OPERA){
			webDriver.manage().window().maximize();
		}
		
		
		return webDriver;
	}

}
