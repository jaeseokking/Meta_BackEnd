package com.real.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
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
	@PostConstruct 
	protected void init() { 
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); 
	} 
	
	// JWT 토큰 생성 
	public String createToken(String memberBizno) {
		System.out.println("토큰생성");
		Claims claims = Jwts.claims().setSubject(memberBizno); 
		// JWT payload 에 저장되는 정보단위 
		//claims.put("roles", "Member1"); 
		// 정보는 key / value 쌍으로 저장된다. 
		Date now = new Date();
		return Jwts.builder() 
				.setClaims(claims) // 정보 저장 
				.setIssuedAt(now) // 토큰 발행 시간 정보 
				.setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time 
				.signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 // signature 에 들어갈 secret값 세팅 
				.compact(); 
	} 
	
	// JWT 토큰에서 인증 정보 조회 
//	public Authentication getAuthentication(String token) { 
//		UserDetails userDetails = UserDetailsService.loadUserByUsername(this.getMemberBizno(token)); 
//		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()); 
//	} 
	
	// 토큰에서 회원 정보 추출 
	public String getMemberBizno(String token) { 
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject(); 
	} 
	// Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값' 
	public String resolveToken(HttpServletRequest request) { 
		return request.getHeader("X-AUTH-TOKEN"); 
	} 
	
	// 토큰의 유효성 + 만료일자 확인 
	
	public boolean validateToken(String jwtToken) { 
		try { 
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date()); 
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
		}
	}
	
	//토큰 재설정
	public String getToken(String subject, long expire) {
		String accessToken = "";
		
		Claims claims = Jwts.claims().setSubject(subject);
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

}
