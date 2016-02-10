package com.lexmark.automation.test.helper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;


/**
 * The base test class for automation.
 * 
 * All test class should extends this class.
 * 
 * @author Santu Saha
 *
 */
public class SeleniumTestNgBaseTest {
	
	private static final Logger logger = Logger.getLogger(SeleniumTestNgBaseTest.class);
		
	
	
	/**
	 * driver - the selenium web driver 
	 */
	protected WebDriver driver = null;
	
	/**
	 * browserType - the type of web browser, e.g- 'IE','FIREFOX','CHROME' 
	 */
	protected String browserType = null;
	
	
	/**
	 * This method creates the Web Driver instance based on the browser type.
	 * 
	 * It runs before a test class starts execution
	 * 
	 * @param driverType - the parameter passed from the testNG XML configuration file
	 */
	@BeforeClass(groups={"basic"})
	@Parameters(value = "browser-type")
	public final void initializeBeforeClass(String driverType) {
		browserType = driverType;
		BrowserTypeEnum browserTypeEnum = BrowserTypeEnum.valueOf(driverType);
		driver = TestAutomationHelper.getWebDriver(browserTypeEnum);
	}
	
	/**
	 * This method creates the Web Driver instance based on the browser type.
	 * 
	 * It runs before a test class starts execution
	 * 
	 * @param driverType - the parameter passed from the testNG XML configuration file
	 */
	@BeforeSuite(groups={"basic"})
	@Parameters(value = "browser-type")
	public final void initializeBeforeSuite(String driverType) {
		BrowserTypeEnum browserTypeEnum = BrowserTypeEnum.valueOf(driverType);
		driver = TestAutomationHelper.getWebDriver(browserTypeEnum);
		driver.manage().timeouts().implicitlyWait(AutomationTestConstant.DELAY_AVG, TimeUnit.SECONDS);
		
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
		}
		
		logger.info("WebDriver initialization completed for "+browserType);
		
		TestAutomationHelper.initializeQASession(driver);
	}
	
	/**
	 * This method validates whether the input domain is valid for a particular TestNG test.
	 * If the input domain is not valid, all test method of the test will be skipped.
	 * 
	 * @param validDomains - passed from TestNG XML configuration file.
	 */
	@BeforeTest(groups={"basic"})
	@Parameters(value = "valid-domains")
	public final void validateDomain(String validDomains) {
		
		String inputDomain = TestSetup.getTestEnvironment();
		
		logger.debug("Input Domain is "+inputDomain);
		logger.debug("Valid Domains for this test is "+validDomains);
		
		boolean domainValidationSuccess = false;
		
		if(validDomains != null && !AutomationTestConstant.EMPTY.equals(validDomains.trim())){
			if(validDomains.contains(AutomationTestConstant.COMMA)){
				String[] validDomainArray = validDomains.split(AutomationTestConstant.COMMA);
				for(String validDomain : validDomainArray){
					if(validDomain.equalsIgnoreCase(inputDomain)){
						domainValidationSuccess = true;
						break;
					}
				}
			}else if(validDomains.equalsIgnoreCase(inputDomain)){
				domainValidationSuccess = true;
			}
		}
		if(!domainValidationSuccess){
			logger.warn("Input domain "+inputDomain+" is not valid for this test");
			throw new RuntimeException("Input domain "+inputDomain+" is not valid for this test");
		}
	}
	
	/**
	 * Close the Web Driver.
	 * 
	 * It runs after execution of the all test method of a 
	 * certain test suite configured in the testNG configuration file.
	 */
	@AfterSuite(groups={"basic"},alwaysRun=true)
	public final void destroy() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	protected void closeCookiesMessageModal(String locale){
		List<String> allCookieLocales = TestSetup.getCookieEnabledLocaleList();
		if(allCookieLocales.contains(locale)){
		try {
			WebElement cookieMessageElement = WaitHelper.waitUntillElementIsPresent(driver, TimeUnit.SECONDS, AutomationTestConstant.RETRY_INTERVAL_MIN, AutomationTestConstant.TIMEOUT_MIN, By.id("cookieMessage"));
			logger.info("Cookie message present");
			if(cookieMessageElement.isDisplayed()){
				logger.info("Cookie message modal is visible, going to click the checkbox");
				driver.findElement(By.id("hideCookieMessage")).click();
				logger.info("Cookie message modal is visible, going to close the window");
				driver.findElement(By.id("closeCookieMessage")).click();
				logger.info("Close button clicked for the cookie message window");
			}
		}catch(TimeoutException timeoutException){
			logger.error("Error occured while processing cookie message for locale: "+locale,timeoutException);
		}catch (NoSuchElementException e) {
			logger.error("Error occured while processing cookie message for locale: "+locale,e);
		}
	}else{
		logger.info("Cookie message modal handling should be skipped for "+ locale);
	}
	}

}
