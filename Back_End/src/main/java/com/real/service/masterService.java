package com.real.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.real.dto.MemberVo;
import com.real.dto.NoticeVo;

public interface masterService {

	int AdminA(MemberVo member);

	Map<String, Object> AdminS(String keyword);

	int AdminD(ArrayList listIDX);
	
	MemberVo AdminO(String idx);

	Map<String, Object> AdminG(String page);

	Map<String, Object> NoticeL(String page);

	int NoticeI(NoticeVo notice);

	int NoticeU(NoticeVo notice);

	int NoticeD(ArrayList listIDX);

	NoticeVo NoticeO(String idx);

	Map<String, Object> NoticeS(String keyword);

	Map<String, Object> EnquiryL(String page);

	Map<String, Object> EnquiryO(String idx);

	int EnquiryR(Map<String, Object> param);

	int AdminU(MemberVo member);



}
