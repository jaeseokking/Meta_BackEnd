package com.real.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	private String secretKey = "realmkt";
	
	// 토큰 유효시간 30분 
	private long tokenValidTime = 30 * 60 * 1000L; 
	
	// 객체 초기화, secretKey를 Base64로 인코딩한다.
//	@PostConstruct 
//	protected void init() { 
//		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); 
//	} 
//	
	
	// 토큰에서 회원 사업자번호 추출 
	public String getMemberBizno(String token) { 
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject(); 
	} 
	
	// 토큰에서 회원 IDX 추출 
	public int getMemberIDX(String token) { 
		return (Integer) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("IDX"); 
	} 
	
		
	// 토큰의 유효성 + 만료일자 확인 
	public boolean validateToken(String jwtToken) { 
		try { 
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return claims.getBody().getExpiration().before(new Date()); 
		} catch (Exception e) {
			return false; 
		} 
	}
	
	//토큰 확인(만료, 유효성)
	public boolean checkClaim(String jwt) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();	
			return true;
		}  catch (ExpiredJwtException e) {
			System.out.println("Token Expired");
			return false;
		} catch (JwtException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	//토큰 재설정
	public String getToken(String subject, Integer idx, long expire) {
		String accessToken = "";
		
		Claims claims = Jwts.claims().setSubject(subject);
		claims.put("IDX", idx);
		accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
//                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(expire).toInstant(ZoneOffset.ofHours(9))))
                .setExpiration(new Date(System.currentTimeMillis() + (expire * (1000 * 60 ))))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
		
		return accessToken;
	}
	
	public void deleteToken() {
		
	}
	
	public Map<String, Object> getRefreshToken(Cookie [] cookies) {
		Map<String, Object> result = new HashMap<String, Object>();
		String refreshToken = "";
		
		if(cookies != null && cookies.length > 0) {
    		for(Cookie cookie : cookies) {
    			if(cookie.getName().equals("refresh_token")) {
    				refreshToken = cookie.getValue();
    				if(checkClaim(refreshToken)) {
    					result.put("result", "TOKEN VALID");
    					result.put("refreshToken", refreshToken);
    				}else {
    					result.put("result", "TOKEN EXPIRED");
    				}
    			}
    		}
    	}
    	if(refreshToken == null || "".equals(refreshToken)) {
			result.put("result", "TOKEN NULL");
    	}
		
		return result;
	}

}
