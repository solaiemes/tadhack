package com.solaiemes.provisioning;

import java.io.IOException;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import com.solaiemes.constant.Constants;
import com.solaiemes.main.Main;

public class UserProvisioning {
	private static final Log log = LogFactory.getLog(UserProvisioning.class);
	
	public static void register(String username,String token)throws Exception{
			
		String registerUserParameter="?action=register&username="+username;
		if(token!=null){
			registerUserParameter=registerUserParameter+"&token="+token;
		}
		
		HttpPost register = new HttpPost(Constants.URLSESSION+registerUserParameter);
		register.addHeader("app_id", Constants.API_ID);
		register.addHeader("app_key", Constants.API_KEY);
		try {
			HttpResponse response = Main.getHttsClient().execute(register);
			
			int errorCode =response.getStatusLine().getStatusCode();

			if(errorCode == 200) {
		        String responseBody = EntityUtils.toString(response.getEntity());
		        JSONObject json = (JSONObject) JSONSerializer.toJSON(responseBody);
		  	    if(json==null){
		            log.error("Register error:"+username+", Notification, JSON response is null");
		        }else if(json.getInt("errorCode")!=0){
		  	    	log.error("Register error:"+username+", Code error is not 0, "+json.getInt("errorCode"));
		  	    }else{
		  	    	log.info("Register OK "+username);
		  	    }
			}else{
				log.error("Error invoke register Api "+username+", "+errorCode);
			}
		} catch (ClientProtocolException e) {
			log.error("register(), ClientProtocolException: "+e.getCause());
		} catch (IOException e) {
			log.error("register(), IOException: "+e.getCause());
		}
	}
	
	public static void unregister(String username,String token)throws Exception{
		String unregisterUserParameter="?action=unregister&username="+username;
		if(token!=null){
			unregisterUserParameter=unregisterUserParameter+"&token="+token;
		}
		
		HttpPost unregister = new HttpPost(Constants.URLSESSION+unregisterUserParameter);
		unregister.addHeader("app_id", Constants.API_ID);
		unregister.addHeader("app_key", Constants.API_KEY);
		try {
			HttpResponse response = Main.getHttsClient().execute(unregister);
			
			int errorCode =response.getStatusLine().getStatusCode();

			if(errorCode == 200) {
		        String responseBody = EntityUtils.toString(response.getEntity());
		        JSONObject json = (JSONObject) JSONSerializer.toJSON(responseBody);
		  	    if(json==null){
		            log.error("Unregister error:"+username+", Notification, JSON response is null");
		        }else if(json.getInt("errorCode")!=0){
		  	    	log.error("Unregister error:"+username+", Code error is not 0, "+json.getInt("errorCode"));
		  	    }else{
		  	    	log.info("Unregister OK "+username);
		  	    }
			}else{
				log.error("Error invoke register Api "+username+", "+errorCode);
			}
		} catch (ClientProtocolException e) {
			log.error("unregister(), ClientProtocolException: "+e.getCause());
		} catch (IOException e) {
			log.error("unregister(), IOException: "+e.getCause());
		}
	}
}
