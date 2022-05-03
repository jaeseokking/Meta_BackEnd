package com.real.service;

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
	 * Master 가 관리자 추가하기 
	 */
	@Override
	public int AdminA(MemberVo member) {
		// TODO Auto-generated method stub
		
		String password = member.getBizno();
		String en_password = AES.encryptStringToBase64(password);
		member.setPassword(en_password);
		
		int result = masterdao.AdminA(member);
		
		return 0;
	}
	

}
