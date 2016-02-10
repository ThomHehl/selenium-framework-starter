package com.lexmark.automation.test.helper;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;

public class LocaleDataProvider {
	
	
	@DataProvider
	public static Object[][] commerceLocales(){
		
		//get the list of commerce locales
		//disregard the locales passed in through Ant, only valid on commerce
		List<String> localeList = TestSetup.getLocaleList("commerce");
		
		List<String> testLocaleList = TestSetup.getTestLocaleList();
		
		List<String> alowedLocaleList = new ArrayList<String>();
		for(String locale : localeList){
			if(testLocaleList.contains(locale)){
				alowedLocaleList.add(locale);
			}
		}
		
		//Load the two dimensional array with the locale list results
		Object [][] locales = new Object[alowedLocaleList.size()][1];
	
		for (int x=0;x<locales.length;x++){
			locales[x][0] = new LocaleData(alowedLocaleList.get(x));
		}

		return locales;
		
	}
	
	@DataProvider(name="world-wide-locales")
	public static Object[][] getWorldWideLocales(){
		
		//get the list of commerce locales
		//disregard the locales passed in through Ant, only valid on commerce
		List<String> localeList = TestSetup.getLocaleList("WORLD_WIDE");
		
		List<String> testLocaleList = TestSetup.getTestLocaleList();
		
		List<String> alowedLocaleList = new ArrayList<String>();
		for(String locale : localeList){
			if(testLocaleList.contains(locale)){
				alowedLocaleList.add(locale);
			}
		}
		
		//Load the two dimensional array with the locale list results
		Object [][] locales = new Object[alowedLocaleList.size()][1];
	
		for (int x=0;x<locales.length;x++){
			locales[x][0] = new LocaleData(alowedLocaleList.get(x));
		}

		return locales;
		
	}
	
	@DataProvider(name="atg-locales")
	public static Object[][] getATGLocales(){
		
		//get the list of commerce locales
		//disregard the locales passed in through Ant, only valid on commerce
		List<String> localeList = TestSetup.getLocaleList("atg");
		
		List<String> testLocaleList = TestSetup.getTestLocaleList();
		
		List<String> alowedLocaleList = new ArrayList<String>();
		for(String locale : localeList){
			if(testLocaleList.contains(locale)){
				alowedLocaleList.add(locale);
			}
		}
		
		//Load the two dimensional array with the locale list results
		Object [][] locales = new Object[alowedLocaleList.size()][1];
	
		for (int x=0;x<locales.length;x++){
			locales[x][0] = new LocaleData(alowedLocaleList.get(x));
		}

		return locales;
		
	}
	
	@DataProvider(name="non-commerce-atg-locales")
	public static Object[][] getNonCommerceATGLocales(){
		
		//get the list of commerce locales
		//disregard the locales passed in through Ant, only valid on commerce
		List<String> localeList = TestSetup.getLocaleList("ATG.NON_COMMERCE");
		
		List<String> testLocaleList = TestSetup.getTestLocaleList();
		
		List<String> alowedLocaleList = new ArrayList<String>();
		for(String locale : localeList){
			if(testLocaleList.contains(locale)){
				alowedLocaleList.add(locale);
			}
		}
		
		//Load the two dimensional array with the locale list results
		Object [][] locales = new Object[alowedLocaleList.size()][1];
	
		for (int x=0;x<locales.length;x++){
			locales[x][0] = new LocaleData(alowedLocaleList.get(x));
		}

		return locales;
		
	}
	
	
	@DataProvider
	public static Object[][] newDesignEnabledLocales(){
		List<String> localeList = TestSetup.getLocaleList("NEW_DESIGN_ENABLED");
		
		List<String> testLocaleList = TestSetup.getTestLocaleList();
		
		List<String> alowedLocaleList = new ArrayList<String>();
		for(String locale : localeList){
			if(testLocaleList.contains(locale)){
				alowedLocaleList.add(locale);
			}
		}
		
		//Load the two dimensional array with the locale list results
		Object [][] locales = new Object[alowedLocaleList.size()][1];
	
		for (int x=0;x<locales.length;x++){
			locales[x][0] = new LocaleData(alowedLocaleList.get(x));
		}

		return locales;
		
	}
	
		
	@DataProvider(name="dealer-locator-T1-locales")
	public static Object[][] enabledDealerLocatorT1Locales(){
		
		List<String> localeList = TestSetup.getLocaleList("DEALER_LOCATOR_ENABLED_T1");
		
		List<String> testLocaleList = TestSetup.getTestLocaleList();
		
		List<String> alowedLocaleList = new ArrayList<String>();
		for(String locale : localeList){
			if(testLocaleList.contains(locale)){
				alowedLocaleList.add(locale);
			}
		}
		
		//Load the two dimensional array with the locale list results
		Object [][] locales = new Object[alowedLocaleList.size()][1];
	
		for (int x=0;x<locales.length;x++){
			locales[x][0] = new LocaleData(alowedLocaleList.get(x));
		}

		return locales;
		
	}
	
	@DataProvider(name="dealer-locator-locales")
	public static Object[][] enabledDealerLocatorLocales(){
		
		List<String> localeList = TestSetup.getLocaleList("DEALER_LOCATOR_ENABLED");
		
		List<String> testLocaleList = TestSetup.getTestLocaleList();
		
		List<String> alowedLocaleList = new ArrayList<String>();
		for(String locale : localeList){
			if(testLocaleList.contains(locale)){
				alowedLocaleList.add(locale);
			}
		}
		
		//Load the two dimensional array with the locale list results
		Object [][] locales = new Object[alowedLocaleList.size()][1];
	
		for (int x=0;x<locales.length;x++){
			locales[x][0] = new LocaleData(alowedLocaleList.get(x));
		}

		return locales;
		
	}
	
	@DataProvider
	public static Object[][] getDaySpecificTestLocales(){
		
        //No. of rows = times the test is repeated
        //No. of columns = parameters needed for each run of the test
		
		String inputLocales = System.getProperty("testLocales");
		List<String> localeList = null;
		
		if(!StringUtils.isEmpty(inputLocales)){
			localeList = Arrays.asList(inputLocales.split(AutomationTestConstant.COMMA));
		}else{
			//get the list of locales from test-metadata.properties file.
			String dayNames[] = new DateFormatSymbols().getWeekdays();
			String key = "TEST_RUN."+dayNames[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)];
			localeList = TestSetup.getLocaleList(key);
		}

		//Load the two dimensional array with the locale list results
		Object [][] locales = new Object[localeList.size()][1];
	
		for (int x=0;x<locales.length;x++){
			locales[x][0] = new LocaleData(localeList.get(x));
		}

		return locales;
		
	}
	
	@DataProvider
	public static Object[][] getDaySpecificNonCommerceTestLocales(){
		
        //No. of rows = times the test is repeated
        //No. of columns = parameters needed for each run of the test
		
		String inputLocales = System.getProperty("testLocales");
		List<String> localeList = null;
		
		if(!StringUtils.isEmpty(inputLocales)){
			localeList = Arrays.asList(inputLocales.split(AutomationTestConstant.COMMA));
		}else{
			//get the list of locales from test-metadata.properties file.
			String dayNames[] = new DateFormatSymbols().getWeekdays();
			String key = "TEST_RUN."+dayNames[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)];
			localeList = TestSetup.getLocaleList(key);
		}
		
		List<String> commerceLocaleList = TestSetup.getLocaleList("commerce");
		List<String> nonCommerceLocaleList = new ArrayList<String>(localeList);
		nonCommerceLocaleList.removeAll(commerceLocaleList);

		//Load the two dimensional array with the locale list results
		Object [][] locales = new Object[nonCommerceLocaleList.size()][1];
	
		for (int x=0;x<locales.length;x++){
			locales[x][0] = new LocaleData(nonCommerceLocaleList.get(x));
		}

		return locales;
		
	}
	
	@DataProvider
	public static Object[][] getDaySpecificCommerceTestLocales(){
		
        //No. of rows = times the test is repeated
        //No. of columns = parameters needed for each run of the test
		
		String inputLocales = System.getProperty("testLocales");
		List<String> localeList = null;
		
		if(!StringUtils.isEmpty(inputLocales)){
			localeList = Arrays.asList(inputLocales.split(AutomationTestConstant.COMMA));
		}else{
			//get the list of locales from test-metadata.properties file.
			String dayNames[] = new DateFormatSymbols().getWeekdays();
			String key = "TEST_RUN."+dayNames[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)];
			localeList = TestSetup.getLocaleList(key);
		}
		
		List<String> commerceLocaleList = TestSetup.getLocaleList("commerce");
		List<String> daySpecificCommerceLocaleList = new ArrayList<String>(localeList);
		for(String locale : localeList){
			if(!commerceLocaleList.contains(locale)){
				daySpecificCommerceLocaleList.remove(locale);
			}
		}

		//Load the two dimensional array with the locale list results
		Object [][] locales = new Object[daySpecificCommerceLocaleList.size()][1];
	
		for (int x=0;x<locales.length;x++){
			locales[x][0] = new LocaleData(daySpecificCommerceLocaleList.get(x));
		}

		return locales;
		
	}
	
	@DataProvider
	public static Object[][] getDaySpecificNonAtgTestLocales(){
		
        //No. of rows = times the test is repeated
        //No. of columns = parameters needed for each run of the test
		
		String inputLocales = System.getProperty("testLocales");
		List<String> localeList = null;
		
		if(!StringUtils.isEmpty(inputLocales)){
			localeList = Arrays.asList(inputLocales.split(AutomationTestConstant.COMMA));
		}else{
			//get the list of locales from test-metadata.properties file.
			String dayNames[] = new DateFormatSymbols().getWeekdays();
			String key = "TEST_RUN."+dayNames[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)];
			localeList = TestSetup.getLocaleList(key);
		}
		
		List<String> atgLocaleList = TestSetup.getLocaleList("atg");
		List<String> nonATGLocaleList = new ArrayList<String>(localeList);
		nonATGLocaleList.removeAll(atgLocaleList);

		//Load the two dimensional array with the locale list results
		Object [][] locales = new Object[nonATGLocaleList.size()][1];
	
		for (int x=0;x<locales.length;x++){
			locales[x][0] = new LocaleData(nonATGLocaleList.get(x));
		}

		return locales;
		
	}

}
