package com.intuit.mint.splunk.AutoMonitor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.splunk.Application;
import com.splunk.Args;
import com.splunk.Job;
import com.splunk.JobArgs;
import com.splunk.JobCollection;
import com.splunk.ResultsReaderXml;
import com.splunk.Service;
import com.splunk.ServiceArgs;

public class SplunkJob {
	
	 public static void main(String[] args) {

	        // Create a map of arguments and add login parameters
	        ServiceArgs loginArgs = new ServiceArgs();
	        loginArgs.setUsername("admin");
	        loginArgs.setPassword("intuit");
	        loginArgs.setHost("127.0.0.1");
	        loginArgs.setPort(8089);
	        
	        Service service = new Service("localhost", 8089);
	        service.login("admin", "intuit");
	        
	        // Create a Service instance and log in with the argument map
	       // Service service = Service.connect(loginArgs);


	        // A second way to create a new Service object and log in
	        // Service service = new Service("localhost", 8089);
	        // service.login("admin", "changeme");

	        // A third way to create a new Service object and log in
	        // Service service = new Service(loginArgs);
	        // service.login();

	        // Print installed apps to the console to verify login
	        for (Application app : service.getApplications().values()) {
	            System.out.println(app.getName());
	        }
	        
	     // Retrieve the collection
	        JobCollection jobs = service.getJobs();
	        System.out.println("There are " + jobs.size() + " jobs available to 'admin'\n");

	        // List the job SIDs
	        for (Job job : jobs.values()) {
	            System.out.println(job.getName());
	        }
	        
	        
	        
	     // Run a normal search
	        String searchQuery_normal = "search * | head 100";
	        JobArgs jobargs = new JobArgs();
	        jobargs.setExecutionMode(JobArgs.ExecutionMode.NORMAL);
	        Job job = service.getJobs().create(searchQuery_normal, jobargs);

	        // Wait for the search to finish
	        while (!job.isDone()) {
	            try {
	                Thread.sleep(500);
	            } catch (InterruptedException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }

	        // Get the search results and use the built-in XML parser to display them
	        InputStream resultsNormalSearch =  job.getResults();

	        ResultsReaderXml resultsReaderNormalSearch;

	        try {
	            resultsReaderNormalSearch = new ResultsReaderXml(resultsNormalSearch);
	            HashMap<String, String> event;
	            while ((event = resultsReaderNormalSearch.getNextEvent()) != null) {
	                System.out.println("\n****************EVENT****************\n");
	                for (String key: event.keySet())
	                    System.out.println("   " + key + ":  " + event.get(key));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        // Get properties of the completed job
	        System.out.println("\nSearch job properties\n---------------------");
	        System.out.println("Search job ID:         " + job.getSid());
	        System.out.println("The number of events:  " + job.getEventCount());
	        System.out.println("The number of results: " + job.getResultCount());
	        System.out.println("Search duration:       " + job.getRunDuration() + " seconds");
	        System.out.println("This job expires in:   " + job.getTtl() + " seconds");
	        
	        System.out.println("****************************");
	        
	     // Set the parameters for the search:
	        Args oneshotSearchArgs = new Args(); 
	        oneshotSearchArgs.put("earliest_time", "2015-06-24T12:00:00.000-07:00");
	        oneshotSearchArgs.put("latest_time",   "2015-06-25T12:00:00.000-07:00");
	        String oneshotSearchQuery = "search * | head 10";

	        // The search results are returned directly
	        InputStream results_oneshot =  service.oneshotSearch(oneshotSearchQuery, oneshotSearchArgs);

	        // Get the search results and use the built-in XML parser to display them
	        try {
	            ResultsReaderXml resultsReader = new ResultsReaderXml(results_oneshot);
	            System.out.println("Searching everything in a 24-hour time range starting June 19, 12:00pm and displaying 10 results in XML:\n");
	            HashMap<String, String> event;
	            while ((event = resultsReader.getNextEvent()) != null) {
	                System.out.println("\n********EVENT********");
	                for (String key: event.keySet())
	                    System.out.println("   " + key + ":  " + event.get(key));
	                
	            }
	            resultsReader.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	    }

	 
	 
}
