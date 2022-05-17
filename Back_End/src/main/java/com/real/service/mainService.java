package com.real.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.real.dto.MemberVo;

public interface mainService {
	
	public Map<String,Object> login(Map<String, Object> param) ;
	
	public List cw_list(Map<String,Object> map);
	
	public int updatePW(Map<String, Object> updateinfo);

	public int totalcounts(Map<String, Object> param);

	public List excellist(Map<String, Object> excelinfo);

	public void stampSetting(Map<String, Object> stampinfo);

	public Map<String, Object> getStampSetting(int idx);


}
