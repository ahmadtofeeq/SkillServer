package com.alexa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.methods.StringRequestEntity;
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
import org.json.JSONArray;
import org.json.JSONObject;

public class AlexaManager implements IConstant {

	public String generateAlexaAccessToken() {

		String access_Token = "NONE";
		DefaultHttpClient httpClient = null;
		try {
			HttpPost post = new HttpPost(lwaurl);
			HttpParams params = new BasicHttpParams();
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair(lwagrant_type, "client_credentials"));
			nvps.add(new BasicNameValuePair(lwaclient_id, client_id));
			nvps.add(new BasicNameValuePair(lwaclient_secret, client_secret));
			nvps.add(new BasicNameValuePair(lwascope, "alexa:skill_messaging"));

			post.addHeader(lwaContentType, "application/x-www-form-urlencoded");
			post.setParams(params);
			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(post);
			String responseString = inputStreamToString(response.getEntity().getContent());
			System.out.println(responseString);

			JSONObject jsObject = new JSONObject(responseString);
			access_Token = jsObject.getString("access_token");
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
		return access_Token;
	}

	public String sendEventsToAlexaServer(String json) {

		JSONObject jsResponse = new JSONObject();
		DefaultHttpClient httpClient = null;
		try {
			JSONObject gHomeJson = new JSONObject(json);
			JSONArray jsonArray = new JSONObject(json).getJSONArray("ShoppingList");
			String apiAccess = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLjA1MDA3NzRhLWMwOTYtNGVhZi04MDE5LWFkNTg0MDQ0NjJhMSIsImV4cCI6MTUyMjEzNDk1MiwiaWF0IjoxNTIyMTMxMzUyLCJuYmYiOjE1MjIxMzEzNTIsInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjoiQXR6YXxJd0VCSU1UZF9jQmpob2dYVzlYUjYtRnBUOE5QX2NaMWpybUxUZFZySlJZY3ZfQ185RGxvcEtrYnRmbDY4QlNQSkhiRnhYNHdTVmtHRkFRZVJldGJhaFBUMEg1b0t2SDZRN2FVWXVlcmFRNGNTZmpXcE9XcEM1NWtLdDBiekFMR2kzTHE0M253N2M5QzUxZ2VmQ0tfY3RTZnYyOEFRWlBPLWFvLUxQS3AzekJYd2dNS0Z5WUY1ZmZMTjh4ZEtiS2hQdUlJU0tlMXduNjlQY3RscllIa0xJWnRuZ0dTNTkzaGxXTnJGZGNaYWRwRWF3aU03MV9CWWYxaklkNnFtOTVLREhLeU9wdWhsODhITG01NmY4Vms3QmhOYkxWczB2aWQ1SUgxc1pwNG1VZWFOOWFmeEEtbEF6RnFfUlBrVTNudEpSLUMtMmRmUmQ0LU5QYUptNEk3T3l6MkZmeDlBbkZCLXUxcmNqcjZIZkVKN2NBTnRsVUpHRFVzX2ttbS1EQkZYZ2QycEFGczR4WXN1dHd6bFZNYU5tRHdrT09sTVp0UDU5SWQ1d0hIRWRod1lSRDdVbHZqVHIzRlRsSnlJWXRzaDJZbXBtWjRJWldoektiN21IVjY2dEc0QTJsckQ1N0toQUE2cDg5WllkQXhaTWwzVVNqRG9ZWnd1LVpWdEhtbDNFZW9tTzNZMmI3bk9nWExvcFMyV0JVVENXYXlNUS1pbzY2TWY4MjZEYk1GcXdJdU1jbEJmVTB0dXBVSEFWNW9zT2xqa3B5dTd2V2g3NjduSGZLaE10UDFIdVZydDgzTXV0SU0tbGY1Q3IwWWNhLWJBWEJuOHdzWWpQWTgzbmNOYzZ2dkg4byIsImRldmljZUlkIjoiYW16bjEuYXNrLmRldmljZS5BSExPVzdET1BGTVBaNVZMU0RPVFRBTVg1VlpKVFJIUk1XVTVUNVZQM0dSUUZRTFVDNFFVMkNNS1JPQU1JSVNVNE5NTU5ZWFVITFRaMkcyQ0NRNUdCSkYyQUZRSENPQTdORkROVU40TEk0UE5ZSFBJQk1MSDIzUVpRTVdUSFNZQlM1VFZGM01LS0VRUDNQSTQ3SkRLNU1YSVVLTU1VRTdFTUxIQjdWTFFJWk1ER1dNUlJMUjRVIiwidXNlcklkIjoiYW16bjEuYXNrLmFjY291bnQuQUVMNjZDT0Q0U0RORkdDRTRLWUc1N0dLWUpENFdSM09XMjNENTVYSkU0Uks1WUdPVU82SlBINVBNNDQ1Qks3NkVJWERFNDdENFVOT05VTFBVTU9aRFJaQ0dSUzY0N0s1U0RGRDRRUUpCV1Y1RTRMM0tMUkNOQzdXTkZKU1o3WUFNM1ZaRFFLQzdLRFBXTVJKTUhSVVVCQVdISUhSVzdUTlNaSFZPUVoyNjdLUEZTUjMzWVpSU0RONkdINFRBTUY0V1BVNDZGNlFIUjZPVlJZIn19.Qr_TIgaupYKxfOFt5lpPBLLUrw55w2QhSGlO6UPSk85rfeZiYBBzTxylI_9cmIcmat3V8aHAzz0caLtq30pH_aulInTjnJsKUrafs3wIFZMFEwvHGbXdqR-GKyQwLq8Ep1p_NCiqD4OeEZuW9WclybJmmuW_wpi3aRugqh8gtZ1AnjNdUvygOU6F6BL_t0YY2zEVDorReav94BhjYzL9tMhsRMBZLHaYw-pE0cv91no5WrpCcwlvxDVoS4oKsCK90bt5juCWbhmd9KVZxXHLVHuvy3wMzG74fkFYzeaGDH_Z6DKC6Xgf2swwnwpOv2XJA9eWqRcLFRhxtXZSfemakA";// gHomeJson.getString("apiaccess_token");
			String usrID = gHomeJson.getString("user_id");// "amzn1.ask.account.AH5UZGFOTDOI3PVOGRNLJW7QR7L7ZCPQV4GTDWM64ORTYUEL6MJFU4ARYOIE24I43Y52PK6DTNRO7PMLPZSBPCASKK7H5UBIN2FBUYEEPBHD3HTNJ64TVZRDGA5DRZXMHX4IT6WNV3765UE4XSK62ISJFT7EGV2ANMX5C5ZI2CRWSVEJFAXIPWDDI5B5ME5HGL2L26J3SILR7WI";//
																	// gHomeJson.getString("user_id");
			String url = "https://api.eu.amazonalexa.com/v1/skillmessages/users/" + usrID;
			HttpPost post = new HttpPost(url);
			HttpParams params = new BasicHttpParams();

			post.addHeader("Content-Type", "application/json");
			post.addHeader("Authorization", "Bearer " + generateAlexaAccessToken());
			post.setParams(params);

			StringEntity requestEntity = new StringEntity(getSampleJson(jsonArray, apiAccess),
					ContentType.APPLICATION_JSON);
			post.setEntity(requestEntity);
			httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(post);
			org.apache.http.StatusLine status = response.getStatusLine();

			if (status.getStatusCode() == 202) {
				System.out.println("Response is 202");
				jsResponse.put("status code", "202");
				jsResponse.put("message", "Intent is sent.");
			} else {
				jsResponse.put("status code", status.getStatusCode());
				jsResponse.put("message", "Intent is not sent.");
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

	public String getSampleJson(JSONArray jsonArray, String apiaccess_token) {
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
		request.put("apiaccess_token", apiaccess_token);
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

	public String inputStreamToString(InputStream inputStream) throws IOException {
		try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}

			return result.toString();
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {

			}
		}
	}
}
