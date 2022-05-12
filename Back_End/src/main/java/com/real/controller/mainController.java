package com.real.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

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
import com.real.jwt.JwtServiceImpl;
import com.real.service.mainService;

@Controller
@RequestMapping("/api")
public class mainController {
	@Autowired 
	mainService mainservice;

	@Autowired
	JwtServiceImpl jwtService;
	
	//토큰 있는 사용자 정보를 리턴 
	@RequestMapping(value="/getUser", method=RequestMethod.GET )
	public ResponseEntity<Object> getUser(HttpServletRequest request){
		try {
			String token = request.getHeader("jwt-auth-token");
			Map<String, Object> tokenInfoMap = jwtService.getInfo(token);
			
			MemberVo member = new ObjectMapper().convertValue(tokenInfoMap.get("member"), MemberVo.class);
			
			return new ResponseEntity<Object>(member, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value="/excludePath/login", method=RequestMethod.POST )
	public ResponseEntity<Object> login(@RequestBody MemberVo member, HttpServletRequest response) {
		try {
			MemberVo DBmember = new MemberVo(); // 원래는 DB에 저장되어 있는 사용자 정보 가져와야 하는 부분
			DBmember.setId("rhemddj");
			DBmember.setPassword("12345");
			
			if(DBUser.getId().equals(member.getId()) && MemberVo.getPassword().equals(user.getPassword())) { // 유효한 사용자일 경우
				String token = jwtService.createToken(user); // 사용자 정보로 토큰 생성
				response.setHeader("jwt-auth-token", token); // client에 token 전달
				return new ResponseEntity<Object>("login Success", HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>("login Fail", HttpStatus.OK);
			}
		} catch(Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	
	//로그인 
	@ResponseBody
	@RequestMapping(value="/login", method=RequestMethod.POST )
	public Map<String,Object>login(@RequestBody Map<String,Object> memberinfo) {
			
		Map <String, Object> resultMap = new HashMap<String, Object>();
		return mainservice.login(memberinfo);
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
