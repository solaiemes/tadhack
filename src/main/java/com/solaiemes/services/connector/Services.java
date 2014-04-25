package com.solaiemes.services.connector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.solaiemes.constant.Constants;
import com.solaiemes.services.connector.JoynConnector;



public class Services {
	private static final Log log = LogFactory.getLog(Services.class);	
	
	public static void echoConnector(String username, String remoteUri, String message){
		
		String response="You have said: \""+message+"\"";
		if(message.trim().equalsIgnoreCase("help") || message.trim().equalsIgnoreCase("h")){
			log.info("HELP");
			response = "Write a message and you will receive its echo";
		}	
		JoynConnector.sendLongResponse(remoteUri, response, username,Constants.API_ID);
		
	}
	
	public static void welcome(String username, String remoteUri){
		
		String response="Thank you for using this echo services";
		JoynConnector.sendLongResponse(remoteUri, response, username,Constants.API_ID);
		JoynConnector.sendFile("image/png", remoteUri, "linux", "linux", Constants.FILE, username, Constants.API_ID);
		
	}
	

		
}
