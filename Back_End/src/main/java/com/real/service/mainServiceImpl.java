package com.real.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.real.dao.mainDAO;
import com.real.dto.MemberVo;
import com.real.util.AES256;

@Service
public class mainServiceImpl implements mainService {
	@Autowired mainDAO maindao;

	//암호화용 키
	private static final String aesKey = "p8i23e4ric6y7u4ero0pa1s91k1qbv36";
	private static AES256 AES = new AES256("AES/CBC/PKCS5Padding", aesKey.substring(0,16), aesKey.substring(0,16).getBytes());

	@Override
	public Map<String,Object> login(Map<String, Object> map) {
		Map <String, Object> result = new HashMap <String, Object>();	
		try {
			map.put("PASS", AES.encryptStringToBase64((String) map.get("PASS")));

			result = maindao.login(map);
			System.out.println(map);
			return result;
		}catch (Exception e) {
			System.out.println(e.getLocalizedMessage());		
		}
		return result;

	}

	
	
	@Override
	public  List cw_list(Map<String, Object> map) {
	System.out.println(map);
		
		int page = (Integer) map.get("page");
		
		if(page < 1) {
			return null;
		}else {
			int firstpage = (page - 1) * 10;
			map.put("firstpage", firstpage);
			System.out.println(map);
			return maindao.cw_list(map);
		}
		
	}

	@Override
	public int updatePW(Map<String, Object> updateinfo) {
		try {
			updateinfo.put("NewPW", AES.encryptStringToBase64((String) updateinfo.get("NewPW")));
			
			return maindao.updatePW(updateinfo);
		}catch (Exception e) {
			System.out.println(e.getLocalizedMessage());		
			return 0;
		}
	
		
	}

	@Override
	public int totalcounts(Map<String, Object> param) {
		
		if(param.get("what").equals("stamp")) {
			return maindao.stampCounts(param);	
		}else if(param.get("what").equals("notice")){
			return maindao.noticeCounts(param);
		}else if(param.get("what").equals("enquiry")) {
			return maindao.enquiryCounts(param);
		}
		return 0;
		
	}



	@Override
	public List<Object> excellist(Map<String, Object> excelinfo) {
			
		return maindao.excellist(excelinfo);
	}



	@Override
	public void stampSetting(Map<String, Object> stampinfo) {
		maindao.stampSetting(stampinfo);
	}



	@Override 
	public Map<String, Object> getStampSetting(Map<String, Object> param) {
		return maindao.getStampSetting(param);
	}



	@Override
	public List stampList(Map<String, Object> data) {
		
		int page = (Integer) data.get("page");
		
		if(page < 1) {
			return null;
		}else {
			int firstpage = (page - 1) * 10;
			data.put("firstpage", firstpage);
			return maindao.stampList(data);
		}
		
	}



	@Override
	public Map<String, Object> getStampDetail(Map<String, Object> map) {
		return maindao.getStampDetail(map);
	}



	@Override
	public int updateStamp(Map<String, Object> map) {
		return maindao.updateStamp(map);
	}



	@Override
	public List noticeList(Map<String, Object> data) {
		
		int page = (Integer) data.get("page");
		
		if(page < 1) {
			return null;
		}else {
			int firstpage = (page - 1) * 10;
			data.put("firstpage", firstpage);
			return maindao.noticeList(data);

		}
		
	}



	@Override
	public Map<String, Object> noticeDetail(Map<String, Object> map) {
		return maindao.noticeDetail(map);
	}



	@Override
	public List<Object> enquiryList(Map<String, Object> data) {
		
		int page = (Integer) data.get("page");
		
		if(page < 1) {
			return null;
		}else {
			int firstpage = (page - 1) * 10;
			data.put("firstpage", firstpage);
			return maindao.enquiryList(data);
		}
		
	}



	@Override
	public Map<String, Object> getEnquiryDetail(Map<String, Object> map) {
		return maindao.enquiryDetail(map);
	}



	@Override
	public Map<String, Object> getEnquiryReply(Map<String, Object> map) {
		return maindao.enquiryReply(map);
	}



	@Override
	public int enquiryWirte(Map<String, Object> data) {
		return maindao.enquiryWrite(data);
	}



	@Override
	public int enquiryUpdate(Map<String, Object> data) {
		return maindao.enquiryUpdate(data);
	}



	@Override
	public List<String> getShopList(String bizno) {
		return maindao.getShopList(bizno);
	}



	@Override
	public int stampIssuance(Map<String, Object> stampinfo) {
		return maindao.stampIssuance(stampinfo);
	}




	@Override
	public int stampCheck(Map<String, Object> stampinfo) {
		return maindao.stampCheck(stampinfo);
	}



	@Override
	public Map<String, Object> stampView(Map<String, Object> param) {
		return maindao.stampView(param);
	}

	
	
	
	


	
	
}
