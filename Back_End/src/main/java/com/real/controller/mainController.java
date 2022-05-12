package com.real.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.real.dto.MemberVo;
import com.real.service.mainService;
import com.real.token.InvaildTokenException;

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
    
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

	//토큰 발행
	private String getToken(String subject , long expire) {
		String accessToken = "";
		
		Claims claims = Jwts.claims().setSubject(subject);
		accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
//                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(expire).toInstant(ZoneOffset.ofHours(9))))
                .setExpiration(new Date(System.currentTimeMillis() + (expire * (1000 * 60 ))))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact(); 
		return accessToken;
	}
	
	//Claim : payload 정보의 한 조각, key value 값으로 이루어짐
	public boolean checkClaim(String jwt) {
	    try {
	    	Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
	                .parseClaimsJws(jwt).getBody();
	        return true;
	    
	    }catch(ExpiredJwtException e) {   
	        System.out.println("Token Expired");
	        return false;
	    
	    }catch(JwtException e) {        
	        System.out.println("Token Error" + e);
	        return false;
	    }
	}
	
	//토큰 리프레쉬
	@ResponseBody
	@RequestMapping(value="/refreshToken" , method=RequestMethod.POST)
	public String refreshToken(MemberVo member , HttpServletRequest request , HttpServletResponse response) throws Exception{
	    String accessToken = "";
	    String refreshToken = "";

	    Cookie [] cookies = request.getCookies();
	    if(cookies != null && cookies.length > 0 ) {
	    	for(Cookie cookie : cookies) {
	        	if(cookie.getName().equals("refreshToken")) {
	          		refreshToken = cookie.getValue();
	          		if(checkClaim(refreshToken)) {
	              		accessToken = getToken(member.getBizno(), 1);
	                }else {
	                    throw new InvaildTokenException();
	                }
	            }
	    	}
	    }

	    if(refreshToken == null || "".equals(refreshToken)) {
	    	throw new InvaildTokenException();
	    }

	    return accessToken;
	}

	
	//로그인 
	@ResponseBody
	@RequestMapping(value="/login", method=RequestMethod.POST )
	public String login(MemberVo member, HttpServletRequest request, HttpServletResponse response) {
		System.out.println(member.getBizno());
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("BIZNO", member.getBizno());
		param.put("PASS", member.getPassword());
		Map<String, Object> result = mainservice.login(param);
		
		String accessToken = "";
		String refreshToken = "";
		System.out.println(result);
		//회원정보가 있을 경우
		if(result.size() > 0) {
			accessToken = getToken(member.getBizno(), 1);
			refreshToken = getToken(member.getBizno(), 3);
			Cookie refreshCookie = new Cookie("refreshToken" , refreshToken);
			refreshCookie.setMaxAge(3*60);
			response.addCookie(refreshCookie);
		}else {
			result.put("result", false);
		}
	
		Map <String, Object> resultMap = new HashMap<String, Object>();
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
