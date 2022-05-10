package com.real.jwt;

import java.util.Date;
import java.util.Map;

import com.real.dto.MemberVo;


public interface JwtService {
	
	/**
	 * 처리내용 :: 토큰 생성
	 * 
	 * @param member
	 * @return
	 */
	public String createToken(MemberVo member);
	
	
	/**
	 * 처리내용 :: 토큰에 담긴 정보를 가져오는 메소드
	 * 
	 * @param token
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> getInfo(String token) throws Exception; 
	
	
	/**
	 * 처리내용 :: interceptor에서 토큰 유효성을 검증하기 위한 메소드
	 * 
	 * @param token
	 */
	public void checkValid(String token);
	
	
}
