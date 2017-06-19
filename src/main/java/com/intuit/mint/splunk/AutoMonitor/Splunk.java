package com.intuit.mint.splunk.AutoMonitor;

import java.io.InputStream;
import java.util.HashMap;

import com.splunk.Args;
import com.splunk.ResultsReaderXml;
import com.splunk.Service;

public class Splunk {

    Service service;
    
	public Splunk(String hostName, int port, String userName , String password) {
        service = new Service("localhost", 8089);
        service.login("admin", "intuit");
	}
	
	public String getLogs(String startDate, String endDate,String searchString) {
		
        Args oneshotSearchArgs = new Args();         
        oneshotSearchArgs.put("earliest_time", startDate);
        oneshotSearchArgs.put("latest_time",   endDate);
        if (searchString == null) {
			searchString = "*";
		}
        String oneshotSearchQuery = "search " + searchString + " | tail 10";
        
        String result = "";
        InputStream results_oneshot =  service.oneshotSearch(oneshotSearchQuery, oneshotSearchArgs);

        try {
            ResultsReaderXml resultsReader = new ResultsReaderXml(results_oneshot);
            System.out.println("Searching:\n");
            HashMap<String, String> event;
            while ((event = resultsReader.getNextEvent()) != null) {
                	 System.out.println(event.get("_raw"));
                	 result = result + "\n" + event.get("_raw");
            }
            resultsReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
        return result;
	}
	
	
}
