package com.real.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.real.dao.mainDAO;

@Service
public class mainServiceImpl implements mainService {
	@Autowired mainDAO maindao;

	@Override
	public Map<String,Object> login(Map<String, Object> map) {

		System.out.println("Service");
		Map <String, Object> result = new HashMap <String, Object>();	
		
		
		try {
			if(maindao.login(map).size() > 0) {
				result = maindao.login(map);
				result.put("result", true);
			}else {
				result.put("result", false);
			}
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
