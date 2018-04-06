package com.alexa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

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

public class AlexaManager implements IConstant {

	@SuppressWarnings("deprecation")
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

	@SuppressWarnings("deprecation")
	public String sendEventsToAlexaServer(String json) {

		JSONObject jsResponse = new JSONObject();
		DefaultHttpClient httpClient = null;
		try {
			JSONObject gHomeJson = new JSONObject(json);
			String usrID = gHomeJson.getString("user_id");// "amzn1.ask.account.AH5UZGFOTDOI3PVOGRNLJW7QR7L7ZCPQV4GTDWM64ORTYUEL6MJFU4ARYOIE24I43Y52PK6DTNRO7PMLPZSBPCASKK7H5UBIN2FBUYEEPBHD3HTNJ64TVZRDGA5DRZXMHX4IT6WNV3765UE4XSK62ISJFT7EGV2ANMX5C5ZI2CRWSVEJFAXIPWDDI5B5ME5HGL2L26J3SILR7WI";//
															// gHomeJson.getString("user_id");
			String url = "https://api.eu.amazonalexa.com/v1/skillmessages/users/" + usrID;
			HttpPost post = new HttpPost(url);
			HttpParams params = new BasicHttpParams();

			post.addHeader("Content-Type", "application/json");
			post.addHeader("Authorization", "Bearer " + generateAlexaAccessToken());
			post.setParams(params);

			StringEntity requestEntity = new StringEntity(AlexaParser.parse(json), ContentType.APPLICATION_JSON);
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
