package com.lexmark.automation.test.helper;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;


public final class TestAutomationHelper {
	
	private static final Logger logger = Logger.getLogger(TestAutomationHelper.class);
	
	private static final Map<BrowserTypeEnum,WebDriver> webDriverMap = new HashMap<BrowserTypeEnum,WebDriver>();
	
	/**
	 * Get the web driver instance(Local/Remote) based on the browser type.For the first request of a particular
	 * browser type, it creates the web driver instance and keep the reference into a map object.Next time onwards for the similar request,
	 * the web driver reference would be retrieved from the map and returned.
	 * 
	 * @param browserTypeEnum
	 * 
	 * @return web driver instance(Local/Remote)
	 */
	public static WebDriver getWebDriver(BrowserTypeEnum browserTypeEnum) {
		//check if the web driver instance is available in the map
		WebDriver webDriver = webDriverMap.get(browserTypeEnum);
		if(webDriver == null){
			//create the web driver instance 
			synchronized (TestAutomationHelper.class) {
				
				boolean remoteDriver = TestSetup.isRemoteTestRun();
								
				if(remoteDriver){
					logger.debug("Creating remote web driver instance for "+browserTypeEnum);
					try {
						webDriver = SeleniumRemoteWebDriverFactory.getWebDriver(browserTypeEnum);
					} catch (MalformedURLException e) {
						throw new RuntimeException("Error occured while instantiating remote web driver for "+browserTypeEnum,e);
					}
				}else{
					logger.debug("Creating local web driver instance for "+browserTypeEnum);
					webDriver = SeleniumLocalWebDriverFactory.getWebDriver(browserTypeEnum);
				}
				
				if(webDriver != null){
					webDriverMap.put(browserTypeEnum, webDriver);
					try {
						Thread.sleep(AutomationTestConstant.DELAY_AVG);
					} catch (InterruptedException e) {
						logger.error(e);
					}
				}
			}
		}
		return webDriver;
	}

	public static boolean isLocalePresent(String url)
	{
		boolean isLocale = false;
		if (Pattern.compile("/[a-zA-Z]{2}_[a-zA-Z]{2}").matcher(url).find()) {
			isLocale = true;
		}else if(Pattern.compile("userlocale=[A-Z]{2}_[A-Z]{2}").matcher(url).find()){
			isLocale = true;
		}
		return isLocale;
	}
	
	public static int getRandomInt(int min, int max) {

	    int randomNum = min + (int)(Math.random() * (((max-1)-min) + 1));

	    return randomNum;
	}
	
	public static void initializeQASession(WebDriver webDriver){
		String testEnv = TestSetup.getTestEnvironment();
		if("www-qa".equalsIgnoreCase(testEnv)){
			String qaPassword = TestSetup.getQAPassword();
			if(StringUtils.isEmpty(qaPassword)){
				logger.error("QA password should be passed at build time");
				throw new RuntimeException("QA password should be passed at build time to initialize the session");
			}
			webDriver.get("http://www-qa.lexmark.com/"+qaPassword);
		}
	}
	
	public static String getSessionCookieStringForQA(WebDriver webDriver){
		String cookieString = null;
		String testEnv = TestSetup.getTestEnvironment();
		
		if("www-qa".equalsIgnoreCase(testEnv)){
			Cookie cookie = webDriver.manage().getCookieNamed("tmay");
			
			if(cookie == null){
				initializeQASession(webDriver);
				cookie = webDriver.manage().getCookieNamed("tmay");
			}
			
			if(cookie != null){
				cookieString = cookie.getName()+"="+cookie.getValue();
			}
		}
		return cookieString;
	}
}
