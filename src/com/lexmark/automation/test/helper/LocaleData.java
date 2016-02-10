package com.lexmark.automation.test.helper;

import java.util.Arrays;
import java.util.List;

public class LocaleData {
	
	private String locale;
	
	public LocaleData(String locale){
		this.locale = locale;
	}
		
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public String getLocale() {
		return locale;
	}

	public List<String> getSearchTerm() {
		
		if(locale == null || AutomationTestConstant.EMPTY.equals(locale.trim())){
			throw new RuntimeException("locale is NULL or empty - please set a valid locale and retry");
		}
		
		List<String> searchTerms = null;
		
		String searchTermKey = "SEARCH_TERM."+locale.substring(1).toUpperCase();
					
		String localeSearchTerms = TestSetup.getTestMataDataProp().getProperty(searchTermKey);
			
		if(localeSearchTerms != null && !AutomationTestConstant.EMPTY.equals(localeSearchTerms.trim())){
			searchTerms = Arrays.asList(localeSearchTerms.split(AutomationTestConstant.COMMA)); 
		}
		
		return searchTerms;
	}

	//TODO not in use
	public String getTonerSearchURLSuffix() {
		if(locale == null || AutomationTestConstant.EMPTY.equals(locale.trim())){
			throw new RuntimeException("locale is NULL or empty - please set a valid locale and retry");
		}
		
		 String localeTemp = locale.replaceFirst("/", "");
		 String tomerUrlKey = "TONER_SEARCH_URL."+localeTemp.toUpperCase();
		
		return TestSetup.getTestMataDataProp().getProperty(tomerUrlKey);
	}
	
	public String getCountryCode(){
		return locale.substring(4);
	}
	
	public String getLanguageCode(){
		return locale.substring(1,3);
	}
	
	public String getCatalogueNumber(){
		String localeTemp = locale.replaceFirst("/", "");
		String catalogueNumberKey = "CATALOG_NUMBER."+localeTemp.toUpperCase();
		return TestSetup.getTestMataDataProp().getProperty(catalogueNumberKey);
	}
	
	public String getPhoneNumber(){
		String localeTemp = locale.replaceFirst("/", "");
		String catalogueNumberKey = "PHONE_NUMBER."+localeTemp.toUpperCase();
		return TestSetup.getTestMataDataProp().getProperty(catalogueNumberKey);
	}
	
	public String getPostalCode(){
		String localeTemp = locale.replaceFirst("/", "");
		String catalogueNumberKey = "POSTAL_CODE."+localeTemp.toUpperCase();
		return TestSetup.getTestMataDataProp().getProperty(catalogueNumberKey);
	}
	
	public String getAddressLineOne(){
		String localeTemp = locale.replaceFirst("/", "");
		String catalogueNumberKey = "ADD_LINE_ONE."+localeTemp.toUpperCase();
		return TestSetup.getTestMataDataProp().getProperty(catalogueNumberKey);
	}
	
	public String getCity(){
		String localeTemp = locale.replaceFirst("/", "");
		String catalogueNumberKey = "CITY."+localeTemp.toUpperCase();
		return TestSetup.getTestMataDataProp().getProperty(catalogueNumberKey);
	}
	
	public List<String> getProducts(){
		String localeTemp = locale.replaceFirst("/", "");
		 String productKey = "PRODUCT."+localeTemp.toUpperCase();
		 String pids = TestSetup.getTestMataDataProp().getProperty(productKey);
		 List<String> pidList = null;
		 if(pids != null && !pids.trim().equals("")){
			 pidList = Arrays.asList(pids.split(","));
		 }
		 
		 return pidList;
	}
	
	
	public List<String> getSupportedLocales(){
		String localeTemp = locale.replaceFirst("/", "");
		
		String supportedLocaleKey = localeTemp.toLowerCase()+".supported_locales.exceptions";
		String supportedLocales = TestSetup.getTestMataDataProp().getProperty(supportedLocaleKey);
		 List<String> supportedLocaleList = null;
		 if(supportedLocales != null && !supportedLocales.trim().equals("")){
			 supportedLocaleList = Arrays.asList(supportedLocales.split(","));
		 }
		 
		 return supportedLocaleList;
	}
	
	public String getPidPrefix(){
		String prefixEnabledLocales = TestSetup.getTestMataDataProp().getProperty("PID.PREFIX.ENABLE");
		String prefix = null;
		if(prefixEnabledLocales.contains(locale)){
			prefix = getCountryCode();
		}else{
			prefix = AutomationTestConstant.EMPTY;
		}
		
		return prefix;
	}
	
	
	public List<String> getSuppliesItemProductNumber() {
		
		if(locale == null || AutomationTestConstant.EMPTY.equals(locale.trim())){
			throw new RuntimeException("locale is NULL or empty - please set a valid locale and retry");
		}
		
		List<String> suppliesProductNumbers = null;
		
		String suppliesProductNumberKey = "SUPPLIES_ITEM_PRODUCT_NUMBER."+locale.substring(1).toUpperCase();
					
		String localeSuppliesProductNumbers = TestSetup.getTestMataDataProp().getProperty(suppliesProductNumberKey);
			
		if(localeSuppliesProductNumbers != null && !AutomationTestConstant.EMPTY.equals(localeSuppliesProductNumbers.trim())){
			suppliesProductNumbers = Arrays.asList(localeSuppliesProductNumbers.split(AutomationTestConstant.COMMA)); 
		}
		
		return suppliesProductNumbers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocaleData other = (LocaleData) obj;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LocaleData [locale=" + locale + "]";
	}
	
	
	
	

}
