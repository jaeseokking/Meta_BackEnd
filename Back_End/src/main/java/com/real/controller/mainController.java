package com.real.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.real.dto.MemberVo;
import com.real.jwt.JwtTokenProvider;
import com.real.service.mainService;


@Controller
@RequestMapping("/api")
public class mainController {
	@Autowired 
	mainService mainservice;
	
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    
    
    @ResponseBody
  	@RequestMapping(value="/logout", method=RequestMethod.POST )
  	public void logout(HttpServletRequest request , HttpServletResponse response) 
  			throws Exception{
      	
      	String refreshToken = "";
      	
      	Cookie [] cookies = request.getCookies();
      	if(cookies != null && cookies.length > 0) {
      		for(Cookie cookie : cookies) {
      			if(cookie.getName().equals("refresh_token")) {
      					Cookie removeCookie = new Cookie("refresh_token", null);
      					removeCookie.setMaxAge(0);
      					response.addCookie(removeCookie);
      				
      			}
      		}
      	}
   	
      }
    
    
    @ResponseBody
	@RequestMapping(value="/refreshToken", method=RequestMethod.POST )
	public String refreshToken(HttpServletRequest request , HttpServletResponse response) 
			throws Exception{
    	
    	System.out.println("REFRESH TOKEN");
    	String accessToken = "";
    	String refreshToken = "";
    	
    	Cookie [] cookies = request.getCookies();
    	if(cookies != null && cookies.length > 0) {
    		for(Cookie cookie : cookies) {
    			System.out.println("cookie name : " + cookie.getName());
    			if(cookie.getName().equals("refresh_token")) {
    				refreshToken = cookie.getValue();
    				if(jwtTokenProvider.checkClaim(refreshToken)) {
    					String bizno = jwtTokenProvider.getMemberBizno(refreshToken);
    					int idx = jwtTokenProvider.getMemberIDX(refreshToken);
    					System.out.println("bizno :: " + bizno);
    					accessToken = jwtTokenProvider.getToken(bizno,idx, 10);
    					Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
    					refreshCookie.setMaxAge(30 * 60);
    					response.addCookie(refreshCookie);
    				}else {
    					Cookie removeCookie = new Cookie("refresh_token", null);
      					removeCookie.setMaxAge(0);
      					response.addCookie(removeCookie);
    					return null;
    				}
    			}
    		}
    	}
    	if(refreshToken == null || "".equals(refreshToken)) {
    		Cookie removeCookie = new Cookie("refresh_token", null);
			removeCookie.setMaxAge(0);
			response.addCookie(removeCookie);
    		return null;
    	}
    	
    	return accessToken;
    }

    


	
	/**
	 * 회원정보 확인 및 토큰 발행
	 * 
	 * @param member
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/login", method=RequestMethod.POST )
	public String login(@RequestBody MemberVo member , HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("BIZNO", member.getBizno());
		param.put("PASS", member.getPassword());
		Map<String, Object> result = mainservice.login(param);
		
		String accessToken = "";
		String refreshToken = "";
		System.out.print("BIZNO ::" + member.getBizno() + "   PASS" + member.getPassword());
		System.out.println("회원정보 " + result);
		//회원정보가 있을 경우
		if(result.size() > 0) {
			System.out.println("회원 인증 완료" + result.get("BIZNO"));
			//result.put("result", true);
			accessToken = jwtTokenProvider.getToken((String)result.get("BIZNO"), (Integer)result.get("IDX"), 10);
			refreshToken = jwtTokenProvider.getToken((String)result.get("BIZNO"),(Integer)result.get("IDX"), 30);
			
			Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
			refreshCookie.setPath("/");
			refreshCookie.setMaxAge(30 * 60);

			response.addCookie(refreshCookie);
			
			return accessToken;
		}else {
		}
	
		return accessToken;
	}
	
	
	//회원의 총 리스트 개수 가져오기 
	@ResponseBody
	@RequestMapping(value="/board/counts", method=RequestMethod.POST)
	public int 총리스트개수 (@RequestBody Map<String,Object> listinfo) {
		System.out.println("총 리스트 전달 map : " + listinfo);
		return mainservice.totalcounts(listinfo);
	}
	

	//페이징 리스트 가져오기 
	@ResponseBody
	@RequestMapping(value="/board", method=RequestMethod.POST)
	public  List 리스트가져오기 (@RequestBody Map<String,Object> listinfo) {
		System.out.println(listinfo);
		return mainservice.cw_list(listinfo);
		
	}
	
	//엑셀 리스트 가져오기 
	@ResponseBody
	@RequestMapping(value="/exellist", method=RequestMethod.POST )
		public List 엑셀가져오기 (@RequestBody Map<String,Object> excelinfo) {
		System.out.println(excelinfo);

		return mainservice.excellist(excelinfo);		
	}
	
	//비밀번호 변경
	@ResponseBody
	@RequestMapping(value="/passwordEdit", method=RequestMethod.POST)
	public int 비밀번호변경(@RequestBody MemberVo updateinfo) {
		return mainservice.updatePW(updateinfo);
	}
	
	@ResponseBody
	@RequestMapping(value="/stampSetting", method=RequestMethod.POST)
	public Map<String, Object> StampSetting(@RequestBody Map<String, Object> stampinfo, HttpServletRequest request , HttpServletResponse response) throws Exception {
		Map<String, Object> result  = new HashMap<String, Object>();
		
		String refreshToken = "";
		
				
		Cookie [] cookies = request.getCookies();
		Map<String , Object> checkToken = jwtTokenProvider.getRefreshToken(cookies);
		
		if(checkToken.get("result").equals("TOKEN VALID")) {
			refreshToken = (String)checkToken.get("refreshToken");
			String bizno = jwtTokenProvider.getMemberBizno(refreshToken);
			int idx = jwtTokenProvider.getMemberIDX(refreshToken);
			
			stampinfo.put("bizno", bizno);
			stampinfo.put("MEMBER_IDX", idx);

			try {
				
				mainservice.stampSetting(stampinfo);
				
				System.out.println(stampinfo);
			} catch (Exception e) {
				result.put("result", "INSERT ERROR");
			}
			
			Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
			refreshCookie.setMaxAge(30 * 60);
			response.addCookie(refreshCookie);
			result.put("result", "SUCCESS");
		}else {
			Cookie removeCookie = new Cookie("refresh_token", null);
			removeCookie.setMaxAge(0);
			response.addCookie(removeCookie);
			
			if(checkToken.get("result").equals("TOKEN EXPIRED")){
				result.put("result", "SUCCESS");
			}else {
				result.put("result", "TOKEN NULL");
			}
		}
		
	
    	return result;
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getStampSetting", method=RequestMethod.POST)
	public Map<String, Object> getStampSetting( HttpServletRequest request , HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Cookie [] cookies = request.getCookies();
		Map<String , Object> checkToken = jwtTokenProvider.getRefreshToken(cookies);
		
		String refreshToken = "";
		
		if(checkToken.get("result").equals("TOKEN VALID")) {
			refreshToken = (String)checkToken.get("refreshToken");
			int idx = jwtTokenProvider.getMemberIDX(refreshToken);
			System.out.println("?!?" + mainservice.getStampSetting(idx));

			try {
				result.put("setting", mainservice.getStampSetting(idx));
				System.out.println(result);
				
			} catch (Exception e) {
				result.put("result", "GET ERROR");
			}
			
			Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
			refreshCookie.setMaxAge(30 * 60);
			response.addCookie(refreshCookie);
			result.put("result", "SUCCESS");
		}else {
			Cookie removeCookie = new Cookie("refresh_token", null);
			removeCookie.setMaxAge(0);
			response.addCookie(removeCookie);
			
			if(checkToken.get("result").equals("TOKEN EXPIRED")){
				result.put("result", "SUCCESS");
			}else {
				result.put("result", "TOKEN NULL");
			}
		}
		
		
		return result;
	}

}
