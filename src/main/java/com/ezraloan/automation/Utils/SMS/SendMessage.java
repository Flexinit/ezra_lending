package com.ezraloan.automation.Utils.SMS;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static com.ezraloan.automation.Utils.APIConfigs.SMSAPIConfigs.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.USER_AGENT;


@Slf4j
public class SendMessage {

	public static BiConsumer<String, String > sendSMSToSubscriber = ( msisdn,  message) -> {

		try {

			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(SMS_SEND_URL);
			//httppost.addHeader("Content-Type", "application/json");

			// Request parameters and other properties.
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("userid", USER_ID));
			params.add(new BasicNameValuePair("password", PASSWORD));
			params.add(new BasicNameValuePair("msg", message));
			params.add(new BasicNameValuePair("msgType", MSG_TYPE));
			params.add(new BasicNameValuePair("senderid", SENDER_ID));
			params.add(new BasicNameValuePair("duplicatecheck", DUPLICATE_CHECK.toString()));
			params.add(new BasicNameValuePair("output", OUTPUT));
			params.add(new BasicNameValuePair("sendMethod", SEND_METHOD));
			params.add(new BasicNameValuePair("mobile", msisdn));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			// Execute and get the response.
			CloseableHttpResponse httpResponse = httpClient.execute(httppost);

			System.out.println("POST Response Status:: "
					+ httpResponse.getStatusLine().getStatusCode());

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));

			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			reader.close();

			// print result
			System.out.println(response.toString());
			log.info("SEND SMS RESULT: {}", response);
			httpClient.close();
		}catch (Exception ex){
			ex.printStackTrace();
			log.error("EXCEPTION SENDING SMS {}", ex.getMessage());
		}
	};


	public BiConsumer<String, String> sendMSGToSubscriber = (msisdn, message) ->{
		try {
			OkHttpClient client = new OkHttpClient().newBuilder()
					.build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("userid", USER_ID)
					.addFormDataPart("password", PASSWORD)
					.addFormDataPart("mobile", msisdn)
					.addFormDataPart("senderid", SENDER_ID)
					.addFormDataPart("msg", message)
					.addFormDataPart("sendMethod", SEND_METHOD)
					.addFormDataPart("msgType", MSG_TYPE)
					.addFormDataPart("output", OUTPUT)
					.addFormDataPart("duplicatecheck", DUPLICATE_CHECK.toString())
					.build();
			Request request = new Request.Builder()
					.url(SMS_SEND_URL)
					.method("POST", body)
					.addHeader("Cookie", "SERVERID=webC1")
					.build();
			Response response = client.newCall(request).execute();
			log.info("SMS SEND RESPONSE {}", response.body().string());

		}catch (Exception ex){
			ex.printStackTrace();
		}
	};

}

