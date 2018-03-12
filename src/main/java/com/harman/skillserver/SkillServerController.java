package com.harman.skillserver;

import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.harman.db.DBManager;
import com.harman.db.DbConstant;
import com.harman.utils.ErrorType;
import com.harman.utils.HarmanParser;

@RestController
@RequestMapping("/Skill")
public class SkillServerController implements DbConstant {

	@RequestMapping(value = "/UpdateSkill", method = RequestMethod.POST)
	public @ResponseBody String requestCMD(@RequestBody String requestBody) throws IOException {
		System.out.println(requestBody);
		ErrorType errorType = ErrorType.NO_ERROR;
		JSONObject response = new JSONObject();
		try {
			// DBManager dbManager = DBManager.getInstance();
			// Connection connection =
			// dbManager.openConnection("device_info_store");
			Map<String, String> mapList = new HarmanParser().parseData(requestBody);
			for (Map.Entry<String, String> entry : mapList.entrySet()) {
				System.out.println(entry.getKey() + "/" + entry.getValue() + "\n");
			}
			switch (errorType) {
			case NO_ERROR:
				response.put("Status", 1);
				break;

			default:
				response.put("Status", 0);
				break;
			}
			response.put("cmd", "UpdateSmartAudioAnalyticsRes");
		} catch (Exception e) {
			response.put("Status", 0);
			response.put("cmd", "UpdateSmartAudioAnalyticsRes");
			System.out.println("fail to parse");
		} finally {
			DBManager mariaModel = DBManager.getInstance();
			mariaModel.closeConnection();
		}
		System.out.println(errorType.name());
		return response.toString();
	}

}
