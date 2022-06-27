package com.real.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import com.theReal.kakaoArlimTalk.KakaoArlimTalk;


@Controller
@RequestMapping("/api")
public class mainController {
	@Autowired 
	mainService mainservice;
	
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    
    
    /**
     * 로그아웃
     * 
     * @param request
     * @param response
     * @throws Exception
     */
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
    
    
    /**
     * 토큰 재설정
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
	@RequestMapping(value="/refreshToken", method=RequestMethod.POST )
	public String refreshToken(HttpServletRequest request , HttpServletResponse response) 
			throws Exception{
    	
    	String accessToken = "";
    	String refreshToken = "";
    	
    	Cookie [] cookies = request.getCookies();
    	if(cookies != null && cookies.length > 0) {
    		for(Cookie cookie : cookies) {
    			System.out.println("cookie name : " + cookie.getName());
    			System.out.println("비교 :: "+cookie.getName().equals("refresh_token"));
    			if(cookie.getName().equals("refresh_token")) {
    				refreshToken = cookie.getValue();
    				if(jwtTokenProvider.checkClaim(refreshToken)) {
    					String bizno = jwtTokenProvider.getMemberBizno(refreshToken);
    					int idx = jwtTokenProvider.getMemberIDX(refreshToken);
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
		//회원정보가 있을 경우
		if(result != null) {
			accessToken = jwtTokenProvider.getToken((String)result.get("BIZNO"), (Integer)result.get("IDX"), 10);
			refreshToken = jwtTokenProvider.getToken((String)result.get("BIZNO"),(Integer)result.get("IDX"), 30);
			
			
			
			Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
			refreshCookie.setPath("/");
			refreshCookie.setMaxAge(30 * 60);

			response.addCookie(refreshCookie);
			
			return accessToken;
		}
	
		return accessToken;
	}
	
	
	/**
	 * 스탬프 리스트 개수 가져오기
	 * 
	 * @param listinfo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/board/counts", method=RequestMethod.POST)
	public int 총리스트개수 (@RequestBody Map<String,Object> listinfo, HttpServletRequest request , HttpServletResponse response) throws Exception {
		

		String refreshToken = "";
		
		System.out.println("LIST INFO ::::: " + listinfo);
		Cookie [] cookies = request.getCookies();
		Map<String , Object> checkToken = jwtTokenProvider.getRefreshToken(cookies);
		String status = (String) checkToken.get("result");
		
		if(status.equals("TOKEN VALID")) {
			refreshToken = (String)checkToken.get("refreshToken");
			String bizno = jwtTokenProvider.getMemberBizno(refreshToken);
			
			Map<String, Object> param = new HashMap<String, Object>();
			listinfo.put("BIZNO", bizno);
			
			System.out.println(mainservice.totalcounts(listinfo));
			return mainservice.totalcounts(listinfo);

		//토큰 만료된경우
		}else{
			return 3;
		}
		
		
	}
	
//
//	//페이징 리스트 가져오기 
//	@ResponseBody
//	@RequestMapping(value="/board", method=RequestMethod.POST)
//	public  List 리스트가져오기 (@RequestBody Map<String,Object> listinfo) {
//		return mainservice.cw_list(listinfo);
//		
//	}
	
	/**
	 * 스탬프 리스트 가져오기
	 * pagination
	 * 
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/stamp/board", method=RequestMethod.POST)
	public Map<String, Object> getStampList (@RequestBody Map<String, Object> data, HttpServletRequest request , HttpServletResponse response) throws Exception {
		String refreshToken = "";
		Map<String, Object> stampMap = new HashMap<String, Object>();
		
		
		Cookie [] cookies = request.getCookies();
		Map<String , Object> checkToken = jwtTokenProvider.getRefreshToken(cookies);
		String status = (String) checkToken.get("result");
		
		if(status.equals("TOKEN VALID")) {
			refreshToken = (String)checkToken.get("refreshToken");
			String bizno = jwtTokenProvider.getMemberBizno(refreshToken);
			
			data.put("BIZNO", bizno);
			stampMap.put("stampList", mainservice.stampList(data));

			stampMap.put("result", 1);
		//토큰 만료된경우
		}else{
			stampMap.put("result", 0);
		}
		
		return stampMap;
		
	}
	
	//엑셀 리스트 가져오기 
	@ResponseBody
	@RequestMapping(value="/exellist", method=RequestMethod.POST )
		public List 엑셀가져오기 (@RequestBody Map<String,Object> excelinfo) {

		return mainservice.excellist(excelinfo);		
	}
	
	//비밀번호 변경
//	@ResponseBody
//	@RequestMapping(value="/passwordEdit", method=RequestMethod.POST)
//	public int 비밀번호변경(@RequestBody MemberVo updateinfo) {
//		return mainservice.updatePW(updateinfo);
//	}
	
	/**
	 * 스탬프 조건 설정 
	 * 
	 * @param stampinfo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
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
			System.out.println(stampinfo);
			try {
				
				mainservice.stampSetting(stampinfo);
				
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
	
	
	/**
	 * 스탬프 세팅값 가져오기
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getStampSetting", method=RequestMethod.POST)
	public Map<String, Object> getStampSetting(@RequestBody Map<String, Object> shopinfo, HttpServletRequest request , HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Cookie [] cookies = request.getCookies();
		Map<String , Object> checkToken = jwtTokenProvider.getRefreshToken(cookies);
		
		String refreshToken = "";
		
		if(checkToken.get("result").equals("TOKEN VALID")) {
			refreshToken = (String)checkToken.get("refreshToken");
			String bizno = jwtTokenProvider.getMemberBizno(refreshToken);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("BIZNO", bizno);
			try {
				int shop =  Integer.parseInt(String.valueOf(shopinfo.get("SHOP_INFO_NO")));
				param.put("SHOP_INFO_NO", shop);

				result.put("setting", mainservice.getStampSetting(param));
				
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
				result.put("result", "TOKEN EXPIRED");
			}else {
				result.put("result", "TOKEN NULL");
			}
		}
		
		
		return result;
	}
	
	
	/**
	 * 비밀번호 변경 로직
	 * 
	 * @param updateinfo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/passwordEdit", method=RequestMethod.POST)
	public int passwordEdit (@RequestBody Map<String, Object> updateinfo , HttpServletRequest request , HttpServletResponse response) throws Exception {		
		String refreshToken = "";
				
		Cookie [] cookies = request.getCookies();
		Map<String , Object> checkToken = jwtTokenProvider.getRefreshToken(cookies);
		String status = (String) checkToken.get("result");
		
		if(status.equals("TOKEN VALID")) {
			refreshToken = (String)checkToken.get("refreshToken");
			String bizno = jwtTokenProvider.getMemberBizno(refreshToken);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("BIZNO", bizno);
			param.put("PASS", updateinfo.get("currentPW"));
			Map<String, Object> result = mainservice.login(param);
			
			if(result !=  null) {
				param.put("NewPW", updateinfo.get("NewPW"));
				//비밀번호 변경 1 , 비밀번호 미변경 0
				return mainservice.updatePW(param);
			//비밀번호 정보 틀린경우
			}else {
				return 2;
			}
		//토큰 만료된경우
		}else{
			return 3;
		}

		
    
		
	}
	
	@ResponseBody
	@RequestMapping(value="/stamp/detail", method=RequestMethod.POST)
	public Map<String, Object> stampDetail(@RequestBody Map<String, Object> map, HttpServletRequest request , HttpServletResponse response) throws Exception {	
		
		Map<String, Object> result = new HashMap<String, Object>(); 
		Cookie [] cookies = request.getCookies();
		Map<String , Object> checkToken = jwtTokenProvider.getRefreshToken(cookies);
		
		String refreshToken = "";
		
		if(checkToken.get("result").equals("TOKEN VALID")) {
			refreshToken = (String)checkToken.get("refreshToken");
			String bizno = jwtTokenProvider.getMemberBizno(refreshToken);
			Map<String, Object> stampDetail = new HashMap<String , Object>();
			
			Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
			refreshCookie.setMaxAge(30 * 60);
			response.addCookie(refreshCookie);
			map.put("BIZNO", bizno);
				stampDetail = mainservice.getStampDetail(map);
				if(stampDetail != null) {
					result.put("stampDetail", stampDetail);
					result.put("result", "SUCCESS");

				}else {
					result.put("result", "NO DATA");
				}
				
			
		
		}else {
			Cookie removeCookie = new Cookie("refresh_token", null);
			removeCookie.setMaxAge(0);
			response.addCookie(removeCookie);
			
			if(checkToken.get("result").equals("TOKEN EXPIRED")){
				result.put("result", "TOKEN EXPIRED");
			}else {
				result.put("result", "TOKEN NULL");
			}
		}
		
		
		return result; 
	} 
	
	
	
	@ResponseBody
	@RequestMapping(value="/stamp/update", method=RequestMethod.POST)
	public Map<String, Object> stampUpdate(@RequestBody Map<String, Object> map, HttpServletRequest request , HttpServletResponse response) throws Exception {		
		Map<String, Object> result = new HashMap<String, Object>(); 
		Cookie [] cookies = request.getCookies();
		Map<String , Object> checkToken = jwtTokenProvider.getRefreshToken(cookies);
		
		String refreshToken = "";
		
		if(checkToken.get("result").equals("TOKEN VALID")) {
			refreshToken = (String)checkToken.get("refreshToken");
			String bizno = jwtTokenProvider.getMemberBizno(refreshToken);
			int update;
			
			Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
			refreshCookie.setMaxAge(30 * 60);
			response.addCookie(refreshCookie);
			
			map.put("BIZNO", bizno);
				update = mainservice.updateStamp(map);
				if(update > 0) {
					result.put("result", "SUCCESS");
				}else {
					result.put("result", "NO DATA");
				}
				
			
		
		}else {
			Cookie removeCookie = new Cookie("refresh_token", null);
			removeCookie.setMaxAge(0);
			response.addCookie(removeCookie);
			
			if(checkToken.get("result").equals("TOKEN EXPIRED")){
				result.put("result", "TOKEN EXPIRED");
			}else {
				result.put("result", "TOKEN NULL");
			}
		}
		
		
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/notice/board", method=RequestMethod.POST)
	public Map<String, Object> getNoticeList (@RequestBody Map<String, Object> map){
		Map<String, Object> noticeMap = new HashMap<String, Object>();
		
		List<Object> noticeList = mainservice.noticeList(map);
		
		if(noticeList.size() > 0) {
			noticeMap.put("result", "SUCCESS");
			noticeMap.put("noticeList", noticeList);
		}else {
			noticeMap.put("result", "NO DATA");
		}

	
		
		return noticeMap;
		
	}
	
	

	@ResponseBody
	@RequestMapping(value="/notice/detail", method=RequestMethod.POST)
	public Map<String, Object> getNoticeDetail (@RequestBody Map<String, Object> map){
		Map<String, Object> noticeMap = new HashMap<String, Object>();
 
		
		Map<String, Object> noticeDetail = mainservice.noticeDetail(map);
		
		if(noticeDetail.size() > 0) {
			noticeMap.put("result", "SUCCESS");
			noticeMap.put("noticeDetail", noticeDetail);
		}else {
			noticeMap.put("result", "NO DATA");
		}
		
		return noticeMap;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/enquiry/board", method=RequestMethod.POST)
	public Map<String, Object> getEnquiryList (@RequestBody Map<String, Object> data, HttpServletRequest request , HttpServletResponse response) throws Exception {
		String refreshToken = "";
		Map<String, Object> enquiryMap = new HashMap<String, Object>();
		
		
		Cookie [] cookies = request.getCookies();
		Map<String , Object> checkToken = jwtTokenProvider.getRefreshToken(cookies);
		String status = (String) checkToken.get("result");
		
		if(status.equals("TOKEN VALID")) {
			refreshToken = (String)checkToken.get("refreshToken");
			String bizno = jwtTokenProvider.getMemberBizno(refreshToken);
			
			data.put("BIZNO", bizno);
			enquiryMap.put("enquiryList", mainservice.enquiryList(data));
			

			enquiryMap.put("result", 1);
		//토큰 만료된경우
		}else{
			enquiryMap.put("result", 0);
		}
		
		return enquiryMap;
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/enquiry/detail", method=RequestMethod.POST)
	public Map<String, Object> enquiryDetail(@RequestBody Map<String, Object> map, HttpServletRequest request , HttpServletResponse response) throws Exception {	
		
		Map<String, Object> result = new HashMap<String, Object>(); 
		Cookie [] cookies = request.getCookies();
		Map<String , Object> checkToken = jwtTokenProvider.getRefreshToken(cookies);
		
		String refreshToken = "";
		
		if(checkToken.get("result").equals("TOKEN VALID")) {
			refreshToken = (String)checkToken.get("refreshToken");
			String bizno = jwtTokenProvider.getMemberBizno(refreshToken);
			Map<String, Object> enquiryDetail = new HashMap<String , Object>();
			Map<String, Object> enquiryReply = new HashMap<String , Object>();

			
			Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
			refreshCookie.setMaxAge(30 * 60);
			response.addCookie(refreshCookie);
			map.put("BIZNO", bizno);
			
				enquiryDetail = mainservice.getEnquiryDetail(map);
				if(enquiryDetail != null) {
					result.put("enquiryDetail", enquiryDetail);
					if(map.get("type").equals("detail")) {
						enquiryReply = mainservice.getEnquiryReply(map);
					}
					
					result.put("enquiryReply", enquiryReply);
					
					
					result.put("result", "SUCCESS");

				}else {
					result.put("result", "NO DATA");
				}
				
			
		
		}else {
			Cookie removeCookie = new Cookie("refresh_token", null);
			removeCookie.setMaxAge(0);
			response.addCookie(removeCookie);
			
			if(checkToken.get("result").equals("TOKEN EXPIRED")){
				result.put("result", "TOKEN EXPIRED");
			}else {
				result.put("result", "TOKEN NULL");
			}
		}
		
		
		return result; 
	} 
	
	
	@ResponseBody
	@RequestMapping(value="/enquiryWrite", method=RequestMethod.POST)
	public Map<String, Object> enquiryWrite (@RequestBody Map<String, Object> data, HttpServletRequest request , HttpServletResponse response) throws Exception {
		String refreshToken = "";
		Map<String, Object> enquiryMap = new HashMap<String, Object>();
		
		Cookie [] cookies = request.getCookies();
		Map<String , Object> checkToken = jwtTokenProvider.getRefreshToken(cookies);
		String status = (String) checkToken.get("result");
		
		if(status.equals("TOKEN VALID")) {
			refreshToken = (String)checkToken.get("refreshToken");
			String bizno = jwtTokenProvider.getMemberBizno(refreshToken);

			data.put("BIZNO", bizno);
			int insertResult =  mainservice.enquiryWirte(data);
			
			if(insertResult > 0) {
				enquiryMap.put("result", "SUCCESS");
			}else {
				enquiryMap.put("result", "INSERT ERROR");
			}
		//토큰 만료된경우
		}else{
			enquiryMap.put("result", status);
		}
		
		return enquiryMap;
		
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/enquiryUpdate", method=RequestMethod.POST)
	public Map<String, Object> enquiryUpdate (@RequestBody Map<String, Object> data, HttpServletRequest request , HttpServletResponse response) throws Exception {
		String refreshToken = "";
		Map<String, Object> enquiryMap = new HashMap<String, Object>();
		
		Cookie [] cookies = request.getCookies();
		Map<String , Object> checkToken = jwtTokenProvider.getRefreshToken(cookies);
		String status = (String) checkToken.get("result");
		
		if(status.equals("TOKEN VALID")) {
			refreshToken = (String)checkToken.get("refreshToken");
			String bizno = jwtTokenProvider.getMemberBizno(refreshToken);

			data.put("BIZNO", bizno);
			int insertResult =  mainservice.enquiryUpdate(data);
			
			if(insertResult > 0) {
				enquiryMap.put("result", "SUCCESS");
			}else {
				enquiryMap.put("result", "INSERT ERROR");
			}
		//토큰 만료된경우
		}else{
			enquiryMap.put("result", status);
		}
		
		return enquiryMap;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/getShopList", method=RequestMethod.POST)
	public Map<String, Object> getShopList( HttpServletRequest request , HttpServletResponse response) throws Exception {
		System.out.println("가맹점 리스트 가져오기");
		Map<String, Object> result = new HashMap<String, Object>();
		Cookie [] cookies = request.getCookies();
		Map<String , Object> checkToken = jwtTokenProvider.getRefreshToken(cookies);
		
		String refreshToken = "";
		
		if(checkToken.get("result").equals("TOKEN VALID")) {
			refreshToken = (String)checkToken.get("refreshToken");
			String bizno = jwtTokenProvider.getMemberBizno(refreshToken);


			try {
				result.put("shopList", mainservice.getShopList(bizno));
				System.out.println(mainservice.getShopList(bizno));
				
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
				result.put("result", "TOKEN EXPIRED");
			}else {
				result.put("result", "TOKEN NULL");
			}
		}
		
		
		return result;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/stampIssuance", method=RequestMethod.POST)
	public Map<String, Object> StampIssuance(@RequestBody Map<String, Object> stampinfo, HttpServletRequest request , HttpServletResponse response) throws Exception {
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
			//임시 스탬프 코드
			stampinfo.put("STAMPCODE", "CODE"+stampinfo.get("phoneNumber")+stampinfo.get("issuanceDate"));
			System.out.println("STAMP INFO :::: :::: ::: "+stampinfo);
			try {
				
				int insertResult = mainservice.stampIssuance(stampinfo);
				if(insertResult != 1) {
					result.put("result", "INSERT ERROR");
				}else {
					
					KakaoArlimTalk kat = new KakaoArlimTalk();
					
					String phoneNumber = (String) stampinfo.get("phoneNumber");
					String userKey = phoneNumber.substring(phoneNumber.length() -4, phoneNumber.length());
					
					Map<String, Object> alrimTalkMap = new HashMap<String, Object>();
					// 알림톡 내용 설정
					alrimTalkMap.put("userKey", userKey);
					alrimTalkMap.put("shopName", "샵네임");
					alrimTalkMap.put("branchName", "브런치네임");
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
					Date today = new Date();
					alrimTalkMap.put("salesDate", dateFormat.format(today));
					alrimTalkMap.put("totAmt", "1,000");
					// 알림톡 발송	
					try {
						new KakaoArlimTalk().sendArlimTalk("01051242934", alrimTalkMap, "www.naver.com");
						result.put("result", "SUCCESS");
					}catch(Exception e) {
						result.put("result", "KakaoArlimTalk Error");
						e.printStackTrace();
					}
					
					
					
				}
				
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
	
//	@ResponseBody
//	@RequestMapping(value="/send/kakaoAlrimTalk", method=RequestMethod.POST)
//	public  Map<String, Object> sendKakaoAlrimTalk()throws Exception{
//		Map<String, Object> result  = new HashMap<String, Object>();
//		
//		return result;
//	}
//	
	
	
	
	

}
