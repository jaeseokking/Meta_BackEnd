package com.real.service;

import java.util.Map;

import com.real.dto.MemberVo;
import com.real.dto.NoticeVo;

public interface masterService {

	int AdminA(MemberVo member);

	Map<String, Object> AdminS(String keyword);

	int AdminD(String ListIDX);
	
	MemberVo AdminO(String idx);

	Map<String, Object> AdminG(String page);

	Map<String, Object> NoticeL(String page);

	int NoticeI(NoticeVo notice);

	int NoticeU(NoticeVo notice);

	int NoticeD(String listIDX);

	NoticeVo NoticeO(String idx);

	Map<String, Object> NoticeS(String keyword);



}
