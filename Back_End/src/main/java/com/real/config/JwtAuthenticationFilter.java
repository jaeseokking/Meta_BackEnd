package com.real.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.real.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
	
	@Autowired
	private final JwtTokenProvider jwtTokenProvider;


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
		
		if (token != null && jwtTokenProvider.validateToken(token)) { 
			// 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
			//Authentication authentication = jwtTokenProvider.getAuthentication(token); 
			// SecurityContext 에 Authentication 객체를 저장합니다. 
			//SecurityContextHolder.getContext().setAuthentication(authentication); 
			System.out.println("토큰 유효하다"); 
			} 
			chain.doFilter(request, response);
	}

	

}
