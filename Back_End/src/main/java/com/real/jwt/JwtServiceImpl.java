package com.real.jwt;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.real.dto.MemberVo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Component
public class JwtServiceImpl implements JwtService {
	private String secretKey = "realmkt"; //서명에 사용할 secretKey
	private long exp = 1000L * 60 * 60; // 토큰 사용가능 시간 (1시간)
	
	
	@Override
	public String createToken(MemberVo member) {
		return Jwts.builder()
				.setHeaderParam("typ", "JWT") //토큰 타입 지정
				.setSubject("memberToken") //토큰 이름 
				.setExpiration(new Date(System.currentTimeMillis() + exp)) //토큰 유효시간
				.claim("member", member) //토큰에 담을 데이터
				.signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) //secretKey를 사용하여 Hash암호화 알고리즘 처리 
				.compact(); //직렬화 , 문자열로 변경
	}


	@Override
	public Map<String, Object> getInfo(String token) throws Exception {
		Jws<Claims> claims = null;
		try {
			claims =Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
		} catch(Exception e) {
			throw new Exception();
		}
		return claims.getBody();
	}


	@Override
	public void checkValid(String token) {
		Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
	}
	
	
	
}
