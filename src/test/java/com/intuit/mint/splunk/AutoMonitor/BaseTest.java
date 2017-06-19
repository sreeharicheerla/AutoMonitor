package com.intuit.mint.splunk.AutoMonitor;

import org.apache.log4j.Level;


public class BaseTest {
	
	Splunk spl = new Splunk("", 0, "", "");


	//@AfterMethod
	public void afterTest() {
		  spl.getLogs("2015-06-24T12:00:00.000-07:00", "2015-06-25T06:25:00.000-07:00",null);
	}
	
	protected void log(String message) { //default is info level
		Log4jUtil.log(message);
	}
	
	protected void log(String message, String level) {
		Log4jUtil.log(message, level);
	}

	protected void log(String message, Level level) {
		Log4jUtil.log(message, level);
	}
	
}
