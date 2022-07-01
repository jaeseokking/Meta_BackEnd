package com.real.util;

import java.io.FileReader;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;

public class ReadAuth {
	
	public String readAuth(String filePath) throws Exception {
		return rfc2045(readJSON(filePath));
	}
	
	public String readAuth() throws Exception {
		return rfc2045(readJSON(null));
	}
	
	//auth 암호화
	private String rfc2045(String auth){
		
		String result = "Basic ";
		byte[] buffer = auth.getBytes();
		
		Encoder encoder = Base64.getMimeEncoder();
		result += encoder.encodeToString(buffer);

		return result;
	}
	
	//JSON 파일 읽기
	//kakao 정보 읽기
 	private String readJSON(String filePath) throws Exception{
		
		String os = System.getProperty("os.name").toLowerCase();
		String result = "";
		ClassPathResource resource = new ClassPathResource("auth/auth.json");
		if(filePath == null || filePath.equals("")) {
			if(os.contains("win")) {
				System.out.println("filepath ::: " + System.getProperty("user.dir"));
				System.out.println("filepath ::: " + ReadAuth.class.getResource("").getPath());
				filePath = ReadAuth.class.getResource("").getPath() + "auth.json";
			}
			else if(os.contains("linux")) {
				System.out.println("filepath ::: " + System.getProperty("user.dir"));
				System.out.println("filepath ::: " + ReadAuth.class.getResource("").getPath());
				filePath = ReadAuth.class.getResource("").getPath() + "auth.json";
			}
		}
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(filePath));
		JSONObject jsonObject = (JSONObject) obj;
		
		Map<String, Object> map = new ObjectMapper().readValue(jsonObject.toString(), Map.class);
	
		result = map.get("front") + ":" + map.get("back");
		
		return result;
	}

}
