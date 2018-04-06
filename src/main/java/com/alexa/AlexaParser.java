package com.alexa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AlexaParser {

	public static String parse(String json) throws JSONException{
		JSONObject gHomeJson = new JSONObject(json);
		String actionType = gHomeJson.getString("action_type");
		String response="";
		if(!actionType.contains("remind")){
			response=getShoppingList(gHomeJson.getJSONArray("ShoppingList"));
		}else{
			response=getReminderJson(gHomeJson);
		}
		return response;
	}
	public static String getShoppingList(JSONArray jsonArray) {

		JSONObject jsonObject = new JSONObject();
		JSONObject message = new JSONObject();
		JSONObject request = new JSONObject();
		request.put("type", "Messaging.MessageReceived");

		StringBuilder shoppingList = new StringBuilder();
		int length = jsonArray.length();
		for (int i = 0; i < length; i++) {
			if (i < length - 1)
				shoppingList.append(jsonArray.get(i) + ",");
			else
				shoppingList.append(jsonArray.get(i));
		}
		message.put("notice", shoppingList.toString());
		request.put("apiaccess_token", "djjdjd");
		request.put("intent", "add");
		request.put("list_name", "Shopping List");
		request.put("requestId", "amzn1.echo-api.request." + System.currentTimeMillis());
		request.put("timestamp", "2015-05-13T12:34:56Z");
		request.put("message", message);

		jsonObject.put("request", request);
		JSONObject data = new JSONObject();

		data.put("data", jsonObject);
		System.out.println(data.toString());
		return data.toString();
	}

	public static String getReminderJson(JSONObject mainJSON) {

		JSONObject jsonObject = new JSONObject();
		JSONObject message = new JSONObject();
		JSONObject request = new JSONObject();
		request.put("type", "Messaging.MessageReceived");

		StringBuilder shoppingList = new StringBuilder();
		
		if(!mainJSON.isNull("text_msg"))
			shoppingList.append(mainJSON.getString("text_msg"));
		if(!mainJSON.isNull("where_to_pick"))
			shoppingList.append(" "+mainJSON.getString("where_to_pick"));
		if(!mainJSON.isNull("when_to_pick"))
			shoppingList.append(" "+mainJSON.getString("when_to_pick"));
		
		
		message.put("notice", shoppingList.toString());
		
		request.put("apiaccess_token", "djjdjd");
		request.put("intent", "add");
		request.put("list_name", "to-do");
		request.put("requestId", "amzn1.echo-api.request." + System.currentTimeMillis());
		request.put("timestamp", "2015-05-13T12:34:56Z");
		request.put("message", message);

		jsonObject.put("request", request);
		JSONObject data = new JSONObject();

		data.put("data", jsonObject);
		System.out.println(data.toString());
		return data.toString();
	}

}
