package com.lexmark.automation.test.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class LinkValidationHelper {

	private static final Logger logger = Logger.getLogger(LinkValidationHelper.class);

	private static String primaryProxyHost;

	private static String primaryProxyPort;

	private static String secondaryProxyHost;

	private static String secondaryProxyPort;
    
	private static int connection_time_out;
	
	private static boolean proxyEnabled = false;

	static {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(
					"data/test-metadata.properties"));
			if (properties.getProperty("PROXY_ENABLED") != null
					&& "Y".equalsIgnoreCase(properties
							.getProperty("PROXY_ENABLED"))) {
				
				logger.info("Proxy is enabled");
				
				proxyEnabled = true;

				primaryProxyHost = properties.getProperty("PRIMARY_PROXY_HOST");
				primaryProxyPort = properties.getProperty("PRIMARY_PROXY_PORT");
				
				logger.info("Primary Proxy Host: "+primaryProxyHost+", Port: "+primaryProxyPort);

				secondaryProxyHost = properties
						.getProperty("SECONDARY_PROXY_HOST");
				secondaryProxyPort = properties
						.getProperty("SECONDARY_PROXY_PORT");
				
				logger.info("Secondary Proxy Host: "+secondaryProxyHost+", Port: "+secondaryProxyPort);
				
				connection_time_out=Integer.parseInt(properties.getProperty("CONNECTION_TIME_OUT"));
				logger.info("Connection time out: "+connection_time_out);
			}

		} catch (FileNotFoundException e) {
			logger.error("Error occure while loading data/test-metadata.properties",e);
		} catch (IOException e) {
			logger.error("Error occure while loading data/test-metadata.properties",e);
		}
	}
	
	public static synchronized boolean isBrokenLink(String url,String cookieString) {
		return isBrokenLink(url, cookieString, false);
	}

	public static synchronized boolean isBrokenLink(String url,String cookieString,boolean acceptRedirect) {
		boolean broken = true;
		int responseCode = 0;
		if(proxyEnabled){
			System.setProperty("http.proxySet", "true");
			System.setProperty("http.proxyHost",primaryProxyHost);
			System.setProperty("http.proxyPort", primaryProxyPort);
		}
		try {
			responseCode = getResponseCode(url,cookieString);
		}catch(SocketTimeoutException sktToutEx){
			if(proxyEnabled){
				System.setProperty("http.proxyHost",secondaryProxyHost);
				System.setProperty("http.proxyPort", secondaryProxyPort);
				try {
					responseCode = getResponseCode(url,cookieString);
				} catch (Exception exp) {
					logger.warn(exp);
				}
			}
		}catch (Exception ex) {
			logger.warn(ex);
		}
		
		if(proxyEnabled){
			System.clearProperty("http.proxySet");
			System.clearProperty("http.proxyHost");
			System.clearProperty("http.proxyPort");
		}
		
		logger.info("HTTP response Code::" + responseCode+" for URL:: "+url);
		
		if (HttpURLConnection.HTTP_OK == responseCode) {
			broken = false;
		}else if(acceptRedirect && 
				(HttpURLConnection.HTTP_MOVED_PERM == responseCode || HttpURLConnection.HTTP_MOVED_TEMP == responseCode)){
			broken = false;
		}
		
		return broken;
	}
	
	
	private static int getResponseCode(String url,String cookieString) throws MalformedURLException, IOException{
		HttpURLConnection.setFollowRedirects(true);
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setConnectTimeout(connection_time_out);
		con.setRequestMethod("GET");
		if(StringUtils.isNotEmpty(cookieString)){
			con.setRequestProperty("Cookie", cookieString);
		}
		int responseCode = con.getResponseCode();
		if(con != null){
			con.disconnect();
		}
		return responseCode;
	}
	
}
