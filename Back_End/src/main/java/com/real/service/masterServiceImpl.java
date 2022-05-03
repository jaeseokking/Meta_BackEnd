package com.real.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.real.dao.masterDao;
import com.real.dto.MemberVo;
import com.real.util.AES256;

@Service
public class masterServiceImpl implements masterService {
	
	@Autowired
	private masterDao masterdao;
	
	//암호화용 키
	private static final String aesKey = "p8i23e4ric6y7u4ero0pa1s91k1qbv36";
	private static AES256 AES = new AES256("AES/CBC/PKCS5Padding", aesKey.substring(0,16), aesKey.substring(0,16).getBytes());
	
	
	/**
	 * Master가 관리자 추가
	 */
	@Override
	public int AdminA(MemberVo member) {
		// TODO Auto-generated method stub
		
		String password = member.getBizno();
		String en_password = AES.encryptStringToBase64(password);
		member.setPassword(en_password);
		
		int result = masterdao.AdminA(member);
		
		return result;
	}


	/**
	 * Master에서 관리가 계정 검색
	 */
	@Override
	public Map<String, Object> AdminS(String keyword) {
		// TODO Auto-generated method stub
		
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		list = masterdao.AdminS(keyword);
		
		result.put("list", list);
		
		return result;
	}


	/**
	 * Master 관리자 삭제
	 */
	@Override
	public int AdminD(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return masterdao.AdminD(param);
	}
	

}
