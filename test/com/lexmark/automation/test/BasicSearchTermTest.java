package com.lexmark.automation.test;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.lexmark.automation.test.helper.SeleniumTestNgBaseTest;
import com.lexmark.automation.test.pages.LexmarkHomePage;
import com.lexmark.automation.test.pages.SearchResultsPage;

public class BasicSearchTermTest extends SeleniumTestNgBaseTest {

	/**
	 * Example test.
	 * 
	 * Note that there is a base test that you need to extend to pick up a lot of basic functionality.
	 * 
	 * NO page locators or other information should be on this page--just business logic.
	 */
	
	
	//Need to change the name in the next line to match your test name
	private static final Logger logger = Logger.getLogger(BasicSearchTermTest.class);
		
	//Declare Page Objects that you will need in the test
	private LexmarkHomePage homepage = null;
	private SearchResultsPage searchPage = null;
	
	//before running each test, instantiate your page objects
	@BeforeTest
	public void testSetup(){
		
		logger.info("Creating the home page object...");
		homepage = new LexmarkHomePage();
		
		logger.info("Creating search page object...");
		searchPage = new SearchResultsPage();
	}
	
	//Provide the data provider for this specific test here.
	@Test (dataProvider = "searchTerms")
	public void searchTerm(String s) throws InterruptedException{
		
		/*
		 * Create a softAssert object so you can test the behavior of the site, but not kill the test
		 * if something fails.
		 */
		SoftAssert softAssert = new SoftAssert();
		
		logger.info("Launching Lexmark Home Page...");
		driver.get("http://www.lexmark.com");
		
		logger.info("Searching for " + s);
		homepage.enterSearchTerm(driver, s);
		
		// Test to see if the verifyResultsExist method returns true. If not, log a failure.
		softAssert.assertTrue(searchPage.verifyResultsExist(driver),"FAIL: Search string: " + s + " did not lead to search results.");
				
		logger.info("Finished with test");
		
		//Report any failures found during the test 
		softAssert.assertAll();
	}
	
	//Use the Data Provider information from the Selenium Hint and Tricks Notebook: 
	//https://www.evernote.com/pub/weems1974/seleniumhintsandtips
	@DataProvider
	public Object[][] searchTerms(){
		Object [][] searchTermList = new Object[3][1];
			
		searchTermList[0][0]="printer";
		searchTermList[1][0]="";
		searchTermList[2][0]="healthcare";
			
		return searchTermList;
			
		}
		
	
	
}
