package com.lexmark.automation.test.helper;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitHelper {
	
	public static WebElement waitUntillElementIsPresentByName(WebDriver webDriver,TimeUnit timeUnit,long retryInterval,long timeOut,By by,String textName){
		
		WebElement webElement = null;
		
		long startTime = System.currentTimeMillis();
		long maxWaitTime = startTime + TimeUnit.MILLISECONDS.convert(timeOut, timeUnit);
		
		while(maxWaitTime > System.currentTimeMillis() && webElement == null){
			try {
				webElement = webDriver.findElement(by);
				if (! (webElement.getText().equalsIgnoreCase(textName))) {
					try {
						Thread.sleep(TimeUnit.MILLISECONDS.convert(retryInterval, timeUnit));
					} catch (InterruptedException e1) {
						
					}
				}
			} catch (NoSuchElementException e) {
				try {
					Thread.sleep(TimeUnit.MILLISECONDS.convert(retryInterval, timeUnit));
				} catch (InterruptedException e1) {
					
				}
			}
		}
		
		if(webElement == null){
			String currentURL = webDriver.getCurrentUrl();
			String selector = by.toString();
			if(selector.length() > 3){
				selector = selector.substring(3);
			}
			StringBuilder messageBuilder = new StringBuilder();
			messageBuilder.append("Element having name: "+textName+", and "+selector+" is not present in the current page: "+currentURL);
			throw new TimeoutException(messageBuilder.toString());
		}
		
		return webElement;
		
	}
	
	public static WebElement waitUntillElementIsPresent(WebDriver webDriver,TimeUnit timeUnit,long retryInterval,long timeOut,By by){
		
		WebElement webElement = null;
		
		long startTime = System.currentTimeMillis();
		long maxWaitTime = startTime + TimeUnit.MILLISECONDS.convert(timeOut, timeUnit);
		
		while(maxWaitTime > System.currentTimeMillis() && webElement == null){
			try {
				webElement = webDriver.findElement(by);
			} catch (NoSuchElementException e) {
				try {
					Thread.sleep(TimeUnit.MILLISECONDS.convert(retryInterval, timeUnit));
				} catch (InterruptedException e1) {
					
				}
			}
		}
		
		if(webElement == null){
			String currentURL = webDriver.getCurrentUrl();
			String selector = by.toString();
			if(selector.length() > 3){
				selector = selector.substring(3);
			}
			StringBuilder messageBuilder = new StringBuilder();
			messageBuilder.append("Element having "+selector+" is not present in the current page: "+currentURL);
			throw new TimeoutException(messageBuilder.toString());
		}
		
		return webElement;
		
	}
	
    
	
	public static WebElement waitUntillElementIsClickable(WebDriver webDriver,TimeUnit timeUnit,long retryInterval,long timeOut,By by,boolean clickElement){
		
		boolean elementClickable = false;
		WebElement element = null;
		
		long startTime = System.currentTimeMillis();
		long maxWaitTime = startTime + TimeUnit.MILLISECONDS.convert(timeOut, timeUnit);
		boolean isError = false;
		
		while(maxWaitTime > System.currentTimeMillis() && (isError || !elementClickable)){
			try {
				element = webDriver.findElement(by);
				elementClickable = element.isDisplayed() && element.isEnabled();
				if(!elementClickable){
					try {
						Thread.sleep(TimeUnit.MILLISECONDS.convert(retryInterval, timeUnit));
					} catch (InterruptedException e1) {
						
					}
				}else{
					if(clickElement){
						element.click();
						elementClickable = true;
					}
				}
				isError = false;
			} catch (NoSuchElementException e) {
				
				try {
					Thread.sleep(TimeUnit.MILLISECONDS.convert(retryInterval, timeUnit));
				} catch (InterruptedException e1) {
					
				}
			}catch (WebDriverException e) {
				isError = true;
				try {
					Thread.sleep(TimeUnit.MILLISECONDS.convert(retryInterval, timeUnit));
				} catch (InterruptedException e1) {
					
				}
			}
		}
		
		if(element == null || !elementClickable){
			String currentURL = webDriver.getCurrentUrl();
			String selector = by.toString();
			if(selector.length() > 3){
				selector = selector.substring(3);
			}
			
			StringBuilder messageBuilder = new StringBuilder();
			if(element == null){
				messageBuilder.append("Element having "+selector+" is not present in the current page: "+currentURL);
			}else{
				messageBuilder.append("Element having "+selector+" is not clickable in the current page: "+currentURL);
			}
			
			throw new TimeoutException(messageBuilder.toString());
		}
		
		return element;
		
	}
	public static void waitUntillCommandReturnsTrue(ExecuteCommand<Boolean> executeCommand, TimeUnit timeUnit,long retryInterval, long timeOut) {

		boolean result = false;

		long startTime = System.currentTimeMillis();
		long maxWaitTime = startTime + TimeUnit.MILLISECONDS.convert(timeOut, timeUnit);

		while (maxWaitTime > System.currentTimeMillis() && !result) {
			result = executeCommand.execute();
			if (!result) {
				try {
					Thread.sleep(TimeUnit.MILLISECONDS.convert(retryInterval,
							timeUnit));
				} catch (InterruptedException e1) {
					
				}
			}
		}

		if (!result) {
			throw new TimeoutException();
		}

	}
	public static void waitForPageLoad(WebDriver driver,long timeOutInSeconds) {
	    ExpectedCondition<Boolean> pageLoadCondition = new
	        ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver driver) {
	                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	            }
	        };
	    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
	    wait.until(pageLoadCondition);
	}
}
