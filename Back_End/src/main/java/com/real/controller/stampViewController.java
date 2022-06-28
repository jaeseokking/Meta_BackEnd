package com.real.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.real.service.mainService;

@Controller
public class stampViewController {
	
	@Autowired 
	mainService mainservice;
	 
	
	@ResponseBody
	@RequestMapping(value="/stampPage", method=RequestMethod.GET)
	public void stampPage (
			@RequestParam("bizno")String bizno,
			@RequestParam("shop_info_no")String shop_info_no, 
			@RequestParam("userkey")String userkey) {
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("stampPage");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("BIZNO", bizno);
		param.put("SHOP_INFO_NO", shop_info_no);
		param.put("USER_KEY", userkey);
		
		Map<String ,Object> result = new HashMap<String, Object>();
			
		result = mainservice.stampView(param);
		System.out.println("PARAM ::: " + param);
		
		System.out.println("RESULT ::: "+ result);
		
		
		//return mav;
	}

}
