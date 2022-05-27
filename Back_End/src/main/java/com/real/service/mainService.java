package com.real.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.real.dto.MemberVo;

public interface mainService {
	
	public Map<String,Object> login(Map<String, Object> param) ;
	
	public List<Object> cw_list(Map<String,Object> map);
	
	public int updatePW(Map<String, Object> param);

	public int totalcounts(Map<String, Object> param);

	public List<Object> excellist(Map<String, Object> excelinfo);

	public void stampSetting(Map<String, Object> stampinfo);

	public Map<String, Object> getStampSetting(int idx);

	public List<Object> stampList(Map<String, Object> data);

	public Map<String, Object> getStampDetail(Map<String, Object> map);

	public int updateStamp(Map<String, Object> map);

	public List<Object> noticeList(Map<String, Object> map);

	public Map<String, Object> noticeDetail(Map<String, Object> map);

	public List<Object> enquiryList(Map<String, Object> data);

	public Map<String, Object> getEnquiryDetail(Map<String, Object> map);

	public Map<String, Object> getEnquiryReply(Map<String, Object> map);

	public int enquiryWirte(Map<String, Object> data);

	public int enquiryUpdate(Map<String, Object> data);


}
