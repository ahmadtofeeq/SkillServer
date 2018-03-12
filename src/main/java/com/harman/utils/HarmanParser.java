package com.harman.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class HarmanParser {

	public Map<String, String> parseData(String json) {
		Map<String, String> map = new HashMap<String, String>();

		JSONObject jsonObject = new JSONObject(json);

		for (Object o : jsonObject.keySet()) {
			if (jsonObject.get(o.toString()) instanceof JSONObject)
				iterateJson(jsonObject.getJSONObject(o.toString()), map);
			else
				map.put(o.toString(), (String) jsonObject.get(o.toString()));
		}
		return map;

	}

	public void iterateJson(JSONObject jo, Map<String, String> map) {
		for (Object o : jo.keySet()) {
			if (jo.get(o.toString()) instanceof JSONObject)
				iterateJson(jo.getJSONObject(o.toString()), map);
			else
				map.put(o.toString(), (String) jo.get(o.toString()));
		}
	}

}
