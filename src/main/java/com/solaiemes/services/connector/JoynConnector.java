package com.solaiemes.services.connector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.solaiemes.constant.Constants;
import com.solaiemes.main.Main;


public class JoynConnector {
	
	private static final Log log = LogFactory.getLog(JoynConnector.class);
	public static void sendLongResponse(String contactId, String responseText, String username,String token){

		HttpPost im = new HttpPost(Constants.URLIM);
		im.addHeader("app_id", Constants.API_ID);
		im.addHeader("app_key", Constants.API_KEY);

		try {
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		    nameValuePairs.add(new BasicNameValuePair("action", "sendIMMessageAutomaticSession"));
		    nameValuePairs.add(new BasicNameValuePair("username", username));
		    if(token!=null)
		    	nameValuePairs.add(new BasicNameValuePair("token", token));
		    nameValuePairs.add(new BasicNameValuePair("contactId", contactId));
		    nameValuePairs.add(new BasicNameValuePair("message", responseText));
		    im.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = Main.getHttsClient().execute(im);
			
			int errorCode =response.getStatusLine().getStatusCode();

			if(errorCode == 200) {
		        String responseBody = EntityUtils.toString(response.getEntity());
		        JSONObject json = (JSONObject) JSONSerializer.toJSON(responseBody);
		  	    if(json==null){
		            log.error("Send IM error:"+username+", Notification, JSON response is null");
		        }else if(json.getInt("errorCode")!=0){
		  	    	log.error("Send IM error:"+username+", Code error is not 0, "+json.getInt("errorCode"));
		  	    }else{
		  	    	log.info("Send IM OK "+username);
		  	    }
			}else{
				log.error("Error invoke Send IM Api "+username+", "+errorCode);
			}
		} catch (ClientProtocolException e) {
			log.error("sendLongResponse(), ClientProtocolException: "+e.getCause());
		} catch (IOException e) {
			log.error("sendLongResponse(), IOException: "+e.getCause());
		}
		
		
	}
	  
	public static void sendFile(String contentType, String contactId, String filename, String subject, String fileUrl,String username,String token){
	
		HttpPost ft = new HttpPost(Constants.URLFT);
		ft.addHeader("app_id", Constants.API_ID);
		ft.addHeader("app_key", Constants.API_KEY);
		
		try {
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		    nameValuePairs.add(new BasicNameValuePair("action", "sendFile"));
		    nameValuePairs.add(new BasicNameValuePair("username", username));
		    if(token!=null)
		    	nameValuePairs.add(new BasicNameValuePair("token", token));
		    nameValuePairs.add(new BasicNameValuePair("contactId", contactId));
		    nameValuePairs.add(new BasicNameValuePair("subject", subject));
		    nameValuePairs.add(new BasicNameValuePair("filename", filename));
		    nameValuePairs.add(new BasicNameValuePair("fileUrl", fileUrl));
		    nameValuePairs.add(new BasicNameValuePair("contentType", contentType));
		    
		    ft.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = Main.getHttsClient().execute(ft);
			
			int errorCode =response.getStatusLine().getStatusCode();

			if(errorCode == 200) {
		        String responseBody = EntityUtils.toString(response.getEntity());
		        JSONObject json = (JSONObject) JSONSerializer.toJSON(responseBody);
		  	    if(json==null){
		            log.error("Send FT error:"+username+", Notification, JSON response is null");
		        }else if(json.getInt("errorCode")!=0){
		  	    	log.error("Send FT error:"+username+", Code error is not 0, "+json.getInt("errorCode"));
		  	    }else{
		  	    	log.info("Send FT OK "+username);
		  	    }
			}else{
				log.error("Error invoke Send FT Api "+username+", "+errorCode+", "+response.getStatusLine().toString());
			}
		} catch (ClientProtocolException e) {
			log.error("sendLongResponse(), ClientProtocolException: "+e.getCause());
		} catch (IOException e) {
			log.error("sendLongResponse(), IOException: "+e.getCause());
		}
	  }
	  
	public static JSONArray comet(String username){
		JSONArray jsa=new JSONArray();
		try{
			HttpGet comet = new HttpGet(Constants.URLNOTIFIER+"?username="+username);
			comet.addHeader("app_id", Constants.API_ID);
			comet.addHeader("app_key", Constants.API_KEY);	
			HttpResponse response = Main.getHttsClient().execute(comet);
			StatusLine statusLine = response.getStatusLine();
			
				if(statusLine!=null){
					HttpEntity rEntity = response.getEntity();
					if(statusLine.getStatusCode() == 200 && rEntity!=null) {					
						String responseBody = EntityUtils.toString(rEntity);
						JSONObject json = (JSONObject) JSONSerializer.toJSON(responseBody);
						if(json!=null && !json.isEmpty() && json.containsKey("notifications")){
					        jsa=json.getJSONArray("notifications");
					    }					
					}
				}
				
		} catch (Exception e) {}			
		
		return jsa;
}
			
}
