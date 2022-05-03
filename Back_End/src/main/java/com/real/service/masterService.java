package com.real.service;

import java.util.Map;

import com.real.dto.MemberVo;

public interface masterService {

	int AdminA(MemberVo member);

	Map<String, Object> AdminS(String keyword);

}
