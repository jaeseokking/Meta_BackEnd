package com.real.config;

import java.beans.JavaBean;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
public class CORSFilter implements Filter{

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
		res.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
		res.setHeader("Access-Control-Max-Age", "3600");  
		chain.doFilter(request, response);

	}
	
	public void init(FilterConfig filterConfig)  {}

	

	public void destroy() {}

}
