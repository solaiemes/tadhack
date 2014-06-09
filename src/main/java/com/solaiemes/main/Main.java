package com.solaiemes.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import com.solaiemes.constant.Constants;
import com.solaiemes.httpsclient.DefaultHttpsClient;
import com.solaiemes.provisioning.UserProvisioning;
import com.solaiemes.services.EcoSeviceHandler;



public class Main extends Thread{
	
	/////////////////////////////////////////PAY ATTENTON !!////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	//BEFORE START, YOU NEED TO EDIT SERVICE_ECO_USERNAME,API_KEY AND API_ID IN JAVA Constants CLASS
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final Log log = LogFactory.getLog(Main.class);
	public static boolean end=false; 
	public static EcoSeviceHandler echo=null;
	public static DefaultHttpClient client = DefaultHttpsClient.getNewHttpClient();
	
	public Main(){
		try {
			end=true;
			start();
		} catch (Exception e) {
			log.error("Echo Sevices can't run ",e);
		}
	}
	
	@Override
	public void run(){
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		end=false;
		
		if(echo==null){
			echo=new EcoSeviceHandler();
		}		
	
		
		try {
			sleep(4000);
		} catch (InterruptedException e1) {		}
		
		System.out.println("\n**********************************");
		System.out.println("FINISH SERVICES AFTER WRITE 'EXIT'");
		System.out.println("**********************************\n");
		
		while(!end){
			try {
				
				String str=br.readLine();
				if(str!=null && str.equalsIgnoreCase("exit")){
					end=true;
				}
				else{
					System.out.println("\n*****************************************************");
					System.out.println("Unknow option "+str+". FINISH SERVICES AFTER WRITE 'EXIT'");
					System.out.println("*****************************************************\n");
				}
				
			} catch (Exception e) {
				System.out.println("\n**********************************");
				System.out.println("FINISH SERVICES AFTER WRITE 'EXIT'");
				System.out.println("**********************************\n");
			}
		}
		
		
		try {
			UserProvisioning.unregister(Constants.SERVICE_ECO_USERNAME,Constants.API_ID);
		} catch (Exception e) {
			log.error("Echo Sevices can't run ",e);
		}finally{
			getHttsClient().getConnectionManager().shutdown();
		}

		
	}
	
	public synchronized static DefaultHttpClient getHttsClient(){
		return client;
	}
	
	public static void main (String arg[]){
		log.info("Running application");
		new Main();
	}
}
