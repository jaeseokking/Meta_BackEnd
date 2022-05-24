package com.real.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

<<<<<<< HEAD
=======
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

>>>>>>> branch 'master' of https://github.com/jaeseokking/Meta_BackEnd.git
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.fabric.xmlrpc.base.Array;
import com.real.config.CORSFilter;
import com.real.dto.MemberVo;
import com.real.dto.NoticeVo;
import com.real.jwt.JwtTokenProvider;
import com.real.service.masterService;

@Controller
@RequestMapping(value="/master")
public class masterController {
	
	@Autowired
	private masterService masterservice;
	
  @Autowired
    private JwtTokenProvider jwtTokenProvider;
	
	
	/**
	 * Master가 admin 추가
	 * Admin Add
	 * MemberVo 의 요소와 맞게 잘 넣어야함
	 * @param member
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/AdminA" ,method=RequestMethod.POST )
	public int AdminA(@RequestBody MemberVo member) {
		
		int result = 0;
		
		result = masterservice.AdminA(member);
		
		return result;
		
	}
	
	
	/**
	 * master가 admin 검색
	 * Admin Search
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/AdminS",method=RequestMethod.POST)
	public Map<String,Object> AdminS(@RequestBody Map<String,Object> map ){
		
		String keyword = (String) map.get("keyword");
		Map<String,Object> result = new HashMap<String,Object>();
		result = masterservice.AdminS(keyword);
		return result;
		
	}
	
	
	/**
	 * master가 admin 삭제
	 * Admin Delete
	 * @param ListIDX
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/AdminD", method=RequestMethod.POST)
	public int AdminD (@RequestBody Map<String,Object> map) {
		
		int result = 0;
		System.out.println(map);
//		String ListIDX = (String) map.get("list");
		ArrayList ListIDX = (ArrayList) map.get("list");
		result = masterservice.AdminD(ListIDX);
		return result;
	}
	
	/**
	 * Admin List 가져오기 페이징 처리된걸로
	 * Admin GetList
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/AdminG", method=RequestMethod.POST)
	public Map<String,Object> AdminG (@RequestBody Map<String,Object> map) {
		
		String page = (String) map.get("page");
		Map<String,Object> result = new HashMap<String,Object>();
		result = masterservice.AdminG(page);
		return result;
	}
	
	/**
	 * Admin Update
	 * Admin Update
	 * @param member
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/AdminU", method=RequestMethod.POST)
	public int AdminU (@RequestBody MemberVo member) {
		
		int result = 0; 
		
		result = masterservice.AdminU(member);
		
		return result;
	}
	
	/**
	 * 관리자 한개 가져오기
	 * Admin One
	 * @param idx
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/AdminO", method=RequestMethod.POST)
	public MemberVo AdminO(@RequestBody Map<String,Object> map ) {
		String idx = (String) map.get("idx");
		MemberVo member = masterservice.AdminO(idx);
		return member;
	}
	
	
	/**
	 * 공지사항 리스트 가져오기
	 * Notice List
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/NoticeL",method=RequestMethod.POST)
	public Map<String,Object> NoticeL (@RequestBody Map<String,Object> map){
		Map<String,Object> result = new HashMap<String,Object>();
		String page = (String) map.get("page");
		if(page == null) {
			page = "1";
		}
		result = masterservice.NoticeL(page);
		return result;
	}
	
	
	/**
	 * 공지사항 Insert
	 * Notice Insert
	 * @param notice
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/NoticeI",method=RequestMethod.POST)
	public int NoticeI(@RequestBody NoticeVo notice) {
		int result = 0;
		result = masterservice.NoticeI(notice);
		return result;
	}
	
	/**
	 * 공지사항 Update
	 * Notice Update
	 * @param notice
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/NoticeU",method=RequestMethod.POST)
	public int NoticeU(@RequestBody NoticeVo notice) {
		int result = 0;
		result = masterservice.NoticeU(notice);
		return result;
	}
	
	/**
	 * 공지사항 삭제하기
	 * Notice Delete
	 * @param ListIDX
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/NoticeD",method=RequestMethod.POST)
	public int NoticeD(@RequestBody Map<String,Object> map) {
		int result = 0;
		ArrayList ListIDX = (ArrayList) map.get("list");
		result = masterservice.NoticeD(ListIDX);
		return result;
	}
	
	/**
	 * 선택된 공지사항 가져오기
	 * Notice One
	 * @param idx
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/NoticeO",method=RequestMethod.POST)
	public NoticeVo NoticeO(@RequestBody Map<String,Object> map) {
		
		 String idx = (String) map.get("idx");
		
		NoticeVo notice = masterservice.NoticeO(idx);
		
		return notice;
	}
	
	/**
	 * 공지사항 검색하기
	 * Notice Search
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/NoticeS",method=RequestMethod.POST)
	public Map<String,Object> NoticeS(@RequestBody String keyword){
		Map<String,Object> result = new HashMap<String,Object>();
		result =masterservice.NoticeS(keyword);
		return result;
	}
	
	
	/**
	 * 문의사항 리스트 가져오기
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/EnquiryL", method=RequestMethod.POST)
	public Map<String,Object> EnquiryL(@RequestBody Map<String,Object> map){
		Map<String,Object> result = new HashMap<String,Object>();
		String page = (String) map.get("page");
		if(page == null) {
			page = "1";
		}
		result = masterservice.EnquiryL(page);
		return result;
		
	}
	
	
	/**
	 * 선택한 문의사항 가져오기
	 * @param idx
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/EnquiryO",method=RequestMethod.POST)
	public Map<String,Object> EnquiryO(@RequestBody Map<String,Object> map){
		
	    String idx = (String) map.get("idx");
		Map<String,Object> result = new HashMap<String,Object>();
		
		result = masterservice.EnquiryO(idx);
		
		return result;
	}
	
	/**
	 * 문의사항 답글 남기기
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/EnquiryR",method=RequestMethod.POST)
	public int EnquiryR(@RequestBody Map<String,Object> param) {
		return masterservice.EnquiryR(param);
	}
	
	/**
	 * 문의사항 5개 가져오기
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/EnquiryF",method=RequestMethod.POST)
	public Map<String,Object> EnquiryF(@RequestBody Map<String,Object> param) {
		return masterservice.EnquiryF(param);
	}
<<<<<<< HEAD
=======
	
	@ResponseBody
	@RequestMapping(value="/LoginM",method = RequestMethod.POST)
	public Map<String,Object> LoginM (@RequestBody Map<String,Object> param,HttpServletRequest request , HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		
		Map<String,Object> result = masterservice.LoginM(param);
		String accessToken = "";
		String refreshToken = "";
		
		if((Long)result.get("count") == 1) {
			HttpSession se = request.getSession();
			se.setAttribute("Okadmin", 1);
			
			accessToken = jwtTokenProvider.getToken((String)result.get("ID"), (Integer)result.get("IDX"), 10);
			refreshToken = jwtTokenProvider.getToken((String)result.get("ID"),(Integer)result.get("IDX"), 30);
			
			map.put("result",result.get("count"));
			Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
			refreshCookie.setPath("/");
			refreshCookie.setMaxAge(30 * 60);

			response.addCookie(refreshCookie);
			
			map.put("data", accessToken);
			
			return map;
			
		}
		map.put("result",result.get("count"));
		return map;
		
	}
>>>>>>> branch 'master' of https://github.com/jaeseokking/Meta_BackEnd.git

}
