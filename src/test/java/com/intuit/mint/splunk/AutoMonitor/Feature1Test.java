package com.intuit.mint.splunk.AutoMonitor;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class Feature1Test extends BaseTest {
	
	
	Splunk spl = new Splunk("", 0, "", "");

  @BeforeSuite
  public void SetUp() {
	  
  }
	
  @Test
  public void Test1() {
	  System.out.println("This is Test 1\n");
	  System.out.println("\n\n*********Server Logs**********\n\n");
	  spl.getLogs("2015-06-24T12:00:00.000-07:00", "2015-06-25T06:25:00.000-07:00",null);

  }
  
  
  @Test
  public void Test2() {
	  System.out.println("\n\n This is Test 2");
	  System.out.println("\n\n*********Server Logs**********\n\n");
	  spl.getLogs("2015-06-24T12:00:00.000-07:00", "2015-06-25T06:25:00.000-07:00",null);

  }
  
  @AfterClass
  public void afterClass() {
	  System.out.println("\n\n********* All the Exceptions**************\n\n");
	  spl.getLogs("2015-06-25T12:00:00.000-07:00", "2015-06-26T06:25:00.000-07:00","source=mint-web.log ");
  }
}
