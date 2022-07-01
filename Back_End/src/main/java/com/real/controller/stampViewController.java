package com.real.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.real.service.mainService;
import com.real.util.AES256;

@Controller
public class stampViewController {
	
	@Autowired 
	mainService mainservice;
	 
	
	static private AES256 AES = new AES256("STAMP_REAL123456");

	
	@ResponseBody
	@RequestMapping(value="/stampPage", method=RequestMethod.GET)
	public ModelAndView stampPage (@RequestParam("param") String encryptedParam) {
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("stampPage");
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("디코딩 전 param ::: " + encryptedParam);
		
		String param = AES.decryptBase64String(encryptedParam); 
		
		System.out.println("디코딩 후 param ::: " + param);
		
		String[] arr = param.split("\\|");
		
		
		System.out.println(arr[0]);
		System.out.println(arr[1]);
		System.out.println(arr[2]);

		
//		if(bizno == null) {
//			return null;
//		}
//		if(shop_info_no == null) {
//			return null;
//		}
//		
//		if(userkey == null){
//			return null;
//		}
		
		map.put("USER_KEY", arr[0]);
		map.put("BIZNO", arr[1]);
		map.put("SHOP_INFO_NO", arr[2]);
		
//		map.put("BIZNO", bizno);
//		map.put("SHOP_INFO_NO", shop_info_no);
//		map.put("USER_KEY", userkey);
		
		//사업자의 정보가져오기
		Map<String, Object> shopInfo = mainservice.getShopInfo(map);
		System.out.println("SHOP INFO ::: " + shopInfo);
		mav.addObject("shopInfo", shopInfo);
		
		//해당 사용자의 스탬프 리스트 가져오기
		int stampCount = mainservice.stampCount(map);
		int completionCount = (Integer) shopInfo.get("COMPLETION_STAMP");
		System.out.println("STAMP COUNT ==> " + stampCount);
		System.out .println("COMPLETION COUNT ==> " + completionCount + "!!!!");
		
		//스탬프 사이즈 조절
		int marginLeft;
		int stampPosition;
		int unStampPosition;
		if(stampCount <= 0) {
			marginLeft = -30;
			stampPosition = 153;
			unStampPosition = -0;
		}else if(stampCount >= completionCount) {
			marginLeft = 125;
			stampPosition = -2;
			unStampPosition = -159;
		}else {
			marginLeft = (153 -30) -(153 / completionCount * (completionCount - stampCount));
			stampPosition = 153 * (completionCount - stampCount) / completionCount;
			unStampPosition = -153 * stampCount / completionCount;
		}
		

		//mav.addObject("stampList", stampList);
		mav.addObject("stampCount", stampCount);
		mav.addObject("marginLeft", marginLeft);
		mav.addObject("stampPosition", stampPosition);
		mav.addObject("unStampPosition", unStampPosition);
		
		//System.out.println("PARAM ::: " + param);
		
		
		
		
		
		return mav;
	}

}
