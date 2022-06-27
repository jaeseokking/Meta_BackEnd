package com.theReal.kakaoArlimTalk;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.real.util.ReadAuth;

public class KakaoArlimTalk {
	
	private static String AUTH = "";
	private static final String SENDER_KEY = "fe76181baedfe2179b1455492945156ec89c7de8";
	private static final String TMPL_CD = "K469_0003";
	private static final String SEND_URL = "https://kko.emfo-api.co.kr:8080/rest/kakao/send";
	private static final String LOG_URL = "https://kko.emfo-api.co.kr:8080/rest/kakao/logs";
	
	private static final String POST = "POST";
	private static final String GET = "GET";
	
	ObjectMapper mapper = new ObjectMapper();
	
	//authorization값 가져오기
	public KakaoArlimTalk() throws Exception {
		 AUTH = new ReadAuth().readAuth();
	}
	
	@SuppressWarnings("unchecked")
	private static void baseArlimTalk(String requestMethod, String targetUrl, String parameters) throws Exception {
		
		URL url = new URL(targetUrl);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		
		conn.addRequestProperty("Authorization", AUTH);
		conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
		
		if(requestMethod.equals(POST)) {
			conn.setRequestMethod(POST);
			conn.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.write(parameters.getBytes("UTF-8"));
			wr.flush();
			wr.close();
		}
		else if(requestMethod.equals(GET)) {
			conn.setRequestMethod(GET);
		}
		
		int responseCode = conn.getResponseCode();
		
		System.out.println("\nSending '" + requestMethod + "' request to URL: " + url);
		System.out.println("'" + requestMethod + "' parameters: " + parameters);
		System.out.println("Response Code: " + responseCode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		in.close();
		
		System.out.println("response ::: " + response.toString());
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(response.toString());
		JSONObject jsonObject = (JSONObject) obj;
		Map<String, Object> map = new ObjectMapper().readValue(jsonObject.toString(), Map.class);
		String result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map);
		
		System.out.println();
		System.out.println(result);
	}
	
	
	
	public void sendArlimTalk(String phone, Map<String, Object> templateData, String btn_url) throws Exception {
				
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("phone", phone);
		map.put("sender_key", SENDER_KEY);
		map.put("tmpl_cd", TMPL_CD);
		map.put("callback", phone);
		map.put("send_msg", sendMsg(templateData));
		map.put("sms_type", "N");		// N: 미발송, S: sms, L: lms
		map.put("subject", "전자영수증 안내");
		map.put("attachment_type", "웹링크");
		map.put("attachment_name", "전자영수증 안내");
		map.put("attachment_url", "전자영수증 안내^WL^http://" + btn_url + "^http://" + btn_url);
		
		String inputData = mapper.writeValueAsString(map);
		
		inputData = "messages=[" + inputData + "]";
		System.out.println("sendArlimTalk inputData\n" + inputData);
		
		baseArlimTalk(POST, SEND_URL, inputData);
	}
	
	public void sendArlimTalk(Map<String, Object> templateData) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("phone", templateData.get("phone"));
		map.put("sender_key", SENDER_KEY);
		map.put("tmpl_cd", TMPL_CD);
		map.put("callback", templateData.get("phone"));
		map.put("send_msg", sendMsg(templateData));
		map.put("sms_type", "N");		// N: 미발송, S: sms, L: lms
		map.put("subject", "전자영수증 안내");
		map.put("attachment_type", "웹링크");
		map.put("attachment_name", "전자영수증 안내");
		map.put("attachment_url", "전자영수증 안내^WL^http://" + templateData.get("btn_url") + "^http://" + templateData.get("btn_url"));
		
		String inputData = mapper.writeValueAsString(map);
		
		inputData = "messages=[" + inputData + "]";
		System.out.println("sendArlimTalk inputData\n" + inputData);
		
//		baseArlimTalk(POST, SEND_URL, inputData);
	}
	
	public void sendLog() throws Exception {
		System.out.println("LogData");
		baseArlimTalk(GET, LOG_URL, null);
	}
		
	private String sendMsg(Map<String, Object> map) {
		
		return "안녕하세요. " + map.get("userKey") + " 님\n"
				+ "결제 시 요청하신 영수증을 송부해드립니다.\n\n"
				+ "저희 업체를 이용해 주셔서 감사합니다.\n\n"
				+ "[승인 결제정보]\n"
				+ "업체명 : " + map.get("shopName") + "\n"
				+ "매장명 : " + map.get("branchName") + "\n"				
				+ "결제일시 : " + map.get("salesDate") + "\n"
				+ "결제금액 : " + map.get("totAmt") + "원\n"
				+ "세부내역은 하단 링크를 통해 확인이 가능합니다.\n\n"
				+ "감사합니다.";
	}
	
	private String phoneNum(String phone) {
		
		String result = "";
		
		result = phone.replace("-", "");
		
		return result;
	}
}
