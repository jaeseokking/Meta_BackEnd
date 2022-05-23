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

		return maindao.totalcounts(param);	
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
	public Map<String, Object> getStampSetting(int idx) {
		return maindao.getStampSetting(idx);
	}

	
	
	
	


	
	
}
