package com.solaiemes.services;




import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.solaiemes.constant.Constants;
import com.solaiemes.main.Main;
import com.solaiemes.provisioning.UserProvisioning;
import com.solaiemes.services.connector.JoynConnector;
import com.solaiemes.services.connector.Services;


public class EcoSeviceHandler extends Thread {
	
	private static final Log log = LogFactory.getLog(EcoSeviceHandler.class);
	private boolean firstMessage=true;
	public EcoSeviceHandler(){
		try {			
			UserProvisioning.register(Constants.SERVICE_ECO_USERNAME,Constants.API_ID);
			start();
		} catch (Exception e) {
			log.error("Echo Example Sevices can't run ",e);
		}
	}
	
	@Override
	public void run(){
		log.info("Echo Example Sevices");
		while(!Main.end){
			try {
				JSONArray jsa=JoynConnector.comet(Constants.SERVICE_ECO_USERNAME);
				for(int i=0;i<jsa.size();i++){
					JSONObject json =jsa.getJSONObject(i);
					if(json.containsKey("message") && json.containsKey("contactId")){
						if(json.containsKey("action") && json.getString("action").equals("receiveMessage")){
							log.info("Instant messaging received: "+json.getString("message"));
							
							if(firstMessage){
								Services.welcome(Constants.SERVICE_ECO_USERNAME, json.getString("contactId"));
							}
							
							Services.echoConnector(Constants.SERVICE_ECO_USERNAME, json.getString("contactId"), json.getString("message"));
							firstMessage=false;
						}						
					}
				}
				
			} catch (Exception e) {
				log.error("Dictionary Sevices is failing ",e);
			}
		}
		
		
	}
}
