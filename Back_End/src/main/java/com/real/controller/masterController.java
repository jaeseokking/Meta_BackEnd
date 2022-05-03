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
	
	
	

}
