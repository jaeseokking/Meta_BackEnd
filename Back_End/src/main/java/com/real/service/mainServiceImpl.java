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
			
			return result;
		}catch (Exception e) {
			
		
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
		
		return maindao.updatePW(updateinfo);
	}

	@Override
	public int totalcounts(Map<String, Object> param) {
		System.out.println("들어옴!!!!!!2");

		return maindao.totalcounts(param);	
	}



	@Override
	public List<Object> excellist(Map<String, Object> excelinfo) {
			
		return maindao.excellist(excelinfo);
	}

	
	
	
	


	
	
}
