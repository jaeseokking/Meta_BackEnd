package com.real.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.real.dto.MemberVo;
import com.real.dto.NoticeVo;
import com.real.service.masterService;

@Controller
@RequestMapping(value="/master")
public class masterController {
	
	@Autowired
	private masterService masterservice;
	
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
	public Map<String,Object> AdminS(@RequestBody String keyword){
		
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
	public int AdminD (@RequestBody String ListIDX) {
		
		int result = 0;
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
	public Map<String,Object> AdminG (@RequestBody String page) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		result = masterservice.AdminG(page);
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
	public MemberVo AdminO(@RequestBody String idx) {
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
	public Map<String,Object> NoticeL (@RequestBody String page){
		Map<String,Object> result = new HashMap<String,Object>();
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
	public int NoticeD(@RequestBody String ListIDX) {
		int result = 0;
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
	public NoticeVo NoticeO(@RequestBody String idx) {
		
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
	public Map<String,Object> EnquiryL(@RequestBody String page){
		Map<String,Object> result = new HashMap<String,Object>();
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
	public Map<String,Object> EnquiryO(@RequestBody String idx){
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

}
