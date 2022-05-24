package com.real.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.real.dto.MemberVo;

@Repository
public class mainDAO {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private static String namespace="main_mapper";
	

	public Map<String,Object> login(Map<String, Object> map) {
		System.out.println(map);
		return sqlSession.selectOne(namespace+".login", map);
	}
	
	public int updatePW(Map<String, Object> updateinfo) {
		return sqlSession.update(namespace+".updatePW", updateinfo);
	}

	public int stampCounts(Map<String, Object> param) {
		return sqlSession.selectOne(namespace+".stampCounts", param);
	}
	

	public List cw_list(Map<String, Object> map) {

		return  sqlSession.selectList(namespace+".CWlist",map);

	}
	
	public List excellist(Map<String, Object> excelinfo) {
		return sqlSession.selectList(namespace+".excellist",excelinfo);
	}

	public void stampSetting(Map<String, Object> stampinfo) {
		sqlSession.selectList(namespace+".stampSetting", stampinfo);
	}

	public Map<String, Object> getStampSetting(int idx) {
		return sqlSession.selectOne(namespace+".getStampSetting", idx);
	}

	public List stampList(Map<String, Object> data) {
		return sqlSession.selectList(namespace+".stampList", data);
	}

	public Map<String, Object> getStampDetail(Map<String, Object> map) {
		return sqlSession.selectOne(namespace+".getStampDetail", map);
	}

	public int updateStamp(Map<String, Object> map) {
		return sqlSession.update(namespace+".updateStamp", map);
	}

	public List noticeList(Map<String, Object> map) {
		return sqlSession.selectList(namespace+".noticeList", map);
	}
	
	public int noticeCounts(Map<String, Object> param) {
		return sqlSession.selectOne(namespace+".noticeCounts", param);
	}
	
	

	
	
}
