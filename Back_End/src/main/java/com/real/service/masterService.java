package com.real.service;

import java.util.Map;

import com.real.dto.MemberVo;

public interface masterService {

	int AdminA(MemberVo member);

	Map<String, Object> AdminS(String keyword);

	int AdminD(Map<String, Object> param);

	Map<String, Object> AdminG(String page);

	Map<String, Object> NoticeL(String page);

}
