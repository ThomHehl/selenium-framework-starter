package com.lexmark.automation.test.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LexmarkHomePage {

	/**
	 * Sample page object.  In our model, we separate the business logic from the actual structure of the page to
	 * make the tests more maintainable.
	 * 
	 * A page object is just a class that represents a page, and contains the attributes and behaviors (ways a user 
	 * can interact with the page).  Any information about page structure--locators, etc. should live in
	 *  the page object.  Nothing of the sort should be in the test. 
	 */
	
	//For ease of maintenance, keep your element locators in variables at the top of the class
	private static final String SEARCH_BAR_ID = "search-large";
	
	//The next line instantiates the logger for your class.
	private static final Logger logger = Logger.getLogger(LexmarkHomePage.class);
	
	/*
	 * Every method on the page object should represent some action a user can take on a page.
	 * 
	 * You will ALWAYS need to pass in the driver object from your test
	 */
	public void enterSearchTerm(WebDriver driver, String searchTerm){
	
		logger.info("Going to enter search term: " + searchTerm);
		
		//first, create a WebElement object with which you can interact
		WebElement searchBar = driver.findElement(By.id(SEARCH_BAR_ID));
		
		//then send the keyword to it, then send the enter key
		searchBar.sendKeys(searchTerm);
		searchBar.sendKeys(Keys.ENTER);

		return;
	}

}
