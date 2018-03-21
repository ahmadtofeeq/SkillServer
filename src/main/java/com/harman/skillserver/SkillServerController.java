package com.harman.skillserver;

import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alexa.AlexaManager;
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

	@RequestMapping(value = "/ShoppingList", method = RequestMethod.POST)
	public @ResponseBody String updateShoppingList(@RequestBody String requestBody) throws IOException {
		System.out.println(requestBody);
		ErrorType errorType = ErrorType.NO_ERROR;
		JSONObject response = new JSONObject();
		try {
			String access_token = new AlexaManager().generateAlexaAccessToken();
			switch (errorType) {
			case NO_ERROR:
				response.put("Status", 1);
				break;

			default:
				response.put("Status", 0);
				break;
			}
			response.put("access_token", access_token);
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
}
