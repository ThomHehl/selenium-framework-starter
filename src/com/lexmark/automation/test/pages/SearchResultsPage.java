package com.lexmark.automation.test.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage {

	//For ease of maintenance, keep your element locators in variables at the top of the class
	private static final String FIRST_SEARCH_RESULT_ID = "isys-search-results-title-0";
	
	//The next line instantiates the logger for your class.
	private static final Logger logger = Logger.getLogger(SearchResultsPage.class);
	
	/*
	 * Method represents something a user can do on a page--in this case, see if there are any search results
	 */
	public Boolean verifyResultsExist(WebDriver driver){
		
	/*
	 * For this test, we're looking to see if the first search result is there. If it is, the test will pass
	 * If it isn't, in this case, there would be a NoSuchElementException thrown.
	 * 	
	 * We don't ever want exceptions to be in our tests--we should handle any of them and have only assertion 
	 * failures--failures because a test we specifically asked for has failed.
	 * 
	 * So, in this case, we catch the exception and return false back to the test because there was a case where 
	 * there were no search results.
	 */
		boolean searchTermFound = false;
		
		
		try{
			driver.findElement(By.id(FIRST_SEARCH_RESULT_ID));
			
			//If the prior statement doesn't throw an exception, then you found a search item
			searchTermFound = true;
			logger.info("Search term led to search results");
			
		}catch(NoSuchElementException e){
			logger.info("FAIL:  No results for that search term");
		}
		logger.info("SearchTermFound returning: " + searchTermFound);			
		return searchTermFound;
		
	}
}
