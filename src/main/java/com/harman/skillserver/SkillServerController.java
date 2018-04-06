package com.harman.skillserver;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alexa.AlexaManager;
import com.alexa.AlexaParser;
import com.harman.db.DBManager;
import com.harman.db.DbConstant;
import com.harman.utils.ErrorType;
import com.harman.utils.HarmanParser;
import com.hash.helper.EventHelperFactory;
import com.hash.model.Result;
import com.hash.model.google_event.End;
import com.hash.model.google_event.Event;
import com.hash.model.google_event.Start;
import com.hash.network.ResponseHandler;
import com.hash.util.DataException;
import com.hash.util.VOICE_AGENT;

@RestController
@RequestMapping("/Skill")
public class SkillServerController implements DbConstant {

	@RequestMapping(value = "/UpdateSkill", method = RequestMethod.POST)
	public @ResponseBody String requestCMD(@RequestBody String requestBody) throws IOException {

		System.out.println(requestBody);
		ErrorType errorType = ErrorType.NO_ERROR;
		JSONObject response = new JSONObject();
		try {
			Map<String, String> mapList = new HarmanParser().parseData(requestBody);

			Event event = new Event(mapList.get("emailID")); // email id.
			event.setSummary(mapList.get("description"));
			Start start = new Start();
			start.setDateTime(mapList.get("time"));// ISO-8601 standard
			End end = new End();
			end.setDateTime(mapList.get("time"));
			event.setStart(start);
			event.setEnd(end);

			try {
				EventHelperFactory.getEventHelper(VOICE_AGENT.GOOGLE_ASSISTANT).setReminder(mapList.get("access_token"),
						event, new ResponseHandler() {
							@Override
							public void onResponseReceived(Result arg0) {
								System.out.println("Event response :- " + arg0);
							}
						});
			} catch (DataException e) {
				e.printStackTrace();
			}

			switch (errorType) {
			case NO_ERROR:
				response.put("Status", 1);
				break;

			default:
				response.put("Status", 0);
				break;
			}
			response.put("skill", "update Hash server");
		} catch (Exception e) {
			response.put("Status", 0);
			response.put("skill", "update Hash server");
			System.out.println("fail to parse");
		} finally {
			DBManager mariaModel = DBManager.getInstance();
			mariaModel.closeConnection();
		}
		System.out.println(errorType.name());
		return response.toString();
	}
	
	
	@RequestMapping(value = "/setReminder", method = RequestMethod.POST)
	public @ResponseBody String setReminder(@RequestBody String requestBody) throws IOException {
		System.out.println(requestBody);
		JSONObject jsResponse = new JSONObject();
		DefaultHttpClient httpClient = null;
		try {
			JSONObject gHomeJson = new JSONObject(requestBody);
			String url = System.getenv("REMINDER_URL");
			System.out.println(url);
			//"https://6fe7105e.ngrok.io/alexa-reminders";
			HttpPost post = new HttpPost(url);
			HttpParams params = new BasicHttpParams();

			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setParams(params);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("reminder", "Steve is inform you that he will "+gHomeJson.getString("text_msg")));
			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(post);
			org.apache.http.StatusLine status = response.getStatusLine();

			if (status.getStatusCode() == 200) {
				System.out.println("Response is 200");
				jsResponse.put("status code", "200");
				jsResponse.put("message", "Jane has been informed.");
			} else {
				jsResponse.put("status code", status.getStatusCode());
				jsResponse.put("message", "Jane has not been informed");
			}
		} catch (ProtocolException e) {
			System.out.println("ProtocolException");
		} catch (IOException e) {
			System.out.println("IOException");
		} catch (Exception e) {
			System.out.println("Exception - " + e.getMessage());
		} finally {
			try {
				if (httpClient != null)
					httpClient.close();
			} catch (Exception e) {

			}
		}
		return jsResponse.toString();
	}

	@RequestMapping(value = "/ShoppingList", method = RequestMethod.POST)
	public @ResponseBody String updateShoppingList(@RequestBody String requestBody) throws IOException {
		System.out.println(requestBody);
		ErrorType errorType = ErrorType.NO_ERROR;
		JSONObject response = new JSONObject();
		try {
			String message = new AlexaManager().sendEventsToAlexaServer(requestBody);
			response.put("response", message);
		} catch (Exception e) {
			response.put("Status", 0);
			response.put("skill", "update Hash server");
			System.out.println("fail to parse");
		} finally {
			DBManager mariaModel = DBManager.getInstance();
			mariaModel.closeConnection();
		}
		System.out.println(errorType.name());
		return response.toString();
	}

	@RequestMapping(value = "/updateUserCredentials", method = RequestMethod.POST)
	public @ResponseBody String updateUserCredentials(@RequestBody String requestBody) throws IOException {
		System.out.println(requestBody);
		ErrorType errorType = ErrorType.NO_ERROR;
		JSONObject response = new JSONObject();
		try {
			String message = new AlexaManager().sendEventsToAlexaServer(requestBody);
			response.put("response", message);
		} catch (Exception e) {
			response.put("Status", 0);
			response.put("skill", "update Hash server");
			System.out.println("fail to parse");
		} finally {
			DBManager mariaModel = DBManager.getInstance();
			mariaModel.closeConnection();
		}
		System.out.println(errorType.name());
		return response.toString();
	}

	@RequestMapping(value = "/externalNotification", method = RequestMethod.POST)
	public @ResponseBody String externalNotification(@RequestBody String requestBody) throws IOException {
		
		JSONObject response = new JSONObject();
		try {
			JSONObject jsonObject = new JSONObject(requestBody);
			System.out.println(jsonObject.toString());
			String message = "notification is received.";
			response.put("response", message);
			response.put("code",1);
		} catch (Exception e) {
			response.put("code", 0);
			response.put("response", "failed to inform HASH");
			System.out.println("fail to parse");
		}

		return response.toString();
	}

}
