package com.real.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.real.dto.MemberVo;
import com.real.service.masterService;

@Controller
@RequestMapping(value="/master")
public class masterController {
	
	@Autowired
	private masterService masterservice;
	
	/**
	 * Master가 admin 추가
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
	 * @param ListIDX
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/AdminD", method=RequestMethod.POST)
	public int AdminD (@RequestBody String ListIDX) {
		
		int result = 0;
		String[] idxs = ListIDX.split(",");
		
		List<String> idx2 = new ArrayList<String>();	
		for(int i =0; i<idxs.length;i++) {
			idx2.add(idxs[i]);
		}
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("idx", idx2);
		
		result = masterservice.AdminD(param);
		
		return result;
	}
	
	/**
	 * Admin List 가져오기 페이징 처리된걸로
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
	 * 공지사항 리스트 가져오기
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
	
	
	
	

}
