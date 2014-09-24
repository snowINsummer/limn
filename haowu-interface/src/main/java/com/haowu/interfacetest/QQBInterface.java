package com.haowu.interfacetest;

import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;




import com.limn.tool.common.Common;
import com.limn.tool.external.JSONControl;
import com.limn.tool.httpclient.StructureMethod;
/**
 * 
 * 鎶㈤挶鍖匒PP
 * 
 * /hoss-society/app4/agent/client.do 鎺ㄨ崘鎺ュ彛
 * /hoss-society/app4/login/agentlogin.do 鐧诲綍
 * @author limn
 *
 */
public class QQBInterface {
	
	HttpClient client = new HttpClient();
	SystemInterface sif = null;
	private String key = null;
	
	public QQBInterface(String ip, int port){
		client.getHostConfiguration().setHost(ip, port, "http");
		
		
	}
	
	public void loginSystem(){
		sif = new SystemInterface("www.haowu.com", 80);
		sif.login("haowuwang", "haowuwangbestwu");
	}
	
	/**
	 * 鐧诲綍
	 * @param username 鎵嬫満鍙�
	 * @param password 瀵嗙爜
	 */
	public void login(String username, String password){
		
		NameValuePair[] param = { 
				new NameValuePair("user_name", username),
				new NameValuePair("user_pass", password),

		};
		PostMethod postMethod = StructureMethod.getPostMethod(param,
				"/hoss-society/app4/login/agentlogin.do");
		String jsonResutls = StructureMethod.execute(client, postMethod);
		
		HashMap<String,Object> map = JSONControl.getMapFromJson(jsonResutls);
		
		key = (String) map.get("key");
		
//		System.out.println(jsonResutls);
		
	}
	/**
	 * 鎺ㄨ崘瀹㈡埛
	 * @param clientName 瀹㈡埛鍚嶇О
	 * @param clientPhone 瀹㈡埛鎵嬫満鍙�
	 * @param housesID housesID
	 * @param housesName housesName
	 */
	
	public void recommend(String clientName, String clientPhone, String housesID, String housesName){

		JSONObject data_houses = new JSONObject(); 
		data_houses.put("houses_id", housesID);
		data_houses.put("houses_name", housesName);
		data_houses.put("type", "0");
		JSONArray array = new JSONArray();
		array.add(data_houses);
		JSONObject node = new JSONObject(); 
		node.put("data_houses", array);
		node.put("name", clientName);
		node.put("phone", clientPhone);
		
				
		NameValuePair[] param = { 
				new NameValuePair("key", key),
				new NameValuePair("data_json", node.toString()),

		};
		PostMethod postMethod = StructureMethod.getPostMethod(param,
				"/hoss-society/app4/agent/client.do");
		String jsonResutls = StructureMethod.execute(client, postMethod);
		System.out.println(jsonResutls);
		
		
	}
	
	/**
	 * 
	 * @param cityID
	 * @param mac
	 * @return
	 */
	public String getActivitesByCityID(String cityID,String mac){
		NameValuePair[] param2 = { new NameValuePair("cityId", cityID),
				new NameValuePair("key", ""), new NameValuePair("mac", mac),
				new NameValuePair("page", "0"), new NameValuePair("size", "25") };
		PostMethod postMethod2 = StructureMethod.getPostMethod(param2,
				"/hoss-society/app4/activity/getActivities.do");
		String jsonResutls = StructureMethod.execute(client, postMethod2);
		
		return jsonResutls;
	}
	
	/**
	 * 
	 * @param iphone
	 * @param mac
	 * @param cityID
	 * @param username
	 * @param password
	 * @return
	 */
	public String register(String iphone,String mac,String username,String password){
		NameValuePair[] param = { 
				new NameValuePair("account_phone", iphone),
				new NameValuePair("mac", mac),
				new NameValuePair("download_code", "")
		};
		PostMethod postMethod = StructureMethod.getPostMethod(param,
				"/hoss-society/app4/account/reg/verify.do");
		String jsonResutls = StructureMethod.execute(client, postMethod);
		System.out.println(jsonResutls);
		Common.wait(2);
		String ver = sif.getCodeByPhone(iphone);
		System.out.println(ver);
		NameValuePair[] param1 = { 
				new NameValuePair("account_phone", iphone),
				new NameValuePair("verify", ver),
		};
		PostMethod postMethod1 = StructureMethod.getPostMethod(param1,
				"/hoss-society/app4/account/reg/yz.do");
		jsonResutls = StructureMethod.execute(client, postMethod1);
		System.out.println(jsonResutls);
		

		NameValuePair[] param3 = { 
				new NameValuePair("account_name", username),
				new NameValuePair("account_password", password),
				new NameValuePair("account_phone", iphone),
				new NameValuePair("checkStatus", "-1"),
				new NameValuePair("download_code", ""),
				new NameValuePair("mac", "mac")
		};
		PostMethod postMethod3 = StructureMethod.getPostMethod(param3,
				"/hoss-society/app4/account/reg.do");
		jsonResutls = StructureMethod.execute(client, postMethod3);
		System.out.println(jsonResutls);
		return (String) JSONControl.getMapFromJson(jsonResutls).get("status");
	}
	
	public void setCity(String CityID){
		NameValuePair[] param = { 
				new NameValuePair("cityId", CityID),
				new NameValuePair("key", key)
		};
		PostMethod postMethod = StructureMethod.getPostMethod(param,
				"/hoss-society/app4/login/bindBrokerCity.do");
		String jsonResutls = StructureMethod.execute(client, postMethod);
		System.out.println(jsonResutls);
	}
	
	public String getCash(){
		NameValuePair[] param = { 
				new NameValuePair("key", key),
				new NameValuePair("page", "0"),
				new NameValuePair("size", "25"),
				new NameValuePair("type", "0")
		};
		PostMethod postMethod = StructureMethod.getPostMethod(param,
				"/hoss-society/app4/wallets/cashBackList.do");
		String jsonResutls = StructureMethod.execute(client, postMethod);
		
		HashMap<String,Object> map = JSONControl.getMapFromJson(jsonResutls);
		
		return map.get("balance").toString();
		
	}
	
	public void changeCity(String cityID , String phone){
		NameValuePair[] param = { 
				new NameValuePair("key", key),
				new NameValuePair("newCityId", cityID),
				new NameValuePair("phone", phone)
		};
		PostMethod postMethod = StructureMethod.getPostMethod(param,
				"/hoss-society/app4/change/changeCity.do");
		StructureMethod.execute(client, postMethod);
		
	
		
	}
	
	/**
	 * 瀹炰緥
	 * @param args
	 */
	public static void main(String[] args){
		QQBInterface qqbif = new QQBInterface("http://ios1.haowu.com", 83);
		qqbif.login("13810000002", "a111111");
		qqbif.recommend("aa", "13697898451", "12959", "limn-5");
		
	}
}