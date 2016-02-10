package com.lexmark.automation.test.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class TestSetup {
	
	private static final Logger logger = Logger.getLogger(TestSetup.class);
	
	private static Properties testMataDataProp = new Properties();
	
	static{
		try {
			testMataDataProp.load(new FileInputStream("data/test-metadata.properties"));
		} catch (IOException e) {
			logger.error("Error occured while loading test-metadata.properties",e);
		}
	}
	
	/**
	 * 
	 * @return the web application environment(http://www,https://www-qa,..)
	 */
	public static String getTestEnvironment(){
		logger.debug("Loading Test Environment from the system property");
		String testEnv = System.getProperty("environment");
		logger.debug("Test Environment is "+testEnv);
		if(testEnv == null || AutomationTestConstant.EMPTY.equals(testEnv.trim())){
			logger.warn("Test Environment is NULL or Empty, hence using default value :"+AutomationTestConstant.TEST_ENV_DEFAULT);
			testEnv = AutomationTestConstant.TEST_ENV_DEFAULT;
		}
		
		return testEnv;
	}
	
	
	public static String getQAPassword(){
		String testEnv = System.getProperty("qa.pwd");
		return testEnv;
	}
	
	/**
	 * 
	 * @return the base URL of the web application(http://www,https://www-qa,..)
	 */
	public static String getBaseURL(){
		String testEnv = getTestEnvironment();
		logger.debug("Test Environment is "+testEnv);
		
		String protocol=null;
		
		protocol = "http://";
		
		/*if ("www-qa".equals(testEnv)){
			protocol = "https://";
		}else{
			protocol = "http://";
		}*/
		
		testEnv = protocol + testEnv;
		
		return testEnv;
	}
	
	/**
	 * Returns the indicator whether test would run in local or remote.
	 * 
	 * @return true|false
	 */
	public static boolean isRemoteTestRun(){
		logger.debug("Loading Test Run Environment from the system property");
		String runEnv = System.getProperty("test.run.env");
		logger.debug("Test Run Environment is "+runEnv);
		return (runEnv != null && "remote".equalsIgnoreCase(runEnv));
	}
	
	/**
	 * 
	 * @return the HUB URL - required for remote web driver.
	 */
	public static String getTestHubURL(){
		logger.debug("Loading Test HUB URL from the system property");
		String hubURL = System.getProperty("hub.url");
		logger.debug("Test HUB URL is "+hubURL);
		return hubURL;
	}
	
	/**
	 * Return the folder path where browser screenshot will be stored.
	 * 
	 * It will be used to store screenshot for ant error or test failure.
	 * 
	 * @return the path.
	 */
	public static String getPathForScreenShot(){
		logger.debug("Loading file path for storing ScreenShot");
		String screenShotsPath = System.getProperty("screenshot.path");
		logger.debug("ScreenShot would be store in "+screenShotsPath);
		return screenShotsPath;
	}
	 
	 
	/**
	 * Get list of locale based on the system property 'testLocales'
	 */
	 public static List<String> getTestLocaleList(){

		 //possible locale groups are 'WORLD_WIDE','ATG','COMMERCE' & 'CUSTOMIZE'
		 String testLocaleGroup = System.getProperty("testLocale.group");
		 
		 logger.debug("Test Locale group is: "+testLocaleGroup);
		 
		 if(testLocaleGroup == null || AutomationTestConstant.EMPTY.equals(testLocaleGroup.trim())){
			 logger.warn("Test locale not set in the system, fetching from test metadata properties file");
			 testLocaleGroup = testMataDataProp.getProperty("TEST_RUN.LOCALE.GROUP");
		 }
		 
		 if(testLocaleGroup == null || AutomationTestConstant.EMPTY.equals(testLocaleGroup.trim())){
			 logger.warn("Test locale not set in the system, using default locale : "+AutomationTestConstant.TEST_LOCALES_DEFAULT);
			 testLocaleGroup = AutomationTestConstant.TEST_LOCALES_DEFAULT;
		 }
		 
		 List <String>locales = getLocaleList(testLocaleGroup);
		 
		 return locales;
	 }
	 
	 
	 public static List<String> getLocaleList(String inputLocale){
		 
		 logger.debug("Input Locale is =" + inputLocale);
		
		 List<String> localeList =null;
		 
		 if(inputLocale != null){
			 String localKey = getLocalKey(inputLocale);
			 
			 String locales = testMataDataProp.getProperty(localKey);
			 
			 logger.debug("Locales from the properties file are =" + inputLocale);
			 
			 if(locales != null && !AutomationTestConstant.EMPTY.equals(locales.trim())){
				 localeList = Arrays.asList(locales.split(AutomationTestConstant.COMMA)); 
			 }
		 }

		 return localeList;
	 }
	 
	 public static List<String> getCookieEnabledLocaleList(){
		 
		 String locales = testMataDataProp.getProperty("LOCALE.COOKIE_MODAL_ENABLED");
		 List<String> localeList =null;
		 if(locales != null && !AutomationTestConstant.EMPTY.equals(locales.trim())){
			 localeList = Arrays.asList(locales.split(AutomationTestConstant.COMMA)); 
		 }
		 return localeList;
	 }
	 
	 public static List<String> getISYSServers() {

			List<String> isysServers = null;

			String isysServerKey = "ISYS_SERVERS_KEY";

			String iSYSServers = testMataDataProp.getProperty(isysServerKey);

			if (iSYSServers != null&& !AutomationTestConstant.EMPTY.equals(iSYSServers.trim())) {
				isysServers = Arrays.asList(iSYSServers.split(AutomationTestConstant.COMMA));
			}

			return isysServers;
		}
	 
	 /**
	  * 
	  * @param locales - should be not null 
	  * @return
	  */
	 private static String getLocalKey(String locales){
		 return "LOCALE."+locales.toUpperCase();
	 }
	 
	 /**
	  * 
	  * Returns properties captured from test-metadata.properties file located under data folder.
	  * 
	  */
	 public static Properties getTestMataDataProp(){
		 return testMataDataProp;
	 }
	
	 public static String getDefaultCredential(){
			
		 String credentialKey = "DEFULT_CREDENTIAL";
		 String credentialValue = testMataDataProp.getProperty(credentialKey);
		 return credentialValue;
	}

}
