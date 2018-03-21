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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

public class AlexaManager {

	public String generateAlexaAccessToken() {
		String access_Token = "NONE";
		DefaultHttpClient httpClient = null;
		try {
			HttpPost post = new HttpPost("https://api.amazon.com/auth/O2/token");
			HttpParams params = new BasicHttpParams();
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("grant_type", "client_credentials"));
			nvps.add(new BasicNameValuePair("client_id",
					"amzn1.application-oa2-client.a03d7ace942a450fa4f9120ccd3c677f"));
			nvps.add(new BasicNameValuePair("client_secret",
					"c81a2f41fbc6ad23457c9b59cf0db81fa0c9ce73ba528b05847dcf45938e3269"));
			nvps.add(new BasicNameValuePair("scope", "alexa:skill_messaging"));

			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
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
