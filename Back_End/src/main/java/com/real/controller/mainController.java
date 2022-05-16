package com.real.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;


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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
@RequestMapping("/api")
public class mainController {
	@Autowired 
	mainService mainservice;
	
    private String secretKey = "realmkt";
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    
    
    @ResponseBody
	@RequestMapping(value="/refreshToken", method=RequestMethod.POST )
	public String refreshToken(@RequestBody MemberVo member , HttpServletRequest request , HttpServletResponse response) 
			throws Exception{
    	
    	String accessToken = "";
    	String refreshToken = "";
    	
    	Cookie [] cookies = request.getCookies();
    	if(cookies != null && cookies.length > 0) {
    		for(Cookie cookie : cookies) {
    			System.out.println("cookie name : " + cookie.getName());
    			if(cookie.getName().equals("refresh_token")) {
    				refreshToken = cookie.getValue();
    				if(jwtTokenProvider.checkClaim(refreshToken)) {
//    					HttpSession se = request.getSession();
//    					MemberVo vo = (MemberVo) se.getAttribute("member");
//    					accessToken = jwtTokenProvider.getToken(vo.getBizno(), 1);
    					String bizno = jwtTokenProvider.getMemberBizno(refreshToken);
    					System.out.println("bizno :: " + bizno);
    					accessToken = jwtTokenProvider.getToken(bizno, 1);
    					Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
    					refreshCookie.setSecure(true);
    					refreshCookie.setMaxAge(3 * 60);
    					response.addCookie(refreshCookie);
    				}else {
    					return null;
    				}
    			}
    		}
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
		System.out.println(result);
		//회원정보가 있을 경우
		if(result.size() > 0) {
			System.out.println("회원 인증 완료" + result.get("BIZNO"));
			//result.put("result", true);
			accessToken = jwtTokenProvider.getToken((String)result.get("BIZNO") , 1);
			refreshToken = jwtTokenProvider.getToken((String)result.get("BIZNO") , 3);
			
			Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
			refreshCookie.setPath("/");
			refreshCookie.setMaxAge(3 * 60);

			response.addCookie(refreshCookie);
			//memberData.setBizno((String)result.get("BIZNO"));
			
			//서버 세션에 Bizno 저장
//			MemberVo vo = new MemberVo();
//			vo.setBizno((String)result.get("BIZNO"));
//			HttpSession se = request.getSession();
//			se.setAttribute("member", vo);
			
			return jwtTokenProvider.createToken((String)result.get("BIZNO"));
		}else {
			//result.put("result", false);
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
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public int 비밀번호변경(@RequestBody Map<String, Object> updateinfo) {
		return mainservice.updatePW(updateinfo);
	}
	
}
